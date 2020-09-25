package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.huxq17.floatball.libarary.FloatBallManager;
import com.huxq17.floatball.libarary.floatball.FloatBallCfg;
import com.huxq17.floatball.libarary.menu.FloatMenuCfg;
import com.huxq17.floatball.libarary.menu.MenuItem;
import com.huxq17.floatball.libarary.utils.BackGroudSeletor;
import com.huxq17.floatball.libarary.utils.DensityUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.HashMap;
import java.util.Objects;

import netService.HttpNetService;
import netService.entity.NetRetEntity;
import util.ActivityStackManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CheckImResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.base_dialog.MedcalRecordDialog;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.InspectionOrderListActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.PrescriptionNotesListActivity;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * 聊天界面
 */
public class ChatActivity extends BaseActivity {

    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private Context mContext;
    private ChatActivity mActivity;
    private EaseTitleBar titleBar;
    private EaseChatMessageList messageList;
    private EaseChatInputMenu inputMenu;
    private EaseChatFragment chatFragment;
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private OrderMessage orderMessage;
    private FloatBallManager mFloatballManager;
    private boolean isfull = false;
    private MedcalRecordDialog medcalRecordDialog;
    private ImageView ivTransparent;
    private SparseArray<String> stringSparseArray;
    private String orderCode;
    private CheckImResultBean checkImResultBean;
    private PatientRecordDetBean patientRecordDetBean;
    private String operType="";
    private String userCode;
    private String userName;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_chat;
    }


    @Override
    protected void initView() {
        super.initView();
        ivTransparent=findViewById(R.id.iv_transparent);
        ActivityStackManager.getInstance().add(this);
        ActivityUtil.setStatusBarMain(ChatActivity.this);
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            orderMessage =(OrderMessage)extras.getSerializable("orderMsg");
        }
        medcalRecordDialog=new MedcalRecordDialog(this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        stringSparseArray= new SparseArray<>();
        String chatType = getIntent().getStringExtra("chatType");
//        initLayout();
        //new出EaseChatFragment或其子类的实例
        chatFragment = new EaseChatFragment();
        userCode = getIntent().getStringExtra("userCode");
        userName = getIntent().getStringExtra("userName");

        String usersName = getIntent().getStringExtra("usersName");
        String userUrl = getIntent().getStringExtra("userUrl");

        String loginDoctorPosition = getIntent().getStringExtra("loginDoctorPosition");
        String operDoctorCode = getIntent().getStringExtra("operDoctorCode");
        String operDoctorName = getIntent().getStringExtra("operDoctorName");
        orderCode = getIntent().getStringExtra("orderCode");

        String doctorUrl = getIntent().getStringExtra("doctorUrl");

        //患者
        String patientAlias = getIntent().getStringExtra("patientAlias");
        Log.e("TAG", "onCreate: 患者姓名"+patientAlias );
        String patientCode = getIntent().getStringExtra("patientCode");
        String patientAge = getIntent().getStringExtra("patientAge");
        Log.e("tag", "onCreate: 患者"+patientAge );
        String patientSex = getIntent().getStringExtra("patientSex");
        String patientUrl = mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl();


        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userCode);
        args.putString(EaseConstant.EXTRA_USER_NAME, userName);
        args.putString("date", getIntent().getStringExtra("date"));
        args.putString("loginDoctorPosition", loginDoctorPosition);
        args.putString("operDoctorCode", operDoctorCode);
        args.putString("operDoctorName", operDoctorName);
        args.putString("orderCode", orderCode);
        args.putString("usersName", usersName);
        args.putString("userUrl", userUrl);
        args.putSerializable("orderMessage",orderMessage);
        args.putString("doctorUrl", doctorUrl);
        args.putString("Url", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());

        args.putString("patientAlias", patientAlias);
        args.putString("patientCode",patientCode);
        args.putString("patientAge",patientAge);
        args.putString("patientSex",patientSex);
        Log.e("tag", "onCreate: 患者"+patientAge );
        args.putInt(EaseConstant.EXTRA_MESSAGE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_MESSAGE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VOICE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VOICE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VEDIO_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VEDIO_NUM, 0));
        args.putString("chatType", chatType);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        //   SavePreferences.setData("isNewMsg",false);
        getTime(orderCode,"1","1","1");
        initHandler();
        boolean showMenu = true;//换成false试试
        initSinglePageFloatball(showMenu);
        //5 如果没有添加菜单，可以设置悬浮球点击事件
        mFloatballManager.setOnFloatBallClickListener(new FloatBallManager.OnFloatBallClickListener() {
            @Override
            public void onFloatBallClick() {
                ivTransparent.setVisibility(View.VISIBLE);
                Drawable ballIcon = BackGroudSeletor.getdrawble("bg_fs"
                        , ChatActivity.this);
                mFloatballManager.updateFloatBallBg(ballIcon);
            }

            @Override
            public void onOutSideDismiss() {
                ivTransparent.setVisibility(View.GONE);
                Drawable ballIcon = BackGroudSeletor.getdrawble("bg_bl"
                        , ChatActivity.this);
                mFloatballManager.updateFloatBallBg(ballIcon);
                if(StringUtils.isNotEmpty(orderCode)){
                    sendMedicalRecordRequest(orderCode);
                }

            }
        });
        sendGetCheckRequest(userCode,userName);
        //添加监听
        addListener();
    }



    /**
     * 添加监听
     */
    private void addListener(){
        medcalRecordDialog.setOnClickListener((msg, contentType) -> {

            stringSparseArray.put(contentType,msg);
            setMenueStatus(contentType);
            sendSaveMedicalRecordRequest(orderCode,operType,msg,msg,msg,msg,msg,msg,msg);
        });

    }

    /**
     * 设置菜单专题
     * @param contentType 类型
     */
    private void setMenueStatus(int contentType){
        switch (contentType) {
            case MedcalRecordDialog.CHIEF_COMPLAINT_TYPE:
                Drawable bg_zs_choosed = BackGroudSeletor.getdrawble("bg_zs_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(0,bg_zs_choosed);
                operType="1";
                break;
            case MedcalRecordDialog.HISTORY_NEW_TYPE:
                Drawable bg_xbs_choosed = BackGroudSeletor.getdrawble("bg_xbs_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(1,bg_xbs_choosed);
                operType="2";
                break;
            case MedcalRecordDialog.HISTORY_PAST_TYPE:
                Drawable bg_jws_choosed = BackGroudSeletor.getdrawble("bg_jws_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(2,bg_jws_choosed);
                operType="3";
                break;
            case MedcalRecordDialog.HISTORY_ALLERGY_TYPE:
                Drawable bg_gms_choosed = BackGroudSeletor.getdrawble("bg_gms_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(4,bg_gms_choosed);
                operType="4";
                break;
            case MedcalRecordDialog.MEDICAL_EXAMINATION_TYPE:
                Drawable bg_ct_choosed = BackGroudSeletor.getdrawble("bg_ct_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(3,bg_ct_choosed);
                operType="5";
                break;
            case MedcalRecordDialog.TREATMENTPROPOSAL_TYPE:
                Drawable bg_zljy_choosed = BackGroudSeletor.getdrawble("bg_zljy_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(5,bg_zljy_choosed);
                operType="6";
                break;
            case MedcalRecordDialog.INSPECTION_TYPE:
                Drawable bg_jcjy_choosed = BackGroudSeletor.getdrawble("bg_jcjy_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(6,bg_jcjy_choosed);
                break;
            case MedcalRecordDialog.PRESCRIPTION_NOTES_TYPE:
                Drawable bg_cfj_choosed = BackGroudSeletor.getdrawble("bg_cfj_choosed",
                        ChatActivity.this);
                mFloatballManager.updateMenuItem(7,bg_cfj_choosed);
                break;
            default:

        }
    }


    private void addFloatMenuItem() {
        MenuItem itemZS = new MenuItem(BackGroudSeletor.getdrawble("bg_zs_normal",
                this),"主诉") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int chiefComplaintType = MedcalRecordDialog.CHIEF_COMPLAINT_TYPE;
                String content = stringSparseArray.get(chiefComplaintType);
                String patientChiefComplaint="";
                if (patientRecordDetBean!=null) {
                    patientChiefComplaint=patientRecordDetBean.getPatientChiefComplaint();;
                    content= TextUtils.isEmpty(content)
                            ?patientRecordDetBean.getChiefComplaint():content;
                }
                medcalRecordDialog.updateData(
                        chiefComplaintType,"主诉",patientChiefComplaint,content);
                setMenueStatus(chiefComplaintType);
            }
        };
        MenuItem itemXBS = new MenuItem(BackGroudSeletor.getdrawble("bg_xbs_normal",
                this),"现病史") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int historyNewType = MedcalRecordDialog.HISTORY_NEW_TYPE;
                String content = stringSparseArray.get(historyNewType);
                String patientHistoryNew="";
                if (patientRecordDetBean!=null) {
                     patientHistoryNew = patientRecordDetBean.getPatientHistoryNew();
                     content=TextUtils.isEmpty(content)
                             ?patientRecordDetBean.getHistoryNew():content;
                }
                medcalRecordDialog.updateData(
                        historyNewType,"现病史",patientHistoryNew,content);

                setMenueStatus(historyNewType);
            }
        };
        MenuItem itemJWS = new MenuItem(BackGroudSeletor.getdrawble("bg_jws_normal",
                this),"既往史") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int historyPastType = MedcalRecordDialog.HISTORY_PAST_TYPE;
                String content = stringSparseArray.get(historyPastType);
                String patientHistoryPast="";
                if (patientRecordDetBean != null) {
                    patientHistoryPast = patientRecordDetBean.getPatientHistoryPast();
                    content = TextUtils.isEmpty(content)
                            ?patientRecordDetBean.getHistoryPast():content;
                }
                medcalRecordDialog.updateData(
                        historyPastType,"既往史",patientHistoryPast,content);
                setMenueStatus(historyPastType);
            }
        };
        MenuItem itemCT = new MenuItem(BackGroudSeletor.getdrawble("bg_ct_normal",
                this),"查体") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int medicalexaminationType = MedcalRecordDialog.MEDICAL_EXAMINATION_TYPE;
                String content = stringSparseArray.get(medicalexaminationType);
                if (patientRecordDetBean!=null) {
                    content=TextUtils.isEmpty(content)
                            ?patientRecordDetBean.getMedicalExamination():content;
                }
                medcalRecordDialog.updateData(
                        medicalexaminationType,"查体","",content);
                setMenueStatus(medicalexaminationType);
            }
        };
        MenuItem itemGMS = new MenuItem(BackGroudSeletor.getdrawble("bg_gms_normal",
                this),"过敏史") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int historyAllergyType = MedcalRecordDialog.HISTORY_ALLERGY_TYPE;
                String content = stringSparseArray.get(historyAllergyType);
                String patientHistoryAllergy="";
                if (patientRecordDetBean != null) {
                    patientHistoryAllergy = patientRecordDetBean.getPatientHistoryAllergy();
                    content = TextUtils.isEmpty(content)
                            ? patientRecordDetBean.getHistoryAllergy() : content;
                }
                medcalRecordDialog.updateData(
                        historyAllergyType,"过敏史",patientHistoryAllergy,content);

            }
        };
        MenuItem itemZLJY = new MenuItem(BackGroudSeletor.getdrawble("bg_zljy_normal",
                this),"治疗建议") {
            @Override
            public void action() {
                medcalRecordDialog.show();
                int treatmentproposalType = MedcalRecordDialog.TREATMENTPROPOSAL_TYPE;
                String content = stringSparseArray.get(treatmentproposalType);
                if (patientRecordDetBean!=null) {
                    content=TextUtils.isEmpty(content)
                            ?patientRecordDetBean.getTreatmentProposal():content;
                }
                medcalRecordDialog.updateData(
                        treatmentproposalType,"治疗建议","",content);
            }
        };

        MenuItem itemJCJY = new MenuItem(BackGroudSeletor.getdrawble("bg_jcjy_normal",
                this),"检查检验") {
            @Override
            public void action() {
                Bundle inspectionBundle=new Bundle();
                inspectionBundle.putString("patientCode",userCode);
                inspectionBundle.putString("patientName",userName);
                inspectionBundle.putString("orderId",orderCode);
                startActivity(InspectionOrderListActivity.class,inspectionBundle,100);
            }
        };

        MenuItem itemCFJ = new MenuItem(BackGroudSeletor.getdrawble("bg_cfj_normal",
                this),"处方笺") {
            @Override
            public void action() {
                Bundle prescriptionBundle=new Bundle();
                prescriptionBundle.putString("patientCode",userCode);
                prescriptionBundle.putString("patientName",userCode);
                prescriptionBundle.putString("orderId",orderCode);
                startActivity(PrescriptionNotesListActivity.class,prescriptionBundle,100);

            }
        };


        mFloatballManager.addMenuItem(itemZS)
                .addMenuItem(itemXBS)
                .addMenuItem(itemJWS)
                .addMenuItem(itemCT)
                .addMenuItem(itemGMS)
                .addMenuItem(itemZLJY)
                .addMenuItem(itemJCJY)
                .addMenuItem(itemCFJ)
                //.addMenuItem(itemBL)
                .buildMenu();
    }

    /**
     * 设置选中状态
     * @param patientRecordDetBean
     */
    private void initChoosedStatus(PatientRecordDetBean patientRecordDetBean){
        int flagWriteChiefComplaint = patientRecordDetBean.getFlagWriteChiefComplaint();
        if(flagWriteChiefComplaint==1){
            setMenueStatus(MedcalRecordDialog.CHIEF_COMPLAINT_TYPE);
        }
        int flagWriteHistoryNew = patientRecordDetBean.getFlagWriteHistoryNew();
        if(flagWriteHistoryNew==1){
            setMenueStatus(MedcalRecordDialog.HISTORY_NEW_TYPE);
        }
        int flagWriteHistoryPast = patientRecordDetBean.getFlagWriteHistoryPast();
        if (flagWriteHistoryPast==1) {
            setMenueStatus(MedcalRecordDialog.HISTORY_PAST_TYPE);
        }
        int flagHistoryAllergy = patientRecordDetBean.getFlagHistoryAllergy();
        if(flagHistoryAllergy==1){
            setMenueStatus(MedcalRecordDialog.HISTORY_ALLERGY_TYPE);
        }
        int flagWriteMedicalExamination = patientRecordDetBean.getFlagWriteMedicalExamination();
        if (flagWriteMedicalExamination==1) {
            setMenueStatus(MedcalRecordDialog.MEDICAL_EXAMINATION_TYPE);
        }
        int flagWriteTreatmentProposal = patientRecordDetBean.getFlagWriteTreatmentProposal();
        if (flagWriteTreatmentProposal==1) {
            setMenueStatus(MedcalRecordDialog.TREATMENTPROPOSAL_TYPE);
        }
        int flagWriteInspection = patientRecordDetBean.getFlagWriteInspection();
        if (flagWriteInspection==1) {
            setMenueStatus(MedcalRecordDialog.INSPECTION_TYPE);
        }

        int flagWriteDrug = patientRecordDetBean.getFlagWriteDrug();
        if (flagWriteDrug==1) {
            setMenueStatus(MedcalRecordDialog.PRESCRIPTION_NOTES_TYPE);
        }


    }

    /**
     * 发送IM监测请求
     * @param mainPatientCode 患者code
     * @param mainPatientName 患者name
     */
    private void sendGetCheckRequest(String mainPatientCode,String mainPatientName){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainPatientName",mainPatientName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().iMTesting(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode == 1) {
                    String resJsonData = baseBean.getResJsonData();
                    checkImResultBean = GsonUtils.fromJson(resJsonData, CheckImResultBean.class);
                    orderCode = checkImResultBean.getOrderCode();
                    String isBinding = checkImResultBean.getIsBinding();
                    String isSigning = checkImResultBean.getIsSigning();
                    String isReserveing = checkImResultBean.getIsReserveing();
                    if (StringUtils.isNotEmpty(isBinding)&&isBinding.equals("1")) {
                        if (StringUtils.isNotEmpty(isSigning)&&isSigning.equals("0")) {
                            chatFragment.setSignUpBtnStatus(true);
                        }else{
                            chatFragment.setSignUpBtnStatus(false);
                        }
                    }else{
                        chatFragment.setSignUpBtnStatus(false);
                    }

                    if (StringUtils.isNotEmpty(isReserveing)&&isReserveing.equals("1")) {
                        mFloatballManager.show();
                    }else{
                        mFloatballManager.hide();
                    }
                    if (StringUtils.isNotEmpty(orderCode)) {
                        sendGetMedicalRecordInfoRequest(orderCode);
                    }


                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }
        });
    }



    /**
     * 获取病例信息
     * @param orderCode 订单编码
     */
    private void sendGetMedicalRecordInfoRequest(String orderCode){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getPatientMedicalRecord(s).compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode == 1) {
                    String resJsonData = baseBean.getResJsonData();
                    patientRecordDetBean =
                            GsonUtils.fromJson(resJsonData, PatientRecordDetBean.class);
                    initChoosedStatus(patientRecordDetBean);
                }
            }
        });
    }

    /**
     * 发送保存病例请求
     *
     * @param orderCode          订单Id
     * @param operType           操作类型.0:全部;1:主诉;2:现病史;3:既往史;4:过敏史;5:查体
     *                           ;6:治疗建议;11:主诉与现病史;12:既往史与过敏史;
     * @param chiefComplaint     主诉
     * @param historyNew         现病史
     * @param flagHistoryAllergy 既往史
     * @param historyAllergy     过敏史
     * @param medicalExamination 查体
     * @param treatmentProposal  治疗建议
     */
    private void sendSaveMedicalRecordRequest(String orderCode,
                                              String operType,
                                              String chiefComplaint
            , String historyNew, String historyPast, String flagHistoryAllergy
            , String historyAllergy, String medicalExamination
            , String treatmentProposal) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode", orderCode);
        hashMap.put("operType", operType);
        hashMap.put("chiefComplaint", chiefComplaint);
        hashMap.put("historyNew", historyNew);
        hashMap.put("historyPast", historyPast);
        hashMap.put("historyAllergy", historyAllergy);
        hashMap.put("medicalExamination", medicalExamination);
        hashMap.put("treatmentProposal", treatmentProposal);
        hashMap.put("flagHistoryAllergy", "1");
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().savePatientMedicalRecord(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                showLoading("",null);
            }

            @Override
            public void hideLoadingView() {
                dismissLoading();
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    ToastUtils.showToast("保存成功");
                }else{
                    ToastUtils.showToast("保存失败");
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                ToastUtils.showToast("保存失败");
            }

            @Override
            protected String setTag() {
                return super.setTag();
            }
        });

    }


    /**
     * 发送病例
     * @param orderCode 订单code
     */
    private void sendMedicalRecordRequest(String orderCode){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().sendPatientMedicalRecord(s)
                .compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    long reserveConfigEnd = patientRecordDetBean.getReserveConfigEnd();
                    String endTime = DateUtils.getDateToYYYYMMDD(reserveConfigEnd);
                    String treatmentMould = patientRecordDetBean.getTreatmentMould();
                    String patientType="";
                    if (treatmentMould.equals("1")) {
                        patientType="一次性就诊";
                    }else if(treatmentMould.equals("2")){
                        patientType="签约就诊";
                    }
                    OrderMessage orderMessage=new OrderMessage(endTime,patientType
                            ,orderCode,"medicalRecord");
                    chatFragment.sendOrderCardMsg(orderMessage);
                }else{
                    ToastUtils.showToast(baseBean.getResMsg());
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                ToastUtils.showToast(s);
            }
        });
    }

    private void getTime(String orderCode, String treatmentType, String operType, String limitNum) {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        map.put("orderCode", orderCode);
        map.put("treatmentType", treatmentType);
        map.put("operType", operType);
        map.put("limitNum", limitNum);
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByOrderTreatmentLimitNum");
                    Log.e("tag", "更新 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(10);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 10:
                        NetRetEntity  netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                        //    Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();

                        }else{
                       //     Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();

                        }
                        break;
                }
            }
        };
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        titleBar = (EaseTitleBar) this.findViewById(R.id.title_bar);
        titleBar.setTitle(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);

        messageList = (EaseChatMessageList) this.findViewById(R.id.message_list);
        //初始化messagelist
        messageList.init(mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), 1, null);
        //设置item里的控件的点击事件
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                //头像点击事件
            }

            @Override
            public void onUserAvatarLongClick(String username) {

            }

            @Override
            public void onMessageInProgress(EMMessage message) {

            }


            @Override
            public void onBubbleLongClick(EMMessage message) {
                //气泡框长按事件
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
                return false;
            }

            @Override
            public boolean onResendClick(EMMessage message) {
                return false;
            }
        });
