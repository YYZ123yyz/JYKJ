package entity.DoctorInfo;



/**
 * 【医患互动】
 * 医生管理下的患者信息
 * 
 * @author JiaQ
 */
public class InteractPatient implements java.io.Serializable {
	private Integer patientId;//患者ID
	private String patientCode;//患者编码
	private String patientUserLogoUrl;//头像地址
	private Integer patientDiagnosisType;//患者诊断类型
	private String patientTitleDesc;//患者描述（标头）
	private String patientUserName;//患者姓名
	private Integer patientGender;//性别.0:未知;1:男;2:女;
	private String patientUserLabelName;//患者标签。Eg.高血压Ⅰ期
	private String patientNewLoginDate;//最后一次登录日期

	private	String type;					//类型，message=消息记录   user=用户列表
	private boolean noRead;					//是否有未读消息
	private	String lastMessage;				//最后一条消息

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNoRead() {
		return noRead;
	}

	public void setNoRead(boolean noRead) {
		this.noRead = noRead;
	}

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

	public String getPatientUserLogoUrl() {
		return patientUserLogoUrl;
	}

	public void setPatientUserLogoUrl(String patientUserLogoUrl) {
		this.patientUserLogoUrl = patientUserLogoUrl;
	}

	public Integer getPatientDiagnosisType() {
		return patientDiagnosisType;
	}

	public void setPatientDiagnosisType(Integer patientDiagnosisType) {
		this.patientDiagnosisType = patientDiagnosisType;
	}

	public String getPatientTitleDesc() {
		return patientTitleDesc;
	}

	public void setPatientTitleDesc(String patientTitleDesc) {
		this.patientTitleDesc = patientTitleDesc;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public Integer getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(Integer patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientUserLabelName() {
		return patientUserLabelName;
	}

	public void setPatientUserLabelName(String patientUserLabelName) {
		this.patientUserLabelName = patientUserLabelName;
	}

	public String getPatientNewLoginDate() {
		return patientNewLoginDate;
	}

	public void setPatientNewLoginDate(String patientNewLoginDate) {
		this.patientNewLoginDate = patientNewLoginDate;
	}
}
