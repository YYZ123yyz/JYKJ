package www.jykj.com.jykj_zxyl.activity.home.tjhz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.SysParment;
import entity.hzgl.BindPatientGetVCodeParment;
import entity.hzgl.BindPatientParment;
import entity.patientmanager.ProvideIdentityNumberInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import orcameralib.CameraActivity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import zxing.android.CaptureActivity;

import static orcameralib.CameraActivity.KEY_CONTENT_TYPE;
import static orcameralib.CameraActivity.KEY_OUTPUT_FILE_PATH;


/**
 * 添加患者(扫码)
 */
public class AddPatientQRCodeActivity extends AppCompatActivity {

    private             Context                         mContext;
    private AddPatientQRCodeActivity mActivity;
    private             LinearLayout                    mApplicationAudit;                  //申请审核
    private             LinearLayout                    mSMSFZ;                             //扫描身份证\
    private             JYKJApplication                 mApp;



    private Handler mHandler;


    public              ProgressDialog                              mDialogProgress =null;                                  //进度条
    public              String              mRetString;

    private                 String                      mNetRetStr;                 //返回字符串


    private             LinearLayout                    mHZBQLayout;                        //患者标签

    private             TextView                        mHZBQText;                          //患者标签

    private             List<ProvideBasicsDomain>       mList = new ArrayList<>();              //患者标签数据

    private             Button                          mCommitButton;                          //提交

    private             BindPatientParment              mBindPatientParment;
    private             String                          mQrCode;                            //二维码值
    private             String                          mServiceName;                       //服务名称

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient_qrcode);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mBindPatientParment = new BindPatientParment();

        mQrCode = getIntent().getStringExtra("qrCode");
        mServiceName = getIntent().getStringExtra("serviceName");
        mBindPatientParment.setPatientQrCode(mQrCode);
        mBindPatientParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mBindPatientParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mBindPatientParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        initLayout();
        initHandler();
        getBasicDate();
    }

    /**
     *
     */
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        cacerProgress();
                        break;

                }
            }
        };
    }




    /**
     * 初始化界面
     */
    private void initLayout() {

        mHZBQLayout = (LinearLayout)this.findViewById(R.id.li_hzbqLayout);
        mHZBQText = (TextView)this.findViewById(R.id.tv_hzbqText);
        mHZBQLayout.setOnClickListener(new ButtonClick());


        mCommitButton = (Button)this.findViewById(R.id.bt_commit);
        mCommitButton.setOnClickListener(new ButtonClick());
    }




    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_hzbqLayout:
                    showHZBQDialog();
                    break;
                case R.id.bt_commit:
                    commit();
                    break;
            }
        }
    }




    /**
     * 患者标签选择框
     */
    private void showHZBQDialog() {
        final String[] items = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++)
        {
            items[i] = mList.get(i).getAttrName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                mHZBQText.setText(items[arg1]);
                mBindPatientParment.setPatientLabelId(mList.get(arg1).getAttrCode()+"");
                mBindPatientParment.setPatientLabelName(mList.get(arg1).getAttrName());
            }
        });
        builder.create().show();

    }


    //获取患者标签
    public void getBasicDate(){
        getProgressBar("请稍候","正在获取数据。。。");
        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(30001);

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
     * 提交
     */
    private void commit() {
        getProgressBar("请稍候","正在提交。。。");

        if (mBindPatientParment.getPatientLabelId() == null || "".equals(mBindPatientParment.getPatientLabelId() == null))
        {
            Toast.makeText(mContext,"请选择患者标签",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread() {
            public void run() {
//                //提交数据
                try {
                    String str = new Gson().toJson(mBindPatientParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + mServiceName);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("绑定失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(1);
                    return;
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
