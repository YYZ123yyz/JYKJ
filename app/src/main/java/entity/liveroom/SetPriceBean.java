package entity.liveroom;

import java.io.Serializable;

public class SetPriceBean implements Serializable {
    private String loginUserPosition;
    private String requestClientType;
    private String operUserCode;
    private String operUserName;
    private float extendBroadcastPrice;
    private String detailsCode;

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

    public float getExtendBroadcastPrice() {
        return extendBroadcastPrice;
    }

    public void setExtendBroadcastPrice(float extendBroadcastPrice) {
        this.extendBroadcastPrice = extendBroadcastPrice;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }
}
