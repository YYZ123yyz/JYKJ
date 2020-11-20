package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 17:44
 */
public class LiveProtromDetialBean {


    /**
     * riskName : 默认
     * syllabus : {"createMan":"915b29f3d1b7451fa1d4995a8f91b156","syllabusCode":"af5649d247f849948e79ddfda8ed1145","syllabusContent":"[{\"content\":\"哦哦哦嗯摸摸默默看莫莫莫\",\"uuId\":\"1605345735670\"}]"}
     * className : 默认
     * doctorInfo : {"departmentName":"传染科","departmentSecondName":"肝病科","doctorCode":"915b29f3d1b7451fa1d4995a8f91b156","doctorTitleName":"住院医生","goodAtRealm":"","hospitalInfoName":"北京市阜外医院","userLogoUrl":"https://jiuyihtn.com/fileUpload/doctorImage/logo/915b29f3d1b7451fa1d4995a8f91b156/logo_20201013154013.jpg","userName":"邱新海测试医生"}
     * imageList : [{"basicsImgId":282,"imgUrl":"https://jiuyihtn.com/fileUpload/defaultImage/915b29f3d1b7451fa1d4995a8f91b156/default_20201116174652_1.jpg","tableImgId":"223a294e4e404ef3adebf1dd6f29d78c"},{"basicsImgId":283,"imgUrl":"https://jiuyihtn.com/fileUpload/defaultImage/915b29f3d1b7451fa1d4995a8f91b156/default_20201116174652_2.jpg","tableImgId":"223a294e4e404ef3adebf1dd6f29d78c"}]
     */

    private String riskName;
    private SyllabusBean syllabus;
    private String className;
    private String attrName;
    private String broadcastTitle;
    private String imageCode;
    private DoctorInfoBean doctorInfo;
    private List<ImageListBean> imageList;

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public SyllabusBean getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(SyllabusBean syllabus) {
        this.syllabus = syllabus;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DoctorInfoBean getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfoBean doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public List<ImageListBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageListBean> imageList) {
        this.imageList = imageList;
    }

    public static class SyllabusBean {
        /**
         * createMan : 915b29f3d1b7451fa1d4995a8f91b156
         * syllabusCode : af5649d247f849948e79ddfda8ed1145
         * syllabusContent : [{"content":"哦哦哦嗯摸摸默默看莫莫莫","uuId":"1605345735670"}]
         */

        private String createMan;
        private String syllabusCode;
        private String syllabusContent;

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan;
        }

        public String getSyllabusCode() {
            return syllabusCode;
        }

        public void setSyllabusCode(String syllabusCode) {
            this.syllabusCode = syllabusCode;
        }

        public String getSyllabusContent() {
            return syllabusContent;
        }

        public void setSyllabusContent(String syllabusContent) {
            this.syllabusContent = syllabusContent;
        }
    }

    public static class DoctorInfoBean {
        /**
         * departmentName : 传染科
         * departmentSecondName : 肝病科
         * doctorCode : 915b29f3d1b7451fa1d4995a8f91b156
         * doctorTitleName : 住院医生
         * goodAtRealm :
         * hospitalInfoName : 北京市阜外医院
         * userLogoUrl : https://jiuyihtn.com/fileUpload/doctorImage/logo/915b29f3d1b7451fa1d4995a8f91b156/logo_20201013154013.jpg
         * userName : 邱新海测试医生
         */

        private String departmentName;
        private String departmentSecondName;
        private String doctorCode;
        private String doctorTitleName;
        private String goodAtRealm;
        private String hospitalInfoName;
        private String userLogoUrl;
        private String userName;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getDepartmentSecondName() {
            return departmentSecondName;
        }

        public void setDepartmentSecondName(String departmentSecondName) {
            this.departmentSecondName = departmentSecondName;
        }

        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

        public String getDoctorTitleName() {
            return doctorTitleName;
        }

        public void setDoctorTitleName(String doctorTitleName) {
            this.doctorTitleName = doctorTitleName;
        }

        public String getGoodAtRealm() {
            return goodAtRealm;
        }

        public void setGoodAtRealm(String goodAtRealm) {
            this.goodAtRealm = goodAtRealm;
        }

        public String getHospitalInfoName() {
            return hospitalInfoName;
        }

        public void setHospitalInfoName(String hospitalInfoName) {
            this.hospitalInfoName = hospitalInfoName;
        }

        public String getUserLogoUrl() {
            return userLogoUrl;
        }

        public void setUserLogoUrl(String userLogoUrl) {
            this.userLogoUrl = userLogoUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class ImageListBean {
        /**
         * basicsImgId : 282
         * imgUrl : https://jiuyihtn.com/fileUpload/defaultImage/915b29f3d1b7451fa1d4995a8f91b156/default_20201116174652_1.jpg
         * tableImgId : 223a294e4e404ef3adebf1dd6f29d78c
         */

        private int basicsImgId;
        private String imgUrl;
        private String tableImgId;

        public int getBasicsImgId() {
            return basicsImgId;
        }

        public void setBasicsImgId(int basicsImgId) {
            this.basicsImgId = basicsImgId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTableImgId() {
            return tableImgId;
        }

        public void setTableImgId(String tableImgId) {
            this.tableImgId = tableImgId;
        }
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getBroadcastTitle() {
        return broadcastTitle;
    }

    public void setBroadcastTitle(String broadcastTitle) {
        this.broadcastTitle = broadcastTitle;
    }
}
