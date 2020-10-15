package www.jykj.com.jykj_zxyl.personal;


import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.DiagnosisReplayBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:诊后留言契约类
 *
 * @author: qiuxinhai
 * @date: 2020-10-15 16:24
 */
public class DiagnosisReplayContract {

    public interface View extends BaseView {


        /**
         * 发送诊后留言操作返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getOperUpdMyClinicDetailByOrderPatientMessageResult(
                boolean isSucess, String msg);

        /**
         * 获取诊后留言信息
         * @param diagnosisReplayBean 诊后留言返回结果
         */
        void getSearchMyClinicDetailResPatientMessageContentResult(
                DiagnosisReplayBean diagnosisReplayBean);


    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送诊后留言请求
         * @param messageId 留言信息编号.
         * @param orderCode 订单code
         * @param treatmentType 就诊类型
         * @param replyContent 回复内容
         * @param replyType 回复类型.1:正常;2:一般;3:紧急;4:重大紧急;
         * @param patientCode 问诊人)患者编码
         * @param patientName (问诊人)患者姓名
         * @param patientPhone (问诊人)患者电话
         * @param activity Activity
         */
        void sendOperUpdMyClinicDetailByOrderPatientMessageRequest(
                String messageId,
                String orderCode,
                String treatmentType,
                String replyContent,
                String replyType,
                String patientCode,
                String patientName,
                String patientPhone,
                Activity activity);

        /**
         * 诊后留言详情(包含：图片内容)
         * @param orderCode 订单id
         * @param activity Activity
         */
        void sendSearchMyClinicDetailResPatientMessageContent_20201012Request(String orderCode
                ,Activity activity);

    }
}
