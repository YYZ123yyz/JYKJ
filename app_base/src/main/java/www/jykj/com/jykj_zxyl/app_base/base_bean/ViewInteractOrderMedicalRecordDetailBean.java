package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;
import java.util.List;

public class ViewInteractOrderMedicalRecordDetailBean implements Serializable {


    /**
     * chiefComplaint : 哈哈-1
     * createDate : 1600343910000
     * departmentSecondName : 呼吸内科
     * diagnosisName : 冠心病22,测试新增诊断22
     * doctorName : 尹子敬
     * drugName : 阿莫西林,感康
     * flagConfirmState : 1
     * flagHistoryAllergy : 1
     * flagSendMedicalRecord : 1
     * flagWriteChiefComplaint : 1
     * flagWriteDiagnosis : 1
     * flagWriteDrug : 1
     * flagWriteHistoryAllergy : 1
     * flagWriteHistoryNew : 1
     * flagWriteHistoryPast : 1
     * flagWriteInspection : 1
     * flagWriteMedicalExamination : 1
     * flagWriteTreatmentProposal : 1
     * historyAllergy : 哈哈-4
     * historyNew : 哈哈-2
     * historyPast : 哈哈-3
     * inspectionName : CT,血常规,尿常规
     * interactOrderInspectionList : [{"inspectionName":"CT","inspectionOrderCode":"123","sampleOrLocationName":"头部,胸部,"},{"inspectionName":"尿常规","inspectionOrderCode":"456","sampleOrLocationName":"白细胞,红细胞,"}]
     * interactOrderPrescribeList : [{"prescribeInfo":[{"drugAmount":10,"drugName":"西比灵胶囊","drugOrderCode":"f13c75c47c9d441c9b304f72b4e80e52","drugPrice":0,"prescribeVoucher":"testc9d441c9b304f72b4e80001","specName":"1盒/24粒"},{"drugAmount":2,"drugName":"倍他乐克","drugOrderCode":"c31ce89a1c4447a9b5ee45e6bddd8be2","drugPrice":0,"prescribeVoucher":"testc9d441c9b304f72b4e80001","specName":"20片/板/盒"}]},{"prescribeInfo":[{"drugAmount":30,"drugName":"甲硝唑片","drugOrderCode":"fe4b36ef5dd04ad1ab105e17cfe0ce87","drugPrice":0,"prescribeVoucher":"testc9d441c9b304f72b4e80002","specName":"1盒/18片"}]},{"prescribeInfo":[{"drugAmount":2,"drugName":"藿香正气软胶囊","drugOrderCode":"65b825104f654b0a93099ad3308279c3","drugPrice":0,"prescribeVoucher":"testc9d441c9b304f72b4e80003","specName":"1盒/6瓶"}]}]
     * medicalExamination : 哈哈-5
     * patientAge : 32
     * patientGender : 1
     * patientLogoUrl : http://114.215.137.171:8040/patientImage/patientlogo/dafd840808d64027b64893eed11b97b8/Patientlogo_20200617163025.jpg
     * patientName : 搬运工
     * recordCode : 98dbeeb093dd43d2aa8a3feb35987cff
     * treatmentCardNum :
     * treatmentProposal : 哈哈-6
     */

    private String chiefComplaint;
    private long createDate;
    private String departmentSecondName;
    private String diagnosisName;
    private String doctorName;
    private String drugName;
    private int flagConfirmState;
    private int flagHistoryAllergy;
    private int flagSendMedicalRecord;
    private int flagWriteChiefComplaint;
    private int flagWriteDiagnosis;
    private int flagWriteDrug;
    private int flagWriteHistoryAllergy;
    private int flagWriteHistoryNew;
    private int flagWriteHistoryPast;
    private int flagWriteInspection;
    private int flagWriteMedicalExamination;
    private int flagWriteTreatmentProposal;
    private String historyAllergy;
    private String historyNew;
    private String historyPast;
    private String inspectionName;
    private String medicalExamination;
    private int patientAge;
    private int patientGender;
    private String patientLogoUrl;
    private String patientName;
    private String recordCode;
    private String treatmentCardNum;
    private String treatmentProposal;
    private List<InteractOrderInspectionListBean> interactOrderInspectionList;
    private List<InteractOrderPrescribeListBean> interactOrderPrescribeList;

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDepartmentSecondName() {
        return departmentSecondName;
    }

