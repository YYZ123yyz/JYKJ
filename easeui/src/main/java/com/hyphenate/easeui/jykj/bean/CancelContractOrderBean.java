package com.hyphenate.easeui.jykj.bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-13 18:42
 */
public class CancelContractOrderBean {


    /**
     * age : 0
     * audioCaochRateUnitCode :
     * audioCaochRateUnitName :
     * audioCoachDuration : 0
     * audioCoachDurationUnitCode :
     * audioCoachDurationUnitName :
     * audioCoachRate : 0
     * audioNum : 0
     * audioTotle : 0
     * callCaochRateUnitCode : 1
     * callCaochRateUnitName : 天
     * callCoachDuration : 1
     * callCoachDurationUnitCode : null
     * callCoachDurationUnitName : 分钟
     * callCoachRate : 1
     * callNum : 1
     * callTotle : 6
     * detectRate : 1
     * detectRateUnitCode : 1
     * detectRateUnitName : 天
     * figureTextCaochRateUnitCode :
     * figureTextCaochRateUnitName :
     * figureTextCoachRate : 0
     * figureTextNum : 0
     * figureTextTotle : 0
     * gender : 1
     * genderName : 男
     * mainDoctorAlias : 未设置
     * mainDoctorCode : 7b5d2d0205164f12974a3e228f5a6083
     * mainDoctorName : 医生测试002
     * mainPatientCode : 00440e854c0e46d38596b54593312a61
     * mainUserName : 患者测试账号00011
     * mainUserNameAlias : 患者测试账号00011
     * orderDetailList : [{"configDetailTypeCode":"10","configDetailTypeName":"监测类","mainConfigDetailCode":"150","mainConfigDetailName":"体重检测","mainSignCode":"f3731ef3526f470a97227221b339e680","rate":1,"rateUnitCode":"1","rateUnitName":"天","signOrderConfigDetailCode":"9ea8efafdfdb460dbaf29d3483752a46","signOrderConfigDetailId":2121,"totlePrice":50},{"configDetailTypeCode":"10","configDetailTypeName":"监测类","mainConfigDetailCode":"160","mainConfigDetailName":"睡眠","mainSignCode":"f3731ef3526f470a97227221b339e680","rate":1,"rateUnitCode":"1","rateUnitName":"天","signOrderConfigDetailCode":"713cc0a9d6ef4e41b4f105c90849fa3a","signOrderConfigDetailId":2122,"totlePrice":60},{"configDetailTypeCode":"20","configDetailTypeName":"辅导类","duration":1,"durationUnitCode":"null","durationUnitName":"分钟","mainConfigDetailCode":"40","mainConfigDetailName":"电话","mainSignCode":"f3731ef3526f470a97227221b339e680","rate":1,"rateUnitCode":"1","rateUnitName":"天","signOrderConfigDetailCode":"7cd86d0daa8e483ab23adb88acad07b4","signOrderConfigDetailId":2123,"totlePrice":400,"value":1}]
     * refuseReasonClassCode : 90003001
     * refuseReasonClassName : 患者医从性不高
     * refuseRemark : 不好好
     * signCode : f3731ef3526f470a97227221b339e680
     * signDuration : 6
     * signDurationUnit : 月
     * signEndTime : 1613145600000
     * signId : 167
     * signNo : 010620200813174123145777799413594
     * signOtherServiceCode : 150,160
     * signOtherServiceName : 体重检测,睡眠
     * signPrice : 510.0
     * signStartTime : 1597248000000
     * signStatus : 30
     * signStatusName :
     * signUnit : 月
     * version : 5
     * videoCaochRateUnitCode :
     * videoCaochRateUnitName :
     * videoCoachDuration : 0
     * videoCoachDurationUnitCode :
     * videoCoachDurationUnitName :
     * videoCoachRate : 0
     * videoNum : 0
     * videoTotle : 0
     */

