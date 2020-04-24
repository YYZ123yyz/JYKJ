package entity.home.newsMessage;

import java.util.Date;


/**
 * 【消息类】
 * 消息-消息推送提醒(发送日志)
 * 
 * @author JiaQ
 */

public class ProvideMsgPushReminder implements java.io.Serializable {
	private Integer reminderId;
	private String operCode;//操作编码(每条操作信息唯一性)
	private String msgCode;//消息编码(群发消息时,此编码所有接收人一致.)
	private Date msgCreateDate;//消息创建日期
	private String senderUserCode;//发送人编码
	private String senderUserName;//发送人姓名
	private String receiverUserCode;//接收人编码
	private String receiverUserName;//接收人姓名
	private Integer msgType;//消息类型.4000101患者就诊;4000102诊后留言;4000103添加患者;4000104联盟消息;4000105医患圈消息;4000106紧急提醒;4000107患者签约;4000108系统消息
	private Integer msgOper;//消息操作
	private Integer msgTimeInterval;//消息轮询时间间隔.单位:分钟.[使用消息推送(Eg.信鸽)方式时不需要]
	private String msgContent;//消息内容
	private Integer flagMsgRead;//消息是否已读.0:未读;1:已读;
	private Integer flagMsgTop;//是否置顶消息.即首页中提醒.0:否;1:是;
	private Integer flagOperBtn;//阅读消息时,是否需要带有操作按钮.0:不需要;1:需要;
	private String operBtnContent;//操作按钮内容,以[^]进行分割.Eg.接受^拒绝;确认^取消	
	private Date msgReadDate;//消息读取日期
	private String userLogoUrl;

	public String getUserLogoUrl() {
		return userLogoUrl;
	}

	public void setUserLogoUrl(String userLogoUrl) {
		this.userLogoUrl = userLogoUrl;
	}

	public Integer getReminderId() {
		return reminderId;
	}

	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Date getMsgCreateDate() {
		return msgCreateDate;
	}

	public void setMsgCreateDate(Date msgCreateDate) {
		this.msgCreateDate = msgCreateDate;
	}

	public String getSenderUserCode() {
		return senderUserCode;
	}

	public void setSenderUserCode(String senderUserCode) {
		this.senderUserCode = senderUserCode;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getReceiverUserCode() {
		return receiverUserCode;
	}

	public void setReceiverUserCode(String receiverUserCode) {
		this.receiverUserCode = receiverUserCode;
	}

	public String getReceiverUserName() {
		return receiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		this.receiverUserName = receiverUserName;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getMsgOper() {
		return msgOper;
	}

	public void setMsgOper(Integer msgOper) {
		this.msgOper = msgOper;
	}

	public Integer getMsgTimeInterval() {
		return msgTimeInterval;
	}

	public void setMsgTimeInterval(Integer msgTimeInterval) {
		this.msgTimeInterval = msgTimeInterval;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getFlagMsgRead() {
		return flagMsgRead;
	}

	public void setFlagMsgRead(Integer flagMsgRead) {
		this.flagMsgRead = flagMsgRead;
	}

	public Integer getFlagMsgTop() {
		return flagMsgTop;
	}

	public void setFlagMsgTop(Integer flagMsgTop) {
		this.flagMsgTop = flagMsgTop;
	}

	public Integer getFlagOperBtn() {
		return flagOperBtn;
	}

	public void setFlagOperBtn(Integer flagOperBtn) {
		this.flagOperBtn = flagOperBtn;
	}

	public String getOperBtnContent() {
		return operBtnContent;
	}

	public void setOperBtnContent(String operBtnContent) {
		this.operBtnContent = operBtnContent;
	}

	public Date getMsgReadDate() {
		return msgReadDate;
	}

	public void setMsgReadDate(Date msgReadDate) {
		this.msgReadDate = msgReadDate;
	}
}
