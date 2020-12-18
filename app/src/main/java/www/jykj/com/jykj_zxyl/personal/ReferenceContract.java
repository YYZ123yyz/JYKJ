package www.jykj.com.jykj_zxyl.personal;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class ReferenceContract {

    public interface View extends BaseView {


    }

    public interface Presenter extends BasePresenter<View> {

        void getReferenceData(String params);

        void updataReference(String params);
    }
}
