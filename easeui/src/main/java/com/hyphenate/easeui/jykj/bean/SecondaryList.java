package com.hyphenate.easeui.jykj.bean;

public class SecondaryList implements  java.io.Serializable {
    private int basicsDomainId;//编号
    private int  baseCode;//基础编码
    private String baseName;//基础名称
    private int  attrCode;//属性编码
    private String attrName;//属性名称
    private String selectState = "0";

    public int getBasicsDomainId() {
        return basicsDomainId;
    }

    public void setBasicsDomainId(int basicsDomainId) {
        this.basicsDomainId = basicsDomainId;
    }

    public int getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(int baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public int getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(int attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getSelectState() {
        return selectState;
    }

    public void setSelectState(String selectState) {
        this.selectState = selectState;
    }
}
