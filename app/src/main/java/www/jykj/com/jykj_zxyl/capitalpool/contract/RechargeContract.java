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
    }

    public interface Presenter extends BasePresenter<View> {

        void getDocdorAsset(String params);

        void getCardList(String params);

        /**
         * 发送充值接口请求
         * @param payType 充值方式(1微信、2支付宝)
         * @param rechargeMoney 充值金额
         * @param activity Activity
         */
        void sendAccountDoctorBalanceInfoPayRequest(String payType,
                                                    String rechargeMoney, Activity activity);

    }
}