//        //获取下拉刷新控件
//        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
//        //刷新消息列表
//        messageList.refresh();
//        messageList.refreshSeekTo(position);
//        messageList.refreshSelectLast();

        inputMenu = (EaseChatInputMenu) this.findViewById(R.id.input_menu);
//注册底部菜单扩展栏item
//传入item对应的文字，图片及点击事件监听，extendMenuItemClickListener实现EaseChatExtendMenuItemClickListener
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
//        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);

//初始化，此操作需放在registerExtendMenuItem后
        inputMenu.init();
//设置相关事件监听
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
                //   sendTextMessage(content);
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {

            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                ////把touch事件传入到EaseVoiceRecorderView 里进行录音
//                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
//                    @Override
//                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
//                        // 发送语音消息
//                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
//                    }
//                });
                return false;
            }
        });
        inputMenu.showMoreOption();


    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //只有activity被添加到windowmanager上以后才可以调用show方法。
        mFloatballManager.show();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFloatballManager.hide();
    }

    private void exitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        isfull = false;
    }


    public void setFullScreen(View view) {
        if (isfull == true) {
            exitFullScreen();
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            isfull = true;
        }
    }

    private void initSinglePageFloatball(boolean showMenu) {
        //1 初始化悬浮球配置，定义好悬浮球大小和icon的drawable
        int ballSize = DensityUtil.dip2px(this, 45);
        Drawable ballIcon = BackGroudSeletor.getdrawble("bg_bl", this);
        FloatBallCfg ballCfg = new FloatBallCfg(ballSize, ballIcon, FloatBallCfg.Gravity.RIGHT_CENTER);
        //设置悬浮球不半隐藏
//        ballCfg.setHideHalfLater(false);
        if (showMenu) {
            //2 需要显示悬浮菜单
            //2.1 初始化悬浮菜单配置，有菜单item的大小和菜单item的个数
            int menuSize = DensityUtil.dip2px(this, 550);
            int menuItemSize = DensityUtil.dip2px(this, 58);
            FloatMenuCfg menuCfg = new FloatMenuCfg(menuSize, menuItemSize);
            //3 生成floatballManager
            //必须传入Activity
            mFloatballManager = new FloatBallManager(this, ballCfg, menuCfg);
            addFloatMenuItem();
        } else {
            //必须传入Activity
            mFloatballManager = new FloatBallManager(this, ballCfg);
        }
    }



    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if (StringUtils.isNotEmpty(orderCode)) {
                sendGetMedicalRecordInfoRequest(orderCode);
            }
        }
    }
}
