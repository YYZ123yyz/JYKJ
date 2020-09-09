package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.hyphenate.easeui.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReceiveTreatmentResultBean;
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
    private static final String SEND_GET_USER_INFO_REQUEST_TAG="send_get_user_info_request_tag";

    private static final String SEND_GET_CANCEL_APPOINT_REQUEST_TAG="send_get_cancel_appoint_request_tag";

    private static final String SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG="" +
            "send_operconfirm_reserve_patient_doctor_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_APPOINT_PATIENT_LIST_REQUEST_TAG,SEND_GET_USER_INFO_REQUEST_TAG};
    }

    @Override
    public void sendSearchReservePatientDoctorInfoByStatusRequest(String mainDoctorCode,
                                                                  String reserveDate,
                                                                  String reserveStatus,
                                                                  String rowNum,
                                                                  String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
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
                                    patientInfoBeans = GsonUtils.jsonToList(resJsonData,
                                    PatientInfoBean.class);

                            if (!CollectionUtils.isEmpty(patientInfoBeans)) {
                                mView.getSearchReservePaitentDoctorInfoByStatusResult(patientInfoBeans);
                            }else{
                                mView.showEmpty();
                            }
                        }else{
                            mView.getSearchReservePaitentDoctorInfoByStatusResult(new ArrayList<>());
                        }

                    }else{
                        mView.showRetry();
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

    @Override
    public void sendGetUserInfoRequest(String userCodeList) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCodeList",userCodeList);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfoListAndService(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                System.out.println(baseBean);
            }

            @Override
            protected String setTag() {
                return SEND_GET_USER_INFO_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendCancelAppointReasonRequest(String baseCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (mView!=null) {
                            int resCode = baseBean.getResCode();
                            if (resCode==1) {
                                List<BaseReasonBean> baseReasonBeans =
                                        GsonUtils.jsonToList(baseBean.getResJsonData(), BaseReasonBean.class);
                                mView.getCancelAppointReasonResult(baseReasonBeans);
                            }
                        }
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_CANCEL_APPOINT_REQUEST_TAG;
                    }
                });
    }

    @Override
    public void sendOperConfirmReservePatientDoctorInfoRequest(String reserveCode, String reserveRosterDateCode, String mainDoctorCode, String mainDoctorName, String mainPatientCode, String mainPatientName, String version, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveCode",reserveCode);
        hashMap.put("reserveRosterDateCode",reserveRosterDateCode);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainPatientName",mainPatientName);
        hashMap.put("version",version);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operConfirmReservePatientDoctorInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(101);
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
                            ReceiveTreatmentResultBean receiveTreatmentResultBean
                                    = GsonUtils.fromJson(resJsonData, ReceiveTreatmentResultBean.class);
                            mView.getOperConfirmReservePatientDoctorInfoResult(receiveTreatmentResultBean);
                        }

                    }else{
                        mView.getOperConfirmReservePatientDoctorInfoError(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperConfirmReservePatientDoctorInfoError(s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG;
            }
        });
    }
}
