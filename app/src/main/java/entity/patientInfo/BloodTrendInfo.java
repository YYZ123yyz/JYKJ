package entity.patientInfo;

public class BloodTrendInfo {
    private int rowNum;//行数
    private int pageNum;//页码
    private String loginDoctorPosition;//医生所处的位置
    private String operDoctorCode;
    private String operDoctorName;
    private String searchPatientCode;
    private String searchTimeType;
    private String searchDateType;
    private String searchDateStart;
    private String searchDateEnd;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
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

    public String getSearchPatientCode() {
        return searchPatientCode;
    }

    public void setSearchPatientCode(String searchPatientCode) {
        this.searchPatientCode = searchPatientCode;
    }

    public String getSearchTimeType() {
        return searchTimeType;
    }

    public void setSearchTimeType(String searchTimeType) {
        this.searchTimeType = searchTimeType;
    }

    public String getSearchDateType() {
        return searchDateType;
    }

    public void setSearchDateType(String searchDateType) {
        this.searchDateType = searchDateType;
    }

    public String getSearchDateStart() {
        return searchDateStart;
    }

    public void setSearchDateStart(String searchDateStart) {
        this.searchDateStart = searchDateStart;
    }

    public String getSearchDateEnd() {
        return searchDateEnd;
    }

    public void setSearchDateEnd(String searchDateEnd) {
        this.searchDateEnd = searchDateEnd;
    }
}
