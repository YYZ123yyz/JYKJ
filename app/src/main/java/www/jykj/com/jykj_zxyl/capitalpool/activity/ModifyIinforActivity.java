package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.ModifyIinforPresenter;

public class ModifyIinforActivity extends AbstractMvpBaseActivity<ModifyIinforContract.View
        , ModifyIinforPresenter> implements ModifyIinforContract.View {


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

    }


    @OnClick({R.id.bind_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_tv:
//                startActivity(new Intent(CollectionCodeActivity.this, VerificationActivity.class));
                break;
        }

    }
}


