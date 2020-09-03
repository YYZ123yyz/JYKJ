package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:预约患者Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-02 17:27
 */
public class AppointPatientListPresenter extends BasePresenterImpl<AppointPatientListContract.View>
        implements AppointPatientListContract.Presenter {
    private static final String SEND_APPOINT_PATIENT_LIST_REQUEST_TAG="" +
            "send_appoint_patient_list_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_APPOINT_PATIENT_LIST_REQUEST_TAG};
    }

    @Override
    public void sendSearchReservePatientDoctorInfoByStatusRequest(String mainDoctorCode,
                                                                  String reserveDate,
                                                                  String reserveStatus,
                                                                  String rowNum,
                                                                  String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveDate",reserveDate);
        hashMap.put("reserveStatus",reserveStatus);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReservePatientDoctorInfoByStatus(s)
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
                            List<PatientInfoBean>
                                    patientInfoBeans = GsonUtils.jsonToList(resJsonData, PatientInfoBean.class);
                            mView.getSearchReservePaitentDoctorInfoByStatusResult(patientInfoBeans);

                        }else{
                            mView.getSearchReservePaitentDoctorInfoByStatusResult(new ArrayList<>());
                        }

                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_APPOINT_PATIENT_LIST_REQUEST_TAG;
            }
        });
    }
}
