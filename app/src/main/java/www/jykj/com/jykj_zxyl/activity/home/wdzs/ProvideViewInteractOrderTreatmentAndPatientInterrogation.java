package www.jykj.com.jykj_zxyl.activity.home.wdzs;

import java.util.Date;


/**
 * 【医患互动】
 * 患者就诊过程中所需重要数据
 * 
 * @author JiaQ
 */
public class ProvideViewInteractOrderTreatmentAndPatientInterrogation implements java.io.Serializable {
	private Integer treatmentId;//就诊记录信息编号
	private String orderCode;//订单关联编码
	private String imgCode;//图片编码
	private Integer sourceTypeId;//来源(数据)类型.1:平台内就诊记录;2:平台外就诊记录;
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private Integer flagTreatmentState;//接诊状态标识
	private String patientCode;//患者编码
	private String patientName;//问诊患者姓名
	private Date treatmentDateStart;//就诊开始日期(图文\\签约就诊需要)
	private Date treatmentDateEnd;//就诊结束日期(图文\\签约就诊需要)
	private Date treatmentDate;//预约就诊日期(电话\\音频\\视频就诊需要)
	private String treatmentTimeSlot;//预约就诊时间段(电话\\音频\\视频就诊需要)
	private String treatmentLinkPhone;//就诊联系电话(电话就诊需要)
	private String doctorCode;//就诊医生编码
	private String doctorName;//就诊医生姓名
	private Date doctorReceiveDate;//医生接诊日期
	private String doctorReceiveTimeSlot;//医生接诊时间段(电话\\音频\\视频就诊需要)
	private Integer limitImgText;//图文就诊(限制)-医生解答剩余数量
	private Integer limitPhone;//电话就诊(限制)-剩余通话时间
	private Integer limitAudio;//音频就诊(限制)-剩余通话时间
	private Integer limitVideo;//视频就诊(限制)-剩余通话时间
	private Integer limitSigning;//签约服务(限制)-签约天数
	private Date limitSigningExpireDate;//签约服务(限制)-到期日期
	
