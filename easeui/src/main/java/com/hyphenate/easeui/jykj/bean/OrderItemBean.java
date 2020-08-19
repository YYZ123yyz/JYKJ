package com.hyphenate.easeui.jykj.bean;

public class OrderItemBean {

    /**
     * totlePrice : 280
     * configDetailTypeCode : 20
     * configDetailTypeName : 辅导类
     * duration : 10
     * mainConfigDetailName : 电话
     * rate : 3
     * rateUnitCode : DAY
     * mainConfigDetailCode : 40
     * durationUnitCode : FEN
     * value : 10
     * durationUnitName : 分钟
     * rateUnitName : 天
     */

    private double totlePrice;
    private String configDetailTypeCode;
    private String configDetailTypeName;
    private int duration;
    private String mainConfigDetailName;
    private int rate;
    private String rateUnitCode;
    private String mainConfigDetailCode;
    private String durationUnitCode;
    private int value;
    private String durationUnitName;
    private String rateUnitName;

    public double getTotlePrice() {
        return totlePrice;
    }

    public void setTotlePrice(double totlePrice) {
        this.totlePrice = totlePrice;
    }

    public void setTotlePrice(int totlePrice) {
        this.totlePrice = totlePrice;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMainConfigDetailName() {
        return mainConfigDetailName;
    }

    public void setMainConfigDetailName(String mainConfigDetailName) {
        this.mainConfigDetailName = mainConfigDetailName;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getRateUnitCode() {
        return rateUnitCode;
    }

    public void setRateUnitCode(String rateUnitCode) {
        this.rateUnitCode = rateUnitCode;
    }

    public String getMainConfigDetailCode() {
        return mainConfigDetailCode;
    }

    public void setMainConfigDetailCode(String mainConfigDetailCode) {
        this.mainConfigDetailCode = mainConfigDetailCode;
    }

    public String getDurationUnitCode() {
        return durationUnitCode;
    }

    public void setDurationUnitCode(String durationUnitCode) {
        this.durationUnitCode = durationUnitCode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDurationUnitName() {
        return durationUnitName;
    }

    public void setDurationUnitName(String durationUnitName) {
        this.durationUnitName = durationUnitName;
    }

    public String getRateUnitName() {
        return rateUnitName;
    }

    public void setRateUnitName(String rateUnitName) {
        this.rateUnitName = rateUnitName;
    }
}
