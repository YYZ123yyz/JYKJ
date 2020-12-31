package www.jykj.com.jykj_zxyl.mypatient.contract;

import java.util.List;

import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.mypatient.bean.WarningListBean;

public class RedRiskContract {

    public interface View extends BaseView {
        void showMsg(String msg);

        void getListSucess(List<WarningListBean> data);
    }

    public interface Presenter extends BasePresenter<View> {
        void getWarningList(String params);

        void setPatientWarning(String params);
    }
}
