package entity.unionInfo;

import java.util.Date;


/**
 * 医生联盟-联盟成员(医生)信息表
 * 
 * @author JiaQ
 */

public class ProvideViewUnionDoctorMemberDetailInfo implements java.io.Serializable {
	private Integer unionMemberId;//编号
	private String unionCode;//联盟编码
	
	private String doctorCode;//联盟内的医生-关联编码
	private String linkPhone;//联系电话
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String qrCode;//二维码
	private String userLogoUrl;//头像
	private Integer gender;//性别.0:未知;1:男;2:女;
	private Date birthday;//出生日期
	private String hospitalInfoCode;//医院编码
	private String hospitalInfoName;//医院名称
	private String departmentId;//一级科室编码
	private String departmentName;//一级科室名称
	private String departmentSecondId;//二级科室编码
	private String departmentSecondName;//二级科室名称
	private Integer doctorTitle;//医生职称编码
	private String doctorTitleName;//医生职称名称
	private String synopsis;//个人简介
	private String goodAtRealm;//擅长领域
	private Integer flagDoctorStatus;//医生认证状态.0:未认证;1:已认证;
	private Date newLoginDate;//最后一次登录日期
		
	private Integer managePatientNum;//管理患者人数
	private Integer showSort;//展示排序编号.默认与unionMemberId相同
	private Integer flagCreateMan;//是否为联盟创建人.0:否;1:是;
	private Integer flagOperPower;//是否具有联盟操作权限.0:否;1:是;
	private Integer flagPerson;//是否为层级负责人.0:否;1:是;
	
	private Integer unionOrgId;//联盟层级编号.成员所在联盟内的层级编码
	private String unionOrgName;//联盟层级名称
	
	private Integer flagSeePatient;//是否具有查看患者权限.0:否;1:是;
	private Integer flagUseState;//使用状态.1:使用;0:禁用;
	private Integer flagBlacklist;//是否黑名单.1:绑定关系;0:拉入黑名单;
	private Date flagBlacklistDate;//黑名单日期

	private String loginDoctorPosition;			//
	private int rowNum;
	private int pageNum;


	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
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

	public Integer getUnionMemberId() {
		return unionMemberId;
	}

	public void setUnionMemberId(Integer unionMemberId) {
		this.unionMemberId = unionMemberId;
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

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameAlias() {
		return userNameAlias;
	}

	public void setUserNameAlias(String userNameAlias) {
		this.userNameAlias = userNameAlias;
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

	public String getHospitalInfoCode() {
		return hospitalInfoCode;
	}

	public void setHospitalInfoCode(String hospitalInfoCode) {
		this.hospitalInfoCode = hospitalInfoCode;
	}

	public String getHospitalInfoName() {
		return hospitalInfoName;
	}

	public void setHospitalInfoName(String hospitalInfoName) {
		this.hospitalInfoName = hospitalInfoName;
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

	public Integer getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(Integer doctorTitle) {
		this.doctorTitle = doctorTitle;
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

	public Date getNewLoginDate() {
		return newLoginDate;
	}

	public void setNewLoginDate(Date newLoginDate) {
		this.newLoginDate = newLoginDate;
	}

	public Integer getManagePatientNum() {
		return managePatientNum;
	}

	public void setManagePatientNum(Integer managePatientNum) {
		this.managePatientNum = managePatientNum;
	}

	public Integer getShowSort() {
		return showSort;
	}

	public void setShowSort(Integer showSort) {
		this.showSort = showSort;
	}

	public Integer getFlagCreateMan() {
		return flagCreateMan;
	}

	public void setFlagCreateMan(Integer flagCreateMan) {
		this.flagCreateMan = flagCreateMan;
	}

	public Integer getFlagOperPower() {
		return flagOperPower;
	}

	public void setFlagOperPower(Integer flagOperPower) {
		this.flagOperPower = flagOperPower;
	}

	public Integer getFlagPerson() {
		return flagPerson;
	}

	public void setFlagPerson(Integer flagPerson) {
		this.flagPerson = flagPerson;
	}

	public Integer getUnionOrgId() {
		return unionOrgId;
	}

	public void setUnionOrgId(Integer unionOrgId) {
		this.unionOrgId = unionOrgId;
	}

	public String getUnionOrgName() {
		return unionOrgName;
	}

	public void setUnionOrgName(String unionOrgName) {
		this.unionOrgName = unionOrgName;
	}

	public Integer getFlagSeePatient() {
		return flagSeePatient;
	}

	public void setFlagSeePatient(Integer flagSeePatient) {
		this.flagSeePatient = flagSeePatient;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public Integer getFlagBlacklist() {
		return flagBlacklist;
	}

	public void setFlagBlacklist(Integer flagBlacklist) {
		this.flagBlacklist = flagBlacklist;
	}

	public Date getFlagBlacklistDate() {
		return flagBlacklistDate;
	}

	public void setFlagBlacklistDate(Date flagBlacklistDate) {
		this.flagBlacklistDate = flagBlacklistDate;
	}
}
