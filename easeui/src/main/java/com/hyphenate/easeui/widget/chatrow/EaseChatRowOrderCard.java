package com.hyphenate.easeui.widget.chatrow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ViewSysUserDoctorInfoAndHospital;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.activity.CancellationActivity;
import com.hyphenate.easeui.jykj.activity.SigningDetailsActivity;
import com.hyphenate.easeui.jykj.activity.TerminationActivity;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.utils.SharedPreferences_DataSave;
import com.hyphenate.easeui.widget.EaseImageView;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;


/**
 * Description:自定义卡片
 *
 * @author: qiuxinhai
 * @date: 2020-07-29 14:27
 */
public class EaseChatRowOrderCard extends EaseChatRow {

    private EaseImageView mUserHead;//用户头像
    private TextView mTvCardTitle;//卡片标题
    private TextView mTvMonitValue;//监测类型
    private TextView mTvCoachRateValue;//辅导频次
    private TextView mTvSignTimeValue; //签约时长
    private TextView mTvPriceValue;//价格
    private TextView mTvOrderUpdateBtn;
    private TextView mTvCancelContractMsg;//解约信息
    private ImageView ivStampIcon;//印章
    private TextView mTvOperMsg;//操作信息

    private TextView tvMonitorType;
    private TextView tvCoachRate;
    private TextView tvSignTime;
    private RelativeLayout mRlCancelContractOrderRoot;
    private TextView tvCancelContractAgreeBtn;
    private TextView tvCancelContractRefuseBtn;
    private TextView tvOrderReceivedUpdateBtn;
    private Context mContext;
    private LinearLayout mLLRootView;
    private String orderId;
    private String singNO;
    private String monitoringType;
    private String coach;
    private String signUpTime;
    private String price;
    private String orderType;
    private String messageType;

    //预约
    private String statusType;
    private String startTime;
    private String appointMentProject;
    private String appointMentType;

    private ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital;
    private String mNetRetStr;
    private Handler mHandler;
    private String nickName;
    protected Bundle fragmentArgs;
    private String patientCode;
    private String imageUrl;

