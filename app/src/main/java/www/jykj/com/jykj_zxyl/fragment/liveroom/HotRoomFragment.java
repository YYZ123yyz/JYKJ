package www.jykj.com.jykj_zxyl.fragment.liveroom;

import android.app.Activity;
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
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import entity.liveroom.HotLiveInfo;
import entity.liveroom.QueryLiveroomCond;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.LivePublisherActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.NewLivePlayerActivity;
import www.jykj.com.jykj_zxyl.adapter.HotLiveAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.IConstant;
import www.jykj.com.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HotRoomFragment extends Fragment {
    private JYKJApplication mApp;
    private Activity mActivity;
    private Context mContext;
    RecyclerView hot_live_rc;
    LinearLayoutManager mLayoutManager;
    HotLiveAdapter hotLiveAdapter;
    List<HotLiveInfo> mdatas = new ArrayList();
    private int lastVisibleIndex = 0;
    private LoadDataTask loadDataTask;
    boolean mLoadDate = true;
    private RefreshLayout refreshLayout;
    private int pageNumber=1;
    private int pageSize=10;
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
        View retV =  inflater.inflate(R.layout.hot_live_fragment,container,false);
        hot_live_rc = retV.findViewById(R.id.hot_live_rc);
        refreshLayout =retV.findViewById(R.id.refreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        hot_live_rc.setLayoutManager(mLayoutManager);
        hotLiveAdapter = new HotLiveAdapter(mdatas);
        hot_live_rc.setAdapter(hotLiveAdapter);
        addListener();
        hotLiveAdapter.setMyListener(new HotLiveAdapter.OnHotliveItemClickListener(){
            @Override
            public void onClick(int position, View view) {
                switch (view.getId()){
                    case R.id.ll_root:
                        HotLiveInfo parben = mdatas.get(position);
                        if(parben.getUserCode().equals(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode())) {
                            Intent theintent = new Intent(mActivity, LivePublisherActivity.class);
                            theintent.putExtra("detailCode", parben.getDetailsCode());
                            theintent.putExtra("pushUrl", parben.getPullUrl());
                            theintent.putExtra("chatRoomName", parben.getChatRoomCode());
                            theintent.putExtra("chatId", parben.getChatRoomCode());
                            theintent.putExtra("liveTitle", parben.getBroadcastTitle());
                            theintent.putExtra("live_type", LivePublisherActivity.LIVE_TYPE_HOTLIVE);
                            mActivity.startActivity(theintent);
                        }else{
                            Intent theintent = new Intent(mActivity, NewLivePlayerActivity.class);
                            theintent.putExtra("chatId",parben.getChatRoomCode());
                            theintent.putExtra("pullUrl",parben.getPullUrl());
                            theintent.putExtra("PLAY_TYPE", NewLivePlayerActivity.ACTIVITY_TYPE_LIVE_PLAY);
                            mActivity.startActivity(theintent);
                        }
                        break;
                }
            }

            @Override
            public void onLongClick(int position, View view) {

            }
        });
        loadData();
        return retV;
    }



    /**
     * 添加监听
     */
    private void addListener(){
        refreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            pageNumber=1;
            loadData();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageNumber++;
            loadData();
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


    class LoadDataTask extends AsyncTask<Void,Void,List<HotLiveInfo>>{
        QueryLiveroomCond queryCond;
        LoadDataTask(QueryLiveroomCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<HotLiveInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<HotLiveInfo> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(pageNumber));
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/searchLiveRoomDetailsByBroadcastStateResHotPlayList");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaulObjToStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),HotLiveInfo.class);
                }else{
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //refreshLayout.finishLoadMoreWithNoMoreData();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<HotLiveInfo> hotLiveInfos) {
            if(hotLiveInfos.size()>0){
                if(pageNumber==1){
                    mdatas.clear();
                }
                mdatas.addAll(hotLiveInfos);
                hotLiveAdapter.setData(mdatas);
                hotLiveAdapter.notifyDataSetChanged();

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
}
