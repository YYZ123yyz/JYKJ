package www.jykj.com.jykj_zxyl.activity.home.tjhz;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
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
import rx.functions.Action1;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.news.UnionNewsDetailActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import orcameralib.CameraActivity;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.util.tencenUtil.models.QrcodeOCRRequest;
import zxing.android.CaptureActivity;

import static orcameralib.CameraActivity.KEY_CONTENT_TYPE;
import static orcameralib.CameraActivity.KEY_OUTPUT_FILE_PATH;


/**
 * 添加患者
 */
public class AddPatientActivity extends AppCompatActivity {

    private Context mContext;
    private AddPatientActivity mActivity;
    private LinearLayout mApplicationAudit;                  //申请审核
    private LinearLayout mSMSFZ;                             //扫描身份证\
    private String mIDCardFrontPath;                       //身份证正面路径
    private int mIDCardFrontRequstCode = 1000;             //身份证正面获取码

    private JYKJApplication mApp;


    private static final int REQUEST_CODE_SCAN = 10000;

    private Handler mHandler;


    public ProgressDialog mDialogProgress = null;                                  //进度条
    public String mRetString;

    private String mNetRetStr;                 //返回字符串

    private EditText mIDCardEdit;                //身份证号码
    private EditText mUserNameEdit;                //身份证姓名
    private TextView mSexEdit;                //身份性别
    private TextView mBirthDayEdit;                //身份出生日期
    private EditText mPhoneNumEdit;                  //手机号
    private EditText mAddressEdit;                       //地址
    private EditText mSNYEdit;                       //收纳压
    private EditText mSZYEdit;                       //舒张压
    private EditText mXLEdit;                       //心率压

    private ProvideIdentityNumberInfo mProvideIdentityNumberInfo = new ProvideIdentityNumberInfo();          //身份证识别信息
    private LinearLayout mSexChocieLayout;                    //性别布局
    private LinearLayout mBirthDayLayout;                    //生日布局
    private LinearLayout mHZBQLayout;                        //患者标签

    private TextView mHZBQText;                          //患者标签

    private List<ProvideBasicsDomain> mList = new ArrayList<>();              //患者标签数据

    private Button mCommitButton;                          //提交

    private BindPatientParment mBindPatientParment;
    private TextView mGetVCodeText;                      //获取短信验证码
    private EditText mVCodeEdit;                      //获取短信验证码
    private int mTime = 60;

    private LinearLayout mSYS;                           //扫一扫
    private String mQrCode;                        //扫一扫二维码值
    private ImageView addpatient_back;


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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mIDCardFrontRequstCode && resultCode == RESULT_OK) {
            String bitmapString = mApp.gBitMapString;
            setIDCardDate(bitmapString);
        }
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String qrCode = data.getStringExtra("codedContent");
            mQrCode = qrCode;
