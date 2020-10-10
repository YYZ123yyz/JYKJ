package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReceiveTreatmentResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TimelyTreatmentBean;
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
         * @param receiveTreatmentResultBean 接诊返回结果
         */
        void getOperConfirmReservePatientDoctorInfoResult(ReceiveTreatmentResultBean receiveTreatmentResultBean);

        /**
         * 获取接诊请求返回错误
         * @param msg 错误信息
         */
        void getOperConfirmReservePatientDoctorInfoError(String msg);

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

        /**
         * 获取号源类型
         * @param baseReasonBeans 号源类型返回类别
         */
        void getSignalSourceTypeResult(List<BaseReasonBean> baseReasonBeans);

        /**
         * 获取及时接诊返回列表结果
         * @param timelyTreatmentBeans 及时接诊返回列表
         */
        void getTimelyTreatmentListResult(List<TimelyTreatmentBean> timelyTreatmentBeans);

        /**
         * 获取操作医生排班返回结果
         * @param operDoctorScheduResult 返回结果
         */
        void getOperDoctorScheduResult(OperDoctorScheduResultBean operDoctorScheduResult);

        /**
         * 获取校验返回结果
         * @param msg 信息
         */
        void getOperDoctorCheckStepConfirm(String msg);

        /**
         * 操作失败
         * @param msg 信息
         */
        void getOperDoctorScheduError(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void sendSearchReservePatientDoctorInfoRequest(
                String mainDoctorCode, String treatmentType
                , String rowNum, String pageNum, String mainPatientName
                , String ageMax, String ageMin,
                String reserveStartDate,
                String reserveEndDate, String priceRegion,
                String reserveStatus,String dateSort,String priceSort
                ,String diseaseTypeName,String patientSpeak, Activity activity);

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

        /**
         * 发送及时接诊信息
         * @param mainDoctorCode 医生code
         */
        void sendSearchReserveDoctorDateRosterInfoImmediateRequest(String mainDoctorCode,Activity activity);

        /**
         * 获取号源类型
         * @param baseCode code
         */
        void sendGetSignalSourceTypeRequest(String baseCode);

        /**
         * 发送排班设置请求
         * @param mainDoctorCode
         * @param mainDoctorName
         * @param mainDoctorAlias
         * @param week
         * @param reserveType
         * @param reserveTypeName
         * @param times
         * @param startTimes
         * @param endTimes
         * @param reserveCount
         * @param checkStep
         * @param reserveDateRosterCode
         * @param activity
         */
        void sendOperUpdDoctorDateRosterInfoRequest(String mainDoctorCode,
                                                    String mainDoctorName,String mainDoctorAlias
                ,String week,String reserveType,String reserveTypeName
                ,String times,String startTimes,String endTimes, String reserveCount,String checkStep
                ,String reserveDateRosterCode,Activity activity);

        /**
         * 发送获取用户信息请求
         * @param userCodeList 用户Id列表
         */
        void sendGetUserInfoRequest(String userCodeList);
    }
}
