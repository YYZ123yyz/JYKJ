package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-17 15:39
 */
public class MedicinalInfoBean implements Serializable {

    /**
     * drugCode : be87duf92m044326b2a6e856bdabd002
     * drugName : 板兰根颗粒
     * drugSpec : 1包/16袋
     * drugUnit : 袋
     */

    private String drugCode;
    private String drugName;
    private String drugSpec;
    private String drugPack;
    private String drugUnit;
    private String drugUsageRateNum;
    private String drugUsageRateDay;
    private String drugUsageNumUnit;
    private String drugUsageNumFrequency;
    private boolean isChoosed;


    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugSpec() {
        return drugSpec;
    }

    public void setDrugSpec(String drugSpec) {
        this.drugSpec = drugSpec;
    }

    public String getDrugUnit() {
        return drugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        this.drugUnit = drugUnit;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getDrugPack() {
        return drugPack;
    }

    public void setDrugPack(String drugPack) {
        this.drugPack = drugPack;
    }

    public String getDrugUsageRateNum() {
        return drugUsageRateNum;
    }

    public void setDrugUsageRateNum(String drugUsageRateNum) {
        this.drugUsageRateNum = drugUsageRateNum;
    }

    public String getDrugUsageRateDay() {
        return drugUsageRateDay;
    }

    public void setDrugUsageRateDay(String drugUsageRateDay) {
        this.drugUsageRateDay = drugUsageRateDay;
    }

    public String getDrugUsageNumUnit() {
        return drugUsageNumUnit;
    }

    public void setDrugUsageNumUnit(String drugUsageNumUnit) {
        this.drugUsageNumUnit = drugUsageNumUnit;
    }

    public String getDrugUsageNumFrequency() {
        return drugUsageNumFrequency;
    }

    public void setDrugUsageNumFrequency(String drugUsageNumFrequency) {
        this.drugUsageNumFrequency = drugUsageNumFrequency;
    }
}
