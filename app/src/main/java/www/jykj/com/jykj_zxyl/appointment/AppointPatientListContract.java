package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReceiveTreatmentResultBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:预约患者列表契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-02 17:26
 */
public class AppointPatientListContract {

    public interface View extends BaseView {

        /**
         * 查询我的患者列表返回结果
         * @param patientInfoBeans 我的患者列表
         */
        void getSearchReservePaitentDoctorInfoByStatusResult(
                List<PatientInfoBean> patientInfoBeans);

        /**
         * 获取取消预约请求返回结果
         * @param baseReasonBeans 预约请求返回列表
         */
        void getCancelAppointReasonResult(List<BaseReasonBean> baseReasonBeans);

        /**
         * 获取接诊请求返回结果
         * @param receiveTreatmentResultBean 接诊返回结果
         */
        void getOperConfirmReservePatientDoctorInfoResult(ReceiveTreatmentResultBean receiveTreatmentResultBean);

        /**
         * 获取接诊请求返回错误
         * @param msg 错误信息
         */
        void getOperConfirmReservePatientDoctorInfoError(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送获取我的患者列表请求
         * @param mainDoctorCode
         * @param reserveDate
         * @param reserveStatus
         * @param rowNum
         * @param pageNum
         * @param activity
         */
        void sendSearchReservePatientDoctorInfoByStatusRequest(
                String mainDoctorCode,
                String reserveDate,
                String reserveStatus,
                String rowNum,
                String pageNum, Activity activity);

        /**
         * 发送获取用户信息请求
         * @param userCodeList 用户Id列表
         */
        void sendGetUserInfoRequest(String userCodeList);

        /**
         * 发送取消预约原因
         * @param baseCode code
         */
        void sendCancelAppointReasonRequest(String baseCode);

        /**
         * 发送接诊请求
         * @param reserveCode
         * @param reserveRosterDateCode
         * @param mainDoctorCode
         * @param mainDoctorName
         * @param mainPatientCode
         * @param mainPatientName
         * @param version
         * @param activity
         */
        void sendOperConfirmReservePatientDoctorInfoRequest(String reserveCode,
                                                            String reserveRosterDateCode
                ,String mainDoctorCode,String mainDoctorName, String mainPatientCode
                ,String mainPatientName,String version,Activity activity);


    }
}
