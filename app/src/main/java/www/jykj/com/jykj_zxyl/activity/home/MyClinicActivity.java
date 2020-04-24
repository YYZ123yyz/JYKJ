package www.jykj.com.jykj_zxyl.activity.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.hyhd.VideoCallActivity;
import com.hyphenate.easeui.hyhd.VoiceCallActivity;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.wdzs.FragmentZSXQ;
import www.jykj.com.jykj_zxyl.fragment.wdzs.FragmentZSZKZL;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentDoctorFriend;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentDoctorPliatform;

/**
 * 我的诊所
 */
public class MyClinicActivity extends AppCompatActivity {
//    private Context         mContext;
//    private LinearLayout llBack;
//    private LinearLayout llTwjz;
//    private LinearLayout liDHJZ;                    //电话就诊
//    private LinearLayout liSPJZ;                    //视频就诊

    private LinearLayout llBack;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;


    private Context mContext;
    private Handler mHandler;
    private ProgressDialog mDialogProgress;

    private int mPageSelect;                    //当前选中的   1是哪个页面  1=医生好友  2=平台医生
    private FragmentZSXQ mFragmentZSXQ;                //诊所详情
    private FragmentZSZKZL mFragmentZSZKZL;            //诊所状况总览

    private TextView mInviteButton;                  //邀请

    private String mNetRetStr;                 //返回字符串
    private JYKJApplication mApp;
    private String mUnionCode;                  //联盟编码
    private String mUnionName;                  //联盟名称
    private LinearLayout mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clinic);
        mContext = this;
        initView();
    }

    private void initView() {
        pager = (ViewPager) this.findViewById(R.id.page);
        tabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("诊所状况总览");
        mTitles.add("诊所详情");
        mFragmentZSZKZL = new FragmentZSZKZL();
        mFragmentZSXQ = new FragmentZSXQ();
        fragmentList.add(mFragmentZSZKZL);
        fragmentList.add(mFragmentZSXQ);

        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPageSelect = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
