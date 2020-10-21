package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-20 20:25
 */
public class ImageTextBean {

    /**
     * flagImageTextVisitType : 1
     * imageTextCode : 333666999
     * imageTextContent : <p style="letter-spacing: 1px;line-height: 1.75em;"><span style="color: rgb(87, 163, 250);"><strong><span style="font-size: 15px;">超重或肥胖、高盐饮食、吸烟、长期饮酒、长期精神紧张、体力活动不足</span></strong></span><span style="font-size: 15px;color: #585858;">的人群是高血压的高危人群。</span></p>
     * imageTextCreateDate : 2020-10-06
     * imageTextTitle : 测试标题111
     * imageTextWatchUrl :
     * prefixStatisticsNumName : 人看过
     * showWatchNum : 888
     */

    private int flagImageTextVisitType;
    private String imageTextCode;
    private String imageTextContent;
    private String imageTextCreateDate;
    private String imageTextTitle;
    private String imageTextWatchUrl;
    private String prefixStatisticsNumName;
    private String showWatchNum;

    public int getFlagImageTextVisitType() {
        return flagImageTextVisitType;
    }

    public void setFlagImageTextVisitType(int flagImageTextVisitType) {
        this.flagImageTextVisitType = flagImageTextVisitType;
    }

    public String getImageTextCode() {
        return imageTextCode;
    }

    public void setImageTextCode(String imageTextCode) {
        this.imageTextCode = imageTextCode;
    }

    public String getImageTextContent() {
        return imageTextContent;
    }

    public void setImageTextContent(String imageTextContent) {
        this.imageTextContent = imageTextContent;
    }

    public String getImageTextCreateDate() {
        return imageTextCreateDate;
    }

    public void setImageTextCreateDate(String imageTextCreateDate) {
        this.imageTextCreateDate = imageTextCreateDate;
    }

    public String getImageTextTitle() {
        return imageTextTitle;
    }

    public void setImageTextTitle(String imageTextTitle) {
        this.imageTextTitle = imageTextTitle;
    }

    public String getImageTextWatchUrl() {
        return imageTextWatchUrl;
    }

    public void setImageTextWatchUrl(String imageTextWatchUrl) {
        this.imageTextWatchUrl = imageTextWatchUrl;
    }

    public String getPrefixStatisticsNumName() {
        return prefixStatisticsNumName;
    }

    public void setPrefixStatisticsNumName(String prefixStatisticsNumName) {
        this.prefixStatisticsNumName = prefixStatisticsNumName;
    }

    public String getShowWatchNum() {
        return showWatchNum;
    }

    public void setShowWatchNum(String showWatchNum) {
        this.showWatchNum = showWatchNum;
    }
}
