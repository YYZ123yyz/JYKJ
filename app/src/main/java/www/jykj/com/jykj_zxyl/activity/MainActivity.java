package www.jykj.com.jykj_zxyl.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.MainMessage;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import util.VersionsUpdata;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.Constants;
import www.jykj.com.jykj_zxyl.app_base.base_utils.SPUtils;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.consultation.fragment.ConsultationFragment;
import www.jykj.com.jykj_zxyl.service.PushIntentService;
import www.jykj.com.jykj_zxyl.util.ImageViewUtil;
import yyz_exploit.Utils.BadgeUtil;
import yyz_exploit.Utils.HttpUtils;
import yyz_exploit.bean.AppVersionBean;
import entity.home.newsMessage.ProvideMsgPushReminderCount;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.FragmentHZGL;
import www.jykj.com.jykj_zxyl.fragment.FragmentMySelf;
import www.jykj.com.jykj_zxyl.fragment.FragmentShouYe;
import www.jykj.com.jykj_zxyl.fragment.FragmentYHHD;
import www.jykj.com.jykj_zxyl.fragment.FragmentYLZX;
import www.jykj.com.jykj_zxyl.service.MessageReciveService;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import yyz_exploit.dialog.ErrorDialog;

public class MainActivity extends BaseActivity {
    private Context mContext;
    private MainActivity mainActivity;
    private JYKJApplication mApp;
    private ViewPager mViewPage;
    private MyPagerAdapter mMyPagerAdapter;
    private List<Fragment> mListFragment = new ArrayList<Fragment>();                     // fragment列表

    private ImageView mImageViewShouYe;                                         //首页图标
    //    private         ImageView                   mImageViewHZGuanLi;                                      //患者管理图标
    private ImageView mImageViewYHHD;                                            //医患互动图标
    private ImageView mImageViewYLZX;                                            //医疗资讯图标
    //private ImageView mImageViewMySelf;                                        //我图标

    private TextView mTextViewShouYE;                                 //首页text
    //    private         TextView                    mTextViewHZGuanLi;                                  //患者管理text
    private TextView mTextViewYHHD;                                   //医患互动text
    private TextView mTextViewYLZX;                                   //医疗咨询text
    //private TextView mTextViewSelf;                                      //我text
    private ImageView mIvUserHead;
    private LinearLayout mLinearLayoutShouYe;                                 //首页布局
    //    private         LinearLayout                mLinearLayoutHZGuanLi;                                  //患者管理布局
    private LinearLayout mLinearLayoutYHHD;                                 //医患互动布局
    private LinearLayout mLinearLayoutYLZX;                                 //医疗资讯布局
    private LinearLayout mLinearLayoutMySelf;                                 //我布局

    private FragmentShouYe mFragmentShouYe;                                 //首页Fragment
    private FragmentHZGL mFragmentHZGL;                                 //患者管理Fragment
    private ConsultationFragment consultationFragment;
    private FragmentYHHD mFragmentYHHD;//医患互动Fragment
    private FragmentYLZX mFragmentYLZX;                                       //医疗资讯Fragment
    private FragmentMySelf mFragmentMySelf;                                 //我的Fragment
    private int mCurrentFragment;                                //当前所在的Fragment

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;

    public ProvideMsgPushReminderCount mProvideMsgPushReminderCount = new ProvideMsgPushReminderCount();         //未读消息数

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    /**
     * the notifier
     */
    private EaseNotifier notifier = null;


