package www.jykj.com.jykj_zxyl.activity.chapter.activity;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPriceBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChatperSourceBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.PayResult;
import www.jykj.com.jykj_zxyl.activity.chapter.contract.VideoChapterContract;
import www.jykj.com.jykj_zxyl.activity.chapter.presenter.VideoChapterPresenter;
import www.jykj.com.jykj_zxyl.activity.hyhd.VideoDetialPlayerActivity;
import www.jykj.com.jykj_zxyl.adapter.VideoChapterAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.ChapterPop;
import www.jykj.com.jykj_zxyl.wxapi.PayInfoBean;


public class VideoChapterActivity extends AbstractMvpBaseActivity<VideoChapterContract.View, VideoChapterPresenter>
        implements VideoChapterContract.View {
    @BindView(R.id.li_back)
    LinearLayout linBack;
    @BindView(R.id.recycleview)
    RecyclerView mRecycleview;
    @BindView(R.id.iv_tittle)
    ImageView ivTittle;
    @BindView(R.id.part_0)
    LinearLayout tittlePart;
    @BindView(R.id.click_num)
    TextView clickNum;//浏览
    @BindView(R.id.tittle_tv)
    TextView tittleTv;//标题
    @BindView(R.id.program_tv)
    TextView programTv;//类目名
    @BindView(R.id.iv_userhead)
    ImageView ivHead;//头像
    @BindView(R.id.name_tv)
    TextView nameTv;//姓名
    @BindView(R.id.price_tv)
    TextView priceTv;//价格
    @BindView(R.id.all_num)
    TextView allNum;//全部章节
    private JYKJApplication mApp;
    private ChapterPop chapterPop;
    private int mPayType = 0;
    private String courseWareCode;
    public IWXAPI msgApi;
    private Handler mHandler;
    private static final int SDK_PAY_FLAG = 3;
    private String detCode;
    private String payMoney;
    private double balanceMoney;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            courseWareCode = extras.getString("courseWareCode");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_video_chapter;
    }

    @Override
    public void showLoading(int code) {

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
                            mPresenter.getVideoChapterList(getParams(0));
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
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        chapterPop = new ChapterPop(this);

        chapterPop.setGo2PayListen((type, money) -> {

            mPayType = type;
            payMoney = money;
            if (chapterPop.isShowing()){
                chapterPop.dismiss();
            }
            mPresenter.go2Pay(getParams(3), Double.parseDouble(payMoney) <= 0 ? 3 : mPayType);
        });
        mPresenter.getAccountBalance(this);
    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getVideoChapterList(getParams(0));
        initHandler();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private String getParams(int type) {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("courseWareCode", type == 0 ? courseWareCode : detCode);//1e18a17de66441c781bfe8a98d6dc1fc
        stringStringHashMap.put("userType","5");
        switch (type) {
            case 0:  //页面
                stringStringHashMap.put("operUserCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                break;
            case 1: //价格详情

                break;
            case 2://资源
                stringStringHashMap.put("operUserCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                break;
            case 3://支付
                stringStringHashMap.put("operUserCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                stringStringHashMap.put("operUserName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                stringStringHashMap.put("requestClientType", 1);
                stringStringHashMap.put("payType", mPayType);//方式
                stringStringHashMap.put("orderType", 0);//购买类型
                stringStringHashMap.put("rewardPoints", 0);//积分
                stringStringHashMap.put("couponCode", "");//优惠券
                stringStringHashMap.put("payAmount", payMoney);//金额
                break;
        }
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @OnClick({R.id.li_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.li_back:
                finish();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(PayInfoBean msg) {
        LogUtils.e("收到刷新了 ");
        mPresenter.getVideoChapterList(getParams(0));
    }


    @Override
    public void getListSucess(ChapterListBean data) {
        Glide.with(this).load(data.getIconUrl()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                tittlePart.setBackground(ImageUtils.bitmap2Drawable(ImageUtils.fastBlur(ImageUtils.drawable2Bitmap(resource), 1, 25)));
                ivTittle.setImageDrawable(resource);
            }
        });
        allNum.setText(String.format("(共%d章节)", data.getSecondList().size()));
        clickNum.setText(String.format("浏览量: %d", data.getClickCount()));
        tittleTv.setText(data.getTitle());
        nameTv.setText(data.getCreateby());
        int freeType = data.getFreeType();//0免费,1收费
        priceTv.setText(String.format("¥ %s元", data.getPrice()));
        programTv.setText(String.format("类目: %s", data.getClassName()));
        Glide.with(this).load(data.getDoctorLogoUrl()).into(ivHead);
        List<ChapterListBean.SecondListBean> secondList = data.getSecondList();
        VideoChapterAdapter videoChapterAdapter = new VideoChapterAdapter(R.layout.item_videochapter, secondList);
        mRecycleview.setAdapter(videoChapterAdapter);
        videoChapterAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_price:
                        int freeTypePay = secondList.get(position).getFreeType();
                        int flagUserHasBuy = secondList.get(position).getFlagUserHasBuy();
                        detCode = secondList.get(position).getCode();
                        if (freeTypePay == 0) {//修改0
                            mPresenter.getChapterSource(getParams(2));
                        } else {
                            if (flagUserHasBuy == 0) { //没有买   修改0
                                chapterPop.setPayMoney(secondList.get(position).getPrice());
                                chapterPop.setBalanceMoney(balanceMoney+"");
                                chapterPop.showPop(tittlePart);
                            } else {
                                mPresenter.getChapterSource(getParams(2));
                            }
                        }
                        break;
                }
            }
        });
        videoChapterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转

            }
        });
    }

    @Override
    public void getDataFail(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getChatperPriceSucess(ChapterPriceBean bean) {

    }

    @Override
    public void getChapterSourceSucess(ChatperSourceBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("playUrl", bean.getLinkUrl());
        bundle.putString("courseWareCode", detCode);
        startActivity(VideoDetialPlayerActivity.class, bundle);
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
            PayTask alipay = new PayTask(VideoChapterActivity.this);
            Map<String, String> result = alipay.payV2(bean, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        new Thread(payRunnable).start();
    }

    @Override
    public void paySucess(String msg) {
        ToastUtils.showShort(msg);
        mPresenter.getVideoChapterList(getParams(0));
        mPresenter.getAccountBalance(this);
    }

    @Override
    public void getAccountBalanceResult(AccountBalanceBean accountBalanceBean) {
        balanceMoney=accountBalanceBean.getBalanceMoney();
    }
}
