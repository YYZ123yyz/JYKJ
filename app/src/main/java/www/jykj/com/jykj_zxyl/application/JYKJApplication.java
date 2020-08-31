package www.jykj.com.jykj_zxyl.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.*;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.allen.library.cookie.store.SPCookieStore;
import com.allen.library.manage.RxUrlManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMAChatClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.hyhd.DemoHelper;
import com.hyphenate.easeui.hyhd.model.CallReceiver;
import com.hyphenate.easeui.hyhd.model.DbOpenHelper;
import com.hyphenate.easeui.hyhd.model.DemoDBManager;
import com.hyphenate.easeui.hyhd.model.HMSPushHelper;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.ExtEaseUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import entity.DoctorInfo.InteractPatient;
import entity.basicDate.EMMessageEntity;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import entity.liveroom.CloseRoomInfo;
import entity.mySelf.DataCleanManager;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.unionInfo.ProvideUnionDoctorOrg;
import entity.user.ProvideDoctorQualification;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import okhttp3.OkHttpClient;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.LoginActivity;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.app_base.BuildConfig;
import www.jykj.com.jykj_zxyl.app_base.http.AppUrlConfig;
import www.jykj.com.jykj_zxyl.service.MessageReciveService;
import www.jykj.com.jykj_zxyl.util.StrUtils;
import yyz_exploit.dialog.AuthorityDialog;
import yyz_exploit.dialog.ErrorDialog;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.rtmp.TXLiveBase;

public class JYKJApplication extends Application {
    private JYKJApplication instance;
    public boolean gRefreshDate = false;                   //客户管理中是否需要刷新行政区划数据
    public List<Activity> gActivityList = new ArrayList();
    public static Context gContext;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public DisplayImageOptions mImageOptions;                                                // DisplayImageOptions是用于设置图片显示的类
    public CallReceiver mCallReceiver;
    public SharedPreferences_DataSave m_persist;                                                //数据存储

    public UserInfo mLoginUserInfo;                         //登录需要的用户信息
    public ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital;       //登录成功后服务端返回的真实用户信息
    public ProvideDoctorQualification provideDoctorQualification;
    public List<ProvideBasicsRegion> gRegionList = new ArrayList<>();                    //所有区域数据
    public List<ProvideBasicsRegion> gRegionProvideList = new ArrayList<>();            //省级区域数据
    public List<ProvideBasicsRegion> gRegionCityList = new ArrayList<>();                //市级区域数据
    public List<ProvideBasicsRegion> gRegionDistList = new ArrayList<>();                //区县级区域数据

    public String loginDoctorPosition = "20.154455^23.41548512";       //用户所处的经纬度

    public boolean isUpdateReviewUnion = false;                        //加入联盟审核信息是否更新，默认否
    public MainActivity gMainActivity;
    public boolean gNetConnectionHX;
    public boolean gNetWorkTextView = false;                       //是否显示网络状态
    public ProvideUnionDoctorOrg unionSettionChoiceOrg;                          //医生联盟，设置联盟成员联盟层级
    public Integer mMsgTimeInterval = 5;                               //轮询新消息时间，初始设置5分钟
    public String gBitMapString;                                  //身份证拍照bitmap

    private String mNetRetStr;                 //返回字符串


    private List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    public int gNewMessageNum = 0;                                  //新消息数量
    public int gMessageNum = 10;                //可发送的消息数量
    public long gVoiceTime = 20;                 //可拨打语音消息时长（单位：秒）
    public long gVedioTime = 30;                 //可拨打视频消息时长（单位：秒）
    public String curdetailcode = "";

