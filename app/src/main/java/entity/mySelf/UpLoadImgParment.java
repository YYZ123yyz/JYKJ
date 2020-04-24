package entity.mySelf;

/**
 * 上传医生资格认证图片
 */
public class UpLoadImgParment {
    private             String              loginDoctorPosition;
    private             String              operDoctorCode;
    private             String              operDoctorName;
    private             String              flagImgType;
    private             String              imgBase64Data;

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

    public String getFlagImgType() {
        return flagImgType;
    }

    public void setFlagImgType(String flagImgType) {
        this.flagImgType = flagImgType;
    }

    public String getImgBase64Data() {
        return imgBase64Data;
    }

    public void setImgBase64Data(String imgBase64Data) {
        this.imgBase64Data = imgBase64Data;
    }
}
