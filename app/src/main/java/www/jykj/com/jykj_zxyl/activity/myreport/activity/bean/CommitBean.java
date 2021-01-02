package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.io.Serializable;

public class CommitBean implements Serializable {

    /**
     * departmentId : 1
     * departmentName : 科室1
     * departmentSumMoney : 5000.00
     */

    private String departmentId;
    private String departmentName;
    private String departmentSumMoney;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentSumMoney() {
        return departmentSumMoney;
    }

    public void setDepartmentSumMoney(String departmentSumMoney) {
        this.departmentSumMoney = departmentSumMoney;
    }
}
