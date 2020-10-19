package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-15 18:03
 */
public class DiagnosisReplayBean {


    /**
     * interactPatientMessageActiveList : [{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"}]
     * messageId : 0
     * messageImgArray :
     * orderCode : 0101202010141554073068640192
     */

    private int messageId;
    private String messageImgArray;
    private String orderCode;
    private String patientCode;//(问诊人)患者关联编码.外键:sys_user_patient_info
    private String patientName;//(问诊人)[冗余]患者姓名
    private String patientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)
    private String messageContent;//患者留言内容
    private String messageDate;
    private String treatmentType;

    private List<InteractPatientMessageActiveListBean> interactPatientMessageActiveList;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageImgArray() {
        return messageImgArray;
    }

    public void setMessageImgArray(String messageImgArray) {
        this.messageImgArray = messageImgArray;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLinkPhone() {
        return patientLinkPhone;
    }

    public void setPatientLinkPhone(String patientLinkPhone) {
        this.patientLinkPhone = patientLinkPhone;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public List<InteractPatientMessageActiveListBean> getInteractPatientMessageActiveList() {
        return interactPatientMessageActiveList;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public void setInteractPatientMessageActiveList(List<InteractPatientMessageActiveListBean> interactPatientMessageActiveList) {
        this.interactPatientMessageActiveList = interactPatientMessageActiveList;
    }

    public static class InteractPatientMessageActiveListBean {
        /**
         * doctorName : 邱新海测试医生
         * doctorReplyContent : 非你急急急
         * doctorReplyDate : 1602691200000
         * flagDoctorReplyType : 3
         * flagDoctorReplyTypeName : 紧急
         * flagMessageType : 1
         * messageTypeName : 主动发起
         */

        private String doctorName;
        private String doctorReplyContent;
        private long doctorReplyDate;
        private int flagDoctorReplyType;
        private String flagDoctorReplyTypeName;
        private int flagMessageType;
        private String messageTypeName;

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getDoctorReplyContent() {
            return doctorReplyContent;
        }

        public void setDoctorReplyContent(String doctorReplyContent) {
            this.doctorReplyContent = doctorReplyContent;
        }

        public long getDoctorReplyDate() {
            return doctorReplyDate;
        }

        public void setDoctorReplyDate(long doctorReplyDate) {
            this.doctorReplyDate = doctorReplyDate;
        }

        public int getFlagDoctorReplyType() {
            return flagDoctorReplyType;
        }

        public void setFlagDoctorReplyType(int flagDoctorReplyType) {
            this.flagDoctorReplyType = flagDoctorReplyType;
        }

        public String getFlagDoctorReplyTypeName() {
            return flagDoctorReplyTypeName;
        }

        public void setFlagDoctorReplyTypeName(String flagDoctorReplyTypeName) {
            this.flagDoctorReplyTypeName = flagDoctorReplyTypeName;
        }

        public int getFlagMessageType() {
            return flagMessageType;
        }

        public void setFlagMessageType(int flagMessageType) {
            this.flagMessageType = flagMessageType;
        }

        public String getMessageTypeName() {
            return messageTypeName;
        }

        public void setMessageTypeName(String messageTypeName) {
            this.messageTypeName = messageTypeName;
        }
    }


}
