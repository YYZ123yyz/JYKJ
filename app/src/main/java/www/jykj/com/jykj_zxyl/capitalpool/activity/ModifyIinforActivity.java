package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_enumtype.JumpTypeEnum;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.PasswordEditText;

/**
 * 医生解绑银行卡/微信/支付宝信息
 */
public class ModifyIinforActivity extends AbstractMvpBaseActivity<ModifyIinforContract.View
        , ModifyIinforPresenter> implements ModifyIinforContract.View {

    @BindView(R.id.password_et)
    PasswordEditText myEdittext;
    @BindView(R.id.operating_layout)
    LinearLayout operatingLayout;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.modify_password)
    TextView modifyPassword;
    @BindView(R.id.set_tv)
    TextView setTv;
    @BindView(R.id.msg_tv)
    TextView msgTv;
    private String targetActivity;//来源
    private int mType;
    private JYKJApplication mApp;
    private String bankcardCode ="";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_modifyinfor;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        myEdittext.setOnCompleteListener(password -> {
            if (mType== 0 ){
                Intent intent = new Intent(ModifyIinforActivity.this,
                        ModifyIinforAgainActivity.class);
                intent.putExtra("targetActivity",targetActivity);
                intent.putExtra("password",myEdittext.getText().toString());
                startActivity(intent);
            }else if (mType ==1){
                mPresenter.checkPassword(getParams());
            }else if (mType ==3){
                //解绑
                mPresenter.unBindCard(getParams());
            }

        });
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mType = getIntent().getIntExtra("type", 0);
        if (getIntent().hasExtra("bankcardCode")){
            bankcardCode = getIntent().getStringExtra("bankcardCode");
        }
        targetActivity=getIntent().getStringExtra("targetActivity");
        showOrHide();

    }

    private void showOrHide() {
        switch (mType) {
            case 0://设置密码
                setTv.setText("设置支付密码");
                msgTv.setText("请设置支付密码，用于支付验证");
                operatingLayout.setVisibility(View.GONE);
                break;
            case 1://检验密码
                setTv.setText("校验密码");
                msgTv.setText("请输入支付密码，以验证身份");
                operatingLayout.setVisibility(View.VISIBLE);
                break;
            case 2://修改密码
                setTv.setText("修改密码");
                msgTv.setText("请输入支付密码，以验证身份");
                operatingLayout.setVisibility(View.GONE);
                break;
            case 3://解绑银行卡
                setTv.setText("解绑银行卡");
                msgTv.setText("请输入支付密码，以验证身份");
                operatingLayout.setVisibility(View.GONE);
                break;
        }

    }

    @OnClick({R.id.bind_tv,R.id.forget_password,R.id.modify_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_tv:

                break;
            case R.id.forget_password:
                Intent intent = new Intent(ModifyIinforActivity.this, VerificationActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_password:
                startActivity(new Intent(ModifyIinforActivity.this,ModifyActivity.class));
                break;
        }

    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("bankcardCode", bankcardCode);
        stringStringHashMap.put("pwd", myEdittext.getText().toString());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @Override
    public void checkPasswordResult(boolean isSucess, String msg) {
        if (isSucess) {
            if (StringUtils.isNotEmpty(targetActivity)) {
                switch (targetActivity) {
                    case JumpTypeEnum
                            .JUMP_BALANCE_ACTIVITY://跳转余额页面
                        startActivity(BalanceActivity.class,null);
                        break;

                    default:
                }
            }
            this.finish();
        }else{
            ToastUtils.showShort(msg);
        }
    }

    @Override
    public void unBindSucess() {
        ToastUtils.showShort("解绑成功");
        finish();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }
}