    private int age;
    private String audioCaochRateUnitCode;
    private String audioCaochRateUnitName;
    private int audioCoachDuration;
    private String audioCoachDurationUnitCode;
    private String audioCoachDurationUnitName;
    private int audioCoachRate;
    private int audioNum;
    private int audioTotle;
    private String callCaochRateUnitCode;
    private String callCaochRateUnitName;
    private int callCoachDuration;
    private String callCoachDurationUnitCode;
    private String callCoachDurationUnitName;
    private int callCoachRate;
    private int callNum;
    private int callTotle;
    private int detectRate;
    private String detectRateUnitCode;
    private String detectRateUnitName;
    private String figureTextCaochRateUnitCode;
    private String figureTextCaochRateUnitName;
    private int figureTextCoachRate;
    private int figureTextNum;
    private int figureTextTotle;
    private int gender;
    private String genderName;
    private String mainDoctorAlias;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainUserName;
    private String mainUserNameAlias;
    private String refuseReasonClassCode;
    private String refuseReasonClassName;
    private String refuseRemark;
    private String signCode;
    private int signDuration;
    private String signDurationUnit;
    private long signEndTime;
    private int signId;
    private String signNo;
    private String signOtherServiceCode;
    private String signOtherServiceName;
    private double signPrice;
    private long signStartTime;
    private String signStatus;
    private String signStatusName;
    private String signUnit;
    private int version;
    private String videoCaochRateUnitCode;
    private String videoCaochRateUnitName;
    private int videoCoachDuration;
    private String videoCoachDurationUnitCode;
    private String videoCoachDurationUnitName;
    private int videoCoachRate;
    private int videoNum;
    private int videoTotle;
    //解约原因-患者发起
    private String relieveReasonClassCode;
    private String relieveReasonClassName;
    private String relieveRemark;

    //解约原因-医生发起
    private String relieveReasonClassCodeD;
    private String relieveReasonClassNameD;
    private String relieveRemarkD;
    //拒绝原因(签约拒绝)
    private String rejectReasonClassCode;
    private String rejectReasonClassName;
    private String rejectRemark;
    //拒绝原因(解约发起拒绝)
    private String rejectReasonClassCodeJ;
    private String rejectReasonClassNameJ;
    private String rejectRemarkJ;

    private List<OrderDetailListBean> orderDetailList;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAudioCaochRateUnitCode() {
        return audioCaochRateUnitCode;
    }

    public void setAudioCaochRateUnitCode(String audioCaochRateUnitCode) {
        this.audioCaochRateUnitCode = audioCaochRateUnitCode;
    }

    public String getAudioCaochRateUnitName() {
        return audioCaochRateUnitName;
    }

    public void setAudioCaochRateUnitName(String audioCaochRateUnitName) {
        this.audioCaochRateUnitName = audioCaochRateUnitName;
    }

    public int getAudioCoachDuration() {
        return audioCoachDuration;
    }

    public void setAudioCoachDuration(int audioCoachDuration) {
        this.audioCoachDuration = audioCoachDuration;
    }

    public String getAudioCoachDurationUnitCode() {
        return audioCoachDurationUnitCode;
    }

    public void setAudioCoachDurationUnitCode(String audioCoachDurationUnitCode) {
        this.audioCoachDurationUnitCode = audioCoachDurationUnitCode;
    }

    public String getAudioCoachDurationUnitName() {
        return audioCoachDurationUnitName;
    }

    public void setAudioCoachDurationUnitName(String audioCoachDurationUnitName) {
        this.audioCoachDurationUnitName = audioCoachDurationUnitName;
    }

    public int getAudioCoachRate() {
        return audioCoachRate;
    }

    public void setAudioCoachRate(int audioCoachRate) {
        this.audioCoachRate = audioCoachRate;
    }

    public int getAudioNum() {
        return audioNum;
    }

    public void setAudioNum(int audioNum) {
        this.audioNum = audioNum;
    }

    public int getAudioTotle() {
        return audioTotle;
    }

    public void setAudioTotle(int audioTotle) {
        this.audioTotle = audioTotle;
    }

    public String getCallCaochRateUnitCode() {
        return callCaochRateUnitCode;
    }

    public void setCallCaochRateUnitCode(String callCaochRateUnitCode) {
        this.callCaochRateUnitCode = callCaochRateUnitCode;
    }

    public String getCallCaochRateUnitName() {
        return callCaochRateUnitName;
    }

    public void setCallCaochRateUnitName(String callCaochRateUnitName) {
        this.callCaochRateUnitName = callCaochRateUnitName;
    }

    public int getCallCoachDuration() {
        return callCoachDuration;
    }

    public void setCallCoachDuration(int callCoachDuration) {
        this.callCoachDuration = callCoachDuration;
    }

    public String getCallCoachDurationUnitCode() {
        return callCoachDurationUnitCode;
    }

