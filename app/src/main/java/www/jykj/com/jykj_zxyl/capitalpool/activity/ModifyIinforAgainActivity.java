package www.jykj.com.jykj_zxyl.capitalpool.activity;


import android.text.TextUtils;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import com.blankj.utilcode.util.ToastUtils;

import java.util.HashMap;


import butterknife.BindView;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;

import www.jykj.com.jykj_zxyl.app_base.base_enumtype.JumpTypeEnum;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityStackManager;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforAgainContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforAgainPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.PasswordEditText;


public class ModifyIinforAgainActivity extends AbstractMvpBaseActivity<ModifyIinforAgainContract.View
        , ModifyIinforAgainPresenter> implements ModifyIinforAgainContract.View {

    private JYKJApplication mApp;
    private String password;
    @BindView(R.id.password_et)
    PasswordEditText myEdittext;
    @BindView(R.id.bind_tv)
    TextView bindTv;
    private String targetActivity;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_modifyinfor_again;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        myEdittext.setOnCompleteListener(password -> {
            bindTv.setTextColor(getResources().getColor(R.color.writeColor));
            bindTv.setClickable(true);
            bindTv.setBackgroundResource(R.drawable.bg_round_7a9eff_15);
        });
        myEdittext.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                bindTv.setClickable(false);
                bindTv.setTextColor(getResources().getColor(R.color.color_999999));
                bindTv.setBackgroundResource(R.drawable.bg_round_eeeeee_5);
            }
            return false;
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        password = getIntent().getStringExtra("password");
        targetActivity=getIntent().getStringExtra("targetActivity");
    }

    @OnClick({R.id.bind_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_tv:
                if (TextUtils.isEmpty(myEdittext.getText().toString())) {
                    ToastUtils.showShort("请输入密码");
                    return;
                } else {
                    if (myEdittext.getText().toString().length() < 6) {
                        ToastUtils.showShort("请输入完整密码");
                        return;
                    } else {
                        if (password.equals(myEdittext.getText().toString())) {

                            mPresenter.setPassword(getParams());
                        } else {
                            ToastUtils.showShort("两次密码输入不一致");
                            return;
                        }
                    }
                }
                break;
        }

    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("pwd", password);
        stringStringHashMap.put("pwdQ", myEdittext.getText().toString());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @Override
    public void setPasswordSucess(String msg) {
        //ToastUtils.showShort(msg);
        if (StringUtils.isNotEmpty(targetActivity)) {
            switch (targetActivity) {
                case JumpTypeEnum
                        .JUMP_BALANCE_ACTIVITY://跳转余额页面
                    startActivity(BalanceActivity.class,null);
                    break;

                    default:
            }

        }
        finish();
        ActivityStackManager.getInstance().finish(ModifyIinforActivity.class);
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }
}


