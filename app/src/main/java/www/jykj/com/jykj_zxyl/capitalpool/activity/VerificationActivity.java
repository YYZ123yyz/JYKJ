package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.VerificationPresenter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawPresenter;

public class VerificationActivity extends AbstractMvpBaseActivity<VerificationContract.View
        , VerificationPresenter> implements VerificationContract.View {



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
                startActivity(new Intent(VerificationActivity.this,ModifyIinforActivity.class));
                break;
        }

    }
}


