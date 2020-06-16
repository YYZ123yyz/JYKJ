package entity.patientInfo;


import java.util.Collection;

/**
 * 患者个人状况-血压记录(血压记录统计数据)
 * 
 * @author JiaQ
 */

public class ProvidePatientConditionBloodPressureGroup implements java.io.Serializable {

	private String groupRecordDate;//血压记录日期

	/* 全天均压 */
	private String dayAvgHighPressureNum;//【全天】【平均值】收缩压(高压mmHg)
	private String dayAvgLowPressureNum;//【全天】【平均值】舒张压(低压mmHg)
	private String dayAvgHeartRateNum;//【全天】【平均值】心率(次/分钟)
	private Integer dayBloodTypeSecond;//【全天】血压类型小类
	private String dayBloodTypeSecondName;//【全天】血压类型小类名称

	/* 晨间均压 */
	private String morningAvgHighPressureNum;//【晨间】【平均值】收缩压(高压mmHg)
	private String morningAvgLowPressureNum;//【晨间】【平均值】舒张压(低压mmHg)
	private String morningAvgHeartRateNum;//【晨间】【平均值】心率(次/分钟)
	private Integer morningBloodTypeSecond;//【晨间】血压类型小类
	private String morningBloodTypeSecondName;//【晨间】血压类型小类名称

	/* 夜间均压 */
	private String nightAvgHighPressureNum;//【夜间】【平均值】收缩压(高压mmHg)
	private String nightAvgLowPressureNum;//【夜间】【平均值】舒张压(低压mmHg)
	private String nightAvgHeartRateNum;//【夜间】【平均值】心率(次/分钟)
	private Integer nightBloodTypeSecond;//【夜间】血压类型小类
	private String nightBloodTypeSecondName;//【夜间】血压类型小类名称

	/**
	 * 【新版】根据【患者编码】获取【患者打卡记录】【血压记录】【周期数据】
	 */
	private String[] groupRecordDateArray;//血压记录日期
	private String[] dayAvgHighPressureNumArray;//【全天】【平均值】收缩压(高压mmHg)
	private String[] dayAvgLowPressureNumArray;//【全天】【平均值】舒张压(低压mmHg)
	private String[] dayAvgHeartRateNumArray;//【全天】【平均值】心率(次/分钟)

	public String getGroupRecordDate() {
		return groupRecordDate;
	}

	public void setGroupRecordDate(String groupRecordDate) {
		this.groupRecordDate = groupRecordDate;
	}

	public String getDayAvgHighPressureNum() {
		return dayAvgHighPressureNum;
	}

	public void setDayAvgHighPressureNum(String dayAvgHighPressureNum) {
		this.dayAvgHighPressureNum = dayAvgHighPressureNum;
	}

	public String getDayAvgLowPressureNum() {
		return dayAvgLowPressureNum;
	}

	public void setDayAvgLowPressureNum(String dayAvgLowPressureNum) {
		this.dayAvgLowPressureNum = dayAvgLowPressureNum;
	}

	public String getDayAvgHeartRateNum() {
		return dayAvgHeartRateNum;
	}

	public void setDayAvgHeartRateNum(String dayAvgHeartRateNum) {
		this.dayAvgHeartRateNum = dayAvgHeartRateNum;
	}

	public Integer getDayBloodTypeSecond() {
		return dayBloodTypeSecond;
	}

	public void setDayBloodTypeSecond(Integer dayBloodTypeSecond) {
		this.dayBloodTypeSecond = dayBloodTypeSecond;
	}

	public String getDayBloodTypeSecondName() {
		return dayBloodTypeSecondName;
	}

	public void setDayBloodTypeSecondName(String dayBloodTypeSecondName) {
		this.dayBloodTypeSecondName = dayBloodTypeSecondName;
	}

	public String getMorningAvgHighPressureNum() {
		return morningAvgHighPressureNum;
	}

	public void setMorningAvgHighPressureNum(String morningAvgHighPressureNum) {
		this.morningAvgHighPressureNum = morningAvgHighPressureNum;
	}

	public String getMorningAvgLowPressureNum() {
		return morningAvgLowPressureNum;
	}

	public void setMorningAvgLowPressureNum(String morningAvgLowPressureNum) {
		this.morningAvgLowPressureNum = morningAvgLowPressureNum;
	}

	public String getMorningAvgHeartRateNum() {
		return morningAvgHeartRateNum;
	}

	public void setMorningAvgHeartRateNum(String morningAvgHeartRateNum) {
		this.morningAvgHeartRateNum = morningAvgHeartRateNum;
	}

	public Integer getMorningBloodTypeSecond() {
		return morningBloodTypeSecond;
	}

	public void setMorningBloodTypeSecond(Integer morningBloodTypeSecond) {
		this.morningBloodTypeSecond = morningBloodTypeSecond;
	}

	public String getMorningBloodTypeSecondName() {
		return morningBloodTypeSecondName;
	}

	public void setMorningBloodTypeSecondName(String morningBloodTypeSecondName) {
		this.morningBloodTypeSecondName = morningBloodTypeSecondName;
	}

	public String getNightAvgHighPressureNum() {
		return nightAvgHighPressureNum;
	}

	public void setNightAvgHighPressureNum(String nightAvgHighPressureNum) {
		this.nightAvgHighPressureNum = nightAvgHighPressureNum;
	}

	public String getNightAvgLowPressureNum() {
		return nightAvgLowPressureNum;
	}

	public void setNightAvgLowPressureNum(String nightAvgLowPressureNum) {
		this.nightAvgLowPressureNum = nightAvgLowPressureNum;
	}

	public String getNightAvgHeartRateNum() {
		return nightAvgHeartRateNum;
	}

	public void setNightAvgHeartRateNum(String nightAvgHeartRateNum) {
		this.nightAvgHeartRateNum = nightAvgHeartRateNum;
	}

	public Integer getNightBloodTypeSecond() {
		return nightBloodTypeSecond;
	}

	public void setNightBloodTypeSecond(Integer nightBloodTypeSecond) {
		this.nightBloodTypeSecond = nightBloodTypeSecond;
	}

	public String getNightBloodTypeSecondName() {
		return nightBloodTypeSecondName;
	}

	public void setNightBloodTypeSecondName(String nightBloodTypeSecondName) {
		this.nightBloodTypeSecondName = nightBloodTypeSecondName;
	}

	public String[] getGroupRecordDateArray() {
		return groupRecordDateArray;
	}

	public void setGroupRecordDateArray(String[] groupRecordDateArray) {
		this.groupRecordDateArray = groupRecordDateArray;
	}

	public String[] getDayAvgHighPressureNumArray() {
		return dayAvgHighPressureNumArray;
	}

	public void setDayAvgHighPressureNumArray(String[] dayAvgHighPressureNumArray) {
		this.dayAvgHighPressureNumArray = dayAvgHighPressureNumArray;
	}

	public String[] getDayAvgLowPressureNumArray() {
		return dayAvgLowPressureNumArray;
	}

	public void setDayAvgLowPressureNumArray(String[] dayAvgLowPressureNumArray) {
		this.dayAvgLowPressureNumArray = dayAvgLowPressureNumArray;
	}


	public void setDayAvgHeartRateNumArray(String[] dayAvgHeartRateNumArray) {
		this.dayAvgHeartRateNumArray = dayAvgHeartRateNumArray;
	}
}
