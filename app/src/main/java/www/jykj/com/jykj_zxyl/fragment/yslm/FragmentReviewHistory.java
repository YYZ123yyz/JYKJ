package www.jykj.com.jykj_zxyl.fragment.yslm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.CheckReviewActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.adapter.MyReviewAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class FragmentReviewHistory extends Fragment {
    private RecyclerView mRecyclerView;
    private MyReviewAdapter mAdapter;

    public ProgressDialog mDialogProgress =null;

    private Context mContext;                                       //
    private JoinDoctorsUnionActivity mActivity;
    private JYKJApplication mApp;

    private         String                      mNetRetStr;                 //返回字符串

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private                 boolean                     loadDate = true;
    private Handler mHandler;

    private List<ProvideViewUnionDoctorMemberApplyInfo> mMyApplyInfoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_review, container, false);
        mContext = getContext();
        initView(v);
        mApp = (JYKJApplication) mContext.getApplicationContext();
        initHandler();
        //获取我的申请数据
        getDate();
        return v;
    }

    /**
     * 判断，是否需要更新，如果审核页面操作成功，则更新一下，否则不需要更新
     */
    @Override
    public void onResume() {
        super.onResume();
        mMyApplyInfoList.clear();
//        if (mApp.isUpdateReviewUnion)
//        {
//            getDate();
//            mNumPage = 1;
//            mRowNum = 10;
//            mMyApplyInfoList.clear();
//        }
    }

    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MyReviewAdapter(mMyApplyInfoList,mContext,mApp);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
               startActivity(new Intent(mContext,CheckReviewActivity.class).putExtra("applyInfo", (Serializable)mMyApplyInfoList.get(position)));
            }
        });

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
                            getDate();
                        }

                    }
                }
            }
        });

    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 0 && mNumPage == 1)
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        mAdapter.setDate(mMyApplyInfoList);
                        mAdapter.notifyDataSetChanged();
                        mApp.isUpdateReviewUnion = false;
                }
            }
        };
    }


    /**
     * 获取我的申请数据
     */
    private void getDate() {

        getProgressBar("请稍候。。。。","正在加载数据");
        new Thread(){
            public void run(){
                try {
                    ProvideViewUnionDoctorDetailInfo provideViewUnionDoctorDetailInfo = new ProvideViewUnionDoctorDetailInfo();
                    provideViewUnionDoctorDetailInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewUnionDoctorDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewUnionDoctorDetailInfo.setRowNum(mRowNum+"");
                    provideViewUnionDoctorDetailInfo.setPageNum(mNumPage+"");
                    //实体转JSON字符串
                    String str = new Gson().toJson(provideViewUnionDoctorDetailInfo);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/unionDoctorController/searchApprovalDoctorUnionHistoryByParam");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        loadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //获取联盟列表
                    List<ProvideViewUnionDoctorMemberApplyInfo> list= JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewUnionDoctorMemberApplyInfo.class);
                    mMyApplyInfoList .addAll(list);
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
