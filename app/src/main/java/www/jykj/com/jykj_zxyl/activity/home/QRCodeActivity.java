package www.jykj.com.jykj_zxyl.activity.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.xiaomi.clientreport.data.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import entity.shouye.OperScanQrCodeInside;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import util.NetWorkUtils;
import util.QRCodeUtil;
import wechatShare.WechatShareManager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.PhotoDialog;
import yyz_exploit.activity.activity.OpinionActivity;
import zxing.android.CaptureActivity;
import zxing.common.Constant;
import zxing.encode.CodeCreator;

/**
 * 识别码
 */
public class QRCodeActivity extends AppCompatActivity {
    private ImageView ivEwCode;
    private LinearLayout mBack;
    private ImageView ivAdd;
    private MoreFeaturesPopupWindow mPopupWindow;

    private TextView userName;
    private TextView userYY;
    private TextView userPhone;
    private TextView userZC;
    private TextView userSCLY;
    private TextView userGRJJ;

    private TextView sys;
    private JYKJApplication mApp;
    private Context context;

    public static final int REQUEST_CODE_SCAN = 0x123;
    private String qrCode;                         //需要绑定的二维码
    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;
    public ProgressDialog mDialogProgress = null;
    private TextView tv_share;
    public WechatShareManager mShareManager;
    private TextView qr_save;
    private ProvideViewSysUserDoctorInfoAndHospital mProvideViewSysUserPatientInfoAndRegion;
    private String doctorShareUrl;
    private String patientShareUrl;



    private ImageView imageView;
    private yyz_exploit.dialog.ImageView imageView1;
    private String doctorShareTitle;
    private String doctorShareContent;
    private String patientShareTitle;
    private String patientShareContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        context = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(QRCodeActivity.this);
        mShareManager = WechatShareManager.getInstance(context);
        initView();
        initHandler();
        initListener();
        Qrcode();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(context, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else {
                            if ("1".equals(netRetEntity.getResData())) {
                                //医生扫医生二维码，绑定医生好友
                                final EditText et = new EditText(context);
                                new AlertDialog.Builder(context).setTitle("请输入申请描述")
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
                            if ("2".equals(netRetEntity.getResMsg())) {
                                //医生扫医生联盟二维码
                            }
                            if ("3".equals(netRetEntity.getResMsg())) {
                                //医生扫患者二维码
                            }

                        }
                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(context, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), ProvideViewSysUserDoctorInfoAndHospital.class);
                        //医生分享
                        doctorShareUrl = mProvideViewSysUserPatientInfoAndRegion.getDoctorShareUrl();
                        Log.e("tag", "handleMessage:yyyyyy " + doctorShareUrl);
                        //患者分享
                        patientShareUrl = mProvideViewSysUserPatientInfoAndRegion.getPatientShareUrl();
                        //医生title
                        doctorShareTitle = mProvideViewSysUserPatientInfoAndRegion.getDoctorShareTitle();
                        //医生内容
                        doctorShareContent = mProvideViewSysUserPatientInfoAndRegion.getDoctorShareContent();
                        //患者title
                        patientShareTitle = mProvideViewSysUserPatientInfoAndRegion.getPatientShareTitle();
                        //患者内容
                        patientShareContent = mProvideViewSysUserPatientInfoAndRegion.getPatientShareContent();
                        break;
                }
            }
        };
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
                mHandler.sendEmptyMessage(1);
            }
        }.start();
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
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "doctorDataControlle/operScanQrCodeInside");
                    Log.e("tag", "run:444 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /*
     * 二维码
     *
     * */
    private void Qrcode() {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", mApp.loginDoctorPosition);
        map.put("requestClientType", "1");
        map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), www.jykj.com.jykj_zxyl.application.Constant.SERVICEURL + "doctorDataControlle/searchDcotorIdentificationCode");
                    Log.e("tag", "run: "+mNetRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(context);
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

    private void initView() {


        //保存相册
        qr_save = findViewById(R.id.qr_save);
        qr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare();
            }
        });
        //保存相册
