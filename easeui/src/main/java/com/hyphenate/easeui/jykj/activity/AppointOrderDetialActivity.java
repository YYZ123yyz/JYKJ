package com.hyphenate.easeui.jykj.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.AppointOrderDetialContract;
import com.hyphenate.easeui.jykj.AppointOrderDetialPresenter;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.OrderInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

/**
 * Description:预约订单详情
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 09:59
 */
public class AppointOrderDetialActivity
        extends AbstractMvpBaseActivity<AppointOrderDetialContract.View,
                AppointOrderDetialPresenter> implements AppointOrderDetialContract.View{
    private TextView tvOrderType;
    private TextView tvPaymentStatus;
    private TextView tvConsultDoctor;
    private TextView tvConsultant;
    private TextView tvConsultantPhone;
    private TextView tvOrderDate;
    private TextView tvAppointServiceTime;
    private TextView tvServiceTotalPrice;
    private TextView tvCoupon;
    private TextView tvIntegral;
    private TextView tvServiceValidity;
    private TextView tvServiceProject;
    private TextView tvActualPayment;
    private TextView tvPaymentMode;
    private TextView tvOrderDesc;
    private ImageView ivPaymentIcon;
    private BaseToolBar toolbar;
    private ScrollView mScrollView;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private String reserveCode;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_appoint_order_detial;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            reserveCode= extras.getString("reserveCode");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        toolbar=findViewById(R.id.toolbar);
        mScrollView=findViewById(R.id.scroll_view);
        tvOrderType=findViewById(R.id.tv_order_type);
        tvPaymentStatus=findViewById(R.id.tv_payment_status);
        tvConsultDoctor=findViewById(R.id.tv_consult_doctor);
        tvConsultant=findViewById(R.id.tv_consultant);
        tvConsultantPhone=findViewById(R.id.tv_consultant_phone);
        tvOrderDate=findViewById(R.id.tv_order_date);
        tvAppointServiceTime=findViewById(R.id.tv_appoint_service_time);
        tvServiceTotalPrice=findViewById(R.id.tv_service_total_price);
        tvCoupon=findViewById(R.id.tv_coupon);
        tvIntegral=findViewById(R.id.tv_integral);
        tvServiceValidity=findViewById(R.id.tv_service_validity);
        tvServiceProject=findViewById(R.id.tv_service_project);
        tvActualPayment=findViewById(R.id.tv_actual_payment);
        tvPaymentMode=findViewById(R.id.tv_payment_mode);
        ivPaymentIcon=findViewById(R.id.iv_payment_icon);
        tvOrderDesc=findViewById(R.id.tv_order_desc);


        setToolBar();
        initLoadingAndRetryManager();
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchReserveInfoRequest(reserveCode,this);

    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("订单详情");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mScrollView);
        mLoadingLayoutManager.setRetryListener(v -> {

        });
        mLoadingLayoutManager.showLoading();

    }

    @Override
    public void showLoading(int code) {

    }

    /**
     * 设置数据
     * @param orderInfoBean 订单详情
     */
    private void setData(OrderInfoBean orderInfoBean){
        int treatmentType = orderInfoBean.getTreatmentType();
        switch (treatmentType){
            case 1:
                tvOrderType.setText("图文就诊");
                break;
            case 2:
                tvOrderType.setText("音频就诊");
                break;
            case 3:
                tvOrderType.setText("视频就诊");
                break;
            case 5:
                tvOrderType.setText("电话就诊");
                break;
                default:
        }

        int paymentMode = orderInfoBean.getPaymentMode();
        switch (paymentMode){
            case 1:
                tvPaymentMode.setText("微信支付");
                ivPaymentIcon.setImageResource(R.mipmap.bg_weixin_icon);
                break;
            case 2:
                tvPaymentMode.setText("支付宝支付");
                ivPaymentIcon.setImageResource(R.mipmap.bg_alipay_icon);
                break;
            case 3:
                tvPaymentMode.setText("银联支付");
                break;
                default:
        }


        tvPaymentStatus.setText(orderInfoBean.getFlagOrderStateName());
        tvConsultDoctor.setText(orderInfoBean.getMainDoctorName());
        tvConsultant.setText(orderInfoBean.getMainUserName());
        tvConsultantPhone.setText(orderInfoBean.getLinkPhone());
        long createDate = orderInfoBean.getCreateDate();
        tvOrderDate.setText(DateUtils.getDateToYYYYMMDDHHMM(createDate));
        long reserveTime = orderInfoBean.getReserveTime();
        tvAppointServiceTime.setText(DateUtils.getDateToYYYYMMDDHHMM(reserveTime));
        tvServiceTotalPrice.setText(String.format("%s元", orderInfoBean.getServiceTotal()));
        tvActualPayment.setText(String.format("%s元", orderInfoBean.getActualPayment()));
        String orderDesc = orderInfoBean.getOrderDesc();
        tvOrderDesc.setText(StringUtils.isNotEmpty(orderDesc)?orderDesc:"无");

    }

    @Override
    public void getSearchReserveInfoResult(OrderInfoBean orderInfoBeans) {
        setData(orderInfoBeans);
        mLoadingLayoutManager.showContent();
    }

    @Override
    public void getSearchReserveInfoError() {
        mLoadingLayoutManager.showError();
    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }
}
