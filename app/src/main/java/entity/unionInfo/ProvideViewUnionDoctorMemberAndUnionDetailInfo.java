package entity.unionInfo;

import java.util.Date;


/**
 * 【医生联盟】
 * 联盟成员、联盟信息表
 * 
 * @author JiaQ
 */

public class ProvideViewUnionDoctorMemberAndUnionDetailInfo implements java.io.Serializable {
	private Integer unionMemberId;//编号
	
	private String unionCode;//联盟编码
	private String unionName;//联盟名称
	private String unionNameAlias;//联盟名称(别名)
	private String unionNameSpell;//联盟名称拼音助记码
	private String unionQrCode;//联盟二维码
	private String unionLogoUrl;//联盟图标
	private String unionSynopsis;//联盟简介
	private String basicsGradeCode;//联盟等级编码
	private String unionGradeName;//联盟等级名称.默认:默认等级
	
	private String doctorCode;//联盟内的医生编码
	private Integer managePatientNum;//管理患者人数
	private Integer showSort;//展示排序编号
	private Integer flagCreateMan;//是否为联盟创建人.0:否;1:是;
	private Integer flagOperPower;//是否具有联盟操作权限.0:否;1:是;
	private Integer flagPerson;//是否为层级负责人.0:否;1:是;
	
	private Integer unionOrgId;//联盟层级编号.成员所在联盟内的层级编码
	private String unionOrgName;//联盟层级名称
	
	private Integer flagSeePatient;//是否具有查看患者权限.0:否;1:是;
	private Integer flagMainShow;//联盟主展示.0:否;1:是;
	private Integer flagUseState;//使用状态.1:使用;0:禁用;
	private Integer flagBlacklist;//是否黑名单.1:绑定关系;0:拉入黑名单;
	private Date flagBlacklistDate;//黑名单日期
	private String loginDoctorPosition;				//登录用户所在经纬度

	private	String exitUnionCode;			//退出联盟编码
	private	String exitUnionName;			//退出联盟名称
	private	String exitDoctorCode;			//退出联盟医生编码
	private	String exitDoctorName;			//退出联盟医生姓名

	private int rowNum;                     //每页数量
	private int pageNum;                    //页码


	public String getExitUnionCode() {
		return exitUnionCode;
	}

	public void setExitUnionCode(String exitUnionCode) {
		this.exitUnionCode = exitUnionCode;
	}

	public String getExitUnionName() {
		return exitUnionName;
	}

	public void setExitUnionName(String exitUnionName) {
		this.exitUnionName = exitUnionName;
	}

	public String getExitDoctorCode() {
		return exitDoctorCode;
	}

	public void setExitDoctorCode(String exitDoctorCode) {
		this.exitDoctorCode = exitDoctorCode;
	}

	public String getExitDoctorName() {
		return exitDoctorName;
	}

	public void setExitDoctorName(String exitDoctorName) {
		this.exitDoctorName = exitDoctorName;
	}

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

	public Integer getFlagMainShow() {
		return flagMainShow;
	}

	public void setFlagMainShow(Integer flagMainShow) {
		this.flagMainShow = flagMainShow;
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

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
}