    public Handler gHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mViewSysUserDoctorInfoAndHospital.getDoctorCode() == null)
                        mViewSysUserDoctorInfoAndHospital.setDoctorCode("");
                    if (mViewSysUserDoctorInfoAndHospital.getUserPass() == null)
                        mViewSysUserDoctorInfoAndHospital.setUserPass("");
                    //登录
                    String userAccountHuanxin = mViewSysUserDoctorInfoAndHospital.getDoctorCode();
                    EMClient.getInstance().login(userAccountHuanxin, mViewSysUserDoctorInfoAndHospital.getQrCode(), new EMCallBack() {
                        @Override
                        public void onSuccess() {

                            System.out.println("登录成功");
                            setNewsMessage();
//                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int i, String s) {

                            System.out.println("登录失败");
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            System.out.println("正在登录");
                        }
                    });
                    break;
            }




        }
    };
    private EMConnectionListener connectionListener;

    static final String IMTAG = "imlog";
    public void loginIM() {
        /*new Thread() {
            private EMConnectionListener connectionListener;

            public void run() {
                //注册
                try {
                    EMClient.getInstance().createAccount(mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mViewSysUserDoctorInfoAndHospital.getQrCode());
                    EMClient.getInstance().addConnectionListener(connectionListener);

                    gHandler.sendEmptyMessage(1);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    gHandler.sendEmptyMessage(1);
                    System.out.println("~~~~~~~注册失败~~~~~~~~" + e.getDescription());
                }
            }
        }.start();*/
        new Thread() {
            public void run() {

                //注册
                try {
                    EMClient.getInstance().login(mViewSysUserDoctorInfoAndHospital.getDoctorCode(),mViewSysUserDoctorInfoAndHospital.getQrCode(),new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.d(IMTAG, "登录成功");

                            // ** manually load all local groups and conversation
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            // update current user's display name for APNs
                            boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(mViewSysUserDoctorInfoAndHospital.getUserName());
                            if (!updatenick) {
                                Log.e(IMTAG, "更新用户昵称");
                            }
                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                            String retuser = EMClient.getInstance().getCurrentUser();
                            setNewsMessage();
                            Log.e("iis",retuser);
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            Log.d(IMTAG, "登录中...");
                        }

                        @Override
                        public void onError(final int code, final String message) {
                            if (code == 101 || code==204) {
                                try {
                                    EMClient.getInstance().createAccount(mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mViewSysUserDoctorInfoAndHospital.getQrCode());
                                    gHandler.sendEmptyMessage(1);
                                } catch (Exception logex) {
                                    Log.e(IMTAG, "登录失败: " + code);
                                }
                            }
                            Log.d(IMTAG, "登录失败: " + code);
                        }
                    });
                }catch (Exception ex){
                    Log.e(IMTAG,ex.getMessage());
                }
            }}.start();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        /*if(StrUtils.defaulObjToStr(curdetailcode).length()>0){
            CloseRoomInfo subinfo = new CloseRoomInfo();
            subinfo.setDetailsCode(curdetailcode);
            subinfo.setLoginUserPosition(loginDoctorPosition);
            subinfo.setOperUserCode(mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            subinfo.setOperUserName(mViewSysUserDoctorInfoAndHospital.getUserName());
            subinfo.setRequestClientType("1");
            CloseLiveRoomTask closeLiveRoomTask = new CloseLiveRoomTask(subinfo);
            closeLiveRoomTask.execute();
        }*/
    }

    class CloseLiveRoomTask extends AsyncTask<Void,Void,Boolean> {
        CloseRoomInfo subinfo;
        String retmsg = "";
        CloseLiveRoomTask(CloseRoomInfo subinfo){
            this.subinfo =  subinfo;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(subinfo),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/operLiveRoomDetailsNoticeResCloseBroadcasting");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    retmsg = retEntity.getResMsg();
                    return true;
                }else{
                    retmsg = retEntity.getResMsg();
                }
            } catch (Exception e) {
                e.printStackTrace();
                retmsg = "数据存储异常";
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(!aBoolean){
                Toast.makeText(gContext,retmsg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 退出登录
     */
    public  void LoginOut(final Activity activity) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("tag", "run: "+"您已退出登录!" );
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, final String message) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //初始化IM聊天界面
        gContext = getApplicationContext();
        DemoHelper.getInstance().init(gContext);
        //获取本地缓存
        getPersistence();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(gContext, options);

        //注册
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (mCallReceiver == null) {
            mCallReceiver = new CallReceiver();
        }
        getApplicationContext().registerReceiver(mCallReceiver, callFilter);
        //初始化图片方法
        /**
         * 配置并初始化ImageLoader
         */
        if (Constant.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        initImageLoader(getApplicationContext());

        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        mImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.hztx)                    // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.hztx)             // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.hztx)                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))    // 设置成圆角图片
                .build();                                    // 创建配置过得DisplayImageOption对象
        saveIMNumInfo();
        getIMNumInfo();
        initLitesmat();
        //   login();
        initRxHttpUtils();

    }

    void  login(){
        connectionListener = new EMConnectionListener() {

            @Override
            public void onDisconnected(int error) {
                if(error==EMError.USER_LOGIN_ANOTHER_DEVICE){


                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to
                // notify sdk we are ready to receive the events
            }
        };
        EMClient.getInstance().addConnectionListener(connectionListener);
    }



    void initLitesmat(){
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/6803be91c7f78640a122154f66452db8/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "55d1d4ae6154fe55d434335e1ddc05fb"; // 获取到的 licence key
        TXLiveBase.setConsoleEnabled(true);
        instance = this;
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TXLiveBase.getSDKVersionStr());
        CrashReport.initCrashReport(getApplicationContext(),strategy);

        TXLiveBase.getInstance().setLicence(instance, licenceURL, licenceKey);

        closeAndroidPDialog();
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        //数据存储,以json字符串的格式保存
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putString("loginUserInfo", new Gson().toJson(mLoginUserInfo));
        ExtEaseUtils.getInstance().setNickName(mViewSysUserDoctorInfoAndHospital.getUserName());
        ExtEaseUtils.getInstance().setImageUrl(mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        ExtEaseUtils.getInstance().setUserId(mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        m_persist.putString("viewSysUserDoctorInfoAndHospital", new Gson().toJson(mViewSysUserDoctorInfoAndHospital));
        m_persist.commit();
        DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(mViewSysUserDoctorInfoAndHospital.getUserName());
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        DemoHelper.getInstance().setCurrentUserName(mViewSysUserDoctorInfoAndHospital.getDoctorCode()); // 环信Id
        Log.e("tag", "run: "+"ccccccccccccccccc" );
    }

    /**
     *    保存用户信息
     * @param mViewSysUserDoctorInfoAndHospital
     */
    public void saveUserInfo(ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital) {
        //数据存储,以json字符串的格式保存
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putString("userID",mLoginUserInfo.getUserPhone());
        m_persist.putString("userName",mLoginUserInfo.getUserPhone());
        m_persist.putString("loginUserInfo", new Gson().toJson(mLoginUserInfo));
        ExtEaseUtils.getInstance().setNickName(mViewSysUserDoctorInfoAndHospital.getUserName());
        ExtEaseUtils.getInstance().setImageUrl(mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        ExtEaseUtils.getInstance().setUserId(mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        m_persist.putString("viewSysUserDoctorInfoAndHospital", new Gson().toJson(mViewSysUserDoctorInfoAndHospital));
        m_persist.commit();
        DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(mViewSysUserDoctorInfoAndHospital.getUserName());
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        DemoHelper.getInstance().setCurrentUserName(mViewSysUserDoctorInfoAndHospital.getDoctorCode()); // 环信Id
        Log.e("tag", "run: "+"ccccccccccccccccc" );
    }

    /**
     * 保存图文音视频通话限制信息
     */
    public void saveIMNumInfo() {
        //数据存储,以json字符串的格式保存
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putInt("messageNum", gMessageNum);
        m_persist.putLong("voiceTime", gVoiceTime);
        m_persist.putLong("vedioTime", gVedioTime);
        m_persist.commit();
    }

    //获取图文音视频通话限制信息
    public void getIMNumInfo() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        gMessageNum = m_persist.getInt("messageNum", 0);
        gVoiceTime = m_persist.getLong("voiceTime", 0);
        gVedioTime = m_persist.getLong("vedioTime", 0);
        System.out.println(gMessageNum);
        System.out.println(gVoiceTime);
        System.out.println(gVedioTime);
        System.out.println(gVedioTime);
    }


    //清空数据
    public void cleanPersistence() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.remove("loginUserInfo");
        m_persist.remove("viewSysUserDoctorInfoAndHospital");
        mLoginUserInfo.setUserPhone("");
        mLoginUserInfo.setUserPwd("");
        m_persist.commit();
    }

    //获取缓存数据
    public void getPersistence() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoLogin = m_persist.getString("loginUserInfo", "");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mLoginUserInfo = new Gson().fromJson(userInfoLogin, UserInfo.class);
        mViewSysUserDoctorInfoAndHospital = new Gson().fromJson(userInfoSuLogin, ViewSysUserDoctorInfoAndHospital.class);
        System.out.println(mViewSysUserDoctorInfoAndHospital);
    }


    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 设置新消息
     */
    public void setNewsMessage() {
        Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
        List<String> userCodeList = new ArrayList<>();
        //遍历map中的键,获取用户Code列表
        for (String key : conversationMap.keySet()) {
            userCodeList.add(key);
        }

        for (int i = 0; i < userCodeList.size(); i++) {
            //获取未读消息数
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userCodeList.get(i));
            if (conversation != null)
                gNewMessageNum = conversation.getUnreadMsgCount();
            if (gNewMessageNum > 0)
                return;
        }

    }


    public static boolean isApplicationBroughtToBackground(final Context context) {
        //创建activity管理对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        //取出RunningTask栈
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(1);
        //判断是否为空
        if (!list.isEmpty()) {
            //取出运行在栈顶的任务进程
            ComponentName componentName = list.get(0).topActivity;
            //判断任务进程的包名是否和想要判断的程序包名相同
            if (componentName.getPackageName().equals(context.getPackageName())) {
                //相同则说明你该程序运行在前台，则返回ture
                return true;
            }
        }
        //否则返回false
        return false;
    }

    /**
     * 设置环信网络状态
     */
    public void setNetConnectionStateHX() {
        if (gMainActivity != null)
            gMainActivity.setHXNetWorkState();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * 全局请求的统一配置（以下配置根据自身情况选择性的配置即可）
     */
    private void initRxHttpUtils() {

        //一个项目多url的配置方法
        RxUrlManager.getInstance().setMultipleUrl(AppUrlConfig.getAllApiUrl());

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //自定义factory的用法
                //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl(AppUrlConfig.SERVICE_API_ONLINE_URL)
                //开启全局配置
                .setOkClient(createOkHttp());


//        TODO: 2018/5/31 如果以上OkHttpClient的配置满足不了你，传入自己的 OkHttpClient 自行设置
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder
//                .addInterceptor(log_interceptor)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS);
//
//        RxHttpUtils
//                .getInstance()
//                .init(this)
//                .config()
//                .setBaseUrl(BuildConfig.BASE_URL)
//                .setOkClient(builder.build());

    }

    private OkHttpClient createOkHttp() {
        //        获取证书
//        InputStream cerInputStream = null;
//        InputStream bksInputStream = null;
//        try {
//            cerInputStream = getAssets().open("YourSSL.cer");
//            bksInputStream = getAssets().open("your.bks");
//        } catch (IOExceptio
//        n e) {
//            e.printStackTrace();
//        }

        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                //添加公共请求头
                .setHeaders(() -> {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("requestClientVerify", HttpNetService.requestClientVerify);
                    hashMap.put("requestLoginTokenValue", HttpNetService.requestLoginTokenValue);
                    return hashMap;

                })
                //添加自定义拦截器
                //.setAddInterceptor()
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //music_btn_paus、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                .setHasNetCacheTime(1)//默认有网络时候缓存60秒
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))

                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //music_btn_paus、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //设置Hostname校验规则，默认实现返回true，需要时候传入相应校验规则即可
                //.setHostnameVerifier(null)
                //全局超时配置
                .setReadTimeout(60)
                //全局超时配置
                .setWriteTimeout(60)
                //全局超时配置
                .setConnectTimeout(60)
                //全局是否打开请求log日志
                .setDebug(BuildConfig.DEBUG)
                .build();

        return okHttpClient;
    }

}
