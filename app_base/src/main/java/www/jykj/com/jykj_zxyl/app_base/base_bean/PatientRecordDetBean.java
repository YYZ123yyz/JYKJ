package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Created by G on 2020/9/19 14:55
 */
public class PatientRecordDetBean {


    /**
     * createDate : 1600682495000
     * departmentSecondName : 呼吸内科
     * doctorName : 医生测试002
     * flagConfirmState : 1
     * flagHistoryAllergy : 0
     * flagSendMedicalRecord : 0
     * flagWriteChiefComplaint : 0
     * flagWriteDiagnosis : 0
     * flagWriteDrug : 0
     * flagWriteHistoryAllergy : 0
     * flagWriteHistoryNew : 0
     * flagWriteHistoryPast : 0
     * flagWriteInspection : 0
     * flagWriteMedicalExamination : 0
     * flagWriteTreatmentProposal : 0
     * interactOrderInspectionList : []
     * interactOrderPrescribeList : []
     * patientAge : 6
     * patientGender : 1
     * patientLogoUrl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq84OzgEUTZfOS62xVNrkR0wfDIc5rIVWRwwDbcvjQiaxc5X98dDSrdopFmgGuaRtJKFbkWDXpe3Bw/132
     * patientName : 搬运工
     * recordCode : 62d2c333c28c4aba8d619e78fb222bd4
     * treatmentCardNum : 666888
     */

    private long createDate;
    private String departmentSecondName;
    private String diagnosisName;
    private String inspectionName;
    private String drugName;
    private String doctorName;
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
    private int patientAge;
    private int patientGender;
    private String patientLogoUrl;
    private String patientName;
    private String recordCode;
    private String treatmentCardNum;
    private List<InteractOrderInspectionListBean> interactOrderInspectionList;
    private List<InteractOrderPrescribeListBean> interactOrderPrescribeList;
    private String chiefComplaint;
    private String historyNew;
    private String historyPast;
    private String historyAllergy;
    private String medicalExamination;
    private String patientChiefComplaint;
    private String patientHistoryNew;
    private String treatmentMould;
    private long reserveConfigEnd;
    public String getPatientChiefComplaint() {
        return patientChiefComplaint;
    }

    public void setPatientChiefComplaint(String patientChiefComplaint) {
        this.patientChiefComplaint = patientChiefComplaint;
    }

    public String getPatientHistoryNew() {
        return patientHistoryNew;
    }

    public void setPatientHistoryNew(String patientHistoryNew) {
        this.patientHistoryNew = patientHistoryNew;
    }

    public String getPatientHistoryPast() {
        return patientHistoryPast;
    }

    public void setPatientHistoryPast(String patientHistoryPast) {
        this.patientHistoryPast = patientHistoryPast;
    }

    public String getPatientHistoryAllergy() {
        return patientHistoryAllergy;
    }

    public void setPatientHistoryAllergy(String patientHistoryAllergy) {
        this.patientHistoryAllergy = patientHistoryAllergy;
    }

    private String patientHistoryPast;
    private String patientHistoryAllergy;



    public String getTreatmentProposal() {
        return treatmentProposal;
    }

    public void setTreatmentProposal(String treatmentProposal) {
        this.treatmentProposal = treatmentProposal;
    }

    private String treatmentProposal;

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
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

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(String medicalExamination) {
        this.medicalExamination = medicalExamination;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
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

    public String getTreatmentMould() {
        return treatmentMould;
    }

    public void setTreatmentMould(String treatmentMould) {
        this.treatmentMould = treatmentMould;
    }

    public long getReserveConfigEnd() {
        return reserveConfigEnd;
    }

    public void setReserveConfigEnd(long reserveConfigEnd) {
        this.reserveConfigEnd = reserveConfigEnd;
    }
}
