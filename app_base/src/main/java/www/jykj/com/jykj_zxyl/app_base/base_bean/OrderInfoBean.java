package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 15:17
 */
public class OrderInfoBean {

    /**
     * actualPayment : 1000.0
     * couponRecordHistoryCode : 0
     * createDate : 1599493479000
     * flagOrderState : 10
     * flagOrderStateName : 已完成
     * integralRecordHistoryCode : 0
     * linkPhone : 18729001111
     * mainDoctorCode : af50b8a090144fa8b4ffc6ecfe9ff0de
     * mainDoctorName : 贾青
     * mainPatientCode : 00440e854c0e46d38596b54593312a61
     * mainUserName : 患者测试账号00011
     * orderDesc : 一次性签约订单描述
     * paymentMode : 1
     * reserveTime : 1599493897000
     * serviceTotal : 1000.0
     * signEndTime : 1599408000000
     * signStartTime : 1582992000000
     * treatmentType : 1
     */

    private double actualPayment;
    private String couponRecordHistoryCode;
    private long createDate;
    private int flagOrderState;
    private String flagOrderStateName;
    private String integralRecordHistoryCode;
    private String linkPhone;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainUserName;
    private String orderDesc;
    private int paymentMode;
    private long reserveTime;
    private double serviceTotal;
    private long signEndTime;
    private long signStartTime;
    private int treatmentType;
    private int orderType;

    public double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getCouponRecordHistoryCode() {
        return couponRecordHistoryCode;
    }

    public void setCouponRecordHistoryCode(String couponRecordHistoryCode) {
        this.couponRecordHistoryCode = couponRecordHistoryCode;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getFlagOrderState() {
        return flagOrderState;
    }

    public void setFlagOrderState(int flagOrderState) {
        this.flagOrderState = flagOrderState;
    }

    public String getFlagOrderStateName() {
        return flagOrderStateName;
    }

    public void setFlagOrderStateName(String flagOrderStateName) {
        this.flagOrderStateName = flagOrderStateName;
    }

    public String getIntegralRecordHistoryCode() {
        return integralRecordHistoryCode;
    }

    public void setIntegralRecordHistoryCode(String integralRecordHistoryCode) {
        this.integralRecordHistoryCode = integralRecordHistoryCode;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
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

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public long getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(long reserveTime) {
        this.reserveTime = reserveTime;
    }

    public double getServiceTotal() {
        return serviceTotal;
    }

    public void setServiceTotal(double serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public long getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(long signEndTime) {
        this.signEndTime = signEndTime;
    }

    public long getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(long signStartTime) {
        this.signStartTime = signStartTime;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
