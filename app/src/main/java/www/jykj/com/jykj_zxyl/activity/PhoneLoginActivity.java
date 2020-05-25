package www.jykj.com.jykj_zxyl.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import netService.entity.ParmentEntity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.R;

/**
 * 手机号登录Activity
 */
public class PhoneLoginActivity extends AppCompatActivity {


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
    private int mInitVCodeTime = 30;
    private Timer mTimer;
    private String mSmsToken;                  //短信验证码token
    private ImageView phonelogin_weChat;

    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wxaf6f64f6a5878261";
    private ImageView phome_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gActivityList.add(this);
        ActivityUtil.setStatusBar(mActivity);
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
//                                Log.d("登录ID====",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId()+"");
//                               SharedPreferences sp= getSharedPreferences("loginUser",MODE_PRIVATE);
//                               SharedPreferences.Editor editor=sp.edit();
//                               editor.putInt("doctorId",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId());
//                               editor.commit();
                                //登录IM
                                mApp.loginIM();
                                startActivity(new Intent(mContext, MainActivity.class));
                                for (int i = 0; i < mApp.gActivityList.size(); i++) {
                                    mApp.gActivityList.get(i).finish();
                                }
                            } else {
                                Toast.makeText(mContext, "登录失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 10:
                        mInitVCodeTime--;
                        mGetVCode.setText(mInitVCodeTime + "");
                        mGetVCode.setClickable(false);
                        break;
                    case 11:
                        mGetVCode.setText("重新获取");
                        mGetVCode.setClickable(true);
                        mInitVCodeTime = 30;
                        mTimer.cancel();
                        mTask.cancel();
                        break;

                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mPhoneNum = (EditText) this.findViewById(R.id.et_activityPhoneLogin_phoneNumEdit);
        mVCode = (EditText) this.findViewById(R.id.et_activityPhoneLogin_vCodeEdit);
        mUserRegist = (TextView) this.findViewById(R.id.textView_activityPhoneLogin_userRegistTextView);
        mLogin = (Button) this.findViewById(R.id.bt_activityPhoneLogin_loginBt);
        mGetVCode = (TextView) this.findViewById(R.id.textView_activityPhoneLogin_getVcodeTextView);


        mAgreeImg = (ImageView) this.findViewById(R.id.iv_activityPhoneLogin_agreeImg);
        //设置是否同意协议图标
        setAgreeImg();
        mAgreeImg.setOnClickListener(new ButtonClick());

        mGetVCode.setOnClickListener(new ButtonClick());
        mUserRegist.setOnClickListener(new ButtonClick());
        mLogin.setOnClickListener(new ButtonClick());

        //微信登录
        phonelogin_weChat = findViewById(R.id.phonelogin_WeChat);
        phonelogin_weChat.setOnClickListener(new ButtonClick());

        phome_back = findViewById(R.id.phome_back);
        phome_back.setOnClickListener(new ButtonClick());
    }


    /**
     * 设置是否同意协议图标
     */
    private void setAgreeImg() {
        if (mAgree)
            mAgreeImg.setImageResource(R.mipmap.choice);
        else
            mAgreeImg.setImageResource(R.mipmap.nochoice);
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
                case R.id.iv_activityPhoneLogin_agreeImg:
                    //取反
                    mAgree = !mAgree;
                    setAgreeImg();
                    break;
                case R.id.phonelogin_WeChat:
                    WXLogin();
                    break;
                case R.id.phome_back:
                    finish();
                    break;
            }
        }
    }
    /**
     * 登录微信
     */
    private void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);

    }
    /**
     * 登录
     */
    private void userLogin() {
        //登录
        if (!mAgree) {
            Toast.makeText(mContext, "请先同意用户服务协议", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPhoneNum.getText().toString() == null || "".equals(mPhoneNum.getText().toString())) {
            Toast.makeText(mContext, "请先输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mVCode.getText().toString() == null || "".equals(mVCode.getText().toString())) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserPhone(mPhoneNum.getText().toString());
        userInfo.setUserLoginSmsVerify(mVCode.getText().toString());
        userInfo.setTokenSmsVerify(mSmsToken);
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
}
