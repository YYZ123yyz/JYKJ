package entity.DoctorInfo;

import java.util.Date;


/**
 * 绑定医生好友的详细信息
 * 
 * @author JiaQ
 */

public class ProvideViewBindingDdGetBindingDoctor implements java.io.Serializable {
	
	private Integer ddBindingId;//好友绑定ID
	private String ddBindingCode;//好友绑定编码
	private String mainDoctorCode;//主用户编码
	private String bindingDoctorCode;//绑定用户编码
	private Integer flagBlacklist;//是否黑名单.1:绑定关系;0:拉入黑名单;
	private Date flagBlacklistDate;//黑名单日期
	
	private String linkPhone;
	private String email;
	private String userName;
	private String userNameAlias;
	private String userNameSpell;
	private String qrCode;
	private String userLogoUrl;
	private Integer gender;
	private Date birthday;
	private String country;
	private String province;
	private String provinceName;
	private String city;
	private String cityName;
	private String area;
	private String areaName;
	private String address;
	private String nation;
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
	private Date newLoginDate;


	private boolean click;

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}

	public Integer getDdBindingId() {
		return ddBindingId;
	}

	public void setDdBindingId(Integer ddBindingId) {
		this.ddBindingId = ddBindingId;
	}

	public String getDdBindingCode() {
		return ddBindingCode;
	}

	public void setDdBindingCode(String ddBindingCode) {
		this.ddBindingCode = ddBindingCode;
	}

	public String getMainDoctorCode() {
		return mainDoctorCode;
	}

	public void setMainDoctorCode(String mainDoctorCode) {
		this.mainDoctorCode = mainDoctorCode;
	}

	public String getBindingDoctorCode() {
		return bindingDoctorCode;
	}

	public void setBindingDoctorCode(String bindingDoctorCode) {
		this.bindingDoctorCode = bindingDoctorCode;
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

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
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
