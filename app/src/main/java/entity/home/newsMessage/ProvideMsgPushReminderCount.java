package entity.home.newsMessage;


/**
 * 【消息类】
 * 未读消息数量统计(每种类型对应一个字段值)
 * 
 * @author JiaQ
 */

public class ProvideMsgPushReminderCount implements java.io.Serializable {
	private String senderUserCode;//发送人编码
	private String receiverUserCode;//接收人编码

	private Integer msgTypeCount01;//消息类型.4000101患者就诊;
	private Integer msgTypeCount02;//消息类型.4000102诊后留言;
	private Integer msgTypeCount03;//消息类型.4000103添加患者;
	private Integer msgTypeCount04;//消息类型.4000104联盟消息;
	private Integer msgTypeCount05;//消息类型.4000105医患圈消息;
	private Integer msgTypeCount06;//消息类型.4000106紧急提醒;
	private Integer msgTypeCount07;//消息类型.4000107患者签约;
	private Integer msgTypeCount08;//消息类型.4000108系统消息;
	
	private Integer msgTypeCountSum;//未读消息总数
	
	private int msgTimeInterval;//消息轮询时间间隔.单位:分钟.

	private	String	loginDoctorPosition;
	private String	searchDoctorCode;

	public String getLoginDoctorPosition() {
		return loginDoctorPosition;
	}

	public void setLoginDoctorPosition(String loginDoctorPosition) {
		this.loginDoctorPosition = loginDoctorPosition;
	}

	public String getSearchDoctorCode() {
		return searchDoctorCode;
	}

	public void setSearchDoctorCode(String searchDoctorCode) {
		this.searchDoctorCode = searchDoctorCode;
	}

	public String getSenderUserCode() {
		return senderUserCode;
	}

	public void setSenderUserCode(String senderUserCode) {
		this.senderUserCode = senderUserCode;
	}

	public String getReceiverUserCode() {
		return receiverUserCode;
	}

	public void setReceiverUserCode(String receiverUserCode) {
		this.receiverUserCode = receiverUserCode;
	}

	public Integer getMsgTypeCount01() {
		return msgTypeCount01;
	}

	public void setMsgTypeCount01(Integer msgTypeCount01) {
		this.msgTypeCount01 = msgTypeCount01;
	}

	public Integer getMsgTypeCount02() {
		return msgTypeCount02;
	}

	public void setMsgTypeCount02(Integer msgTypeCount02) {
		this.msgTypeCount02 = msgTypeCount02;
	}

	public Integer getMsgTypeCount03() {
		return msgTypeCount03;
	}

	public void setMsgTypeCount03(Integer msgTypeCount03) {
		this.msgTypeCount03 = msgTypeCount03;
	}

	public Integer getMsgTypeCount04() {
		return msgTypeCount04;
	}

	public void setMsgTypeCount04(Integer msgTypeCount04) {
		this.msgTypeCount04 = msgTypeCount04;
	}

	public Integer getMsgTypeCount05() {
		return msgTypeCount05;
	}

	public void setMsgTypeCount05(Integer msgTypeCount05) {
		this.msgTypeCount05 = msgTypeCount05;
	}

	public Integer getMsgTypeCount06() {
		return msgTypeCount06;
	}

	public void setMsgTypeCount06(Integer msgTypeCount06) {
		this.msgTypeCount06 = msgTypeCount06;
	}

	public Integer getMsgTypeCount07() {
		return msgTypeCount07;
	}

	public void setMsgTypeCount07(Integer msgTypeCount07) {
		this.msgTypeCount07 = msgTypeCount07;
	}

	public Integer getMsgTypeCount08() {
		return msgTypeCount08;
	}

	public void setMsgTypeCount08(Integer msgTypeCount08) {
		this.msgTypeCount08 = msgTypeCount08;
	}

	public Integer getMsgTypeCountSum() {
		return msgTypeCountSum;
	}

	public void setMsgTypeCountSum(Integer msgTypeCountSum) {
		this.msgTypeCountSum = msgTypeCountSum;
	}

	public Integer getMsgTimeInterval() {
		return msgTimeInterval;
	}

	public void setMsgTimeInterval(Integer msgTimeInterval) {
		this.msgTimeInterval = msgTimeInterval;
	}
}