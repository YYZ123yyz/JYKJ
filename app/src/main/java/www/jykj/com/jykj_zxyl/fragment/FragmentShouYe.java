package www.jykj.com.jykj_zxyl.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import entity.basicDate.ProvideBasicsDomain;
import entity.home.newsMessage.ProvideMsgPushReminderCount;
import entity.hzgl.BindPatientParment;
import entity.shouye.OperScanQrCodeInside;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import rx.functions.Action1;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.DoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyLiveRoomActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyPatientActivity;
import www.jykj.com.jykj_zxyl.activity.home.NewsActivity;
import www.jykj.com.jykj_zxyl.activity.home.QRCodeActivity;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.AddPatientActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.jykj.com.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import yyz_exploit.Utils.MyImageView;
import yyz_exploit.activity.activity.Home_DetailsActivity;
import yyz_exploit.activity.activity.Home_FeaturedActivity;
import yyz_exploit.bean.BindPatient;
import yyz_exploit.dialog.AddPatientAcitvityDialog;
import zxing.android.CaptureActivity;
import zxing.common.Constant;

import static android.app.Activity.RESULT_OK;


/**
 * 首页fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe extends Fragment implements View.OnClickListener {
    private Context mContext;
    private MainActivity mActivity;
    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;
    private JYKJApplication mApp;
    private LinearLayout mQrCode;
    private LinearLayout mNews;
    private LinearLayout mDoctorUnion;
    private LinearLayout mYQTH;//邀请同行
    private LinearLayout mMyComments;//我的评价
    private LinearLayout mMyLiveRoom;//我的直播间
    private LinearLayout mAddPatient;                   //添加患者
    private LinearLayout mScan;      //扫一扫
    private LinearLayout mMyClinic;//我的诊所
    private LinearLayout mMyPatient;
    public static final int REQUEST_CODE_SCAN = 0x123;

    private TextView mUserNameText;                //用户名
    private TextView mUserTitleText;               //医生职称

    private TextView mNewMessage;                  //新消息提醒
    private LinearLayout mNewMessageLayout;          //新消息提醒
    private LinearLayout llQuickApplication;//快应用
    private MoreFeaturesPopupWindow mPopupWindow;
    private FragmentShouYe mFragment;
    public ProgressDialog mDialogProgress = null;
    private String qrCode;                         //需要绑定的二维码
    private ImageView mUserHead;
    private TextView home_Tv;

    private LinearLayout home_certification;

    private SharedPreferences sp;
    private Button button;
    private LinearLayout home_lin;
    private LinearLayout lin;
    private LinearLayout lin_featured,lin_featured2;
    private MyImageView img_one,img_two,img_three;
    private MyImageView imgs_one,imgs_two,imgs_three;
    private MyImageView img_ones,img_twos,img_threes;
    private yyz_exploit.dialog.Home_imageDialog imageView1;
    private ImageView back;
    private AddPatientAcitvityDialog addPatientAcitvityDialog;

    private List<ProvideBasicsDomain> mList = new ArrayList<>();

    private BindPatientParment mBindPatientParment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_shouyefragment, container, false);

        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mFragment = this;
        mApp = (JYKJApplication) getActivity().getApplication();
        mBindPatientParment = new BindPatientParment();
        initHandler();
        initView(v);
        //  getMessageCount();
        getBasicDate();
        initListener();
        return v;
    }


//    /**
//     * 启动定时器，轮询获取未读消息数
//     */
//    private void startMessageTimer() {
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                getMessageCount();
//            }
//        };
//        timer.schedule(task, 0, mApp.mMsgTimeInterval * 60 * 1000);
//    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0){

                        }
                        else {
                            if ("1".equals(netRetEntity.getResData())) {
                                Log.e("tag", "handleMessage: "+"走了" );
                                //医生扫医生二维码，绑定医生好友
                                final EditText et = new EditText(mContext);
                                new AlertDialog.Builder(mContext).setTitle("请输入申请描述")
                                        .setView(et)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //按下确定键后的事件
                                                bindDoctorFriend(netRetEntity.getResMsg(), et.getText().toString(), qrCode);
                                            }
                                        }).setNegativeButton("取消", null).show();