    public void setCallCoachDurationUnitCode(String callCoachDurationUnitCode) {
        this.callCoachDurationUnitCode = callCoachDurationUnitCode;
    }

    public String getCallCoachDurationUnitName() {
        return callCoachDurationUnitName;
    }

    public void setCallCoachDurationUnitName(String callCoachDurationUnitName) {
        this.callCoachDurationUnitName = callCoachDurationUnitName;
    }

    public int getCallCoachRate() {
        return callCoachRate;
    }

    public void setCallCoachRate(int callCoachRate) {
        this.callCoachRate = callCoachRate;
    }

    public int getCallNum() {
        return callNum;
    }

    public void setCallNum(int callNum) {
        this.callNum = callNum;
    }

    public int getCallTotle() {
        return callTotle;
    }

    public void setCallTotle(int callTotle) {
        this.callTotle = callTotle;
    }

    public int getDetectRate() {
        return detectRate;
    }

    public void setDetectRate(int detectRate) {
        this.detectRate = detectRate;
    }

    public String getDetectRateUnitCode() {
        return detectRateUnitCode;
    }

    public void setDetectRateUnitCode(String detectRateUnitCode) {
        this.detectRateUnitCode = detectRateUnitCode;
    }

    public String getDetectRateUnitName() {
        return detectRateUnitName;
    }

    public void setDetectRateUnitName(String detectRateUnitName) {
        this.detectRateUnitName = detectRateUnitName;
    }

    public String getFigureTextCaochRateUnitCode() {
        return figureTextCaochRateUnitCode;
    }

    public void setFigureTextCaochRateUnitCode(String figureTextCaochRateUnitCode) {
        this.figureTextCaochRateUnitCode = figureTextCaochRateUnitCode;
    }

    public String getFigureTextCaochRateUnitName() {
        return figureTextCaochRateUnitName;
    }

    public void setFigureTextCaochRateUnitName(String figureTextCaochRateUnitName) {
        this.figureTextCaochRateUnitName = figureTextCaochRateUnitName;
    }

    public int getFigureTextCoachRate() {
        return figureTextCoachRate;
    }

    public void setFigureTextCoachRate(int figureTextCoachRate) {
        this.figureTextCoachRate = figureTextCoachRate;
    }

    public int getFigureTextNum() {
        return figureTextNum;
    }

    public void setFigureTextNum(int figureTextNum) {
        this.figureTextNum = figureTextNum;
    }

    public int getFigureTextTotle() {
        return figureTextTotle;
    }

