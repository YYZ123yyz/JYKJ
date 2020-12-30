package www.jykj.com.jykj_zxyl.personal;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.personal.bean.StateDetBean;

public class StateDetContract {


    public interface View extends BaseView {

        void getDetSucess(StateDetBean data);

        void showMsg(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void getStateDet(String params);
    }
}