//
                            }
                            if ("2".equals(netRetEntity.getResData())) {
                                //医生扫医生联盟二维码
                            }
                            if ("3".equals(netRetEntity.getResData())) {
                                //医生扫患者二维码
                                addPatientAcitvityDialog = new AddPatientAcitvityDialog(getContext());
                                addPatientAcitvityDialog.show();
                                EditText app_content = addPatientAcitvityDialog.findViewById(R.id.app_content);
                                //输入的值
                                String ed = app_content.getText().toString();
                                addPatientAcitvityDialog.findViewById(R.id.Lin_label).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showHZBQDialog();
                                    }
                                });
                                addPatientAcitvityDialog.findViewById(R.id.bt_pass).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addPatientAcitvityDialog.dismiss();
                                    }
                                });
                                addPatientAcitvityDialog.findViewById(R.id.bt_commit).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bindPatientFriend(netRetEntity.getResMsg(), ed, qrCode,mBindPatientParment.getPatientLabelId(), mBindPatientParment.getPatientLabelName());
                                    }
                                });
                            }

                        }
                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
//                        if(netRetEntity.getResCode()==0){
//
//                        }else{
//                              Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
//                        }

                        break;
                    case 2:
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if(netRetEntity.getResCode()==1){
                            ProvideMsgPushReminderCount   mProvideMsgPushReminderCount = JSON.parseObject(netRetEntity.getResJsonData(), ProvideMsgPushReminderCount.class);
                            if(mProvideMsgPushReminderCount.getMsgTypeCountSum()==0){
                                mNewMessageLayout.setVisibility(View.GONE);
                            }
                            else  {
                                mNewMessageLayout.setVisibility(View.VISIBLE);
                                mNewMessage.setText( "您有" + mProvideMsgPushReminderCount.getMsgTypeCountSum() + "条未读消息!");
                            }
                        }else{
                            mNewMessageLayout.setVisibility(View.GONE);
                        }
                        break;
                    case 3:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
