package entity.mySelf;


/**
 * 医生设置类-排班
 *
 * @author JiaQ
 */

public class ProvideDoctorSetScheduling implements java.io.Serializable {
	private int schedulingSetId;//就诊类型(服务权限)设置编号
	private String doctorCode;//医生关联编码.外键:sys_user_doctor_info
	private int mondayMorning;//[周一][早]是否坐诊.0:否;1:是;
	private int mondayMorningSourceNum;//[预留][周一][早]剩余号源数量
	private int mondayNoon;//[周一][中]是否坐诊.0:否;1:是;
	private int mondayNoonSourceNum;//[预留][周一][中]剩余号源数量
	private int mondayNight;//[周一][晚]是否坐诊.0:否;1:是;
	private int mondayNightSourceNum;//[预留][周一][晚]剩余号源数量
	private int tuesdayMorning;//[周二][早]是否坐诊.0:否;1:是;
	private int tuesdayMorningSourceNum;//[预留][周二][早]剩余号源数量
	private int tuesdayNoon;//[周二][中]是否坐诊.0:否;1:是;
	private int tuesdayNoonSourceNum;//[预留][周二][中]剩余号源数量
	private int tuesdayNight;//[周二][晚]是否坐诊.0:否;1:是;
	private int tuesdayNightSourceNum;//[预留][周二][晚]剩余号源数量
	private int wednesdayMorning;//[周三][早]是否坐诊.0:否;1:是;
	private int wednesdayMorningSourceNum;//[预留][周三][早]剩余号源数量
	private int wednesdayNoon;//[周三][中]是否坐诊.0:否;1:是;
	private int wednesdayNoonSourceNum;//[预留][周三][中]剩余号源数量
	private int wednesdayNight;//[周三][晚]是否坐诊.0:否;1:是;
	private int wednesdayNightSourceNum;//[预留][周三][晚]剩余号源数量
	private int thursdayMorning;//[周四][早]是否坐诊.0:否;1:是;
	private int thursdayMorningSourceNum;//[预留][周四][早]剩余号源数量
	private int thursdayNoon;//[周四][中]是否坐诊.0:否;1:是;
	private int thursdayNoonSourceNum;//[预留][周四][中]剩余号源数量
	private int thursdayNight;//[周四][晚]是否坐诊.0:否;1:是;
	private int thursdayNightSourceNum;//[预留][周四][晚]剩余号源数量
	private int fridayMorning;//[周五][早]是否坐诊.0:否;1:是;
	private int fridayMorningSourceNum;//[预留][周五][早]剩余号源数量
	private int fridayNoon;//[周五][中]是否坐诊.0:否;1:是;
	private int fridayNoonSourceNum;//[预留][周五][中]剩余号源数量
	private int fridayNight;//[周五][晚]是否坐诊.0:否;1:是;
	private int fridayNightSourceNum;//[预留][周五][晚]剩余号源数量
	private int saturdayMorning;//[周六][早]是否坐诊.0:否;1:是;
	private int saturdayMorningSourceNum;//[预留][周六][早]剩余号源数量
	private int saturdayNoon;//[周六][中]是否坐诊.0:否;1:是;
	private int saturdayNoonSourceNum;//[预留][周六][中]剩余号源数量
	private int saturdayNight;//[周六][晚]是否坐诊.0:否;1:是;
	private int saturdayNightSourceNum;//[预留][周六][晚]剩余号源数量
	private int sundayMorning;//[周日][早]是否坐诊.0:否;1:是;
	private int sundayMorningSourceNum;//[预留][周日][早]剩余号源数量
	private int sundayNoon;//[周日][中]是否坐诊.0:否;1:是;
	private int sundayNoonSourceNum;//[预留][周日][中]剩余号源数量
	private int sundayNight;//[周日][晚]是否坐诊.0:否;1:是;
	private int sundayNightSourceNum;//[预留][周日][晚]剩余号源数量
	private int flagUseState;//使用状态.0:未使用;1:使用中;

	private	String loginDoctorPosition;			//
	private	String operDoctorCode;			//
	private	String operDoctorName;			//

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

	public int getSchedulingSetId() {
		return schedulingSetId;
	}

