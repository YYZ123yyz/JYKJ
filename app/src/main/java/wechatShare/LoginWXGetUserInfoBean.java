package wechatShare;

import java.io.Serializable;

public class LoginWXGetUserInfoBean implements Serializable{


    /**
     * loginDoctorPosition : 108.93425^34.23053
     * requestClientType : 1
     * openId : o_Zkp48kC0V9gA6ziQkruEktKJ-c
     * unionId : V9gA6ziQk
     * nickName : 哈哈?
     * gender : 1
     * avatarUrl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoXIjPoDjMB42GBr4X05dzB3ExmgQ43a07FD1p1rJC8MESlFSLiccxicw6NAAvkvh3OraPIkcUSZR7w/132
     * province :
     * city :
     * country :
     * privilege :
     */

    private String loginDoctorPosition;
    private String requestClientType;
    private String openId;
    private String unionId;
    private String nickName;
    private String gender;
    private String avatarUrl;
    private String province;
    private String city;
    private String country;
    private String privilege;

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
