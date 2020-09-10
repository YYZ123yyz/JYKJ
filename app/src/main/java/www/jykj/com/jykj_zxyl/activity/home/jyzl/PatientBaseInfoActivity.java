package www.jykj.com.jykj_zxyl.activity.home.jyzl;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

/**
 * Description:基本信息
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 14:21
 */
public class PatientBaseInfoActivity extends AbstractMvpBaseActivity<PatientBaseInfoContract.View
        ,PatientBaseInfoPresenter> implements PatientBaseInfoContract.View {

    ImageButton rightImageId;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_character_type)
    TextView tvCharacterType;
    @BindView(R.id.tv_areas)
    TextView tvAreas;
    @BindView(R.id.tv_native_place)
    TextView tvNativePlace;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_waistline)
    TextView tvWaistline;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_is_smoke)
    TextView tvIsSmoke;
    @BindView(R.id.tv_is_alcoholism)
    TextView tvIsAlcoholism;
    @BindView(R.id.tv_is_stay_up_late)
    TextView tvIsStayUpLate;
    @BindView(R.id.tv_blood_abnormal_date)
    TextView tvBloodAbnormalDate;
    @BindView(R.id.tv_diagnosis_date)
    TextView tvDiagnosisDate;
    @BindView(R.id.tv_high_blood_history)
    TextView tvHighBloodHistory;
    @BindView(R.id.tv_family_disease_history)
    TextView tvFamilyDiseaseHistory;
    @BindView(R.id.tv_allergy_history)
    TextView tvAllergyHistory;
    private String mPatientCode;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            mPatientCode= extras.getString("patientCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_patient_base_info;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBar();
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendPatientBaseInfoRequest(mPatientCode,this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("基本信息");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    @Override
    public void showLoading(int code) {

    }
}
