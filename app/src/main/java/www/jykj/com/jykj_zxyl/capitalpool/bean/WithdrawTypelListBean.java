package www.jykj.com.jykj_zxyl.capitalpool.bean;

public class WithdrawTypelListBean {

    /**
     * accountType : 1
     * assetsCode : c877fc2a03bf4552ad070fb112794246
     * bankAddress : 娜恩
     * bankName : 拉的多
     * bankcardCode : 0e240599f7f14e0786df6252e917623a
     * cardAccount : 5356256526626535652
     * cardType : 1
     * cardUserName : 么空
     * createDate : 1608720315000
     * createMan : f902533f60fa4fe197016c94fd33b77c
     * idNumber : 532522632566663522
     * isDefault : 0
     * mainUserCode : f902533f60fa4fe197016c94fd33b77c
     * mainUserName : Doctor_214500
     * withdrawalType : 3
     */

    private int accountType;
    private String assetsCode;
    private String bankAddress;
    private String bankName;
    private String bankcardCode;
    private String cardAccount;
    private int cardType;
    private String cardUserName;
    private long createDate;
    private String createMan;
    private String idNumber;
    private int isDefault;
    private String mainUserCode;
    private String mainUserName;
    private int withdrawalType;
    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAssetsCode() {
        return assetsCode;
    }

    public void setAssetsCode(String assetsCode) {
        this.assetsCode = assetsCode;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankcardCode() {
        return bankcardCode;
    }

    public void setBankcardCode(String bankcardCode) {
        this.bankcardCode = bankcardCode;
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getMainUserCode() {
        return mainUserCode;
    }

    public void setMainUserCode(String mainUserCode) {
        this.mainUserCode = mainUserCode;
    }

    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    public int getWithdrawalType() {
        return withdrawalType;
    }

    public void setWithdrawalType(int withdrawalType) {
        this.withdrawalType = withdrawalType;
    }
}
