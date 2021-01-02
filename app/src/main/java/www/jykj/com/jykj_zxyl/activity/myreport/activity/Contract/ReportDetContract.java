package www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract;

import java.util.List;


import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartDetBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class ReportDetContract {


    public interface View extends BaseView {
        void getmyReportResult(List<ReportBean> reportBeans);

        void getDetListSucess(List<DepartmentListBean> data);

        void getDetSucess(DepartDetBean bean);
    }

    public interface Presenter extends BasePresenter<View> {
        void sendyReportRequest(String params);

        void getDetList(String params);
        
        void getDet(String params);
    }
}
