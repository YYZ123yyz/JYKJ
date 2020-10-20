package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class HistoryContract {
    public interface View extends BaseView {
        /**
         * 既往病史
         */
        void getSearchHistoryResult(List<DoctorRecordBean> DoctorRecordBean);
        /**
         * 失败
         */
        void getSearchHistoryResultError(String msg);
        /**
         * 医生填写
         */

    }
    public interface Presenter extends BasePresenter<HistoryContract.View> {
        /**
         * 获取既往病史
         */
        void sendSearchHistoryRequest(String rowNum,String pageNum,String loginDoctorPosition, String requestClientType, String operDoctorCode,String operDoctorName,String searchPatientCode,String dataSourceType);
    }
}
