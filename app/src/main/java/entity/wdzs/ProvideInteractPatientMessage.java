package entity.wdzs;

import java.util.Date;


/**
 * 【我的诊所】【医患互动】
 * 医患互动-患者就诊-诊后留言
 * 
 * @author JiaQ
 */
public class ProvideInteractPatientMessage implements java.io.Serializable {

	private Integer messageId;//问诊模板编号
	private String orderCode;//订单关联编码
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private String imgCode;//图片编码
	  
	private String patientCode;//(问诊人)患者关联编码
	private String patientName;//(问诊人)[冗余]患者姓名
	private String patientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)  
	private String messageContent;//患者留言内容
	private Date messageDate;//留言日期

	private String doctorCode;//医生关联编码.外键:sys_user_doctor_info
	private String doctorName;//医生姓名
	private Integer flagReplyState;//回复状态.0:待处理;1:未回复;2:已过期;3:已回复;  
	private String replyContent;//回复内容
	private Date replyDate;//回复日期
	private Integer flagUseState;//使用状态.0:未使用;1:使用中;

	private String treatmentTypeName;//[展示使用]就诊(治疗)类型名称

	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(Integer treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientLinkPhone() {
		return patientLinkPhone;
	}

	public void setPatientLinkPhone(String patientLinkPhone) {
		this.patientLinkPhone = patientLinkPhone;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Integer getFlagReplyState() {
		return flagReplyState;
	}

	public void setFlagReplyState(Integer flagReplyState) {
		this.flagReplyState = flagReplyState;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public Integer getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(Integer flagUseState) {
		this.flagUseState = flagUseState;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
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
}
