package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.InteractPatientInterrogation;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:问诊详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-25 10:26
 */
public class InterrogationDetailsContract {

    public interface View extends BaseView {
        /**
         * 获取问诊资料返回结果
         * @param interactPatientInterrogation 问诊资料返回结果
         */
        void getInterrogationDetailResult(InteractPatientInterrogation
                                                  interactPatientInterrogation);
    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送获取问诊详情请求
         * @param orderCode 订单code
         * @param activity activity
         */
        void sendInterrogationDetailRequest(String orderCode, Activity activity);

    }
}
