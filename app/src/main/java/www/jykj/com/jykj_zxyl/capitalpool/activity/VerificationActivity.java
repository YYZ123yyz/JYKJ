package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawPresenter;

public class VerificationActivity extends AbstractMvpBaseActivity<VerificationContract.View
        , VerificationPresenter> implements VerificationContract.View {


    @BindView(R.id.et_name)
    EditText etName;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();

    }



    @OnClick({R.id.next_tv})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.next_tv:
                checkInfo();
                startActivity(new Intent(VerificationActivity.this,ModifyIinforActivity.class));
                break;
        }

    }

    private void checkInfo() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())){
            ToastUtils.showShort("请输入名字");
            return;
        }


    }
}


