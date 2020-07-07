package entity.liveroom;

import java.io.Serializable;

public class DictInfo implements Serializable {
    private String attrCode;
    private String attrName;
    private Integer baseCode;
    private String baseName;

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(Integer baseCode) {
        this.baseCode = baseCode;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}
