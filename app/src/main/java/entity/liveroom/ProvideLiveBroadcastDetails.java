package entity.liveroom;

import java.io.Serializable;
import java.util.Date;

public class ProvideLiveBroadcastDetails implements Serializable {
    private Integer detailsId;//直播详情编号
    private String detailsCode;//直播关联编码(UUID)(关联:直播间聊天室其他)

    private Integer userUseType;//账号类型
    private String userCode;//直播人编码
    private String userName;//直播人姓名

    private Integer flagBroadcastState;//直播状态.0:状态错误;1:直播预告;2:正在直播;3:专题讲座;
    private Integer flagBroadcastType;//直播类型.0:类型错误;1:视频直播;2:音频直播;

    private String broadcastTitle;//直播主题
    private String broadcastCoverImgUrl;//直播封面
    private Date broadcastDate;//预告直播时间(直播预告操作时,此值必填)

    private String attrCode;//关键词编码.Eg.1^2^3
    private String attrName;//[冗余]关键词名称.Eg.医学^降血压^心血管健康

    private String classCode;//类目编码.Eg.1^2^3
    private String className;//[冗余]类目名称.Eg.诊断^治疗^康复

    private String riskCode;//危险因素编码.Eg.1^2^3
    private String riskName;//[冗余]危险因素名称.Eg.家族史^吸烟^饮酒

    private Integer watchObject;//观看对象.0:所有;1:医生;2:患者;
    private String doctorWatchObjectAuthCode;//医生观看权限编码.Eg.1^2^3
    private String doctorWatchObjectAuthName;//[冗余]医生观看权限名称.Eg.公开^医生好友^付费
    private String patientWatchObjectAuthCode;//患者观看权限编码.Eg.1^2^3
    private String patientWatchObjectAuthName;//[冗余]患者观看权限名称.Eg.公开^医患绑定^签约患者

    private String pushUrl;//推流地址
    private String pullUrl;//拉流地址
    private String chatRoomCode;//直播间编码
    private String playUrl;//播放地址(专题讲座类型使用)

    private Integer extendBroadcastWatchNum;//观看人数
    private Integer extendBroadcastFollowNum;//关注人数
    private Integer extendBroadcastViewsNum;//浏览量
    private Float extendBroadcastPrice;//观看价格
    private String extendBroadcastParam;//扩展直播间参数

    private Integer flagAnchorStates;//主播状态.0:离线;1:上线;



    /*************************** 非对称属性 ***************************/
    private String broadcastUserName;//展示：直播人姓名
    private String broadcastUserLogoUrl;//展示：直播人头像
    private String broadcastTypeName;//展示：直播类型名称
    private String extendBroadcastPriceShow;//展示：观看价格


