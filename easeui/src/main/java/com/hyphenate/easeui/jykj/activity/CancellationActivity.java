package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.bean.CancelContractOrderBean;
import com.hyphenate.easeui.jykj.bean.Restcommit;
import com.hyphenate.easeui.jykj.bean.SignPatientDoctorOrderBean;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.math.BigDecimal;
import java.util.HashMap;

public class CancellationActivity extends AppCompatActivity implements View.OnClickListener {

    private String mNetRetStr;
  //  private JYKJApplication mApp;
    private Handler mHandler;
    private CancelContractOrderBean signPatientDoctorOrderBean;
    private LinearLayout llBack;
    private RelativeLayout rl;
    private TextView tvName;
    private LinearLayout linDetect;
    private TextView tvTermination;
    private TextView cancellationTime;
    private TextView cancellationClass;
    private TextView cancellationTimes;
    private TextView cancellationDuration;
    private TextView cancellationPrice;
    private Button btnRefuse;
    private Button btnAgree;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    private String orderId;
    private String singNO;
    private String nickName;
    private String patientCode;
    private NetRetEntity netRetEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderId = extras.getString("singCode");
            singNO = extras.getString("singNO");
            nickName = extras.getString("nickName");
            patientCode = extras.getString("patientCode");

        }
        initView();
        getdata();
        initHandler();
    }
//获取解约订单详情
    private void getdata() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("signOrderCode",orderId );

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/searchSignPatientDoctorOrder");
                    Log.e("TAG", "run:  获取解约订单详情  " + mNetRetStr);
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

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode()==1) {
                            signPatientDoctorOrderBean = JSON.parseObject(netRetEntity.getResJsonData(), CancelContractOrderBean.class);
                            setShow();
                        }else{
                            Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case  2:
                         netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                         if(netRetEntity.getResCode()==1){
                             Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                         }else{
                             Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                         }
                        break;
                }
            }
        };
    }

    //布局显示
    private void setShow() {
        tvName.setText(signPatientDoctorOrderBean.getRefuseReasonClassName());
        tvTermination.setText(signPatientDoctorOrderBean.getRefuseRemark());
        cancellationTime.setText((CharSequence) DateUtils.getDate(signPatientDoctorOrderBean.getSignDurationUnit()));


        //签约时长
        cancellationDuration.setText(signPatientDoctorOrderBean.getSignDurationUnit());
        //监测类型
        String signOtherServiceCode = signPatientDoctorOrderBean.getSignOtherServiceCode();
        String [] temp = null;
        temp = signOtherServiceCode.split(",");
        cancellationClass.setText(temp.length+"项");
        //辅导类型
        cancellationTimes.setText(signPatientDoctorOrderBean.getDetectRate()+signPatientDoctorOrderBean.getDetectRateUnitName());
        //签约价格
        double signPrice = signPatientDoctorOrderBean.getSignPrice();
        cancellationPrice.setText(signPrice + "");
    }

    private void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        rl = (RelativeLayout) findViewById(R.id.rl);
        tvName = (TextView) findViewById(R.id.tv_name);
        linDetect = (LinearLayout) findViewById(R.id.lin_Detect);
        tvTermination = (TextView) findViewById(R.id.tv_termination);
        cancellationTime = (TextView) findViewById(R.id.cancellation_time);
        cancellationClass = (TextView) findViewById(R.id.cancellation_class);
        cancellationTimes = (TextView) findViewById(R.id.cancellation_times);
        cancellationDuration = (TextView) findViewById(R.id.cancellation_duration);
        cancellationPrice = (TextView) findViewById(R.id.cancellation_price);
        btnRefuse = (Button) findViewById(R.id.btn_Refuse);
        btnAgree = (Button) findViewById(R.id.btn_agree);
        //拒绝
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CancellationActivity.this, TerminationActivity.class)
//                        .putExtra("singCode", signPatientDoctorOrderBean.getSignCode())
//                        .putExtra("signNo", signPatientDoctorOrderBean.getSignNo())
                );
            }
        });
        //同意
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agree();
            }
        });
    }
   //同意
    private void agree() {
//处理同意解约逻辑
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", "108.93425^34.23053");
        hashMap.put("mainDoctorCode", code);
        hashMap.put("mainDoctorName", name);
        hashMap.put("signCode", signPatientDoctorOrderBean.getSignCode());
        hashMap.put("signNo", signPatientDoctorOrderBean.getSignNo());
        hashMap.put("mainPatientCode", patientCode);
        hashMap.put("mainUserName", nickName);
        hashMap.put("confimresult", "1");
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(hashMap), Constant.SERVICEURL + "doctorSignControlle/operTerminationConfim");
                    Log.e("tag", "同意" + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}