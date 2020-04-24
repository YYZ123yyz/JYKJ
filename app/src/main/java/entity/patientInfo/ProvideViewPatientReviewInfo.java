package entity.patientInfo;

public class ProvideViewPatientReviewInfo {
    private String loginDoctorPosition;//医生所处的位置
    private String searchDoctorCode;//医生编码
    private String patientUserName;
    private String patientGender;
    private String patientLinkPhone;
    private String patientIdNumber;
    private String patientLabelId;
    private String operDoctorCode;
    private String operDoctorName;
    private String dpApplyId;
    private int flagApplyState;
    private String refuseReason;
    private String dpApplyIdStr;

    public String getDpApplyIdStr() {
        return dpApplyIdStr;
    }

    public void setDpApplyIdStr(String dpApplyIdStr) {
        this.dpApplyIdStr = dpApplyIdStr;
    }


    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getSearchDoctorCode() {
        return searchDoctorCode;
    }

    public void setSearchDoctorCode(String searchDoctorCode) {
        this.searchDoctorCode = searchDoctorCode;
    }

    public String getPatientUserName() {
        return patientUserName;
    }

    public void setPatientUserName(String patientUserName) {
        this.patientUserName = patientUserName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientLinkPhone() {
        return patientLinkPhone;
    }

    public void setPatientLinkPhone(String patientLinkPhone) {
        this.patientLinkPhone = patientLinkPhone;
    }

    public String getPatientIdNumber() {
        return patientIdNumber;
    }

    public void setPatientIdNumber(String patientIdNumber) {
        this.patientIdNumber = patientIdNumber;
    }

    public String getPatientLabelId() {
        return patientLabelId;
    }

    public void setPatientLabelId(String patientLabelId) {
        this.patientLabelId = patientLabelId;
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

    public String getDpApplyId() {
        return dpApplyId;
    }

    public void setDpApplyId(String dpApplyId) {
        this.dpApplyId = dpApplyId;
    }

    public int getFlagApplyState() {
        return flagApplyState;
    }

    public void setFlagApplyState(int flagApplyState) {
        this.flagApplyState = flagApplyState;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }
}
