package entity.basicDate;

/**
 * 获取联盟名称参数
 */
public class GetUnionNameParment {
    private         String          doctorName;         //医生名称
    private         String          province;         //所在省份
    private         String          city;         //所在城市
    private         String          area;         //所在区(县)
    private         String          hospitalName;         //	医院名称
    private         String          departmentName;         //一级科室名称
    private         String          departmentSecondName;         //二级科室名称

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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
}
