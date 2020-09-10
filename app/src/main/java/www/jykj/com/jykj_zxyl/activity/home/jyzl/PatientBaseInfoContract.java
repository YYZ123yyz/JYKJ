package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:患者基本信息契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 14:23
 */
public class PatientBaseInfoContract  {

    public interface View extends BaseView {


    }

    public interface Presenter extends BasePresenter<View> {

        void sendPatientBaseInfoRequest(String searchPatientCode, Activity activity);

    }
}
