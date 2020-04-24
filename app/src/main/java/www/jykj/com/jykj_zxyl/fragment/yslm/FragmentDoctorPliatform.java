package www.jykj.com.jykj_zxyl.fragment.yslm;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.DoctorInfo.GetDoctorBindParment;
import entity.DoctorInfo.ProvideViewBindingDdGetBindingDoctor;
import entity.basicDate.GetUnionNameParment;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideUnionDoctor;
import entity.unionInfo.ProvideViewSysUserDoctorInfoAndHospital;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.DoctorUnionInviteMemberActivity;
import www.jykj.com.jykj_zxyl.adapter.InviteMemberListAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.FullyLinearLayoutManager;
import www.jykj.com.jykj_zxyl.util.ProvincePicker;

/**
 * 邀请加入，平台医生
 */
public class FragmentDoctorPliatform extends Fragment {
    private LinearLayout llBack;



    public          ProgressDialog              mDialogProgress =null;

    private         Context                     mContext;                                       //
    private         DoctorUnionInviteMemberActivity mActivity;
    private         JYKJApplication             mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         Handler                     mHandler;

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条

    public GetDoctorBindParment mGetDoctorBindParment;              //获取列表的参数


    private         List<ProvideViewBindingDdGetBindingDoctor> mDoctorPlatformList = new ArrayList<>();                 //获取到的平台医生列表

    private                 RecyclerView                mRecycleView;

    private          List<ProvideViewBindingDdGetBindingDoctor> doctorInfos = new ArrayList<>();                    //展示得医生数据
    private             InviteMemberListAdapter mAdapter;
    private                 int                         mShowModel = 1;                                 //当前显示模式 1=医生好友  2=平台医生  3=搜索列表



    private             TextView mSearch;                            //搜索按钮
    private             LinearLayout                    mRegionLayout;                      //选择地区布局
    private             LinearLayout                    mHospitalLayout;                      //选择医院布局
    private             LinearLayout                    mDepartmentLayout;                      //选择一级科室布局
    private             LinearLayout                    mDepartmentSecondLayout;                      //选择二级科室布局

    private                 ProvincePicker mPicker;                                            //省市县三级联动选择框
    public                  Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map

    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                      mChoiceRegionID;                                       //选择的区域ID
    private                 List<ProvideHospitalInfo>   mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private                 String[]                    mProvideHospitalNameInfos;                              //医院对应的名称列表

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentFInfos = new ArrayList<>();              //获取到的医院一级科室列表
    private                 String[]                        mProvideHospitalDepartmentFNameInfos;                              //医院一级科室对应的名称列表

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentSInfos = new ArrayList<>();              //获取到的医院二级科室列表
    private                 String[]                    mProvideHospitalDepartmentSNameInfos;                              //医院二级科室对应的名称列表
    private ProvideUnionDoctor mProvideUnionDoctor;
    private GetUnionNameParment getUnionNameParment;            //获取医生联盟名称参数
    private                 int                             mChoiceHospitalIndex;           //选择的医院下标

    private                 TextView                        mChoiceRegionText;              //地区选择Text
    private                 TextView                        mChoiceHospitalText;              //医院选择Text
    private                 TextView                        mChoiceDepartmentText;              //科室选择Text
    private                 TextView                        mChoiceDepartmentSecondText;              //二级科室选择Text

