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

import entity.HZIfno;
import entity.wdzs.ProvideBasicsDisease;
import entity.wdzs.ProvideBasicsImg;
import entity.wdzs.ProvideInteractOrderDiag;
import entity.wdzs.ProvideInteractOrderMedical;
import entity.wdzs.ProvideInteractPatientMessage;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.adapter.TWJZ_CFQRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.ActivityUtil;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;

/**
 * 图文就诊（就诊小结）
 */
public class TWJZ_JZXJActivity extends AppCompatActivity {

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private Handler mHandler;
    private TWJZ_JZXJActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private ProvideViewInteractOrderTreatmentAndPatientInterrogation mProvideViewInteractOrderTreatmentAndPatientInterrogation;
    private ProvideInteractPatientMessage mProvideInteractPatientMessage;
    private TextView mNameTitle;               //标题，患者姓名
    private TextView mMessageType;               //消息类型
    private TextView mMessageDate;               //留言日期
    private TextView mMessageContent;               //消息类型
    private TextView mMessageLinkPhone;            //联系电话

    private RecyclerView mImageRecycleView;
    private FullyGridLayoutManager mGridLayoutManager;
    private WZZXImageViewRecycleAdapter mAdapter;

    private List<ProvideBasicsImg> mProvideBasicsImg = new ArrayList<>();
    private String mGetImgNetRetStr;                 //获取图片返回字符串

    private TextView mMessageReply;                  //留言回复内容
    private TextView mCommit;                        //
    private ProvideInteractOrderMedical mProvideInteractOrderMedical;

    private TextView mTitleName;
    private TextView mJZXJContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_jzxj);
        ActivityUtil.setStatusBarMain(TWJZ_JZXJActivity.this);

        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mProvideViewInteractOrderTreatmentAndPatientInterrogation = (ProvideViewInteractOrderTreatmentAndPatientInterrogation) getIntent().getSerializableExtra("wzxx");
        initLayout();
        initListener();
        initHandler();
        getData();
    }

    private void initLayout() {
        mTitleName = (TextView) this.findViewById(R.id.tv_userNameTitle);
        mJZXJContent = (TextView) this.findViewById(R.id.tv_jzxjContent);
        mCommit = (TextView) this.findViewById(R.id.tv_commit);
        mCommit.setOnClickListener(new ButtonClick());

    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            mProvideInteractOrderMedical = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractOrderMedical.class);
                            if (mProvideInteractOrderMedical != null) {
                                showLayoutDate();
                            }

                        }
                        break;

                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mGetImgNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {


                        }

                        break;

                    case 2:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void getData() {
        getProgressBar("请稍后", "正在获取数据。。。");
        ProvideInteractOrderMedical provideInteractOrderMedical = new ProvideInteractOrderMedical();
        provideInteractOrderMedical.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractOrderMedical.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderMedical.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderMedical.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractOrderMedical);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResMedicalContent");
                    String string01 = Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResOrderDiag";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_commit:
                    commit();
                    break;

            }
        }
    }

    private void showLayoutDate() {
        mTitleName = (TextView) this.findViewById(R.id.tv_userNameTitle);

        mTitleName.setText("【" + mProvideInteractOrderMedical.getPatientName() + "】就诊小结");
        if (mProvideInteractOrderMedical.getTreatmentContent() == null || "".equals(mProvideInteractOrderMedical.getTreatmentContent()))
            mJZXJContent.setHint("请填写就诊小结");
        else
            mJZXJContent.setText(mProvideInteractOrderMedical.getTreatmentContent());
    }

    /**
     * 提交rang
     */
    private void commit() {


        ProvideInteractOrderMedical provideInteractOrderMedical = new ProvideInteractOrderMedical();
        provideInteractOrderMedical.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideInteractOrderMedical.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderMedical.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideInteractOrderMedical.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());
        provideInteractOrderMedical.setTreatmentContent(mJZXJContent.getText().toString());
        if (provideInteractOrderMedical.getTreatmentContent() == null || "".equals(mProvideInteractOrderMedical.getTreatmentContent())) {
            Toast.makeText(mContext, "请先填写就诊小结", Toast.LENGTH_SHORT).show();
            return;
        }
        getProgressBar("请稍候", "正在提交数据。。。");
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractOrderMedical);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByMedicalContent");
                    String string01 = Constant.SERVICEURL + "doctorInteractDataControlle/operUpdMyClinicDetailByMedicalContent";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
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
