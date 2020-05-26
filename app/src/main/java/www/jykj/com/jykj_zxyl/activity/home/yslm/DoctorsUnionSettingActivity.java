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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.MemberListInfo;
import entity.unionInfo.ProvideUnionDoctorMember;
import entity.unionInfo.ProvideUnionDoctorOrg;
import entity.unionInfo.ProvideViewUnionDoctorMemberAndUnionDetailInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberDetailInfo;
import entity.unionInfo.UnionMemberSettingParment;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.MyUnionMemberListAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;


/**
 * 医生联盟 == > 设置
 */
public class DoctorsUnionSettingActivity extends AppCompatActivity {

    private LinearLayout llBack;

    public ProgressDialog mDialogProgress = null;

    private Context mContext;                                       //
    private DoctorsUnionSettingActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;

    private         TextView            mUserName;              //用户名
    private         TextView            mHospital;              //医院
    private         TextView            mDepartment;              //科室
    private         TextView            mTitleName;              //职称
    private         TextView            mUnionDate;              //归属联盟层级

    private         LinearLayout       mOperaLayout;            //操作权限布局
    private         LinearLayout       mUnionHeadLayout;            //层级负责人布局
    private         LinearLayout       mUnionDateLayout;            //归属层级布局
    private         LinearLayout       mViewPatientsLayout;            //查看患者权限布局
    private         LinearLayout       mBlacklistLayout;                //拉入黑名单权限布局

    private         ImageView           mOperaImage;            //操作权限选择图标
    private         ImageView           mUnionHeadImage;            //层级负责人选择图标
    private         ImageView           mViewPatientImage;            //查看患者权限选择图标
    private         ImageView           mBlacklistImage;            //拉入黑名单权限选择图标
    private         ProvideViewUnionDoctorMemberDetailInfo  mProvideViewUnionDoctorMemberDetailInfo;
    private UnionMemberSettingParment mUnionMemberSettingParment;           //提交的设置参数
    private         String              mUnionCode;
    private         String              mUnionName;
    private         TextView            mCommit;                        //提交
    private         ImageView           mHead;

