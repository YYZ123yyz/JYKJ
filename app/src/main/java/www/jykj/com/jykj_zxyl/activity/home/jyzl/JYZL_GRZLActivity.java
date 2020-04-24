package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.adapter.JYZL_GRZLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.JYZLecycleAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * 就诊总览==》个人总览
 */
public class JYZL_GRZLActivity extends AppCompatActivity {


    private Context mContext;
    private TWJZ_CFQActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private JYZL_GRZLRecycleAdapter mJYZL_GRZLRecycleAdapter;      //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private LinearLayout mGRXX;                                  //个人信息

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyzl_grzl);
        mContext = this;
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityGRZL_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mJYZL_GRZLRecycleAdapter = new JYZL_GRZLRecycleAdapter(mHZEntyties, mContext);
        mRecycleView.setAdapter(mJYZL_GRZLRecycleAdapter);

        mGRXX = (LinearLayout) this.findViewById(R.id.li_activityJZZLGRZL_hzxx);
        mGRXX.setOnClickListener(new ButtonClick());

    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityJZZLGRZL_hzxx:
                    startActivity(new Intent(mContext, GRXX_GRZKActivity.class));
                    break;
            }
        }
    }
}
