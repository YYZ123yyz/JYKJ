package www.jykj.com.jykj_zxyl.app_base.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description:Retrofit工具类
 *
 * @author: qiuxinhai
 * @date: 2016/8/3 15:49
 */
public class RetrofitUtil {

    private static final String ENC_NAME = "utf-8";
    static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 根据param 构建RequestBody
     * @param params Map
     * @return String
     */
    @NonNull
    public static RequestBody requestBody(@Nullable Map params){
//        if (params != null) {
//          handlePage(params);
//        }
        return requestBody((Object) params);
    }

    /**
     * url参数编码
     * @param params Map
     * @return String
     */
    @NonNull
    public static String encodeParam(@Nullable Map params) {
      if (params != null) {
        handlePage(params);
      }
      return encodeParam((Object) params);
    }
    /**
     * url参数编码
     * @param params Map
     * @return String
     */
    @NonNull
    public static String encodeParam(@Nullable Map params, boolean needTranslate) {
      if (params != null) {
          if (needTranslate) {
              handlePage(params);
          }
      }
      return encodeParam((Object) params);
    }
    /**
     * 根据param 构建RequestBody
     * @param object Object
     * @return String
     */
    @NonNull
    private static RequestBody requestBody(@Nullable Object object){
        String jsonParam = toJSONStr(object);
        return RequestBody.create(MEDIA_TYPE_JSON,jsonParam);
    }

    /**
     * url参数编码
     * @param object Object
     * @return String
     */
    @NonNull
    private static String encodeParam(@Nullable Object object) {
        String jsonParam = toJSONStr(object);
        //LogUtil.i("HTTP", jsonParam);
        return encode(jsonParam);
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using a specific encoding scheme[UTF-8]
     */
    @NonNull
    private static String encode(@Nullable String s) {
        try {
            if (s == null) return "";
            return URLEncoder.encode(s, ENC_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    /**
     * Decodes a {@code application/x-www-form-urlencoded} string using a specific
     * encoding scheme[UTF-8]
     */
    @NonNull
    public static String decode(@Nullable String s) {
        try {
            if (s == null) return "";
            return URLDecoder.decode(s, ENC_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    /**
     * 转换为json格式的字符串
     *
     * @param obj
     * @return
     */
    @NonNull
    private static String toJSONStr(@Nullable Object obj) {
        Gson g = new GsonBuilder().create();
        return g.toJson(obj);
    }
    /**
     * 分页参数转换 pag将eIndex、pageIndex 转换成 firstResult、maxResult
     * @param paramMap Map
     */
    private static void handlePage(Map paramMap) {
        int pageIndex = 0;
        int pageSize = 0;
        if (paramMap.get("pageIndex") == null || TextUtils.isEmpty(paramMap.get("pageIndex").toString())
                || paramMap.get("pageSize") == null || TextUtils.isEmpty(paramMap.get("pageSize").toString())){
            return;
        }
        String pageIndexString = paramMap.get("pageIndex") != null ? paramMap
                .get("pageIndex").toString() : "1";
        String pageSizeString = paramMap.get("pageSize") != null ? paramMap
                .get("pageSize").toString() : "10";

        if (pageIndexString.length() > 0) {
            pageIndex = Integer.valueOf(pageIndexString);
        } else {
            pageIndex = 1;
        }
        if (pageSizeString.length() > 0) {
            pageSize = Integer.valueOf(pageSizeString);
        } else {
            pageSize = 10;
        }
        int firstResult = (pageIndex - 1) * pageSize;
        int maxResult = pageSize;
        paramMap.put("pageStart", firstResult);
        paramMap.put("pageSize", maxResult);
    }
}
