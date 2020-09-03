package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
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

    }
}
