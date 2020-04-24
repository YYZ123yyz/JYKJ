package entity.unionInfo;

import java.util.Date;


/**
 * 医生详细信息
 * 
 * @author JiaQ
 */

public class ProvideViewSysUserDoctorInfoAndHospital  implements java.io.Serializable {
	private Integer doctorId;//医生ID
	private String doctorCode;//医生编码
	private Integer userUseType;//用户标识.5:医生;6:患者;
	private String linkPhone;//登录手机号
	private String userAccount;//登录账号(未设置登录账号时，值与登录手机号一致)
	private String email;//邮箱
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String userNameSpell;//姓名拼音助记码
	private String qrCode;//二维码
	private String userLogoUrl;//用户头像
	private String idNumber;//身份证号
	private String nativePlace;//籍贯
	private Integer gender;//性别.0:未知;1:男;2:女;
	private String birthday;//生日
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String provinceName;//用户所在省份名称
	private String city;//用户所在城市
	private String cityName;//用户所在城市名称
	private String area;//用户所在区(县)
	private String areaName;//用户所在区(县)名称
	private String address;//地址
	private String nation;//民族
	
	private String hospitalInfoCode;//医院编码
	private String hospitalInfoName;//医院名称
	private String departmentId;//一级科室编码
	private String departmentName;//一级科室名称
	private String departmentSecondId;//二级科室编码
	private String departmentSecondName;//二级科室名称
	private Integer doctorTitle;//医生职称编码
	private String doctorTitleName;//医生职称名称
	private String synopsis;
	private String goodAtRealm;
	private Integer flagDoctorStatus;
	private Date newLoginDate;//最近登录日期


	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public Integer getUserUseType() {
		return userUseType;
	}

	public void setUserUseType(Integer userUseType) {
		this.userUseType = userUseType;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUserNameSpell() {
		return userNameSpell;
	}

	public void setUserNameSpell(String userNameSpell) {
		this.userNameSpell = userNameSpell;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
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
}
