package www.jykj.com.jykj_zxyl.activity.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.enitity.ThumbViewInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientMessage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import util.ImagDialog;
import util.ImageLoader;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.PhotoDialog;
import www.jykj.com.jykj_zxyl.util.StringUtils;
import www.jykj.com.jykj_zxyl.util.Util;


public class ZhlyReplyActivity extends AppCompatActivity {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private Handler mHandler;
    private ZhlyReplyActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private ProvideInteractPatientMessage mProvideInteractPatientMessage;
    private TextView mNameTitle;               //标题，患者姓名
    private TextView mMessageType;               //消息类型
    private TextView mMessageDate;               //留言日期
    private TextView mMessageContent;               //消息类型
    private TextView mMessageLinkPhone;            //联系电话

    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private WZZXImageViewRecycleAdapter mAdapter;

    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private String mGetImgNetRetStr;                 //获取图片返回字符串

    private TextView mMessageReply;                  //留言回复内容
    private TextView mCommit;                        //

    private  boolean photo=true;

    private PhotoDialog photoDialog;
    private TextView no_commit;
    private LinearLayout lin_status;
    private TextView zhli_status;
    private String replyTyp;
    private String status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhly_doctor_reply);
        ActivityUtil.setStatusBarMain(ZhlyReplyActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        Intent intent = getIntent();
        status = intent.getStringExtra("status");
        initView();
        initListener();
        getData();
        initHandler();
        sendDataRequest(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

    }

    private void initView() {
        lin_status = findViewById(R.id.lin_status);
        lin_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }

        });
        zhli_status = findViewById(R.id.zhli_status);
        //是否上传了图片
        no_commit = findViewById(R.id.no_commit);
        //   mNameTitle = (TextView) this.findViewById(R.id.tv_patientName);
        mMessageType = this.findViewById(R.id.tv_msgType);
        mMessageDate =  this.findViewById(R.id.tv_msgDate);
        mMessageContent =  this.findViewById(R.id.content);
        mMessageLinkPhone =  this.findViewById(R.id.tv_linkPhone);
        mMessageReply = (EditText) this.findViewById(R.id.tv_messageReply);
        //   mMessageReply.setText(mProvideInteractPatientMessage.getReplyContent());
        mCommit =  this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });
    }


    /**
     * 发送数据请求
     * @param orderCode
     */
    private void sendDataRequest(String orderCode){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);

        ApiHelper.getApiService().searchMyClinicDetailResPatientMessageContent_20201012(s).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                System.out.println(baseBean);

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            mProvideInteractPatientMessage = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractPatientMessage.class);

                            if (mProvideInteractPatientMessage != null) {
                                showLayoutDate();
                                if(mProvideInteractPatientMessage.getMessageDate()==null){
                                    Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getMessageDate().toString() );
                                    mMessageDate.setText("未提交");
                                }
                                mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
                                if(mProvideInteractPatientMessage.getMessageContent()==null&&mProvideInteractPatientMessage.getMessageContent().equals("")){       Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getMessageContent() );
                                    mMessageContent.setText("未提交");
                                }
                                mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
                                if(mProvideInteractPatientMessage.getPatientLinkPhone()==null&&mProvideInteractPatientMessage.getPatientLinkPhone().equals("")){  Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getPatientLinkPhone() );
                                    mMessageLinkPhone.setText("未提交");
                                }
                                mMessageLinkPhone.setText(mProvideInteractPatientMessage.getPatientLinkPhone());
                                getImgData();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            mProvideBasicsImg = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsImg.class);
                            if (mProvideBasicsImg != null && mProvideBasicsImg.size() > 0) {
                                if(mProvideInteractPatientMessage.getMessageDate()==null){
                                    Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getMessageDate().toString() );
                                    mMessageDate.setText("未提交");
                                }
                                mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
                                if(mProvideInteractPatientMessage.getMessageContent()==null&&mProvideInteractPatientMessage.getMessageContent().equals("")){       Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getMessageContent() );
                                    mMessageContent.setText("未提交");
                                }
                                mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
                                if(mProvideInteractPatientMessage.getPatientLinkPhone()==null&&mProvideInteractPatientMessage.getPatientLinkPhone().equals("")){  Log.e("tag", "handleMessage: "+mProvideInteractPatientMessage.getPatientLinkPhone() );
                                    mMessageLinkPhone.setText("未提交");
                                }
                                mMessageLinkPhone.setText( mProvideInteractPatientMessage.getPatientLinkPhone());
                                no_commit.setVisibility(View.GONE);
                                mAdapter.setDate(mProvideBasicsImg);
                                mAdapter.notifyDataSetChanged();
                            }

                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            startJumpChatActivity();
                            finish();


                        }

                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void getImgData() {
        ProvideBasicsImg provideBasicsImg = new ProvideBasicsImg();
        provideBasicsImg.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideBasicsImg.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBasicsImg.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBasicsImg.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideBasicsImg.setImgCode(mProvideInteractPatientMessage.getImgCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideBasicsImg);
                    mGetImgNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResPatientMessageImg");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mGetImgNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }


    private void showLayoutDate() {

        lin_status = findViewById(R.id.lin_status);
        lin_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }

        });
        zhli_status = findViewById(R.id.zhli_status);
        if (StringUtils.isNotEmpty(status)) {
            switch (status) {
                case "1":
                    zhli_status.setText("正常");
                    zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv4));
                    break;
                case "2":
                    zhli_status.setText("一般");
                    zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv3));
                    break;
                case "3":
                    zhli_status.setText("紧急");
                    zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv2));
                    break;
                case "4":
                    zhli_status.setText("重大紧急");
                    zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv1));
                    break;
                    default:
            }
        }
        //是否上传了图片
        no_commit = findViewById(R.id.no_commit);
        //   mNameTitle = (TextView) this.findViewById(R.id.tv_patientName);
        mMessageType =  this.findViewById(R.id.tv_msgType);
        mMessageDate =  this.findViewById(R.id.tv_msgDate);
        mMessageContent =  this.findViewById(R.id.content);
        mMessageLinkPhone =  this.findViewById(R.id.tv_linkPhone);
        mMessageReply = (EditText) this.findViewById(R.id.tv_messageReply);
        mMessageReply.setText(mProvideInteractPatientMessage.getReplyContent());
        mCommit =  this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        //  mNameTitle.setText("【" + mProvideInteractPatientMessage.getPatientName() + "】诊后留言");
        mMessageType.setText(mProvideInteractPatientMessage.getTreatmentTypeName());
        if(mProvideInteractPatientMessage.getMessageDate()==null){
            mMessageDate.setText("未提交");
        }
        mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
        if(mProvideInteractPatientMessage.getMessageContent()==null&&mProvideInteractPatientMessage.getMessageContent().equals("")){
            Log.e("tag", "handleMessage:111 "+mProvideInteractPatientMessage.getMessageContent() );
            mMessageContent.setText("未提交");
        }
        mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
        if(mProvideInteractPatientMessage.getPatientLinkPhone()==null&&mProvideInteractPatientMessage.getPatientLinkPhone().equals("")){  Log.e("tag", "handleMessage:111 "+mProvideInteractPatientMessage.getPatientLinkPhone() );
            mMessageLinkPhone.setText("未提交");
        }
        mMessageLinkPhone.setText(mProvideInteractPatientMessage.getPatientLinkPhone());

        mImageRecycleView = (RecyclerView) this.findViewById(R.id.rv_imageView);
        //创建默认的线性LayoutManager
        mGridLayoutManager = new FullyGridLayoutManager(mContext, 3);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mImageRecycleView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mImageRecycleView.setHasFixedSize(true);


        //创建并设置Adapter
        mAdapter = new WZZXImageViewRecycleAdapter(mProvideBasicsImg, mContext);
        mImageRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WZZXImageViewRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                PhotoDialog photoDialog = new PhotoDialog(mContext,R.style.PhotoDialog);
                photoDialog.setDate(mContext,mApp,mProvideBasicsImg,position);
                photoDialog.show();
            }


            @Override
            public void onLongClick(int position) {

            }
        });

    }
    @SuppressLint("ResourceAsColor")
    private void dialog() {
        final Dialog dialog = new Dialog(ZhlyReplyActivity.this, R.style.BottomDialog);
        View view = LayoutInflater.from(ZhlyReplyActivity.this).inflate(R.layout.bottom_dialog_nocommit, null);
        dialog.setContentView(view);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        tv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zhli_status.setText("重大紧急");
                zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv1));
                replyTyp = "4";
                dialog.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhli_status.setText("紧急");
                zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv2));
                replyTyp = "3";
                dialog.dismiss();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhli_status.setText("一般");
                zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv3));
                replyTyp = "2";
                dialog.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhli_status.setText("正常");
                zhli_status.setTextColor(ZhlyReplyActivity.this.getResources().getColor(R.color.tv4));
                replyTyp = "1";
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ZhlyReplyActivity.this.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    /**
     * 提交
     */
    private void commit() {

        ProvideInteractPatientMessage provideInteractPatientMessage = new ProvideInteractPatientMessage();
        provideInteractPatientMessage.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractPatientMessage.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientMessage.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        if(TextUtils.isEmpty(mMessageReply.getText().toString())){
            Toast.makeText(mContext, "请输入回复内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mProvideInteractPatientMessage==null){
            provideInteractPatientMessage.setMessageId(0);
        }else{
            provideInteractPatientMessage.setMessageId(mProvideInteractPatientMessage.getMessageId());
        }
        provideInteractPatientMessage.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideInteractPatientMessage.setTreatmentType(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentType());
        provideInteractPatientMessage.setReplyContent(mMessageReply.getText().toString());
       if(TextUtils.isEmpty(replyTyp)){
           Toast.makeText(mContext,"请选择消息类型", Toast.LENGTH_SHORT).show();
           return;
       }
        provideInteractPatientMessage.setReplyType(replyTyp);
        provideInteractPatientMessage.setPatientCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());
        provideInteractPatientMessage.setPatientName(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientName());

        String treatmentLinkPhone = mProvideViewInteractOrderTreatmentAndPatientInterrogation.getTreatmentLinkPhone();
        if (StringUtils.isNotEmpty(treatmentLinkPhone)) {
            provideInteractPatientMessage.setPatientPhone(treatmentLinkPhone);
        }else{
            provideInteractPatientMessage.setPatientPhone("");
        }

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientMessage);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage");
                    String string01 = Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage";
                    System.out.println(string + string01);
                    Log.e("tag", "提交 "+mNetRetStr );
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

    private void initListener() {
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 跳转IM
     */
    private void startJumpChatActivity(){
        Intent intent = new Intent(this, ChatActivity.class);
        //患者
        intent.putExtra("userCode",
                mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());
        intent.putExtra("userName",
                mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientName());
        //医生
        intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //URL
        intent.putExtra("doctorUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
        intent.putExtra("patientCode",
                mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());


        OrderMessage orderMessage=new OrderMessage(
                mApp.mViewSysUserDoctorInfoAndHospital.getUserName()
                ,mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl()
                ,mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode(),replyTyp
                ,"MessageAfterDiagnosis");

        Bundle bundle = new Bundle();
        bundle.putSerializable("orderMsg", orderMessage);
        intent.putExtras(bundle);
        startActivityForResult(intent,1000);
    }

    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideInteractPatientMessage provideInteractPatientMessage = new ProvideInteractPatientMessage();
        provideInteractPatientMessage.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractPatientMessage.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientMessage.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientMessage.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientMessage);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResPatientMessageContent");
                    Log.e("", "文字 "+mNetRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
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