    private AppVersionBean appVersionBean;
    private EMConnectionListener connectionListener;
    private EMConnectionListener emConnectionListener;
    private ErrorDialog errorDialog;
    private TextView mTvUnreadBtn;
    private int unreadMessageCount;
    private float fontSizeScale;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        super.initView();
        mContext = this;
        mainActivity = this;
        EventBus.getDefault().register(this);
        ActivityUtil.setStatusBarMain(mainActivity);
        mApp = (JYKJApplication) getApplication();
        mApp.gMainActivity = this;
        //判断是否有新消息
        mApp.setNewsMessage();
        initLayout();               //初始化布局
        initHandler();
        //开启监听服务
        Intent intent = new Intent(this, MessageReciveService.class);
        startService(intent);
        mApp.gNetWorkTextView = true;
        //data();
        getLocation();
        BadgeUtil.setBadgeCount(this, unreadMessageCount, R.drawable.bg_red_circle);
    }


    @Override
    protected void initData() {
        super.initData();

    }



    /**
     * 设置环信网络状态
     *
     * @param gNetConnectionHX
     */
    public void setHXNetWorkState(boolean gNetConnectionHX) {
        if (mHandler != null)
            mHandler.sendEmptyMessage(10);

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        //检查定位权限
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        //判断
        if (permissions.size() == 0) {//有权限，直接获取定位
            getLocationLL();
        } else {//没有权限，获取定位权限
            requestPermissions(permissions.toArray(new String[permissions.size()]), 2);
        }

    }

    private void getLocationLL() {
        Log.e("tag", "getLocation: " + "获取定位权限1 - 开始");
        Location location = getLastKnownLocation();
        if (location != null) {
            //传递经纬度给网页
            String result = "{code: '0',type:'2',data: {longitude: '" + location.getLongitude() + "',latitude: '" + location.getLatitude() + "'}}";
            //   wvShow.loadUrl("javascript:callback(" + result + ");");

            //日志
            String locationStr = "维度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();
            Log.e("tag", "经纬度 " + locationStr);
        } else {
            Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
            Log.e("tag", "获取定位权限7 - " + "位置获取失败");
        }

    }


    /**
     * 定位：得到位置对象
     *
     * @return
     */
    private Location getLastKnownLocation() {
        //获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    /**
     * 定位：权限监听
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2://定位
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("tag", "onRequestPermissionsResult: " + "同意定位权限");
                    getLocationLL();
                } else {
                    Toast.makeText(this, "未同意获取定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    /**
     * 启动定时器，轮询获取未读消息数
     */
    private void startMessageTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //   getMessageCount();
            }
        };
        timer.schedule(task, 0, mApp.mMsgTimeInterval * 60 * 1000);
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        showComplexDialog();
                        break;
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUnReadMsgBtnStatus();
        //启动程序，查询是否有未读消息
//       getMessageCount();
        String userLogoUrl = mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl();
        ImageViewUtil.showImageView(this, userLogoUrl, mIvUserHead);
    }

    /**
     * 设置未读消息按钮状态
     */
    @SuppressLint("DefaultLocale")
    private void setUnReadMsgBtnStatus() {
        unreadMessageCount = EMClient.getInstance().chatManager().getUnreadMessageCount();
        if (unreadMessageCount > 0) {
            mTvUnreadBtn.setVisibility(View.VISIBLE);
            mTvUnreadBtn.setText(String.format("%d", unreadMessageCount));
        } else {
            mTvUnreadBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
           mTvUnreadBtn=this.findViewById(R.id.tv_unread_btn);
        mLinearLayoutShouYe = (LinearLayout) this.findViewById(R.id.l1_activityMain_ShouYeLayout);
    //    mLinearLayoutHZGuanLi = (LinearLayout)this.findViewById(R.id.l1_activityMain_HZGuanLiLayout);
        mLinearLayoutYHHD = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutHYHD);
        mLinearLayoutYLZX = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutYLZX);
        mLinearLayoutMySelf = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutGRZX);

        mImageViewShouYe = (ImageView) this.findViewById(R.id.iv_activityMain_ImageShouYe);
//        mImageViewHZGuanLi = (ImageView) this.findViewById(R.id.iv_activityMain_ImageHZGuanLi);
        mImageViewYHHD =  this.findViewById(R.id.iv_activityMain_ImageHYHD);
        mImageViewYLZX = (ImageView) this.findViewById(R.id.iv_activityMain_ImageYLZX);
//        mImageViewMySelf = (ImageView) this.findViewById(R.id.iv_activityMain_ImageGRZX);

        mTextViewShouYE = (TextView) this.findViewById(R.id.tv_activityMain_TextShouYe);
