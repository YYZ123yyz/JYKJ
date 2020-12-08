package www.jykj.com.jykj_zxyl.app_base.base_utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-04-17 17:24
 */
public class RxUtils {
    /**
     * 可以根据需求自行修改
     *
     * @param fileName  后台协定的接受图片的name（没特殊要求就可以随便写）
     * @param paramsMap 普通参数 图文混合参数
     * @param filePaths 图片路径
     * @return List<MultipartBody.Part>
     */
    public static List<MultipartBody.Part> getMultipartPart(String fileName, Map<String, Object> paramsMap, List<String> filePaths) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {

                Object o = paramsMap.get(key);
                if(o!=null){
                    builder.addFormDataPart(key, o.toString());
                }

            }
        }

        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //"fileName"+i 后台接收图片流的参数名
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }
        if(CollectionUtils.isEmpty(filePaths)){
            builder.addFormDataPart("","");
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        return parts;
    }
}
