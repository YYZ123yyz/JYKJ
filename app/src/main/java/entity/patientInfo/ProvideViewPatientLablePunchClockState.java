package entity.patientInfo;

import java.math.BigDecimal;
import java.util.Date;

public class ProvideViewPatientLablePunchClockState implements java.io.Serializable{
    /* 患者数据 */
    private Integer patientId;//患者编号
    private String patientCode;//患者编码
    private String linkPhone;//登录手机
    private String userAccount;//登录账号
    private String email;//邮箱
    private String userPass;//密码
    private String userRoleId;//角色ID
    private Integer userFamilyMain;//
    private String userLabelName;//
    private String userName;//姓名
    private String userNameAlias;//别名
    private String userNameSpell;//姓名拼音助记码
    private String qrCode;//二维码
    private String userLogoUrl;//头像URL地址
    private String idNumber;//身份证
    private String nativePlace;//籍贯
    private Integer gender;//性别.0:未知;1:男;2:女;
    private long birthday;//生日
    private String country;//所在国家
    private String province;//省份编码
    private String provinceName;//省份名称
    private String city;//市编码
    private String cityName;//市名称
    private String area;//区(县)编码
    private String areaName;//区(县)名称
    private String address;//地址
    private String nation;//民族
    private Integer flagPatientStatus;//患者认证状态.0:未认证;1:已认证;
    private Date newLoginDate;//最后一次登录日期

    /* 标签数据 */
    private Integer userLabelId;//患者标签数据编号
    private Integer userLabel;//患者标签[大类][默认高血压规则编码].对应数据字典.Eg.高血压.外键:sys_domain(attrCode)
    private String userLabelSecond;//患者标签[小类].对应数据字典.Eg.高血压一期.外键:sys_domain(attrCode)
    private String userLabelSecondName;//[冗余]标签名称

    /* 打开状态数据 */
    private Integer patientStateId;//患者打卡状态编号
    private String stateType;//患者打卡状态类型.0:暂未评测;1:正常;2:提醒;3:预警;
    private Integer evaluateType;//状态评测类型.1:每日评测;2:每周评测;3:每月评测;
    private Date evaluateDate;//状态评测日期
    private Date startEvaluateDate;//起始评测日期
    private Date endEvaluateDate;//结束评测日期

    /*签约单数据*/
    private  String signCode;     //签约编码 uuid
    private  String signNo;       //签约号
    private  String signStatus;  //签约状态:10:已提交 20:已同意 30:签约完成 40:到期解约 50:续约 100:已拒绝 110:需修改 120:提前解约 130:付款中 140: 【患者】解约申请中  160: 【医生】解约申请中
    private BigDecimal signPrice;  //签约价格
    private  Integer signDuration;  //签约时长
    private  String signUnit;  //签约时长单位
    private  String signDurationUnit;  //签约时长展示
    private  String signOtherServiceCode;  //签约其他服务编码，多个”，“隔开    ---监测类服务
    private  Integer detectRate;  //检测频次  3
    private  String detectRateUnitCode;  //检测频次单位编码  code
    private  String detectRateUnitName;  //检测频次单位名称  天
    public String getSignCode() {
        return signCode;
    }

    public BigDecimal getSignPrice() {
        return signPrice;
    }

    public void setSignPrice(BigDecimal signPrice) {
        this.signPrice = signPrice;
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

    public String getSignOtherServiceCode() {
        return signOtherServiceCode;
    }

    public void setSignOtherServiceCode(String signOtherServiceCode) {
        this.signOtherServiceCode = signOtherServiceCode;
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

    public void setSignCode(String signCode) {
        this.signCode = signCode;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserFamilyMain() {
        return userFamilyMain;
    }

    public void setUserFamilyMain(Integer userFamilyMain) {
        this.userFamilyMain = userFamilyMain;
    }

    public String getUserLabelName() {
        return userLabelName;
    }

    public void setUserLabelName(String userLabelName) {
        this.userLabelName = userLabelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameAlias() {
        return userNameAlias;
    }

    public void setUserNameAlias(String userNameAlias) {
        this.userNameAlias = userNameAlias;
    }

    public String getUserNameSpell() {
        return userNameSpell;
    }

    public void setUserNameSpell(String userNameSpell) {
        this.userNameSpell = userNameSpell;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getFlagPatientStatus() {
        return flagPatientStatus;
    }

    public void setFlagPatientStatus(Integer flagPatientStatus) {
        this.flagPatientStatus = flagPatientStatus;
    }

    public Date getNewLoginDate() {
        return newLoginDate;
    }

    public void setNewLoginDate(Date newLoginDate) {
        this.newLoginDate = newLoginDate;
    }

    public Integer getUserLabelId() {
        return userLabelId;
    }

    public void setUserLabelId(Integer userLabelId) {
        this.userLabelId = userLabelId;
    }

    public Integer getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(Integer userLabel) {
        this.userLabel = userLabel;
    }

    public String getUserLabelSecond() {
        return userLabelSecond;
    }

    public void setUserLabelSecond(String userLabelSecond) {
        this.userLabelSecond = userLabelSecond;
    }

    public String getUserLabelSecondName() {
        return userLabelSecondName;
    }

    public void setUserLabelSecondName(String userLabelSecondName) {
        this.userLabelSecondName = userLabelSecondName;
    }

    public Integer getPatientStateId() {
        return patientStateId;
    }

    public void setPatientStateId(Integer patientStateId) {
        this.patientStateId = patientStateId;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public Integer getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Integer evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Date getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public Date getStartEvaluateDate() {
        return startEvaluateDate;
    }

    public void setStartEvaluateDate(Date startEvaluateDate) {
        this.startEvaluateDate = startEvaluateDate;
    }

    public Date getEndEvaluateDate() {
        return endEvaluateDate;
    }

    public void setEndEvaluateDate(Date endEvaluateDate) {
        this.endEvaluateDate = endEvaluateDate;
    }
}
