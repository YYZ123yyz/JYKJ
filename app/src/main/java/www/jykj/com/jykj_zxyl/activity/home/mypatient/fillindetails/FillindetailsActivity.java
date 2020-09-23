package www.jykj.com.jykj_zxyl.activity.home.mypatient.fillindetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation.BasicInformationContract;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation.BasicInformationContractPresenter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ViewInteractOrderMedicalRecordDetailBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * 医生填写详情
 */
public class FillindetailsActivity extends AbstractMvpBaseActivity<FillindetailsContract.View,
        FillindetailsPresenter> implements FillindetailsContract.View {
    private JYKJApplication mApp;
    private String recordId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fillindetails;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchFillindetailsRequest(mApp.loginDoctorPosition,"1",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),mApp.mViewSysUserDoctorInfoAndHospital.getUserName(),recordId);
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        Intent intent = getIntent();
        recordId = intent.getStringExtra("recordId");
    }

    @Override
    public void getSearchFillindetailsResult(List<ViewInteractOrderMedicalRecordDetailBean> viewInteractOrderMedicalRecordDetailBeans) {

    }

    @Override
    public void showLoading(int code) {

    }
}