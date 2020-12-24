package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.weiget.PasswordEditText;

public class ModifyActivity extends BaseActivity {

    @BindView(R.id.password_et)
    PasswordEditText etPassword;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void initView() {
        super.initView();
        etPassword.setOnCompleteListener(new PasswordEditText.OnPasswordCompleteListener() {
            @Override
            public void onComplete(String password) {
                Intent intent = new Intent(ModifyActivity.this, ModifyIinforAgainActivity.class);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });
    }
}
