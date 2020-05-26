package www.jykj.com.jykj_zxyl.activity.home.twjz;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import entity.wdzs.ProvideBasicsDisease;
import entity.wdzs.ProvideDrugInfo;
import entity.wdzs.ProvideInteractOrderPrescribe;
import entity.wdzs.ProvideInteractOrderPrescribeInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.IDCardScanningActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 图文就诊（开具处方==>处方药品）
 */
public class KJCF_CFYPActivity extends AppCompatActivity {

    private                 Context                 mContext;
    private KJCF_CFYPActivity mActivity;
    private                 Handler                 mHandler;
    private                 JYKJApplication         mApp;
    private                 LinearLayout            mCFYP;          //处方药品

    private                 LinearLayout            mXZYP;              //选择药品
    private    static final int                     mXZYPRequstCode = 1;

    private                 TextView                mYPMC;                  //药品名称

    private                 LinearLayout            mBack;
    private                 EditText                mGMSL;                  //购买数量
    private                 EditText                mMCFYSL;                  //每次服用数量
    private                 EditText                mYYPL;                  //用药频率
    private                 EditText                mFYZQ;                  //服用周期
    private                 EditText                mYYBZ;                  //用药备注

    private                 TextView                mYPGG;                  //药品规格

    private                 Button                  mXZ;                    //新增用药


    private                 TextView                mPrescribeTypeText;     //处方类型
    private                 LinearLayout            mCFLXLayout;            //处方类型
    private                 ProvideDrugInfo         mProvideDrugInfo;

    private                 List<ProvideBasicsDomain> mList = new ArrayList<>();                //处方类型

    private                 ProvideInteractOrderPrescribe   mProvideInteractOrderPrescribe;

    public ProgressDialog mDialogProgress =null;