    /* 【直播详情】使用 */
    private String titleMainShow;//展示：主标题显示.Eg.授课专家
    private String broadcastUserTitleName;//展示：职称.Eg.主任医师
    private String titleDetailShow;//展示：副标题显示.Eg.中国营养学会常务理事
    private Integer flagLikes;//点赞标识.0:未点赞;1:已点赞;

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }

    public Integer getUserUseType() {
        return userUseType;
    }

    public void setUserUseType(Integer userUseType) {
        this.userUseType = userUseType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFlagBroadcastState() {
        return flagBroadcastState;
    }

    public void setFlagBroadcastState(Integer flagBroadcastState) {
        this.flagBroadcastState = flagBroadcastState;
    }

    public Integer getFlagBroadcastType() {
        return flagBroadcastType;
    }

    public void setFlagBroadcastType(Integer flagBroadcastType) {
        this.flagBroadcastType = flagBroadcastType;
    }

    public String getBroadcastTitle() {
        return broadcastTitle;
    }

    public void setBroadcastTitle(String broadcastTitle) {
        this.broadcastTitle = broadcastTitle;
    }

    public String getBroadcastCoverImgUrl() {
        return broadcastCoverImgUrl;
    }

    public void setBroadcastCoverImgUrl(String broadcastCoverImgUrl) {
        this.broadcastCoverImgUrl = broadcastCoverImgUrl;
    }

    public Date getBroadcastDate() {
        return broadcastDate;
    }

    public void setBroadcastDate(Date broadcastDate) {
        this.broadcastDate = broadcastDate;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRiskCode() {
        return riskCode;
    }

    public void setRiskCode(String riskCode) {
        this.riskCode = riskCode;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public Integer getWatchObject() {
        return watchObject;
    }

    public void setWatchObject(Integer watchObject) {
        this.watchObject = watchObject;
    }

    public String getDoctorWatchObjectAuthCode() {
        return doctorWatchObjectAuthCode;
    }

    public void setDoctorWatchObjectAuthCode(String doctorWatchObjectAuthCode) {
        this.doctorWatchObjectAuthCode = doctorWatchObjectAuthCode;
    }

    public String getDoctorWatchObjectAuthName() {
        return doctorWatchObjectAuthName;
    }

    public void setDoctorWatchObjectAuthName(String doctorWatchObjectAuthName) {
        this.doctorWatchObjectAuthName = doctorWatchObjectAuthName;
    }

    public String getPatientWatchObjectAuthCode() {
        return patientWatchObjectAuthCode;
    }

    public void setPatientWatchObjectAuthCode(String patientWatchObjectAuthCode) {
        this.patientWatchObjectAuthCode = patientWatchObjectAuthCode;
    }

    public String getPatientWatchObjectAuthName() {
        return patientWatchObjectAuthName;
    }

    public void setPatientWatchObjectAuthName(String patientWatchObjectAuthName) {
        this.patientWatchObjectAuthName = patientWatchObjectAuthName;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getChatRoomCode() {
        return chatRoomCode;
    }

    public void setChatRoomCode(String chatRoomCode) {
        this.chatRoomCode = chatRoomCode;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public Integer getExtendBroadcastWatchNum() {
        return extendBroadcastWatchNum;
    }

    public void setExtendBroadcastWatchNum(Integer extendBroadcastWatchNum) {
        this.extendBroadcastWatchNum = extendBroadcastWatchNum;
    }

    public Integer getExtendBroadcastFollowNum() {
        return extendBroadcastFollowNum;
    }

    public void setExtendBroadcastFollowNum(Integer extendBroadcastFollowNum) {
        this.extendBroadcastFollowNum = extendBroadcastFollowNum;
    }

    public Integer getExtendBroadcastViewsNum() {
        return extendBroadcastViewsNum;
    }

    public void setExtendBroadcastViewsNum(Integer extendBroadcastViewsNum) {
        this.extendBroadcastViewsNum = extendBroadcastViewsNum;
    }

    public Float getExtendBroadcastPrice() {
        return extendBroadcastPrice;
    }

    public void setExtendBroadcastPrice(Float extendBroadcastPrice) {
        this.extendBroadcastPrice = extendBroadcastPrice;
    }

    public String getExtendBroadcastParam() {
        return extendBroadcastParam;
    }

    public void setExtendBroadcastParam(String extendBroadcastParam) {
        this.extendBroadcastParam = extendBroadcastParam;
    }

    public Integer getFlagAnchorStates() {
        return flagAnchorStates;
    }

    public void setFlagAnchorStates(Integer flagAnchorStates) {
        this.flagAnchorStates = flagAnchorStates;
    }

    public String getBroadcastUserName() {
        return broadcastUserName;
    }

    public void setBroadcastUserName(String broadcastUserName) {
        this.broadcastUserName = broadcastUserName;
    }

    public String getBroadcastUserLogoUrl() {
        return broadcastUserLogoUrl;
    }

    public void setBroadcastUserLogoUrl(String broadcastUserLogoUrl) {
        this.broadcastUserLogoUrl = broadcastUserLogoUrl;
    }

    public String getBroadcastTypeName() {
        return broadcastTypeName;
    }

    public void setBroadcastTypeName(String broadcastTypeName) {
        this.broadcastTypeName = broadcastTypeName;
    }

    public String getExtendBroadcastPriceShow() {
        return extendBroadcastPriceShow;
    }

    public void setExtendBroadcastPriceShow(String extendBroadcastPriceShow) {
        this.extendBroadcastPriceShow = extendBroadcastPriceShow;
    }

    public String getTitleMainShow() {
        return titleMainShow;
    }

    public void setTitleMainShow(String titleMainShow) {
        this.titleMainShow = titleMainShow;
    }

    public String getBroadcastUserTitleName() {
        return broadcastUserTitleName;
    }

    public void setBroadcastUserTitleName(String broadcastUserTitleName) {
        this.broadcastUserTitleName = broadcastUserTitleName;
    }

    public String getTitleDetailShow() {
        return titleDetailShow;
    }

    public void setTitleDetailShow(String titleDetailShow) {
        this.titleDetailShow = titleDetailShow;
    }

    public Integer getFlagLikes() {
        return flagLikes;
    }

    public void setFlagLikes(Integer flagLikes) {
        this.flagLikes = flagLikes;
    }
}
