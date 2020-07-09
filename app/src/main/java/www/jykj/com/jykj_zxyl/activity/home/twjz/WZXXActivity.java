package www.jykj.com.jykj_zxyl.activity.home.twjz;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientInterrogation;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import util.NetWorkUtils;
import util.QRCodeUtil;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.QRCodeActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideDoctorSetServiceState;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmenNoFinish;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmentHistory;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.PhotoDialog;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 问诊信息
 */
public class WZXXActivity extends AppCompatActivity {

    private Context mContext;
    private WZXXActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //返回字符串
    private String mGetImgNetRetStr;                 //获取图片返回字符串
    private TextView mJZLX;                         //就诊类型
    private TextView mYSXM;                         //医生姓名
    private TextView mHZXM;                         //患者姓名
    private TextView mHZSJ;                         //患者手机
    private TextView mXB;                         //性别
    private TextView mNL;                         //年龄
    private TextView mYCRQ;                         //最早发现高血压异常日期
    private TextView mJZHZ;                         //家族内是否有其他高血压患者
    private TextView mSSY;                         //收缩压
    private TextView mSZY;                         //舒张压
    private TextView mXL;                         //心率

    private TextView mCLYQ;                        //测量仪器

    private TextView mCLFF;                          //测量方法
    private TextView mGXYBS;                          //高血压病史
    private TextView mBQZS;                          //病情自述

    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private WZZXImageViewRecycleAdapter mAdapter;


    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private List<ProvideInteractPatientInterrogation> mProvideInteractPatientInterrogations;
    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private LinearLayout mBack;

    private yyz_exploit.dialog.ImageView imageView1;
    private TextView tv;
    private TextView tv_yes;
    private ScrollView wzxx_sc;
    private TextView tv_gson;

