package www.jykj.com.jykj_zxyl.patient;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:签约患者契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-30 10:25
 */
public class MySignUpPatientContract {

    public interface View extends BaseView {


    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送签约患者请求
         * @param rowNum 分页查询属性.每页行数
         * @param pageNum 分页查询属性.当前页码
         * @param searchDoctorCode 查询操作的医生编码
         * @param searchStateType 状态类型.0:全部;1:正常;2:提醒;3:预警;
         * @param activity activity
         */
        void sendSearchDoctorManagePatientDataByParamRequest(
                int rowNum,int pageNum,
                String searchDoctorCode
                , String searchStateType, Activity activity);
    }
}
