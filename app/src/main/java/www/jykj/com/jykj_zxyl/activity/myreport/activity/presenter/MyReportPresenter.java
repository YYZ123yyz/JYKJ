package www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.MyReportContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class MyReportPresenter    extends
        BasePresenterImpl<MyReportContract.View> implements MyReportContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendyReportRequest(String loginDoctorPosition, String operDoctorCode, String operDoctorName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("operDoctorCode",operDoctorCode);
        hashMap.put("operDoctorName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getMyReport(s)
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

                        List<ReportBean> reportBeans = GsonUtils.jsonToList(baseBean.getResJsonData(), ReportBean.class);
                        mView.getmyReportResult(reportBeans);
                        //   mView.getSearchMyClinicDetailResPatientMessageContentResult(diagnosisReplayBean);
                    }
                }

            }

        });
    }
}
