package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsRegion;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 就诊总览==》个人总览==>个人状况==>基本信息
 */
public class GRXX_GRZK_JBXXActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         TWJZ_CFQActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private         JYZL_GRZLRecycleAdapter       mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    private         String                  mPatientCode;                       //患者code

    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private             ProvideViewSysUserPatientInfoAndRegion  mProvideViewSysUserPatientInfoAndRegion;

    private         ImageView               mUserPhoto;                         //用户头像
    private         ImageView               mUserQrCode;                         //二维码
    private         TextView                mUserAuth;                         //认证状态
    private         TextView                mUserName;                         //姓名
    private         TextView                mUserSex;                         //性别
    private         TextView                mUserMZ;                         //民族
    private         TextView                mUserJG;                         //籍贯
    private         TextView                mUserBirthDay;                         //出生日期
    private         TextView                mUserIDCardNum;                         //身份证号
    private         TextView                mUserLinkPhone;                         //电话
    private         TextView                mUserRegion;                         //所在区域
    private         TextView                mUserAddress;                         //现居地
    private         TextView                mNewLoginDate;                      //最近一次登录时间

    private         LinearLayout            mBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_jbxx);
        ActivityUtil.setStatusBarMain(GRXX_GRZK_JBXXActivity.this);

        mContext = this;
        mApp = (JYKJApplication) getApplication();
        mPatientCode = getIntent().getStringExtra("patientCode");
        initLayout();
        initHandler();
        getDate();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(JSON.parseObject(mNetRetStr,NetRetEntity.class).getResJsonData(),ProvideViewSysUserPatientInfoAndRegion.class);
                        showViewDate();
                        break;
                    case 2:
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.li_back);
        mBack.setOnClickListener(new ButtonClick());
        mUserPhoto = (ImageView)this.findViewById(R.id.iv_activityHZZL_userPhoto);
        mUserQrCode = (ImageView)this.findViewById(R.id.iv_activityHZZL_userQrCode);
        mUserAuth = (TextView)this.findViewById(R.id.tv_activityHZZL_userAuthState);

        mUserAuth = (TextView)this.findViewById(R.id.tv_activityHZZL_userAuthState);
        mUserName = (TextView)this.findViewById(R.id.tv_activityHZZL_userName);
        mUserSex = (TextView)this.findViewById(R.id.tv_activityHZZL_sex);
        mUserMZ = (TextView)this.findViewById(R.id.tv_activityHZZL_MZ);
        mUserJG = (TextView)this.findViewById(R.id.tv_activityHZZL_JG);
        mUserBirthDay = (TextView)this.findViewById(R.id.tv_activityHZZL_BirthDay);
        mUserIDCardNum = (TextView)this.findViewById(R.id.tv_activityHZZL_idCardNum);
        mUserLinkPhone = (TextView)this.findViewById(R.id.tv_activityHZZL_linkPhone);

        mUserRegion = (TextView)this.findViewById(R.id.tv_activityHZZL_region);
        mUserAddress = (TextView)this.findViewById(R.id.tv_activityHZZL_address);

        mNewLoginDate = (TextView)this.findViewById(R.id.tv_activityHZZL_newLoginDate);

    }

    /**
     * 设置显示
     */
    private void showViewDate() {

        try {
            int avatarResId = Integer.parseInt(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            Glide.with(mContext).load(avatarResId).into(mUserPhoto);
        } catch (Exception e) {
            //use default avatar
            Glide.with(mContext).load(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(mUserPhoto);
        }
        Bitmap bitmap = Util.createQRImage(mProvideViewSysUserPatientInfoAndRegion.getQrCode());
        mUserQrCode.setImageBitmap(bitmap);

        if (mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() == 0)
            mUserAuth.setText("未认证");
        else
            mUserAuth.setText("已认证");

        if (mProvideViewSysUserPatientInfoAndRegion.getUserName() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getUserName()))
            mUserName.setText("未设置");
        else
            mUserName.setText(mProvideViewSysUserPatientInfoAndRegion.getUserName());

        if (mProvideViewSysUserPatientInfoAndRegion.getGender() == 0)
            mUserSex.setText("未知");
        else if (mProvideViewSysUserPatientInfoAndRegion.getGender() == 1)
            mUserSex.setText("男");
        else if (mProvideViewSysUserPatientInfoAndRegion.getGender() == 2)
            mUserSex.setText("女");

        if (mProvideViewSysUserPatientInfoAndRegion.getNation() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getNation()))
            mUserMZ.setText("未设置");
        else
            mUserMZ.setText(mProvideViewSysUserPatientInfoAndRegion.getNation());

        if (mProvideViewSysUserPatientInfoAndRegion.getNativePlace() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getNativePlace()))
            mUserJG.setText("未设置");
        else
            mUserJG.setText(mProvideViewSysUserPatientInfoAndRegion.getNativePlace());

        if (mProvideViewSysUserPatientInfoAndRegion.getBirthday() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getBirthday()))
            mUserBirthDay.setText("未设置");
        else
            mUserBirthDay.setText(Util.dateToStr(mProvideViewSysUserPatientInfoAndRegion.getBirthday()));

        if (mProvideViewSysUserPatientInfoAndRegion.getNewLoginDate() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getNewLoginDate()))
            mNewLoginDate.setText("未设置");
        else
            mNewLoginDate.setText(Util.dateToStr(mProvideViewSysUserPatientInfoAndRegion.getNewLoginDate()));

        if (mProvideViewSysUserPatientInfoAndRegion.getIdNumber() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getIdNumber()))
            mUserIDCardNum.setText("未设置");
        else
            mUserIDCardNum.setText(mProvideViewSysUserPatientInfoAndRegion.getIdNumber());

        if (mProvideViewSysUserPatientInfoAndRegion.getLinkPhone() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getLinkPhone()))
            mUserLinkPhone.setText("未设置");
        else
            mUserLinkPhone.setText(mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());

        String region = "";
        if (mProvideViewSysUserPatientInfoAndRegion.getProvinceName() != null || !"".equals(mProvideViewSysUserPatientInfoAndRegion.getProvinceName()))
            region += mProvideViewSysUserPatientInfoAndRegion.getProvinceName();
        if (mProvideViewSysUserPatientInfoAndRegion.getCityName() != null || !"".equals(mProvideViewSysUserPatientInfoAndRegion.getCityName()))
            region += mProvideViewSysUserPatientInfoAndRegion.getCityName();
        if (mProvideViewSysUserPatientInfoAndRegion.getAreaName() != null || !"".equals(mProvideViewSysUserPatientInfoAndRegion.getAreaName()))
            region += mProvideViewSysUserPatientInfoAndRegion.getAreaName();
        if ("".equals(region))
            mUserRegion .setText("未设置");
        else
            mUserRegion.setText(region);

        if (mProvideViewSysUserPatientInfoAndRegion.getAddress() == null || "".equals(mProvideViewSysUserPatientInfoAndRegion.getAddress()))
            mUserAddress.setText("未设置");
        else
            mUserAddress.setText(mProvideViewSysUserPatientInfoAndRegion.getAddress());
    }

    /**
     * 获取基本信息
     */
    private void getDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion = new ProvideViewSysUserPatientInfoAndRegion();
                    provideViewSysUserPatientInfoAndRegion.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewSysUserPatientInfoAndRegion.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewSysUserPatientInfoAndRegion.setSearchPatientCode(mPatientCode);

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResBasic");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取区域信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
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



    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityGRZK_JBXX:
                    startActivity(new Intent(mContext,GRXX_GRZK_JBXXActivity.class));
                    break;
                case R.id.li_back:
                    finish();
                    break;
            }
        }
    }



    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