//        qr_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userLogoUrl = mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Bitmap bitmap = NetWorkUtils.getHttpBitmap(userLogoUrl);
//                        Bitmap bm = QRCodeUtil.getImageBitmap(bitmap, "1111111", 360);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ivEwCode.setImageBitmap(bm);
//                                saveBmp2Gallery(context,bm,"");
//                            }
//                        });
//                    }
//                }).start();
//
//
//            }
//        });


        //二维码
        ivEwCode = (ImageView) findViewById(R.id.iv_ew_code);

        mBack = (LinearLayout) findViewById(R.id.ll_back);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
        String userLogoUrl = mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = NetWorkUtils.getHttpBitmap(userLogoUrl);
                Bitmap bm = QRCodeUtil.getImageBitmap(bitmap, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), 360);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivEwCode.setImageBitmap(bm);
                        ivEwCode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageView1 = new yyz_exploit.dialog.ImageView(QRCodeActivity.this);
                                imageView1.show();
                                ImageView im = imageView1.findViewById(R.id.img);
                                im.setImageBitmap(bm);
                                Log.e("TAG", "onClick: " + "000000000000000");
                                //关闭
                                imageView1.findViewById(R.id.im_back).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        imageView1.dismiss();
                                    }
                                });

                                //保存相册
                                imageView1.findViewById(R.id.shar).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String userLogoUrl = mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Bitmap bitmap = NetWorkUtils.getHttpBitmap(userLogoUrl);
                                                Bitmap bm = QRCodeUtil.getImageBitmap(bitmap, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), 360);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ivEwCode.setImageBitmap(bm);
                                                        saveBmp2Gallery(context, bm, "");
                                                    }
                                                });
                                            }
                                        }).start();


                                    }
                                });
                            }
                        });
                    }
                });
            }
        }).start();

        userName = (TextView) this.findViewById(R.id.tv_userName);
        userYY = (TextView) this.findViewById(R.id.tv_userYY);
        userPhone = (TextView) this.findViewById(R.id.tv_phoneNum);
        userZC = (TextView) this.findViewById(R.id.tv_useZC);
        userSCLY = (TextView) this.findViewById(R.id.tv_userSCLY);
        userGRJJ = (TextView) this.findViewById(R.id.tv_userGRJJ);


        tv_share = (TextView) this.findViewById(R.id.tv_share);


        sys = (TextView) this.findViewById(R.id.sys);
        sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        userName.setText(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        userYY.setText(mApp.mViewSysUserDoctorInfoAndHospital.getHospitalInfoName());

        Integer flagDoctorStatus = mApp.mViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus();
        if (flagDoctorStatus == 0) {
            userPhone.setText("未认证");
        } else {
            userPhone.setText("已认证");
        }

        if (TextUtils.isEmpty(mApp.mViewSysUserDoctorInfoAndHospital.getDepartmentName())) {
            userZC.setText(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorTitleName());
        } else {
            userZC.setText(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorTitleName() + "|" + mApp.mViewSysUserDoctorInfoAndHospital.getDepartmentName());
        }
        if (mApp.mViewSysUserDoctorInfoAndHospital.getGoodAtRealm() == null) {
            userSCLY.setText("暂无数据");
        } else {
            userSCLY.setText(mApp.mViewSysUserDoctorInfoAndHospital.getGoodAtRealm());
        }

        if (mApp.mViewSysUserDoctorInfoAndHospital.getSynopsis() == null) {
            userGRJJ.setText("暂无数据");
        } else {
            userGRJJ.setText(mApp.mViewSysUserDoctorInfoAndHospital.getSynopsis());

        }

        //医生分享
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare();
            }
        });
        //患者分享
        qr_save = findViewById(R.id.qr_save);
        qr_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient();
            }
        });
    }

    private void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow = new MoreFeaturesPopupWindow(QRCodeActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                }
            }
        });

    }


    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    // 患者分享
    private void patient() {

        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_dynamic, null);
        dialog.setContentView(view);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //分享好友
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToShare(SendMessageToWX.Req.WXSceneSession, patientShareTitle, patientShareContent, patientShareUrl, R.mipmap.logo);
                dialog.dismiss();
            }
        });
        //分享朋友圈
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToShare(SendMessageToWX.Req.WXSceneTimeline, patientShareTitle, patientShareContent, patientShareUrl, R.mipmap.logo);
                dialog.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }


    //医生分享
    private void wechatShare() {

        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_dynamic, null);
        dialog.setContentView(view);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //分享好友
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToShare(SendMessageToWX.Req.WXSceneSession, doctorShareTitle, doctorShareContent, doctorShareUrl, R.mipmap.logo);
                dialog.dismiss();
            }
        });
        //分享朋友圈
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToShare(SendMessageToWX.Req.WXSceneTimeline, doctorShareTitle, doctorShareContent, doctorShareUrl, R.mipmap.logo);
                dialog.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }


    /**
     * 分享到微信
     *
     * @param type            SendMessageToWX.Req.WXSceneSession  //会话    SendMessageToWX.Req.WXSceneTimeline //朋友圈
     * @param title           标题
     * @param content         内容
     * @param url             地址
     * @param pictureResource 图标
     */
    public void ToShare(int type, String title, String content, String url, int pictureResource) {
        //i   0是会话  1是朋友圈
        if (isWeixinAvilible(context)) {
            WechatShareManager.ShareContentWebpage mShareContent =
                    (WechatShareManager.ShareContentWebpage) mShareManager.getShareContentWebpag(title, content, url, pictureResource);

            mShareManager.shareByWebchat(mShareContent, type);
        } else {
            Toast.makeText(context, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context, Bitmap bmp, String picName) {
        saveImageToGallery(bmp, picName);
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);


        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存图片到图库
     *
     * @param bmp
     */
    public static void saveImageToGallery(Bitmap bmp, String bitName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "yingtan");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = bitName + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPhotoFile(file);
    }

    //保存到相册
    private static File mPhotoFile = null;

    public static void setPhotoFile(File photoFile) {
        mPhotoFile = photoFile;
    }
}
