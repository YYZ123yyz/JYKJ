package www.jykj.com.jykj_zxyl.appointment;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorScheduTimesBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OperDoctorScheduResultBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:我的在线排班契约类
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:58
 */
public class OnlineScheduContract {

    public interface View extends BaseView {

        /**
         * 获取日历数据列表
         * @param calendarItemBeans 日历列表
         */
        void getSearchSchedulingMsgCalandarResult(List<CalendarItemBean> calendarItemBeans);

        /**
         * 获取号源类型返回结果
         * @param baseReasonBeans 号源类型返回结果
         */
        void getSignalSourceTypeResult(List<BaseReasonBean> baseReasonBeans);

        /**
         * 获取操作医生排班返回结果
         * @param operDoctorScheduResult 返回结果
         */
        void getOperDoctorScheduResult(OperDoctorScheduResultBean operDoctorScheduResult);


        /**
         * 校验确认弹框
         * @param msg 消息
         */
        void getOperDoctorScheduCheckStepConfirm(String msg);

        /**
         * 操作失败
         * @param msg 信息
         */
        void getOperDoctorScheduError(String msg);

        /**
         * 获取排班明细返回结果
         * @param doctorScheduTimesBeans 排班明细列表
         */
        void getSearchReserveDoctorDateRosterInfoResult(List<DoctorScheduTimesBean> doctorScheduTimesBeans);

        void getOperDelDoctorDateRosterInfoResult(boolean isSucess,String msg);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送获取排班日历信息请求
         * @param mainDoctorCode 医生code
         */
        void sendSearchSchedulingMsgCalandarRequest(String mainDoctorCode, Activity activity);

        /**
         * 获取当前排班明细
         * @param mainDoctorCode 医生code
         * @param times 时间
         * @param activity activity
         */
        void searchReserveDoctorDateRosterInfoRequest(String mainDoctorCode,String times,Activity activity);

        /**
         * 获取号源类型
         * @param baseCode 编码
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
         * 删除医生线上排班
         * @param reserveDateRosterCode 线上排班明细编号
         */
        void sendOperDelDoctorDateRosterInfoRequest(String reserveDateRosterCode,Activity activity);
    }
}
