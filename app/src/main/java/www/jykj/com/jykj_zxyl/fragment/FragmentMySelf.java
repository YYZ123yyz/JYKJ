package www.jykj.com.jykj_zxyl.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.hyphenate.easeui.jykj.bean.ProvideDoctorPatientUserInfo;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.PhoneLoginActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.activity.home.MyClinicActivity;
import www.jykj.com.jykj_zxyl.activity.home.QRCodeActivity;
import www.jykj.com.jykj_zxyl.activity.myself.ExitAllianceActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyAccountActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyPBActivity;
import www.jykj.com.jykj_zxyl.activity.myself.PlatformLicenseActivity;
import www.jykj.com.jykj_zxyl.activity.myself.ServicePermisionActivity;
import www.jykj.com.jykj_zxyl.activity.myself.SettingActivity;
import www.jykj.com.jykj_zxyl.activity.myself.ShareDataSetActivity;
import www.jykj.com.jykj_zxyl.activity.myself.UserAuthenticationActivity;
import www.jykj.com.jykj_zxyl.activity.myself.UserCenterActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CheckWithdrawalPwdSetStatusBean;
import www.jykj.com.jykj_zxyl.app_base.base_enumtype.JumpTypeEnum;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.activity.MyOnlineScheduActivity;
import www.jykj.com.jykj_zxyl.capitalpool.activity.BalanceActivity;
import www.jykj.com.jykj_zxyl.capitalpool.activity.ModifyIinforActivity;
import www.jykj.com.jykj_zxyl.capitalpool.activity.RechargeActivity;
import www.jykj.com.jykj_zxyl.capitalpool.activity.StatisticChartActivity;
import www.jykj.com.jykj_zxyl.capitalpool.activity.UserAccountActivity;
import www.jykj.com.jykj_zxyl.personal.activity.MyServiceHistoryActivity;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.personal.activity.ReferenceMapActivity;
import www.jykj.com.jykj_zxyl.personal.activity.WarningActivity;
import www.jykj.com.jykj_zxyl.util.ImageViewUtil;
import yyz_exploit.activity.Myself_Service;
import yyz_exploit.activity.activity.LectureActivity;
import yyz_exploit.activity.activity.NoticeActivity;


