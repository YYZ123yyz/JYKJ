package www.jykj.com.jykj_zxyl.app_base.base_utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：项目的util工具 gson解析
 */
public class GsonUtils {
    public static Gson gson = new Gson();
    /**
     * 说明：如果解析抛异常返回null
     * @param result 要解析的json字符串
     * @param clazz 对应的javabean的字节码
     * @return 返回 对应的javabean 对象
     */
    public static <T> T fromJson(String result, Class<T> clazz){
        try{
            if(gson==null){
                gson = new Gson();
            }
            return gson.fromJson(result, clazz);
        }catch (Exception e){

            return null;
        }
    }

    public static String toJson(Object obj){
        if(null == gson){
            gson = new Gson();
        }
        return gson.toJson(obj);
    }


    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        if(StringUtils.isNotEmpty(json)){
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }
}
