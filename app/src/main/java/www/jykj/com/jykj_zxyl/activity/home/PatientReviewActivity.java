package www.jykj.com.jykj_zxyl.activity.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalInfo;
import entity.patientInfo.ProvideBindingDoctorPatientApply;
import entity.patientInfo.ProvideViewPatientInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class PatientReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler mHandler;
    private JYKJApplication mApp;
    private TextView mUserName;
    private TextView mApplyReason;
    private TextView mUserLaber;
    private Button mButtonAgree;
    private Button mButtonRefuned;
    private EditText mEtRefuned;
    private LinearLayout mBack;
    private ProvideBindingDoctorPatientApply data;
    private String mNetRetStr;

    private int flagApplyState;
    private String refuseReason;
    private                 List<ProvideBasicsDomain>   mProvideBasicsDomainInfos = new ArrayList<>();
    private                 String[]                    mProvideBasicsDomainNameInfos;
//    private

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationauditdeal);
        ActivityUtil.setStatusBarMain(PatientReviewActivity.this);

        initView();
        initHandler();
        initData();
        initListener();
    }

    private void initView(){
        mApp = (JYKJApplication) getApplication();
        mUserName = this.findViewById(R.id.tv_activityApplyAudit_userNameText);
        mApplyReason = this.findViewById(R.id.tv_activityApplyAudit_applyReson);
        mUserLaber = this.findViewById(R.id.tv_activityApplyAudit_userLaber);
        mButtonAgree = this.findViewById(R.id.bt_activityApplyAudit_agree);
        mButtonRefuned = this.findViewById(R.id.tv_activityApplyAudit_refuned);
        mEtRefuned = this.findViewById(R.id.et_activityApplyAudit_refunedReasud);
        mBack = this.findViewById(R.id.ll_back);
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent!=null){
            data = (ProvideBindingDoctorPatientApply) intent.getSerializableExtra("doctorbindingdata");
            mUserName.setText(data.getPatientUserName());
            mApplyReason.setText(data.getApplyReason());
            mUserLaber.setText(data.getPatientLabelName());
            mEtRefuned.setText(data.getRefuseReason());
        }
        getLaberData();
    }

    private void initListener(){
        mBack.setOnClickListener(this);
        mButtonAgree.setOnClickListener(this);
        mButtonRefuned.setOnClickListener(this);
        mUserLaber.setOnClickListener(this);
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 0) {
                            Toast.makeText(PatientReviewActivity.this, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            setResult(3);
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_activityApplyAudit_agree:
                flagApplyState = 3;
                review(flagApplyState);
                break;
            case R.id.tv_activityApplyAudit_refuned:
                flagApplyState = 1;
                review(flagApplyState);
                break;
            case R.id.tv_activityApplyAudit_userLaber:
                if(data.getPatientLabelName().equals("")||data.getPatientLabelName()==null){
                    Toast.makeText(this,"请选择标签",Toast.LENGTH_SHORT).show();
                    return;
                }
                showChoseUserLaber();
                break;
        }
    }

    private void review(int flagApplyState){
        refuseReason = mEtRefuned.getText().toString().trim();
        new Thread(){
            public void run(){
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(data.getInvitationDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(data.getInvitationDoctorName());
                    provideViewPatientInfo.setDpApplyId(data.getDpApplyId()+"");
                    provideViewPatientInfo.setFlagApplyState(flagApplyState);
                    provideViewPatientInfo.setRefuseReason(refuseReason);
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+jsonString ,Constant.SERVICEURL+"bindingDoctorPatientControlle/operApprovalBindingDoctorPatient");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();


    }

    private void getLaberData(){
        new Thread(){
            public void run(){
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(30001);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBasicsDomain),Constant.SERVICEURL+"basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取标签信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mProvideBasicsDomainInfos =new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideBasicsDomain>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(3);
            }
        }.start();

    }

    private void showChoseUserLaber(){
        if (mProvideBasicsDomainInfos != null)
        {
            mProvideBasicsDomainNameInfos = new String[mProvideBasicsDomainInfos.size()];
        }
        for (int i = 0; i < mProvideBasicsDomainInfos.size(); i++)
        {
            mProvideBasicsDomainNameInfos[i] = mProvideBasicsDomainInfos.get(i).getAttrName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideBasicsDomainNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserLaber.setText(mProvideBasicsDomainInfos.get(which).getAttrName());
            }
        });
        listDialog.show();

    }
}
