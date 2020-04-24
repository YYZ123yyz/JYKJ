package www.jykj.com.jykj_zxyl.fragment.twjz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.TWJZActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.jykj.com.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.jykj.com.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZHistoryInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 图文就诊历史记录fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentHistory extends Fragment {
    private             Context                             mContext;
    private             Handler                             mHandler;
    private             TWJZActivity                        mActivity;
    private             JYKJApplication                     mApp;
    private             RecyclerView                        mHZInfoRecycleView;              //患者列表
    private             LinearLayoutManager                 layoutManager;
    private             TWJZHistoryInfoRecycleAdapter       mTWJZHistoryInfoRecycleAdapter;       //适配器
    private             List<HZIfno>                        mHZEntyties = new ArrayList<>();            //所有数据



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitytwjz_history, container, false);
        mContext = getContext();
        mActivity = (TWJZActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mHZInfoRecycleView = (RecyclerView) view.findViewById(R.id.rv_fragmentNoFinish_history);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mHZInfoRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHZInfoRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mTWJZHistoryInfoRecycleAdapter = new TWJZHistoryInfoRecycleAdapter(mHZEntyties,mContext);
        mHZInfoRecycleView.setAdapter(mTWJZHistoryInfoRecycleAdapter);
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {

                }
            }
        };
    }


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {


            }
        }
    }


    /**
     * 设置数据
     */
    private void setData() {
        for (int i = 0; i < 40; i++)
        {
            HZIfno hzIfno = new HZIfno();
            if (i%3 == 0)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(3);
            }
            mHZEntyties.add(hzIfno);
        }
        mTWJZHistoryInfoRecycleAdapter.setDate(mHZEntyties);
        mTWJZHistoryInfoRecycleAdapter.notifyDataSetChanged();
    }
}
