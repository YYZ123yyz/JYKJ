package entity;

public class RedeemRecordEntity {
    private         String              mRedeemName;                    //名称
    private         String              mRedeemExplain;                    //说明
    private         String              mRedeemDate;                    //兑换时间
    private         String              mRedeemPrice;                    //消耗积分
    private         int                 mRedeemState;                    //名称

    public String getmRedeemName() {
        return mRedeemName;
    }

    public void setmRedeemName(String mRedeemName) {
        this.mRedeemName = mRedeemName;
    }

    public String getmRedeemExplain() {
        return mRedeemExplain;
    }

    public void setmRedeemExplain(String mRedeemExplain) {
        this.mRedeemExplain = mRedeemExplain;
    }

    public String getmRedeemDate() {
        return mRedeemDate;
    }

    public void setmRedeemDate(String mRedeemDate) {
        this.mRedeemDate = mRedeemDate;
    }

    public String getmRedeemPrice() {
        return mRedeemPrice;
    }

    public void setmRedeemPrice(String mRedeemPrice) {
        this.mRedeemPrice = mRedeemPrice;
    }

    public int getmRedeemState() {
        return mRedeemState;
    }

    public void setmRedeemState(int mRedeemState) {
        this.mRedeemState = mRedeemState;
    }
}