//                        if(netRetEntity.getResCode()==0){
//
//                        }else{
//                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
//                        }
                        break;
                    case 10:
                        cacerProgress();
                        break;
                }
            }
        };
    }

    //获取患者标签
    public void getBasicDate() {
        getProgressBar("请稍候", "正在获取数据。。。");
        //开始识别
        new Thread() {
            public void run() {
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(30001);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        //   retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(10);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(10);
                    return;
                }

                mHandler.sendEmptyMessage(10);
            }
        }.start();
    }



    @Override
    public void onResume() {
        super.onResume();
        //启动程序，查询是否有未读消息
        getMessageCount();
        //   getAppData();
    }

    /**
     * 获取未读消息数量
     */
    private void getMessageCount() {
        ProvideMsgPushReminderCount provideMsgPushReminderCount = new ProvideMsgPushReminderCount();
        if(mApp.mViewSysUserDoctorInfoAndHospital!=null){
            provideMsgPushReminderCount.setLoginDoctorPosition(mApp.loginDoctorPosition);
            provideMsgPushReminderCount.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            new Thread() {
                public void run() {
                    try {
                        String string = new Gson().toJson(provideMsgPushReminderCount);
                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount");
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
    }


    /**
     * 设置新消息提醒
     *
     * @param //string
     */

    private void initView(View view) {
        //第一张图片
        img_one = view.findViewById(R.id.img_one);
        img_one.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor1.png");
        img_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
                imageView1.show();
                MyImageView im = imageView1.findViewById(R.id.img);
                im.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor1.png");

                back = imageView1.findViewById(R.id.im_back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView1.dismiss();
                    }
                });
            }
        });
        //第er张图片
        img_two = view.findViewById(R.id.img_two);
        img_two.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor2.png");
        img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_two();
            }
        });
        //第三张图片
        img_three = view.findViewById(R.id.img_three);
        img_three.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor3.png");
        img_three.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             img_three();
                                         }
                                     }
        );
        imgs_one = view.findViewById(R.id.imgs_one);
        imgs_one.setImageURL("http://jiuyihtn.com/AppAssembly/img/fakeFriends3.jpg");
        imgs_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgs_one();
            }
        });
        imgs_two = view.findViewById(R.id.imgs_two);
        imgs_two.setImageURL("http://jiuyihtn.com/AppAssembly/img/logo2.png");
        imgs_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
                imageView1.show();
                MyImageView im = imageView1.findViewById(R.id.img);
                im.setImageURL("http://jiuyihtn.com/AppAssembly/img/logo2.png");

                back = imageView1.findViewById(R.id.im_back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView1.dismiss();
                    }
                });
            }
        });
        imgs_three = view.findViewById(R.id.imgs_three);
        imgs_three.setImageURL("http://jiuyihtn.com/AppAssembly/img/fakeFriends4.jpg");


        img_ones = view.findViewById(R.id.img_ones);
        img_ones.setImageURL("http://jiuyihtn.com/AppAssembly/img/main1.png");
        img_twos = view.findViewById(R.id.img_twos);
        img_twos.setImageURL("http://jiuyihtn.com/AppAssembly/img/main3.jpg");
        img_threes = view.findViewById(R.id.img_threes);
        img_threes.setImageURL("http://jiuyihtn.com/AppAssembly/img/main2.jpg");

        lin = view.findViewById(R.id.lin);
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), Home_DetailsActivity.class);
                startActivity(intent1);
            }
        });
        home_lin = view.findViewById(R.id.home_lin);
        home_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Home_DetailsActivity.class);
                startActivity(intent);
            }
        });

        lin_featured = view.findViewById(R.id.lin_featured);
        lin_featured2 = view.findViewById(R.id.lin_featured2);
        lin_featured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getContext(), Home_FeaturedActivity.class);
                startActivity(intent3);
            }
        });
        lin_featured2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getContext(), Home_FeaturedActivity.class);
                startActivity(intent4);
            }
        });
        mQrCode = view.findViewById(R.id.ll_qr_code);
        mNews = view.findViewById(R.id.ll_news);
        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
        mYQTH = view.findViewById(R.id.ll_yqth);
        mMyComments = view.findViewById(R.id.ll_my_comment);
        //添加患者
        mAddPatient = view.findViewById(R.id.li_home_addPatient);

        mMyLiveRoom = view.findViewById(R.id.ll_my_liveroom);
        mScan = view.findViewById(R.id.ll_sys);
        mMyClinic = view.findViewById(R.id.ll_wdzs);

        //用户头像

        mUserNameText = (TextView) view.findViewById(R.id.tv_fragmentShouYe_userNameText);

        mUserTitleText = (TextView) view.findViewById(R.id.tv_fragmentShouYe_userTitleText);
        mMyPatient = view.findViewById(R.id.ll_wdhz);
        mNewMessage = (TextView) view.findViewById(R.id.tv_fragmentShouYe_NewMessage);
        if (mApp.mViewSysUserDoctorInfoAndHospital != null) {
            mUserNameText.setText(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            mUserTitleText.setText(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorTitleName());
        }

        mNewMessageLayout = (LinearLayout) view.findViewById(R.id.li_fragmentShouYe_newMessage);
        mNewMessageLayout.setVisibility(View.GONE);
        llQuickApplication = (LinearLayout) view.findViewById(R.id.ll_quick_application);
        //用户头像
        mUserHead = (ImageView) view.findViewById(R.id.iv_userhead);

        //医师资格认证
        home_certification = view.findViewById(R.id.home_certification);

        if (mApp.mViewSysUserDoctorInfoAndHospital != null) {
            if (mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl() != null && !"".equals(mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl())) {
                try {
                    int avatarResId = Integer.parseInt(mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                    Glide.with(mContext).load(avatarResId).into(mUserHead);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(mUserHead);
                }
            }

        }

    }


    /**
     * 患者标签选择框
     */
    private void showHZBQDialog() {
        final String[] items = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            items[i] = mList.get(i).getAttrName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                TextView tv_label = addPatientAcitvityDialog.findViewById(R.id.tv_label);
                tv_label .setText(items[arg1]);
                mBindPatientParment.setPatientLabelId(mList.get(arg1).getAttrCode() + "");
                mBindPatientParment.setPatientLabelName(mList.get(arg1).getAttrName());
            }
        });
        builder.create().show();

    }



    private void imgs_one() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/fakeFriends3.jpg");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void img_three() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor3.png");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void img_two() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/doctor2.png");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void initListener() {
        mQrCode.setOnClickListener(this);
        mNews.setOnClickListener(this);
        mYQTH.setOnClickListener(this);
        mMyComments.setOnClickListener(this);
        //添加患者
        mAddPatient.setOnClickListener(this);
        mMyLiveRoom.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mMyClinic.setOnClickListener(this);
        mMyPatient.setOnClickListener(this);
        mNewMessageLayout.setOnClickListener(this);
        llQuickApplication.setOnClickListener(this);
        //医师资格认证
        home_certification.setOnClickListener(this);

        imgs_three.setOnClickListener(this);
        img_ones.setOnClickListener(this);
        img_twos.setOnClickListener(this);
        img_threes.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgs_three:
                imgs_three();
                break;
            case R.id.img_ones:
                img_ones();
                break;
            case R.id.img_twos:
                img_twos();
                break;
            case R.id.img_threes:
                img_threes();
                break;

            case R.id.ll_qr_code:
                startActivity(new Intent(getActivity(), QRCodeActivity.class));
                break;
            case R.id.ll_news:
                startActivity(new Intent(getActivity(), NewsActivity.class).putExtra("newMessage", mActivity.mProvideMsgPushReminderCount.getMsgTypeCountSum()));
                break;
            case R.id.li_fragmentShouYe_newMessage:
                startActivity(new Intent(getActivity(), NewsActivity.class).putExtra("newMessage", mActivity.mProvideMsgPushReminderCount.getMsgTypeCountSum()));
                break;
