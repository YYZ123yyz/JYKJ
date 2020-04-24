package entity.mySelf.doctorScheduling;

public class CommitDoctorPBParment {
    private         String      loginDoctorPosition;
    private         String      operDoctorCode;
    private         String      operDoctorName;
    private         String      schedulingJsonObject;

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

    public String getSchedulingJsonObject() {
        return schedulingJsonObject;
    }

    public void setSchedulingJsonObject(String schedulingJsonObject) {
        this.schedulingJsonObject = schedulingJsonObject;
    }
}
