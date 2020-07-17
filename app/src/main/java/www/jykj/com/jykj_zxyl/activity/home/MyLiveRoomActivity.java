package www.jykj.com.jykj_zxyl.activity.home;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barnettwong.dragfloatactionbuttonlibrary.view.DragFloatActionButton;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.LivePublisherActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.fragment.liveroom.HotRoomFragment;
import www.jykj.com.jykj_zxyl.fragment.liveroom.PreRoomFragment;
import www.jykj.com.jykj_zxyl.fragment.liveroom.SubjectRoomFragment;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import yyz_exploit.activity.LivePushActivity;

import yyz_exploit.activity.activity.BeforesettingActivity;
import yyz_exploit.activity.activity.LectureActivity;
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
    //private XRecyclerView room_recy;

    //数据集合
    private List<String >list=new ArrayList<>();
    //获取数据的开始
    private int curr;
    private Banner live_banner;
    private TextView room_my;

    private RelativeLayout parentView;
    private DragFloatActionButton live;
    private TextView room_lecture;
    private ViewPager roompager;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;
    private List<String> mTitles;
    private TextView room_forecast;
    private TextView room_Hit;
    private TextView room_Lecture;
    private TextView room_text;
    String live_type = LivePublisherActivity.LIVE_TYPE_HOTLIVE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_live_room);
        ActivityUtil.setStatusBarMain(MyLiveRoomActivity.this);
        initView();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.live:
                Intent intent = new Intent(MyLiveRoomActivity.this, BeforesettingActivity.class);
                intent.putExtra("live_type",live_type);
                startActivity(intent);
                break;
            case R.id.room_forecast:
                live.setVisibility(View.VISIBLE);
                roompager.setCurrentItem(0);
                room_text.setText("直播预报");
                live_type = LivePublisherActivity.LIVE_TYPE_HOTLIVE;
                break;
            case R.id.room_Hit:
                live.setVisibility(View.VISIBLE);
                roompager.setCurrentItem(1);
                room_text.setText("正在热播");
                live_type = LivePublisherActivity.LIVE_TYPE_HOTLIVE;
                break;
            case R.id.room_Lecture:
                /*Intent intent3 = new Intent(this, LectureActivity.class);
                startActivity(intent3);*/
                live.setVisibility(View.GONE);
                roompager.setCurrentItem(2);
                room_text.setText("专题讲座");
                live_type = LivePublisherActivity.LIVE_TYPE_SUBJECTLIVE;
                break;
        }
    }
    private void initView(){
        room_text = findViewById(R.id.room_text);
        //专题讲座
        room_lecture = findViewById(R.id.room_Lecture);
        room_lecture.setOnClickListener(this);
        room_Hit = findViewById(R.id.room_Hit);
        room_Hit.setOnClickListener(this);
        room_forecast = findViewById(R.id.room_forecast);
        room_forecast.setOnClickListener(this);
        //我主讲
        room_my = findViewById(R.id.room_my);
        room_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyLiveRoomActivity.this, LectureActivity.class);
                startActivity(intent);
            }
        });

        fragmentList = new ArrayList();
        fragmentList.add(new PreRoomFragment());
        fragmentList.add(new HotRoomFragment());
        fragmentList.add(new SubjectRoomFragment());
        mTitles = new ArrayList();
        mTitles.add("直播预报");
        mTitles.add("正在热播");
        mTitles.add("专题讲座");

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList,mTitles);

        live_banner = findViewById(R.id.live_banner);
        List imgs=new ArrayList<>();

        imgs.add(getStringFromDrawableRes(MyLiveRoomActivity.this,R.mipmap.live_image));

        imgs.add(getStringFromDrawableRes(MyLiveRoomActivity.this,R.mipmap.tu));

        imgs.add(getStringFromDrawableRes(MyLiveRoomActivity.this,R.mipmap.live_image));


        //设置内置样式，共有六种可以点入方法内逐一体验使用。

        live_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        //设置图片加载器，图片加载器在下方

        live_banner.setImageLoader(new MyLoader());

        //设置图片网址或地址的集合

        live_banner.setImages(imgs);

        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验

        live_banner.setBannerAnimation(Transformer.Default);

        //设置轮播图的标题集合

     //   live_banner.setBannerTitles(titles);

        //设置轮播间隔时间

        live_banner.setDelayTime(2000);

        //设置是否为自动轮播，默认是“是”。

        live_banner.isAutoPlay(true);

        //设置指示器的位置，小点点，左中右。

        live_banner.setIndicatorGravity(BannerConfig.CENTER)

//以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。

             //   .setOnBannerListener((OnBannerListener) MyLiveRoomActivity.this)

//必须最后调用的方法，启动轮播图。

                .start();
        roompager = findViewById(R.id.roompager);
        roompager.setAdapter(fragmentAdapter);
        live_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                roompager.setCurrentItem(position);
            }
        });

        /*room_recy = findViewById(R.id.room_recy);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MyLiveRoomActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        room_recy.setLayoutManager(layoutManager);

        room_recy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            //下拉刷新
            public void onRefresh() {
                //当下拉刷新的时候，重新获取数据，所有curr要变回0，并且把集合list清空
                curr=0;
                list.clear();
                getData(curr);
                room_recy.refreshComplete();
            }

            @Override
            //上拉加载
            public void onLoadMore() {
                //当上拉加载的时候，因为一次获取是10个数据，所也在获取的时候就要在加10的地方开始获取
//                如：第一次0——9；
//                    第二次10——19；
//                SystemClock.sleep(1000);
                curr=curr+10;
                getData(curr);
                room_recy.refreshComplete();
            }
        });*/



        //返回
        findViewById(R.id.iv_back_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        live = findViewById(R.id.live);
        live.setOnClickListener(this);
    }

    public static String getStringFromDrawableRes(Context context, int id) {

        Resources resources = context.getResources();

        String path = ContentResolver.SCHEME_ANDROID_RESOURCE +"://"

                + resources.getResourcePackageName(id) +"/"

                + resources.getResourceTypeName(id) +"/"

                + resources.getResourceEntryName(id);

        return path;

    }

    private void getData(int number){
        for (int i=number;i<number+10;i++){
            list.add("数据是第"+i+"个");
        }

        //调用Adapter展示数据，这个判断是为了不重复创建MyAdapter的对象
//        if (adapter==null){
//            adapter=new MyAdapter(list,context);
//            room_recy.setAdapter(adapter);
//        }else {
//            adapter.notifyDataSetChanged();
//        }

    }



    //自定义的图片加载器

    private class MyLoader extends ImageLoader {

        @Override

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load((String) path).into(imageView);
           /* imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("imgv",String.valueOf(v.getId()));
                }
            });*/
        }

    }

}
