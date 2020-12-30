package www.jykj.com.jykj_zxyl.mypatient.fragment;

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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.mypatient.adapter.MyTableViewAdapter;
import www.jykj.com.jykj_zxyl.mypatient.contract.SignedPatientContract;
import www.jykj.com.jykj_zxyl.mypatient.presenter.SignedPatientPresenter;
import www.jykj.com.jykj_zxyl.util.StringUtils;
import yyz_exploit.bean.Status;

/**
 * 未签约患者
 */
public class NotSignedPatientFragment extends AbstractMvpBaseFragment<SignedPatientContract.View,
        SignedPatientPresenter> implements SignedPatientContract.View {

    @BindView(R.id.table_layout)
    TabLayout tableLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
     private JYKJApplication mApp;
    private String stateType_0;
    private String stateType_1;
    private String stateType_2;
    private String stateType_3;
    private MyTableViewAdapter tableViewAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_signed_patient_fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mApp = (JYKJApplication)getActivity(). getApplication();
        initViewPager();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.sendOperNumberRequest("10","1",mApp.loginDoctorPosition,mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode(),"0");
    }


    @Override
    protected void initData() {
        super.initData();

    }


    @OnClick({R.id.iv_right_part})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right_part:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }

    }
    /**
     * 初始化ViewPager
     */
    private void initViewPager(){
        List<String> titleList = new ArrayList<>();
//        titleList.add("全部"+"("+stateType_0+")");
//        titleList.add("预警"+"("+stateType_3+")");
//        titleList.add("提醒"+"("+stateType_2+")");
//        titleList.add("正常"+"("+stateType_1+")");
        List<Fragment> viewList = new ArrayList<>();
        titleList.add("低危");
        titleList.add("中危");
        titleList.add("高危");
        titleList.add("很高危");
        titleList.add("正常");
        titleList.add("全部");
        for (int i = 0; i < titleList.size(); i++) {
            switch (i){
                case 0:
                    viewList.add(SignedPatientChildFragment.newInstance("10"));
                    break;
                case 1:
                    viewList.add(SignedPatientChildFragment.newInstance("20"));
                    break;
                case 2:
                    viewList.add(SignedPatientChildFragment.newInstance("30"));
                    break;
                case 3:
                    viewList.add(SignedPatientChildFragment.newInstance("40"));
                    break;
                case 4:
                    viewList.add(SignedPatientChildFragment.newInstance("50"));
                    break;
                case 5:
                    viewList.add(SignedPatientChildFragment.newInstance("0"));
                    break;
                default:

            }
        }

        // 给TableLayout添加tab选项卡
        for (int i = 0 ; i < titleList.size() ; i++){
            tableLayout.addTab(tableLayout.newTab().setText(titleList.get(i)));
        }
        // 初始化ViewPager适配器
        tableViewAdapter = new MyTableViewAdapter(getChildFragmentManager(),viewList,titleList);
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


    @Override
    public void getOperNumberResult(Status status) {
        if(status!=null){
            String stateType_10 = status.getStateType_10();
            String stateType_20 = status.getStateType_20();
            String stateType_30 = status.getStateType_30();
            String stateType_40 = status.getStateType_40();
            String stateType_50 = status.getStateType_50();
            String stateType_0 = status.getStateType_0();

            TabLayout.Tab tabAt0 = tableLayout.getTabAt(0);
            if (tabAt0!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder("低危"+"("+ stateType_10 + ")");

                ForegroundColorSpan redSpan0 = new ForegroundColorSpan(Color.parseColor("#FEA32C"));
                builder.setSpan(redSpan0, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt0.setText(builder);
            }
            TabLayout.Tab tabAt1 = tableLayout.getTabAt(1);
            if (tabAt1!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder("中危"+"("+ stateType_20 + ")");
                ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.parseColor("#FE2C2C"));
                builder.setSpan(redSpan1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt1.setText(builder);
            }
            TabLayout.Tab tabAt2 = tableLayout.getTabAt(2);
            if (tabAt2!=null) {
                tabAt2.setText("高危"+"("+stateType_30+")");

                SpannableStringBuilder builder = new SpannableStringBuilder("高危"+"("+ stateType_30 + ")");
                ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.parseColor("#FE6600"));
                builder.setSpan(redSpan1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt1.setText(builder);
            }
            TabLayout.Tab tabAt3 = tableLayout.getTabAt(3);
            if (tabAt3!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder("很高危"+"("+ stateType_40 + ")");
                ForegroundColorSpan redSpan3 = new ForegroundColorSpan(Color.parseColor("#D30005"));
                builder.setSpan(redSpan3, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt3.setText(builder);
            }

            TabLayout.Tab tabAt4 = tableLayout.getTabAt(4);

            if (tabAt4!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder("正常"+"("+ stateType_50 + ")");
                ForegroundColorSpan redSpan4 = new ForegroundColorSpan(Color.parseColor("#38CF40"));
                builder.setSpan(redSpan4, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt4.setText(builder);
            }
            TabLayout.Tab tabAt5 = tableLayout.getTabAt(5);
            if (tabAt5!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder("正常"+"("+ stateType_0 + ")");
                ForegroundColorSpan redSpan5 = new ForegroundColorSpan(Color.parseColor("#333333"));
                builder.setSpan(redSpan5, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tabAt5.setText(builder);
            }
            tableViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading(int code) {

    }
}