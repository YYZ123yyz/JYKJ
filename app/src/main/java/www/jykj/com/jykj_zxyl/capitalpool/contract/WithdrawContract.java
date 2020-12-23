package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class WithdrawContract {

    public interface View extends BaseView {




    }

    public interface Presenter extends BasePresenter<View> {

        void go2Withdraw(String params);
    }
}
