package entity.user;

import java.util.Date;


/**
 * 用户(医生)基本信息-医生资质
 *
 * @author JiaQ
 */

public class ProvideDoctorQualification implements java.io.Serializable {
    private Integer doctorQualificationId;//医生资质编号
    private String doctorCode;//医生编码
    private String imgId;//图片ID
    private Integer idNumberPositive;//[医生]身份证正面.0:未上传;1:已上传;
    private Integer idNumberSide;//[医生]身份证反面.0:未上传;1:已上传;
    private Integer practising;//[医生]医师执业证.0:未上传;1:已上传;
    private Integer qualification;//[医生]医师资格证.0:未上传;1:已上传;
    private Integer professional;//[医生]医师职称证.0:未上传;1:已上传;

    private Integer flagAuthenticationType;//认证类型.1:医生线上自行认证;2:线下认证;3:医院统一认证;4:;5:;
    private Integer flagSubmitState;//提交状态.0:未提交;1:已提交;
    private Date submitDate;//提交日期
    private String submitReason;//[预留]提交描述
    private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
    private String refuseReason;//拒绝(未通过)原因描述
    private Date approvalDate;//审批日期

    private Integer flagDel;
    private String remark;
    private Date createDate;
    private String createMan;
    private Date updateDate;
    private String updateMan;

    @Override
    public String toString() {
        return "ProvideDoctorQualification{" +
                "doctorQualificationId=" + doctorQualificationId +
                ", doctorCode='" + doctorCode + '\'' +
                ", imgId='" + imgId + '\'' +
                ", idNumberPositive=" + idNumberPositive +
                ", idNumberSide=" + idNumberSide +
                ", practising=" + practising +
                ", qualification=" + qualification +
                ", professional=" + professional +
                ", flagAuthenticationType=" + flagAuthenticationType +
                ", flagSubmitState=" + flagSubmitState +
                ", submitDate=" + submitDate +
                ", submitReason='" + submitReason + '\'' +
                ", flagApplyState=" + flagApplyState +
                ", refuseReason='" + refuseReason + '\'' +
                ", approvalDate=" + approvalDate +
                ", flagDel=" + flagDel +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", createMan='" + createMan + '\'' +
                ", updateDate=" + updateDate +
                ", updateMan='" + updateMan + '\'' +
                ", idNumberPositiveImgUrl='" + idNumberPositiveImgUrl + '\'' +
                ", idNumberSideImgUrl='" + idNumberSideImgUrl + '\'' +
                ", practisingImgUrl='" + practisingImgUrl + '\'' +
                ", qualificationImgUrl='" + qualificationImgUrl + '\'' +
                ", professionalImgUrl='" + professionalImgUrl + '\'' +
                '}';
    }

    private String idNumberPositiveImgUrl;//[医生]身份证正面图片地址
    private String idNumberSideImgUrl;//[医生]身份证反面图片地址
    private String practisingImgUrl;//[医生]医师执业证图片地址

    public void setDoctorQualificationId(Integer doctorQualificationId) {
        this.doctorQualificationId = doctorQualificationId;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public void setIdNumberPositive(Integer idNumberPositive) {
        this.idNumberPositive = idNumberPositive;
    }

    public void setIdNumberSide(Integer idNumberSide) {
        this.idNumberSide = idNumberSide;
    }

    public void setPractising(Integer practising) {
        this.practising = practising;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public void setProfessional(Integer professional) {
        this.professional = professional;
    }

    public void setFlagAuthenticationType(Integer flagAuthenticationType) {
        this.flagAuthenticationType = flagAuthenticationType;
    }

    public void setFlagSubmitState(Integer flagSubmitState) {
        this.flagSubmitState = flagSubmitState;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public void setSubmitReason(String submitReason) {
        this.submitReason = submitReason;
    }

    public void setFlagApplyState(Integer flagApplyState) {
        this.flagApplyState = flagApplyState;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void setFlagDel(Integer flagDel) {
        this.flagDel = flagDel;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public void setIdNumberPositiveImgUrl(String idNumberPositiveImgUrl) {
        this.idNumberPositiveImgUrl = idNumberPositiveImgUrl;
    }

    public void setIdNumberSideImgUrl(String idNumberSideImgUrl) {
        this.idNumberSideImgUrl = idNumberSideImgUrl;
    }

    public void setPractisingImgUrl(String practisingImgUrl) {
        this.practisingImgUrl = practisingImgUrl;
    }

    public void setQualificationImgUrl(String qualificationImgUrl) {
        this.qualificationImgUrl = qualificationImgUrl;
    }

    public void setProfessionalImgUrl(String professionalImgUrl) {
        this.professionalImgUrl = professionalImgUrl;
    }

    public Integer getDoctorQualificationId() {
        return doctorQualificationId;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public String getImgId() {
        return imgId;
    }

    public Integer getIdNumberPositive() {
        return idNumberPositive;
    }

    public Integer getIdNumberSide() {
        return idNumberSide;
    }

    public Integer getPractising() {
        return practising;
    }

    public Integer getQualification() {
        return qualification;
    }

    public Integer getProfessional() {
        return professional;
    }

    public Integer getFlagAuthenticationType() {
        return flagAuthenticationType;
    }

    public Integer getFlagSubmitState() {
        return flagSubmitState;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public String getSubmitReason() {
        return submitReason;
    }

    public Integer getFlagApplyState() {
        return flagApplyState;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public Integer getFlagDel() {
        return flagDel;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public String getIdNumberPositiveImgUrl() {
        return idNumberPositiveImgUrl;
    }

    public String getIdNumberSideImgUrl() {
        return idNumberSideImgUrl;
    }

    public String getPractisingImgUrl() {
        return practisingImgUrl;
    }

    public String getQualificationImgUrl() {
        return qualificationImgUrl;
    }

    public String getProfessionalImgUrl() {
        return professionalImgUrl;
    }

    private String qualificationImgUrl;//[医生]医师资格证图片地址
    private String professionalImgUrl;//[医生]医师职称证图片地址

}
