package www.jykj.com.jykj_zxyl.capitalpool.contract;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

public class ModifyIinforContract {

    public interface View extends BaseView {

        /**
         * 校验密码是否成功
         * @param isSucess true or false
         * @param msg 状态信息
         */
        void checkPasswordResult(boolean isSucess,String msg);

    }

    public interface Presenter extends BasePresenter<View> {

        void checkPassword(String params);

    }
}
