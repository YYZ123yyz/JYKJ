package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.GetUnionNameParment;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideUnionDoctor;
import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.DoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.adapter.JoinUnionListAdapter;
import www.jykj.com.jykj_zxyl.adapter.MyCommentAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ProvincePicker;

public class JoinDoctorsUnionActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private JoinUnionListAdapter mAdapter;
    private LinearLayout llBack;

    public          ProgressDialog              mDialogProgress =null;

    private         Context                     mContext;                                       //
    private         JoinDoctorsUnionActivity    mActivity;
    private         JYKJApplication             mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         Handler                     mHandler;

    private         LinearLayout                mChoiceRegion;              //选择区域
    private         TextView                    mChoiceRegionText;                  //选择的地区

    private         LinearLayout                mChoiceHospital;                //选择医院
    private         TextView                    mChoiceHospitalText;                  //选择的地区

    private         EditText                    mSearchEditText;                        //输入框

    private         ProvincePicker              mPicker;                                            //省市县三级联动选择框
    public          Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map
    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                      mChoiceRegionID;                                       //选择的区域ID

    private                 List<ProvideHospitalInfo>   mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private                 String[]                    mProvideHospitalNameInfos;                              //医院对应的名称列表
    private                 ProvideViewUnionDoctorDetailInfo mProvideViewUnionDoctorDetailInfo;
    private                 int                             mChoiceHospitalIndex;           //选择的医院下标

    private                 TextView                    mSearchTextView;                    //搜索

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private                 int                         mSearchModel;                   //搜索模式  1=按条件搜索  其他=扫码搜索
    private                 List<ProvideViewUnionDoctorDetailInfo>          mUnionList = new ArrayList<>();

    private                 boolean                     loadDate = true;               //是否加载数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_union);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewUnionDoctorDetailInfo = new ProvideViewUnionDoctorDetailInfo();
        initView();
        initHandler();
        //获取所有联盟
        mNumPage = 1;
        mSearchModel = 1;
        mUnionList.removeAll(mUnionList);
        searchUnion();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        mAdapter.setDate(mUnionList);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAdapter.setDate(mUnionList);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        mAdapter.setDate(mUnionList);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new JoinUnionListAdapter(mContext,mUnionList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1) {
                        if (loadDate)
                        {
                            mNumPage ++;
                            searchUnion();
                        }

                    }
                }
            }
        });



        mChoiceRegion = (LinearLayout)this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceRegionLayout);
        mChoiceRegionText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceRegionText);

        mChoiceHospital = (LinearLayout)this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceHospitalLayout);
        mChoiceHospitalText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceHospitalText);
        mSearchTextView = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_searchText);

        mSearchEditText = (EditText)this.findViewById(R.id.et_search);

        mChoiceRegion.setOnClickListener(new ButtonClick());
        mChoiceHospital.setOnClickListener(new ButtonClick());
        llBack.setOnClickListener(new ButtonClick());
        mSearchTextView.setOnClickListener(new ButtonClick());

        mAdapter.setOnItemClickListener(new JoinUnionListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //加入联盟
                alert_edit(position);
//                joinUnion(position);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    /**
     *
     * 填写申请说明
     * @param
     */
    public void alert_edit(int index){
        final EditText et = new EditText(this);
        et.setHeight(500);
        et.setBackgroundResource(R.mipmap.bg_7);
        et.setPadding(50,50,50,50);
        et.setHint("请填写申请说明");
        new AlertDialog.Builder(this)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        joinUnion(index,et.getText().toString());
                    }
                }).setNegativeButton("取消",null).show();
    }


    /**
     *
     * @param position
     */
    private void joinUnion(int position,String joinReason) {
        getProgressBar("请稍候。。。。","正在提交");
        new Thread(){
            public void run(){
                try {
//                    ProvideViewUnionDoctorDetailInfo provideViewUnionDoctorDetailInfo = mUnionList.get(position);
                    ProvideViewUnionDoctorDetailInfo provideViewUnionDoctorDetailInfo = new ProvideViewUnionDoctorDetailInfo();
                    provideViewUnionDoctorDetailInfo.setUnionCode(mUnionList.get(position).getUnionCode());
//                    provideViewUnionDoctorDetailInfo.setuni
                    provideViewUnionDoctorDetailInfo.setApplyReason(joinReason);
                    provideViewUnionDoctorDetailInfo.setLoginDoctorPosition("20.1545^56.145454");
                    provideViewUnionDoctorDetailInfo.setApplyFlagDoctorStatus(mApp.mViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus()+"");
                    provideViewUnionDoctorDetailInfo.setApplyDoctorTitleName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorTitleName());

                    provideViewUnionDoctorDetailInfo.setUnionDoctorCode(mUnionList.get(position).getApplyDoctorCode());
                    provideViewUnionDoctorDetailInfo.setUnionDoctorName(mUnionList.get(position).getUserName());
                    provideViewUnionDoctorDetailInfo.setApplyDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewUnionDoctorDetailInfo.setApplyDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    //实体转JSON字符串
                    String str = new Gson().toJson(provideViewUnionDoctorDetailInfo);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"unionDoctorController/operApplyDoctorUnion");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("申请加入失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    //申请加入成功
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
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


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.li_activityJoinDoctorsUnion_choiceRegionLayout:
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0)
                    {
                        Toast.makeText(mContext,"区域数据为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity,2);
                    mPicker.show();
                    break;

                case R.id.li_activityJoinDoctorsUnion_choiceHospitalLayout:
                    if (mProvideViewUnionDoctorDetailInfo.getProvince() == null || "".equals(mProvideViewUnionDoctorDetailInfo.getProvince()))
                    {
                        Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalView();
                    break;
                case R.id.tv_activityJoinDoctorsUnion_searchText:
                    mNumPage = 1;
                    mSearchModel = 1;
                    mUnionList.removeAll(mUnionList);
                    loadDate = true;
                    searchUnion();
                    break;

            }
        }
    }

    /**
     * 搜索
     */
    private void searchUnion() {
        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    //搜索模式
                    if (mSearchModel == 1)
                    {
                        mProvideViewUnionDoctorDetailInfo.setUnionName(mSearchEditText.getText().toString());
                    }
                    else
                    {
                        mProvideViewUnionDoctorDetailInfo.setProvince("");
                        mProvideViewUnionDoctorDetailInfo.setCity("");
                        mProvideViewUnionDoctorDetailInfo.setArea("");
                    }

                    mProvideViewUnionDoctorDetailInfo.setRowNum(mRowNum+"");
                    mProvideViewUnionDoctorDetailInfo.setPageNum(mNumPage+"");
                    mProvideViewUnionDoctorDetailInfo.setLoginDoctorPosition("15.45415^532.545");

                    if (mProvideViewUnionDoctorDetailInfo.getUnionCode() == null)
                        mProvideViewUnionDoctorDetailInfo.setUnionCode("");
                    if (mProvideViewUnionDoctorDetailInfo.getUnionQrCode() == null)
                        mProvideViewUnionDoctorDetailInfo.setUnionQrCode("");
                    if (mProvideViewUnionDoctorDetailInfo.getUnionName() == null)
                        mProvideViewUnionDoctorDetailInfo.setUnionName("");
                    if (mProvideViewUnionDoctorDetailInfo.getProvince() == null)
                        mProvideViewUnionDoctorDetailInfo.setProvince("");
                    if (mProvideViewUnionDoctorDetailInfo.getCity() == null)
                        mProvideViewUnionDoctorDetailInfo.setCity("");
                    if (mProvideViewUnionDoctorDetailInfo.getArea() == null)
                        mProvideViewUnionDoctorDetailInfo.setArea("");
                    if (mProvideViewUnionDoctorDetailInfo.getHospitalId() == null)
                        mProvideViewUnionDoctorDetailInfo.setHospitalId("");
                    if (mProvideViewUnionDoctorDetailInfo.getDepartmentId() == null)
                        mProvideViewUnionDoctorDetailInfo.setDepartmentId("");
                    if (mProvideViewUnionDoctorDetailInfo.getDepartmentSecondId() == null)
                        mProvideViewUnionDoctorDetailInfo.setDepartmentSecondId("");
                    mProvideViewUnionDoctorDetailInfo.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    //实体转JSON字符串
                    String str = new Gson().toJson(mProvideViewUnionDoctorDetailInfo);
//                    //JSON字符串转实体
                    mProvideViewUnionDoctorDetailInfo = new Gson().fromJson(str,ProvideViewUnionDoctorDetailInfo.class);
                    //获取
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(mProvideViewUnionDoctorDetailInfo),Constant.SERVICEURL+"unionDoctorController/searchDoctorUnionBasicByParam");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        loadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取联盟信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //获取联盟列表
                    List<ProvideViewUnionDoctorDetailInfo> list= new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideViewUnionDoctorDetailInfo>>(){}.getType());
                    mUnionList .addAll(list);
                } catch (Exception e) {
                    loadDate = false;
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
     * 显示医院
     */
    private void showChoiceHospitalText(int index) {
        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
        mProvideViewUnionDoctorDetailInfo.setHospitalId(mProvideHospitalInfos.get(index).getHospitalInfoCode());
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
     * 设置所在地区显示
     */
    public void setRegionText() {
        mProvideViewUnionDoctorDetailInfo.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            mProvideViewUnionDoctorDetailInfo.setCity("");
            mProvideViewUnionDoctorDetailInfo.setArea("");
        }
        else if (mChoiceRegionMap.get("dist") == null ||"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            mProvideViewUnionDoctorDetailInfo.setArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id()))
        {
            mProvideViewUnionDoctorDetailInfo.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id()))
        {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name()+mChoiceRegionMap.get("city").getRegion_name()+mChoiceRegionMap.get("dist").getRegion_name());
            mProvideViewUnionDoctorDetailInfo.setArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }
        //初始化医院
        mChoiceHospitalText.setText("请选择医院");
        mProvideViewUnionDoctorDetailInfo.setHospitalId("");

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



}