//            String bitmapString = mApp.gBitMapString;
//            setIDCardDate(bitmapString);
            if (qrCode != null && !"".equals(qrCode)) {
//                //调用扫一扫功能，获取接口名称
                getSysServiceName(qrCode);
            }
            System.out.println(qrCode);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);
        ActivityUtil.setStatusBarMain(AddPatientActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mBindPatientParment = new BindPatientParment();
//        mIDCardFrontPath = new File(mCoet_activityAddpatient_sexEditntext.getFilesDir(), "2.jpg").toString();
        initLayout();
        initDir();
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
                        setLayoutDate();
                        break;
                    case 2:
                        cacerProgress();
                        break;
                    case 3:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mBindPatientParment.setBindingSmsVerifyTokenData(netRetEntity.getResTokenData());
                            //启动定时器
                            startTimer();
                        } else
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        mGetVCodeText.setText(mTime + "");
                        break;
                    case 5:
                        mGetVCodeText.setText("重新获取");
                        break;
                    case 6:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            startActivity(new Intent(mContext, AddPatientQRCodeActivity.class)
                                    .putExtra("qrCode", mQrCode)
                                    .putExtra("serviceName", netRetEntity.getResMsg()));
                        } else
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 启动定时器，控制短信验证码时间
     */
    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (mTime > 0) {
                    mTime--;
                    mHandler.sendEmptyMessage(4);
                } else {
                    mTime = 60;
                    mHandler.sendEmptyMessage(5);
                    timer.cancel();
                }

            }
        };
        timer.schedule(task, 0, 1000);
    }

    /**
     * 设置页面数据
     */
    private void setLayoutDate() {

        if (mProvideIdentityNumberInfo.getIdNumber() != null && !"".equals(mProvideIdentityNumberInfo.getIdNumber()))
            mIDCardEdit.setText(mProvideIdentityNumberInfo.getIdNumber());
        if (mProvideIdentityNumberInfo.getUserName() != null && !"".equals(mProvideIdentityNumberInfo.getUserName()))
            mUserNameEdit.setText(mProvideIdentityNumberInfo.getUserName());
        if (mProvideIdentityNumberInfo.getGender() != null && !"".equals(mProvideIdentityNumberInfo.getGender()))
            mSexEdit.setText(mProvideIdentityNumberInfo.getGender());
        if (mProvideIdentityNumberInfo.getBirthday() != null && !"".equals(mProvideIdentityNumberInfo.getBirthday()))
            mBirthDayEdit.setText(mProvideIdentityNumberInfo.getBirthday());
        if (mProvideIdentityNumberInfo.getAddress() != null && !"".equals(mProvideIdentityNumberInfo.getAddress()))
            mAddressEdit.setText(mProvideIdentityNumberInfo.getAddress());
    }

    /**
     * 初始化界面
     */
    private void initLayout() {
        mGetVCodeText = (TextView) this.findViewById(R.id.tv_getVCodeText);
        mGetVCodeText.setOnClickListener(new ButtonClick());
        mVCodeEdit = (EditText) this.findViewById(R.id.et_yzm);
        mIDCardEdit = (EditText) this.findViewById(R.id.et_activityAddpatient_idCardEdit);
        mUserNameEdit = (EditText) this.findViewById(R.id.et_activityAddpatient_userNameEdit);
        mSexEdit = (TextView) this.findViewById(R.id.et_activityAddpatient_sexEdit);
        mBirthDayEdit = (TextView) this.findViewById(R.id.et_activityAddpatient_birthDayEdit);
        mSexEdit.setOnClickListener(new ButtonClick());
        mBirthDayLayout = (LinearLayout) this.findViewById(R.id.li_birthDayChoice_layout);
        mBirthDayLayout.setOnClickListener(new ButtonClick());
        mSexChocieLayout = (LinearLayout) this.findViewById(R.id.li_sexChoice_layout);
        mSexChocieLayout.setOnClickListener(new ButtonClick());
        mSMSFZ = (LinearLayout) this.findViewById(R.id.li_activityAddPatient_smsfz);
        mSMSFZ.setOnClickListener(new ButtonClick());

        mSYS = (LinearLayout) this.findViewById(R.id.li_sys);
        mSYS.setOnClickListener(new ButtonClick());

        mHZBQLayout = (LinearLayout) this.findViewById(R.id.li_hzbqLayout);
        mHZBQText = (TextView) this.findViewById(R.id.tv_hzbqText);
        mHZBQLayout.setOnClickListener(new ButtonClick());
        mPhoneNumEdit = (EditText) this.findViewById(R.id.et_phoneNum);
        mAddressEdit = (EditText) this.findViewById(R.id.et_address);
        mSZYEdit = (EditText) this.findViewById(R.id.et_xy);
        mSNYEdit = (EditText) this.findViewById(R.id.et_sny);
        mXLEdit = (EditText) this.findViewById(R.id.et_xl);

        mCommitButton = (Button) this.findViewById(R.id.bt_commit);
        mCommitButton.setOnClickListener(new ButtonClick());

        //返回
        addpatient_back = findViewById(R.id.addpatient_back);
        addpatient_back.setOnClickListener(new ButtonClick());
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.li_activityAddPatient_smsfz:

                    startActivityForResult(new Intent(mContext, CameraActivity.class)
                            .putExtra(KEY_CONTENT_TYPE, "IDCardFront")
                            .putExtra(KEY_OUTPUT_FILE_PATH, mIDCardFrontPath), 1000);
//                    mContext.startActivityForResult<CameraActivity>(1001,
//                        CameraActivity.KEY_OUTPUT_FILE_PATH to File(filesDir, "2.jpg"),
//                    CameraActivity.KEY_CONTENT_TYPE to CameraActivity.CONTENT_TYPE_ID_CARD_FRONT)
                    break;
                case R.id.li_sexChoice_layout:
                    showSexChoiceDialog();
                    break;
                case R.id.li_birthDayChoice_layout:
                    showBirthDayChoiceDialog();
                    break;
                case R.id.li_hzbqLayout:
                    showHZBQDialog();
                    break;
                case R.id.bt_commit:
                    commit();
                    break;
                case R.id.tv_getVCodeText:
                    getVCode();
                    break;
                case R.id.li_sys:
                    scan();
                    break;
                    //返回
                case R.id.addpatient_back:
                    finish();
                    break;

            }
        }
    }


    /**
     * 患者标签选择框
     */
    private void showHZBQDialog() {
        final String[] items = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            items[i] = mList.get(i).getAttrName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择患者标签");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                mHZBQText.setText(items[arg1]);
                mBindPatientParment.setPatientLabelId(mList.get(arg1).getAttrCode() + "");
                mBindPatientParment.setPatientLabelName(mList.get(arg1).getAttrName());
            }
        });
        builder.create().show();

    }

    /**
     * 选择生日
     */
    private void showBirthDayChoiceDialog() {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
//            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            days = new StringBuffer().append(mYear).append(mMonth + 1).append(mDay).toString();
            mBirthDayEdit.setText(days);
        }
    };

    /**
     * 选择性别
     */
    private void showSexChoiceDialog() {
        String[] sexArry = new String[]{"女", "男"};// 性别选择
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mSexEdit.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    /**
     * 扫一扫
     */
    private void scan() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    //回调函数
    public void setIDCardDate(String bitmapString) {
        getProgressBar("请稍候", "正在识别。。。");
        //开始识别
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideIdentityNumberInfo ProvideIdentityNumberInfo = new ProvideIdentityNumberInfo();
                    ProvideIdentityNumberInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    ProvideIdentityNumberInfo.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    ProvideIdentityNumberInfo.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    ProvideIdentityNumberInfo.setFlagIdCardSide("1");
                    ProvideIdentityNumberInfo.setScanIdNumberImg((URLEncoder.encode("data:image/jpg;base64," + bitmapString)));
                    String str = new Gson().toJson(ProvideIdentityNumberInfo);
                    System.out.println("~~~~~~~~~~~~~开始提交了~~~~~~~~~~~~~~");
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorDataControlle/operScanByIdNumberImg");
                    System.out.println("~~~~~~~~~~~~~返回~~~~~~~~~~~~~~" + mRetString);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mProvideIdentityNumberInfo = JSON.parseObject(netRetEntity.getResJsonData(), ProvideIdentityNumberInfo.class);

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
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(1);
                    return;
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    //获取患者标签
    public void getBasicDate() {
        getProgressBar("请稍候", "正在获取数据。。。");
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
                    mList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);

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
        getProgressBar("请稍候", "正在提交。。。");
        mBindPatientParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mBindPatientParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mBindPatientParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        mBindPatientParment.setPatientIdNumber(mIDCardEdit.getText().toString());
        mBindPatientParment.setPatientUserName(mUserNameEdit.getText().toString());
        mBindPatientParment.setPatientGender(mSexEdit.getText().toString());
        mBindPatientParment.setPatientBirthday(mBirthDayEdit.getText().toString());
        mBindPatientParment.setPatientLinkPhone(mPhoneNumEdit.getText().toString());
        mBindPatientParment.setPatientAddress(mAddressEdit.getText().toString());
        mBindPatientParment.setHighPressureNum(mSNYEdit.getText().toString());
        mBindPatientParment.setLowPressureNum(mSZYEdit.getText().toString());
        mBindPatientParment.setHeartRateNum(mXLEdit.getText().toString());
        mBindPatientParment.setBindingSmsVerifyData(mVCodeEdit.getText().toString());
        if ("男".equals(mBindPatientParment.getPatientGender())) {
            mBindPatientParment.setPatientGender("1");
        }
        if ("女".equals(mBindPatientParment.getPatientGender())) {
            mBindPatientParment.setPatientGender("2");
        }
        if (mBindPatientParment.getPatientUserName() == null || "".equals(mBindPatientParment.getPatientUserName() == null)) {
            Toast.makeText(mContext, "请填写姓名", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getPatientGender() == null || "".equals(mBindPatientParment.getPatientGender() == null)) {
            Toast.makeText(mContext, "请填写性别", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getPatientLinkPhone() == null || "".equals(mBindPatientParment.getPatientLinkPhone() == null)) {
            Toast.makeText(mContext, "请填写联系手机", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getPatientLabelId() == null || "".equals(mBindPatientParment.getPatientLabelId() == null)) {
            Toast.makeText(mContext, "请选择患者标签", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getHighPressureNum() == null || "".equals(mBindPatientParment.getHighPressureNum() == null)) {
            Toast.makeText(mContext, "请填写收缩压", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getLowPressureNum() == null || "".equals(mBindPatientParment.getLowPressureNum() == null)) {
            Toast.makeText(mContext, "请填写舒张压", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getHeartRateNum() == null || "".equals(mBindPatientParment.getHeartRateNum() == null)) {
            Toast.makeText(mContext, "请填写心率", Toast.LENGTH_SHORT).show();
        }
        if (mBindPatientParment.getBindingSmsVerifyData() == null || "".equals(mBindPatientParment.getBindingSmsVerifyData() == null)) {
            Toast.makeText(mContext, "请填写验证码", Toast.LENGTH_SHORT).show();
        }
        new Thread() {
            public void run() {
//                //提交数据
                try {

                    String str = new Gson().toJson(mBindPatientParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "bindingDoctorPatientControlle/operDoctorWritePatientInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("绑定失败：" + netRetEntity.getResMsg());
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
    }

    /**
     * 获取短信验证码
     */
    private void getVCode() {
        getProgressBar("请稍候", "正在获取。。。");
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    BindPatientGetVCodeParment bindPatientGetVCodeParment = new BindPatientGetVCodeParment();
                    bindPatientGetVCodeParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    bindPatientGetVCodeParment.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    bindPatientGetVCodeParment.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    bindPatientGetVCodeParment.setSendUserLinkPhone(mPhoneNumEdit.getText().toString());
                    if (bindPatientGetVCodeParment.getSendUserLinkPhone() == null || "".equals(bindPatientGetVCodeParment.getSendUserLinkPhone())) {
                        Toast.makeText(mContext, "请先填写手机号", Toast.LENGTH_SHORT).show();
                    }
                    String str = new Gson().toJson(bindPatientGetVCodeParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "bindingDoctorPatientControlle/getBindingPatientSmsVerify");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
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
            }
        }.start();
    }

    /**
     * 调用扫一扫功能获取服务名称
     */
    private void getSysServiceName(String qrCode) {
        getProgressBar("请稍候", "正在识别。。。");
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    SysParment bindPatientGetVCodeParment = new SysParment();
                    bindPatientGetVCodeParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    bindPatientGetVCodeParment.setUserUseType(mApp.mViewSysUserDoctorInfoAndHospital.getUserUseType() + "");
                    bindPatientGetVCodeParment.setOperUserCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    bindPatientGetVCodeParment.setOperUserName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    bindPatientGetVCodeParment.setScanQrCode(qrCode);
                    String str = new Gson().toJson(bindPatientGetVCodeParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorDataControlle/operScanQrCodeInside");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(7);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(7);
                    return;
                }

                mHandler.sendEmptyMessage(7);
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
