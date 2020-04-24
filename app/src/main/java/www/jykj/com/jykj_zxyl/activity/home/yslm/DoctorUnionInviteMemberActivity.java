package www.jykj.com.jykj_zxyl.activity.home.yslm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.model.EaseImageCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.DoctorInfo.GetDoctorBindParment;
import entity.DoctorInfo.ProvideViewBindingDdGetBindingDoctor;
import entity.MemberListInfo;
import entity.basicDate.GetUnionNameParment;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideUnionDoctor;
import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.unionInfo.ProvideDoctorPatientUserInfo;
import entity.unionInfo.ProvideViewSysUserDoctorInfoAndHospital;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.DoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.adapter.InviteMemberListAdapter;
import www.jykj.com.jykj_zxyl.adapter.MyUnionMemberListAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentDoctorFriend;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentDoctorPliatform;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentMyApply;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentMyReview;
import www.jykj.com.jykj_zxyl.fragment.yslm.FragmentReviewHistory;
import www.jykj.com.jykj_zxyl.util.FullyLinearLayoutManager;
import www.jykj.com.jykj_zxyl.util.ProvincePicker;

/**
 * 医生联盟邀请加入
 */
public class DoctorUnionInviteMemberActivity extends AppCompatActivity {
    public          Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map
    public      Map<String,ProvideViewBindingDdGetBindingDoctor> mChoiceUser = new HashMap<>();
    private LinearLayout llBack;
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;


    private Context mContext;
    private Handler mHandler;
    private ProgressDialog mDialogProgress;

    private             int                 mPageSelect;                  //当前选中的   1是哪个页面  1=医生好友  2=平台医生
    private             FragmentDoctorFriend        mDoctorFriend;          //医生好友Fragment
    private             FragmentDoctorPliatform        mDoctorPlatform;          //平台医生Fragment

    private             TextView                mInviteButton;                  //邀请

    private         String                      mNetRetStr;                 //返回字符串
    private             JYKJApplication         mApp;
    private             String                  mUnionCode;                  //联盟编码
    private             String                  mUnionName;                  //联盟名称


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union_invidemember);
        mApp = (JYKJApplication) getApplication();
        mContext = this;
        mUnionCode = getIntent().getStringExtra("unionCode");
        mUnionName = getIntent().getStringExtra("unionName");
        initView();
        initHandler();
    }

    private void initHandler() {

            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what)
                    {
                        case 0:
                            cacerProgress();
                            NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            };

    }

    /*重新加载布局*/
    public void reLoadFragView(){
        /*现将该fragment从fragmentList移除*/
        if (fragmentList.contains(mDoctorFriend)){
            fragmentList.remove(mDoctorFriend);
        }
        /*从FragmentManager中移除*/
        getSupportFragmentManager().beginTransaction().remove(mDoctorFriend).commit();

        if (fragmentList.contains(mDoctorPlatform)){
            fragmentList.remove(mDoctorPlatform);
        }
        /*从FragmentManager中移除*/
        getSupportFragmentManager().beginTransaction().remove(mDoctorPlatform).commit();


        /*重新创建*/
        mDoctorFriend = new FragmentDoctorFriend();
        mDoctorPlatform = new FragmentDoctorPliatform();
        fragmentList.add(mDoctorFriend);
        fragmentList.add(mDoctorPlatform);

        fragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);

    }

