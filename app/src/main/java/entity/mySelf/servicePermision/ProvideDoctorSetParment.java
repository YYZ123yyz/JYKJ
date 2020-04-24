package entity.mySelf.servicePermision;

/**
 * 保存设置参数
 */
public class ProvideDoctorSetParment {
    private             String              loginDoctorPosition;
    private             String              operDoctorCode;
    private             String              operDoctorName;
    private             String              serviceType;
    private             String              flagOpening;
    private             String              priceBasics;
    private             String              pricePremium;
    private             String              sourceNumBasics;
    private             String              sourceNumPremium;
    private             String              sourceNumFree;

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getOperDoctorCode() {
        return operDoctorCode;
    }

    public void setOperDoctorCode(String operDoctorCode) {
        this.operDoctorCode = operDoctorCode;
    }

    public String getOperDoctorName() {
        return operDoctorName;
    }

    public void setOperDoctorName(String operDoctorName) {
        this.operDoctorName = operDoctorName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getFlagOpening() {
        return flagOpening;
    }

    public void setFlagOpening(String flagOpening) {
        this.flagOpening = flagOpening;
    }

    public String getPriceBasics() {
        return priceBasics;
    }

    public void setPriceBasics(String priceBasics) {
        this.priceBasics = priceBasics;
    }

    public String getPricePremium() {
        return pricePremium;
    }

    public void setPricePremium(String pricePremium) {
        this.pricePremium = pricePremium;
    }

    public String getSourceNumBasics() {
        return sourceNumBasics;
    }

    public void setSourceNumBasics(String sourceNumBasics) {
        this.sourceNumBasics = sourceNumBasics;
    }

    public String getSourceNumPremium() {
        return sourceNumPremium;
    }

    public void setSourceNumPremium(String sourceNumPremium) {
        this.sourceNumPremium = sourceNumPremium;
    }

    public String getSourceNumFree() {
        return sourceNumFree;
    }

    public void setSourceNumFree(String sourceNumFree) {
        this.sourceNumFree = sourceNumFree;
    }
}
