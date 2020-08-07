package www.jykj.com.jykj_zxyl.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.DocterFriendChildEntity;
import entity.DocterFriendEntity;
import entity.DoctorInfo.InteractDoctorDetailInfo;
import entity.DoctorInfo.InteractDoctorUnionInfo;
import entity.DoctorInfo.InteractPatient;
import entity.HZIfno;
import entity.basicDate.EMMessageEntity;
import entity.basicDate.InteractDoctorUnionPersonnel;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import entity.yhhd.ProvideDoctorGoodFriendGroup;
import entity.yhhd.ProvideGroupConsultationUserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.IDCardScanningActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.HYHD_HYSQActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.NewChatGroupActivity;
import www.jykj.com.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.jykj.com.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.NestedExpandaleListView;
import www.jykj.com.jykj_zxyl.util.PermissionUtils;
import www.jykj.com.jykj_zxyl.util.Util;


/**
 * 医患互动fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentYHHD extends Fragment {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private MainActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;
    private RecyclerView mMessageRecycleView;

    private LinearLayoutManager layoutManager;
    private MessageInfoRecycleAdapter mMessageInfoRecycleAdapter;       //适配器
    private List<InteractPatient> mInteractPatient = new ArrayList<>();            //所有数据
    private List<ProvideDoctorGoodFriendGroup> mInteractDoctorUnionInfo = new ArrayList<>();            //医生联盟数据

    private List<HZIfno> mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private TextView mMessageList;                       //消息列表
    private TextView mAll;                               //全部患者
    private TextView mPay;                               //消息列表
    private TextView mFriend;                            //消息列表

    private NestedExpandaleListView mYSHY;                              //医生好友
    private DorcerFriendExpandableListViewAdapter mDorcerFriendExpandableListViewAdapter;   //医生好友适配器


    private ImageView mJQImage;                           //建群
    private TextView mHYSQText;                          //好友申请
    private String mNetRetStr;                 //返回字符串

    private InteractPatient mClickInteractPatient;              //点击进入聊天的患者

    private List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    private TextView mHXNetWorkState;                    //环信网络状态监听

    private int mCurrent = 0;                       //当前下标

    private int clickIndex;                         //点击列表下标

    private InteractPatient interactPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_hyhdfragment, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        mApp.gNetWorkTextView = true;
        getMessageList();

        return v;
    }


    private void getUserIdentification(String code) {
        ProvideGroupConsultationUserInfo provideGroupConsultationUserInfo = new ProvideGroupConsultationUserInfo();
        provideGroupConsultationUserInfo.setLoginUserPosition(mApp.loginDoctorPosition);
        provideGroupConsultationUserInfo.setUserCode(code);
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideGroupConsultationUserInfo), Constant.SERVICEURL + "doctorGroupConsultationControlle/searchUserIdentificationByUserCode");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }

    /**
     * 获取消息列表
     */
    public void getMessageList() {

        if (mHandler == null)
            return;

        new Thread() {

            public void run() {
                try {
                    Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
                    List<String> userCodeList = new ArrayList<>();
                    //遍历map中的键,获取用户Code列表
                    for (String key : conversationMap.keySet()) {
                        userCodeList.add(key);
                    }

                    String parmentString = "";
                    for (int i = 0; i < userCodeList.size(); i++) {
                        if (i == userCodeList.size() - 1)
                            parmentString += userCodeList.get(i);
                        else
                            parmentString += userCodeList.get(i) + ",";
                    }
                    ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo = new ProvideDoctorPatientUserInfo();
                    provideDoctorPatientUserInfo.setUserCodeList(parmentString);
                    String parment = "jsonDataInfo=" + new Gson().toJson(provideDoctorPatientUserInfo);
                    mNetRetStr = HttpNetService.urlConnectionService(parment, Constant.SERVICEURL + "doctorPatientCommonDataController/getUserInfoList");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        mNetRetStr = new Gson().toJson(retEntity);
                        retEntity.setResCode(0);
                        mHandler.sendEmptyMessage(4);
                        return;
                    } else {
                        mProvideDoctorPatientUserInfo = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideDoctorPatientUserInfo>>() {
                        }.getType());
                        for (int i = 0; i < mProvideDoctorPatientUserInfo.size(); i++) {
                            EMConversation emcConversation = conversationMap.get(mProvideDoctorPatientUserInfo.get(i).getUserCode());
                            List<EMMessage> emMessages = emcConversation.getAllMessages();
                            EMMessage emMessage = emcConversation.getAllMessages().get(emcConversation.getAllMessages().size() - 1);
                            String str = "{" + emMessage.getBody().toString() + "}";
                            EMMessageEntity emMessageEntity = new Gson().fromJson(str, EMMessageEntity.class);
                            mProvideDoctorPatientUserInfo.get(i).setLastMessage(emMessageEntity.getTxt());
                            //获取未读消息数
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mProvideDoctorPatientUserInfo.get(i).getUserCode());
                            int num = conversation.getUnreadMsgCount();
                            if (num > 0) {
                                mProvideDoctorPatientUserInfo.get(i).setNoRead(true);
                            } else
                                mProvideDoctorPatientUserInfo.get(i).setNoRead(false);
                            System.out.println("");
                        }

                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(4);
            }
        }.start();
    }

    /**
     * @param v
     */
    private void initLayout(View v) {
       ImageView img_ease=v.findViewById(R.id.img_ease);
        mHXNetWorkState = (TextView) v.findViewById(R.id.tv_hxNetWorkState);
        if (mApp.gNetConnectionHX)
            mHXNetWorkState.setVisibility(View.GONE);
        else
            mHXNetWorkState.setVisibility(View.VISIBLE);
        mMessageRecycleView = (RecyclerView) v.findViewById(R.id.rv_fragmethyhd_messageInfo);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mMessageRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mMessageRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mMessageInfoRecycleAdapter = new MessageInfoRecycleAdapter(mApp, mInteractPatient, mContext, mActivity);
        mMessageRecycleView.setAdapter(mMessageInfoRecycleAdapter);
        mMessageInfoRecycleAdapter.setOnItemClickListener(new MessageInfoRecycleAdapter.OnItemClickListener() {

            @Override
            public void onClick(int position) {
                mClickInteractPatient = mInteractPatient.get(position);
                if (mCurrent == 0) {
                    clickIndex = position;
                    getUserIdentification(mClickInteractPatient.getPatientCode());
                } else {
                    mInteractPatient.get(position).setNoRead(false);
                    Intent intent = new Intent();
                    intent.setClass(mContext, ChatActivity.class);
                    intent.putExtra("userCode", mClickInteractPatient.getPatientCode());
                    intent.putExtra("userName", mClickInteractPatient.getPatientUserName());
                    //     Log.e("tag", "handleMessage:患者姓名2 "+ mClickInteractPatient.getPatientUserName());
                    intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                    intent.putExtra("doctorUrl", mClickInteractPatient.getPatientUserLogoUrl());
                    intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(position).getUserNameAlias());
                    Log.e("tag", "患者 别名 " + mProvideDoctorPatientUserInfo.get(position).getGoodAtRealm());
                    intent.putExtra("patientCode", mProvideDoctorPatientUserInfo.get(position).getUserCode());
                    Log.e("tag", "患者  code " + mProvideDoctorPatientUserInfo.get(position).getUserCode());

                    intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(position).getGender());
                    Log.e("tag", "患者  性别: " + mProvideDoctorPatientUserInfo.get(position).getGender() + "");
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mYSHY = (NestedExpandaleListView) v.findViewById(R.id.rv_fragmethyhd_yshyInfo);
        mDorcerFriendExpandableListViewAdapter = new DorcerFriendExpandableListViewAdapter(mInteractDoctorUnionInfo, mContext, mApp);
        mYSHY.setAdapter(mDorcerFriendExpandableListViewAdapter);

        mMessageList = (TextView) v.findViewById(R.id.tv_fragmentHYHD_messageList);
        mAll = (TextView) v.findViewById(R.id.tv_fragmentHYHD_all);
        mPay = (TextView) v.findViewById(R.id.tv_fragmentHYHD_pay);
        mFriend = (TextView) v.findViewById(R.id.tv_fragmentHYHD_friend);

        mMessageList.setOnClickListener(new ButtonClick());
        mAll.setOnClickListener(new ButtonClick());
        mPay.setOnClickListener(new ButtonClick());
        mFriend.setOnClickListener(new ButtonClick());

        mYSHY.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mInteractDoctorUnionInfo.get(groupPosition).setClick(!mInteractDoctorUnionInfo.get(groupPosition).isClick());
                mDorcerFriendExpandableListViewAdapter.setDate(mInteractDoctorUnionInfo);
                mDorcerFriendExpandableListViewAdapter.notifyDataSetInvalidated();
                mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
        mYSHY.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent();
                intent.setClass(mContext, ChatActivity.class);
                intent.putExtra("userCode", mInteractDoctorUnionInfo.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorCode());
                intent.putExtra("userName", mInteractDoctorUnionInfo.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorUserName());
                //      Log.e("tag", "handleMessage:患者姓名3 "+ mClickInteractPatient.getPatientUserName());
                intent.putExtra("vedioNum", 1000000);
                intent.putExtra("voiceNum", 1000000);
                intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                intent.putExtra("doctorUrl", mInteractDoctorUnionInfo.get(i).getGroupLogoUrl());
                intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(i).getUserNameAlias());
                Log.e("tag", "患者 别名 " + mProvideDoctorPatientUserInfo.get(i).getUserNameAlias());
                intent.putExtra("patientCode", mProvideDoctorPatientUserInfo.get(i).getUserCode());
                Log.e("tag", "患者  code " + mProvideDoctorPatientUserInfo.get(i).getUserCode());

                intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(i).getGender());
                Log.e("tag", "患者  性别: " + mProvideDoctorPatientUserInfo.get(i).getGender() + "");
                startActivity(intent);
                return false;
            }
        });

        //  mJQImage = (ImageView) v.findViewById(R.id.iv_fragmentHYHD_jqImage);
        //   mJQImage.setOnClickListener(new ButtonClick());
        mYSHY.setVisibility(View.GONE);
        mMessageRecycleView.setVisibility(View.VISIBLE);
