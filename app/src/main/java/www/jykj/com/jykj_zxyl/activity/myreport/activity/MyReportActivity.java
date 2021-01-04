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
    private String diseaseTypeCode;
    private LinearLayout part_0;
    private ArrayList<DepartmentListBean> fListBeans;
    private ArrayList<DepartmentListBean.HospitalDepartmentListBean> sListBeans;
    private RecyclerView_Adapter recyclerView_adapter;
    private RecyclerView recy;
    private LinearLayoutManager layoutManager;
    private List<CommitBean>  list=new ArrayList<>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_report;
    }

    protected void initView() {
     //   ActivityUtil.setStatusBarMain(this);
        listreportbean = new ArrayList<>();
        mApp = (JYKJApplication) getApplication();
        recy = findViewById(R.id.recy);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recy.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recy.setHasFixedSize(true);
        recyclerView_adapter = new RecyclerView_Adapter(list);
        recy.setAdapter(recyclerView_adapter);
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
            public void onCommit() {
             //   getcoommit();
            }

        });
        part_0 = findViewById(R.id.part_0);
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.toolbar);
        //选择时间
        tv_time = findViewById(R.id.tv_time);
        //自定义筛选
        tv_select = findViewById(R.id.tv_select);
        setToolBar();
    }

    private void showNomalX2Dialog() {
        if (fListBeans != null && sListBeans != null) {
            ChooseDepDialog chooseNomalDIglog = new ChooseDepDialog(this);

            chooseNomalDIglog.show();
            chooseNomalDIglog.setData(fListBeans, sListBeans);
        }

    }


    private void showNomalDiaglog() {
        ChooseNomalDialog chooseNomalDIglog = new ChooseNomalDialog(this);
        chooseNomalDIglog.setType(0);
        chooseNomalDIglog.setData(getDayStrList(), null);
        chooseNomalDIglog.show();
    }


    @Override
    protected void initData() {
        super.initData();
        getcoommit();
        mPresenter.sendyReportRequest(mApp.loginDoctorPosition, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
                mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
      //  mPresenter.getDetList(getParams());

    }

    @OnClick({R.id.tv_time, R.id.tv_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                showCalendarDialog();
            //    startActivity(new Intent(MyReportActivity.this, ReportDetActivity.class));
                break;

            case R.id.tv_select:
                myReportDialog.showPop(part_0);
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
        toolbar.setMainTitle("医院报表");
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
        hashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        hashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        return RetrofitUtil.encodeParam(hashMap);
    }


    public void  getcoommit(){
       /* String s = tv_time.getText().toString();
        if(TextUtils.isEmpty(s)){
            ToastUtils.showShort("请选择日期");
            return;
        }*/
        mPresenter.getInquireRequest(mApp.loginDoctorPosition, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
        , mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), "10", "2020-12",
                "1", "", "",
                "");

    }


    @Override
    public void getmyReportResult(List<ReportBean> reportBeans) {
        if (reportBeans != null) {
            for (ReportBean reportBean : reportBeans) {
                listreportbean.add(reportBean);
            }
        }
    }

    @Override
    public void getDetListSucess(List<DepartmentListBean> data) {
        LogUtils.e("请求接口   呦呦呦    总共  "+data.size());
        fListBeans = new ArrayList<>();
        sListBeans = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            fListBeans.add(data.get(i));
            LogUtils.e("请求接口   呦呦呦   实体类 "+   i +"            "+data.get(i).getHospitalDepartmentList());


            if (data.get(i).getHospitalDepartmentList() !=null){

                LogUtils.e("请求接口   呦呦呦   尺寸   "+data.get(i).getHospitalDepartmentList().size());

                for (int j = 0; j < data.get(i).getHospitalDepartmentList().size(); j++) {
                    sListBeans.add(data.get(i).getHospitalDepartmentList().get(j));
                }
            }


        }
    }

    @Override
    public void getInquireResult(List<CommitBean> commitBeans) {
        if(commitBeans!=null){
            for (CommitBean commitBean : commitBeans) {
                list.add(commitBean);
            }
            recyclerView_adapter.setDate(list);
            recy.setAdapter(recyclerView_adapter);
            recyclerView_adapter.notifyDataSetChanged();
        }
    }
}