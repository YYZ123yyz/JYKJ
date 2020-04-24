package www.jykj.com.jykj_zxyl.activity.ylzx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.PhoneLoginActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class YLZXWebActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 YLZXWebActivity             mActivity;
    private                 WebView                     webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ylzx_webinfo);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        webView= (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://news.fh21.com.cn/ylzx/sjzydbdfyy/6603143.html");
        //这一段可以保证在webview中的网页中点击某个链接时仍然在app中打开，而不是用浏览器打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    //这一段代码可以保证在webview中点击返回键时不会退出程序，而是返回上一级页面
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode ==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
