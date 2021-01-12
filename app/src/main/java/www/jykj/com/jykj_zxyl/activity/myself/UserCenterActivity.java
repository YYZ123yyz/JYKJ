package www.jykj.com.jykj_zxyl.activity.myself;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.mySelf.ProvideViewSysUserDoctorInfoAndHospital;
import entity.patientInfo.ProvideViewSysUserPatientInfoAndRegion;
import entity.service.ViewSysUserDoctorInfoAndHospital;
import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import entity.user.UserInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import rx.functions.Action1;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.home.yslm.JoinDoctorsUnionActivity;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.util.ProvincePicker;

import www.jykj.com.jykj_zxyl.util.Util;
import yyz_exploit.Utils.QueryUserCond;
import yyz_exploit.Utils.StrUtils;
import yyz_exploit.Utils.UserResultInfo;
import yyz_exploit.activity.activity.FeedbackActivity;
import zxing.android.CaptureActivity;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity {

    private Context mContext;
    private UserCenterActivity mActivity;
    public ProgressDialog mDialogProgress = null;

    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串

    private ProvincePicker mPicker;                                            //省市县三级联动选择框
    public Map<String, ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map

    private int mChoiceRegionLevel;                                       //选择的区域级别
    private String mChoiceRegionID;                                       //选择的区域ID

    public static Pattern p =
            Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private Handler mHandler;

    private LinearLayout mUserHeadLayout;                //用户头像
    private LinearLayout mChoiceSexLayout;           //选择性别布局
    private LinearLayout mChoiceBirthLayout;             //选择生日布局
    private TextView mUserSexText;                   //用户性别
    private TextView mUserBirthDayText;              //用户生日

    private LinearLayout mChoiceRegionLayout;            //选择区域布局
    private TextView mChoiceRegionText;                 //地区选择text

    private LinearLayout mChoiceHospitalLayout;              //选择医院布局
    private TextView mChoiceHospitalText;                //选择医院text
    String path = Environment.getExternalStorageDirectory() + "";
    private File mTempFile = createFileIfNeed("UserIcon.png");         //声明一个拍照结果的临时文件
    private ImageView mUserHeadImage;                 //用户头像显示

    private List<ProvideHospitalInfo> mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private String[] mProvideHospitalNameInfos;                              //医院对应的名称列表

    private LinearLayout mChoiceDepartmentLayout;                                  //选择一级科室布局
    private TextView mChoiceDepartmentText;                                    //选择二级科室

    private LinearLayout mChoiceDepartmentSecondLayout;                              //选择二级科室布局
    private TextView mChocieDepartmentSecondText;                                //选择二级科室
    private int mChoiceHospitalIndex;           //选择的医院下标

    private List<ProvideHospitalDepartment> mProvideHospitalDepartmentFInfos = new ArrayList<>();              //获取到的医院一级科室列表
    private String[] mProvideHospitalDepartmentFNameInfos;                              //医院一级科室对应的名称列表


    private List<ProvideHospitalDepartment> mProvideHospitalDepartmentSInfos = new ArrayList<>();              //获取到的医院二级科室列表
    private String[] mProvideHospitalDepartmentSNameInfos;                              //医院二级科室对应的名称列表

    private EditText mUserNameText;                                                  //用户名

    private TextView mUserTitleName;                                                 //医生职称
    private EditText mAddressEdit;                                                   //通讯地址
    private EditText mEmalEdit;                                                      //邮箱
    private EditText mSynopsisEdit;                                                      //个人简介
    private EditText mGoodAtRealmEdit;                                               //擅长领域

    private List<ProvideBasicsDomain> mDoctorTitleList = new ArrayList<>();              //医生职称数据

    private TextView mCommit;                                                        //提交
    private Bitmap mUserHeadBitmap;
    private String mNetLoginRetStr;                 //登录返回字符串


    private ProvideViewSysUserDoctorInfoAndHospital mProvideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();  //医生信息
    private ImageView usercenter_back;
    private LinearLayout usercenterBack;
    private ImageButton ivAdd;
    private MoreFeaturesPopupWindow mPopupWindow;
    public UserCenterActivity() throws IOException {
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_myself_usercenter;
    }

    @Override
    protected void initView() {
        super.initView();
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
        getData();
        initDate(); //初始化数据 获取医院、科室、二级科室
        getBasicDate();//   获取医院职称
    }

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
    private File createFileIfNeed(String fileName) throws IOException {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                    case 1:
                        cacerProgress();
                        setLayoutDate();
                        break;
                    case 3:
                        cacerProgress();
                        if (mNetLoginRetStr != null && !mNetLoginRetStr.equals("")) {
                            NetRetEntity  netRetEntity = new Gson().fromJson(mNetLoginRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                mApp.mViewSysUserDoctorInfoAndHospital = new Gson().fromJson(netRetEntity.getResJsonData(), ViewSysUserDoctorInfoAndHospital.class);
                                mApp.saveUserInfo();
                                Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
                                //登录IM
                                mApp.loginIM();
                                finish();
                            } else {
                                Toast.makeText(mContext, "提交失败" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "网络异常，请联系管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        break;
                }
            }
        };
    }


    /**
     * 设置布局显示
     */
    private void setLayoutDate() {
        Integer flagDoctorStatus = mProvideViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus();
        if (flagDoctorStatus!=null) {
            mUserNameText.setEnabled(flagDoctorStatus == 0);
        }
        //姓名
        if (mProvideViewSysUserDoctorInfoAndHospital.getUserName() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getUserName()))
            mUserNameText.setHint("请填写姓名（必填）");
        else
            mUserNameText.setText(mProvideViewSysUserDoctorInfoAndHospital.getUserName());
        //性别
        if (mProvideViewSysUserDoctorInfoAndHospital.getGender() == 1)
            mUserSexText.setText("男");
        else if (mProvideViewSysUserDoctorInfoAndHospital.getGender() == 2)
            mUserSexText.setText("女");
        else
            mUserSexText.setHint("请选择性别");
        //出生日期
        if (mProvideViewSysUserDoctorInfoAndHospital.getBirthday() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getBirthday()))
            mUserBirthDayText.setText(Util.dateToStrNUR(mProvideViewSysUserDoctorInfoAndHospital.getBirthday()));
        //区域
        String region;
        if (TextUtils.isEmpty(mProvideViewSysUserDoctorInfoAndHospital.getProvinceName()) && TextUtils.isEmpty(mProvideViewSysUserDoctorInfoAndHospital.getCityName()) && TextUtils.isEmpty(mProvideViewSysUserDoctorInfoAndHospital.getAreaName())) {
            region = "";
        } else if (TextUtils.isEmpty(mProvideViewSysUserDoctorInfoAndHospital.getCityName())) {
            region = mProvideViewSysUserDoctorInfoAndHospital.getProvinceName();
        } else if (TextUtils.isEmpty(mProvideViewSysUserDoctorInfoAndHospital.getAreaName())) {
            region = mProvideViewSysUserDoctorInfoAndHospital.getProvinceName() + mProvideViewSysUserDoctorInfoAndHospital.getCityName();
        } else {
            region = mProvideViewSysUserDoctorInfoAndHospital.getProvinceName() + mProvideViewSysUserDoctorInfoAndHospital.getCityName() + mProvideViewSysUserDoctorInfoAndHospital.getAreaName();
        }

        mChoiceRegionText.setText(region);
        //医院
        if (mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoName() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoName()))
            mChoiceHospitalText.setText(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoName());
        //科室
        if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentName() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentName()))
            mChoiceDepartmentText.setText(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentName());
        //二级科室
        if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentSecondName() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentSecondName()))
            mChocieDepartmentSecondText.setText(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentSecondName());
        //职称
        if (mProvideViewSysUserDoctorInfoAndHospital.getDoctorTitleName() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getDoctorTitleName()))
            mUserTitleName.setText(mProvideViewSysUserDoctorInfoAndHospital.getDoctorTitleName());
        //通讯地址
        if (mProvideViewSysUserDoctorInfoAndHospital.getAddress() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getAddress()))
            mAddressEdit.setText(mProvideViewSysUserDoctorInfoAndHospital.getAddress());
        //邮箱
        if (mProvideViewSysUserDoctorInfoAndHospital.getEmail() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getEmail()))

            mEmalEdit.setText(mProvideViewSysUserDoctorInfoAndHospital.getEmail());


        //个人简介
        if (mProvideViewSysUserDoctorInfoAndHospital.getSynopsis() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getSynopsis()))
            mSynopsisEdit.setText(mProvideViewSysUserDoctorInfoAndHospital.getSynopsis());
        //擅长领域
        if (mProvideViewSysUserDoctorInfoAndHospital.getGoodAtRealm() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getGoodAtRealm()))
            mGoodAtRealmEdit.setText(mProvideViewSysUserDoctorInfoAndHospital.getGoodAtRealm());

        if (mProvideViewSysUserDoctorInfoAndHospital.getUserLogoUrl() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getUserLogoUrl())) {
            try {
                int avatarResId = Integer.parseInt(mProvideViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(mUserHeadImage);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mProvideViewSysUserDoctorInfoAndHospital.getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(mUserHeadImage);
            }
        }

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mCommit = (TextView) this.findViewById(R.id.tv_activityUserCenter_commit);
        mCommit.setOnClickListener(new ButtonClick());

        mUserNameText = (EditText) this.findViewById(R.id.tv_activityUserCenter_userNameText);

        mUserTitleName = (TextView) this.findViewById(R.id.tv_activityUserCenter_userTitleEdit);
        mUserTitleName.setOnClickListener(new ButtonClick());
        mAddressEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_addressEdit);
        mEmalEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_emalEdit);
        mSynopsisEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_synopsisEdit);
        mGoodAtRealmEdit = (EditText) this.findViewById(R.id.et_activityUserCenter_goodAtRealmEdit);

        mUserHeadLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userHeadLayout);
        mUserHeadLayout.setOnClickListener(new ButtonClick());
        mChoiceSexLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userSexLayout);
        mChoiceSexLayout.setOnClickListener(new ButtonClick());
        mUserHeadImage = (ImageView) this.findViewById(R.id.iv_activityUserCenter_userImage);

        ivAdd = findViewById(R.id.right_image_search);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow ==null){
                    mPopupWindow = new MoreFeaturesPopupWindow(UserCenterActivity.this);
                }
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                }else {
                    mPopupWindow.dismiss();
                }
            }
        });

        mChoiceBirthLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userBirthLayout);
        mChoiceBirthLayout.setOnClickListener(new ButtonClick());
        mUserSexText = (TextView) this.findViewById(R.id.tv_activityUserCenter_userSexText);

        mUserBirthDayText = (TextView) this.findViewById(R.id.tv_activityUserCenter_userBirthDayText);

        mChoiceRegionLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_userRegionLayout);
        mChoiceRegionLayout.setOnClickListener(new ButtonClick());

        mChoiceRegionText = (TextView) this.findViewById(R.id.tv_activityUserCenter_userRegionText);

        mChoiceHospitalLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_choiceHospitalLayout);
        mChoiceHospitalLayout.setOnClickListener(new ButtonClick());
        mChoiceHospitalText = (TextView) this.findViewById(R.id.tv_activityUserCenter_choiceHospitalText);

        mChoiceDepartmentLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_choiceDepartmentLayout);
        mChoiceDepartmentLayout.setOnClickListener(new ButtonClick());
        mChoiceDepartmentText = (TextView) this.findViewById(R.id.tv_activityUserCenter_choiceDepartmentText);

        mChoiceDepartmentSecondLayout = (LinearLayout) this.findViewById(R.id.li_activityUserCenter_choiceDepartmentSecondLayout);
        mChoiceDepartmentSecondLayout.setOnClickListener(new ButtonClick());
        mChocieDepartmentSecondText = (TextView) this.findViewById(R.id.tv_activityUserCenter_choiceDepartmentSecondText);

        //返回
        usercenterBack = findViewById(R.id.back);
        usercenterBack.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_activityUserCenter_userHeadLayout:
                    RxPermissions.getInstance(UserCenterActivity.this)
                            .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {//允许权限，6.0以下默认true
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
                                    } else {
                                        Toast.makeText(UserCenterActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    break;
                case R.id.li_activityUserCenter_userSexLayout:
                    Integer flagDoctorStatus = mProvideViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus();
                    if (flagDoctorStatus!=null) {
                        if (flagDoctorStatus==0) {
                            showSexChoiceDialog();
                        }
                    }

                    break;
                case R.id.li_activityUserCenter_userBirthLayout:
                    //showBirthDayChoiceDialog();
                    Integer flagDoctorStatus2 = mProvideViewSysUserDoctorInfoAndHospital.getFlagDoctorStatus();
                    if (flagDoctorStatus2!=null) {
                        if (flagDoctorStatus2==0) {
                            showCalendarDialog();
                        }
                    }
                    break;
                case R.id.li_activityUserCenter_userRegionLayout:
                    //选择区域
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0) {
                        Toast.makeText(mContext, "区域数据为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity, 5);
                    mPicker.show();
                    break;
                case R.id.li_activityUserCenter_choiceHospitalLayout:
                    if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince())) {
                        Toast.makeText(mContext, "请选择地区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalView();
                    break;
                case R.id.li_activityUserCenter_choiceDepartmentLayout:

                    if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince())) {
                        Toast.makeText(mContext, "请选择地区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode())) {
                        Toast.makeText(mContext, "请选择医院", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalDepartmentFView();

                    break;
                case R.id.li_activityUserCenter_choiceDepartmentSecondLayout:
                    if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince())) {
                        Toast.makeText(mContext, "请选择地区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode())) {
                        Toast.makeText(mContext, "请选择医院", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId())) {
                        Toast.makeText(mContext, "请选择一级科室", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showChoiceHospitalDepartmentSView();
                    break;
                //选择医生职称
                case R.id.tv_activityUserCenter_userTitleEdit:
                    showDoctorTitleDialog();
                    break;
                case R.id.tv_activityUserCenter_commit:
                    commit();
                    break;
                //返回
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    //验证函数优化版
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }

    boolean isupsuccess = false;

    /**
     * 提交
     */
    private void commit() {
        mProvideViewSysUserDoctorInfoAndHospital.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mProvideViewSysUserDoctorInfoAndHospital.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mProvideViewSysUserDoctorInfoAndHospital.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        mProvideViewSysUserDoctorInfoAndHospital.setUserName(mUserNameText.getText().toString());
        mProvideViewSysUserDoctorInfoAndHospital.setAddress(mAddressEdit.getText().toString());
        mProvideViewSysUserDoctorInfoAndHospital.setEmail(mEmalEdit.getText().toString());
        mProvideViewSysUserDoctorInfoAndHospital.setSynopsis(mSynopsisEdit.getText().toString());
        mProvideViewSysUserDoctorInfoAndHospital.setGoodAtRealm(mGoodAtRealmEdit.getText().toString());
        //判断为空
        if (mProvideViewSysUserDoctorInfoAndHospital.getUserName() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getUserName())) {
            Toast.makeText(mContext, "请选择姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getGender() == 1 || mProvideViewSysUserDoctorInfoAndHospital.getGender() == 2) {

        } else {
            Toast.makeText(mContext, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mProvideViewSysUserDoctorInfoAndHospital.getBirthday() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getBirthday())) {
            Toast.makeText(mContext, "请选择出生日期", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mProvideViewSysUserDoctorInfoAndHospital.setBirthdayStr(Util.dateToStr(mProvideViewSysUserDoctorInfoAndHospital.getBirthday()));
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode())) {
            Toast.makeText(mContext, "请选择医院", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId())) {
            Toast.makeText(mContext, "请选择科室", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentSecondId() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentSecondId())) {
            Toast.makeText(mContext, "请选择二级科室", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getDoctorTitle() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getDoctorTitle())) {
            Toast.makeText(mContext, "请选择医生职称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() == null || "".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince())) {
            Toast.makeText(mContext, "请选择省份", Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("请稍候", "正在提交数据...");
        new Thread() {
            public void run() {
                boolean isupsuccess = false;
                try {
                    if (mUserHeadBitmap != null)
                        mProvideViewSysUserDoctorInfoAndHospital.setBase64ImgData((URLEncoder.encode("data:image/jpg;base64," + BitmapUtil.bitmaptoString(mUserHeadBitmap))));
                    String city = mProvideViewSysUserDoctorInfoAndHospital.getCity();
                    if (StringUtils.isEmpty(city)) {
                        mProvideViewSysUserDoctorInfoAndHospital.setCity("-1");
                    }
                    String str = new Gson().toJson(mProvideViewSysUserDoctorInfoAndHospital);
                    HashMap<String,Object> stringObjectHashMap=new HashMap<>();

                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorPersonalSetControlle/operUserDoctorInfo");
                    Log.e("tag", "run:  修改  "+mNetRetStr );
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 1) {
                        isupsuccess = true;
                        mApp.mViewSysUserDoctorInfoAndHospital.setUserName(mProvideViewSysUserDoctorInfoAndHospital.getUserName());
                        mApp.mViewSysUserDoctorInfoAndHospital.setUserNameAlias(mProvideViewSysUserDoctorInfoAndHospital.getUserNameAlias());
                        mApp.mViewSysUserDoctorInfoAndHospital.setGender(mProvideViewSysUserDoctorInfoAndHospital.getGender());
                        if (null != mProvideViewSysUserDoctorInfoAndHospital.getBirthday()) {
                            mApp.mViewSysUserDoctorInfoAndHospital.setBirthday(Util.dateToStr(mProvideViewSysUserDoctorInfoAndHospital.getBirthday()));
                        }
                        mApp.mViewSysUserDoctorInfoAndHospital.setCity(StrUtils.defaultStr(mProvideViewSysUserDoctorInfoAndHospital.getCity()));
                        mApp.mViewSysUserDoctorInfoAndHospital.setProvince(StrUtils.defaultStr(mProvideViewSysUserDoctorInfoAndHospital.getProvince()));
                        mApp.mViewSysUserDoctorInfoAndHospital.setAddress(StrUtils.defaultStr(mProvideViewSysUserDoctorInfoAndHospital.getAddress()));
                  //      mApp.mViewSysUserDoctorInfoAndHospital.setAddress(StrUtils.defaultStr(mChoiceRegionID));
                        mApp.mViewSysUserDoctorInfoAndHospital.setLinkPhone(StrUtils.defaultStr(mProvideViewSysUserDoctorInfoAndHospital.getLinkPhone()));
                        Log.e("tag", "手机号 " + mApp.mViewSysUserDoctorInfoAndHospital.getLinkPhone());
                        mNetRetStr = new Gson().toJson(netRetEntity);
                        Log.e("tag", "提交 " + mNetRetStr);
                    }else{
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(3);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                //重新登录，刷新用户信息
                if (isupsuccess) {
                    QueryUserCond quecond = new QueryUserCond();
                    quecond.setUserCodeList(mProvideViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    try {
                        mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(quecond), Constant.SERVICEURL + "doctorPatientCommonDataController/getUserInfoList");
                        NetRetEntity retEntity = JSON.parseObject(mNetLoginRetStr, NetRetEntity.class);
                        if (1 == retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length() > 3) {
                            List<UserResultInfo> retlist = JSON.parseArray(retEntity.getResJsonData(), UserResultInfo.class);
                            UserResultInfo onebean = retlist.get(0);
                            mApp.mViewSysUserDoctorInfoAndHospital.setUserLogoUrl(onebean.getUserLogoUrl());
                            mApp.mViewSysUserDoctorInfoAndHospital.setQrCode(onebean.getQrCode());
                            NetRetEntity retentone = new NetRetEntity();
                            retentone.setResCode(1);
                            retentone.setResJsonData(new Gson().toJson(mApp.mViewSysUserDoctorInfoAndHospital));
                            mNetLoginRetStr = new Gson().toJson(retentone);
                            Log.e("tag", "run:" + retEntity.getResJsonData());
                        }
                    } catch (Exception e) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                        mNetLoginRetStr = new Gson().toJson(retEntity);
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }

    /**
     * 选择二级科室
     */
    private void showChoiceHospitalDepartmentSView() {
        if (mProvideHospitalDepartmentSInfos != null) {
            mProvideHospitalDepartmentSNameInfos = new String[mProvideHospitalDepartmentSInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentSInfos.size(); i++) {
            mProvideHospitalDepartmentSNameInfos[i] = mProvideHospitalDepartmentSInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择二级科室");
        listDialog.setItems(mProvideHospitalDepartmentSNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentSText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 显示科室名称以及获取数据
     *
     * @param index
     */
    private void showChoiceHospitalDepartmentSText(int index) {
        mChocieDepartmentSecondText.setText(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId(mProvideHospitalDepartmentSInfos.get(index).getHospitalDepartmentId() + "");
    }


    /**
     * 选择一级科室
     */
    private void showChoiceHospitalDepartmentFView() {
        if (mProvideHospitalDepartmentFInfos != null) {
            mProvideHospitalDepartmentFNameInfos = new String[mProvideHospitalDepartmentFInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentFInfos.size(); i++) {
            mProvideHospitalDepartmentFNameInfos[i] = mProvideHospitalDepartmentFInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择一级科室");
        listDialog.setItems(mProvideHospitalDepartmentFNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示科室名称以及获取数据
     *
     * @param index
     */
    private void showChoiceHospitalDepartmentText(int index) {

        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId("");
        mChocieDepartmentSecondText.setText("");
        mChocieDepartmentSecondText.setHint("请选择二级科室");
        mChoiceDepartmentText.setText(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId() + "");

        //获取科室信息
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    //获取二级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(mChoiceHospitalIndex).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideHospitalDepartment), Constant.SERVICEURL + "hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取二级科室信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //二级科室信息获取成功
                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>() {
                    }.getType());
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


    /**
     * 选择医院对话框
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null) {
            mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++) {
            mProvideHospitalNameInfos[i] = mProvideHospitalInfos.get(i).getHospitalName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideHospitalNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 显示医院并获取一级科室
     */
    private void showChoiceHospitalText(int index) {
        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentId("");
        mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId("");
        mChoiceDepartmentText.setText("");
        mChoiceDepartmentText.setHint("请选择一级科室");
        mChocieDepartmentSecondText.setText("");
        mChocieDepartmentSecondText.setHint("请选择二级科室");
        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
        mProvideViewSysUserDoctorInfoAndHospital.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
        mChoiceHospitalIndex = index;
        //获取科室信息
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    //获取一级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(0);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideHospitalDepartment), Constant.SERVICEURL + "hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取一级科室信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //一级科室信息获取成功
                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>() {
                    }.getType());
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



    private void showCalendarDialog(){
        TimePickerView timePickerView=new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateTime = DateUtils.getDate(date);
                mUserBirthDayText.setText(dateTime);
                mProvideViewSysUserDoctorInfoAndHospital.setBirthday(Util.strToDateLongV2(dateTime));
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt)).setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").build();
        timePickerView.show();
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

        private Date d;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear + 1;
            int mDay = dayOfMonth;
            String month = "";
            String day = "";
//            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            if (mMonth < 10)
                month = "0" + mMonth;
            else
                month = mMonth + "";
            if (mDay < 10)
                day = "0" + mDay;
            else
                day = mDay + "";
            mUserBirthDayText.setText(mYear + "-" + month + "-" + day);
            mProvideViewSysUserDoctorInfoAndHospital.setBirthday(Util.strToDateLongV2(mYear + "-" + month + "-" + day));
        }
    };

    /**
     * 选择性别
     */
    private void showDoctorTitleDialog() {
        String[] doctorTitleName = new String[mDoctorTitleList.size()];// 医生职称
        for (int i = 0; i < mDoctorTitleList.size(); i++)
            doctorTitleName[i] = mDoctorTitleList.get(i).getAttrName();
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                mUserTitleName.setText(doctorTitleName[which]);
                mProvideViewSysUserDoctorInfoAndHospital.setDoctorTitle(mDoctorTitleList.get(which).getAttrCode());
                mProvideViewSysUserDoctorInfoAndHospital.setDoctorTitleName(mDoctorTitleList.get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


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
                mUserSexText.setText(sexArry[which]);
                switch (which) {
                    case 0:
                        mProvideViewSysUserDoctorInfoAndHospital.setGender(2);
                        break;
                    case 1:
                        mProvideViewSysUserDoctorInfoAndHospital.setGender(1);
                        break;
                }
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    @Override
    protected void onActivityResult(
            int requestCode,  // 请求码 自定义
            int resultCode,  // 结果码 成功 -1 == OK
            Intent data) { // 数据 ? 可以没有
        try {

            // 如果是直接从相册获取
            if (requestCode == Constant.SELECT_PIC_FROM_ALBUM
                    && resultCode == RESULT_OK
                    && data != null) {

                final Uri uri = data.getData();//返回相册图片的Uri
                BitmapUtil.startPhotoZoom(mActivity, uri, 450);
            }

            // 处理拍照返回
            if (requestCode == Constant.SELECT_PIC_BY_TACK_PHOTO
                    && resultCode == RESULT_OK) {// 拍照成功 RESULT_OK= -1
                // 剪裁图片
                BitmapUtil.startPhotoZoom(mActivity, Uri.fromFile(mTempFile), 450);
            }
            // 接收剪裁回来的结果
            if (requestCode == Constant.REQUEST_PHOTO_CUT
                    && resultCode == RESULT_OK) {// 剪裁加工成功
                //让剪裁结果显示到图片框
                setPicToView(data);
            }
        } catch (Exception e) {
            Log.i("yi", "yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPicToView(Intent data) {
        Bitmap photo;
        try {
            Uri u = data.getData();
            if (u != null) {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));//将imageUri对象的图片加载到内存
            } else {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"))));//将imageUri对象的图片加载到内存
            }
            mUserHeadBitmap = photo;

            Glide.with(this).load(photo).into(mUserHeadImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    private void initDate() {
        new Thread() {
            public void run() {
                try {
                    //获取医院数据,判断区域级别
                    if (mProvideViewSysUserDoctorInfoAndHospital.getArea() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getArea())) {
                        mChoiceRegionLevel = 3;
                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getArea();
                    } else if (mProvideViewSysUserDoctorInfoAndHospital.getCity() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getCity())) {
                        mChoiceRegionLevel = 2;
                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getCity();
                    } else if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getProvince())) {
                        mChoiceRegionLevel = 1;
                        mChoiceRegionID = mProvideViewSysUserDoctorInfoAndHospital.getProvince();
                    }
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideBasicsRegion), Constant.SERVICEURL + "hospitalDataController/getHospitalInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(4);
                        return;
                    }
                    //医院数据获取成功
                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>() {
                    }.getType());

                    //获取一级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(0);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideHospitalDepartment), Constant.SERVICEURL + "hospitalDataController/getHospitalDepartment");
                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取一级科室信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(4);
                        return;
                    }
                    //一级科室信息获取成功
                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>() {
                    }.getType());

                    //获取二级科室
                    provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideViewSysUserDoctorInfoAndHospital.getHospitalInfoCode());
                    if (mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId() != null && !"".equals(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId())) {
                        provideHospitalDepartment.setHospitalDepartmentId(Integer.parseInt(mProvideViewSysUserDoctorInfoAndHospital.getDepartmentId()));
                        mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideHospitalDepartment), Constant.SERVICEURL + "hospitalDataController/getHospitalDepartment");
                        netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            NetRetEntity retEntity = new NetRetEntity();
                            retEntity.setResCode(0);
                            retEntity.setResMsg("获取二级科室信息失败：" + netRetEntity.getResMsg());
                            mNetRetStr = new Gson().toJson(retEntity);
                            mHandler.sendEmptyMessage(4);
                            return;
                        }
                        //二级科室信息获取成功
                        mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>() {
                        }.getType());
                        System.out.println();
                    }
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(4);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(4);
            }
        }.start();

    }

    /**
     * 获取数据
     */
    private void getData() {
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideViewSysUserDoctorInfoAndHospital provideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();
                    provideViewSysUserDoctorInfoAndHospital.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewSysUserDoctorInfoAndHospital.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    //实体转JSON字符串
                    String str = new Gson().toJson(provideViewSysUserDoctorInfoAndHospital);
                    //获取用户数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "/doctorPersonalSetControlle/getUserDoctorInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    mProvideViewSysUserDoctorInfoAndHospital = JSON.parseObject(netRetEntity.getResJsonData(), ProvideViewSysUserDoctorInfoAndHospital.class);
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


    //获取医生职称
    public void getBasicDate() {
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(50003);

                    String str = new Gson().toJson(provideBasicsDomain);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mDoctorTitleList = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);
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
     * 设置所在地区显示
     */
    public void setRegionText() {
        if (mProvideViewSysUserDoctorInfoAndHospital.getProvince() != null) {
            //将选择的医院，科室，名称清空
            if (!mProvideViewSysUserDoctorInfoAndHospital.getProvince().equals(mChoiceRegionMap.get("provice").getRegion_id())
                    || !mProvideViewSysUserDoctorInfoAndHospital.getProvince().equals(mChoiceRegionMap.get("city").getRegion_id())
                    || !mProvideViewSysUserDoctorInfoAndHospital.getProvince().equals(mChoiceRegionMap.get("dist").getRegion_id())) {
                mProvideViewSysUserDoctorInfoAndHospital.setHospitalInfoCode("");
                mProvideViewSysUserDoctorInfoAndHospital.setDepartmentId("");
                mProvideViewSysUserDoctorInfoAndHospital.setDepartmentSecondId("");
                mChoiceHospitalText.setText("");
                mChoiceHospitalText.setHint("请选择医院");
                mChoiceDepartmentText.setText("");
                mChoiceDepartmentText.setHint("请选择一级科室");
                mChocieDepartmentSecondText.setText("");
                mChocieDepartmentSecondText.setHint("请选择二级科室");
            }
        }


        mProvideViewSysUserDoctorInfoAndHospital.setProvince(mChoiceRegionMap.get("provice").getRegion_id());
        //mProvideViewSysUserDoctorInfoAndHospital.setProvince(mChoiceRegionMap.get("provice").getRegion_name());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            if (mChoiceRegionMap.get("provice").getRegion_name().equals("") || mChoiceRegionMap.get("provice").getRegion_name() == null || mChoiceRegionMap.get("provice").getRegion_name() == "") {
                mChoiceRegionText.setText("");
            }
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;//市级所有，则是省级
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            mProvideViewSysUserDoctorInfoAndHospital.setCity("");
            mProvideViewSysUserDoctorInfoAndHospital.setArea("");
        } else if (mChoiceRegionMap.get("dist") == null || "qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            if (mChoiceRegionMap.get("city").getRegion_name().equals("") || mChoiceRegionMap.get("city").getRegion_name() == null || mChoiceRegionMap.get("city").getRegion_name() == "") {
                mChoiceRegionText.setText("mChoiceRegionMap.get(\"provice\").getRegion_name()");
            }
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name() + mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            mProvideViewSysUserDoctorInfoAndHospital.setArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            mProvideViewSysUserDoctorInfoAndHospital.setCity(mChoiceRegionMap.get("city").getRegion_id());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            if (mChoiceRegionMap.get("dist").getRegion_name().equals("") || mChoiceRegionMap.get("dist").getRegion_name() == "" || mChoiceRegionMap.get("dist").getRegion_name() == null) {
                mChoiceRegionText.setText("mChoiceRegionMap.get(\"provice\").getRegion_name() + mChoiceRegionMap.get(\"city\").getRegion_name() ");
            }
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name() + mChoiceRegionMap.get("city").getRegion_name() + mChoiceRegionMap.get("dist").getRegion_name());
            mProvideViewSysUserDoctorInfoAndHospital.setArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }

        //获取区域内医院
        getProgressBar("请稍候。。。。", "正在加载数据");
        new Thread() {
            public void run() {
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setRegion_level(mChoiceRegionLevel);
                    provideBasicsRegion.setRegion_id(mChoiceRegionID);
                    String str = new Gson().toJson(provideBasicsRegion);
                    //获取医院数据
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(provideBasicsRegion), Constant.SERVICEURL + "hospitalDataController/getHospitalInfo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取医院信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //医院数据获取成功
                    mProvideHospitalInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalInfo>>() {
                    }.getType());

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


}