package www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * 基本健康信息
 */
public class BasicInformationActivity extends AbstractMvpBaseActivity<BasicInformationContract.View,
        BasicInformationContractPresenter> implements BasicInformationContract.View {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.li_back)
    LinearLayout liBack;
    @BindView(R.id.tv_xglx)
    TextView tvXglx;
    @BindView(R.id.li_xglx)
    LinearLayout liXglx;
    @BindView(R.id.tv_mz)
    TextView tvMz;
    @BindView(R.id.li_mz)
    LinearLayout liMz;
    @BindView(R.id.tv_jg)
    TextView tvJg;
    @BindView(R.id.li_jg)
    LinearLayout liJg;
    @BindView(R.id.et_sg)
    TextView etSg;
    @BindView(R.id.et_yw)
    TextView etYw;
    @BindView(R.id.et_tz)
    TextView etTz;
    @BindView(R.id.tv_sfxy)
    TextView tvSfxy;
    @BindView(R.id.li_sfxy)
    LinearLayout liSfxy;
    @BindView(R.id.tv_sfxj)
    TextView tvSfxj;
    @BindView(R.id.li_sfxj)
    LinearLayout liSfxj;
    @BindView(R.id.tv_sfay)
    TextView tvSfay;
    @BindView(R.id.li_sfay)
    LinearLayout liSfay;
    @BindView(R.id.tv_zzfxycsj)
    TextView tvZzfxycsj;
    @BindView(R.id.li_zzfxycsj)
    LinearLayout liZzfxycsj;
    @BindView(R.id.tv_qznf)
    TextView tvQznf;
    @BindView(R.id.li_qznf)
    LinearLayout liQznf;
    @BindView(R.id.tv_zxybs)
    TextView tvZxybs;
    @BindView(R.id.li_zxybs)
    LinearLayout liZxybs;
    @BindView(R.id.tv_sfyjzs)
    TextView tvSfyjzs;
    @BindView(R.id.li_sfyjzs)
    LinearLayout liSfyjzs;
    @BindView(R.id.tv_gxybsms)
    TextView tvGxybsms;
    @BindView(R.id.tv_have)
    TextView tvHave;
    @BindView(R.id.tv_gmsms)
    TextView tvGmsms;
    @BindView(R.id.lin_gms)
    LinearLayout linGms;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.lin_data)
    LinearLayout linData;
    private JYKJApplication mApp;
    private String mPatientCode;                       //患者code

    @Override
    protected int setLayoutId() {
        return R.layout.activity_basic_information;
    }

    @Override
    protected void initView() {
        super.initView();
        ActivityUtil.setStatusBarMain(this);
        mApp = (JYKJApplication) getApplication();
        mPatientCode = getIntent().getStringExtra("patientCode");
        addClick();
    }

    private void addClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchBasichealthinformationRequest(mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), mPatientCode);
    }

    @Override
    public void getSearchBasichealthinformationResult(ProvideViewPatientHealthyAndBasicsBean provideViewPatientHealthyAndBasicsBean) {
        //性格类型
        if (provideViewPatientHealthyAndBasicsBean != null) {
            tvNone.setVisibility(View.GONE);
            linData.setVisibility(View.VISIBLE);
            if (provideViewPatientHealthyAndBasicsBean.getCharacterType() == 0) {
                tvXglx.setText("未知");
            } else if (provideViewPatientHealthyAndBasicsBean.getCharacterType() == 1) {
                tvXglx.setText("内向");
            } else if (provideViewPatientHealthyAndBasicsBean.getCharacterType() == 2) {
                tvXglx.setText("外向");
            }
            //民族
            if (TextUtils.isEmpty(provideViewPatientHealthyAndBasicsBean.getNation())) {
                tvMz.setText("未设置");
            } else {
                tvMz.setText(provideViewPatientHealthyAndBasicsBean.getNation());
            }
            //籍贯
            if (TextUtils.isEmpty(provideViewPatientHealthyAndBasicsBean.getNativePlace())) {
                tvJg.setText("未设置");
            } else {
                tvJg.setText(provideViewPatientHealthyAndBasicsBean.getNativePlace());
            }
            //身高
            if (provideViewPatientHealthyAndBasicsBean.getHeight() == 0) {
                etSg.setText("未设置");
            } else {
                etSg.setText(provideViewPatientHealthyAndBasicsBean.getHeight() + "");
            }
            //腰围 waistline
            if (provideViewPatientHealthyAndBasicsBean.getWaistline() == 0) {
                etYw.setText("未设置");
            }
            etYw.setText(provideViewPatientHealthyAndBasicsBean.getWaistline() + "");
            //体重(weight)、
            if (provideViewPatientHealthyAndBasicsBean.getWeight() != 0) {
                etTz.setText(provideViewPatientHealthyAndBasicsBean.getWeight() + "");
            }
            //是否吸烟(1:是;0:否;)(flagSmoking)
            if (provideViewPatientHealthyAndBasicsBean.getFlagSmoking() == 1) {
                tvSfxy.setText("是");
            } else {
                tvSfxy.setText("否");
            }


            //是否酗酒(1:是;0:否;)(flagAlcoholism)
            if (provideViewPatientHealthyAndBasicsBean.getFlagAlcoholism() == 1) {
                tvSfxj.setText("是");
            } else {
                tvSfxj.setText("否");
            }
            //是否熬夜(1:是;0:否;)(flagStayUpLate)、
            if (provideViewPatientHealthyAndBasicsBean.getFlagStayUpLate() == 1) {
                tvSfay.setText("是");
            } else {
                tvSfay.setText("否");
            }
            //最早发现血压异常日期(bloodPressureAbnormalDate)
            long bloodPressureAbnormalDate = provideViewPatientHealthyAndBasicsBean.getBloodPressureAbnormalDate();
            String date = DateUtils.getDateToYYYYMMDD(bloodPressureAbnormalDate);
            tvZzfxycsj.setText(date);
            // 确诊年份(confirmedYear)、
            long confirmedYear = provideViewPatientHealthyAndBasicsBean.getConfirmedYear();
            //String dates = DateUtils.getDateToYYYYMMDD(confirmedYear);
            tvQznf.setText(confirmedYear+"");
            // 是否有高血压病史(flagHtnHistory)
            if (provideViewPatientHealthyAndBasicsBean.getFlagHtnHistory() == 1) {
                tvZxybs.setText("是");
            } else {
                tvZxybs.setText("否");
            }
            // 是否有家族病史(flagFamilyHtn)、
            if (provideViewPatientHealthyAndBasicsBean.getFlagFamilyHtn() == 1) {
                tvSfyjzs.setText("是");
            } else {
                tvSfyjzs.setText("否");
            }
            // 高血压病史描述(htnHistory)
            if (!TextUtils.isEmpty(provideViewPatientHealthyAndBasicsBean.getHtnHistory())) {
                tvGxybsms.setText(provideViewPatientHealthyAndBasicsBean.getHtnHistory());
            }
            //   是否有过敏史(1:有;0:无;)(flagAllergy)
            if (provideViewPatientHealthyAndBasicsBean.getFlagAllergy() == 1) {
                tvHave.setText("是");
                tvGmsms.setText(provideViewPatientHealthyAndBasicsBean.getHistoryAllergy());
            } else {
                tvHave.setText("否");
                linGms.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void getSearchBasichealthinformationResultError(String msg) {
        tvNone.setVisibility(View.VISIBLE);
        linData.setVisibility(View.GONE);
        ToastUtils.showToast(msg);

    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}