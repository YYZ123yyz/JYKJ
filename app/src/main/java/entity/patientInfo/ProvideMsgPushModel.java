package entity.patientInfo;

import java.util.Date;


/**
 * 【消息类】
 * 消息-消息推送内容模板
 * 
 * @author JiaQ
 */

public class ProvideMsgPushModel implements java.io.Serializable {
	private Integer msgModelId;//消息模板编号
	private Integer msgModelType;//消息模板类型(大类)
	private Integer msgModelTypeName;//消息模板名称(大类)
	private Integer msgModelTypeSecond;//消息模板类型(二级类)
	private Integer msgModelTypeSecondName;//消息模板名称(二级类)
	private String msgContent;//消息模板内容
	private Integer sort;//排序
	  
	private Integer flagDel;
	private String remark;
	private Date createDate;
	private String createMan;
	private Date updateDate;
	private String updateMan;

	public Integer getMsgModelId() {
		return msgModelId;
	}

	public void setMsgModelId(Integer msgModelId) {
		this.msgModelId = msgModelId;
	}

	public Integer getMsgModelType() {
		return msgModelType;
	}

	public void setMsgModelType(Integer msgModelType) {
		this.msgModelType = msgModelType;
	}

	public Integer getMsgModelTypeName() {
		return msgModelTypeName;
	}

	public void setMsgModelTypeName(Integer msgModelTypeName) {
		this.msgModelTypeName = msgModelTypeName;
	}

	public Integer getMsgModelTypeSecond() {
		return msgModelTypeSecond;
	}

	public void setMsgModelTypeSecond(Integer msgModelTypeSecond) {
		this.msgModelTypeSecond = msgModelTypeSecond;
	}

	public Integer getMsgModelTypeSecondName() {
		return msgModelTypeSecondName;
	}

	public void setMsgModelTypeSecondName(Integer msgModelTypeSecondName) {
		this.msgModelTypeSecondName = msgModelTypeSecondName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getFlagDel() {
		return flagDel;
	}

	public void setFlagDel(Integer flagDel) {
		this.flagDel = flagDel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateMan() {
		return createMan;
	}

	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateMan() {
		return updateMan;
	}

	public void setUpdateMan(String updateMan) {
		this.updateMan = updateMan;
	}
}
