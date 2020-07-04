package entity.conditions;

import java.io.Serializable;

public class QueryKeyworkCond implements Serializable {
    private String loginUserPosition;
    private String requestClientType;
    private String operUserCode;
    private String operUserName;
    private String searchKeywordsType;

    public String getLoginUserPosition() {
        return loginUserPosition;
    }

    public void setLoginUserPosition(String loginUserPosition) {
        this.loginUserPosition = loginUserPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOperUserCode() {
        return operUserCode;
    }

    public void setOperUserCode(String operUserCode) {
        this.operUserCode = operUserCode;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getSearchKeywordsType() {
        return searchKeywordsType;
    }

    public void setSearchKeywordsType(String searchKeywordsType) {
        this.searchKeywordsType = searchKeywordsType;
    }
}
