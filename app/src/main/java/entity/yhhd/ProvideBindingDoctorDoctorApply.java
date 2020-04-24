package entity.yhhd;

import java.util.Date;


/**
 * 【医生好友绑定】
 * 医生好友绑定申请表
 * 
 * @author JiaQ
 */

public class ProvideBindingDoctorDoctorApply implements java.io.Serializable {
	private Integer ddApplyId;//医生好友绑定申请表编号
	private String ddBindingCode;//关联编码
	private String operCode;//操作编码
	private String launchDoctorCode;//发起人.医生-关联编码
	private String launchDoctorName;//发起人.医生姓名
	private String invitationDoctorCode;//被邀请人.医生-关联编码
	private String invitationDoctorName;//被邀请人.医生姓名
	private Integer flagApplyType;//申请类型.0:未知;1:扫码添加;2:手动添加(通过输入二维码);3:医生联盟内添加;
	private Date applyDate;//申请日期
	private String applyReason;//申请描述
	private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述
	private Date approvalDate;//审批日期
	

	private String flagApplyTypeName;//申请类型名称.0:未知;1:扫码添加;2:手动添加(通过输入二维码);3:医生联盟内添加;
	private String userLogoUrl;//用户头像

	private	int 	rowNum;
	private	int 	pageNum;
	private	String 	loginDoctorPosition;
	private	String 	searchDoctorCode;
	private	String 	searchDoctorName;
	private	String 	operDoctorCode;
	private	String 	operDoctorName;


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

	public Integer getDdApplyId() {
		return ddApplyId;
	}

	public void setDdApplyId(Integer ddApplyId) {
		this.ddApplyId = ddApplyId;
	}

	public String getDdBindingCode() {
		return ddBindingCode;
	}

	public void setDdBindingCode(String ddBindingCode) {
		this.ddBindingCode = ddBindingCode;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getLaunchDoctorCode() {
		return launchDoctorCode;
	}

	public void setLaunchDoctorCode(String launchDoctorCode) {
		this.launchDoctorCode = launchDoctorCode;
	}

	public String getLaunchDoctorName() {
		return launchDoctorName;
	}

	public void setLaunchDoctorName(String launchDoctorName) {
		this.launchDoctorName = launchDoctorName;
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

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getFlagApplyTypeName() {
		return flagApplyTypeName;
	}

	public void setFlagApplyTypeName(String flagApplyTypeName) {
		this.flagApplyTypeName = flagApplyTypeName;
	}

	public String getUserLogoUrl() {
		return userLogoUrl;
	}

	public void setUserLogoUrl(String userLogoUrl) {
		this.userLogoUrl = userLogoUrl;
	}

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

	public String getSearchDoctorCode() {
		return searchDoctorCode;
	}

	public void setSearchDoctorCode(String searchDoctorCode) {
		this.searchDoctorCode = searchDoctorCode;
	}

	public String getSearchDoctorName() {
		return searchDoctorName;
	}

	public void setSearchDoctorName(String searchDoctorName) {
		this.searchDoctorName = searchDoctorName;
	}
}
