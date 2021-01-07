package www.jykj.com.jykj_zxyl.activity.myreport.activity.bean;

import java.io.Serializable;

public class CommitBean implements Serializable {


    /**
     * departmentId : 1
     * departmentName : 内科
     * totalDayAmount : 1400.0
     */

    private int departmentId;
    private String departmentName;
    private double totalDayAmount;
    private String doctorName;
    private String doctorCode;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getTotalDayAmount() {
        return totalDayAmount;
    }

    public void setTotalDayAmount(double totalDayAmount) {
        this.totalDayAmount = totalDayAmount;
    }
}
