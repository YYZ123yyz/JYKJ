package entity.yhhd;


/**
 * ******************************** 非对称实体类
 * 【医患互动】
 * [医生联盟]下[归属的人员]信息
 * 
 * @author JiaQ
 */
public class ProvideDoctorGoodFriendInfo implements java.io.Serializable {
	private Integer ddBindingId;//医生好友绑定Id
	private String doctorCode;//医生编码(IM注册所需的账号)
	private String doctorUserName;//医生姓名
	private String doctorUserNameAlias;//医生姓名(别名)
	private String doctorQrCode;//医生二维码(IM注册所需的密码)
	private String doctorUserLogoUrl;//头像地址
	private Integer doctorGender;//性别(0:未知;1:男;2:女;)
	private String doctorHospitalInfo;//医生医院信息(医院名称+科室名称+二级科室名称)
	private String doctorTitleName;//医生描述.Eg.@主任医师
	private String doctorNewLoginDate;//最后一次登录日期

	public Integer getDdBindingId() {
		return ddBindingId;
	}

	public void setDdBindingId(Integer ddBindingId) {
		this.ddBindingId = ddBindingId;
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

	public String getDoctorUserNameAlias() {
		return doctorUserNameAlias;
	}

	public void setDoctorUserNameAlias(String doctorUserNameAlias) {
		this.doctorUserNameAlias = doctorUserNameAlias;
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

	public Integer getDoctorGender() {
		return doctorGender;
	}

	public void setDoctorGender(Integer doctorGender) {
		this.doctorGender = doctorGender;
	}

	public String getDoctorHospitalInfo() {
		return doctorHospitalInfo;
	}

	public void setDoctorHospitalInfo(String doctorHospitalInfo) {
		this.doctorHospitalInfo = doctorHospitalInfo;
	}

	public String getDoctorTitleName() {
		return doctorTitleName;
	}

	public void setDoctorTitleName(String doctorTitleName) {
		this.doctorTitleName = doctorTitleName;
	}

	public String getDoctorNewLoginDate() {
		return doctorNewLoginDate;
	}

	public void setDoctorNewLoginDate(String doctorNewLoginDate) {
		this.doctorNewLoginDate = doctorNewLoginDate;
	}
}
