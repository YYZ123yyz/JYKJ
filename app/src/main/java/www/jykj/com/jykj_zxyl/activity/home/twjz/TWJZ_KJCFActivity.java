package www.jykj.com.jykj_zxyl.activity.home.twjz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.wdzs.ProvideInteractOrderMedical;
import entity.wdzs.ProvideInteractOrderPrescribe;
import entity.wdzs.ProvideInteractOrderPrescribeGroup;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.jyzl.JYZL_GRZLActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.adapter.DorcerFriendExpandableListViewAdapter;
import www.jykj.com.jykj_zxyl.adapter.JYZLecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.TWJZ_CFQRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.NestedExpandaleListView;

/**
 * 图文就诊（开具处方）
 */
public class TWJZ_KJCFActivity extends AppCompatActivity {

    public ProgressDialog mDialogProgress =null;

    private             Context                                 mContext;
    private             Handler                                 mHandler;

    private             String                              mNetRetStr;                 //返回字符串

    private TWJZ_KJCFActivity mActivity;
    private                 JYKJApplication         mApp;
    private                 LinearLayout            mCFYP;          //处方药品

    private                 NestedExpandaleListView                mCFXX;                              //医生好友
    private                 TextView                        mAddYP;                             //添加用药
    private   static final  int                             mAddYPRequst = 1;
    private                 TextView                                mWTJText;
    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private List<ProvideInteractOrderPrescribe> mProvideInteractOrderPrescribes = new ArrayList<>();
    private                 int                             mDeleteIndex;


    private         RecyclerView            mRecycleView;

    private         LinearLayoutManager     layoutManager;
    private         TWJZ_CFQRecycleAdapter mAdapter;       //适配器



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_kjcf);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initLayout();
        initHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
//        mCFYP = (LinearLayout)this.findViewById(R.id.li_activityTWJZKJCF_cfyp);
//        mCFYP.setOnClickListener(new ButtonClick());
//
        mAddYP = (TextView)this.findViewById(R.id.tv_addYP);
        mAddYP.setOnClickListener(new ButtonClick());
        mWTJText = (TextView)this.findViewById(R.id.tv_wtj);

        mRecycleView = (RecyclerView) this.findViewById(R.id.tv_recycelView);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new TWJZ_CFQRecycleAdapter(mProvideInteractOrderPrescribes,mContext);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TWJZ_CFQRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivityForResult(new Intent(mContext,KJCF_CFYPActivity.class).putExtra("cfyp",mProvideInteractOrderPrescribes.get(position)),mAddYPRequst);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        mAdapter.setOnDeleteItemClickListener(new TWJZ_CFQRecycleAdapter.OnItemDeleteClickListener() {
            @Override
            public void onClick(int position) {
                mDeleteIndex = position;
                deleteYP();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    /**
     * 删除药品
     */
    private void deleteYP() {
        getProgressBar("请稍候","正在提交。。。");
        new Thread() {
            public void run() {
//                          //提交数据
                try {
                    ProvideInteractOrderPrescribe provideInteractOrderPrescribe = mProvideInteractOrderPrescribes.get(mDeleteIndex);
                    provideInteractOrderPrescribe.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    provideInteractOrderPrescribe.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
                    provideInteractOrderPrescribe.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
                    String str = new Gson().toJson(provideInteractOrderPrescribe);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "doctorInteractDataControlle/operDelMyClinicDetailByPrescribe");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("提交失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(6);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    mHandler.sendEmptyMessage(6);
                    return;
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mProvideInteractOrderPrescribes = JSON.parseArray(netRetEntity.getResJsonData(),ProvideInteractOrderPrescribe.class);
                            if (mProvideInteractOrderPrescribes != null)
                            {
                                if (mProvideInteractOrderPrescribes.size() > 0)
                                {
                                    mWTJText.setVisibility(View.GONE);
                                }
                                else
                                {
                                    mWTJText.setVisibility(View.VISIBLE);
                                }
                                mAdapter.setDate(mProvideInteractOrderPrescribes);
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
//                        netRetEntity = JSON.parseObject(mGetImgNetRetStr,NetRetEntity.class);
//                        if (netRetEntity.getResCode() == 0)
//                        {
//                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//
//
//                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1)
                        {
                            mProvideInteractOrderPrescribes.remove(mDeleteIndex);
                            mAdapter.setDate(mProvideInteractOrderPrescribes);
                            mAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍后","正在获取数据。。。");
        ProvideInteractOrderPrescribe provideInteractOrderPrescribe = new ProvideInteractOrderPrescribe();
        provideInteractOrderPrescribe.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractOrderPrescribe.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderPrescribe.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderPrescribe.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderPrescribe);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"doctorInteractDataControlle/searchMyClinicDetailResPrescribe");
                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/searchMyClinicDetailResPrescribe";
                    System.out.println(string+string01);
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


    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_commit:
                    commit();
                    break;
                case R.id.tv_addYP:
                    startActivityForResult(new Intent(mContext,KJCF_CFYPActivity.class).putExtra("xzyp",mProvideViewInteractOrderTreatmentAndPatientInterrogation),mAddYPRequst);
                    break;

            }
        }
    }
    private void showLayoutDate() {

    }

    /**
     * 提交rang
     */
    private void commit() {


//        ProvideInteractOrderMedical provideInteractOrderMedical = new ProvideInteractOrderMedical();
//        provideInteractOrderMedical.setLoginDoctorPosition(mApp.loginDoctorPosition);
//        provideInteractOrderMedical.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
//        provideInteractOrderMedical.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
//        provideInteractOrderMedical.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
//        provideInteractOrderMedical.setTreatmentContent(mJZXJContent.getText().toString());
//        if (provideInteractOrderMedical.getTreatmentContent() == null || "".equals(mProvideInteractOrderMedical.getTreatmentContent()))
//        {
//            Toast.makeText(mContext,"请先填写就诊小结",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        getProgressBar("请稍候","正在提交数据。。。");
//        new Thread(){
//            public void run(){
//                try {
//                    String string = new Gson().toJson(provideInteractOrderMedical);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByMedicalContent");
//                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByMedicalContent";
//                    System.out.println(string+string01);
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(2);
//            }
//        }.start();
    }

    private void initListener() {
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }




}
