package entity.patientInfo;

import java.util.Date;



/**
 * 患者个人状况-血压记录
 * 
 * @author JiaQ
 */

public class ProvidePatientConditionBloodPressureRecord implements java.io.Serializable {

	private Integer bloodPressureId;//血压记录信息编号
	private String patientCode;//患者编码
	private Date recordDate;//记录日期
	private Date recordTime;//记录时间
	private Integer recordType;//记录类型.0:未分类;1:手动录入;2:设备采集;
	private Integer recordTimeType;//记录时间类型.0:未分类;1:晨间;2:午间;3:夜间;
	private Integer highPressureNum;//收缩压(高压mmHg)
	private Integer lowPressureNum;//舒张压(低压mmHg)
	private Integer heartRateNum;//心率(次/分钟)
	private Integer bloodType;//血压类型.
	private String bloodTypeName;//血压类型名称
	private Integer bloodTypeSecond;//血压评测类型编码(血压类型小类)
	private String bloodTypeSecondName;//血压评测类型名称(血压类型小类名称)

	public Integer getBloodPressureId() {
		return bloodPressureId;
	}

	public void setBloodPressureId(Integer bloodPressureId) {
		this.bloodPressureId = bloodPressureId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public Integer getRecordTimeType() {
		return recordTimeType;
	}

	public void setRecordTimeType(Integer recordTimeType) {
		this.recordTimeType = recordTimeType;
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

	public Integer getBloodType() {
		return bloodType;
	}

	public void setBloodType(Integer bloodType) {
		this.bloodType = bloodType;
	}

	public String getBloodTypeName() {
		return bloodTypeName;
	}

	public void setBloodTypeName(String bloodTypeName) {
		this.bloodTypeName = bloodTypeName;
	}

	public Integer getBloodTypeSecond() {
		return bloodTypeSecond;
	}

	public void setBloodTypeSecond(Integer bloodTypeSecond) {
		this.bloodTypeSecond = bloodTypeSecond;
	}

	public String getBloodTypeSecondName() {
		return bloodTypeSecondName;
	}

	public void setBloodTypeSecondName(String bloodTypeSecondName) {
		this.bloodTypeSecondName = bloodTypeSecondName;
	}
}
