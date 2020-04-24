package entity.mySelf;

public class OperPassWordParment {
    private             String                loginDoctorPosition;
    private             String                operDoctorCode;
    private             String                operDoctorName;
    private             String                oldPassWord;
    private             String                newPassWord;

    private             String                sendUserLinkPhone;
    private             String                bindingSmsVerifyData;
    private             String                bindingSmsVerifyTokenData;

    public String getSendUserLinkPhone() {
        return sendUserLinkPhone;
    }

    public void setSendUserLinkPhone(String sendUserLinkPhone) {
        this.sendUserLinkPhone = sendUserLinkPhone;
    }

    public String getBindingSmsVerifyData() {
        return bindingSmsVerifyData;
    }

    public void setBindingSmsVerifyData(String bindingSmsVerifyData) {
        this.bindingSmsVerifyData = bindingSmsVerifyData;
    }

    public String getBindingSmsVerifyTokenData() {
        return bindingSmsVerifyTokenData;
    }

    public void setBindingSmsVerifyTokenData(String bindingSmsVerifyTokenData) {
        this.bindingSmsVerifyTokenData = bindingSmsVerifyTokenData;
    }

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

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
}
