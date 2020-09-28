package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.fragment;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.fillindetails.FillindetailsActivity;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryContract;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryPresenter;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter.DoctorAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.SlideRecyclerView;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.PatientRecordActivity;

public class DoctorFragment extends AbstractMvpBaseFragment<HistoryContract.View,
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
    private List<DoctorRecordBean> list;
    private String patientCode;

    public static DoctorFragment newInstance() {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchHistoryRequest(pageSize + "", pageIndex + "", mApp.loginDoctorPosition, "1", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(), mApp.mViewSysUserDoctorInfoAndHospital.getUserName(), "dafd840808d64027b64893eed11b97b8", "2");
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
    public void getSearchHistoryResult(List<DoctorRecordBean> providePatientConditionDiseaseRecordBeans) {
        list = new ArrayList<>();
        if (providePatientConditionDiseaseRecordBeans != null) {
            linData.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
            list.addAll(providePatientConditionDiseaseRecordBeans);
            doctorAdapter = new DoctorAdapter(list);
            rvList.setAdapter(doctorAdapter);
            doctorAdapter.setOnItemCoachingClickListener(new Rv_CoachingAdapter.OnItemCoachingClickListener() {
                @Override
                public void onClick(int position) {
                    int recordId = providePatientConditionDiseaseRecordBeans.get(position).getRecordId();
                    Intent intent = new Intent(getContext(), FillindetailsActivity.class);
                  //  Intent intent = new Intent(getContext(), PatientRecordActivity.class);
                    intent.putExtra("recordId", recordId+"");
                    startActivity(intent);
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
        return R.layout.layout_doctorfragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
     //   unbinder.unbind();
    }
}
