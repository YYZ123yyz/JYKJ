package www.jykj.com.jykj_zxyl.activity.myself;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.mySelf.ProvideDoctorQualification;
import entity.mySelf.UpLoadImgParment;
import entity.patientmanager.ProvideIdentityNumberInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import orcameralib.CameraActivity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.tjhz.AddPatientQRCodeActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;

import static orcameralib.CameraActivity.KEY_CONTENT_TYPE;
import static orcameralib.CameraActivity.KEY_OUTPUT_FILE_PATH;

public class UserAuthenticationActivity extends AppCompatActivity {

    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private Context mContext;
    private UserAuthenticationActivity mActivity;
    private TextView mDetail;                        //明细
    private LinearLayout mBack;
    private RelativeLayout mIDCardFrontLayout;               //身份证正面

    private String mIDCardFrontPath;                       //身份证正面路径
    private RelativeLayout mIDCardBackLayout;                  //身份证反面
    private int mIDCardFrontRequstCode = 1000;             //身份证正面获取码
    private int mIDCardBackRequstCode = 1001;             //身份证反面获取码
    private JYKJApplication mApp;

    public ProgressDialog mDialogProgress = null;                                  //进度条
    public String mRetString;

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;
    private ImageView mIDCardFont;                //身份证正面
    private ImageView mIDCardBack;                //身份证反面
    private ImageView mZYZImage;                  //执业证
    private ImageView mZGZImage;                  //资格证
    private ImageView mZCZImage;                  //职称证

    private File mTempFile;              //声明一个拍照结果的临时文件

    private int mCurrentPhoto;              //当前照片类型  1=执业证  2=资格证  3=职称证
    private int mPhotoType;                 //图片类型  1、身份证正面   2、身份证反面  3、执业证  4、资格证  5、职称证
    private String mPhotoBitmapString;             //图片base64字符串
    private Button mCommit;
    private LinearLayout lin_sumbit;
    private TextView submit_state;
    private LinearLayout lin_data;
    private TextView sumbit_data;
    private LinearLayout lin_status;
    private TextView status;


    /**
     * 创建临时文件夹 _tempphoto
     */
    private void initDir() {
        // 声明目录
        File tempDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/_tempphoto");
        if (!tempDir.exists()) {
            tempDir.mkdirs();// 创建目录
        }
        mIDCardFrontPath = new File(tempDir, BitmapUtil.getPhotoFileName()).toString();// 生成临时文件
        mTempFile = new File(tempDir, BitmapUtil.getPhotoFileName());// 生成临时文件
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmentself_userauthentication);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initDir();
        initHandler();
        initLayout();
        getDate();
    }

    /**
     * 加载数据
     */
    private void getDate() {
        getProgressBar("请稍候", "正在加载数据。。。");
        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    UpLoadImgParment upLoadImgParment = new UpLoadImgParment();
                    upLoadImgParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    upLoadImgParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    upLoadImgParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    String str = new Gson().toJson(upLoadImgParment);
                    System.out.println("~~~~~~~~~~~~~开始提交了~~~~~~~~~~~~~~");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorPersonalSetControlle/getUserDoctorQualificationImg");
                    System.out.println("~~~~~~~~~~~~~返回~~~~~~~~~~~~~~" + mRetString);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        return;
                    }else{
                        Integer flagSubmitState = mApp.provideDoctorQualification.getFlagSubmitState();
                        if(flagSubmitState==1){
                            //提交状态
                            submit_state.setText("已提交");
                            //提交日期
                            Date submitDate = mApp.provideDoctorQualification.getSubmitDate();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                                    "yyyy-MM-dd hh:mm:ss");
                            String format = simpleDateFormat.format(submitDate);
                            sumbit_data.setText(format);
                            //审核状态
                            status.setText("通过");

                        }
