package www.jykj.com.jykj_zxyl.app_base.base_bean;


/**
 * 【医患互动】
 * 患者就诊-(问诊)模板
 * 
 * @author JiaQ
 */
public class InteractPatientInterrogation implements java.io.Serializable {
	private int interrogationId;//问诊模板编号
	private String orderCode;//订单关联编码(生成唯一订单号.Eg.年月日时分秒+日期的编码)
	private int treatmentType;//就诊(治疗)类型.0:未知;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
	private String imgCode;//图片编码.外键:basics_img(tableImgId)
	private String doctorCode;//[预留]医生关联编码.外键:sys_user_doctor_info
	private String doctorName;//[预留][冗余]医生姓名
	private String patientCode;//(问诊人)患者关联编码.外键:sys_user_patient_info
	private String patientName;//(问诊人)[冗余]患者姓名
	private String patientLinkPhone;//(问诊人)患者手机号(电话就诊时,接听医生来电)
	private int gender;//性别.0:未知;1:男;2:女;
	private String birthday;//年龄(或者存储出生日期)
	private long bloodPressureAbnormalDate;//最早发现血压异常日期
	private int flagHtnHistory;//是否有高血压病史.1:是;0:否;[操作方式:单选按钮]
	private String htnHistory;//高血压病史[操作方式:文本]
	private int flagFamilyHtn;//家族内是否有其他高血压患者(直系亲属).1:是;0:否;[操作方式:单选按钮]
	private int highPressureNum;//收缩压(高压mmHg).Eg.0为未填写
	private int lowPressureNum;//舒张压(低压mmHg).Eg.0为未填写
	private int heartRateNum;//心率(次/分钟).Eg.0为未填写
	private int measureInstrument;//测量仪器.外键:basics_domain(attrCode.10006)
	private String measureInstrumentName;//[冗余]测量仪器名称
	private int measureMode;//测量方式.外键:basics_domain(attrCode.10007)
	private String measureModeName;//[冗余]测量方式名称
	private String stateOfIllness;//病情自述

	private String chiefComplaint;//主诉(本次就诊主要痛苦)
	private String historyNew;//现病史(主要痛苦的发生过程)
	private String historyPast;//既往史(曾经得过的其他疾病)
	private String historyAllergy;//过敏史(是否有其他药物过敏史)

	private int flagUseState;//使用状态.0:未使用;1:使用中
	  
	private int flagDel;
	private String remark;
	private long createDate;
	private String createMan;
	private long updateDate;
	private String updateMan;
	
	/****************** 非对称属性 *****************/
	private String treatmentTypeName;//就诊(治疗)类型展示内容
	private String interrogationImgArray;//问诊资料图片,存储上传的图片路径集合.使用[,]进行数据分隔

	public int getInterrogationId() {
		return interrogationId;
	}

	public void setInterrogationId(int interrogationId) {
		this.interrogationId = interrogationId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public int getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(int treatmentType) {
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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public long getBloodPressureAbnormalDate() {
		return bloodPressureAbnormalDate;
	}

	public void setBloodPressureAbnormalDate(long bloodPressureAbnormalDate) {
		this.bloodPressureAbnormalDate = bloodPressureAbnormalDate;
	}

	public int getFlagHtnHistory() {
		return flagHtnHistory;
	}

	public void setFlagHtnHistory(int flagHtnHistory) {
		this.flagHtnHistory = flagHtnHistory;
	}

	public String getHtnHistory() {
		return htnHistory;
	}

	public void setHtnHistory(String htnHistory) {
		this.htnHistory = htnHistory;
	}

	public int getFlagFamilyHtn() {
		return flagFamilyHtn;
	}

	public void setFlagFamilyHtn(int flagFamilyHtn) {
		this.flagFamilyHtn = flagFamilyHtn;
	}

	public int getHighPressureNum() {
		return highPressureNum;
	}

	public void setHighPressureNum(int highPressureNum) {
		this.highPressureNum = highPressureNum;
	}

	public int getLowPressureNum() {
		return lowPressureNum;
	}

	public void setLowPressureNum(int lowPressureNum) {
		this.lowPressureNum = lowPressureNum;
	}

	public int getHeartRateNum() {
		return heartRateNum;
	}

	public void setHeartRateNum(int heartRateNum) {
		this.heartRateNum = heartRateNum;
	}

	public int getMeasureInstrument() {
		return measureInstrument;
	}

	public void setMeasureInstrument(int measureInstrument) {
		this.measureInstrument = measureInstrument;
	}

	public String getMeasureInstrumentName() {
		return measureInstrumentName;
	}

	public void setMeasureInstrumentName(String measureInstrumentName) {
		this.measureInstrumentName = measureInstrumentName;
	}

	public int getMeasureMode() {
		return measureMode;
	}

	public void setMeasureMode(int measureMode) {
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

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getHistoryNew() {
		return historyNew;
	}

	public void setHistoryNew(String historyNew) {
		this.historyNew = historyNew;
	}

	public String getHistoryPast() {
		return historyPast;
	}

	public void setHistoryPast(String historyPast) {
		this.historyPast = historyPast;
	}

	public String getHistoryAllergy() {
		return historyAllergy;
	}

	public void setHistoryAllergy(String historyAllergy) {
		this.historyAllergy = historyAllergy;
	}

	public int getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(int flagUseState) {
		this.flagUseState = flagUseState;
	}

	public int getFlagDel() {
		return flagDel;
	}

	public void setFlagDel(int flagDel) {
		this.flagDel = flagDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateMan() {
		return updateMan;
	}

	public void setUpdateMan(String updateMan) {
		this.updateMan = updateMan;
	}

	public String getTreatmentTypeName() {
		return treatmentTypeName;
	}

	public void setTreatmentTypeName(String treatmentTypeName) {
		this.treatmentTypeName = treatmentTypeName;
	}

	public String getInterrogationImgArray() {
		return interrogationImgArray;
	}

	public void setInterrogationImgArray(String interrogationImgArray) {
		this.interrogationImgArray = interrogationImgArray;
	}
}