//        mJQImage.setVisibility(View.VISIBLE);
        mHYSQText = (TextView) v.findViewById(R.id.iv_fragmentHYHD_hysqImage);
        mHYSQText.setOnClickListener(new ButtonClick());
        mHYSQText.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getYSLMDate();
        if (mMessageInfoRecycleAdapter != null) {
            mMessageInfoRecycleAdapter.setDate(mInteractPatient);
            mMessageInfoRecycleAdapter.notifyDataSetChanged();
        }
        if (mCurrent == 0) {
            getMessageList();
        }

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        break;
                    case 1:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            mInteractPatient.clear();
                            if (netRetEntity.getResCode() == 1) {
                                mInteractPatient = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<InteractPatient>>() {
                                }.getType());
                                for (int i = 0; i < mInteractPatient.size(); i++) {
                                    mInteractPatient.get(i).setType("user");
                                }
                                mMessageInfoRecycleAdapter.setDate(mInteractPatient);
                                mMessageInfoRecycleAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(mContext, "登录失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 2:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                List<ViewSysUserDoctorInfoAndHospital> viewSysUserDoctorInfoAndHospitals = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ViewSysUserDoctorInfoAndHospital>>() {
                                }.getType());
                                mInteractPatient.removeAll(mInteractPatient);
                                for (int i = 0; i < viewSysUserDoctorInfoAndHospitals.size(); i++) {
                                    InteractPatient interactPatient = new InteractPatient();
                                    interactPatient.setPatientCode(viewSysUserDoctorInfoAndHospitals.get(i).getDoctorCode());
                                    interactPatient.setPatientTitleDesc(viewSysUserDoctorInfoAndHospitals.get(i).getDoctorTitleName());
                                    interactPatient.setPatientDiagnosisType(viewSysUserDoctorInfoAndHospitals.get(i).getUserUseType());
                                    interactPatient.setPatientNewLoginDate(viewSysUserDoctorInfoAndHospitals.get(i).getNewLoginDate());
                                    interactPatient.setPatientUserLabelName(viewSysUserDoctorInfoAndHospitals.get(i).getHospitalInfoName());
                                    interactPatient.setPatientUserName(viewSysUserDoctorInfoAndHospitals.get(i).getUserName());
                                    interactPatient.setPatientUserLogoUrl(viewSysUserDoctorInfoAndHospitals.get(i).getUserLogoUrl());
                                    interactPatient.setType("user");
                                    mInteractPatient.add(interactPatient);
                                }
                                mMessageInfoRecycleAdapter.setDate(mInteractPatient);
                                mMessageInfoRecycleAdapter.notifyDataSetChanged();


                            } else {
                                //  Toast.makeText(mContext, "获取失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 3:
                        cacerProgress();
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                mInteractDoctorUnionInfo = JSON.parseArray(netRetEntity.getResJsonData(), ProvideDoctorGoodFriendGroup.class);
                                mDorcerFriendExpandableListViewAdapter.setDate(mInteractDoctorUnionInfo);
                                mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();

                            } else {
                                //     Toast.makeText(mContext, "获取失败，" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 4:
                        cacerProgress();
                        mInteractPatient.removeAll(mInteractPatient);
                        for (int i = 0; i < mProvideDoctorPatientUserInfo.size(); i++) {
                            InteractPatient interactPatient = new InteractPatient();
                            interactPatient.setPatientCode(mProvideDoctorPatientUserInfo.get(i).getUserCode());
                            interactPatient.setPatientTitleDesc(mProvideDoctorPatientUserInfo.get(i).getDoctorTitleName());
                            interactPatient.setPatientDiagnosisType(mProvideDoctorPatientUserInfo.get(i).getUserUseType());
                            interactPatient.setPatientNewLoginDate(mProvideDoctorPatientUserInfo.get(i).getNewLoginDate());
                            interactPatient.setPatientUserLabelName(mProvideDoctorPatientUserInfo.get(i).getHospitalInfoName());
                            interactPatient.setPatientUserName(mProvideDoctorPatientUserInfo.get(i).getUserName());
                            interactPatient.setType("message");
                            interactPatient.setLastMessage(mProvideDoctorPatientUserInfo.get(i).getLastMessage());
                            interactPatient.setNoRead(mProvideDoctorPatientUserInfo.get(i).isNoRead());
                            interactPatient.setPatientUserLogoUrl(mProvideDoctorPatientUserInfo.get(i).getUserLogoUrl());
                            mInteractPatient.add(interactPatient);
                        }
                        mMessageInfoRecycleAdapter.setDate(mInteractPatient);
                        mMessageInfoRecycleAdapter.notifyDataSetChanged();
                        mApp.setNewsMessage();
                        mActivity.setHZTabView();
                        break;
                    case 10:
                        //连接正常
                        if (mApp.gNetConnectionHX) {
                            mHXNetWorkState.setVisibility(View.GONE);
                        } else {
                            mHXNetWorkState.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 6:
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                ProvideGroupConsultationUserInfo provideGroupConsultationUserInfo = JSON.parseObject(netRetEntity.getResJsonData(), ProvideGroupConsultationUserInfo.class);
                                if (provideGroupConsultationUserInfo.getUserUseType() == 6) {
                                    mClickInteractPatient = mInteractPatient.get(clickIndex);
                                    mInteractPatient.get(clickIndex).setNoRead(false);
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ChatActivity.class);
                                    intent.putExtra(EaseConstant.EXTRA_MESSAGE_NUM, -1);
                                    intent.putExtra("userCode", mClickInteractPatient.getPatientCode());
                                    intent.putExtra("userName", mClickInteractPatient.getPatientUserName());
                                    //       Log.e("tag", "handleMessage:患者姓名 "+ mClickInteractPatient.getPatientUserName());
                                    //   intent.putExtra("chatType", "twjz");
                                    //     intent.putExtra("chatType", "twjz");
                                    intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                                    intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                                    intent.putExtra("doctorUrl", mClickInteractPatient.getPatientUserLogoUrl());
                                    String date = Util.dateToStr(provideGroupConsultationUserInfo.getServiceStopDate());
                                    intent.putExtra("date", date);
                                    intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(clickIndex).getUserNameAlias());
                                    Log.e("tag", "患者 别名 " + mProvideDoctorPatientUserInfo.get(clickIndex).getUserNameAlias());
                                    intent.putExtra("patientCode", mProvideDoctorPatientUserInfo.get(clickIndex).getUserCode());
                                    Log.e("tag", "患者  code " + mProvideDoctorPatientUserInfo.get(clickIndex).getUserCode());

                                    if (TextUtils.isEmpty(mProvideDoctorPatientUserInfo.get(clickIndex).getBirthday())) {

                                    } else {
                                        intent.putExtra("patientAge", DateUtils.getAgeFromBirthTime(mProvideDoctorPatientUserInfo.get(clickIndex).getBirthday())
                                        );
                                    }


                                    intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(clickIndex).getGender());
                                    Log.e("tag", "患者  性别: " + mProvideDoctorPatientUserInfo.get(clickIndex).getGender() + "");
                                    startActivity(intent);
                                } else {
                                    mClickInteractPatient = mInteractPatient.get(clickIndex);
                                    mInteractPatient.get(clickIndex).setNoRead(false);
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ChatActivity.class);
                                    intent.putExtra("userCode", mClickInteractPatient.getPatientCode());
                                    intent.putExtra("userName", mClickInteractPatient.getPatientUserName());
                                    //       Log.e("tag", "handleMessage:患者姓名1 "+ mClickInteractPatient.getPatientUserName());
                                    intent.putExtra("vedioNum", 1000000);
                                    intent.putExtra("voiceNum", 1000000);
                                    intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                                    intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                                    intent.putExtra("doctorUrl", mClickInteractPatient.getPatientUserLogoUrl());
                                    intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(clickIndex).getGoodAtRealm());
                                    Log.e("tag", "患者 别名 " + mProvideDoctorPatientUserInfo.get(clickIndex).getGoodAtRealm());
                                    intent.putExtra("patientCode", mProvideDoctorPatientUserInfo.get(clickIndex).getUserCode());
                                    Log.e("tag", "患者  code " + mProvideDoctorPatientUserInfo.get(clickIndex).getUserCode());
                                    intent.putExtra("patientAge", mProvideDoctorPatientUserInfo.get(clickIndex).getNewLoginDate());
                                    Log.e("tag", "患者  年龄: " + DateUtils.getAgeFromBirthTime(mProvideDoctorPatientUserInfo.get(clickIndex).getNewLoginDate()));
                                    intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(clickIndex).getGender().toString());

                                    startActivity(intent);
                                }


                            } else {
                                //      Toast.makeText(mContext, "用户类型获取失败" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 按钮默认布局
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void defaultLayout() {
        mMessageList.setBackgroundResource(0);
        mMessageList.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mAll.setBackgroundResource(0);
        mAll.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mPay.setBackgroundResource(0);
        mPay.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
        mFriend.setBackgroundResource(0);
        mFriend.setTextColor(mActivity.getResources().getColor(R.color.writeColor));
    }

    /**
     * 设置环信网络状态
     *
     * @param gNetConnectionHX
     */
    public void setHXNetWorkState(boolean gNetConnectionHX) {
        if (mHandler != null)
            mHandler.sendEmptyMessage(10);

    }


    class ButtonClick implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_fragmentHYHD_messageList:
                    mCurrent = 0;
                    defaultLayout();
                    mYSHY.setVisibility(View.GONE);
                    mMessageRecycleView.setVisibility(View.VISIBLE);
                    //   mJQImage.setVisibility(View.VISIBLE);
                    mHYSQText.setVisibility(View.GONE);
               //     mMessageList.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mMessageList.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
                    getMessageList();
                    break;
                case R.id.tv_fragmentHYHD_all:
                    mCurrent = 1;
                    defaultLayout();
                    mYSHY.setVisibility(View.GONE);
                    mHYSQText.setVisibility(View.GONE);
                    mMessageRecycleView.setVisibility(View.VISIBLE);
                    //   mJQImage.setVisibility(View.VISIBLE);
              //      mAll.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mAll.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
                    //获取数据
                    getHZDate();
                    break;
                case R.id.tv_fragmentHYHD_pay:
                    mCurrent = 2;
                    defaultLayout();
                    mYSHY.setVisibility(View.GONE);
                    //   mJQImage.setVisibility(View.VISIBLE);
                    mHYSQText.setVisibility(View.GONE);
                 //   mPay.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mPay.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
                    //获取数据
                    getQYHXDate();
                    break;

                case R.id.tv_fragmentHYHD_friend:
                    mCurrent = 3;
                    defaultLayout();
                    mYSHY.setVisibility(View.VISIBLE);
                    mMessageRecycleView.setVisibility(View.GONE);
                    //     mJQImage.setVisibility(View.GONE);
                    mHYSQText.setVisibility(View.VISIBLE);
                 //   mFriend.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mFriend.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
                    //获取数据
                    getYSLMDate();

                    break;
//                case R.id.iv_fragmentHYHD_jqImage:
//                    startActivity(new Intent(mContext, NewChatGroupActivity.class));
//                    break;
                case R.id.iv_fragmentHYHD_hysqImage:
                    startActivity(new Intent(mContext, HYHD_HYSQActivity.class));
                    break;
            }
        }
    }


    /**
     * 获取医生好友联盟数据
     */
    private void getYSLMDate() {
        getProgressBar("请稍候。。。", "正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(interactDoctorDetailInfo), Constant.SERVICEURL + "doctorManagePatient/interactDoctorUnionAllList");
                } catch (Exception e) {
                    Log.e("tag", "run: " + mNetRetStr);
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 获取签约患者数据
     */
    private void getQYHXDate() {
        getProgressBar("请稍候。。。", "正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(interactDoctorDetailInfo), Constant.SERVICEURL + "doctorManagePatient/interactPatientSigningList");
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
     * 获取全部患者数据
     */
    private void getHZDate() {
        getProgressBar("请稍候。。。", "正在获取数据");
        InteractDoctorDetailInfo interactDoctorDetailInfo = new InteractDoctorDetailInfo();
        interactDoctorDetailInfo.setDoctorId(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorId());
        interactDoctorDetailInfo.setDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(interactDoctorDetailInfo), Constant.SERVICEURL + "doctorManagePatient/interactPatientAllList");
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
            mDialogProgress = new ProgressDialog(getContext());
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
