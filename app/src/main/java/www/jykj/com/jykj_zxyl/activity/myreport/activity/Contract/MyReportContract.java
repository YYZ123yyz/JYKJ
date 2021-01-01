package www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract;

import java.util.List;

import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
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