	public void setSchedulingSetId(int schedulingSetId) {
		this.schedulingSetId = schedulingSetId;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public int getMondayMorning() {
		return mondayMorning;
	}

	public void setMondayMorning(int mondayMorning) {
		this.mondayMorning = mondayMorning;
	}

	public int getMondayMorningSourceNum() {
		return mondayMorningSourceNum;
	}

	public void setMondayMorningSourceNum(int mondayMorningSourceNum) {
		this.mondayMorningSourceNum = mondayMorningSourceNum;
	}

	public int getMondayNoon() {
		return mondayNoon;
	}

	public void setMondayNoon(int mondayNoon) {
		this.mondayNoon = mondayNoon;
	}

	public int getMondayNoonSourceNum() {
		return mondayNoonSourceNum;
	}

	public void setMondayNoonSourceNum(int mondayNoonSourceNum) {
		this.mondayNoonSourceNum = mondayNoonSourceNum;
	}

	public int getMondayNight() {
		return mondayNight;
	}

	public void setMondayNight(int mondayNight) {
		this.mondayNight = mondayNight;
	}

	public int getMondayNightSourceNum() {
		return mondayNightSourceNum;
	}

	public void setMondayNightSourceNum(int mondayNightSourceNum) {
		this.mondayNightSourceNum = mondayNightSourceNum;
	}

	public int getTuesdayMorning() {
		return tuesdayMorning;
	}

	public void setTuesdayMorning(int tuesdayMorning) {
		this.tuesdayMorning = tuesdayMorning;
	}

	public int getTuesdayMorningSourceNum() {
		return tuesdayMorningSourceNum;
	}

	public void setTuesdayMorningSourceNum(int tuesdayMorningSourceNum) {
		this.tuesdayMorningSourceNum = tuesdayMorningSourceNum;
	}

	public int getTuesdayNoon() {
		return tuesdayNoon;
	}

	public void setTuesdayNoon(int tuesdayNoon) {
		this.tuesdayNoon = tuesdayNoon;
	}

	public int getTuesdayNoonSourceNum() {
		return tuesdayNoonSourceNum;
	}

	public void setTuesdayNoonSourceNum(int tuesdayNoonSourceNum) {
		this.tuesdayNoonSourceNum = tuesdayNoonSourceNum;
	}

	public int getTuesdayNight() {
		return tuesdayNight;
	}

	public void setTuesdayNight(int tuesdayNight) {
		this.tuesdayNight = tuesdayNight;
	}

	public int getTuesdayNightSourceNum() {
		return tuesdayNightSourceNum;
	}

	public void setTuesdayNightSourceNum(int tuesdayNightSourceNum) {
		this.tuesdayNightSourceNum = tuesdayNightSourceNum;
	}

	public int getWednesdayMorning() {
		return wednesdayMorning;
	}

	public void setWednesdayMorning(int wednesdayMorning) {
		this.wednesdayMorning = wednesdayMorning;
	}

	public int getWednesdayMorningSourceNum() {
		return wednesdayMorningSourceNum;
	}

	public void setWednesdayMorningSourceNum(int wednesdayMorningSourceNum) {
		this.wednesdayMorningSourceNum = wednesdayMorningSourceNum;
	}

	public int getWednesdayNoon() {
		return wednesdayNoon;
	}

	public void setWednesdayNoon(int wednesdayNoon) {
		this.wednesdayNoon = wednesdayNoon;
	}

	public int getWednesdayNoonSourceNum() {
		return wednesdayNoonSourceNum;
	}

	public void setWednesdayNoonSourceNum(int wednesdayNoonSourceNum) {
		this.wednesdayNoonSourceNum = wednesdayNoonSourceNum;
	}

	public int getWednesdayNight() {
		return wednesdayNight;
	}

	public void setWednesdayNight(int wednesdayNight) {
		this.wednesdayNight = wednesdayNight;
	}

	public int getWednesdayNightSourceNum() {
		return wednesdayNightSourceNum;
	}

	public void setWednesdayNightSourceNum(int wednesdayNightSourceNum) {
		this.wednesdayNightSourceNum = wednesdayNightSourceNum;
	}

	public int getThursdayMorning() {
		return thursdayMorning;
	}

	public void setThursdayMorning(int thursdayMorning) {
		this.thursdayMorning = thursdayMorning;
	}

	public int getThursdayMorningSourceNum() {
		return thursdayMorningSourceNum;
	}

	public void setThursdayMorningSourceNum(int thursdayMorningSourceNum) {
		this.thursdayMorningSourceNum = thursdayMorningSourceNum;
	}

	public int getThursdayNoon() {
		return thursdayNoon;
	}

	public void setThursdayNoon(int thursdayNoon) {
		this.thursdayNoon = thursdayNoon;
	}

	public int getThursdayNoonSourceNum() {
		return thursdayNoonSourceNum;
	}

	public void setThursdayNoonSourceNum(int thursdayNoonSourceNum) {
		this.thursdayNoonSourceNum = thursdayNoonSourceNum;
	}

	public int getThursdayNight() {
		return thursdayNight;
	}

	public void setThursdayNight(int thursdayNight) {
		this.thursdayNight = thursdayNight;
	}

	public int getThursdayNightSourceNum() {
		return thursdayNightSourceNum;
	}

	public void setThursdayNightSourceNum(int thursdayNightSourceNum) {
		this.thursdayNightSourceNum = thursdayNightSourceNum;
	}

	public int getFridayMorning() {
		return fridayMorning;
	}

	public void setFridayMorning(int fridayMorning) {
		this.fridayMorning = fridayMorning;
	}

	public int getFridayMorningSourceNum() {
		return fridayMorningSourceNum;
	}

	public void setFridayMorningSourceNum(int fridayMorningSourceNum) {
		this.fridayMorningSourceNum = fridayMorningSourceNum;
	}

	public int getFridayNoon() {
		return fridayNoon;
	}

	public void setFridayNoon(int fridayNoon) {
		this.fridayNoon = fridayNoon;
	}

	public int getFridayNoonSourceNum() {
		return fridayNoonSourceNum;
	}

	public void setFridayNoonSourceNum(int fridayNoonSourceNum) {
		this.fridayNoonSourceNum = fridayNoonSourceNum;
	}

	public int getFridayNight() {
		return fridayNight;
	}

	public void setFridayNight(int fridayNight) {
		this.fridayNight = fridayNight;
	}

	public int getFridayNightSourceNum() {
		return fridayNightSourceNum;
	}

	public void setFridayNightSourceNum(int fridayNightSourceNum) {
		this.fridayNightSourceNum = fridayNightSourceNum;
	}

	public int getSaturdayMorning() {
		return saturdayMorning;
	}

	public void setSaturdayMorning(int saturdayMorning) {
		this.saturdayMorning = saturdayMorning;
	}

	public int getSaturdayMorningSourceNum() {
		return saturdayMorningSourceNum;
	}

	public void setSaturdayMorningSourceNum(int saturdayMorningSourceNum) {
		this.saturdayMorningSourceNum = saturdayMorningSourceNum;
	}

	public int getSaturdayNoon() {
		return saturdayNoon;
	}

	public void setSaturdayNoon(int saturdayNoon) {
		this.saturdayNoon = saturdayNoon;
	}

	public int getSaturdayNoonSourceNum() {
		return saturdayNoonSourceNum;
	}

	public void setSaturdayNoonSourceNum(int saturdayNoonSourceNum) {
		this.saturdayNoonSourceNum = saturdayNoonSourceNum;
	}

	public int getSaturdayNight() {
		return saturdayNight;
	}

	public void setSaturdayNight(int saturdayNight) {
		this.saturdayNight = saturdayNight;
	}

	public int getSaturdayNightSourceNum() {
		return saturdayNightSourceNum;
	}

	public void setSaturdayNightSourceNum(int saturdayNightSourceNum) {
		this.saturdayNightSourceNum = saturdayNightSourceNum;
	}

	public int getSundayMorning() {
		return sundayMorning;
	}

	public void setSundayMorning(int sundayMorning) {
		this.sundayMorning = sundayMorning;
	}

	public int getSundayMorningSourceNum() {
		return sundayMorningSourceNum;
	}

	public void setSundayMorningSourceNum(int sundayMorningSourceNum) {
		this.sundayMorningSourceNum = sundayMorningSourceNum;
	}

	public int getSundayNoon() {
		return sundayNoon;
	}

	public void setSundayNoon(int sundayNoon) {
		this.sundayNoon = sundayNoon;
	}

	public int getSundayNoonSourceNum() {
		return sundayNoonSourceNum;
	}

	public void setSundayNoonSourceNum(int sundayNoonSourceNum) {
		this.sundayNoonSourceNum = sundayNoonSourceNum;
	}

	public int getSundayNight() {
		return sundayNight;
	}

	public void setSundayNight(int sundayNight) {
		this.sundayNight = sundayNight;
	}

	public int getSundayNightSourceNum() {
		return sundayNightSourceNum;
	}

	public void setSundayNightSourceNum(int sundayNightSourceNum) {
		this.sundayNightSourceNum = sundayNightSourceNum;
	}

	public int getFlagUseState() {
		return flagUseState;
	}

	public void setFlagUseState(int flagUseState) {
		this.flagUseState = flagUseState;
	}
}
