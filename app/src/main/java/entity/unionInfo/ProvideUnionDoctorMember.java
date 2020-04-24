package entity.unionInfo;

import java.util.Date;


/**
 * 【医生联盟】
 * 医生联盟-联盟成员(医生)权限表
 * 
 * 注意事项：
 * 创建群聊，包含联盟内所有成员。
 * 
 * @author JiaQ
 */

public class ProvideUnionDoctorMember implements java.io.Serializable {
	private Integer unionMemberId;//编号
	private String unionCode;//联盟编码
	private String doctorCode;//联盟内的医生-关联编码
	private Integer managePatientNum;//管理患者人数
	private Integer showSort;//展示排序编号
	private Integer flagCreateMan;//是否为联盟创建人.0:否;1:是;
	private Integer flagOperPower;//是否具有联盟操作权限.0:否;1:是;
	private Integer flagPerson;//是否为层级负责人.0:否;1:是;
	private Integer unionOrgId;//成员所在联盟内的层级编码
	private Integer flagSeePatient;//是否具有查看患者权限.0:否;1:是;
	private Integer flagUseState;//使用状态.1:使用;0:禁用;
	private Integer flagBlacklist;//是否黑名单.1:绑定关系;0:拉入黑名单;
	private Date flagBlacklistDate;//黑名单日期

	private String loginDoctorPosition;			//医生登录所处位置

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
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
