package www.jykj.com.jykj_zxyl.activity.home.mypatient.symptom;

import www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation.BasicInformationContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.SymptormBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class SymptormContract {
    public interface View extends BaseView {
        /**
         * 症状信息
         */
        void getSearchSymptormResult(SymptormBean symptormBean);
        /**
         * 获取症状信息错误
         */
         void getSearchSymptormResultError(String msg);
    }
    public interface Presenter extends BasePresenter<SymptormContract.View> {
        /**
         *  查询症状信息
         */
        void sendSearchSymptormRequest(String loginDoctorPosition, String requestClientType, String operDoctorCode,String operDoctorName,String searchPatientCode);
    }
}
