package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ViewSysUserDoctorInfoAndHospital;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.adapter.Item_Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.adapter.Item_Rv_detectAdapter;
import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.adapter.Rv_detectAdapter;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.jykj.bean.DetectBean;
import com.hyphenate.easeui.jykj.bean.GetdetailsBean;
import com.hyphenate.easeui.jykj.bean.OrderItemBean;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.hyphenate.easeui.jykj.bean.ProvideBasicsDomain;
import com.hyphenate.easeui.jykj.bean.ProvideDoctorPatientUserInfo;
import com.hyphenate.easeui.jykj.bean.Restcommit;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.CollectionUtils;
import com.hyphenate.easeui.utils.MainMessage;
import com.hyphenate.easeui.utils.SharedPreferences_DataSave;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static android.icu.text.DateTimePatternGenerator.DAY;
import static android.widget.Toast.LENGTH_SHORT;


public class SigningDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public ProgressDialog mDialogProgress = null;
    private Context mContext;
    private SigningDetailsActivity mActivity;
    private List<DetectBean> mDetectBeans = new ArrayList<>();            //检测类型
    private List<DetectBean> mCoachingBean = new ArrayList<>();            //检测类型
    private Rv_detectAdapter rv_detectAdapter;
    private Rv_CoachingAdapter rvCoachingAdapter;
    private TimePickerView timePickerView;
    private String startTime;
    private TextView tvGson;
    private LinearLayout ivBackLeft;
    private TextView patientName;
    private TextView patientAge;
    private TextView patientSex;
    private LinearLayout linDetect;
    private RecyclerView rvqbzz;
    private LinearLayout linTime;
    private LinearLayout linClass;
    private TextView tvStartTime;
    private LinearLayout linStartTime;
    private TextView tvDuration;
    private LinearLayout linDuration;
    private Button btActivityMySelfSettingExitButton;
    private ScrollView wzxxSc;
    private SharedPreferences sharedPreferences;
    private String name;
    private String code;
    // private String mNetRetStr;
    private Handler mHandler;
    private String doctorAlias;
    private String patientAlias;
    private String patientCode;
    private String patientName1;
    private List<ProvideBasicsDomain> provideBasicsDomains;
    private String patientAge1;
    private String patientSex1;
    private int day;
    private TextView tv_times;
    private List<ProvideBasicsDomain> dayList = new ArrayList<>();   //天
    private List<ProvideBasicsDomain> secondaryList = new ArrayList<>();   //次数
    private List<ProvideBasicsDomain> monthList = new ArrayList<>();  //月
    private List<ProvideBasicsDomain> minuteList = new ArrayList<>();    //分钟
    private List<ProvideBasicsDomain> monthsList = new ArrayList<>();   //6个月
    private Object List;
    private TextView totalprice;
    private double Coachingprice = 0;
    private double Detectprice = 0;
    private double totalprice1 = 0;
    private ImageView iv_activityLogin_agreeImg;
    private boolean mAgree = true;                     //是否同意协议，默认同意
    private int dayListattrCode;
    private int secondaryListattrCode;
    private int monthListattrCode;
    private int videominuteListattrCode;
    private int videosecondaryListattrCode;
    private int videomonthListattrCode;
    private String monthsListattrName;
    private int monthsListattrCode;
    private int ageFromBirthTime;
    private int monthsListattrCode1;
    private String signOrderCode;
    private GetdetailsBean getdetailsBeans;
    private List<GetdetailsBean.OrderDetailListBean> DetectitemBeans = new ArrayList<>();
    private List<GetdetailsBean.OrderDetailListBean> CoachingitemBeans = new ArrayList<>();
    //    private Item_Rv_detectAdapter item_rv_detectAdapter;
