package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ExtEaseUtils {
    private final static int MODE_SPEC = android.os.Build.VERSION.SDK_INT <= 10 ? 0 : Context.MODE_MULTI_PROCESS;
    private String nickName;
    private String  imageUrl;
    private String userId;
    private ExtEaseUtils(){
    }
    private static class ExtEaseUtilsFactory{
        private static ExtEaseUtils instance =  new ExtEaseUtils();
    }
    public static ExtEaseUtils getInstance(){
        return ExtEaseUtilsFactory.instance;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
