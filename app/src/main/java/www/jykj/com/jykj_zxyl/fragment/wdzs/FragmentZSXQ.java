package www.jykj.com.jykj_zxyl.fragment.wdzs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.hyhd.VideoCallActivity;
import com.hyphenate.easeui.hyhd.VoiceCallActivity;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.TWJZActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_CFQActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.TWJZ_KJCFActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.WDZS_WZXQActivity;
import www.jykj.com.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideDoctorSetServiceState;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideInteractClinicStateOverview;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.adapter.MyClinicZSZKZLAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZNoFinishRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;


/**
 * 诊所详情
 * Created by admin on 2016/6/1.
 */
public class FragmentZSXQ extends Fragment {

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //返回字符串


    private Context mContext;
    private Handler mHandler;
    private MyClinicActivity mActivity;
    private JYKJApplication mApp;
    private RecyclerView mNoFinishRecycleView;              //未完成列表
    private LinearLayoutManager layoutManager;
    private TWJZNoFinishRecycleAdapter mTWJZNoFinishRecycleAdapter;       //适配器
    private List<HZIfno> mHZEntyties = new ArrayList<>();            //所有数据
    private ProvideDoctorSetServiceState mProvideDoctorSetServiceState;

    private List<ProvideViewInteractOrderTreatmentAndPatientInterrogation> provideViewInteractOrderTreatmentAndPatientInterrogations = new ArrayList<>();

    private int mPageNum = 1;                               //页数
    private int mRowNum = 10;                               //每页行数
    private int mType = 1;                               //就诊(治疗)类型.-1:全部;1:图文就诊;2:音频就诊;3:视频就诊;4:签约服务;5:电话就诊;
    private TextView mWWC;
    private TextView mYWC;
    private int mModel = 1;                      //当前模式 1= 未完成 2=已完成
    private LinearLayout mTWJZ;                      //图文就诊
    private LinearLayout mYPJZ;                      //音频就诊
    private LinearLayout mSPJZ;                      //视频就诊
    private LinearLayout mQYFW;                      //签约服务
    private LinearLayout mDHJZ;                      //电话就诊

    private ImageView mTWJZImage;                 //图文就诊
    private ImageView mYPJZImage;                 //音频就诊
    private ImageView mSPJZImage;                 //视频就诊
    private ImageView mQYFWImage;                 //签约服务
    private ImageView mDHJZImage;                 //电话就诊

