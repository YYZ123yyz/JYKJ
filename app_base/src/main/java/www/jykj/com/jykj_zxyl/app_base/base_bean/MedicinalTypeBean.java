package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-17 11:06
 */
public class MedicinalTypeBean {


    /**
     * attrCode : 1003601
     * attrName : 利尿剂
     * baseCode : 10036
     * baseName : 药物分类
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
