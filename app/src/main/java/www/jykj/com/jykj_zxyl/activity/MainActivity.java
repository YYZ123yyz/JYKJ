package www.jykj.com.jykj_zxyl.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.model.EaseNotifier;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import util.VersionsUpdata;
import yyz_exploit.Utils.HttpUtils;
import yyz_exploit.bean.AppVersionBean;
import entity.home.newsMessage.ProvideMsgPushReminderCount;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.FragmentHZGL;
import www.jykj.com.jykj_zxyl.fragment.FragmentMySelf;
import www.jykj.com.jykj_zxyl.fragment.FragmentShouYe;
import www.jykj.com.jykj_zxyl.fragment.FragmentYHHD;
import www.jykj.com.jykj_zxyl.fragment.FragmentYLZX;
import www.jykj.com.jykj_zxyl.service.MessageReciveService;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class MainActivity extends AppCompatActivity {
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
    private ImageView mImageViewMySelf;                                        //我图标

    private TextView mTextViewShouYE;                                 //首页text
    //    private         TextView                    mTextViewHZGuanLi;                                  //患者管理text
    private TextView mTextViewYHHD;                                   //医患互动text
    private TextView mTextViewYLZX;                                   //医疗咨询text
    private TextView mTextViewSelf;                                      //我text

    private LinearLayout mLinearLayoutShouYe;                                 //首页布局
    //    private         LinearLayout                mLinearLayoutHZGuanLi;                                  //患者管理布局
    private LinearLayout mLinearLayoutYHHD;                                 //医患互动布局
    private LinearLayout mLinearLayoutYLZX;                                 //医疗资讯布局
    private LinearLayout mLinearLayoutMySelf;                                 //我布局

    private FragmentShouYe mFragmentShouYe;                                 //首页Fragment
    private FragmentHZGL mFragmentHZGL;                                 //患者管理Fragment

    private FragmentYHHD mFragmentYHHD;                                 //医患互动Fragment
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mainActivity = this;
        ActivityUtil.setStatusBarMain(mainActivity);
        mApp = (JYKJApplication) getApplication();
        mApp.gMainActivity = this;
        mApp.gActivityList.add(this);
        //判断是否有新消息
        mApp.setNewsMessage();
        initLayout();               //初始化布局
        initHandler();
        //开启监听服务
        Intent intent = new Intent(this, MessageReciveService.class);
        startService(intent);

        //启动定时器轮询未读消息数
        startMessageTimer();

    }
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
                getMessageCount();
            }
        };
        timer.schedule(task, 0, mApp.mMsgTimeInterval * 60 * 1000);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        mProvideMsgPushReminderCount = JSON.parseObject(netRetEntity.getResJsonData(), ProvideMsgPushReminderCount.class);
                        mApp.mMsgTimeInterval = mProvideMsgPushReminderCount.getMsgTimeInterval();
                        if (mProvideMsgPushReminderCount != null) {
                            String string = "";
                            if (mProvideMsgPushReminderCount.getMsgTypeCount01() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount01() + "条患者就诊消息";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount02() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount02() + "条诊后留言消息";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount03() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount03() + "条添加患者消息";
//                            else if (mProvideMsgPushReminderCount.getMsgTypeCount04() > 0)
//                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount04() + "条医生联盟消息";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount05() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount05() + "条医患圈消息";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount06() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount06() + "条紧急提醒";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount07() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount07() + "条患者签约消息";
                            else if (mProvideMsgPushReminderCount.getMsgTypeCount08() > 0)
                                string = "您有" + mProvideMsgPushReminderCount.getMsgTypeCount08() + "条系统消息消息";
                       //     mFragmentShouYe.setNewMessageView(string);
                        } else
                            mFragmentShouYe.setNewMessageView("");

                        break;
                }
            }
        };
    }

    /**
     * 获取未读消息数量
     */
    private void getMessageCount() {
        ProvideMsgPushReminderCount provideMsgPushReminderCount = new ProvideMsgPushReminderCount();

        if(mApp.mViewSysUserDoctorInfoAndHospital!=null){
            provideMsgPushReminderCount.setLoginDoctorPosition(mApp.loginDoctorPosition);
            provideMsgPushReminderCount.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            new Thread() {
                public void run() {
                    try {
                        String string = new Gson().toJson(provideMsgPushReminderCount);
                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount");
                        String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                        System.out.println(string + string01);
                    } catch (Exception e) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                        mNetRetStr = new Gson().toJson(retEntity);
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }.start();
        }




    }


    @Override
    protected void onResume() {
        super.onResume();
        //启动程序，查询是否有未读消息
        getMessageCount();
        //   getAppData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mLinearLayoutShouYe = (LinearLayout) this.findViewById(R.id.l1_activityMain_ShouYeLayout);
//        mLinearLayoutHZGuanLi = (LinearLayout)this.findViewById(R.id.l1_activityMain_HZGuanLiLayout);
        mLinearLayoutYHHD = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutHYHD);
        mLinearLayoutYLZX = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutYLZX);
        mLinearLayoutMySelf = (LinearLayout) this.findViewById(R.id.l1_activityMain_LayoutGRZX);

        mImageViewShouYe = (ImageView) this.findViewById(R.id.iv_activityMain_ImageShouYe);
