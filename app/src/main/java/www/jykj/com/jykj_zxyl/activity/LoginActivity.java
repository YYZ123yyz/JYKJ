package www.jykj.com.jykj_zxyl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.hyhd.DemoHelper;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import entity.basicDate.ProvideBasicsRegion;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.WebServiceUtil;
import netService.entity.NetRetEntity;
import netService.entity.ParmentEntity;
import util.LinkdoodLoadingDialog;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.AndroidThreadExecutor;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.PreferenceUtils;
import yyz_exploit.Forget_activity;
import yyz_exploit.Utils.HttpUtil;
import yyz_exploit.Utils.HttpUtils;
import yyz_exploit.Utils.PrefParams;
import yyz_exploit.activity.activity.Login_protocolActivity;
import yyz_exploit.activity.activity.PrivacyActivity;
import yyz_exploit.activity.activity.ResActivity;
import yyz_exploit.bean.ResBean;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends BaseActivity {

    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;
    private String mNetRegionRetStr;                 //获取返回字符串
    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist,login_protocol,privacy;                 //用户注册
    private Button mLogin;                     //登录
    private EditText mAccountEdit;               //输入帐号
    private EditText mPassWordEdit;              //输入密码
    private Context mContext;
    private LoginActivity mActivity;
    private JYKJApplication mApp;                       //
    private String mNetLoginRetStr;                 //登录返回字符串


    private boolean mAgree = true;                     //是否同意协议，默认同意
    private ImageView mAgreeImg;                  //同意协议图标

    protected LinkdoodLoadingDialog mLoadingDialog;
    private ImageView login_weChat;

    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wxaf6f64f6a5878261";
    private String WX_APPSecret = "7cac79aab0a527e47eb7f68eeddd265f";
    private TextView login_forget;
    private ReceiveBroadCast receiveBroadCast;


    private LinearLayout mLoginWeChat;

    private IWXAPI api;
    private SharedPreferences sp;
    private    Handler  handler= new  Handler();
    private SharedPreferences sp1;



    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
        initHandler();
    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        System.out.println(mApp.mViewSysUserDoctorInfoAndHospital);
        if (mApp.mLoginUserInfo != null && mApp.mLoginUserInfo.getUserPhone() != null &&
                mApp.mLoginUserInfo.getUserPwd() != null && !"".equals(mApp.mLoginUserInfo.getUserPhone())
                && !"".equals(mApp.mLoginUserInfo.getUserPhone())) {
            mAccountEdit.setText(mApp.mLoginUserInfo.getUserPhone());
            mPassWordEdit.setText(mApp.mLoginUserInfo.getUserPwd());
            userLogin();
        }
    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:

                        if (mNetLoginRetStr != null && !mNetLoginRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUserPhone(mAccountEdit.getText().toString());
                                userInfo.setUserPwd(mPassWordEdit.getText().toString());
                                mApp.mLoginUserInfo = userInfo;
                                mApp.mViewSysUserDoctorInfoAndHospital = new Gson().fromJson(netRetEntity.getResJsonData(), ViewSysUserDoctorInfoAndHospital.class);
                                mApp.saveUserInfo();
                                //Toast.makeText(mContext, "恭喜，登录成功", Toast.LENGTH_SHORT).show();
                                //登录IM
//                                mApp.loginIM();
//                                startActivity(new Intent(mContext, MainActivity.class));
//                                finish();
                                loginIm(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                                        ,mApp.mViewSysUserDoctorInfoAndHospital.getQrCode()
                                        ,mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                            } else {
                                cacerProgress();
                                Toast.makeText(mContext, "登录失败，" + netRetEntity.getResMsg(), LENGTH_SHORT).show();
                                //mLoadingDialog.dismiss();
                            }
                        } else {
                            cacerProgress();
                            Toast.makeText(mContext, "网络异常，请联系管理员", LENGTH_SHORT).show();
                            //mLoadingDialog.dismiss();
                        }
                        break;
                    case 2:
                        getRegionDate();
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mPhoneLogin = (TextView) this.findViewById(R.id.textView_activityLogin_phoneLoginTextView);
        mUseRegist = (TextView) this.findViewById(R.id.textView_activityLogin_userRegistTextView);

        mAccountEdit = (EditText) this.findViewById(R.id.tv_activityLogin_accountEdit);
        //response为后台返回的json数据
        sp1 = getSharedPreferences("SP_Data_List",Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String peopleListJson = sp1.getString("KEY_Data_List_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(sp1==null){
            Log.e("tag", "DataList: "+"zoule " );
        }else{
            mAccountEdit.setText(peopleListJson);
        }
        mPassWordEdit = (EditText) this.findViewById(R.id.tv_activityLogin_passWordEdit);

        mLogin = (Button) this.findViewById(R.id.button_activityLogin_LoginButton);
        mLogin.setOnClickListener(new ButtonClick());
        mPhoneLogin.setOnClickListener(new ButtonClick());
        mUseRegist.setOnClickListener(new ButtonClick());

        mLoadingDialog = new LinkdoodLoadingDialog(this, "");

        //微信登录
        login_weChat = findViewById(R.id.login_WeChat);
        login_weChat.setOnClickListener(new ButtonClick());

        //忘记密码
        login_forget = findViewById(R.id.login_Forget);
        login_forget.setOnClickListener(new ButtonClick());

     //用户 协议
        login_protocol=findViewById(R.id.login_protocol);
        login_protocol.setOnClickListener(new ButtonClick());

        //隐私
        privacy=findViewById(R.id.privacy);
        privacy.setOnClickListener(new ButtonClick());
    }



    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textView_activityLogin_phoneLoginTextView:
                    //手机号登录
                    Intent intent = new Intent();
                    intent.setClass(mContext, PhoneLoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.textView_activityLogin_userRegistTextView:
                    //用户注册
                    intent = new Intent();
                    intent.setClass(mContext, UseRegistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_activityLogin_LoginButton:
                    userLogin();
                    break;
                //微信登录
                case R.id.login_WeChat:
                    weChatLogin();
                    break;
                //忘记密码
                case R.id.login_Forget:
                    Intent intent1 = new Intent(LoginActivity.this, Forget_activity.class);
                    startActivity(intent1);
                    break;
                    //用户协议
                case R.id.login_protocol:
                    Intent intent2 = new Intent(LoginActivity.this, Login_protocolActivity.class);
                    startActivity(intent2);
                    break;
                    //隐私
                case R.id.privacy:
                    Intent intent3 = new Intent(LoginActivity.this, PrivacyActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    }


    //微信登录
    private void weChatLogin() {
        if (isWeixinAvilible(mContext)) {
            //创建微信api并注册到微信
            WXapi = WXAPIFactory.createWXAPI(mContext, WX_APP_ID);
            WXapi.registerApp(WX_APP_ID);
            //发起登录请求
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            WXapi.sendReq(req);
        } else {
            Toast.makeText(mContext, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 用户登录
     */
    private void userLogin() {
        if (mAccountEdit.getText().toString() == null || "".equals(mAccountEdit.getText().toString())) {
            Toast.makeText(mContext, "帐号不能为空", LENGTH_SHORT).show();
            return;
        }
        if (mPassWordEdit.getText().toString() == null || "".equals(mPassWordEdit.getText().toString())) {
            Toast.makeText(mContext, "密码不能为空", LENGTH_SHORT).show();
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(mAccountEdit.getText().toString());
        userInfo.setUserPwd(mPassWordEdit.getText().toString());
//        //创建sp对象
        sp1 = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit() ;
        editor.putString("KEY_Data_List_DATA", mAccountEdit.getText().toString()) ; //存入json串
        editor.commit() ;//提交
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(userInfo), Constant.SERVICEURL + "doctorLoginController/loginDoctorPwd");
                    Log.e("tag", "run:6666 "+mNetLoginRetStr );
                    mLoadingDialog.dismiss();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    mLoadingDialog.dismiss();
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    /**
     * 获取区域数据
     */
    private void getRegionDate() {
        //连接网络，登录
        //    showLoadingDialog("请稍候...");
        getProgressBar("请稍候....", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_parent_id("0");
                    mNetRegionRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideBasicsRegion), Constant.SERVICEURL + "basicDataController/getBasicsRegion");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRegionRetStr, NetRetEntity.class);
                    Log.e("tag", "run: 地区 "+mNetRegionRetStr );
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRegionRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //区域数据获取成功
                    mApp.gRegionList = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideBasicsRegion>>() {
                    }.getType());

                    for (int i = 0; i < mApp.gRegionList.size(); i++) {
                        if (mApp.gRegionList.get(i).getRegion_level() == 1) {
                            mApp.gRegionProvideList.add(mApp.gRegionList.get(i));
                        }
                        if (mApp.gRegionList.get(i).getRegion_level() == 2) {
                            mApp.gRegionCityList.add(mApp.gRegionList.get(i));
                        }
                        if (mApp.gRegionList.get(i).getRegion_level() == 3) {
                            mApp.gRegionDistList.add(mApp.gRegionList.get(i));
                        }
                    }
                    mLoadingDialog.dismiss();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRegionRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
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

    /**
     * 自定义加载loading弹出层(来自String)  *
     */
    public void showLoadingDialog(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (text != null) {
                    mLoadingDialog.setText(text);
                    if (!mLoadingDialog.isShowing()) {
                        mLoadingDialog.show();
                    }

                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        receiveBroadCast = new LoginActivity.ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("authlogin");
        getBaseContext().registerReceiver(receiveBroadCast, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //    getApplication().unregisterReceiver(receiveBroadCast);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    public void getAccessToken() {
        SharedPreferences WxSp = getApplicationContext()
                .getSharedPreferences(PrefParams.spName, Context.MODE_PRIVATE);
        String code = WxSp.getString(PrefParams.CODE, "");
        final SharedPreferences.Editor WxSpEditor = WxSp.edit();
        Log.d("tag", "-----获取到的code----" + code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WX_APP_ID
                + "&secret="
                + WX_APPSecret
                + "&code="
                + code
                + "&grant_type=authorization_code";
        Log.d("tag", "--------即将获取到的access_token的地址--------");
        HttpUtil.sendHttpRequest(url, new HttpUtil.HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                //解析以及存储获取到的信息
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("tag", "-----获取到的json数据1-----" + jsonObject.toString());
                    String access_token = jsonObject.getString("access_token");
                    Log.d("tag", "--------获取到的access_token的地址--------" + access_token);
                    String openid = jsonObject.getString("openid");
                    String refresh_token = jsonObject.getString("refresh_token");

                    if (!access_token.equals("")) {
                        WxSpEditor.putString(PrefParams.ACCESS_TOKEN, access_token);
                        WxSpEditor.apply();
                    }
                    if (!refresh_token.equals("")) {
                        WxSpEditor.putString(PrefParams.REFRESH_TOKEN, refresh_token);
                        WxSpEditor.apply();
                    }
                    if (!openid.equals("")) {
                        WxSpEditor.putString(PrefParams.WXOPENID, openid);
                        WxSpEditor.apply();
                        getPersonMessage(access_token, openid);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplication(), "通过code获取数据没有成功", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * 登录IM
     * @param userCode 用户code
     * @param orCode 二维码code
     * @param userName 用户名称
     */
    private void loginIm(String userCode,String orCode,String userName){
        AndroidThreadExecutor.getInstance().executeOnWorkThread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(userCode,orCode,new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        cacerProgress();
                        // ** manually load all local groups and conversation
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        // update current user's display name for APNs
                        boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(userName);
                        if (!updatenick) {
                        }
                        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                        String retuser = EMClient.getInstance().getCurrentUser();
                        // setNewsMessage();
                        Log.e("iis",retuser);
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }

                    @Override
                    public void onError(final int code, final String message) {
                        AndroidThreadExecutor.getInstance().executeOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "登录失败，请稍后再试" , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }


    //获取用户信息
    private void getPersonMessage(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.sendHttpRequest(url, new HttpUtil.HttpCallBackListener() {

            @Override
            public void onFinish(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("tag", "------获取到的个人信息------" + jsonObject.toString());

                    String openid1 = jsonObject.getString("openid");
                    String nickname = jsonObject.getString("nickname");
                    String sex = jsonObject.getString("sex");
                    String language = jsonObject.getString("language");
                    String city = jsonObject.getString("city");
                    String province = jsonObject.getString("province");
                    String country = jsonObject.getString("country");
                    String headimgurl = jsonObject.getString("headimgurl");
                    String unionid = jsonObject.getString("unionid");


                    HashMap<String, String> map = new HashMap<>();
                    map.put("loginDoctorPosition", "108.93425^34.23053");
                    map.put("requestClientType", "1");
                    map.put("openId", openid1);
                    map.put("unionId", unionid);
                    map.put("nickName", nickname);
                    map.put("gender", sex);
                    map.put("avatarUrl", headimgurl);
                    map.put("province", province);
                    map.put("city", city);
                    map.put("country", country);
                    map.put("privilege", "");
                    Log.d("tag", new JSONObject(map).toString());
                    //请求后台接口
                    HttpUtils.sendOKHttpRequest(Constant.SERVICEURL + "doctorLoginController/loginDoctorWechatGrant", new JSONObject(map).toString(), new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            Log.d("tag", "onFailure: " + "授权失败");
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String string = response.body().string();
                            NetRetEntity resBean = new Gson().fromJson(string, NetRetEntity.class);
                            handler .postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            if (resBean.getResCode() == 0) {
                                                Toast.makeText(getApplication(), "授权失败，需要重新授权", Toast.LENGTH_SHORT).show();
                                            }
                                            if (resBean.getResCode() == 1 && resBean.getResData().equals("1")) {
                                                //進行綁定
                                                Intent intent = new Intent(LoginActivity.this, ResActivity.class);
                                                intent.putExtra("openid", openid1);
                                                startActivity(intent);
                                            }
                                            else {
                                                UserInfo userInfo = new UserInfo();
                                                ViewSysUserDoctorInfoAndHospital viewSysUserDoctorInfoAndHospital = new Gson().fromJson(resBean.getResJsonData(), ViewSysUserDoctorInfoAndHospital.class);
                                                mApp.mViewSysUserDoctorInfoAndHospital = viewSysUserDoctorInfoAndHospital;
                                                userInfo.setUserPhone(viewSysUserDoctorInfoAndHospital.getUserAccount());
                                                userInfo.setUserPwd(viewSysUserDoctorInfoAndHospital.getUserPass());
                                                mApp.mLoginUserInfo = userInfo;
                                                //登录IM
                                                mApp.mViewSysUserDoctorInfoAndHospital.getUserRoleId();
                                                //mApp.loginIM();

                                                mApp.saveUserInfo(viewSysUserDoctorInfoAndHospital);
//                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                                startActivity(intent);
//                                               finish();
                                                loginIm(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                                                        ,mApp.mViewSysUserDoctorInfoAndHospital.getQrCode()
                                                        ,mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                                            }
                                        }
                                    }, 100
                            );
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplication(), "通过openid获取数据没有成功", Toast.LENGTH_SHORT).show();
            }

        });
    }


    class ReceiveBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            getAccessToken();
        }
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}