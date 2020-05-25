package www.jykj.com.jykj_zxyl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;


import rx.Observer;
import rx.functions.Action1;
import www.jykj.com.jykj_zxyl.R;
import zxing.android.CaptureActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private ImageView ivOneDot, ivTwoDot, ivThreeDot;
    private View view1;
    private View view2;
    private View view3;
    private ArrayList<View> list;
    private TextView tvLogin, tvRegister;
    private TextView welcome_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_splash);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_welcome);
        initView();
        requestPermission();
    }


    private void initView() {
        //跳过
        welcome_pass = findViewById(R.id.welcome_pass);
        welcome_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mViewPager = this.findViewById(R.id.viewpager);
      //  ivOneDot = this.findViewById(R.id.iv_one_dot);
       // ivTwoDot = this.findViewById(R.id.iv_two_dot);
    //    ivThreeDot = this.findViewById(R.id.iv_three_dot);
        list = new ArrayList<>();
        view1 = View.inflate(this, R.layout.view_guide_one, null);
        view2 = View.inflate(this, R.layout.view_guide_two, null);
        view3 = View.inflate(this, R.layout.view_guide_three, null);
        tvLogin = view3.findViewById(R.id.tv_login);
     //   tvRegister = view3.findViewById(R.id.tv_regist);
        tvLogin.setOnClickListener(this);
    //    tvRegister.setOnClickListener(this);
        list.add(view1);
        list.add(view2);
        list.add(view3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position == 0) {
//                    ivOneDot.setImageResource(R.drawable.shape_dot_white);
//                    ivTwoDot.setImageResource(R.drawable.shape_dot_gray);
//                    ivThreeDot.setImageResource(R.drawable.shape_dot_gray);
//                }
//                if (position == 1) {
//                    ivOneDot.setImageResource(R.drawable.shape_dot_gray);
//                    ivTwoDot.setImageResource(R.drawable.shape_dot_white);
//                    ivThreeDot.setImageResource(R.drawable.shape_dot_gray);
//                }
//                if (position == 2) {
//                    ivOneDot.setImageResource(R.drawable.shape_dot_gray);
//                    ivTwoDot.setImageResource(R.drawable.shape_dot_gray);
//                    ivThreeDot.setImageResource(R.drawable.shape_dot_white);
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(list.get(position));
            }
        });
    }


    private void requestPermission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CALL_PHONE
                )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {//允许权限，6.0以下默认true

                        } else {
                            Toast.makeText(WelcomeActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