/**
 * 个人中心fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentMySelf extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private JYKJApplication mApp;
    private LinearLayout mMyAccountLinearLayout;             //我的账户布局
    private LinearLayout mPBLinearLayout;                    //我的排班布局
    private LinearLayout mShareDataSetLinearLayout;          //我的共享数据设置布局
    private LinearLayout mServicePermisionLinearLayout;          //我的共享数据设置布局
    private LinearLayout mPlatformLicensesLayout;                     //我的平台授权
    private LinearLayout mExitAllianceLayout;                     //退出联盟
    private LinearLayout mSetting;                           //设置
    private ImageView mUserHead;                          //用户头像
    private TextView mNameText;                          //姓名
    private ImageView mUserAuthentication;                //用户认证
    private LinearLayout myself_service;
    private LinearLayout lin_notice;
    private LinearLayout lin_lecture;
    private LinearLayout lin_invite;
    private LinearLayout lin_warning;
    private LinearLayout mLlWalletRoot;
    private LinearLayout mLlCouponRoot;
    private LinearLayout mLlIntegralRoot;
    private String setStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activitymain_myselffragment, container, false);
        mContext = getContext();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        return v;
    }

    private void initLayout(View v) {

        lin_invite = v.findViewById(R.id.lin_invite);
        lin_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QRCodeActivity.class);
                startActivity(intent);
            }
        });
        mLlWalletRoot=v.findViewById(R.id.ll_wallet_root);
        mLlCouponRoot=v.findViewById(R.id.ll_coupon_root);
        mLlIntegralRoot=v.findViewById(R.id.ll_integral_root);
        mMyAccountLinearLayout = (LinearLayout) v.findViewById(R.id.li_fragmentMySelf_MyAccount);
        mPBLinearLayout = (LinearLayout) v.findViewById(R.id.li_fragmentMySelf_PB);
        mShareDataSetLinearLayout = (LinearLayout) v.findViewById(R.id.li_fragmentMySelf_shareDataSet);
        mServicePermisionLinearLayout = (LinearLayout) v.findViewById(R.id.li_fragmentMySelf_servicePermission);
        //    mPlatformLicensesLayout = (LinearLayout)v.findViewById(R.id.li_fragmentMySelf_platformLicenses);
        //  mExitAllianceLayout = (LinearLayout)v.findViewById(R.id.li_fragmentMySelf_exitAlliance);
        mSetting = (LinearLayout) v.findViewById(R.id.li_fragmentMySelf_setting);
        //头像
        mUserHead = (ImageView) v.findViewById(R.id.iv_fragmentMyself_userHeadImage);
        mUserHead.setOnClickListener(new ButtonClick());
        mNameText = (TextView) v.findViewById(R.id.tv_fragmentMySelf_nameText);
        mUserAuthentication = (ImageView) v.findViewById(R.id.iv_fragmentMyself_userAuthentication);


        mMyAccountLinearLayout.setOnClickListener(new ButtonClick());
        mPBLinearLayout.setOnClickListener(new ButtonClick());
        mShareDataSetLinearLayout.setOnClickListener(new ButtonClick());
        mServicePermisionLinearLayout.setOnClickListener(new ButtonClick());
        //  mPlatformLicensesLayout.setOnClickListener(new ButtonClick());
        // mExitAllianceLayout.setOnClickListener(new ButtonClick());
        mSetting.setOnClickListener(new ButtonClick());
        mNameText.setOnClickListener(new ButtonClick());
        mUserAuthentication.setOnClickListener(new ButtonClick());

        //我的服务历史
        myself_service = v.findViewById(R.id.myself_service);
        myself_service.setOnClickListener(new ButtonClick());

        lin_notice = v.findViewById(R.id.lin_Notice);
        lin_notice.setOnClickListener(new ButtonClick());


        lin_lecture = v.findViewById(R.id.lin_Lecture);
        lin_lecture.setOnClickListener(new ButtonClick());

        lin_warning = v.findViewById(R.id.lin_warning);
        lin_warning.setOnClickListener(new ButtonClick());
        mLlWalletRoot.setOnClickListener(new ButtonClick());
        mLlCouponRoot.setOnClickListener(new ButtonClick());
        mLlIntegralRoot.setOnClickListener(new ButtonClick());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            sendGetUserInfoRequest();
            sendCheckWithdrawalPwdSetStatusRequest();
        }
    }

    private void sendGetUserInfoRequest() {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCode",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfo(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        int resCode = baseBean.getResCode();
                        if (resCode==1) {
                            ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo =
                                    GsonUtils.fromJson(baseBean.getResJsonData(),
                                            ProvideDoctorPatientUserInfo.class);
                            if (provideDoctorPatientUserInfo!=null) {
                                int flagDoctorStatus = provideDoctorPatientUserInfo.getFlagDoctorStatus();
                                mApp.mViewSysUserDoctorInfoAndHospital.setFlagDoctorStatus(flagDoctorStatus);
                                if (flagDoctorStatus==0) {
                                    mUserAuthentication.setImageResource(R.mipmap.fragmentmyself_wrz);
                                }else{
                                    mUserAuthentication.setImageResource(R.mipmap.fragmentmyself_yrz);
                                }

                                ImageViewUtil.showImageView(getContext(),
                                        provideDoctorPatientUserInfo.getUserLogoUrl(), mUserHead);
                                mNameText.setText(provideDoctorPatientUserInfo.getUserName());
                            }

                        }
                    }
                });
    }


    /**
     * 检查医生支付/提现密码设置状态请求
     */
    private void sendCheckWithdrawalPwdSetStatusRequest(){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(this.getActivity());
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().checkWithdrawalPwdSetStatus(s).compose(
                Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    String resJsonData = baseBean.getResJsonData();
                    CheckWithdrawalPwdSetStatusBean checkWithdrawalPwdSetStatusBean
                            = GsonUtils.fromJson(resJsonData, CheckWithdrawalPwdSetStatusBean.class);
                    if (checkWithdrawalPwdSetStatusBean!=null) {
                         setStatus = checkWithdrawalPwdSetStatusBean.getSetStatus();
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sendGetUserInfoRequest();
        sendCheckWithdrawalPwdSetStatusRequest();
//        if (mApp.mViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus() == 1)
//            mUserAuthentication.setImageResource(R.mipmap.fragmentmyself_yrz);
//        else
//            mUserAuthentication.setImageResource(R.mipmap.fragmentmyself_wrz);


    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //直播预告
                case R.id.lin_Notice:
                    Intent intent2 = new Intent(getContext(), NoticeActivity.class);
                    startActivity(intent2);
                    break;
                //专题讲座
                case R.id.lin_Lecture:
                    Intent intent3 = new Intent(getContext(), LectureActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.li_fragmentMySelf_MyAccount:
                    //我的账户
                    Intent intent = new Intent();
                    intent.setClass(mContext, MyAccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.li_fragmentMySelf_PB:
                    //我的排班
                    intent = new Intent();
                    intent.setClass(mContext, MyOnlineScheduActivity.class);
                    startActivity(intent);
                    break;
                case R.id.li_fragmentMySelf_shareDataSet:
                    //我的患者数据共享设置
                    intent = new Intent();
                    intent.setClass(mContext, ShareDataSetActivity.class);
                    startActivity(intent);
                    break;
                case R.id.li_fragmentMySelf_servicePermission:
                    //我的服务权限开通
                    intent = new Intent();
                    intent.setClass(mContext, ServicePermisionActivity.class);
                    startActivity(intent);
                    break;
                /*case R.id.li_fragmentMySelf_platformLicenses:
                    //我的服务权限设置
                    intent = new Intent();
                    intent.setClass(mContext,PlatformLicenseActivity.class);
                    startActivity(intent);
                    break;
                case R.id.li_fragmentMySelf_exitAlliance:
                    //退出联盟
                    intent = new Intent();
                    intent.setClass(mContext,ExitAllianceActivity.class);
                    startActivity(intent);
                    break;*/
                case R.id.li_fragmentMySelf_setting:
                    //设置
                    intent = new Intent();
                    intent.setClass(mContext, SettingActivity.class);
                    startActivity(intent);
                    break;

                case R.id.iv_fragmentMyself_userHeadImage:
                    //个人中心
                    intent = new Intent();
                    intent.setClass(mContext, UserCenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.iv_fragmentMyself_userAuthentication:
                    //用户认证
                    intent = new Intent();
                    intent.setClass(mContext, UserAuthenticationActivity.class);
                    startActivity(intent);
                    break;
                //我的服务历史
                case R.id.myself_service:
//                    Intent intent1 = new Intent(getActivity(), Myself_Service.class);
//                    startActivity(intent1);

                    Intent intent1 = new Intent(getActivity(), MyServiceHistoryActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.lin_warning:
                    Intent intent5 = new Intent(getActivity(), RechargeActivity.class);//WarningActivity.class   ReferenceMapActivity   RechargeActivity
                    startActivity(intent5);
                    break;
                case R.id.ll_wallet_root:
                    if (setStatus.equals("0")) {
                        Intent intent7 = new Intent(getActivity(), ModifyIinforActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("type",0);
                        bundle.putString("targetActivity", JumpTypeEnum.JUMP_BALANCE_ACTIVITY);
                        intent7.putExtras(bundle);
                        startActivity(intent7);
                    } else if (setStatus.equals("1")) {
                        Intent intent6 = new Intent(getActivity(), BalanceActivity.class);
                        startActivity(intent6);
                    }
                    break;
                case R.id.ll_coupon_root:

                    break;
                case R.id.ll_integral_root:

                    break;
                    default:
            }
        }

    }



}
