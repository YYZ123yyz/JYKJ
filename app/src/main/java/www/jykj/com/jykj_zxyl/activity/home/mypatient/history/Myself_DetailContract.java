package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.Myself_DetaiBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class Myself_DetailContract {

    public interface View extends BaseView {
        /**
         * 患者填写详情
         *  Myself_DetaiBean
         */
        void getSearchMyself_DetailResult(Myself_DetaiBean myself_detaiBean);

        /**
         *   获取失败
         */
        void getSearchMyself_DetailResultError(String msg);
    }


    public interface Presenter extends BasePresenter<Myself_DetailContract.View> {
        /**
         * 获取本人填写详情
         */
        void sendSearchMyself_DetaiRequest(String loginPatientPosition, String requestClientType, String operPatientCode,String operPatientName,String recordId);
    }
}
