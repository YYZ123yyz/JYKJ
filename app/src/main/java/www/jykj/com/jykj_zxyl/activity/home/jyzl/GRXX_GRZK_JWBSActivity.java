package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.PatientJWBSAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * 就诊总览==》个人总览==>个人状况==>既往病史
 */
public class GRXX_GRZK_JWBSActivity extends AppCompatActivity {


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
    private          int                    recordTypeId = 0;                           //搜索的记录类型
    private         TextView                mMore;

    private         LinearLayout            mQB;                                //选择全部
    private         LinearLayout            mJWBS;                                //选择既往病史
    private         LinearLayout            mBCJL;                                //选择病程记录
    private RelativeLayout                  mBack;
    private         List<ProvidePatientConditionDiseaseRecord> mProvidePatientConditionDiseaseRecords = new ArrayList<>();

    private PatientJWBSAdapter mPatientJWBSAdapter;

    private PopupWindow mPopuWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl_grzk_jwbs);
        mPatientCode = getIntent().getStringExtra("patientCode");
        mContext = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        getDate();

    }

    /**
     *  显示Popuwindow
     */
    private void showPopuWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popuwindow_activityjwbs_more,null);
        mQB = (LinearLayout)contentView.findViewById(R.id.l1_popuwindowActivitEditMyInfo_qb);
        mQB.setOnClickListener(new ButtonClick());

        mJWBS = (LinearLayout)contentView.findViewById(R.id.l1_popuwindowActivitEditMyInfo_jwbs);
        mJWBS.setOnClickListener(new ButtonClick());

        mBCJL = (LinearLayout)contentView.findViewById(R.id.l1_popuwindowActivitEditMyInfo_bcjl);
        mBCJL.setOnClickListener(new ButtonClick());

        int win = view.getWidth();
        mPopuWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击窗体外部后dismiss
        mPopuWindow.setOutsideTouchable(true);
        mPopuWindow.setTouchable(true);
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        if (mPopuWindow.isShowing())
        {
            mPopuWindow.dismiss();
        }
        else
        {
            mPopuWindow.showAsDropDown(view,0,0);
        }
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
                        Log.e("aaa","mNetRetStr/"+mNetRetStr);
                        mProvidePatientConditionDiseaseRecords = JSON.parseArray(JSON.parseObject(mNetRetStr,NetRetEntity.class).getResJsonData(),ProvidePatientConditionDiseaseRecord.class);
//                        showViewDate();
                        mPatientJWBSAdapter.setDate(mProvidePatientConditionDiseaseRecords);
                        mPatientJWBSAdapter.notifyDataSetChanged();
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
                    ProvidePatientConditionDiseaseRecord providePatientConditionDiseaseRecord = new ProvidePatientConditionDiseaseRecord();
                    providePatientConditionDiseaseRecord.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    providePatientConditionDiseaseRecord.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    providePatientConditionDiseaseRecord.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    providePatientConditionDiseaseRecord.setSearchPatientCode(mPatientCode);
                    providePatientConditionDiseaseRecord.setRowNum(mRowNum+"");
                    providePatientConditionDiseaseRecord.setPageNum(mPageNum+"");
                    providePatientConditionDiseaseRecord.setRecordTypeId(recordTypeId);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(providePatientConditionDiseaseRecord), Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResDiseaseRecord");
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
        mBack = (RelativeLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());
        mMore = (TextView)this.findViewById(R.id.more);
        mMore.setOnClickListener(new ButtonClick());
        mRecycleView = (RecyclerView)this.findViewById(R.id.rv_activityPatientLaber_patientLaber);

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mPatientJWBSAdapter = new PatientJWBSAdapter(mProvidePatientConditionDiseaseRecords,mContext);
        mRecycleView.setAdapter(mPatientJWBSAdapter);
        mPatientJWBSAdapter.setOnItemClickListener(new PatientJWBSAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,JWBSDetailActivity.class).putExtra("patientInfo",mProvidePatientConditionDiseaseRecords.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.more:
                    showPopuWindow(mMore);
                    break;
                case R.id.l1_popuwindowActivitEditMyInfo_qb:
                    recordTypeId = 0;
                    mPopuWindow.dismiss();
                    getDate();

                    break;
                case R.id.l1_popuwindowActivitEditMyInfo_jwbs:
                    recordTypeId = 1;
                    mPopuWindow.dismiss();
                    getDate();
                    break;
                case R.id.l1_popuwindowActivitEditMyInfo_bcjl:
                    recordTypeId = 2;
                    mPopuWindow.dismiss();
                    getDate();
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
