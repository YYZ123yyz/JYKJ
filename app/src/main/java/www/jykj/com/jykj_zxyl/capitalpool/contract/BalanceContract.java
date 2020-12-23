package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:余额契约类
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 17:30
 */
public class BalanceContract {


    public interface View extends BaseView {

        /**
         * 获取余额信息
         * @param accountBalanceBean 余额信息
         */
        void getSearchAccountBalanceResult(AccountBalanceBean accountBalanceBean);

        /**
         * 获取搜索账户余额信息返回错误
         */
        void getSearchAccountBalanceError();
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 医生个人账户余额详情表
         * @param activity Activity
         */
        void sendSearchAccountDoctorAssetsInfoRequest(Activity activity);

    }
}
