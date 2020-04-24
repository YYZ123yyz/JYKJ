package entity;

/**
 * 医生联盟列表
 */
public class MemberListInfo {

    /**
     * departmentName : 内科
     * departmentSecondName : 呼吸内科
     * doctorTitleName : 副主任医师
     * gender : 0
     * hospitalInfoName : 昆明市工人医院
     * qrCode : JY0100YS200111180041689EXB
     * unionCode : 68c6816382b54e0896ebae536e462f15
     * userName : 尹子敬
     */

    private String departmentName;
    private String departmentSecondName;
    private String doctorTitleName;
    private int gender;
    private String hospitalInfoName;
    private String qrCode;
    private String unionCode;
    private String userName;

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

    public String getDoctorTitleName() {
        return doctorTitleName;
    }

    public void setDoctorTitleName(String doctorTitleName) {
        this.doctorTitleName = doctorTitleName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHospitalInfoName() {
        return hospitalInfoName;
    }

    public void setHospitalInfoName(String hospitalInfoName) {
        this.hospitalInfoName = hospitalInfoName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUnionCode() {
        return unionCode;
    }

    public void setUnionCode(String unionCode) {
        this.unionCode = unionCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
