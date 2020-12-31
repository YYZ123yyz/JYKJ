package www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.activity.home.jyzl.PatientBaseInfoContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.ReportBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class MyReportContract {
    public interface View extends BaseView {
        /**
         * 疾病类型
         * @param reportBeans
         */
        void getmyReportResult(List<ReportBean> reportBeans);
    }

    public interface Presenter extends BasePresenter<MyReportContract.View> {
        void sendyReportRequest(String loginDoctorPosition, String operDoctorCode,
                                String operDoctorName);

    }
}
