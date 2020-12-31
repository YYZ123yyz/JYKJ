package www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter;


import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.ReportDetContract;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class ReportDetPresenter extends BasePresenterImpl<ReportDetContract.View> implements ReportDetContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
