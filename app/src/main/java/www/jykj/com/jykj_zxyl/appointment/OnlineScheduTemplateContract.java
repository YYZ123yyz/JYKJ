package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DcotorScheduTimesWeekBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorScheduTimesBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:线上排班模版契约类
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 10:54
 */
public class OnlineScheduTemplateContract {

    public interface View extends BaseView {
        /**
         * 获取周请求头返回信息
          * @param itemBeans 周列表
         */
      void getSearchReserveDoctorRosterInfoHeaderResult(List<CalendarItemBean> itemBeans);

        /**
         * 获取搜索医生周排班列表请求返回结果
         * @param doctorScheduTimesBeans 排班列表
         */
      void getSearchReserveDoctorRosterInfoResult(List<DcotorScheduTimesWeekBean> doctorScheduTimesBeans);

        /**
         * 获取医生排班明细返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
      void getOperDelReserveDoctorRosterInfoResult(boolean isSucess,String msg);

        /**
         * 获取医生设置排班信息(周)
         * @param isSucess ture or false
         * @param msg 返回信息
         */
      void getOperUpdReserveDoctorRosterInfoRequest(boolean isSucess,String msg);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 获取周请求头信息
         * @param mainDoctorCode 医生code
         * @param activity activity
         */
        void sendSearchReserveDoctorRosterInfoHeaderRequest(String mainDoctorCode, Activity activity);

        /**
         * 发送获取医生周排班信息
         * @param mainDoctorCode 医生code
         * @param week 周
         */
        void sendSearchReserveDoctorRosterInfoRequest(String mainDoctorCode,String week,Activity activity);

        /**
         * 发送医生排班明细删除请求
         * @param reserveDoctorRosterCode 医生排班明细code
         * @param activity activity
         */
        void sendOperDelReserveDoctorRosterInfoRequest(String reserveDoctorRosterCode,Activity activity);



        void sendOperUpdReserveDoctorRosterInfoRequest(String mainDoctorCode
                ,String mainDoctorName,String mainDoctorAlias
                ,String week,String weekView,String startTimes,String endTimes,String reserveCount,String checkStep
                ,String reserveDoctorRosterCode,Activity activity);
    }
}
