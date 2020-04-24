package entity.patientInfo;

public class PatientMsgInfo {
    private String loginDoctorPosition;
    private String operDoctorCode;
    private String operDoctorName;
    private String msgRemindContent;
    private String receivePatientCode;
    private String receivePatientName;
    private String searchPatientCode;
    private String searchPatientName;

    public String getSearchPatientCode() {
        return searchPatientCode;
    }

    public void setSearchPatientCode(String searchPatientCode) {
        this.searchPatientCode = searchPatientCode;
    }

    public String getSearchPatientName() {
        return searchPatientName;
    }

    public void setSearchPatientName(String searchPatientName) {
        this.searchPatientName = searchPatientName;
    }

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

    public String getMsgRemindContent() {
        return msgRemindContent;
    }

    public void setMsgRemindContent(String msgRemindContent) {
        this.msgRemindContent = msgRemindContent;
    }

    public String getReceivePatientCode() {
        return receivePatientCode;
    }

    public void setReceivePatientCode(String receivePatientCode) {
        this.receivePatientCode = receivePatientCode;
    }

    public String getReceivePatientName() {
        return receivePatientName;
    }

    public void setReceivePatientName(String receivePatientName) {
        this.receivePatientName = receivePatientName;
    }
}
