package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.ApiUtils;
import com.hyphenate.easeui.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DcotorScheduTimesWeekBean;
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
 * Description:排班设置模版
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 10:55
 */
public class OnlineScheduTemplatePresenter extends BasePresenterImpl<OnlineScheduTemplateContract
        .View> implements OnlineScheduTemplateContract.Presenter {
    private static final String SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_HEADER_REQUEST_TAG=
            "send_search_reserve_doctor_info_header_request_tag";

    private static final String SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG="" +
            "send_search_reserve_doctor_info_request_tag";
    private static final String SEND_OPER_DELRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG="" +
            "send_oper_delreserve_doctor_info_request_tag";

    private static final String SEND_OPER_UPDRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG="" +
            "send_oper_updreserve_doctor_roster_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{
                SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_HEADER_REQUEST_TAG
                ,SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG
                ,SEND_OPER_DELRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG
                ,SEND_OPER_UPDRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG
        };
    }

    @Override
    public void sendSearchReserveDoctorRosterInfoHeaderRequest(String mainDoctorCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveDoctorRosterInfoHeader(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                            mView.getSearchReserveDoctorRosterInfoHeaderResult(calendarItemBeans);
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
                return SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_HEADER_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSearchReserveDoctorRosterInfoRequest(String mainDoctorCode, String week, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("week",week);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveDoctorRosterInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                            List<DcotorScheduTimesWeekBean> doctorScheduTimesBeans =
                                    GsonUtils.jsonToList(resJsonData, DcotorScheduTimesWeekBean.class);
                            if(!CollectionUtils.isEmpty(doctorScheduTimesBeans)){
                                mView.getSearchReserveDoctorRosterInfoResult(doctorScheduTimesBeans);
                            }else{
                                mView.getSearchReserveDoctorRosterInfoResult(new ArrayList<>());
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
                return SEND_SEARCH_RESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendOperDelReserveDoctorRosterInfoRequest(String reserveDoctorRosterCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveDoctorRosterCode",reserveDoctorRosterCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operDelReserveDoctorRosterInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperDelReserveDoctorRosterInfoResult(true,baseBean.getResMsg());
                    }else{
                        mView.getOperDelReserveDoctorRosterInfoResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperDelReserveDoctorRosterInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_DELRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendOperUpdReserveDoctorRosterInfoRequest(String mainDoctorCode, String mainDoctorName
            , String mainDoctorAlias, String week, String weekView, String startTimes, String endTimes
            , String reserveCount, String checkStep, String reserveDoctorRosterCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("mainDoctorAlias",mainDoctorAlias);

        hashMap.put("startTimes",startTimes);
        hashMap.put("endTimes",endTimes);
        hashMap.put("reserveCount",reserveCount);
        hashMap.put("checkStep",checkStep);
        if (StringUtils.isNotEmpty(reserveDoctorRosterCode)) {
            hashMap.put("reserveDoctorRosterCode",reserveDoctorRosterCode);

        }else {
            hashMap.put("week",week);
            hashMap.put("weekView",weekView);
        }
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdReserveDoctorRosterInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        OperDoctorScheduResultBean operDoctorScheduResult =
                                GsonUtils.fromJson(resJsonData, OperDoctorScheduResultBean.class);
                        String status = "";
                        if (operDoctorScheduResult != null) {
                            status = operDoctorScheduResult.getStatus();
                        }
                        if (StringUtils.isNotEmpty(status) && status.equals("1")) {
                            mView.getOperUpdReservedDoctorRosterInfoCheckStepConfirm(baseBean.getResMsg());
                        } else {
                            mView.getOperUpdReserveDoctorRosterInfoRequest(true, baseBean.getResMsg());
                        }

                    }else{
                        mView.getOperUpdReserveDoctorRosterInfoRequest(false,baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperUpdReserveDoctorRosterInfoRequest(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_UPDRESERVE_DOCTOR_ROSTER_INFO_REQUEST_TAG;
            }
        });
    }


}
