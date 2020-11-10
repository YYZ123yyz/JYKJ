package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.fragment.DoctorFragment;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.fragment.MyselfFragment;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.MyViewPager;

/**
 * 既往病史
 */
public class HistoryActivity extends BaseActivity {
    private BaseToolBar mToolBar;//顶部toolBar
    private TabLayout mTabLayout;
    private MyViewPager mVpager;
    private List<Fragment> mFragments;//Fragment集合列表
    private List<String> mTitles;//标题集合列表
    private FragmentAdapter fragmentAdapter;
    private MyselfFragment myselfFragment;
    private DoctorFragment doctorFragment;
    private String patientCode;
    private String patientName;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mFragments=new ArrayList<>();
        mTitles=new ArrayList<>();
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        patientCode = intent.getStringExtra("patientCode");
        patientName = intent.getStringExtra("patientName");
        mToolBar=findViewById(R.id.toolbar);
        mTabLayout=findViewById(R.id.tab_layout);
        mVpager=findViewById(R.id.vp_pager);
        setToolBar();
        initTabLayout();
    }

    private void initTabLayout() {
        mTitles.add("本人填写");
        mTitles.add("医生填写");
        myselfFragment = MyselfFragment.newInstance();
        mFragments.add(myselfFragment);
        doctorFragment = DoctorFragment.newInstance(patientCode);
        mFragments.add(doctorFragment);

        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),
                mFragments, mTitles);
        mVpager.setAdapter(fragmentAdapter);
        mVpager.setOffscreenPageLimit(2);
        mVpager.setScrollble(false);
        mTabLayout.setupWithViewPager(mVpager);//与ViewPage建立关系

        SharedPreferences mSharedPreferences=getSharedPreferences("name", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString("patientCode",patientCode);
        editor.putString("patientName",patientName);
        editor.apply();

    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        mToolBar.setMainTitle("既往病史");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
    }
}