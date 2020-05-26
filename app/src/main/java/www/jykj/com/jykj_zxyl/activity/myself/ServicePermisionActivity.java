package www.jykj.com.jykj_zxyl.activity.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import entity.basicDate.ProvideBasicsRegion;
import entity.mySelf.servicePermision.ProvideDoctorSetServiceState;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的服务权限
 */
public class ServicePermisionActivity extends AppCompatActivity {

    private Context mContext;
    private ServicePermisionActivity mActivity;
    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;
    private String mNetRetStr;                 //获取返回字符串
    private JYKJApplication mApp;

    private LinearLayout mTWJZImageTextLayout;                   //图文就诊布局
    private TextView mTWJZText;                              //图文就诊
    private LinearLayout mYPJZImageTextLayout;                   //音频就诊布局
    private TextView mYPJZText;                              //音频就诊
    private LinearLayout mSPJZImageTextLayout;                   //视频就诊布局
    private TextView mSPJZText;                              //视频就诊
    private LinearLayout mQYFWImageTextLayout;                   //签约服务布局
    private TextView mQYFWText;                              //签约服务

    private LinearLayout mBack;


    private ProvideDoctorSetServiceState mProvideDoctorSetServiceState;
    private LinearLayout li_activityServicePermision_phone;
    private TextView tv_activityServicePermision_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_servicepermision);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideDoctorSetServiceState = JSON.parseObject(netRetEntity.getResJsonData(), ProvideDoctorSetServiceState.class);
                            setLayoutDate();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 设置布局显示
     */
    private void setLayoutDate() {
        if (mProvideDoctorSetServiceState.getFlagImgText() != null && mProvideDoctorSetServiceState.getFlagImgText() == 1) {
            mTWJZText.setText("已开通");
            mTWJZText.setTextColor(getResources().getColor(R.color.groabColor));

        } else {
            mTWJZText.setText("未开通");
            mTWJZText.setTextColor(getResources().getColor(R.color.textColor_vo));
        }

        if (mProvideDoctorSetServiceState.getFlagAudio() != null && mProvideDoctorSetServiceState.getFlagAudio() == 1) {
            mYPJZText.setText("已开通");
            mYPJZText.setTextColor(getResources().getColor(R.color.groabColor));

        } else {
            mYPJZText.setText("未开通");
            mYPJZText.setTextColor(getResources().getColor(R.color.textColor_vo));
        }

        if (mProvideDoctorSetServiceState.getFlagVideo() != null && mProvideDoctorSetServiceState.getFlagVideo() == 1) {
            mSPJZText.setText("已开通");
            mSPJZText.setTextColor(getResources().getColor(R.color.groabColor));

        } else {
            mSPJZText.setText("未开通");
            mSPJZText.setTextColor(getResources().getColor(R.color.textColor_vo));
        }

        if (mProvideDoctorSetServiceState.getFlagSigning() != null && mProvideDoctorSetServiceState.getFlagSigning() == 1) {
            mQYFWText.setText("已开通");
            mQYFWText.setTextColor(getResources().getColor(R.color.groabColor));

        } else {
            mQYFWText.setText("未开通");
            mQYFWText.setTextColor(getResources().getColor(R.color.textColor_vo));
        }

        if (mProvideDoctorSetServiceState.getFlagPhone() != null && mProvideDoctorSetServiceState.getFlagPhone() == 1) {
            mQYFWText.setText("已开通");
            mQYFWText.setTextColor(getResources().getColor(R.color.groabColor));

        } else {
            mQYFWText.setText("未开通");
            mQYFWText.setTextColor(getResources().getColor(R.color.textColor_vo));
        }

    }


    /**
     * 初始化布局
     */
    private void initLayout() {

        mBack = (LinearLayout) findViewById(R.id.ll_back);
        mBack.setOnClickListener(new ButtonClick());

        mTWJZImageTextLayout = (LinearLayout) this.findViewById(R.id.li_activityServicePermision_ImageText);
        mTWJZImageTextLayout.setOnClickListener(new ButtonClick());
        mTWJZText = (TextView) this.findViewById(R.id.tv_activityServicePermision_TVText);

        mYPJZImageTextLayout = (LinearLayout) this.findViewById(R.id.li_activityServicePermision_YPJZImageText);
        mYPJZImageTextLayout.setOnClickListener(new ButtonClick());
        mYPJZText = (TextView) this.findViewById(R.id.tv_activityServicePermision_YPJZText);

        mSPJZImageTextLayout = (LinearLayout) this.findViewById(R.id.li_activityServicePermision_SPJZImageText);
        mSPJZImageTextLayout.setOnClickListener(new ButtonClick());
        mSPJZText = (TextView) this.findViewById(R.id.tv_activityServicePermision_SPJZText);

        mQYFWImageTextLayout = (LinearLayout) this.findViewById(R.id.li_activityServicePermision_FWQXImageText);
        mQYFWImageTextLayout.setOnClickListener(new ButtonClick());
        mQYFWText = (TextView) this.findViewById(R.id.tv_activityServicePermision_FWQXText);

        //电话就诊
        li_activityServicePermision_phone = findViewById(R.id.li_activityServicePermision_Phone);
        li_activityServicePermision_phone.setOnClickListener(new ButtonClick());
        mQYFWImageTextLayout.setOnClickListener(new ButtonClick());
        //已开通未开通
        tv_activityServicePermision_phone = findViewById(R.id.tv_activityServicePermision_Phone);
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;
                case R.id.li_activityServicePermision_ImageText:
                    Intent intent = new Intent();
                    intent.setClass(mContext, ServicePermisionSetActivity.class);
                    intent.putExtra("doctorStatus", mProvideDoctorSetServiceState.getFlagDoctorStatus());
                    intent.putExtra("serviceType", 1);
                    startActivity(intent);
                    break;
                case R.id.li_activityServicePermision_YPJZImageText:
                    intent = new Intent();
                    intent.setClass(mContext, ServicePermisionSetActivity.class);
                    intent.putExtra("doctorStatus", mProvideDoctorSetServiceState.getFlagDoctorStatus());
                    intent.putExtra("serviceType", 2);
                    startActivity(intent);
                    break;
                case R.id.li_activityServicePermision_SPJZImageText:
                    intent = new Intent();
                    intent.setClass(mContext, ServicePermisionSetActivity.class);
                    intent.putExtra("doctorStatus", mProvideDoctorSetServiceState.getFlagDoctorStatus());
                    intent.putExtra("serviceType", 3);
                    startActivity(intent);
                    break;
                case R.id.li_activityServicePermision_FWQXImageText:
                    intent = new Intent();
                    intent.setClass(mContext, ServicePermisionSetActivity.class);
                    intent.putExtra("doctorStatus", mProvideDoctorSetServiceState.getFlagDoctorStatus());
                    intent.putExtra("serviceType", 4);
                    startActivity(intent);
                    break;
                //电话就诊
                case R.id.li_activityServicePermision_Phone:
                    intent = new Intent();
                    intent.setClass(mContext, ServicePermisionSetActivity.class);
                    intent.putExtra("doctorStatus", mProvideDoctorSetServiceState.getFlagDoctorStatus());
                    intent.putExtra("serviceType", 5);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void getData() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideDoctorSetServiceState provideDoctorSetServiceState = new ProvideDoctorSetServiceState();
                    provideDoctorSetServiceState.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideDoctorSetServiceState.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideDoctorSetServiceState.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideDoctorSetServiceState), Constant.SERVICEURL + "doctorPersonalSetControlle/getDoctorSetServiceStateResData");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + retEntity.getResMsg());
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
