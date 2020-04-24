package entity;

public class IDCardScanningParment {
    private         String        Action;                   //公共参数，本接口取值：IDCardOCR
    private         String        Version;                  //公共参数，本接口取值：2018-11-19
    private         String        Region;                   //公共参数，详见产品支持的 地域列表，本接口仅支持其中的: ap-beijing, ap-guangzhou, ap-hongkong, ap-shanghai, na-toronto
    private         String        ImageBase64;              //图片的 Base64 值。
    private         String        ImageUrl;                 //图片的 Url 地址
    private         String        CardSide;                 //FRONT 为身份证有照片的一面（人像面），BACK 为身份证有国徽的一面（国徽面）。
    private         String        Config;                   //可选字段，根据需要选择是否请求对应字段。


    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getImageBase64() {
        return ImageBase64;
    }

    public void setImageBase64(String imageBase64) {
        ImageBase64 = imageBase64;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCardSide() {
        return CardSide;
    }

    public void setCardSide(String cardSide) {
        CardSide = cardSide;
    }

    public String getConfig() {
        return Config;
    }

    public void setConfig(String config) {
        Config = config;
    }
}
