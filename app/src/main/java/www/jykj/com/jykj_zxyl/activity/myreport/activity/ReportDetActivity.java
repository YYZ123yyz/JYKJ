package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.ReportDetContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter.ReportDetPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.Util;

public class ReportDetActivity extends AbstractMvpBaseActivity<ReportDetContract.View, ReportDetPresenter> implements ReportDetContract.View {

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.right_image_search)
    ImageButton imageButtonE;
    @BindView(R.id.iv_once)
    ImageView ivOnce;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    @BindView(R.id.tv_once)
    TextView tvOnce;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.iv_sele_sign)
    ImageView ivSeleSing;
    @BindView(R.id.iv_sele_once)
    ImageView ivSeleOnce;
    @BindView(R.id.part_0)
    LinearLayout partLayout;
    private JYKJApplication mApp;
    private int mSignType = 0;//一次性
    private List<ReportBean> mReportBeans;
    private StatisticsDialog myReportDialog;
    private ArrayList<DepartmentListBean> fListBeans;
    private ArrayList<DepartmentListBean.HospitalDepartmentListBean> sListBeans;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_report_det;
    }

    @Override
    protected void initView() {
        super.initView();

        setToolBar();
        myReportDialog = new StatisticsDialog(this);
        myReportDialog.setOnDevChoose(new StatisticsDialog.onDevChoose() {
            @Override
            public void onDevChoose() {
                showNomalX2Dialog();
            }

            @Override
            public void onDiseaseChoose() {
//                showDrugDosageDialog();
                showNomalDiaglog();
            }
        });
    }

    private void showNomalX2Dialog() {
        if (fListBeans != null && sListBeans != null) {
            ChooseDepDialog chooseNomalDIglog = new ChooseDepDialog(this);

            chooseNomalDIglog.show();
            chooseNomalDIglog.setData(fListBeans, sListBeans);
        }

    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mReportBeans = new ArrayList<>();
        mPresenter.sendyReportRequest(getParams());
        mPresenter.getDetList(getParams());
    }

    @Override
    public void showLoading(int code) {

    }


    private void setToolBar() {
        toolbar.setMainTitle("统计报表");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleClickListener(v -> {
            MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(ReportDetActivity.this);
            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
            }
        });
    }

    @OnClick({R.id.once_sign_lin, R.id.sign_lin, R.id.tv_time, R.id.tv_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.once_sign_lin://一次性
                mSignType = 0;
                ivOnce.setSelected(true);
                ivSign.setSelected(false);

                break;

            case R.id.sign_lin://签约
                mSignType = 1;
                ivOnce.setSelected(false);
                ivSign.setSelected(true);
                break;
            case R.id.tv_time:
                tvTime.setTextColor(getResources().getColor(R.color.color_6694fb));
                tvSelect.setTextColor(getResources().getColor(R.color.black));
                showCalendarDialog();
                break;
            case R.id.tv_select:
                tvTime.setTextColor(getResources().getColor(R.color.black));
                tvSelect.setTextColor(getResources().getColor(R.color.color_6694fb));
                myReportDialog.showPop(partLayout);
                break;
        }
    }


    private void showCalendarDialog() {
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateTime = DateUtils.getDateYYYMM(date);
                tvTime.setText(dateTime);
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt)).setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }

    private void showNomalDiaglog() {
        ChooseNomalDialog chooseNomalDIglog = new ChooseNomalDialog(this);
        chooseNomalDIglog.setType(0);
        chooseNomalDIglog.setData(getDayStrList(), null);
        chooseNomalDIglog.show();
    }


    private ArrayList<String> getDayStrList() {
        ArrayList<String> list = new ArrayList<>();
        if (mReportBeans != null) {
            for (ReportBean reportBean : mReportBeans) {
                list.add(reportBean.getDiseaseTypeName());
            }
        }

        return list;
    }



    private ArrayList<String> getFStringList(){
        ArrayList<String> list = new ArrayList<>();
        if (fListBeans != null) {
            for (DepartmentListBean reportBean : fListBeans) {
                list.add(reportBean.getDepartmentName());
            }
        }

        return list;
    }

    private ArrayList<String> getSStringList(){
        ArrayList<String> list = new ArrayList<>();
        if (sListBeans != null) {
            for (DepartmentListBean.HospitalDepartmentListBean reportBean : sListBeans) {
                list.add(reportBean.getDepartmentName());
            }
        }

        return list;
    }

    @Override
    public void getmyReportResult(List<ReportBean> reportBeans) {
        LogUtils.e("传过来   " + reportBeans.size());
        if (reportBeans != null) {
            mReportBeans = reportBeans;
            LogUtils.e("真实尺寸    " + mReportBeans.size());
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

    public String getParams() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
        hashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        hashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        return RetrofitUtil.encodeParam(hashMap);
    }
}

