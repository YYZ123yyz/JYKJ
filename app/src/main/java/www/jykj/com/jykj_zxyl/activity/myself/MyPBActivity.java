package www.jykj.com.jykj_zxyl.activity.myself;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.DoctorInfo.InteractDoctorDetailInfo;
import entity.mySelf.ProvideDoctorSetScheduling;
import entity.mySelf.doctorScheduling.CommitDoctorPBParment;
import entity.mySelf.doctorScheduling.DoctorSetScheduling;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.adapter.MyPBRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的排班
 */
public class MyPBActivity extends AppCompatActivity {


    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;


    private Context mContext;
    private MyPBActivity mActivity;
    private LinearLayout mYSurplus;                  //我的余额
    private RecyclerView mPBRecycleView;             //排班列表
    private LinearLayoutManager layoutManager;
    private MyPBRecycleAdapter mMyPBRecycleAdapter;       //适配器

    private List<DoctorSetScheduling> mPbInfos = new ArrayList<>(); ;

    //    private List<DoctorSetScheduling> PbInfos = new ArrayList<>();
    private ProvideDoctorSetScheduling mProvideDoctorSetScheduling = new ProvideDoctorSetScheduling();

    private JYKJApplication mApp;
    private String mNetRetStr;                 //返回字符串
    private LinearLayout mBack;                      //返回
    private TextView mCommit;                    //提交

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_mypb);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
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
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();

                        break;
                    case 3:
                        cacerProgress();
                        mMyPBRecycleAdapter.setDate(mPbInfos);
                        mMyPBRecycleAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }


    /**
     * 初始化布局
     */
    private void initLayout() {

        mBack = (LinearLayout) this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());
        mCommit = (TextView) this.findViewById(R.id.tv_activityMyPB_commit);
        mCommit.setOnClickListener(new ButtonClick());
        mPBRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityMyPB_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mPBRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mPBRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
