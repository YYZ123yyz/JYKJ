package www.jykj.com.jykj_zxyl.activity.myself.setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import entity.hzgl.BindPatientGetVCodeParment;
import entity.mySelf.OperPassWordParment;
import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.LoginActivity;
import www.jykj.com.jykj_zxyl.activity.myself.SettingActivity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 设置 == > 修改密码
 */
public class OpeaPassWordActivity extends BaseActivity {


    private String mNetRetStr;                 //返回字符串
    public ProgressDialog mDialogProgress = null;
    private Context mContext;
    private OpeaPassWordActivity mActivity;
    private JYKJApplication mApp;
    private Handler mHandler;
    private EditText mOldPassWordEdit;                   //原始密码
    private EditText mNewPassWordEdit;                   //新密码
    private TextView mCommit;                            //提交

    private LinearLayout mBack;

    private TextView mgetVCodeText;                      //获取验证码
    private String bindingSmsVerifyTokenData;          //绑定所需短信验证Token值
    private String bindingSmsVerifyData;                //绑定短信验证码
    private int mTime = 60;

    private EditText mVCodeEdit;                         //短信验证码
    private MoreFeaturesPopupWindow mPopupWindow;
    private ImageButton ivAdd;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_myself_setting_opeapassword;
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

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 1) {
                            //修改成功，重新登录
                            mApp.cleanPersistence();
                            startActivity(new Intent(mContext, LoginActivity.class));
                            for (int i = 0; i < mApp.gActivityList.size(); i++) {
                                mApp.gActivityList.get(i).finish();
                            }
                            Toast.makeText(mContext, "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            bindingSmsVerifyTokenData = netRetEntity.getResTokenData();
                            //启动定时器
                            startTimer();
                        } else
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        mgetVCodeText.setText(mTime + "");
                        break;
                    case 5:
                        mgetVCodeText.setText("重新获取");
                        break;
                }
            }
        };
    }

    /**
     * 启动定时器，控制短信验证码时间
     */
    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (mTime > 0) {
                    mTime--;
                    mHandler.sendEmptyMessage(4);
                } else {
                    mTime = 60;
                    mHandler.sendEmptyMessage(5);
                    timer.cancel();
                }

            }
        };
        timer.schedule(task, 0, 1000);
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mOldPassWordEdit = (EditText) this.findViewById(R.id.tv_activityOperPassWord_oldPassWordText);
        mNewPassWordEdit = (EditText) this.findViewById(R.id.tv_activityOperPassWord_newPassWordText);
        mgetVCodeText = (TextView) this.findViewById(R.id.tv_getVCodeText);
        mVCodeEdit = (EditText) this.findViewById(R.id.et_yzm);

        mgetVCodeText.setOnClickListener(new ButtonClick());
        mCommit = (TextView) this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new ButtonClick());
        mBack = (LinearLayout) this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivAdd = findViewById(R.id.right_image_search);
        ivAdd.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_commit:
                    commit();
                    break;
                case R.id.tv_getVCodeText:
                    getVCode();
                    break;
                case R.id.right_image_search:
                    if (mPopupWindow ==null){
                        mPopupWindow = new MoreFeaturesPopupWindow(OpeaPassWordActivity.this);
                    }
                    if (!mPopupWindow.isShowing()) {
                        mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                    }else {
                        mPopupWindow.dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void commit() {
        OperPassWordParment operPassWordParment = new OperPassWordParment();
        operPassWordParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
        operPassWordParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        operPassWordParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        operPassWordParment.setOldPassWord(mOldPassWordEdit.getText().toString());
        operPassWordParment.setNewPassWord(mNewPassWordEdit.getText().toString());
        operPassWordParment.setBindingSmsVerifyTokenData(bindingSmsVerifyTokenData);
        operPassWordParment.setSendUserLinkPhone(mApp.mViewSysUserDoctorInfoAndHospital.getLinkPhone());
        operPassWordParment.setBindingSmsVerifyData(mVCodeEdit.getText().toString());

        if (mOldPassWordEdit.getText().toString() == null || "".equals(mOldPassWordEdit.getText().toString())) {
            Toast.makeText(mContext, "请填写原始密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mNewPassWordEdit.getText().toString() == null || "".equals(mNewPassWordEdit.getText().toString())) {
            Toast.makeText(mContext, "请填写新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mOldPassWordEdit.getText().toString().equals(mNewPassWordEdit.getText().toString())) {
            Toast.makeText(mContext, "两次输入的不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mVCodeEdit.getText().toString() == null || "".equals(mVCodeEdit.getText().toString())) {
            Toast.makeText(mContext, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("请稍候。。。。", "正在提交");
        new Thread() {
            public void run() {
                try {
                    //实体转JSON字符串
                    String str = new Gson().toJson(operPassWordParment);
                    //获取用户数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "/doctorPersonalSetControlle/operUpdUserPassWord");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    Log.e("tag", "提交 "+mNetRetStr );
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("密码修改失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
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
     * 获取短信验证码
     */
    private void getVCode() {
        getProgressBar("请稍候", "正在获取。。。");
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    BindPatientGetVCodeParment bindPatientGetVCodeParment = new BindPatientGetVCodeParment();
                    bindPatientGetVCodeParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    bindPatientGetVCodeParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    bindPatientGetVCodeParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    bindPatientGetVCodeParment.setSendUserLinkPhone(mApp.mViewSysUserDoctorInfoAndHospital.getLinkPhone());
                    if (bindPatientGetVCodeParment.getSendUserLinkPhone() == null || "".equals(bindPatientGetVCodeParment.getSendUserLinkPhone())) {
                        Toast.makeText(mContext, "请先填写手机号", Toast.LENGTH_SHORT).show();
                    }
                    String str = new Gson().toJson(bindPatientGetVCodeParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorPersonalSetControlle/getUserPassWordSmsVerify");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(3);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(3);
                    return;
                }

                mHandler.sendEmptyMessage(3);
            }
        }.start();
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