//    /**
//     * 返回，先判断当前是否条件搜索
//     * 是则先清空条件，否则退出
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            if (mPageSelect == 0)
//            {
//                if (mDoctorFriend.mGetDoctorBindParment.getSearchDepartmentId() != null || !"".equals(mDoctorFriend.mGetDoctorBindParment.getSearchDepartmentId())
//                        || mDoctorFriend.mGetDoctorBindParment.getSearchHospitalInfoCode() != null || !"".equals(mDoctorFriend.mGetDoctorBindParment.getSearchHospitalInfoCode())
//                        || mDoctorFriend.mGetDoctorBindParment.getSearchProvince() != null || !"".equals(mDoctorFriend.mGetDoctorBindParment.getSearchProvince())
//                        || mDoctorFriend.mGetDoctorBindParment.getSearchDoctorName() != null || !"".equals(mDoctorFriend.mGetDoctorBindParment.getSearchDoctorName())
//                        || mDoctorFriend.mGetDoctorBindParment.getSearchQrCode() != null || !"".equals(mDoctorFriend.mGetDoctorBindParment.getSearchQrCode())
//                        )
//                {
//
//                    mDoctorFriend.mGetDoctorBindParment = new GetDoctorBindParment();
//                    mDoctorFriend.getDate();
//                    reLoadFragView();
//                }
//            }
//            else if (mPageSelect == 1)
//            {
//                if (mDoctorPlatform.mGetDoctorBindParment.getSearchDepartmentId() != null || !"".equals(mDoctorPlatform.mGetDoctorBindParment.getSearchDepartmentId())
//                        || mDoctorPlatform.mGetDoctorBindParment.getSearchHospitalInfoCode() != null || !"".equals(mDoctorPlatform.mGetDoctorBindParment.getSearchHospitalInfoCode())
//                        || mDoctorPlatform.mGetDoctorBindParment.getSearchProvince() != null || !"".equals(mDoctorPlatform.mGetDoctorBindParment.getSearchProvince())
//                        || mDoctorPlatform.mGetDoctorBindParment.getSearchDoctorName() != null || !"".equals(mDoctorPlatform.mGetDoctorBindParment.getSearchDoctorName())
//                        || mDoctorPlatform.mGetDoctorBindParment.getSearchQrCode() != null || !"".equals(mDoctorPlatform.mGetDoctorBindParment.getSearchQrCode())
//                        )
//                {
//                    mDoctorPlatform.mGetDoctorBindParment = new GetDoctorBindParment();
//                    mDoctorPlatform.getDate();
//
//                }
//            }
//            else
//            {
//                finish();
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void initView(){
        pager= (ViewPager) this.findViewById(R.id.page);
        tabLayout= (TabLayout) this.findViewById(R.id.tab_layout);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        mInviteButton = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_searchText);
        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invateDotor();
            }
        });
        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add("医生好友");
        mTitles.add("平台医生");
        mDoctorFriend = new FragmentDoctorFriend();
        mDoctorPlatform = new FragmentDoctorPliatform();
        fragmentList.add(mDoctorFriend);
        fragmentList.add(mDoctorPlatform);

        fragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPageSelect = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initListener(){
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    /**
     * 邀请医生
     */
    private void invateDotor() {
        if (mChoiceUser.size() == 0)
        {
            Toast.makeText(mContext,"请选择邀请人",Toast.LENGTH_SHORT).show();
            return;
        }
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    List<ProvideDoctorPatientUserInfo> list = new ArrayList<>();
                    //遍历选中map
                    for (Map.Entry<String, ProvideViewBindingDdGetBindingDoctor> entry : mChoiceUser.entrySet()) {
                        ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo = new ProvideDoctorPatientUserInfo();
                        provideDoctorPatientUserInfo.setUserCode(entry.getValue().getBindingDoctorCode());
                        provideDoctorPatientUserInfo.setUserName(entry.getValue().getUserName());
                        list.add(provideDoctorPatientUserInfo);
                    }

                    ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo = new ProvideDoctorPatientUserInfo();
                    provideDoctorPatientUserInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideDoctorPatientUserInfo.setUnionCode(mUnionCode);
                    provideDoctorPatientUserInfo.setUnionName(mUnionName);
                    provideDoctorPatientUserInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideDoctorPatientUserInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideDoctorPatientUserInfo.setInviteDoctorList(JSON.toJSONString(list));
                    String str = JSON.toJSONString(provideDoctorPatientUserInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"unionDoctorController/operDoctorUnionInviteMember");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    public void setRegionText() {
        if (mPageSelect == 0)
            mDoctorFriend.setRegionText(mChoiceRegionMap);
        else if (mPageSelect == 1)
            mDoctorPlatform.setRegionText(mChoiceRegionMap);
    }

    /**
     * 设置平台医生选中状态
     */
    public void setDoctorPlatformState() {
        mDoctorPlatform.setChoiceState();
    }

    /**
     * 设置医生好友选中状态
     */
    public void setDoctorFriendState() {
        mDoctorFriend.setChoiceState();
    }
}
