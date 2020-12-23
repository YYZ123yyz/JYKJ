package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.MoneyDialog;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.MoneyPop;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.WithdrawTypePop;

public class WithdrawActivity extends AbstractMvpBaseActivity<WithdrawContract.View
        , WithdrawPresenter> implements WithdrawContract.View {

    @BindView(R.id.go2pay_tv)
    TextView go2Pay;
    @BindView(R.id.et_withdraw)
    TextView etWithdraw;
    @BindView(R.id.card_tv)
    TextView cardTv;
    private WithdrawTypePop withdrawTypePop;
    private JYKJApplication mApp;
    private List<WithdrawTypelListBean> mTypeData;
    private MoneyDialog moneyPop;
    private String mPassword = "";
    private String bankId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        withdrawTypePop = new WithdrawTypePop(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getWithdrawType(getParams());
    }

    @OnClick({R.id.go2pay_tv, R.id.withdraw_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go2pay_tv:
//                startActivity(new Intent(WithdrawActivity.this, AddBankcardActivity.class));
                checkInput();

                break;
            case R.id.withdraw_type:
                if (withdrawTypePop == null) {
                    withdrawTypePop = new WithdrawTypePop(this);
                }
                if (mTypeData != null) {
                    withdrawTypePop.setData(mTypeData);
                }
                withdrawTypePop.showPop(go2Pay);
                break;
        }

    }

    private void showPop() {

    }

    private void checkInput() {
        if (TextUtils.isEmpty(cardTv.getText().toString())) {
            ToastUtils.showShort("请先选择支付方式");
            return;
        }
        if (cardTv.getText().toString().equals("--")) {
            ToastUtils.showShort("请输入正确的支付方式");
            return;
        }
        if (TextUtils.isEmpty(etWithdraw.getText().toString().trim())) {
            ToastUtils.showShort("请填写提现金额");
            return;
        }
//        mPresenter.go2Withdraw(getParams());
        mPresenter.getWithdrawCost(getParams());
    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        stringStringHashMap.put("bankcardCode", bankId);
        stringStringHashMap.put("cashMoney", etWithdraw.getText().toString());
        stringStringHashMap.put("oldPwd", mPassword);
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @Override
    public void getTypeSucess(List<WithdrawTypelListBean> data) {
        mTypeData = data;
        WithdrawTypelListBean withdrawTypelListBean = data.get(0);
        cardTv.setText(withdrawTypelListBean.getBankName());
        bankId = withdrawTypelListBean.getIdNumber();
    }

    @Override
    public void getWithdrawCost(WithdrawCostBean bean) {
        if (moneyPop == null) {
            moneyPop = new MoneyDialog(this);
        }
        moneyPop.show();
        bean.setMoney(etWithdraw.getText().toString().trim());
        moneyPop.setData(bean);
        moneyPop.setOnDevChoose(new MoneyDialog.onDevChoose() {
            @Override
            public void onDevChoose(String password) {
                mPassword = password;
                mPresenter.go2Withdraw(getParams());
            }
        });

    }

    @Override
    public void checkSucess() {

    }

    @Override
    public void withDrawSucess() {
        ToastUtils.showShort("提现申请成功");
        finish();
    }
}

