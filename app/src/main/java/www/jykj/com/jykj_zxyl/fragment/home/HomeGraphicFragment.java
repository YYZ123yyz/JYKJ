package www.jykj.com.jykj_zxyl.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import util.CustomViewPager;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MultiItemEntity;
import www.jykj.com.jykj_zxyl.app_base.base_html5.H5Activity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.interfaces.OnClickRelationContractListener;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.fragment.FragmentShouYe;
import www.jykj.com.jykj_zxyl.fragment.home.adapter.HealthEducationAdapter;
import www.jykj.com.jykj_zxyl.fragment.home.adapter.HealthEducationItemType;

public class HomeGraphicFragment extends AbstractMvpBaseFragment<HealthEducationContract.View,
        HealthEducationPresenter> implements HealthEducationContract.View {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private List<MultiItemEntity> mMultiItemEntitys;
    private HealthEducationAdapter mHealthEducationAdapter;
    private OnClickRelationContractListener onClickRelationContractListener;
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
        Fragment parentFragment = this.getParentFragment();
        if (parentFragment!=null) {
            FragmentShouYe fragmentShouYe = (FragmentShouYe) parentFragment;
            CustomViewPager pager = fragmentShouYe.getPager();
            pager.setObjectForPosition(view ,3);
        }
        mMultiItemEntitys=new ArrayList<>();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchIndexHealthEducationRequest("1","3"
                ,pageSize,pageIndex,this.getActivity());
    }

    /**
     * 刷新数据
     */
    public void refreshData(){
        if (isAdded()) {
            pageIndex=1;
            mPresenter.sendSearchIndexHealthEducationRequest("1","3"
                    ,pageSize,pageIndex,this.getActivity());
        }

    }

    /**
     * 加载更多
     */
    public void loadMoreData(){
        if (isAdded()) {
            pageIndex++;
            mPresenter.sendSearchIndexHealthEducationRequest("1","3"
                    ,pageSize,pageIndex,this.getActivity());
        }

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mHealthEducationAdapter=new HealthEducationAdapter(this.getContext(),mMultiItemEntitys);
        mHealthEducationAdapter.setOnClickItemListener(new HealthEducationAdapter.OnClickItemListener() {
            @Override
            public void onClickItemPos(int pos) {
                HealthEducationBean healthEducationBean = (HealthEducationBean) mMultiItemEntitys.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("url",healthEducationBean.getImageTextWatchUrl());
                bundle.putString("title","图文");
                int flagImageTextVisitType = healthEducationBean.getFlagImageTextVisitType();
                if (flagImageTextVisitType==1) {
                    bundle.putString("imageTextCode",healthEducationBean.getImageTextCode());
                }
                startActivity(H5Activity.class,bundle);
            }
        });
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(mHealthEducationAdapter);
    }





    @Override
    public void getSearchIndexHealthEducationResult(List<HealthEducationBean> list) {
        if(pageIndex==1){
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

        } else {
            if (pageIndex == 1) {

                //mLoadingLayout.showEmpty();
            } else {
                if (onClickRelationContractListener!=null) {
                    onClickRelationContractListener.finishLoadMore();
                }
            }
        }
    }


    private void handleData(List<MultiItemEntity> list){
        for (MultiItemEntity multiItemEntity : list) {
            HealthEducationBean healthEducationBean
                    = (HealthEducationBean) multiItemEntity;
            int flagContentType = healthEducationBean.getFlagContentType();
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
                default:
            }
        }
    }


}

