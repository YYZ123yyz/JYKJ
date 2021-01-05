package www.jykj.com.jykj_zxyl.personal;

import java.util.ArrayList;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.custom.RefrecenmapBean;

public class ReferenceContract {

    public interface View extends BaseView {

        void getManDataSucess(ArrayList<RefrecenmapBean> manBeans);


        void getWomenDataSucess(ArrayList<RefrecenmapBean> manBeans);

        void showMsg(String msg);

        void updataSucess();
    }

    public interface Presenter extends BasePresenter<View> {

        void getReferenceData(String docCode,String params);

        void updataReference(String params);
    }
}
