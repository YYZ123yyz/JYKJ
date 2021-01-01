package www.jykj.com.jykj_zxyl.activity.home.news;

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
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;

import java.util.HashMap;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MessageListChildBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.base_view.ProgressWebView;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.util.UiHelper;

/**
 * Description:消息详情
 *
 * @author: qiuxinhai
 * @date: 2020-12-31 15:29
 */
public class MessageDetialActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.wb_content)
    ProgressWebView mWebView;
    @BindView(R.id.tv_immediate_btn)
    TextView tvImmediateBtn;
    private LoadingLayoutManager mLoadingLayout;
    private String url;
    private boolean isLoadFailed;
    private MessageListChildBean messageListChildBean;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_msg_deital;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            messageListChildBean=(MessageListChildBean) extras.getSerializable("result");
            if (messageListChildBean!=null) {
                url=messageListChildBean.getPushParm();
            }
        }
        setToolBar();
        initWebView();
        addListener();
        initLoadingAndRetryManager();
    }


    @Override
    protected void initData() {
        super.initData();
        int flagMsgRead = messageListChildBean.getFlagMsgRead();
        if (flagMsgRead==0) {
            sendOperUpdPatientMsgStateRequest(messageListChildBean.getReminderId()+"");
        }
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("系统消息");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }


    /**
     * 初始化空页面加载布局
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mWebView);
        mLoadingLayout.showLoading();
    }

    private void sendOperUpdPatientMsgStateRequest(String reminderId){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("reminderId", reminderId);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdMsgPushReminderState(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                    }

                    @Override
                    protected void onError(String s) {
                        super.onError(s);
                    }

                });
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
    }

    /**
     * 添加监听
     */
    private void addListener() {
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
                if (!MessageDetialActivity.this.isDestroyed()) {
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
                if (!MessageDetialActivity.this.isDestroyed()) {
                    mLoadingLayout.showError();
                }
            }
        });

        tvImmediateBtn.setOnClickListener(v -> {
            String pushMsgType = messageListChildBean.getPushMsgType();
            String pushParm=messageListChildBean.getPushParm();
            String detailsID = messageListChildBean.getDetailsID();
            if (www.jykj.com.jykj_zxyl.util.StringUtils.isNotEmpty(pushMsgType)) {
                switch (pushMsgType) {
                    case "1":
                        UiHelper.goToJumpSysMessage(this, pushParm);
                        break;
                    case "2":
                        break;
                    case "3":
                        UiHelper.goToJumpLiveDetial(this, detailsID);
                        break;
                    case "4":
                        UiHelper.goToJumpMyPatient(this);
                        break;
                    case "5":
                        //UiHelper.goToJumpMedicalRecord(this);
                        break;
                    case "6":
                        UiHelper.goToJumpMyClinic(this);
                        break;
                    case "7":

                        break;
                    case "8":
                        UiHelper.gotoJumpVideoChapterDetial(this, detailsID);
                        break;
                    case "9":
                        break;
                    default:
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
