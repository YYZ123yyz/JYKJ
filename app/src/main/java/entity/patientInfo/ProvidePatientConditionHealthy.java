package entity.patientInfo;

import java.util.Date;


/**
 * 患者个人状况-健康信息
 * 
 * @author JiaQ
 */

public class ProvidePatientConditionHealthy implements java.io.Serializable {

	private Integer healthyId;//基本信息编号
	private String patientCode;//用户(患者)基本信息编码.外键:sys_patient_info
	private Integer height;//身高(CM)
	private Integer waistline;//腰围
	private Integer weight;//体重
	private Integer flagSmoking;//是否吸烟.1:是;0:否;
	private Integer flagAlcoholism;//是否酗酒.1:是;0:否;
	private Integer flagStayUpLate;//是否熬夜.1:是;0:否;
	private Date bloodPressureAbnormalDate;//最早发现血压异常日期
	private Date confirmedYear;//确诊年份
	private String onsetSymptoms;//起病症状[操作方式:多选(最多3项)].Eg.1^2^3.
	private String currentSymptoms;//目前症状[操作方式:多选(最多3项)].Eg.1^2^3.
	private String complication;//并发症[操作方式:多选(最多3项)].Eg.1^2^3.
	private String combinedDisease;//合并疾病[操作方式:多选(最多3项)].Eg.1^2^3.
	private String currentTreatmentPlan;//目前治疗方案[操作方式:多选(最多3项)].Eg.1^2^3.
	private Integer flagFamilyHtn;//家族内是否有其他高血压患者(直系亲属).1:是;0:否;[操作方式:单选按钮]
	private String htnHistory;//高血压病史[操作方式:文本]
	private String stateOfIllness;//病情自述
	private Integer flagUseState;//使用状态.0:未使用;1:使用中;
	
	
	
	private Integer flagDel;
	private String remark;
	private Date createDate;
	private String createMan;
	private Date updateDate;
	private String updateMan;

	public Integer getHealthyId() {
		return healthyId;
	}

	public void setHealthyId(Integer healthyId) {
		this.healthyId = healthyId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWaistline() {
		return waistline;
	}

	public void setWaistline(Integer waistline) {
		this.waistline = waistline;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getFlagSmoking() {
		return flagSmoking;
	}

	public void setFlagSmoking(Integer flagSmoking) {
		this.flagSmoking = flagSmoking;
	}

	public Integer getFlagAlcoholism() {
		return flagAlcoholism;
	}

	public void setFlagAlcoholism(Integer flagAlcoholism) {
		this.flagAlcoholism = flagAlcoholism;
	}

	public Integer getFlagStayUpLate() {
		return flagStayUpLate;
	}

	public void setFlagStayUpLate(Integer flagStayUpLate) {
		this.flagStayUpLate = flagStayUpLate;
	}

	public Date getBloodPressureAbnormalDate() {
		return bloodPressureAbnormalDate;
	}

	public void setBloodPressureAbnormalDate(Date bloodPressureAbnormalDate) {
		this.bloodPressureAbnormalDate = bloodPressureAbnormalDate;
	}

	public Date getConfirmedYear() {
		return confirmedYear;
	}

	public void setConfirmedYear(Date confirmedYear) {
		this.confirmedYear = confirmedYear;
	}

	public String getOnsetSymptoms() {
		return onsetSymptoms;
	}

	public void setOnsetSymptoms(String onsetSymptoms) {
		this.onsetSymptoms = onsetSymptoms;
	}

	public String getCurrentSymptoms() {
		return currentSymptoms;
	}

	public void setCurrentSymptoms(String currentSymptoms) {
		this.currentSymptoms = currentSymptoms;
	}

	public String getComplication() {
		return complication;
	}

	public void setComplication(String complication) {
		this.complication = complication;
	}

	public String getCombinedDisease() {
		return combinedDisease;
	}

	public void setCombinedDisease(String combinedDisease) {
		this.combinedDisease = combinedDisease;
	}

	public String getCurrentTreatmentPlan() {
		return currentTreatmentPlan;
	}

	public void setCurrentTreatmentPlan(String currentTreatmentPlan) {
		this.currentTreatmentPlan = currentTreatmentPlan;
	}

	public Integer getFlagFamilyHtn() {
		return flagFamilyHtn;
	}

	public void setFlagFamilyHtn(Integer flagFamilyHtn) {
		this.flagFamilyHtn = flagFamilyHtn;
	}

	public String getHtnHistory() {
		return htnHistory;
	}

	public void setHtnHistory(String htnHistory) {
		this.htnHistory = htnHistory;
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

	public Integer getFlagDel() {
		return flagDel;
	}

	public void setFlagDel(Integer flagDel) {
		this.flagDel = flagDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateMan() {
		return updateMan;
	}

	public void setUpdateMan(String updateMan) {
		this.updateMan = updateMan;
	}
}
