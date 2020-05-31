package www.jykj.com.jykj_zxyl.activity.hzgl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.PhoneLoginActivity;
import www.jykj.com.jykj_zxyl.activity.UseRegistActivity;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_HZBQActivity;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_JBXXActivity;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_JWBSActivity;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.GRXX_GRZK_ZZXXActivity;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

/**
 * 患者管理 ==》 患者资料
 */
public class HZGLHZZLActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 HZGLHZZLActivity            mActivity;
    private ProvideViewPatientLablePunchClockState      mProvideViewPatientLablePunchClockState;
    private                 TextView                    mUserNameText;
    private                 LinearLayout                mJBXXLayout;                    //基本信息
    private                 LinearLayout                mBQJLLayout;                          //标签记录
    private                 LinearLayout                mZZXXLayout;                    //症状信息
    private                 LinearLayout                mJWBSLayout;                    //既往病史

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setStatusBar(mActivity);
        setContentView(R.layout.activity_fragmenthzgl_hzzl);
        mContext = this;
        mActivity = this;
        mProvideViewPatientLablePunchClockState = (ProvideViewPatientLablePunchClockState) getIntent().getSerializableExtra("patientInfo");
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        mUserNameText = (TextView)this.findViewById(R.id.tv_activityFragmentHzzl_userName);
        mUserNameText.setText("["+mProvideViewPatientLablePunchClockState.getUserName()+"]个人状况");

        mJBXXLayout = (LinearLayout)this.findViewById(R.id.tv_activityFragmentHzzl_JBXX);
        mJBXXLayout.setOnClickListener(new ButtonClick());

        mBQJLLayout = (LinearLayout)this.findViewById(R.id.li_activityHZGL_bqjl);
        mBQJLLayout.setOnClickListener(new ButtonClick());

        mZZXXLayout = (LinearLayout)this.findViewById(R.id.li_activityFragmentHZGL_zzxxLayout);
        mZZXXLayout.setOnClickListener(new ButtonClick());

        mJWBSLayout = (LinearLayout)this.findViewById(R.id.jwbs);
        mJWBSLayout.setOnClickListener(new ButtonClick());
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_activityFragmentHzzl_JBXX:
                    startActivity(new Intent(mContext,GRXX_GRZK_JBXXActivity.class).putExtra("patientCode",mProvideViewPatientLablePunchClockState.getPatientCode()));
                    break;
                case R.id.li_activityHZGL_bqjl:
                    startActivity(new Intent(mContext,GRXX_GRZK_HZBQActivity.class).putExtra("patientCode",mProvideViewPatientLablePunchClockState.getPatientCode()));
                    break;
                case R.id.li_activityFragmentHZGL_zzxxLayout:
                    startActivity(new Intent(mContext,GRXX_GRZK_ZZXXActivity.class).putExtra("patientCode",mProvideViewPatientLablePunchClockState.getPatientCode()));
                    break;
                case R.id.jwbs:
                    startActivity(new Intent(mContext,GRXX_GRZK_JWBSActivity.class).putExtra("patientCode",mProvideViewPatientLablePunchClockState.getPatientCode()));
                    break;
            }
        }
    }



}
