package www.jykj.com.jykj_zxyl.fragment.liveroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import entity.liveroom.FocusBean;
import entity.liveroom.PreLiveInfo;
import entity.liveroom.QueryLiveroomCond;
import entity.liveroom.SubFocusResp;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.liveroom.LiveroomDetailActivity;
import www.jykj.com.jykj_zxyl.adapter.PreLiveAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.IConstant;
import www.jykj.com.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PreRoomFragment extends Fragment {
    private JYKJApplication mApp;
    private Activity mActivity;
    private Context mContext;
    RecyclerView hot_live_rc;
    LinearLayoutManager mLayoutManager;
    PreLiveAdapter preLiveAdapter;
    List<PreLiveInfo> mdatas = new ArrayList();
    private int pageNumber=1;
    private int pageSize=10;
    private int lastVisibleIndex = 0;
    private LoadDataTask loadDataTask;
    boolean mLoadDate = true;
    private RefreshLayout refreshLayout;
    public ProgressDialog mDialogProgress = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (JYKJApplication)getActivity().getApplication();
        mActivity = getActivity();
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retV =  inflater.inflate(R.layout.pre_live_fragment,container,false);
        hot_live_rc = retV.findViewById(R.id.pre_live_rc);
        refreshLayout =retV.findViewById(R.id.refreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        hot_live_rc.setLayoutManager(mLayoutManager);
        preLiveAdapter = new PreLiveAdapter(mdatas);
        hot_live_rc.setAdapter(preLiveAdapter);
        addListener();
        preLiveAdapter.setMyListener(new PreLiveAdapter.OnHotliveItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                switch (view.getId()){
                    case R.id.ll_root:
                        PreLiveInfo parbean = mdatas.get(position);
                        Intent parintent = new Intent(mActivity, LiveroomDetailActivity.class);
                        parintent.putExtra("detailCode",parbean.getDetailsCode());
                        mActivity.startActivity(parintent);
                        break;
                    case R.id.iv_collection_btn:
                        PreLiveInfo starbean = mdatas.get(position);
                        doFocus(starbean,position);
                        break;
                }

            }

            @Override
            public void onLongClick(int position, View view) {

            }
        });
        return retV;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 添加监听
     */
    private void addListener(){
        refreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber=1;
                loadData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNumber++;
                loadData();
            }
        });
    }

    public void loadData() {
        if (isAdded() && refreshLayout != null) {
            refreshLayout.autoRefresh();
            QueryLiveroomCond queryCond = new QueryLiveroomCond();
            queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
            queryCond.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            queryCond.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            queryCond.setPageNum(String.valueOf(pageNumber));
            queryCond.setRowNum(String.valueOf(pageSize));
            queryCond.setRequestClientType("1");
            queryCond.setSearchBroadcastTitle("");
            queryCond.setSearchClassCode("");
            queryCond.setSearchKeywordsCode("");
            queryCond.setSearchRiskCode("");
            queryCond.setSearchUserName("");
            loadDataTask = new LoadDataTask(queryCond);
            loadDataTask.execute();
        }

    }

    class LoadDataTask extends AsyncTask<Void,Void,List<PreLiveInfo>> {
        QueryLiveroomCond queryCond;
        LoadDataTask(QueryLiveroomCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<PreLiveInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<PreLiveInfo> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(pageNumber));
                String quejson = new Gson().toJson(queryCond);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+quejson,"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/searchLiveRoomDetailsByBroadcastStateResNoticeList");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode()
                        && StrUtils.defaulObjToStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),PreLiveInfo.class);
                }else{
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // refreshLayout.finishLoadMoreWithNoMoreData();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<PreLiveInfo> hotLiveInfos) {
            if(hotLiveInfos.size()>0){
               if(pageNumber==1){
                   mdatas.clear();
               }
                mdatas.addAll(hotLiveInfos);
                preLiveAdapter.setData(mdatas);
                preLiveAdapter.notifyDataSetChanged();

            }else{
                if(pageNumber>1){
                    pageNumber = pageNumber - 1;
                }
            }
            if(pageNumber==1){
                refreshLayout.finishRefresh(1000);
            }else{
                refreshLayout.finishLoadMore();
            }
            mLoadDate = true;
        }
    }

    void doFocus(PreLiveInfo parabean,int selpos){
        getProgressBar("提交数据","数据提交中，请稍后");
        FocusBean subbean = new FocusBean();
        subbean.setDetailsCode(parabean.getDetailsCode());
        subbean.setLoginUserPosition(mApp.loginDoctorPosition);
        subbean.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        subbean.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        subbean.setRequestClientType("1");
        SubFocusTask subFocusTask = new SubFocusTask(subbean,parabean,selpos);
        subFocusTask.execute();
    }

    class SubFocusTask extends AsyncTask<Void,Void,Boolean> {
        FocusBean subean;
        String repmsg = "";
        SubFocusResp subresp = null;
        PreLiveInfo sellive = null;
        int selpos = -1;

        SubFocusTask(FocusBean subean, PreLiveInfo sellive, int selpos) {
            this.subean = subean;
            this.sellive = sellive;
            this.selpos = selpos;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String suburl = "https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/extendBroadcastFollowNum";
                if (1 == sellive.getFlagLikes()) {
                    suburl = "https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/Numberofprecastviewerscancelled";
                    sellive.setFlagLikes(0);
                    if (null != sellive.getExtendBroadcastFollowNum() && sellive.getExtendBroadcastFollowNum().length() > 0) {
                        sellive.setExtendBroadcastFollowNum(String.valueOf(Integer.parseInt(sellive.getExtendBroadcastFollowNum()) - 1));
                    } else {
                        sellive.setExtendBroadcastFollowNum("0");
                    }
                } else {
                    sellive.setFlagLikes(1);
                    if (null != sellive.getExtendBroadcastFollowNum() && sellive.getExtendBroadcastFollowNum().length() > 0) {
                        sellive.setExtendBroadcastFollowNum(String.valueOf(Integer.parseInt(sellive.getExtendBroadcastFollowNum()) + 1));
                    } else {
                        sellive.setExtendBroadcastFollowNum("0");
                    }
                }
                String repjson = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(subean), suburl);
                com.hyphenate.easeui.netService.entity.NetRetEntity retEntity = JSON.parseObject(repjson, com.hyphenate.easeui.netService.entity.NetRetEntity.class);
                repmsg = retEntity.getResMsg();
                if (1 == retEntity.getResCode()) {
                    subresp = JSON.parseObject(StrUtils.defaulObjToStr(retEntity.getResJsonData()), SubFocusResp.class);
                    return true;
                }
            } catch (Exception ex) {
                repmsg = "系统异常";
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            cacerProgress();
            Toast.makeText(mContext, repmsg, Toast.LENGTH_SHORT).show();
            if (aBoolean) {
                mdatas.set(selpos, sellive);
                preLiveAdapter.notifyDataSetChanged();
            }
        }
    }
        /**
         * 获取进度条
         */

        public void getProgressBar(String title, String progressPrompt) {
            if (mDialogProgress == null) {
                mDialogProgress = new ProgressDialog(mActivity);
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
