package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.utils.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.base_view.SimpleDividerItemDecoration;
import www.jykj.com.jykj_zxyl.app_base.base_view.SlideRecyclerView;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionNotesContract;
import www.jykj.com.jykj_zxyl.medicalrecord.PrescriptionNotesPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.PrescriptionNotesAdapter;

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
    SlideRecyclerView rvList;
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
        setToolBar();
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
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("处方笺");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        SimpleDividerItemDecoration decor = new SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.VERTICAL,true);
        decor.setDrawable(getResources().getDrawable(R.drawable.bg_shape_line));
        rvList.addItemDecoration(decor);
        prescriptionNotesAdapter=new PrescriptionNotesAdapter(this,list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(prescriptionNotesAdapter);
        prescriptionNotesAdapter.setOnClickItemListener(new PrescriptionNotesAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {

            }

            @Override
            public void onClickDeleteItem(int pos) {
                PrescriptionNotesBean prescriptionNotesBean = list.get(pos);
                List<PrescriptionNotesBean.PrescribeInfoBean> prescribeInfos=
                        prescriptionNotesBean.getPrescribeInfo();
                PrescriptionNotesBean.PrescribeInfoBean prescribeInfoBean = prescribeInfos.get(0);
                mPresenter.sendDeletePrescriptionNotesRequest(
                        prescribeInfoBean.getPrescribeVoucher(),PrescriptionNotesListActivity.this);
            }
        });
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
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
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
        if(code==101){
            showLoading("",null);
        }

    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getPrescriptionNotesResult(List<PrescriptionNotesBean> prescriptionNotesBeans) {
        list=prescriptionNotesBeans;
        mLoadingLayoutManager.showContent();
        prescriptionNotesAdapter.setData(prescriptionNotesBeans);
        mRefreshLayout.finishRefresh();

    }

    @Override
    public void getDeletePrescriptionNotesResult(boolean isSucess, String msg) {
        if (isSucess) {
            mPresenter.sendPrescriptionNotesRequest(orderId,this);
        }else{
            ToastUtils.showToast(msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1000:
                mPresenter.sendPrescriptionNotesRequest(orderId,this);
                break;
                default:
        }
    }
}
