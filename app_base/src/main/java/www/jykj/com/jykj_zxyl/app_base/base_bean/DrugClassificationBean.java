package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-27 17:07
 */
public class DrugClassificationBean {


    /**
     * drugTypeMedicineList : [{"flagUseState":1,"medicineCode":"001001","medicineName":"氢氯噻嗪","parentMedicineCode":"001","sort":1},{"flagUseState":1,"medicineCode":"001002","medicineName":"氨苯蝶啶","parentMedicineCode":"001","sort":1},{"flagUseState":1,"medicineCode":"001003","medicineName":"阿米洛利","parentMedicineCode":"001","sort":3},{"flagUseState":1,"medicineCode":"001004","medicineName":"呋塞米","parentMedicineCode":"001","sort":4},{"flagUseState":1,"medicineCode":"001005","medicineName":"吲达帕胺","parentMedicineCode":"001","sort":5}]
     * flagUseState : 1
     * medicineCode : 001
     * medicineName : 利尿剂
     * parentMedicineCode : 0
     * sort : 1
     */

    private int flagUseState;
    private String medicineCode;
    private String medicineName;
    private String parentMedicineCode;
    private int sort;
    private List<DrugTypeMedicineListBean> drugTypeMedicineList;

    public int getFlagUseState() {
        return flagUseState;
    }

    public void setFlagUseState(int flagUseState) {
        this.flagUseState = flagUseState;
    }

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getParentMedicineCode() {
        return parentMedicineCode;
    }

    public void setParentMedicineCode(String parentMedicineCode) {
        this.parentMedicineCode = parentMedicineCode;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<DrugTypeMedicineListBean> getDrugTypeMedicineList() {
        return drugTypeMedicineList;
    }

    public void setDrugTypeMedicineList(List<DrugTypeMedicineListBean> drugTypeMedicineList) {
        this.drugTypeMedicineList = drugTypeMedicineList;
    }

    public static class DrugTypeMedicineListBean {
        /**
         * flagUseState : 1
         * medicineCode : 001001
         * medicineName : 氢氯噻嗪
         * parentMedicineCode : 001
         * sort : 1
         */

        private int flagUseState;
        private String medicineCode;
        private String medicineName;
        private String parentMedicineCode;
        private int sort;

        public int getFlagUseState() {
            return flagUseState;
        }

        public void setFlagUseState(int flagUseState) {
            this.flagUseState = flagUseState;
        }

        public String getMedicineCode() {
            return medicineCode;
        }

        public void setMedicineCode(String medicineCode) {
            this.medicineCode = medicineCode;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public String getParentMedicineCode() {
            return parentMedicineCode;
        }

        public void setParentMedicineCode(String parentMedicineCode) {
            this.parentMedicineCode = parentMedicineCode;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
