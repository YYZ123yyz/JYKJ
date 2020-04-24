package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.MyCouponEntity;
import entity.MyIntegralEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MyCouponRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MyIntegralRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的优惠券
 */
public class MyCouponActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 MyCouponActivity            mActivity;
    private                 RecyclerView                mCouponRecycleView;                        //我的优惠券列表
    private                 LinearLayoutManager         layoutManager;
    private                 MyCouponRecycleAdapter      mMyCouponRecycleAdapter;       //适配器
    private                 List<MyCouponEntity>        mMyCouponEntities = new ArrayList<>();
    private                 TextView                    mDetail;                               //明细

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentself_mycoupon);
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
        mDetail = (TextView)this.findViewById(R.id.activity_myCouponInfo_detail);
        mDetail.setOnClickListener(new ButtonClick());

        mCouponRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityMyCoupon_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mCouponRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mCouponRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMyCouponRecycleAdapter = new MyCouponRecycleAdapter(mMyCouponEntities,mContext,mActivity);
        mCouponRecycleView.setAdapter(mMyCouponRecycleAdapter);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_myCouponInfo_detail:
                    Intent intent = new Intent();
                    intent.setClass(MyCouponActivity.this,MyCouponDetailActivity.class);
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
            MyCouponEntity yCouponEntity = new MyCouponEntity();
            mMyCouponEntities.add(yCouponEntity);

        }
        mMyCouponRecycleAdapter.setDate(mMyCouponEntities);
        mMyCouponRecycleAdapter.notifyDataSetChanged();

    }



}
