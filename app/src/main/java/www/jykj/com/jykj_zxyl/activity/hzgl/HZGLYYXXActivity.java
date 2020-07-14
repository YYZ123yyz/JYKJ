package www.jykj.com.jykj_zxyl.activity.hzgl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.HZGL_YYXX_RecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import yyz_exploit.Utils.HttpUtils;
import yyz_exploit.bean.HelpBean;
import yyz_exploit.bean.YyBean;


/**
 * 患者管理（用药信息）
 */
public class HZGLYYXXActivity extends AppCompatActivity implements  View.OnClickListener{

    private Context mContext;
    private HZGLYYXXActivity mActivity;
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private HZGL_YYXX_RecycleAdapter mHZGL_YYXX_RecycleAdapter;       //适配器
    private List<YyBean.PatientConditionTakingRecordListBean> mHZEntyties = new ArrayList<>();            //所有数据
    private List<HZIfno> mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private LinearLayout llBack;
    private ProvideViewPatientLablePunchClockState mData;
    private int mRowNum = 5;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvSex;
    private boolean mLoadDate = true;                    //是否可以加载数据
    private List<HZIfno> hzIfnos;
    private List<YyBean> list;
    private LinearLayout item_fragmentHZGL_hzSex;
    private TextView tv_sex;
    private TextView rlStartTime;

    private String startTime,endTime;
    private TextView tvEndTime;
    private TextView tvStartTime;
    private RelativeLayout rlStartTime1;
    private RelativeLayout rlEndTime;
    private TextView tvComfirm;
    private LinearLayout llBloodDayAverage,llBloodWeekAverage,llBloodMonthAverage;
    private TextView day_tv,day_tv1,day_tv2;
    private String searchDateType="";
    private String  searchDateStart="";
    private String      searchDateEnd="";
    private LinearLayout lin_date;
    private boolean data;
    private LinearLayout img_gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthzgl_yyxx);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);

        initLayout();
        getResData( searchDateType, searchDateStart, searchDateEnd);
        initHandler();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        lin_date = findViewById(R.id.lin_date);
        img_gson = findViewById(R.id.img_gson);
        img_gson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data){
                    lin_date.setVisibility(View.VISIBLE );
                }else {
                    lin_date.setVisibility(View.GONE );

                }
                data = !data;

            }
        });
        if (getIntent() != null) {
            mData = (ProvideViewPatientLablePunchClockState) getIntent().getSerializableExtra("patientLable");
        }

        rlStartTime1 = findViewById(R.id.rl_start_time);
        tvStartTime = findViewById(R.id.tv_start_time);
        rlEndTime = findViewById(R.id.rl_end_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvComfirm = findViewById(R.id.tv_comfirm);
        tvComfirm.setOnClickListener(this);
        rlStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateUtils.showDatePickerDialog(HZGLYYXXActivity.this, false, "请选择月日", 2020, 1, 1, new DateUtils.OnDatePickerListener() {
                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        startTime = year+"-"+month+"-"+dayOfMonth;
                        tvStartTime.setText(startTime);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        rlEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateUtils.showDatePickerDialog(HZGLYYXXActivity.this, false, "请选择月日", 2020, 1, 1, new DateUtils.OnDatePickerListener() {
                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        endTime = year+"-"+month+"-"+dayOfMonth;
                        tvEndTime.setText(endTime);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });


        llBloodDayAverage = (LinearLayout) findViewById(R.id.ll_blood_day_average);
        llBloodWeekAverage = (LinearLayout) findViewById(R.id.ll_blood_week_average);
        llBloodMonthAverage = (LinearLayout) findViewById(R.id.ll_blood_month_average);
        llBloodDayAverage.setOnClickListener(this);
        llBloodWeekAverage.setOnClickListener(this);
        llBloodMonthAverage.setOnClickListener(this);

        day_tv = findViewById(R.id.day_tv);
        day_tv1 = findViewById(R.id.day_tv1);
        day_tv2 = findViewById(R.id.day_tv2);



        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityFragmentHZGL_YYXX);
        llBack = (LinearLayout) this.findViewById(R.id.ll_back);
        tvAge = (TextView) this.findViewById(R.id.tv_age);
        tvName = (TextView) this.findViewById(R.id.tv_name);
      //  tvSex = (TextView) this.findViewById(R.id.tv_sex);
        tv_sex = findViewById(R.id.tv_sex);
        llBack.setOnClickListener(new ButtonClick());
        tvName.setText(mData.getUserName());
        item_fragmentHZGL_hzSex = findViewById(R.id.item_fragmentHZGL_hzSex);
        if(mData.getGender()==1){
            Drawable drawable=getDrawable(R.mipmap.man);
            item_fragmentHZGL_hzSex.setBackground(drawable);
        }else{
            Drawable drawable=getDrawable(R.mipmap.women);
            item_fragmentHZGL_hzSex.setBackground(drawable);
        }
        tv_sex.setText(DateUtils.getAgeFromBirthDate(mData.getBirthday())+"");
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGL_YYXX_RecycleAdapter = new HZGL_YYXX_RecycleAdapter(mHZEntyties,mContext);
        mRecycleView.setAdapter(mHZGL_YYXX_RecycleAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_blood_day_average:
                day_tv.setTextColor(this.getResources().getColor(R.color.groabColor));
                day_tv1.setTextColor(this.getResources().getColor(R.color.black));
                day_tv2.setTextColor(this.getResources().getColor(R.color.black));
                mHZEntyties.clear();
                searchDateType="1";
                getResData(searchDateType,"","");
                mHZGL_YYXX_RecycleAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_blood_week_average:
                day_tv.setTextColor(this.getResources().getColor(R.color.black));
                day_tv1.setTextColor(this.getResources().getColor(R.color.groabColor));
                day_tv2.setTextColor(this.getResources().getColor(R.color.black));
                mHZEntyties.clear();
                searchDateType="3";
                getResData(searchDateType,"","");
                mHZGL_YYXX_RecycleAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_blood_month_average:
                day_tv.setTextColor(this.getResources().getColor(R.color.black));
                day_tv1.setTextColor(this.getResources().getColor(R.color.black));
                day_tv2.setTextColor(this.getResources().getColor(R.color.groabColor));
                mHZEntyties.clear();
                searchDateType="4";
                getResData(searchDateType,"","");
                break;
            case R.id.tv_comfirm:
                mHZEntyties.clear();
                getResData("",startTime,endTime);
                mHZGL_YYXX_RecycleAdapter.notifyDataSetChanged();
                break;
        }
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;
            }
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mHZGL_YYXX_RecycleAdapter.setDate(mHZEntyties);
                        mHZGL_YYXX_RecycleAdapter.notifyDataSetChanged();
                        break;

                }
            }
        };
    }



    private void getResData(String searchDateType,String searchDateStart,String searchDateEnd) {

        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    provideViewPatientInfo.setSearchTakingMedicine("-1");
                    provideViewPatientInfo.setSearchDateType(searchDateType);
                    provideViewPatientInfo.setSearchDateStart(searchDateStart);
                    provideViewPatientInfo.setSearchDateEnd(searchDateEnd);
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResTakingMedicineRecord");
                    Log.e("tag", "患者 "+mNetRetStr );
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    list = JSON.parseArray(netRetEntity.getResJsonData(), YyBean.class);
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        mHZEntyties.addAll(list.get(i).getPatientConditionTakingRecordList());
                    }



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

}
