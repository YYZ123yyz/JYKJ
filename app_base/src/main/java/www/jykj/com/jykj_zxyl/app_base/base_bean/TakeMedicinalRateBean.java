package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-17 18:26
 */
public class TakeMedicinalRateBean {


    /**
     * attrCode : 1003501
     * attrName : 每天一次
     * baseCode : 10035
     * baseName : 用药频率
     * flagDataType : 0
     * flagLoad : 0
     */

    private int attrCode;
    private String attrName;
    private int baseCode;
    private String baseName;
    private int flagDataType;
    private int flagLoad;

    public int getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(int attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(int baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public int getFlagDataType() {
        return flagDataType;
    }

    public void setFlagDataType(int flagDataType) {
        this.flagDataType = flagDataType;
    }

    public int getFlagLoad() {
        return flagLoad;
    }

    public void setFlagLoad(int flagLoad) {
        this.flagLoad = flagLoad;
    }
}
