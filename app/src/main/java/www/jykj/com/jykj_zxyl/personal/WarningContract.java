package www.jykj.com.jykj_zxyl.personal;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.ReservePatientDoctorInfo;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.personal.bean.AllNumBean;

public class WarningContract {

    public interface View extends BaseView {

        void getNumSucess(AllNumBean data);

        void getNumFail();
    }

    public interface Presenter extends BasePresenter<View> {

        void getStateNum(String params);
    }
}
