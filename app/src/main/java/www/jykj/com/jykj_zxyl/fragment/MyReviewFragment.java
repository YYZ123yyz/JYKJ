package www.jykj.com.jykj_zxyl.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.patientInfo.ProvideBindingDoctorPatientApply;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientReviewInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.AddPatientActivity;
import www.jykj.com.jykj_zxyl.activity.home.PatientReviewActivity;
import www.jykj.com.jykj_zxyl.adapter.ReviewAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


public class MyReviewFragment extends Fragment implements View.OnClickListener {
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecyclerView;
    private ReviewAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<ProvideBindingDoctorPatientApply> mHZEntyties = new ArrayList<>();            //所有数据
    private int mRowNum = 10;                        //分页行数
    private int mPageNum = 1;
    private String mNetRetStr;
    private TextView tvOneClickReview;
    private RelativeLayout rlBottomView;
    private TextView tvAgree;
    private TextView tvRefuse;
    private List<String> applyIdStr = new ArrayList<>();
    private String applyId;
    private int flagApplyState;
    private TextView tvAddPatient;
    /**
     * 是否显示checkbox
     */
    private boolean isShowCheck;
    /**
     * 记录选中的checkbox
     */
    private List<String> checkList;
    private List<ProvideBindingDoctorPatientApply>canCheckList;//可审核集合


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myreview, container, false);
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getData();
        initListener();
        return v;
    }

    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        tvOneClickReview = view.findViewById(R.id.tv_one_click_review);
        rlBottomView = view.findViewById(R.id.rl_bottom_view);
        tvAgree = view.findViewById(R.id.tv_agree);
        tvRefuse = view.findViewById(R.id.tv_refuse);
        tvAddPatient = view.findViewById(R.id.tv_add_patient);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        checkList = new ArrayList<>();
        canCheckList = new ArrayList<>();
        refreshUI();

    }

    /**
     * 适配器
     */
    private void refreshUI() {
        if (mAdapter == null) {
            mAdapter = new ReviewAdapter(mHZEntyties, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mAdapter.setDate(mHZEntyties);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 0) {
                            Toast.makeText(getActivity(), retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getActivity(), retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        rlBottomView.setVisibility(View.GONE);
                        mAdapter.setShowCheckBox(false);
                        refreshUI();
                        mAdapter.setDate(mHZEntyties);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void getData() {

        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "bindingDoctorPatientControlle/searchApplyBindingDoctorPatientHistoryByParam");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
                    List<ProvideBindingDoctorPatientApply> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBindingDoctorPatientApply.class);
                    mHZEntyties.addAll(list);
                    for (int i = 0;i<list.size();i++){
                        if(list.get(i).getFlagApplyState()==0||list.get(i).getFlagApplyState()==1){
                            canCheckList.addAll(list);
                        }
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

    private void initListener() {
        tvOneClickReview.setOnClickListener(this);
        tvAgree.setOnClickListener(this);
        tvRefuse.setOnClickListener(this);
        tvAddPatient.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new ReviewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (checkList.contains(String.valueOf(position))) {
                    checkList.remove(String.valueOf(position));
                } else {
                    if (mHZEntyties.get(position).getFlagApplyState() == 3 || mHZEntyties.get(position).getFlagApplyState() == 1) {
                        checkList.add(String.valueOf(position));
                    }
                }
                if (!isShowCheck) {
                    if (mHZEntyties.get(position).getFlagApplyState() == 1 || mHZEntyties.get(position).getFlagApplyState() == 0) {
                        Intent intent = new Intent(getActivity(), PatientReviewActivity.class);
                        intent.putExtra("doctorbindingdata", mHZEntyties.get(position));
                        
                        startActivityForResult(intent, 1);
                    }
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            getData();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_agree:
                flagApplyState = 1;
                getApplyIDStr();
                if (checkList.size() == 0) {
                    Toast.makeText(getActivity(), "请选中审核对象", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    review(flagApplyState);
                }

                break;
            case R.id.tv_refuse:
                flagApplyState = 3;
                if (checkList.size() == 0) {
                    Toast.makeText(getActivity(), "请选中审核对象", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    review(flagApplyState);
                }
                break;
            case R.id.tv_one_click_review:
                if (isShowCheck) {
                    rlBottomView.setVisibility(View.GONE);
                    mAdapter.setShowCheckBox(false);
                    refreshUI();
                    checkList.clear();
                } else {
                    if(canCheckList.size()==0){
                        Toast.makeText(getActivity(), "没有可审核对象", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        rlBottomView.setVisibility(View.VISIBLE);
                        mAdapter.setShowCheckBox(true);
                        refreshUI();
                    }
                }
                isShowCheck = !isShowCheck;
                break;
            case R.id.tv_add_patient:
                startActivity(new Intent(getActivity(),AddPatientActivity.class));
                break;
        }
    }

    private String getApplyIDStr() {
        applyIdStr.clear();
        for (int i = 0; i < checkList.size(); i++) {
            applyIdStr.add(mHZEntyties.get(Integer.parseInt(checkList.get(i))).getDpApplyId() + "");
        }
        applyId = listToString(applyIdStr);
        return applyId;
    }

    private String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append("^"); // 分隔符
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    private void review(int flagApplyState) {
        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientReviewInfo provideViewPatientReviewInfo = new ProvideViewPatientReviewInfo();
                    provideViewPatientReviewInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientReviewInfo.setOperDoctorCode(mHZEntyties.get(Integer.parseInt(checkList.get(0))).getInvitationDoctorCode());
                    provideViewPatientReviewInfo.setOperDoctorName(mHZEntyties.get(Integer.parseInt(checkList.get(0))).getInvitationDoctorName());
                    provideViewPatientReviewInfo.setDpApplyIdStr(applyId);
                    provideViewPatientReviewInfo.setFlagApplyState(flagApplyState);
                    provideViewPatientReviewInfo.setRefuseReason("");
                    String jsonString = JSON.toJSONString(provideViewPatientReviewInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "bindingDoctorPatientControlle/operApprovalBindingDoctorPatientList");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

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

}
