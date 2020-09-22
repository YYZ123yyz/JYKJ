package www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation;

import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class BasicInformationContract {
    public interface View extends BaseView {
        /**
         * 获取健康基本信息
         */
        void getSearchBasichealthinformationResult(ProvideViewPatientHealthyAndBasicsBean provideViewPatientHealthyAndBasicsBean);
        /**
         * 获取健康基本信息
         */
        void getSearchBasichealthinformationResultError(String msg);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         *  查询健康基本信息
         */
        void sendSearchBasichealthinformationRequest(String loginDoctorPosition, String requestClientType, String operDoctorCode,String operDoctorName,String searchPatientCode);
    }
}

