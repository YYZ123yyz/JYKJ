package entity.unionInfo;

import java.io.Serializable;
import java.util.Date;


/**
 * 医生联盟-医生加入联盟邀请表
 * 
 * @author JiaQ
 */
public class ProvideViewUnionDoctorMemberInviteInfo implements Serializable {
	private Integer memberInviteId;//编号
	private String operCode;//操作编码
	private String inviteCode;//邀请编码(每条邀请信息唯一性)	
	private String unionCode;//关联编码
	
	
	
	private String unionName;//联盟名称
	private String unionNameAlias;//联盟名称(别名)
	private String unionNameSpell;//联盟名称拼音助记码
	private Integer unionGrade;//联盟等级
	private String basicsGradeCode;//联盟等级
	private String unionGradeName;//联盟等级名称.默认:默认等级
	private Integer upperLimitDoctorNum;//医生人数上限
	private Integer upperLimitDoctorNumNow;//当前(现有)医生人数上限
	private Integer upperLimitPatientNum;//患者人数上限
	private Integer upperLimitPatientNumNow;//当前(现有)患者人数上限
	private String unionQrCode;//联盟二维码
	private String unionLogoUrl;//联盟图标
	private String unionSynopsis;//联盟简介
	
	
	
	private String inviteDoctorCode;//被邀请加入联盟的医生编码
	private String inviteDoctorName;//被邀请加入联盟的医生姓名
	private String inviteDoctorNameAlias;//被邀请加入联盟的医生姓名(别名)
	private Integer flagInviteType;//申请类型.0:未知;1:创建联盟时邀请;2:创建联盟后邀请;
	private String operDoctorCode;//邀请人编码
	private String operDoctorName;//邀请人姓名
	private String operDoctorNameAlias;//邀请人姓名(别名)
	private Date inviteDate;//邀请日期
	private String inviteReason;//邀请描述
	private Integer flagInviteState;//邀请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述
	
	
	
	/******************************** 【被邀请医生的详细信息】 *********************************/
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

	//提交的参数
	private String loginDoctorPosition;

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public Integer getMemberInviteId() {
		return memberInviteId;
	}

	public void setMemberInviteId(Integer memberInviteId) {
		this.memberInviteId = memberInviteId;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
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

	public String getUnionNameAlias() {
		return unionNameAlias;
	}

	public void setUnionNameAlias(String unionNameAlias) {
		this.unionNameAlias = unionNameAlias;
	}

	public String getUnionNameSpell() {
		return unionNameSpell;
	}

	public void setUnionNameSpell(String unionNameSpell) {
		this.unionNameSpell = unionNameSpell;
	}

	public Integer getUnionGrade() {
		return unionGrade;
	}

	public void setUnionGrade(Integer unionGrade) {
		this.unionGrade = unionGrade;
	}

	public String getBasicsGradeCode() {
		return basicsGradeCode;
	}

	public void setBasicsGradeCode(String basicsGradeCode) {
		this.basicsGradeCode = basicsGradeCode;
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

	public String getInviteDoctorCode() {
		return inviteDoctorCode;
	}

	public void setInviteDoctorCode(String inviteDoctorCode) {
		this.inviteDoctorCode = inviteDoctorCode;
	}

	public String getInviteDoctorName() {
		return inviteDoctorName;
	}

	public void setInviteDoctorName(String inviteDoctorName) {
		this.inviteDoctorName = inviteDoctorName;
	}

	public String getInviteDoctorNameAlias() {
		return inviteDoctorNameAlias;
	}

	public void setInviteDoctorNameAlias(String inviteDoctorNameAlias) {
		this.inviteDoctorNameAlias = inviteDoctorNameAlias;
	}

	public Integer getFlagInviteType() {
		return flagInviteType;
	}

	public void setFlagInviteType(Integer flagInviteType) {
		this.flagInviteType = flagInviteType;
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

	public String getOperDoctorNameAlias() {
		return operDoctorNameAlias;
	}

	public void setOperDoctorNameAlias(String operDoctorNameAlias) {
		this.operDoctorNameAlias = operDoctorNameAlias;
	}

	public Date getInviteDate() {
		return inviteDate;
	}

	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}

	public String getInviteReason() {
		return inviteReason;
	}

	public void setInviteReason(String inviteReason) {
		this.inviteReason = inviteReason;
	}

	public Integer getFlagInviteState() {
		return flagInviteState;
	}

	public void setFlagInviteState(Integer flagInviteState) {
		this.flagInviteState = flagInviteState;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
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
