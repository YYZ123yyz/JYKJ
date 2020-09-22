package www.jykj.com.jykj_zxyl.medicalrecord;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:处方药品契约类
 *
 * @author: qiuxinhai
 * @date: 2020-09-17 14:24
 */
public class PrescriptionMedicinalContract {



    public interface View extends BaseView {

        /**
         * 获取服药评论返回结果
         * @param takeMedicinalRateBeans 服药频率列表
         */
        void getTakeMedicinalRateResult(List<TakeMedicinalRateBean> takeMedicinalRateBeans);


    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送服药频率请求
         * @param baseCode code
         */
        void sendTakeMedicinalRateRequest(String baseCode);

        /**
         * 发送处方类型请求
         * @param baseCode
         */
        void sendPrescriptionTypeRequest(String baseCode);

    }
}
