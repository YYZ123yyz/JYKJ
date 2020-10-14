package www.jykj.com.jykj_zxyl.consultation.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import entity.DoctorInfo.InteractPatient;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.consultation.SignUpPatientListContract;
import www.jykj.com.jykj_zxyl.consultation.SignUpPatientListPresenter;

/**
 * Description:签约患者
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 11:07
 */
public class SignUpPatientListFragment extends AbstractMvpBaseFragment<SignUpPatientListContract.View
        , SignUpPatientListPresenter> implements SignUpPatientListContract.View{

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private MessageInfoRecycleAdapter mMessageInfoRecycleAdapter;//消息列表适配器
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private List<InteractPatient> mInteractPatients;//数据列表

    private JYKJApplication mApp;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mInteractPatients=new ArrayList<>();
        mApp = (JYKJApplication) getActivity().getApplication();
        //初始化Loading加载
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendGetSignUpPatientRequest(mApp.mViewSysUserDoctorInfoAndHospital
                .getDoctorId()+"",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mPresenter.sendGetSignUpPatientRequest(mApp.mViewSysUserDoctorInfoAndHospital
                    .getDoctorId()+"",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        });
        mLoadingLayout.showLoading();
    }

    /**
     * 初始化Recyclerview
     */
    private void initRecyclerView(){
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageInfoRecycleAdapter=new MessageInfoRecycleAdapter(mApp,mInteractPatients,this.getContext());
        rvList.setAdapter(mMessageInfoRecycleAdapter);
        mMessageInfoRecycleAdapter.setOnItemClickListener(new MessageInfoRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                InteractPatient mClickInteractPatient = mInteractPatients.get(position);
                mInteractPatients.get(position).setNoRead(false);
                Intent intent = new Intent();
                intent.setClass(Objects.requireNonNull(SignUpPatientListFragment.this.getContext()), ChatActivity.class);
                intent.putExtra("userCode", mClickInteractPatient.getPatientCode());
                intent.putExtra("userName", mClickInteractPatient.getPatientUserName());
                //     Log.e("tag", "handleMessage:患者姓名2 "+ mClickInteractPatient.getPatientUserName());
                intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                intent.putExtra("doctorUrl", mClickInteractPatient.getPatientUserLogoUrl());
                // intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(position).getUserNameAlias());
                intent.putExtra("patientCode", mClickInteractPatient.getPatientCode());

                // intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(position).getGender());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    /**
     * 添加监听
     */
    private void addListener(){
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPresenter.sendGetSignUpPatientRequest(mApp.mViewSysUserDoctorInfoAndHospital
                    .getDoctorId()+"",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        });
        mRefreshLayout.setEnableLoadMore(false);

    }


    @Override
    public void getSignUpPatientResult(List<InteractPatient> list) {
        mInteractPatients.clear();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setType("user");
        }
        if (!CollectionUtils.isEmpty(list)) {

            mInteractPatients.addAll(list);
            mMessageInfoRecycleAdapter.setDatas(mInteractPatients);
            mMessageInfoRecycleAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayout.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayout.showContent();
    }


    @Override
    public void showEmpty() {
        super.showEmpty();
        mLoadingLayout.showEmpty();
    }

    @Override
    public void showRetry() {
        super.showRetry();
        mLoadingLayout.showError();
    }


}

