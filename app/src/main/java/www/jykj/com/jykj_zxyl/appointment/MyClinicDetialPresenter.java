package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReceiveTreatmentResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TimelyTreatmentBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;
import yyz_exploit.Utils.GsonUtil;

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

    private static final String SEND_GET_RESERVE_DOCTOR_DATE_INFO_IMMEDIATE_REQUEST_TAG="" +
            "send_get_reserve_doctor_date_info_immediate_request_tag";

    private static final String SEND_GET_SIGNAL_SOURCE_TYPE_REQUEST_TAG="send_get_signal_source_type_request_tag";

    private static final String SEND_OPER_UPD_DOCTOR_REQUEST_TAG="send_oper_upd_doctor_request_tag";

    private static final String SEND_GET_USER_INFO_REQUEST_TAG="send_get_user_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_RESERVE_PATIENT_DOCTOR_INFO_REQUEST_TAG
                ,SEND_OPERCONFIRM_RESERVE_PATIENT_DOCTOR_REQUEST_TAG
                ,SEND_GET_CANCEL_APPOINT_REQUEST_TAG
                ,SEND_GET_PRICE_REGION_REQUEST_TAG
                ,SEND_GET_RESERVE_DOCTOR_DATE_INFO_IMMEDIATE_REQUEST_TAG
                ,SEND_GET_SIGNAL_SOURCE_TYPE_REQUEST_TAG
                ,SEND_OPER_UPD_DOCTOR_REQUEST_TAG,SEND_GET_USER_INFO_REQUEST_TAG};
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

    @Override
    public void sendSearchReserveDoctorDateRosterInfoImmediateRequest(String mainDoctorCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveDoctorDateRosterInfoImmediate(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(103);
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
                            List<TimelyTreatmentBean> timelyTreatmentBeans
                                    = GsonUtils.jsonToList(resJsonData, TimelyTreatmentBean.class);

                            mView.getTimelyTreatmentListResult(timelyTreatmentBeans);
                        }else{
                            mView.getTimelyTreatmentListResult(new ArrayList<>());
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
                return SEND_GET_RESERVE_DOCTOR_DATE_INFO_IMMEDIATE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendGetSignalSourceTypeRequest(String baseCode) {

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
                                mView.getSignalSourceTypeResult(baseReasonBeans);
                            }
                        }
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_SIGNAL_SOURCE_TYPE_REQUEST_TAG;
                    }
                });

    }

    @Override
    public void sendOperUpdDoctorDateRosterInfoRequest(String mainDoctorCode, String mainDoctorName, String mainDoctorAlias, String week, String reserveType, String reserveTypeName, String times, String startTimes, String endTimes, String reserveCount, String checkStep, String reserveDateRosterCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("mainDoctorAlias",mainDoctorAlias);
        hashMap.put("week",week);
        hashMap.put("reserveType",reserveType);
        hashMap.put("reserveTypeName",reserveTypeName);
        hashMap.put("times",times);
        hashMap.put("startTimes",startTimes);
        hashMap.put("endTimes",endTimes);
        hashMap.put("reserveCount",reserveCount);
        hashMap.put("checkStep",checkStep);
        if (StringUtils.isNotEmpty(reserveDateRosterCode)) {
            hashMap.put("reserveDateRosterCode",reserveDateRosterCode);
        }
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDoctorDateRosterInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(104);
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
                        OperDoctorScheduResultBean operDoctorScheduResult =
                                GsonUtils.fromJson(baseBean.getResJsonData(), OperDoctorScheduResultBean.class);

                        String status="";
                        if (operDoctorScheduResult!=null) {
                            status = operDoctorScheduResult.getStatus();
                        }
                        if (StringUtils.isNotEmpty(status)&&status.equals("1")) {
                            mView.getOperDoctorCheckStepConfirm(operDoctorScheduResult.getMessage());
                        }else{
                            mView.getOperDoctorScheduResult(operDoctorScheduResult);
                        }

                    }else{
                        mView.getOperDoctorScheduError(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperDoctorScheduError(s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_UPD_DOCTOR_REQUEST_TAG;
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


}
