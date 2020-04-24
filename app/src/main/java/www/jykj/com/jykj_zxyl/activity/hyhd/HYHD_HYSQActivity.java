package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.yhhd.ProvideBindingDoctorDoctorApply;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.twjz.KJCF_CFYPActivity;
import www.jykj.com.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZ_CFQRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.YHHD_HYSQRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.NestedExpandaleListView;

/**
 * 好友申请
 */
public class HYHD_HYSQActivity extends AppCompatActivity {

    private             LinearLayout                        mBack;
    private             Context                             mContext;
    private             HYHD_HYSQActivity                   mActivity;
    private             Handler                             mHandler;
    private             JYKJApplication                     mApp;

    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private         YHHD_HYSQRecycleAdapter mAdapter;       //适配器

    private                 int                         mRowNum = 10;                        //分页行数
    private                 int                         mPageNum = 1;                       //分页页码
    private                   boolean                   mLoadDate = true;

    private                 int                         mOperaType;                         //操作类型  1=同意  3=拒绝
    private                 int                         getmOperaIndex;                         //操作下标
    private                 String                      refundReson;                        //拒绝原因



    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;


    private List<ProvideBindingDoctorDoctorApply> mList = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthyhd_hysq);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
        initHandler();
        getDate();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            mLoadDate = false;
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            List<ProvideBindingDoctorDoctorApply>   list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideBindingDoctorDoctorApply.class);
                            mList.addAll(list);
                            mAdapter.setDate(mList);
                            mAdapter.notifyDataSetChanged();
                            if (list.size() < mRowNum)
                                mLoadDate = false;
                        }
                        break;
                    case 3:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            mLoadDate = false;
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            mList.get(getmOperaIndex).setFlagApplyState(mOperaType);
                            mList.get(getmOperaIndex).setRefuseReason(refundReson);
                            mAdapter.setDate(mList);
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        };
    }
    /**
     * 获取数据
     */
    private void getDate() {
        getProgressBar("请稍候","正在获取数据");
        ProvideBindingDoctorDoctorApply provideBindingDoctorDoctorApply = new ProvideBindingDoctorDoctorApply();
        provideBindingDoctorDoctorApply.setRowNum(mRowNum);
        provideBindingDoctorDoctorApply.setPageNum(mPageNum);
        provideBindingDoctorDoctorApply.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideBindingDoctorDoctorApply.setSearchDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBindingDoctorDoctorApply.setSearchDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideBindingDoctorDoctorApply),Constant.SERVICEURL+"bindingDoctorPatientControlle/searchBindingDoctorDoctorApplyList");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();

    }

    /**
     * 审核
     */
    private void OperApprovalBindingDoctorDoctor() {
        getProgressBar("请稍候","正在提交");
        ProvideBindingDoctorDoctorApply provideBindingDoctorDoctorApply = new ProvideBindingDoctorDoctorApply();
        provideBindingDoctorDoctorApply.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideBindingDoctorDoctorApply.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBindingDoctorDoctorApply.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        provideBindingDoctorDoctorApply.setDdApplyId(mList.get(getmOperaIndex).getDdApplyId());
        provideBindingDoctorDoctorApply.setFlagApplyState(mOperaType);
        provideBindingDoctorDoctorApply.setRefuseReason(refundReson);
        new Thread(){
            public void run(){
                try {
                    String str = JSON.toJSONString(provideBindingDoctorDoctorApply);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+str,Constant.SERVICEURL+"bindingDoctorPatientControlle/operApprovalBindingDoctorDoctor");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();

    }

    /**
     * 初始化布局
     */
    private void initLayout() {

        mBack = (LinearLayout)this.findViewById(R.id.back);
        mBack.setOnClickListener(new ButtonClick());

        mRecycleView = (RecyclerView) this.findViewById(R.id.iv_hysq);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new YHHD_HYSQRecycleAdapter(mList,mContext);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if (mLoadDate){
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if(lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            mPageNum++;
                            getDate();
                        }
                    }
                }
            }
        });
        mAdapter.setOnItemTYClickListenerr(new YHHD_HYSQRecycleAdapter.OnItemTYClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(mContext,"同意",Toast.LENGTH_SHORT).show();
                mOperaType = 3;
                getmOperaIndex = position;
                OperApprovalBindingDoctorDoctor();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        mAdapter.setOnItemJJClickListenerr(new YHHD_HYSQRecycleAdapter.OnItemJJClickListener() {
            @Override
            public void onClick(int position) {
                final EditText et = new EditText(mContext);
                new AlertDialog.Builder(mContext).setTitle("请输入拒绝原因")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                                mOperaType = 1;
                                getmOperaIndex = position;
                                refundReson = et.getText().toString();
                                OperApprovalBindingDoctorDoctor();
                            }
                        }).setNegativeButton("取消",null).show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }



    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.back:
                    finish();
                    break;
            }
        }
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
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
