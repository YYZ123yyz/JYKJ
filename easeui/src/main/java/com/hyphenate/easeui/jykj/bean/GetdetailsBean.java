package com.hyphenate.easeui.jykj.bean;

import java.util.List;

public class GetdetailsBean {


    /**
     * age : 24
     * gender : 1
     * genderName : 未知
     * mainDoctorAlias : Doctor_091360
     * mainDoctorCode : ea7566487db54d7885bbacf2d15739bf
     * mainDoctorName : Doctor_091360
     * mainPatientCode : ae0b55c6be7f4b1588f0ea73b8653d26
     * mainUserName : A～杨
     * mainUserNameAlias : A～杨
     * orderDetailList : [{"configDetailTypeCode":"10","configDetailTypeName":"监测类","duration":0,"durationUnitCode":"","durationUnitName":"","mainConfigDetailCode":"110","mainConfigDetailName":"血压","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":1,"rateUnitCode":"90051003","rateUnitName":"天","signOrderConfigDetailCode":"95c00fa1783b41178223f2306c8109df","signOrderConfigDetailId":1265,"totlePrice":12},{"configDetailTypeCode":"10","configDetailTypeName":"监测类","duration":0,"durationUnitCode":"","durationUnitName":"","mainConfigDetailCode":"130","mainConfigDetailName":"生命体征(体温、脉搏、心率、呼吸)","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":1,"rateUnitCode":"90051003","rateUnitName":"天","signOrderConfigDetailCode":"72d404c1a4f74252b7305672c6a269bf","signOrderConfigDetailId":1266,"totlePrice":30},{"configDetailTypeCode":"10","configDetailTypeName":"监测类","duration":0,"durationUnitCode":"","durationUnitName":"","mainConfigDetailCode":"150","mainConfigDetailName":"体重检测","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":1,"rateUnitCode":"90051003","rateUnitName":"天","signOrderConfigDetailCode":"7f8e27e20feb43baa15cfa4bd6423ef2","signOrderConfigDetailId":1267,"totlePrice":50},{"configDetailTypeCode":"20","configDetailTypeName":"辅导类","duration":0,"durationUnitCode":"","durationUnitName":"","mainConfigDetailCode":"10","mainConfigDetailName":"图文","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":4,"rateUnitCode":"90051005","rateUnitName":"月","signOrderConfigDetailCode":"61dca761f73f463ab145d99278b97a0e","signOrderConfigDetailId":1268,"totlePrice":2100},{"configDetailTypeCode":"20","configDetailTypeName":"辅导类","duration":5,"durationUnitCode":"90051001","durationUnitName":"分钟","mainConfigDetailCode":"20","mainConfigDetailName":"音频","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":4,"rateUnitCode":"90051005","rateUnitName":"月","signOrderConfigDetailCode":"8f910725d62f49bbb77c599e86d67573","signOrderConfigDetailId":1269,"totlePrice":200},{"configDetailTypeCode":"20","configDetailTypeName":"辅导类","duration":5,"durationUnitCode":"90051001","durationUnitName":"分钟","mainConfigDetailCode":"30","mainConfigDetailName":"视频","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":4,"rateUnitCode":"90051005","rateUnitName":"月","signOrderConfigDetailCode":"aa0f9f75c7924829a408c08358643e2b","signOrderConfigDetailId":1270,"totlePrice":300},{"configDetailTypeCode":"20","configDetailTypeName":"辅导类","duration":5,"durationUnitCode":"90051001","durationUnitName":"分钟","mainConfigDetailCode":"40","mainConfigDetailName":"电话","mainSignCode":"3965856941c14254a15abccf3f1925c2","rate":4,"rateUnitCode":"90051005","rateUnitName":"月","signOrderConfigDetailCode":"8e1c6f51b19b4c9daf293af30ee86931","signOrderConfigDetailId":1271,"totlePrice":400}]
     * signCode : 3965856941c14254a15abccf3f1925c2
     * signDuration : 12
     * signDurationUnit : 月
     * signEndTime : 1628092800000
     * signId : 21
     * signNo : 010620200805182811853732930949592
     * signPrice : 12092.0
     * signStartTime : 1596556800000
     * signStatus : 10
     * signUnit : 90051005
     * version : 1
     */

