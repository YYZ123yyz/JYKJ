package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.io.Serializable;


public class AuthorityBean implements Serializable {


    /**
     * departmentName : 妇产科
     * departmentSecondName : 妇科
     * hospitalName : 北京市阜外医院
     * userGradeCode : 30
     * userGradeName : 院长
     */

    private String departmentName;
    private String departmentSecondName;
    private String hospitalName;
    private String userGradeCode;
    private String userGradeName;
    private String showType;

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentSecondName() {
        return departmentSecondName;
    }

    public void setDepartmentSecondName(String departmentSecondName) {
        this.departmentSecondName = departmentSecondName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getUserGradeCode() {
        return userGradeCode;
    }

    public void setUserGradeCode(String userGradeCode) {
        this.userGradeCode = userGradeCode;
    }

    public String getUserGradeName() {
        return userGradeName;
    }

    public void setUserGradeName(String userGradeName) {
        this.userGradeName = userGradeName;
    }
}
