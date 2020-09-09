package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 14:14
 */
public class ReceiveTreatmentResultBean {


    /**
     * createDate : 1599535787000
     * createMan : system
     * departmentId :
     * departmentName :
     * departmentSecondId :
     * departmentSecondName :
     * doctorHospitalCode :
     * doctorHospitalName :
     * doctorLogoUrl : http://114.215.137.171:8040/doctorImage/logo/7b5d2d0205164f12974a3e228f5a6083/logo_20200622123604.jpg
     * doctorTitle :
     * doctorTitleName :
     * flagDel : 1
     * flagOrderState : 2
     * flagTreatmentState : 10
     * mainDoctorAlias :
     * mainDoctorCode : 7b5d2d0205164f12974a3e228f5a6083
     * mainDoctorName : 医生测试002
     * mainPatientAlias : ZQ
     * mainPatientCode : 54f4ee7c30124539b29879aae61dc786
     * mainPatientName : 张强
     * medicalRecordStatus : 10
     * orderCode : 0101202009081129474281510593
     * patientLogoUrl : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI45aK2P2L3KUh3aB3Wtsj6UqhBvnPhHz1PBy3CHcHPeiaVnA78SpSvfD8moicqniaKhhb3uBCKZyVOQ/132
     * reserveCode : 5972940cc318443b9a987b8bdace170c
     * reserveConfigEnd : 1599817500000
     * reserveConfigStart : 1599816600000
     * reserveId : 242
     * reserveProjectCode : 1
     * reserveProjectLastCount : 0
     * reserveProjectName : 图文就诊
     * reserveProjectSumCount : 0
     * reserveRosterDateCode : a74129e2a1bb4b5a9937af5cfd4c0d87
     * reserveStatus : 20
     * reserveStatusName : 已接诊
     * reserveTimes : 1599535787000
     * reserveToDoctorOrder : 1
     * sumDuration : -1
     * treatmentType : 1
     * updateDate : 1599545605000
     * updateMan : 54f4ee7c30124539b29879aae61dc786
     * useDuration : 0
     * version : 1
     */

    private long createDate;
    private String createMan;
    private String departmentId;
    private String departmentName;
    private String departmentSecondId;
    private String departmentSecondName;
    private String doctorHospitalCode;
    private String doctorHospitalName;
    private String doctorLogoUrl;
    private String doctorTitle;
    private String doctorTitleName;
    private int flagDel;
    private int flagOrderState;
    private int flagTreatmentState;
    private String mainDoctorAlias;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientAlias;
    private String mainPatientCode;
    private String mainPatientName;
    private String medicalRecordStatus;
    private String orderCode;
    private String patientLogoUrl;
    private String reserveCode;
    private long reserveConfigEnd;
    private long reserveConfigStart;
    private int reserveId;
    private String reserveProjectCode;
    private int reserveProjectLastCount;
    private String reserveProjectName;
    private int reserveProjectSumCount;
    private String reserveRosterDateCode;
    private String reserveStatus;
    private String reserveStatusName;
    private long reserveTimes;
    private int reserveToDoctorOrder;
    private int sumDuration;
    private int treatmentType;
    private long updateDate;
    private String updateMan;
    private int useDuration;
    private int version;
    private long admissionStartTimes;   //医生接诊开始时间
    private long admissionEndTimes;   //医生接诊结束时间

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentSecondId() {
        return departmentSecondId;
    }

    public void setDepartmentSecondId(String departmentSecondId) {
        this.departmentSecondId = departmentSecondId;
    }

    public String getDepartmentSecondName() {
        return departmentSecondName;
    }

    public void setDepartmentSecondName(String departmentSecondName) {
        this.departmentSecondName = departmentSecondName;
    }

    public String getDoctorHospitalCode() {
        return doctorHospitalCode;
    }

    public void setDoctorHospitalCode(String doctorHospitalCode) {
        this.doctorHospitalCode = doctorHospitalCode;
    }

    public String getDoctorHospitalName() {
        return doctorHospitalName;
    }

    public void setDoctorHospitalName(String doctorHospitalName) {
        this.doctorHospitalName = doctorHospitalName;
    }

