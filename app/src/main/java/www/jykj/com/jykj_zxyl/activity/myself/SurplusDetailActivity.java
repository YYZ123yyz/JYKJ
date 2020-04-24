package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.MySurplusDetailEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MySurplusRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class SurplusDetailActivity extends AppCompatActivity {

    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private Context mContext;
    private SurplusDetailActivity mActivity;
    private RecyclerView mSurplusDetailRecycleView;                        //明细列表
    private LinearLayoutManager layoutManager;
    private MySurplusRecycleAdapter mMySurplusRecycleAdapter;       //适配器
    private List<MySurplusDetailEntity> mySurplusDetailEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_mysurplus_detail);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }


    /**
     * 初始化布局
     */
    private void initLayout() {
        mSurplusDetailRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityMySurplusDetail_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mSurplusDetailRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mSurplusDetailRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMySurplusRecycleAdapter = new MySurplusRecycleAdapter(mySurplusDetailEntities, mContext, mActivity);
        mSurplusDetailRecycleView.setAdapter(mMySurplusRecycleAdapter);
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_mySurPlus_detail:
                    Intent intent = new Intent();
                    intent.setClass(SurplusDetailActivity.this, SurplusDetailActivity.class);
                    startActivity(intent);

            }
        }
    }


    /**
     * 设置数据
     */

    private void setData() {
        for (int i = 0; i < 20; i++) {
            MySurplusDetailEntity mySurplusDetailEntity = new MySurplusDetailEntity();
            if (i % 2 != 0) {
                mySurplusDetailEntity.setSuType(1);
                mySurplusDetailEntity.setDate("2016年1月6日 12:23:32");
                mySurplusDetailEntity.setPrice("-100.00");
                mySurplusDetailEntity.setYe("余额 20.00");
                mySurplusDetailEntities.add(mySurplusDetailEntity);
            } else {
                mySurplusDetailEntity.setSuType(-1);
                mySurplusDetailEntity.setDate("2016年1月6日 12:23:32");
                mySurplusDetailEntity.setPrice("+100.00");
                mySurplusDetailEntity.setYe("余额 20.00");
                mySurplusDetailEntities.add(mySurplusDetailEntity);
            }
        }
        mMySurplusRecycleAdapter.setDate(mySurplusDetailEntities);
        mMySurplusRecycleAdapter.notifyDataSetChanged();

    }


}
