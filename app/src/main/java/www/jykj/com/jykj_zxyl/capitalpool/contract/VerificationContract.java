package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class VerificationContract {

    public interface View extends BaseView {

        void showMsg(String msg);

        void checkSucess();
    }

    public interface Presenter extends BasePresenter<View> {

        void sendMs(String params);

        void checkAccount (String params);
    }
}
