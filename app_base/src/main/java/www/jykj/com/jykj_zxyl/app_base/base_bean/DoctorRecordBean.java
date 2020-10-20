package www.jykj.com.jykj_zxyl.app_base.base_bean;

public class DoctorRecordBean {

    /**
     * createDate : 1600756516000
     * diagnosisName : 冠心病,            冠心病,             冠心病,
     * doctorName : 医生测试002
     * orderCode : 0101202009181608445105661560
     * recordId : 2
     * showMedicalRecordName : 就诊病历
     * treatmentProposal : zljy
     */

    private long createDate;
    private String diagnosisName;
    private String doctorName;
    private String orderCode;
    private int recordId;
    private String showMedicalRecordName;
    private String treatmentProposal;
    /**
     * imgCode : d93f3b24-ab9a-40dc-8fd9-14af3579cc86
     * recordContent : 药物住宿
     * recordName : 暂无
     * treatmentDate : 1594224000000
     */

    private String imgCode;
    private String recordContent;
    private String recordName;
    private long treatmentDate;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
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

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
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
