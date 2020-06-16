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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.Serializable;
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
import www.jykj.com.jykj_zxyl.activity.home.yslm.CheckReviewActivity;
import www.jykj.com.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.jykj.com.jykj_zxyl.adapter.MyReviewAdapter;
import www.jykj.com.jykj_zxyl.adapter.RMJXRecycleAdapter;
import www.jykj.com.jykj_zxyl.adapter.WDZS_XZJBAdapter;
import www.jykj.com.jykj_zxyl.adapter.WZZXImageViewRecycleAdapter;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.FullyGridLayoutManager;


/**
 * 我的诊所 == 》 选择疾病
 */
public class WDZS_XZJBActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private WDZS_XZJBAdapter mAdapter;
    private int mNumPage = 1;                  //页数（默认，1）
    private int mRowNum = 10;                  //每页加载10条

    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private Handler mHandler;
    private WDZS_XZJBActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串


    private TextView mQD;                        //
    private List<ProvideBasicsDisease> mProvideBasicsDiseases = new ArrayList<>();
    private boolean loadDate = true;                //是否加载数据
    private EditText mSearchEdit;
    private LinearLayout mSearchLayout;                  //搜索布局


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twjz_xzjb);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        initLayout();
        initHandler();
        getData();
    }

    private void initLayout() {
        mSearchEdit = (EditText) this.findViewById(R.id.et_patientName);
//        mSearchLayout = (LinearLayout) this.findViewById(R.id.li_searcLayout);
//        mSearchLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mNumPage = 1;
//                mRowNum = 10;
//                mProvideBasicsDiseases.clear();
//                getData();
//            }
//        });
        mRecyclerView = this.findViewById(R.id.rv_recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new WDZS_XZJBAdapter(mProvideBasicsDiseases, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WDZS_XZJBAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("jkxx", mProvideBasicsDiseases.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= manager.getItemCount() - 1) {
                        if (loadDate) {
                            mNumPage++;
                            getData();
                        }

                    }
                }
            }
        });

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
                            loadDate = false;
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            List<ProvideBasicsDisease> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvideBasicsDisease.class);
                            mProvideBasicsDiseases.addAll(list);
                            if (mProvideBasicsDiseases != null) {
                                mAdapter.setDate(mProvideBasicsDiseases);
                                mAdapter.notifyDataSetChanged();
                            }
                            if (list.size() < mRowNum)
                                loadDate = false;

                        }
                        break;

                    case 1:


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
        ProvideBasicsDisease provideBasicsDisease = new ProvideBasicsDisease();
        provideBasicsDisease.setPageNum(mNumPage + "");
        provideBasicsDisease.setRowNum(mRowNum + "");
        provideBasicsDisease.setLoginDoctorPosition(mApp.loginDoctorPosition);
        provideBasicsDisease.setOperDoctorCode(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBasicsDisease.setOperDoctorName(mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        provideBasicsDisease.setSrarchDiseaseName(mSearchEdit.getText().toString());
//        provideInteractOrderDiag.setOrderCode(mProvideViewInteractOrderTreatmentAndPatientInterrogation.getOrderCode());

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideBasicsDisease);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "doctorInteractDataControlle/searchMyClinicDetailResOrderDiagInfo");
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
