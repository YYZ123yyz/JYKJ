package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

public class ProvidePatientConditionDiseaseRecordBean implements Serializable {


    /**
     * createDate : 1600682495000
     * doctorName : 医生测试002
     * orderCode : 0101202009181608445105661560
     * recordId : 8
     * showMedicalRecordName : 就诊病历
     * treatmentProposal : hgghh
     * diagnosisName : 冠心病22,测试新增诊断22,
     */

    private long createDate;
    private String doctorName;
    private String orderCode;
    private int recordId;
    private String showMedicalRecordName;
    private String treatmentProposal;
    private String diagnosisName;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getShowMedicalRecordName() {
        return showMedicalRecordName;
    }

    public void setShowMedicalRecordName(String showMedicalRecordName) {
        this.showMedicalRecordName = showMedicalRecordName;
    }

    public String getTreatmentProposal() {
        return treatmentProposal;
    }

    public void setTreatmentProposal(String treatmentProposal) {
        this.treatmentProposal = treatmentProposal;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }
}
