package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawDetAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetPresenter;

public class WithdrawDetActivity extends AbstractMvpBaseActivity<WithdrawDetContract.View
        , WithdrawDetPresenter> implements WithdrawDetContract.View {

    @BindView(R.id.my_recycleview)
    RecyclerView myRecyeleview;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_withdraw_det;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        myRecyeleview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            strings.add("xx");
        }
        WithdrawDetAdapter withdrawDetAdapter = new WithdrawDetAdapter(R.layout.item_withdraw_det, strings);
        myRecyeleview.setAdapter(withdrawDetAdapter);

    }


    @OnClick({R.id.withdraw_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_tv:
                startActivity(new Intent(WithdrawDetActivity.this, UserAccountActivity.class));
                break;
        }

    }
}


