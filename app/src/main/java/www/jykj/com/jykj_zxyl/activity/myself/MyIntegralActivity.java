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

import entity.MyIntegralEntity;
import entity.MySurplusDetailEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MyIntegralRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MySurplusRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的积分明细
 */
public class MyIntegralActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 MyIntegralActivity          mActivity;
    private                 RecyclerView                mIntegralRecycleView;                        //明细列表
    private                 LinearLayoutManager         layoutManager;
    private                 MyIntegralRecycleAdapter    mMyIntegralRecycleAdapter;       //适配器
    private                 List<MyIntegralEntity> mMyIntegralEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_myintegral_detail);
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
        mIntegralRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityMyIntegral_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mIntegralRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mIntegralRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMyIntegralRecycleAdapter = new MyIntegralRecycleAdapter(mMyIntegralEntities,mContext,mActivity);
        mIntegralRecycleView.setAdapter(mMyIntegralRecycleAdapter);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_mySurPlus_detail:
                    Intent intent = new Intent();
                    intent.setClass(MyIntegralActivity.this,MyIntegralActivity.class);
                    startActivity(intent);

            }
        }
    }


    /**
     * 设置数据
     */

    private void setData() {
        for (int i = 0; i < 20; i++)
        {
            MyIntegralEntity myIntegralEntity = new MyIntegralEntity();
            if (i%2 != 0)
            {
                myIntegralEntity.setIntegralType(1);
                myIntegralEntity.setDate("2016年1月6日 12:23:32");
                myIntegralEntity.setPrice("-100.00");
                myIntegralEntity.setIntegralTypeString("积分兑换");
                mMyIntegralEntities.add(myIntegralEntity);
            }
           else
            {
                myIntegralEntity.setIntegralType(-1);
                myIntegralEntity.setDate("2016年1月6日 12:23:32");
                myIntegralEntity.setPrice("-100.00");
                myIntegralEntity.setIntegralTypeString("发表新动态");
                mMyIntegralEntities.add(myIntegralEntity);
            }
        }
        mMyIntegralRecycleAdapter.setDate(mMyIntegralEntities);
        mMyIntegralRecycleAdapter.notifyDataSetChanged();

    }



}