    //   private List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();
    public EaseChatRowOrderCard(Context context, EMMessage message,
                                int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
        this.mContext = context;
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_order : R.layout.ease_row_send_order, this);
        initHandler();
    }
    @Override
    protected void onFindViewById() {
        mUserHead = findViewById(R.id.iv_userhead);
        mTvCardTitle = findViewById(R.id.tv_card_title);
        mTvMonitValue = findViewById(R.id.tv_monitor_value);
        mTvCoachRateValue = findViewById(R.id.tv_coach_rate_value);
        mTvSignTimeValue = findViewById(R.id.tv_sign_time_vlaue);
        mTvPriceValue = findViewById(R.id.tv_price_vlaue);
        mLLRootView = findViewById(R.id.ll_root_view);
        ivStampIcon = findViewById(R.id.iv_stamp_icon);
        mTvOperMsg = findViewById(R.id.tv_oper_msg);
        mTvOrderUpdateBtn = findViewById(R.id.tv_order_update_btn);
        mTvCancelContractMsg = findViewById(R.id.tv_cancel_contract_msg);
        mRlCancelContractOrderRoot = findViewById(R.id.rl_cancel_contract_order_root);
        tvCancelContractAgreeBtn = findViewById(R.id.tv_cancel_contract_agree_btn);
        tvCancelContractRefuseBtn = findViewById(R.id.tv_cancel_contract_refuse_btn);
        tvOrderReceivedUpdateBtn=findViewById(R.id.tv_order_received_update_btn);
        tvMonitorType=findViewById(R.id.tv_monitor_type);
        tvCoachRate=findViewById(R.id.tv_coach_rate);
        tvSignTime=findViewById(R.id.tv_sign_time);
        addListener();
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onSetUpView() {
        SharedPreferences_DataSave m_persist = new SharedPreferences_DataSave(activity,
                "JYKJDOCTER");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mViewSysUserDoctorInfoAndHospital
                = new Gson().fromJson(userInfoSuLogin, ViewSysUserDoctorInfoAndHospital.class);
        // TODO: 2020-07-29 显示ui
        orderId = message.getStringAttribute("orderId", "");
        monitoringType = message.getStringAttribute("monitoringType", "");
        coach = message.getStringAttribute("coach", "");
        signUpTime = message.getStringAttribute("signUpTime", "");
        price = message.getStringAttribute("price", "");
        messageType = message.getStringAttribute("messageType", "");
        singNO = message.getStringAttribute("singNo", "");
        patientCode = message.getStringAttribute("patientCode", "");
        nickName = message.getStringAttribute("nickName", "");
        imageUrl = message.getStringAttribute("imageUrl", "");
        statusType=message.getStringAttribute("statusType","");
        startTime=message.getStringAttribute("startTime","");
        appointMentProject=message.getStringAttribute("appointMentProject","");
        appointMentType=message.getStringAttribute("appointMentType","");
        mTvMonitValue.setText(monitoringType);
        mTvCoachRateValue.setText(coach);
        mTvSignTimeValue.setText(signUpTime);
        mTvPriceValue.setText(String.format("¥%s", price));
        orderType = message.getStringAttribute("orderType", "");//1已同意 2 修改 3 拒绝（由患者操作发起时会携带此参数）
        if (messageType.equals("terminationOrder")) {
            mTvCardTitle.setText("解约订单");
        } else if (messageType.equals("card")) {
            mTvCardTitle.setText("签约订单");
        }else if(messageType.equals("appointment")){
            tvMonitorType.setText("预约时间");
            tvCoachRate.setText("预约项目");
            tvSignTime.setText("预约类型");
            if (statusType.equals("1")) {
                mTvCardTitle.setText("预约成功");
            }else if(statusType.equals("2")){
                mTvCardTitle.setText("取消预约");
            }
            mTvMonitValue.setText(startTime);
            mTvCoachRateValue.setText(appointMentProject);
            mTvSignTimeValue.setText(appointMentType);
        }
        EMMessage.Direct direct = message.direct();
        if (direct== EMMessage.Direct.RECEIVE) {
            if (messageType.equals("card")) {
                mRlCancelContractOrderRoot.setVisibility(View.GONE);
                mTvOperMsg.setVisibility(View.VISIBLE);
                switch (orderType) {
                    case "1":
                        tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                        mTvOperMsg.setVisibility(View.VISIBLE);
                        mTvOperMsg.setText("对方已同意");
                        break;
                    case "2":
                        ivStampIcon.setVisibility(View.GONE);
                        mTvOperMsg.setText("修改");
                        mTvOperMsg.setVisibility(View.GONE);
                        tvOrderReceivedUpdateBtn.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                        mTvOperMsg.setText("对方已拒绝");
                        tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                        mTvOperMsg.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            } else if (messageType.equals("terminationOrder")) {
                if (!TextUtils.isEmpty(orderType)) {
                    switch (orderType) {
                        case "1":
                            mRlCancelContractOrderRoot.setVisibility(View.GONE);
                            tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                            ivStampIcon.setVisibility(View.VISIBLE);
                            ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                            mTvOperMsg.setVisibility(View.VISIBLE);
                            mTvOperMsg.setText("对方已同意");
                            break;
                        case "2":
                            ivStampIcon.setVisibility(View.VISIBLE);
                            ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                            mTvOperMsg.setVisibility(View.VISIBLE);
                            mTvOperMsg.setText("对方已拒绝");
                            tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                            mRlCancelContractOrderRoot.setVisibility(View.GONE);
                            break;
                        case "3":
                            ivStampIcon.setVisibility(View.GONE);
                            mTvOperMsg.setVisibility(View.VISIBLE);
                            mTvOperMsg.setText("对方已撤销解约");
                            tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                            mRlCancelContractOrderRoot.setVisibility(View.GONE);

                            break;
                            default:
                    }
                }else{
                    mTvOperMsg.setVisibility(View.GONE);
                    ivStampIcon.setVisibility(View.GONE);
                    mRlCancelContractOrderRoot.setVisibility(View.VISIBLE);
                }


            }else if(messageType.equals("appointment")){
                ivStampIcon.setVisibility(View.GONE);
                mTvPriceValue.setVisibility(View.GONE);
                if (statusType.equals("1")) {
                  mTvOperMsg.setText("对方预约成功");
                }else if(statusType.equals("2")){
                    mTvOperMsg.setText("对方已取消预约");
                }
            }

        } else if (direct == EMMessage.Direct.SEND) {
            if (messageType.equals("terminationOrder")) {
                switch (orderType) {
                    case "1":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                        mTvCancelContractMsg.setVisibility(View.VISIBLE);
                        mTvOrderUpdateBtn.setVisibility(View.GONE);
                        mTvCancelContractMsg.setText("您已同意");
                        break;
                    case "2":
                        ivStampIcon.setVisibility(View.VISIBLE);
                        ivStampIcon.setImageResource(R.mipmap.bg_refuse_stamp);
                        mTvCancelContractMsg.setVisibility(View.VISIBLE);
                        mTvOrderUpdateBtn.setVisibility(View.GONE);
                        mTvCancelContractMsg.setText("您已拒绝");
                        break;
                    case "3":
                        ivStampIcon.setVisibility(View.GONE);
                        mTvOrderUpdateBtn.setVisibility(View.GONE);
                        mTvCancelContractMsg.setVisibility(View.VISIBLE);
                        mTvCancelContractMsg.setText("您已撤销解约");
                        break;
                    default:
                        mTvCancelContractMsg.setVisibility(View.VISIBLE);
                        mTvOrderUpdateBtn.setVisibility(View.GONE);
                        ivStampIcon.setVisibility(View.GONE);
                        mTvCancelContractMsg.setText("您已成功发起解约，等待对方确认");
                        break;
                }


            } else if (messageType.equals("card")) {
                mTvOrderUpdateBtn.setVisibility(View.VISIBLE);
                mTvCancelContractMsg.setVisibility(View.GONE);
                //修改
                mTvOrderUpdateBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //卡片发出点击修改跳转订单修改页面
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", orderId);
                        bundle.putString("singNO", singNO);
                        bundle.putString("status", "2");
                        startActivity(SigningDetailsActivity.class, bundle);
                    }
                });
            }


        }
        setBtnOperStatus();

    }

    /**
     * 设置操作按钮状态
     */
    private void setBtnOperStatus(){
        boolean isValid = message.getBooleanAttribute("isValid", false);
        if (isValid) {
            if (mTvOrderUpdateBtn!=null) {
                mTvOrderUpdateBtn.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
                mTvOrderUpdateBtn.setTextColor(ContextCompat.getColor(mContext,R.color.writeColor));
                mTvOrderUpdateBtn.setTag(1);
            }
            if (tvCancelContractAgreeBtn != null) {
                tvCancelContractAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                tvCancelContractAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                tvCancelContractAgreeBtn.setTag(1);
            }
            if (tvCancelContractRefuseBtn != null) {
                tvCancelContractRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_7a9eff_13);
                tvCancelContractRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_7a9eff));
                tvCancelContractRefuseBtn.setTag(1);
            }
            if (tvOrderReceivedUpdateBtn!=null) {
                tvOrderReceivedUpdateBtn.setBackgroundResource(R.drawable.bg_gradient_a6bffe_13);
                tvOrderReceivedUpdateBtn.setTextColor(ContextCompat.getColor(mContext, R.color.writeColor));
                tvOrderReceivedUpdateBtn.setTag(1);
            }
        }else{
            if (tvCancelContractAgreeBtn != null) {
                tvCancelContractAgreeBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                tvCancelContractAgreeBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                tvCancelContractAgreeBtn.setTag(0);
            }
            if (tvCancelContractRefuseBtn != null) {
                tvCancelContractRefuseBtn.setBackgroundResource(R.drawable.bg_oval_frame_999999_13);
                tvCancelContractRefuseBtn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                tvCancelContractRefuseBtn.setTag(0);
            }

            if (tvOrderReceivedUpdateBtn!=null) {
                tvOrderReceivedUpdateBtn.setBackgroundResource(R.drawable.bg_round_999999_15);
                tvOrderReceivedUpdateBtn.setTextColor(ContextCompat.getColor(mContext, R.color.writeColor));
                tvOrderReceivedUpdateBtn.setTag(0);
            }

            if (mTvOrderUpdateBtn!=null) {
                mTvOrderUpdateBtn.setBackgroundResource(R.drawable.bg_round_999999_15);
                mTvOrderUpdateBtn.setTextColor(ContextCompat.getColor(mContext,R.color.writeColor));
                mTvOrderUpdateBtn.setTag(0);
            }
        }
    }

    /**
     * 添加监听
     */
    private void addListener() {

        if (message.direct() == EMMessage.Direct.RECEIVE) {

            tvCancelContractAgreeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = tvCancelContractAgreeBtn.getTag();
                    if (tag!=null) {
                        String strTag = tag.toString();
                        if (!TextUtils.isEmpty(strTag)) {
                            int isValid = Integer.parseInt(strTag);
                            if(isValid==1){
                                nickName = message.getStringAttribute("nickName", "");
                                if (message.direct() == EMMessage.Direct.RECEIVE) {
                                    //处理同意解约逻辑
                                    final HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("loginDoctorPosition", "108.93425^34.23053");
                                    hashMap.put("mainDoctorCode", mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                                    hashMap.put("mainDoctorName", mViewSysUserDoctorInfoAndHospital.getUserName());
                                    hashMap.put("signCode", orderId);
                                    Log.e(TAG, "同意  订单号 "+orderId );
                                    hashMap.put("signNo", singNO);
                                    hashMap.put("mainPatientCode", message.getFrom());
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

                                            mHandler.sendEmptyMessage(1);
                                        }
                                    }.start();
                                }
                            }
                        }
                    }

                }
            });
            tvCancelContractRefuseBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = tvCancelContractRefuseBtn.getTag();
                    if (tag!=null) {
                        String strTag = tag.toString();
                    boolean isValid = message.getBooleanAttribute("isValid", false);
                    if(isValid){
                        OrderMessage orderMessage=new OrderMessage(nickName,mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(),orderId
                                ,monitoringType,coach,signUpTime,price,singNO,"2"
                                ,messageType,patientCode);
                        //处理拒绝解约逻辑  跳转页面
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", orderId);
                        bundle.putString("signNo", singNO);
                        bundle.putString("patientName", nickName);
                        bundle.putString("patientCode",message.getFrom() );
                        bundle.putSerializable("orderMsg",orderMessage);
                        startActivity(TerminationActivity.class, bundle);
                    }

                    }

                }
            });
            tvOrderReceivedUpdateBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValid = message.getBooleanAttribute("isValid", false);
                    if (isValid) {
                        //跳转修改页面
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", orderId);
                        bundle.putString("status", "2");
                        bundle.putString("singNO", singNO);
                        Log.e(TAG, "onClick:  订单编号   1111 "+orderId);
                        startActivity(SigningDetailsActivity.class, bundle);
                    }

                    }


            });


        } else if (message.direct() == EMMessage.Direct.SEND) {
            mTvOrderUpdateBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValid = message.getBooleanAttribute("isValid", false);
                    if (isValid) {
                        //跳转修改页面
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", orderId);
                        bundle.putString("status", "2");
                        bundle.putString("singNO", singNO);
                        Log.e(TAG, "onClick:  订单编号   333 "+orderId);
                        startActivity(SigningDetailsActivity.class, bundle);
                    }

                }
            });

        }

        mLLRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageType.equals("terminationOrder")) {
                    if (!orderType.equals("3")) {
                        //解约详情
                        String from="";
                        EMMessage.Direct direct = message.direct();
                        if(direct== EMMessage.Direct.RECEIVE) {
                            from="2";
                        }else if(direct==EMMessage.Direct.SEND){
                            from="1";
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", orderId);
                        bundle.putString("singNo", singNO);
                        bundle.putString("from",from);
                        bundle.putString("patientName", nickName);
                        bundle.putString("patientCode", patientCode);
                        bundle.putString("DoctorName", mViewSysUserDoctorInfoAndHospital.getUserName());
                        bundle.putString("DoctoCode", mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                        startActivity(CancellationActivity.class, bundle);
                    }

                } else if (messageType.equals("card")) {
                    //签约订单详情
                    Bundle bundle = new Bundle();
                    bundle.putString("singCode", orderId);
                    bundle.putString("signNo", singNO);
                    bundle.putString("patientName", nickName);
                    bundle.putString("patientCode", patientCode);
                    bundle.putString("status", "1");
                    startActivity(SigningDetailsActivity.class, bundle);
                }

            }
        });


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
                        if (!TextUtils.isEmpty(mNetRetStr)) {
                        NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            Toast.makeText(mContext, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "handleMessage: 类型type "+messageType);
                            EventBus.getDefault().post(new OrderMessage(nickName,imageUrl,orderId
                                    ,monitoringType,coach,signUpTime,price,singNO,"1",messageType,patientCode));
                        } else {
                            Toast.makeText(mContext, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                        break;

                }


            }
        };
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
        localIntent.setClass(mContext, paramClass);
        mContext.startActivity(localIntent);
    }


}
