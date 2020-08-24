package entity.home.newsMessage;


import java.io.Serializable;

/**
 * 【消息类】
 * 未读消息数量统计(每种类型对应一个字段值)
 * 
 * @author JiaQ
 */

public class ProvideMsgPushReminderCount implements Serializable {

	/**
	 * msgTimeInterval : 5
	 * msgTypeCount01 : 0
	 * msgTypeCount02 : 1
	 * msgTypeCount03 : 0
	 * msgTypeCount04 : 0
	 * msgTypeCount05 : 0
	 * msgTypeCount06 : 0
	 * msgTypeCount07 : 0
	 * msgTypeCount08 : 0
	 * msgTypeCountSum : 1
	 * receiverUserCode : 7b5d2d0205164f12974a3e228f5a6083
	 * senderUserCode :
	 */

	private int msgTimeInterval;
	private int msgTypeCount01;
	private int msgTypeCount02;
	private int msgTypeCount03;
	private int msgTypeCount04;
	private int msgTypeCount05;
	private int msgTypeCount06;
	private int msgTypeCount07;
	private int msgTypeCount08;
	private int msgTypeCountSum;
	private String receiverUserCode;
	private String senderUserCode;

	public int getMsgTimeInterval() {
		return msgTimeInterval;
	}

	public void setMsgTimeInterval(int msgTimeInterval) {
		this.msgTimeInterval = msgTimeInterval;
	}

	public int getMsgTypeCount01() {
		return msgTypeCount01;
	}

	public void setMsgTypeCount01(int msgTypeCount01) {
		this.msgTypeCount01 = msgTypeCount01;
	}

	public int getMsgTypeCount02() {
		return msgTypeCount02;
	}

	public void setMsgTypeCount02(int msgTypeCount02) {
		this.msgTypeCount02 = msgTypeCount02;
	}

	public int getMsgTypeCount03() {
		return msgTypeCount03;
	}

	public void setMsgTypeCount03(int msgTypeCount03) {
		this.msgTypeCount03 = msgTypeCount03;
	}

	public int getMsgTypeCount04() {
		return msgTypeCount04;
	}

	public void setMsgTypeCount04(int msgTypeCount04) {
		this.msgTypeCount04 = msgTypeCount04;
	}

	public int getMsgTypeCount05() {
		return msgTypeCount05;
	}

	public void setMsgTypeCount05(int msgTypeCount05) {
		this.msgTypeCount05 = msgTypeCount05;
	}

	public int getMsgTypeCount06() {
		return msgTypeCount06;
	}

	public void setMsgTypeCount06(int msgTypeCount06) {
		this.msgTypeCount06 = msgTypeCount06;
	}

	public int getMsgTypeCount07() {
		return msgTypeCount07;
	}

	public void setMsgTypeCount07(int msgTypeCount07) {
		this.msgTypeCount07 = msgTypeCount07;
	}

	public int getMsgTypeCount08() {
		return msgTypeCount08;
	}

	public void setMsgTypeCount08(int msgTypeCount08) {
		this.msgTypeCount08 = msgTypeCount08;
	}

	public int getMsgTypeCountSum() {
		return msgTypeCountSum;
	}

	public void setMsgTypeCountSum(int msgTypeCountSum) {
		this.msgTypeCountSum = msgTypeCountSum;
	}

	public String getReceiverUserCode() {
		return receiverUserCode;
	}

	public void setReceiverUserCode(String receiverUserCode) {
		this.receiverUserCode = receiverUserCode;
	}

	public String getSenderUserCode() {
		return senderUserCode;
	}

	public void setSenderUserCode(String senderUserCode) {
		this.senderUserCode = senderUserCode;
	}
}