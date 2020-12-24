package www.jykj.com.jykj_zxyl.capitalpool.contract;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;

public class UserAccountContract {

    public interface View extends BaseView {
        void getNodata();

        void getDataSucess(List<WithdrawTypelListBean> data);

        void getWeiChat( WithdrawTypelListBean weichatBean);

        void getAli(WithdrawTypelListBean  aliBean);

        void getEmpeyData(int type);
    }

    public interface Presenter extends BasePresenter<View> {

        void getDoctorInfo(String params);
    }
}
