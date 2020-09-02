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
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorScheduTimesBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:在线排班Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:59
 */
public class OnlineScheduPresenter extends
        BasePresenterImpl<OnlineScheduContract.View> implements OnlineScheduContract.Presenter {

    private static final String SEND_SEARCH_SCHEDU_MSG_CALANDAR_REQUEST_TAG
            ="send_search_scheduling_msg_request_tag";
    private static final String SEND_SEARCH_RESERVE_DOCTOR_DATE_ROSTERINFO_TAG
            ="send_search_reserve_doctor_date_rosterinfo_tag";

    private static final String SEND_GET_SIGNAL_SOURCE_REQUEST_TAG="send_get_signal_source_request_tag";

    private static final String SEND_OPER_UPD_DOCTOR_REQUEST_TAG="send_oper_upd_doctor_request_tag";

    private static final String SEND_OPER_DELDOCTOR_ROSTER_INFO_REQUEST_TAG="send_oper_doldoctor_roster_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_SCHEDU_MSG_CALANDAR_REQUEST_TAG
                ,SEND_SEARCH_RESERVE_DOCTOR_DATE_ROSTERINFO_TAG
                ,SEND_GET_SIGNAL_SOURCE_REQUEST_TAG
                ,SEND_OPER_UPD_DOCTOR_REQUEST_TAG,SEND_OPER_DELDOCTOR_ROSTER_INFO_REQUEST_TAG};
    }



    @Override
    public void sendSearchSchedulingMsgCalandarRequest(String mainDoctorCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveDoctorDateRosterInfoHeader(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            List<CalendarItemBean> calendarItemBeans =
                                    GsonUtils.jsonToList(resJsonData, CalendarItemBean.class);
                            mView.getSearchSchedulingMsgCalandarResult(calendarItemBeans);
                        }else{
                            mView.showEmpty();
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
                return SEND_SEARCH_SCHEDU_MSG_CALANDAR_REQUEST_TAG;
            }
        });
    }

    @Override
    public void searchReserveDoctorDateRosterInfoRequest(String mainDoctorCode, String times, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("times",times);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveDoctorDateRosterInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            List<DoctorScheduTimesBean> doctorScheduTimesBeans =
                                    GsonUtils.jsonToList(resJsonData, DoctorScheduTimesBean.class);
                            if(!CollectionUtils.isEmpty(doctorScheduTimesBeans)){
                                mView.getSearchReserveDoctorDateRosterInfoResult(doctorScheduTimesBeans);
                            }else{
                                mView.getSearchReserveDoctorDateRosterInfoResult(new ArrayList<>());
                            }

                        }else{
                            mView.showEmpty();
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
                return SEND_SEARCH_RESERVE_DOCTOR_DATE_ROSTERINFO_TAG;
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
                        return SEND_GET_SIGNAL_SOURCE_REQUEST_TAG;
                    }
                });
    }

    @Override
    public void sendOperUpdDoctorDateRosterInfoRequest(String mainDoctorCode, String mainDoctorName, String mainDoctorAlias,
                                                       String week, String reserveType,
                                                       String reserveTypeName, String times,
                                                       String startTimes, String endTimes, String reserveCount,
                                                       String checkStep, String reserveDateRosterCode, Activity activity) {
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
                        OperDoctorScheduResultBean operDoctorScheduResult =
                                GsonUtils.fromJson(baseBean.getResJsonData(), OperDoctorScheduResultBean.class);
                        mView.getOperDoctorScheduResult(operDoctorScheduResult);
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
    public void sendOperDelDoctorDateRosterInfoRequest(String reserveDateRosterCode, Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveDateRosterCode",reserveDateRosterCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operDelDoctorDateRosterInfo(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperDelDoctorDateRosterInfoResult(true,baseBean.getResMsg());
                    } else{
                        mView.getOperDelDoctorDateRosterInfoResult(false,baseBean.getResMsg());
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);

                if (mView!=null) {
                    mView.getOperDelDoctorDateRosterInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_DELDOCTOR_ROSTER_INFO_REQUEST_TAG;
            }
        });
    }


}
