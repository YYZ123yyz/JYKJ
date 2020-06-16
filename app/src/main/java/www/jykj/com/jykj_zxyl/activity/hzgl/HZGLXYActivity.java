package www.jykj.com.jykj_zxyl.activity.hzgl;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.custom.ChartView;
import www.jykj.com.jykj_zxyl.fragment.hzgl.BloodLogFragment;
import www.jykj.com.jykj_zxyl.fragment.hzgl.BloodRulesFragment;
import www.jykj.com.jykj_zxyl.fragment.hzgl.TrendFragment;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class HZGLXYActivity extends AppCompatActivity {

    private Context mContext;
    private HZGLXYActivity mActivity;
    private TextView mXYQST;            //血压趋势图
    private TextView mXYRZ;              //血压日志
    private TextView mXYGZ;              //血压规则

    private FragmentManager fragmentManager;
    private TrendFragment trendFragment;
    private BloodLogFragment bloodLogFragment;
    private BloodRulesFragment bloodRulesFragment;
    private ProvideViewPatientLablePunchClockState mData;
    private LinearLayout ll_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthzgl_xy);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        setIndex(0);

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mXYQST = (TextView) this.findViewById(R.id.tv_activityHZGLXY_xyQST);
        mXYRZ = (TextView) this.findViewById(R.id.tv_activityHZGLXY_xyRZ);
        mXYGZ = (TextView) this.findViewById(R.id.tv_activityHZGLXY_xygz);
        fragmentManager = getFragmentManager();

        mXYQST.setOnClickListener(new ButtonClick());
        mXYRZ.setOnClickListener(new ButtonClick());
        mXYGZ.setOnClickListener(new ButtonClick());
        if (getIntent() != null) {
            mData = (ProvideViewPatientLablePunchClockState) getIntent().getSerializableExtra("patientLable");
        }

    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_activityHZGLXY_xyQST:
                    //还原默认
                    setTextDefault();
                    setIndex(0);
                    mXYQST.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mXYQST.setTextColor(getResources().getColor(R.color.tabColor_nomal));
                    break;
                case R.id.tv_activityHZGLXY_xyRZ:
                    //还原默认
                    setTextDefault();
                    setIndex(1);
                    mXYRZ.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mXYRZ.setTextColor(getResources().getColor(R.color.tabColor_nomal));
                    break;
                case R.id.tv_activityHZGLXY_xygz:
                    //还原默认
                    setTextDefault();
                    setIndex(2);
                    mXYGZ.setBackgroundResource(R.mipmap.pg_messagetitle);
                    mXYGZ.setTextColor(getResources().getColor(R.color.tabColor_nomal));
                    break;
            }
        }
    }

    /**
     * 去掉TextView背景
     */
    private void setTextDefault() {
        mXYQST.setBackgroundResource(0);
        mXYQST.setTextColor(getResources().getColor(R.color.writeColor));
        mXYRZ.setBackgroundResource(0);
        mXYRZ.setTextColor(getResources().getColor(R.color.writeColor));
        mXYGZ.setBackgroundResource(0);
        mXYGZ.setTextColor(getResources().getColor(R.color.writeColor));
    }


    private void setIndex(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:

                if (trendFragment == null) {
                    trendFragment = new TrendFragment();
                    transaction.add(R.id.content, trendFragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("patientLable", mData);
                    trendFragment.setArguments(bundle);
                } else {
                    transaction.show(trendFragment);
                }
                break;
            case 1:
                if (bloodLogFragment == null) {
                    bloodLogFragment = new BloodLogFragment();
                    transaction.add(R.id.content, bloodLogFragment);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("patientLable", mData);
                    bloodLogFragment.setArguments(bundle);
                } else {
                    transaction.show(bloodLogFragment);
                }

                break;
            case 2:
                if (bloodRulesFragment == null) {
                    bloodRulesFragment = new BloodRulesFragment();
                    transaction.add(R.id.content, bloodRulesFragment);
                } else {
                    transaction.show(bloodRulesFragment);
                }
                break;

        }

        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (trendFragment != null) {
            transaction.hide(trendFragment);
        }
        if (bloodLogFragment != null) {
            transaction.hide(bloodLogFragment);
        }
        if (bloodRulesFragment != null) {
            transaction.hide(bloodRulesFragment);
        }

    }


}
