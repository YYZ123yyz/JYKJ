package entity.DoctorInfo;


/**
 * 【医患互动】
 * 患者详细信息
 * 
 * @author JiaQ
 */
public class InteractPatientDetailInfo implements java.io.Serializable {
	private Integer patientId;//患者ID
	private String patientCode;//患者编码
	private Integer patientDiagnosisType;//患者诊断类型
	private String patientUserLabelName;//患者标签。Eg.高血压Ⅰ期
	private String patientUserName;//患者姓名
	private String patientQrCode;//二维码
	private String patientUserLogoUrl;//头像地址
	private Integer patientGender;//性别.0:未知;1:男;2:女;
	private String nativePlace;//籍贯
	private Integer age;//年龄
	private String country;//国家
	private String province;//省份
	private String city;//城市
	private String nation;//民族
	private String patientNewLoginDate;//最后一次登录日期

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

	public Integer getPatientDiagnosisType() {
		return patientDiagnosisType;
	}

	public void setPatientDiagnosisType(Integer patientDiagnosisType) {
		this.patientDiagnosisType = patientDiagnosisType;
	}

	public String getPatientUserLabelName() {
		return patientUserLabelName;
	}

	public void setPatientUserLabelName(String patientUserLabelName) {
		this.patientUserLabelName = patientUserLabelName;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public String getPatientQrCode() {
		return patientQrCode;
	}

	public void setPatientQrCode(String patientQrCode) {
		this.patientQrCode = patientQrCode;
	}

	public String getPatientUserLogoUrl() {
		return patientUserLogoUrl;
	}

	public void setPatientUserLogoUrl(String patientUserLogoUrl) {
		this.patientUserLogoUrl = patientUserLogoUrl;
	}

	public Integer getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(Integer patientGender) {
		this.patientGender = patientGender;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
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

	public String getPatientNewLoginDate() {
		return patientNewLoginDate;
	}

	public void setPatientNewLoginDate(String patientNewLoginDate) {
		this.patientNewLoginDate = patientNewLoginDate;
	}
}
