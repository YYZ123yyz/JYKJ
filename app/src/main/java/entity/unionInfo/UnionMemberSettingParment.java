package entity.unionInfo;

import java.io.Serializable;

public class UnionMemberSettingParment implements Serializable {

    private         String              loginDoctorPosition;
    private         String              unionCode;
    private         String              doctorCode;
    private         String              doctorName;
    private         String              flagOperPower;
    private         String              flagPerson;
    private         String              unionOrgId;
    private         String              unionOrgName;
    private         String              flagSeePatient;
    private         String              flagBlacklist;
    private         String              operDoctorCode;
    private         String              operDoctorName;


    public String getUnionOrgName() {
        return unionOrgName;
    }

    public void setUnionOrgName(String unionOrgName) {
        this.unionOrgName = unionOrgName;
    }

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getUnionCode() {
        return unionCode;
    }

    public void setUnionCode(String unionCode) {
        this.unionCode = unionCode;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getFlagOperPower() {
        return flagOperPower;
    }

    public void setFlagOperPower(String flagOperPower) {
        this.flagOperPower = flagOperPower;
    }

    public String getFlagPerson() {
        return flagPerson;
    }

    public void setFlagPerson(String flagPerson) {
        this.flagPerson = flagPerson;
    }

    public String getUnionOrgId() {
        return unionOrgId;
    }

    public void setUnionOrgId(String unionOrgId) {
        this.unionOrgId = unionOrgId;
    }

    public String getFlagSeePatient() {
        return flagSeePatient;
    }

    public void setFlagSeePatient(String flagSeePatient) {
        this.flagSeePatient = flagSeePatient;
    }

    public String getFlagBlacklist() {
        return flagBlacklist;
    }

    public void setFlagBlacklist(String flagBlacklist) {
        this.flagBlacklist = flagBlacklist;
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
}
