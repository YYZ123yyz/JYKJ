package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoContract;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoPresenter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

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
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.rl_choose_drug_dosage_root)
    RelativeLayout rlChooseDrugDosageRoot;
    @BindView(R.id.tv_drug_dosage)
    TextView tvDrugDosage;
    @BindView(R.id.rl_choose_drug_type_root)
    RelativeLayout rlChooseDrugTypeRoot;
    @BindView(R.id.tv_drug_type)
    TextView tvDrugType;
    @BindView(R.id.ed_drug_unit)
    EditText edDrugUnit;
    @BindView(R.id.ed_drug_specs)
    EditText edDrugSpecs;
    @BindView(R.id.ed_drug_name_alias)
    EditText edDrugNameAlias;
    @BindView(R.id.ed_factory_name)
    EditText edFactoryName;
    @BindView(R.id.ed_use_usage)
    EditText edUseUsage;
    @BindView(R.id.ed_use_num)
    EditText edUserNum;
    private List<DrugDosageBean> drugDosageBeans;
    private DrugDosageBean currentDrugDosageBean;
    private DrugClassificationBean.DrugTypeMedicineListBean currentDrugTypeMedicineListBean;
    private List<DrugClassificationBean> drugClassificationBeans;
    private OptionsPickerView optionPickUnit;
    private String medicineCode;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_drug_info;
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            medicineCode=extras.getString("medicineCode");
        }
        drugDosageBeans=new ArrayList<>();
        ActivityUtil.setStatusBarMain(this);
        initKeyBoardListener(scrollView);
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchDrugTypeDosageRequest(this);
        mPresenter.sendGetDrugTypeMedicineRequest("",this);
    }


    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlChooseDrugDosageRoot.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(drugDosageBeans)) {
                showDrugDosageDialog(drugDosageBeans);
            }
        });
        rlChooseDrugTypeRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CollectionUtils.isEmpty(drugClassificationBeans)) {
                    showDrugTypeDialog(drugClassificationBeans);
                }
            }
        });
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = edInputMedicineContent.getText().toString();
                if (TextUtils.isEmpty(medicineName)) {
                    ToastUtils.showToast("药品名称不能为空");
                    return;
                }
                if (currentDrugTypeMedicineListBean==null) {
                    ToastUtils.showToast("请选择药品分类");
                    return;
                }
                String drugUnit = edDrugUnit.getText().toString();
                if (TextUtils.isEmpty(drugUnit)) {
                    ToastUtils.showToast("药品单位不能为空");
                    return;
                }
                String drugSpecs = edDrugSpecs.getText().toString();
                if (TextUtils.isEmpty(drugSpecs)) {
                    ToastUtils.showToast("药品规格不能为空");
                    return;
                }
                String drugNameAlias = edDrugNameAlias.getText().toString();

                mPresenter.sendOperUpdDrugInfo_201116(medicineCode,
                        medicineName,drugNameAlias,drugUnit,drugSpecs
                        ,currentDrugDosageBean.getDosageCode()
                        ,edFactoryName.getText().toString()
                        ,edUseUsage.getText().toString(),edUserNum.getText().toString(),
                        AddDrugInfoActivity2.this);


            }
        });
    }

    @Override
    public void getOperUpdDrugInfoResult(boolean isSucess, String msg) {
        if (isSucess) {
            setResult(1000);
            this.finish();
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getSearchDrugTypeDosageResult(List<DrugDosageBean> drugDosageBeans) {
       this.drugDosageBeans=drugDosageBeans;
    }

    @Override
    public void getDrugClassificationBeanResult(List<DrugClassificationBean> drugClassificationBeans) {
        this.drugClassificationBeans=drugClassificationBeans;
    }

    @Override
    public void showLoading(int code) {
        if(code==101){
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        //super.hideLoading();
        dismissLoading();
    }

    /**
     * 药剂类型选择弹框
     * @param list 药剂列表
     */
    private void showDrugDosageDialog(List<DrugDosageBean> list){
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    currentDrugDosageBean = drugDosageBeans.get(options1);
                    tvDrugDosage.setText(currentDrugDosageBean.getDosageName());
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getDrugDosageNameList(list), null, null);
        optionPickUnit.show();
    }


    /**
     * 药品类型弹框
     *
     * @param drugClassificationBeans 药品类型
     */
    private void showDrugTypeDialog(List<DrugClassificationBean> drugClassificationBeans) {
        optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    currentDrugTypeMedicineListBean
                            = drugClassificationBeans.get(options1).getDrugTypeMedicineList().get(options2);
                    tvDrugType.setText(currentDrugTypeMedicineListBean.getMedicineName());
                }).setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        optionPickUnit.setNPicker(getDrugGroupName(drugClassificationBeans)
                                , getDrugChildName(drugClassificationBeans
                                        .get(options1).getDrugTypeMedicineList())
                                , null);
                        optionPickUnit.setSelectOptions(options1, options2);
                    }
                })
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(getDrugGroupName(drugClassificationBeans),
                getDrugChildName(drugClassificationBeans.get(0).getDrugTypeMedicineList())
                , null);
        optionPickUnit.show();

    }






    private List<String> getDrugGroupName(List<DrugClassificationBean> drugClassificationBeans){
        List<String> list = new ArrayList<>();
        for (DrugClassificationBean drugClassificationBean : drugClassificationBeans) {
            String medicineName = drugClassificationBean.getMedicineName();
            list.add(medicineName);
        }
        return list;
    }

    private List<String> getDrugChildName(List<DrugClassificationBean.DrugTypeMedicineListBean>
                                                  listBeans){
        List<String> list = new ArrayList<>();
        for (DrugClassificationBean.DrugTypeMedicineListBean listBean : listBeans) {
            String medicineName = listBean.getMedicineName();
            list.add(medicineName);
        }
        return list;
    }


    private List<String> getDrugDosageNameList(List<DrugDosageBean> dataList) {
        List<String> list = new ArrayList<>();
        for (DrugDosageBean drugDosageBean : dataList) {
            list.add(drugDosageBean.getDosageName());
        }
        return list;
    }
}
