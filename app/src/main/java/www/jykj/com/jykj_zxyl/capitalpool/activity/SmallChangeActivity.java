package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceListInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.GridDividerItemDecoration;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.AccountBalanceInfoListAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.ChartSketchListAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.ChartSketchBean;
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
    @BindView(R.id.rl_list_top_root)
    RelativeLayout rlListTopRoot;
    @BindView(R.id.rl_statistic_top_root)
    RelativeLayout rlStatisticTopRoot;
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
    @BindView(R.id.ll_pay_root)
    LinearLayout llPayRoot;
    @BindView(R.id.ll_income_root)
    LinearLayout llIncomeRoot;
    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.tv_statistic_filter_btn)
    TextView tvStatisticFilterBtn;
    @BindView(R.id.tv_pay_text)
    TextView tvPayText;
    @BindView(R.id.tv_statistic_pay_amount)
    TextView tvStatisticPayAmount;
    @BindView(R.id.tv_income_text)
    TextView tvIncomeText;
    @BindView(R.id.tv_statistic_income_amount)
    TextView tvStatisticIncomeAmount;
    @BindView(R.id.ll_statistic_content_root)
    LinearLayout llStatisticContentRoot;
    @BindView(R.id.ll_bill_content_root)
    LinearLayout llBillContentRoot;
    @BindView(R.id.iv_bill_btn)
    ImageView ivBillBtn;
    @BindView(R.id.iv_stistic_btn)
    ImageView ivStisticBtn;
    @BindView(R.id.tv_bill_text)
    TextView tvBillText;
    @BindView(R.id.tv_stistic_text)
    TextView tvStisticText;
    @BindView(R.id.rv_list_sketch)
    RecyclerView rvListSketch;
    @BindView(R.id.rl_content_root)
    RelativeLayout rlContentRoot;
    private String changeType="1";
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private AccountBalanceInfoListAdapter accountBalanceInfoListAdapter;
    private List<AccountBalanceListInfoBean.AccountDoctorBalanceInfoListBean> listBeans;
    private ChartSketchListAdapter sketchListAdapter;
    private List<ChartSketchBean> listSketch;
    private String currentDate="";
    private boolean isShowloading;
    private String sourceType="1";
    @Override
    protected int setLayoutId() {
        return R.layout.activity_small_change;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        listBeans=new ArrayList<>();
        listSketch=new ArrayList<>();
        initLoadingAndRetryManager();
        initRecyclerView();
        initSketchRecyclerView();
        addListener();
        setContentHideOrVisible();
    }

    @Override
    protected void initData() {
        super.initData();
        currentDate=DateUtils.getDeviceTimeOfYM();
        mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                pageSize,pageIndex,this);
        tvStatisticFilterBtn.setText(currentDate);
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(rlContentRoot);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            if (sourceType.equals("1")) {
                mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                        pageSize,pageIndex,this);
            }else if(sourceType.equals("2")){
                mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                        SmallChangeActivity.this);
            }


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
     * 初始化示意图RecyclerView
     */
    private void initSketchRecyclerView(){
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        sketchListAdapter=new ChartSketchListAdapter(R.layout.item_chart_sketch,listSketch);
        mGridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        GridDividerItemDecoration  gridDividerItemDecoration=new GridDividerItemDecoration(1
                , Color.parseColor("#D4D4D4"));
        rvListSketch.addItemDecoration(gridDividerItemDecoration);
        rvListSketch.setLayoutManager(mGridLayoutManager);
        rvListSketch.setAdapter(sketchListAdapter);
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
        llBillRoot.setOnClickListener(v -> {
            sourceType="1";
            setContentHideOrVisible();
            mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                    pageSize,pageIndex,SmallChangeActivity.this);
        });
        llStisticRoot.setOnClickListener(v -> {
            sourceType="2";
            setContentHideOrVisible();
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    SmallChangeActivity.this);
        });

        llPayRoot.setOnClickListener(v -> {
            changeType="2";
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    SmallChangeActivity.this);
            isShowloading=true;
            setPayModeStatus();

        });
        llIncomeRoot.setOnClickListener(v -> {
            changeType="1";
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    SmallChangeActivity.this);
            isShowloading=true;
            setPayModeStatus();
        });

        tvStatisticFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosedTimeDialog();
            }
        });
    }

    private void setContentHideOrVisible(){
        if (sourceType.equals("1")) {
            rlListTopRoot.setVisibility(View.VISIBLE);
            rlStatisticTopRoot.setVisibility(View.GONE);
            llBillContentRoot.setVisibility(View.VISIBLE);
            llStatisticContentRoot.setVisibility(View.GONE);
            ivBillBtn.setImageResource(R.mipmap.bg_bill_normal);
            tvBillText.setTextColor(ContextCompat.getColor(this,R.color.color_799dfe));
            ivStisticBtn.setImageResource(R.mipmap.bg_statistic_press);
            tvStisticText.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
        }else if(sourceType.equals("2")){
            rlListTopRoot.setVisibility(View.GONE);
            rlStatisticTopRoot.setVisibility(View.VISIBLE);
            llBillContentRoot.setVisibility(View.GONE);
            llStatisticContentRoot.setVisibility(View.VISIBLE);
            ivBillBtn.setImageResource(R.mipmap.bg_bill_press);
            tvBillText.setTextColor(ContextCompat.getColor(this,R.color.color_666666));
            ivStisticBtn.setImageResource(R.mipmap.bg_statistic_normal);
            tvStisticText.setTextColor(ContextCompat.getColor(this,R.color.color_799dfe));
        }
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

    private void initChartData(){
        chart.setVisibility(View.VISIBLE);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);


    }

    /**
     * 设置支付方式按钮状态
     */
    private void setPayModeStatus(){
        if (changeType.equals("1")) {
            tvPayText.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvStatisticPayAmount.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvIncomeText.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvStatisticIncomeAmount.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
        }else if(changeType.equals("2")){
            tvPayText.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvStatisticPayAmount.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvIncomeText.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvStatisticIncomeAmount.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
        }
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
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            isShowloading=true;
            currentDate = DateUtils.getDateYYYMM(date);
            tvStatisticFilterBtn.setText(currentDate);
            mPresenter.sendSearchAccountDoctorBalanceInfoListRequest(currentDate,
                    pageSize,pageIndex,this);
            pageIndex=1;
        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }


    /**
     * 刷新数据
     * @param accountStisticInfoBean 统计数据
     */
    private void refreshData( AccountStisticInfoBean accountStisticInfoBean) {
        tvStatisticPayAmount.setText(String.format("¥ %s", accountStisticInfoBean.getTotalOut()));
        tvStatisticIncomeAmount.setText(String.format("¥ %s", accountStisticInfoBean.getTotalIncome()));
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(changeType.equals("1")){
            listSketch.clear();
            float incomeFigureText = accountStisticInfoBean.getIncomeFigureText();
            if(incomeFigureText>0){
                int color = Color.parseColor("#E24A47");
                colors.add(color);
                entries.add(new PieEntry(incomeFigureText,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("图文就诊");
                listSketch.add(chartSketchBean);
            }
            float incomeVideo = accountStisticInfoBean.getIncomeVideo();
            if (incomeVideo>0) {
                int color = Color.parseColor("#EA883B");
                colors.add(color);
                entries.add(new PieEntry(incomeVideo,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("视频就诊");
                listSketch.add(chartSketchBean);
            }
            float incomeAudio = accountStisticInfoBean.getIncomeAudio();
            if (incomeAudio>0) {
                int color = Color.parseColor("#A763A5");
                colors.add(color);
                entries.add(new PieEntry(incomeAudio,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("音频就诊");
                listSketch.add(chartSketchBean);
            }

            float incomeCall = accountStisticInfoBean.getIncomeCall();
            if (incomeCall>0) {
                int color = Color.parseColor("#52B394");
                colors.add(color);
                entries.add(new PieEntry(incomeCall,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("电话就诊");
                listSketch.add(chartSketchBean);
            }

            float incomeLiveBroadcast = accountStisticInfoBean.getIncomeLiveBroadcast();
            if (incomeLiveBroadcast>0) {
                int color = Color.parseColor("#3688A1");
                colors.add(color);
                entries.add(new PieEntry(incomeLiveBroadcast,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("观看直播");
                listSketch.add(chartSketchBean);
            }

            float incomeSign = accountStisticInfoBean.getIncomeSign();
            if (incomeSign>0) {
                int color = Color.parseColor("#17B939");
                colors.add(color);
                entries.add(new PieEntry(incomeSign,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("签约服务");
                listSketch.add(chartSketchBean);
            }

            float incomeConsultation = accountStisticInfoBean.getIncomeConsultation();
            if (incomeConsultation>0) {
                int color = Color.parseColor("#EFE343");
                colors.add(color);
                entries.add(new PieEntry(incomeConsultation,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("会诊收入");
                listSketch.add(chartSketchBean);
            }
            float incomeRecharge = accountStisticInfoBean.getIncomeRecharge();
            if(incomeRecharge>0){
                int color = Color.parseColor("#B28850");
                colors.add(color);
                entries.add(new PieEntry(incomeRecharge,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("用户充值");
                listSketch.add(chartSketchBean);
            }

            float incomeCourseware = accountStisticInfoBean.getIncomeCourseware();
            if (incomeCourseware>0){
                int color = Color.parseColor("#ACD598");
                colors.add(color);
                entries.add(new PieEntry(incomeCourseware,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("课件收入");
                listSketch.add(chartSketchBean);
            }



        }else{
            listSketch.clear();
            float expendConsultation = accountStisticInfoBean.getExpendConsultation();
            if (expendConsultation>0) {
                int color = Color.parseColor("#E24A47");
                colors.add(color);
                entries.add(new PieEntry(expendConsultation,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("会诊支出");
                listSketch.add(chartSketchBean);
            }

            float expendLiveBroadcast = accountStisticInfoBean.getExpendLiveBroadcast();
            if (expendLiveBroadcast>0) {
                int color = Color.parseColor("#52B394");
                colors.add(color);
                entries.add(new PieEntry(expendLiveBroadcast,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("直播支出");
                listSketch.add(chartSketchBean);
            }
            float expendCourseware = accountStisticInfoBean.getExpendCourseware();
            if (expendCourseware>0) {
                int color = Color.parseColor("#8C97CB");
                colors.add(color);
                entries.add(new PieEntry(expendCourseware,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("课件支出");
                listSketch.add(chartSketchBean);
            }

            float expendWithdrawal = accountStisticInfoBean.getExpendWithdrawal();
            if (expendWithdrawal>0) {
                int color = Color.parseColor("#CCE198");
                colors.add(color);
                entries.add(new PieEntry(expendWithdrawal,""));
                ChartSketchBean chartSketchBean=new ChartSketchBean();
                chartSketchBean.setColorSketch(color);
                chartSketchBean.setSketchName("提现支出");
                listSketch.add(chartSketchBean);
            }


        }

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());


        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();

        sketchListAdapter.notifyDataSetChanged();
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
        if(pageIndex==1){
            listBeans.clear();
            mRefreshLayout.finishRefresh();
        }
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
    public void getSearchAccountDoctorIncomOutInfoResult(AccountStisticInfoBean accountStisticInfoBean) {
        initChartData();
        refreshData(accountStisticInfoBean);
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
