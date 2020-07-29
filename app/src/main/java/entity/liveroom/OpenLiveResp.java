package entity.liveroom;

import java.io.Serializable;

public class OpenLiveResp implements Serializable {
    private String chatRoomCode;
    private String pullUrl;
    private String pushUrl;

    public String getChatRoomCode() {
        return chatRoomCode;
    }

    public void setChatRoomCode(String chatRoomCode) {
        this.chatRoomCode = chatRoomCode;
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
}
