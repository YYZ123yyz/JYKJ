package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.io.Serializable;


public class ReportBean implements Serializable {


    /**
     * diseaseTypeCode : jibingzhenduanmingcheng001
     * diseaseTypeName : 冠心病
     */

    private String diseaseTypeCode;
    private String diseaseTypeName;

    public String getDiseaseTypeCode() {
        return diseaseTypeCode;
    }

    public void setDiseaseTypeCode(String diseaseTypeCode) {
        this.diseaseTypeCode = diseaseTypeCode;
    }

    public String getDiseaseTypeName() {
        return diseaseTypeName;
    }

    public void setDiseaseTypeName(String diseaseTypeName) {
        this.diseaseTypeName = diseaseTypeName;
    }
}
