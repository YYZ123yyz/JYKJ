package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmenNoFinish;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmentHistory;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentMyApply;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentMyReview;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentReviewHistory;

/**
 * 医生联盟审核
 */
public class ReviewActivity extends AppCompatActivity {
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private LinearLayout llBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initView();
        initListener();
    }

    private void initView(){
        pager= (ViewPager) this.findViewById(R.id.page);
        tabLayout= (TabLayout) this.findViewById(R.id.tab_layout);
        llBack = (LinearLayout) findViewById(R.id.ll_back);

        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add("我的申请");
        mTitles.add("待我审核");
        mTitles.add("审核记录");

        fragmentList.add(new FragmentMyApply());
        fragmentList.add(new FragmentMyReview());
        fragmentList.add(new FragmentReviewHistory());

        fragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);

    }

    private void initListener(){
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
