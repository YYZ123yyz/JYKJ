package www.jykj.com.jykj_zxyl.medicalrecord;
import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionTypeBean;
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

        /**
         * 获取处方类型返回结果
         * @param prescriptionTypeBeans 处方类型列表
         */
        void getPrescriptionTypeResult(List<PrescriptionTypeBean> prescriptionTypeBeans);

        /**
         * 获取保存合作更新处方返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getSaveAndUpdatePrescriptionResult(boolean isSucess,String msg);

        /**
         * 发送删除处方返回结果
         * @param isSucess ture or false
         * @param pos 当前位置
         * @param msg 返回信息
         */
        void getDeletePrescriptionResult(boolean isSucess,int pos,String msg);


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

        /**
         * 保存更新处方请求
         * @param list 保存更新处方请求
         * @param activity 当前activity
         */
        void sendSaveAndUpdatePrescriptionRequest(List<PrescriptionItemUploadBean> list, Activity activity);


        /**
         * 发送删除处方请求
         * @param drugOrderCode 处方编码
         * @param orderCode 订单code
         * @param pos 位置
         * @param activity 当前activity
         */
        void sendDeletePrescriptionRequest(String drugOrderCode,String orderCode,int pos,Activity activity);
    }
}
