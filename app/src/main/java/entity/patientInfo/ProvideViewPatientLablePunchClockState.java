package entity.patientInfo;

import java.io.Serializable;

public class ProvideViewPatientLablePunchClockState implements Serializable{

    /**
     * birthday : 1593792000000
     * detectRate : 1
     * detectRateUnitCode : 1
     * detectRateUnitName : 天
     * gender : 1
     * newLoginDate : 1597128986000
     * patientCode : 00440e854c0e46d38596b54593312a61
     * signCode : c95854bee259466b8b491ddff19f64f4
     * signDuration : 6
     * signDurationUnit : 6
     * signNo : 0106202008171040238187187284
     * signOtherServiceCode : 110
     * signPrice : 1840.0
     * signStatus : 20
     * signUnit : 月
     * stateType : 1
     * userLabelSecond : 3000102
     * userLabelSecondName : 正常
     * userLogoUrl : http://114.215.137.171:8040/defaultImageShow.jpg
     * userName : 患者测试账号00011
     */

    private long birthday;
    private int detectRate;
    private String detectRateUnitCode;
    private String detectRateUnitName;
    private int gender;
    private long newLoginDate;
    private String patientCode;
    private String signCode;
    private int signDuration;
    private String signDurationUnit;
    private String orderCode;
    private String signNo;
    private String signOtherServiceCode;
    private double signPrice;
    private String signStatus;
    private String signUnit;
    private String stateType;
    private String userLabelSecond;
    private String userLabelSecondName;
    private String userLogoUrl;
    private String userName;
    private String reportUrl;
    private int isSigning;
    private String stateTypeName;
    private String linkPhone;


    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getDetectRate() {
        return detectRate;
    }

    public void setDetectRate(int detectRate) {
        this.detectRate = detectRate;
    }

    public String getDetectRateUnitCode() {
        return detectRateUnitCode;
    }

    public void setDetectRateUnitCode(String detectRateUnitCode) {
        this.detectRateUnitCode = detectRateUnitCode;
    }

    public String getDetectRateUnitName() {
        return detectRateUnitName;
    }

    public void setDetectRateUnitName(String detectRateUnitName) {
        this.detectRateUnitName = detectRateUnitName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getNewLoginDate() {
        return newLoginDate;
    }

    public void setNewLoginDate(long newLoginDate) {
        this.newLoginDate = newLoginDate;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public int getSignDuration() {
        return signDuration;
    }

    public void setSignDuration(int signDuration) {
        this.signDuration = signDuration;
    }

    public String getSignDurationUnit() {
        return signDurationUnit;
    }

    public void setSignDurationUnit(String signDurationUnit) {
        this.signDurationUnit = signDurationUnit;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getSignOtherServiceCode() {
        return signOtherServiceCode;
    }

    public void setSignOtherServiceCode(String signOtherServiceCode) {
        this.signOtherServiceCode = signOtherServiceCode;
    }

    public double getSignPrice() {
        return signPrice;
    }

    public void setSignPrice(double signPrice) {
        this.signPrice = signPrice;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getUserLabelSecond() {
        return userLabelSecond;
    }

    public void setUserLabelSecond(String userLabelSecond) {
        this.userLabelSecond = userLabelSecond;
    }

    public String getUserLabelSecondName() {
        return userLabelSecondName;
    }

    public void setUserLabelSecondName(String userLabelSecondName) {
        this.userLabelSecondName = userLabelSecondName;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getIsSigning() {
        return isSigning;
    }

    public void setIsSigning(int isSigning) {
        this.isSigning = isSigning;
    }

    public String getStateTypeName() {
        return stateTypeName;
    }

    public void setStateTypeName(String stateTypeName) {
        this.stateTypeName = stateTypeName;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
}
