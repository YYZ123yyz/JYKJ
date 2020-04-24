package www.jykj.com.jykj_zxyl.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.JYZL_GRZLActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZLecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZ_CFQRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class JYZLActivity extends AppCompatActivity {


    private         Context                 mContext;
    private         TWJZ_CFQActivity        mActivity;
    private         Handler                 mHandler;
    private         JYKJApplication         mApp;
    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private         JYZLecycleAdapter       mJYZLecycleAdapter;       //适配器
    private         List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl);
        mContext = this;
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityJYZL_JYZLInfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJYZLecycleAdapter = new JYZLecycleAdapter(mHZEntyties,mContext);
        mRecycleView.setAdapter(mJYZLecycleAdapter);
        mJYZLecycleAdapter.setOnItemClickListener(new JYZLecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,JYZL_GRZLActivity.class));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}
