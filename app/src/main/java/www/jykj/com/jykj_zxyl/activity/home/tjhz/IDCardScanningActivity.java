package www.jykj.com.jykj_zxyl.activity.home.tjhz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.net.URLEncoder;

import entity.patientmanager.ProvideIdentityNumberInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.util.idcard_scanning_util.CameraSurfaceView;
import www.jykj.com.jykj_zxyl.util.tencenUtil.OcrClient;
import www.jykj.com.jykj_zxyl.util.tencenUtil.common.Credential;
import www.jykj.com.jykj_zxyl.util.tencenUtil.models.IDCardOCRRequest;

/**
 * 身份证扫描
 */
public class IDCardScanningActivity extends AppCompatActivity {

    private             Context             mContext;
    private             IDCardScanningActivity  mActivity;
    private             JYKJApplication         mApp;
    private             Button              button;
    private             CameraSurfaceView   mCameraSurfaceView;
    private             Handler                             mHandler;


    public              ProgressDialog                              mDialogProgress =null;                                  //进度条
    public              String              mRetString;

    private                 String                      mNetRetStr;                 //返回字符串


//    private             RectOnCamera        rectOnCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcardscanning);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        //获取相机权限
        initLayout();
        initHandler();

    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        System.out.print(mRetString);
                        break;
                    case 1:
                        cacerProgress();
                        System.out.print(mRetString);
                        break;
                }
            }
        };
    }
    /**
     * 初始化界面
     */
    private void initLayout() {
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
        button = (Button) findViewById(R.id.takePic);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraSurfaceView.takePicture(mActivity);
            }
        });
    }



    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityAddPatient_smsfz:
                    startActivity(new Intent(mContext,IDCardScanningActivity.class));
                    break;

            }
        }
    }

    //回调函数
    public void setIDCardDate(final byte[] date){
        getProgressBar("请稍候","正在识别。。。");
        //开始识别
        new Thread() {
            public void run() {
                //提交数据
                        try {
                            Bitmap img = BitmapUtil.Bytes2Bimap(date);
//                            Bitmap imgCompress = BitmapUtil.compressImage(img);
                            ProvideIdentityNumberInfo ProvideIdentityNumberInfo = new ProvideIdentityNumberInfo();
                            ProvideIdentityNumberInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                            ProvideIdentityNumberInfo.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                            ProvideIdentityNumberInfo.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                            ProvideIdentityNumberInfo.setFlagIdCardSide("1");
                            String base64String = BitmapUtil.bitmaptoString(img);
                            ProvideIdentityNumberInfo.setScanIdNumberImg((URLEncoder.encode("data:image/jpg;base64,"+base64String)));

                            String str = new Gson().toJson(ProvideIdentityNumberInfo);
                            System.out.println("~~~~~~~~~~~~~开始提交了~~~~~~~~~~~~~~");
                            mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorDataControlle/operScanByIdNumberImg");
                            System.out.println("~~~~~~~~~~~~~返回~~~~~~~~~~~~~~"+mRetString);
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 0) {
                                NetRetEntity retEntity = new NetRetEntity();
                                retEntity.setResCode(0);
                                retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());

                                mNetRetStr = new Gson().toJson(retEntity);
                                mHandler.sendEmptyMessage(1);
                                return;
                            }

                        } catch (Exception e) {
                            NetRetEntity retEntity = new NetRetEntity();
                            retEntity.setResCode(0);
                            retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                            System.out.println("发生议程，原因："+e.getMessage());
                            mNetRetStr = new Gson().toJson(retEntity);
                            mHandler.sendEmptyMessage(1);
                            return;
                        }
                        //判断是否有图片
                        mHandler.sendEmptyMessage(0);
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

