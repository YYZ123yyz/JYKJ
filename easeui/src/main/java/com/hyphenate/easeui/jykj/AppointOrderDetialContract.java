package com.hyphenate.easeui.jykj;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.OrderInfoBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 10:49
 */
public class AppointOrderDetialContract {

    public interface View extends BaseView {

        /**
         * 获取搜索订单返回结果
         * @param orderInfoBeans 订单列表
         */
        void getSearchReserveInfoResult(OrderInfoBean orderInfoBeans);

        void getSearchReserveInfoError();
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送搜查询详情
         * @param orderCode 订单Id
         */
        void sendSearchReserveInfoRequest(String orderCode, Activity activity);

    }
}
