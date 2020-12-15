package entity.user;

public class UserInfo {
    private String userPhone;                          //用户手机号
    private String userPwd;                            //用户密码
    private String userLoginSmsVerify;                 //用户短信验证码
    private String tokenSmsVerify;                     //短信验证码Tokend
    private String loginDoctorPosition;
    private String loginClient;
    private String deviceToken;
    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    private String requestClientType;
    private String openId;

    public String getTokenSmsVerify() {
        return tokenSmsVerify;
    }

    public void setTokenSmsVerify(String tokenSmsVerify) {
        this.tokenSmsVerify = tokenSmsVerify;
    }

    public String getUserLoginSmsVerify() {
        return userLoginSmsVerify;
    }

    public void setUserLoginSmsVerify(String userLoginSmsVerify) {
        this.userLoginSmsVerify = userLoginSmsVerify;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getuserLoginSmsVerify(String userLoginSmsVerify) {
        return userLoginSmsVerify;
    }

    public void setuserLoginSmsVerify(String userLoginSmsVerify) {
        this.userPwd = userLoginSmsVerify;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