//        mTextViewHZGuanLi = (TextView) this.findViewById(R.id.tv_activityMain_TextHZGuanLi);
        mTextViewYHHD = (TextView) this.findViewById(R.id.tv_activityMain_TextHYHD);
        mTextViewYLZX = (TextView) this.findViewById(R.id.tv_activityMain_TextYLZX);
        mIvUserHead=this.findViewById(R.id.iv_user_head);
        //       mTextViewSelf = (TextView) this.findViewById(R.id.tv_activityMain_TextGRZX);
        fontSizeScale = (float) SPUtils.get(this, Constants.SP_FontScale, 0.0f);
        setDefaultLayout();
        mImageViewShouYe.setBackgroundResource(R.mipmap.sy_press);
        mTextViewShouYE.setTextColor(getResources().getColor(R.color.tabColor_press));
        mLinearLayoutShouYe.setOnClickListener(new ButtonClick());
//        mLinearLayoutHZGuanLi.setOnClickListener(new ButtonClick());
        mLinearLayoutYHHD.setOnClickListener(new ButtonClick());
        mLinearLayoutYLZX.setOnClickListener(new ButtonClick());
        mLinearLayoutMySelf.setOnClickListener(new ButtonClick());

        mViewPage = (ViewPager) this.findViewById(R.id.vp_activityMain_viewPage);
        if (mFragmentShouYe == null) {
            mFragmentShouYe = new FragmentShouYe();
            mListFragment.add(mFragmentShouYe);
        }

//        if (mFragmentHZGL == null)
//        {
//            mFragmentHZGL  = new FragmentHZGL();
//            mListFragment.add(mFragmentHZGL);
//        }