	private String interrogationPatientCode;//(问诊人)患者关联编码.外键:sys_user_patient_info
	private String interrogationPatientName;//(问诊人)[冗余]患者姓名
	private String interrogationPatientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)
	private Integer interrogationGender;//性别.0:未知;1:男;2:女;
	private String interrogationBirthday;//年龄(或者存储出生日期)
	
	private String sourceTypeIdName;//展示使用：来源(数据)类型.1:平台内就诊记录;2:平台外就诊记录;
	private String treatmentTypeName;//展示使用：就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private String flagTreatmentStateName;//展示使用：接诊状态标识.0:未支付(等待支付状态);1:已支付(可开始接诊);2:接诊中(医生开始回复或操作);3:接诊结束(等待回复诊后留言);11:接诊完成(诊后留言已回复);	
	private String treatmentTimeSlotName;//展示使用：预约就诊时间段(电话\\音频\\视频就诊需要).0:未知;1:早;2:中;3:晚;
	private String doctorReceiveTimeSlotName;//展示使用：医生接诊时间段(电话\\音频\\视频就诊需要).0:未知;1:早;2:中;3:晚;

	private String rowNum;
	private String pageNum;
	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;

	public Integer getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(Integer treatmentId) {
		this.treatmentId = treatmentId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public Integer getTreatmentType() {
		return treatmentType;
	}



	public void setTreatmentType(Integer treatmentType) {
		this.treatmentType = treatmentType;
	}

	public Integer getFlagTreatmentState() {
		return flagTreatmentState;
	}

	public void setFlagTreatmentState(Integer flagTreatmentState) {
		this.flagTreatmentState = flagTreatmentState;
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

	public Date getTreatmentDateStart() {
		return treatmentDateStart;
	}

	public void setTreatmentDateStart(Date treatmentDateStart) {
		this.treatmentDateStart = treatmentDateStart;
	}

	public Date getTreatmentDateEnd() {
		return treatmentDateEnd;
	}

	public void setTreatmentDateEnd(Date treatmentDateEnd) {
		this.treatmentDateEnd = treatmentDateEnd;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getTreatmentTimeSlot() {
		return treatmentTimeSlot;
	}

	public void setTreatmentTimeSlot(String treatmentTimeSlot) {
		this.treatmentTimeSlot = treatmentTimeSlot;
	}

	public String getTreatmentLinkPhone() {
		return treatmentLinkPhone;
	}

	public void setTreatmentLinkPhone(String treatmentLinkPhone) {
		this.treatmentLinkPhone = treatmentLinkPhone;
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

	public Date getDoctorReceiveDate() {
		return doctorReceiveDate;
	}

	public void setDoctorReceiveDate(Date doctorReceiveDate) {
		this.doctorReceiveDate = doctorReceiveDate;
	}

	public String getDoctorReceiveTimeSlot() {
		return doctorReceiveTimeSlot;
	}

	public void setDoctorReceiveTimeSlot(String doctorReceiveTimeSlot) {
		this.doctorReceiveTimeSlot = doctorReceiveTimeSlot;
	}

	public Integer getLimitImgText() {
		return limitImgText;
	}

	public void setLimitImgText(Integer limitImgText) {
		this.limitImgText = limitImgText;
	}

	public Integer getLimitPhone() {
		return limitPhone;
	}

	public void setLimitPhone(Integer limitPhone) {
		this.limitPhone = limitPhone;
	}

	public Integer getLimitAudio() {
		return limitAudio;
	}

	public void setLimitAudio(Integer limitAudio) {
		this.limitAudio = limitAudio;
	}

	public Integer getLimitVideo() {
		return limitVideo;
	}

	public void setLimitVideo(Integer limitVideo) {
		this.limitVideo = limitVideo;
	}

	public Integer getLimitSigning() {
		return limitSigning;
	}

	public void setLimitSigning(Integer limitSigning) {
		this.limitSigning = limitSigning;
	}

	public Date getLimitSigningExpireDate() {
		return limitSigningExpireDate;
	}

	public void setLimitSigningExpireDate(Date limitSigningExpireDate) {
		this.limitSigningExpireDate = limitSigningExpireDate;
	}

	public String getInterrogationPatientCode() {
		return interrogationPatientCode;
	}

	public void setInterrogationPatientCode(String interrogationPatientCode) {
		this.interrogationPatientCode = interrogationPatientCode;
	}

	public String getInterrogationPatientName() {
		return interrogationPatientName;
	}

	public void setInterrogationPatientName(String interrogationPatientName) {
		this.interrogationPatientName = interrogationPatientName;
	}

	public String getInterrogationPatientLinkPhone() {
		return interrogationPatientLinkPhone;
	}

	public void setInterrogationPatientLinkPhone(String interrogationPatientLinkPhone) {
		this.interrogationPatientLinkPhone = interrogationPatientLinkPhone;
	}

	public Integer getInterrogationGender() {
		return interrogationGender;
	}

	public void setInterrogationGender(Integer interrogationGender) {
		this.interrogationGender = interrogationGender;
	}

	public String getInterrogationBirthday() {
		return interrogationBirthday;
	}

	public void setInterrogationBirthday(String interrogationBirthday) {
		this.interrogationBirthday = interrogationBirthday;
	}

	public String getSourceTypeIdName() {
		return sourceTypeIdName;
	}

	public void setSourceTypeIdName(String sourceTypeIdName) {
		this.sourceTypeIdName = sourceTypeIdName;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getFlagTreatmentStateName() {
		return flagTreatmentStateName;
	}

	public void setFlagTreatmentStateName(String flagTreatmentStateName) {
		this.flagTreatmentStateName = flagTreatmentStateName;
	}

	public String getTreatmentTimeSlotName() {
		return treatmentTimeSlotName;
	}

	public void setTreatmentTimeSlotName(String treatmentTimeSlotName) {
		this.treatmentTimeSlotName = treatmentTimeSlotName;
	}

	public String getDoctorReceiveTimeSlotName() {
		return doctorReceiveTimeSlotName;
	}

	public void setDoctorReceiveTimeSlotName(String doctorReceiveTimeSlotName) {
		this.doctorReceiveTimeSlotName = doctorReceiveTimeSlotName;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
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
