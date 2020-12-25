package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AddBankcardContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AddBankcardPresenter;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;


public class AddBankcardActivity extends AbstractMvpBaseActivity<AddBankcardContract.View
        , AddBankcardPresenter> implements AddBankcardContract.View {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_cardnum)
    EditText etCardNum;
    @BindView(R.id.et_bank_id)
    EditText etBankId;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    private JYKJApplication mApp;
    private BaseToolBar toolbar;
    private ImageButton imageButtonE;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_bankcard;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        imageButtonE = findViewById(R.id.right_image_search);
        toolbar = findViewById(R.id.toolbar);
        setToolBar();
    }

    private void setToolBar() {
        toolbar.setMainTitle("添加银行卡");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        //add
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(AddBankcardActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
    }

    @OnClick({R.id.bind_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_tv:
                checkInput();
                break;
        }

    }

    private void checkInput() {
        /*if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            ToastUtils.showShort("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(etCardNum.getText().toString().trim())) {
            ToastUtils.showShort("请填写身份证号");
            return;
        }
        if (TextUtils.isEmpty(etBankId.getText().toString().trim())) {
            ToastUtils.showShort("请输入银行卡号");
            return;
        }
        if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            ToastUtils.showShort("请输入银行开户城市");
            return;
        }
        if (TextUtils.isEmpty(etBankName.getText().toString().trim())) {
            ToastUtils.showShort("请输入银行名");
            return;
        }
        if (RegexUtils.isIDCard18(etCardNum.getText().toString().trim())) {
            ToastUtils.showShort("请填写正确的身份证号");
            return;
        }*/
      /*  Intent intent = new Intent(this, WithdrawDetActivity.class);
        startActivity(intent);*/
           mPresenter.bindBankCard(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        stringStringHashMap.put("assetsCode", SPUtils.getInstance().getString("assetsCode") == null ? "" : SPUtils.getInstance().getString("assetsCode"));//SPUtils.getInstance().getString("assetsCode") == null ? "c877fc2a03bf4552ad070fb112794246" : SPUtils.getInstance().getString("assetsCode"))
        stringStringHashMap.put("cardUserName", etName.getText().toString().trim());
        stringStringHashMap.put("idNumber", etCardNum.getText().toString().trim());
        stringStringHashMap.put("cardAccount", etBankId.getText().toString().trim());
        stringStringHashMap.put("bankAddress", etCity.getText().toString().trim());
        stringStringHashMap.put("bankName", etBankName.getText().toString().trim());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }


    @Override
    public void bindSucess() {
        ToastUtils.showShort("操作成功");
        finish();
    }
}

