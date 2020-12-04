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
import com.jykj.live.lvb.liveplayer.LivePlayerActivity;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import entity.conditions.QueryLectureCond;
import entity.liveroom.ProvideLiveBroadcastDetails;
import entity.liveroom.QueryLiveroomCond;
import entity.liveroom.SubjectLiveInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyLiveRoomActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.LectureDetailActivity;
import www.jykj.com.jykj_zxyl.adapter.SubtitleLiveAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.IConstant;
import www.jykj.com.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectRoomFragment extends Fragment {
    private JYKJApplication mApp;
    private Activity mActivity;
    private Context mContext;
    RecyclerView hot_live_rc;
    LinearLayoutManager mLayoutManager;
    SubtitleLiveAdapter subtitleLiveAdapter;
    List<ProvideLiveBroadcastDetails> mdatas = new ArrayList();
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
        View retV =  inflater.inflate(R.layout.subject_live_fragment,container,false);
        hot_live_rc = retV.findViewById(R.id.subject_live_rc);
        refreshLayout =retV.findViewById(R.id.refreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        hot_live_rc.setLayoutManager(mLayoutManager);
        subtitleLiveAdapter = new SubtitleLiveAdapter(mdatas);
        subtitleLiveAdapter.setMyListener(new SubtitleLiveAdapter.OnHotliveItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                switch (view.getId()){
                    case R.id.ll_root:
                        ProvideLiveBroadcastDetails parbean = mdatas.get(position);
                        Intent lecdetint = new Intent(mActivity, LectureDetailActivity.class);
                        lecdetint.putExtra("detailCode",parbean.getDetailsCode());
                        lecdetint.putExtra("sub_room_info",parbean);
                        mActivity.startActivity(lecdetint);
                        break;
                }
            }

            @Override
            public void onLongClick(int position, View view) {

            }
        });
        hot_live_rc.setAdapter(subtitleLiveAdapter);
        addListener();
        loadData();
        return retV;
    }

    public void refreshData(){
        pageNumber=1;
        loadData();
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

    public void loadData(){
        if (isAdded()&&refreshLayout!=null) {
            refreshLayout.autoRefresh();
            QueryLectureCond queryCond = new QueryLectureCond();
            queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
            queryCond.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            queryCond.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            queryCond.setPageNum(String.valueOf(pageNumber));
            queryCond.setRowNum(String.valueOf(pageSize));
            queryCond.setRequestClientType("1");
            //queryCond.setSearchBroadcastTitle("");
            //queryCond.setSearchClassCode("");
            //queryCond.setSearchKeywordsCode("");
            //queryCond.setSearchRiskCode("");
            //queryCond.setSearchUserName("");
            loadDataTask = new LoadDataTask(queryCond);
            loadDataTask.execute();
        }
    }

    class LoadDataTask extends AsyncTask<Void,Void,List<ProvideLiveBroadcastDetails>> {
        QueryLectureCond queryCond;
        LoadDataTask(QueryLectureCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<ProvideLiveBroadcastDetails> doInBackground(Void... voids) {
            mLoadDate = false;
            List<ProvideLiveBroadcastDetails> retlist = new ArrayList();
            try {
                queryCond.setPageNum(String.valueOf(pageNumber));
                String quejson = new Gson().toJson(queryCond);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+quejson,"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/searchLiveRoomDetailsByBroadcastStateResSpecialList");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaulObjToStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),ProvideLiveBroadcastDetails.class);
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
        protected void onPostExecute(List<ProvideLiveBroadcastDetails> hotLiveInfos) {
            if(pageNumber==1){
                mdatas.clear();
            }
            if(hotLiveInfos.size()>0){
                mdatas.addAll(hotLiveInfos);
                subtitleLiveAdapter.setData(mdatas);
                subtitleLiveAdapter.notifyDataSetChanged();
            }else {
                if(pageNumber>1){
                    pageNumber = pageNumber - 1;
                }
                subtitleLiveAdapter.notifyDataSetChanged();
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
