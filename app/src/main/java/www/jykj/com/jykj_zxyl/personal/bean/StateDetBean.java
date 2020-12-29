package www.jykj.com.jykj_zxyl.personal.bean;

public class StateDetBean {

    /**
     * diseaseNum : 1个
     * diseaseNumContent : 糖尿病；
     * htnClassifyName : 高血压Ⅰ期
     * htnLClassifyLevelImgUrl : https://www.jiuyihtn.com/AppApplication/patientHtnLClassifyLevel.jpg
     * organNum : 3个
     * organNumContent : 左心室肥厚；静股动脉 PWV>12m/s；ABI<0.9；
     * riskFactorsContent : 抽烟；喝酒；
     * riskFactorsNum : 2个
     */

    private String diseaseNum;
    private String diseaseNumContent;
    private String htnClassifyName;
    private String htnLClassifyLevelImgUrl;
    private String organNum;
    private String organNumContent;
    private String riskFactorsContent;
    private String riskFactorsNum;

    public String getDiseaseNum() {
        return diseaseNum;
    }

    public void setDiseaseNum(String diseaseNum) {
        this.diseaseNum = diseaseNum;
    }

    public String getDiseaseNumContent() {
        return diseaseNumContent;
    }

    public void setDiseaseNumContent(String diseaseNumContent) {
        this.diseaseNumContent = diseaseNumContent;
    }

    public String getHtnClassifyName() {
        return htnClassifyName;
    }

    public void setHtnClassifyName(String htnClassifyName) {
        this.htnClassifyName = htnClassifyName;
    }

    public String getHtnLClassifyLevelImgUrl() {
        return htnLClassifyLevelImgUrl;
    }

    public void setHtnLClassifyLevelImgUrl(String htnLClassifyLevelImgUrl) {
        this.htnLClassifyLevelImgUrl = htnLClassifyLevelImgUrl;
    }

    public String getOrganNum() {
        return organNum;
    }

    public void setOrganNum(String organNum) {
        this.organNum = organNum;
    }

    public String getOrganNumContent() {
        return organNumContent;
    }

    public void setOrganNumContent(String organNumContent) {
        this.organNumContent = organNumContent;
    }

    public String getRiskFactorsContent() {
        return riskFactorsContent;
    }

    public void setRiskFactorsContent(String riskFactorsContent) {
        this.riskFactorsContent = riskFactorsContent;
    }

    public String getRiskFactorsNum() {
        return riskFactorsNum;
    }

    public void setRiskFactorsNum(String riskFactorsNum) {
        this.riskFactorsNum = riskFactorsNum;
    }
}