//                        Integer flagApplyState = mApp.provideDoctorQualification.getFlagApplyState();
//                        if(flagApplyState==3){
//
//                        }

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
                        break;
                    case 2:
                        cacerProgress();
                        break;
                    case 3:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            switch (mPhotoType) {
                                case 1:
                                    Glide.with(mContext).load(BitmapUtil.stringtoBitmap(mPhotoBitmapString)).into(mIDCardFont);
                                    break;
                                case 2:
                                    Glide.with(mContext).load(BitmapUtil.stringtoBitmap(mPhotoBitmapString)).into(mIDCardBack);
                                    break;
                                case 3:
                                    Glide.with(mContext).load(BitmapUtil.stringtoBitmap(mPhotoBitmapString)).into(mZYZImage);
                                    break;
                                case 4:
                                    Glide.with(mContext).load(BitmapUtil.stringtoBitmap(mPhotoBitmapString)).into(mZGZImage);
                                    break;
                                case 5:
                                    Glide.with(mContext).load(BitmapUtil.stringtoBitmap(mPhotoBitmapString)).into(mZCZImage);
                                    break;
                            }
                        }

                        break;

                    case 5:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == mIDCardFrontRequstCode && resultCode == RESULT_OK) {
            getProgressBar("请稍候", "正在处理。。。");
            new Thread() {
                public void run() {
                    String bitmapString = mApp.gBitMapString;
                    upLoadImg(1, bitmapString);
                }
            }.start();


        }
        if (requestCode == mIDCardBackRequstCode && resultCode == RESULT_OK) {
            getProgressBar("请稍候", "正在处理。。。");
            new Thread() {
                public void run() {
                    String bitmapString = mApp.gBitMapString;
                    upLoadImg(2, bitmapString);
                }
            }.start();

        }
        try {

            // 如果是直接从相册获取
            if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
                    && resultCode == RESULT_OK
                    && data != null) {

                final Uri uri = data.getData();//返回相册图片的Uri
                final Bitmap[] photo = new Bitmap[1];
                getProgressBar("请稍候", "正在处理。。。");
                new Thread() {
                    public void run() {
                        if (uri != null) {
                            try {
                                photo[0] = BitmapUtil.getimageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData())));//将imageUri对象的图片加载到内存
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("进来了");
                            try {
                                photo[0] = BitmapUtil.getimageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg")))));//将imageUri对象的图片加载到内存
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("图片：" + photo[0]);
                        switch (mCurrentPhoto) {
                            case 1:
                                upLoadImg(3, BitmapUtil.bitmaptoString(photo[0]));
                                break;
                            case 2:
                                upLoadImg(4, BitmapUtil.bitmaptoString(photo[0]));
                                break;
                            case 3:
                                upLoadImg(5, BitmapUtil.bitmaptoString(photo[0]));
                                break;
                        }
                    }
                }.start();
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                getProgressBar("请稍候", "正在处理。。。");
                new Thread() {
                    public void run() {
                        Bitmap photo = null;
                        try {
                            photo = BitmapUtil.getimageBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(mTempFile))));//将imageUri对象的图片加载到内存
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        switch (mCurrentPhoto) {
                            case 1:
                                upLoadImg(3, BitmapUtil.bitmaptoString(photo));
                                break;
                            case 2:
                                upLoadImg(4, BitmapUtil.bitmaptoString(photo));
                                break;
                            case 3:
                                upLoadImg(5, BitmapUtil.bitmaptoString(photo));
                                break;
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            Log.i("yi", "yichahahaha");
        }
    }

    //回调函数
    public void upLoadImg(int type, String bitmapString) {

        mPhotoType = type;
        mPhotoBitmapString = bitmapString;
        //开始识别
//        new Thread() {
//            public void run() {
//                //提交数据
        try {
            UpLoadImgParment upLoadImgParment = new UpLoadImgParment();
            upLoadImgParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
            upLoadImgParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
            upLoadImgParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            upLoadImgParment.setFlagImgType(type + "");
            upLoadImgParment.setImgBase64Data((URLEncoder.encode("data:image/jpg;base64," + bitmapString)));
            String str = new Gson().toJson(upLoadImgParment);
            System.out.println("~~~~~~~~~~~~~开始提交了~~~~~~~~~~~~~~");
            mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorPersonalSetControlle/operUserDoctorStatus");
            System.out.println("~~~~~~~~~~~~~返回~~~~~~~~~~~~~~" + mRetString);
            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

            if (netRetEntity.getResCode() == 0) {
                NetRetEntity retEntity = new NetRetEntity();
                retEntity.setResCode(0);
                retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());

                mNetRetStr = new Gson().toJson(retEntity);
                mHandler.sendEmptyMessage(3);
                return;
            }

        } catch (Exception e) {
            NetRetEntity retEntity = new NetRetEntity();
            retEntity.setResCode(0);
            retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
            mNetRetStr = new Gson().toJson(retEntity);
            mHandler.sendEmptyMessage(3);
            return;
        }

        mHandler.sendEmptyMessage(3);
