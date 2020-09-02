package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:我的诊所契约类
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 15:09
 */
public class MyClinicDetialContract {

    public interface View extends BaseView {
        /**
         * 获取搜索患者列表返回结果
         * @param patientInfoBeans 患者列表
         */
        void getSearchReservePatientDoctorInfoResult( List<PatientInfoBean> patientInfoBeans);

        /**
         * 获取接诊请求返回结果
         * @param isSucess ture or false
         * @param msg 信息
         */
        void getOperConfirmReservePatientDoctorInfoResult(boolean isSucess,String msg);

        /**
         * 发送取消预约请求返回结果
         * @param isSucess true or false
         * @param msg 信息
         */
        void getOperCancelReservePatientDoctorInfoResult(boolean isSucess,String msg);

        /**
         * 获取取消预约请求返回结果
         * @param baseReasonBeans 预约请求返回列表
         */
        void getCancelAppointReasonResult(List<BaseReasonBean> baseReasonBeans);

        /**
         * 获取价格区间返回结果
         * @param baseReasonBeans 价格区间列表
         */
        void getPriceRegionReasonResult(List<BaseReasonBean> baseReasonBeans);
    }

    public interface Presenter extends BasePresenter<View> {

        void sendSearchReservePatientDoctorInfoRequest(
                String mainDoctorCode, String treatmentType
                , String rowNum, String pageNum, String mainPatientName
                , String ageMax, String ageMin,
                String reserveStartDate,
                String reserveEndDate, String priceRegion,
                String reserveStatus,String dateSort,String priceSort, Activity activity);

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

        /**
         * 发送取消预约请求
         * @param reserveCode
         * @param reserveRosterDateCode
         * @param mainDoctorCode
         * @param mainDoctorName
         * @param mainPatientCode
         * @param mainPatientName
         * @param version
         * @param cancelReserveCode
         * @param cancelReserveName
         * @param cancelReserveRemark
         * @param activity
         */
        void sendOperCancelReservePatientDoctorInfoRequest(String reserveCode,
                                                           String reserveRosterDateCode
                ,String mainDoctorCode,String mainDoctorName, String mainPatientCode
                ,String mainPatientName, String version, String cancelReserveCode,
                                                           String cancelReserveName,
                                                           String cancelReserveRemark
                                                           ,Activity activity);

        /**
         * 发送取消预约原因
         * @param baseCode code
         */
        void sendCancelAppointReasonRequest(String baseCode);

        /**
         * 获取价格区间请求code
         * @param baseCode code
         */
        void sendPriceRegionReasonRequest(String baseCode);

    }
}
