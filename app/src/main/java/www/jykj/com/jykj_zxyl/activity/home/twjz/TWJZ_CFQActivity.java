package www.jykj.com.jykj_zxyl.activity.home.twjz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.TWJZNoFinishRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZ_CFQRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 图文就诊（处方签）
 */
public class TWJZ_CFQActivity extends AppCompatActivity {

    private                 Context                 mContext;
    private TWJZ_CFQActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;
    private                 RecyclerView            mRecycleView;

    private                 LinearLayoutManager     layoutManager;
    private                 TWJZ_CFQRecycleAdapter mTWJZ_CFQRecycleAdapter;       //适配器
    private                 List<HZIfno>            mHZEntyties = new ArrayList<>();            //所有数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_cfq);
        ActivityUtil.setStatusBarMain(TWJZ_CFQActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        setData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
//        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityCFQ_recycleView);
//        //创建默认的线性LayoutManager
//        layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayout.VERTICAL);
//        mRecycleView.setLayoutManager(layoutManager);
//        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        mRecycleView.setHasFixedSize(true);
//        //创建并设置Adapter
//        mTWJZ_CFQRecycleAdapter = new TWJZ_CFQRecycleAdapter(mHZEntyties,mContext);
//        mRecycleView.setAdapter(mTWJZ_CFQRecycleAdapter);
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
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

                case R.id.li_activityTWJZKJCF_cfyp:
                    startActivity(new Intent(mContext,KJCF_CFYPActivity.class));
                    break;

            }
        }
    }


    /**
     * 设置数据
     */
    private void setData() {
//        for (int i = 0; i < 40; i++)
//        {
//            HZIfno hzIfno = new HZIfno();
//            if (i%3 == 0)
//            {
//                hzIfno.setHzName("测试名"+i);
//                hzIfno.setHzAge((30+(i%3))+"");
//                hzIfno.setHzSex(1);
//                hzIfno.setLaber("患者标签：高血压I期");
//                hzIfno.setTwjzZT(1);
//            }
//            if (i%3 == 1)
//            {
//                hzIfno.setHzName("测试名"+i);
//                hzIfno.setHzAge((30+(i%3))+"");
//                hzIfno.setHzSex(-1);
//                hzIfno.setLaber("患者标签：高血压I期");
//                hzIfno.setTwjzZT(2);
//            }
//            if (i%3 == 2)
//            {
//                hzIfno.setHzName("测试名"+i);
//                hzIfno.setHzAge((30+(i%3))+"");
//                hzIfno.setHzSex(1);
//                hzIfno.setLaber("患者标签：高血压I期");
//                hzIfno.setTwjzZT(3);
//            }
//            mHZEntyties.add(hzIfno);
//        }
//        mTWJZ_CFQRecycleAdapter.setDate(mHZEntyties);
//        mTWJZ_CFQRecycleAdapter.notifyDataSetChanged();
    }




}
