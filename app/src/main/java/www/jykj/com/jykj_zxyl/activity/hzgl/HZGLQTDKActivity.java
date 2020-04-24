package www.jykj.com.jykj_zxyl.activity.hzgl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import www.jykj.com.jykj_zxyl.adapter.HZGL_QTDK_RecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.HZGL_YYXX_RecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.DateUtils;


/**
 * 患者管理（其他打卡）
 */
public class HZGLQTDKActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private HZGLQTDKActivity mActivity;
    private                 RecyclerView                mRecycleView;

    private                 LinearLayoutManager         layoutManager;
    private                 HZGL_QTDK_RecycleAdapter    mHZGL_QTDK_RecycleAdapter;       //适配器
    private                 List<HZIfno>                mHZEntyties = new ArrayList<>();            //所有数据
    private                 List<HZIfno>                mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private TextView tvName;
    private ProvideViewPatientLablePunchClockState mData;

    private int mRowNum = 5;                        //分页行数
    private int mPageNum = 1;                       //分页页码
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private boolean mLoadDate = true;                    //是否可以加载数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthzgl_qtdk);
        mContext = this;
        mActivity = this;
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
        mRecycleView = (RecyclerView) this.findViewById(R.id.rv_activityFragmentHZGL_QTDK);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mHZGL_QTDK_RecycleAdapter = new HZGL_QTDK_RecycleAdapter(mHZEntyties,mContext);
        mRecycleView.setAdapter(mHZGL_QTDK_RecycleAdapter);
        findViewById(R.id.rl_back).setOnClickListener(new ButtonClick());
        tvName = findViewById(R.id.tv_name);


        String sex;
        if(mData.getGender()==1){
            sex = "男";
        }else{
            sex = "女";
        }
        String age = DateUtils.getAgeFromBirthDate(mData.getBirthday())+"岁";
        tvName.setText(mData.getUserName() + " "+sex + " "+age);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_back:
                    finish();
                    break;
            }
        }
    }


    /**
     * 设置数据
     */
    private void setData() {
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

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mHZGL_QTDK_RecycleAdapter.setDate(mHZEntyties);
                        mHZGL_QTDK_RecycleAdapter.notifyDataSetChanged();
                        break;

                }
            }
        };
    }
}
