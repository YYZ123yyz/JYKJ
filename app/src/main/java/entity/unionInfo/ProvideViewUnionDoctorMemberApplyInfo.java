package entity.unionInfo;

import java.util.Date;


/**
 * 医生联盟-医生加入联盟申请表
 * 
 * 关联：
 * 1. 联盟信息
 * 2. 联盟等级
 * 
 * @author JiaQ
 */

public class ProvideViewUnionDoctorMemberApplyInfo implements java.io.Serializable {
	private Integer memberApplyId;//编号
	private String applyCode;//申请编码
	
	private String unionCode;//联盟编码
	private String unionName;//联盟名称
	private String unionGradeName;//联盟等级名称.默认:默认等级
	private Integer upperLimitDoctorNum;//医生人数上限
	private Integer upperLimitDoctorNumNow;//当前(现有)医生人数上限
	private Integer upperLimitPatientNum;//患者人数上限
	private Integer upperLimitPatientNumNow;//当前(现有)患者人数上限
	private String unionQrCode;//联盟二维码
	private String unionLogoUrl;//联盟图标
	private String unionSynopsis;//联盟简介
	
	private String applyDoctorCode;//申请医生-编码
	private String applyDoctorName;//申请医生-姓名
	
	private String receiverDoctorCode;//接收人(审核人)-编码
	private String receiverDoctorName;//接收人(审核人)-姓名
	
	private Date applyDate;//申请日期
	private String applyReason;//申请描述
	private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述
	
	private String approvalDoctorCode;//审批(审核)人-编码
	private String approvalDoctorName;//审批(审核)人-姓名
	private Date approvalDate;//审批日期
	
	private String remark;
	
	
	
	/******************************** 【非对称属性】 *********************************/
	private String qrCode;//二维码
	private String userLogoUrl;//用户头像
	private Integer gender;//性别.0:未知;1:男;2:女;
	private Date birthday;//生日
	private String provinceName;//用户所在省份名称
	private String cityName;//用户所在城市名称
	private String areaName;//用户所在区(县)名称
	
	private String hospitalInfoName;//医院名称
	private String departmentName;//一级科室名称
	private String departmentSecondName;//二级科室名称
	private String doctorTitleName;//医生职称名称
	private String synopsis;//医生个人简介
	private String goodAtRealm;//医生擅长领域
	private Integer flagDoctorStatus;//医生认证状态.0:未认证;1:已认证;


	public Integer getMemberApplyId() {
		return memberApplyId;
	}

	public void setMemberApplyId(Integer memberApplyId) {
		this.memberApplyId = memberApplyId;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(String unionCode) {
		this.unionCode = unionCode;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public String getUnionGradeName() {
		return unionGradeName;
	}

	public void setUnionGradeName(String unionGradeName) {
		this.unionGradeName = unionGradeName;
	}

	public Integer getUpperLimitDoctorNum() {
		return upperLimitDoctorNum;
	}

	public void setUpperLimitDoctorNum(Integer upperLimitDoctorNum) {
		this.upperLimitDoctorNum = upperLimitDoctorNum;
	}

	public Integer getUpperLimitDoctorNumNow() {
		return upperLimitDoctorNumNow;
	}

	public void setUpperLimitDoctorNumNow(Integer upperLimitDoctorNumNow) {
		this.upperLimitDoctorNumNow = upperLimitDoctorNumNow;
	}

	public Integer getUpperLimitPatientNum() {
		return upperLimitPatientNum;
	}

	public void setUpperLimitPatientNum(Integer upperLimitPatientNum) {
		this.upperLimitPatientNum = upperLimitPatientNum;
	}

	public Integer getUpperLimitPatientNumNow() {
		return upperLimitPatientNumNow;
	}

	public void setUpperLimitPatientNumNow(Integer upperLimitPatientNumNow) {
		this.upperLimitPatientNumNow = upperLimitPatientNumNow;
	}

	public String getUnionQrCode() {
		return unionQrCode;
	}

	public void setUnionQrCode(String unionQrCode) {
		this.unionQrCode = unionQrCode;
	}

	public String getUnionLogoUrl() {
		return unionLogoUrl;
	}

	public void setUnionLogoUrl(String unionLogoUrl) {
		this.unionLogoUrl = unionLogoUrl;
	}

	public String getUnionSynopsis() {
		return unionSynopsis;
	}

	public void setUnionSynopsis(String unionSynopsis) {
		this.unionSynopsis = unionSynopsis;
	}

	public String getApplyDoctorCode() {
		return applyDoctorCode;
	}

	public void setApplyDoctorCode(String applyDoctorCode) {
		this.applyDoctorCode = applyDoctorCode;
	}

	public String getApplyDoctorName() {
		return applyDoctorName;
	}

	public void setApplyDoctorName(String applyDoctorName) {
		this.applyDoctorName = applyDoctorName;
	}

	public String getReceiverDoctorCode() {
		return receiverDoctorCode;
	}

	public void setReceiverDoctorCode(String receiverDoctorCode) {
		this.receiverDoctorCode = receiverDoctorCode;
	}

	public String getReceiverDoctorName() {
		return receiverDoctorName;
	}

	public void setReceiverDoctorName(String receiverDoctorName) {
		this.receiverDoctorName = receiverDoctorName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
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

	public String getApprovalDoctorCode() {
		return approvalDoctorCode;
	}

	public void setApprovalDoctorCode(String approvalDoctorCode) {
		this.approvalDoctorCode = approvalDoctorCode;
	}

	public String getApprovalDoctorName() {
		return approvalDoctorName;
	}

	public void setApprovalDoctorName(String approvalDoctorName) {
		this.approvalDoctorName = approvalDoctorName;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getUserLogoUrl() {
		return userLogoUrl;
	}

	public void setUserLogoUrl(String userLogoUrl) {
		this.userLogoUrl = userLogoUrl;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getHospitalInfoName() {
		return hospitalInfoName;
	}

	public void setHospitalInfoName(String hospitalInfoName) {
		this.hospitalInfoName = hospitalInfoName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentSecondName() {
		return departmentSecondName;
	}

	public void setDepartmentSecondName(String departmentSecondName) {
		this.departmentSecondName = departmentSecondName;
	}

	public String getDoctorTitleName() {
		return doctorTitleName;
	}

	public void setDoctorTitleName(String doctorTitleName) {
		this.doctorTitleName = doctorTitleName;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getGoodAtRealm() {
		return goodAtRealm;
	}

	public void setGoodAtRealm(String goodAtRealm) {
		this.goodAtRealm = goodAtRealm;
	}

	public Integer getFlagDoctorStatus() {
		return flagDoctorStatus;
	}

	public void setFlagDoctorStatus(Integer flagDoctorStatus) {
		this.flagDoctorStatus = flagDoctorStatus;
	}
}
