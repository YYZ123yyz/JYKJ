package www.jykj.com.jykj_zxyl.activity.myself.setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.basicDate.ProvideBasicsDomain;
import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.LoginActivity;
import www.jykj.com.jykj_zxyl.activity.myself.UserCenterActivity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 设置 == > 关于我们
 */
public class AboutActivity extends BaseActivity {


    private String mNetRetStr;                 //返回字符串
    public ProgressDialog mDialogProgress = null;
    private Context mContext;
    private AboutActivity mActivity;
    private JYKJApplication mApp;
    private Handler mHandler;
    private TextView mAboutText;
    private LinearLayout mBack;
    private WebView about_wb;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_myself_setting_about;
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
    }

    /**
     * 初始化布局
     */
    private void initLayout() {

        about_wb = findViewById(R.id.about_wb);
        about_wb.loadUrl("https://jiuyihtn.com/AppAssembly/aboutUs.html");

        mAboutText = (TextView) this.findViewById(R.id.tv_about);
        mBack = (LinearLayout) this.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

//    /**
//     * 获取数据
//     */
//    private void getData() {
//        getProgressBar("请稍候。。。。", "正在加载数据");
//        new Thread() {
//            public void run() {
//                try {
//                    ProvideViewSysUserDoctorInfoAndHospital provideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();
//                    provideViewSysUserDoctorInfoAndHospital.setLoginDoctorPosition(mApp.loginDoctorPosition);
//                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
//                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
//                    //实体转JSON字符串
//                    String str = new Gson().toJson(provideViewSysUserDoctorInfoAndHospital);
//                    //获取用户数据
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "/doctorPersonalSetControlle/getDoctorAboutUsHtmlData");
//                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(0);
//                        return;
//                    }
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(0);
//            }
//        }.start();
//    }

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
