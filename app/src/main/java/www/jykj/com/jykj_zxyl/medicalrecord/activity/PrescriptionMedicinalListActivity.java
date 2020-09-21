package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionMedicinalItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.PrescriptionMedicinalAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.utils.ConvertUtils;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

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
    private String patientCode;
    private String patientName;
    private String orderId;
    private JYKJApplication mApp;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            patientCode=extras.getString("patientCode");
            patientName=extras.getString("patientName");
            orderId=extras.getString("orderId");
        }
        dataBeans=new ArrayList<>();
        takeMedicinalRateBeans=new ArrayList<>();
        prescriptionTypeBeans=new ArrayList<>();
        medicinalInfoSparseArray=new SparseArray<>();
        prescriptionTypeSparseArray=new SparseArray<>();
        takeMedicinalRateSparseArray=new SparseArray<>();

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
        mPresenter.sendTakeMedicinalRateRequest("10035");
        mPresenter.sendPrescriptionTypeRequest("10033");
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("处方药品");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }




    /**
     * 获取View
     * @param itemDataBean 数据项目
     * @param pos 位置
     * @return
     */
    private View getView(PrescriptionMedicinalItemDataBean itemDataBean,int pos){

        View view = View.inflate(context, R.layout.item_prescription_medicinal, null);
        view.setTag(pos);
        ImageView mIvDeteteBtn=view.findViewById(R.id.iv_delete_btn);
        TextView mTvPrescriptionType = view.findViewById(R.id.tv_prescription_type);
        TextView mTvMedicinalName = view.findViewById(R.id.tv_medicinal_name);
        EditText mEdBuyMedicinalNum=view.findViewById(R.id.ed_buy_medicinal_num);
        EditText mEdTakeMedicinalNum = view.findViewById(R.id.ed_take_medicinal_num);
        TextView mTvTakeMedicinalRate=view.findViewById(R.id.tv_take_medicinal_rate);
        TextView mTvTakeMedicinalCycle=view.findViewById(R.id.tv_take_medicinal_cycle);
        TextView mTvUnitName=view.findViewById(R.id.tv_unit_name);
        TextView mTvTakeUnmUnit=view.findViewById(R.id.tv_take_num_unit);
        EditText edInputContent=view.findViewById(R.id.ed_input_content);
        RelativeLayout mRlPrescriptionType=view.findViewById(R.id.rl_prescription_type);
        RelativeLayout mRlMedicinalName=view.findViewById(R.id.rl_medicinal_name);
        RelativeLayout mRlbuyMedicinalNum=view.findViewById(R.id.rl_buy_medicinal_num);
        RelativeLayout mRlTakeMedicinalNum=view.findViewById(R.id.rl_take_medicinal_num);
        RelativeLayout mRlTakeMedicinalRate=view.findViewById(R.id.rl_take_medicinal_rate);
        RelativeLayout mRlTakeMedicinalCycle=view.findViewById(R.id.rl_take_medicinal_cycle);
        mRlPrescriptionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llRootView.removeViewAt(pos);
                if (!CollectionUtils.isEmpty(prescriptionTypeBeans)) {
                    showPrescriptionTypeDialog(mTvPrescriptionType,pos);
                }
            }
        });
        mIvDeteteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRlMedicinalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("pos",pos);
                MedicinalInfoBean medicinalInfoBean = medicinalInfoSparseArray.get(pos);
                bundle.putSerializable("currentMedicinalInfo",medicinalInfoBean);
                startActivity(ChoosedMedicinalListActivity.class,
                        bundle,100);
            }
        });
        mRlbuyMedicinalNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRlTakeMedicinalNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRlTakeMedicinalRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CollectionUtils.isEmpty(takeMedicinalRateBeans)) {
                    showTakeMedicinalRateDialog(mTvTakeMedicinalRate,pos);
                }
            }
        });
        mRlTakeMedicinalCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                dataBeans.get(pos).setTakeMedicinalRemark(s.toString());
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
            dataBeans.add(itemDataBean);
            llRootView.addView(getView(itemDataBean,dataBeans.size()-1));
            initKeyBoardListener(scrollView);
        });
        tvEnsureBtn.setOnClickListener(v -> {
            if (CollectionUtils.isEmpty(dataBeans)) {
                ToastUtils.showToast("请添加处方项目");
                return;
            }
            List<PrescriptionItemUploadBean> uploadBeans = ConvertUtils.convertPrescriptionLocalToUploadBean(dataBeans, orderId, patientCode, patientName, mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    , mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
            mPresenter.sendSaveAndUpdatePrescriptionRequest(uploadBeans,this);
        });

    }










    /**
     * 处方类型弹框
     *
     * @param pos 位置
     */
    private void showPrescriptionTypeDialog(TextView textView,int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    PrescriptionTypeBean prescriptionTypeBean = prescriptionTypeBeans.get(pos);
                    prescriptionTypeSparseArray.put(pos,prescriptionTypeBean);
                    dataBeans.get(pos).setPrescriptionTypeName(prescriptionTypeBean.getAttrName());
                    dataBeans.get(pos).setPrescriptionTypeCode(prescriptionTypeBean.getAttrCode()+"");
                   // mPresMedAdapter.notifyDataSetChanged();
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
                    TakeMedicinalRateBean takeMedicinalRateBean = takeMedicinalRateBeans.get(pos);
                    takeMedicinalRateSparseArray.put(pos,takeMedicinalRateBean);
                    dataBeans.get(pos).setTakeMedicinalRateName(takeMedicinalRateBean.getAttrName());
                    dataBeans.get(pos).setTakeMedicinalRateCode(takeMedicinalRateBean.getAttrCode()+"");
                    takeMedicinalRate.setText(takeMedicinalRateBean.getAttrName());
                })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(ConvertUtils.convertTakeMedicinalRateToStringArry(takeMedicinalRateBeans),
                null, null);
        optionPickUnit.show();
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
                            //mPresMedAdapter.notifyDataSetChanged();

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
}
