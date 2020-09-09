package com.hyphenate.easeui.jykj;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.CancelAppointOrderDetialBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:解约详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-09 14:07
 */
public class CancelAppointDetialContract {


    public interface View extends BaseView {
        /**
         * 获取取消预约返回订单结果
         * @param cancelAppointOrderDetialBean 订单详情
         */
        void getSearchReservePatientDoctorInfoxxResult(
                CancelAppointOrderDetialBean cancelAppointOrderDetialBean);

    }


    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送取消预约订单信息请求
         *
         * @param reserveCode 订单Code
         * @param activity    activity
         */
        void sendSearchReservePatientDoctorInfoXxRequest(String reserveCode, Activity activity);

    }
}
