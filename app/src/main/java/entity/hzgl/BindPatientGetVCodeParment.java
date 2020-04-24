package entity.hzgl;

public class BindPatientGetVCodeParment {
    private             String              loginDoctorPosition;
    private             String              operDoctorCode;
    private             String              operDoctorName;
    private             String              sendUserLinkPhone;

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

    public String getSendUserLinkPhone() {
        return sendUserLinkPhone;
    }

    public void setSendUserLinkPhone(String sendUserLinkPhone) {
        this.sendUserLinkPhone = sendUserLinkPhone;
    }
}
