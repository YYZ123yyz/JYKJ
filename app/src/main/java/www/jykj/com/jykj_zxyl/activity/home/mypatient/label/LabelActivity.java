package www.jykj.com.jykj_zxyl.activity.home.mypatient.label;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientLabelBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.SlideRecyclerView;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class LabelActivity extends AbstractMvpBaseActivity<LabelContract.View,
        LabelPresenter> implements LabelContract.View {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.li_back)
    LinearLayout liBack;
    @BindView(R.id.label_rv)
    SlideRecyclerView labelRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lin_data)
    LinearLayout linData;
    @BindView(R.id.tv_none)
    TextView tvNone;
    private JYKJApplication mApp;
    private String mPatientCode;                       //患者code
    private List<MultiItemEntity> mMultiItemEntitys;//多布局内容列表
    private LinearLayoutManager layoutManager;
    private LabelAdapter mLabelAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_label;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        mPatientCode = getIntent().getStringExtra("patientCode");
        mMultiItemEntitys = new ArrayList<>();

        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        labelRv.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        labelRv.setHasFixedSize(true);

        addOnClick();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchLabelRequest(pageSize + "", pageIndex + "", mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), mPatientCode);
    }

    /**
     * 点击事件
     */
    private void addOnClick() {
        liBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getSearchLabelResult(List<ProvidePatientLabelBean> providePatientLabelBean) {
        if (providePatientLabelBean != null) {
            linData.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
            mLabelAdapter = new LabelAdapter(providePatientLabelBean);
            labelRv.setAdapter(mLabelAdapter);
        }
    }

    @Override
    public void getSearchLabelResultError(String msg) {
        linData.setVisibility(View.GONE);
        tvNone.setVisibility(View.VISIBLE);
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}