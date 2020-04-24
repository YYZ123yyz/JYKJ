package entity.wdzs;


/**
 * 【我的诊所】【医患互动】
 * 就诊详情-各项记录填写状态
 * 
 * @author JiaQ
 */

public class ProvideInteractClinicRecordWriteState implements java.io.Serializable {
	
	private Integer totalityState;//整体状态.0:未完成;1:已完成;
	
	private Integer messageState;//诊后留言回复.0:问诊人未提交;1:问诊人已提交;2:医生已回复;
	private Integer diagState;//诊断描述状态.0:未填写;1:已填写;
	private Integer treatmentState;//就诊小结状态.0:未填写;1:已填写;
	private Integer prescribeState;//开具处方.0:未填写;1:已填写;
	private Integer medicalState;//就诊记录.0:未填写;1:已填写;

	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;
	private String orderCode;

	public Integer getTotalityState() {
		return totalityState;
	}

	public void setTotalityState(Integer totalityState) {
		this.totalityState = totalityState;
	}

	public Integer getMessageState() {
		return messageState;
	}

	public void setMessageState(Integer messageState) {
		this.messageState = messageState;
	}

	public Integer getDiagState() {
		return diagState;
	}

	public void setDiagState(Integer diagState) {
		this.diagState = diagState;
	}

	public Integer getTreatmentState() {
		return treatmentState;
	}

	public void setTreatmentState(Integer treatmentState) {
		this.treatmentState = treatmentState;
	}

	public Integer getPrescribeState() {
		return prescribeState;
	}

	public void setPrescribeState(Integer prescribeState) {
		this.prescribeState = prescribeState;
	}

	public Integer getMedicalState() {
		return medicalState;
	}

	public void setMedicalState(Integer medicalState) {
		this.medicalState = medicalState;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
