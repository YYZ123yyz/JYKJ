package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class MySurplusActivity extends AppCompatActivity {

    private                 TextView                    mPhoneLogin;                //手机号登录
    private                 TextView                    mUseRegist;                 //用户注册
    private                 Button                      mLogin;                     //登录
    private                 Context                     mContext;
    private                 MySurplusActivity           mActivity;
    private                 TextView                    mDetail;                        //明细

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_mysurplus);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mDetail = (TextView)this.findViewById(R.id.activity_mySurPlus_detail);
        mDetail.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_mySurPlus_detail:
                    Intent intent = new Intent();
                    intent.setClass(MySurplusActivity.this,SurplusDetailActivity.class);
                    startActivity(intent);

            }
        }
    }



}
