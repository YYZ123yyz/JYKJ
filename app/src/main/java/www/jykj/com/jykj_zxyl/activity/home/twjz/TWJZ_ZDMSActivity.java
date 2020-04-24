package www.jykj.com.jykj_zxyl.activity.home.twjz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.wdzs.ProvideBasicsDisease;
import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractOrderDiag;
import entity.wdzs.ProvideInteractPatientMessage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;
import www.jykj.com.jykj_zxyl.util.Util;


/**
 * 我的诊所 == 》 诊断描述
 */
public class TWJZ_ZDMSActivity extends AppCompatActivity {

    public ProgressDialog mDialogProgress =null;

    private             Context                                 mContext;
    private             Handler                                 mHandler;
    private TWJZ_ZDMSActivity mActivity;
    private              JYKJApplication                            mApp;

    private             String                              mNetRetStr;                 //返回字符串
    private              ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private             ProvideInteractPatientMessage           mProvideInteractPatientMessage;
    private             TextView                                  mNameTitle;               //标题，患者姓名
    private             TextView                                  mMessageType;               //消息类型
    private             TextView                                  mMessageDate;               //留言日期
    private             TextView                                  mMessageContent;               //消息类型
    private             TextView                                  mMessageLinkPhone;            //联系电话

    private             RecyclerView                                mImageRecycleView;
    private             FullyGridLayoutManager                      mGridLayoutManager;
    private             WZZXImageViewRecycleAdapter                 mAdapter;

    private             List<ProvideBasicsImg>                      mProvideBasicsImg = new ArrayList<>();
    private             String                              mGetImgNetRetStr;                 //获取图片返回字符串

    private             TextView                                mMessageReply;                  //留言回复内容
    private             TextView                                mCommit;                        //
    private             ProvideInteractOrderDiag                mProvideInteractOrderDiag;