//    private Item_Rv_CoachingAdapter item_rv_coachingAdapter;
    private RecyclerView rvClass;
    private CoachingBean MMcoachingBean;
    private String videomonthListattrName;
    private String videosecondaryListattrName;
    private String videominuteListattrName;
    private String type;
    private String secondaryListattrName;
    private String monthListattrName;
    private String type1;
    private NetRetEntity netRetEntity;
    private String doctorUrl;
    private String signNo;
    private String singCode;
    private TextView day_tv;
    private String dayListattrName;
    private OrderMessage orderMessage;
    private long day1;
    private TextView tv_prices;
    private ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo;
    private String status;
    private LinearLayout protocol_lin;
    private String singNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_details);
        mContext = this;
        mActivity = this;
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        Log.e("TAG", "onCreate: " + name);
        code = sharedPreferences.getString("code", "");
        doctorAlias = sharedPreferences.getString("mainDoctorAlias", "");

        //跳转传值
        Intent intent = getIntent();
        patientAlias = intent.getStringExtra("patientAlias");
        patientCode = intent.getStringExtra("patientCode");
        patientName1 = intent.getStringExtra("patientName");
        patientAge1 = intent.getStringExtra("patientAge");
        patientSex1 = intent.getStringExtra("patientSex");
        singCode = intent.getStringExtra("singCode");
        doctorUrl = intent.getStringExtra("doctorUrl");
        Log.e("TAG", "onCreate: " + doctorUrl);
        duration();
        //获取患者信息

        //订单详情的标识
        initView();
        addListener();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            type = extras.getString("singCode");
            status = extras.getString("status");
            singNO = extras.getString("singNO");
            if (!TextUtils.isEmpty(type)){
                Getdetails();
            }
        }
        initHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }

    private void getUser() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("userCode", patientCode);
        new Thread() {
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorPatientCommonDataController/getUserInfo");
                    Log.e("TAG", "run: 患者全部信息 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                Message message = new Message();
                message.what = 30;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
                // mHandler.sendEmptyMessage(4);
            }
        }.start();
    }

    private void duration() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("baseCode", "900051^900052^900053^900054^900055");
        new Thread() {
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "basicDataController/getBasicsDomainArray");
                    Log.e("TAG", "run: 全部 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                Message message = new Message();
                message.what = 4;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
                // mHandler.sendEmptyMessage(4);
            }
        }.start();
    }

    private void initView() {
        protocol_lin = (LinearLayout) findViewById(R.id.protocol_lin);
        day_tv = (TextView) findViewById(R.id.day_tv);
        //图标
        linStartTime = (LinearLayout) findViewById(R.id.lin_start_time_s);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        rvClass = (RecyclerView) findViewById(R.id.rv_class);
        iv_activityLogin_agreeImg = (ImageView) findViewById(R.id.iv_activityLogin_agreeImg);
        protocol_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgree = !mAgree;
                setAgreeImg();
            }
        });
        //总价
        tv_prices = (TextView) findViewById(R.id.tv_prices);
        totalprice = (TextView) findViewById(R.id.Totalprice);
        tvGson = (TextView) findViewById(R.id.tv_gson);
        ivBackLeft = (LinearLayout) findViewById(R.id.iv_back_left);
        ivBackLeft.setOnClickListener(this);
        ivBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        patientName = (TextView) findViewById(R.id.patient_name);
        patientName.setText(patientName1);
        patientAge = (TextView) findViewById(R.id.patient_age);
        patientSex= (TextView) findViewById(R.id.patient_sex);
        if (TextUtils.isEmpty(patientSex1)) {
            patientSex.setText("男");
        } else if (patientSex1.equals("1")) {
            patientSex.setText("男");
        } else if (patientSex1.equals("0")) {
            patientSex.setText("未知");
        } else {
            patientSex.setText("女");
        }

        linDetect = (LinearLayout) findViewById(R.id.lin_Detect);
        linDetect.setOnClickListener(this);
        rvqbzz = (RecyclerView) findViewById(R.id.rvqbzz);
        linTime = (LinearLayout) findViewById(R.id.lin_time);

        //频次
        linTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Frequencyofdetection();
            }
        });
        linClass = (LinearLayout) findViewById(R.id.lin_class);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        linDuration = (LinearLayout) findViewById(R.id.lin_duration);

        btActivityMySelfSettingExitButton = (Button) findViewById(R.id.bt_activityMySelfSetting_exitButton);
        //提交
        btActivityMySelfSettingExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(type)) {
                    commit();
                } else {
                    ModificationSubmission();
                }

            }
        });
        wzxxSc = (ScrollView) findViewById(R.id.wzxx_sc);

        //检测类型
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvqbzz.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvqbzz.setHasFixedSize(true);
//        //创建并设置Adapter
        rv_detectAdapter = new Rv_detectAdapter(mDetectBeans, mContext, mActivity);
        rvqbzz.setAdapter(rv_detectAdapter);
        rv_detectAdapter.setOnItemClickListener(new Rv_detectAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void onLongClick(int position) {

            }

            @Override
            public void onTextChanged(int pos, String value) {
                if (!CollectionUtils.isEmpty(mDetectBeans)
                        && pos < mDetectBeans.size()) {
                    if (!TextUtils.isEmpty(value)) {
                        mDetectBeans.get(pos).setPrice(Double.valueOf(value));
                    } else {
                        mDetectBeans.get(pos).setPrice(0);
                    }

                    setTotalprice(mDetectBeans, mCoachingBean);
                }

            }
        });
        //辅导类别
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
     //   LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(mContext,new S);
        layoutManager2.setOrientation(LinearLayout.VERTICAL);
        rvClass.setLayoutManager(layoutManager2);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvClass.setHasFixedSize(true);
