package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 15:46
 */
public class PatientInfoBean implements Serializable {


    /**
     * mainDoctorCode : 7b5d2d0205164f12974a3e228f5a6083
     * mainDoctorName : 医生测试002
     * mainPatientCode : df90285cf3be47e2b74f24837f0b4652
     * mainPatientName : Wigan
     * patientAge : 0
     * patientLogoUrl : http://114.215.137.171:8040/patientImage/patientlogo/df90285cf3be47e2b74f24837f0b4652/Patientlogo_20200823213844.jpg
     * patientSex : 1
     * reserveCode : d05a58e2325349cda15ed19804a81037
     * reserveConfigStart : 1598911200000
     * reserveProjectCode : 1
     * reserveProjectName : 图文就诊
     * reserveRosterDateCode : fd0ca9eab60a4212964297fe133cd6bc
     * reserveStatus : 10
     * treatmentType : 1
     * version : 0
     */

    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainPatientName;
    private String patientAge;
    private String patientLogoUrl;
    private String patientSex;
    private String reserveCode;
    private long reserveConfigStart;
    private String reserveProjectCode;
    private String reserveProjectName;
    private String reserveRosterDateCode;
    private String reserveStatus;
    private int treatmentType;
    private int version;
    private String orderCode;
    private String reportUrl;

    public String getMainDoctorCode() {
        return mainDoctorCode;
    }

    public void setMainDoctorCode(String mainDoctorCode) {
        this.mainDoctorCode = mainDoctorCode;
    }

    public String getMainDoctorName() {
        return mainDoctorName;
    }

    public void setMainDoctorName(String mainDoctorName) {
        this.mainDoctorName = mainDoctorName;
    }

    public String getMainPatientCode() {
        return mainPatientCode;
    }

    public void setMainPatientCode(String mainPatientCode) {
        this.mainPatientCode = mainPatientCode;
    }

    public String getMainPatientName() {
        return mainPatientName;
    }

    public void setMainPatientName(String mainPatientName) {
        this.mainPatientName = mainPatientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientLogoUrl() {
        return patientLogoUrl;
    }

    public void setPatientLogoUrl(String patientLogoUrl) {
        this.patientLogoUrl = patientLogoUrl;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public long getReserveConfigStart() {
        return reserveConfigStart;
    }

    public void setReserveConfigStart(long reserveConfigStart) {
        this.reserveConfigStart = reserveConfigStart;
    }

    public String getReserveProjectCode() {
        return reserveProjectCode;
    }

    public void setReserveProjectCode(String reserveProjectCode) {
        this.reserveProjectCode = reserveProjectCode;
    }

    public String getReserveProjectName() {
        return reserveProjectName;
    }

    public void setReserveProjectName(String reserveProjectName) {
        this.reserveProjectName = reserveProjectName;
    }

    public String getReserveRosterDateCode() {
        return reserveRosterDateCode;
    }

    public void setReserveRosterDateCode(String reserveRosterDateCode) {
        this.reserveRosterDateCode = reserveRosterDateCode;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
