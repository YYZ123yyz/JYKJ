package www.jykj.com.jykj_zxyl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import entity.basicDate.ProvideBasicsRegion;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import netService.entity.ParmentEntity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.R;
import yyz_exploit.Utils.HttpUtil;
import yyz_exploit.Utils.HttpUtils;
import yyz_exploit.Utils.PrefParams;
import yyz_exploit.activity.activity.Login_protocolActivity;
import yyz_exploit.activity.activity.PrivacyActivity;
import yyz_exploit.activity.activity.ResActivity;

/**
 * 手机号登录Activity
 */
public class PhoneLoginActivity extends BaseActivity {
    private String mNetRegionRetStr;                 //获取返回字符串
    private SharedPreferences sp1;
    public ProgressDialog mDialogProgress = null;

    private TextView mPhoneLogin;                //手机号登录
    private Context mContext;
    private PhoneLoginActivity mActivity;
    private JYKJApplication mApp;
    private EditText mPhoneNum;              //手机号
    private EditText mVCode;                 //验证码
    private TextView mGetVCode;              //获取验证码

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;
    private TextView mUserRegist;            //用户注册
    private Button mLogin;                 //登录

    private boolean mAgree = true;                     //是否同意协议，默认同意
    private ImageView mAgreeImg;                  //同意协议图标

    private TimerTask mTask;
    private int mInitVCodeTime = 60;
    private Timer mTimer;
    private String mSmsToken;                  //短信验证码token
    private ImageView phonelogin_weChat;

    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wxaf6f64f6a5878261";
    private ImageView phome_back;

    String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

    private TextView login_protocol,privacy;
    private String WX_APPSecret = "7cac79aab0a527e47eb7f68eeddd265f";
    private    Handler  handler= new  Handler();


    @Override
    protected int setLayoutId() {
        return R.layout.activity_phonelogin;
    }

    @Override
    protected void initView() {
        super.initView();
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
    }

