package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.hyphenate.easeui.entity.ViewSysUserDoctorInfoAndHospital;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.bean.CancelContractBean;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.jykj.bean.ProvideViewPatientLablePunchClockState;
import com.hyphenate.easeui.jykj.dialog.CancelContractDialog;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.utils.SharedPreferences_DataSave;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityStackManager;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityUtils;

public class TerminationActivity2 extends AppCompatActivity implements View.OnClickListener {
    public ProgressDialog mDialogProgress = null;
    private TerminationActivity2 mTerminationActivity;
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
    private OrderMessage orderMessage;
    private ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital;
    private String orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termination);
        ActivityUtils.setStatusBarMain(this);
        mTerminationActivity = this;
        cancelContractDialog = new CancelContractDialog(TerminationActivity2.this);
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
        Bundle extras = this.getIntent().getExtras();
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(TerminationActivity2.this,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mViewSysUserDoctorInfoAndHospital
                = new Gson().fromJson(userInfoSuLogin, ViewSysUserDoctorInfoAndHospital.class);
        if (extras != null) {
            orderId = extras.getString("singCode");
            singNO = extras.getString("signNo");
            orderType = extras.getString("orderType");
            nickName = extras.getString("patientName");
            patientCode = extras.getString("patientCode");
            orderMessage = (OrderMessage) extras.getSerializable("orderMsg");
        }
        initView();
        initHandler();
    }

    @SuppressLint("HandlerLeak")
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
                            if (orderMessage!=null) {
                                orderMessage.setOrderType("2");
                            }
                            EventBus.getDefault().post(orderMessage);
                            Toast.makeText(mTerminationActivity, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            ActivityStackManager.getInstance().finish(CancellationActivity.class);
                            TerminationActivity2.this.finish();
                        } else {
                            Toast.makeText(mTerminationActivity, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:

                        break;
                }
            }
        };
    }

    private void initView() {
        if(TextUtils.isEmpty(orderType)){

        }
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
                Log.e("", "onClickItem: " + cancelContractBean.getAttrName());
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
                    map.put("mainDoctorCode", mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    map.put("mainDoctorName", mViewSysUserDoctorInfoAndHospital.getUserName());
                    map.put("signCode", orderId);
                    map.put("signNo", singNO);
                    map.put("mainPatientCode", patientCode);
                    map.put("mainUserName", nickName);
                    // 	解约原因分类编码
                    map.put("refuseReasonClassCode", mCancelContractBean.getAttrCode() + "");
                    // 	解约原因分类名称
                    map.put("refuseReasonClassName", mCancelContractBean.getAttrName());
                    map.put("refuseRemark", ed_termination.getText().toString());
                    map.put("confimresult", "0");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map),
                            Constant.SERVICEURL + "doctorSignControlle/operTerminationConfim");
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
            mDialogProgress = new ProgressDialog(TerminationActivity2.this);
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