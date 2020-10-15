package www.jykj.com.jykj_zxyl.consultation.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.MainMessage;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import entity.DoctorInfo.InteractPatient;
import entity.basicDate.EMMessageEntity;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.consultation.MessageListContract;
import www.jykj.com.jykj_zxyl.consultation.MessageListPresenter;
import www.jykj.com.jykj_zxyl.consultation.utils.ConvertUtils;

/**
 * Description:消息列表
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 11:05
 */
public class MessageListFragment extends AbstractMvpBaseFragment<MessageListContract.View
        , MessageListPresenter> implements MessageListContract.View {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private MessageInfoRecycleAdapter mMessageInfoRecycleAdapter;//消息列表适配器
    private List<InteractPatient> mInteractPatients;//数据列表
    private JYKJApplication mApp;
    private Context mContext;
    private InteractPatient mClickInteractPatient;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        EventBus.getDefault().register(this);
        mContext=this.getContext();
        mApp = (JYKJApplication) Objects.requireNonNull(getActivity()).getApplication();
        mInteractPatients=new ArrayList<>();
        //初始化loading
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();

    }


    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.sendMessageListRequest(ConvertUtils.getConsultationUserList());
        mMessageInfoRecycleAdapter.notifyDataSetChanged();
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
    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mMessageInfoRecycleAdapter = new MessageInfoRecycleAdapter(mApp, mInteractPatients,
                this.getContext());
        rvList.setAdapter(mMessageInfoRecycleAdapter);
        mMessageInfoRecycleAdapter.setOnItemClickListener(new MessageInfoRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mMessageInfoRecycleAdapter.setOnItemClickListener(new MessageInfoRecycleAdapter.OnItemClickListener() {

                    @Override
                    public void onClick(int position) {
                        mClickInteractPatient = mInteractPatients.get(position);
                        mInteractPatients.get(position).setNoRead(false);
                        Intent intent = new Intent();
                        intent.setClass(mContext, ChatActivity.class);
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
            mPresenter.sendMessageListRequest(ConvertUtils.getConsultationUserList());
        });
        mRefreshLayout.setEnableLoadMore(false);

    }



    @Override
    public void getMessageListResult(List<ProvideDoctorPatientUserInfo> list) {
        mInteractPatients.clear();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        if (!CollectionUtils.isEmpty(list)) {

            handleData(list);
            mInteractPatients.addAll(ConvertUtils.convertDataToInteractList(list));
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

    //主线程中执行

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(MainMessage msg) {
        mPresenter.sendMessageListRequest(ConvertUtils.getConsultationUserList());
    }


    /**
     * 处理数据
     *
     * @param mProvideDoctorPatientUserInfo 患者信息
     */
    private void handleData(List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo) {
        Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
        for (int i = 0; i < mProvideDoctorPatientUserInfo.size(); i++) {
            EMConversation emcConversation = conversationMap.get(mProvideDoctorPatientUserInfo.get(i).getUserCode());
            EMMessage emMessage = emcConversation.getAllMessages().get(emcConversation.getAllMessages().size() - 1);
            String str = "{" + emMessage.getBody().toString() + "}";
            EMMessageEntity emMessageEntity = new Gson().fromJson(str, EMMessageEntity.class);
            mProvideDoctorPatientUserInfo.get(i).setLastMessage(emMessageEntity.getTxt());
            //获取未读消息数
            EMConversation conversation = EMClient.getInstance().chatManager()
                    .getConversation(mProvideDoctorPatientUserInfo.get(i).getUserCode());
            int num = conversation.getUnreadMsgCount();
            if (num > 0) {
                mProvideDoctorPatientUserInfo.get(i).setNoRead(true);
            } else
                mProvideDoctorPatientUserInfo.get(i).setNoRead(false);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
