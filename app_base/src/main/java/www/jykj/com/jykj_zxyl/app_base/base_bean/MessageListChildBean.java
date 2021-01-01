package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 15:00
 */
public class MessageListChildBean implements Serializable {


    /**
     * detailsID : 1121
     * flagMsgRead : 1
     * msgTitle : 诊后留言
     * operCode :
     * pushMsgType : 8
     * pushParm : https://www.jiuyihtn.com/AppAssembly/prompted.html?reminderId=1121&operUserCode=642c11aa51ef4e8aa3e833df960bef1f&operUserName=邱新海医生&msgType=5
     * pushParmCode :
     * reminderId : 1121
     * sendMsgDate : 20年12月30日 14时43分
     * senderUserName : 平台AI管理员
     * userLogoUrl : https://www.jiuyihtn.com/AppExternalLinkImg/defaultImageShow.jpg
     */

    private String detailsID;
    private int flagMsgRead;
    private String msgTitle;
    private String operCode;
    private String pushMsgType;
    private String pushParm;
    private String pushParmCode;
    private int reminderId;
    private String sendMsgDate;
    private String senderUserName;
    private String userLogoUrl;

    public String getDetailsID() {
        return detailsID;
    }

    public void setDetailsID(String detailsID) {
        this.detailsID = detailsID;
    }

    public int getFlagMsgRead() {
        return flagMsgRead;
    }

    public void setFlagMsgRead(int flagMsgRead) {
        this.flagMsgRead = flagMsgRead;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getPushMsgType() {
        return pushMsgType;
    }

    public void setPushMsgType(String pushMsgType) {
        this.pushMsgType = pushMsgType;
    }

    public String getPushParm() {
        return pushParm;
    }

    public void setPushParm(String pushParm) {
        this.pushParm = pushParm;
    }

    public String getPushParmCode() {
        return pushParmCode;
    }

    public void setPushParmCode(String pushParmCode) {
        this.pushParmCode = pushParmCode;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getSendMsgDate() {
        return sendMsgDate;
    }

    public void setSendMsgDate(String sendMsgDate) {
        this.sendMsgDate = sendMsgDate;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }
}
