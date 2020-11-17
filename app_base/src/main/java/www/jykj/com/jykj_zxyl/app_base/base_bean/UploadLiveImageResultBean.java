package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-16 17:57
 */
public class UploadLiveImageResultBean {

    /**
     * createDate : 1605520601734
     * createMan : 915b29f3d1b7451fa1d4995a8f91b156
     * detailsCode : 9a0efd437ba24beca6bd3a50ee7ad992
     * imageCode : 9a418424edec46bdba47488758a54e03
     * imageIdArray : [284, 285]
     */

    private long createDate;
    private String createMan;
    private String detailsCode;
    private String imageCode;
    private List<String> imageIdArray;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(String detailsCode) {
        this.detailsCode = detailsCode;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public List<String> getImageIdArray() {
        return imageIdArray;
    }

    public void setImageIdArray(List<String> imageIdArray) {
        this.imageIdArray = imageIdArray;
    }
}
