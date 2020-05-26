package www.jykj.com.jykj_zxyl.activity.myself;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.mySelf.servicePermision.ProvideDoctorSetParment;
import entity.mySelf.servicePermision.ProvideDoctorSetService;
import entity.mySelf.servicePermision.ProvideDoctorSetServiceState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 服务权限开通设置
 */
public class ServicePermisionSetActivity extends AppCompatActivity {

    private Context mContext;
    private ServicePermisionSetActivity mActivity;
    public ProgressDialog mDialogProgress = null;
    private Handler mHandler;
    private String mNetRetStr;                 //获取返回字符串
    private JYKJApplication mApp;
    private ProvideDoctorSetService mProvideDoctorSetService;

    private int mServiceType;              //服务类型
    private int mDoctorStatus;              //医生认证状态

    private TextView mTitleText;                     //标题
    private TextView mServiceSetTitleText;         //开通服务标题
    private ImageView mServiceSetStateImg,back;         //开通服务状态

    private TextView mBasePriceTitleText;             //基础价格标题
    private EditText mBasePriceEdit;                 //基础价格
    private TextView mYJPriceTitleText;             //溢价价格标题
    private EditText mYJPriceEdit;                 //溢价价格
    private EditText mHYNumEdit;                 //号源数量
    private EditText mYJHYNumEdit;                 //溢价号源数量
    private EditText mMFHYPriceEdit;                 //免费号源数量
    private TextView mMessageNumTitle;               //消息数量
    private LinearLayout mYPMessageNumTitle;             //音频通话时长
    private LinearLayout mSPMessageNumTitle;             //视频频通话时长

    private TextView mFWSHText;                      //服务时间
    private TextView mMessageNumText;                      //回复消息数量
    private TextView mYPNumText;                      //音频通话时间
    private TextView mSPNumText;                      //视频通话时间
    private TextView mCommit;                        //保存

