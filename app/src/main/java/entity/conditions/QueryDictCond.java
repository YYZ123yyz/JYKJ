package entity.conditions;

import java.io.Serializable;

public class QueryDictCond implements Serializable {
    private String baseCode;

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }
}
