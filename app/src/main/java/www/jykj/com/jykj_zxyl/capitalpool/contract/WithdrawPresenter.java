package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class WithdrawPresenter extends BasePresenterImpl<WithdrawContract.View>
        implements WithdrawContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void go2Withdraw(String params) {

    }
}