//        mImageViewHZGuanLi = (ImageView) this.findViewById(R.id.iv_activityMain_ImageHZGuanLi);
        mImageViewYHHD = (ImageView) this.findViewById(R.id.iv_activityMain_ImageHYHD);
        mImageViewYLZX = (ImageView) this.findViewById(R.id.iv_activityMain_ImageYLZX);
        mImageViewMySelf = (ImageView) this.findViewById(R.id.iv_activityMain_ImageGRZX);

        mTextViewShouYE = (TextView) this.findViewById(R.id.tv_activityMain_TextShouYe);
//        mTextViewHZGuanLi = (TextView) this.findViewById(R.id.tv_activityMain_TextHZGuanLi);
        mTextViewYHHD = (TextView) this.findViewById(R.id.tv_activityMain_TextHYHD);
        mTextViewYLZX = (TextView) this.findViewById(R.id.tv_activityMain_TextYLZX);
        mTextViewSelf = (TextView) this.findViewById(R.id.tv_activityMain_TextGRZX);

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

        if (mFragmentYHHD == null) {
            mFragmentYHHD = new FragmentYHHD();
            mListFragment.add(mFragmentYHHD);
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
                        if (mApp.gNewMessageNum > 0) {
                            mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_press);
                            mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
                        } else {
                            mImageViewYHHD.setBackgroundResource(R.mipmap.hz_press);
                            mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
                        }
                        break;
                    case 2:
                        mCurrentFragment = 2;
                        setDefaultLayout();
                        mImageViewYLZX.setBackgroundResource(R.mipmap.yhq_press);
                        mTextViewYLZX.setTextColor(getResources().getColor(R.color.tabColor_press));
                        break;
                    case 3:
                        mCurrentFragment = 3;
                        setDefaultLayout();
                        mImageViewMySelf.setBackgroundResource(R.mipmap.grzx_press);
                        mTextViewSelf.setTextColor(getResources().getColor(R.color.tabColor_press));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("score","onPageScrollStateChanged:"+state);
            }
        });
    }


    /**
     * 默认布局
     */
    private void setDefaultLayout() {
        mImageViewShouYe.setBackgroundResource(R.mipmap.sy_nomal);
//        mImageViewHZGuanLi.setBackgroundResource(R.mipmap.hzgl_nomal);
        if (mApp.gNewMessageNum > 0)
            mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_nomal);
        else
            mImageViewYHHD.setBackgroundResource(R.mipmap.hz_nomal);
        mImageViewYLZX.setBackgroundResource(R.mipmap.yhq_normal);
        mImageViewMySelf.setBackgroundResource(R.mipmap.grzx_nomal);

        mTextViewShouYE.setTextColor(getResources().getColor(R.color.tabColor_nomal));
//        mTextViewHZGuanLi.setTextColor(getResources().getColor(R.color.tabColor_nomal));
        mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_nomal));
        mTextViewYLZX.setTextColor(getResources().getColor(R.color.tabColor_nomal));
        mTextViewSelf.setTextColor(getResources().getColor(R.color.tabColor_nomal));
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
                mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_press);
                mTextViewYHHD.setTextColor(getResources().getColor(R.color.tabColor_press));
            } else {
                mImageViewYHHD.setBackgroundResource(R.mipmap.hyhdnews_nomal);
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
        if (mApp.gNetWorkTextView)
            mFragmentYHHD.setHXNetWorkState(mApp.gNetConnectionHX);
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












    @Override
    protected void onRestart() {
        super.onRestart();
        //  getAppData();
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
                String string = response.body().string(); Log.e("aaaaaa",string);
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
}
