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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;

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
    private List<ProvidePatientConditionBloodPressureGroup> groupList = new ArrayList<>();
    private LineChart mLineChart;
    private LinearLayout llBloodDayAverage, llBloodWeekAverage, llBloodMonthAverage;
    private LinearLayout viewBloodDayAverage, viewBloodWeekAverage, viewBloodMonthAverage;
    private LinearLayout llBloodDay, llBloodWeek, llBloodMonth;
    private LinearLayout viewBloodDay, viewBloodWeek, viewBloodMonth;
    private DialogUtil dialogUtil;
    private String searchTimeType = "0";//0全天;1:晨间;3:夜间
    private String searchDateType = "3";//当天(1:当天;2:近3天;3:近7天;4:近30天;5:近3个月;6:近6个月;)
    private TextView tvName,tvAge,tvSex;
    private TextView tvViewTime;
    private String startTime,endTime;

    float zmienna = 123;
    private float[] yData = {zmienna, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trend, container, false);
        initData(v);
        initHandler();
        return v;
    }

    private void initData(View v) {
        mApp = (JYKJApplication) getActivity().getApplication();
        if (isAdded()) {
            mData = (ProvideViewPatientLablePunchClockState) getArguments().getSerializable("patientLable");
        }
        mLineChart = (LineChart) v.findViewById(R.id.line_chart);
        llBloodDayAverage = (LinearLayout) v.findViewById(R.id.ll_blood_day_average);
        llBloodWeekAverage = (LinearLayout) v.findViewById(R.id.ll_blood_week_average);
        llBloodMonthAverage = (LinearLayout) v.findViewById(R.id.ll_blood_month_average);
        viewBloodDayAverage = (LinearLayout) v.findViewById(R.id.view_blood_day_average);
        viewBloodWeekAverage = (LinearLayout) v.findViewById(R.id.view_blood_week_average);
        viewBloodMonthAverage = (LinearLayout) v.findViewById(R.id.view_blood_month_average);

//        tvViewTime = (TextView)v.findViewById(R.id.tv_view_time);
        TextView tvComfirm = v.findViewById(R.id.tv_comfirm);
        RelativeLayout rlStartTime = v.findViewById(R.id.rl_start_time);
        TextView tvStartTime = v.findViewById(R.id.tv_start_time);
        RelativeLayout rlEndTime = v.findViewById(R.id.rl_end_time);
        TextView tvEndTime = v.findViewById(R.id.tv_end_time);
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   tvViewTime.setText(startTime+"至"+endTime);
                getData(searchTimeType,searchDateType);
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





     //   llBloodDay = (LinearLayout) v.findViewById(R.id.ll_blood_day);
     //   llBloodWeek = (LinearLayout) v.findViewById(R.id.ll_blood_week);
      //  llBloodMonth = (LinearLayout) v.findViewById(R.id.ll_blood_month);
     //   viewBloodDay = (LinearLayout) v.findViewById(R.id.view_blood_day);
      //  viewBloodWeek = (LinearLayout) v.findViewById(R.id.view_blood_week);
    //    viewBloodMonth = (LinearLayout) v.findViewById(R.id.view_blood_month);
//        tvName = (TextView)v.findViewById(R.id.tv_name);
//        tvAge = (TextView)v.findViewById(R.id.tv_age);
//        tvSex = (TextView)v.findViewById(R.id.tv_sex);

        String name = mData.getUserName();
        String sex;
        String age;
        if (mData.getGender() == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        age = DateUtils.getAgeFromBirthDate(mData.getBirthday()) + "岁";
//        tvName.setText(name);
//        tvSex.setText(sex);
//        tvAge.setText(age);

        llBloodDayAverage.setOnClickListener(this);
        llBloodWeekAverage.setOnClickListener(this);
        llBloodMonthAverage.setOnClickListener(this);
       // llBloodDay.setOnClickListener(this);
      //  llBloodMonth.setOnClickListener(this);
     //   llBloodWeek.setOnClickListener(this);
     //   tvViewTime.setOnClickListener(this);
        getData(searchTimeType,searchDateType);

    }


    private void getData(String searchTimeType,String searchDateType) {
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
//                    provideViewPatientInfo.setPageNum(mPageNum);
//                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    provideViewPatientInfo.setSearchDateType(searchDateType);
                  //  provideViewPatientInfo.setSearchTimeType(searchTimeType);
                    provideViewPatientInfo.setSearchDateStart(startTime);
                    provideViewPatientInfo.setSearchDateEnd(endTime);

                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResBloodPressureCycleData");
                    Log.e("mNetRetStr",mNetRetStr);
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        if(dialogUtil!=null){
                            dialogUtil.dismiss();
                        }
                        return;
                    }
                    List<ProvidePatientConditionBloodPressureGroup> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionBloodPressureGroup.class);
                    groupList.addAll(list);
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
    LineDataSet dataSet1,dataSet2,dataSet3;
    private void setLineChart() {

        List<Entry> entries1 = new ArrayList<>();
        entries1.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries1.add(new Entry(i, groupList.get(i).getDayAvgHeartRateNum()));
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
        entries2.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries2.add(new Entry(i, groupList.get(i).getDayAvgHighPressureNum()));
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
        entries3.clear();
        for (int i = 0; i < groupList.size(); i++) {
            entries3.add(new Entry(i, groupList.get(i).getDayAvgLowPressureNum()));
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
        xAxis.setGranularity(2f);
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.setGranularityEnabled(true);
        // 标签倾斜
        //设置X轴值为字符串
//        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                if(groupList.size()>0) {
//                    return groupList.get((int) value).getGroupRecordDate();
//                }
//                return "";
//            }
//        });

        //得到Y轴
        YAxis yAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);

        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        mLineChart.setDragEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_blood_day_average:
                if(viewBloodDayAverage.getVisibility()!=View.VISIBLE) {
                    viewBloodDayAverage.setVisibility(View.VISIBLE);
                    viewBloodWeekAverage.setVisibility(View.INVISIBLE);
                    viewBloodMonthAverage.setVisibility(View.INVISIBLE);
                }
                groupList.clear();
                searchDateType = "1";
                getData(searchTimeType,searchDateType);
                break;
            case R.id.ll_blood_week_average:
                if(viewBloodWeekAverage.getVisibility()!=View.VISIBLE) {
                    viewBloodDayAverage.setVisibility(View.INVISIBLE);
                    viewBloodWeekAverage.setVisibility(View.VISIBLE);
                    viewBloodMonthAverage.setVisibility(View.INVISIBLE);
                }
                groupList.clear();
                searchDateType = "3";
                getData(searchTimeType,searchDateType);
                break;
            case R.id.ll_blood_month_average:
                if(viewBloodMonthAverage.getVisibility()!=View.VISIBLE) {
                    viewBloodDayAverage.setVisibility(View.INVISIBLE);
                    viewBloodWeekAverage.setVisibility(View.INVISIBLE);
                    viewBloodMonthAverage.setVisibility(View.VISIBLE);
                }
                groupList.clear();
                searchDateType = "4";
                getData(searchTimeType,searchDateType);
                break;
          //  case R.id.ll_blood_day:
//                if(viewBloodDay.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.VISIBLE);
//                    viewBloodWeek.setVisibility(View.GONE);
//                    viewBloodMonth.setVisibility(View.GONE);
//                }
//                groupList.clear();
//                searchTimeType = "0";
//                getData(searchTimeType,searchDateType);
            //    break;
//            case R.id.ll_blood_week:
//                if(viewBloodWeek.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.GONE);
//                    viewBloodWeek.setVisibility(View.VISIBLE);
//                    viewBloodMonth.setVisibility(View.GONE);
//                }
//                groupList.clear();
//                searchTimeType = "1";
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.ll_blood_month:
//                if(viewBloodMonth.getVisibility()!=View.VISIBLE) {
//                    viewBloodDay.setVisibility(View.GONE);
//                    viewBloodWeek.setVisibility(View.GONE);
//                    viewBloodMonth.setVisibility(View.VISIBLE);
//                }
//                groupList.clear();
//                searchTimeType = "3";
//                getData(searchTimeType,searchDateType);
//                break;
//            case R.id.tv_view_time:
//                showViewTimePop();
//                break;

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
      //  popupWindow.showAsDropDown(tvViewTime,0,0);
//        TextView tvDay1 = view.findViewById(R.id.tv_30_day);
//        TextView tvDay2 = view.findViewById(R.id.tv_3_month);
//        TextView tvDay3 = view.findViewById(R.id.tv_6_month);


//        tvDay1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvDay1.setBackgroundResource(R.drawable.shape_corner_button1);
//                tvDay2.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay3.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay1.setTextColor(getResources().getColor(R.color.writeColor));
//                tvDay2.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay3.setTextColor(getResources().getColor(R.color.blackColor));
//                searchDateType = "4";
//            }
//        });
//
//        tvDay2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvDay1.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay2.setBackgroundResource(R.drawable.shape_corner_button1);
//                tvDay3.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay1.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay2.setTextColor(getResources().getColor(R.color.writeColor));
//                tvDay3.setTextColor(getResources().getColor(R.color.blackColor));
//                searchDateType = "5";
//            }
//        });
//
//        tvDay3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tvDay1.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay2.setBackgroundResource(R.drawable.shape_button_button2);
//                tvDay3.setBackgroundResource(R.drawable.shape_corner_button1);
//                tvDay1.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay2.setTextColor(getResources().getColor(R.color.blackColor));
//                tvDay3.setTextColor(getResources().getColor(R.color.writeColor));
//                searchDateType = "6";
//            }
//        });





    }
}
