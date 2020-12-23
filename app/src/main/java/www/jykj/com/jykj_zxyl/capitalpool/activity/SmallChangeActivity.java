package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-19 16:06
 */
public class SmallChangeActivity extends BaseActivity  {

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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_small_change;
    }

    @Override
    protected void initView() {
        super.initView();

    }



}
