package entity.liveroom;

import java.io.Serializable;

public class QueryLiveroomCond implements Serializable {
    private String rowNum;
    private String pageNum;
    private String loginUserPosition;
    private String requestClientType;
    private String operUserCode;
    private String operUserName;
    private String searchUserName;
    private String searchBroadcastTitle;
    private String searchKeywordsCode;
    private String searchClassCode;
    private String searchRiskCode;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

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

    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName;
    }

    public String getSearchBroadcastTitle() {
        return searchBroadcastTitle;
    }

    public void setSearchBroadcastTitle(String searchBroadcastTitle) {
        this.searchBroadcastTitle = searchBroadcastTitle;
    }

    public String getSearchKeywordsCode() {
        return searchKeywordsCode;
    }

    public void setSearchKeywordsCode(String searchKeywordsCode) {
        this.searchKeywordsCode = searchKeywordsCode;
    }

    public String getSearchClassCode() {
        return searchClassCode;
    }

    public void setSearchClassCode(String searchClassCode) {
        this.searchClassCode = searchClassCode;
    }

    public String getSearchRiskCode() {
        return searchRiskCode;
    }

    public void setSearchRiskCode(String searchRiskCode) {
        this.searchRiskCode = searchRiskCode;
    }
}
