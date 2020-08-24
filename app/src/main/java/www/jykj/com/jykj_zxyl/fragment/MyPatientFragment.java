package www.jykj.com.jykj_zxyl.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.jykj.bean.OrderMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import entity.patientInfo.ProvideViewPatientInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyPatientActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.jykj.com.jykj_zxyl.adapter.MyPatientRecyclerAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import yyz_exploit.activity.activity.RefuseActivity;
import yyz_exploit.activity.activity.TerminationActivity;
import yyz_exploit.bean.Status;

public class MyPatientFragment extends Fragment {

    private Context mContext;
    private Handler mHandler;
    private MyPatientActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mHZInfoRecycleView;              //患者列表
    private LinearLayoutManager layoutManager;
    private MyPatientRecyclerAdapter mHZGLRecycleAdapter;       //适配器
    private List<ProvideViewPatientLablePunchClockState> mHZEntyties = new ArrayList<>();            //所有数据
    private int mRowNum = 5;                        //分页行数
    private int mPageNum = 1;                       //分页页码

    private LinearLayout mQB;            //全部
    private LinearLayout mQBCut;         //全部下划线
    private LinearLayout mYJ;            //预警
    private LinearLayout mYJCut;         //预警下划线
    private LinearLayout mTX;            //提醒
    private LinearLayout mTXCut;         //提醒下划线
    private LinearLayout mZC;            //正常
    private LinearLayout mZCCut;         //正常下划线
    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;
    private int mSearchStateType = 0;//状态类型.0:全部;1:正常;2:提醒;3:预警
    private boolean loadDate = true;
    private String mNetLoginRetStr;
    private TextView tv_all;
    private TextView tv_remind;
    private TextView tv_warning;
    private TextView tv_normal;
    private Status statusList;
    private NetRetEntity netRetEntity;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_patient, container, false);
        mContext = getContext();
        mActivity = (MyPatientActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNumber();
        mHZEntyties.clear();
        getData(mSearchStateType);
        //     mHZGLRecycleAdapter.notifyDataSetChanged();
    }

    private void getNumber() {
        HashMap<String, String> map = new HashMap<>();
        map.put("rowNum", "10");
        map.put("pageNum", "1");
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("searchDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("searchFlagSigning", "1");

        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "bindingDoctorPatientControlle/searchDoctorManagePatientDataByTotal");
                    Log.e("TAG", "run:  已签约 " + mNetLoginRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(10);
            }
        }.start();

    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        //全部
        tv_all = view.findViewById(R.id.tv_All);
        //预警
        tv_warning = view.findViewById(R.id.tv_Warning);
        //提醒
        tv_remind = view.findViewById(R.id.tv_remind);
        //正常
        tv_normal = view.findViewById(R.id.tv_normal);
        mHZInfoRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmethzgl_hzinfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mHZInfoRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHZInfoRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGLRecycleAdapter = new MyPatientRecyclerAdapter(mHZEntyties, mContext);
        mHZInfoRecycleView.setAdapter(mHZGLRecycleAdapter);

        //患者资料点击事件
        mHZGLRecycleAdapter.setOnItemClickListener(new MyPatientRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("patientInfo", mHZEntyties.get(position));
                intent.setClass(mContext, HZGLHZZLActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //解约点击事件
        mHZGLRecycleAdapter.setOnYYItemClickListener(new MyPatientRecyclerAdapter.OnYYItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.e("TAG", "点击"+mHZEntyties.get(position).getSignStatus() );
                if (TextUtils.isEmpty(mHZEntyties.get(position).getSignStatus())) {
                    Toast.makeText(mContext, "暂无订单", Toast.LENGTH_SHORT).show();
                } else if (mHZEntyties.get(position).getSignStatus().equals("140")) {
                    agree(position);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, TerminationActivity.class);
                    intent.putExtra("patientLable", mHZEntyties.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        //血压点击事件
        mHZGLRecycleAdapter.setOnXYItemClickListener(new MyPatientRecyclerAdapter.OnXYItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("patientInfo", mHZEntyties.get(position));
                intent.setClass(mContext, HZGLHZZLActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //提醒患者点击事件
        mHZGLRecycleAdapter.setOnTXHZItemClickListener(new MyPatientRecyclerAdapter.OnTXHZItemClickListener() {
            @Override
            public void onClick(int position) {
                if(TextUtils.isEmpty(mHZEntyties.get(position).getSignStatus())){
                    Intent intent = new Intent();
                    intent.setClass(mContext, HZGLTXHZActivity.class);
                    intent.putExtra("patientLable", mHZEntyties.get(position));
                    startActivity(intent);
                    return;
                }
                if (mHZEntyties.get(position).getSignStatus().equals("140")) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, RefuseActivity.class);
                    intent.putExtra("patientLable", mHZEntyties.get(position));
                    startActivity(intent);
                } else if (mHZEntyties.get(position).getSignStatus().equals("150")) {
                    Revoke(position);
                    Log.e("TAG", "撤销: "+"走了" );
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, HZGLTXHZActivity.class);
                    intent.putExtra("patientLable", mHZEntyties.get(position));
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mQB = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_qb);
        mQBCut = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_qbCut);
        mYJ = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_yj);
        mYJCut = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_yjCut);
        mTX = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_tx);
        mTXCut = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_txCut);
        mZC = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_zc);
        mZCCut = (LinearLayout) view.findViewById(R.id.li_fragmentHZGL_zcCut);


        mQB.setOnClickListener(new ButtonClick());
        mYJ.setOnClickListener(new ButtonClick());
        mTX.setOnClickListener(new ButtonClick());
        mZC.setOnClickListener(new ButtonClick());

    }

    //同意
    private void agree(int position) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("mainDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("mainDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        map.put("signCode", mHZEntyties.get(position).getSignCode());
        map.put("signNo", mHZEntyties.get(position).getSignNo());
        map.put("confimresult","1");
        map.put("mainPatientCode", mHZEntyties.get(position).getPatientCode());
        map.put("mainUserName", mHZEntyties.get(position).getUserName());
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run() {
                try {
                    String mNetRetStr = "";
                    mNetRetStr = com.hyphenate.easeui.netService.HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), com.hyphenate.easeui.hyhd.model.Constant.SERVICEURL + "doctorSignControlle/operTerminationConfim");
                    Log.e("tag", "同意" + mNetRetStr);
                    NetRetEntity netRetEntity1 = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity1.getResCode() == 1) {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        //患者
                        intent.putExtra("userCode", mHZEntyties.get(position).getPatientCode());
                        intent.putExtra("userName", mHZEntyties.get(position).getUserName());
                        //医生
                        intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                        //URL
                        intent.putExtra("doctorUrl", mHZEntyties.get(position).getUserLogoUrl());
                        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
                        intent.putExtra("patientCode", mHZEntyties.get(position).getPatientCode());
                        intent.putExtra("patientSex", mHZEntyties.get(position).getGender());
                        if (mHZEntyties.get(position).getBirthday() == 0) {
                            Toast.makeText(mContext, netRetEntity1.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            intent.putExtra("patientAge", DateUtils.getDateToString(mHZEntyties.get(position).getBirthday())
                            );
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderMsg", new OrderMessage(mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(), mHZEntyties.get(position).getSignCode(), getMonitorTypeSize(mHZEntyties.get(position).getSignOtherServiceCode()) + "项", mHZEntyties.get(position).getDetectRate() + "天/" + mHZEntyties.get(position).getDetectRateUnitCode() + mHZEntyties.get(position).getDetectRateUnitName(), mHZEntyties.get(position).getSignDuration() +"个"+ mHZEntyties.get(position).getSignDurationUnit(), mHZEntyties.get(position).getSignPrice() + "", mHZEntyties.get(position).getSignNo(), "1", "terminationOrder", mHZEntyties.get(position).getPatientCode()));
                        //   EventBus.getDefault().post(bundle);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(mContext, netRetEntity1.getResMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, netRetEntity1.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    com.hyphenate.easeui.netService.entity.NetRetEntity retEntity = new com.hyphenate.easeui.netService.entity.NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                mHandler.sendEmptyMessage(30);
            }
        }.start();
    }

    //撤销解约
    private void Revoke(int position) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("mainDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("mainDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        map.put("signCode", mHZEntyties.get(position).getSignCode());
        map.put("signNo", mHZEntyties.get(position).getSignNo());
        map.put("mainPatientCode", mHZEntyties.get(position).getPatientCode());
        map.put("mainUserName", mHZEntyties.get(position).getUserName());
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = com.hyphenate.easeui.netService.HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), com.hyphenate.easeui.hyhd.model.Constant.SERVICEURL + "doctorSignControlle/operTerminationRevoke");
                    Log.e("tag", "撤销解约" + mNetRetStr);
                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
            //        if (mNetRetStr != null) {
                        if (netRetEntity.getResCode() == 1) {

                          //  Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "撤銷成功 " );
                            Intent intent = new Intent(getContext(), ChatActivity.class);
                            //患者
                            intent.putExtra("userCode", mHZEntyties.get(position).getPatientCode());
                            intent.putExtra("userName", mHZEntyties.get(position).getUserName());
                            //医生
                            intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                            intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                            //URL
                            intent.putExtra("doctorUrl", mHZEntyties.get(position).getUserLogoUrl());
                            //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
                            intent.putExtra("patientCode", mHZEntyties.get(position).getPatientCode());
                            intent.putExtra("patientSex", mHZEntyties.get(position).getGender());
//                            if (mHZEntyties.get(position).getBirthday() == 0) {
//                                Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                intent.putExtra("patientAge", DateUtils.getDateToString(mHZEntyties.get(position).getBirthday())
//                                );
//                            }
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderMsg", new OrderMessage(mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(), mHZEntyties.get(position).getSignCode(), getMonitorTypeSize(mHZEntyties.get(position).getSignOtherServiceCode()) + "项", "1次/" + mHZEntyties.get(position).getDetectRateUnitCode() + mHZEntyties.get(position).getDetectRateUnitName(), mHZEntyties.get(position).getSignDuration() +"个"+ mHZEntyties.get(position).getSignDurationUnit(), mHZEntyties.get(position).getSignPrice() + "", mHZEntyties.get(position).getSignNo(), "3", "terminationOrder", mHZEntyties.get(position).getPatientCode()));
                            intent.putExtras(bundle);
                            startActivity(intent);
                            mHZGLRecycleAdapter.notifyDataSetChanged();
                        } else {
                          //  Toast.makeText(mContext, ""+netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                 //   }

                } catch (Exception e) {
                    com.hyphenate.easeui.netService.entity.NetRetEntity retEntity = new com.hyphenate.easeui.netService.entity.NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                mHandler.sendEmptyMessage(20);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void handleMessage(Message msg) {
                String result = "";
                NetRetEntity netRetEntity;
                Bundle data = msg.getData();
                if (data != null) {
                    result = data.getString("result");
                }

                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        if (result != null && !result.equals("")) {
                            netRetEntity = new Gson().fromJson(result, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                List<ProvideViewPatientLablePunchClockState> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewPatientLablePunchClockState.class);
                                mHZEntyties.addAll(list);
                                mHZGLRecycleAdapter.setDate(mHZEntyties);
                                mHZGLRecycleAdapter.notifyDataSetChanged();
                            } else {

                            }
                        }

                        break;
                    case 2:
                        cacerProgress();
                        if (mHZEntyties != null) {
                            mHZGLRecycleAdapter.setDate(mHZEntyties);
                            mHZGLRecycleAdapter.notifyDataSetChanged();
                        } else {

                        }

                        break;
                    case 3:
                        cacerProgress();
                        if (mHZEntyties != null) {
                            mHZGLRecycleAdapter.setDate(mHZEntyties);
                            mHZGLRecycleAdapter.notifyDataSetChanged();
                        } else {

                        }

                        break;
                    case 4:
                        cacerProgress();
                        if (mHZEntyties != null) {
                            mHZGLRecycleAdapter.setDate(mHZEntyties);
                            mHZGLRecycleAdapter.notifyDataSetChanged();
                        } else {

                        }
                        break;
                    case 10:
                        if (!TextUtils.isEmpty(mNetLoginRetStr)) {
                            netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            statusList = JSON.parseObject(netRetEntity.getResJsonData(), Status.class);
                            if (statusList != null) {
                                if (statusList.getStateType_0().equals("")) {

                                } else {
                                    tv_all.setText("全部" + "(" + statusList.getStateType_0() + ")");
                                }
                                if (statusList.getStateType_3().equals("")) {

                                } else {
                                    tv_warning.setText("预警" + "(" + statusList.getStateType_3() + ")");

                                }
                                if (statusList.getStateType_2().equals("")) {

                                } else {
                                    tv_remind.setText("提醒" + "(" + statusList.getStateType_2() + ")");

                                }
                                if (statusList.getStateType_1().equals("")) {

                                } else {
                                    tv_normal.setText("正常" + "(" + statusList.getStateType_1() + ")");

                                }
                            }

                        } else {

                        }

                        break;
                    case 20:

                        break;
                    case 30:

                        break;
                }
            }
        };
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_fragmentHZGL_qb:
                    cutDefault();
                    mQBCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 0;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    //      mHandler.sendEmptyMessage(1);
                    break;
                case R.id.li_fragmentHZGL_yj:
                    cutDefault();
                    mYJCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 3;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    //      mHandler.sendEmptyMessage(2);
                    break;
                case R.id.li_fragmentHZGL_tx:
                    cutDefault();
                    mTXCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 2;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    //        mHandler.sendEmptyMessage(3);
                    break;
                case R.id.li_fragmentHZGL_zc:
                    cutDefault();
                    mZCCut.setVisibility(View.VISIBLE);
                    mSearchStateType = 1;
                    mHZEntyties.clear();
                    getData(mSearchStateType);
                    //        mHandler.sendEmptyMessage(4);
                    break;

            }
        }
    }


    private void getData(int searchStateType) {

        getProgressBar("请稍候...", "正在获取数据");
        new Thread() {
            public void run() {
                String netResultJson = "";
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setSearchStateType(searchStateType);
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    netResultJson = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "bindingDoctorPatientControlle/searchDoctorManagePatientDataByParam");
                    Log.e("tag", "全部患者 " + mNetRetStr);
                } catch (Exception e) {
                    loadDate = false;
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    netResultJson = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("result", netResultJson);
                message.setData(bundle);
                mHandler.sendMessage(message);
                //   mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void cutDefault() {
        mQBCut.setVisibility(View.GONE);
        mYJCut.setVisibility(View.GONE);
        mTXCut.setVisibility(View.GONE);
        mZCCut.setVisibility(View.GONE);
    }


    /**
     * 获取进度条
     * 获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(getActivity());
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

    /**
     * 敬请期待
     */
    private void showStaySunedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getActivity(), R.layout.dialog_stay_tuned, null);
        dialog.setView(dialogView);
        dialog.show();
    }

    /**
     * 获取监测类型项目数量
     * @param monitorType 监测类型
     * @return size
     */
    private int getMonitorTypeSize(String monitorType){
        if (!TextUtils.isEmpty(monitorType)) {
                String[] split = monitorType.split(",");
            return split.length;
        }
        return 0;
    }

}
