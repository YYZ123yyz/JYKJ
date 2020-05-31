package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.basicDate.ProvideBasicsRegion;
import entity.patientInfo.ProvidePatientLabel;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.PatientLaberAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 就诊总览==》个人总览==>个人状况==>患者标签
 */
public class GRXX_GRZK_HZBQActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         TWJZ_CFQActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private         JYZL_GRZLRecycleAdapter       mJYZL_GRZLRecycleAdapter;      //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据
    private         LinearLayout            mJBXX;                                  //基本信息
    public                  ProgressDialog              mDialogProgress =null;
    private                 String                      mNetRetStr;                 //获取返回字符串
    private         String                  mPatientCode;
    private         int                     mRowNum = 10;                            //每页行数
    private         int                     mPageNum = 1;                           //页数

    private         List<ProvidePatientLabel> providePatientLabels = new ArrayList<>();

    private         PatientLaberAdapter    mPatientLaberAdapter;

    private RelativeLayout          mBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_hzbq);
        ActivityUtil.setStatusBarMain(GRXX_GRZK_HZBQActivity.this);
        mPatientCode = getIntent().getStringExtra("patientCode");
        mContext = this;
        mApp = (JYKJApplication) getApplication();
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
                        providePatientLabels = JSON.parseArray(JSON.parseObject(mNetRetStr,NetRetEntity.class).getResJsonData(),ProvidePatientLabel.class);
//                        showViewDate();
                        mPatientLaberAdapter.setDate(providePatientLabels);
                        mPatientLaberAdapter.notifyDataSetChanged();
                        System.out.println();
                        break;
                    case 2:
                        break;
                }
            }
        };
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
                    ProvidePatientLabel providePatientLabel = new ProvidePatientLabel();
                    providePatientLabel.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    providePatientLabel.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    providePatientLabel.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    providePatientLabel.setSearchPatientCode(mPatientCode);
                    providePatientLabel.setRowNum(mRowNum+"");
                    providePatientLabel.setPageNum(mPageNum+"");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(providePatientLabel), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResLabel");
                    Log.e("tag", "run:vvv "+mNetRetStr );
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

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (RelativeLayout) this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());
        mRecycleView = (RecyclerView)this.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mPatientLaberAdapter = new PatientLaberAdapter(providePatientLabels,mContext);
        mRecycleView.setAdapter(mPatientLaberAdapter);
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.li_activityGRZK_JBXX:
                    startActivity(new Intent(mContext,GRXX_GRZK_HZBQActivity.class));
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
