package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.hyphenate.easeui.jykj.utils.DateUtils;
import java.util.ArrayList;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountStatisticContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountStatisticPresenter;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;

/**
 * Description:统计图
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 14:53
 */
public class StatisticChartActivity extends AbstractMvpBaseActivity<AccountStatisticContract.View,
        AccountStatisticPresenter> implements AccountStatisticContract.View {
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.ll_pay_root)
    LinearLayout llPayRoot;
    @BindView(R.id.ll_income_root)
    LinearLayout llIncomeRoot;
    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.tv_filter_btn)
    TextView tvFilterBtn;
    @BindView(R.id.tv_pay_text)
    TextView tvPayText;
    @BindView(R.id.tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.tv_income_text)
    TextView tvIncomeText;
    @BindView(R.id.tv_income_amount)
    TextView tvIncomeAmount;
    @BindView(R.id.ll_income_diagram_root)
    LinearLayout llIncomDiagramRoot;
    @BindView(R.id.ll_pay_diagram_root)
    LinearLayout llPayDiagramRoot;
    @BindView(R.id.ll_content_root)
    LinearLayout llContentRoot;
    private String currentDate="";
    private String changeType="1";
    private boolean isShowLoading;
    private ImageButton imageButtonE;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    @Override
    protected int setLayoutId() {
        return R.layout.activity_stistic_chart;
    }


    @Override
    protected void initView() {
        super.initView();
        imageButtonE = findViewById(R.id.right_image_search);
        setToolBar();
        initLoadingAndRetryManager();
        addListener();
        initChartData();
        setPayModeStatus();
    }


    @Override
    protected void initData() {
        super.initData();
        currentDate= DateUtils.getDeviceTimeOfYM();
        tvFilterBtn.setText(currentDate);
        mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,this);
    }


    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(llContentRoot);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,this);

        });
        mLoadingLayout.showLoading();
    }
    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("统计");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(StatisticChartActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });

    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvFilterBtn.setOnClickListener(v -> showChoosedTimeDialog());
        llPayRoot.setOnClickListener(v -> {
            changeType="2";
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    StatisticChartActivity.this);
            isShowLoading=true;
            setPayModeStatus();

        });
        llIncomeRoot.setOnClickListener(v -> {
            changeType="1";
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    StatisticChartActivity.this);
            isShowLoading=true;
            setPayModeStatus();
        });
    }

    /**
     * 设置支付方式按钮状态
     */
    private void setPayModeStatus(){
        if (changeType.equals("1")) {
            tvPayText.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvPayAmount.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvIncomeText.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvIncomeAmount.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            llIncomDiagramRoot.setVisibility(View.VISIBLE);
            llPayDiagramRoot.setVisibility(View.GONE);
        }else if(changeType.equals("2")){
            tvPayText.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvPayAmount.setTextColor(ContextCompat.getColor(this,R.color.color_000000));
            tvIncomeText.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            tvIncomeAmount.setTextColor(ContextCompat.getColor(this,R.color.color_999999));
            llIncomDiagramRoot.setVisibility(View.GONE);
            llPayDiagramRoot.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 预约选择时间弹框
     */
    private void showChoosedTimeDialog() {
        TimePickerView timePickerView = new TimePickerBuilder(this, (date, v) -> {
            isShowLoading=true;
            currentDate = DateUtils.getDateYYYMM(date);
            tvFilterBtn.setText(currentDate);
            mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,
                    StatisticChartActivity.this);
        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }

    private void initChartData(){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        //chart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);


    }


    /**
     * 刷新数据
     * @param accountStisticInfoBean 统计数据
     */
    private void refreshData( AccountStisticInfoBean accountStisticInfoBean) {
        tvPayAmount.setText(String.format("¥ %s", accountStisticInfoBean.getTotalOut()));
        tvIncomeAmount.setText(String.format("¥ %s", accountStisticInfoBean.getTotalIncome()));
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        if(changeType.equals("1")){
            float incomeFigureText = accountStisticInfoBean.getIncomeFigureText();
            if(incomeFigureText>0){
                colors.add(Color.parseColor("#E24A47"));
                entries.add(new PieEntry(incomeFigureText,""));
            }
            float incomeVideo = accountStisticInfoBean.getIncomeVideo();
            if (incomeVideo>0) {
                colors.add(Color.parseColor("#EA883B"));
                entries.add(new PieEntry(incomeVideo,""));
            }
            float incomeAudio = accountStisticInfoBean.getIncomeAudio();
            if (incomeAudio>0) {
                colors.add(Color.parseColor("#A763A5"));
                entries.add(new PieEntry(incomeAudio,""));
            }

            float incomeCall = accountStisticInfoBean.getIncomeCall();
            if (incomeCall>0) {
                colors.add(Color.parseColor("#52B394"));
                entries.add(new PieEntry(incomeCall,""));
            }

            float incomeLiveBroadcast = accountStisticInfoBean.getIncomeLiveBroadcast();
            if (incomeLiveBroadcast>0) {
                colors.add(Color.parseColor("#3688A1"));
                entries.add(new PieEntry(incomeLiveBroadcast,""));
            }

            float incomeSign = accountStisticInfoBean.getIncomeSign();
            if (incomeSign>0) {
                colors.add(Color.parseColor("#17B939"));
                entries.add(new PieEntry(incomeSign,""));
            }

            float incomeConsultation = accountStisticInfoBean.getIncomeConsultation();
            if (incomeConsultation>0) {
                colors.add(Color.parseColor("#EFE343"));
                entries.add(new PieEntry(incomeConsultation,""));
            }


        }else{
            float expendConsultation = accountStisticInfoBean.getExpendConsultation();
            if (expendConsultation>0) {
                colors.add(Color.parseColor("#E24A47"));
                entries.add(new PieEntry(expendConsultation,""));
            }

            float expendLiveBroadcast = accountStisticInfoBean.getExpendLiveBroadcast();
            if (expendLiveBroadcast>0) {
                colors.add(Color.parseColor("#52B394"));
                entries.add(new PieEntry(expendLiveBroadcast,""));
            }

        }


        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);




        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


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
    }



    @Override
    public void showLoading(int code) {
        if (isShowLoading) {
            showLoading("", null);
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        mLoadingLayout.showEmpty();
    }

    @Override
    public void showRetry() {
        super.showRetry();

        mLoadingLayout.showError();
    }

    @Override
    public void getSearchAccountDoctorIncomOutInfoResult(AccountStisticInfoBean
                                                                 accountStisticInfoBean) {
        mLoadingLayout.showContent();
        refreshData(accountStisticInfoBean);
    }
}
