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

    private String startTime;
    private String cancelTime;
    private String appointMentProject;
    private String appointMentType;
    private String statusType;


    private String receiveTime;
    private String endTime;
    private String surplusTimes;


    private String patientType;

    public OrderMessage(String nickName,
                        String imageUrl,
                        String orderId,
                        String monitoringType,
                        String coach,
                        String signUpTime,
                        String price,
                        String singNo,
                        String orderType, //  订单类型
                        String messageType,//
                        String patientCode) {

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

    public OrderMessage(String nickName,
                        String imageUrl,
                        String orderId,
                        String startTime,
                        String cancelTime,
                        String appointMentProject,
                        String appointMentType,
                        String statusType,
                        String messageType) {
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.orderId = orderId;
        this.startTime = startTime;
        this.cancelTime = cancelTime;
        this.appointMentProject = appointMentProject;
        this.appointMentType = appointMentType;
        this.statusType=statusType;
        this.messageType = messageType;
    }


    public OrderMessage(String nickName,
                        String imageUrl,
                        String orderId,
                        String receiveTime,
                        String endTime,
                        String surplusTimes,
                        String appointMentProject,
                        String messageType){
        this.nickName=nickName;
        this.imageUrl=imageUrl;
        this.orderId=orderId;
        this.receiveTime=receiveTime;
        this.endTime=endTime;
        this.surplusTimes=surplusTimes;
        this.appointMentProject=appointMentProject;
        this.messageType=messageType;

    }


    public OrderMessage(
            String nickName,
            String imageUrl,
            String endTime,
                        String patientType,
                        String orderId,
                        String messageType){
        this.nickName=nickName;
        this.imageUrl=imageUrl;
        this.endTime=endTime;
        this.patientType=patientType;
        this.orderId=orderId;
        this.messageType=messageType;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getAppointMentProject() {
        return appointMentProject;
    }

    public void setAppointMentProject(String appointMentProject) {
        this.appointMentProject = appointMentProject;
    }

    public String getAppointMentType() {
        return appointMentType;
    }

    public void setAppointMentType(String appointMentType) {
        this.appointMentType = appointMentType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSurplusTimes() {
        return surplusTimes;
    }

    public void setSurplusTimes(String surplusTimes) {
        this.surplusTimes = surplusTimes;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }


}
