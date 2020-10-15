package www.jykj.com.jykj_zxyl.mypatient.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoContract;
import yyz_exploit.bean.Status;

public class SignedPatientContract {
    public interface View extends BaseView {
        /**
         * 我的患者数量统计
         */
        void getOperNumberResult(Status status);
    }
    public interface Presenter extends BasePresenter<SignedPatientContract.View> {
        /**
         *  患者数量统计
         * @param rowNum
         * @param pageNum
         * @param loginDoctorPosition
         * @param searchDoctorCode
         * @param searchFlagSigning
         */
        void sendOperNumberRequest(String rowNum, String pageNum, String loginDoctorPosition
                , String searchDoctorCode,String searchFlagSigning);

    }
}
