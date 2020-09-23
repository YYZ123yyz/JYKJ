package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

public class ProvideViewPatientHealthyAndBasicsBean   implements Serializable {

    /**
     * bloodPressureAbnormalDate : 1594224000000
     * characterType : 1
     * confirmedYear : 1577808000000
     * flagAlcoholism : 0
     * flagFamilyHtn : 0
     * flagHtnHistory : 0
     * flagSmoking : 0
     * flagStayUpLate : 0
     * height : 175
     * htnHistory :
     * nation : 汉族
     * nativePlace : 山东号
     * waistline : 36
     * weight : 175
     */

    private long bloodPressureAbnormalDate;
    private int characterType;
    private long confirmedYear;
    private int flagAlcoholism;
    private int flagFamilyHtn;
    private int flagHtnHistory;
    private int flagSmoking;
    private int flagStayUpLate;
    private int height;
    private String htnHistory;
    private String nation;
    private String nativePlace;
    private int waistline;
    private int weight;

    public long getBloodPressureAbnormalDate() {
        return bloodPressureAbnormalDate;
    }

    public void setBloodPressureAbnormalDate(long bloodPressureAbnormalDate) {
        this.bloodPressureAbnormalDate = bloodPressureAbnormalDate;
    }

    public int getCharacterType() {
        return characterType;
    }

    public void setCharacterType(int characterType) {
        this.characterType = characterType;
    }

    public long getConfirmedYear() {
        return confirmedYear;
    }

    public void setConfirmedYear(long confirmedYear) {
        this.confirmedYear = confirmedYear;
    }

    public int getFlagAlcoholism() {
        return flagAlcoholism;
    }

    public void setFlagAlcoholism(int flagAlcoholism) {
        this.flagAlcoholism = flagAlcoholism;
    }

    public int getFlagFamilyHtn() {
        return flagFamilyHtn;
    }

    public void setFlagFamilyHtn(int flagFamilyHtn) {
        this.flagFamilyHtn = flagFamilyHtn;
    }

    public int getFlagHtnHistory() {
        return flagHtnHistory;
    }

    public void setFlagHtnHistory(int flagHtnHistory) {
        this.flagHtnHistory = flagHtnHistory;
    }

    public int getFlagSmoking() {
        return flagSmoking;
    }

    public void setFlagSmoking(int flagSmoking) {
        this.flagSmoking = flagSmoking;
    }

    public int getFlagStayUpLate() {
        return flagStayUpLate;
    }

    public void setFlagStayUpLate(int flagStayUpLate) {
        this.flagStayUpLate = flagStayUpLate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHtnHistory() {
        return htnHistory;
    }

    public void setHtnHistory(String htnHistory) {
        this.htnHistory = htnHistory;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public int getWaistline() {
        return waistline;
    }

    public void setWaistline(int waistline) {
        this.waistline = waistline;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
