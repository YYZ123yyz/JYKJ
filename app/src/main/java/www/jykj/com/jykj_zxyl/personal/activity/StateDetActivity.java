package www.jykj.com.jykj_zxyl.personal.activity;


import android.content.Intent;
import android.view.View;


import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.personal.StateDetContract;
import www.jykj.com.jykj_zxyl.personal.StateDetPresenter;



public class StateDetActivity extends AbstractMvpBaseActivity<StateDetContract.View
        , StateDetPresenter> implements StateDetContract.View {



    @Override
    protected int setLayoutId() {
        return R.layout.activity_state_det;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();

    }



    @OnClick({R.id.look_im})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.look_im:
                startActivity(new Intent(StateDetActivity.this,PreviewImageAvitity.class));
                break;
        }

    }
}