//            case R.id.ll_doctor_union:
//                startActivity(new Intent(getActivity(), DoctorsUnionActivity.class));
//                break;

            case R.id.ll_yqth:
//                startActivity(new Intent(getActivity(),InvitepeersActivity.class));
                break;
            case R.id.ll_my_comment:
//                startActivity(new Intent(getActivity(),MyCommentActivity.class));
                break;

//            case R.id.li_fragmentShouYe_addPatient:
////                startActivity(new Intent(getActivity(),AddPatientActivity.class));
//                break;
            case R.id.ll_my_liveroom:
                startActivity(new Intent(getActivity(), MyLiveRoomActivity.class));
                break;
            case R.id.ll_sys:
                scan();
                break;
            //我的诊所
            case R.id.ll_wdzs:
                startActivity(new Intent(getActivity(), MyClinicActivity.class));
                break;
            case R.id.ll_wdhz:
                startActivity(new Intent(getActivity(), MyPatientActivity.class));
                break;
            case R.id.ll_quick_application:
                mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
                mPopupWindow.setSouYeFragment(mFragment);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(llQuickApplication, 0, 0);
                }else
                    mPopupWindow.dismiss();
                break;
            //医师资格认证
            case R.id.home_certification:
                Intent intent = new Intent(getContext(), UserAuthenticationActivity.class);
                startActivity(intent);
                break;
            //添加患者
            case R.id.li_home_addPatient:
                mContext.startActivity(new Intent(mContext, AddPatientActivity.class));
                break;
        }
    }

    private void img_threes() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/main2.jpg");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void img_twos() {

        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/main3.jpg");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void img_ones() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/main1.png");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    private void imgs_three() {
        imageView1 = new yyz_exploit.dialog.Home_imageDialog(getContext());
        imageView1.show();
        MyImageView im = imageView1.findViewById(R.id.img);
        im.setImageURL("http://jiuyihtn.com/AppAssembly/img/fakeFriends4.jpg");

        back = imageView1.findViewById(R.id.im_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.dismiss();
            }
        });
    }

    /**
     * 扫一扫
     */
    private void scan() {
        RxPermissions.getInstance(getActivity())
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {//允许权限，6.0以下默认true
                            Intent intent = new Intent(getActivity(), CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE_SCAN);
                        } else {
                            Toast.makeText(getActivity(), "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                qrCode = content;
                operScanQrCodeInside(content);
            }
        }
    }

    private void operScanQrCodeInside(String content) {
        getProgressBar("请稍候", "正在处理");
        OperScanQrCodeInside operScanQrCodeInside = new OperScanQrCodeInside();
        operScanQrCodeInside.setLoginDoctorPosition(mApp.loginDoctorPosition);
        operScanQrCodeInside.setUserUseType("5");
        operScanQrCodeInside.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        operScanQrCodeInside.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        operScanQrCodeInside.setScanQrCode(content);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "doctorDataControlle/operScanQrCodeInside");
                    Log.e("tag", "添加 "+mNetRetStr );
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
     * 医生好友绑定
     */
    private void bindDoctorFriend(String url, String reason, String qrCode) {
        getProgressBar("请稍候", "正在处理");
        BindDoctorFriend bindDoctorFriend = new BindDoctorFriend();
        bindDoctorFriend.setLoginDoctorPosition(mApp.loginDoctorPosition);
        bindDoctorFriend.setBindingDoctorQrCode(qrCode);
        bindDoctorFriend.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        bindDoctorFriend.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        bindDoctorFriend.setApplyReason(reason);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "/" + url);

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
     * 医患绑定
     *    bindPatientFriend(netRetEntity.getResMsg(), ed, qrCode,mBindPatientParment.getPatientLabelId(), mBindPatientParment.getPatientLabelName());
     */
    private void bindPatientFriend(String url, String reason, String qrCode,String patientLabel,String patientLabelName) {
        getProgressBar("请稍候", "正在处理");
        BindPatient bindDoctorFriend = new BindPatient();
        bindDoctorFriend.setLoginDoctorPosition(mApp.loginDoctorPosition);
        bindDoctorFriend.setPatientQrCode(qrCode);
        bindDoctorFriend.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        bindDoctorFriend.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        bindDoctorFriend.setPatientLabelId(patientLabel);
        bindDoctorFriend.setPatientLabelName(patientLabelName);
        bindDoctorFriend.setApplyReason(reason);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "/" + url);
                    Log.e("tag", "医患绑定 "+mNetRetStr );
                } catch (Exception e) {
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