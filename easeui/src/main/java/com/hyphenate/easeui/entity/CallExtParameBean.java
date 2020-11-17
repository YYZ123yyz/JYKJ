package com.hyphenate.easeui.entity;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-13 16:28
 */
public class CallExtParameBean implements Serializable {

    private String nickName;
    private String imageUrl;
    private int surplusDuration;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSurplusDuration() {
        return surplusDuration;
    }

    public void setSurplusDuration(int surplusDuration) {
        this.surplusDuration = surplusDuration;
    }
}
