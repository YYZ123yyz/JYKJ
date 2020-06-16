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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientConditionHealthy;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 就诊总览==》个人总览==>个人状况==>症状信息
 */
public class GRXX_GRZK_ZZXXActivity extends AppCompatActivity {


    private Context mContext;
    private TWJZ_CFQActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private LinearLayout mJBXX;                                  //基本信息
    private String mPatientCode;                       //患者code

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //获取返回字符串
    private ProvidePatientConditionHealthy mProvidePatientConditionHealthy;

    private TextView mSG;                         //身高
    private TextView mYW;                         //腰围
    private TextView mTZ;                         //体重
    private TextView mSFXY;                         //是否吸烟
    private TextView mSFXJ;                         //是否酗酒
    private TextView mSFAY;                         //是否熬夜

    private TextView mUserAuth;                         //认证状态
    private TextView mUserName;                         //姓名
    private TextView mUserSex;                         //性别
    private TextView mUserMZ;                         //民族
    private TextView mUserJG;                         //籍贯
    private TextView mUserBirthDay;                         //出生日期
    private TextView mUserIDCardNum;                         //身份证号
    private TextView mUserLinkPhone;                         //电话
    private TextView mUserRegion;                         //所在区域

    private TextView mMQZLFA;                        //目前治疗方案

