package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.appointment.activity.MyClinicDetialActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionNotesContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionNotesPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.PrescriptionNotesAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.PrescriptionNotesChildsAdapter;

/**
 * Description:处方笺列表
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 15:59
 */
public class PrescriptionNotesListActivity extends AbstractMvpBaseActivity<
        PrescriptionNotesContract.View, PrescriptionNotesPresenter>
        implements PrescriptionNotesContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_add_btn)
    TextView tvAddBtn;
    private String orderId;//订单Id
    private String patientCode;//患者code
    private String patientName;//患者名称
    private PrescriptionNotesAdapter prescriptionNotesAdapter;//处方笺适配器
    private List<PrescriptionNotesBean> list;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        list=new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            patientCode=extras.getString("patientCode");
            patientName=extras.getString("patientName");
            orderId= extras.getString("orderId");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_prescription_notes_list;
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化Loading加载页面
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendPrescriptionNotesRequest(orderId,this);

    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        prescriptionNotesAdapter=new PrescriptionNotesAdapter(this,list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(prescriptionNotesAdapter);
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendPrescriptionNotesRequest(orderId,this);
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 添加监听
     */
    private void addListener(){

        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPresenter.sendPrescriptionNotesRequest(orderId,this);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            mPresenter.sendPrescriptionNotesRequest(orderId,this);
        });
        tvAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("patientCode",patientCode);
                bundle.putString("patientName",patientName);
                bundle.putString("orderId",orderId);
                startActivity(PrescriptionMedicinalListActivity.class,bundle,100);
            }
        });
    }

    @Override
    public void showLoading(int code) {


    }

    @Override
    public void getPrescriptionNotesResult(List<PrescriptionNotesBean> prescriptionNotesBeans) {
        prescriptionNotesAdapter.setData(prescriptionNotesBeans);
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }
}
