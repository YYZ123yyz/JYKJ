package www.jykj.com.jykj_zxyl.activity.hzgl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.adapter.HZGL_YYXX_RecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.MessageInfoRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;


/**
 * 患者管理（用药信息）
 */
public class HZGLYYXXActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private                 HZGLYYXXActivity            mActivity;
    private                 RecyclerView                mRecycleView;

    private                 LinearLayoutManager         layoutManager;
    private                 HZGL_YYXX_RecycleAdapter    mHZGL_YYXX_RecycleAdapter;       //适配器
    private                 List<HZIfno>                mHZEntyties = new ArrayList<>();            //所有数据
    private                 List<HZIfno>                mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private                 LinearLayout                llBack;
    private                 ProvideViewPatientLablePunchClockState  mData;
    private int mRowNum = 5;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvSex;
    private boolean mLoadDate = true;                    //是否可以加载数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthzgl_yyxx);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
        initHandler();
        setData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        if(getIntent()!=null){
            mData = (ProvideViewPatientLablePunchClockState)getIntent().getSerializableExtra("patientLable");
        }

        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityFragmentHZGL_YYXX);
        llBack = (LinearLayout)this.findViewById(R.id.ll_back) ;
        tvAge = (TextView)this.findViewById(R.id.tv_age);
        tvName = (TextView)this.findViewById(R.id.tv_name);
        tvSex = (TextView)this.findViewById(R.id.tv_sex);
        llBack.setOnClickListener(new ButtonClick());
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGL_YYXX_RecycleAdapter = new HZGL_YYXX_RecycleAdapter(mHZEntyties,mContext);
        mRecycleView.setAdapter(mHZGL_YYXX_RecycleAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            mPageNum++;
                            setData();
                        }
                    }
                }
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
                case R.id.ll_back:
                    finish();
                    break;
            }
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mHZGL_YYXX_RecycleAdapter.setDate(mHZEntyties);
                        mHZGL_YYXX_RecycleAdapter.notifyDataSetChanged();
                        break;

                }
            }
        };
    }


    /**
     * 设置数据
     */
    private void setData() {
        tvName.setText(mData.getUserName());
        if(mData.getGender()==1){
            tvSex.setText("男");
        }else{
            tvSex.setText("女");
        }
        tvAge.setText(DateUtils.getAgeFromBirthDate(mData.getBirthday())+"岁");
        new Thread() {
            public void run() {
                try {
                    ProvideViewPatientInfo provideViewPatientInfo = new ProvideViewPatientInfo();
                    provideViewPatientInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideViewPatientInfo.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideViewPatientInfo.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    provideViewPatientInfo.setPageNum(mPageNum);
                    provideViewPatientInfo.setRowNum(mRowNum);
                    provideViewPatientInfo.setSearchPatientCode(mData.getPatientCode());
                    provideViewPatientInfo.setSearchTakingMedicine("-1");
                    String jsonString = JSON.toJSONString(provideViewPatientInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + jsonString, Constant.SERVICEURL + "patientDataControlle/searchDoctorManagePatientStateResTakingMedicineRecord");

                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取信息失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(1);
                        mLoadDate = false;
                        return;
                    }
                    List<HZIfno> list = JSON.parseArray(netRetEntity.getResJsonData(), HZIfno.class);
                    mHZEntyties.addAll(list);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();



    }
}
