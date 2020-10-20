package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

public class Myself_DetaiBean implements Serializable {


    /**
     * imgCode : 0C50724B61134DB8BFB49D9692D33E1F
     * patientCode : 4dcc513a5dd34fa09a7a229a175e5c11
     * recordContent : 哈哈哈哈哈哈哈哈好开心的时候就这样被你们刷屏刷屏的我真是的一种心情叫人都要把前端换成这样也没有任何意义。我们都在这里了。嗯不是这你你这这你这这这这这你这这你你这你你这这你这这你你这这这这要把我当成我朋友的时间就在一起就是你最想的了
     * recordId : 73
     * recordImgArray : http://114.215.137.171:8040/patientConditionDiseaseRecordImage/diseaseRecord/4dcc513a5dd34fa09a7a229a175e5c11/DiseaseRecord_20200925140938.jpg
     * recordName : 哈哈哈哈
     * treatmentDate : 1600963200000
     */

    private String imgCode;
    private String patientCode;
    private String recordContent;
    private int recordId;
    private String recordImgArray;
    private String recordName;
    private long treatmentDate;

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordImgArray() {
        return recordImgArray;
    }

    public void setRecordImgArray(String recordImgArray) {
        this.recordImgArray = recordImgArray;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public long getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(long treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
}
