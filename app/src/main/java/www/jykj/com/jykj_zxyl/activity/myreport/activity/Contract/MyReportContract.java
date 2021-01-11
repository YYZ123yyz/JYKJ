package www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract;

import java.util.List;

import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.CommitBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class MyReportContract {
    public interface View extends BaseView {
        /**
         * 疾病类型
         *
         * @param reportBeans
         */
        void getmyReportResult(List<ReportBean> reportBeans);

        /**
         * 二级科室
         *
         * @param data
         */
        void getDetListSucess(List<DepartmentListBean> data);

        void getInquireResult(List<CommitBean> commitBeans);

    }

    public interface Presenter extends BasePresenter<MyReportContract.View> {
        void sendyReportRequest(String loginDoctorPosition, String operDoctorCode,
                                String operDoctorName);

        void getDetList(String params);

        void getInquireRequest(String loginDoctorPosition, String operDoctorCode,
                               String operDoctorName, String userGradeCode,
                               String reportPeriod, String totalType,
                               String diseaseTypeCode, String searchType,String departmentCode,
                               String userName);


    }
}
