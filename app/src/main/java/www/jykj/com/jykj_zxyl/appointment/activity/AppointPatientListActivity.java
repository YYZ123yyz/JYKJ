package www.jykj.com.jykj_zxyl.appointment.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.hyphenate.easeui.hyhd.VideoCallActivity;
import com.hyphenate.easeui.hyhd.VoiceCallActivity;
import com.hyphenate.easeui.jykj.bean.OrderMessage;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;
import www.jykj.com.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReceiveTreatmentResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_html5.H5Activity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.AppointPatientListContract;
import www.jykj.com.jykj_zxyl.appointment.AppointPatientListPresenter;
import www.jykj.com.jykj_zxyl.appointment.adapter.MyPatientInfoAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.ConsultationInfoActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.PatientRecordActivity;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:预约患者列表
 *
 * @author: qiuxinhai
 * @date: 2020-09-02 17:22
 */
public class AppointPatientListActivity extends AbstractMvpBaseActivity<
        AppointPatientListContract.View, AppointPatientListPresenter>
        implements AppointPatientListContract.View{
    @BindView(R.id.txt_left_title)
    TextView txtLeftTitle;
    @BindView(R.id.left_image_id)
    ImageButton leftImageId;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.txt_right_title)
    TextView txtRightTitle;
    @BindView(R.id.right_image_search)
    ImageButton rightImageSearch;
    @BindView(R.id.right_image_id)
    ImageButton rightImageId;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private String reserveDate;
    private String reserveStatus;
    private String title;
    private JYKJApplication mApp;
    private MyPatientInfoAdapter myPatientInfoAdapter;
    private List<PatientInfoBean> mPatientInfoBeans;
    private List<BaseReasonBean> baseReasonBeans;
    private PatientInfoBean currentPatientInfoBean;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            reserveDate=extras.getString("reserveDate");
            reserveStatus=extras.getString("reserveStatus");
            title=extras.getString("title");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_appoint_patient_list;
    }



    @Override
    protected void initView() {
        super.initView();
        mPatientInfoBeans=new ArrayList<>();
        baseReasonBeans=new ArrayList<>();
        mApp = (JYKJApplication) getApplication();
        //初始化ToolBar
        setToolBar();
        //添加监听
        addListener();
        //初始化Loading
        initLoadingAndRetryManager();
        //初始化Recyclerview
        initRecyclerView();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendCancelAppointReasonRequest("900061");
        mPresenter.sendSearchReservePatientDoctorInfoByStatusRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                ,reserveDate,reserveStatus,pageSize+"",pageIndex+"",this);
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchReservePatientDoctorInfoByStatusRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    ,reserveDate,reserveStatus,pageSize+"",pageIndex+"",this);
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        myPatientInfoAdapter=new MyPatientInfoAdapter(this,mPatientInfoBeans);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(myPatientInfoAdapter);
        myPatientInfoAdapter.setOnClickItemListener(new MyPatientInfoAdapter.OnClickItemListener() {
            @Override
            public void onClickCancelAppointment(int pos) {
                currentPatientInfoBean = mPatientInfoBeans.get(pos);
                Bundle bundle=new Bundle();
                bundle.putSerializable("baseReasonBeans", (Serializable) baseReasonBeans);
                bundle.putSerializable("currentPatientInfoBean",currentPatientInfoBean);
                startActivity(CancelAppointActivity.class,bundle,100);
            }

            @Override
            public void onClickReceiveTreatment(int pos) {
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                currentPatientInfoBean=patientInfoBean;
                mPresenter.sendOperConfirmReservePatientDoctorInfoRequest(
                        patientInfoBean.getReserveCode(),
                        patientInfoBean.getReserveRosterDateCode()
                        ,patientInfoBean.getMainDoctorCode(),
                        patientInfoBean.getMainDoctorName(),
                        patientInfoBean.getMainPatientCode(),
                        patientInfoBean.getMainPatientName(),"0"
                        ,AppointPatientListActivity.this);
            }

            @Override
            public void onClickMedicalRecordDetial(int pos) {
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",patientInfoBean.getOrderCode());
                bundle.putString("patientCode",patientInfoBean.getMainPatientCode());
                bundle.putString("patientName",patientInfoBean.getMainPatientName());
                bundle.putBoolean("isEdit",true);
                startActivity(PatientRecordActivity.class,bundle);
            }

            @Override
            public void onClickStatisticTable(int pos) {
                String reportUrl = mPatientInfoBeans.get(pos).getReportUrl();
                Bundle bundle=new Bundle();
                bundle.putString("url",reportUrl);
                startActivity(H5Activity.class,bundle);
            }

            @Override
            public void onClickImItem(int pos) {
                Intent intent = new Intent();
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                ProvideViewPatientLablePunchClockState provideviewpatientInfo=new ProvideViewPatientLablePunchClockState();
                provideviewpatientInfo.setUserName(patientInfoBean.getMainPatientName());
                provideviewpatientInfo.setPatientCode(patientInfoBean.getMainPatientCode());
                intent.putExtra("patientInfo", provideviewpatientInfo);
                intent.setClass(AppointPatientListActivity.this, HZGLHZZLActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickConsultItem(int pos) {
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",patientInfoBean.getOrderCode());
                startActivity(ConsultationInfoActivity.class,bundle);
            }

            @Override
            public void onClickInterrogation(int pos) {
                PatientInfoBean patientInfoBean = mPatientInfoBeans.get(pos);
                String reserveProjectCode = patientInfoBean.getReserveProjectCode();
                switch (reserveProjectCode) {
                    case "1":
                        startJumpChatActivity(patientInfoBean);
                        break;
                    case "2":
                        startActivity(new Intent(AppointPatientListActivity.this,
                                VideoCallActivity.class).putExtra("username",
                                patientInfoBean.getMainPatientCode())
                                .putExtra("isComingCall", false)
                                .putExtra("nickName", patientInfoBean.getMainPatientName()));
                        break;
                    case "3":
                        String patientLinkPhone = patientInfoBean.getPatientLinkPhone();
                        if (StringUtils.isNotEmpty(patientLinkPhone)) {
                            callPhone(patientLinkPhone);
                        }
                        break;
                    case "5":

                        startActivity(new Intent(AppointPatientListActivity.this, VoiceCallActivity.class)
                                .putExtra("username", patientInfoBean.getMainPatientCode())
                                .putExtra("isComingCall", false)
                                .putExtra("nickName", patientInfoBean.getMainPatientName()));
                        break;
                }
            }
        });

    }

    /**
     * 添加监听
     */
    private void addListener() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            pageIndex=1;
            mPresenter.sendSearchReservePatientDoctorInfoByStatusRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    ,reserveDate,reserveStatus,pageSize+"",pageIndex+"",
                    AppointPatientListActivity.this);

        });
        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageIndex++;
            mPresenter.sendSearchReservePatientDoctorInfoByStatusRequest(
                    mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode()
                    ,reserveDate,reserveStatus,pageSize+"",pageIndex+"",
                    AppointPatientListActivity.this);

        });
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle(title);
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }


    @Override
    public void showLoading(int code) {

    }

    @Override
    public void showEmpty() {
        if(pageIndex == 1){
            mLoadingLayoutManager.showEmpty();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    @Override
    public void showRetry() {
        if (pageIndex==1) {
            mLoadingLayoutManager.showError();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }


    @Override
    public void getSearchReservePaitentDoctorInfoByStatusResult(
            List<PatientInfoBean> patientInfoBeans) {
        if (pageIndex == 1) {
            mPatientInfoBeans.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(patientInfoBeans)) {
            mPatientInfoBeans.addAll(patientInfoBeans);
            myPatientInfoAdapter.setData(mPatientInfoBeans);
            myPatientInfoAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayoutManager.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayoutManager.showContent();
    }

    @Override
    public void getCancelAppointReasonResult(List<BaseReasonBean> baseReasonBeans) {
        this.baseReasonBeans=baseReasonBeans;
    }

    @Override
    public void getOperConfirmReservePatientDoctorInfoResult(ReceiveTreatmentResultBean receiveTreatmentResultBean) {
        startJumpChatActivity(currentPatientInfoBean,receiveTreatmentResultBean);
    }

    @Override
    public void getOperConfirmReservePatientDoctorInfoError(String msg) {
        ToastUtils.showToast(msg);
    }


    /**
     * 跳转IM
     * @param currentPatientInfoBean 患者信息
     */
    private void startJumpChatActivity(PatientInfoBean currentPatientInfoBean,
                                       ReceiveTreatmentResultBean receiveTreatmentResultBean){


        Intent intent = new Intent(this, ChatActivity.class);
        //患者
        intent.putExtra("userCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("userName", currentPatientInfoBean.getMainPatientName());
        //医生
        intent.putExtra("usersName", currentPatientInfoBean.getMainDoctorName());
        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //URL
        intent.putExtra("doctorUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
        intent.putExtra("patientCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("patientSex", currentPatientInfoBean.getPatientSex());
        String reserveProjectCode = currentPatientInfoBean.getReserveProjectCode();
        String appointMentType="";
        switch (reserveProjectCode){
            case "1":
                appointMentType="10";
                break;
            case "2":
                appointMentType="20";
                break;
            case "3":
                appointMentType="30";
                break;

            case "5":
                appointMentType="40";
                break;
            default:
        }
        String surplusTimes="";
        if(appointMentType.equals("10")){
            surplusTimes=receiveTreatmentResultBean.getReserveProjectLastCount()+"次";
        }else{
            surplusTimes=(receiveTreatmentResultBean.getSumDuration()
                    -receiveTreatmentResultBean.getUseDuration())+"分钟";
        }
        String receiveTime= www.jykj.com.jykj_zxyl.util.DateUtils.getDateToStringYYYMMDDHHMM(
                receiveTreatmentResultBean.getAdmissionStartTimes());
        String endTime = www.jykj.com.jykj_zxyl.util.DateUtils.getDateToStringYYYMMDDHHMM(receiveTreatmentResultBean.getAdmissionEndTimes());
        Bundle bundle = new Bundle();
        OrderMessage receiveTreatment = new OrderMessage(mApp.mViewSysUserDoctorInfoAndHospital.getUserName(),
                mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl(),
                currentPatientInfoBean.getOrderCode(),
                currentPatientInfoBean.getSignCode(), receiveTime,
                endTime, surplusTimes, appointMentType, "receiveTreatment");
        receiveTreatment.setOrderType(currentPatientInfoBean.getTreatmentType()+"");
        receiveTreatment.setReserveCode(currentPatientInfoBean.getReserveCode());
        bundle.putSerializable("orderMsg",
                receiveTreatment);
        intent.putExtras(bundle);
        startActivityForResult(intent,1000);
    }

    /**
     * 跳转IM
     * @param currentPatientInfoBean 患者信息
     */
    private void startJumpChatActivity(PatientInfoBean currentPatientInfoBean){
        Intent intent = new Intent(this, ChatActivity.class);
        //患者
        intent.putExtra("userCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("userName", currentPatientInfoBean.getMainPatientName());
        //医生
        intent.putExtra("usersName", currentPatientInfoBean.getMainDoctorName());
        intent.putExtra("userUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //URL
        intent.putExtra("doctorUrl", mApp.mViewSysUserDoctorInfoAndHospital.getUserLogoUrl());
        //intent.putExtra("patientAlias", mHZEntyties.get(position).getan);
        intent.putExtra("patientCode", currentPatientInfoBean.getMainPatientCode());
        intent.putExtra("patientSex", currentPatientInfoBean.getPatientSex());
        startActivity(intent);
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    private void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
