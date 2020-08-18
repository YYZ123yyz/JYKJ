package com.hyphenate.easeui.jykj.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SignPatientDoctorOrderConfigDetail implements java.io.Serializable{

    private  Integer signOrderConfigDetailId;  //
    private  String signOrderConfigDetailCode;  //签约和配置明细对应表编码
    private  String mainSignCode;  //签约表编码
    private  String configDetailTypeCode;  //配置明细类型编码：检测类型、辅导类型
    private  String configDetailTypeName;  //配置明细类型名称：检测类型、辅导类型
    private  String mainConfigDetailCode;  //配置明细表编码
    private  String mainConfigDetailName;  //配置明细表名称
    private  Integer value;  //次数
    private  Integer rate;   //频次
    private  String rateUnitCode;  //频次单位编码
    private  String rateUnitName;  //频次单位名称
    private  Integer duration;  //时长
    private  String durationUnitCode;  //时长单位编码
    private  String durationUnitName;  //时长单位名称
    private BigDecimal totlePrice;  //单项合计价格
    private  Integer flagDel;  //删除标识.1:正常;0:删除;
    private  String remark;  //备注信息
    private Date createDate;  //创建日期
    private  String createMan;  //创建人
    private  Date updateDate;  //修改日期
    private  String updateMan;  //修改人


    public Integer getSignOrderConfigDetailId() {
        return signOrderConfigDetailId;
    }

    public void setSignOrderConfigDetailId(Integer signOrderConfigDetailId) {
        this.signOrderConfigDetailId = signOrderConfigDetailId;
    }

    public String getSignOrderConfigDetailCode() {
        return signOrderConfigDetailCode;
    }

    public void setSignOrderConfigDetailCode(String signOrderConfigDetailCode) {
        this.signOrderConfigDetailCode = signOrderConfigDetailCode;
    }

    public String getMainSignCode() {
        return mainSignCode;
    }

    public void setMainSignCode(String mainSignCode) {
        this.mainSignCode = mainSignCode;
    }

    public String getConfigDetailTypeCode() {
        return configDetailTypeCode;
    }

    public void setConfigDetailTypeCode(String configDetailTypeCode) {
        this.configDetailTypeCode = configDetailTypeCode;
    }

    public String getConfigDetailTypeName() {
        return configDetailTypeName;
    }

    public void setConfigDetailTypeName(String configDetailTypeName) {
        this.configDetailTypeName = configDetailTypeName;
    }

    public String getMainConfigDetailCode() {
        return mainConfigDetailCode;
    }

    public void setMainConfigDetailCode(String mainConfigDetailCode) {
        this.mainConfigDetailCode = mainConfigDetailCode;
    }

    public String getMainConfigDetailName() {
        return mainConfigDetailName;
    }

    public void setMainConfigDetailName(String mainConfigDetailName) {
        this.mainConfigDetailName = mainConfigDetailName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getRateUnitCode() {
        return rateUnitCode;
    }

    public void setRateUnitCode(String rateUnitCode) {
        this.rateUnitCode = rateUnitCode;
    }

    public String getRateUnitName() {
        return rateUnitName;
    }

    public void setRateUnitName(String rateUnitName) {
        this.rateUnitName = rateUnitName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDurationUnitCode() {
        return durationUnitCode;
    }

    public void setDurationUnitCode(String durationUnitCode) {
        this.durationUnitCode = durationUnitCode;
    }

    public String getDurationUnitName() {
        return durationUnitName;
    }

    public void setDurationUnitName(String durationUnitName) {
        this.durationUnitName = durationUnitName;
    }

    public BigDecimal getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(BigDecimal totlePrice) {
        this.totlePrice = totlePrice;
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