    private                 TextView                        mSearchButton;                      //搜索按钮
    private                 EditText                        mSearchEdit;                        //搜索内容


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activityinvite_doctorplatform, container, false);
        mContext = getContext();
        mActivity = (DoctorUnionInviteMemberActivity) getActivity();
        mApp = (JYKJApplication) mContext.getApplicationContext();
        mGetDoctorBindParment = new GetDoctorBindParment();
        initView(v);
        initHandler();
        //加载数据
        getDate();
        return v;
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 0)
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                mAdapter.setData(mDoctorPlatformList);
                                mAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    private void initView(View v){


        mRegionLayout = (LinearLayout)v.findViewById(R.id.li_activityCreateUnion_choiceRegion);
        mHospitalLayout = (LinearLayout)v.findViewById(R.id.li_activityCreateUnion_choiceHospitalLayout);
        mDepartmentLayout = (LinearLayout)v.findViewById(R.id.li_activityCreateUnion_choiceHospitalDepartmentF);
        mDepartmentSecondLayout = (LinearLayout)v.findViewById(R.id.li_activityCreateUnion_choiceHospitalDepartmentS);
        mRegionLayout.setOnClickListener(new ButtonClick());
        mHospitalLayout.setOnClickListener(new ButtonClick());
        mDepartmentLayout.setOnClickListener(new ButtonClick());
        mDepartmentSecondLayout.setOnClickListener(new ButtonClick());

        mChoiceRegionText = (TextView)v.findViewById(R.id.tv_activityCreateUnion_choiceRegionText);
        mChoiceHospitalText = (TextView)v.findViewById(R.id.tv_activityCreateUnion_choiceHostpitalText);
        mChoiceDepartmentText = (TextView)v.findViewById(R.id.tv_activityCreateUnion_choiceHospitalDepartmentF);
        mChoiceDepartmentSecondText = (TextView)v.findViewById(R.id.tv_activityCreateUnion_choiceHospitalDepartmentS);

        mSearch = (TextView)v.findViewById(R.id.tv_search);
        mSearch.setOnClickListener(new ButtonClick());

        mSearchEdit = (EditText)v.findViewById(R.id.et_activityCreateUnion_choiceRegionText);

        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(mContext);

        //创建并设置Adapter

        mRecycleView = (RecyclerView)v.findViewById(R.id.rv_doctorList);
        manager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);

        mRecycleView.setLayoutManager(manager);
        mAdapter = new InviteMemberListAdapter(mContext, doctorInfos);
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1) {
                        mNumPage ++;
                        getDate();
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new InviteMemberListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                    //平台医生
                    mDoctorPlatformList.get(position).setClick(!mDoctorPlatformList.get(position).isClick());
                    mAdapter.setData(mDoctorPlatformList);
                    mAdapter.notifyDataSetChanged();
                    //如果当前是选中状态，则将其加入选中列表中
                    if (mDoctorPlatformList.get(position).isClick())
                        mActivity.mChoiceUser.put(mDoctorPlatformList.get(position).getBindingDoctorCode(),mDoctorPlatformList.get(position));
                    else
                        mActivity.mChoiceUser.remove(mDoctorPlatformList.get(position).getBindingDoctorCode());
                    mActivity.setDoctorFriendState();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    /**
     * 设置选择的区域信息
     * @param choiceRegionMap
     */
    public void setRegionText(Map<String,ProvideBasicsRegion> choiceRegionMap) {
        mChoiceRegionMap = choiceRegionMap;
        mGetDoctorBindParment.setSearchProvince(mChoiceRegionMap.get("provice").getRegion_id());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            mGetDoctorBindParment.setSearchCity("");
            mGetDoctorBindParment.setSearchArea("");
        }
        else if (mChoiceRegionMap.get("dist") == null ||"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            mGetDoctorBindParment.setSearchArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mGetDoctorBindParment.setSearchCity(mChoiceRegionMap.get("city").getRegion_id());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name()+mChoiceRegionMap.get("dist").getRegion_name());
            mGetDoctorBindParment.setSearchArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }

        //获取区域内医院
        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsRegion),Constant.SERVICEURL+"hospitalDataController/getHospitalInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //医院数据获取成功
                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>(){}.getType());

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 设置平台医生选中状态
     */
    public void setChoiceState() {
        for (int i = 0; i < mDoctorPlatformList.size(); i++)
        {
            if (mActivity.mChoiceUser.get(mDoctorPlatformList.get(i).getBindingDoctorCode()) != null)
                mDoctorPlatformList.get(i).setClick(true);
            else
                mDoctorPlatformList.get(i).setClick(false);
        }
        mAdapter.setData(mDoctorPlatformList);
        mAdapter.notifyDataSetChanged();
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_activityCreateUnion_choiceRegion:
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0)
                    {
                        Toast.makeText(mContext,"区域数据为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity,3);
                    mPicker.show();
                    break;
                case R.id.li_activityCreateUnion_choiceHospitalLayout:
                    if (mGetDoctorBindParment.getSearchProvince() == null || "".equals(mGetDoctorBindParment.getSearchProvince()))
                    {
                        Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalView();
                    break;
                case R.id.li_activityCreateUnion_choiceHospitalDepartmentF:
                    if (mGetDoctorBindParment.getSearchProvince() == null || "".equals(mGetDoctorBindParment.getSearchProvince()))
                    {
                        Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mGetDoctorBindParment.getSearchHospitalInfoCode() == null || "".equals(mGetDoctorBindParment.getSearchHospitalInfoCode()))
                    {
                        Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalDepartmentFView();
                    break;
                case R.id.li_activityCreateUnion_choiceHospitalDepartmentS:
                    if (mGetDoctorBindParment.getSearchProvince() == null || "".equals(mGetDoctorBindParment.getSearchProvince()))
                    {
                        Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mGetDoctorBindParment.getSearchHospitalInfoCode() == null || "".equals(mGetDoctorBindParment.getSearchHospitalInfoCode()))
                    {
                        Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mGetDoctorBindParment.getSearchDepartmentId() == null || "".equals(mGetDoctorBindParment.getSearchDepartmentId()))
                    {
                        Toast.makeText(mContext,"请选择一级科室",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalDepartmentSView();
                    break;
                case R.id.tv_search:
                    mDoctorPlatformList.removeAll(mDoctorPlatformList);
                    mNumPage = 1;
                    mRowNum = 10;
                    mGetDoctorBindParment.setSearchDoctorName(mSearchEdit.getText().toString());
                    getDate();
                    break;

            }
        }
    }

    /**
     * 显示科室名称以及获取数据
     * @param index
     */
    private void showChoiceHospitalDepartmentSText(int index) {
        mChoiceDepartmentSecondText.setText(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
        mGetDoctorBindParment.setSearchDepartmentSecondId(mProvideHospitalDepartmentSInfos.get(index).getHospitalDepartmentId()+"");
        System.out.println("");
    }

    /**
     * 选择二级科室
     */
    private void showChoiceHospitalDepartmentSView() {
        if (mProvideHospitalDepartmentSInfos != null)
        {
            mProvideHospitalDepartmentSNameInfos = new String[mProvideHospitalDepartmentSInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentSInfos.size(); i++)
        {
            mProvideHospitalDepartmentSNameInfos[i] = mProvideHospitalDepartmentSInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择二级科室");
        listDialog.setItems(mProvideHospitalDepartmentSNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentSText(which);
            }
        });
        listDialog.show();
    }



    /**
     * 显示科室名称以及获取数据
     * @param index
     */
    private void showChoiceHospitalDepartmentText(int index) {
        mChoiceDepartmentText.setText(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
        mGetDoctorBindParment.setSearchDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId()+"");
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    //获取二级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(mChoiceHospitalIndex).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //二级科室信息获取成功
                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    /**
     * 选择一级科室
     */
    private void showChoiceHospitalDepartmentFView() {
        if (mProvideHospitalDepartmentFInfos != null)
        {
            mProvideHospitalDepartmentFNameInfos = new String[mProvideHospitalDepartmentFInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentFInfos.size(); i++)
        {
            mProvideHospitalDepartmentFNameInfos[i] = mProvideHospitalDepartmentFInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择一级科室");
        listDialog.setItems(mProvideHospitalDepartmentFNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 选择医院对话框
     *
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null)
        {
            mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++)
        {
            mProvideHospitalNameInfos[i] = mProvideHospitalInfos.get(i).getHospitalName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideHospitalNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示医院并获取一级科室
     */
    private void showChoiceHospitalText(int index) {
        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
        mGetDoctorBindParment.setSearchHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
        mChoiceHospitalIndex = index;
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    //获取一级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(0);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //一级科室信息获取成功
                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    /**
     * 加载数据 第一次进入页面，加载全部数据
     * 1：先加载医生好友数据
     * 2：在加载平台医生数据
     */
    public void getDate() {

        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    mGetDoctorBindParment.setRowNum(mRowNum+"");
                    mGetDoctorBindParment.setPageNum(mNumPage+"");
                    mGetDoctorBindParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    mGetDoctorBindParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    mGetDoctorBindParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    //实体转JSON字符串
                    String str = new Gson().toJson(mGetDoctorBindParment);
                    //获取
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(mGetDoctorBindParment),Constant.SERVICEURL+"bindingDoctorControlle/searchPlatformDoctorInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //获取平台医生
                    List<ProvideViewSysUserDoctorInfoAndHospital> listPtDoctor = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideViewSysUserDoctorInfoAndHospital>>(){}.getType());
                    List<ProvideViewBindingDdGetBindingDoctor> listFriend = new ArrayList<>();
                    for (int i = 0; i < listPtDoctor.size(); i++)
                    {
                        ProvideViewBindingDdGetBindingDoctor provideViewBindingDdGetBindingDoctor = new ProvideViewBindingDdGetBindingDoctor();
                        provideViewBindingDdGetBindingDoctor.setBindingDoctorCode(listPtDoctor.get(i).getDoctorCode());
                        provideViewBindingDdGetBindingDoctor.setUserName(listPtDoctor.get(i).getUserName());
                        provideViewBindingDdGetBindingDoctor.setHospitalInfoName(listPtDoctor.get(i).getHospitalInfoName());
                        provideViewBindingDdGetBindingDoctor.setDepartmentName(listPtDoctor.get(i).getDepartmentName());
                        provideViewBindingDdGetBindingDoctor.setDepartmentSecondName(listPtDoctor.get(i).getDepartmentSecondName());
                        provideViewBindingDdGetBindingDoctor.setUserLogoUrl(listPtDoctor.get(i).getUserLogoUrl());
                        provideViewBindingDdGetBindingDoctor.setGender(listPtDoctor.get(i).getGender());
                        listFriend.add(provideViewBindingDdGetBindingDoctor);
                    }
                    mDoctorPlatformList .addAll(listFriend);
                    for (int i = 0; i < mDoctorPlatformList.size(); i++)
                    {
                        if (mActivity.mChoiceUser.get(mDoctorPlatformList.get(i).getBindingDoctorCode()) != null)
                            mDoctorPlatformList.get(i).setClick(true);
                        else
                            mDoctorPlatformList.get(i).setClick(false);
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