    private RecyclerView mRecycleView;              //列表

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymyclinic_zsxq, container, false);
        mContext = getContext();
        mActivity = (MyClinicActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        getData();

        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {

        mWWC = (TextView) view.findViewById(R.id.tv_wwc);
        mYWC = (TextView) view.findViewById(R.id.tv_ywc);

        mTWJZ = (LinearLayout) view.findViewById(R.id.ll_twjz);
        mYPJZ = (LinearLayout) view.findViewById(R.id.li_ypjz);
        mSPJZ = (LinearLayout) view.findViewById(R.id.li_spjz);
        mQYFW = (LinearLayout) view.findViewById(R.id.li_qyfw);
        mDHJZ = (LinearLayout) view.findViewById(R.id.li_dhjz);

        mWWC.setOnClickListener(new ButtonClick());
        mYWC.setOnClickListener(new ButtonClick());

        mTWJZ.setOnClickListener(new ButtonClick());
        mYPJZ.setOnClickListener(new ButtonClick());
        mSPJZ.setOnClickListener(new ButtonClick());
        mQYFW.setOnClickListener(new ButtonClick());
        mDHJZ.setOnClickListener(new ButtonClick());

        mTWJZImage = (ImageView) view.findViewById(R.id.iv_twjz);
        mYPJZImage = (ImageView) view.findViewById(R.id.iv_ypjz);
        mSPJZImage = (ImageView) view.findViewById(R.id.iv_spjz);
        mQYFWImage = (ImageView) view.findViewById(R.id.iv_qyfw);
        mDHJZImage = (ImageView) view.findViewById(R.id.iv_dhjz);

        mNoFinishRecycleView = (RecyclerView) view.findViewById(R.id.wdzs_recycleView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mNoFinishRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mNoFinishRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mTWJZNoFinishRecycleAdapter = new TWJZNoFinishRecycleAdapter(provideViewInteractOrderTreatmentAndPatientInterrogations, mContext);
        mNoFinishRecycleView.setAdapter(mTWJZNoFinishRecycleAdapter);
        mTWJZNoFinishRecycleAdapter.setOnWZXXItemClickListener(new TWJZNoFinishRecycleAdapter.OnWZXXItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, WZXXActivity.class).putExtra("wzxx", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        mTWJZNoFinishRecycleAdapter.setOnHYHDItemClickListener(new TWJZNoFinishRecycleAdapter.OnHYHDItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (mType) {
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(mContext, ChatActivity.class);
                        intent.putExtra("userCode", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientCode());
                        intent.putExtra("userName", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientName());
                        intent.putExtra("chatType", "twjz");

                        intent.putExtra("loginDoctorPosition", mApp.loginDoctorPosition);
                        intent.putExtra("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                        intent.putExtra("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                        intent.putExtra("orderCode", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getOrderCode());

                        intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitImgText());           //消息数量
                        intent.putExtra(EaseConstant.EXTRA_VOICE_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitAudio());           //音频时长（单位：秒）
                        intent.putExtra(EaseConstant.EXTRA_VEDIO_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitVideo());           //视频时长（单位：秒）
                        startActivity(intent);
                        break;
                    case 2:
                        if (provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitAudio() <= 0) {
                            Toast.makeText(getContext(), "语音时长已用尽", Toast.LENGTH_LONG).show();
                            return;
                        }
                        startActivity(new Intent(getActivity(), VoiceCallActivity.class)
                                .putExtra("username", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientCode())
                                .putExtra("isComingCall", false)
                                .putExtra("nickName", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientName())
                                .putExtra("voiceNum", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitAudio()));
                        break;
                    case 3:
                        if (provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitVideo() <= 0) {
                            Toast.makeText(getContext(), "视频时长已用尽", Toast.LENGTH_LONG).show();
                            return;
                        }
                        int time = provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitVideo();
                        startActivity(new Intent(getActivity(), VideoCallActivity.class)
                                .putExtra("username", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientCode())
                                .putExtra("isComingCall", false)
                                .putExtra("vedioNum", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitVideo()));
                        break;
                    case 4:
                        intent = new Intent();
                        intent.setClass(mContext, ChatActivity.class);
                        intent.putExtra("userCode", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientCode());
                        intent.putExtra("userName", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getPatientName());

                        intent.putExtra("loginDoctorPosition", mApp.loginDoctorPosition);
                        intent.putExtra("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                        intent.putExtra("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                        intent.putExtra("orderCode", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getOrderCode());

                        intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitImgText());           //消息数量
                        intent.putExtra(EaseConstant.EXTRA_VOICE_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitAudio());           //音频时长（单位：秒）
                        intent.putExtra(EaseConstant.EXTRA_VEDIO_NUM, provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getLimitVideo());           //视频时长（单位：秒）
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + provideViewInteractOrderTreatmentAndPatientInterrogations.get(position).getInterrogationPatientLinkPhone());
                        intent.setData(data);
                        startActivity(intent);

                }


            }

            @Override
            public void onLongClick(int position) {

            }
        });
        mTWJZNoFinishRecycleAdapter.setOnKJCFItemClickListener(new TWJZNoFinishRecycleAdapter.OnKJCFItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext, WDZS_WZXQActivity.class).putExtra("wzxx", provideViewInteractOrderTreatmentAndPatientInterrogations.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_wwc:
                    mModel = 1;
                    getWWCData();
                    mWWC.setTextColor(getResources().getColor(R.color.groabColor));
                    mYWC.setTextColor(getResources().getColor(R.color.textColor_vo));
                    break;
                case R.id.tv_ywc:
                    mModel = 2;
                    getYWCData();
                    mWWC.setTextColor(getResources().getColor(R.color.textColor_vo));
                    mYWC.setTextColor(getResources().getColor(R.color.groabColor));
                    break;
                case R.id.ll_twjz:
                    mType = 1;
                    if (mProvideDoctorSetServiceState.getFlagImgText() == 0) {
                        Toast.makeText(mContext, "暂未开通此权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getWWCData();
                    break;
                case R.id.li_ypjz:
                    if (mProvideDoctorSetServiceState.getFlagAudio() == 0) {
                        Toast.makeText(mContext, "暂未开通此权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mType = 2;
                    getWWCData();
                    break;
                case R.id.li_spjz:
                    if (mProvideDoctorSetServiceState.getFlagVideo() == 0) {
                        Toast.makeText(mContext, "暂未开通此权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mType = 3;
                    getWWCData();
                    break;
                case R.id.li_qyfw:
                    if (mProvideDoctorSetServiceState.getFlagSigning() == 0) {
                        Toast.makeText(mContext, "暂未开通此权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mType = 4;
                    getWWCData();
                    break;
                case R.id.li_dhjz:
                    if (mProvideDoctorSetServiceState.getFlagPhone() == 0) {
                        Toast.makeText(mContext, "暂未开通此权限", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mType = 5;
                    getWWCData();
                    break;

            }
        }
    }

    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideDoctorSetServiceState provideDoctorSetServiceState = new ProvideDoctorSetServiceState();
        provideDoctorSetServiceState.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideDoctorSetServiceState.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideDoctorSetServiceState.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideDoctorSetServiceState);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResServiceState");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
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
                            mProvideDoctorSetServiceState = JSON.parseObject(netRetEntity.getResJsonData(), ProvideDoctorSetServiceState.class);
                            if (mProvideDoctorSetServiceState != null)
                                setLayoutDate();
                            getWWCData();
                        }

                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            provideViewInteractOrderTreatmentAndPatientInterrogations = JSON.parseArray(netRetEntity.getResJsonData(), ProvideViewInteractOrderTreatmentAndPatientInterrogation.class);
                            mTWJZNoFinishRecycleAdapter.setDate(provideViewInteractOrderTreatmentAndPatientInterrogations);
                            mTWJZNoFinishRecycleAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 设置显示
     */
    private void setLayoutDate() {
        if (mProvideDoctorSetServiceState.getFlagImgText() == 1)
            mTWJZImage.setImageResource(R.mipmap.wdzs_twjz);
        else
            mTWJZImage.setImageResource(R.mipmap.tw_no);

        if (mProvideDoctorSetServiceState.getFlagAudio() == 1)
            mYPJZImage.setImageResource(R.mipmap.yyjz);
        else
            mYPJZImage.setImageResource(R.mipmap.yy_no);

        if (mProvideDoctorSetServiceState.getFlagVideo() == 1)
            mSPJZImage.setImageResource(R.mipmap.wdzs_spjz);
        else
            mSPJZImage.setImageResource(R.mipmap.sp_no);

        if (mProvideDoctorSetServiceState.getFlagSigning() == 1)
            mQYFWImage.setImageResource(R.mipmap.qyjz);
        else
            mQYFWImage.setImageResource(R.mipmap.qy_no);

        if (mProvideDoctorSetServiceState.getFlagPhone() == 1)
            mDHJZImage.setImageResource(R.mipmap.wdzs_dhjz);
        else
            mDHJZImage.setImageResource(R.mipmap.dh_no);
    }


    /**
     * 获取未完成数据
     */
    private void getWWCData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideViewInteractOrderTreatmentAndPatientInterrogation provideViewInteractOrderTreatmentAndPatientInterrogation = new ProvideViewInteractOrderTreatmentAndPatientInterrogation();
        provideViewInteractOrderTreatmentAndPatientInterrogation.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideViewInteractOrderTreatmentAndPatientInterrogation.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideViewInteractOrderTreatmentAndPatientInterrogation.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        provideViewInteractOrderTreatmentAndPatientInterrogation.setPageNum(mPageNum + "");
        provideViewInteractOrderTreatmentAndPatientInterrogation.setRowNum(mRowNum + "");
        provideViewInteractOrderTreatmentAndPatientInterrogation.setTreatmentType(mType);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewInteractOrderTreatmentAndPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResTreatmentRecord");
                    Log.e("bbbb", "run: "+string );
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
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
     * 获取已完成数据
     */
    private void getYWCData() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideViewInteractOrderTreatmentAndPatientInterrogation provideViewInteractOrderTreatmentAndPatientInterrogation = new ProvideViewInteractOrderTreatmentAndPatientInterrogation();
        provideViewInteractOrderTreatmentAndPatientInterrogation.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideViewInteractOrderTreatmentAndPatientInterrogation.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideViewInteractOrderTreatmentAndPatientInterrogation.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        provideViewInteractOrderTreatmentAndPatientInterrogation.setPageNum(mPageNum + "");
        provideViewInteractOrderTreatmentAndPatientInterrogation.setRowNum(mRowNum + "");
        provideViewInteractOrderTreatmentAndPatientInterrogation.setTreatmentType(mType);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewInteractOrderTreatmentAndPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResTreatmentRecord");
                    Log.e("bbbb", "run: "+string );
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
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