    private         LinearLayout        mBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorunion_setting);
        ActivityUtil.setStatusBarMain(DoctorsUnionSettingActivity.this);

        mContext = this;
        mActivity = this;
        mProvideViewUnionDoctorMemberDetailInfo = (ProvideViewUnionDoctorMemberDetailInfo) getIntent().getSerializableExtra("doctorInfo");
        mApp = (JYKJApplication) getApplication();
        mUnionCode = getIntent().getStringExtra("unionCode");
        mUnionName = getIntent().getStringExtra("unionName");
        initLayout();
        initHandler();
        mUnionMemberSettingParment = new UnionMemberSettingParment();
        mUnionMemberSettingParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mUnionMemberSettingParment.setUnionCode(mUnionCode);
        mUnionMemberSettingParment.setDoctorCode(mProvideViewUnionDoctorMemberDetailInfo.getDoctorCode());
        mUnionMemberSettingParment.setDoctorName(mProvideViewUnionDoctorMemberDetailInfo.getUserName());
        mUnionMemberSettingParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mUnionMemberSettingParment.setFlagOperPower("0");
        mUnionMemberSettingParment.setFlagPerson("0");
        mUnionMemberSettingParment.setFlagSeePatient("0");
        mUnionMemberSettingParment.setFlagBlacklist("0");
        mUnionMemberSettingParment.setUnionOrgId("");
        mUnionMemberSettingParment.setUnionOrgName("");
        mUnionMemberSettingParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mUnionMemberSettingParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        setLayoutDate();
    }



    /**
     *
     */
    private void initLayout() {
        mBack = (LinearLayout)this.findViewById(R.id.ll_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       mUserName = (TextView)this.findViewById(R.id.tv_fragmentMySelf_nameText);
        mHospital = (TextView)this.findViewById(R.id.tv_hospital);
        mDepartment = (TextView)this.findViewById(R.id.tv_department);
        mTitleName = (TextView)this.findViewById(R.id.tv_title);
        mUnionDate = (TextView)this.findViewById(R.id.tv_unionDate);
        mHead = (ImageView)this.findViewById(R.id.iv_fragmentMyself_userHeadImage);
        mOperaLayout = (LinearLayout)this.findViewById(R.id.li_unionOpera);
        mUnionHeadLayout = (LinearLayout)this.findViewById(R.id.li_unionHead);
        mUnionDateLayout = (LinearLayout)this.findViewById(R.id.li_unionDate);
        mViewPatientsLayout = (LinearLayout)this.findViewById(R.id.li_viewPatients);
        mBlacklistLayout = (LinearLayout)this.findViewById(R.id.li_blacklist);

        mOperaLayout.setOnClickListener(new ButtonClick());
        mUnionHeadLayout.setOnClickListener(new ButtonClick());
        mUnionDateLayout.setOnClickListener(new ButtonClick());
        mViewPatientsLayout.setOnClickListener(new ButtonClick());
        mBlacklistLayout.setOnClickListener(new ButtonClick());

        mOperaImage = (ImageView)this.findViewById(R.id.iv_unionOpera);
        mUnionHeadImage = (ImageView)this.findViewById(R.id.iv_unionHead);
        mViewPatientImage = (ImageView)this.findViewById(R.id.iv_viewPatients);
        mBlacklistImage = (ImageView)this.findViewById(R.id.iv_blacklist);

        mCommit = (TextView)this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new ButtonClick());


    }

    //判断是否选择了层级
    @Override
    protected void onResume() {
        super.onResume();
        if (mApp.unionSettionChoiceOrg != null)
        {
            mUnionMemberSettingParment.setUnionOrgId(mApp.unionSettionChoiceOrg.getUnionOrgId()+"");
            mUnionMemberSettingParment.setUnionOrgName(mApp.unionSettionChoiceOrg.getOrgName());
        }
        setLayoutDate();
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_unionOpera:
                    if ("0".equals(mUnionMemberSettingParment.getFlagOperPower()))
                        mUnionMemberSettingParment.setFlagOperPower("1");
                    else
                        mUnionMemberSettingParment.setFlagOperPower("0");
                    setLayoutDate();
                    break;
                case R.id.li_unionHead:
                    if ("0".equals(mUnionMemberSettingParment.getFlagPerson()))
                        mUnionMemberSettingParment.setFlagPerson("1");
                    else
                        mUnionMemberSettingParment.setFlagPerson("0");
                    setLayoutDate();
                    break;
                case R.id.li_unionDate:
                    startActivity(new Intent(mContext,DoctorsUnionSettingChoiceOrgActivity.class).putExtra("unionCode",mUnionCode)
                    .putExtra("unionName",mUnionName)
                    .putExtra("choiceOrg",mUnionMemberSettingParment));
                    break;
                case R.id.li_viewPatients:
                    if ("0".equals(mUnionMemberSettingParment.getFlagSeePatient()))
                        mUnionMemberSettingParment.setFlagSeePatient("1");
                    else
                        mUnionMemberSettingParment.setFlagSeePatient("0");
                    setLayoutDate();
                    break;
                case R.id.li_blacklist:
                    if ("0".equals(mUnionMemberSettingParment.getFlagBlacklist()))
                        mUnionMemberSettingParment.setFlagBlacklist("1");
                    else
                        mUnionMemberSettingParment.setFlagBlacklist("0");
                    setLayoutDate();
                    break;
                case R.id.tv_commit:
                    commit();
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void commit() {
        getProgressBar("请稍候。。。。","正在提交数据");
        new Thread(){
            public void run(){
                try {

                    //实体转JSON字符串
                    String str = new Gson().toJson(mUnionMemberSettingParment);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"/unionDoctorController/operDoctorUnionMemberSet");
                    Log.i("deb",mNetRetStr);
                    System.out.println(mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0)
                    {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void setLayoutDate(){
        mUserName.setText(mProvideViewUnionDoctorMemberDetailInfo.getUserName());
        mHospital.setText(mProvideViewUnionDoctorMemberDetailInfo.getHospitalInfoName());
        mDepartment.setText(mProvideViewUnionDoctorMemberDetailInfo.getDepartmentName()+"("+mProvideViewUnionDoctorMemberDetailInfo.getDepartmentSecondName()+")");
        mTitleName.setText(mProvideViewUnionDoctorMemberDetailInfo.getDoctorTitleName());
        if (mProvideViewUnionDoctorMemberDetailInfo.getUserLogoUrl() != null && !"".equals(mProvideViewUnionDoctorMemberDetailInfo.getUserLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(mProvideViewUnionDoctorMemberDetailInfo.getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(mHead);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvideViewUnionDoctorMemberDetailInfo.getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mHead);
            }
        }

        if ("0".equals(mUnionMemberSettingParment.getFlagOperPower()))
            mOperaImage.setBackgroundResource(R.mipmap.nochoice);
        else
            mOperaImage.setBackgroundResource(R.mipmap.choice);

        if ("0".equals(mUnionMemberSettingParment.getFlagPerson()))
            mUnionHeadImage.setBackgroundResource(R.mipmap.nochoice);
        else
            mUnionHeadImage.setBackgroundResource(R.mipmap.choice);

        if ("0".equals(mUnionMemberSettingParment.getFlagSeePatient()))
            mViewPatientImage.setBackgroundResource(R.mipmap.nochoice);
        else
            mViewPatientImage.setBackgroundResource(R.mipmap.choice);

        if ("0".equals(mUnionMemberSettingParment.getFlagBlacklist()))
            mBlacklistImage.setBackgroundResource(R.mipmap.nochoice);
        else
            mBlacklistImage.setBackgroundResource(R.mipmap.choice);

        if (mUnionMemberSettingParment.getUnionOrgName() == null || "".equals(mUnionMemberSettingParment.getUnionOrgName()))
            mUnionDate.setText("请点击选择");
        else
            mUnionDate.setText(mUnionMemberSettingParment.getUnionOrgName());
    }



    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
    }




    /**
     *   获取进度条
     *   获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
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
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }
}
