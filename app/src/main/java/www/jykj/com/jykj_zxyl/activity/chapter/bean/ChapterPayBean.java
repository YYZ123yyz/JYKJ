package www.jykj.com.jykj_zxyl.activity.chapter.bean;

public class ChapterPayBean {

    /**
     * appId : wx4ccb2ac1c5491336
     * nonceStr : 20201125113728EEHWQ09FC1LIUV30OT
     * packagePrepayId : Sign=WXPay
     * partnerid : 1579159781
     * prepayid : wx25113728342161cdf722ecfc9731b50000
     * sign : 4DE641077D746B9EBE8E47FC08BB2C89
     * timeStamp : 1606275448
     */

    private String appId;
    private String nonceStr;
    private String packagePrepayId;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timeStamp;
    private String alipay_sdk;

    public String getAlipay_sdk() {
        return alipay_sdk;
    }

    public void setAlipay_sdk(String alipay_sdk) {
        this.alipay_sdk = alipay_sdk;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackagePrepayId() {
        return packagePrepayId;
    }

    public void setPackagePrepayId(String packagePrepayId) {
        this.packagePrepayId = packagePrepayId;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
