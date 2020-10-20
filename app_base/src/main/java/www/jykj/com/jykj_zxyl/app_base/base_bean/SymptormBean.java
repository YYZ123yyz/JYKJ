package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

public class SymptormBean implements Serializable {


    /**
     * combinedDisease : 1000405
     * combinedDiseaseName : 心肌梗死。
     * complication : 1000304^1000305^1000303^1000306
     * complicationName : 心脏病、左心室肥厚、心绞痛、心肌梗死。
     * currentSymptoms : 1000202^1000204^1000203^1000205
     * currentSymptomsName : 头晕脑胀、头痛、耳鸣、眼睑水肿。
     * currentTreatmentPlan : 1000502
     * currentTreatmentPlanName : 口服药。
     * onsetSymptoms : 1000102^1000111^1000105
     * onsetSymptomsName : 头晕脑胀、眼睑水肿、心悸。
     * stateOfIllness : 脚本测试0915
     */

    private String combinedDisease;
    private String combinedDiseaseName;
    private String complication;
    private String complicationName;
    private String currentSymptoms;
    private String currentSymptomsName;
    private String currentTreatmentPlan;
    private String currentTreatmentPlanName;
    private String onsetSymptoms;
    private String onsetSymptomsName;
    private String stateOfIllness;

    public String getCombinedDisease() {
        return combinedDisease;
    }

    public void setCombinedDisease(String combinedDisease) {
        this.combinedDisease = combinedDisease;
    }

    public String getCombinedDiseaseName() {
        return combinedDiseaseName;
    }

    public void setCombinedDiseaseName(String combinedDiseaseName) {
        this.combinedDiseaseName = combinedDiseaseName;
    }

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) {
        this.complication = complication;
    }

    public String getComplicationName() {
        return complicationName;
    }

    public void setComplicationName(String complicationName) {
        this.complicationName = complicationName;
    }

    public String getCurrentSymptoms() {
        return currentSymptoms;
    }

    public void setCurrentSymptoms(String currentSymptoms) {
        this.currentSymptoms = currentSymptoms;
    }

    public String getCurrentSymptomsName() {
        return currentSymptomsName;
    }

    public void setCurrentSymptomsName(String currentSymptomsName) {
        this.currentSymptomsName = currentSymptomsName;
    }

    public String getCurrentTreatmentPlan() {
        return currentTreatmentPlan;
    }

    public void setCurrentTreatmentPlan(String currentTreatmentPlan) {
        this.currentTreatmentPlan = currentTreatmentPlan;
    }

    public String getCurrentTreatmentPlanName() {
        return currentTreatmentPlanName;
    }

    public void setCurrentTreatmentPlanName(String currentTreatmentPlanName) {
        this.currentTreatmentPlanName = currentTreatmentPlanName;
    }

    public String getOnsetSymptoms() {
        return onsetSymptoms;
    }

    public void setOnsetSymptoms(String onsetSymptoms) {
        this.onsetSymptoms = onsetSymptoms;
    }

    public String getOnsetSymptomsName() {
        return onsetSymptomsName;
    }

    public void setOnsetSymptomsName(String onsetSymptomsName) {
        this.onsetSymptomsName = onsetSymptomsName;
    }

    public String getStateOfIllness() {
        return stateOfIllness;
    }

    public void setStateOfIllness(String stateOfIllness) {
        this.stateOfIllness = stateOfIllness;
    }
}
