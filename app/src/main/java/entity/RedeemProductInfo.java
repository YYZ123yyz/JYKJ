package entity;

public class RedeemProductInfo {
    private             String          redeemProductName;                  //积分兑换产品名称
    private             String          getRedeemProductExplain;                  //积分兑换产品说明
    private             String          redeemProductPrice;                  //积分兑换产品价格

    public String getRedeemProductName() {
        return redeemProductName;
    }

    public void setRedeemProductName(String redeemProductName) {
        this.redeemProductName = redeemProductName;
    }

    public String getGetRedeemProductExplain() {
        return getRedeemProductExplain;
    }

    public void setGetRedeemProductExplain(String getRedeemProductExplain) {
        this.getRedeemProductExplain = getRedeemProductExplain;
    }

    public String getRedeemProductPrice() {
        return redeemProductPrice;
    }

    public void setRedeemProductPrice(String redeemProductPrice) {
        this.redeemProductPrice = redeemProductPrice;
    }
}
