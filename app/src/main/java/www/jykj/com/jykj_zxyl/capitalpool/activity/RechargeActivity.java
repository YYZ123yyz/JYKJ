package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargeContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargePresenter;


public class RechargeActivity extends AbstractMvpBaseActivity<RechargeContract.View
        , RechargePresenter> implements RechargeContract.View {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();

    }


    @OnClick({R.id.go2pay_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go2pay_tv:
                startActivity(new Intent(RechargeActivity.this, WithdrawActivity.class));
                break;
        }

    }
}

