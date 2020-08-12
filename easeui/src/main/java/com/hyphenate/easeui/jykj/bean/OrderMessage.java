package com.hyphenate.easeui.jykj.bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 19:17
 */
public class OrderMessage implements Serializable {
    private String nickName;
    private String imageUrl;
    private String orderId;
    private String monitoringType;
    private String coach;
    private String signUpTime;
    private String price;
    private String singNo;
    private String orderType;
    private String messageType;
    private String patientCode;

    public OrderMessage(String nickName, String imageUrl, String orderId, String monitoringType, String coach, String signUpTime, String price, String singNo, String orderType, String messageType, String patientCode) {
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.orderId = orderId;
        this.monitoringType = monitoringType;
        this.coach = coach;
        this.signUpTime = signUpTime;
        this.price = price;
        this.singNo = singNo;
        this.orderType = orderType;
        this.messageType = messageType;
        this.patientCode = patientCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSingNo() {
        return singNo;
    }

    public void setSingNo(String singNo) {
        this.singNo = singNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMonitoringType() {
        return monitoringType;
    }

    public void setMonitoringType(String monitoringType) {
        this.monitoringType = monitoringType;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
