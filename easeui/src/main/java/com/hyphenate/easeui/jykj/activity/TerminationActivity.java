package com.hyphenate.easeui.jykj.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.bean.CancelContractBean;
import com.hyphenate.easeui.jykj.bean.ProvideViewPatientLablePunchClockState;
import com.hyphenate.easeui.jykj.dialog.CancelContractDialog;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;

public class TerminationActivity extends AppCompatActivity implements View.OnClickListener {
    public ProgressDialog mDialogProgress = null;
    private TerminationActivity mTerminationActivity;
//    private JYKJApplication mApp;
    private Handler mHandler;
    private String mNetRetStr;
    private CancelContractDialog cancelContractDialog;
    private ProvideViewPatientLablePunchClockState mData;
    private CancelContractBean mCancelContractBean;
    private LinearLayout llBack;
    private RelativeLayout rl;
    private TextView tvName;
    private LinearLayout linDetect;
    private EditText ed_termination;
    private Button btnSend;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    private String orderId;
    private String singNO;
    private String nickName;
    private String patientCode;
    private String singCode;
    private String cansignNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termination);
        mTerminationActivity = this;
        cancelContractDialog = new CancelContractDialog(TerminationActivity.this);
//        if (getIntent() != null) {
//            mData = (ProvideViewPatientLablePunchClockState) getIntent().getSerializableExtra("patientLable");
//        }
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderId = extras.getString("orderId");
            singNO = extras.getString("singNO");
            nickName = extras.getString("nickName");
            patientCode = extras.getString("patientCode");

        }else{
            Intent intent = getIntent();
            singCode = intent.getStringExtra("signCode");
             cansignNo = intent.getStringExtra("signNo");
        }
        initView();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            Toast.makeText(mTerminationActivity, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("tag", "handleMessage: " + "提交失败");
                        }
                        break;
                    case 2:

                        break;
                }
            }
        };
    }

    private void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl = (RelativeLayout) findViewById(R.id.rl);
        tvName = (TextView) findViewById(R.id.tv_name);
        linDetect = (LinearLayout) findViewById(R.id.lin_Detect);
        ed_termination = (EditText) findViewById(R.id.ed_termination);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
         linDetect.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cancelContractDialog.show();
             }
         });
        cancelContractDialog.setOnClickItemListener(new CancelContractDialog.OnClickItemListener() {
            @Override
            public void onClickItem(CancelContractBean cancelContractBean) {
                tvName.setText(cancelContractBean.getAttrName());
                Log.e("", "onClickItem: "+cancelContractBean.getAttrName() );
                mCancelContractBean = cancelContractBean;
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    private void commit() {
        getProgressBar("请稍候...", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("loginDoctorPosition", "108.93425^34.23053");
                    map.put("mainDoctorCode", code);
                    map.put("mainDoctorName", name);
                    if(TextUtils.isEmpty(orderId)&&TextUtils.isEmpty(singNO)){
                        map.put("signCode", singCode);
                        map.put("signNo", cansignNo);
                    }else{
                        map.put("signCode", orderId);
                        map.put("signNo", singNO);
                    }
                    map.put("mainPatientCode", patientCode);
                    map.put("mainUserName",nickName);
                    // 	解约原因分类编码
                    map.put("refuseReasonClassCode", mCancelContractBean.getAttrCode()+"");
                    // 	解约原因分类名称
                    map.put("refuseReasonClassName", mCancelContractBean.getAttrName());
                    map.put("refuseRemark", ed_termination.getText().toString());

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/operTerminationSumbit");
                    Log.e("tag", "run: 解约" + mNetRetStr);
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


    /**
     * 获取进度条
     * 获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(TerminationActivity.this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}