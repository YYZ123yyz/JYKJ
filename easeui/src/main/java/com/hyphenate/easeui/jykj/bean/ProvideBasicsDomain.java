package com.hyphenate.easeui.jykj.bean;



/**
 * 【基础数据】
 * 数据字典
 * 
 * @author JiaQ
 */
public class ProvideBasicsDomain extends SecondaryList implements java.io.Serializable {
	private int basicsDomainId;//编号
	private int baseCode;//基础编码
	private String baseName;//基础名称
	private int attrCode;//属性编码
	private String attrName;//属性名称
	private String selectState = "0";

	@Override
	public int getBasicsDomainId() {
		return basicsDomainId;
	}

	public void setBasicsDomainId(int basicsDomainId) {
		this.basicsDomainId = basicsDomainId;
	}

	@Override
	public int getBaseCode() {
		return baseCode;
	}

	public void setBaseCode(int baseCode) {
		this.baseCode = baseCode;
	}

	@Override
	public String getBaseName() {
		return baseName;
	}

	@Override
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	@Override
	public int getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(int attrCode) {
		this.attrCode = attrCode;
	}

	@Override
	public String getAttrName() {
		return attrName;
	}

	@Override
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	@Override
	public String getSelectState() {
		return selectState;
	}

	@Override
	public void setSelectState(String selectState) {
		this.selectState = selectState;
	}
}
