package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.List;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.MyReportContract;
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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_report;
    }

    protected void initView() {
        ActivityUtil.setStatusBarMain(this);
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
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    TextView tv_disease = myReportDialog.findViewById(R.id.tv_disease);
                    //    withdrawalState = drugDosageNameList.get(options1);
                //    stateTv.setText(withdrawalState);
                  /*  Log.e("TAG", "showDrugDosageDialog: "+withdrawalState );
                    if(!TextUtils.isEmpty(withdrawalState)){
                        if(withdrawalState.equals("0")){
                            stateTv.setText("已提交");
                        }else if(withdrawalState.equals("1")){
                            stateTv.setText("审核中");
                        }else if(withdrawalState.equals("2")){
                            stateTv.setText("打款中");
                        }else{
                            stateTv.setText("已完成");
                        }
                    }*/

                 //   pageNum = 0;
                  //  mPresenter.getWithdrawDet(getParams());
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

       // optionPickUnit.setNPicker(drugDosageNameList,
       //         null, null);
        optionPickUnit.show();
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

        }
    }
}