//            }
//        }.start();
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

    /**
     * 初始化布局
     */
    private void initLayout() {
        mBack = (LinearLayout) this.findViewById(R.id.li_activityAuthentication_back);
        mBack.setOnClickListener(new ButtonClick());

        mIDCardFrontLayout = (RelativeLayout) this.findViewById(R.id.ri_idcardFront);
        mIDCardFrontLayout.setOnClickListener(new ButtonClick());

        mIDCardBackLayout = (RelativeLayout) this.findViewById(R.id.ri_idcardBack);
        mIDCardBackLayout.setOnClickListener(new ButtonClick());

        mIDCardFont = (ImageView) this.findViewById(R.id.iv_idcardFont);
        mIDCardBack = (ImageView) this.findViewById(R.id.iv_idcardBack);

        mZYZImage = (ImageView) this.findViewById(R.id.iv_zyz);
        mZYZImage.setOnClickListener(new ButtonClick());
        mZGZImage = (ImageView) this.findViewById(R.id.iv_zgz);
        mZGZImage.setOnClickListener(new ButtonClick());
        mZCZImage = (ImageView) this.findViewById(R.id.iv_zcz);
        mZCZImage.setOnClickListener(new ButtonClick());

        mCommit = (Button) this.findViewById(R.id.bt_commit);
        mCommit.setOnClickListener(new ButtonClick());

        //提交状态lin
        lin_sumbit = findViewById(R.id.lin_sumbit);
        //提交状态textview
        submit_state = findViewById(R.id.submit_state);

        //提交日期  lin
        lin_data = findViewById(R.id.lin_data);
        //提交日期 textview
        sumbit_data = findViewById(R.id.sumbit_data);

        //审核状态   lin
        lin_status = findViewById(R.id.lin_status);
        //审核状态 textview
        status = findViewById(R.id.status);

        lin_sumbit.setVisibility(View.INVISIBLE);
        lin_data.setVisibility(View.INVISIBLE);
        lin_status.setVisibility(View.INVISIBLE);
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_activityAuthentication_back:
                    finish();
                    break;

                case R.id.ri_idcardFront:
                    startActivityForResult(new Intent(mContext, CameraActivity.class)
                            .putExtra(KEY_CONTENT_TYPE, "IDCardFront")
                            .putExtra(KEY_OUTPUT_FILE_PATH, mIDCardFrontPath), mIDCardFrontRequstCode);
                    break;

                case R.id.ri_idcardBack:
                    startActivityForResult(new Intent(mContext, CameraActivity.class)
                            .putExtra(KEY_CONTENT_TYPE, "IDCardBack")
                            .putExtra(KEY_OUTPUT_FILE_PATH, mIDCardFrontPath), mIDCardBackRequstCode);
                    break;

                case R.id.iv_zyz:
                    mCurrentPhoto = 1;
                    String[] items = {"拍照", "从相册选择"};
                    Dialog dialog = new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder.build());
                                            builder.detectFileUriExposure();
                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            // 指定调用相机拍照后照片(结果)的储存路径
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                            // 等待返回结果
                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
                                            break;
                                        case 1:
                                            BitmapUtil.selectAlbum(mActivity);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                    break;
                case R.id.iv_zgz:
                    mCurrentPhoto = 2;
                    String[] items2 = {"拍照", "从相册选择"};
                    Dialog dialog2 = new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setItems(items2, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder.build());
                                            builder.detectFileUriExposure();
                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            // 指定调用相机拍照后照片(结果)的储存路径
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                            // 等待返回结果
                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
                                            break;
                                        case 1:
                                            BitmapUtil.selectAlbum(mActivity);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                    break;
                case R.id.iv_zcz:
                    mCurrentPhoto = 3;
                    String[] items3 = {"拍照", "从相册选择"};
                    Dialog dialog3 = new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setItems(items3, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0:
                                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                            StrictMode.setVmPolicy(builder.build());
                                            builder.detectFileUriExposure();
                                            // 添加Action类型：MediaStore.ACTION_IMAGE_CAPTURE
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            // 指定调用相机拍照后照片(结果)的储存路径
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempFile));
                                            // 等待返回结果
                                            startActivityForResult(intent, Constant.SELECT_PIC_BY_TACK_PHOTO);
                                            break;
                                        case 1:
                                            BitmapUtil.selectAlbum(mActivity);//从相册选择
                                            break;
                                    }
                                }
                            }).show();
                    break;

                case R.id.bt_commit:
                    commit();
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void commit() {

        getProgressBar("请稍候", "正在提交。。。");
        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    UpLoadImgParment upLoadImgParment = new UpLoadImgParment();
                    upLoadImgParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    upLoadImgParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    upLoadImgParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    String str = new Gson().toJson(upLoadImgParment);
                    System.out.println("~~~~~~~~~~~~~开始提交了~~~~~~~~~~~~~~");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorPersonalSetControlle/operSubmitDoctorQualification");
                    System.out.println("~~~~~~~~~~~~~返回~~~~~~~~~~~~~~" + mRetString);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());

                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(5);
                        return;
                    }else {
                        lin_sumbit.setVisibility(View.VISIBLE);
                        lin_data.setVisibility(View.VISIBLE);
                        lin_status.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(5);
                    return;
                }

                mHandler.sendEmptyMessage(5);
            }
        }.start();

    }


}
