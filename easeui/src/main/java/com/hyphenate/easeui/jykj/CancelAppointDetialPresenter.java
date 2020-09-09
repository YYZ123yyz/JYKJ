package com.hyphenate.easeui.jykj;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CancelAppointOrderDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description: 发送取消预期详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-09 14:11
 */
public class CancelAppointDetialPresenter extends BasePresenterImpl<CancelAppointDetialContract.View>
        implements CancelAppointDetialContract.Presenter {

    private static final String SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG=
            "send_search_reserve_patient_doctor_info_request_tag";
    @Override
    public void sendSearchReservePatientDoctorInfoXxRequest(String reserveCode, Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveCode",reserveCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getSearchReservePatientDoctorInfoXx(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(100);
                }
            }

            @Override
            public void hideLoadingView() {

                if (mView!=null) {
                    mView.hideLoading();
                }

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            CancelAppointOrderDetialBean cancelAppointOrderDetialBean
                                    = GsonUtils.fromJson(resJsonData, CancelAppointOrderDetialBean.class);
                            mView.getSearchReservePatientDoctorInfoxxResult(cancelAppointOrderDetialBean);
                        }
                    }else{
                        mView.showRetry();
                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG;
            }
        });
    }

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG};
    }


}
