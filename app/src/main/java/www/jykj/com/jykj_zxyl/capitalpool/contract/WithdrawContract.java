package www.jykj.com.jykj_zxyl.capitalpool.contract;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class WithdrawContract {

    public interface View extends BaseView {


        void getTypeSucess(List<WithdrawTypelListBean> data);

        void getWithdrawCost(WithdrawCostBean bean);


        void checkSucess();

        void withDrawSucess();
    }

    public interface Presenter extends BasePresenter<View> {

        void getWithdrawType(String params);

        void go2Withdraw(String params);

        void getWithdrawCost(String params);

        void checkPassword(String params);
    }
}
