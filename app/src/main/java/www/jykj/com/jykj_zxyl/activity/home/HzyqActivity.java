package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 消息=》患者就诊=》会诊邀请
 */
public class HzyqActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hzyq);
        ActivityUtil.setStatusBarMain(HzyqActivity.this);

        initView();
        initListener();
    }

    private void initView(){
        llBack = (LinearLayout) findViewById(R.id.ll_back);
    }

    private void initListener(){
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
