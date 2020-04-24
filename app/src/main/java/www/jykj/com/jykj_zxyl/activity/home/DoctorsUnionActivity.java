package www.jykj.com.jykj_zxyl.activity.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import entity.MemberListInfo;
import entity.basicDate.ProvideBasicsRegion;
import entity.unionInfo.ProvideUnionDoctorMember;
import entity.unionInfo.ProvideViewUnionDoctorMemberAndUnionDetailInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberDetailInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import netService.entity.ParmentEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.CreateUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorUnionInviteMemberActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorUnionSettingOrgDateActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorsUnionSettingActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.ReviewActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.UnionMessageActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.UpdateUnionActivity;
import www.jykj.com.jykj_zxyl.adapter.MyUnionMemberListAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 医生联盟
 */
public class DoctorsUnionActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<MemberListInfo> memberListInfoList = new ArrayList<>();
    private MyUnionMemberListAdapter mAdapter;
    private LinearLayout llJoinUnion;
    private LinearLayout llCreateUnion;
    private LinearLayout llReview;
    private LinearLayout llBack;

    public ProgressDialog mDialogProgress = null;

    private Context mContext;                                       //
    private DoctorsUnionActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;
    private List<ProvideViewUnionDoctorMemberAndUnionDetailInfo> mProvideViewUnionDoctorMemberAndUnionDetailInfos = new ArrayList<>();
    private List<ProvideViewUnionDoctorMemberAndUnionDetailInfo> mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos = new ArrayList<>();
    private ImageView mUserHead;                  //用户头像
    private TextView mUserName;                  //医生姓名
    private TextView mExitUnion;                  //退出联盟
    private TextView mUserRank;                  //联盟等级
    private TextView mDoctorNum;                  //医生数
    private TextView mPatientNum;                  //患者数
    private TextView mNoDateView;                    //没有数据
    private RelativeLayout mDateView;                      //显示数据

    private TextView mSWichUnion;                    //切换联盟
    private                 List<ProvideUnionDoctorMember>    mProvideUnionDoctorMember = new ArrayList<>();          //医生权限
    private                 TextView                    mOrgDate;                       //设置层级架构

    private                 int                         mRowNum = 10;                        //分页行数
    private                 int                         mPageNum = 1;                       //分页页码

    private                 List<ProvideViewUnionDoctorMemberDetailInfo> mProvideViewUnionDoctorMemberDetailInfos = new ArrayList<>();

    private                 TextView                    mInviteMember;                      //邀请加入

    private                 boolean                    mLoadDate = true;                    //是否可以加载数据
    private                 LinearLayout                mLMMessage;                         //联盟消息
    private                 RelativeLayout              mUnionInfoLayout;                   //我的联盟布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_union);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initView();
        initListener();
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
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 0) {
                            Toast.makeText(mContext, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            setView();
                        } else {
                            setView();
                        }
                        getUnionMember();
                        break;

                    case 2:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 0)
                            Toast.makeText(mContext, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else if (mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos.size() > 0) {

                            setDialogView();
                        }
                        break;
                    case 3:
                        cacerProgress();
                        getDate();
                        break;
//                    case 4:
//
//                        break;
                    case 4:
                        cacerProgress();
                       mAdapter.setDate(mProvideViewUnionDoctorMemberDetailInfos);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    /**
     * 显示切换联盟对话框
     */
    private void setDialogView() {
        String[] mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfosNames = new String[mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos.size()];
        for (int i = 0; i < mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos.size(); i++) {
            mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfosNames[i] = mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos.get(i).getUnionName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择联盟");
        listDialog.setItems(mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfosNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSwitchSelectionUnion(which);
            }
        });
        listDialog.show();
    }

    /**
     * 获取选中的联盟
     *
     * @param which
     */
    private void getSwitchSelectionUnion(int which) {
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewUnionDoctorMemberAndUnionDetailInfo provideViewUnionDoctorMemberAndUnionDetailInfo = new ProvideViewUnionDoctorMemberAndUnionDetailInfo();
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setUnionCode(mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos.get(which).getUnionCode());
                    String jsonString = JSON.toJSONString(provideViewUnionDoctorMemberAndUnionDetailInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "unionDoctorController/getMyDoctorUnionSwitchSelection");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mProvideViewUnionDoctorMemberAndUnionDetailInfos = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewUnionDoctorMemberAndUnionDetailInfo.class);
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
     * 显示
     */
    private void setView() {
        if (mProvideViewUnionDoctorMemberAndUnionDetailInfos.size() == 0) {
            mNoDateView.setVisibility(View.VISIBLE);
            mDateView.setVisibility(View.GONE);
        } else {
            mUserName.setText(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionName());
            mUserRank.setText(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionGradeName());
            mDoctorNum.setVisibility(View.GONE);
            if (mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getManagePatientNum() == null)
                mPatientNum.setText("患者数:0");
            else
                mPatientNum.setText("患者数:" + mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getManagePatientNum());
            mNoDateView.setVisibility(View.GONE);
            mDateView.setVisibility(View.VISIBLE);
        }
        if (mProvideUnionDoctorMember.size() > 0)
        {
            if (mProvideUnionDoctorMember.get(0).getFlagCreateMan() == 1)
            {
                mOrgDate.setVisibility(View.VISIBLE);
             }
             else if (mProvideUnionDoctorMember.get(0).getFlagCreateMan() == 0)
            {
                mOrgDate.setVisibility(View.GONE);
            }
            else if (mProvideUnionDoctorMember.get(0).getFlagOperPower() == 1)
            {
                mOrgDate.setVisibility(View.VISIBLE);
            }
            else if (mProvideUnionDoctorMember.get(0).getFlagOperPower() == 1)
            {
                mOrgDate.setVisibility(View.GONE);
            }
            else if (mProvideUnionDoctorMember.get(0).getFlagPerson() == 1)
            {
                mOrgDate.setVisibility(View.VISIBLE);
            }
            else if (mProvideUnionDoctorMember.get(0).getFlagPerson() == 1)
            {
                mOrgDate.setVisibility(View.GONE);
            }
            if (mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionLogoUrl() != null && !"".equals(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionLogoUrl()))
                Glide.with(this).load(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionLogoUrl()).into(mUserHead);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.union_list);
        llJoinUnion = (LinearLayout) findViewById(R.id.ll_join_union);
        llCreateUnion = (LinearLayout) findViewById(R.id.ll_create_union);
        mUnionInfoLayout = (RelativeLayout)findViewById(R.id.dateView);
        llReview = (LinearLayout) findViewById(R.id.ll_review);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        mLMMessage = (LinearLayout)findViewById(R.id.ll_message);
        mUserHead = (ImageView) this.findViewById(R.id.iv_head);
        mUserName = (TextView) this.findViewById(R.id.tv_name);
        mExitUnion = (TextView) this.findViewById(R.id.tv_exitUnion);
        mUserRank = (TextView) this.findViewById(R.id.tv_rank);
        mDoctorNum = (TextView) this.findViewById(R.id.tv_doctor_num);
        mPatientNum = (TextView) this.findViewById(R.id.tv_patient_num);

        mNoDateView = (TextView) this.findViewById(R.id.noDateView);
        mDateView = (RelativeLayout) this.findViewById(R.id.dateView);

        mSWichUnion = (TextView) this.findViewById(R.id.switchUnionTextView);
        mExitUnion = (TextView) this.findViewById(R.id.tv_exitUnion);
        mOrgDate = (TextView)this.findViewById(R.id.tv_activitySetOrgData);

        mInviteMember = (TextView)this.findViewById(R.id.tv_activityDoctorsUnion_inviteMember);

        LinearLayoutManager manager = new LinearLayoutManager(DoctorsUnionActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MyUnionMemberListAdapter(DoctorsUnionActivity.this, mProvideViewUnionDoctorMemberDetailInfos);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if (mLoadDate){
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if(lastVisiblePosition >= manager.getItemCount() - 1) {
                            mPageNum++;
                            getDate();
                        }
                    }
                }
            }
        });

        mAdapter.setOnSettingItemClickListener(new MyUnionMemberListAdapter.OnItemSettingClickListener() {
            @Override
            public void onClick(int position) {
                //设置
                startActivity(new Intent(mContext,DoctorsUnionSettingActivity.class).putExtra("doctorInfo",mProvideViewUnionDoctorMemberDetailInfos.get(position))
                .putExtra("unionCode",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode())
                        .putExtra("unionName",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionName()));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }



    private void initListener(){

        llJoinUnion.setOnClickListener(this);
        llBack.setOnClickListener(this);
        llCreateUnion.setOnClickListener(this);
        llReview.setOnClickListener(this);
        mSWichUnion.setOnClickListener(this);
        mExitUnion.setOnClickListener(this);
        mOrgDate.setOnClickListener(this);
        mInviteMember.setOnClickListener(this);
        mLMMessage.setOnClickListener(this);
        mUnionInfoLayout.setOnClickListener(this);
    }

    private void initData() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_join_union:
                startActivity(new Intent(this, JoinDoctorsUnionActivity.class));
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_create_union:
                startActivity(new Intent(this, CreateUnionActivity.class));
                break;
            case R.id.ll_review:
                startActivity(new Intent(this, ReviewActivity.class));
                break;
            case R.id.ll_message:
                startActivity(new Intent(this, UnionMessageActivity.class));
                break;
            case R.id.switchUnionTextView:
                getSwitchUnionDate();
                break;
            case R.id.tv_exitUnion:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("是否确定退出联盟?");
                builder.setNegativeButton("否", null);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exitUnion();
                    }
                });
                builder.show();
                break;
            case R.id.tv_activitySetOrgData:
                startActivity(new Intent(this,DoctorUnionSettingOrgDateActivity.class)
                        .putExtra("unionCode",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode())
                        .putExtra("unionName",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionName()));
                break;

            case R.id.tv_activityDoctorsUnion_inviteMember:
                startActivity(new Intent(this,DoctorUnionInviteMemberActivity.class)
                        .putExtra("unionCode",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode())
                        .putExtra("unionName",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionName()));
                break;
            case R.id.dateView:
                //判断是否有操作权限
                if (mProvideUnionDoctorMember.size() > 0)
                {
                    if (mProvideUnionDoctorMember.get(0).getFlagOperPower() == 1)
                    {
                        startActivity(new Intent(mContext,UpdateUnionActivity.class).putExtra("doctorUnion",mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0)));
                    }
                }
                break;
        }
    }

    /**
     * 退出联盟
     */
    private void exitUnion() {
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewUnionDoctorMemberAndUnionDetailInfo provideViewUnionDoctorMemberAndUnionDetailInfo = new ProvideViewUnionDoctorMemberAndUnionDetailInfo();
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setExitUnionCode(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode());
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setExitUnionName(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode());
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setExitDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setExitDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    String jsonString = JSON.toJSONString(provideViewUnionDoctorMemberAndUnionDetailInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "unionDoctorController/operExitDoctorUnion");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("退出失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
//                    mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos =  JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewUnionDoctorMemberAndUnionDetailInfo.class);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    e.printStackTrace();
                    return;

                }

                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 获取切换联盟数据
     */
    private void getSwitchUnionDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewUnionDoctorMemberAndUnionDetailInfo provideViewUnionDoctorMemberAndUnionDetailInfo = new ProvideViewUnionDoctorMemberAndUnionDetailInfo();
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    String jsonString = JSON.toJSONString(provideViewUnionDoctorMemberAndUnionDetailInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "unionDoctorController/getMyDoctorUnionSwitchShow");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mSwitchProvideViewUnionDoctorMemberAndUnionDetailInfos = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewUnionDoctorMemberAndUnionDetailInfo.class);
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
     * 获取数据
     */
    private void getDate() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewUnionDoctorMemberAndUnionDetailInfo provideViewUnionDoctorMemberAndUnionDetailInfo = new ProvideViewUnionDoctorMemberAndUnionDetailInfo();
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorMemberAndUnionDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    String jsonString = JSON.toJSONString(provideViewUnionDoctorMemberAndUnionDetailInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "unionDoctorController/getMyDoctorUnionMainShow");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mProvideViewUnionDoctorMemberAndUnionDetailInfos = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewUnionDoctorMemberAndUnionDetailInfo.class);

                    //获取联盟权限
                    ProvideUnionDoctorMember provideUnionDoctorMember = new ProvideUnionDoctorMember();
                    provideUnionDoctorMember.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideUnionDoctorMember.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideUnionDoctorMember.setUnionCode(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode());
                    String jsonStringQX = JSON.toJSONString(provideUnionDoctorMember);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+jsonStringQX ,Constant.SERVICEURL+"unionDoctorController/getMyDoctorUnionMemberJurisdiction");
                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mProvideUnionDoctorMember =JSON.parseArray(netRetEntity.getResJsonData(),ProvideUnionDoctorMember.class);
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
     * 获取数据
     */
    private void getUnionMember() {
        //连接网络，获取联盟数据
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    ProvideViewUnionDoctorMemberDetailInfo provideViewUnionDoctorMemberDetailInfo = new ProvideViewUnionDoctorMemberDetailInfo();
                    provideViewUnionDoctorMemberDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorMemberDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewUnionDoctorMemberDetailInfo.setUnionCode(mProvideViewUnionDoctorMemberAndUnionDetailInfos.get(0).getUnionCode());
                    provideViewUnionDoctorMemberDetailInfo.setRowNum(mRowNum);
                    provideViewUnionDoctorMemberDetailInfo.setPageNum(mPageNum);
                    String jsonString = JSON.toJSONString(provideViewUnionDoctorMemberDetailInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+jsonString ,Constant.SERVICEURL+"unionDoctorController/getMyDoctorUnionMemberShow");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mLoadDate = false;
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(4);
                        return;
                    }
                    List<ProvideViewUnionDoctorMemberDetailInfo> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewUnionDoctorMemberDetailInfo.class);
                    mProvideViewUnionDoctorMemberDetailInfos.addAll(list);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(4);
            }
        }.start();
    }


    /**
     *   获取进度条
     *   获取进度条
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
