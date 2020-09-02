package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:我的诊所详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 15:19
 */
public class MyClinicDetialPresenter extends BasePresenterImpl<MyClinicDetialContract.View>
        implements MyClinicDetialContract.Presenter {

    private static final String SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG=
            "send_search_reserve_patient_doctor_info_request_tag";
    private static final String SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG="" +
            "send_operconfirm_reserve_patient_doctor_request_tag";
    private static final String SEND_GET_CANCEL_APPOINT_REQUEST_TAG="send_get_cancel_appoint_request_tag";

    private static final String SEND_GET_PRICE_REGION_REQUEST_TAG="send_get_price_region_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG
                ,SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG
                ,SEND_GET_CANCEL_APPOINT_REQUEST_TAG,SEND_GET_PRICE_REGION_REQUEST_TAG};
    }



    @Override
    public void sendSearchReservePatientDoctorInfoRequest(String mainDoctorCode, String treatmentType, String rowNum, String pageNum, String mainPatientName, String ageMax, String ageMin, String reserveStartDate, String reserveEndDate, String priceRegion, String reserveStatus, String dateSort, String priceSort, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("treatmentType",treatmentType);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        hashMap.put("mainPatientName",mainPatientName);
        hashMap.put("ageMax",ageMax);
        hashMap.put("ageMin",ageMin);
        hashMap.put("reserveStartDate",reserveStartDate);
        hashMap.put("reserveEndDate",reserveEndDate);
        hashMap.put("priceRegion",priceRegion);
        hashMap.put("reserveStatus",reserveStatus);
        hashMap.put("dateSort",dateSort);
        hashMap.put("priceSort",priceSort);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReservePatientDoctorInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                            List<PatientInfoBean> patientInfoBeans
                                    = GsonUtils.jsonToList(resJsonData, PatientInfoBean.class);
                            mView.getSearchReservePatientDoctorInfoResult(patientInfoBeans);
                        }else{
                            mView.getSearchReservePatientDoctorInfoResult(new ArrayList<>());
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
                return SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendOperConfirmReservePatientDoctorInfoRequest(String reserveCode, String reserveRosterDateCode
            , String mainDoctorCode, String mainDoctorName, String mainPatientCode,
                                                               String mainPatientName, String version,Activity activity) {
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
                        mView.getOperConfirmReservePatientDoctorInfoResult(true,baseBean.getResMsg());
                    }else{
                        mView.getOperConfirmReservePatientDoctorInfoResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperConfirmReservePatientDoctorInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendOperCancelReservePatientDoctorInfoRequest(String reserveCode, String reserveRosterDateCode, String mainDoctorCode, String mainDoctorName, String mainPatientCode, String mainPatientName, String version, String cancelReserveCode, String cancelReserveName, String cancelReserveRemark, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveCode",reserveCode);
        hashMap.put("reserveRosterDateCode",reserveRosterDateCode);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainPatientName",mainPatientName);
        hashMap.put("version",version);
        hashMap.put("cancelReserveCode",cancelReserveCode);
        hashMap.put("cancelReserveName",cancelReserveName);
        hashMap.put("cancelReserveRemark",cancelReserveRemark);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operCancelReservePatientDoctorInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(102);
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
                        mView.getOperCancelReservePatientDoctorInfoResult(true,baseBean.getResMsg());
                    }else{
                        mView.getOperCancelReservePatientDoctorInfoResult(false,baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperCancelReservePatientDoctorInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return super.setTag();
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
    public void sendPriceRegionReasonRequest(String baseCode) {
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
                                mView.getPriceRegionReasonResult(baseReasonBeans);
                            }
                        }
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_PRICE_REGION_REQUEST_TAG;
                    }
                });
    }
}
