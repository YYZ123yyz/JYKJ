package www.jykj.com.jykj_zxyl.personal.activity;


import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.mypatient.fragment.NotNormalFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.NotRemindFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.NotWarningFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.RedHighRiskFragment;

import www.jykj.com.jykj_zxyl.personal.WarningContract;
import www.jykj.com.jykj_zxyl.personal.WarningPresenter;
import www.jykj.com.jykj_zxyl.personal.adapter.WarningTableViewAdapter;
import www.jykj.com.jykj_zxyl.personal.bean.AllNumBean;
import www.jykj.com.jykj_zxyl.util.StringUtils;
import yyz_exploit.dialog.ImageView;

public class WarningActivity extends AbstractMvpBaseActivity<WarningContract.View
        , WarningPresenter> implements WarningContract.View {
    private String stateType_0 = "0";
    private String stateType_1 = "0";
    private String stateType_2 = "0";
    private String stateType_3 = "0";
    private String stateType_4 = "0";
    private String stateType_5 = "0";
    @BindView(R.id.table_layout)
    TabLayout tableLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private String patientName = "";
    private String ageStart = "";
    private String ageEnd = "";
    private JYKJApplication mApp;
    private WarningTableViewAdapter tableViewAdapter;
    private List<String> titleList;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_warning;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        initViewPager();
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getStateNum(getParams());
    }

    private String getParams() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginDoctorPosition", ParameUtil.loginDoctorPosition);
        map.put("searchDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        map.put("searchFlagSigning", "1");

        map.put("patientName", patientName);
        map.put("ageStart", ageStart);
        map.put("ageEnd", ageEnd);

        return RetrofitUtil.encodeParam(map);
    }


    private void initViewPager() {
        titleList = new ArrayList<>();
        titleList.add("低危" + "(" + stateType_0 + ")");
        titleList.add("中危" + "(" + stateType_3 + ")");
        titleList.add("高危" + "(" + stateType_2 + ")");
        titleList.add("很高危" + "(" + stateType_1 + ")");
        titleList.add("正常" + "(" + stateType_4 + ")");
        titleList.add("全部" + "(" + stateType_5 + ")");

        List<Fragment> viewList = new ArrayList<>();
        //全部
        viewList.add(new RedHighRiskFragment());
        //预警
        viewList.add(new NotWarningFragment());
        //提醒
        viewList.add(new NotRemindFragment());
        //正常
        viewList.add(new NotNormalFragment());
        viewList.add(new NotNormalFragment());
        viewList.add(new NotNormalFragment());

        // 给TableLayout添加tab选项卡
        for (int i = 0; i < titleList.size(); i++) {
            tableLayout.addTab(tableLayout.newTab().setText(titleList.get(i)));
        }
        // 初始化ViewPager适配器
        tableViewAdapter = new WarningTableViewAdapter(getSupportFragmentManager(), viewList, titleList);
        // 给ViewPager设置适配器
        viewpager.setAdapter(tableViewAdapter);
        // 设置TableLayout为可滚动（在ViewPager设置Adapter之后），也可在布局中添加tabMode属性
        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewpager.setOffscreenPageLimit(4);
        // 将TabLayout和ViewPager关联起来
        tableLayout.setupWithViewPager(viewpager);
        // 给Tabs设置适配器
        tableLayout.setTabsFromPagerAdapter(tableViewAdapter);
    }


    @OnClick({R.id.iv_right_part})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right_part:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }

    }

    @Override
    public void getNumSucess(AllNumBean data) {

        stateType_0 = data.getStateType_10();
        stateType_1 = data.getStateType_20();
        stateType_2 = data.getStateType_30();
        stateType_3 = data.getStateType_40();
        stateType_4 = data.getStateType_50();
        stateType_5 = data.getStateType_0();

        stateType_0 = StringUtils.isNotEmpty(stateType_0) ? "低危(" + stateType_0 + ")" : "低危(0)";
        stateType_1 = StringUtils.isNotEmpty(stateType_1) ? "中危(" + stateType_1 + ")" : "中危(0)";
        stateType_2 = StringUtils.isNotEmpty(stateType_2) ? "高危(" + stateType_2 + ")" : "高危(0)";
        stateType_3 = StringUtils.isNotEmpty(stateType_3) ? "很高危(" + stateType_3 + ")" : "很高危(0)";
        stateType_4 = StringUtils.isNotEmpty(stateType_4) ? "正常(" + stateType_4 + ")" : "正常(0)";
        stateType_5 = StringUtils.isNotEmpty(stateType_5) ? "全部(" + stateType_5 + ")" : "全部(0)";


        TabLayout.Tab tabAt0 = tableLayout.getTabAt(0);
        if (tabAt0 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_0);
            ForegroundColorSpan redSpan0 = new ForegroundColorSpan(Color.parseColor("#FEA32C"));
            builder.setSpan(redSpan0, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tabAt0.setText(builder);
        }
        TabLayout.Tab tabAt1 = tableLayout.getTabAt(1);
        if (tabAt1 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_1);
            ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.parseColor("#FE6600"));
            builder.setSpan(redSpan1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tabAt1.setText(builder);
        }
        TabLayout.Tab tabAt2 = tableLayout.getTabAt(2);
        if (tabAt2 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_2);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.parseColor("#FE2C2C"));
            builder.setSpan(redSpan2, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tabAt2.setText(builder);
        }
        TabLayout.Tab tabAt3 = tableLayout.getTabAt(3);
        if (tabAt3 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_3);
            ForegroundColorSpan redSpan3 = new ForegroundColorSpan(Color.parseColor("#D30005"));
            builder.setSpan(redSpan3, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            tabAt3.setText(builder);
        }


        TabLayout.Tab tabAt4 = tableLayout.getTabAt(4);
        if (tabAt4 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_4);
            ForegroundColorSpan redSpan4 = new ForegroundColorSpan(Color.parseColor("#38CF40"));
            builder.setSpan(redSpan4, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tabAt4.setText(builder);
        }

        TabLayout.Tab tabAt5 = tableLayout.getTabAt(5);
        if (tabAt5 != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(stateType_5);
            ForegroundColorSpan redSpan5 = new ForegroundColorSpan(Color.parseColor("#333333"));
            builder.setSpan(redSpan5, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tabAt5.setText(builder);
        }
    }

    @Override
    public void getNumFail() {

    }
}
