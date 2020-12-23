package www.jykj.com.jykj_zxyl.app_base.http;

import com.allen.library.RxHttpUtils;

import www.jykj.com.jykj_zxyl.app_base.BuildConfig;


/**
 * Description:Api帮助类
 *
 * @author: qiuxinhai
 * @date: 2018/6/3 16:10
 * <p>
 * 多baseUrl的底层API的底层代码如下，RxHttpUtils只是为通用方法又包装了一层而已
 * 如果RxHttpUtils现有方式不能满足你，完全可以使用如下方式自定义自己的配置信息
 * <p>
 * RxHttpUtils.createApi("baseUrlKey1", "baseUrlValue1", XXXApi1.class);
 * 这两种写法作用相同
 * ApiFactory.getInstance().createApi("baseUrlKey1", "baseUrlValue1", XXXApi1.class);
 * ApiFactory.getInstance().createApi("baseUrlKey2", "baseUrlValue2", XXXApi2.class);
 * ApiFactory.getInstance().createApi("baseUrlKey3", "baseUrlValue3", XXXApi3.class);
 * <p>
 * 如果项目中只有一个baseUrl除了上边方式之外可以使用封装好的方法
 * ApiFactory.getInstance().createApi(XXXApi.class);
 */
public class ApiHelper {

    /**
     * 获取轻说api
     *
     * @return ApiService
     */
    public static ApiService getApiService() {
        if (BuildConfig.API_ONLINE) {
            return getOnLineApi();
        } else {
            return getDevTestApi();
        }
    }

     /**
     * 获取线上的api
     *
     * @return ApiService
     */
    public static ApiService getOnLineApi(){
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_API_ONLINE_KEY,
                AppUrlConfig.SERVICE_API_ONLINE_URL, ApiService.class);
    }

    /**
     * 获取线下测试api
     * @return ApiService
     */
    public static ApiService getDevTestApi(){
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_API_TEST_KEY,
                AppUrlConfig.SERVICE_API_TEST_URL, ApiService.class);
    }


    /**
     * 获取直播Api
     * @return ApiService
     */
    public static ApiService getLiveApi(){
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_LIVE_API_KEY,
                AppUrlConfig.SERVICE_LIVE_API_URL, ApiService.class);
    }

    /**
     * 获取资金池Api
     * @return ApiService
     */
    public static ApiService getFundPoolApi(){
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_FUND_POOL_API_KEY,
                AppUrlConfig.SERVICE_FUND_POOL_API_URL, ApiService.class);
    }


    /**
     * 获取本地Api
     *
     * @return ApiService
     */
    public static ApiService getLocalApi() {
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_API_LOCAL_KEY,
                AppUrlConfig.SERVICE_API_LOCAL_URL, ApiService.class);
    }


    /**
     * 获取本地Api
     *
     * @return ApiService
     */
    public static ApiService getLocalApi2() {
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_API_LOCAL_KEY_1,
                AppUrlConfig.SERVICE_API_LOCAL_URL_1, ApiService.class);
    }

    /**
     * 获取测试Api
     * @return ApiService
     */
    public static ApiService getPatientTestApi(){
        return RxHttpUtils.createApi(AppUrlConfig.SERVICE_PATIENT_API_KEY,
                AppUrlConfig.SERVICE_PATIENT_API_URL, ApiService.class);
    }



    /**
     * 获取文件类型
     * @param fileType 100新增 101 更新
     * @return url
     */
    public static String getUploadFileUrl(int fileType) {
        String fileUrl = null;
        switch (fileType){
            case 100:
                fileUrl=AppUrlConfig.SERVICE_LIVE_API_URL+"broadcastImageController/addBroadcastImageNew";
                break;
            case 101:
                fileUrl=AppUrlConfig.SERVICE_LIVE_API_URL+"broadcastImageController/updateBroadcastImageNew";
                break;
                default:
        }
        return fileUrl;
    }

}
