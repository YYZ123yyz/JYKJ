package www.jykj.com.jykj_zxyl.activity.myself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 我的服务权限
 */
public class PlatformLicenseActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private PlatformLicenseActivity mActivity;
    private                 LinearLayout                mImageTextLayout;                   //图文就诊布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_platformlicenses);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setData();
    }



    /**
     * 初始化布局
     */
    private void initLayout() {
        mImageTextLayout = (LinearLayout)this.findViewById(R.id.li_activityServicePermision_ImageText);
        mImageTextLayout.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.li_activityServicePermision_ImageText:
                    Intent intent = new Intent();
                    intent.setClass(mContext,ServicePermisionSetActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {

    }



}