//        DoctorSetScheduling doctorSetScheduling = new DoctorSetScheduling();
//        doctorSetScheduling.setDate(1);
//        DoctorSetScheduling doctorSetScheduling1 = new DoctorSetScheduling();
//        doctorSetScheduling1.setDate(2);
//        DoctorSetScheduling doctorSetScheduling2 = new DoctorSetScheduling();
//        doctorSetScheduling2.setDate(3);
//        DoctorSetScheduling doctorSetScheduling3 = new DoctorSetScheduling();
//        doctorSetScheduling3.setDate(4);
//        DoctorSetScheduling doctorSetScheduling4 = new DoctorSetScheduling();
//        doctorSetScheduling4.setDate(5);
//        DoctorSetScheduling doctorSetScheduling5 = new DoctorSetScheduling();
//        doctorSetScheduling5.setDate(6);
//        DoctorSetScheduling doctorSetScheduling6 = new DoctorSetScheduling();
//        doctorSetScheduling6.setDate(7);
//        mPbInfos.add( doctorSetScheduling);
//        mPbInfos.add(doctorSetScheduling1);
//        mPbInfos.add(doctorSetScheduling2);
//        mPbInfos.add(doctorSetScheduling3);
//        mPbInfos.add(doctorSetScheduling4);
//        mPbInfos.add(doctorSetScheduling5);
//        mPbInfos.add(doctorSetScheduling6);
        Log.e("tag",mPbInfos.size()+"");
        mMyPBRecycleAdapter = new MyPBRecycleAdapter(mPbInfos, mContext, mActivity);
        mPBRecycleView.setAdapter(mMyPBRecycleAdapter);

        mMyPBRecycleAdapter.setOnItemMoningClickListener(new MyPBRecycleAdapter.OnItemMoningClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        if (mProvideDoctorSetScheduling.getMondayMorning() == 0)
                            mProvideDoctorSetScheduling.setMondayMorning(1);
                        else if (mProvideDoctorSetScheduling.getMondayMorning() == 1)
                            mProvideDoctorSetScheduling.setMondayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 1:
                        if (mProvideDoctorSetScheduling.getTuesdayMorning() == 0)
                            mProvideDoctorSetScheduling.setTuesdayMorning(1);
                        else if (mProvideDoctorSetScheduling.getTuesdayMorning() == 1)
                            mProvideDoctorSetScheduling.setTuesdayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 2:
                        if (mProvideDoctorSetScheduling.getWednesdayMorning() == 0)
                            mProvideDoctorSetScheduling.setWednesdayMorning(1);
                        else if (mProvideDoctorSetScheduling.getWednesdayMorning() == 1)
                            mProvideDoctorSetScheduling.setWednesdayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 3:
                        if (mProvideDoctorSetScheduling.getThursdayMorning() == 0)
                            mProvideDoctorSetScheduling.setThursdayMorning(1);
                        else if (mProvideDoctorSetScheduling.getThursdayMorning() == 1)
                            mProvideDoctorSetScheduling.setThursdayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 4:
                        if (mProvideDoctorSetScheduling.getFridayMorning() == 0)
                            mProvideDoctorSetScheduling.setFridayMorning(1);
                        else if (mProvideDoctorSetScheduling.getFridayMorning() == 1)
                            mProvideDoctorSetScheduling.setFridayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 5:
                        if (mProvideDoctorSetScheduling.getSaturdayMorning() == 0)
                            mProvideDoctorSetScheduling.setSaturdayMorning(1);
                        else if (mProvideDoctorSetScheduling.getSaturdayMorning() == 1)
                            mProvideDoctorSetScheduling.setSaturdayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;
                    case 6:
                        if (mProvideDoctorSetScheduling.getSundayMorning() == 0)
                            mProvideDoctorSetScheduling.setSundayMorning(1);
                        else if (mProvideDoctorSetScheduling.getSundayMorning() == 1)
                            mProvideDoctorSetScheduling.setSundayMorning(0);
                        if (mPbInfos.get(position).getMorning() == 0)
                            mPbInfos.get(position).setMorning(1);
                        else if (mPbInfos.get(position).getMorning() == 1)
                            mPbInfos.get(position).setMorning(0);
                        break;

                }
                mMyPBRecycleAdapter.setDate(mPbInfos);
                mMyPBRecycleAdapter.notifyDataSetChanged();
            }
        });

        mMyPBRecycleAdapter.setOnItemNoonClickListener(new MyPBRecycleAdapter.OnItemNoonClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        if (mProvideDoctorSetScheduling.getMondayNoon() == 0)
                            mProvideDoctorSetScheduling.setMondayNoon(1);
                        else if (mProvideDoctorSetScheduling.getMondayNoon() == 1)
                            mProvideDoctorSetScheduling.setMondayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 1:
                        if (mProvideDoctorSetScheduling.getTuesdayNoon() == 0)
                            mProvideDoctorSetScheduling.setTuesdayNoon(1);
                        else if (mProvideDoctorSetScheduling.getTuesdayNoon() == 1)
                            mProvideDoctorSetScheduling.setTuesdayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 2:
                        if (mProvideDoctorSetScheduling.getWednesdayNoon() == 0)
                            mProvideDoctorSetScheduling.setWednesdayNoon(1);
                        else if (mProvideDoctorSetScheduling.getWednesdayNoon() == 1)
                            mProvideDoctorSetScheduling.setWednesdayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 3:
                        if (mProvideDoctorSetScheduling.getThursdayNoon() == 0)
                            mProvideDoctorSetScheduling.setThursdayNoon(1);
                        else if (mProvideDoctorSetScheduling.getThursdayNoon() == 1)
                            mProvideDoctorSetScheduling.setThursdayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 4:
                        if (mProvideDoctorSetScheduling.getFridayNoon() == 0)
                            mProvideDoctorSetScheduling.setFridayNoon(1);
                        else if (mProvideDoctorSetScheduling.getFridayNoon() == 1)
                            mProvideDoctorSetScheduling.setFridayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 5:
                        if (mProvideDoctorSetScheduling.getSaturdayNoon() == 0)
                            mProvideDoctorSetScheduling.setSaturdayNoon(1);
                        else if (mProvideDoctorSetScheduling.getSaturdayNoon() == 1)
                            mProvideDoctorSetScheduling.setSaturdayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;
                    case 6:
                        if (mProvideDoctorSetScheduling.getSundayNoon() == 0)
                            mProvideDoctorSetScheduling.setSundayNoon(1);
                        else if (mProvideDoctorSetScheduling.getSundayNoon() == 1)
                            mProvideDoctorSetScheduling.setSundayNoon(0);
                        if (mPbInfos.get(position).getNoon() == 0)
                            mPbInfos.get(position).setNoon(1);
                        else if (mPbInfos.get(position).getNoon() == 1)
                            mPbInfos.get(position).setNoon(0);
                        break;

                }
                mMyPBRecycleAdapter.setDate(mPbInfos);
                mMyPBRecycleAdapter.notifyDataSetChanged();
            }
        });


        mMyPBRecycleAdapter.setOnItemNightClickListener(new MyPBRecycleAdapter.OnItemNightClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        if (mProvideDoctorSetScheduling.getMondayNight() == 0)
                            mProvideDoctorSetScheduling.setMondayNight(1);
                        else if (mProvideDoctorSetScheduling.getMondayNight() == 1)
                            mProvideDoctorSetScheduling.setMondayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 1:
                        if (mProvideDoctorSetScheduling.getTuesdayNight() == 0)
                            mProvideDoctorSetScheduling.setTuesdayNight(1);
                        else if (mProvideDoctorSetScheduling.getTuesdayNight() == 1)
                            mProvideDoctorSetScheduling.setTuesdayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 2:
                        if (mProvideDoctorSetScheduling.getWednesdayNight() == 0)
                            mProvideDoctorSetScheduling.setWednesdayNight(1);
                        else if (mProvideDoctorSetScheduling.getWednesdayNight() == 1)
                            mProvideDoctorSetScheduling.setWednesdayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 3:
                        if (mProvideDoctorSetScheduling.getThursdayNight() == 0)
                            mProvideDoctorSetScheduling.setThursdayNight(1);
                        else if (mProvideDoctorSetScheduling.getThursdayNight() == 1)
                            mProvideDoctorSetScheduling.setThursdayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 4:
                        if (mProvideDoctorSetScheduling.getFridayNight() == 0)
                            mProvideDoctorSetScheduling.setFridayNight(1);
                        else if (mProvideDoctorSetScheduling.getFridayNight() == 1)
                            mProvideDoctorSetScheduling.setFridayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 5:
                        if (mProvideDoctorSetScheduling.getSaturdayNight() == 0)
                            mProvideDoctorSetScheduling.setSaturdayNight(1);
                        else if (mProvideDoctorSetScheduling.getSaturdayNight() == 1)
                            mProvideDoctorSetScheduling.setSaturdayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;
                    case 6:
                        if (mProvideDoctorSetScheduling.getSundayNight() == 0)
                            mProvideDoctorSetScheduling.setSundayNight(1);
                        else if (mProvideDoctorSetScheduling.getSundayNight() == 1)
                            mProvideDoctorSetScheduling.setSundayNight(0);
                        if (mPbInfos.get(position).getNight() == 0)
                            mPbInfos.get(position).setNight(1);
                        else if (mPbInfos.get(position).getNight() == 1)
                            mPbInfos.get(position).setNight(0);
                        break;

                }
                mMyPBRecycleAdapter.setDate(mPbInfos);
                mMyPBRecycleAdapter.notifyDataSetChanged();
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
                case R.id.back:
                    finish();
                    break;
                case R.id.tv_activityMyPB_commit:
                    //提交
                    commit();
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void commit() {
        getProgressBar("请稍候...", "正在提交");
        new Thread() {
            public void run() {
                try {
                    CommitDoctorPBParment commitDoctorPBParment = new CommitDoctorPBParment();
                    commitDoctorPBParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    commitDoctorPBParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    commitDoctorPBParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    commitDoctorPBParment.setSchedulingJsonObject(JSON.toJSONString(mProvideDoctorSetScheduling));
                    String string = JSON.toJSONString(commitDoctorPBParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorPersonalSetControlle/operDoctorSchedulingInfo");

                    finish();
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(4);
            }
        }.start();
    }

    /**
     * 设置数据
     */
    private void getData() {

        getProgressBar("请稍候。。。", "正在获取数据");
        ProvideDoctorSetScheduling provideDoctorSetScheduling = new ProvideDoctorSetScheduling();
        provideDoctorSetScheduling.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideDoctorSetScheduling.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideDoctorSetScheduling.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideDoctorSetScheduling), Constant.SERVICEURL + "doctorPersonalSetControlle/getDoctorSchedulingInfo");

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                if (netRetEntity.getResCode() == 1)
                    mProvideDoctorSetScheduling = JSON.parseObject(netRetEntity.getResJsonData(), ProvideDoctorSetScheduling.class);

                //开始封装数据
                for (int i = 0; i <= 6; i++) {
                    switch (i) {
                        case 0:
                            DoctorSetScheduling doctorSetScheduling = new DoctorSetScheduling();
                            doctorSetScheduling.setDate(i+1);
                            doctorSetScheduling.setMorning(mProvideDoctorSetScheduling.getMondayMorning());
                            doctorSetScheduling.setMorningSourceNum(mProvideDoctorSetScheduling.getMondayMorningSourceNum());
                            doctorSetScheduling.setNoon(mProvideDoctorSetScheduling.getMondayNoon());
                            doctorSetScheduling.setNoonSourceNum(mProvideDoctorSetScheduling.getMondayNoonSourceNum());
                            doctorSetScheduling.setNight(mProvideDoctorSetScheduling.getMondayNight());
                            doctorSetScheduling.setNightSourceNum(mProvideDoctorSetScheduling.getMondayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling);
                            break;
                        case 1:
                            DoctorSetScheduling doctorSetScheduling01 = new DoctorSetScheduling();
                            doctorSetScheduling01.setDate(i+1);
                            doctorSetScheduling01.setMorning(mProvideDoctorSetScheduling.getTuesdayMorning());
                            doctorSetScheduling01.setMorningSourceNum(mProvideDoctorSetScheduling.getTuesdayMorningSourceNum());
                            doctorSetScheduling01.setNoon(mProvideDoctorSetScheduling.getTuesdayNoon());
                            doctorSetScheduling01.setNoonSourceNum(mProvideDoctorSetScheduling.getTuesdayNoonSourceNum());
                            doctorSetScheduling01.setNight(mProvideDoctorSetScheduling.getTuesdayNight());
                            doctorSetScheduling01.setNightSourceNum(mProvideDoctorSetScheduling.getTuesdayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling01);
                            break;
                        case 2:
                            DoctorSetScheduling doctorSetScheduling02 = new DoctorSetScheduling();
                            doctorSetScheduling02.setDate(i+1);
                            doctorSetScheduling02.setMorning(mProvideDoctorSetScheduling.getWednesdayMorning());
                            doctorSetScheduling02.setMorningSourceNum(mProvideDoctorSetScheduling.getWednesdayMorningSourceNum());
                            doctorSetScheduling02.setNoon(mProvideDoctorSetScheduling.getWednesdayNoon());
                            doctorSetScheduling02.setNoonSourceNum(mProvideDoctorSetScheduling.getWednesdayNoonSourceNum());
                            doctorSetScheduling02.setNight(mProvideDoctorSetScheduling.getWednesdayNight());
                            doctorSetScheduling02.setNightSourceNum(mProvideDoctorSetScheduling.getWednesdayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling02);
                            break;
                        case 3:
                            DoctorSetScheduling doctorSetScheduling03 = new DoctorSetScheduling();
                            doctorSetScheduling03.setDate(i+1);
                            doctorSetScheduling03.setMorning(mProvideDoctorSetScheduling.getThursdayMorning());
                            doctorSetScheduling03.setMorningSourceNum(mProvideDoctorSetScheduling.getThursdayMorningSourceNum());
                            doctorSetScheduling03.setNoon(mProvideDoctorSetScheduling.getThursdayNoon());
                            doctorSetScheduling03.setNoonSourceNum(mProvideDoctorSetScheduling.getThursdayNoonSourceNum());
                            doctorSetScheduling03.setNight(mProvideDoctorSetScheduling.getThursdayNight());
                            doctorSetScheduling03.setNightSourceNum(mProvideDoctorSetScheduling.getThursdayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling03);
                            break;
                        case 4:
                            DoctorSetScheduling doctorSetScheduling04 = new DoctorSetScheduling();
                            doctorSetScheduling04.setDate(i+1);
                            doctorSetScheduling04.setMorning(mProvideDoctorSetScheduling.getFridayMorning());
                            doctorSetScheduling04.setMorningSourceNum(mProvideDoctorSetScheduling.getFridayMorningSourceNum());
                            doctorSetScheduling04.setNoon(mProvideDoctorSetScheduling.getFridayNoon());
                            doctorSetScheduling04.setNoonSourceNum(mProvideDoctorSetScheduling.getFridayNoonSourceNum());
                            doctorSetScheduling04.setNight(mProvideDoctorSetScheduling.getFridayNight());
                            doctorSetScheduling04.setNightSourceNum(mProvideDoctorSetScheduling.getFridayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling04);
                            break;
                        case 5:
                            DoctorSetScheduling doctorSetScheduling05 = new DoctorSetScheduling();
                            doctorSetScheduling05.setDate(i+1);
                            doctorSetScheduling05.setMorning(mProvideDoctorSetScheduling.getSaturdayMorning());
                            doctorSetScheduling05.setMorningSourceNum(mProvideDoctorSetScheduling.getSaturdayMorningSourceNum());
                            doctorSetScheduling05.setNoon(mProvideDoctorSetScheduling.getSaturdayNoon());
                            doctorSetScheduling05.setNoonSourceNum(mProvideDoctorSetScheduling.getSaturdayNoonSourceNum());
                            doctorSetScheduling05.setNight(mProvideDoctorSetScheduling.getSaturdayNight());
                            doctorSetScheduling05.setNightSourceNum(mProvideDoctorSetScheduling.getSaturdayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling05);
                            break;
                        case 6:
                            DoctorSetScheduling doctorSetScheduling06 = new DoctorSetScheduling();
                            doctorSetScheduling06.setDate(i+1);
                            doctorSetScheduling06.setMorning(mProvideDoctorSetScheduling.getSundayMorning());
                            doctorSetScheduling06.setMorningSourceNum(mProvideDoctorSetScheduling.getSundayMorningSourceNum());
                            doctorSetScheduling06.setNoon(mProvideDoctorSetScheduling.getSundayNoon());
                            doctorSetScheduling06.setNoonSourceNum(mProvideDoctorSetScheduling.getSundayNoonSourceNum());
                            doctorSetScheduling06.setNight(mProvideDoctorSetScheduling.getSundayNight());
                            doctorSetScheduling06.setNightSourceNum(mProvideDoctorSetScheduling.getSundayNightSourceNum());
                            mPbInfos.add(doctorSetScheduling06);
                            break;
                    }
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
