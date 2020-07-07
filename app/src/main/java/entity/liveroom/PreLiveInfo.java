package entity.liveroom;

import java.io.Serializable;

public class PreLiveInfo implements Serializable {
    private String detailsId;
    private String detailsCode;
    private String userCode;
    private String broadcastUserLogoUrl;
    private String broadcastUserName;
    private String broadcastTypeName;
    private String broadcastTitle;
    private String broadcastCoverImgUrl;
    private String keywordsName;
    private String className;
    private String pullUrl;
    private String chatRoomCode;
    private String extendBroadcastFollowNum;
    private String extendBroadcastPriceShow;

    public String getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getBroadcastUserLogoUrl() {
        return broadcastUserLogoUrl;
    }

    public void setBroadcastUserLogoUrl(String broadcastUserLogoUrl) {
        this.broadcastUserLogoUrl = broadcastUserLogoUrl;
    }

    public String getBroadcastUserName() {
        return broadcastUserName;
    }

    public void setBroadcastUserName(String broadcastUserName) {
        this.broadcastUserName = broadcastUserName;
    }

    public String getBroadcastTypeName() {
        return broadcastTypeName;
    }

    public void setBroadcastTypeName(String broadcastTypeName) {
        this.broadcastTypeName = broadcastTypeName;
    }

    public String getBroadcastTitle() {
        return broadcastTitle;
    }

    public void setBroadcastTitle(String broadcastTitle) {
        this.broadcastTitle = broadcastTitle;
    }

    public String getBroadcastCoverImgUrl() {
        return broadcastCoverImgUrl;
    }

    public void setBroadcastCoverImgUrl(String broadcastCoverImgUrl) {
        this.broadcastCoverImgUrl = broadcastCoverImgUrl;
    }

    public String getKeywordsName() {
        return keywordsName;
    }

    public void setKeywordsName(String keywordsName) {
        this.keywordsName = keywordsName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getChatRoomCode() {
        return chatRoomCode;
    }

    public void setChatRoomCode(String chatRoomCode) {
        this.chatRoomCode = chatRoomCode;
    }

    public String getExtendBroadcastFollowNum() {
        return extendBroadcastFollowNum;
    }

    public void setExtendBroadcastFollowNum(String extendBroadcastFollowNum) {
        this.extendBroadcastFollowNum = extendBroadcastFollowNum;
    }

    public String getExtendBroadcastPriceShow() {
        return extendBroadcastPriceShow;
    }

    public void setExtendBroadcastPriceShow(String extendBroadcastPriceShow) {
        this.extendBroadcastPriceShow = extendBroadcastPriceShow;
    }
}
