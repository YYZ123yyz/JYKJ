package entity.liveroom;

import java.io.Serializable;

public class BroadCheckResp implements Serializable {
    private String chatRoomCode;
    private String detailsCode;
    private String flagBroadcastState;
    private String pullUrl;
    private String pushUrl;
    private String userCode;
    private String userName;

    public String getChatRoomCode() {
        return chatRoomCode;
    }

    public void setChatRoomCode(String chatRoomCode) {
        this.chatRoomCode = chatRoomCode;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }

    public String getFlagBroadcastState() {
        return flagBroadcastState;
    }

    public void setFlagBroadcastState(String flagBroadcastState) {
        this.flagBroadcastState = flagBroadcastState;
    }

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
