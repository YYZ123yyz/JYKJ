package www.jykj.com.jykj_zxyl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.AlreadyUserFragment;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.WithoutUserFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXZN;


/**
 * 医疗资讯fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYLZX extends Fragment {
    private Context mContext;
    private MainActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ylzx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        return v;
    }

    /**
     * 初始化布局
     */
    private void initLayout(View view) {
        pager = view.findViewById(R.id.page);
        tabLayout = view.findViewById(R.id.tab_layout);

        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("热门精选");
        mTitles.add("医学新闻");
        mTitles.add("医学文献");
        mTitles.add("医学指南");
        mTitles.add("我的收藏");
        fragmentList.add(new FragmentRMJX());
        fragmentList.add(new FragmentYXXW());
        fragmentList.add(new FragmentYXWX());
        fragmentList.add(new FragmentYXZN());
        fragmentList.add(new FragmentWDSC());

        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }
}
