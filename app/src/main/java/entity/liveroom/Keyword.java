package entity.liveroom;

import java.io.Serializable;

public class Keyword implements Serializable {
    private String keywordsCode;
    private String keywordsName;
    private String selectState = "0";

    public String getKeywordsCode() {
        return keywordsCode;
    }

    public void setKeywordsCode(String keywordsCode) {
        this.keywordsCode = keywordsCode;
    }

    public String getKeywordsName() {
        return keywordsName;
    }

    public void setKeywordsName(String keywordsName) {
        this.keywordsName = keywordsName;
    }

    public String getSelectState() {
        return selectState;
    }

    public void setSelectState(String selectState) {
        this.selectState = selectState;
    }
}
