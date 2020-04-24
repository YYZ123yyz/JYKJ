package entity.home.newsMessage;

/**
 * 操作消息参数
 */
public class OperMessageParment {
    private             String              loginDoctorPosition;
    private             String              operDoctorCode;
    private             String              operDoctorName;

    private             String              reminderId;
    private             String              operCode;
    private             String              msgOper;
    private             String              flagApplyState;
    private             String              refuseReason;

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getOperDoctorCode() {
        return operDoctorCode;
    }

    public void setOperDoctorCode(String operDoctorCode) {
        this.operDoctorCode = operDoctorCode;
    }

    public String getOperDoctorName() {
        return operDoctorName;
    }

    public void setOperDoctorName(String operDoctorName) {
        this.operDoctorName = operDoctorName;
    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getMsgOper() {
        return msgOper;
    }

    public void setMsgOper(String msgOper) {
        this.msgOper = msgOper;
    }

    public String getFlagApplyState() {
        return flagApplyState;
    }

    public void setFlagApplyState(String flagApplyState) {
        this.flagApplyState = flagApplyState;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }
}
