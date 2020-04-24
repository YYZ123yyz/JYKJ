package www.jykj.com.jykj_zxyl.fragment.yslm;


import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.home.newsMessage.GetNewsMessageParment;
import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberInviteInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.adapter.MyApplyAdapter;
import www.jykj.com.jykj_zxyl.adapter.UnionMyBeInvateHandlerAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class FragmentUnionMyBeInvateHandler extends Fragment {
    private RecyclerView mRecyclerView;
    private UnionMyBeInvateHandlerAdapter mAdapter;

    public ProgressDialog mDialogProgress =null;

    private Context mContext;                                       //
    private JoinDoctorsUnionActivity mActivity;
    private JYKJApplication mApp;

    private         String                      mNetRetStr;                 //返回字符串

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条

    private Handler mHandler;


    private                 boolean                     loadDate;                       //是否加载数据
    private List<ProvideViewUnionDoctorMemberInviteInfo> mProvideViewUnionDoctorMemberInviteInfo = new ArrayList<>();                 //获取到的消息



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_apply, container, false);

        mContext = getContext();
        initView(v);
        mApp = (JYKJApplication) mContext.getApplicationContext();
        initHandler();
        //获取我的申请数据
        getDate();
        return v;
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        if (retEntity.getResCode() == 0 && mNumPage > 1)
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        mAdapter.setDate(mProvideViewUnionDoctorMemberInviteInfo);
                        mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void initView(View v){
        mRecyclerView = v.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new UnionMyBeInvateHandlerAdapter(mProvideViewUnionDoctorMemberInviteInfo,mContext,mApp);
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
                            getDate();
                        }

                    }
                }
            }
        });
    }


    /**
     * 获取我的申请数据
     */
    private void getDate() {

        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    GetNewsMessageParment getNewsMessageParment = new GetNewsMessageParment();
                    getNewsMessageParment.setRowNum(mRowNum+"");
                    getNewsMessageParment.setPageNum(mNumPage+"");
                    getNewsMessageParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    getNewsMessageParment.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    String jsonString = JSON.toJSONString(getNewsMessageParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "unionDoctorController/searchInviteDoctorUnionApprovalHistory");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        loadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    List<ProvideViewUnionDoctorMemberInviteInfo> list =  JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewUnionDoctorMemberInviteInfo.class);
                    mProvideViewUnionDoctorMemberInviteInfo.addAll(list);
                } catch (Exception e) {
                    loadDate = false;
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    e.printStackTrace();
                    return;

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