    /**
     * 初始化布局
     */
    @SuppressLint("WrongViewCast")
    private void initLayout() {
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCommit = (TextView) this.findViewById(R.id.commit);
        mCommit.setOnClickListener(new ButtonClick());

        mTitleText = (TextView) this.findViewById(R.id.titleText);
        mServiceSetTitleText = (TextView) this.findViewById(R.id.tv_activityServicePermisionSet_serviceTitle);
        mServiceSetStateImg = (ImageView) this.findViewById(R.id.iv_activityServicePermisionSet_serviceSet);
        mServiceSetStateImg.setOnClickListener(new ButtonClick());
        mMessageNumTitle = (TextView) this.findViewById(R.id.messageNumTitle);
        mYPMessageNumTitle = (LinearLayout) this.findViewById(R.id.ypmessageNumTitle);
        mSPMessageNumTitle = (LinearLayout) this.findViewById(R.id.spMessageNumTitle);

        mBasePriceTitleText = (TextView) this.findViewById(R.id.tv_activityServicePermisionSet_servicePriceTitle);
        mYJPriceTitleText = (TextView) this.findViewById(R.id.tv_activityServicePermisionSet_serviceYJPriceTitle);

        mBasePriceEdit = (EditText) this.findViewById(R.id.et_activityServicePermisionSet_servicePriceTitle);
        mYJPriceEdit = (EditText) this.findViewById(R.id.et_activityServicePermisionSet_yjPriceEdit);
        mHYNumEdit = (EditText) this.findViewById(R.id.et_activityServicePermisionSet_hyNumEdit);
        mYJHYNumEdit = (EditText) this.findViewById(R.id.et_activityServicePermisionSet_yjhyNumEdit);
        mMFHYPriceEdit = (EditText) this.findViewById(R.id.et_activityServicePermisionSet_mfhyNumEdit);

        mFWSHText = (TextView) this.findViewById(R.id.tv_fwsj);
        mMessageNumText = (TextView) this.findViewById(R.id.tv_messageNum);

        mYPNumText = (TextView) this.findViewById(R.id.tv_ypthsj);
        mSPNumText = (TextView) this.findViewById(R.id.tv_spthsj);

        switch (mServiceType) {
            case 1:
                mTitleText.setText("图文就诊设置");
                mServiceSetTitleText.setText("开通图文就诊");
                mBasePriceTitleText.setText("图文就诊基础价格");
                mYJPriceTitleText.setText("图文就诊溢价价格");
                mMessageNumTitle.setText("回复消息数量");
                mYPMessageNumTitle.setVisibility(View.GONE);
                mSPMessageNumTitle.setVisibility(View.GONE);
                break;
            case 2:
                mTitleText.setText("音频就诊设置");
                mServiceSetTitleText.setText("音频图文就诊");
                mBasePriceTitleText.setText("音频就诊基础价格");
                mYJPriceTitleText.setText("音频就诊溢价价格");
                mMessageNumTitle.setText("音频通话时长");
                mYPMessageNumTitle.setVisibility(View.GONE);
                mSPMessageNumTitle.setVisibility(View.GONE);
                break;
            case 3:
                mTitleText.setText("视频就诊设置");
                mServiceSetTitleText.setText("视频图文就诊");
                mBasePriceTitleText.setText("视频就诊基础价格");
                mYJPriceTitleText.setText("视频就诊溢价价格");
                mMessageNumTitle.setText("视频通话时长");
                mYPMessageNumTitle.setVisibility(View.GONE);
                mSPMessageNumTitle.setVisibility(View.GONE);
                break;
            case 4:
                mTitleText.setText("签约服务设置");
                mServiceSetTitleText.setText("签约服务图文就诊");
                mBasePriceTitleText.setText("签约服务基础价格");
                mYJPriceTitleText.setText("签约服务溢价价格");
                mMessageNumTitle.setText("回复消息数量");
                mYPMessageNumTitle.setVisibility(View.VISIBLE);
                mSPMessageNumTitle.setVisibility(View.VISIBLE);
                break;
            case 5:
                mTitleText.setText("电话就诊");
                mServiceSetTitleText.setText("电话就诊");
                mBasePriceTitleText.setText("签约服务基础价格");
                mYJPriceTitleText.setText("签约服务溢价价格");
                mMessageNumTitle.setText("电话通话时长");
                mMessageNumText.setText("15分钟");
                mYPMessageNumTitle.setVisibility(View.GONE);
                mSPMessageNumTitle.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_servicepermisionset);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        mDoctorStatus = getIntent().getIntExtra("doctorStatus", 0);
        mServiceType = getIntent().getIntExtra("serviceType", 0);
        initLayout();
        initHandler();
        getData();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideDoctorSetService = JSON.parseObject(netRetEntity.getResJsonData(), ProvideDoctorSetService.class);
                            setLayoutDate();
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

    private void setLayoutDate() {
        if (mProvideDoctorSetService.getFlagOpening() == 1)
            mServiceSetStateImg.setImageResource(R.mipmap.sharedataset_close);
        else
            mServiceSetStateImg.setImageResource(R.mipmap.sharedataset_open);
        if (mProvideDoctorSetService.getPriceBasics() == 0.0)
            mBasePriceEdit.setHint("请输入");
        else
            mBasePriceEdit.setText(mProvideDoctorSetService.getPriceBasics() + "");
        if (mProvideDoctorSetService.getPricePremium() == 0.0)
            mYJPriceEdit.setHint("请输入");
        else
            mYJPriceEdit.setText(mProvideDoctorSetService.getPricePremium() + "");
        if (mProvideDoctorSetService.getSourceNumBasics() == 0)
            mHYNumEdit.setHint("请输入");
        else
            mHYNumEdit.setText(mProvideDoctorSetService.getSourceNumBasics() + "");
        if (mProvideDoctorSetService.getSourceNumPremium() == 0)
            mYJHYNumEdit.setHint("请输入");
        else
            mYJHYNumEdit.setText(mProvideDoctorSetService.getSourceNumPremium() + "");
        if (mProvideDoctorSetService.getSourceNumFree() == 0)
            mMFHYPriceEdit.setHint("请输入");
        else
            mMFHYPriceEdit.setText(mProvideDoctorSetService.getSourceNumFree() + "");

        switch (mServiceType) {
            case 1:
                if (mProvideDoctorSetService.getServiceDateStr() != null && !"".equals(mProvideDoctorSetService.getServiceDateStr()))
                    mFWSHText.setText(mProvideDoctorSetService.getServiceDateStr());
                else
                    mFWSHText.setText("0");
                if (mProvideDoctorSetService.getLimitNumStr() != null && !"".equals(mProvideDoctorSetService.getLimitNumStr()))
                    mMessageNumText.setText(mProvideDoctorSetService.getLimitNumStr());
                else
                    mMessageNumText.setText("0");
                break;
            case 2:
                if (mProvideDoctorSetService.getServiceDateStr() != null && !"".equals(mProvideDoctorSetService.getServiceDateStr()))
                    mFWSHText.setText(mProvideDoctorSetService.getServiceDateStr());
                else
                    mFWSHText.setText("0");
                if (mProvideDoctorSetService.getLimitNumStr() != null && !"".equals(mProvideDoctorSetService.getLimitNumStr()))
                    mMessageNumText.setText(mProvideDoctorSetService.getLimitNumStr());
                else
                    mMessageNumText.setText("0");
                break;
            case 3:
                if (mProvideDoctorSetService.getServiceDateStr() != null && !"".equals(mProvideDoctorSetService.getServiceDateStr()))
                    mFWSHText.setText(mProvideDoctorSetService.getServiceDateStr());
                else
                    mFWSHText.setText("0");
                if (mProvideDoctorSetService.getLimitNumStr() != null && !"".equals(mProvideDoctorSetService.getLimitNumStr()))
                    mMessageNumText.setText(mProvideDoctorSetService.getLimitNumStr());
                else
                    mMessageNumText.setText("0");
                break;
            case 4:
                if (mProvideDoctorSetService.getServiceDateStr() != null && !"".equals(mProvideDoctorSetService.getServiceDateStr()))
                    mFWSHText.setText(mProvideDoctorSetService.getServiceDateStr());
                else
                    mFWSHText.setText("0");
                if (mProvideDoctorSetService.getSigningImgTextStr() != null && !"".equals(mProvideDoctorSetService.getSigningImgTextStr()))
                    mMessageNumText.setText(mProvideDoctorSetService.getSigningImgTextStr());
                else
                    mMessageNumText.setText("0");
                if (mProvideDoctorSetService.getSigningAudioStr() != null && !"".equals(mProvideDoctorSetService.getSigningAudioStr()))
                    mYPNumText.setText(mProvideDoctorSetService.getSigningAudioStr());
                else
                    mYPNumText.setText("0");
                if (mProvideDoctorSetService.getSigningVideoStr() != null && !"".equals(mProvideDoctorSetService.getSigningVideoStr()))
                    mSPNumText.setText(mProvideDoctorSetService.getSigningVideoStr());
                else
                    mSPNumText.setText("0");
                break;
            case 5:
                if (mProvideDoctorSetService.getServiceDateStr() != null && !"".equals(mProvideDoctorSetService.getServiceDateStr()))
                    mFWSHText.setText(mProvideDoctorSetService.getServiceDateStr());
                else
                    mFWSHText.setText("0");
                if (mProvideDoctorSetService.getLimitNumStr() != null && !"".equals(mProvideDoctorSetService.getLimitNumStr()))
                    mMessageNumText.setText(mProvideDoctorSetService.getLimitNumStr());
                else
                    mMessageNumText.setText("0");
                break;
        }
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_activityServicePermisionSet_serviceSet:
                    if (mProvideDoctorSetService.getFlagOpening() == 0)
                        mProvideDoctorSetService.setFlagOpening(1);
                    else
                        mProvideDoctorSetService.setFlagOpening(0);
                    if (mProvideDoctorSetService.getFlagOpening() == 1)
                        mServiceSetStateImg.setImageResource(R.mipmap.sharedataset_close);
                    else
                        mServiceSetStateImg.setImageResource(R.mipmap.sharedataset_open);
                    break;

                case R.id.commit:
                    commit();
                    finish();
                    break;
            }
        }
    }

    /**
     * 保存
     */
    private void commit() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在保存");
        new Thread() {
            public void run() {
                try {
                    ProvideDoctorSetParment provideDoctorSetParment = new ProvideDoctorSetParment();
                    provideDoctorSetParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideDoctorSetParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideDoctorSetParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideDoctorSetParment.setServiceType(mServiceType + "");
                    provideDoctorSetParment.setFlagOpening(mProvideDoctorSetService.getFlagOpening() + "");
                    String string = mBasePriceEdit.getText().toString();
                    if (mBasePriceEdit.getText().toString() != null && !"".equals(mBasePriceEdit.getText().toString()))
                        provideDoctorSetParment.setPriceBasics(mBasePriceEdit.getText().toString());
                    else
                        provideDoctorSetParment.setPriceBasics("0");

                    if (mYJPriceEdit.getText().toString() != null && !"".equals(mYJPriceEdit.getText().toString()))
                        provideDoctorSetParment.setPricePremium(mYJPriceEdit.getText().toString());
                    else
                        provideDoctorSetParment.setPricePremium("0");

                    if (mHYNumEdit.getText().toString() != null && !"".equals(mHYNumEdit.getText().toString()))
                        provideDoctorSetParment.setSourceNumBasics(mHYNumEdit.getText().toString());
                    else
                        provideDoctorSetParment.setSourceNumBasics("0");

                    if (mYJHYNumEdit.getText().toString() != null && !"".equals(mYJHYNumEdit.getText().toString()))
                        provideDoctorSetParment.setSourceNumPremium(mYJHYNumEdit.getText().toString());
                    else
                        provideDoctorSetParment.setSourceNumPremium("0");

                    if (mMFHYPriceEdit.getText().toString() != null && !"".equals(mMFHYPriceEdit.getText().toString()))
                        provideDoctorSetParment.setSourceNumFree(mMFHYPriceEdit.getText().toString());
                    else
                        provideDoctorSetParment.setSourceNumFree("0");

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideDoctorSetParment), Constant.SERVICEURL + "doctorPersonalSetControlle/operDoctorSetService");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("保存失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }
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

    /**
     * 设置数据
     */
    private void getData() {
        //连接网络，登录
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideDoctorSetService provideDoctorSetService = new ProvideDoctorSetService();
                    provideDoctorSetService.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideDoctorSetService.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideDoctorSetService.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideDoctorSetService.setServiceType(mServiceType);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideDoctorSetService), Constant.SERVICEURL + "doctorPersonalSetControlle/getDoctorSetServiceByServiceTypeResData");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    Log.e("bbbb", "run: "+mNetRetStr );
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
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
