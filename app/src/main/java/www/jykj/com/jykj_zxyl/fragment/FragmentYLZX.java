package www.jykj.com.jykj_zxyl.fragment;

import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.AlreadyUserFragment;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.WithoutUserFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXZN;


/**
 * 医疗资讯fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYLZX extends Fragment {
    private Context mContext;
    private MainActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private WebView web;
    private MoreFeaturesPopupWindow mPopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ylzx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
      //  initHandler();
        return v;
    }

    /**
     * 初始化布局
     */
    private void initLayout(View view) {
        ImageButton ivAdd = view.findViewById(R.id.right_image_search);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow ==null){
                    mPopupWindow = new MoreFeaturesPopupWindow(getActivity());
                }
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                }else {
                    mPopupWindow.dismiss();
                }
            }
        });
        web = view.findViewById(R.id.web);
        WebSettings webSettings = web.getSettings();
        web.loadUrl("http://jiuyihtn.com/AppAssembly/medicalAdvice.html");
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }


            //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //接受所有证书
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                LogUtil.e(TAG, "onReceivedError--->" + error.toString());
//                isLoadFailed = true;
//                if (!H5Activity.this.isDestroyed()) {
//                    mLoadingAndRetryManager.showRetry();
//                }
            }
        });


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        pager = view.findViewById(R.id.page);
//        tabLayout = view.findViewById(R.id.tab_layout);
//
//        fragmentList = new ArrayList<>();
//        mTitles = new ArrayList<>();
//        mTitles.add("热门精选");
//        mTitles.add("医学新闻");
//        mTitles.add("医学文献");
//        mTitles.add("医学指南");
//        mTitles.add("我的收藏");
//        fragmentList.add(new FragmentRMJX());
//        fragmentList.add(new FragmentYXXW());
//        fragmentList.add(new FragmentYXWX());
//        fragmentList.add(new FragmentYXZN());
//        fragmentList.add(new FragmentWDSC());
//
//        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitles);
//        pager.setAdapter(fragmentAdapter);
//        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }


//    private void initHandler() {
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//
//                }
//            }
//        };
//    }
}
