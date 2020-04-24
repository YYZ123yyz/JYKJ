package entity.unionInfo;

import java.util.Date;


/**
 * 【医生联盟】
 * 基础信息-关联视图详细信息
 * 
 * @author JiaQ
 */
public class ProvideViewUnionDoctorDetailInfo {
	private Integer unionId;//编号
	private String unionCode;//关联编码
	private String unionName;//联盟名称
	private String unionNameAlias;//联盟名称(别名)
	private String unionNameSpell;//联盟名称拼音助记码
	
	private Integer unionGrade;//联盟等级
	private String basicsGradeCode;//联盟等级
	private String unionGradeName;//联盟等级名称
	private Integer upperLimitDoctorNum;//医生人数上限
	private Integer upperLimitDoctorNumNow;//当前(现有)医生人数上限
	private Integer upperLimitPatientNum;//患者人数上限
	private Integer upperLimitPatientNumNow;//当前(现有)患者人数上限
	private Integer flagApplyPromoteGrade;//是否可申请提升等级.0:不可提升;1:可提升;
	private Integer flagUseState;//使用状态.1:使用;0:禁用;
	
	private String unionQrCode;//联盟二维码
	private String unionLogoUrl;//联盟图标
	private String unionSynopsis;//联盟简介
	private String country;//所在国家.默认为：中国
	
	private String province;//所在省份
	private String provinceName;//所在省份名称
	private String city;//所在城市
	private String cityName;//所在城市名称
	private String area;//所在区(县)
	private String areaName;//所在区(县)名称
	
	private String address;//地址
	
	private String hospitalId;//医院编号
	private String hospitalName;//医院名称
	
	private String departmentId;//科室编号(大类)
	private String departmentName;//科室名称
	
	private String departmentSecondId;//二级科室编号(小类)
	private String departmentSecondName;//二级科室名称
	
	private String applyDoctorCode;//申请医生-关联编码
	private String userName;//申请医生姓名
	
	private Integer flagApplyType;//申请类型.0:未知;1:平台申请;
	private Date applyDate;//申请日期
	private String applyReason;//申请描述
	private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
	private String refuseReason;//拒绝(未通过)原因描述

	private String loginDoctorPosition;			//

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	/*
	 * 申请加入医生联盟时的申请状态
	 * 【获取医生联盟数据时使用】
	 * 应用场景：
	 * 	用于限制用于的【申请加入联盟】操作。
	 * 
	 * 0	==> 未加入、未提交申请(或之前提交的申请已过期、已被拒绝)；
	 * 1	==> 未加入、已提交申请；
	 * 2	==> 已加入；
	 * */
	private Integer unionApplyJoinState;


	public Integer getUnionId() {
		return unionId;
	}

	public void setUnionId(Integer unionId) {
		this.unionId = unionId;
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

	public Integer getFlagApplyPromoteGrade() {
		return flagApplyPromoteGrade;
	}

	public void setFlagApplyPromoteGrade(Integer flagApplyPromoteGrade) {
		this.flagApplyPromoteGrade = flagApplyPromoteGrade;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
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

	public String getApplyDoctorCode() {
		return applyDoctorCode;
	}

	public void setApplyDoctorCode(String applyDoctorCode) {
		this.applyDoctorCode = applyDoctorCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getUnionApplyJoinState() {
		return unionApplyJoinState;
	}

	public void setUnionApplyJoinState(Integer unionApplyJoinState) {
		this.unionApplyJoinState = unionApplyJoinState;
	}
}
