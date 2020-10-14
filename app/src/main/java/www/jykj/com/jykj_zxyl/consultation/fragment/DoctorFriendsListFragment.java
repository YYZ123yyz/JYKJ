package www.jykj.com.jykj_zxyl.consultation.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import entity.yhhd.ProvideDoctorGoodFriendGroup;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.HYHD_HYSQActivity;
import www.jykj.com.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.consultation.DoctorFriendsContract;
import www.jykj.com.jykj_zxyl.consultation.DoctorFriendsPresenter;
import www.jykj.com.jykj_zxyl.util.NestedExpandaleListView;

/**
 * Description:医生好友列表
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 11:08
 */
public class DoctorFriendsListFragment extends AbstractMvpBaseFragment<DoctorFriendsContract.View
        , DoctorFriendsPresenter> implements DoctorFriendsContract.View{
    @BindView(R.id.rv_list)
    NestedExpandaleListView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_fragmentHYHD_hysqImage)
    TextView ivHySqBtn;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private JYKJApplication mApp;
    private DorcerFriendExpandableListViewAdapter mDorcerFriendExpandableListViewAdapter;
    private List<ProvideDoctorGoodFriendGroup> groupList;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_doctor_friends;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        groupList=new ArrayList<>();
        mApp = (JYKJApplication) getActivity().getApplication();
        //初始化loading
        initLoadingAndRetryManager();
        //初始化Recyclerview
        initRecyclerView();
        //添加监听
        addListener();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendDoctorFriendsRequest(mApp.mViewSysUserDoctorInfoAndHospital
                .getDoctorId()+"",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
        });
        mLoadingLayout.showLoading();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        mDorcerFriendExpandableListViewAdapter =
                new DorcerFriendExpandableListViewAdapter(groupList, this.getContext(), mApp);
        rvList.setAdapter(mDorcerFriendExpandableListViewAdapter);
    }


    /**
     * 添加监听
     */
    private void addListener(){
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPresenter.sendDoctorFriendsRequest(mApp.mViewSysUserDoctorInfoAndHospital
                    .getDoctorId()+"",mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        });
        mRefreshLayout.setEnableLoadMore(false);
        ivHySqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HYHD_HYSQActivity.class,null);
            }
        });


        rvList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                groupList.get(groupPosition).setClick(!groupList.get(groupPosition).isClick());
                mDorcerFriendExpandableListViewAdapter.setDate(groupList);
                mDorcerFriendExpandableListViewAdapter.notifyDataSetInvalidated();
                mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
        rvList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent();
                intent.setClass(DoctorFriendsListFragment.this.getContext(), ChatActivity.class);
                intent.putExtra("userCode", groupList.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorCode());
                intent.putExtra("userName", groupList.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorUserName());
                //      Log.e("tag", "handleMessage:患者姓名3 "+ mClickInteractPatient.getPatientUserName());
                intent.putExtra("vedioNum", 1000000);
                intent.putExtra("voiceNum", 1000000);
                intent.putExtra("usersName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
                intent.putExtra("doctorUrl", groupList.get(i).getGroupLogoUrl());
                //intent.putExtra("patientAlias", mProvideDoctorPatientUserInfo.get(i).getUserNameAlias());
                intent.putExtra("patientCode", groupList.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorCode());

                //intent.putExtra("patientSex", mProvideDoctorPatientUserInfo.get(i).getGender());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void getDoctorFriendsResult(List<ProvideDoctorGoodFriendGroup>
                                                   list) {
        groupList.clear();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();

        if (!CollectionUtils.isEmpty(list)) {
            groupList.addAll(list);
            mDorcerFriendExpandableListViewAdapter.setDate(groupList);
            mDorcerFriendExpandableListViewAdapter.notifyDataSetChanged();
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
