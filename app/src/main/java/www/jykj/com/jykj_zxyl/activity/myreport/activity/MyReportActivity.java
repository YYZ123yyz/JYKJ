package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.MyReportContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter.MyReportPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的报表
 */
public class MyReportActivity extends AbstractMvpBaseActivity<MyReportContract.View,
        MyReportPresenter> implements MyReportContract.View  {

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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_report;
    }

    protected void initView() {
        ActivityUtil.setStatusBarMain(this);
        listreportbean=new ArrayList<>();
        mApp= (JYKJApplication) getApplication();
        myReportDialog = new MyReportDialog(this);
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.toolbar);
        //选择时间
        tv_time = findViewById(R.id.tv_time);
        //自定义筛选
        tv_select = findViewById(R.id.tv_select);
        setToolBar();
    }

    @Override
    protected void initData() {
        super.initData();
   mPresenter.sendyReportRequest(mApp.loginDoctorPosition,mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),
           mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
    }

    @OnClick({R.id.tv_time,R.id.tv_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                break;

            case R.id.tv_select:
                Log.e("TAG", "onClick: cccc"+"zoule " );
                myReportDialog.show();
                myReportDialog.setOnClickListener(new MyReportDialog.OnClickListener() {
                    @Override
                    public void onClickSucessBtn() {

                    }

                    @Override
                    public void disease() {
                        //选择疾病
                        showDrugDosageDialog();
                    }

                    @Override
                    public void department() {

                    }
                });
                break;
        }
    }

    private void showDrugDosageDialog() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                diseaseTypeName = listreportbean.get(options1).getDiseaseTypeName();
                diseaseTypeCode = listreportbean.get(options1).getDiseaseTypeCode();
                disease = myReportDialog.findViewById(R.id.tv_disease);
                disease.setText(diseaseTypeName);

            }
        })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDayStrList(listreportbean), null, null);
        optionPickUnit.show();

    }

    private List<String> getDayStrList(List<ReportBean> report) {
        List<String> list = new ArrayList<>();
        for (ReportBean reportBean : report) {
            list.add(reportBean.getDiseaseTypeName());
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

    @Override
    public void getmyReportResult(List<ReportBean> reportBeans) {
        if(reportBeans!=null){
            for (ReportBean reportBean : reportBeans) {
                listreportbean.add(reportBean);
            }
        }
    }
}