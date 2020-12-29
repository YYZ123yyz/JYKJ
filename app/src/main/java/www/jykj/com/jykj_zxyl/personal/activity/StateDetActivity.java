package www.jykj.com.jykj_zxyl.personal.activity;


import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.personal.StateDetContract;
import www.jykj.com.jykj_zxyl.personal.StateDetPresenter;
import www.jykj.com.jykj_zxyl.personal.bean.StateDetBean;


public class StateDetActivity extends AbstractMvpBaseActivity<StateDetContract.View
        , StateDetPresenter> implements StateDetContract.View {

    private String patentCode;
    private String patentName;
    private String linkPhone;
    private JYKJApplication mApp;
    @BindView(R.id.classify_name)
    TextView classifyName;
    @BindView(R.id.factors_content)
    TextView factorsContent;
    @BindView(R.id.organ_content)
    TextView organContent;
    @BindView(R.id.disease_content)
    TextView diseaseContent;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.right_image_search)
    ImageButton imageButtonE;
    private String htnLClassifyLevelImgUrl;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_state_det;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
    }

    private void setToolBar() {
        toolbar.setMainTitle("状态详情");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        //add
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(StateDetActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();

        patentCode = getIntent().getStringExtra("patentCode");
        patentName = getIntent().getStringExtra("patentName");
        linkPhone = getIntent().getStringExtra("linkPhone");

        mPresenter.getStateDet(getParams());
    }

    private String getParams() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        map.put("requestClientType", "1");
        map.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        map.put("searchPatientCode", patentCode);

        map.put("searchPatientName", patentName);
        map.put("searchPatientLinkPhone", linkPhone);//


        return RetrofitUtil.encodeParam(map);
    }


    @OnClick({R.id.look_im})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look_im:
                if (htnLClassifyLevelImgUrl != null) {
                    Intent intent = new Intent(StateDetActivity.this, PreviewImageAvitity.class);
                    intent.putExtra("url", htnLClassifyLevelImgUrl);
                    startActivity(intent);
                }

                break;
        }

    }

    @Override
    public void getDetSucess(StateDetBean data) {
        classifyName.setText(data.getHtnClassifyName());
        factorsContent.setText(data.getRiskFactorsContent());
        organContent.setText(data.getOrganNumContent());
        diseaseContent.setText(data.getDiseaseNumContent());
        htnLClassifyLevelImgUrl = data.getHtnLClassifyLevelImgUrl();
    }

    @Override
    public void showMsg(String msg) {

    }
}

