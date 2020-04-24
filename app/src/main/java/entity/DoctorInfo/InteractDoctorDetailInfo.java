package entity.DoctorInfo;


/**
 * 【医患互动】
 * 医生详细信息
 * 
 * @author JiaQ
 */
public class InteractDoctorDetailInfo implements java.io.Serializable {
	private Integer doctorId;//医生ID
	private String doctorCode;//医生编码
	private String doctorUserName;//姓名
	private String doctorQrCode;//二维码
	private String doctorUserLogoUrl;//头像地址
	private Integer gender;//性别.0:未知;1:男;2:女;
	private Integer age;//年龄
	private String country;//国家
	private String province;//省份
	private String city;//城市
	private String nation;//民族
	private String hospitalInfoName;//医院名称
	private String departmentName;//科室名称
	private String departmentSecondName;//二级科室
	private String doctorTitleName;//职称名称
	private String synopsis;//个人简介
	private String goodAtRealm;//擅长领域
	private Integer flagDoctorStatus;//医生认证状态.0:未认证;1:已认证;
	private String newLoginDateStr;//最后一次登录日期


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

	public String getDoctorUserName() {
		return doctorUserName;
	}

	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}

	public String getDoctorQrCode() {
		return doctorQrCode;
	}

	public void setDoctorQrCode(String doctorQrCode) {
		this.doctorQrCode = doctorQrCode;
	}

	public String getDoctorUserLogoUrl() {
		return doctorUserLogoUrl;
	}

	public void setDoctorUserLogoUrl(String doctorUserLogoUrl) {
		this.doctorUserLogoUrl = doctorUserLogoUrl;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
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

	public String getNewLoginDateStr() {
		return newLoginDateStr;
	}

	public void setNewLoginDateStr(String newLoginDateStr) {
		this.newLoginDateStr = newLoginDateStr;
	}
}
