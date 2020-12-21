package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawPresenter;

public class WithdrawActivity extends AbstractMvpBaseActivity<WithdrawContract.View
        , WithdrawPresenter> implements WithdrawContract.View {



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

    }



    @OnClick({R.id.go2pay_tv})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.go2pay_tv:
                startActivity(new Intent(WithdrawActivity.this,AddBankcardActivity.class));
                break;
        }

    }
}