    /**
     * 启动定时器
     */
    private void startTask() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mInitVCodeTime > 0) {
                    mHandler.sendEmptyMessage(10);

                } else {
                    mHandler.sendEmptyMessage(11);
                }
            }
        };
        mTimer.schedule(mTask, 0, 1000);
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            if (netRetEntity.getResCode() == 1) {
                                startTask();
                                mSmsToken = netRetEntity.getResTokenData();
                                Toast.makeText(mContext, "获取成功，请注意接收短信", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "获取失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络连接异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        getRegionDate();
                        break;

                    case 10:
                        mInitVCodeTime--;
                        mGetVCode.setText(mInitVCodeTime + "");
                        mGetVCode.setClickable(false);
                        break;
                    case 11:
                        mGetVCode.setText("重新获取");
                        mGetVCode.setClickable(true);
                        mInitVCodeTime = 60;
                        mTimer.cancel();
                        mTask.cancel();
                        break;
                    case 100:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                            if (netRetEntity.getResCode() == 1) {
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUserPhone(mPhoneNum.getText().toString());
                                userInfo.setUserLoginSmsVerify(mVCode.getText().toString());

                                mApp.mLoginUserInfo = userInfo;
                                mApp.mViewSysUserDoctorInfoAndHospital = new Gson().fromJson(netRetEntity.getResJsonData(), ViewSysUserDoctorInfoAndHospital.class);
                                mApp.saveUserInfo();
                                Toast.makeText(mContext, "恭喜，登录成功", Toast.LENGTH_SHORT).show();
                                Log.d("登录ID====",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId()+"");
                                SharedPreferences sp= getSharedPreferences("loginUser",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putInt("doctorId",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId());
                                editor.commit();
                                //登录IM
                                mApp.loginIM();
                                startActivity(new Intent(mContext, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(mContext, "登录失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
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
                 //   mLoadingDialog.dismiss();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRegionRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(100);
            }
        }.start();

    }



    /**
     * 初始化布局
     */
    private void initLayout() {
        mPhoneNum = (EditText) this.findViewById(R.id.et_activityPhoneLogin_phoneNumEdit);
        //response为后台返回的json数据
        sp1 = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String peopleListJson = sp1.getString("KEY_Data_List_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(sp1==null){
            Log.e("tag", "DataList: "+"zoule " );
        }else{
            mPhoneNum.setText(peopleListJson);
        }
        mVCode = (EditText) this.findViewById(R.id.et_activityPhoneLogin_vCodeEdit);
        mUserRegist = (TextView) this.findViewById(R.id.textView_activityPhoneLogin_userRegistTextView);
        mLogin = (Button) this.findViewById(R.id.bt_activityPhoneLogin_loginBt);
        mGetVCode = (TextView) this.findViewById(R.id.textView_activityPhoneLogin_getVcodeTextView);

        mGetVCode.setOnClickListener(new ButtonClick());
        mUserRegist.setOnClickListener(new ButtonClick());
        mLogin.setOnClickListener(new ButtonClick());

        //微信登录
        phonelogin_weChat = findViewById(R.id.phonelogin_WeChat);
        phonelogin_weChat.setOnClickListener(new ButtonClick());

        phome_back = findViewById(R.id.phome_back);
        phome_back.setOnClickListener(new ButtonClick());

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
                case R.id.textView_activityPhoneLogin_getVcodeTextView:
                    getVCode();
                    break;
                case R.id.textView_activityPhoneLogin_userRegistTextView:
                    startActivity(new Intent(mContext, UseRegistActivity.class));
                    break;
                case R.id.bt_activityPhoneLogin_loginBt:
                    userLogin();
                    break;

                case R.id.phonelogin_WeChat:
                    weChatLogin();
                    break;
                case R.id.phome_back:
                    finish();
                    break;
                case R.id.login_protocol:
                    Intent intent2 = new Intent(PhoneLoginActivity.this, Login_protocolActivity.class);
                    startActivity(intent2);
                    break;
                //隐私
                case R.id.privacy:
                    Intent intent3 = new Intent(PhoneLoginActivity.this, PrivacyActivity.class);
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


    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    /**
     * 登录
     */
    private void userLogin() {
        if (mPhoneNum.getText().toString() == null || "".equals(mPhoneNum.getText().toString())) {
            Toast.makeText(mContext, "请先输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mVCode.getText().toString() == null || "".equals(mVCode.getText().toString())) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isMobileNO(mPhoneNum.getText().toString())){
            Toast.makeText(mContext, "请输入正确手机号", Toast.LENGTH_SHORT).show();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(mPhoneNum.getText().toString());
        userInfo.setUserLoginSmsVerify(mVCode.getText().toString());
        userInfo.setTokenSmsVerify(mSmsToken);
        sp1 = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit() ;
        editor.putString("KEY_Data_List_DATA", mPhoneNum.getText().toString()) ; //存入json串
        editor.commit() ;//提交
        getProgressBar("请稍候", "正在登录");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(userInfo), Constant.SERVICEURL + "doctorLoginController/loginDoctorSmsVerify");

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 获取验证码
     */
    private void getVCode() {
        //获取验证码
        if (mPhoneNum.getText().toString() == null || "".equals(mPhoneNum.getText().toString())) {
            Toast.makeText(mContext, "请先输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(mPhoneNum.getText().toString());
        ParmentEntity parmentEntity = new ParmentEntity();
        parmentEntity.setJsonDataInfo(new Gson().toJson(userInfo));
        getProgressBar("请稍候", "正在获取验证码");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(userInfo), Constant.SERVICEURL + "doctorLoginController/getLoginSmsVerify");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
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


    class ReceiveBroadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            getAccessToken();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //    getApplication().unregisterReceiver(receiveBroadCast);

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
                            Log.d("tag111111111", string);
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
                                                Intent intent = new Intent(PhoneLoginActivity.this, ResActivity.class);
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
                                                mApp.loginIM();

                                                mApp.saveUserInfo(viewSysUserDoctorInfoAndHospital);
                                                Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
                                                startActivity(intent);

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


}
