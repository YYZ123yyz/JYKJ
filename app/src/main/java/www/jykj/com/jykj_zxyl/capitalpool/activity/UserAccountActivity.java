package www.jykj.com.jykj_zxyl.capitalpool.activity;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.capitalpool.adapter.UserAccountAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.UserAccountPresenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class UserAccountActivity extends AbstractMvpBaseActivity<UserAccountContract.View
        , UserAccountPresenter> implements UserAccountContract.View {

    @BindView(R.id.recycleview)
    RecyclerView myRecyleview;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_account;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        myRecyleview.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            strings.add("sss");

        }
        UserAccountAdapter userAccountAdapter = new UserAccountAdapter(R.layout.item_account, strings);
        myRecyleview.setAdapter(userAccountAdapter);
    }


    @OnClick({R.id.weichat_rela})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weichat_rela:
                startActivity(new Intent(UserAccountActivity.this, CollectionCodeActivity.class));
                break;
        }

    }
}

