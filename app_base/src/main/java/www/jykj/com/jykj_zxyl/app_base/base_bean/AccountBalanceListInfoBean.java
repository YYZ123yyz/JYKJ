package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 18:54
 */
public class AccountBalanceListInfoBean {


    /**
     * accountDoctorBalanceInfoList : [{"balanceNow":0,"changeChildType":10,"changeType":1,"changeTypeName":"收入","costTotalMoney":0,"flagFrozen":1,"infoDate":1608393600000,"infoDateTime":1608466911000,"infoDesc":"充值","infoMoney":5000,"mainUserCode":"642c11aa51ef4e8aa3e833df960bef1f","mainUserName":"邱新海医生","sourceType":1,"sourceTypeName":"微信","userUseType":5},{"balanceNow":5000,"changeChildType":10,"changeType":1,"changeTypeName":"收入","costTotalMoney":0,"flagFrozen":1,"infoDate":1608393600000,"infoDateTime":1608468043000,"infoDesc":"充值","infoMoney":5000,"mainUserCode":"642c11aa51ef4e8aa3e833df960bef1f","mainUserName":"邱新海医生","sourceType":2,"sourceTypeName":"支付宝","userUseType":5},{"balanceNow":10000,"changeChildType":10,"changeType":1,"changeTypeName":"收入","costTotalMoney":0,"flagFrozen":1,"infoDate":1608393600000,"infoDateTime":1608469054000,"infoDesc":"充值","infoMoney":3000,"mainUserCode":"642c11aa51ef4e8aa3e833df960bef1f","mainUserName":"邱新海医生","sourceType":2,"sourceTypeName":"支付宝","userUseType":5}]
     * totalIncome : 13000.0
     * totalOut : 0.0
     */

    private double totalIncome;
    private double totalOut;
    private List<AccountDoctorBalanceInfoListBean> accountDoctorBalanceInfoList;

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(double totalOut) {
        this.totalOut = totalOut;
    }

    public List<AccountDoctorBalanceInfoListBean> getAccountDoctorBalanceInfoList() {
        return accountDoctorBalanceInfoList;
    }

    public void setAccountDoctorBalanceInfoList(List<AccountDoctorBalanceInfoListBean> accountDoctorBalanceInfoList) {
        this.accountDoctorBalanceInfoList = accountDoctorBalanceInfoList;
    }

    public static class AccountDoctorBalanceInfoListBean {
        /**
         * balanceNow : 0.0
         * changeChildType : 10
         * changeType : 1
         * changeTypeName : 收入
         * costTotalMoney : 0.0
         * flagFrozen : 1
         * infoDate : 1608393600000
         * infoDateTime : 1608466911000
         * infoDesc : 充值
         * infoMoney : 5000.0
         * mainUserCode : 642c11aa51ef4e8aa3e833df960bef1f
         * mainUserName : 邱新海医生
         * sourceType : 1
         * sourceTypeName : 微信
         * userUseType : 5
         */

        private double balanceNow;
        private int changeChildType;
        private String changeChildTypeName;
        private int changeType;
        private String changeTypeName;
        private double costTotalMoney;
        private int flagFrozen;
        private long infoDate;
        private long infoDateTime;
        private String infoDesc;
        private String infoMoney;
        private String infoMoneyStr;
        private String mainUserCode;
        private String mainUserName;
        private int sourceType;
        private String sourceTypeName;
        private int userUseType;
        private int orderType;
        private String orderTypeName;
        private String sourceUserName;


        public double getBalanceNow() {
            return balanceNow;
        }

        public void setBalanceNow(double balanceNow) {
            this.balanceNow = balanceNow;
        }

        public int getChangeChildType() {
            return changeChildType;
        }

        public void setChangeChildType(int changeChildType) {
            this.changeChildType = changeChildType;
        }

        public int getChangeType() {
            return changeType;
        }

        public void setChangeType(int changeType) {
            this.changeType = changeType;
        }

        public String getChangeTypeName() {
            return changeTypeName;
        }

        public void setChangeTypeName(String changeTypeName) {
            this.changeTypeName = changeTypeName;
        }

        public double getCostTotalMoney() {
            return costTotalMoney;
        }

        public void setCostTotalMoney(double costTotalMoney) {
            this.costTotalMoney = costTotalMoney;
        }

        public int getFlagFrozen() {
            return flagFrozen;
        }

        public void setFlagFrozen(int flagFrozen) {
            this.flagFrozen = flagFrozen;
        }

        public long getInfoDate() {
            return infoDate;
        }

        public void setInfoDate(long infoDate) {
            this.infoDate = infoDate;
        }

        public long getInfoDateTime() {
            return infoDateTime;
        }

        public void setInfoDateTime(long infoDateTime) {
            this.infoDateTime = infoDateTime;
        }

        public String getInfoDesc() {
            return infoDesc;
        }

        public void setInfoDesc(String infoDesc) {
            this.infoDesc = infoDesc;
        }

        public String getInfoMoney() {
            return infoMoney;
        }

        public void setInfoMoney(String infoMoney) {
            this.infoMoney = infoMoney;
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

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

        public String getSourceTypeName() {
            return sourceTypeName;
        }

        public void setSourceTypeName(String sourceTypeName) {
            this.sourceTypeName = sourceTypeName;
        }

        public int getUserUseType() {
            return userUseType;
        }

        public void setUserUseType(int userUseType) {
            this.userUseType = userUseType;
        }

        public String getChangeChildTypeName() {
            return changeChildTypeName;
        }

        public void setChangeChildTypeName(String changeChildTypeName) {
            this.changeChildTypeName = changeChildTypeName;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getOrderTypeName() {
            return orderTypeName;
        }

        public void setOrderTypeName(String orderTypeName) {
            this.orderTypeName = orderTypeName;
        }

        public String getSourceUserName() {
            return sourceUserName;
        }

        public void setSourceUserName(String sourceUserName) {
            this.sourceUserName = sourceUserName;
        }

        public String getInfoMoneyStr() {
            return infoMoneyStr;
        }

        public void setInfoMoneyStr(String infoMoneyStr) {
            this.infoMoneyStr = infoMoneyStr;
        }
    }
}
