package www.jykj.com.jykj_zxyl.personal;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DiagnosisReplayBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:诊后留言Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-15 16:25
 */
public class DiagnosisReplayPresenter extends
        BasePresenterImpl<DiagnosisReplayContract.View> implements DiagnosisReplayContract.Presenter {

    private static final String SEND_OPER_DIAGNOSIS_REPLAY_REQUEST_TAG=
            "send_oper_diagnosis_replay_request_tag";
    private static final String SEND_GET_DIAGNOSIS_REPLAY_REQUEST_TAG="" +
            "send_get_diagnosis_replay_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_OPER_DIAGNOSIS_REPLAY_REQUEST_TAG
                ,SEND_GET_DIAGNOSIS_REPLAY_REQUEST_TAG};
    }


    @Override
    public void sendOperUpdMyClinicDetailByOrderPatientMessageRequest(String messageId,
                                                                      String orderCode,
                                                                      String treatmentType,
                                                                      String replyContent,
                                                                      String replyType,
                                                                      String patientCode,
                                                                      String patientName,
                                                                      String patientPhone,
                                                                      Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("messageId",messageId);
        hashMap.put("orderCode",orderCode);
        hashMap.put("treatmentType",treatmentType);
        hashMap.put("replyContent",replyContent);
        hashMap.put("replyType",replyType);
        hashMap.put("patientCode",patientCode);
        hashMap.put("patientName",patientName);
        hashMap.put("patientPhone",patientPhone);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdMyClinicDetailByOrderPatientMessage_20201012(s)
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
                        mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());
                    }else{
                        mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                            ,s);
                }

            }

            @Override
            protected String setTag() {
                return SEND_OPER_DIAGNOSIS_REPLAY_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSearchMyClinicDetailResPatientMessageContent_20201012Request(String orderCode,
                                                                                 Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMyClinicDetailResPatientMessageContent_20201012(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        DiagnosisReplayBean diagnosisReplayBean =
                                GsonUtils.fromJson(baseBean.getResJsonData(), DiagnosisReplayBean.class);
                        mView.getSearchMyClinicDetailResPatientMessageContentResult(diagnosisReplayBean);
                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_GET_DIAGNOSIS_REPLAY_REQUEST_TAG;
            }
        });
    }

}