//        if (mFragmentYHHD == null) {
//            mFragmentYHHD = new FragmentYHHD();
//            mListFragment.add(mFragmentYHHD);
//        }
        if (consultationFragment==null) {
            consultationFragment=new ConsultationFragment();
            mListFragment.add(consultationFragment);
        }

        if (mFragmentYLZX == null) {
            mFragmentYLZX = new FragmentYLZX();
            mListFragment.add(mFragmentYLZX);
        }

        if (mFragmentMySelf == null) {
            mFragmentMySelf = new FragmentMySelf();
            mListFragment.add(mFragmentMySelf);
        }


        mMyPagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager(), mListFragment);
        mViewPage.setAdapter(mMyPagerAdapter);
        mViewPage.setCurrentItem(0);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("score","onPageScrolled:"+position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("score", "onPageSelected:" + position);
                switch (position) {
                    case 0:
                        mCurrentFragment = 0;
                        setDefaultLayout();
                        mImageViewShouYe.setBackgroundResource(R.mipmap.sy_press);
                        mTextViewShouYE.setTextColor(getResources().getColor(R.color.tabColor_press));
                        break;

                    case 1:
                        mCurrentFragment = 1;
                        setDefaultLayout();
//                        if (mApp.gNewMessageNum > 0) {
//                            mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_press);
//                            mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
//                        } else {
                        mImageViewYHHD.setBackgroundResource(R.mipmap.hz_press);
                        mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
                        //   }
                        break;
                    case 2:
                        mCurrentFragment = 2;
                        setDefaultLayout();
                        mImageViewYLZX.setBackgroundResource(R.mipmap.yhq_presss);
                        mTextViewYLZX.setTextColor(getResources().getColor(R.color.tabColor_press));
                        break;
                    case 3:
                        mCurrentFragment = 3;
                        setDefaultLayout();
                        //mImageViewMySelf.setBackgroundResource(R.mipmap.grzx_press);
                        //mTextViewSelf.setTextColor(getResources().getColor(R.color.tabColor_press));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("score","onPageScrollStateChanged:"+state);
            }
        });
        mViewPage.setOffscreenPageLimit(4);
    }


    /**
     * 默认布局
     */
    private void setDefaultLayout() {
        mImageViewShouYe.setBackgroundResource(R.mipmap.sy_nomal);
//        mImageViewHZGuanLi.setBackgroundResource(R.mipmap.hzgl_nomal);
        if (mApp.gNewMessageNum > 0)
            mImageViewYHHD.setBackgroundResource(R.mipmap.hz_nomal);
        else
            mImageViewYHHD.setBackgroundResource(R.mipmap.hz_nomal);
        mImageViewYLZX.setBackgroundResource(R.mipmap.class_img);
        //mImageViewMySelf.setBackgroundResource(R.mipmap.grzx_nomal);

        mTextViewShouYE.setTextColor(getResources().getColor(R.color.tabColor_nomal));
//        mTextViewHZGuanLi.setTextColor(getResources().getColor(R.color.tabColor_nomal));
        mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_nomal));
        mTextViewYLZX.setTextColor(getResources().getColor(R.color.tabColor_nomal));
       // mTextViewSelf.setTextColor(getResources().getColor(R.color.tabColor_nomal));
    }


    /**
     * fragment切换
     *
     * @param currentfragment
     */
    private void changeFragment(int currentfragment) {
        mViewPage.setCurrentItem(currentfragment);
        mMyPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 设置会诊显示
     */
    public void setHZTabView() {
        if (mApp.gNewMessageNum > 0) {
            if (mCurrentFragment == 1) {
                //    mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_press);
                mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
            } else {
                mImageViewYHHD.setBackgroundResource(R.mipmap.hz_nomal);
                mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_nomal));
            }
        } else {
            if (mCurrentFragment == 1) {
                mImageViewYHHD.setBackgroundResource(R.mipmap.hz_press);
                mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
            } else {
                mImageViewYHHD.setBackgroundResource(R.mipmap.hz_nomal);
                mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_nomal));
            }
        }
    }

    /**
     * 设置环信网络状态
     */
    public void setHXNetWorkState() {
//        if (mApp.gNetWorkTextView) {
//            mFragmentYHHD.setHXNetWorkState(mApp.gNetConnectionHX);
//
//        }

    }

    void showComplexDialog() {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //    设置Content来显示一个信息
        builder.setTitle("异地登录");
        builder.setMessage("您的账号已在其他地方登陆！");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    mApp.cleanPersistence();
                    mApp.LoginOut(MainActivity.this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        //    设置一个NegativeButton
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                mApp.cleanPersistence();
//                mApp.LoginOut(MainActivity.this);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });

        //    显示出该对话框
        builder.show();

    }


    /**
     * fragmnent显示新消息
     */
    public void setNewsMessageView() {
        if (mFragmentYHHD != null)
            mFragmentYHHD.getMessageList();
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.l1_activityMain_ShouYeLayout:
                    changeFragment(0);
                    break;
//                case R.id.l1_activityMain_HZGuanLiLayout:
//                    changeFragment(1);
//                    break;
                case R.id.l1_activityMain_LayoutHYHD:
                    changeFragment(1);
                    break;

                case R.id.l1_activityMain_LayoutYLZX:
                    changeFragment(2);
                    break;
                case R.id.l1_activityMain_LayoutGRZX:
                    changeFragment(3);
                    break;
            }
        }
    }


    //ViewPager
    class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mViewList;

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }

        public MyPagerAdapter(FragmentManager fm, List<Fragment> mViewList) {
            super(fm);
            this.mViewList = mViewList;
        }


        @Override
        public int getCount() {
            return mViewList.size();
        }

    }

    void initNotifier() {
        notifier = new EaseNotifier(mContext);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取版本跟新的数据
     */
    private void getAppData() {
        HttpUtils.sendGetOHttpRequest("", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                Log.e("aaaaaa", string);
                final AppVersionBean appVersionBean = new Gson().fromJson(string, AppVersionBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //进行网络判断 当前VersionCode小于API 的code  调取弹窗在线更新
                        if (1 > packageCode(MainActivity.this)) {
                            showUpdataDialog(appVersionBean);
                        }
                    }
                });

            }
        });
    }

    /**
     * 获取应用的版本号versionCode：
     *
     * @param context
     * @return
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 更新的弹窗
     *
     * @param appVersionBean
     */
    private void showUpdataDialog(AppVersionBean appVersionBean) {
        StringBuffer message = new StringBuffer();
        //message 为更新的具体信息  我注释的这些 你到时候换成你的bean类
//        String[] split = appVersionBean.resMsg.split(",");
//        for (int i = 0; i < split.length; i++) {
//            message.append(split[i] + "\n");
//        }
//        String downLoadUrl = appVersionBean.getVersion().getUpdateUrl();
        //downLoadUrl 是下载app的的网址
        new VersionsUpdata(this).initdata(message, true, "");
    }

    //主线程中执行

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(MainMessage msg) {
        setUnReadMsgBtnStatus();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        if(fontSizeScale>0.5){
            config.fontScale= fontSizeScale;//1 设置正常字体大小的倍数
        }
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
}