    private             TextView                                mTitleName;
    private             TextView                                mZDMC1;
    private             TextView                                mZDMC2;
    private             TextView                                mZDMC3;
    private             TextView                                mZDBM1;
    private             TextView                                mZDBM2;
    private             TextView                                mZDBM3;
    private             TextView                                mZDMS;
    private     static    final          int                       mCHOICEJB1 = 1;
    private     static    final          int                       mCHOICEJB2 = 2;
    private     static    final          int                       mCHOICEJB3 = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_zdms);
        mContext =this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initLayout();
        initListener();
        initHandler();
        getData();
    }

    private void initLayout() {
        mTitleName = (TextView)this.findViewById(R.id.tv_userNameTitle);
        mZDMC1 = (TextView)this.findViewById(R.id.tv_zdmc1);
        mZDMC2 = (TextView)this.findViewById(R.id.tv_zdmc2);
        mZDMC3 = (TextView)this.findViewById(R.id.tv_zdmc3);
        mZDBM1 = (TextView)this.findViewById(R.id.tv_zdbm1);
        mZDBM1 = (TextView)this.findViewById(R.id.tv_zdbm2);
        mZDBM1 = (TextView)this.findViewById(R.id.tv_zdbm3);
        mZDMS = (EditText)this.findViewById(R.id.tv_zdms);
        mCommit = (TextView)this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new ButtonClick());

        mZDMC1.setOnClickListener(new ButtonClick());
        mZDMC2.setOnClickListener(new ButtonClick());
        mZDMC3.setOnClickListener(new ButtonClick());


    }

    @Override
    protected void onActivityResult(
            int requestCode,  // 请求码 自定义
            int resultCode,  // 结果码 成功 -1 == OK
            Intent data) { // 数据 ? 可以没有
        try {

            // 诊断一
            if (requestCode == mCHOICEJB1
                    && resultCode == RESULT_OK
                    && data != null) {
                ProvideBasicsDisease provideBasicsDisease = (ProvideBasicsDisease) data.getSerializableExtra("jkxx");
                if (provideBasicsDisease != null)
                {
                    mProvideInteractOrderDiag.setDiagDiseaseCode1(provideBasicsDisease.getDiseaseCode());
                    mProvideInteractOrderDiag.setDiagDiseaseName1(provideBasicsDisease.getDiseaseName());
                    mZDMC1.setText(provideBasicsDisease.getDiseaseName());
                }


            }
            // 诊断二
            if (requestCode == mCHOICEJB2
                    && resultCode == RESULT_OK
                    && data != null) {
                ProvideBasicsDisease provideBasicsDisease = (ProvideBasicsDisease) data.getSerializableExtra("jkxx");
                if (provideBasicsDisease != null)

                {
                    mProvideInteractOrderDiag.setDiagDiseaseCode2(provideBasicsDisease.getDiseaseCode());
                    mProvideInteractOrderDiag.setDiagDiseaseName2(provideBasicsDisease.getDiseaseName());
                    mZDMC2.setText(provideBasicsDisease.getDiseaseName());
                }

            }
            // 诊断三
            if (requestCode == mCHOICEJB3
                    && resultCode == RESULT_OK
                    && data != null) {
                ProvideBasicsDisease provideBasicsDisease = (ProvideBasicsDisease) data.getSerializableExtra("jkxx");
                if (provideBasicsDisease != null)
                {
                    mProvideInteractOrderDiag.setDiagDiseaseCode3(provideBasicsDisease.getDiseaseCode());
                    mProvideInteractOrderDiag.setDiagDiseaseName3(provideBasicsDisease.getDiseaseName());
                    mZDMC3.setText(provideBasicsDisease.getDiseaseName());
                }
            }

        }catch (Exception e)
        {
            Log.i("yi","yichahahaha");
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                            mProvideInteractOrderDiag = JSON.parseObject(netRetEntity.getResJsonData(),ProvideInteractOrderDiag.class);
                            if (mProvideInteractOrderDiag != null)
                            {
                                showLayoutDate();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {


                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
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
        ProvideInteractOrderDiag provideInteractOrderDiag = new ProvideInteractOrderDiag();
        provideInteractOrderDiag.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractOrderDiag.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderDiag.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderDiag.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderDiag);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"doctorInteractDataControlle/searchMyClinicDetailResOrderDiag");
                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/searchMyClinicDetailResOrderDiag";
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

                case R.id.tv_zdmc1:
                    startActivityForResult(new Intent(mContext,WDZS_XZJBActivity.class),mCHOICEJB1);
                    break;
                case R.id.tv_zdmc2:
                    startActivityForResult(new Intent(mContext,WDZS_XZJBActivity.class),mCHOICEJB2);
                    break;
                case R.id.tv_zdmc3:
                    startActivityForResult(new Intent(mContext,WDZS_XZJBActivity.class),mCHOICEJB3);
                    break;
                case R.id.tv_commit:
                    commit();
                    break;

            }
        }
    }
    private void showLayoutDate() {
        mTitleName = (TextView)this.findViewById(R.id.tv_userNameTitle);
        mZDMC1 = (TextView)this.findViewById(R.id.tv_zdmc1);
        mZDMC2 = (TextView)this.findViewById(R.id.tv_zdmc2);
        mZDMC3 = (TextView)this.findViewById(R.id.tv_zdmc3);
        mZDBM1 = (TextView)this.findViewById(R.id.tv_zdbm1);
        mZDBM2 = (TextView)this.findViewById(R.id.tv_zdbm2);
        mZDBM3 = (TextView)this.findViewById(R.id.tv_zdbm3);

        mTitleName.setText("【"+mProvideInteractOrderDiag.getPatientName()+"】诊断描述");
        if (mProvideInteractOrderDiag.getDiagDiseaseName1() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseName1()))
            mZDMC1.setText("请选择诊断名称");
        else
            mZDMC1.setText(mProvideInteractOrderDiag.getDiagDiseaseName1());
        if (mProvideInteractOrderDiag.getDiagDiseaseName2() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseName2()))
            mZDMC2.setText("请选择诊断名称");
        else
            mZDMC2.setText(mProvideInteractOrderDiag.getDiagDiseaseName2());
        if (mProvideInteractOrderDiag.getDiagDiseaseName3() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseName3()))
            mZDMC3.setText("请选择诊断名称");
        else
            mZDMC3.setText(mProvideInteractOrderDiag.getDiagDiseaseName3());
        if (mProvideInteractOrderDiag.getDiagDiseaseNameAlias1() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseNameAlias1()))
            mZDBM1.setHint("请填写诊断别名");
        else
            mZDBM1.setText(mProvideInteractOrderDiag.getDiagDiseaseNameAlias1());
        if (mProvideInteractOrderDiag.getDiagDiseaseNameAlias2() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseNameAlias2()))
            mZDBM2.setHint("请填写诊断别名");
        else
            mZDBM2.setText(mProvideInteractOrderDiag.getDiagDiseaseNameAlias2());
        if (mProvideInteractOrderDiag.getDiagDiseaseNameAlias3() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseNameAlias3()))
            mZDBM3.setHint("请填写诊断别名");
        else
            mZDBM3.setText(mProvideInteractOrderDiag.getDiagDiseaseNameAlias3());


