package www.jykj.com.jykj_zxyl.app_base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-16 16:01
 */
public class ImageInfoBean {

    private String ItemID;                 //条目ID，该ID用于关联该图片是哪一个的图片（企业环境或者商品）
    private String PhotoID;                //图片ID          （MD5）
    private String Photo;                  //原图内容          （Base64转成的字符串）
    private String PhotoSL;                 //缩略图内容          （Base64转成的字符串）
    private String PhotoUrl;               //图片URL
    private String PhotoUrlSL;             //缩略图URL
    private int uploadStatus;//上传图片状态
    private int basicsImgId;
    private String tableImgId;
    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getPhotoID() {
        return PhotoID;
    }

    public void setPhotoID(String photoID) {
        PhotoID = photoID;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPhotoSL() {
        return PhotoSL;
    }

    public void setPhotoSL(String photoSL) {
        PhotoSL = photoSL;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getPhotoUrlSL() {
        return PhotoUrlSL;
    }

    public void setPhotoUrlSL(String photoUrlSL) {
        PhotoUrlSL = photoUrlSL;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getBasicsImgId() {
        return basicsImgId;
    }

    public void setBasicsImgId(int basicsImgId) {
        this.basicsImgId = basicsImgId;
    }



    public String getTableImgId() {
        return tableImgId;
    }

    public void setTableImgId(String tableImgId) {
        this.tableImgId = tableImgId;
    }
}
