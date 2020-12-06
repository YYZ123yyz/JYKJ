package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoContract;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoPresenter;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-26 17:05
 */
public class AddDrugInfoActivity2 extends AbstractMvpBaseActivity<AddDrugInfoContract.View,
        AddDrugInfoPresenter> implements AddDrugInfoContract.View{

    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.ed_input_medicine_content)
    EditText edInputMedicineContent;
    @BindView(R.id.ed_int)
    EditText edInt;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_dosage_name)
    TextView tvDosageName;
    @BindView(R.id.tv_specs_name)
    TextView tvSpecsName;
    @BindView(R.id.tv_usage_name)
    TextView tvUsageName;
    @BindView(R.id.tv_consumption_name)
    TextView tvConsumptionName;
    @BindView(R.id.tv_factory_name)
    TextView tvFactoryName;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_drug_info;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void getOperUpdDrugInfoResult(boolean isSucess, String msg) {

    }

    @Override
    public void showLoading(int code) {

    }
}
