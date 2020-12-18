package www.jykj.com.jykj_zxyl.personal;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class WarningPresenter extends BasePresenterImpl<WarningContract.View>
        implements WarningContract.Presenter{

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
