package entity.patientInfo;

public class MsgPushModel {

    private int msgModelId;//消息模板编号
    private String msgContent;//消息模板内容

    public int getMsgModelId() {
        return msgModelId;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgModelId(int msgModelId) {
        this.msgModelId = msgModelId;
    }
}
