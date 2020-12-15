package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-19 16:57
 */
public class PrescriptionNotesBean implements Serializable{

    private List<PrescribeInfoBean> prescribeInfo;

    public List<PrescribeInfoBean> getPrescribeInfo() {
        return prescribeInfo;
    }

    public void setPrescribeInfo(List<PrescribeInfoBean> prescribeInfo) {
        this.prescribeInfo = prescribeInfo;
    }

    public static class PrescribeInfoBean implements Serializable {
        /**
         * drugAmount : 1.0
         * drugAmountName : 1.0(1盒/24粒)
         * drugCode : be87duf92m044326b2a6e856bdabd001
         * drugMoneys : 11.0
         * drugName : 风热颗粒
         * drugOrderCode : 27974250a35c44b68725712673afeddd
         * drugPack : 盒
         * orderCode : 2c534ac9d97245e480315b9e3804f459
         * patientCode : 4dcc513a5dd34fa09a7a229a175e5c11
         * patientName : Pan
         * prescribeDate : 1600503381000
         * prescribeType : 1003301
         * prescribeTypeName : 平台处方
         * prescribeVoucher : b97f861d13324ce2af8660cc35ff6bfc
         * specName : 1盒/24粒
         * specUnit : 粒
         * useCycle : 1
         * useDesc : 急急急
         * useFrequency : 1
         * useFrequencyCode : 1003501
         * useFrequencyName : 每天一次
         * useNum : 3
         * useNumName : 3 粒/次
         */

        private double drugAmount;
        private String drugAmountName;
        private String drugCode;
        private double drugMoneys;
        private String drugName;
        private String drugOrderCode;
        private String drugPack;
        private String orderCode;
        private String patientCode;
        private String patientName;
        private long prescribeDate;
        private int prescribeType;
        private String prescribeTypeName;
        private String prescribeVoucher;
        private String specName;
        private String specUnit;
        private int useCycle;
        private String useDesc;
        private int useFrequency;
        private String useFrequencyCode;
        private String useFrequencyName;
        private int useNum;
        private String useNumName;
        private double drugPrice;
        private String useFrequencyDay;
        private String useUsageDay;
        public double getDrugAmount() {
            return drugAmount;
        }

        public void setDrugAmount(double drugAmount) {
            this.drugAmount = drugAmount;
        }

        public String getDrugAmountName() {
            return drugAmountName;
        }

        public void setDrugAmountName(String drugAmountName) {
            this.drugAmountName = drugAmountName;
        }

        public String getDrugCode() {
            return drugCode;
        }

        public void setDrugCode(String drugCode) {
            this.drugCode = drugCode;
        }

        public double getDrugMoneys() {
            return drugMoneys;
        }

        public void setDrugMoneys(double drugMoneys) {
            this.drugMoneys = drugMoneys;
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

        public String getDrugPack() {
            return drugPack;
        }

        public void setDrugPack(String drugPack) {
            this.drugPack = drugPack;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public long getPrescribeDate() {
            return prescribeDate;
        }

        public void setPrescribeDate(long prescribeDate) {
            this.prescribeDate = prescribeDate;
        }

        public int getPrescribeType() {
            return prescribeType;
        }

        public void setPrescribeType(int prescribeType) {
            this.prescribeType = prescribeType;
        }

        public String getPrescribeTypeName() {
            return prescribeTypeName;
        }

        public void setPrescribeTypeName(String prescribeTypeName) {
            this.prescribeTypeName = prescribeTypeName;
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

        public String getSpecUnit() {
            return specUnit;
        }

        public void setSpecUnit(String specUnit) {
            this.specUnit = specUnit;
        }

        public int getUseCycle() {
            return useCycle;
        }

        public void setUseCycle(int useCycle) {
            this.useCycle = useCycle;
        }

        public String getUseDesc() {
            return useDesc;
        }

        public void setUseDesc(String useDesc) {
            this.useDesc = useDesc;
        }

        public int getUseFrequency() {
            return useFrequency;
        }

        public void setUseFrequency(int useFrequency) {
            this.useFrequency = useFrequency;
        }

        public String getUseFrequencyCode() {
            return useFrequencyCode;
        }

        public void setUseFrequencyCode(String useFrequencyCode) {
            this.useFrequencyCode = useFrequencyCode;
        }

        public String getUseFrequencyName() {
            return useFrequencyName;
        }

        public void setUseFrequencyName(String useFrequencyName) {
            this.useFrequencyName = useFrequencyName;
        }

        public int getUseNum() {
            return useNum;
        }

        public void setUseNum(int useNum) {
            this.useNum = useNum;
        }

        public String getUseNumName() {
            return useNumName;
        }

        public void setUseNumName(String useNumName) {
            this.useNumName = useNumName;
        }

        public double getDrugPrice() {
            return drugPrice;
        }

        public void setDrugPrice(double drugPrice) {
            this.drugPrice = drugPrice;
        }

        public String getUseFrequencyDay() {
            return useFrequencyDay;
        }

        public void setUseFrequencyDay(String useFrequencyDay) {
            this.useFrequencyDay = useFrequencyDay;
        }

        public String getUseUsageDay() {
            return useUsageDay;
        }

        public void setUseUsageDay(String useUsageDay) {
            this.useUsageDay = useUsageDay;
        }
    }
}
