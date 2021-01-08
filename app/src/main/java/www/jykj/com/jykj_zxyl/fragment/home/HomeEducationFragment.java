package www.jykj.com.jykj_zxyl.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import util.CustomViewPager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.chapter.activity.VideoChapterActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.LivePlayerTwoActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.LivePublisherThreeActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.VideoDetialPlayerActivity;
import www.jykj.com.jykj_zxyl.activity.liveroom.LiveroomDetailActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HomeHealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MultiItemEntity;
import www.jykj.com.jykj_zxyl.app_base.base_html5.H5Activity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.interfaces.OnClickRelationContractListener;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.fragment.FragmentShouYe;
import www.jykj.com.jykj_zxyl.fragment.home.adapter.HealthEducationAdapter;
import www.jykj.com.jykj_zxyl.fragment.home.adapter.HealthEducationItemType;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

public class HomeEducationFragment extends AbstractMvpBaseFragment<HealthEducationContract.View,
        HealthEducationPresenter> implements HealthEducationContract.View {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private List<MultiItemEntity> mMultiItemEntitys;
    private HealthEducationAdapter mHealthEducationAdapter;
    private OnClickRelationContractListener onClickRelationContractListener;
    private JYKJApplication mApp;
    private String createDate="";
    private boolean isLoadMore=true;

    public void setOnClickRelationContractListener(OnClickRelationContractListener onClickRelationContractListener) {
        this.onClickRelationContractListener = onClickRelationContractListener;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_education;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mApp = (JYKJApplication)getActivity().getApplication();
        Fragment parentFragment = this.getParentFragment();
        if (parentFragment!=null) {
            FragmentShouYe fragmentShouYe = (FragmentShouYe) parentFragment;
            CustomViewPager pager = fragmentShouYe.getPager();
            pager.setObjectForPosition(view ,0);
        }

        mMultiItemEntitys=new ArrayList<>();
        //初始化RecyclerView
        initRecyclerView();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendListGetIndexHealthEducationRequest("0"
                ,createDate,pageSize,this.getActivity());
    }

    /**
     * 刷新数据
     */
    public void refreshData(){
        if (isAdded()) {
            createDate="";
            mPresenter.sendListGetIndexHealthEducationRequest("0"
                    ,createDate,pageSize,this.getActivity());
        }

    }

    /**
     * 加载更多
     */
    public void loadMoreData(){
        if (isAdded()) {
            if (isLoadMore) {
                mPresenter.sendListGetIndexHealthEducationRequest("0"
                        ,createDate,pageSize,this.getActivity());
            }else{
                if (onClickRelationContractListener!=null) {
                    onClickRelationContractListener.finishLoadMore();
                }
            }

        }

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mHealthEducationAdapter = new HealthEducationAdapter(this.getContext(), mMultiItemEntitys);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(mHealthEducationAdapter);
        mHealthEducationAdapter.setOnClickItemListener(pos -> {
            HomeHealthEducationBean healthEducationBean
                    = (HomeHealthEducationBean) mMultiItemEntitys.get(pos);
            int linkType = healthEducationBean.getLinkType();
            if (linkType==0) {
                String userCode = healthEducationBean.getUserCode();
                if(StringUtils.isNotEmpty(userCode)&&userCode.equals(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode())) {
                    Intent theintent = new Intent(mActivity, LivePublisherThreeActivity.class);
                    theintent.putExtra("detailCode", healthEducationBean.getRelationCode());
                    theintent.putExtra("pushUrl", healthEducationBean.getPushUrl());
                    theintent.putExtra("chatRoomName", healthEducationBean.getChatRoomCode());
                    theintent.putExtra("chatId", healthEducationBean.getChatRoomCode());
                    theintent.putExtra("liveTitle", healthEducationBean.getTitle());
                    theintent.putExtra("live_type", LivePublisherThreeActivity.LIVE_TYPE_HOTLIVE);
                    mActivity.startActivity(theintent);
                }else{
                    Intent parintent = new Intent(mActivity, LiveroomDetailActivity.class);
                    parintent.putExtra("detailCode",healthEducationBean.getRelationCode());
                    mActivity.startActivity(parintent);
                }
            }else if(linkType==1){
                Bundle bundle=new Bundle();
                bundle.putString("url",healthEducationBean.getLinkUrl());
                bundle.putString("title","图文");
                startActivity(H5Activity.class,bundle);
            }else if(linkType==2){
                Bundle bundle=new Bundle();
                bundle.putString("courseWareCode",healthEducationBean.getRelationCode());
                startActivity(VideoChapterActivity.class,bundle);
            }

        });
    }



    @Override
    public void getIndexHealthEducationResult(List<HomeHealthEducationBean> list) {
        if(TextUtils.isEmpty(createDate)){
            mMultiItemEntitys.clear();
            if (onClickRelationContractListener!=null) {
                onClickRelationContractListener.finishRefresh();
            }

        }
        if (!CollectionUtils.isEmpty(list)) {
            mMultiItemEntitys.addAll(list);
            handleData(mMultiItemEntitys);
            mHealthEducationAdapter.setDatas(mMultiItemEntitys);
            mHealthEducationAdapter.notifyDataSetChanged();
            if (onClickRelationContractListener!=null) {
                onClickRelationContractListener.finishLoadMore();
            }
            if (list.size()<pageSize) {
                isLoadMore=false;
            }

            HomeHealthEducationBean homeHealthEducationBean = list.get(list.size() - 1);
            createDate=DateUtils.getDateToStringYYYMMDDHHMMSS(homeHealthEducationBean.getCreatetime());

        } else {
            if (TextUtils.isEmpty(createDate)) {

                //mLoadingLayout.showEmpty();
            } else {
                if (onClickRelationContractListener!=null) {
                    onClickRelationContractListener.finishLoadMore();
                }
            }
        }
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        if (onClickRelationContractListener!=null) {
            onClickRelationContractListener.finishLoadMore();
        }
    }

    /**
     * 处理数据
     *
     * @param list 数据列表
     */
    private void handleData(List<MultiItemEntity> list) {
        for (MultiItemEntity multiItemEntity : list) {
            HomeHealthEducationBean healthEducationBean
                    = (HomeHealthEducationBean) multiItemEntity;
            int flagContentType = healthEducationBean.getType();
            switch (flagContentType) {
                case 1:
                    healthEducationBean.setItemType(HealthEducationItemType.MULTIPLE_VIDEO_TYPE);
                    break;
                case 2:
                    healthEducationBean.setItemType(HealthEducationItemType.MULTIPLE_AUDIO_TYPE);
                    break;
                case 3:
                    healthEducationBean.setItemType(HealthEducationItemType.MULTIPLE_PICTURE_TEXT_TYPE);
                    break;
//                case 4:
//                    healthEducationBean.setItemType(HealthEducationItemType.MULTIPLE_COURSE_WARE);
//                    break;
                default:
            }
        }
    }


}
