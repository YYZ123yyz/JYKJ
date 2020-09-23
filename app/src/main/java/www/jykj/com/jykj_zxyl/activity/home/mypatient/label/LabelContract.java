package www.jykj.com.jykj_zxyl.activity.home.mypatient.label;

import java.util.List;

import www.jykj.com.jykj_zxyl.activity.home.mypatient.symptom.SymptormContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientLabelBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class LabelContract {

    public interface View extends BaseView {
        /**
         * 标签信息
         */
        void getSearchLabelResult(List<ProvidePatientLabelBean> providePatientLabelBean);
        /**
         * 获取标签信息失败
         */
        void getSearchLabelResultError(String  msg);
    }

    public interface Presenter extends BasePresenter<LabelContract.View> {
        /**
         * 获取标签信息
         */
        void sendSearchLabelRequest(String rowNum,String pageNum,String loginDoctorPosition, String requestClientType, String operDoctorCode,String operDoctorName,String searchPatientCode);
    }
}
