package entity.liveroom;

import java.io.Serializable;

public class CoverInfo implements Serializable {
    private String loginUserPosition;
    private String requestClientType;
    private String operUserCode;
    private String operUserName;
    private String imgBase64Data;

    public String getLoginUserPosition() {
        return loginUserPosition;
    }

    public void setLoginUserPosition(String loginUserPosition) {
        this.loginUserPosition = loginUserPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOperUserCode() {
        return operUserCode;
    }

    public void setOperUserCode(String operUserCode) {
        this.operUserCode = operUserCode;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getImgBase64Data() {
        return imgBase64Data;
    }

    public void setImgBase64Data(String imgBase64Data) {
        this.imgBase64Data = imgBase64Data;
    }
}
