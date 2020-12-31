package www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.MyReportContract;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class MyReportPresenter    extends
        BasePresenterImpl<MyReportContract.View> implements MyReportContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendyReportRequest(String loginDoctorPosition, String operDoctorCode, String operDoctorName) {
        /*HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("operDoctorCode",operDoctorCode);
        hashMap.put("operDoctorName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);*/
    }
}
