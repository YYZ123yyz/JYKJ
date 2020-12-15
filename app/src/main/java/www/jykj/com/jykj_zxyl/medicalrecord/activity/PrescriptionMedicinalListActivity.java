package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionMedicinalItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.utils.ConvertUtils;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:处方药品列表
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 10:23
 */
public class PrescriptionMedicinalListActivity extends AbstractMvpBaseActivity<
        PrescriptionMedicinalContract.View, PrescriptionMedicinalPresenter>
        implements PrescriptionMedicinalContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.iv_add_more)
    ImageView ivAddMore;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    @BindView(R.id.rl_operation_buttom_bar)
    RelativeLayout rlOperationButtomBar;
    @BindView(R.id.ll_root_view)
    LinearLayout llRootView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private List<PrescriptionMedicinalItemDataBean> dataBeans;
    private List<TakeMedicinalRateBean> takeMedicinalRateBeans;//服药频率
    private List<PrescriptionTypeBean> prescriptionTypeBeans;//处方类型
    private SparseArray<MedicinalInfoBean> medicinalInfoSparseArray;//用药信息
    private SparseArray<PrescriptionTypeBean> prescriptionTypeSparseArray;//处方类型
    private SparseArray<TakeMedicinalRateBean> takeMedicinalRateSparseArray;//服药频率
    private SparseArray<BaseReasonBean> takeUsageRateSparseArray;//次数
    private SparseArray<BaseReasonBean> takeUsageDaySparseArray;
    private List<BaseReasonBean> usageRateBeans;
    private List<BaseReasonBean> usageDayBeans;
    private String patientCode;//患者Id
    private String patientName;//患者名称
    private String orderId;//订单Id
    private String prescribeVoucher;//处方凭证编码(变更用)
    private PrescriptionNotesBean mPrescriptionNotesBean;//当前处方笺
    private JYKJApplication mApp;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            patientCode=extras.getString("patientCode");
            patientName=extras.getString("patientName");
            orderId=extras.getString("orderId");
            mPrescriptionNotesBean=(PrescriptionNotesBean)extras.getSerializable("result");
            if (mPrescriptionNotesBean!=null) {
                prescribeVoucher= mPrescriptionNotesBean.getPrescribeInfo().get(0).getPrescribeVoucher();
            }
        }
        dataBeans=new ArrayList<>();
        takeMedicinalRateBeans=new ArrayList<>();
        prescriptionTypeBeans=new ArrayList<>();
        usageRateBeans=new ArrayList<>();
        usageDayBeans=new ArrayList<>();
        medicinalInfoSparseArray=new SparseArray<>();
        prescriptionTypeSparseArray=new SparseArray<>();
        takeMedicinalRateSparseArray=new SparseArray<>();
        takeUsageRateSparseArray=new SparseArray<>();
        takeUsageDaySparseArray=new SparseArray<>();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_list;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        initKeyBoardListener(scrollView);
        setToolBar();
        addListener();

    }

    @Override
    protected void initData() {
        super.initData()    ;

        if (mPrescriptionNotesBean!=null) {
            List<PrescriptionMedicinalItemDataBean> prescriptionMedicinalItemDataBeans =
                    ConvertUtils.convertPrescriptionNotesToItem(mPrescriptionNotesBean);
            dataBeans.addAll(prescriptionMedicinalItemDataBeans);
            for (int i = 0; i < dataBeans.size(); i++) {
                MedicinalInfoBean medicinalInfoBean=new MedicinalInfoBean();
                String drugName = dataBeans.get(i).getDrugName();
                medicinalInfoBean.setDrugName(drugName);
                String drugCode = dataBeans.get(i).getDrugCode();
                medicinalInfoBean.setDrugCode(drugCode);
                medicinalInfoSparseArray.put(i, medicinalInfoBean);
                PrescriptionMedicinalItemDataBean itemDataBean = dataBeans.get(i);
                llRootView.addView(getView(itemDataBean));
                initKeyBoardListener(scrollView);
            }
        }
        //mPresenter.sendTakeMedicinalRateRequest("10035");
        mPresenter.sendPrescriptionTypeRequest("10033");
        mPresenter.sendUsageRateRequest("10038",this);
        mPresenter.sendUsageDayRequest("10039",this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("处方药品");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setLeftTitleClickListener(v -> {
            setResult(1000);
            PrescriptionMedicinalListActivity.this.finish();
        });
    }




    /**
     * 获取View
     * @param itemDataBean 数据项目
     * @return View
     */
    private View getView(PrescriptionMedicinalItemDataBean itemDataBean){

        View view = View.inflate(context, R.layout.item_prescription_medicinal, null);
        ImageView mIvDeteteBtn=view.findViewById(R.id.iv_delete_btn);
        TextView mTvPrescriptionType = view.findViewById(R.id.tv_prescription_type);
        TextView mTvMedicinalName = view.findViewById(R.id.tv_medicinal_name);
        EditText mEdBuyMedicinalNum=view.findViewById(R.id.ed_buy_medicinal_num);
        EditText mEdTakeMedicinalNum = view.findViewById(R.id.ed_take_medicinal_num);
        TextView mTvTakeMedicinalRate=view.findViewById(R.id.tv_take_medicinal_rate);
        EditText edTakeMedcinalCycle=view.findViewById(R.id.ed_take_medicinal_cycle);
        TextView mTvUnitName=view.findViewById(R.id.tv_unit_name);
        TextView mTvTakeUnmUnit=view.findViewById(R.id.tv_take_num_unit);
        EditText edInputContent=view.findViewById(R.id.ed_input_content);
        RelativeLayout mRlPrescriptionType=view.findViewById(R.id.rl_prescription_type);
        RelativeLayout mRlMedicinalName=view.findViewById(R.id.rl_medicinal_name);
        RelativeLayout mRlTakeMedicinalRate=view.findViewById(R.id.rl_take_medicinal_rate);
        String prescriptionTypeName = itemDataBean.getPrescriptionTypeName();
        mTvPrescriptionType.setText(StringUtils.isNotEmpty(prescriptionTypeName)
                ?prescriptionTypeName:"未填写");
        String drugName = itemDataBean.getDrugName();
        mTvMedicinalName.setText(StringUtils.isNotEmpty(drugName)?drugName:"未填写");
        String buyMedicinalNum = itemDataBean.getBuyMedicinalNum();
        mEdBuyMedicinalNum.setText(buyMedicinalNum);
        mTvUnitName.setText(itemDataBean.getUnitName());
        String takeMedicinalNum = itemDataBean.getTakeMedicinalNum();
        mEdTakeMedicinalNum.setText(takeMedicinalNum);
        mTvTakeUnmUnit.setText(itemDataBean.getSpecUnit());
        String takeMedicinalRateName = itemDataBean.getTakeMedicinalRateName();
        mTvTakeMedicinalRate.setText(StringUtils.isNotEmpty(takeMedicinalRateName)
                ?takeMedicinalRateName:"未填写");

        edInputContent.setText(itemDataBean.getTakeMedicinalRemark());
        edTakeMedcinalCycle.setText(itemDataBean.getUseCycle());
        mRlPrescriptionType.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(prescriptionTypeBeans)) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                showPrescriptionTypeDialog(mTvPrescriptionType,pos);
            }
        });
        mIvDeteteBtn.setOnClickListener(v -> {

            String orderCode = itemDataBean.getOrderCode();

            if(StringUtils.isNotEmpty(orderCode)){
                int itemPos = getItemPos(orderId, dataBeans);
                mPresenter.sendDeletePrescriptionRequest(
                        itemDataBean.getDrugOrderCode(),orderCode,itemPos,
                        PrescriptionMedicinalListActivity.this);
            }else{
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                dataBeans.remove(pos);
                llRootView.removeView(view);
            }

        });
        mRlMedicinalName.setOnClickListener(v -> {
            int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
            Bundle bundle=new Bundle();
            bundle.putInt("pos",pos);
            MedicinalInfoBean medicinalInfoBean = medicinalInfoSparseArray.get(pos);
            bundle.putSerializable("currentMedicinalInfo",medicinalInfoBean);
            startActivity(ChoosedMedicinalListActivity.class,
                    bundle,100);
        });

        mRlTakeMedicinalRate.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(takeMedicinalRateBeans)) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                showTakeMedicinalRateDialog(mTvTakeMedicinalRate,pos);
            }
        });

        mEdBuyMedicinalNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                dataBeans.get(pos).setBuyMedicinalNum(s.toString());
            }
        });
        mEdTakeMedicinalNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                dataBeans.get(pos).setTakeMedicinalNum(s.toString());
            }
        });
        edInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                dataBeans.get(pos).setTakeMedicinalRemark(s.toString());
            }
        });
        edTakeMedcinalCycle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int pos = getItemByUuId(itemDataBean.getUuId(), dataBeans);
                dataBeans.get(pos).setUseCycle(s.toString());
            }
        });

        return view;
    }

    /**
     * 添加监听
     */
    private void addListener(){
        ivAddMore.setOnClickListener(v -> {
            PrescriptionMedicinalItemDataBean itemDataBean = new PrescriptionMedicinalItemDataBean();
            itemDataBean.setUuId(DateUtils.getCurrentMillis());
            dataBeans.add(itemDataBean);
            llRootView.addView(getView(itemDataBean));
            initKeyBoardListener(scrollView);
        });
        tvEnsureBtn.setOnClickListener(v -> {
            if (CollectionUtils.isEmpty(dataBeans)) {
                ToastUtils.showToast("请添加处方项目");
                return;
            }
            boolean pass = checkInputData(dataBeans);
            if(pass){
                List<PrescriptionItemUploadBean> uploadBeans =
                        ConvertUtils.convertPrescriptionLocalToUploadBean(dataBeans,
                                orderId, patientCode, patientName,
                                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                                , mApp.mViewSysUserDoctorInfoAndHospital.getUserName());

                mPresenter.sendSaveAndUpdatePrescriptionRequest(uploadBeans,prescribeVoucher,this);
            }

        });

    }

    private boolean checkInputData(List<PrescriptionMedicinalItemDataBean> dataBeans){
        boolean isPass=true;
        for (int i = 0; i < dataBeans.size(); i++) {
            String prescriptionTypeName = dataBeans.get(i).getPrescriptionTypeName();
            if (TextUtils.isEmpty(prescriptionTypeName)) {
                ToastUtils.showToast("请填写处方类型");
                isPass=false;
                break;
            }
            String drugName = dataBeans.get(i).getDrugName();
            if (TextUtils.isEmpty(drugName)) {
                ToastUtils.showToast("请选择药品名称");
                isPass=false;
                break;
            }
            String buyMedicinalNum = dataBeans.get(i).getBuyMedicinalNum();

            if (TextUtils.isEmpty(buyMedicinalNum)) {
                ToastUtils.showToast("请填写购药数量");
                isPass=false;
                break;
            }
            String takeMedicinalNum = dataBeans.get(i).getTakeMedicinalNum();
            if (TextUtils.isEmpty(takeMedicinalNum)) {
                ToastUtils.showToast("请填写服药数量");
                isPass=false;
                break;
            }
            String takeMedicinalRateName = dataBeans.get(i).getTakeMedicinalRateName();
            if (TextUtils.isEmpty(takeMedicinalRateName)) {
                ToastUtils.showToast("请选择服药频率");
                isPass=false;
                break;
            }

            String useCycle = dataBeans.get(i).getUseCycle();
            if (TextUtils.isEmpty(useCycle)) {
                ToastUtils.showToast("请填写服药周期");
                isPass=false;
                break;
            }

        }
        return isPass;
    }


    /**
     * 查询当前位置
     * @param orderId orderId
     * @param list 处方列表
     * @return position
     */
    private int getItemPos(String orderId,List<PrescriptionMedicinalItemDataBean> list){
        int currentPos=-1;
        for (int i = 0; i < list.size(); i++) {

            String orderCode = list.get(i).getOrderCode();
            if (orderCode.equals(orderId)) {
                currentPos=i;
                break;
            }
        }
        return currentPos;
    }

    /**
     * 根据uuid获取当前位置
     * @param uuId uuId
     * @param list 处方列表
     * @return position
     */
    private int getItemByUuId(String uuId,List<PrescriptionMedicinalItemDataBean> list){
        int currentPos=-1;
        for (int i = 0; i < list.size(); i++) {

            String uuID = list.get(i).getUuId();
            if (uuID.equals(uuId)) {
                currentPos=i;
                break;
            }
        }
        return currentPos;
    }

    /**
     * 处方类型弹框
     *
     * @param pos 位置
     */
    private void showPrescriptionTypeDialog(TextView textView,int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    PrescriptionTypeBean prescriptionTypeBean = prescriptionTypeBeans.get(options1);
                    prescriptionTypeSparseArray.put(pos,prescriptionTypeBean);
                    dataBeans.get(pos).setPrescriptionTypeName(prescriptionTypeBean.getAttrName());
                    dataBeans.get(pos).setPrescriptionTypeCode(prescriptionTypeBean.getAttrCode()+"");
                    textView.setText(prescriptionTypeBean.getAttrName());
        })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(ConvertUtils.convertPrescriptionTypeToStringArry(prescriptionTypeBeans),
                null, null);
        optionPickUnit.show();
    }


    /**
     * 用药频率弹框
     *
     * @param takeMedicinalRate 用药频率
     * @param pos 位置
     */
    private void showTakeMedicinalRateDialog(TextView takeMedicinalRate,int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    TakeMedicinalRateBean takeMedicinalRateBean = takeMedicinalRateBeans.get(options1);
                    takeMedicinalRateSparseArray.put(pos,takeMedicinalRateBean);
                    dataBeans.get(pos).setTakeMedicinalRateName(takeMedicinalRateBean.getAttrName());
                    dataBeans.get(pos).setTakeMedicinalRateCode(takeMedicinalRateBean.getAttrCode()+"");
                    takeMedicinalRate.setText(takeMedicinalRateBean.getAttrName());
                })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(getUsageRate(),
                getUsageDay(), null);
        optionPickUnit.show();
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


    @Override
    public void showLoading(int code) {
        if (code==102) {
            showLoading("",null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            switch (resultCode) {
                case 1000:
                    if (extras != null) {
                        MedicinalInfoBean medicinalInfoBean
                                = (MedicinalInfoBean) extras.getSerializable("result");
                        int currentPos = extras.getInt("pos");
                        if (medicinalInfoBean != null) {
                            medicinalInfoSparseArray.put(currentPos, medicinalInfoBean);
                            String drugName = medicinalInfoBean.getDrugName();
                            dataBeans.get(currentPos).setDrugName(drugName);
                            dataBeans.get(currentPos).setUnitName(medicinalInfoBean.getDrugUnit());
                            dataBeans.get(currentPos).setDrugPack(medicinalInfoBean.getDrugPack());
                            dataBeans.get(currentPos).setDrugCode(medicinalInfoBean.getDrugCode());
                            dataBeans.get(currentPos).setSpecUnit(medicinalInfoBean.getDrugUnit());
                            dataBeans.get(currentPos).setSpecName(medicinalInfoBean.getDrugSpec());
                            View childAt = llRootView.getChildAt(currentPos);
                            if (childAt!=null) {
                                TextView tvMedicinalName = childAt.findViewById(R.id.tv_medicinal_name);
                                tvMedicinalName.setText(medicinalInfoBean.getDrugName());
                                TextView tvUnitName = childAt.findViewById(R.id.tv_unit_name);
                                TextView tvTakeNumUnit = childAt.findViewById(R.id.tv_take_num_unit);
                                tvUnitName.setText(medicinalInfoBean.getDrugPack());
                                tvTakeNumUnit.setText(medicinalInfoBean.getDrugUnit());

                            }
                        }

                    }

                    break;
                case 1001:
                    break;
                default:
            }
        }
    }

    @Override
    public void getTakeMedicinalRateResult(List<TakeMedicinalRateBean> list) {
        takeMedicinalRateBeans=list;
    }

    @Override
    public void getPrescriptionTypeResult(List<PrescriptionTypeBean> list) {
        prescriptionTypeBeans=list;
    }

    @Override
    public void getSaveAndUpdatePrescriptionResult(boolean isSucess, String msg) {
        if(isSucess){
            setResult(1000);
            this.finish();
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void getDeletePrescriptionResult(boolean isSucess, int pos, String msg) {
        if (isSucess) {
            llRootView.removeViewAt(pos);
        } else {
            ToastUtils.showToast(msg);
        }
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
    public void onBackPressed() {
        setResult(1000);
        this.finish();
    }
}
