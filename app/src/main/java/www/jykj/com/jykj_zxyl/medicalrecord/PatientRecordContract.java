package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalTypeBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Created by G on 2020/9/19 9:54
 */
public class PatientRecordContract {

    public interface View extends BaseView {
//        void getPatientRecordDet(  det);
    }


    public interface Presenter extends BasePresenter<View> {
        void getPatientRecord(String param);

    }
}
