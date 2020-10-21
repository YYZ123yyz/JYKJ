package entity.liveroom;

import java.io.Serializable;

public class SubFocusResp implements Serializable {
    private String detailsCode;
    private String mainUserCode;
    private String thumbsUpUserCode;

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }

    public String getMainUserCode() {
        return mainUserCode;
    }

    public void setMainUserCode(String mainUserCode) {
        this.mainUserCode = mainUserCode;
    }

    public String getThumbsUpUserCode() {
        return thumbsUpUserCode;
    }

    public void setThumbsUpUserCode(String thumbsUpUserCode) {
        this.thumbsUpUserCode = thumbsUpUserCode;
    }
}
