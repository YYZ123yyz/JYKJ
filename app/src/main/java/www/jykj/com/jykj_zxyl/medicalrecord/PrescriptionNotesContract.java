package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:处方笺信息
 *
 * @author: qiuxinhai
 * @date: 2020-09-19 16:20
 */
public class PrescriptionNotesContract {

    public interface View extends BaseView {

        /**
         * 获取处方笺返回结果
         * @param prescriptionNotesBeans 处方笺列表
         */
        void getPrescriptionNotesResult(List<PrescriptionNotesBean> prescriptionNotesBeans);

    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 获取处方笺列表请求
         * @param orderCode 订单code
         * @param activity activity
         */
        void sendPrescriptionNotesRequest(String orderCode, Activity activity);

    }
}
