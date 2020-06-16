package www.jykj.com.jykj_zxyl.fragment.hzgl;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvidePatientConditionBloodPressureGroup;
import entity.patientInfo.ProvidePatientConditionBloodPressureRecord;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.BloodLogListAdapter;
import www.jykj.com.jykj_zxyl.adapter.BloodLogListDetailAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.DialogUtil;

/**
 * 血压日志
 */
public class BloodLogFragment extends Fragment {
    private RecyclerView mRcyclerview;
    private BloodLogListAdapter mAdapter;
    private View view;
    private ProvideViewPatientLablePunchClockState mData;
    private TextView mPatientUser;
    private List<ProvidePatientConditionBloodPressureGroup> pressureGroupsList;
    private int mRowNum = 10;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private DialogUtil dialogUtil;
    private boolean mLoadDate = true;                    //是否可以加载数据
    private boolean isShow;
    List<ProvidePatientConditionBloodPressureRecord> recordList = new ArrayList<>();
    private BloodLogListDetailAdapter bloodLogListDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bloodlog, container, false);
        mApp = (JYKJApplication) getActivity().getApplication();
        initView();
        initHandler();
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initView() {
        mRcyclerview = view.findViewById(R.id.log_list);
    }

    private void initData() {
        if (isAdded()) {
            mData = (ProvideViewPatientLablePunchClockState) getArguments().getSerializable("patientLable");
        }

        pressureGroupsList = new ArrayList<>();
        setData();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRcyclerview.setLayoutManager(manager);
        mAdapter = new BloodLogListAdapter(pressureGroupsList, getActivity());
        mRcyclerview.setAdapter(mAdapter);

        mRcyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            mPageNum++;
                            setData();
                        }
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new BloodLogListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, BloodLogListAdapter.ViewHolder holder) {
                ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                LinearLayout llDetails = holder.itemView.findViewById(R.id.ll_details);
                RecyclerView rv = holder.itemView.findViewById(R.id.rv_log_list);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(manager);
                bloodLogListDetailAdapter = new BloodLogListDetailAdapter(recordList, getActivity());
                rv.setAdapter(bloodLogListDetailAdapter);
                ProvidePatientConditionBloodPressureGroup data = pressureGroupsList.get(position);
                if (isShow) {
                    isShow = false;
                    ivArrow.setImageResource(R.mipmap.by_1);
                    llDetails.setVisibility(View.VISIBLE);
                    getLogDetail(data);
                } else {
                    isShow = true;
                    ivArrow.setImageResource(R.mipmap.by_2);
                    llDetails.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setData() {
        getLogTitle();
    }

    private void getLogTitle() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(getActivity());
        } else {
            dialogUtil.show();
        }
        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResBloodPressureLogTitle");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        if (dialogUtil.isShow()) {
                            dialogUtil.dismiss();
                        }
                        mLoadDate = false;
                        return;
                    }
                    List<ProvidePatientConditionBloodPressureGroup> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureGroup.class);
                    pressureGroupsList.addAll(list);
                    if (dialogUtil.isShow()) {
                        dialogUtil.dismiss();
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    if (dialogUtil.isShow()) {
                        dialogUtil.dismiss();
                    }
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();


    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mAdapter.setDate(pressureGroupsList);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        bloodLogListDetailAdapter.setDate(recordList);
                        bloodLogListDetailAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void getLogDetail(ProvidePatientConditionBloodPressureGroup data) {
        recordList.clear();
        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewPatientInfo.setPageNum(1);
                    provideViewPatientInfo.setRowNum(100);
                    provideViewPatientInfo.setSearchRecordDate(data.getGroupRecordDate());
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResBloodPressureLogList");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                    List<ProvidePatientConditionBloodPressureRecord> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureRecord.class);
                    recordList.addAll(list);

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
