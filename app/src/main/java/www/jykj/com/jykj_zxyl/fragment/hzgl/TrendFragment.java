package www.jykj.com.jykj_zxyl.fragment.hzgl;



import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

import org.jsoup.helper.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import entity.patientInfo.BloodTrendInfo;
import entity.patientInfo.ProvidePatientConditionBloodPressureGroup;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.ChartView;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.DialogUtil;
import yyz_exploit.bean.ProvidePatientConditionBlood;

/**
 * 血压趋势图
 */
    public class TrendFragment extends Fragment implements View.OnClickListener {
    private int mRowNum = 100;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private ProvideViewPatientLablePunchClockState mData;
    private List<String> groupList=new ArrayList<>();
    private LineChart mLineChart;
    private LinearLayout llBloodDayAverage, llBloodWeekAverage, llBloodMonthAverage;
    private DialogUtil dialogUtil;
    private String searchDateType;//当天(1:当天;2:近3天;3:近7天;4:近30天;5:近3个月;6:近6个月;)
    private String startTime,endTime;

    float zmienna = 123;
    private float[] yData = {zmienna, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private TextView day_tv,day_tv1,day_tv2;
    private List<ProvidePatientConditionBlood> list=new ArrayList<>();
    private LinearLayout lin_date;
    private boolean data;
    private LinearLayout img_gson;
    private ProvidePatientConditionBlood providePatientConditionBlood;
    private List<String> dayAvgHeartRateNumArray;
    private List<String> dayAvgHighPressureNumArray;
    private List<String> dayAvgLowPressureNumArray;
    private List<String> groupRecordDateArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trend, container, false);
        mApp = (JYKJApplication) getActivity().getApplication();
        if (isAdded()) {
            mData = (ProvideViewPatientLablePunchClockState) getArguments().getSerializable("patientLable");
        }
        initData(v);
        initHandler();
        return v;
    }

    private void initData(View v) {
        lin_date = v.findViewById(R.id.lin_date);
        img_gson = v.findViewById(R.id.img_gson);
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
        day_tv = v.findViewById(R.id.day_tv);
        day_tv.setTextColor(this.getResources().getColor(R.color.groabColor));
        day_tv1 = v.findViewById(R.id.day_tv1);
        day_tv2 = v.findViewById(R.id.day_tv2);

        mLineChart = (LineChart) v.findViewById(R.id.line_chart);
        mLineChart.setNoDataText("暂无数据");
        llBloodDayAverage = (LinearLayout) v.findViewById(R.id.ll_blood_day_average);
        llBloodWeekAverage = (LinearLayout) v.findViewById(R.id.ll_blood_week_average);
        llBloodMonthAverage = (LinearLayout) v.findViewById(R.id.ll_blood_month_average);

        TextView tvComfirm = v.findViewById(R.id.tv_comfirm);
        RelativeLayout rlStartTime = v.findViewById(R.id.rl_start_time);
        TextView tvStartTime = v.findViewById(R.id.tv_start_time);
        RelativeLayout rlEndTime = v.findViewById(R.id.rl_end_time);
        TextView tvEndTime = v.findViewById(R.id.tv_end_time);
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("",startTime,endTime);
            }
        });

        rlStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateUtils.showDatePickerDialog(getActivity(), false, "请选择月日", 2020, 1, 1, new DateUtils.OnDatePickerListener() {
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
                DateUtils.showDatePickerDialog(getActivity(), false, "请选择月日", 2020, 1, 1, new DateUtils.OnDatePickerListener() {
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

        llBloodDayAverage.setOnClickListener(this);
        llBloodWeekAverage.setOnClickListener(this);
        llBloodMonthAverage.setOnClickListener(this);
      //  getData("","","");

    }


    private void getData(String searchDateType,String searchDateStart,String searchDateEnd) {
        if(dialogUtil==null){
            dialogUtil = new DialogUtil(getActivity());
        }else{
            dialogUtil.show();
        }
        new Thread() {
            public void run() {
                try {
                    BloodTrendInfo provideViewPatientInfo = new BloodTrendInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    provideViewPatientInfo.setSearchDateType(searchDateType);
                    provideViewPatientInfo.setSearchDateStart(searchDateStart);
                    provideViewPatientInfo.setSearchDateEnd(searchDateEnd);

                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResBloodPressureCycleDataNew");
                    Log.e("mNetRetStr",mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        Toast.makeText(getActivity(),"获取失败",Toast.LENGTH_SHORT);
                    }else{
                        Gson gson = new Gson();
                        providePatientConditionBlood = gson.fromJson(netRetEntity.getResJsonData(), ProvidePatientConditionBlood.class);
                        dayAvgHeartRateNumArray = providePatientConditionBlood.getDayAvgHeartRateNumArray();
                        dayAvgHighPressureNumArray = providePatientConditionBlood.getDayAvgHighPressureNumArray();
                        dayAvgLowPressureNumArray = providePatientConditionBlood.getDayAvgLowPressureNumArray();
                        groupRecordDateArray = providePatientConditionBlood.getGroupRecordDateArray();

                        groupList.addAll(dayAvgHeartRateNumArray);
                        groupList.addAll(dayAvgHighPressureNumArray);
                        groupList.addAll(dayAvgLowPressureNumArray);
                        groupList.addAll(groupRecordDateArray);

                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    if(dialogUtil!=null){
                        dialogUtil.dismiss();
                    }
                    e.printStackTrace();

                }
                if(dialogUtil!=null){
                    dialogUtil.dismiss();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setData();
                        break;

                }
            }
        };
    }

    private void setData() {
        initLineChart();
        setLineChart();
    }
    LineDataSet dataSet1,dataSet2,dataSet3,dataSet4;
    private void setLineChart() {

            List<Entry> entries1 = new ArrayList<>();

        for (int i = 0; i < dayAvgHeartRateNumArray.size(); i++) {
            String string = dayAvgHeartRateNumArray.get(i).toString();
           int p=Integer.valueOf(string).intValue();
            entries1.add(new Entry(i,p));
        }
        dataSet1 = new LineDataSet(entries1, "心率");
        dataSet1.setColor(Color.parseColor("#3ED045"));
        dataSet1.setCircleColor(Color.parseColor("#3ED045"));
        dataSet1.setLineWidth(1f);
        dataSet1.setDrawFilled(false);
        dataSet1.setCircleRadius(3f);
        dataSet1.setDrawCircleHole(true);
        dataSet1.setCircleHoleColor(Color.parseColor("#3ED045"));
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);


        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < dayAvgHighPressureNumArray.size(); i++) {
            String string = dayAvgHighPressureNumArray.get(i).toString();
            int p=Integer.valueOf(string).intValue();
            entries2.add(new Entry(i,p));
        }
        dataSet2 = new LineDataSet(entries2, "收缩压");
        dataSet2.setColor(Color.parseColor("#176DE6"));
        dataSet2.setCircleColor(Color.parseColor("#176DE6"));
        dataSet2.setCircleHoleColor(Color.parseColor("#176DE6"));
        dataSet2.setLineWidth(1f);
        dataSet2.setDrawFilled(false);
        dataSet2.setCircleRadius(3f);
        dataSet2.setDrawCircleHole(true);


        List<Entry> entries3 = new ArrayList<>();
        for (int i = 0; i < dayAvgLowPressureNumArray.size(); i++) {
            String string = dayAvgLowPressureNumArray.get(i).toString();
            int p=Integer.valueOf(string).intValue();
            entries3.add(new Entry(i,p));
        }
        dataSet3 = new LineDataSet(entries3, "舒张压");
        dataSet3.setColor(Color.parseColor("#FAB834"));
        dataSet3.setCircleColor(Color.parseColor("#FAB834"));
        dataSet3.setCircleHoleColor(Color.parseColor("#FAB834"));
        dataSet3.setLineWidth(1f);
        dataSet3.setDrawFilled(false);
        dataSet3.setCircleRadius(3f);
        dataSet3.setDrawCircleHole(true);

        LineData lineData = new LineData(dataSet1, dataSet2, dataSet3);
        mLineChart.setData(lineData);
    }

    private void initLineChart(){
        XAxis xAxis = mLineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);

//        xAxis.setAxisMaximum((float) list.size());
        //不显示网格线
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(6);
        //设置最小间隔，防止当放大时出现重复标签
        xAxis.setGranularity(1f);
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.setGranularityEnabled(true);


        //得到Y轴
        YAxis yAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(50);

        //设置从Y轴值
        yAxis.setAxisMinimum(50f);
      //  xAxis.setSpaceBetweenLabels(int characters)
        xAxis.setSpaceMax(4);
        mLineChart.setDragEnabled(true);
     //   mLineChart.setPinchZoom(true);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
//        mLineChart.setDragEnabled(true);
//        mLineChart.setScaleEnabled(false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_blood_day_average:

                day_tv.setTextColor(this.getResources().getColor(R.color.groabColor));
                day_tv1.setTextColor(this.getResources().getColor(R.color.black));
                day_tv2.setTextColor(this.getResources().getColor(R.color.black));
                groupList.clear();
                searchDateType = "1";
                getData(searchDateType,"","");
                break;
            case R.id.ll_blood_week_average:

                day_tv.setTextColor(this.getResources().getColor(R.color.black));
                day_tv1.setTextColor(this.getResources().getColor(R.color.groabColor));
                day_tv2.setTextColor(this.getResources().getColor(R.color.black));
                groupList.clear();
                searchDateType = "3";
                getData(searchDateType,"","");
                break;
            case R.id.ll_blood_month_average:

                day_tv.setTextColor(this.getResources().getColor(R.color.black));
                day_tv1.setTextColor(this.getResources().getColor(R.color.black));
                day_tv2.setTextColor(this.getResources().getColor(R.color.groabColor));
                groupList.clear();
                searchDateType = "4";
                getData(searchDateType,"","");
                break;

        }
    }

    private void showViewTimePop(){
        PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_view_time, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
    }

    //定义一个方法进行接收
    public static TrendFragment create(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        TrendFragment fragment01 = new TrendFragment();
        fragment01.setArguments(bundle);
        return fragment01;
    }

}
