package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.PayResult;
import www.jykj.com.jykj_zxyl.app_base.base_utils.VerificationUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargeContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargePresenter;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.CommonPayConfirmDialog;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.CommonPayPwdCheckDialog;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.wxapi.PayInfoBean;

public class RechargeActivity extends AbstractMvpBaseActivity<RechargeContract.View
        , RechargePresenter> implements RechargeContract.View {

    @BindView(R.id.iv_weichat_choose)
    ImageView weichatIv;
    @BindView(R.id.iv_ali_choose)
    ImageView aliIv;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.go2pay_tv)
    TextView tvGo2PayBtn;
    private String payType="1";//支付方式
    private JYKJApplication mApp;
    public IWXAPI msgApi;
    private Handler mHandler;
    private static final int SDK_PAY_FLAG = 3;
    private CommonPayPwdCheckDialog commonPayPwdCheckDialog;
    private CommonPayConfirmDialog commonPayConfirmDialog;
    private BaseToolBar toolbar;
    private ImageButton imageButtonE;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_recharge;
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        String resultStatus = payResult.getResultStatus();

                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            //mPresenter.getVideoChapterList(getParams(0));
                            //ToastUtils.showShort("支付成功");
                            commonPayConfirmDialog.show();
                            String value = edInputContent.getText().toString();
                            commonPayConfirmDialog.setData(value);
                        } else if (TextUtils.equals(resultStatus, "6001")) {
//                         用户取消
                            //ToastUtils.showShort("支付失败");
                        } else {

                        }

                        break;

                    default:
                        break;
                }
            }
        };
    }


    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        commonPayPwdCheckDialog=new CommonPayPwdCheckDialog(this);
        commonPayConfirmDialog=new CommonPayConfirmDialog(this);
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.top);
        setToolBar();


    }

    private void setToolBar() {
        toolbar.setMainTitle("充值");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        //add
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(RechargeActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initHandler();
        mApp = (JYKJApplication) getApplication();
        weichatIv.setSelected(true);
        aliIv.setSelected(false);
        addListener();
    }

    /**
     * 添加监听
     */
    private void addListener(){
        edInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                boolean number = VerificationUtils.isNumber(value);
                if(number){
                    tvGo2PayBtn.setTextColor(ContextCompat.getColor(RechargeActivity.this,R.color.color_white));
                    tvGo2PayBtn.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
                }else{
                    tvGo2PayBtn.setTextColor(ContextCompat.getColor(RechargeActivity.this,R.color.color_666666));
                    tvGo2PayBtn.setBackgroundResource(R.drawable.bg_round_eeeeee_15);
                }
            }
        });
        commonPayPwdCheckDialog.setOnCompleteListener(pwd -> {
           mPresenter.checkPassword(getParams(pwd));
            commonPayPwdCheckDialog.dismiss();
        });
        commonPayConfirmDialog.setOnClickListener(new CommonPayConfirmDialog.OnClickListener() {
            @Override
            public void onClickNotPay() {
                commonPayConfirmDialog.dismiss();
            }

            @Override
            public void onClickPaid() {
                mPresenter.sendGetOrderStatusRequest(payType,RechargeActivity.this);
            }
        });
    }

    @OnClick({R.id.go2pay_tv, R.id.ali_layout, R.id.weichat_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go2pay_tv:
                String value = edInputContent.getText().toString();
                if (TextUtils.isEmpty(value)) {
                    ToastUtils.showShort("请输入充值金额");

                }
                boolean number = VerificationUtils.isNumber(value);
                if (!number) {
                    ToastUtils.showShort("请输入正确的金额");
                    return;
                }
                commonPayPwdCheckDialog.show();
                commonPayPwdCheckDialog.setData(value);


                break;
            case R.id.ali_layout:
                weichatIv.setSelected(false);
                aliIv.setSelected(true);
                payType="2";
                break;

            case R.id.weichat_layout:
                weichatIv.setSelected(true);
                aliIv.setSelected(false);
                payType="1";
                break;
        }

    }



    @Override
    public void showLoading(int code) {
        if (code==102||code==100) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }



    private String getParams(String pwd) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("pwd", pwd);
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }


    @Override
    public void getPayInfoSucess(ChapterPayBean bean) {
        if (null == msgApi) {
            msgApi = WXAPIFactory.createWXAPI(this, "wxaf6f64f6a5878261");
            msgApi.registerApp("wxaf6f64f6a5878261");
        }
        PayReq request = new PayReq();
        request.appId = bean.getAppId();
        request.partnerId = bean.getPartnerid();
        String prepare_id = bean.getPrepayid();
        request.prepayId = prepare_id;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = bean.getNonceStr();
        request.timeStamp = bean.getTimeStamp();
        request.sign = bean.getSign();
        request.signType = "MD5";
        msgApi.sendReq(request);
    }

    @Override
    public void getAliPayInfoSucess(String bean) {
        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(RechargeActivity.this);
            Map<String, String> result = alipay.payV2(bean, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        new Thread(payRunnable).start();
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getAccountDoctorBalanceInfoPayError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void checkPasswordResult(boolean isSucess, String msg) {
        if (isSucess) {
            String value = edInputContent.getText().toString();
            mPresenter.sendAccountDoctorBalanceInfoPayRequest(payType,value,this);
        }else{
            ToastUtils.showShort(msg);
        }
    }

    @Override
    public void getOrderStatusResult(boolean isSucess, String msg) {
        if(isSucess){
            this.finish();
        }else{
            ToastUtils.showShort(msg);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(PayInfoBean msg) {
        commonPayConfirmDialog.show();
        String value = edInputContent.getText().toString();
        commonPayConfirmDialog.setData(value);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

