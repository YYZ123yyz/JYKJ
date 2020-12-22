package www.jykj.com.jykj_zxyl.custom;

public class RefrecenmapBean {


    private int ageEnd;
    private int ageStart;
    private String doctorCode;
    private String doctorName;
    private String doctorsetCode;
    private int flagAgeType;
    private String flagDataType;
    private int flagGender; //1男,2女
    private double gradeFloatingValue;
    private double highNum;
    private double lowNum;
    private boolean isClick;
    private int clickNum; //0全,1高,2低,3阀

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(int ageEnd) {
        this.ageEnd = ageEnd;
    }

    public int getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(int ageStart) {
        this.ageStart = ageStart;
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

    public String getDoctorsetCode() {
        return doctorsetCode;
    }

    public void setDoctorsetCode(String doctorsetCode) {
        this.doctorsetCode = doctorsetCode;
    }

    public int getFlagAgeType() {
        return flagAgeType;
    }

    public void setFlagAgeType(int flagAgeType) {
        this.flagAgeType = flagAgeType;
    }

    public String getFlagDataType() {
        return flagDataType;
    }

    public void setFlagDataType(String flagDataType) {
        this.flagDataType = flagDataType;
    }

    public int getFlagGender() {
        return flagGender;
    }

    public void setFlagGender(int flagGender) {
        this.flagGender = flagGender;
    }

    public double getGradeFloatingValue() {
        return gradeFloatingValue;
    }

    public void setGradeFloatingValue(double gradeFloatingValue) {
        this.gradeFloatingValue = gradeFloatingValue;
    }

    public double getHighNum() {
        return highNum;
    }

    public void setHighNum(double highNum) {
        this.highNum = highNum;
    }

    public double getLowNum() {
        return lowNum;
    }

    public void setLowNum(double lowNum) {
        this.lowNum = lowNum;
    }
}
