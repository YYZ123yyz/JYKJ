package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionMedicinalItemDataBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionMedicinalPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.PrescriptionMedicinalAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.utils.ConvertUtils;

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
    private List<PrescriptionMedicinalItemDataBean> dataBeans;
    private List<TakeMedicinalRateBean> takeMedicinalRateBeans;//服药频率
    private PrescriptionMedicinalAdapter mPresMedAdapter;//处方适配器
    private SparseArray<MedicinalInfoBean> medicinalInfoSparseArray;


    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        dataBeans=new ArrayList<>();
        takeMedicinalRateBeans=new ArrayList<>();
        medicinalInfoSparseArray=new SparseArray<>();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_list;
    }


    @Override
    protected void initView() {
        super.initView();

        setToolBar();
        initRecyclerView();
        addListener();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendTakeMedicinalRateRequest("10035");
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
     * 添加监听
     */
    private void addListener(){
        ivAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescriptionMedicinalItemDataBean itemDataBean = new PrescriptionMedicinalItemDataBean();
                dataBeans.add(itemDataBean);
                mPresMedAdapter.notifyDataSetChanged();
            }
        });
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        mPresMedAdapter=new PrescriptionMedicinalAdapter(this,dataBeans);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mPresMedAdapter);
        mPresMedAdapter.setOnClickItemListener(new PrescriptionMedicinalAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {
            }

            @Override
            public void onClickDeleteItem(int pos) {
                dataBeans.remove(pos);
                mPresMedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onClickPrescriptionType(int pos) {

            }

            @Override
            public void onClickMedicinalName(int pos) {

                Bundle bundle=new Bundle();
                bundle.putInt("pos",pos);
                MedicinalInfoBean medicinalInfoBean = medicinalInfoSparseArray.get(pos);
                bundle.putSerializable("currentMedicinalInfo",medicinalInfoBean);
                startActivity(ChoosedMedicinalListActivity.class,
                        bundle,100);
            }

            @Override
            public void onClickBuyMedicinalNum(int pos) {
                showTakeMedicinalRateDialog(pos);
            }

            @Override
            public void onClickTakeMedicinalNum(int pos) {

            }

            @Override
            public void onClickTakeMedicinalRate(int pos) {

            }

            @Override
            public void onClickTakeMedicinalCycle(int pos) {

            }
        });
    }


    /**
     * 检查检验等级弹框
     *
     * @param pos 位置
     */
    private void showTakeMedicinalRateDialog(int pos) {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                })
                .setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();
        optionPickUnit.setNPicker(ConvertUtils.convertTakeMedicinalRateToString(takeMedicinalRateBeans),
                null, null);
        optionPickUnit.show();
    }

    @Override
    public void showLoading(int code) {

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
                            mPresMedAdapter.notifyDataSetChanged();
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
}