    public void setFigureTextTotle(int figureTextTotle) {
        this.figureTextTotle = figureTextTotle;
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

    public String getRefuseReasonClassCode() {
        return refuseReasonClassCode;
    }

    public void setRefuseReasonClassCode(String refuseReasonClassCode) {
        this.refuseReasonClassCode = refuseReasonClassCode;
    }

    public String getRefuseReasonClassName() {
        return refuseReasonClassName;
    }

    public void setRefuseReasonClassName(String refuseReasonClassName) {
        this.refuseReasonClassName = refuseReasonClassName;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
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

    public String getSignOtherServiceCode() {
        return signOtherServiceCode;
    }

    public void setSignOtherServiceCode(String signOtherServiceCode) {
        this.signOtherServiceCode = signOtherServiceCode;
    }

    public String getSignOtherServiceName() {
        return signOtherServiceName;
    }

    public void setSignOtherServiceName(String signOtherServiceName) {
        this.signOtherServiceName = signOtherServiceName;
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

    public String getSignStatusName() {
        return signStatusName;
    }

    public void setSignStatusName(String signStatusName) {
        this.signStatusName = signStatusName;
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

    public String getVideoCaochRateUnitCode() {
        return videoCaochRateUnitCode;
    }

    public void setVideoCaochRateUnitCode(String videoCaochRateUnitCode) {
        this.videoCaochRateUnitCode = videoCaochRateUnitCode;
    }

    public String getVideoCaochRateUnitName() {
        return videoCaochRateUnitName;
    }

    public void setVideoCaochRateUnitName(String videoCaochRateUnitName) {
        this.videoCaochRateUnitName = videoCaochRateUnitName;
    }

    public int getVideoCoachDuration() {
        return videoCoachDuration;
    }

    public void setVideoCoachDuration(int videoCoachDuration) {
        this.videoCoachDuration = videoCoachDuration;
    }

    public String getVideoCoachDurationUnitCode() {
        return videoCoachDurationUnitCode;
    }

    public void setVideoCoachDurationUnitCode(String videoCoachDurationUnitCode) {
        this.videoCoachDurationUnitCode = videoCoachDurationUnitCode;
    }

    public String getVideoCoachDurationUnitName() {
        return videoCoachDurationUnitName;
    }

    public void setVideoCoachDurationUnitName(String videoCoachDurationUnitName) {
        this.videoCoachDurationUnitName = videoCoachDurationUnitName;
    }

    public int getVideoCoachRate() {
        return videoCoachRate;
    }

    public void setVideoCoachRate(int videoCoachRate) {
        this.videoCoachRate = videoCoachRate;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public int getVideoTotle() {
        return videoTotle;
    }

    public void setVideoTotle(int videoTotle) {
        this.videoTotle = videoTotle;
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
         * mainConfigDetailCode : 150
         * mainConfigDetailName : 体重检测
         * mainSignCode : f3731ef3526f470a97227221b339e680
         * rate : 1
         * rateUnitCode : 1
         * rateUnitName : 天
         * signOrderConfigDetailCode : 9ea8efafdfdb460dbaf29d3483752a46
         * signOrderConfigDetailId : 2121
         * totlePrice : 50.0
         * duration : 1
         * durationUnitCode : null
         * durationUnitName : 分钟
         * value : 1
         */

        private String configDetailTypeCode;
        private String configDetailTypeName;
        private String mainConfigDetailCode;
        private String mainConfigDetailName;
        private String mainSignCode;
        private int rate;
        private String rateUnitCode;
        private String rateUnitName;
        private String signOrderConfigDetailCode;
        private int signOrderConfigDetailId;
        private double totlePrice;
        private int duration;
        private String durationUnitCode;
        private String durationUnitName;
        private Integer  value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
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

    }

    public String getRelieveReasonClassCode() {
        return relieveReasonClassCode;
    }

    public void setRelieveReasonClassCode(String relieveReasonClassCode) {
        this.relieveReasonClassCode = relieveReasonClassCode;
    }

    public String getRelieveReasonClassName() {
        return relieveReasonClassName;
    }

    public void setRelieveReasonClassName(String relieveReasonClassName) {
        this.relieveReasonClassName = relieveReasonClassName;
    }

    public String getRelieveRemark() {
        return relieveRemark;
    }

    public void setRelieveRemark(String relieveRemark) {
        this.relieveRemark = relieveRemark;
    }

    public String getRelieveReasonClassCodeD() {
        return relieveReasonClassCodeD;
    }

    public void setRelieveReasonClassCodeD(String relieveReasonClassCodeD) {
        this.relieveReasonClassCodeD = relieveReasonClassCodeD;
    }

    public String getRelieveReasonClassNameD() {
        return relieveReasonClassNameD;
    }

    public void setRelieveReasonClassNameD(String relieveReasonClassNameD) {
        this.relieveReasonClassNameD = relieveReasonClassNameD;
    }

    public String getRelieveRemarkD() {
        return relieveRemarkD;
    }

    public void setRelieveRemarkD(String relieveRemarkD) {
        this.relieveRemarkD = relieveRemarkD;
    }

    public String getRejectReasonClassCode() {
        return rejectReasonClassCode;
    }

    public void setRejectReasonClassCode(String rejectReasonClassCode) {
        this.rejectReasonClassCode = rejectReasonClassCode;
    }

    public String getRejectReasonClassName() {
        return rejectReasonClassName;
    }

    public void setRejectReasonClassName(String rejectReasonClassName) {
        this.rejectReasonClassName = rejectReasonClassName;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public String getRejectReasonClassCodeJ() {
        return rejectReasonClassCodeJ;
    }

    public void setRejectReasonClassCodeJ(String rejectReasonClassCodeJ) {
        this.rejectReasonClassCodeJ = rejectReasonClassCodeJ;
    }

    public String getRejectReasonClassNameJ() {
        return rejectReasonClassNameJ;
    }

    public void setRejectReasonClassNameJ(String rejectReasonClassNameJ) {
        this.rejectReasonClassNameJ = rejectReasonClassNameJ;
    }

    public String getRejectRemarkJ() {
        return rejectRemarkJ;
    }

    public void setRejectRemarkJ(String rejectRemarkJ) {
        this.rejectRemarkJ = rejectRemarkJ;
    }
}
