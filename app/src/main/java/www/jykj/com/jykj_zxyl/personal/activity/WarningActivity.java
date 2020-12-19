package www.jykj.com.jykj_zxyl.personal.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.view.Gravity;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.mypatient.fragment.NotNormalFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.NotRemindFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.NotWarningFragment;
import www.jykj.com.jykj_zxyl.mypatient.fragment.RedHighRiskFragment;

import www.jykj.com.jykj_zxyl.personal.WarningContract;
import www.jykj.com.jykj_zxyl.personal.WarningPresenter;
import www.jykj.com.jykj_zxyl.personal.adapter.WarningTableViewAdapter;
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
    private WarningTableViewAdapter tableViewAdapter;

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


    private void initViewPager() {
        List<String> titleList = new ArrayList<>();
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
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_right_part:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }

    }
}
