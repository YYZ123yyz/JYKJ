package www.jykj.com.jykj_zxyl.activity.myreport.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.LogUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.Contract.ReportDetContract;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartDetBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.ReportBean;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.presenter.ReportDetPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.Util;

public class ReportDetActivity extends AbstractMvpBaseActivity<ReportDetContract.View,
        ReportDetPresenter> implements ReportDetContract.View {

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
    @BindView(R.id.oneTimeVisitNum_tv)
    TextView oneTimeVisitNum_tv;
    @BindView(R.id.oneTimeVisitCount_tv)
    TextView oneTimeVisitCount_tv;
    @BindView(R.id.oneTimeVisitFrozenAmount_tv)
    TextView oneTimeVisitFrozenAmount_tv;
    @BindView(R.id.oneTimeVisitAmount_tv)
    TextView oneTimeVisitAmount_tv;
    @BindView(R.id.oneTimeVisitRefundCount_tv)
    TextView oneTimeVisitRefundCount_tv;
    @BindView(R.id.oneTimeVisitRefundAmount_tv)
    TextView oneTimeVisitRefundAmount_tv;
    @BindView(R.id.all_money_tv)
    TextView all_money_tv;
    @BindView(R.id.tv_one_1)
    TextView tvOne1;
    @BindView(R.id.tv_one_2)
    TextView tvOne2;
    @BindView(R.id.tv_msg_0)
    TextView tvMsg0;
    @BindView(R.id.tv_msg_1)
    TextView tvMsg1;
    @BindView(R.id.tv_msg_2)
    TextView tvMsg2;
    @BindView(R.id.tv_msg_3)
    TextView tvMsg3;
    @BindView(R.id.lin_msg_1)
    RelativeLayout linMsg1;
    @BindView(R.id.lin_msg_0)
    RelativeLayout linMsg0;
    @BindView(R.id.choose_dis)
    TextView chooseDisTv;
    private JYKJApplication mApp;
    private int mSignType = 0;//一次性
    private List<ReportBean> mReportBeans;
    private StatisticsDialog myReportDialog;
    private ArrayList<DepartmentListBean> fListBeans;
    private ArrayList<DepartmentListBean.HospitalDepartmentListBean> sListBeans;
    private String depId = "";
    private String diseaseTypeCode = "";
    private String usercode;
    private LinearLayout lin_customize;
    private String showType;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_report_det;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        Intent intent = getIntent();
        usercode = intent.getStringExtra("usercode");
        showType = intent.getStringExtra("showType");
        //自定义选择框
        lin_customize = findViewById(R.id.lin_customize);

        myReportDialog = new StatisticsDialog(this);
        myReportDialog.setOnDevChoose(new StatisticsDialog.onDevChoose() {
            @Override
            public void onDevChoose() {  //科室
                showNomalX2Dialog();
            }

            @Override
            public void onDiseaseChoose() {  //疾病
//                showDrugDosageDialog();
                showNomalDiaglog();
            }

            @Override
            public void onCommit(String dep, String dis, String depName, String disName) {
                depId = dep;
                diseaseTypeCode = dis;
                chooseDisTv.setText(String.format("%s,%s", disName, depName));
                myReportDialog.dismiss();
                mPresenter.getDet(getParams(1));
            }
        });

        mSignType = 0;
        ivOnce.setSelected(true);
        ivSign.setSelected(false);
        chooseDisTv.setText("全部");
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



    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        tvTime.setText(DateUtils.getDeviceTimeOfYM());

        mReportBeans = new ArrayList<>();
        mPresenter.sendyReportRequest(getParams(0));
        mPresenter.getDetList(getParams(0));
        mPresenter.getDet(getParams(1));
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
                if(showType.equals("2")){
                    if( mSignType == 0){
                        //   Log.e("TAG", "onClickcccccccccccc: "+usercode );
                        myReportDialog.oneTime(usercode);
                        lin_customize.setVisibility(View.VISIBLE);
                    }else if(mSignType==1){
                        if(usercode.equals("10")){
                            lin_customize.setVisibility(View.INVISIBLE);
                        }else{
                            lin_customize.setVisibility(View.VISIBLE);
                            myReportDialog.signUp(usercode);
                        }
                    }
                }else if (showType.equals("1")){
                    myReportDialog.signUp(usercode);
                }

                mPresenter.getDet(getParams(1));
                break;

            case R.id.sign_lin://签约
                mSignType = 1;
                ivOnce.setSelected(false);
                ivSign.setSelected(true);
                if(showType.equals("2")){
                    if( mSignType == 0){
                        //   Log.e("TAG", "onClickcccccccccccc: "+usercode );
                        myReportDialog.oneTime(usercode);
                        lin_customize.setVisibility(View.VISIBLE);
                    }else if(mSignType==1){
                        if(usercode.equals("10")){
                            lin_customize.setVisibility(View.INVISIBLE);
                        }else{
                            lin_customize.setVisibility(View.VISIBLE);
                            myReportDialog.signUp(usercode);
                        }
                    }
                }else if (showType.equals("1")){
                    myReportDialog.signUp(usercode);
                }

                mPresenter.getDet(getParams(1));
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
                mPresenter.getDet(getParams(1));
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
        chooseNomalDIglog.setOnDepChoose(new ChooseNomalDialog.onDisChoose() {
            @Override
            public void chooseListen(String name, String id) {

                String diseaseCode = "";
                for (int i = 0; i < mReportBeans.size(); i++) {
                    if (mReportBeans.get(i).getDiseaseTypeName().equals(name)) {
                        diseaseCode = mReportBeans.get(i).getDiseaseTypeCode();
                    }
                }
                myReportDialog.setDisChoose(name, diseaseCode);
            }
        });
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


    private ArrayList<String> getFStringList() {
        ArrayList<String> list = new ArrayList<>();
        if (fListBeans != null) {
            for (DepartmentListBean reportBean : fListBeans) {
                list.add(reportBean.getDepartmentName());
            }
        }

        return list;
    }

    private ArrayList<String> getSStringList() {
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
        if (reportBeans != null) {
            mReportBeans = reportBeans;
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
    public void getDetSucess(DepartDetBean bean) {
        LogUtils.e("xxxxxxxxx   111111111");
        if (bean != null) {
            LogUtils.e("xxxxxxxxx   详情揍我");
            if (mSignType == 0) { //一次性
                linMsg1.setVisibility(View.VISIBLE);
                linMsg0.setVisibility(View.VISIBLE);

                tvMsg0.setText("一次性就诊人次");
                tvMsg1.setText("一次性就诊人数");
                tvMsg2.setText("一次性就诊冻结金额");
                tvMsg3.setText("一次性就诊金额");

                oneTimeVisitNum_tv.setText(String.format("%s人", bean.getTotalOneTimeVisitNum()));
                oneTimeVisitCount_tv.setText(String.format("%s人", bean.getTotalOneTimeVisitCount()));
                oneTimeVisitFrozenAmount_tv.setText(String.format("¥%s元", bean.getTotalOneTimeVisitFrozenAmount()));
                oneTimeVisitAmount_tv.setText(String.format("¥%s元", bean.getTotalOneTimeVisitAmount()));
                oneTimeVisitRefundCount_tv.setText(String.format("%s人", bean.getTotalOneTimeVisitRefundCount()));
                oneTimeVisitRefundAmount_tv.setText(String.format("¥%s元", bean.getTotalOneTimeVisitRefundAmount()));
                all_money_tv.setText(String.format("¥%s元", bean.getTotalOneTimeVisitSumAmount() == null ? "0" : bean.getTotalOneTimeVisitSumAmount()));//bean.getOneTimeVisitSumAmount() == null ? "0" : bean.getOneTimeVisitSumAmount()

            } else {//签约
                linMsg1.setVisibility(View.GONE);
                linMsg0.setVisibility(View.GONE);

                tvMsg0.setText("签约患者人数");
                tvMsg1.setText("签约患者执行金额");
                tvMsg2.setText("患者解约人数");
                tvMsg3.setText("患者解约金额");

                oneTimeVisitNum_tv.setText(String.format("%s人", bean.getTotalSignUpPatientCount()));
                oneTimeVisitCount_tv.setText(String.format("¥%s元", bean.getTotalSignUpPatientExecutedAmount()));
                oneTimeVisitFrozenAmount_tv.setText(String.format("%s人", bean.getTotalTerminatedPatientAmount()));
                oneTimeVisitAmount_tv.setText(String.format("¥%s元", bean.getTotalTerminatedPatientCount()));
                all_money_tv.setText(String.format("¥%s元", bean.getTotalSignPatientSumAmount() == null ? "0" : bean.getTotalSignPatientSumAmount()));//bean.getOneTimeVisitSumAmount() == null ? "0" : bean.getOneTimeVisitSumAmount()

            }



        }

    }

    public String getParams(int type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
        hashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        hashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        LogUtils.e("loginDoctorPosition", mApp.loginDoctorPosition);
        LogUtils.e("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        LogUtils.e("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

        if (type == 1) {
            hashMap.put("userGradeCode", "10");
            hashMap.put("reportPeriod", tvTime.getText().toString());
            hashMap.put("treatmentType", mSignType == 0 ? "1" : "2");
            hashMap.put("diseaseTypeCode", diseaseTypeCode);
            hashMap.put("departmentCode", depId);
            hashMap.put("userName", "");

        }


        return RetrofitUtil.encodeParam(hashMap);
    }
}