//        mNameTitle.setText("【"+mProvideInteractPatientMessage.getPatientName()+"】诊后留言");
//        mMessageType.setText(mProvideInteractPatientMessage.getTreatmentTypeName());
//        mMessageDate.setText(Util.dateToStr(mProvideInteractPatientMessage.getMessageDate()));
//        mMessageContent.setText(mProvideInteractPatientMessage.getMessageContent());
//        mMessageLinkPhone.setText("联系电话："+mProvideInteractPatientMessage.getPatientLinkPhone());
//
//        mImageRecycleView = (RecyclerView)this.findViewById(R.id.rv_imageView);
//        //创建默认的线性LayoutManager
//        mGridLayoutManager = new FullyGridLayoutManager(mContext,3);
//        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
//        mImageRecycleView.setLayoutManager(mGridLayoutManager);
//        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        mImageRecycleView.setHasFixedSize(true);
//
//        //创建并设置Adapter
//        mAdapter = new WZZXImageViewRecycleAdapter(mProvideBasicsImg,mContext);
//        mImageRecycleView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new WZZXImageViewRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
////                PhotoDialog photoDialog = new PhotoDialog(mContext,R.style.PhotoDialog);
////                photoDialog.setDate(mContext,mApp,mCommodity.getCommodityPicture(),position);
////                photoDialog.show();
//            }
//
//            @Override
//            public void onLongClick(int position) {
//
//            }
//        });

    }

    /**
     * 提交
     */
    private void commit() {


        mProvideInteractOrderDiag.setDiagDiseaseNameAlias1(mZDBM1.getText().toString());
        mProvideInteractOrderDiag.setDiagDiseaseNameAlias2(mZDBM2.getText().toString());
        mProvideInteractOrderDiag.setDiagDiseaseNameAlias3(mZDBM3.getText().toString());
        mProvideInteractOrderDiag.setDiagDiseaseDesc(mZDMS.getText().toString());
        if (mProvideInteractOrderDiag.getDiagDiseaseDesc() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseDesc()))
        {
            Toast.makeText(mContext,"请填写诊断描述",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mProvideInteractOrderDiag.getDiagDiseaseCode1() == null || "".equals(mProvideInteractOrderDiag.getDiagDiseaseCode1()))
        {
            Toast.makeText(mContext,"诊断一必填",Toast.LENGTH_SHORT).show();
            return;
        }
        mProvideInteractOrderDiag.setLoginDoctorPosition(mApp.loginDoctorPosition);
        mProvideInteractOrderDiag.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mProvideInteractOrderDiag.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        mProvideInteractOrderDiag.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        mProvideInteractOrderDiag.setPatientCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientCode());
        mProvideInteractOrderDiag.setPatientName(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getPatientName());
        getProgressBar("请稍候","正在提交数据。。。");
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(mProvideInteractOrderDiag);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string,Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByOrderDiag");
                    String string01 = Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByOrderPatientMessage";
                    System.out.println(string+string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();
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
