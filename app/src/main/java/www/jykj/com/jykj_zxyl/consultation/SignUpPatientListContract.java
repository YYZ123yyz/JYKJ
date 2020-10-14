package www.jykj.com.jykj_zxyl.consultation;

import java.util.List;

import entity.DoctorInfo.InteractPatient;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:签约患者契约类
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 16:48
 */
public class SignUpPatientListContract {

    public interface View extends BaseView {
        /**
         * 获取所有换泽数据
         * @param interactPatients 换泽列表
         */
        void getSignUpPatientResult(List<InteractPatient> interactPatients);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 获取所有患者请求
         * @param doctorId 医生id
         * @param doctorCode 医生code
         */
        void sendGetSignUpPatientRequest(String doctorId, String doctorCode);

    }
}
