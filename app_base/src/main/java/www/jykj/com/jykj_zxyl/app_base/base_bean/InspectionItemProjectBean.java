package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 16:30
 */
public class InspectionItemProjectBean implements Serializable {


    /**
     * gradeCode : jcdj0075025f4a22a17241dcd04fb001
     * gradeTitle : 默认检查等级
     * hospitalInfoCode : 0
     * hospitalInfoName : 缺省值
     * inspectionCode : 1000000001
     * inspectionId : 1
     * inspectionName : CT
     * inspectionNameFive : 11
     * inspectionNameSpell : ct
     * inspectionPayService : 210300001A
     * inspectionSort : 1
     * inspectionType : 10
     * inspectionTypeName : 检查
     * parentInspectionCode : 0
     * price : 300.0
     * reminderCode : 60000001
     * reminderTitle : 检查提醒
     * spec : 个
     * unit : 个
     */

    private String gradeCode;
    private String gradeTitle;
    private String hospitalInfoCode;
    private String hospitalInfoName;
    private String inspectionCode;
    private int inspectionId;
    private String inspectionName;
    private String inspectionNameFive;
    private String inspectionNameSpell;
    private String inspectionPayService;
    private int inspectionSort;
    private int inspectionType;
    private String inspectionTypeName;
    private String parentInspectionCode;
    private double price;
    private String reminderCode;
    private String reminderTitle;
    private String spec;
    private String unit;
    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
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

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode;
    }

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getInspectionNameFive() {
        return inspectionNameFive;
    }

    public void setInspectionNameFive(String inspectionNameFive) {
        this.inspectionNameFive = inspectionNameFive;
    }

    public String getInspectionNameSpell() {
        return inspectionNameSpell;
    }

    public void setInspectionNameSpell(String inspectionNameSpell) {
        this.inspectionNameSpell = inspectionNameSpell;
    }

    public String getInspectionPayService() {
        return inspectionPayService;
    }

    public void setInspectionPayService(String inspectionPayService) {
        this.inspectionPayService = inspectionPayService;
    }

    public int getInspectionSort() {
        return inspectionSort;
    }

    public void setInspectionSort(int inspectionSort) {
        this.inspectionSort = inspectionSort;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getInspectionTypeName() {
        return inspectionTypeName;
    }

    public void setInspectionTypeName(String inspectionTypeName) {
        this.inspectionTypeName = inspectionTypeName;
    }

    public String getParentInspectionCode() {
        return parentInspectionCode;
    }

    public void setParentInspectionCode(String parentInspectionCode) {
        this.parentInspectionCode = parentInspectionCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReminderCode() {
        return reminderCode;
    }

    public void setReminderCode(String reminderCode) {
        this.reminderCode = reminderCode;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
