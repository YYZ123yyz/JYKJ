package www.jykj.com.jykj_zxyl.mypatient.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

    /**
     * 初始化ViewPager
     */
    private void initViewPager(){
        List<String> titleList = new ArrayList<>();
        titleList.add("全部"+"("+stateType_0+")");
        titleList.add("预警"+"("+stateType_3+")");
        titleList.add("提醒"+"("+stateType_2+")");
        titleList.add("正常"+"("+stateType_1+")");

        List<Fragment> viewList = new ArrayList<>();
        //全部
        viewList.add(new NotAllFragment());
        //预警
        viewList.add(new NotWarningFragment());
        //提醒
        viewList.add(new NotRemindFragment());
        //正常
        viewList.add(new NotNormalFragment());

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
            stateType_0 = status.getStateType_0();
            stateType_1 = status.getStateType_1();
            stateType_2 = status.getStateType_2();
            stateType_3 = status.getStateType_3();
            stateType_0= StringUtils.isNotEmpty(stateType_0)?stateType_0:"0";
            stateType_1=StringUtils.isNotEmpty(stateType_1)?stateType_1:"0";
            stateType_2=StringUtils.isNotEmpty(stateType_2)?stateType_2:"0";
            stateType_3=StringUtils.isNotEmpty(stateType_3)?stateType_3:"0";
            TabLayout.Tab tabAt0 = tableLayout.getTabAt(0);
            if (tabAt0!=null) {
                tabAt0.setText("全部"+"("+stateType_0+")");
            }
            TabLayout.Tab tabAt1 = tableLayout.getTabAt(1);
            if (tabAt1!=null) {
                tabAt1.setText("预警"+"("+stateType_3+")");
            }
            TabLayout.Tab tabAt2 = tableLayout.getTabAt(2);
            if (tabAt2!=null) {
                tabAt2.setText("预警"+"("+stateType_2+")");
            }
            TabLayout.Tab tabAt3 = tableLayout.getTabAt(1);
            if (tabAt3!=null) {
                tabAt3.setText("预警"+"("+stateType_1+")");
            }

        }
    }

    @Override
    public void showLoading(int code) {

    }
}