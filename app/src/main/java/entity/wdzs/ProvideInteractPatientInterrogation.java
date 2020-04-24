package entity.wdzs;

import java.util.Date;


/**
 * 【我的诊所】
 * 患者就诊-(问诊)模板
 * 
 * @author JiaQ
 */

public class ProvideInteractPatientInterrogation implements java.io.Serializable {
	private Integer interrogationId;//问诊模板编号
	private String orderCode;//订单关联编码
	private Integer treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private String imgCode;//图片编码
	private String doctorCode;//医生关联编码
	private String doctorName;//医生姓名
	private String patientCode;//(问诊人)患者关联编码
	private String patientName;//(问诊人)患者姓名
	private String patientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)
	private Integer gender;//性别.0:未知;1:男;2:女;
	private String birthday;//年龄(或者存储出生日期)
	private Date bloodPressureAbnormalDate;//最早发现血压异常日期
	private String htnHistory;//高血压病史[操作方式:文本]
	private Integer flagFamilyHtn;//家族内是否有其他高血压患者(直系亲属).1:是;0:否;[操作方式:单选按钮]
	private Integer highPressureNum;//收缩压(高压mmHg).Eg.0为未填写
	private Integer lowPressureNum;//舒张压(低压mmHg).Eg.0为未填写
	private Integer heartRateNum;//心率(次/分钟).Eg.0为未填写
	private Integer measureInstrument;//测量仪器
	private String measureInstrumentName;//测量仪器名称
	private Integer measureMode;//测量方式
	private String measureModeName;//测量方式名称
	private String stateOfIllness;//病情自述
	private Integer flagUseState;//使用状态.0:未使用;1:使用中
	
	
	private String treatmentTypeName;//就诊(治疗)类型展示内容

	private String loginDoctorPosition;
	private String operDoctorCode;
	private String operDoctorName;


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

	public Integer getInterrogationId() {
		return interrogationId;
	}

	public void setInterrogationId(Integer interrogationId) {
		this.interrogationId = interrogationId;
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

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Date getBloodPressureAbnormalDate() {
		return bloodPressureAbnormalDate;
	}

	public void setBloodPressureAbnormalDate(Date bloodPressureAbnormalDate) {
		this.bloodPressureAbnormalDate = bloodPressureAbnormalDate;
	}

	public String getHtnHistory() {
		return htnHistory;
	}

	public void setHtnHistory(String htnHistory) {
		this.htnHistory = htnHistory;
	}

	public Integer getFlagFamilyHtn() {
		return flagFamilyHtn;
	}

	public void setFlagFamilyHtn(Integer flagFamilyHtn) {
		this.flagFamilyHtn = flagFamilyHtn;
	}

	public Integer getHighPressureNum() {
		return highPressureNum;
	}

	public void setHighPressureNum(Integer highPressureNum) {
		this.highPressureNum = highPressureNum;
	}

	public Integer getLowPressureNum() {
		return lowPressureNum;
	}

	public void setLowPressureNum(Integer lowPressureNum) {
		this.lowPressureNum = lowPressureNum;
	}

	public Integer getHeartRateNum() {
		return heartRateNum;
	}

	public void setHeartRateNum(Integer heartRateNum) {
		this.heartRateNum = heartRateNum;
	}

	public Integer getMeasureInstrument() {
		return measureInstrument;
	}

	public void setMeasureInstrument(Integer measureInstrument) {
		this.measureInstrument = measureInstrument;
	}

	public String getMeasureInstrumentName() {
		return measureInstrumentName;
	}

	public void setMeasureInstrumentName(String measureInstrumentName) {
		this.measureInstrumentName = measureInstrumentName;
	}

	public Integer getMeasureMode() {
		return measureMode;
	}

	public void setMeasureMode(Integer measureMode) {
		this.measureMode = measureMode;
	}

	public String getMeasureModeName() {
		return measureModeName;
	}

	public void setMeasureModeName(String measureModeName) {
		this.measureModeName = measureModeName;
	}

	public String getStateOfIllness() {
		return stateOfIllness;
	}

	public void setStateOfIllness(String stateOfIllness) {
		this.stateOfIllness = stateOfIllness;
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
}
