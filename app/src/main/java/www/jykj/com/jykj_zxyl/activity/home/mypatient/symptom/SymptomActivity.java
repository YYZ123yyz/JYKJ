package www.jykj.com.jykj_zxyl.activity.home.mypatient.symptom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.SymptormBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 症状信息
 */
public class SymptomActivity extends AbstractMvpBaseActivity<SymptormContract.View,
        SymptormPresenter> implements SymptormContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.li_back)
    LinearLayout liBack;
    @BindView(R.id.li_qbzz)
    LinearLayout liQbzz;
    @BindView(R.id.li_mqzz)
    LinearLayout liMqzz;
    @BindView(R.id.li_bfz)
    LinearLayout liBfz;
    @BindView(R.id.li_hbjb)
    LinearLayout liHbjb;
    @BindView(R.id.li_mqzlfa)
    LinearLayout liMqzlfa;
    @BindView(R.id.self_descrip)
    TextView selfDescrip;
    @BindView(R.id.rv_flowlayout)
    TagFlowLayout rvFlowlayout;
    @BindView(R.id.Currentsymptoms_flowlayout)
    TagFlowLayout CurrentsymptomsFlowlayout;
    @BindView(R.id.Complication_flowlayout)
    TagFlowLayout ComplicationFlowlayout;
    @BindView(R.id.Comorbidity_flowlayout)
    TagFlowLayout ComorbidityFlowlayout;
    @BindView(R.id.Treatmentsolutions_flowlayout)
    TagFlowLayout TreatmentsolutionsFlowlayout;
    @BindView(R.id.lin_data)
    LinearLayout linData;
    @BindView(R.id.tv_none)
    TextView tvNone;
    private SymptomRecycleAdapter mSymptomRecycleAdapter;
    private JYKJApplication mApp;
    private String mPatientCode;                       //患者code
    private LayoutInflater mInflater;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_symptom;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchSymptormRequest(mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), mPatientCode);
    }

    @Override
    protected void initView() {
        super.initView();
        mInflater = LayoutInflater.from(this);
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        mPatientCode = getIntent().getStringExtra("patientCode");
        addOnClick();
    }

    private void addOnClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getSearchSymptormResult(SymptormBean symptormBean) {
        if (symptormBean != null) {
              linData.setVisibility(View.VISIBLE);
              tvNone.setVisibility(View.GONE);
            //起病症状 rvOnsetsymptoms
            ArrayList<String> onsetSymptomsList = new ArrayList<>();
            String onsetSymptomsName = symptormBean.getOnsetSymptomsName();
            String[] onsetSymptoms = onsetSymptomsName.split("、");
            List<String> onsetSymptomslist = Arrays.asList(onsetSymptoms);
            onsetSymptomsList.addAll(onsetSymptomslist);
            rvFlowlayout.setAdapter(new TagAdapter<String>(onsetSymptomsList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                            rvFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }

            });
            //目前症状
            ArrayList<String> CurrentsymptomsList = new ArrayList<>();
            String currentSymptomsName = symptormBean.getCurrentSymptomsName();
            String[] currentSymptoms = currentSymptomsName.split("、");
            List<String> currentSymptomslist = Arrays.asList(currentSymptoms);
            CurrentsymptomsList.addAll(currentSymptomslist);
            CurrentsymptomsFlowlayout.setAdapter(new TagAdapter<String>(CurrentsymptomsList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                            CurrentsymptomsFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }

            });
            //并发症
            ArrayList<String> ComplicationList = new ArrayList<>();
            String complicationName = symptormBean.getComplicationName();
            String[] complication = complicationName.split("、");
            List<String> complicationlist = Arrays.asList(complication);
            ComplicationList.addAll(complicationlist);
            ComplicationFlowlayout.setAdapter(new TagAdapter<String>(ComplicationList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                            ComplicationFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }

            });
            //合并疾病
            ArrayList<String> combinedDiseaseList = new ArrayList<>();
            String combinedDiseaseName = symptormBean.getCombinedDiseaseName();
            String[] combinedDisease = combinedDiseaseName.split("、");
            List<String> combinedDiseaselist = Arrays.asList(combinedDisease);
            combinedDiseaseList.addAll(combinedDiseaselist);
            ComorbidityFlowlayout.setAdapter(new TagAdapter<String>(combinedDiseaseList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                            ComorbidityFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }

            });
            //目前治疗方案
            ArrayList<String> currentTreatmentPlanList = new ArrayList<>();
            String currentTreatmentPlanName = symptormBean.getCurrentTreatmentPlanName();
            String[] currentTreatmentPlan = currentTreatmentPlanName.split("、");
            List<String> list = Arrays.asList(currentTreatmentPlan);
            currentTreatmentPlanList.addAll(list);
            TreatmentsolutionsFlowlayout.setAdapter(new TagAdapter<String>(currentTreatmentPlanList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.content_flow,
                            TreatmentsolutionsFlowlayout, false);
                    tv.setText(s);
                    return tv;
                }

            });
        }
    }

    @Override
    public void getSearchSymptormResultError(String msg) {
        linData.setVisibility(View.GONE);
        tvNone.setVisibility(View.VISIBLE);
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