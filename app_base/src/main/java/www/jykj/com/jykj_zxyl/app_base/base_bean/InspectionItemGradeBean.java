package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-15 11:30
 */
public class InspectionItemGradeBean {


    /**
     * gradeCode : jcdj0075025f4a22a17241dcd04fb002
     * gradeContentCode : 02001,02002,02003
     * gradeContentName : 一般,加急,加强(如:打造影剂)
     * gradeSort : 0
     * gradeTitle : CT检查等级
     * hospitalInfoCode : 0
     * hospitalInfoName : 缺省值
     */

    private String gradeCode;
    private String gradeContentCode;
    private String gradeContentName;
    private int gradeSort;
    private String gradeTitle;
    private String hospitalInfoCode;
    private String hospitalInfoName;

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeContentCode() {
        return gradeContentCode;
    }

    public void setGradeContentCode(String gradeContentCode) {
        this.gradeContentCode = gradeContentCode;
    }

    public String getGradeContentName() {
        return gradeContentName;
    }

    public void setGradeContentName(String gradeContentName) {
        this.gradeContentName = gradeContentName;
    }

    public int getGradeSort() {
        return gradeSort;
    }

    public void setGradeSort(int gradeSort) {
        this.gradeSort = gradeSort;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public String getHospitalInfoCode() {
        return hospitalInfoCode;
    }

    public void setHospitalInfoCode(String hospitalInfoCode) {
        this.hospitalInfoCode = hospitalInfoCode;
    }

    public String getHospitalInfoName() {
        return hospitalInfoName;
    }

    public void setHospitalInfoName(String hospitalInfoName) {
        this.hospitalInfoName = hospitalInfoName;
    }
}
