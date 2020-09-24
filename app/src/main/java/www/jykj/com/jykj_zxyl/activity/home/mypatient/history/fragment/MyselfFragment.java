package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryContract;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryPresenter;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter.DoctorAdapter;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter.MyselfAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.SlideRecyclerView;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class MyselfFragment extends AbstractMvpBaseFragment<HistoryContract.View,
        HistoryPresenter> implements HistoryContract.View {


    @BindView(R.id.rv_list)
    SlideRecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    @BindView(R.id.lin_data)
    LinearLayout linData;
    @BindView(R.id.tv_none)
    TextView tvNone;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private DoctorAdapter doctorAdapter;
    private MyselfAdapter doctorAdapter1;
    private String patientCode;

    public static MyselfFragment newInstance() {
        MyselfFragment fragment = new MyselfFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mApp = (JYKJApplication) getActivity().getApplication();
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        patientCode = mSharedPreferences.getString("patientCode", "");
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        rvList.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvList.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchHistoryRequest(pageSize + "", pageIndex + "", mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), patientCode, "1");

    }

    @Override
    public void getSearchHistoryResult(List<DoctorRecordBean> doctorRecordBeans) {
        if (doctorRecordBeans != null) {
            linData.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
            doctorAdapter1 = new MyselfAdapter(doctorRecordBeans);
            rvList.setAdapter(doctorAdapter1);
            doctorAdapter1.setOnItemCoachingClickListener(new Rv_CoachingAdapter.OnItemCoachingClickListener() {
                @Override
                public void onClick(int position) {

                }

                @Override
                public void onLongClick(int position) {

                }
            });
        }
    }

    @Override
    public void getSearchHistoryResultError(String msg) {
        linData.setVisibility(View.GONE);
        tvNone.setVisibility(View.VISIBLE);
        ToastUtils.showToast(msg);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_myselffragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
