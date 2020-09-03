package www.jykj.com.jykj_zxyl.appointment.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.appointment.AppointPatientListContract;
import www.jykj.com.jykj_zxyl.appointment.AppointPatientListPresenter;
import www.jykj.com.jykj_zxyl.appointment.adapter.MyPatientInfoAdapter;

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
    private JYKJApplication mApp;
    private MyPatientInfoAdapter myPatientInfoAdapter;
    private List<PatientInfoBean> mPatientInfoBeans;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            reserveDate=extras.getString("reserveDate");
            reserveStatus=extras.getString("reserveStatus");
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
        mApp = (JYKJApplication) getApplication();
        //初始化ToolBar
        setToolBar();
        //初始化Loading
        initLoadingAndRetryManager();
        //初始化Recyclerview
        initRecyclerView();
        //添加监听
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
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

            }

            @Override
            public void onClickReceiveTreatment(int pos) {

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
        toolbar.setMainTitle("预约患者");
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
        if (!CollectionUtils.isEmpty(mPatientInfoBeans)) {
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
}
