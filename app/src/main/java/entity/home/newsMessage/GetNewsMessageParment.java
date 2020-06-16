package entity.home.newsMessage;

/**
 * 获取新消息参数
 */
public class GetNewsMessageParment {

    private String rowNum;
    private String pageNum;
    private String loginDoctorPosition;
    private String searchDoctorCode;
    private String msgType;
    private String flagMsgRead;

    private String doctorCode;

    private String searchDoctorName;

    public String getSearchDoctorName() {
        return searchDoctorName;
    }

    public void setSearchDoctorName(String searchDoctorName) {
        this.searchDoctorName = searchDoctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

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

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getSearchDoctorCode() {
        return searchDoctorCode;
    }

    public void setSearchDoctorCode(String searchDoctorCode) {
        this.searchDoctorCode = searchDoctorCode;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getFlagMsgRead() {
        return flagMsgRead;
    }

    public void setFlagMsgRead(String flagMsgRead) {
        this.flagMsgRead = flagMsgRead;
    }
}
