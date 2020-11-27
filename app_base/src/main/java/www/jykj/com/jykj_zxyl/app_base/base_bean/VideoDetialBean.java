package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-25 16:20
 */
public class VideoDetialBean {
    /**
     * 课件code
     */
    private String code;
    /**
     * 课件title
     */
    private String title;
    /**
     * 课件图标地址
     */
    private String iconUrl;

    /**
     * 父级课件code(0为顶级)
     */
    private String parentCode;
    /**
     * 课件描述
     */
    private String remark;
    /**
     * 课件资源链接地址
     */
    private String linkUrl;
    /**
     * 免费类型(0免费1收费 2部分收费)
     */
    private Integer freeType;
    /**
     * 会员免费类型(0免费1收费)
     */
    private Integer vipFreeType;
    /**
     * 状态(0下架1上架)
     */
    private Integer status;
    /**
     * 浏览量
     */
    private Integer clickCount;
    /**
     * 时长
     */
    private String duration;
    /**
     * 来源类型(0未知1直播录制2用户上传3平台上传)
     */
    private Integer sourceType;
    /**
     * 直播间编码
     */
    private String liveRoomCode;
    /**
     * 医生编码
     */
    private String doctorCode;
    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 医生职称
     */
    private String doctorTitleName;
    /**
     * 医生简介
     */
    private String doctorSynopsis;
    /**
     * 医生擅长领域
     */
    private String doctorGoodAtRealm;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getFreeType() {
        return freeType;
    }

    public void setFreeType(Integer freeType) {
        this.freeType = freeType;
    }

    public Integer getVipFreeType() {
        return vipFreeType;
    }

    public void setVipFreeType(Integer vipFreeType) {
        this.vipFreeType = vipFreeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getLiveRoomCode() {
        return liveRoomCode;
    }

    public void setLiveRoomCode(String liveRoomCode) {
        this.liveRoomCode = liveRoomCode;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTitleName() {
        return doctorTitleName;
    }

    public void setDoctorTitleName(String doctorTitleName) {
        this.doctorTitleName = doctorTitleName;
    }

    public String getDoctorSynopsis() {
        return doctorSynopsis;
    }

    public void setDoctorSynopsis(String doctorSynopsis) {
        this.doctorSynopsis = doctorSynopsis;
    }

    public String getDoctorGoodAtRealm() {
        return doctorGoodAtRealm;
    }

    public void setDoctorGoodAtRealm(String doctorGoodAtRealm) {
        this.doctorGoodAtRealm = doctorGoodAtRealm;
    }
}
