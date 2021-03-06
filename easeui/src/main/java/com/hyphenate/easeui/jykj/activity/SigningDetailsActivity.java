package com.hyphenate.easeui.jykj.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.jykj.adapter.Item_Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.adapter.Item_Rv_detectAdapter;
import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.adapter.Rv_detectAdapter;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.jykj.bean.DetectBean;
import com.hyphenate.easeui.jykj.bean.GetdetailsBean;
import com.hyphenate.easeui.jykj.bean.OrderItemBean;
import com.hyphenate.easeui.jykj.bean.ProvideBasicsDomain;
import com.hyphenate.easeui.jykj.bean.Restcommit;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SigningDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private SigningDetailsActivity mActivity;
    private List<DetectBean> mDetectBeans = new ArrayList<>();            //检测类型
    private List<CoachingBean> mCoachingBean = new ArrayList<>();            //检测类型
    private List<OrderItemBean> mOrderItemBean = new ArrayList<>();
    private Rv_detectAdapter rv_detectAdapter;
    private Rv_CoachingAdapter rvCoachingAdapter;
    private TimePickerView timePickerView;
    private String time;
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
    private String mNetRetStr;
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
    private double totalprice1 = 0;
    private ImageView iv_activityLogin_agreeImg;
    private boolean mAgree = true;                     //是否同意协议，默认同意
    private Integer dayListattrCode;
    private Integer secondaryListattrCode;
    private Integer monthListattrCode;
    private Integer videominuteListattrCode;
    private Integer videosecondaryListattrCode;
    private Integer videomonthListattrCode;
    private String monthsListattrName;
    private Integer monthsListattrCode;
    private int ageFromBirthTime;
    private Integer monthsListattrCode1;
    private String signOrderCode;
    private GetdetailsBean getdetailsBeans;
    private List<GetdetailsBean.OrderDetailListBean> DetectitemBeans = new ArrayList<>();
    private List<GetdetailsBean.OrderDetailListBean> CoachingitemBeans = new ArrayList<>();
    private Item_Rv_detectAdapter item_rv_detectAdapter;
    private Item_Rv_CoachingAdapter item_rv_coachingAdapter;
    private RecyclerView rvClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_details);
        mContext = this;
        mActivity = this;
        sharedPreferences = getSharedPreferences("sp", Activity.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
        code = sharedPreferences.getString("code", "");
        doctorAlias = sharedPreferences.getString("mainDoctorAlias", "");


        Intent intent = getIntent();
        patientAlias = intent.getStringExtra("patientAlias");
        Log.e("TAG", "onCreate: "+patientAlias );
        patientCode = intent.getStringExtra("patientCode");
        patientName1 = intent.getStringExtra("patientName");
        patientAge1 = intent.getStringExtra("patientAge");
        patientSex1 = intent.getStringExtra("patientSex");

        initView();
        //    Getdetails();
        duration();
        initHandler();
    }

    private void duration() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("baseCode", "900051^900052^900053^900054^900055");
        new Thread() {
            public void run() {
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

                mHandler.sendEmptyMessage(4);
            }
        }.start();
    }

    private void initView() {
        //图标
        iv_activityLogin_agreeImg = (ImageView) findViewById(R.id.iv_activityLogin_agreeImg);
        iv_activityLogin_agreeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgree = !mAgree;
                setAgreeImg();
            }
        });
        //总价
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
        if (TextUtils.isEmpty(patientAge1)) {

        } else {
            ageFromBirthTime = DateUtils.getAgeFromBirthTime(patientAge1);
        }
        patientAge.setText(ageFromBirthTime + "");
        patientSex = (TextView) findViewById(R.id.patient_sex);
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
        linDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, DetectActivity.class), 1);
            }
        });
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
        linClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, CoachingActivity.class), 2);
            }
        });
        rvClass = (RecyclerView) findViewById(R.id.rv_class);
        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        linStartTime = (LinearLayout) findViewById(R.id.lin_start_time);
        linStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime();
            }
        });
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        linDuration = (LinearLayout) findViewById(R.id.lin_duration);
        linDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Duration();
            }
        });
        btActivityMySelfSettingExitButton = (Button) findViewById(R.id.bt_activityMySelfSetting_exitButton);
        //提交
        btActivityMySelfSettingExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
                //      ModificationSubmission();
            }
        });
        wzxxSc = (ScrollView) findViewById(R.id.wzxx_sc);

        //检测类型
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rvqbzz.setLayoutManager(mLinearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvqbzz.setHasFixedSize(true);
//        //创建并设置Adapter
        rv_detectAdapter = new Rv_detectAdapter(mDetectBeans, mContext, mActivity);
        rvqbzz.setAdapter(rv_detectAdapter);

        //辅导类别
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(mContext);
        mLinearLayoutManager2.setOrientation(LinearLayout.VERTICAL);
        rvClass.setLayoutManager(mLinearLayoutManager2);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvClass.setHasFixedSize(true);
//        //创建并设置Adapter
        rvCoachingAdapter = new Rv_CoachingAdapter(mCoachingBean, mContext, mActivity);
        rvClass.setAdapter(rvCoachingAdapter);
        rvCoachingAdapter.setOnItemCoachingClickListener(new Rv_CoachingAdapter.OnItemCoachingClickListener() {
            @Override
            public void onClick(int position) {
                time();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        rvCoachingAdapter.setOnItemCoachingLinClickListener(new Rv_CoachingAdapter.OnItemCoachingLinClickListener() {
            @Override
            public void onClick(int position) {
                Videofrequency();
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
            iv_activityLogin_agreeImg.setImageResource(R.mipmap.choice);
        else
            iv_activityLogin_agreeImg.setImageResource(R.mipmap.nochoice);
    }


    //签约时长
    private void Duration() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvDuration.setText(monthsList.get(options1).getAttrName());
                monthsListattrName = monthsList.get(options1).getAttrName();
                monthsListattrCode1 = monthsList.get(options1).getAttrCode();
            }
        })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(monthsList), null, null);
        optionPickUnit.show();
        Log.e("tag", "Duration: " + "走了");
    }

    //音视频弹框
    private void Videofrequency() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                videominuteListattrCode = minuteList.get(options1).getAttrCode();
                videosecondaryListattrCode = secondaryList.get(options2).getAttrCode();
                videomonthListattrCode = monthList.get(options3).getAttrCode();
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
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("signCode", "3965856941c14254a15abccf3f1925c2f");
        map.put("version", getdetailsBeans.getVersion() + "");
        map.put("signDuration", monthsListattrCode1 + "");
        map.put("signUnit", "月");
        map.put("signPrice", totalprice1);
        if (TextUtils.isEmpty(tvStartTime.getText().toString())) {
            Toast.makeText(mActivity, "请选择签约开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("signStartTime", tvStartTime.getText().toString());
        map.put("orderDetailList", getUploadOrderItems(mDetectBeans));
        new Thread() {
            public void run() {
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

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    //提交
    private void commit() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("mainDoctorAlias", doctorAlias);
        map.put("mainPatientCode", patientCode);
        map.put("mainUserName", patientName1);
        map.put("mainUserNameAlias", "patientAlias");
        map.put("gender", "1");
        map.put("age", ageFromBirthTime + "");

        map.put("signDuration", monthsListattrCode1 + "");
        map.put("signUnit", "月");
        map.put("signDurationUnit", monthsListattrName + "");
        map.put("signPrice", totalprice1);
        if (TextUtils.isEmpty(tvStartTime.getText().toString())) {
            Toast.makeText(mActivity, "请选择签约开始时间", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("signStartTime", tvStartTime.getText().toString());
        map.put("orderDetailList", getUploadOrderItems(mDetectBeans));
        new Thread() {
            public void run() {
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

                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (TextUtils.isEmpty(mNetRetStr)) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                                List<Restcommit> restcommits = JSON.parseArray(netRetEntity.getResJsonData(), Restcommit.class);
                                System.out.println(restcommits);
                                signOrderCode = restcommits.get(0).getSignOrderCode();

                            }
                        }
                        break;
                    case 4:
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            provideBasicsDomains = JSON.parseArray(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), ProvideBasicsDomain.class);

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
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                            if (netRetEntity.getResCode() == 1) {
                                getdetailsBeans = JSON.parseObject(netRetEntity.getResJsonData(), GetdetailsBean.class);
                                setLayoutDate();
                            }
                        }
                        break;
                }


            }
        };
    }

    //设置布局显示
    private void setLayoutDate() {
        //名称
        patientName.setText(getdetailsBeans.getMainUserName());
        //性别
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
        //监测类型
        item_rv_detectAdapter = new Item_Rv_detectAdapter(DetectitemBeans, mContext, mActivity);
        item_rv_detectAdapter.setDate(DetectitemBeans);
        rvqbzz.setAdapter(item_rv_detectAdapter);
        item_rv_detectAdapter.notifyDataSetChanged();
        //辅导类别
        item_rv_coachingAdapter = new Item_Rv_CoachingAdapter(CoachingitemBeans, mContext, mActivity);
        item_rv_coachingAdapter.setDate(CoachingitemBeans);
        rvClass.setAdapter(rvCoachingAdapter);
        item_rv_coachingAdapter.notifyDataSetChanged();
        item_rv_coachingAdapter.setOnItemCoachingClickListener(new Item_Rv_CoachingAdapter.OnItemCoachingClickListener() {
            @Override
            public void onClick(int position) {
                time();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        rvCoachingAdapter.setOnItemCoachingLinClickListener(new Rv_CoachingAdapter.OnItemCoachingLinClickListener() {
            @Override
            public void onClick(int position) {
                Videofrequency();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //开始时间
        tvStartTime.setText(DateUtils.getDateToString(getdetailsBeans.getSignStartTime()));
        //签约时长
        int signDuration = getdetailsBeans.getSignDuration();
        Log.e("TAG", "setLayoutDate:  id " + getdetailsBeans.getSignStatus());
        for (ProvideBasicsDomain provideBasicsDomain : monthsList) {
            if (provideBasicsDomain.getAttrCode() == signDuration) {
                tvDuration.setText(provideBasicsDomain.getAttrName());
                Log.e("TAG", "setLayoutDate:   签约时间 " + provideBasicsDomain.getAttrName().toString());
            }
        }
        //总价
        totalprice.setText(getdetailsBeans.getSignPrice() + "");
    }


    //获取订单详情
    private void Getdetails() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("loginDoctorPosition", "108.93425^34.23053");
        map.put("operDoctorCode", code);
        map.put("operDoctorName", name);
        map.put("signOrderCode", "3965856941c14254a15abccf3f1925c2");

        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "doctorSignControlle/searchSignPatientDoctorOrder");
                    Log.e("tag", "获取详情 " + mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    //辅导类别时长
    private void time() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                secondaryListattrCode = secondaryList.get(options1).getAttrCode();
                monthListattrCode = monthList.get(options2).getAttrCode();

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
            orderItemBean.setTotlePrice((int) detectBean.getPrice());
            orderItemBean.setConfigDetailTypeCode(detectBean.getConfigDetailTypeCode());
            orderItemBean.setConfigDetailTypeName(detectBean.getConfigDetailTypeName());
            orderItemBean.setMainConfigDetailName(detectBean.getConfigDetailName());
            if (detectBean.getConfigDetailTypeName().equals("监测类型")) {
                orderItemBean.setDuration(0);
                orderItemBean.setDurationUnitCode("");
                orderItemBean.setDurationUnitName("");
            }
            if (detectBean.getConfigDetailName().equals("图文")) {
                orderItemBean.setDuration(0);
                orderItemBean.setDurationUnitCode("");
                orderItemBean.setDurationUnitName("");
            }
            orderItemBean.setDuration(1);
            orderItemBean.setMainConfigDetailCode(detectBean.getConfigDetailCode());
            orderItemBean.setValue(0);
            orderItemBean.setRate(dayListattrCode);
            orderItemBean.setDurationUnitName("分钟");
            orderItemBean.setRateUnitName("天");
            orderItemBean.setRateUnitCode(dayListattrCode + "");
            orderItemBean.setDurationUnitCode(videominuteListattrCode + "");
            itemBeans.add(orderItemBean);
        }
        return itemBeans;
    }

    private void startTime() {
        timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time = getDate(date);
                tvStartTime.setText(time);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //检测类型
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mDetectBeans = (ArrayList<DetectBean>) data.getSerializableExtra("detect");
            if (mDetectBeans != null) {
                rv_detectAdapter.setDate(mDetectBeans);
                rv_detectAdapter.notifyDataSetChanged();
            }

        }
        double a;
        //辅导类别
        if (requestCode == 2 && resultCode == RESULT_OK) {
            mCoachingBean = (ArrayList<CoachingBean>) data.getSerializableExtra("coaching");
            if (mCoachingBean != null) {
                for (int i = 0; i < mCoachingBean.size(); i++) {
                    double price = mCoachingBean.get(i).getPrice();
                    totalprice1 = price + mCoachingBean.get(i).getPrice();
                }
                Log.e("tag", "onActivityResult:  总价" + totalprice1);
                if (totalprice1 == 0) {
                    totalprice.setText("");
                } else {
                    totalprice.setText(totalprice1 + "");
                }
                rvCoachingAdapter.setDate(mCoachingBean);
                rvCoachingAdapter.notifyDataSetChanged();
            }

        }


    }

}
