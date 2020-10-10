package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-09 18:55
 */
public class BannerAndHospitalInfoBean {


    /**
     * hospitalInfo : {"area":"1002811","basicsRegionId":0,"city":"1002805","country":"0","flagDel":1,"hospitalInfoCode":"0","hospitalInfoId":5,"hospitalName":"省人民医院","hospitalNameChinese":"省人民医院","hospitalNameSpell":"srmyy","hospitalType":5000105,"imgUrl":"http://114.215.137.171:8040/interactImage/inquiry/df90285cf3be47e2b74f24837f0b4652/inquiry_20200914180154.jpg","logoImg":"1","province":"1000026"}
     * viewBasicsBannerFilesList : [{"appTypeCode":1,"appTypeName":"ios医生端","contentUrl":"http://www.baiduc.om","fileName":"abc.jpg","filePath":"AppBanner/broadcast/d_2.png","positionType":1,"sort":1,"subtitle":"宣传使用","title":"宣传","viewBannerUrl":"https://jiuyihtn.com/AppBanner/broadcast/d_2.png"},{"appTypeCode":1,"appTypeName":"ios医生端","contentUrl":"http://fanyi.baidu.com","fileName":"abc.jpg","filePath":"AppBanner/broadcast/d_1.png","positionType":2,"sort":1,"subtitle":"自己的平台推广链接","title":"自有平台推广","viewBannerUrl":"https://jiuyihtn.com/AppBanner/broadcast/d_1.png"},{"appTypeCode":1,"appTypeName":"ios医生端","contentUrl":"http://www.baidu.com","fileName":"abc.jpg","filePath":"AppBanner/broadcast/d_3.png","positionType":1,"sort":2,"subtitle":"分渠道推广链接","title":"推广链接","viewBannerUrl":"https://jiuyihtn.com/AppBanner/broadcast/d_3.png"}]
     */

    private HospitalInfoBean hospitalInfo;
    private List<ViewBasicsBannerFilesListBean> viewBasicsBannerFilesList;

    public HospitalInfoBean getHospitalInfo() {
        return hospitalInfo;
    }

    public void setHospitalInfo(HospitalInfoBean hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    public List<ViewBasicsBannerFilesListBean> getViewBasicsBannerFilesList() {
        return viewBasicsBannerFilesList;
    }

    public void setViewBasicsBannerFilesList(List<ViewBasicsBannerFilesListBean> viewBasicsBannerFilesList) {
        this.viewBasicsBannerFilesList = viewBasicsBannerFilesList;
    }

    public static class HospitalInfoBean {
        /**
         * area : 1002811
         * basicsRegionId : 0
         * city : 1002805
         * country : 0
         * flagDel : 1
         * hospitalInfoCode : 0
         * hospitalInfoId : 5
         * hospitalName : 省人民医院
         * hospitalNameChinese : 省人民医院
         * hospitalNameSpell : srmyy
         * hospitalType : 5000105
         * imgUrl : http://114.215.137.171:8040/interactImage/inquiry/df90285cf3be47e2b74f24837f0b4652/inquiry_20200914180154.jpg
         * logoImg : 1
         * province : 1000026
         */

        private String area;
        private int basicsRegionId;
        private String city;
        private String country;
        private int flagDel;
        private String hospitalInfoCode;
        private int hospitalInfoId;
        private String hospitalName;
        private String hospitalNameChinese;
        private String hospitalNameSpell;
        private int hospitalType;
        private String imgUrl;
        private String logoImg;
        private String province;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getBasicsRegionId() {
            return basicsRegionId;
        }

        public void setBasicsRegionId(int basicsRegionId) {
            this.basicsRegionId = basicsRegionId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getFlagDel() {
            return flagDel;
        }

        public void setFlagDel(int flagDel) {
            this.flagDel = flagDel;
        }

        public String getHospitalInfoCode() {
            return hospitalInfoCode;
        }

        public void setHospitalInfoCode(String hospitalInfoCode) {
            this.hospitalInfoCode = hospitalInfoCode;
        }

        public int getHospitalInfoId() {
            return hospitalInfoId;
        }

        public void setHospitalInfoId(int hospitalInfoId) {
            this.hospitalInfoId = hospitalInfoId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getHospitalNameChinese() {
            return hospitalNameChinese;
        }

        public void setHospitalNameChinese(String hospitalNameChinese) {
            this.hospitalNameChinese = hospitalNameChinese;
        }

        public String getHospitalNameSpell() {
            return hospitalNameSpell;
        }

        public void setHospitalNameSpell(String hospitalNameSpell) {
            this.hospitalNameSpell = hospitalNameSpell;
        }

        public int getHospitalType() {
            return hospitalType;
        }

        public void setHospitalType(int hospitalType) {
            this.hospitalType = hospitalType;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }

    public static class ViewBasicsBannerFilesListBean {
        /**
         * appTypeCode : 1
         * appTypeName : ios医生端
         * contentUrl : http://www.baiduc.om
         * fileName : abc.jpg
         * filePath : AppBanner/broadcast/d_2.png
         * positionType : 1
         * sort : 1
         * subtitle : 宣传使用
         * title : 宣传
         * viewBannerUrl : https://jiuyihtn.com/AppBanner/broadcast/d_2.png
         */

        private int appTypeCode;
        private String appTypeName;
        private String contentUrl;
        private String fileName;
        private String filePath;
        private int positionType;
        private int sort;
        private String subtitle;
        private String title;
        private String viewBannerUrl;

        public int getAppTypeCode() {
            return appTypeCode;
        }

        public void setAppTypeCode(int appTypeCode) {
            this.appTypeCode = appTypeCode;
        }

        public String getAppTypeName() {
            return appTypeName;
        }

        public void setAppTypeName(String appTypeName) {
            this.appTypeName = appTypeName;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getPositionType() {
            return positionType;
        }

        public void setPositionType(int positionType) {
            this.positionType = positionType;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getViewBannerUrl() {
            return viewBannerUrl;
        }

        public void setViewBannerUrl(String viewBannerUrl) {
            this.viewBannerUrl = viewBannerUrl;
        }
    }
}
