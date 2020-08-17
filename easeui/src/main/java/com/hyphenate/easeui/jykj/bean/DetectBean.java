package com.hyphenate.easeui.jykj.bean;

public class DetectBean implements java.io.Serializable{

    /**
     * configDetailCode : 001
     * configDetailId : 1
     * configDetailName : 血压
     * configDetailTypeCode : 001
     * configDetailTypeName : 健康检测
     * price : 10.0
     */
    private String configDetailCode;
    private int configDetailId;
    private String configDetailName;
    private String configDetailTypeCode;
    private String configDetailTypeName;
    private double price;
    private int value=1;
    private int minute;//分钟数
    private int frequency;//次数
    private int months;//月数
    private double totalPrice;
    private boolean choice;			//是否选中

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public String getConfigDetailCode() {
        return configDetailCode;
    }

    public void setConfigDetailCode(String configDetailCode) {
        this.configDetailCode = configDetailCode;
    }

    public int getConfigDetailId() {
        return configDetailId;
    }

    public void setConfigDetailId(int configDetailId) {
        this.configDetailId = configDetailId;
    }

    public String getConfigDetailName() {
        return configDetailName;
    }

    public void setConfigDetailName(String configDetailName) {
        this.configDetailName = configDetailName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "DetectBean{" +
                "configDetailCode='" + configDetailCode + '\'' +
                ", configDetailId=" + configDetailId +
                ", configDetailName='" + configDetailName + '\'' +
                ", configDetailTypeCode='" + configDetailTypeCode + '\'' +
                ", configDetailTypeName='" + configDetailTypeName + '\'' +
                ", price=" + price +
                ", choice=" + choice +
                '}';
    }
}
