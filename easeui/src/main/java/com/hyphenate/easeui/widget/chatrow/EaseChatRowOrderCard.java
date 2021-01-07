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
import com.hyphenate.easeui.jykj.activity.AppointOrderDetialActivity;
import com.hyphenate.easeui.jykj.activity.CancelAppointDetialActivity;
import com.hyphenate.easeui.jykj.activity.CancellationActivity;
import com.hyphenate.easeui.jykj.activity.SigningDetailsActivity;
import com.hyphenate.easeui.jykj.activity.TerminationActivity2;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.utils.SharedPreferences_DataSave;
import com.hyphenate.easeui.widget.EaseImageView;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;


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

    private RelativeLayout mRlCancelContractOrderRoot;
    private TextView tvCancelContractAgreeBtn;
    private TextView tvCancelContractRefuseBtn;
    private TextView tvOrderReceivedUpdateBtn;
    private Context mContext;
    private LinearLayout mLLRootView;
    private String signCode;
    private String orderCode;
    private String singNO;
    private String monitoringType;
    private String coach;
    private String signUpTime;
    private String price;
    private String orderType;
    private String messageType;

    private String receiveTime;
    private String endTime;
    private String surplusTimes;

    private String flagReplyType;

    private RelativeLayout rlMonitorType;
    private RelativeLayout rlMonitorRate;
    private RelativeLayout rlSignTime;

    private RelativeLayout rlAppointTime;
    private RelativeLayout rlCancelAppointTime;
    private RelativeLayout rlAppointProject;
    private RelativeLayout rlAppointType;
    private TextView tvAppointTimeValue;
    private TextView tvCancelAppointTimeValue;
    private TextView tvAppointProjectValue;
    private TextView tvAppointTypeValue;


    //接诊
    private RelativeLayout rlReceiveTime;
    private RelativeLayout rlEndTime;
    private RelativeLayout rlReceiveDoctor;
    private RelativeLayout rlSurplusTimes;
    private RelativeLayout rlSurplusDuration;
    private TextView tvReceiveTimeValue;
    private TextView tvEndTimeValue;
    private TextView tvReceivieDoctorValue;
    private TextView tvSurplusTimesValue;
    private TextView tvSurplusDurationValue;


    //预约
    private String statusType;
    private String startTime;
    private String cancelTime;
    private String appointMentProject;
    private String appointMentType;

    //病例
    private RelativeLayout rlMedicalPatient;
    private RelativeLayout rlMedicalEndTime;
    private RelativeLayout rlMedicalDoctor;
    private RelativeLayout rlPatientType;;
    private TextView tvPatientName;
    private TextView tvMedicalDoctor;
    private TextView tvPatientType;
    private TextView tvMedicalEndTime;
    private TextView tvSendBtn;

    //问诊
    private TextView tvDiagnosisMessage;
    private TextView tvImmediatelySeeBtn;
    private TextView tvMessageTypeValue;
    private RelativeLayout rlConsultationMessage;

    private String patientType;
    private String patientName;
    private ViewSysUserDoctorInfoAndHospital mViewSysUserDoctorInfoAndHospital;
    private String mNetRetStr;
    private Handler mHandler;
    private String nickName;
    protected Bundle fragmentArgs;
    private String patientCode;
    private String patientUrl;
    private String imageUrl;
    private String format;
    private String reserveCode;

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
        rlMonitorType=findViewById(R.id.rl_monitor_type);
        rlMonitorRate=findViewById(R.id.rl_monitor_rate);
        rlSignTime=findViewById(R.id.rl_sign_time);

        rlAppointTime=findViewById(R.id.rl_appoint_time);
        rlCancelAppointTime=findViewById(R.id.rl_cancel_appoint_time);
        rlAppointProject=findViewById(R.id.rl_appoint_project);
        rlAppointType=findViewById(R.id.rl_appoint_type);
        tvAppointTimeValue=findViewById(R.id.tv_appoint_time_value);
        tvCancelAppointTimeValue=findViewById(R.id.tv_cancel_appoint_time_value);
        tvAppointProjectValue=findViewById(R.id.tv_appoint_project_value);
        tvAppointTypeValue=findViewById(R.id.tv_appoint_type_value);

        rlReceiveTime=findViewById(R.id.rl_receive_time);
        rlEndTime=findViewById(R.id.rl_end_time);
        rlReceiveDoctor=findViewById(R.id.rl_receive_doctor);
        rlSurplusTimes=findViewById(R.id.rl_surplus_times);
        rlSurplusDuration=findViewById(R.id.rl_surplus_duration);
        tvReceiveTimeValue=findViewById(R.id.tv_receive_time_value);
        tvEndTimeValue=findViewById(R.id.tv_end_time_value);
        tvReceivieDoctorValue=findViewById(R.id.tv_receive_doctor_value);
        tvSurplusTimesValue=findViewById(R.id.tv_surplus_times_value);
        tvSurplusDurationValue=findViewById(R.id.tv_surplus_duration_value);
        rlMedicalPatient=findViewById(R.id.rl_medical_patient);
        rlMedicalEndTime=findViewById(R.id.rl_medical_end_time);
        rlMedicalDoctor=findViewById(R.id.rl_medical_doctor);
        rlPatientType=findViewById(R.id.rl_patient_type);
        tvPatientName=findViewById(R.id.tv_patient_name);
        tvMedicalDoctor=findViewById(R.id.tv_medical_doctor);
        tvPatientType=findViewById(R.id.tv_patient_type);
        tvMedicalEndTime=findViewById(R.id.tv_medical_end_time);
        tvSendBtn=findViewById(R.id.tv_send_btn);
        tvDiagnosisMessage=findViewById(R.id.tv_diagnosis_message);
        tvImmediatelySeeBtn=findViewById(R.id.tv_immediately_see_btn);
        rlConsultationMessage=findViewById(R.id.rl_consultation_message);
        tvMessageTypeValue=findViewById(R.id.tv_message_type_value);

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
        orderCode=message.getStringAttribute("orderId","");
        signCode = message.getStringAttribute("signId", "");
        singNO = message.getStringAttribute("singNo", "");
        monitoringType = message.getStringAttribute("monitoringType", "");
        coach = message.getStringAttribute("coach", "");
        signUpTime = message.getStringAttribute("signUpTime", "");
        price = message.getStringAttribute("price", "");
        messageType = message.getStringAttribute("messageType", "");

        patientCode = message.getStringAttribute("patientCode", "");
        if (TextUtils.isEmpty(patientCode)) {
            patientCode= message.getFrom();
        }
        nickName = message.getStringAttribute("nickName", "");
        imageUrl = message.getStringAttribute("imageUrl", "");
        patientUrl=message.getStringAttribute("patientUrl","");
        statusType=message.getStringAttribute("statusType","");
        startTime=message.getStringAttribute("startTime","");
        cancelTime=message.getStringAttribute("cancelTime","");
        appointMentProject=message.getStringAttribute("appointMentProject","");
        appointMentType=message.getStringAttribute("appointMentType","");
        receiveTime=message.getStringAttribute("receiveTime","");
        endTime=message.getStringAttribute("endTime","");
        surplusTimes=message.getStringAttribute("surplusTimes","");

        patientType=message.getStringAttribute("patientType","");
        patientName=message.getStringAttribute("patientName","");
        if (TextUtils.isEmpty(patientName)) {
            patientName=nickName;
        }
        flagReplyType=message.getStringAttribute("flagReplyType","");
        reserveCode=message.getStringAttribute("reserveCode","");
        mTvMonitValue.setText(monitoringType);
        mTvCoachRateValue.setText(coach);
        mTvSignTimeValue.setText(signUpTime);
        mTvPriceValue.setText(String.format("¥ %s", price));
        orderType = message.getStringAttribute("orderType", "");//1已同意 2 修改 3 拒绝（由患者操作发起时会携带此参数）
        switch (messageType) {
            case "terminationOrder":
                mTvCardTitle.setText("解约订单");
                rlMonitorType.setVisibility(View.VISIBLE);
                rlMonitorRate.setVisibility(View.VISIBLE);
                rlSignTime.setVisibility(View.VISIBLE);

                rlAppointTime.setVisibility(View.GONE);
                rlCancelAppointTime.setVisibility(View.GONE);
                rlAppointProject.setVisibility(View.GONE);
                rlAppointType.setVisibility(View.GONE);

                rlReceiveTime.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.GONE);
                rlReceiveDoctor.setVisibility(View.GONE);
                rlSurplusTimes.setVisibility(View.GONE);
                rlSurplusDuration.setVisibility(View.GONE);

                rlMedicalPatient.setVisibility(View.GONE);
                rlMedicalEndTime.setVisibility(View.GONE);
                rlMedicalDoctor.setVisibility(View.GONE);
                rlPatientType.setVisibility(View.GONE);
                tvSendBtn.setVisibility(View.GONE);
                tvDiagnosisMessage.setVisibility(View.GONE);
                tvImmediatelySeeBtn.setVisibility(View.GONE);


                break;
            case "card":
                mTvCardTitle.setText("签约订单");
                rlMonitorType.setVisibility(View.VISIBLE);
                rlMonitorRate.setVisibility(View.VISIBLE);
                rlSignTime.setVisibility(View.VISIBLE);

                rlAppointTime.setVisibility(View.GONE);
                rlCancelAppointTime.setVisibility(View.GONE);
                rlAppointProject.setVisibility(View.GONE);
                rlAppointType.setVisibility(View.GONE);

                rlReceiveTime.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.GONE);
                rlReceiveDoctor.setVisibility(View.GONE);
                rlSurplusTimes.setVisibility(View.GONE);
                rlSurplusDuration.setVisibility(View.GONE);

                rlMedicalPatient.setVisibility(View.GONE);
                rlMedicalEndTime.setVisibility(View.GONE);
                rlMedicalDoctor.setVisibility(View.GONE);
                rlPatientType.setVisibility(View.GONE);
                tvSendBtn.setVisibility(View.GONE);
                tvDiagnosisMessage.setVisibility(View.GONE);
                tvImmediatelySeeBtn.setVisibility(View.GONE);

                break;
            case "appointment":
                rlMonitorType.setVisibility(View.GONE);
                rlMonitorRate.setVisibility(View.GONE);
                rlSignTime.setVisibility(View.GONE);

                rlAppointTime.setVisibility(View.VISIBLE);
                rlCancelAppointTime.setVisibility(View.VISIBLE);
                rlAppointProject.setVisibility(View.VISIBLE);
                rlAppointType.setVisibility(View.VISIBLE);

                rlReceiveTime.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.GONE);
                rlReceiveDoctor.setVisibility(View.GONE);
                rlSurplusTimes.setVisibility(View.GONE);
                rlSurplusDuration.setVisibility(View.GONE);

                rlMedicalPatient.setVisibility(View.GONE);
                rlMedicalEndTime.setVisibility(View.GONE);
                rlMedicalDoctor.setVisibility(View.GONE);
                rlPatientType.setVisibility(View.GONE);
                tvSendBtn.setVisibility(View.GONE);
                tvDiagnosisMessage.setVisibility(View.GONE);
                tvImmediatelySeeBtn.setVisibility(View.GONE);
                tvAppointTimeValue.setText(startTime);
                tvAppointProjectValue.setText(appointMentProject);
                tvAppointTypeValue.setText(appointMentType);
                tvCancelAppointTimeValue.setText(cancelTime);
                if (statusType.equals("1")) {
                    mTvCardTitle.setText("预约成功");
                } else if (statusType.equals("2")) {
                    mTvCardTitle.setText("取消预约");
                }
                break;
            case "receiveTreatment":
                rlMonitorType.setVisibility(View.GONE);
                rlMonitorRate.setVisibility(View.GONE);
                rlSignTime.setVisibility(View.GONE);

                rlAppointTime.setVisibility(View.GONE);
                rlCancelAppointTime.setVisibility(View.GONE);
                rlAppointProject.setVisibility(View.GONE);
                rlAppointType.setVisibility(View.GONE);



                rlEndTime.setVisibility(View.VISIBLE);
                rlSurplusTimes.setVisibility(View.VISIBLE);
                rlSurplusDuration.setVisibility(View.VISIBLE);

                rlMedicalPatient.setVisibility(View.GONE);
                rlMedicalEndTime.setVisibility(View.GONE);
                rlMedicalDoctor.setVisibility(View.GONE);
                rlPatientType.setVisibility(View.GONE);
                tvSendBtn.setVisibility(View.GONE);
                tvDiagnosisMessage.setVisibility(View.GONE);
                tvImmediatelySeeBtn.setVisibility(View.GONE);
                tvReceiveTimeValue.setText(receiveTime);
                tvEndTimeValue.setText(endTime);
                switch (appointMentProject) {
                    case "10":
                        mTvCardTitle.setText("图文");
                        break;
                    case "20":
                        mTvCardTitle.setText("音频");
                        break;
                    case "30":
                        mTvCardTitle.setText("视频");
                        break;
                    case "40":
                        mTvCardTitle.setText("电话");
                        break;
                    default:
                }
                if (appointMentProject.equals("10")) {
                    rlReceiveDoctor.setVisibility(View.VISIBLE);
                    rlSurplusTimes.setVisibility(View.VISIBLE);
                    rlSurplusDuration.setVisibility(View.GONE);
                    rlReceiveTime.setVisibility(View.VISIBLE);
                    tvSurplusTimesValue.setText(surplusTimes);
                }else{
                    rlReceiveTime.setVisibility(View.GONE);
                    rlReceiveDoctor.setVisibility(View.GONE);
                    rlSurplusTimes.setVisibility(View.GONE);
                    rlSurplusDuration.setVisibility(View.VISIBLE);
                    tvSurplusDurationValue.setText(surplusTimes);
                }
                tvReceivieDoctorValue.setText(mViewSysUserDoctorInfoAndHospital.getUserName());
                break;
            case "medicalRecord":
                mTvCardTitle.setText("病历");
                rlMonitorType.setVisibility(View.GONE);
                rlMonitorRate.setVisibility(View.GONE);
                rlSignTime.setVisibility(View.GONE);
                rlReceiveDoctor.setVisibility(View.GONE);
                rlAppointTime.setVisibility(View.GONE);
                rlCancelAppointTime.setVisibility(View.GONE);
                rlAppointProject.setVisibility(View.GONE);
                rlAppointType.setVisibility(View.GONE);
                mTvCancelContractMsg.setVisibility(View.GONE);
                rlReceiveTime.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.GONE);
                rlSurplusTimes.setVisibility(View.GONE);
                rlSurplusDuration.setVisibility(View.GONE);
                mTvOrderUpdateBtn.setVisibility(View.GONE);
                mTvPriceValue.setVisibility(View.GONE);

                rlMedicalPatient.setVisibility(View.VISIBLE);
                rlMedicalEndTime.setVisibility(View.VISIBLE);
                rlMedicalDoctor.setVisibility(View.VISIBLE);
                rlPatientType.setVisibility(View.VISIBLE);
                tvSendBtn.setVisibility(View.VISIBLE);
                tvDiagnosisMessage.setVisibility(View.GONE);
                tvImmediatelySeeBtn.setVisibility(View.GONE);
                tvPatientName.setText(patientName);
                tvMedicalEndTime.setText(endTime);
                tvMedicalDoctor.setText(mViewSysUserDoctorInfoAndHospital.getUserName());
                tvPatientType.setText(patientType);

                break;
            case "MessageAfterDiagnosis":
                mTvCardTitle.setText("诊后留言");
                rlMonitorType.setVisibility(View.GONE);
                rlMonitorRate.setVisibility(View.GONE);
                rlSignTime.setVisibility(View.GONE);
                rlReceiveDoctor.setVisibility(View.GONE);
                rlAppointTime.setVisibility(View.GONE);
                rlCancelAppointTime.setVisibility(View.GONE);
                rlAppointProject.setVisibility(View.GONE);
                rlAppointType.setVisibility(View.GONE);
               // mTvCancelContractMsg.setVisibility(View.GONE);
                rlReceiveTime.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.GONE);
                rlSurplusTimes.setVisibility(View.GONE);
                rlSurplusDuration.setVisibility(View.GONE);
                //mTvOrderUpdateBtn.setVisibility(View.GONE);
                mTvPriceValue.setVisibility(View.GONE);
                if (mTvOperMsg!=null) {
                    mTvOperMsg.setVisibility(View.GONE);
                }
                rlMedicalPatient.setVisibility(View.GONE);
                rlMedicalEndTime.setVisibility(View.GONE);
                rlMedicalDoctor.setVisibility(View.GONE);
                rlPatientType.setVisibility(View.GONE);
                tvSendBtn.setVisibility(View.GONE);
                tvDiagnosisMessage.setVisibility(View.VISIBLE);
                tvImmediatelySeeBtn.setVisibility(View.VISIBLE);

                 break;

            default:

        }

        EMMessage.Direct direct = message.direct();
        if (direct== EMMessage.Direct.RECEIVE) {
            switch (messageType) {
                case "card":
                    mRlCancelContractOrderRoot.setVisibility(View.GONE);
                    mTvOperMsg.setVisibility(View.VISIBLE);
                    mTvPriceValue.setVisibility(View.VISIBLE);
                    switch (orderType) {
                        case "1":
                            tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                            ivStampIcon.setVisibility(View.VISIBLE);
                            ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                            mTvOperMsg.setVisibility(View.VISIBLE);
                            mTvOperMsg.setText("对方已同意");
                            //EventBus.getDefault().post(new OrderMessage());
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
                    break;
                case "terminationOrder":
                    if (!TextUtils.isEmpty(orderType)) {
                        switch (orderType) {
                            case "1":
                                mRlCancelContractOrderRoot.setVisibility(View.GONE);
                                tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                                ivStampIcon.setVisibility(View.VISIBLE);
                                ivStampIcon.setImageResource(R.mipmap.bg_agree_stamp);
                                mTvOperMsg.setVisibility(View.VISIBLE);
                                mTvOperMsg.setText("对方已同意");
                               // EventBus.getDefault().post(new OrderMessage());
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
                    } else {
                        mTvOperMsg.setVisibility(View.GONE);
                        ivStampIcon.setVisibility(View.GONE);
                        tvOrderReceivedUpdateBtn.setVisibility(View.GONE);
                        mRlCancelContractOrderRoot.setVisibility(View.VISIBLE);
                    }


                    break;
                case "appointment":
                    mRlCancelContractOrderRoot.setVisibility(View.GONE);
                    ivStampIcon.setVisibility(View.GONE);
                    mTvPriceValue.setVisibility(View.GONE);
                    if (statusType.equals("1")) {
                        mTvOperMsg.setText("对方预约成功");
                    } else if (statusType.equals("2")) {
                        mTvOperMsg.setText("对方已取消预约");
                    }
                    break;
                case "MessageAfterDiagnosis":
                    mRlCancelContractOrderRoot.setVisibility(View.GONE);
                    tvImmediatelySeeBtn.setText("立即回复");
                    ivStampIcon.setVisibility(View.GONE);
                    tvDiagnosisMessage.setText(String.format("患者%s向您发起诊后留言,请及时回复", nickName));
                    break;
            }

        } else if (direct == EMMessage.Direct.SEND) {
            switch (messageType) {
                case "terminationOrder":
                    rlConsultationMessage.setVisibility(View.GONE);
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


                    break;
                case "card":
                    mTvOrderUpdateBtn.setVisibility(View.VISIBLE);
                    mTvCancelContractMsg.setVisibility(View.GONE);
                    mTvPriceValue.setVisibility(View.VISIBLE);
                    rlConsultationMessage.setVisibility(View.GONE);
                    //修改
                    mTvOrderUpdateBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Object tag = mTvOrderUpdateBtn.getTag();
                            if (tag!=null) {
                                String strTag = tag.toString();
                                if (!TextUtils.isEmpty(strTag)) {
                                    int isValid = Integer.parseInt(strTag);
                                    if(isValid==1){
                                        //卡片发出点击修改跳转订单修改页面
                                        Bundle bundle = new Bundle();
                                        bundle.putString("signCode", signCode);
                                        bundle.putString("orderCode", orderCode);
                                        bundle.putString("patientName", nickName);
                                        bundle.putString("patientCode", patientCode);
                                        bundle.putString("singNO", singNO);
                                        bundle.putString("status", "2");
                                        bundle.putString("doctorUrl", imageUrl);
                                        bundle.putString("patientUrl", Constant.patientUrl);
                                        startActivity(SigningDetailsActivity.class, bundle);
                                    }
                                }
                            }

                        }
                    });
                    break;
                case "appointment":
                    rlConsultationMessage.setVisibility(View.GONE);
                    mTvOrderUpdateBtn.setVisibility(View.GONE);
                    ivStampIcon.setVisibility(View.GONE);
                    mTvPriceValue.setVisibility(View.GONE);
                    mTvCancelContractMsg.setVisibility(View.VISIBLE);

                    if (statusType.equals("1")) {
                        mTvCancelContractMsg.setText("您已预约成功");
                    } else if (statusType.equals("2")) {
                        mTvCancelContractMsg.setText("您已取消预约");
                    }
                    break;
                case "receiveTreatment":
                    rlConsultationMessage.setVisibility(View.GONE);
                    mTvOrderUpdateBtn.setVisibility(View.GONE);
                    ivStampIcon.setVisibility(View.GONE);
                    mTvPriceValue.setVisibility(View.GONE);
                    mTvCancelContractMsg.setVisibility(View.VISIBLE);
                    mTvCancelContractMsg.setText("您已接诊");
                    break;
                case "MessageAfterDiagnosis":
                    tvImmediatelySeeBtn.setText("立即查看");
                    rlConsultationMessage.setVisibility(View.VISIBLE);
                    switch (flagReplyType) {
                        case "1":
                            tvMessageTypeValue.setText("正常");
                            tvMessageTypeValue.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                            break;
                        case "2":
                            tvMessageTypeValue.setText("一般");
                            tvMessageTypeValue.setTextColor(ContextCompat.getColor(mContext, R.color.color_00C017));

                            break;
                        case "3":
                            tvMessageTypeValue.setText("紧急");
                            tvMessageTypeValue.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF6600));
                            break;
                        case "4":
                            tvMessageTypeValue.setText("重大紧急");
                            tvMessageTypeValue.setTextColor(ContextCompat.getColor(mContext, R.color.color_D70000));
                            break;
                        default:
                    }


                    break;
                    default:
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

            tvCancelContractAgreeBtn.setOnClickListener(new OnClickListener()   {
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
                                    hashMap.put("signCode", signCode);
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
                    boolean isValid = message.getBooleanAttribute("isValid", false);
                    if(isValid){
                        OrderMessage orderMessage=new OrderMessage(nickName
                                ,mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(),orderCode,signCode,
                                monitoringType,coach,signUpTime,price,singNO,"2"
                                ,messageType,patientCode);
                        //处理拒绝解约逻辑  跳转页面
                        Bundle bundle = new Bundle();
                        bundle.putString("singCode", signCode);
                        bundle.putString("signNo", singNO);
                        bundle.putString("patientName", nickName);
                        bundle.putString("patientCode",message.getFrom() );
                        bundle.putSerializable("orderMsg",orderMessage);
                        startActivity(TerminationActivity2.class, bundle);
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
                        bundle.putString("signCode", signCode);
                        bundle.putString("orderCode",orderCode);
                        bundle.putString("patientName", nickName);
                        bundle.putString("patientCode", patientCode);
                        bundle.putString("status", "2");
                        bundle.putString("singNO", singNO);
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
                        bundle.putString("signCode", signCode);
                        bundle.putString("orderCode",orderCode);
                        bundle.putString("patientName", nickName);
                        bundle.putString("patientCode", patientCode);
                        bundle.putString("status", "2");
                        bundle.putString("singNO", singNO);
                        startActivity(SigningDetailsActivity.class, bundle);
                    }

                }
            });

        }

        mLLRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (messageType) {
                    case "terminationOrder":
                        if (!orderType.equals("3")) {
                            //解约详情
                            String from = "";
                            EMMessage.Direct direct = message.direct();
                            if (direct == EMMessage.Direct.RECEIVE) {
                                from = "2";
                            } else if (direct == EMMessage.Direct.SEND) {
                                from = "1";
                            }
                            boolean isValid = message.getBooleanAttribute("isValid", false);
                            Bundle bundle = new Bundle();
                            bundle.putString("orderCode", orderCode);
                            bundle.putString("singNo", singNO);
                            bundle.putString("orderType",orderType);
                            bundle.putString("from", from);
                            bundle.putString("patientName", nickName);
                            bundle.putString("patientCode", patientCode);
                            bundle.putString("signCode",signCode);
                            bundle.putBoolean("isValid",isValid);
                            boolean isVisible=false;
                            if (mRlCancelContractOrderRoot!=null) {
                                if (mRlCancelContractOrderRoot.getVisibility()== View.VISIBLE) {
                                    isVisible=true;
                                }else{
                                    isVisible=false;
                                }
                            }

                            bundle.putBoolean("isVisibleButtomRoot",isVisible);
                            bundle.putString("DoctorName", mViewSysUserDoctorInfoAndHospital.getUserName());
                            bundle.putString("DoctoCode", mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                            startActivity(CancellationActivity.class, bundle);
                        }

                        break;
                    case "card": {
                        if (orderType.equals("3")) {
                            boolean isValid = message.getBooleanAttribute("isValid", false);
                            Bundle bundle = new Bundle();
                            bundle.putString("orderCode", orderCode);
                            bundle.putString("singNo", singNO);
                            bundle.putString("signCode",signCode);
                            bundle.putString("from", "2");
                            bundle.putBoolean("isValid",isValid);
                            bundle.putString("orderType",orderType);
                            bundle.putString("patientName", nickName);
                            bundle.putString("patientCode", patientCode);
                            bundle.putString("DoctorName", mViewSysUserDoctorInfoAndHospital.getUserName());
                            bundle.putString("DoctoCode", mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                            boolean isVisible=false;
                            if (mRlCancelContractOrderRoot!=null) {
                                if (mRlCancelContractOrderRoot.getVisibility()== View.VISIBLE) {
                                    isVisible=true;
                                }else{
                                    isVisible=false;
                                }
                            }
                            bundle.putBoolean("isVisibleButtomRoot",isVisible);
                            startActivity(CancellationActivity.class, bundle);
                        } else {
                            //签约订单详情
                            Bundle bundle = new Bundle();
                            bundle.putString("orderCode", orderCode);
                            bundle.putString("signCode",signCode);
                            bundle.putString("singNO", singNO);
                            bundle.putString("patientName", nickName);
                            bundle.putString("patientCode", patientCode);
                            bundle.putString("orderCode",orderCode);
                            bundle.putString("status", "1");
                            startActivity(SigningDetailsActivity.class, bundle);
                        }
                        break;
                    }
                    case "appointment": {
                        Bundle bundle = new Bundle();
                        bundle.putString("reserveCode", StringUtils.isNotEmpty(reserveCode)?reserveCode:orderCode);
                        startActivity(CancelAppointDetialActivity.class, bundle);
                        break;
                    }
                    case "receiveTreatment":{
                        Bundle bundle=new Bundle();
                        bundle.putString("reserveCode",StringUtils.isNotEmpty(reserveCode)?reserveCode:orderCode);
                        startActivity(AppointOrderDetialActivity.class,bundle);

                        break;
                    }
                    case "medicalRecord": {
                        Bundle bundle = new Bundle();
                        bundle.putString("orderCode", orderCode);
                        bundle.putString("patientCode", message.getTo());
                        bundle.putString("patientName", patientName);
                        bundle.putBoolean("isEdit",true);
                        Intent intent = new Intent();
                        intent.setAction("www.jykj.com.jykj_zxyl." +
                                "medicalrecord.activity.PatientRecordActivity");
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                        break;
                    }
                    case "MessageAfterDiagnosis":{
                        Bundle bundle = new Bundle();
                        bundle.putString("orderCode", orderCode);
                        bundle.putString("patientCode", message.getTo());
                        bundle.putString("patientName", patientName);
                        bundle.putBoolean("isFromCard",true);
                        Intent intent = new Intent();
                        intent.setAction("www.jykj.com.jykj_zxyl.personal.activity.DiagnosisReplayActivity");
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);

                        break;
                    }

                    default:
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
                            EventBus.getDefault().post(new OrderMessage(nickName,imageUrl,orderCode,signCode,
                                    monitoringType,coach,signUpTime,price,singNO,"1",messageType,patientCode));
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
