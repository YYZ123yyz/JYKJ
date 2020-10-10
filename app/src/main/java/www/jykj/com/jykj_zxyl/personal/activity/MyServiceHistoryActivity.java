package www.jykj.com.jykj_zxyl.personal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.ZhlyReplyActivity;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReservePatientDoctorInfo;
import www.jykj.com.jykj_zxyl.app_base.base_html5.H5Activity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.ConsultationInfoActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.activity.PatientRecordActivity;
import www.jykj.com.jykj_zxyl.personal.MyServiceHistoryContract;
import www.jykj.com.jykj_zxyl.personal.MyServiceHistoryPresenter;
import www.jykj.com.jykj_zxyl.personal.adapter.ServiceHistoryAdapter;

/**
 * Description:我的服务历史
 *
 * @author: qiuxinhai
 * @date: 2020-10-06 11:40
 */
public class MyServiceHistoryActivity extends AbstractMvpBaseActivity<MyServiceHistoryContract.View
        , MyServiceHistoryPresenter> implements MyServiceHistoryContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_image_text_icon)
    ImageView ivImageTextIcon;
    @BindView(R.id.ll_image_text_root)
    LinearLayout llImageTextRoot;
    @BindView(R.id.iv_phone_icon)
    ImageView ivPhoneIcon;
    @BindView(R.id.ll_phone_root)
    LinearLayout llPhoneRoot;
    @BindView(R.id.iv_video_icon)
    ImageView ivVideoIcon;
    @BindView(R.id.ll_video_root)
    LinearLayout llVideoRoot;
    @BindView(R.id.iv_audio_icon)
    ImageView ivAudioIcon;
    @BindView(R.id.ll_audio_root)
    LinearLayout llAudioRoot;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ServiceHistoryAdapter serviceHistoryAdapter;//服务历史适配器
    private int serviceType=1;//就诊类型 (1图文，2音频，3视频，4签约，5电话)
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private List<ReservePatientDoctorInfo> mReservePatientDoctorInfos;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_service_history;
    }

    @Override
    protected void initView() {
        super.initView();

        mReservePatientDoctorInfos=new ArrayList<>();
        //设置toolBar
        setToolBar();
        //初始化Loading页面
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();
        //设置操作按钮状态
        setOperBtnStatus(serviceType);
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的服务历史");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);

        });
        mLoadingLayoutManager.showLoading();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        serviceHistoryAdapter=new ServiceHistoryAdapter(this,mReservePatientDoctorInfos);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(serviceHistoryAdapter);
        serviceHistoryAdapter.setOnClickItemListener(new ServiceHistoryAdapter.OnClickItemListener() {
            @Override
            public void onClickConsultDataItem(int pos) {
                ReservePatientDoctorInfo reservePatientDoctorInfo = mReservePatientDoctorInfos.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",reservePatientDoctorInfo.getOrderCode());
                startActivity(ConsultationInfoActivity.class,bundle);
            }

            @Override
            public void onClickStatisticTableItem(int pos) {
                ReservePatientDoctorInfo reservePatientDoctorInfo
                        = mReservePatientDoctorInfos.get(pos);
                String reportUrl = reservePatientDoctorInfo.getReportUrl();
                Bundle bundle=new Bundle();
                bundle.putString("url",reportUrl);
                startActivity(H5Activity.class,bundle);
            }

            @Override
            public void onClickMedicalRecordItem(int pos) {
                ReservePatientDoctorInfo reservePatientDoctorInfo = mReservePatientDoctorInfos.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderCode",reservePatientDoctorInfo.getOrderCode());
                bundle.putString("patientCode",reservePatientDoctorInfo.getMainPatientCode());
                bundle.putString("patientName",reservePatientDoctorInfo.getMainPatientName());
                startActivity(PatientRecordActivity.class,bundle);
            }

            @Override
            public void onClickDiagnosisMessageItem(int pos) {
                ReservePatientDoctorInfo reservePatientDoctorInfo = mReservePatientDoctorInfos.get(pos);
                ProvideViewInteractOrderTreatmentAndPatientInterrogation patientInterrogation
                        =new ProvideViewInteractOrderTreatmentAndPatientInterrogation();
                patientInterrogation.setOrderCode(reservePatientDoctorInfo.getOrderCode());
                patientInterrogation.setTreatmentType(reservePatientDoctorInfo.getTreatmentType());
                patientInterrogation.setPatientCode(reservePatientDoctorInfo.getMainPatientCode());
                patientInterrogation.setPatientName(reservePatientDoctorInfo.getMainPatientName());
                patientInterrogation.setTreatmentLinkPhone(reservePatientDoctorInfo.getPatientLinkPhone());
                Intent intent = new Intent(MyServiceHistoryActivity.this, ZhlyReplyActivity.class);
                intent.putExtra("wzxx", patientInterrogation);

                startActivity(intent);
            }
        });
    }

    /**
     * 添加监听
     */
    private void addListener(){
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            pageIndex=1;
            mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize,
                    pageIndex, this);

        });
        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            pageIndex++;
            mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize,
                    pageIndex, this);

        });
    }



    @OnClick({R.id.ll_image_text_root, R.id.ll_phone_root,
            R.id.ll_video_root, R.id.ll_audio_root,

    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_image_text_root:
                serviceType = 1;
                pageIndex=1;
                setOperBtnStatus(serviceType);
                mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);
                break;
            case R.id.ll_audio_root:
                serviceType = 2;
                pageIndex=1;
                setOperBtnStatus(serviceType);
                mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);
                break;
            case R.id.ll_video_root:
                serviceType = 3;
                pageIndex=1;
                setOperBtnStatus(serviceType);
                mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);
                break;
            case R.id.ll_phone_root:
                serviceType = 5;
                pageIndex=1;
                setOperBtnStatus(serviceType);
                mPresenter.sendSearchReserveInfoMyHistoryRequest(serviceType, pageSize, pageIndex, this);
                break;

            default:
        }
    }


    /**
     * 设置操作按钮状态
     *
     * @param type 类型
     */
    private void setOperBtnStatus(int type) {
        switch (type) {
            case 1://图文

                ivImageTextIcon.setImageResource(R.mipmap.bg_image_text_press);
                ivPhoneIcon.setImageResource(R.mipmap.bg_phone_normal);
                ivVideoIcon.setImageResource(R.mipmap.bg_video_normal);
                ivAudioIcon.setImageResource(R.mipmap.bg_audio_normal);
                break;
            case 2://音频

                ivImageTextIcon.setImageResource(R.mipmap.bg_image_text_normal);
                ivPhoneIcon.setImageResource(R.mipmap.bg_phone_normal);
                ivVideoIcon.setImageResource(R.mipmap.bg_video_normal);
                ivAudioIcon.setImageResource(R.mipmap.bg_audio_press);

                break;
            case 3://视频
                ivImageTextIcon.setImageResource(R.mipmap.bg_image_text_normal);
                ivPhoneIcon.setImageResource(R.mipmap.bg_phone_normal);
                ivVideoIcon.setImageResource(R.mipmap.bg_video_press);
                ivAudioIcon.setImageResource(R.mipmap.bg_audio_normal);
                break;
            case 5://电话

                ivImageTextIcon.setImageResource(R.mipmap.bg_image_text_normal);
                ivPhoneIcon.setImageResource(R.mipmap.bg_phone_press);
                ivVideoIcon.setImageResource(R.mipmap.bg_video_normal);
                ivAudioIcon.setImageResource(R.mipmap.bg_audio_normal);
                break;
            default:

        }
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getSearchReserveInfoMyHistoryResult(List<ReservePatientDoctorInfo> list) {

        if (pageIndex == 1) {
            mReservePatientDoctorInfos.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(list)) {
            mReservePatientDoctorInfos.addAll(list);
            serviceHistoryAdapter.setData(mReservePatientDoctorInfos);
            serviceHistoryAdapter.notifyDataSetChanged();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