    public String getDoctorLogoUrl() {
        return doctorLogoUrl;
    }

    public void setDoctorLogoUrl(String doctorLogoUrl) {
        this.doctorLogoUrl = doctorLogoUrl;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDoctorTitleName() {
        return doctorTitleName;
    }

    public void setDoctorTitleName(String doctorTitleName) {
        this.doctorTitleName = doctorTitleName;
    }

    public int getFlagDel() {
        return flagDel;
    }

    public void setFlagDel(int flagDel) {
        this.flagDel = flagDel;
    }

    public int getFlagOrderState() {
        return flagOrderState;
    }

    public void setFlagOrderState(int flagOrderState) {
        this.flagOrderState = flagOrderState;
    }

    public int getFlagTreatmentState() {
        return flagTreatmentState;
    }

    public void setFlagTreatmentState(int flagTreatmentState) {
        this.flagTreatmentState = flagTreatmentState;
    }

    public String getMainDoctorAlias() {
        return mainDoctorAlias;
    }

    public void setMainDoctorAlias(String mainDoctorAlias) {
        this.mainDoctorAlias = mainDoctorAlias;
    }

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

    public String getMainPatientAlias() {
        return mainPatientAlias;
    }

    public void setMainPatientAlias(String mainPatientAlias) {
        this.mainPatientAlias = mainPatientAlias;
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

    public String getMedicalRecordStatus() {
        return medicalRecordStatus;
    }

    public void setMedicalRecordStatus(String medicalRecordStatus) {
        this.medicalRecordStatus = medicalRecordStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPatientLogoUrl() {
        return patientLogoUrl;
    }

    public void setPatientLogoUrl(String patientLogoUrl) {
        this.patientLogoUrl = patientLogoUrl;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public long getReserveConfigEnd() {
        return reserveConfigEnd;
    }

    public void setReserveConfigEnd(long reserveConfigEnd) {
        this.reserveConfigEnd = reserveConfigEnd;
    }

    public long getReserveConfigStart() {
        return reserveConfigStart;
    }

    public void setReserveConfigStart(long reserveConfigStart) {
        this.reserveConfigStart = reserveConfigStart;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getReserveProjectCode() {
        return reserveProjectCode;
    }

    public void setReserveProjectCode(String reserveProjectCode) {
        this.reserveProjectCode = reserveProjectCode;
    }

    public int getReserveProjectLastCount() {
        return reserveProjectLastCount;
    }

    public void setReserveProjectLastCount(int reserveProjectLastCount) {
        this.reserveProjectLastCount = reserveProjectLastCount;
    }

    public String getReserveProjectName() {
        return reserveProjectName;
    }

    public void setReserveProjectName(String reserveProjectName) {
        this.reserveProjectName = reserveProjectName;
    }

    public int getReserveProjectSumCount() {
        return reserveProjectSumCount;
    }

    public void setReserveProjectSumCount(int reserveProjectSumCount) {
        this.reserveProjectSumCount = reserveProjectSumCount;
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

    public String getReserveStatusName() {
        return reserveStatusName;
    }

    public void setReserveStatusName(String reserveStatusName) {
        this.reserveStatusName = reserveStatusName;
    }

    public long getReserveTimes() {
        return reserveTimes;
    }

    public void setReserveTimes(long reserveTimes) {
        this.reserveTimes = reserveTimes;
    }

    public int getReserveToDoctorOrder() {
        return reserveToDoctorOrder;
    }

    public void setReserveToDoctorOrder(int reserveToDoctorOrder) {
        this.reserveToDoctorOrder = reserveToDoctorOrder;
    }

    public int getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(int sumDuration) {
        this.sumDuration = sumDuration;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public int getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(int useDuration) {
        this.useDuration = useDuration;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getAdmissionStartTimes() {
        return admissionStartTimes;
    }

    public void setAdmissionStartTimes(long admissionStartTimes) {
        this.admissionStartTimes = admissionStartTimes;
    }

    public long getAdmissionEndTimes() {
        return admissionEndTimes;
    }

    public void setAdmissionEndTimes(long admissionEndTimes) {
        this.admissionEndTimes = admissionEndTimes;
    }
}
