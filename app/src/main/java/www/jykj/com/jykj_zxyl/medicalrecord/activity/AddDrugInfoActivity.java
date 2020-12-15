package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoContract;
import www.jykj.com.jykj_zxyl.medicalrecord.AddDrugInfoPresenter;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:添加药品信息
 *
 * @author: qiuxinhai
 * @date: 2020-09-18 09:43
 */
public class AddDrugInfoActivity extends AbstractMvpBaseActivity<AddDrugInfoContract.View,
        AddDrugInfoPresenter> implements AddDrugInfoContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.ed_drug_content)
    EditText edDrugContent;
    @BindView(R.id.ed_unit_content)
    EditText edUnitContent;
    @BindView(R.id.ed_specs_content)
    EditText edSpecsContent;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    private String drugUseType;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            drugUseType= extras.getString("drugUseType");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_drug_info;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        addListener();
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("添加药物");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener() {
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drugNameContent = edDrugContent.getText().toString();
                if (!StringUtils.isNotEmpty(drugNameContent)) {
                    ToastUtils.showToast("药品名称不能为空");
                    return;
                }
                String unitContent = edUnitContent.getText().toString();
                if (!StringUtils.isNotEmpty(unitContent)) {
                    ToastUtils.showToast("药品单位不能为空");
                    return;
                }
                String specsContent = edSpecsContent.getText().toString();
                if (!StringUtils.isNotEmpty(specsContent)) {
                    ToastUtils.showToast("药品规格不能为空");
                    return;
                }
                mPresenter.sendOperUpdDrugInfoRequest(drugUseType,drugNameContent,unitContent
                        ,specsContent,AddDrugInfoActivity.this);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void showLoading(int code) {
        showLoading("",null);
    }

    @Override
    public void hideLoading() {
        dismissLoading();
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

    }

    @Override
    public void getDrugClassificationBeanResult(List<DrugClassificationBean> drugClassificationBeans) {

    }

    @Override
    public void getDurgSmallUnitResult(List<BaseReasonBean> baseReasonBeans) {

    }

    @Override
    public void getDrugBigUnitResult(List<BaseReasonBean> baseReasonBeans) {

    }

    @Override
    public void getUsageRateResult(List<BaseReasonBean> baseReasonBeans) {

    }

    @Override
    public void getUsageDayResult(List<BaseReasonBean> baseReasonBeans) {

    }
}
