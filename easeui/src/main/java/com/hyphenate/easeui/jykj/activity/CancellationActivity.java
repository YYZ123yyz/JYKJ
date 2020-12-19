package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.hyphenate.easeui.entity.ViewSysUserDoctorInfoAndHospital;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.bean.CancelContractOrderBean;
import com.hyphenate.easeui.jykj.bean.GetdetailsBean;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.jykj.bean.Restcommit;
import com.hyphenate.easeui.jykj.bean.SignPatientDoctorOrderBean;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.utils.SharedPreferences_DataSave;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;

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
    private LinearLayout llBottomRoot;
    private Button btnRefuse;
    private Button btnAgree;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    private String orderId;
    private String singNO;
    private String signCode;
    private String nickName;
    private String patientCode;
    private NetRetEntity netRetEntity;
    private String doctorName;
    private String doctoCode;
    private LinearLayout details_rl;
    private LinearLayout details;
    private LinearLayout mLlCancelContractRoot;
    private LinearLayout mLlRefuseRoot;
    private TextView mTvRefuseReason;
    private TextView mTvRefuseContent;
    private String from;
    private TextView name1;
    private String orderType;
    private ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital;
    private List<CancelContractOrderBean.OrderDetailListBean> DetectitemBeans = new ArrayList<>();
    private List<CancelContractOrderBean.OrderDetailListBean> CoachingitemBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        ActivityUtils.setStatusBarMain(this);
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");

        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(
                CancellationActivity.this,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mViewSysUserDoctorInfoAndHospital
                = new Gson().fromJson(userInfoSuLogin, ViewSysUserDoctorInfoAndHospital.class);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("orderCode");
            singNO = extras.getString("singNO");
            nickName = extras.getString("nickName");
            patientCode = extras.getString("patientCode");
            doctorName = extras.getString("DoctorName");
            doctoCode = extras.getString("DoctoCode");
            signCode=extras.getString("signCode");
            from = extras.getString("from");
            orderType=extras.getString("orderType");
        }
        initView();
        getdata();
        initHandler();
    }

    //获取解约订单详情
    private void getdata() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", doctoCode);
        map.put("operDoctorName", doctorName);
        map.put("orderCode", orderId);
        map.put("signCode",signCode);

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
                        if (netRetEntity.getResCode() == 1) {
                            signPatientDoctorOrderBean = JSON.parseObject(netRetEntity.getResJsonData(), CancelContractOrderBean.class);

                            //List<CancelContractOrderBean.OrderDetailListBean> orderDetailList1 = signPatientDoctorOrderBean.getOrderDetailList();
                            List<CancelContractOrderBean.OrderDetailListBean> orderDetailList = signPatientDoctorOrderBean.getOrderDetailList();
                            //List<GetdetailsBean.OrderDetailListBean> orderDetailList = signPatientDoctorOrderBean.getOrderDetailList();

                            for (CancelContractOrderBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
                                String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
                                if (configDetailTypeCode.equals("10")) {
                                    DetectitemBeans.add(orderDetailListBean);
                                } else if (configDetailTypeCode.equals("20")) {
                                    CoachingitemBeans.add(orderDetailListBean);
                                }
                            }
                            setShow();
                        } else {
                            details_rl.setVisibility(View.GONE);
                            Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                        //    Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
//                            EventBus.getDefault().post(new OrderMessage(nickName,imageUrl,orderId,signCode,
//                                    monitoringType,coach,signUpTime,price,singNO,"1",messageType,patientCode));

                            OrderMessage orderMessage =getOrderMessage();
                            EventBus.getDefault().post(orderMessage);
                            CancellationActivity.this.finish();
                        } else {
                            Toast.makeText(CancellationActivity.this, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    private OrderMessage getOrderMessage() {
        OrderMessage orderMessage1 = new OrderMessage(
                mViewSysUserDoctorInfoAndHospital.getUserName(), mViewSysUserDoctorInfoAndHospital.getUserLogoUrl()
                , orderId, signCode, cancellationClass.getText().toString()
                , cancellationTimes.getText().toString(), cancellationDuration.getText().toString(),
                cancellationPrice.getText().toString(), singNO, "",
                "terminationOrder", patientCode);
        return orderMessage1;
    }

    //布局显示
    private void setShow() {

        if (TextUtils.isEmpty(orderType)) {
            mLlCancelContractRoot.setVisibility(View.VISIBLE);
            mLlRefuseRoot.setVisibility(View.GONE);
            if(from.equals("1")){
                String relieveReasonClassNameD = signPatientDoctorOrderBean.getRelieveReasonClassNameD();
                tvName.setText(relieveReasonClassNameD);
                String relieveRemarkD = signPatientDoctorOrderBean.getRelieveRemarkD();
                tvTermination.setText(StringUtils.isNotEmpty(relieveRemarkD) ? relieveRemarkD : "暂无");
            }else if(from.equals("2")){
                String relieveReasonClassName = signPatientDoctorOrderBean.getRelieveReasonClassName();
                tvName.setText(relieveReasonClassName);
                String relieveRemark = signPatientDoctorOrderBean.getRelieveRemark();
                tvTermination.setText(StringUtils.isNotEmpty(relieveRemark) ? relieveRemark : "暂无");
            }


        }else{
            switch (orderType) {
                case "1":
                    mLlCancelContractRoot.setVisibility(View.VISIBLE);
                    mLlRefuseRoot.setVisibility(View.GONE);
                    String relieveReasonClassNameD = signPatientDoctorOrderBean.getRelieveReasonClassNameD();
                    String relieveReasonClassName = signPatientDoctorOrderBean.getRelieveReasonClassName();
                    if (StringUtils.isNotEmpty(relieveReasonClassNameD)) {
                        tvName.setText(relieveReasonClassNameD);
                        String relieveRemarkD = signPatientDoctorOrderBean.getRelieveRemarkD();
                        tvTermination.setText(StringUtils.isNotEmpty(relieveRemarkD) ? relieveRemarkD : "暂无");
                    }else{
                        tvName.setText(relieveReasonClassName);
                        String relieveRemark = signPatientDoctorOrderBean.getRelieveRemark();
                        tvTermination.setText(StringUtils.isNotEmpty(relieveRemark) ? relieveRemark : "暂无");
                    }
//                    if (from.equals("1")) {
//                        String relieveReasonClassNameD = signPatientDoctorOrderBean.getRelieveReasonClassNameD();
//                        tvName.setText(relieveReasonClassNameD);
//                        String relieveRemarkD = signPatientDoctorOrderBean.getRelieveRemarkD();
//                        tvTermination.setText(StringUtils.isNotEmpty(relieveRemarkD) ? relieveRemarkD : "暂无");
//
//                    } else if (from.equals("2")) {
//                        String relieveReasonClassName = signPatientDoctorOrderBean.getRelieveReasonClassName();
//                        tvName.setText(relieveReasonClassName);
//                        String relieveRemark = signPatientDoctorOrderBean.getRelieveRemark();
//                        tvTermination.setText(StringUtils.isNotEmpty(relieveRemark) ? relieveRemark : "暂无");
//                    }
                    break;
                case "2":
                    mLlCancelContractRoot.setVisibility(View.GONE);
                    mLlRefuseRoot.setVisibility(View.VISIBLE);
                    mTvRefuseReason.setText(signPatientDoctorOrderBean.getRejectReasonClassNameJ());
                    String rejectRemarkJ = signPatientDoctorOrderBean.getRejectRemarkJ();
                    mTvRefuseContent.setText(StringUtils.isNotEmpty(rejectRemarkJ) ? rejectRemarkJ : "暂无");


                    break;
                case "3":
                    mLlCancelContractRoot.setVisibility(View.GONE);
                    mLlRefuseRoot.setVisibility(View.VISIBLE);
                    mTvRefuseReason.setText(signPatientDoctorOrderBean.getRejectReasonClassName());
                    String rejectRemark = signPatientDoctorOrderBean.getRejectRemark();
                    mTvRefuseContent.setText(StringUtils.isNotEmpty(rejectRemark) ? rejectRemark : "暂无");
                    break;
            }
        }
//
//        if (TextUtils.isEmpty(relieveReasonClassNameD)&&TextUtils.isEmpty(relieveReasonClassName)) {
//            mLlCancelContractRoot.setVisibility(View.GONE);
//        }else{
//            mLlCancelContractRoot.setVisibility(View.VISIBLE);
//        }
//        if(from.equals("1")){
//            if (StringUtils.isNotEmpty(relieveReasonClassNameD)) {
//                tvName.setText(relieveReasonClassNameD);
//                mLlCancelContractRoot.setVisibility(View.VISIBLE);
//            }else{
//                mLlCancelContractRoot.setVisibility(View.GONE);
//            }
//            String relieveRemarkD = signPatientDoctorOrderBean.getRelieveRemarkD();
//            tvTermination.setText(StringUtils.isNotEmpty(relieveRemarkD)?relieveRemarkD:"暂无");
//        }else if(from.equals("2")){
//            if (StringUtils.isNotEmpty(relieveReasonClassName)) {
//                tvName.setText(relieveReasonClassName);
//                mLlCancelContractRoot.setVisibility(View.VISIBLE);
//            }else{
//                mLlCancelContractRoot.setVisibility(View.GONE);
//            }
//            String relieveRemark = signPatientDoctorOrderBean.getRelieveRemark();
//            tvTermination.setText(StringUtils.isNotEmpty(relieveRemark)?relieveRemark:"暂无");
//        }
//
//
//        String rejectReasonClassCode = signPatientDoctorOrderBean.getRejectReasonClassCode();
//        String rejectReasonClassCodeJ = signPatientDoctorOrderBean.getRejectReasonClassCodeJ();
//        if (StringUtils.isNotEmpty(rejectReasonClassCode)) {
//            mTvRefuseReason.setText(signPatientDoctorOrderBean.getRejectReasonClassName());
//            String rejectRemark = signPatientDoctorOrderBean.getRejectRemark();
//            mTvRefuseContent.setText(StringUtils.isNotEmpty(rejectRemark)?rejectRemark:"暂无");
//            mLlRefuseRoot.setVisibility(View.VISIBLE);
//        }
//        if (StringUtils.isNotEmpty(rejectReasonClassCodeJ)) {
//            mTvRefuseReason.setText(signPatientDoctorOrderBean.getRejectReasonClassNameJ());
//            String rejectRemarkJ = signPatientDoctorOrderBean.getRejectRemarkJ();
//            mTvRefuseContent.setText(StringUtils.isNotEmpty(rejectRemarkJ)?rejectRemarkJ:"暂无");
//            mLlRefuseRoot.setVisibility(View.VISIBLE);
//        }
//        if(TextUtils.isEmpty(rejectReasonClassCode)&&TextUtils.isEmpty(rejectReasonClassCodeJ)){
//            mLlRefuseRoot.setVisibility(View.GONE);
//        }


        cancellationTime.setText(DateUtils.stampToDate(signPatientDoctorOrderBean.getSignStartTime()));
        //签约时长
        cancellationDuration.setText(signPatientDoctorOrderBean.getSignDuration() + "个" + signPatientDoctorOrderBean.getSignDurationUnit());
        //监测类型
        String signOtherServiceCode = signPatientDoctorOrderBean.getSignOtherServiceCode();
        String[] temp = null;
        temp = signOtherServiceCode.split(",");
        cancellationClass.setText(temp.length + "项");
        //监测类型
        cancellationTimes.setText("1次/" + signPatientDoctorOrderBean.getDetectRate() + signPatientDoctorOrderBean.getDetectRateUnitName());
        //签约价格
        double signPrice = signPatientDoctorOrderBean.getSignPrice();
        cancellationPrice.setText("￥"+signPrice + "");
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("signCode", signPatientDoctorOrderBean.getSignCode());
                bundle.putString("orderCode",orderId);
                bundle.putString("singNO", singNO);
                bundle.putString("status", "1");
                startActivity(SigningDetailsActivity.class,bundle);
            }
        });

    }

    private void initView() {
        name1 = findViewById(R.id.name);
        details =  findViewById(R.id.details);
        details_rl = findViewById(R.id.details_lin);
        llBack =findViewById(R.id.ll_back);
        btnRefuse=findViewById(R.id.btn_Refuse);
        btnAgree=findViewById(R.id.btn_agree);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl = findViewById(R.id.rl);
        tvName = findViewById(R.id.tv_name);
        linDetect = findViewById(R.id.lin_Detect);
        tvTermination = findViewById(R.id.tv_termination);
        cancellationTime = findViewById(R.id.cancellation_time);
        cancellationClass = findViewById(R.id.cancellation_class);
        cancellationTimes = findViewById(R.id.cancellation_times);
        cancellationDuration = findViewById(R.id.cancellation_duration);
        cancellationPrice = findViewById(R.id.cancellation_price);
        mLlCancelContractRoot=findViewById(R.id.ll_cancel_contract_root);
        mLlRefuseRoot=findViewById(R.id.ll_refuse_root);
        mTvRefuseReason=findViewById(R.id.tv_refuse_reason);
        mTvRefuseContent=findViewById(R.id.tv_refuse_content);
        llBottomRoot=findViewById(R.id.ll_bottom_root);
        if (StringUtils.isNotEmpty(from)) {
            if (from.equals("1")) {
                llBottomRoot.setVisibility(View.GONE);
            }else if(from.equals("2")){
                llBottomRoot.setVisibility(View.VISIBLE);
            }
        }
//        if(from.equals("1")){
//            details.setVisibility(View.GONE);
//            name1.setVisibility(View.GONE);
//        }else if(from.equals("2")){
//            details.setVisibility(View.VISIBLE);
//            name1.setVisibility(View.VISIBLE);
//        }

        details.setVisibility(View.VISIBLE);
        name1.setVisibility(View.VISIBLE);
        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //处理拒绝解约逻辑  跳转页面
                Bundle bundle = new Bundle();
                bundle.putString("singCode", signCode);
                bundle.putString("signNo", singNO);
                bundle.putString("patientName", nickName);
                bundle.putString("patientCode",patientCode);
                bundle.putSerializable("orderMsg",getOrderMessage());
                startActivity(TerminationActivity2.class, bundle);

            }
        });
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
        //Log.e("TAG", "agree: ",code );
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", "108.93425^34.23053");
        hashMap.put("mainDoctorCode", doctoCode);
        hashMap.put("mainDoctorName", doctorName);
        hashMap.put("signCode", signPatientDoctorOrderBean.getSignCode());
        hashMap.put("signNo", signPatientDoctorOrderBean.getSignNo());
        hashMap.put("mainPatientCode", signPatientDoctorOrderBean.getMainPatientCode());
        hashMap.put("mainUserName", signPatientDoctorOrderBean.getMainUserName());
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
    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    protected void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this, paramClass);
        this.startActivity(localIntent);
    }

}