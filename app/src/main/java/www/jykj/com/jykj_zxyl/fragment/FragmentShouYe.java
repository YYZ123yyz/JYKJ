package www.jykj.com.jykj_zxyl.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.sak.ultilviewlib.UltimateRefreshView;
import com.sak.ultilviewlib.interfaces.OnFooterRefreshListener;
import com.sak.ultilviewlib.interfaces.OnHeaderRefreshListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import entity.hzgl.BindPatientParment;
import entity.shouye.OperScanQrCodeInside;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import rx.functions.Action1;
import util.CustomViewPager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyLiveRoomActivity;
import www.jykj.com.jykj_zxyl.activity.home.NewsActivity;
import www.jykj.com.jykj_zxyl.activity.home.QRCodeActivity;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.AddPatientActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.jykj.com.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.jykj.com.jykj_zxyl.adapter.TittleFragmentAdapter;
import www.jykj.com.jykj_zxyl.adapter.TraditionFooterAdapter;
import www.jykj.com.jykj_zxyl.adapter.TraditionHeaderAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BannerAndHospitalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_html5.H5Activity;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.interfaces.OnClickRelationContractListener;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.activity.MyClinicDetialActivity;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.fragment.home.HomeAudioFragment;
import www.jykj.com.jykj_zxyl.fragment.home.HomeEducationFragment;
import www.jykj.com.jykj_zxyl.fragment.home.HomeGraphicFragment;
import www.jykj.com.jykj_zxyl.fragment.home.HomeVideoFragment;
import www.jykj.com.jykj_zxyl.live.AddLiveProgromActivity;
import www.jykj.com.jykj_zxyl.mypatient.activity.PatientActivity;
import yyz_exploit.Utils.MyImageView;
import yyz_exploit.bean.BindPatient;
import yyz_exploit.dialog.AddPatientAcitvityDialog;
import zxing.android.CaptureActivity;
import zxing.common.Constant;

import static android.app.Activity.RESULT_OK;