    private void setLayoutDate() {
        if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==0){
            mJZLX.setText("未知");
        }else if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==1){
            mJZLX.setText("图文就诊");
        }
        else if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==2){
            mJZLX.setText("音频就诊");
        }
        else if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==3){
            mJZLX.setText("视频就诊");
        }
        else if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==4){
            mJZLX.setText("签约就诊");
        }
        else if(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType()==5){
            mJZLX.setText("电话就诊");
        }

        //   mYSXM.setText(mProvideInteractPatientInterrogations.get(0).getDoctorName());
        mHZXM.setText(mProvideInteractPatientInterrogations.get(0).getPatientName());
        mHZSJ.setText(mProvideInteractPatientInterrogations.get(0).getPatientLinkPhone());
        if (mProvideInteractPatientInterrogations.get(0).getGender() == 0)
            mXB.setText("未知");
        if (mProvideInteractPatientInterrogations.get(0).getGender() == 1)
            mXB.setText("男");
        if (mProvideInteractPatientInterrogations.get(0).getGender() == 2)
            mXB.setText("女");
        mNL.setText(mProvideInteractPatientInterrogations.get(0).getBirthday());

        //最早日期
        // new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if(mProvideInteractPatientInterrogations.get(0).getBloodPressureAbnormalDate()==null){
            mYCRQ.setText("");
        }else{
            mYCRQ.setText(Util.dateToStrNUR(mProvideInteractPatientInterrogations.get(0).getBloodPressureAbnormalDate()));
        }

        if (mProvideInteractPatientInterrogations.get(0).getFlagFamilyHtn() == 0)
            mJZHZ.setText("否");
        if (mProvideInteractPatientInterrogations.get(0).getFlagFamilyHtn() == 1)
            mJZHZ.setText("是");
        mSSY.setText(mProvideInteractPatientInterrogations.get(0).getHighPressureNum() + "mmHg");
        mSZY.setText(mProvideInteractPatientInterrogations.get(0).getLowPressureNum() + "mmHg");
        mXL.setText(mProvideInteractPatientInterrogations.get(0).getHeartRateNum() + "次/分钟");
        mCLYQ.setText(mProvideInteractPatientInterrogations.get(0).getMeasureInstrumentName());
        mCLFF.setText(mProvideInteractPatientInterrogations.get(0).getMeasureModeName());
        mGXYBS.setText(mProvideInteractPatientInterrogations.get(0).getHtnHistory());
        mBQZS.setText(mProvideInteractPatientInterrogations.get(0).getStateOfIllness());
        if(mProvideInteractPatientInterrogations.get(0).getFlagFamilyHtn()==1){
            tv_yes.setText("是");
        }if(mProvideInteractPatientInterrogations.get(0).getFlagFamilyHtn()==0){
            tv_yes.setText("否");
        }
    }


    /**
     * 初始化布局
     */
    private void initLayout() {
        tv_gson = findViewById(R.id.tv_gson);
        wzxx_sc = findViewById(R.id.wzxx_sc);
        tv_yes = findViewById(R.id.tv_yes);
        mBack = (LinearLayout) this.findViewById(R.id.iv_back_left);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mJZLX = (TextView) this.findViewById(R.id.tv_activityHZZL_userYW);
        //   mYSXM = (TextView) this.findViewById(R.id.tv_activityHZZL_userTZ);
        mHZXM = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXY);
        mHZSJ = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXJ);
        mXB = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFAY);
        mNL = (TextView) this.findViewById(R.id.tv_activityHZZL_userAuthState);
        mYCRQ = (TextView) this.findViewById(R.id.tv_activityHZZL_MZ);
        mJZHZ = (TextView) this.findViewById(R.id.tv_activityHZZL_userName);
        mSSY = (TextView) this.findViewById(R.id.tv_activityHZZL_sex);
        mSZY = (TextView) this.findViewById(R.id.tv_activityHZZL_szy);
        mXL = (TextView) this.findViewById(R.id.tv_activityHZZL_xl);
        mCLYQ = (TextView) this.findViewById(R.id.tv_activityHZZL_clyq);
        mCLFF = (TextView) this.findViewById(R.id.tv_activityHZZL_clfs);
        mGXYBS = (TextView) this.findViewById(R.id.tv_activityHZZL_BirthDay);
        mBQZS = (TextView) this.findViewById(R.id.tv_activityHZZL_idCardNum);

        tv = findViewById(R.id.tv);
        mImageRecycleView = (RecyclerView) this.findViewById(R.id.rv_imageView);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);
        //创建并设置Adapter

        mAdapter = new WZZXImageViewRecycleAdapter(mProvideBasicsImg, mContext);
        mImageRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WZZXImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                PhotoDialog photoDialog = new PhotoDialog(mContext, R.style.PhotoDialog);
                photoDialog.setDate(mContext, mApp, mProvideBasicsImg, position);
                photoDialog.show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wzxx);
        ActivityUtil.setStatusBarMain(WZXXActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initLayout();
        initHandler();
        getData();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            wzxx_sc.setVisibility(View.GONE);
                            tv_gson.setVisibility(View.VISIBLE);
                            cacerProgress();
                        } else {
                            mProvideInteractPatientInterrogations = JSON.parseArray(netRetEntity.getResJsonData(), ProvideInteractPatientInterrogation.class);
                            if (mProvideInteractPatientInterrogations == null && mProvideInteractPatientInterrogations.size() ==0&&mProvideInteractPatientInterrogations.get(0).getImgCode().equals("")&&mProvideInteractPatientInterrogations.get(0).getImgCode()==null){
                                wzxx_sc.setVisibility(View.GONE);

                            }else{
                                setLayoutDate();
                                getImgData();

                            }

                        }

                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {

                        } else {
                            mProvideBasicsImg = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsImg.class);
                            if (mProvideBasicsImg != null && mProvideBasicsImg.size() > 0) {
                                tv.setText("图片仅本人和咨询医生可见");
                                mAdapter.setDate(mProvideBasicsImg);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        break;
                }
            }
        };
    }


    /**
     * 设置数据
     */
    private void getData() {
        ProvideInteractPatientInterrogation provideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
        provideInteractPatientInterrogation.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractPatientInterrogation.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientInterrogation.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        provideInteractPatientInterrogation.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideInteractPatientInterrogation.setPatientCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResPatientInterrogationText");
                    Log.e("文字", "run:文字 "+mNetRetStr );
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
    /**
     * 设置数据
     */
    private void getImgData() {
        ProvideBasicsImg provideBasicsImg = new ProvideBasicsImg();
        provideBasicsImg.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideBasicsImg.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBasicsImg.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        provideBasicsImg.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        Log.e("tag", "图片编码 "+mProvideInteractPatientInterrogations.get(0).getImgCode() );
        if(TextUtils.isEmpty(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getImgCode())){
            provideBasicsImg.setImgCode(mProvideInteractPatientInterrogations.get(0).getImgCode());
        }
        else{
            provideBasicsImg.setImgCode("");
        }
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideBasicsImg);
                    mGetImgNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResPatientInterrogationImg");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                    Log.e("图片", "图片 "+mGetImgNetRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mGetImgNetRetStr = new Gson().toJson(retEntity);
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