    private             String                              mNetRetStr;                 //返回字符串

    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_kjcf_cfyp);
        ActivityUtil.setStatusBarMain(KJCF_CFYPActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();

        initLayout();
        mProvideInteractOrderPrescribe = (ProvideInteractOrderPrescribe) getIntent().getSerializableExtra("cfyp");
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("xzyp");
        if (mProvideInteractOrderPrescribe == null)
        {
            mProvideInteractOrderPrescribe = new ProvideInteractOrderPrescribe();
            mProvideInteractOrderPrescribe.setPrescribeId(0);
            mProvideInteractOrderPrescribe.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
            mProvideInteractOrderPrescribe.setPatientCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());
            mProvideInteractOrderPrescribe.setPatientName(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientName());
        }

        else
        {
            setLayoutDate();
        }

        initHandler();
        getBasicDate();
    }

    /**
     *
     */
    private void setLayoutDate() {
        mGMSL.setText(mProvideInteractOrderPrescribe.getDrugAmount()+"");
        mMCFYSL.setText(mProvideInteractOrderPrescribe.getUseNum()+"");
        mYYPL.setText(mProvideInteractOrderPrescribe.getUseFrequency()+"");
        mFYZQ.setText(mProvideInteractOrderPrescribe.getUseCycle()+"");
        mYYBZ.setText(mProvideInteractOrderPrescribe.getUseDesc()+"");
        mYPGG.setText(mProvideInteractOrderPrescribe.getSpecName()+"");
        mYPMC.setText(mProvideInteractOrderPrescribe.getDrugName()+"");
        mYPGG.setText(mProvideInteractOrderPrescribe.getDrugAmount()+"");
        mPrescribeTypeText.setText(mProvideInteractOrderPrescribe.getPrescribeTypeName());
    }

    @Override
    protected void onActivityResult(
            int requestCode,  // 请求码 自定义
            int resultCode,  // 结果码 成功 -1 == OK
            Intent data) { // 数据 ? 可以没有
        try {

            if (requestCode == mXZYPRequstCode
                    && resultCode == RESULT_OK
                    && data != null) {
                mProvideDrugInfo = (ProvideDrugInfo) data.getSerializableExtra("jkxx");
                if (mProvideDrugInfo != null)
                {
                    mYPMC.setText(mProvideDrugInfo.getDrugName());
                    mYPGG.setText(mProvideDrugInfo.getDrugUnit());
                    mProvideInteractOrderPrescribe.setDrugCode(mProvideDrugInfo.getDrugCode());
                    mProvideInteractOrderPrescribe.setDrugName(mProvideDrugInfo.getDrugName());
//                    provideInteractOrderPrescribeInfo.setProductBatchNumber(mProvideDrugInfo.get);
                    mProvideInteractOrderPrescribe.setSpecUnit(mProvideDrugInfo.getDrugUnit());
                    mProvideInteractOrderPrescribe.setSpecName(mProvideDrugInfo.getDrugSpec());


                }
            }

        }catch (Exception e)
        {
            Log.i("yi","yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mXZYP = (LinearLayout)this.findViewById(R.id.li_xzyp);
        mXZYP.setOnClickListener(new ButtonClick());

        mCFLXLayout = (LinearLayout)this.findViewById(R.id.li_cflxLayout);
        mCFLXLayout.setOnClickListener(new ButtonClick());
        mGMSL = (EditText)this.findViewById(R.id.et_gmsl);
        mMCFYSL = (EditText)this.findViewById(R.id.et_mcfysl);
        mYYPL = (EditText)this.findViewById(R.id.et_yypl);
        mFYZQ = (EditText)this.findViewById(R.id.et_fyzq);
        mYYBZ = (EditText)this.findViewById(R.id.et_yybz);
        mYPGG = (TextView)this.findViewById(R.id.tv_ypgg);
        mXZ = (Button)this.findViewById(R.id.button_xz);
        mXZ.setOnClickListener(new ButtonClick());
        mYPMC = (TextView)this.findViewById(R.id.tv_ypmc);
        mPrescribeTypeText = (TextView)this.findViewById(R.id.prescribeType);
        mBack = (LinearLayout)this.findViewById(R.id.li_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 2:
                        cacerProgress();
                        break;
                    case 6:
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        cacerProgress();
                        break;
                }
            }
        };
    }

    /**
     * 处方类型
     */
    private void showCFLX() {
        String[] sexCFLX = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++)
        {
            sexCFLX[i] = mList.get(i).getAttrName();
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexCFLX, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                mPrescribeTypeText.setText(mList.get(which).getAttrName());
                mProvideInteractOrderPrescribe.setPrescribeTypeName(mList.get(which).getAttrName());
                mProvideInteractOrderPrescribe.setPrescribeType(mList.get(which).getAttrCode());


                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_cflxLayout:
                    showCFLX();
                    break;
                case R.id.li_xzyp:
                    startActivityForResult(new Intent(mContext,KJCF_XZYPActivity.class),mXZYPRequstCode);
                    break;
                case R.id.button_xz:
                    if (mProvideInteractOrderPrescribe.getDrugCode() == null)
                    {
                        Toast.makeText(mContext,"请先选择药品",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mGMSL.getText().toString() == null || "".equals(mGMSL.getText().toString()))
                    {
                        Toast.makeText(mContext,"请填写购买数量",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mMCFYSL.getText().toString() == null || "".equals(mMCFYSL.getText().toString()))
                    {
                        Toast.makeText(mContext,"请填写每次服用数量",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mYYPL.getText().toString() == null || "".equals(mYYPL.getText().toString()))
                    {
                        Toast.makeText(mContext,"请填写用药频率",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mFYZQ.getText().toString() == null || "".equals(mFYZQ.getText().toString()))
                    {
                        Toast.makeText(mContext,"请填写服药周期",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mProvideInteractOrderPrescribe.setDrugAmount(Float.parseFloat(mGMSL.getText().toString()));
                    mProvideInteractOrderPrescribe.setUseNum(Integer.parseInt(mMCFYSL.getText().toString()));
                    mProvideInteractOrderPrescribe.setUseFrequency(Integer.parseInt(mYYPL.getText().toString()));
                    mProvideInteractOrderPrescribe.setUseCycle(Integer.parseInt(mFYZQ.getText().toString()));
                    mProvideInteractOrderPrescribe.setUseDesc(mYYBZ.getText().toString());

                    mProvideInteractOrderPrescribe.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    mProvideInteractOrderPrescribe.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()+"");
                    mProvideInteractOrderPrescribe.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName()+"");

                    getProgressBar("请稍候","正在提交。。。");
                    new Thread() {
                        public void run() {
//                          //提交数据
                            try {
                                String str = new Gson().toJson(mProvideInteractOrderPrescribe);
                                mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByPrescribe");
                                NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                                if (netRetEntity.getResCode() == 0) {
                                    NetRetEntity retEntity = new NetRetEntity();
                                    retEntity.setResCode(0);
                                    retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());
                                    mNetRetStr = new Gson().toJson(retEntity);
                                    mHandler.sendEmptyMessage(6);
                                    return;
                                }

                            } catch (Exception e) {
                                NetRetEntity retEntity = new NetRetEntity();
                                retEntity.setResCode(0);
                                retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                                mNetRetStr = new Gson().toJson(retEntity);
                                mHandler.sendEmptyMessage(6);
                                return;
                            }
                            mHandler.sendEmptyMessage(6);
                        }
                    }.start();
                    break;

            }
        }
    }


    //获取处方类型
    public void getBasicDate(){
        getProgressBar("请稍候","正在获取数据。。。");
        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(10033);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mList = JSON.parseArray(netRetEntity.getResJsonData(),ProvideBasicsDomain.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(2);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(2);
                    return;
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }


}
