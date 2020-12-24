package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.personal.StateDetContract;

public class RechargeContract {

    public interface View extends BaseView {

        void getPayInfoSucess(ChapterPayBean bean);

        void getAliPayInfoSucess(String bean);
        void getDataFail(String msg);

        /**
         * 充值返回错误
         * @param msg 返回错误
         */
        void getAccountDoctorBalanceInfoPayError(String msg);


        /**
         * 校验密码是否成功
         * @param isSucess true or false
         * @param msg 状态信息
         */
        void checkPasswordResult(boolean isSucess,String msg);

        /**
         * 获取订单状态返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getOrderStatusResult(boolean isSucess,String msg);
    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送充值接口请求
         * @param payType 充值方式(1微信、2支付宝)
         * @param rechargeMoney 充值金额
         * @param activity Activity
         */
        void sendAccountDoctorBalanceInfoPayRequest(String payType,
                                                    String rechargeMoney, Activity activity);

        /**
         * 密码校验
         * @param params 校验参数
         */
        void checkPassword(String params);

        /**
         * 查询订单状态
         * @param payType 支付方式1微信2支付宝
         * @param activity Activity
         */
        void sendGetOrderStatusRequest(String payType,Activity activity);
    }
}
