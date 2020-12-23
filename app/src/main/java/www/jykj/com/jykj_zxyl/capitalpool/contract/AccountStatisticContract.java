package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:账户统计
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 20:44
 */
public class AccountStatisticContract {

    public interface View extends BaseView {

        /**
         * 获取账户统计信息
         * @param accountStisticInfoBean 账户统计
         */
        void getSearchAccountDoctorIncomOutInfoResult(AccountStisticInfoBean accountStisticInfoBean);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送收支明细弹框
         * @param countPeriod 时间
         * @param changeType 收支类型(收入:1; 支出：2)
         * @param activity Activity
         */
        void sendSearchAccountDoctorIncomeOutInfoRequest(String countPeriod, String changeType,
                                                         Activity activity);
    }
}
