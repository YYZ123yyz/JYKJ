package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.util.List;

public class DepartmentListBean {

    /**
     * departmentName : 内科
     * hospitalDepartmentId : 1
     * hospitalDepartmentList : [{"departmentName":"呼吸内科","hospitalDepartmentId":14,"parentId":1},{"departmentName":"消化内科","hospitalDepartmentId":15,"parentId":1},{"departmentName":"精神内科","hospitalDepartmentId":16,"parentId":1},{"departmentName":"心血管内科","hospitalDepartmentId":17,"parentId":1},{"departmentName":"肾内科","hospitalDepartmentId":18,"parentId":1},{"departmentName":"血液内科","hospitalDepartmentId":19,"parentId":1},{"departmentName":"免疫科","hospitalDepartmentId":20,"parentId":1},{"departmentName":"内分泌科","hospitalDepartmentId":21,"parentId":1}]
     * parentId : 0
     */

    private String departmentName;
    private int hospitalDepartmentId;

    /**
     * departmentName : 呼吸内科
     * hospitalDepartmentId : 14
     * parentId : 1
     */

    private List<HospitalDepartmentListBean> hospitalDepartmentList;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getHospitalDepartmentId() {
        return hospitalDepartmentId;
    }

    public void setHospitalDepartmentId(int hospitalDepartmentId) {
        this.hospitalDepartmentId = hospitalDepartmentId;
    }



    public List<HospitalDepartmentListBean> getHospitalDepartmentList() {
        return hospitalDepartmentList;
    }

    public void setHospitalDepartmentList(List<HospitalDepartmentListBean> hospitalDepartmentList) {
        this.hospitalDepartmentList = hospitalDepartmentList;
    }

    public static class HospitalDepartmentListBean {
        private String departmentName;
        private int hospitalDepartmentId;
        private int parentId;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getHospitalDepartmentId() {
            return hospitalDepartmentId;
        }

        public void setHospitalDepartmentId(int hospitalDepartmentId) {
            this.hospitalDepartmentId = hospitalDepartmentId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
