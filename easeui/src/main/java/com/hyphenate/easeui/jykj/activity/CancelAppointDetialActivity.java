package com.hyphenate.easeui.jykj.activity;


import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.CancelAppointDetialContract;
import com.hyphenate.easeui.jykj.CancelAppointDetialPresenter;

import www.jykj.com.jykj_zxyl.app_base.base_bean.CancelAppointOrderDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

/**
 * Description:取消预约详情
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 16:07
 */
public class CancelAppointDetialActivity extends AbstractMvpBaseActivity<
        CancelAppointDetialContract.View, CancelAppointDetialPresenter>
        implements CancelAppointDetialContract.View {

    private TextView mTvCancelContract;
    private TextView mTvCancelContractDesc;
    private LinearLayout mLLContentRoot;
    private BaseToolBar toolBar;
    private String orderCode;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    @Override
    protected int setLayoutId() {
        return R.layout.activity_cancel_appoint_detial;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderCode=extras.getString("orderCode");
        }
        mTvCancelContract=findViewById(R.id.tv_cancel_contract);
        mTvCancelContractDesc=findViewById(R.id.tv_cancel_contract_desc);
        mLLContentRoot=findViewById(R.id.ll_content_root);
        toolBar=findViewById(R.id.toolbar);
        setToolBar();
        initLoadingAndRetryManager();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchReservePatientDoctorInfoXxRequest(orderCode,this);

    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mLLContentRoot);
        mLoadingLayoutManager.setRetryListener(v -> {
            mPresenter.sendSearchReservePatientDoctorInfoXxRequest(orderCode,this);
        });
        mLoadingLayoutManager.showLoading();

    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolBar.setMainTitle("取消预约");
        //返回键
        toolBar.setLeftTitleClickListener(view -> finish());
    }


    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getSearchReservePatientDoctorInfoxxResult(CancelAppointOrderDetialBean cancelAppointOrderDetialBean) {
        setData(cancelAppointOrderDetialBean);
        mLoadingLayoutManager.showContent();
    }

    /**
     * 设置数据
     * @param cancelAppointOrderDetialBean 订单详情信息
     */
    private void setData(CancelAppointOrderDetialBean cancelAppointOrderDetialBean){
        mTvCancelContract.setText(cancelAppointOrderDetialBean.getCancelReserveName());
        mTvCancelContractDesc.setText(cancelAppointOrderDetialBean.getCancelReserveRemark());
    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }

    @Override
    public void showRetry() {
        mLoadingLayoutManager.showError();
    }
}
