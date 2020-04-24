package entity.patientInfo;

import java.util.Date;


/**
 * 患者信息
 * 
 * @author JiaQ
 */
public class ProvideViewSysUserPatientInfoAndRegion implements java.io.Serializable {
	
	private Integer patientId;//用户ID
	private String patientCode;//用户编码
	private Integer userUseType;//用户标识.5:医生;6:患者;
	private String linkPhone;//登录手机号
	private String userAccount;//登录账号(未设置登录账号时，值与登录手机号一致)
	private String email;//邮箱
	private String userLabelName;//患者标签名称
	private String userName;//姓名
	private String userNameAlias;//姓名别名
	private String userNameSpell;//姓名拼音助记码
	private String qrCode;//二维码
	private String userLogoUrl;//用户头像
	private String idNumber;//身份证号
	private String nativePlace;//籍贯
	private Integer gender;//性别.0:未知;1:男;2:女;
	private Date birthday;//生日
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String provinceName;//用户所在省份名称
	private String city;//用户所在城市
	private String cityName;//用户所在城市名称
	private String area;//用户所在区(县)
	private String areaName;//用户所在区(县)名称
	private String address;//地址
	private String nation;//民族
	private Integer flagPatientStatus;//患者认证状态(0:未认证;1:已认证;)
	private Date newLoginDate;//最后一次登录日期

	private	String loginDoctorPosition;
	private	String operDoctorCode;
	private	String operDoctorName;
	private	String searchPatientCode;


	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
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

	public String getUserLabelName() {
		return userLabelName;
	}

	public void setUserLabelName(String userLabelName) {
		this.userLabelName = userLabelName;
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

	public Integer getFlagPatientStatus() {
		return flagPatientStatus;
	}

	public void setFlagPatientStatus(Integer flagPatientStatus) {
		this.flagPatientStatus = flagPatientStatus;
	}

	public Date getNewLoginDate() {
		return newLoginDate;
	}

	public void setNewLoginDate(Date newLoginDate) {
		this.newLoginDate = newLoginDate;
	}

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
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

	public String getSearchPatientCode() {
		return searchPatientCode;
	}

	public void setSearchPatientCode(String searchPatientCode) {
		this.searchPatientCode = searchPatientCode;
	}
}
