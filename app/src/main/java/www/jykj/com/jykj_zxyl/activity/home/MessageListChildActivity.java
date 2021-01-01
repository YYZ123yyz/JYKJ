package www.jykj.com.jykj_zxyl.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.contract.MessageListContract;
import www.jykj.com.jykj_zxyl.activity.home.contract.MessageListPresenter;
import www.jykj.com.jykj_zxyl.activity.home.news.MessageDetialActivity;
import www.jykj.com.jykj_zxyl.adapter.MessageChildListAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MessageListChildBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 14:19
 */
public class MessageListChildActivity extends AbstractMvpBaseActivity<MessageListContract.View,
        MessageListPresenter> implements MessageListContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private MessageChildListAdapter messageChildListAdapter;
    private List<MessageListChildBean> msgList;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private String messageType;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_message_list_child;
    }


    @Override
    protected void initView() {
        super.initView();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
             messageType = extras.getString("messageType");
        }
        msgList=new ArrayList<>();
        initLoadingAndRetryManager();
        setToolBar();
        addListener();
        initRecyclerView();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("消息");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleSearchBtnVisible(false);
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mPresenter.sendMessageListRequest(messageType,"-1"
                    ,pageSize,pageIndex,this);
        });
        mLoadingLayout.showLoading();
    }

    /**
     * 添加监听
     */
    private void addListener() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            pageIndex = 1;
            mPresenter.sendMessageListRequest(messageType,"-1"
                    ,pageSize,pageIndex,this);
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                mPresenter.sendMessageListRequest(messageType,"-1"
                        ,pageSize,pageIndex,MessageListChildActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendMessageListRequest(messageType,"-1"
                ,pageSize,pageIndex,this);
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        messageChildListAdapter=new MessageChildListAdapter(R.layout.item_message_list,msgList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(messageChildListAdapter);
        messageChildListAdapter.setOnClickItemListener(new MessageChildListAdapter.OnClickItemListener() {
            @Override
            public void onClickItemPos(MessageListChildBean item) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("result",item);
                startActivity(MessageDetialActivity.class,bundle,1000);
            }
        });

    }



    @Override
    public void showLoading(int code) {

    }


    @Override
    public void getMessageListResult(List<MessageListChildBean> messageListChildBeans) {
        mLoadingLayout.showContent();
        if(pageIndex==1){
            msgList.clear();
            mRefreshLayout.finishRefresh();
        }
        mRefreshLayout.finishLoadMore();
        if (!CollectionUtils.isEmpty(messageListChildBeans)) {

            msgList.addAll(messageListChildBeans);
            messageChildListAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayout.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
    }


    @Override
    public void showEmpty() {
        super.showEmpty();
        mRefreshLayout.finishLoadMore();
        if (pageIndex==1) {
            mLoadingLayout.showEmpty();
        }


    }

    @Override
    public void showRetry() {
        super.showRetry();
        mRefreshLayout.finishLoadMore();
        if(pageIndex==1){
            mLoadingLayout.showError();
        }
    }

}
