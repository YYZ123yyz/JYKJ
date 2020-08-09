package com.hyphenate.easeui.jykj.bean;

public class MinuteList implements  java.io.Serializable {
    private Integer basicsDomainId;//编号
    private Integer baseCode;//基础编码
    private String baseName;//基础名称
    private Integer attrCode;//属性编码
    private String attrName;//属性名称
    private String selectState = "0";


    public Integer getBasicsDomainId() {
        return basicsDomainId;
    }

    public void setBasicsDomainId(Integer basicsDomainId) {
        this.basicsDomainId = basicsDomainId;
    }

    public Integer getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(Integer baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public Integer getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(Integer attrCode) {
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
