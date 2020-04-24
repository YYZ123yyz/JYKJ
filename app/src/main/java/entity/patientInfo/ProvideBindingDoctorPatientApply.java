package entity.patientInfo;

import java.util.Date;



/**
 * 【医患绑定】
 * 医患绑定申请表
 * 
 * @author JiaQ
 */

public class ProvideBindingDoctorPatientApply implements java.io.Serializable {
	private Integer dpApplyId;//医患绑定申请表编号
	private String dpBindingCode;//关联编码.外键:binding_doctor_patient
	private String operCode;//操作编码(每条操作信息唯一性.对消息表msg_push_reminder(operCode)对应)
	private String patientIdNumber;//身份证号
	private String patientUserName;//姓名
	private Integer patientGender;//性别.0:未知;1:男;2:女;
	private long patientBirthday;//生日
	private String patientLinkPhone;//联系手机
	private String patientAddress;//现居住地
	private Integer patientLabelId;//患者标签编码
	private String patientLabelName;//患者标签名称
	private Integer highPressureNum;//收缩压(高压mmHg)
	private Integer lowPressureNum;//舒张压(低压mmHg)
	private Integer heartRateNum;//心率(次/分钟)
	
	private String launchPatientCode;//患者关联编码
	private String invitationDoctorCode;//医生关联编码
	private String invitationDoctorName;//医生姓名
	
	private Integer flagApplyType;//申请类型.0:未知;1:扫码添加(医生扫码患者);2:手动添加(医生手动添加);3:;4:;5:;6:扫码添加(患者扫码医生,需要医生审核);7:手动添加(患者通过查找医生等方式添加,需要医生审核);8:;9:;10:;
	private long applyDate;//申请日期
	private String applyReason;//申请描述
	private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述
	private Date approvalDate;//审批日期

	public Integer getDpApplyId() {
		return dpApplyId;
	}

	public void setDpApplyId(Integer dpApplyId) {
		this.dpApplyId = dpApplyId;
	}

	public String getDpBindingCode() {
		return dpBindingCode;
	}

	public void setDpBindingCode(String dpBindingCode) {
		this.dpBindingCode = dpBindingCode;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getPatientIdNumber() {
		return patientIdNumber;
	}

	public void setPatientIdNumber(String patientIdNumber) {
		this.patientIdNumber = patientIdNumber;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public Integer getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(Integer patientGender) {
		this.patientGender = patientGender;
	}

	public long getPatientBirthday() {
		return patientBirthday;
	}

	public void setPatientBirthday(long patientBirthday) {
		this.patientBirthday = patientBirthday;
	}

	public String getPatientLinkPhone() {
		return patientLinkPhone;
	}

	public void setPatientLinkPhone(String patientLinkPhone) {
		this.patientLinkPhone = patientLinkPhone;
	}

	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public Integer getPatientLabelId() {
		return patientLabelId;
	}

	public void setPatientLabelId(Integer patientLabelId) {
		this.patientLabelId = patientLabelId;
	}

	public String getPatientLabelName() {
		return patientLabelName;
	}

	public void setPatientLabelName(String patientLabelName) {
		this.patientLabelName = patientLabelName;
	}

	public Integer getHighPressureNum() {
		return highPressureNum;
	}

	public void setHighPressureNum(Integer highPressureNum) {
		this.highPressureNum = highPressureNum;
	}

	public Integer getLowPressureNum() {
		return lowPressureNum;
	}

	public void setLowPressureNum(Integer lowPressureNum) {
		this.lowPressureNum = lowPressureNum;
	}

	public Integer getHeartRateNum() {
		return heartRateNum;
	}

	public void setHeartRateNum(Integer heartRateNum) {
		this.heartRateNum = heartRateNum;
	}

	public String getLaunchPatientCode() {
		return launchPatientCode;
	}

	public void setLaunchPatientCode(String launchPatientCode) {
		this.launchPatientCode = launchPatientCode;
	}

	public String getInvitationDoctorCode() {
		return invitationDoctorCode;
	}

	public void setInvitationDoctorCode(String invitationDoctorCode) {
		this.invitationDoctorCode = invitationDoctorCode;
	}

	public String getInvitationDoctorName() {
		return invitationDoctorName;
	}

	public void setInvitationDoctorName(String invitationDoctorName) {
		this.invitationDoctorName = invitationDoctorName;
	}

	public Integer getFlagApplyType() {
		return flagApplyType;
	}

	public void setFlagApplyType(Integer flagApplyType) {
		this.flagApplyType = flagApplyType;
	}

	public long getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(long applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public Integer getFlagApplyState() {
		return flagApplyState;
	}

	public void setFlagApplyState(Integer flagApplyState) {
		this.flagApplyState = flagApplyState;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
}
