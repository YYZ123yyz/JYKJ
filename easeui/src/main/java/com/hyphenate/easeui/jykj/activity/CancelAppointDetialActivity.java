package com.hyphenate.easeui.jykj.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.hyphenate.easeui.R;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;

/**
 * Description:取消预约详情
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 16:07
 */
public class CancelAppointDetialActivity extends BaseActivity {

    private TextView mTvCancelContract;
    private TextView mTvCancelContractDesc;
    private BaseToolBar toolBar;
    private String orderCode;
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
        toolBar=findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        sendSearchSignPatientDoctorOrderRequest(orderCode,this);

    }

    /**
     * 获取签约订单请求
     * @param signOrderCode 订单Id
     * @param activity 当前activity
     */
    private void sendSearchSignPatientDoctorOrderRequest(String signOrderCode, Activity activity){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("signOrderCode",signOrderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveInfo(s)
                .compose(Transformer.<String>switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {

            }
        });
    }
}
