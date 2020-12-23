package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountStatisticContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.AccountStatisticPresenter;

/**
 * Description:统计图
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 14:53
 */
public class StatisticChartActivity extends AbstractMvpBaseActivity<AccountStatisticContract.View,
        AccountStatisticPresenter> implements OnChartValueSelectedListener,AccountStatisticContract.View {
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
    private TimePickerView timePickerView;
    private String currentDate="";
    private String changeType="1";
    private AccountStisticInfoBean accountStisticInfoBean;

    protected final String[] parties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    @Override
    protected int setLayoutId() {
        return R.layout.activity_stistic_chart;
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBar();
        addListener();
        initChartData();
    }


    @Override
    protected void initData() {
        super.initData();
        currentDate= DateUtils.getDeviceTimeOfYM();
        mPresenter.sendSearchAccountDoctorIncomeOutInfoRequest(currentDate,changeType,this);
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("统计");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
        toolbar.setRightTitleSearchBtnVisible(false);

    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosedTimeDialog();
            }
        });
    }


    /**
     * 预约选择时间弹框
     */
    private void showChoosedTimeDialog() {
        timePickerView = new TimePickerBuilder(this, (date, v) -> {

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
        chart.setOnChartValueSelectedListener(this);

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);


    }


    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

//        for (int i = 0; i < count; i++) {
//            entries.add(new PieEntry((float) (Math.random() * range) + range / 5,
//                    parties[i % parties.length]));
//        }
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeAudio(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeCall(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeConsultation(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeCourseware(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeCourseware(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeFigureText(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeLiveBroadcast(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeRecharge(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeSign(),""));
        entries.add(new PieEntry(accountStisticInfoBean.getIncomeVideo(),""));
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

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
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getSearchAccountDoctorIncomOutInfoResult(AccountStisticInfoBean
                                                                     accountStisticInfoBean) {
     this.accountStisticInfoBean=accountStisticInfoBean;
        setData(4,100);
    }
}
