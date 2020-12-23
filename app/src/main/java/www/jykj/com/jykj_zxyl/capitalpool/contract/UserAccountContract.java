package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class UserAccountContract {

    public interface View extends BaseView {
        void getNodata();

    }

    public interface Presenter extends BasePresenter<View> {

        void getDoctorInfo(String params);
    }
}
