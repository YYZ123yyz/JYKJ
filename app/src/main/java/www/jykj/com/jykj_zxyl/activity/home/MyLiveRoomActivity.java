package www.jykj.com.jykj_zxyl.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import yyz_exploit.activity.LivePushActivity;

import yyz_exploit.adapter.MyFragmentAdapter2;
import yyz_exploit.fragment.BeingFragment;
import yyz_exploit.fragment.HistoryFragment;

public class MyLiveRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fs1 = new ArrayList<>();
    private LinearLayout lin_room;
    private TextView live_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_live_room);
        initView();

    }

    private void initView(){

        //返回
        findViewById(R.id.iv_back_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //我要直播
        live_tv = findViewById(R.id.live_tv);
        live_tv.setOnClickListener(this);
        live_tv.bringToFront();

        tabLayout = findViewById(R.id.sy_tablayout);
        viewPager = findViewById(R.id.sy_viewpager);
        fs1.clear();
        BeingFragment beingfragment = BeingFragment.create("正在直播");
        fs1.add(beingfragment);
        HistoryFragment historyfragment = HistoryFragment.create("历史直播");
        fs1.add(historyfragment);
        //设置适配器
        MyFragmentAdapter2 adapter = new MyFragmentAdapter2(getSupportFragmentManager(), fs1);
        viewPager.setAdapter(adapter);
        //设置下划线颜色
        tabLayout.setSelectedTabIndicatorColor(Color.RED);
        //添加选项卡
        TabLayout.Tab tab1 = tabLayout.newTab();
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tabLayout.addTab(tab2);
        //将viewpager关联到tablayout
        tabLayout.setupWithViewPager(viewPager);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_tv:
                Intent intent = new Intent(MyLiveRoomActivity.this, LivePushActivity.class);
                startActivity(intent);
                Log.d("tag", "onClick: "+"21111111");
                break;
        }
    }
}
