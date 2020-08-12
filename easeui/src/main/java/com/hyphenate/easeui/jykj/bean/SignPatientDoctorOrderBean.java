package com.hyphenate.easeui.jykj.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SignPatientDoctorOrderBean {

    private  Integer signId;  //
    private  String signCode;  //签约编码 uuid
    private  String mainDoctorCode;  //医生编码
    private  String mainDoctorName;  //医生姓名
    private  String mainDoctorAlias;  //医生昵称
    private  String mainPatientCode;  //患者编码
    private  String mainUserName;  //患者姓名
    private  String mainUserNameAlias;  //患者昵称
    private  String signNo;  //签约号  （规则：年月日时分秒毫秒）
    private  String signStatus;  //签约状态:10:已提交 20:已同意 30:签约完成 40:到期解约 50:续约 100:已拒绝 110:需修改 120:提前解约 130:付款中 140: 【患者】解约申请中  160: 【医生】解约申请中
    private  String signStatusName;  //签约状态名称
    private  Integer signDuration;  //签约时长
    private  String signUnit;  //签约时长单位
    private  String signDurationUnit;  //签约时长展示
    private BigDecimal signPrice;  //签约价格
    private  String signConfigSnapCode;  //签约配置快照编码
    private  String signConfigSnapName;  //签约配置快照名称
    private  String signOtherServiceCode;  //签约其他服务编码，多个”，“隔开    ---监测类服务
    private  String signOtherServiceName;  //签约其他服务
    private  Date signStartTime;  //签约开始时间
    private  Date signEndTime;  //签约结束时间
    private  Date terminationTime;  //解约时间
    private  Integer figureTextTotle;  //图文总数 (月频次*几个月)
    private  Integer audioTotle;  //音频总数
    private  Integer videoTotle;  //视频总数
    private  Integer callTotle;  //电话总数
    private  Integer figureTextLastCount;  //图文使用数量(默认:0) (签约服务消费时，+1)
    private  Integer audioLastCount;  //音频使用数量
    private  Integer videoLastCount;  //视频使用数量
    private  Integer callLastCount;  //电话使用数量
    //监测类
    private  Integer detectRate;  //检测频次  3
    private  String detectRateUnitCode;  //检测频次单位编码  code
    private  String detectRateUnitName;  //检测频次单位名称  天
    //辅导类
    private  Integer figureTextNum; //图文次数
    private  Integer figureTextCoachRate; //图文辅导频次
    private  String figureTextCaochRateUnitCode; //图文辅导频次单位编码
    private  String figureTextCaochRateUnitName; //图文辅导频次单位名称
    private  Integer figureTextCoachDuration; //图文时长
    private  String figureTextCoachDurationUnitCode; //图文时长单位编码
    private  String figureTextCoachDurationUnitName; //图文时长单位编码名称
    private  Integer audioNum; //音频次数
    private  String audioCaochRateUnitCode; //音频辅导频次单位编码
    private  Integer audioCoachRate; //音频辅导频次
    private  String audioCaochRateUnitName; //音频辅导频次单位名称
    private  Integer audioCoachDuration; //音频时长
    private  String audioCoachDurationUnitCode; //音频时长单位编码
    private  String audioCoachDurationUnitName; //音频时长单位编码名称
    private  Integer videoNum; //视频次数
    private  Integer videoCoachRate; //视频辅导频次
    private  String videoCaochRateUnitCode; //视频辅导频次单位编码
    private  String videoCaochRateUnitName; //视频辅导频次单位名称
    private  Integer videoCoachDuration; //视频时长
    private  String videoCoachDurationUnitCode; //视频时长单位编码
    private  String videoCoachDurationUnitName; //视频时长单位编码名称
    private  Integer callNum; //电话次数
    private  Integer callCoachRate; //电话辅导频次
    private  String callCaochRateUnitCode; //电话辅导频次单位编码
    private  String callCaochRateUnitName; //电话辅导频次单位名称
    private  Integer callCoachDuration; //电话时长
    private  String callCoachDurationUnitCode; //电话时长单位编码
    private  String callCoachDurationUnitName; //电话时长单位编码名称

    private  Integer refuseCount;  //拒绝次数，初始化为0
    private  Date lastTimeRefuseCount;  //上次拒绝时间
    //扩展
    private  String refuseReasonClassCode;  //拒绝/解约原因分类code
    private  String refuseReasonClassName;  //拒绝/解约原因分类名称

    private  String refuseRemark;  //拒绝/解约原因
    private  Integer version;  //行锁定
    private  Integer flagDel;  //删除标识.1:正常;0:删除;
    private  String remark;  //备注信息
    private  Date createDate;  //创建日期
    private  String createMan;  //创建人
    private Date updateDate;  //修改日期
    private  String updateMan;  //修改人

    private List<SignPatientDoctorOrderConfigDetail> orderDetailList;   //详情集合

    //扩展字段
    private  String patientAge;       //年龄
    private  String patientGender;   //性别.(0:未知;1:男;2:女);

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
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

    public String getMainDoctorAlias() {
        return mainDoctorAlias;
    }

    public void setMainDoctorAlias(String mainDoctorAlias) {
        this.mainDoctorAlias = mainDoctorAlias;
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

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
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

    public Integer getSignDuration() {
        return signDuration;
    }

    public void setSignDuration(Integer signDuration) {
        this.signDuration = signDuration;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public String getSignDurationUnit() {
        return signDurationUnit;
    }

    public void setSignDurationUnit(String signDurationUnit) {
        this.signDurationUnit = signDurationUnit;
    }

    public BigDecimal getSignPrice() {
        return signPrice;
    }

    public void setSignPrice(BigDecimal signPrice) {
        this.signPrice = signPrice;
    }

    public String getSignConfigSnapCode() {
        return signConfigSnapCode;
    }

    public void setSignConfigSnapCode(String signConfigSnapCode) {
        this.signConfigSnapCode = signConfigSnapCode;
    }

    public String getSignConfigSnapName() {
        return signConfigSnapName;
    }

    public void setSignConfigSnapName(String signConfigSnapName) {
        this.signConfigSnapName = signConfigSnapName;
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

    public Date getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Date signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Date getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Date signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Date getTerminationTime() {
        return terminationTime;
    }

    public void setTerminationTime(Date terminationTime) {
        this.terminationTime = terminationTime;
    }

    public Integer getFigureTextTotle() {
        return figureTextTotle;
    }

    public void setFigureTextTotle(Integer figureTextTotle) {
        this.figureTextTotle = figureTextTotle;
    }

    public Integer getAudioTotle() {
        return audioTotle;
    }

    public void setAudioTotle(Integer audioTotle) {
        this.audioTotle = audioTotle;
    }

    public Integer getVideoTotle() {
        return videoTotle;
    }

    public void setVideoTotle(Integer videoTotle) {
        this.videoTotle = videoTotle;
    }

    public Integer getCallTotle() {
        return callTotle;
    }

    public void setCallTotle(Integer callTotle) {
        this.callTotle = callTotle;
    }

    public Integer getFigureTextLastCount() {
        return figureTextLastCount;
    }

    public void setFigureTextLastCount(Integer figureTextLastCount) {
        this.figureTextLastCount = figureTextLastCount;
    }

    public Integer getAudioLastCount() {
        return audioLastCount;
    }

    public void setAudioLastCount(Integer audioLastCount) {
        this.audioLastCount = audioLastCount;
    }

    public Integer getVideoLastCount() {
        return videoLastCount;
    }

    public void setVideoLastCount(Integer videoLastCount) {
        this.videoLastCount = videoLastCount;
    }

    public Integer getCallLastCount() {
        return callLastCount;
    }

    public void setCallLastCount(Integer callLastCount) {
        this.callLastCount = callLastCount;
    }

    public Integer getDetectRate() {
        return detectRate;
    }

    public void setDetectRate(Integer detectRate) {
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

    public Integer getFigureTextNum() {
        return figureTextNum;
    }

    public void setFigureTextNum(Integer figureTextNum) {
        this.figureTextNum = figureTextNum;
    }

    public Integer getFigureTextCoachRate() {
        return figureTextCoachRate;
    }

    public void setFigureTextCoachRate(Integer figureTextCoachRate) {
        this.figureTextCoachRate = figureTextCoachRate;
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

    public Integer getFigureTextCoachDuration() {
        return figureTextCoachDuration;
    }

    public void setFigureTextCoachDuration(Integer figureTextCoachDuration) {
        this.figureTextCoachDuration = figureTextCoachDuration;
    }

    public String getFigureTextCoachDurationUnitCode() {
        return figureTextCoachDurationUnitCode;
    }

    public void setFigureTextCoachDurationUnitCode(String figureTextCoachDurationUnitCode) {
        this.figureTextCoachDurationUnitCode = figureTextCoachDurationUnitCode;
    }

    public String getFigureTextCoachDurationUnitName() {
        return figureTextCoachDurationUnitName;
    }

    public void setFigureTextCoachDurationUnitName(String figureTextCoachDurationUnitName) {
        this.figureTextCoachDurationUnitName = figureTextCoachDurationUnitName;
    }

    public Integer getAudioNum() {
        return audioNum;
    }

    public void setAudioNum(Integer audioNum) {
        this.audioNum = audioNum;
    }

    public String getAudioCaochRateUnitCode() {
        return audioCaochRateUnitCode;
    }

    public void setAudioCaochRateUnitCode(String audioCaochRateUnitCode) {
        this.audioCaochRateUnitCode = audioCaochRateUnitCode;
    }

    public Integer getAudioCoachRate() {
        return audioCoachRate;
    }

    public void setAudioCoachRate(Integer audioCoachRate) {
        this.audioCoachRate = audioCoachRate;
    }

    public String getAudioCaochRateUnitName() {
        return audioCaochRateUnitName;
    }

    public void setAudioCaochRateUnitName(String audioCaochRateUnitName) {
        this.audioCaochRateUnitName = audioCaochRateUnitName;
    }

    public Integer getAudioCoachDuration() {
        return audioCoachDuration;
    }

    public void setAudioCoachDuration(Integer audioCoachDuration) {
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

    public Integer getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(Integer videoNum) {
        this.videoNum = videoNum;
    }

    public Integer getVideoCoachRate() {
        return videoCoachRate;
    }

    public void setVideoCoachRate(Integer videoCoachRate) {
        this.videoCoachRate = videoCoachRate;
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

    public Integer getVideoCoachDuration() {
        return videoCoachDuration;
    }

    public void setVideoCoachDuration(Integer videoCoachDuration) {
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

    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    public Integer getCallCoachRate() {
        return callCoachRate;
    }

    public void setCallCoachRate(Integer callCoachRate) {
        this.callCoachRate = callCoachRate;
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

    public Integer getCallCoachDuration() {
        return callCoachDuration;
    }

    public void setCallCoachDuration(Integer callCoachDuration) {
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

    public Integer getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Integer refuseCount) {
        this.refuseCount = refuseCount;
    }

    public Date getLastTimeRefuseCount() {
        return lastTimeRefuseCount;
    }

    public void setLastTimeRefuseCount(Date lastTimeRefuseCount) {
        this.lastTimeRefuseCount = lastTimeRefuseCount;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public List<SignPatientDoctorOrderConfigDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<SignPatientDoctorOrderConfigDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
}
