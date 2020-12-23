package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class ModifyIinforAgainContract {

    public interface View extends BaseView {

        void setPasswordSucess(String msg);

        void showMsg(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void setPassword(String params);
    }
}
