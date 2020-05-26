package www.jykj.com.jykj_zxyl.activity.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.enitity.ThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractPatientMessage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import util.ImagDialog;
import util.ImageLoader;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.PhotoDialog;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhly_doctor_reply);
        ActivityUtil.setStatusBarMain(ZhlyReplyActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initListener();
        initHandler();
        getData();
    }

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
                                mAdapter.setDate(mProvideBasicsImg);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
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
        mNameTitle = (TextView) this.findViewById(R.id.tv_patientName);
        mMessageType = (TextView) this.findViewById(R.id.tv_msgType);
        mMessageDate = (TextView) this.findViewById(R.id.tv_msgDate);
        mMessageContent = (TextView) this.findViewById(R.id.content);
        mMessageLinkPhone = (TextView) this.findViewById(R.id.tv_linkPhone);
        mMessageReply = (EditText) this.findViewById(R.id.tv_messageReply);
        mCommit = (TextView) this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        mNameTitle.setText("【" + mProvideInteractPatientMessage.getPatientName() + "】诊后留言");
        mMessageType.setText(mProvideInteractPatientMessage.getTreatmentTypeName());
        mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
        mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
        mMessageLinkPhone.setText("联系电话：" + mProvideInteractPatientMessage.getPatientLinkPhone());

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

    /**
     * 提交
     */
    private void commit() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideInteractPatientMessage provideInteractPatientMessage = new ProvideInteractPatientMessage();
        provideInteractPatientMessage.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractPatientMessage.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientMessage.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractPatientMessage.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideInteractPatientMessage.setPatientCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());
        provideInteractPatientMessage.setPatientName(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientName());
        provideInteractPatientMessage.setReplyContent(mMessageReply.getText().toString());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientMessage);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage");
                    String string01 = Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage";
                    System.out.println(string + string01);
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
                    String string01 = Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResPatientMessageContent";
                    System.out.println(string + string01);
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
