package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargeContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.RechargePresenter;


public class RechargeActivity extends AbstractMvpBaseActivity<RechargeContract.View
        , RechargePresenter> implements RechargeContract.View {

    @BindView(R.id.iv_weichat_choose)
    ImageView weichatIv;
    @BindView(R.id.iv_ali_choose)
    ImageView aliIv;
    private JYKJApplication mApp;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        mPresenter.getDocdorAsset(getParams());
        mPresenter.getCardList(getParams());
    }

    @OnClick({R.id.go2pay_tv, R.id.ali_layout, R.id.weichat_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go2pay_tv:


                startActivity(new Intent(RechargeActivity.this, WithdrawActivity.class));
                break;
            case R.id.ali_layout:
                weichatIv.setSelected(false);
                aliIv.setSelected(true);
                break;

            case R.id.weichat_layout:
                weichatIv.setSelected(true);
                aliIv.setSelected(false);
                break;
        }

    }

    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }
}