    private int age;
    private int gender;
    private String genderName;
    private String mainDoctorAlias;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainUserName;
    private String mainUserNameAlias;
    private String signCode;
    private int signDuration;
    private String signDurationUnit;
    private long signEndTime;
    private int signId;
    private String signNo;
    private double signPrice;
    private long signStartTime;
    private String signStatus;
    private String signUnit;
    private int version;
    private List<OrderDetailListBean> orderDetailList;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getMainDoctorAlias() {
        return mainDoctorAlias;
    }

    public void setMainDoctorAlias(String mainDoctorAlias) {
        this.mainDoctorAlias = mainDoctorAlias;
    }

    public String getMainDoctorCode() {
        return mainDoctorCode;
    }

    public void setMainDoctorCode(String mainDoctorCode) {
        this.mainDoctorCode = mainDoctorCode;
    }

    public String getMainDoctorName() {
        return mainDoctorName;
    }

    public void setMainDoctorName(String mainDoctorName) {
        this.mainDoctorName = mainDoctorName;
    }

    public String getMainPatientCode() {
        return mainPatientCode;
    }

    public void setMainPatientCode(String mainPatientCode) {
        this.mainPatientCode = mainPatientCode;
    }

    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    public String getMainUserNameAlias() {
        return mainUserNameAlias;
    }

    public void setMainUserNameAlias(String mainUserNameAlias) {
        this.mainUserNameAlias = mainUserNameAlias;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public int getSignDuration() {
        return signDuration;
    }

    public void setSignDuration(int signDuration) {
        this.signDuration = signDuration;
    }

    public String getSignDurationUnit() {
        return signDurationUnit;
    }

    public void setSignDurationUnit(String signDurationUnit) {
        this.signDurationUnit = signDurationUnit;
    }

    public long getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(long signEndTime) {
        this.signEndTime = signEndTime;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public double getSignPrice() {
        return signPrice;
    }

    public void setSignPrice(double signPrice) {
        this.signPrice = signPrice;
    }

    public long getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(long signStartTime) {
        this.signStartTime = signStartTime;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<OrderDetailListBean> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailListBean> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public static class OrderDetailListBean {
        /**
         * configDetailTypeCode : 10
         * configDetailTypeName : 监测类
         * duration : 0
         * durationUnitCode :
         * durationUnitName :
         * mainConfigDetailCode : 110
         * mainConfigDetailName : 血压
         * mainSignCode : 3965856941c14254a15abccf3f1925c2
         * rate : 1
         * rateUnitCode : 90051003
         * rateUnitName : 天
         * signOrderConfigDetailCode : 95c00fa1783b41178223f2306c8109df
         * signOrderConfigDetailId : 1265
         * totlePrice : 12.0
         */

        private String configDetailTypeCode;
        private String configDetailTypeName;
        private int duration;
        private String durationUnitCode;
        private String durationUnitName;
        private String mainConfigDetailCode;
        private String mainConfigDetailName;
        private String mainSignCode;
        private int rate;
        private String rateUnitCode;
        private String rateUnitName;
        private String signOrderConfigDetailCode;
        private int signOrderConfigDetailId;
        private double totlePrice;

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

        public String getMainSignCode() {
            return mainSignCode;
        }

        public void setMainSignCode(String mainSignCode) {
            this.mainSignCode = mainSignCode;
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

        public String getRateUnitName() {
            return rateUnitName;
        }

        public void setRateUnitName(String rateUnitName) {
            this.rateUnitName = rateUnitName;
        }

        public String getSignOrderConfigDetailCode() {
            return signOrderConfigDetailCode;
        }

        public void setSignOrderConfigDetailCode(String signOrderConfigDetailCode) {
            this.signOrderConfigDetailCode = signOrderConfigDetailCode;
        }

        public int getSignOrderConfigDetailId() {
            return signOrderConfigDetailId;
        }

        public void setSignOrderConfigDetailId(int signOrderConfigDetailId) {
            this.signOrderConfigDetailId = signOrderConfigDetailId;
        }

        public double getTotlePrice() {
            return totlePrice;
        }

        public void setTotlePrice(double totlePrice) {
            this.totlePrice = totlePrice;
        }
    }
}
