package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceListInfoBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 18:27
 */
public class AccountBalanceListContract {

    public interface View extends BaseView {

        /**
         * 获取医生账户明细列表返回结果
         * @param accountBalanceListInfoBean 医生账户信息列表
         */
        void getSearchAccountDoctorBalanceInfoResult( AccountBalanceListInfoBean
                                                              accountBalanceListInfoBean );
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 获取医生账户流水(余额)明细表
         * @param countPeriod 时间
         * @param rowNum 开始位置
         * @param pageNum 每页大小
         * @param activity Activity
         */
        void sendSearchAccountDoctorBalanceInfoListRequest(String countPeriod
                ,int rowNum, int pageNum,Activity activity );

    }
}
