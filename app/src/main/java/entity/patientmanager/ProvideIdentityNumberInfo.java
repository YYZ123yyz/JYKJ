package entity.patientmanager;


/**
 * 身份证数据对象
 * 
 * @author JiaQ
 */
public class ProvideIdentityNumberInfo implements java.io.Serializable {
	/* 身份证-正面信息 */
	private String userName;//姓名
	private String nation;//民族
	private String address;//住址
	private String idNumber;//公民身份号码
	private String birthday;//出生
	private String gender;//性别
	
	/* 身份证-正面信息 */
	private String issuingOffice;//签发机关
	private String issuingData;//签发日期
	private String failureDate;//失效日期

	/* 获取参数*/
	private String loginDoctorPosition;
	private String operUserCode;
	private String operUserName;
	private String flagIdCardSide;
	private String scanIdNumberImg;


	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public String getOperUserCode() {
		return operUserCode;
	}

	public void setOperUserCode(String operUserCode) {
		this.operUserCode = operUserCode;
	}

	public String getOperUserName() {
		return operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}

	public String getFlagIdCardSide() {
		return flagIdCardSide;
	}

	public void setFlagIdCardSide(String flagIdCardSide) {
		this.flagIdCardSide = flagIdCardSide;
	}

	public String getScanIdNumberImg() {
		return scanIdNumberImg;
	}

	public void setScanIdNumberImg(String scanIdNumberImg) {
		this.scanIdNumberImg = scanIdNumberImg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIssuingOffice() {
		return issuingOffice;
	}

	public void setIssuingOffice(String issuingOffice) {
		this.issuingOffice = issuingOffice;
	}

	public String getIssuingData() {
		return issuingData;
	}

	public void setIssuingData(String issuingData) {
		this.issuingData = issuingData;
	}

	public String getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(String failureDate) {
		this.failureDate = failureDate;
	}
}
