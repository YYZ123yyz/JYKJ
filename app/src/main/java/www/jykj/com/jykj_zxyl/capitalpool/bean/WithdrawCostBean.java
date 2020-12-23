package www.jykj.com.jykj_zxyl.capitalpool.bean;

public class WithdrawCostBean {

    /**
     * platformPersonTax : 13.00%
     * platformService : 2.00%
     * platformServeCost : 10.0000
     * platformPersonTaxCost : 20.0000
     * platformServiceMin : 10.0000
     * platformPersonTaxMin : 20.0000
     */

    private String platformPersonTax;
    private String platformService;
    private String platformServeCost;
    private String platformPersonTaxCost;
    private String platformServiceMin;
    private String platformPersonTaxMin;
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPlatformPersonTax() {
        return platformPersonTax;
    }

    public void setPlatformPersonTax(String platformPersonTax) {
        this.platformPersonTax = platformPersonTax;
    }

    public String getPlatformService() {
        return platformService;
    }

    public void setPlatformService(String platformService) {
        this.platformService = platformService;
    }

    public String getPlatformServeCost() {
        return platformServeCost;
    }

    public void setPlatformServeCost(String platformServeCost) {
        this.platformServeCost = platformServeCost;
    }

    public String getPlatformPersonTaxCost() {
        return platformPersonTaxCost;
    }

    public void setPlatformPersonTaxCost(String platformPersonTaxCost) {
        this.platformPersonTaxCost = platformPersonTaxCost;
    }

    public String getPlatformServiceMin() {
        return platformServiceMin;
    }

    public void setPlatformServiceMin(String platformServiceMin) {
        this.platformServiceMin = platformServiceMin;
    }

    public String getPlatformPersonTaxMin() {
        return platformPersonTaxMin;
    }

    public void setPlatformPersonTaxMin(String platformPersonTaxMin) {
        this.platformPersonTaxMin = platformPersonTaxMin;
    }
}