/**
 * 首页fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe extends AbstractMvpBaseFragment<HomePagerContract.View
        ,HomePagerPresenter> implements View.OnClickListener,HomePagerContract.View {
    private Context mContext;
    private MainActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    private LinearLayout mDoctorUnion;
    public static final int REQUEST_CODE_SCAN = 0x123;
    private TextView mUserNameText;                //用户名
    private MoreFeaturesPopupWindow mPopupWindow;
    private FragmentShouYe mFragment;
    public ProgressDialog mDialogProgress = null;
    private String qrCode;                         //需要绑定的二维码
    private ImageView mUserHead;
    private yyz_exploit.dialog.Home_imageDialog imageView1;
    private ImageView back;
    private AddPatientAcitvityDialog addPatientAcitvityDialog;

    private List<ProvideBasicsDomain> mList = new ArrayList<>();

    private BindPatientParment mBindPatientParment;
    //修改
    private ImageView ivCode, ivAdd, ivMessage;//顶部按钮
    private LinearLayout myClinicLin, myPatientLin, myLiveLin;//中间按钮
    private Banner topBanner,middleBanner;//banner
    private ArrayList<String> imageUrls;
    private ArrayList<String> topContentUrls;
    private ArrayList<String> imageMiddleUrls;
    private ArrayList<String> middleContentUrls;

    private CustomViewPager pager;
    private TittleFragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private HomeEducationFragment homeEducationFragment;
    private HomeVideoFragment homeVideoFragment;
    private HomeAudioFragment homeAudioFragment;
    private HomeGraphicFragment homeGraphicFragment;
    private UltimateRefreshView mRefreshLayout;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private int currentPos;

    public CustomViewPager getPager() {
        return pager;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mFragment = this;
        mApp = (JYKJApplication) getActivity().getApplication();
        mBindPatientParment = new BindPatientParment();
        initHandler();
        mDoctorUnion = view.findViewById(R.id.ll_doctor_union);
        mRefreshLayout=view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setBaseHeaderAdapter(new TraditionHeaderAdapter(this.getContext()));
        mRefreshLayout.setBaseFooterAdapter(new TraditionFooterAdapter(this.getContext()));
        //用户头像

        mUserNameText = view.findViewById(R.id.tv_fragmentShouYe_userNameText);

        //用户头像
        mUserHead = view.findViewById(R.id.iv_userhead);
        //修改

        ivCode = view.findViewById(R.id.iv_code);
        ivAdd = view.findViewById(R.id.iv_add);
        ivMessage = view.findViewById(R.id.iv_message);
        myClinicLin = view.findViewById(R.id.lin_myclinic);
        myPatientLin = view.findViewById(R.id.lin_mypatien);
        myLiveLin = view.findViewById(R.id.lin_mylive);
        topBanner = view.findViewById(R.id.top_banner);
        middleBanner = view.findViewById(R.id.middle_banner);
        pager = view.findViewById(R.id.page);
        tabLayout =  view.findViewById(R.id.tab_layout);

        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("健康教育");
        mTitles.add("视频");
        mTitles.add("音频");
        mTitles.add("图文");

        homeEducationFragment = new HomeEducationFragment();
        homeEducationFragment.setOnClickRelationContractListener(new OnClickRelationContractListener() {
            @Override
            public void finishRefresh() {
                mRefreshLayout.onHeaderRefreshComplete();
            }

            @Override
            public void finishLoadMore() {
                mRefreshLayout.onFooterRefreshComplete();
            }
        });
        homeVideoFragment = new HomeVideoFragment();
        homeVideoFragment.setOnClickRelationContractListener(new OnClickRelationContractListener() {
            @Override
            public void finishRefresh() {
                mRefreshLayout.onHeaderRefreshComplete();
            }

            @Override
            public void finishLoadMore() {
                mRefreshLayout.onFooterRefreshComplete();
            }
        });
        homeAudioFragment = new HomeAudioFragment();

        homeAudioFragment.setOnClickRelationContractListener(new OnClickRelationContractListener() {
            @Override
            public void finishRefresh() {
                mRefreshLayout.onHeaderRefreshComplete();
            }

            @Override
            public void finishLoadMore() {
                mRefreshLayout.onFooterRefreshComplete();
            }
        });
        homeGraphicFragment = new HomeGraphicFragment();
        homeGraphicFragment.setOnClickRelationContractListener(new OnClickRelationContractListener() {
            @Override
            public void finishRefresh() {
                mRefreshLayout.onHeaderRefreshComplete();
            }

            @Override
            public void finishLoadMore() {
                mRefreshLayout.onFooterRefreshComplete();
            }
        });
        fragmentList.add(homeEducationFragment);
        fragmentList.add(homeVideoFragment);
        fragmentList.add(homeAudioFragment);
        fragmentList.add(homeGraphicFragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView customView = (TextView) tab.getCustomView();
                Log.e("走没有","111"+customView);
                if (customView!=null){
                    customView.setTextSize(20);
                    customView.setTextColor(getResources().getColor(R.color.color_7a9eff));
                }
                int position = tab.getPosition();
                pager.resetHeight(position);
                pager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView customView = (TextView) tab.getCustomView();
                if (customView!=null){
                    customView.setTextSize(12);
                    customView.setTextColor(getResources().getColor(R.color.color_666666));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fragmentAdapter = new TittleFragmentAdapter(getChildFragmentManager(), fragmentList);
        pager.setAdapter(fragmentAdapter);
        pager.resetHeight(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPos=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(pager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            if (tabAt!=null){
                tabAt.setCustomView(getTabView(i));
            }
        }
        pager.setCurrentItem(0);

        if (tabLayout.getSelectedTabPosition() ==0){

            TextView customView = (TextView) tabLayout.getTabAt(0).getCustomView();
            if (customView!=null){
                customView.setTextSize(20);
                customView.setTextColor(getResources().getColor(R.color.color_7a9eff));
            }
        }
        initLoadingAndRetryManager();
        getBasicDate();
        initListener();

    }

    @Override
    protected void initData() {
        super.initData();
        imageUrls = new ArrayList<>();
        imageMiddleUrls = new ArrayList<>();
        topContentUrls=new ArrayList<>();
        middleContentUrls=new ArrayList<>();
        mPresenter.sendGetBannerAndHospitalInfoRequest("1"
                , Arrays.asList("1","2"),"0");

    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();

        });
        mLoadingLayoutManager.showLoading();

    }




    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String mNetRetStr = "";
                Bundle data = msg.getData();
                if (data != null) {
                    mNetRetStr = data.getString("result");
                }
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {

                        } else {
                            if ("1".equals(netRetEntity.getResData())) {
                                Log.e("tag", "handleMessage: " + "走了");
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
                                        bindPatientFriend(netRetEntity.getResMsg(), ed, qrCode, mBindPatientParment.getPatientLabelId(), mBindPatientParment.getPatientLabelName());
                                    }
                                });
                            }

                        }
                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {

                        } else {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 2:

                        break;
                    case 3:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {

                        } else {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        }
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
                String mNetRetStr;
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
                        Message message = new Message();
                        message.what = 10;
                        Bundle bundle = new Bundle();
                        bundle.putString("result", mNetRetStr);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    Message message = new Message();
                    message.what = 10;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", mNetRetStr);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    return;
                }
                Message message = new Message();
                message.what = 10;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        }.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        //启动程序，查询是否有未读消息
        getMessageCount();
    }

    /**
     * 获取未读消息数量
     */
    private void getMessageCount() {

        if (mApp.mViewSysUserDoctorInfoAndHospital != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
            hashMap.put("searchDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            new Thread() {
                public void run() {
                    String mNetRetStr;
                    try {
                        String string = new Gson().toJson(hashMap);
                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount");
                    } catch (Exception e) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                        mNetRetStr = new Gson().toJson(retEntity);
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", mNetRetStr);
                    message.setData(bundle);
                    message.what = 2;
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }



    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(mTitles.get(currentPosition));
        return view;
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
                tv_label.setText(items[arg1]);
                mBindPatientParment.setPatientLabelId(mList.get(arg1).getAttrCode() + "");
                mBindPatientParment.setPatientLabelName(mList.get(arg1).getAttrName());
            }
        });
        builder.create().show();

    }


    private void initListener() {
        mRefreshLayout.setOnFooterRefreshListener(new OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(UltimateRefreshView ultimateRefreshView) {
                switch (currentPos){
                    case 0:
                        homeEducationFragment.loadMoreData();
                        break;
                    case 1:
                        homeVideoFragment.loadMoreData();
                        break;
                    case 2:
                        homeAudioFragment.loadMoreData();
                        break;
                    case 3:
                        homeGraphicFragment.loadMoreData();
                        break;
                        default:
                }

            }
        });
        mRefreshLayout.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(UltimateRefreshView ultimateRefreshView) {
                switch (currentPos){
                    case 0:
                        homeEducationFragment.refreshData();
                        break;
                    case 1:
                        homeVideoFragment.refreshData();
                        break;
                    case 2:
                        homeAudioFragment.refreshData();
                        break;
                    case 3:
                        homeGraphicFragment.refreshData();
                        break;
                        default:
                }
            }
        });
        //mRefreshLayout.fin
        //修改
        ivAdd.setOnClickListener(this);
        ivCode.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        myClinicLin.setOnClickListener(this);
        myPatientLin.setOnClickListener(this);
        myLiveLin.setOnClickListener(this);
        topBanner.setOnBannerListener(position -> {
            String url = topContentUrls.get(position);
            Bundle bundle=new Bundle();
            bundle.putString("url",url);
            startActivity(H5Activity.class,bundle);
        });
        middleBanner.setOnBannerListener(position -> {
            String url = middleContentUrls.get(position);
            Bundle bundle=new Bundle();
            bundle.putString("url",url);
            startActivity(H5Activity.class,bundle);
        });
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
                startActivity(new Intent(getActivity(), MyClinicDetialActivity.class));
                //startActivity(new Intent(getActivity(), InspectionProjectChoosedActivity.class));
                break;
            case R.id.ll_wdhz:
                startActivity(new Intent(getActivity(), PatientActivity.class));
                break;
            case R.id.ll_quick_application:
//                mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
//                mPopupWindow.setSouYeFragment(mFragment);
//                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
//                    mPopupWindow.showAsDropDown(llQuickApplication, 0, 0);
//                } else
//                    mPopupWindow.dismiss();
//                break;


            //医师资格认证
            case R.id.home_certification:
                Intent intent = new Intent(getContext(), UserAuthenticationActivity.class);
                startActivity(intent);
                break;
            //添加患者
            case R.id.li_home_addPatient:
                mContext.startActivity(new Intent(mContext, AddPatientActivity.class));
                break;

            //修改
            case R.id.iv_code:
                startActivity(new Intent(getActivity(), QRCodeActivity.class));
                break;
            case R.id.iv_add:
                mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
                mPopupWindow.setSouYeFragment(mFragment);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                } else
                    mPopupWindow.dismiss();

                break;
            case R.id.iv_message:
                startActivity(new Intent(getActivity(), NewsActivity.class).putExtra("newMessage", mActivity.mProvideMsgPushReminderCount));
                break;
            case R.id.lin_myclinic:
                startActivity(new Intent(getActivity(), MyClinicDetialActivity.class));

                //startActivity(new Intent(getActivity(), PrescriptionMedicinalListActivity.class));
                break;
            case R.id.lin_mypatien:
                startActivity(new Intent(getActivity(), PatientActivity.class));
                break;
            case R.id.lin_mylive:
                startActivity(new Intent(getActivity(), MyLiveRoomActivity.class));
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
                String mNetRetStr = "";
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "doctorDataControlle/operScanQrCodeInside");
                    Log.e("tag", "添加 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
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
                String mNetRetStr = "";
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
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 医患绑定
     * bindPatientFriend(netRetEntity.getResMsg(), ed, qrCode,mBindPatientParment.getPatientLabelId(), mBindPatientParment.getPatientLabelName());
     */
    private void bindPatientFriend(String url, String reason, String qrCode, String patientLabel, String patientLabelName) {
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
                String mNetRetStr = "";
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "/" + url);
                    Log.e("tag", "医患绑定 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
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
        if (addPatientAcitvityDialog!=null) {
            addPatientAcitvityDialog.dismiss();
        }
    }


    @Override
    public void getBannerAndHospitalInfoResult(BannerAndHospitalInfoBean bannerAndHospitalInfoBean) {
        setDataInfo(bannerAndHospitalInfoBean);
        mLoadingLayoutManager.showContent();
    }

    /**
     * 设置数据
     * @param bannerAndHospitalInfoBean banner和医院信息
     */
    private void setDataInfo(BannerAndHospitalInfoBean bannerAndHospitalInfoBean){
        BannerAndHospitalInfoBean.HospitalInfoBean hospitalInfo = bannerAndHospitalInfoBean.getHospitalInfo();
        Glide.with(mContext).load(hospitalInfo.getImgUrl())
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mUserHead);
        mUserNameText.setText(hospitalInfo.getHospitalName());
        List<BannerAndHospitalInfoBean.ViewBasicsBannerFilesListBean>
                viewBasicsBannerFilesList = bannerAndHospitalInfoBean.getViewBasicsBannerFilesList();
        for (BannerAndHospitalInfoBean.ViewBasicsBannerFilesListBean
                viewBasicsBannerFilesListBean : viewBasicsBannerFilesList) {
            int positionType = viewBasicsBannerFilesListBean.getPositionType();
            if (positionType==1) {
                imageUrls.add(viewBasicsBannerFilesListBean.getViewBannerUrl());
                topContentUrls.add(viewBasicsBannerFilesListBean.getContentUrl());
            }else if(positionType==2){
                imageMiddleUrls.add(viewBasicsBannerFilesListBean.getViewBannerUrl());
                middleContentUrls.add(viewBasicsBannerFilesListBean.getContentUrl());
            }
        }

        topBanner.setDelayTime(5000)
                .setImageLoader(new MyLoader())
                .isAutoPlay(true)
                .setImages(imageUrls).start();
        middleBanner.setDelayTime(5000)
                .setImageLoader(new MyLoader())
                .isAutoPlay(true)
                .setImages(imageMiddleUrls).start();

    }

    @Override
    public void showEmpty() {
        mLoadingLayoutManager.showEmpty();
    }

    @Override
    public void showRetry() {
        mLoadingLayoutManager.showError();
    }

    public class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(mApp).load(path).into(imageView);
        }

    }


}