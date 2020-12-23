package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceListInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.AccountBalanceInfoListAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountBalanceListContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountBalanceListPresenter;

/**
 * Description:零钱明细
 *
 * @author: qiuxinhai
 * @date: 2020-12-19 16:06
 */
public class SmallChangeActivity extends AbstractMvpBaseActivity<AccountBalanceListContract.View
        , AccountBalanceListPresenter> implements AccountBalanceListContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_filter_btn)
    TextView tvFilterBtn;
    @BindView(R.id.tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.tv_income_amount)
    TextView tvIncomeAmount;
    @BindView(R.id.rl_top_root)
    RelativeLayout rlTopRoot;
    @BindView(R.id.rl_bottom_root)
    RelativeLayout rlBottomRoot;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_bill_root)
    LinearLayout llBillRoot;
    @BindView(R.id.ll_stistic_root)
    LinearLayout llStisticRoot;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private AccountBalanceInfoListAdapter accountBalanceInfoListAdapter;
    private List<AccountBalanceListInfoBean.AccountDoctorBalanceInfoListBean> listBeans;
    private TimePickerView timePickerView;
    private String currentDate="";
    private boolean isShowloading=true;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_small_change;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        listBeans=new ArrayList<>();
        initLoadingAndRetryManager();
        initRecyclerView();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        currentDate=DateUtils.getDeviceTimeOfYM();
        mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                pageSize,pageIndex,this);
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
        });
        mLoadingLayout.showLoading();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        accountBalanceInfoListAdapter=new AccountBalanceInfoListAdapter(R.layout.item_small_change
                ,listBeans);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(accountBalanceInfoListAdapter);
    }

    /**
     * 添加监听
     */
    private void addListener(){
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            isShowloading=false;
            pageIndex=1;
            mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                    pageSize,pageIndex,this);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
            mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                    pageSize,pageIndex,SmallChangeActivity.this);
        });
        tvFilterBtn.setOnClickListener(v -> showChoosedTimeDialog());
        llBillRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        llStisticRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StatisticChartActivity.class,null);
            }
        });
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("零钱明细");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleSearchBtnVisible(false);

    }


    /**
     * 设置数据
     * @param accountBalanceListInfoBean 零钱明细设置数据
     */
    private void setData(AccountBalanceListInfoBean accountBalanceListInfoBean){
        tvPayAmount.setText(String.format("支出¥ %s", accountBalanceListInfoBean.getTotalOut()));
        tvIncomeAmount.setText(String.format("收入¥ %s", accountBalanceListInfoBean.getTotalIncome()));
    }

    /**
     * 预约选择时间弹框
     */
    private void showChoosedTimeDialog() {
        timePickerView = new TimePickerBuilder(this, (date, v) -> {
            isShowloading=true;
            currentDate = DateUtils.getDateYYYMM(date);

            mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                    pageSize,pageIndex,this);
            pageIndex=1;
        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }


    @Override
    public void showLoading(int code) {
        if (pageIndex == 1) {
            if (isShowloading) {
                showLoading("", null);
            }

        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }


    @Override
    public void getSearchAccountDoctorBalanceInfoResult(AccountBalanceListInfoBean
                                                                    accountBalanceListInfoBean) {
        setData(accountBalanceListInfoBean);
        listBeans.clear();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        List<AccountBalanceListInfoBean.AccountDoctorBalanceInfoListBean>
                accountDoctorBalanceInfoList = accountBalanceListInfoBean.getAccountDoctorBalanceInfoList();
        if (!CollectionUtils.isEmpty(accountDoctorBalanceInfoList)) {

            listBeans.addAll(accountDoctorBalanceInfoList);
            accountBalanceInfoListAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayout.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayout.showContent();
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        mRefreshLayout.finishLoadMore();
        if (pageIndex==1) {
            mLoadingLayout.showEmpty();
        }


    }

    @Override
    public void showRetry() {
        super.showRetry();
        mRefreshLayout.finishLoadMore();
        if(pageIndex==1){
            mLoadingLayout.showError();
        }
    }
}
