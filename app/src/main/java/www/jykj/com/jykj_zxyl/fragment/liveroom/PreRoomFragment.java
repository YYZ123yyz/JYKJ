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
import entity.liveroom.PreLiveInfo;
import entity.liveroom.QueryLiveroomCond;
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

public class PreRoomFragment extends Fragment {
    private JYKJApplication mApp;
    private Activity mActivity;
    private Context mContext;
    RecyclerView hot_live_rc;
    LinearLayoutManager mLayoutManager;
    PreLiveAdapter preLiveAdapter;
    List<PreLiveInfo> mdatas = new ArrayList();
    private int pageno=1;
    private int lastVisibleIndex = 0;
    private LoadDataTask loadDataTask;
    boolean mLoadDate = true;
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
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        hot_live_rc.setLayoutManager(mLayoutManager);
        preLiveAdapter = new PreLiveAdapter(mdatas);
        hot_live_rc.setAdapter(preLiveAdapter);
        hot_live_rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= mLayoutManager.getItemCount() - 1) {
                            pageno++;
                            loadData();
                        }
                    }
                }
            }
        });
        preLiveAdapter.setMyListener(new PreLiveAdapter.OnHotliveItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                switch (view.getId()){
                    case R.id.pre_live_btn:
                        PreLiveInfo parbean = mdatas.get(position);
                        Intent parintent = new Intent(mActivity, LiveroomDetailActivity.class);
                        parintent.putExtra("detailCode",parbean.getDetailsCode());
                        mActivity.startActivity(parintent);
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

    void loadData(){
        QueryLiveroomCond queryCond = new QueryLiveroomCond();
        queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
        queryCond.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        queryCond.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        queryCond.setPageNum(String.valueOf(pageno));
        queryCond.setRowNum(String.valueOf(IConstant.PAGE_SIZE));
        queryCond.setRequestClientType("1");
        queryCond.setSearchBroadcastTitle("");
        queryCond.setSearchClassCode("");
        queryCond.setSearchKeywordsCode("");
        queryCond.setSearchRiskCode("");
        queryCond.setSearchUserName("");
        loadDataTask = new LoadDataTask(queryCond);
        loadDataTask.execute();
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
                queryCond.setPageNum(String.valueOf(pageno));
                String quejson = new Gson().toJson(queryCond);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+quejson,"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/searchLiveRoomDetailsByBroadcastStateResNoticeList");
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaulObjToStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),PreLiveInfo.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<PreLiveInfo> hotLiveInfos) {
            if(hotLiveInfos.size()>0){
                mdatas.addAll(hotLiveInfos);
                preLiveAdapter.setData(mdatas);
                preLiveAdapter.notifyDataSetChanged();
            }else{
                if(pageno>1){
                    pageno = pageno - 1;
                }
            }
            mLoadDate = true;
        }
    }
}
