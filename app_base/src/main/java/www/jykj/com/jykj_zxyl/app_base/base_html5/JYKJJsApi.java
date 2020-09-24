package www.jykj.com.jykj_zxyl.app_base.base_html5;


import android.support.annotation.NonNull;

/**
 * Description:native 调用 JavaScript
 *
 * @author: qiuxinhai
 * @date: 2019-06-21 15:15
 */
public class JYKJJsApi {
    public interface JSLoader{
        void loadJS(String javaScript);
    }

    public static final String TAG = "AllinJSApi";

    private JSLoader mJSLoader;

    public JYKJJsApi(@NonNull JYKJJsApi.JSLoader jsLoader) {
        this.mJSLoader = jsLoader;
    }

    /**
     * 打开 H5[我的作品] 页面
     */
    public void openMyCreationPage() {
        loadJSOnReady("specialTemplate.myWorksHtml()");
    }

    private void loadJSOnReady(String jScript) {
        loadJS("$(document).ready(function () {" + jScript + "});");
    }

    private void loadJS(String jScript) {
        mJSLoader.loadJS("javascript:(function(){" + jScript + "})()");
    }

}
