package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.MyReportContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter.RecyclerView_Adapter;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.CommitBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter.MyReportPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的报表
 */
public class MyReportActivity extends AbstractMvpBaseActivity<MyReportContract.View,
        MyReportPresenter> implements MyReportContract.View {

    private BaseToolBar toolbar;
    private ImageButton imageButtonE;
    private TextView tv_time;
    private TextView tv_select;
    private MyReportDialog myReportDialog;
    private JYKJApplication mApp;
    private List<ReportBean> listreportbean;
    private String diseaseTypeName;
    private TextView disease;
    private String diseaseTypeCode="";
    private LinearLayout part_0;
    private ArrayList<DepartmentListBean> fListBeans;
    private ArrayList<DepartmentListBean.HospitalDepartmentListBean> sListBeans;
    private RecyclerView_Adapter recyclerView_adapter;
    private RecyclerView recy;
    private LinearLayoutManager layoutManager;
    private List<CommitBean>  list=new ArrayList<>();
    private TextView tv_detail;
    private String usercode;
    private String deviceTimeyears;
    private String depId = "";
    private TextView choose_dis;
    private String name;
    private TextView hospitalname;
    private String showType;
    private String departmentId;
    private double price;
    private TextView tv_price;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_report;
    }

    protected void initView() {
        deviceTimeyears = DateUtils.getDeviceTimeyears();
        Intent intent = getIntent();
        usercode = intent.getStringExtra("usercode");
        name = intent.getStringExtra("name");
        showType = intent.getStringExtra("showType");
        //科室id
        departmentId = intent.getStringExtra("departmentId");
        listreportbean = new ArrayList<>();
        mApp = (JYKJApplication) getApplication();
        choose_dis = findViewById(R.id.choose_dis);
        tv_detail = findViewById(R.id.tv_detail);
        recy = findViewById(R.id.recy);
        tv_price = findViewById(R.id.tv_price);

        hospitalname = findViewById(R.id.hospitalname);
        hospitalname.setText(name+"月报表");
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recy.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recy.setHasFixedSize(true);
        recyclerView_adapter = new RecyclerView_Adapter(list);
        recy.setAdapter(recyclerView_adapter);
        recyclerView_adapter.setOnItemClickListener(new RecyclerView_Adapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                int status = list.get(position).getStatus();

                if(status==1){
                    Intent intent1 = new Intent(MyReportActivity.this, MyReportActivity.class);
                    int departmentId = list.get(position).getDepartmentId();
                    intent1.putExtra("departmentId", departmentId+"");
                    intent1.putExtra("name", name);
                    intent1.putExtra("showType","2");
                    intent1.putExtra("usercode","20");
                    startActivity(intent1);
                }  else{
                    Intent intent = new Intent(MyReportActivity.this, ReportDetActivity.class);
                    intent.putExtra("usercode", "10");
                    intent.putExtra("showType","2");
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(int position) {

            }
        });
        myReportDialog = new MyReportDialog(this);
        myReportDialog.setOnDevChoose(new MyReportDialog.onDevChoose() {
            @Override
            public void onDevChoose() {
                showNomalX2Dialog();
            }

            @Override
            public void onDiseaseChoose() {
                //疾病选择
                showNomalDiaglog();
            }

            @Override
            public void onCommit(String type, String disid, String depid, String name,String disname,
                                 String depname) {
                //查询数据
                if(TextUtils.isEmpty(disname)&&!TextUtils.isEmpty(depname)){
                    choose_dis.setText(depname);
                }else if(!TextUtils.isEmpty(disname)&&TextUtils.isEmpty(depname)){
                    choose_dis.setText(disname);
                }else if(!TextUtils.isEmpty(disname)&&!TextUtils.isEmpty(depname)){
                    choose_dis.setText(String.format("%s,%s", disname, depname));
                }else if(TextUtils.isEmpty(disname)&&TextUtils.isEmpty(depname)){
                    choose_dis.setText("");
                }
                myReportDialog.dismiss();
                mPresenter.getInquireRequest(mApp.loginDoctorPosition, "7d9e7fdf0f6440d894bfb0239e0e3dca"
                        , "D_pan", usercode, deviceTimeyears    ,
                        type, disid,"2", depid,
                        name);
            }


        });
        part_0 = findViewById(R.id.part_0);
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.toolbar);
        //选择时间
        tv_time = findViewById(R.id.tv_time);
        tv_time.setText(deviceTimeyears);
        //自定义筛选
        tv_select = findViewById(R.id.tv_select);
        setToolBar();
    }

    private void showNomalX2Dialog() {
        if (fListBeans != null && sListBeans != null) {
            ChooseDepDialog chooseNomalDIglog = new ChooseDepDialog(this);

            chooseNomalDIglog.show();
            chooseNomalDIglog.setData(fListBeans, sListBeans);
            chooseNomalDIglog.setOnDepChoose(new ChooseDepDialog.onDepChoose() {
                @Override
                public void chooseListen(String name, String id) {

                    myReportDialog.setDepChoose(name, id);
                }
            });
        }
    }


    private void showNomalDiaglog() {
        ChooseNomalDialog chooseNomalDIglog = new ChooseNomalDialog(this);
        chooseNomalDIglog.setType(0);
        chooseNomalDIglog.setData(getDayStrList(), null);
        chooseNomalDIglog.show();
        chooseNomalDIglog.setOnDepChoose(new ChooseNomalDialog.onDisChoose() {
            @Override
            public void chooseListen(String name, String id) {

                String diseaseCode = "";
                for (int i = 0; i < listreportbean.size(); i++) {
                    if (listreportbean.get(i).getDiseaseTypeName().equals(name)) {
                        diseaseCode = listreportbean.get(i).getDiseaseTypeCode();
                    }
                }
                myReportDialog.setDisChoose(name, diseaseCode);
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.getDetList(getParams());

        mPresenter.sendyReportRequest(mApp.loginDoctorPosition, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        //查询数据
        mPresenter.getInquireRequest(mApp.loginDoctorPosition, "7d9e7fdf0f6440d894bfb0239e0e3dca"
                , "D_Pan", usercode, deviceTimeyears,
                "1", "", "2",
                departmentId,"");

    }

    @OnClick({R.id.tv_time, R.id.tv_select,R.id.tv_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                showCalendarDialog();
                break;

            case R.id.tv_select:
                if(showType.equals("2")){
                    myReportDialog.status(usercode);
                    myReportDialog.showPop(part_0);
                }else if(showType.equals("1")){
                    myReportDialog.showPop(part_0);
                }

                break;
            case R.id.tv_detail:
                Intent intent = new Intent(MyReportActivity.this, ReportDetActivity.class);
                intent.putExtra("usercode", usercode);
                startActivity(intent);
                break;
        }
    }


    private void showCalendarDialog() {
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateTime = DateUtils.getDateYYYMM(date);
                tv_time.setText(dateTime);
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt)).setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }


    private ArrayList<String> getDayStrList() {
        ArrayList<String> list = new ArrayList<>();
        if (listreportbean != null) {
            for (ReportBean reportBean : listreportbean) {
                list.add(reportBean.getDiseaseTypeName());
            }
        }

        return list;
    }

    private void setToolBar() {
        toolbar.setMainTitle(name+"报表");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(MyReportActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }


    @Override
    public void showLoading(int code) {

    }

    public String getParams() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
        hashMap.put("operDoctorCode", "7d9e7fdf0f6440d894bfb0239e0e3dca");
        hashMap.put("operDoctorName", "D_pan");
        return RetrofitUtil.encodeParam(hashMap);
    }


    public void  getcoommit(){

       /* mPresenter.getInquireRequest(mApp.loginDoctorPosition, "7d9e7fdf0f6440d894bfb0239e0e3dca"
        , "D_pan", usercode, deviceTimeyears,
                "1", "", "",
                "");*/

    }


    @Override
    public void getmyReportResult(List<ReportBean> reportBeans) {
        if (reportBeans != null) {
            listreportbean=reportBeans;
        }
    }

    @Override
    public void getDetListSucess(List<DepartmentListBean> data) {
        fListBeans = new ArrayList<>();
        sListBeans = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            fListBeans.add(data.get(i));
            if (data.get(i).getHospitalDepartmentList() != null) {


                for (int j = 0; j < data.get(i).getHospitalDepartmentList().size(); j++) {
                    sListBeans.add(data.get(i).getHospitalDepartmentList().get(j));
                }
            }


        }
    }

    @Override
    public void getInquireResult(List<CommitBean> commitBeans) {
        if(commitBeans!=null){
            list.clear();
            for (CommitBean commitBean : commitBeans) {
                list.add(commitBean);
                double totalDayAmount = commitBean.getTotalDayAmount();
                price = price +totalDayAmount;
            }
            tv_price.setText("￥"+price+"元");
            recyclerView_adapter.setDate(list);
            recy.setAdapter(recyclerView_adapter);
            recyclerView_adapter.notifyDataSetChanged();
        }
    }
}