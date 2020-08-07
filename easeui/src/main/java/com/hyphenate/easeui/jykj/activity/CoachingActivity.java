package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.adapter.Coaching_RVAdapter;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CoachingActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Handler mHandler;
    private String mNetRetStr;
 //   private JYKJApplication mApp;
    private List<CoachingBean> coachingBeans;
    private Coaching_RVAdapter detect_rvAdapter;
    private CoachingActivity mActivity;
    private LinearLayout liBack;
    private RecyclerView rvCoaching;
    private Button btCoaching;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching);
     //   ButterKnife.bind(this);
     //   mApp = (JYKJApplication) getApplication();
        mContext = this;
        mActivity = this;
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
      //  ActivityUtil.setStatusBarMain(mActivity);
        initView();
        Detect();
        OnClickListener();
        initHandler();
    }

    private void OnClickListener() {
      //  btCoaching.setOnClickListener(this);
    }

    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            coachingBeans = JSON.parseArray(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), CoachingBean.class);
                            detect_rvAdapter = new Coaching_RVAdapter(coachingBeans, mContext);
                            rvCoaching.setAdapter(detect_rvAdapter);
                            detect_rvAdapter.setOnItemClickListener(new Coaching_RVAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    coachingBeans.get(position).setChoice(!coachingBeans.get(position).isChoice());
                                    detect_rvAdapter.setDate(coachingBeans);
                                    detect_rvAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onLongClick(int position) {

                                }
                            });
                        }

                        break;
                }


            }
        };
    }


    private void Detect() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("configDetailTypeCode", "20");
        map.put("operDoctorCode", code);
        map.put("operDoctorName",name);

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/searchSignConfigDetail");
                    Log.e("tag", "类别 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void initView() {
        liBack = (LinearLayout) findViewById(R.id.li_back);
        liBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvCoaching = (RecyclerView) findViewById(R.id.rv_coaching);
        btCoaching = (Button) findViewById(R.id.bt_coaching);
        btCoaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });

        liBack.setOnClickListener(this);
        //检测类型
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvCoaching.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvCoaching.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    //提交
    private void commit() {
        List<CoachingBean> list = new ArrayList<>();
        for (int i = 0; i < coachingBeans.size(); i++) {
            if (coachingBeans.get(i).isChoice())
                list.add(coachingBeans.get(i));
        }
        Intent intent = new Intent();
        intent.putExtra("coaching", (Serializable) list);
        setResult(RESULT_OK, intent);
        finish();
    }
}