package entity.liveroom;

import java.io.Serializable;

public class LiveRoomInfo implements Serializable {
    private String loginUserPosition;
    private String requestClientType;
    private String operUserCode;
    private String operUserName;
    private String broadcastState;
    private String broadcastType;
    private String broadcastTitle;
    private String broadcastCoverImgUrl;
    private String broadcastDate;
    private String attrCode;
    private String attrName;
    private String classCode;
    private String className;
    private String riskCode;
    private String riskName;
    private String doctorWatchObjectAuthCode;
    private String doctorWatchObjectAuthName;
    private String patientWatchObjectAuthCode;
    private String patientWatchObjectAuthName;

    public String getLoginUserPosition() {
        return loginUserPosition;
    }

    public void setLoginUserPosition(String loginUserPosition) {
        this.loginUserPosition = loginUserPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOperUserCode() {
        return operUserCode;
    }

    public void setOperUserCode(String operUserCode) {
        this.operUserCode = operUserCode;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getBroadcastState() {
        return broadcastState;
    }

    public void setBroadcastState(String broadcastState) {
        this.broadcastState = broadcastState;
    }

    public String getBroadcastType() {
        return broadcastType;
    }

    public void setBroadcastType(String broadcastType) {
        this.broadcastType = broadcastType;
    }

    public String getBroadcastTitle() {
        return broadcastTitle;
    }

    public void setBroadcastTitle(String broadcastTitle) {
        this.broadcastTitle = broadcastTitle;
    }

    public String getBroadcastCoverImgUrl() {
        return broadcastCoverImgUrl;
    }

    public void setBroadcastCoverImgUrl(String broadcastCoverImgUrl) {
        this.broadcastCoverImgUrl = broadcastCoverImgUrl;
    }

    public String getBroadcastDate() {
        return broadcastDate;
    }

    public void setBroadcastDate(String broadcastDate) {
        this.broadcastDate = broadcastDate;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public String getDoctorWatchObjectAuthCode() {
        return doctorWatchObjectAuthCode;
    }

    public void setDoctorWatchObjectAuthCode(String doctorWatchObjectAuthCode) {
        this.doctorWatchObjectAuthCode = doctorWatchObjectAuthCode;
    }

    public String getDoctorWatchObjectAuthName() {
        return doctorWatchObjectAuthName;
    }

    public void setDoctorWatchObjectAuthName(String doctorWatchObjectAuthName) {
        this.doctorWatchObjectAuthName = doctorWatchObjectAuthName;
    }

    public String getPatientWatchObjectAuthCode() {
        return patientWatchObjectAuthCode;
    }

    public void setPatientWatchObjectAuthCode(String patientWatchObjectAuthCode) {
        this.patientWatchObjectAuthCode = patientWatchObjectAuthCode;
    }

    public String getPatientWatchObjectAuthName() {
        return patientWatchObjectAuthName;
    }

    public void setPatientWatchObjectAuthName(String patientWatchObjectAuthName) {
        this.patientWatchObjectAuthName = patientWatchObjectAuthName;
    }
}
