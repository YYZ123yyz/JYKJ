package www.jykj.com.jykj_zxyl.activity.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import entity.home.newsMessage.GetNewsMessageParment;
import entity.home.newsMessage.ProvideMsgPushReminder;
import entity.unionInfo.ProvideViewUnionDoctorMemberAndUnionDetailInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.news.UnionNewsDetailActivity;
import www.jykj.com.jykj_zxyl.adapter.PaintVisitAdapter;
import www.jykj.com.jykj_zxyl.adapter.UnionNewsAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;

public class UnionNewsActivity extends AppCompatActivity implements View.OnClickListener {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private UnionNewsActivity mActivity;
    private Handler mHandler;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串

    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private boolean loadDate = true;
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private UnionNewsAdapter mAdapter;
    private boolean mLoadDate = true;              //是否加载数据

    private String mMessageType;               //消息类型

    private TextView mTitle;

    private List<ProvideMsgPushReminder> mMsgPushReminders = new ArrayList<>();                 //获取到的消息

    private TextView    Type;
    private SmartRefreshLayout refreshLayout;
 private int page=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union_news);
        ActivityUtil.setStatusBarMain(UnionNewsActivity.this);

        mContext = this;
        mActivity = this;
        mMessageType = getIntent().getStringExtra("messageType");
        mApp = (JYKJApplication) getApplication();
        initView();
        initListener();
        initHandler();
        getDate();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        mAdapter = new UnionNewsAdapter(mMsgPushReminders,mContext);
                        mRecyclerView.setAdapter(mAdapter);
                        //加载更多
                        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                            @Override
                            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                page ++ ;
                              //  getDate();
                                mAdapter.notifyDataSetChanged();
                                refreshLayout.finishLoadMore(2000);//加载完成
                            }
                        });
                        //刷新
                        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                            @Override
                            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                                mMsgPushReminders.clear();
                                getDate();
                                mAdapter.notifyDataSetChanged();
                                refreshLayout.finishRefresh(true);//刷新完成
                            }
                        });
                        //消息详情点击事件
                        mAdapter.setOnItemClickListener(new UnionNewsAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(mContext, UnionNewsDetailActivity.class);
                                intent.putExtra("newMessage", mMsgPushReminders.get(position));
                                String msgLookUrl = mMsgPushReminders.get(position).getMsgLookUrl();
                                intent.putExtra("URL", msgLookUrl);
                                Log.e("tag", "链接"+msgLookUrl);
                                Integer reminderId = mMsgPushReminders.get(position).getReminderId();
                                intent.putExtra("reminderId", reminderId);
                                mMsgPushReminders.get(position).setFlagMsgRead(1);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongClick(int position) {

                            }
                        });

                        break;
                }
            }
        };
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAdapter.setdate(mMsgPushReminders);
//        mAdapter.notifyDataSetChanged();
//    }

    private void initView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        mTitle = this.findViewById(R.id.title);
        switch (Integer.parseInt(mMessageType)) {
            case 4000101:
                mTitle.setText("患者就诊");
                break;
            case 4000102:
                mTitle.setText("诊后留言");
                break;
            case 4000103:
                mTitle.setText("添加患者");
                break;
            case 4000104:
                mTitle.setText("联盟消息");
                break;
            case 4000105:
                mTitle.setText("医患圈");
                break;
            case 4000106:
                mTitle.setText("紧急提醒");
                break;
            case 4000107:
                mTitle.setText("患者签约");
                break;
            case 4000108:
                mTitle.setText("系统消息");
                break;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (mLoadDate) {
//                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
//                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
//                            if (loadDate) {
//                                mPageNum++;
//                                getDate();
//                            }
//                        }
//                    }
//                }
//            }
//        });

    }

    private void initListener() {
        llBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 获取联盟消息
     */
    private void getDate() {
        getProgressBar("请稍候。。。。", "正在获取数据");
        new Thread() {
            public void run() {
                try {
                    GetNewsMessageParment getNewsMessageParment = new GetNewsMessageParment();
                    getNewsMessageParment.setRowNum(mRowNum + "");
                    getNewsMessageParment.setPageNum(mPageNum + "");
                    getNewsMessageParment.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    getNewsMessageParment.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    getNewsMessageParment.setMsgType(mMessageType);
                    getNewsMessageParment.setFlagMsgRead("-1");
                    getNewsMessageParment.setSearchDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    String jsonString = JSON.toJSONString(getNewsMessageParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderListResAllData");
                    Log.e("tag", "run: 666"+mNetRetStr );
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        loadDate = false;
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取失败：" + retEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    List<ProvideMsgPushReminder> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideMsgPushReminder.class);
                    if (list.size() > 0) {
                        if (list.get(0).getMsgLookUrl() == null || "".equals(list.get(0).getMsgLookUrl())) {
                            loadDate = false;
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                    mMsgPushReminders.addAll(list);
                } catch (Exception e) {
                    loadDate = false;
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(0);
                    e.printStackTrace();
                    return;

                }

                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 获取进度条
     * 获取进度条
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}
