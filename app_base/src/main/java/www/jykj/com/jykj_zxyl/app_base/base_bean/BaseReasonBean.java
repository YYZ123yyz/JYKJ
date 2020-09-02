package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-31 18:48
 */
public class BaseReasonBean implements Serializable {


    /**
     * attrCode : 90003001
     * attrName : 患者医从性不高
     * baseCode : 90003
     * baseName : 医生解约原因
     * flagDataType : 0
     * flagLoad : 0
     */

    private int attrCode;
    private String attrName;
    private int baseCode;
    private String baseName;
    private int flagDataType;
    private int flagLoad;
    private boolean isChoosed;
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

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
