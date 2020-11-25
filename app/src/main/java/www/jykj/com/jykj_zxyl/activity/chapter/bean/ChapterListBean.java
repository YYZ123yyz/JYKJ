package www.jykj.com.jykj_zxyl.activity.chapter.bean;

import java.util.List;

public class ChapterListBean {

    /**
     * vipFreeType : 0
     * createBy : admin
     * secondList : [{"vipFreeType":0,"duration":"00:00","code":"d2e7c518d737442cb6b3b11765b7cac3","clickCount":0,"price":"0.00","freeType":0,"iconUrl":"http://114.215.137.171:8040/liveImage/cover/915b29f3d1b7451fa1d4995a8f91b156/cover_20201120095105.jpg","title":"测试课件的子集-第一章","flagUserHasBuy":0},{"vipFreeType":0,"duration":"00:00","code":"d2e7c518d737442cb6b3b11765b7cac2","clickCount":0,"price":"0.00","freeType":0,"iconUrl":"http://114.215.137.171:8040/liveImage/cover/915b29f3d1b7451fa1d4995a8f91b156/cover_20201120095105.jpg","title":"测试课件的子集-第二章","flagUserHasBuy":0}]
     * code : d2e7c518d737442cb6b3b11765b7cac6
     * clickCount : 0
     * price : 0.00
     * freeType : 0
     * iconUrl : http://114.215.137.171:8040/liveImage/cover/915b29f3d1b7451fa1d4995a8f91b156/cover_20201120095105.jpg
     * title : 测试课件
     */

    private int vipFreeType;
    private String createBy;
    private String code;
    private int clickCount;
    private String price;
    private int freeType;
    private String iconUrl;
    private String title;
    private String className;
    private String doctorLogoUrl;

    public String getDoctorLogoUrl() {
        return doctorLogoUrl;
    }

    public void setDoctorLogoUrl(String doctorLogoUrl) {
        this.doctorLogoUrl = doctorLogoUrl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * vipFreeType : 0
     * duration : 00:00
     * code : d2e7c518d737442cb6b3b11765b7cac3
     * clickCount : 0
     * price : 0.00
     * freeType : 0
     * iconUrl : http://114.215.137.171:8040/liveImage/cover/915b29f3d1b7451fa1d4995a8f91b156/cover_20201120095105.jpg
     * title : 测试课件的子集-第一章
     * flagUserHasBuy : 0
     */

    private List<SecondListBean> secondList;

    public int getVipFreeType() {
        return vipFreeType;
    }

    public void setVipFreeType(int vipFreeType) {
        this.vipFreeType = vipFreeType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFreeType() {
        return freeType;
    }

    public void setFreeType(int freeType) {
        this.freeType = freeType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SecondListBean> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<SecondListBean> secondList) {
        this.secondList = secondList;
    }

    public static class SecondListBean {
        private int vipFreeType;
        private String duration;
        private String code;
        private int clickCount;
        private String price;
        private int freeType;
        private String iconUrl;
        private String title;
        private int flagUserHasBuy;

        public int getVipFreeType() {
            return vipFreeType;
        }

        public void setVipFreeType(int vipFreeType) {
            this.vipFreeType = vipFreeType;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getFreeType() {
            return freeType;
        }

        public void setFreeType(int freeType) {
            this.freeType = freeType;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFlagUserHasBuy() {
            return flagUserHasBuy;
        }

        public void setFlagUserHasBuy(int flagUserHasBuy) {
            this.flagUserHasBuy = flagUserHasBuy;
        }
    }
}
