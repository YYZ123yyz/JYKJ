package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoContract;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoPresenter;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * Description:添加药品信息
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
    NestedScrollView scrollView;
    @BindView(R.id.rl_choose_drug_dosage_root)
    RelativeLayout rlChooseDrugDosageRoot;
    @BindView(R.id.tv_drug_dosage)
    TextView tvDrugDosage;
    @BindView(R.id.rl_choose_drug_type_root)
    RelativeLayout rlChooseDrugTypeRoot;
    @BindView(R.id.tv_drug_type)
    TextView tvDrugType;
    @BindView(R.id.ed_drug_name_alias)
    EditText edDrugNameAlias;
    @BindView(R.id.ed_factory_name)
    EditText edFactoryName;
    @BindView(R.id.rl_drug_specs_root)
    RelativeLayout rlDurgSpecsRoot;
    @BindView(R.id.rl_usage_root)
    RelativeLayout rlUsageRoot;
    @BindView(R.id.rl_use_amount_root)
    RelativeLayout rlUseAmountRoot;
    private List<DrugDosageBean> drugDosageBeans;
    private DrugDosageBean currentDrugDosageBean;
    private DrugClassificationBean.DrugTypeMedicineListBean currentDrugTypeMedicineListBean;
    private List<DrugClassificationBean> drugClassificationBeans;
    private List<BaseReasonBean> smallReasonBeans;
    private List<BaseReasonBean> bigReasonBeans;
    private List<BaseReasonBean> usageRateBeans;
    private List<BaseReasonBean> usageDayBeans;
    private BaseReasonBean currentSmallReasonBean;
    private BaseReasonBean currentBigReasonBean;
    private List<String> listSpecsNumbers;
    private List<String> listUsagesDay;
    private List<String> listUageasRate;
    private List<String> listUseAmounts;
    private OptionsPickerView optionPickUnit;
    private String medicineCode;
    private int wordLimitNum= 20;
    private String specsNum;//规格数量
    private String usageDay;//用法
    private String usageRate;//用法频率
    private String consumptionNum;//用量
    private String consumptionRate;//用量次数
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
        smallReasonBeans=new ArrayList<>();
        bigReasonBeans=new ArrayList<>();
        listSpecsNumbers=new ArrayList<>();
        listUsagesDay=new ArrayList<>();
        listUageasRate=new ArrayList<>();
        listUseAmounts=new ArrayList<>();
        usageRateBeans=new ArrayList<>();
        usageDayBeans=new ArrayList<>();
        ActivityUtil.setStatusBarMain(this);
        initKeyBoardListener(scrollView);
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchDrugTypeDosageRequest(this);
        mPresenter.sendGetDurgSmallUnitRequest("10041",this);
        mPresenter.sendGetDrugBigUnitRequest("10040",this);
        mPresenter.sendUsageRateRequest("10038",this);
        mPresenter.sendUsageDayRequest("10039",this);
        mPresenter.sendGetDrugTypeMedicineRequest("",this);
    }


    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 添加监听
     */
    private void addListener() {
        edInputMedicineContent.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence enterWords;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                enterWords = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //已输入字数
                //TextView显示剩余字数
                selectionStart = edInputMedicineContent.getSelectionStart();
                selectionEnd = edInputMedicineContent.getSelectionEnd();
                if (enterWords.length() > wordLimitNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    edInputMedicineContent.setText(s);
                    //设置光标在最后
                    edInputMedicineContent.setSelection(tempSelection);
                }
            }
        });

        edDrugNameAlias.addTextChangedListener(new TextWatcher() {

            //记录输入的字数
            private CharSequence enterWords;
            private int selectionStart;
            private int selectionEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                enterWords = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //已输入字数
                //TextView显示剩余字数
                selectionStart = edInputMedicineContent.getSelectionStart();
                selectionEnd = edInputMedicineContent.getSelectionEnd();
                if (enterWords.length() > wordLimitNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    edInputMedicineContent.setText(s);
                    //设置光标在最后
                    edInputMedicineContent.setSelection(tempSelection);
                }
            }
        });

        rlChooseDrugDosageRoot.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(drugDosageBeans)) {
                KeyboardUtils.hideSoftInput(AddDrugInfoActivity2.this);
                showDrugDosageDialog(drugDosageBeans);
            }
        });
        rlChooseDrugTypeRoot.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(drugClassificationBeans)) {
                KeyboardUtils.hideSoftInput(AddDrugInfoActivity2.this);
                showDrugTypeDialog(drugClassificationBeans);
            }
        });

        rlDurgSpecsRoot.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(AddDrugInfoActivity2.this);
            showDrugSpecsDialog();
        });
        rlUsageRoot.setOnClickListener(v -> {

            KeyboardUtils.hideSoftInput(AddDrugInfoActivity2.this);
            showUsageDialog();
        });
        rlUseAmountRoot.setOnClickListener(v -> {
            if (currentSmallReasonBean == null) {
                ToastUtils.showToast("请选择药品规格");
                return;
            }
            KeyboardUtils.hideSoftInput(AddDrugInfoActivity2.this);
            showUseAmountDialog();
        });
        tvEnsureBtn.setOnClickListener(v -> {
            String medicineName = edInputMedicineContent.getText().toString();
            if (TextUtils.isEmpty(medicineName)) {
                ToastUtils.showToast("药品名称不能为空");
                return;
            }
            if (currentDrugTypeMedicineListBean == null) {
                ToastUtils.showToast("请选择药品分类");
                return;
            }

            if (currentSmallReasonBean==null) {
                ToastUtils.showToast("请选择规格");
                return;
            }

            if (currentDrugDosageBean==null) {
                ToastUtils.showToast("请选择剂型");
                return;
            }
            if (TextUtils.isEmpty(usageDay)) {
                ToastUtils.showToast("请选择用法");
                return;
            }
            if (TextUtils.isEmpty(consumptionNum)) {
                ToastUtils.showToast("请选择用量");
                return;
            }

            String factoryName = edFactoryName.getText().toString();
            if (TextUtils.isEmpty(factoryName)) {
                ToastUtils.showToast("请填写厂家名称");
            }

            String drugNameAlias = edDrugNameAlias.getText().toString();
            mPresenter.sendOperUpdDrugInfo_201208(medicineCode
                    ,medicineName,drugNameAlias,specsNum
                    ,currentSmallReasonBean.getAttrName()
                    ,currentBigReasonBean.getAttrName(),
                    tvSpecsName.getText().toString()
                    ,currentDrugDosageBean.getDosageCode(),factoryName
                    ,usageRate,usageDay,consumptionNum,
                    "1",tvUsageName.getText().toString()
                    ,tvConsumptionName.getText().toString(),AddDrugInfoActivity2.this);

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
    public void getDurgSmallUnitResult(List<BaseReasonBean> baseReasonBeans) {
        smallReasonBeans=baseReasonBeans;
    }

    @Override
    public void getDrugBigUnitResult(List<BaseReasonBean> baseReasonBeans) {
        bigReasonBeans=baseReasonBeans;
    }

    @Override
    public void getUsageRateResult(List<BaseReasonBean> baseReasonBeans) {
        usageRateBeans=baseReasonBeans;
    }

    @Override
    public void getUsageDayResult(List<BaseReasonBean> baseReasonBeans) {
        usageDayBeans=baseReasonBeans;
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

        optionPickUnit.setNPicker(getDrugDosageNameList(list),
                null, null);
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
                .setOptionsSelectChangeListener((options1, options2, options3) -> {
                    optionPickUnit.setNPicker(getDrugGroupName(drugClassificationBeans)
                            , getDrugChildName(drugClassificationBeans
                                    .get(options1).getDrugTypeMedicineList())
                            , null);
                    optionPickUnit.setSelectOptions(options1, options2);
                })
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(getDrugGroupName(drugClassificationBeans),
                getDrugChildName(drugClassificationBeans.get(0).getDrugTypeMedicineList())
                , null);
        optionPickUnit.show();

    }

    /**
     * 药品规格弹框
     */
    private void showDrugSpecsDialog(){
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    specsNum=listSpecsNumbers.get(options1);
                    currentSmallReasonBean=smallReasonBeans.get(options2);
                    currentBigReasonBean=bigReasonBeans.get(options3);
                    tvSpecsName.setText(String.format("%s%s/%s",
                            listSpecsNumbers.get(options1), smallReasonBeans.get(options2)
                            .getAttrName(), bigReasonBeans.get(options3).getAttrName()));
                    tvConsumptionName.setText(smallReasonBeans.get(options2).getAttrName());
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        listSpecsNumbers  = getSpecsNumbers();
        optionPickUnit.setNPicker(listSpecsNumbers, getSmallUnits(), getBigUnits());
        optionPickUnit.show();
    }

    /**
     * 用法弹框
     */
    private void showUsageDialog(){
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    tvUsageName.setText(String.format("%s/%s", listUageasRate.get(options1)
                            , listUsagesDay.get(options2)));
                    usageDay = listUsagesDay.get(options1).replace("天","");
                    usageRate= listUageasRate.get(options2).replace("次","");
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        listUageasRate = getUsageRate();
        listUsagesDay = getUsageDay();
        optionPickUnit.setNPicker(getUsageRate(), getUsageDay(),null);
        optionPickUnit.show();
    }



    /**
     * 用量弹框
     */
    private void showUseAmountDialog(){
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    consumptionNum = listUseAmounts.get(options1);
                    tvConsumptionName.setText(String.format("%s%s", consumptionNum,
                            String.format("%s/%s", currentSmallReasonBean.getAttrName()
                                    , "1次")));
                    consumptionRate=listUageasRate.get(options2).replace("次","");
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        listUseAmounts  = getListUseAmounts();
        List<String> listUsagesRate = getListUsagesRate();
        optionPickUnit.setNPicker(listUseAmounts, null,null);
        optionPickUnit.show();
    }



    private List<String> getSpecsNumbers(){
       List<String> stringList=new ArrayList<>();
        for (int i=0;i<200;i++){
            stringList.add(i+1+"");
        }
        return stringList;
    }

    private List<String> getSmallUnits(){
        List<String> stringList=new ArrayList<>();
        if (!CollectionUtils.isEmpty(smallReasonBeans)) {
            for (BaseReasonBean smallReasonBean : smallReasonBeans) {
                stringList.add(smallReasonBean.getAttrName());
            }
        }
        return stringList;
    }

    private List<String> getBigUnits(){
        List<String> stringList=new ArrayList<>();
        if (!CollectionUtils.isEmpty(bigReasonBeans)) {
            for (BaseReasonBean bigReasonBean : bigReasonBeans) {
                stringList.add(bigReasonBean.getAttrName());
            }
        }
        return stringList;
    }

    private List<String> getUsageRate(){
        List<String> stringList=new ArrayList<>();
        if (!CollectionUtils.isEmpty(usageRateBeans)) {
            for (BaseReasonBean usageRateBean : usageRateBeans) {
                stringList.add(usageRateBean.getAttrName());
            }
        }
        return stringList;
    }

    private List<String> getUsageDay(){
        List<String> stringList=new ArrayList<>();
        if (!CollectionUtils.isEmpty(usageDayBeans)) {
            for (BaseReasonBean usageDayBean : usageDayBeans) {
                stringList.add(usageDayBean.getAttrName());
            }
        }
        return stringList;
    }

    @SuppressLint("DefaultLocale")
    private List<String> getListUsagesDay(){
        List<String> stringList=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            stringList.add(String.format("%d天", i + 1));
        }
        return stringList;
    }

    @SuppressLint("DefaultLocale")
    private List<String> getListUsagesRate(){
        List<String> stringList=new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            stringList.add(String.format("%d次", i + 1));
        }
        return stringList;
    }


    private List<String> getListUseAmounts(){
        List<String> stringList=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            stringList.add(i+1+"");
        }
        return stringList;
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
