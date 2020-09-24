package www.jykj.com.jykj_zxyl.app_base.base_html5;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.base_view.ProgressWebView;

/**
 * Description:公共H5页面
 *
 * @author: qiuxinhai
 * @date: 2019-06-21 11:53
 */
public class H5Activity extends BaseActivity {
    BaseToolBar toolbar;
    /**
     * WebView组件
     */
    private ProgressWebView mWebView;
    /**
     * web链接
     */
    private String url;
    /**
     * 重新加载空页面管理
     */
    private LoadingLayoutManager mLoadingLayout;
    /**
     * 是否加载失败
     */
    private boolean isLoadFailed;
    /**
     * Native调用Js
     */
    private JYKJJsApi mQSJsApi;

    @Override
    protected void onBeforeSetContentLayout() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_base_html5;
    }

    @Override
    protected void initView() {
        toolbar=findViewById(R.id.toolbar);
        setToolBar();
        mWebView = findViewById(R.id.wb_content);
        //初始化空页面加载布局
        initLoadingAndRetryManager();
        //初始化WebView
        initWebView();
        //添加监听
        addListener();
    }
    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("统计表");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }
    /**
     * 初始化空页面加载布局
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mWebView);
        mLoadingLayout.setRetryListener(v -> H5Activity.this.finish());
        mLoadingLayout.showLoading();
    }

    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        //获取webview 设置
        WebSettings webSettings = mWebView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        //启用自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        //设置缓存策略
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置允许js弹出alert对话框
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //WebView启动https和http混合模式
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //触摸焦点起作用
        mWebView.requestFocus();
        //取消滚动条
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //加载url
        mWebView.loadUrl(url);
        //mWebView.loadDataWithBaseURL( null, url , "text/html", "UTF-8", null ) ;
    }

    /**
     * 添加监听
     */
    private void addListener() {
        mWebView.addJavascriptInterface(new AppJsKit(this), "android");
        mQSJsApi = new JYKJJsApi(javaScript -> mWebView.loadUrl(javaScript));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //在当前的webview中跳转到新的url
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!StringUtils.isNotEmpty(url)) {
                    mLoadingLayout.showError();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isLoadFailed) {
                    return;
                }
                if (!H5Activity.this.isDestroyed()) {
                    mLoadingLayout.showContent();

                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //接受所有证书
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isLoadFailed = true;
                if (!H5Activity.this.isDestroyed()) {
                    mLoadingLayout.showError();
                }
            }
        });
    }


    private void nativeCallJs() {
        mQSJsApi.openMyCreationPage();
    }



}
