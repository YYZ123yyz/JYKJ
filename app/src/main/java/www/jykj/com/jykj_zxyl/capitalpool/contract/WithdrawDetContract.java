package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.HomeHealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawDetBean;

public class WithdrawDetContract {

    public interface View extends BaseView {
        /**
         * 医生提现明细列表
         * @param withdrawDetBeans
         */
        void getWithdrawDetResult(List<WithdrawDetBean> withdrawDetBeans);

        void getDetSize();
    }

    public interface Presenter extends BasePresenter<View> {

        void getWithdrawDet(String params);

    }
}