    private TextView mBQZS;                          //病情自述
    private LinearLayout mBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_zzxx);
        ActivityUtil.setStatusBarMain(GRXX_GRZK_ZZXXActivity.this);

        mContext = this;
        mApp = (JYKJApplication) getApplication();
        mPatientCode = getIntent().getStringExtra("patientCode");
        initLayout();
        initHandler();
        getDate();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        mProvidePatientConditionHealthy = JSON.parseObject(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), ProvidePatientConditionHealthy.class);
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
        mBack = (LinearLayout) this.findViewById(R.id.li_back);
        mBack.setOnClickListener(new ButtonClick());

        mUserAuth = (TextView) this.findViewById(R.id.tv_activityHZZL_userAuthState);

        mUserAuth = (TextView) this.findViewById(R.id.tv_activityHZZL_userAuthState);
        mUserName = (TextView) this.findViewById(R.id.tv_activityHZZL_userName);
        mUserSex = (TextView) this.findViewById(R.id.tv_activityHZZL_sex);
        mUserMZ = (TextView) this.findViewById(R.id.tv_activityHZZL_MZ);
        mUserJG = (TextView) this.findViewById(R.id.tv_activityHZZL_JG);
        mUserBirthDay = (TextView) this.findViewById(R.id.tv_activityHZZL_BirthDay);
        mUserIDCardNum = (TextView) this.findViewById(R.id.tv_activityHZZL_idCardNum);
        mUserLinkPhone = (TextView) this.findViewById(R.id.tv_activityHZZL_linkPhone);

        mUserRegion = (TextView) this.findViewById(R.id.tv_activityHZZL_region);

        mSG = (TextView) this.findViewById(R.id.tv_activityHZZL_userSG);
        mYW = (TextView) this.findViewById(R.id.tv_activityHZZL_userYW);
        mTZ = (TextView) this.findViewById(R.id.tv_activityHZZL_userTZ);
        mSFXY = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXY);
        mSFXJ = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFXJ);
        mSFAY = (TextView) this.findViewById(R.id.tv_activityHZZL_userSFAY);

        mMQZLFA = (TextView) this.findViewById(R.id.tv_activityHZZL_mqzlfa);
        mBQZS = (TextView) this.findViewById(R.id.tv_activityHZZL_bqzs);
    }

    /**
     * 设置显示
     */
    private void showViewDate() {

        if (mProvidePatientConditionHealthy.getHeight() == null || "".equals(mProvidePatientConditionHealthy.getHeight()))
            mSG.setText("未设置");
        else{
            mSG.setText(mProvidePatientConditionHealthy.getHeight()+"");
        }

        if (mProvidePatientConditionHealthy.getWaistline() == null || "".equals(mProvidePatientConditionHealthy.getWaistline()))
            mYW.setText("未设置");
        else
            mYW.setText(mProvidePatientConditionHealthy.getWaistline()+"");

        if (mProvidePatientConditionHealthy.getWeight() == null || "".equals(mProvidePatientConditionHealthy.getWeight()))
            mTZ.setText("未设置");
        else
            mTZ.setText(mProvidePatientConditionHealthy.getWeight()+"");

        if (mProvidePatientConditionHealthy.getFlagSmoking() == null || "".equals(mProvidePatientConditionHealthy.getBloodPressureAbnormalDate()))
            mSFXY.setText("未设置");
        else if (mProvidePatientConditionHealthy.getFlagSmoking() == 0)
            mSFXY.setText("否");
        else if (mProvidePatientConditionHealthy.getFlagSmoking() == 1)
            mSFXY.setText("是");

        if (mProvidePatientConditionHealthy.getFlagAlcoholism() == null || "".equals(mProvidePatientConditionHealthy.getFlagAlcoholism()))
            mSFXJ.setText("未设置");
        else if (mProvidePatientConditionHealthy.getFlagAlcoholism() == 0)
            mSFXJ.setText("否");
        else if (mProvidePatientConditionHealthy.getFlagAlcoholism() == 1)
            mSFXJ.setText("是");

        if (mProvidePatientConditionHealthy.getFlagStayUpLate() == null || "".equals(mProvidePatientConditionHealthy.getFlagStayUpLate()))
            mSFAY.setText("未设置");
        else if (mProvidePatientConditionHealthy.getFlagStayUpLate() == 0)
            mSFAY.setText("否");
        else if (mProvidePatientConditionHealthy.getFlagStayUpLate() == 1)
            mSFAY.setText("是");


        if (mProvidePatientConditionHealthy.getBloodPressureAbnormalDate() == null || "".equals(mProvidePatientConditionHealthy.getBloodPressureAbnormalDate()))
            mUserAuth.setText("未设置");
        else
            mUserAuth.setText(Util.dateToStr(mProvidePatientConditionHealthy.getBloodPressureAbnormalDate()));

        if (mProvidePatientConditionHealthy.getHtnHistory() == null || "".equals(mProvidePatientConditionHealthy.getHtnHistory()))
            mUserName.setText("未设置");
        else
            mUserName.setText(mProvidePatientConditionHealthy.getHtnHistory());

        if (mProvidePatientConditionHealthy.getFlagFamilyHtn() == null)
            mUserSex.setText("未设置");
        else if (mProvidePatientConditionHealthy.getFlagFamilyHtn() == 0)
            mUserSex.setText("否");
        else if (mProvidePatientConditionHealthy.getFlagFamilyHtn() == 1)
            mUserSex.setText("是");

        if (mProvidePatientConditionHealthy.getConfirmedYear() == null || "".equals(mProvidePatientConditionHealthy.getConfirmedYear()))
            mUserMZ.setText("未设置");
        else
            mUserMZ.setText(Util.dateToStr(mProvidePatientConditionHealthy.getConfirmedYear()));

//        if (mProvidePatientConditionHealthy.getNativePlace() == null || "".equals(mProvidePatientConditionHealthy.getNativePlace()))
//            mUserJG.setText("未设置");
//        else
//            mUserJG.setText(mProvidePatientConditionHealthy.getNativePlace());

        if (mProvidePatientConditionHealthy.getOnsetSymptoms() == null || "".equals(mProvidePatientConditionHealthy.getOnsetSymptoms()))
            mUserBirthDay.setText("未设置");
        else
            mUserBirthDay.setText(mProvidePatientConditionHealthy.getOnsetSymptoms());


        if (mProvidePatientConditionHealthy.getCurrentSymptoms() == null || "".equals(mProvidePatientConditionHealthy.getCurrentSymptoms()))
            mUserIDCardNum.setText("未设置");
        else
            mUserIDCardNum.setText(mProvidePatientConditionHealthy.getCurrentSymptoms());

        if (mProvidePatientConditionHealthy.getComplication() == null || "".equals(mProvidePatientConditionHealthy.getComplication()))
            mUserLinkPhone.setText("未设置");
        else
            mUserLinkPhone.setText(mProvidePatientConditionHealthy.getComplication());

        if (mProvidePatientConditionHealthy.getCombinedDisease() != null || !"".equals(mProvidePatientConditionHealthy.getCombinedDisease()))
            mUserRegion.setText("未设置");
        else
            mUserRegion.setText(mProvidePatientConditionHealthy.getCombinedDisease());

        if (mProvidePatientConditionHealthy.getCurrentTreatmentPlan() != null || !"".equals(mProvidePatientConditionHealthy.getCurrentTreatmentPlan()))
            mMQZLFA.setText("未设置");
        else
            mMQZLFA.setText(mProvidePatientConditionHealthy.getCurrentTreatmentPlan());

        if (mProvidePatientConditionHealthy.getStateOfIllness() != null || !"".equals(mProvidePatientConditionHealthy.getStateOfIllness()))
            mBQZS.setText("未设置");
        else
            mBQZS.setText(mProvidePatientConditionHealthy.getStateOfIllness());
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

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideViewSysUserPatientInfoAndRegion), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResHealthy");
                    Log.e("tag", "健康信息 "+mNetRetStr );
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


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityGRZK_JBXX:
                    startActivity(new Intent(mContext, GRXX_GRZK_ZZXXActivity.class));
                    break;
                case R.id.li_back:
                    finish();
                    break;
            }
        }
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
