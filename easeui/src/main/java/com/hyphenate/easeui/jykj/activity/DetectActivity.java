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
import com.hyphenate.easeui.jykj.adapter.Detect_RVAdapter;
import com.hyphenate.easeui.jykj.bean.DetectBean;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DetectActivity extends AppCompatActivity implements View.OnClickListener {


    private Context mContext;
    //   private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private DetectActivity mActivity;
    private NetRetEntity netRetEntity;
    private Detect_RVAdapter detect_rvAdapter;
    private List<DetectBean> detectBeans = new ArrayList<>();
    private LinearLayout liBack;
    private RecyclerView rvDetect;
    private Button btActivityMySelfSettingExitButton;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    private List<DetectBean> mDetectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        mContext = this;
        mActivity = this;
        mDetectList = (ArrayList<DetectBean>) getIntent().getSerializableExtra("detect");
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
        initView();
        //   OnClickListener();
        Detect();
        initHandler();
    }

    private void initView() {
        liBack = (LinearLayout) findViewById(R.id.li_back);
        liBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvDetect = (RecyclerView) findViewById(R.id.rv_detect);
        btActivityMySelfSettingExitButton = (Button) findViewById(R.id.bt_activityMySelfSetting_exitButton);
        btActivityMySelfSettingExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        //检测类型
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvDetect.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvDetect.setHasFixedSize(true);
        detect_rvAdapter = new Detect_RVAdapter(detectBeans, mContext);
        rvDetect.setAdapter(detect_rvAdapter);
        detect_rvAdapter.setOnItemClickListener(new Detect_RVAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                detectBeans.get(position).setChoice(!detectBeans.get(position).isChoice());
                detect_rvAdapter.setDate(detectBeans);
                detect_rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
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
                            detectBeans = JSON.parseArray(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), DetectBean.class);
                            for (int i = 0; i < detectBeans.size(); i++) {
                                for (int j = 0; j < mDetectList.size(); j++) {
                                    if (detectBeans.get(i).getConfigDetailCode().equals(mDetectList.get(j).getConfigDetailCode())) {
                                        detectBeans.get(i).setChoice(true);
                                    }
                                }
                            }
                            detect_rvAdapter.setDate(detectBeans);
                            detect_rvAdapter.notifyDataSetChanged();
                        }

                        break;
                }


            }
        };
    }

    private void Detect() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("configDetailTypeCode", "10");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/searchSignConfigDetail");
                    Log.e("tag", "服务 " + mNetRetStr);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void commit() {
        List<DetectBean> list = new ArrayList<>();
        for (int i = 0; i < detectBeans.size(); i++) {
            if (detectBeans.get(i).isChoice())
                list.add(detectBeans.get(i));
        }
        Intent intent = new Intent();
        intent.putExtra("detect", (Serializable) list);
        setResult(RESULT_OK, intent);
        finish();
    }
}