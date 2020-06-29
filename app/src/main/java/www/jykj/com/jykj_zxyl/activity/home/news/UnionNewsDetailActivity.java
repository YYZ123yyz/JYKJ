package www.jykj.com.jykj_zxyl.activity.home.news;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.home.newsMessage.GetNewsMessageParment;
import entity.home.newsMessage.ProvideMsgPushReminder;
import entity.home.newsMessage.OperMessageParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.UnionNewsActivity;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.JYZL_GRZLActivity;
import www.jykj.com.jykj_zxyl.adapter.UnionNewsAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.TextViewHtml;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

/**
 * 医生联盟消息详情
 */
public class UnionNewsDetailActivity extends AppCompatActivity {

    private Context mContext;
    public ProgressDialog mDialogProgress = null;

    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private String[] mBtnString;                 //按钮列表
    private Handler mHandler;
    private ImageView mess_back;
    private WebView mess_wb;
    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private         List<ProvideMsgPushReminder>   mDate = new ArrayList<>();
    private boolean loadDate = true;
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private UnionNewsAdapter mAdapter;
    private boolean mLoadDate = true;              //是否加载数据

    private String mMessageType;               //消息类型

    private TextView mTitle;

    private List<ProvideMsgPushReminder> mMsgPushReminders = new ArrayList<>();                 //获取到的消息

    private TextView    Type;
    private String mNetLoginRetStr;

    private ProvideMsgPushReminder provideMsgPushReminder;
    private String url;
    private String operCode;
    private Intent intent;
    private NetRetEntity retEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageunion_detail);
        ActivityUtil.setStatusBarMain(UnionNewsDetailActivity.this);
        mApp = (JYKJApplication) getApplication();
        mContext = this;
        initLayout();
        initHandler();
        setMsgReadState();
        data();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mess_back = findViewById(R.id.mess_back);
        mess_back.setOnClickListener(new ButtonClick());

        intent = getIntent();
        url = intent.getStringExtra("URL");
        mess_wb = findViewById(R.id.mess_wb);
        @SuppressLint("SetJavaScriptEnabled")
        WebSettings webSettings = mess_wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        mess_wb.loadUrl(url);
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.mess_back:
                    finish();
                    break;
            }
        }
    }
    /**
     * 审核
     *
     * @param
     */
    private void data() {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition",mApp.loginDoctorPosition);
        map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        int reminderId = intent.getIntExtra("reminderId", 0);
        map.put("reminderId",reminderId+"");
        Log.e("tag", "data: "+reminderId );
        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "msgDataControlle/operUpdMsgPushReminderState");
                    Log.e("tag", "run:,,,, "+mNetLoginRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 获取消息详情
     */
    private void setMsgReadState() {

    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}
