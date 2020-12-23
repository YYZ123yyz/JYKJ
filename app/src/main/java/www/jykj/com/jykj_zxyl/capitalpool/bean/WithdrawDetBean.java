package www.jykj.com.jykj_zxyl.capitalpool.bean;

import java.io.Serializable;


public class WithdrawDetBean implements Serializable {


    /**
     * approCreateDate : 1608607953000
     * cashMoney : 200.0
     * platformPersonTax : 13.0
     * platformPersonTaxCost : 26.0
     * platformPersonTaxMin : 20.0
     * platformService : 2.0
     * platformServiceCost : 10.0
     * platformServiceMin : 10.0
     * realMoney : 200.0
     * withdrawalCode : 5d0b75793aae4a32bdfed475117f21ad
     * withdrawalDate : 1608480000000
     * withdrawalDateTime : 1608480000000
     * withdrawalState : 2
     */

    private long approCreateDate;
    private double cashMoney;
    private double platformPersonTax;
    private double platformPersonTaxCost;
    private double platformPersonTaxMin;
    private double platformService;
    private double platformServiceCost;
    private double platformServiceMin;
    private double realMoney;
    private String withdrawalCode;
    private long withdrawalDate;
    private long withdrawalDateTime;
    private int withdrawalState;

    public long getApproCreateDate() {
        return approCreateDate;
    }

    public void setApproCreateDate(long approCreateDate) {
        this.approCreateDate = approCreateDate;
    }

    public double getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(double cashMoney) {
        this.cashMoney = cashMoney;
    }

    public double getPlatformPersonTax() {
        return platformPersonTax;
    }

    public void setPlatformPersonTax(double platformPersonTax) {
        this.platformPersonTax = platformPersonTax;
    }

    public double getPlatformPersonTaxCost() {
        return platformPersonTaxCost;
    }

    public void setPlatformPersonTaxCost(double platformPersonTaxCost) {
        this.platformPersonTaxCost = platformPersonTaxCost;
    }

    public double getPlatformPersonTaxMin() {
        return platformPersonTaxMin;
    }

    public void setPlatformPersonTaxMin(double platformPersonTaxMin) {
        this.platformPersonTaxMin = platformPersonTaxMin;
    }

    public double getPlatformService() {
        return platformService;
    }

    public void setPlatformService(double platformService) {
        this.platformService = platformService;
    }

    public double getPlatformServiceCost() {
        return platformServiceCost;
    }

    public void setPlatformServiceCost(double platformServiceCost) {
        this.platformServiceCost = platformServiceCost;
    }

    public double getPlatformServiceMin() {
        return platformServiceMin;
    }

    public void setPlatformServiceMin(double platformServiceMin) {
        this.platformServiceMin = platformServiceMin;
    }

    public double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(double realMoney) {
        this.realMoney = realMoney;
    }

    public String getWithdrawalCode() {
        return withdrawalCode;
    }

    public void setWithdrawalCode(String withdrawalCode) {
        this.withdrawalCode = withdrawalCode;
    }

    public long getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(long withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public long getWithdrawalDateTime() {
        return withdrawalDateTime;
    }

    public void setWithdrawalDateTime(long withdrawalDateTime) {
        this.withdrawalDateTime = withdrawalDateTime;
    }

    public int getWithdrawalState() {
        return withdrawalState;
    }

    public void setWithdrawalState(int withdrawalState) {
        this.withdrawalState = withdrawalState;
    }
}
