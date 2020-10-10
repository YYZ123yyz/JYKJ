package www.jykj.com.jykj_zxyl.activity.home.mypatient.fillindetails;

import java.util.List;

import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ViewInteractOrderMedicalRecordDetailBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class FillindetailsContract {
    public interface View extends BaseView {
        /**
         * 详情
         */
        void getSearchFillindetailsResult(List<ViewInteractOrderMedicalRecordDetailBean> viewInteractOrderMedicalRecordDetailBeans);
    }
    public interface Presenter extends BasePresenter<FillindetailsContract.View> {
        /**
         *  获取详情
         */
        void sendSearchFillindetailsRequest(String loginDoctorPosition, String requestClientType, String operDoctorCode,String operDoctorName,String orderCode);
    }
}
