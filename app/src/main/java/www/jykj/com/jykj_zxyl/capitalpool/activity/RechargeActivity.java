package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.PayResult;
import www.jykj.com.jykj_zxyl.app_base.base_utils.VerificationUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargeContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargePresenter;
public class RechargeActivity extends AbstractMvpBaseActivity<RechargeContract.View
        , RechargePresenter> implements RechargeContract.View {

    @BindView(R.id.iv_weichat_choose)
    ImageView weichatIv;
    @BindView(R.id.iv_ali_choose)
    ImageView aliIv;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    private String payType="1";//支付方式
    private JYKJApplication mApp;
    public IWXAPI msgApi;
    private Handler mHandler;
    private static final int SDK_PAY_FLAG = 3;
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
                        /**
                         * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();

                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            //mPresenter.getVideoChapterList(getParams(0));
                            ToastUtils.showShort("支付成功");
                        } else if (TextUtils.equals(resultStatus, "6001")) {
//                         用户取消
                            ToastUtils.showShort("支付失败");
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

    }

    @Override
    protected void initData() {
        super.initData();
        initHandler();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getDocdorAsset(getParams());
        mPresenter.getCardList(getParams());
        weichatIv.setSelected(true);
        aliIv.setSelected(false);
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
                mPresenter.sendAccountDoctorBalanceInfoPayRequest(payType,value,
                        RechargeActivity.this);

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
        if (code==102) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
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
}

