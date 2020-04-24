package entity.unionInfo;

/**
 * 上传联盟图标参数
 */
public class UpLoadUnionIconParment {
    private             String               loginDoctorPosition;
    private             String               operDoctorCode;
    private             String               operDoctorName;
    private             String               unionId;
    private             String               unionCode;
    private             String               base64Data;

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getOperDoctorCode() {
        return operDoctorCode;
    }

    public void setOperDoctorCode(String operDoctorCode) {
        this.operDoctorCode = operDoctorCode;
    }

    public String getOperDoctorName() {
        return operDoctorName;
    }

    public void setOperDoctorName(String operDoctorName) {
        this.operDoctorName = operDoctorName;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionCode() {
        return unionCode;
    }

    public void setUnionCode(String unionCode) {
        this.unionCode = unionCode;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
}
