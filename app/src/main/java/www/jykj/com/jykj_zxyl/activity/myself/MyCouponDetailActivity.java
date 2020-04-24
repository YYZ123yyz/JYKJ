package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.MyCouponEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.AlreadyUserFragment;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.WithoutUserFragment;
import www.jykj.com.jykj_zxyl.adapter.MyCouponRecycleAdapter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的优惠券明细
 */
public class MyCouponDetailActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 MyCouponDetailActivity      mActivity;

    private                 ViewPager                   pager;
    private                 FragmentAdapter             fragmentAdapter;
    private                 List<Fragment>              fragmentList;
    private                 TabLayout                   tabLayout;
    private                 List<String>                mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentself_mycoupondetail);
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
        pager= (ViewPager) this.findViewById(R.id.page);
        tabLayout= (TabLayout) this.findViewById(R.id.tab_layout);

        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add("已使用");
        mTitles.add("已过期");
        fragmentList.add(new AlreadyUserFragment());
        fragmentList.add(new WithoutUserFragment());

        fragmentAdapter=new FragmentAdapter(mActivity.getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

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
                    intent.setClass(MyCouponDetailActivity.this,MyCouponDetailActivity.class);
                    startActivity(intent);

            }
        }
    }


    /**
     * 设置数据
     */

    private void setData() {
//        for (int i = 0; i < 20; i++)
//        {
//            MyCouponEntity yCouponEntity = new MyCouponEntity();
//            mMyCouponEntities.add(yCouponEntity);
//
//        }
//        mMyCouponRecycleAdapter.setDate(mMyCouponEntities);
//        mMyCouponRecycleAdapter.notifyDataSetChanged();

    }



}
