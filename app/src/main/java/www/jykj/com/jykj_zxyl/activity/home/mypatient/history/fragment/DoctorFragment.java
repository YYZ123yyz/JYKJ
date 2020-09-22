package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.PatientReviewActivity;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryContract;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryPresenter;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter.DoctorAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.SlideRecyclerView;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class DoctorFragment extends AbstractMvpBaseFragment<HistoryContract.View,
                HistoryPresenter> implements HistoryContract.View {

    @BindView(R.id.rv_list)
    SlideRecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private DoctorAdapter doctorAdapter;
    private List<ProvidePatientConditionDiseaseRecordBean> list;

    public static DoctorFragment newInstance() {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchHistoryRequest(pageSize+"",pageIndex+"",mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), "dafd840808d64027b64893eed11b97b8","2");
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mApp = (JYKJApplication) getActivity().getApplication();
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        rvList.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvList.setHasFixedSize(true);

    }

    @Override
    public void getSearchHistoryResult(List<ProvidePatientConditionDiseaseRecordBean> providePatientConditionDiseaseRecordBeans) {
        list = new ArrayList<>();
        if (providePatientConditionDiseaseRecordBeans!=null) {
            list.addAll(providePatientConditionDiseaseRecordBeans);
            doctorAdapter = new DoctorAdapter(list);
            rvList.setAdapter(doctorAdapter);
            doctorAdapter.setOnItemCoachingClickListener(new Rv_CoachingAdapter.OnItemCoachingClickListener() {
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

    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_doctorfragment;
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
