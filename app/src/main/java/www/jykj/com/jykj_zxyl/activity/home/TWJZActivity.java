package www.jykj.com.jykj_zxyl.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmenNoFinish;
import www.jykj.com.jykj_zxyl.fragment.twjz.FragmentHistory;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentRMJX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentWDSC;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXWX;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXXW;
import www.jykj.com.jykj_zxyl.fragment.ylzx.FragmentYXZN;

/**
 * 图文就诊
 */
public class TWJZActivity extends AppCompatActivity {

    private Context mContext;
    private TWJZActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private LinearLayout llBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        pager = (ViewPager) this.findViewById(R.id.page);
        tabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        llBack = (LinearLayout) findViewById(R.id.ll_back);

        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("未完成");
        mTitles.add("历史记录");

        fragmentList.add(new FragmenNoFinish());
        fragmentList.add(new FragmentHistory());

        fragmentAdapter = new FragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }

    private void initListener() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

//    @Override
//    public void delete(int[] ids) {
//        for (int id : ids) {
//            Staffs staffs = staffsMapper.selectByPrimaryKey(id);
//            String filePath = "D:/images";
//            String s1 = filePath + staffs.getStaffphotoname();
//            String s2 = filePath + staffs.getNocardpositivename();
//            String s3 = filePath + staffs.getNocardnegativename();
//            String s4 = filePath + staffs.getJobcarname();
//
//            //删除本地图片
//            File f=new File("路径");
//            try {
//                System.out.println(f.getCanonicalPath());
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                System.out.println("Sorry,can't get canonical path");
//            }
//            recurDelete(f);
//
//            staffsMapper.deleteByPrimaryKey(id);
//        }
//    }
//
//
//    public static void recurDelete(File f) {
//        try {
//            for (File fi : f.listFiles()) {
//                if (fi.isDirectory()) {
//                    recurDelete(fi);
//                } else {
//                    fi.delete();
//                }
//            }
//            f.delete();
//        } catch (NullPointerException n) {
//            System.out.println("Sorry,No such file");
//        }
//    }

}
