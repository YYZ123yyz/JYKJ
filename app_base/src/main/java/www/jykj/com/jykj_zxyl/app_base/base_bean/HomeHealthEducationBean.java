package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-20 17:52
 */
public class HomeHealthEducationBean  extends MultiItemEntity{

    /**
     * 类型(0未知1视频2音频3图文4课件)
     */
    private int type;
    /**
     * 资讯类型code
     */
    private String newsTypeCode;
    /**
     * 封面地址
     */
    private String coverImgUrl;

    /**
     * 预告、热播、讲座、图文、课件 12345
     */
    private int state;
    /**
     * 直播类型名称.Eg.视频直播;音频直播;图文资讯;直播课件
     */
    private String contentTypeName;
    /**
     * 标题
     */
    private String title;
    /**
     * 关键词名称.Eg.医学^降血压^心血管健康
     */
    private String attrName;
    /**
     * 类目名称.Eg.诊断^治疗^康复
     */
    private String className;
    /**
     * 人数统计前缀名称.Eg.关注人数(预告);观看人数(热播);浏览量(专题讲座);人看过(图文)
     */
    private String prefixStatisticsNumName;
    /**
     * 观看人数
     */
    private int showWatchNum;
    /**
     * 关联编码（type为1和2时存入直播详情code,type为3时存入图文code,type为4时存入课件code）
     */
    private String relationCode;
    /**
     * 展示：观看价格.Eg.免费;¥40.00
     */
    private String showExtendBroadcastPrice;
    /**
     * 跳转类型.0:详情(视频、音频)1:跳转链接地址(图文)2:跳转列表(课件)
     */
    private int linkType;
    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 推流地址
     */
    private String pushUrl;

    /**
     * 用户code
     */
    private String userCode;

    private String chatRoomCode;
    /**
     * 创建时间
     */
    private long createtime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNewsTypeCode() {
        return newsTypeCode;
    }

    public void setNewsTypeCode(String newsTypeCode) {
        this.newsTypeCode = newsTypeCode;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }



    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPrefixStatisticsNumName() {
        return prefixStatisticsNumName;
    }

    public void setPrefixStatisticsNumName(String prefixStatisticsNumName) {
        this.prefixStatisticsNumName = prefixStatisticsNumName;
    }

    public int getShowWatchNum() {
        return showWatchNum;
    }

    public void setShowWatchNum(int showWatchNum) {
        this.showWatchNum = showWatchNum;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getShowExtendBroadcastPrice() {
        return showExtendBroadcastPrice;
    }

    public void setShowExtendBroadcastPrice(String showExtendBroadcastPrice) {
        this.showExtendBroadcastPrice = showExtendBroadcastPrice;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getChatRoomCode() {
        return chatRoomCode;
    }

    public void setChatRoomCode(String chatRoomCode) {
        this.chatRoomCode = chatRoomCode;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