    public void setDepartmentSecondName(String departmentSecondName) {
        this.departmentSecondName = departmentSecondName;
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

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getFlagConfirmState() {
        return flagConfirmState;
    }

    public void setFlagConfirmState(int flagConfirmState) {
        this.flagConfirmState = flagConfirmState;
    }

    public int getFlagHistoryAllergy() {
        return flagHistoryAllergy;
    }

    public void setFlagHistoryAllergy(int flagHistoryAllergy) {
        this.flagHistoryAllergy = flagHistoryAllergy;
    }

    public int getFlagSendMedicalRecord() {
        return flagSendMedicalRecord;
    }

    public void setFlagSendMedicalRecord(int flagSendMedicalRecord) {
        this.flagSendMedicalRecord = flagSendMedicalRecord;
    }

    public int getFlagWriteChiefComplaint() {
        return flagWriteChiefComplaint;
    }

    public void setFlagWriteChiefComplaint(int flagWriteChiefComplaint) {
        this.flagWriteChiefComplaint = flagWriteChiefComplaint;
    }

    public int getFlagWriteDiagnosis() {
        return flagWriteDiagnosis;
    }

    public void setFlagWriteDiagnosis(int flagWriteDiagnosis) {
        this.flagWriteDiagnosis = flagWriteDiagnosis;
    }

    public int getFlagWriteDrug() {
        return flagWriteDrug;
    }

    public void setFlagWriteDrug(int flagWriteDrug) {
        this.flagWriteDrug = flagWriteDrug;
    }

    public int getFlagWriteHistoryAllergy() {
        return flagWriteHistoryAllergy;
    }

    public void setFlagWriteHistoryAllergy(int flagWriteHistoryAllergy) {
        this.flagWriteHistoryAllergy = flagWriteHistoryAllergy;
    }

    public int getFlagWriteHistoryNew() {
        return flagWriteHistoryNew;
    }

    public void setFlagWriteHistoryNew(int flagWriteHistoryNew) {
        this.flagWriteHistoryNew = flagWriteHistoryNew;
    }

    public int getFlagWriteHistoryPast() {
        return flagWriteHistoryPast;
    }

    public void setFlagWriteHistoryPast(int flagWriteHistoryPast) {
        this.flagWriteHistoryPast = flagWriteHistoryPast;
    }

    public int getFlagWriteInspection() {
        return flagWriteInspection;
    }

    public void setFlagWriteInspection(int flagWriteInspection) {
        this.flagWriteInspection = flagWriteInspection;
    }

    public int getFlagWriteMedicalExamination() {
        return flagWriteMedicalExamination;
    }

    public void setFlagWriteMedicalExamination(int flagWriteMedicalExamination) {
        this.flagWriteMedicalExamination = flagWriteMedicalExamination;
    }

    public int getFlagWriteTreatmentProposal() {
        return flagWriteTreatmentProposal;
    }

    public void setFlagWriteTreatmentProposal(int flagWriteTreatmentProposal) {
        this.flagWriteTreatmentProposal = flagWriteTreatmentProposal;
    }

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getHistoryNew() {
        return historyNew;
    }

    public void setHistoryNew(String historyNew) {
        this.historyNew = historyNew;
    }

    public String getHistoryPast() {
        return historyPast;
    }

    public void setHistoryPast(String historyPast) {
        this.historyPast = historyPast;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(String medicalExamination) {
        this.medicalExamination = medicalExamination;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public int getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(int patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientLogoUrl() {
        return patientLogoUrl;
    }

    public void setPatientLogoUrl(String patientLogoUrl) {
        this.patientLogoUrl = patientLogoUrl;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getTreatmentCardNum() {
        return treatmentCardNum;
    }

    public void setTreatmentCardNum(String treatmentCardNum) {
        this.treatmentCardNum = treatmentCardNum;
    }

    public String getTreatmentProposal() {
        return treatmentProposal;
    }

    public void setTreatmentProposal(String treatmentProposal) {
        this.treatmentProposal = treatmentProposal;
    }

    public List<InteractOrderInspectionListBean> getInteractOrderInspectionList() {
        return interactOrderInspectionList;
    }

    public void setInteractOrderInspectionList(List<InteractOrderInspectionListBean> interactOrderInspectionList) {
        this.interactOrderInspectionList = interactOrderInspectionList;
    }

    public List<InteractOrderPrescribeListBean> getInteractOrderPrescribeList() {
        return interactOrderPrescribeList;
    }

    public void setInteractOrderPrescribeList(List<InteractOrderPrescribeListBean> interactOrderPrescribeList) {
        this.interactOrderPrescribeList = interactOrderPrescribeList;
    }

    public static class InteractOrderInspectionListBean {
        /**
         * inspectionName : CT
         * inspectionOrderCode : 123
         * sampleOrLocationName : 头部,胸部,
         */

        private String inspectionName;
        private String inspectionOrderCode;
        private String sampleOrLocationName;

        public String getInspectionName() {
            return inspectionName;
        }

        public void setInspectionName(String inspectionName) {
            this.inspectionName = inspectionName;
        }

        public String getInspectionOrderCode() {
            return inspectionOrderCode;
        }

        public void setInspectionOrderCode(String inspectionOrderCode) {
            this.inspectionOrderCode = inspectionOrderCode;
        }

        public String getSampleOrLocationName() {
            return sampleOrLocationName;
        }

        public void setSampleOrLocationName(String sampleOrLocationName) {
            this.sampleOrLocationName = sampleOrLocationName;
        }
    }

    public static class InteractOrderPrescribeListBean {
        private List<PrescribeInfoBean> prescribeInfo;

        public List<PrescribeInfoBean> getPrescribeInfo() {
            return prescribeInfo;
        }

        public void setPrescribeInfo(List<PrescribeInfoBean> prescribeInfo) {
            this.prescribeInfo = prescribeInfo;
        }

        public static class PrescribeInfoBean {
            /**
             * drugAmount : 10.0
             * drugName : 西比灵胶囊
             * drugOrderCode : f13c75c47c9d441c9b304f72b4e80e52
             * drugPrice : 0.0
             * prescribeVoucher : testc9d441c9b304f72b4e80001
             * specName : 1盒/24粒
             */

            private double drugAmount;
            private String drugName;
            private String drugOrderCode;
            private double drugPrice;
            private String prescribeVoucher;
            private String specName;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public double getDrugAmount() {
                return drugAmount;
            }

            public void setDrugAmount(double drugAmount) {
                this.drugAmount = drugAmount;
            }

            public String getDrugName() {
                return drugName;
            }

            public void setDrugName(String drugName) {
                this.drugName = drugName;
            }

            public String getDrugOrderCode() {
                return drugOrderCode;
            }

            public void setDrugOrderCode(String drugOrderCode) {
                this.drugOrderCode = drugOrderCode;
            }

            public double getDrugPrice() {
                return drugPrice;
            }

            public void setDrugPrice(double drugPrice) {
                this.drugPrice = drugPrice;
            }

            public String getPrescribeVoucher() {
                return prescribeVoucher;
            }

            public void setPrescribeVoucher(String prescribeVoucher) {
                this.prescribeVoucher = prescribeVoucher;
            }

            public String getSpecName() {
                return specName;
            }

            public void setSpecName(String specName) {
                this.specName = specName;
            }
        }
    }

}