//        //创建并设置Adapter
        rvCoachingAdapter = new Rv_CoachingAdapter(mCoachingBean, mContext, mActivity);
        rvClass.setAdapter(rvCoachingAdapter);
        rvCoachingAdapter.setOnItemCoachingClickListener(new Rv_CoachingAdapter.OnItemCoachingClickListener() {
            @Override
            public void onClick(int position) {
                time(position);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        rvCoachingAdapter.setOnItemCoachingLinClickListener(new Rv_CoachingAdapter.OnItemCoachingLinClickListener() {
            @Override
            public void onClick(int position) {
                Videofrequency(position);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    /**
     * 设置是否同意协议图标
     */
    private void setAgreeImg() {
        if (mAgree)
            iv_activityLogin_agreeImg.setImageResource(R.mipmap.nochoice);
        else
            iv_activityLogin_agreeImg.setImageResource(R.mipmap.choice);
    }


    //签约时长
    private void Duration() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvDuration.setText(monthsList.get(options1).getAttrName());
                monthsListattrName = monthsList.get(options1).getAttrName();
                monthsListattrCode1 = monthsList.get(options1).getAttrCode();
                // main(startTime);
                setTotalprice(mDetectBeans, mCoachingBean);
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(monthsList), null, null);
        optionPickUnit.show();
    }


    private void Videofrequency(final int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                videominuteListattrCode = minuteList.get(options1).getAttrCode();
                videominuteListattrName = minuteList.get(options1).getAttrName();
                videosecondaryListattrCode = secondaryList.get(options2).getAttrCode();

                videosecondaryListattrName = secondaryList.get(options2).getAttrName();
                videomonthListattrCode = monthList.get(options3).getAttrCode();
                videomonthListattrName = monthList.get(options3).getAttrName();
//                double price = mCoachingBean.get(pos).getPrice();
//                mCoachingBean.get(pos).setTotalPrice(price*videosecondaryListattrCode);
                mCoachingBean.get(pos).setValue(videosecondaryListattrCode);
                mCoachingBean.get(pos).setMinute(videominuteListattrCode);
                mCoachingBean.get(pos).setFrequency(videosecondaryListattrCode);
                mCoachingBean.get(pos).setMonths(videomonthListattrCode);
                rvCoachingAdapter.notifyDataSetChanged();
                setTotalprice(mDetectBeans, mCoachingBean);
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(minuteList), getDayStrList(secondaryList), getDayStrList(monthList));
        optionPickUnit.show();
    }

    //检测频次
    private void Frequencyofdetection() {

        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                dayListattrCode = dayList.get(options1).getAttrCode();
                dayListattrName = dayList.get(options1).getAttrName();
                day_tv.setText(dayListattrName);
                setTotalprice(mDetectBeans, mCoachingBean);
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(dayList), null, null);
        optionPickUnit.show();

    }

    private List<String> getDayStrList(List<ProvideBasicsDomain> dataList) {
        List<String> list = new ArrayList<>();
        for (ProvideBasicsDomain provideBasicsDomain : dataList) {
            list.add(provideBasicsDomain.getAttrName());
        }
        return list;
    }

    private Integer getDayCodeByName(String name) {
        Integer code = null;
        for (ProvideBasicsDomain provideBasicsDomain : dayList) {
            if (provideBasicsDomain.getAttrName().equals(name)) {
                code = provideBasicsDomain.getAttrCode();
                break;
            }
        }
        return code;
    }

    //修改提交
    private void ModificationSubmission() {
        if (mAgree) {
            Toast.makeText(mContext, "请先同意用户签约协议", LENGTH_SHORT).show();
            return;
        }
        if (!CollectionUtils.isEmpty(mDetectBeans)) {
            if (dayListattrCode == 0) {
                Toast.makeText(mActivity, "请选择频率时长", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(tvStartTime.getText().toString())) {
            Toast.makeText(mActivity, "请选择签约开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isChoosedFrequencyAll(mCoachingBean)) {
            Toast.makeText(mActivity, "请选择频次", Toast.LENGTH_SHORT).show();
            return;
        }
        if (monthsListattrCode1 == 0) {
            Toast.makeText(mActivity, "请选择签约时长", Toast.LENGTH_SHORT).show();
            return;
        }
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("mainDoctorAlias", doctorAlias);
        map.put("mainPatientCode", patientCode);
        map.put("mainUserName", patientName1);
        map.put("mainUserNameAlias", patientName1);
        map.put("gender", "1");
        map.put("age", patientAge.getText().toString());
        map.put("signDuration", monthsListattrCode1 + "");
        map.put("signUnit", "月");
        map.put("version", getdetailsBeans.getVersion());
        map.put("signCode", type);
        String totalPrice = totalprice.getText().toString();
        map.put("signPrice", totalPrice);
        map.put("signDurationUnit", "月");
        map.put("signStartTime", tvStartTime.getText().toString());
        java.util.List<OrderItemBean> uploadOrderItems = getUploadOrderItems(mDetectBeans);
        java.util.List<OrderItemBean> uploadOrderItems1 = getUploadOrderItems(mCoachingBean);
        List<OrderItemBean> orderItemBeans = new ArrayList<>();
        orderItemBeans.addAll(uploadOrderItems);
        orderItemBeans.addAll(uploadOrderItems1);
        map.put("orderDetailList", orderItemBeans);
        getProgressBar("请稍候...", "正在提交");
        new Thread() {
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/operUpdSignPatientDoctorOrder");
                    Log.e("tag", "修改提交" + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
                //  mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    //提交
    private void commit() {
        if (mAgree) {
            Toast.makeText(mContext, "请先同意用户签约协议", LENGTH_SHORT).show();
            return;
        }
        if (!CollectionUtils.isEmpty(mDetectBeans)) {
            if (dayListattrCode == 0) {
                Toast.makeText(mActivity, "请选择频率时长", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(tvStartTime.getText().toString())) {
            Toast.makeText(mActivity, "请选择签约开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isChoosedFrequencyAll(mCoachingBean)) {
            Toast.makeText(mActivity, "请选择频次", Toast.LENGTH_SHORT).show();
            return;
        }
        if (monthsListattrCode1 == 0) {
            Toast.makeText(mActivity, "请选择签约时长", Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("请稍候...", "正在提交");
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("mainDoctorAlias", doctorAlias);
        map.put("mainPatientCode", patientCode);
        map.put("mainUserName", patientName1);
        map.put("mainUserNameAlias", patientName1);
        map.put("gender", "1");
        map.put("age", patientAge.getText().toString());
        map.put("signDuration", monthsListattrCode1 + "");
        map.put("signUnit", "月");
        map.put("signDurationUnit", "月");
        String totalPrice = totalprice.getText().toString();
        map.put("signPrice", totalPrice);
        map.put("signStartTime", tvStartTime.getText().toString());
        java.util.List<OrderItemBean> uploadOrderItems = getUploadOrderItems(mDetectBeans);
        java.util.List<OrderItemBean> uploadOrderItems1 = getUploadOrderItems(mCoachingBean);
        List<OrderItemBean> orderItemBeans = new ArrayList<>();
        orderItemBeans.addAll(uploadOrderItems);
        orderItemBeans.addAll(uploadOrderItems1);
        map.put("orderDetailList", orderItemBeans);
        new Thread() {
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/operInsSignPatientDoctorOrder");
                    Log.e("tag", "订单提交" + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
                //   mHandler.sendEmptyMessage(1);
            }
        }.start();
    }


    /**
     * double 取整数
     *
     * @param totalPrice 价格
     * @return 整数
     */
    private int priceDoubleToInteger(String totalPrice) {
        int subPrice = 0;
        if (!TextUtils.isEmpty(totalPrice)) {
            double calculatePrice = Double.parseDouble(totalPrice);
            BigDecimal big = new BigDecimal(calculatePrice);
            int v = big.setScale(0, BigDecimal.ROUND_UP).intValue();
            subPrice = v;
        }
        return subPrice;
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = "";
                Bundle data = msg.getData();
                if (data != null) {
                    result = data.getString("result");
                }

                switch (msg.what) {
                    case 1:
                        cacerProgress();
                        if (result != null && !result.equals("")) {
                            netRetEntity = new Gson().fromJson(result, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                                String resJsonData = netRetEntity.getResJsonData();
                                if (!TextUtils.isEmpty(resJsonData)) {
                                    Restcommit restcommit = JSON.parseObject(resJsonData, Restcommit.class);
                                    signOrderCode = restcommit.getSignOrderCode();
                                    signNo = restcommit.getSignNo();
                                }
                                Log.e("TAG", "handleMessage: " + patientCode);
                                String monitorRate = "";
                                if (dayListattrCode != 0) {
                                    monitorRate = "1次/" + dayListattrCode + "天";
                                }
                                if (TextUtils.isEmpty(signOrderCode)) {
                                    OrderMessage orderMessage = new OrderMessage(name, doctorUrl, type, mDetectBeans.size() + "项", monitorRate, tvDuration.getText().toString(), totalprice.getText().toString(), singNO, "", "card", patientCode);
                                    EventBus.getDefault().post(orderMessage);
                                    finish();
                                } else {
                                    OrderMessage orderMessage = new OrderMessage(name, doctorUrl, signOrderCode, mDetectBeans.size() + "项", monitorRate, tvDuration.getText().toString(), totalprice.getText().toString(), signNo, "", "card", patientCode);
                                    EventBus.getDefault().post(orderMessage);
                                    finish();
                                }


                            } else {
                                Toast.makeText(mContext, "" + netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case 4:
                        if (result != null && !result.equals("")) {
                            NetRetEntity netRetEntity = JSON.parseObject(result, NetRetEntity.class);
                            int resCode = netRetEntity.getResCode();
                            if (resCode == 1) {
                                provideBasicsDomains = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDomain.class);
                            }

                            if (provideBasicsDomains != null) {
                                for (ProvideBasicsDomain provideBasicsDomain : provideBasicsDomains) {
                                    Integer baseCode = provideBasicsDomain.getBaseCode();
                                    if (baseCode == 900051) {
                                        dayList.add(provideBasicsDomain);
                                    } else if (baseCode == 900052) {
                                        secondaryList.add(provideBasicsDomain);
                                    } else if (baseCode == 900053) {
                                        monthList.add(provideBasicsDomain);
                                    } else if (baseCode == 900054) {
                                        minuteList.add(provideBasicsDomain);
                                    } else if (baseCode == 900055) {
                                        monthsList.add(provideBasicsDomain);
                                    }

                                }
                            }

                        } else {
                            Log.e("tag", "handleMessage: " + "请求失败");
                        }
                        break;
                    case 5:
                        cacerProgress();
                        if (result != null && !result.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(result, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                getdetailsBeans = JSON.parseObject(netRetEntity.getResJsonData(), GetdetailsBean.class);
                                setLayoutData();
                            } else {

                            }
                        }
                        break;
                    case 30:
                        if (result != null && !result.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(result, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                               provideDoctorPatientUserInfo = JSON.parseObject(netRetEntity.getResJsonData(),
                                        ProvideDoctorPatientUserInfo.class);
                                patientAge.setText( DateUtils.getAgeFromBirthTime(provideDoctorPatientUserInfo.getBirthday())+"");
                            } else {

                            }
                        }
                        break;

                }


            }
        };
    }

    /**
     * 添加监听
     */
    private void addListener() {
        linStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime();
            }
        });
        ivBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, DetectActivity.class).putExtra("detect", (Serializable) mDetectBeans), 1);
            }
        });
        linClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, CoachingActivity.class).putExtra("coaching", (Serializable) mCoachingBean), 2);
            }
        });


        linDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tvStartTime.getText().toString())) {
                    Toast.makeText(mActivity, "请选择开始时间", Toast.LENGTH_SHORT).show();
                } else {
                    Duration();
                }
            }
        });
    }

    //订单详情设置布局显示
    @SuppressLint("SetTextI18n")
    private void setLayoutData() {
        if(status.equals("1")){
            protocol_lin.setVisibility(View.GONE);
            btActivityMySelfSettingExitButton.setVisibility(View.GONE);
        }else if (status==null&&status.equals("")){

        }
        singNO=getdetailsBeans.getSignNo();
        dayListattrCode = Integer.parseInt(getdetailsBeans.getOrderDetailList().get(0).getRateUnitCode());
        monthsListattrCode1 = getdetailsBeans.getSignDuration();
        startTime = DateUtils.stampToDate(getdetailsBeans.getSignStartTime());
        patientName.setText(patientName1);
        day_tv.setText(getdetailsBeans.getOrderDetailList().get(0).getRate() + getdetailsBeans.getOrderDetailList().get(0).getRateUnitName());
        wzxxSc = (ScrollView) findViewById(R.id.wzxx_sc);
        //检测类型
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
//        mLinearLayoutManager.setOrientation(LinearLayout.VERTICAL);
//        rvqbzz.setLayoutManager(mLinearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        //rvqbzz.setHasFixedSize(true);
        //辅导类别
//        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(mContext);
//        mLinearLayoutManager2.setOrientation(LinearLayout.VERTICAL);
//        rvClass.setLayoutManager(mLinearLayoutManager2);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        //rvClass.setHasFixedSize(true);
        //名称
        patientName.setText(getdetailsBeans.getMainUserName());
        //性别
        patientSex = (TextView) findViewById(R.id.patient_sex);
        int gender = getdetailsBeans.getGender();
        if (gender == 1) {
            patientSex.setText("男");
        } else if (gender == 2) {
            patientSex.setText("女");
        } else {
            patientSex.setText("未知");
        }
        //年龄
        patientAge.setText(getdetailsBeans.getAge() + "");

        List<GetdetailsBean.OrderDetailListBean> orderDetailList = getdetailsBeans.getOrderDetailList();

        for (GetdetailsBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
            String configDetailTypeCode = orderDetailListBean.getConfigDetailTypeCode();
            if (configDetailTypeCode.equals("10")) {
                DetectitemBeans.add(orderDetailListBean);
            } else if (configDetailTypeCode.equals("20")) {
                CoachingitemBeans.add(orderDetailListBean);
            }
        }
        if (DetectitemBeans != null && DetectitemBeans.size() > 0) {
            linTime.setVisibility(View.VISIBLE);
        } else {
            linTime.setVisibility(View.GONE);
        }

        mDetectBeans = convertData(DetectitemBeans);
        rv_detectAdapter.setDate(mDetectBeans);
        rv_detectAdapter.notifyDataSetChanged();
        mCoachingBean = convertData(CoachingitemBeans);
        rvCoachingAdapter.setDate(mCoachingBean);
        rvCoachingAdapter.notifyDataSetChanged();

        //开始时间
        tvStartTime.setText(DateUtils.stampToDate(getdetailsBeans.getSignStartTime()));
        monthsListattrCode1 = getdetailsBeans.getSignDuration();
        //签约时长
        //   int signDuration = getdetailsBeans.getSignDuration();
        Log.e("TAG", "setLayoutDate:  id " + getdetailsBeans.getSignStatus());
        tvDuration.setText(getdetailsBeans.getSignDuration() + "个" + getdetailsBeans.getSignDurationUnit());
        //总价
        int totalPrice = priceDoubleToInteger(
                getdetailsBeans.getSignPrice() + "");
        totalprice.setText(totalPrice + "");
        patientName1 = getdetailsBeans.getMainUserName();
        patientAlias = getdetailsBeans.getMainUserNameAlias();

    }

    private List<DetectBean> convertData(List<GetdetailsBean.OrderDetailListBean> orderDetailList) {
        List<DetectBean> detectBeans = new ArrayList<>();
        for (GetdetailsBean.OrderDetailListBean orderDetailListBean : orderDetailList) {
            DetectBean detectBean = new DetectBean();
            detectBean.setConfigDetailCode(orderDetailListBean.getMainConfigDetailCode());
            detectBean.setConfigDetailId(orderDetailListBean.getSignOrderConfigDetailId());
            detectBean.setConfigDetailName(orderDetailListBean.getMainConfigDetailName());
            detectBean.setConfigDetailTypeCode(orderDetailListBean.getConfigDetailTypeCode());
            detectBean.setConfigDetailTypeName(orderDetailListBean.getConfigDetailTypeName());
            detectBean.setPrice(orderDetailListBean.getTotlePrice());
            if(!TextUtils.isEmpty(orderDetailListBean.getDurationUnitCode())){
                int i = Integer.valueOf(orderDetailListBean.getDurationUnitCode()).intValue();
                Log.e("TAG", "convertData:  分钟数"+i  );
                detectBean.setMinute(i);
            }
            detectBean.setFrequency(orderDetailListBean.getValue());
            detectBean.setMonths(orderDetailListBean.getRate());
            detectBean.setValue(orderDetailListBean.getValue());
            detectBeans.add(detectBean);
        }
        return detectBeans;
    }

    //获取订单详情
    private void Getdetails() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("signOrderCode", type);
        new Thread() {
            public void run() {
                String mNetRetStr = "";
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/searchSignPatientDoctorOrder");
                    Log.e("TAG", "run:   订单详情"+mNetRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                Message message = new Message();
                message.what = 5;
                Bundle bundle = new Bundle();
                bundle.putString("result", mNetRetStr);
                message.setData(bundle);
                mHandler.sendMessage(message);
                //  mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    //辅导类别时长
    private void time(final int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                secondaryListattrCode = secondaryList.get(options1).getAttrCode();
                secondaryListattrName = secondaryList.get(options1).getAttrName();
                monthListattrCode = monthList.get(options2).getAttrCode();
                monthListattrName = monthList.get(options2).getAttrName();

//                double price = mCoachingBean.get(pos).getPrice();
//                mCoachingBean.get(pos).setPrice(price*videosecondaryListattrCode);
//                mCoachingBean.get(pos).setValue(videosecondaryListattrCode);
//                mCoachingBean.get(pos).setMinute(videominuteListattrCode);
                mCoachingBean.get(pos).setValue(secondaryListattrCode);
                mCoachingBean.get(pos).setFrequency(secondaryListattrCode);
                mCoachingBean.get(pos).setMonths(monthListattrCode);

                rvCoachingAdapter.notifyDataSetChanged();
                setTotalprice(mDetectBeans,mCoachingBean);
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(secondaryList), getDayStrList(monthList), null);
        optionPickUnit.show();

    }

    @Override
    public void onClick(View v) {

    }

    private List<OrderItemBean> getUploadOrderItems(List<DetectBean> detectBeans) {
        List<OrderItemBean> itemBeans = new ArrayList<>();
        for (DetectBean detectBean : detectBeans) {
            OrderItemBean orderItemBean = new OrderItemBean();
            orderItemBean.setTotlePrice(detectBean.getPrice());
            orderItemBean.setConfigDetailTypeCode(detectBean.getConfigDetailTypeCode());
            orderItemBean.setConfigDetailTypeName(detectBean.getConfigDetailTypeName());
            orderItemBean.setMainConfigDetailName(detectBean.getConfigDetailName());
            String configDetailTypeName = detectBean.getConfigDetailTypeName();
            String configDetailTypeCode = detectBean.getConfigDetailTypeCode();

            if (!TextUtils.isEmpty(configDetailTypeName) && configDetailTypeName.equals("监测类型")) {
                orderItemBean.setDuration(0);
                orderItemBean.setDurationUnitCode("");
            }orderItemBean.setDurationUnitName("");
            orderItemBean.setMainConfigDetailCode(detectBean.getConfigDetailCode());
            orderItemBean.setValue(detectBean.getValue());
            if (configDetailTypeCode.equals("10")) {
                orderItemBean.setRate(dayListattrCode);
            } else {
                orderItemBean.setRate(detectBean.getMonths());
            }
            if(configDetailTypeCode.equals("10")){
                orderItemBean.setRateUnitName("天");
                orderItemBean.setRateUnitCode(dayListattrCode + "");

            }else if(configDetailTypeCode.equals("20")){
                orderItemBean.setDurationUnitCode(detectBean.getMinute()+"");
                orderItemBean.setDurationUnitName("分钟");
                orderItemBean.setRateUnitName("月");
                orderItemBean.setRateUnitCode(detectBean.getMonths()+"");
                orderItemBean.setDuration(detectBean.getMinute());
            }


            itemBeans.add(orderItemBean);
        }
        return itemBeans;
    }

    private void startTime() {
        timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTime = getDate(date);
                tvStartTime.setText(startTime);
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").build();
        timePickerView.show();
    }

    private String getDate(Date data) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String string = simpleDateFormat.format(data);
        return string;
    }

    private String getEndTime(int dayCode) {
        Calendar c = Calendar.getInstance();//获得一个bai日历的实du例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = sdf.parse(startTime);//初始日期
        } catch (Exception e) {

        }
        c.setTime(dates);//设置日历时间zhi
        c.add(Calendar.MONTH, dayCode);//在日历的月份上增加6个月
        return sdf.format(c.getTime());
    }

    private long calculateDays(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        day1 = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day1 = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day1 + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");

            if (day1 >= 1) {
                return day1;
            } else {
                if (day1 == 0) {
                    return 1;
                } else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //检测类型
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mDetectBeans = (ArrayList<DetectBean>) data.getSerializableExtra("detect");
            if (mDetectBeans != null) {
                linTime.setVisibility(View.VISIBLE);
                rv_detectAdapter.setDate(mDetectBeans);
                rv_detectAdapter.notifyDataSetChanged();
            }

        }
        //辅导类别
        if (requestCode == 2 && resultCode == RESULT_OK) {
            mCoachingBean = (ArrayList<DetectBean>) data.getSerializableExtra("coaching");
            if (mCoachingBean == null) {
                totalprice.setText("");
            }
            if (mCoachingBean != null) {
                rvCoachingAdapter.setDate(mCoachingBean);
                rvCoachingAdapter.notifyDataSetChanged();
            }
        }

        setTotalprice(mDetectBeans, mCoachingBean);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setTotalprice(List<DetectBean> monitorDetectBeans, List<DetectBean> coatchDetectBeans) {
        List<DetectBean> detectBeans = new ArrayList<>();
        if (!CollectionUtils.isEmpty(monitorDetectBeans)) {
            calculateMonitorPrice(monitorDetectBeans);
            detectBeans.addAll(monitorDetectBeans);
        }
        if (!CollectionUtils.isEmpty(coatchDetectBeans)) {
            calculateCoatchPrice(coatchDetectBeans);
            detectBeans.addAll(coatchDetectBeans);
        }
        double totalPrice = 0;
        for (DetectBean detectBean : detectBeans) {
            totalPrice = totalPrice + detectBean.getTotalPrice();
        }
        int price = priceDoubleToInteger(totalPrice + "");
        totalprice.setText(String.format("%d", price));
    }

    /**
     * 计算监测类价格
     *
     * @param monitorDetectBeans 监测类集合
     */
    private void calculateMonitorPrice(List<DetectBean> monitorDetectBeans) {
        if (dayListattrCode != 0 && monthsListattrCode1 != 0) {
            for (DetectBean monitorDetectBean : monitorDetectBeans) {
                double price = monitorDetectBean.getPrice();
                long days = calculateDays(startTime, getEndTime(monthsListattrCode1), "yyyy-MM-dd");
                double calculatePrice = days * price / dayListattrCode;
                BigDecimal big = new BigDecimal(calculatePrice);
                double v = big.setScale(8, BigDecimal.ROUND_UP).doubleValue();
                //monitorDetectBean.setPrice(vv);
                monitorDetectBean.setTotalPrice(v);
            }
        }
    }

    /**
     * 计算辅导类价格
     *
     * @param coatchDetectBeans 辅导类集合
     */
    private void calculateCoatchPrice(List<DetectBean> coatchDetectBeans) {
        if (monthsListattrCode1 != 0) {
            for (DetectBean coatchDetectBean : coatchDetectBeans) {
                int frequency = coatchDetectBean.getFrequency();
                int months = coatchDetectBean.getMonths();
                double price = coatchDetectBean.getPrice();
                if (frequency != 0 && months != 0) {
                    double calculatePrice = monthsListattrCode1 * frequency * price / months ;
                    BigDecimal big = new BigDecimal(calculatePrice);
                    double v = big.setScale(8, BigDecimal.ROUND_UP).doubleValue();
                    coatchDetectBean.setTotalPrice(v);
                }

            }
        }

    }

    /**
     * 是否填写频率
     *
     * @param coatchDetectBeans 辅导类类别
     * @return true or false
     */
    private boolean isChoosedFrequencyAll(List<DetectBean> coatchDetectBeans) {
        int complateTotal = 0;
        if (!CollectionUtils.isEmpty(coatchDetectBeans)) {
            for (DetectBean coatchDetectBean : coatchDetectBeans) {

                int frequency = coatchDetectBean.getFrequency();
                int months = coatchDetectBean.getMonths();
                if (frequency != 0 && months != 0) {
                    complateTotal++;
                }
            }
        }
        return coatchDetectBeans.size() == complateTotal;
    }

    /**
     * 获取进度条
     * 获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mActivity);
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
