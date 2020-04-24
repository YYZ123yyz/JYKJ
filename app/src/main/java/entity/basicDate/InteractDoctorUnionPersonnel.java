package entity.basicDate;


/**
 * 【医患互动】
 * [医生联盟]下[归属的人员]信息
 * 
 * @author JiaQ
 */

public class InteractDoctorUnionPersonnel implements java.io.Serializable {
	private Integer doctorId;//医生ID
	private String doctorCode;//医生编码
	private String doctorUserLogoUrl;//头像地址
	private String doctorTitleDesc;//医生描述（标头）。Eg.@省三院
	private String doctorUserName;//医生姓名
	private Integer doctorGender;//性别.0:未知;1:男;2:女;
	private String doctorNewLoginDate;//最后一次登录日期

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

	public String getDoctorUserLogoUrl() {
		return doctorUserLogoUrl;
	}

	public void setDoctorUserLogoUrl(String doctorUserLogoUrl) {
		this.doctorUserLogoUrl = doctorUserLogoUrl;
	}

	public String getDoctorTitleDesc() {
		return doctorTitleDesc;
	}

	public void setDoctorTitleDesc(String doctorTitleDesc) {
		this.doctorTitleDesc = doctorTitleDesc;
	}

	public String getDoctorUserName() {
		return doctorUserName;
	}

	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}

	public Integer getDoctorGender() {
		return doctorGender;
	}

	public void setDoctorGender(Integer doctorGender) {
		this.doctorGender = doctorGender;
	}

	public String getDoctorNewLoginDate() {
		return doctorNewLoginDate;
	}

	public void setDoctorNewLoginDate(String doctorNewLoginDate) {
		this.doctorNewLoginDate = doctorNewLoginDate;
	}
}
