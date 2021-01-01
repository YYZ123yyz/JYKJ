package www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract;

import java.util.List;


import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class ReportDetContract {


    public interface View extends BaseView {
        void getmyReportResult(List<ReportBean> reportBeans);
    }

    public interface Presenter extends BasePresenter<View> {
        void sendyReportRequest(String params);
    }
}
