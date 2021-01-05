package www.jykj.com.jykj_zxyl.mypatient.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

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
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tv_start_age)
    TextView tvStartAge;
    @BindView(R.id.tv_end_age)
    TextView tvEndAge;
     private JYKJApplication mApp;
    private String stateType_0="0";
    private String stateType_1 = "0";
    private String stateType_2 = "0";
    private String stateType_3 = "0";
    private String stateType_4 = "0";
    private String stateType_5 = "0";
    private MyTableViewAdapter tableViewAdapter;
    private List<Fragment> viewList;
    private String startAge;
    private String endAge;
    private int currentPos;

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


    @OnClick({R.id.iv_right_part,R.id.tv_ensure_btn,R.id.tv_reset_btn,
            R.id.tv_start_age,R.id.tv_end_age})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right_part:
                drawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.tv_start_age:
                showChoosedAgeDialog(1);
                 break;
            case R.id.tv_end_age:
                showChoosedAgeDialog(2);
                break;
            case R.id.tv_reset_btn:
                 edInputContent.setText("");
                 startAge=null;
                 endAge=null;
                for (Fragment fragment : viewList) {
                    NotSignedPatientChildFragment childFragment
                            = (NotSignedPatientChildFragment) fragment;
                    childFragment.resetData("",null,null);
                }
                break;
            case R.id.tv_ensure_btn:
                String patientName = edInputContent.getText().toString();
//                if (TextUtils.isEmpty(patientName)) {
//                    ToastUtils.showToast("姓名不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(startAge)) {
//                    ToastUtils.showToast("请输入开始年龄");
//                    return;
//                }
//                if (TextUtils.isEmpty(endAge)) {
//                    ToastUtils.showToast("请输入结束年龄");
//                    return;
//                }
                drawerLayout.closeDrawers();
                NotSignedPatientChildFragment notSignedPatientChildFragment
                        = (NotSignedPatientChildFragment) viewList.get(currentPos);
                if (notSignedPatientChildFragment!=null) {
                    notSignedPatientChildFragment.searchData(patientName, StringUtils.isNotEmpty(startAge) ?
                            Integer.parseInt(startAge) : null, StringUtils.isNotEmpty(endAge) ? Integer.parseInt(endAge) : null);
                }

                break;
            default:
        }

    }

    /**
     * 选择年龄范围弹框
     * @param type 1为开始年龄 2为结束年龄
     */
    private void showChoosedAgeDialog(int type) {
        List<String> stringList=new ArrayList<>();
        for(int i=0;i<100;i++) {
            stringList.add(i+1+"");
        }

        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this.getContext(),
                (options1, options2, options3, v) -> {
                    if (type==1) {
                        startAge=options1+"";
                        tvStartAge.setText(startAge);
                    }else if(type==2){
                        endAge=options1+"";
                        tvEndAge.setText(endAge);
                    }
                }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(stringList, null, null);
        optionPickUnit.show();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager(){
        List<String> titleList = new ArrayList<>();
        viewList = new ArrayList<>();
        titleList.add("低危");
        titleList.add("中危");
        titleList.add("高危");
        titleList.add("很高危");
        titleList.add("正常");
        titleList.add("全部");
        for (int i = 0; i < titleList.size(); i++) {
            switch (i){
                case 0:
                    viewList.add(NotSignedPatientChildFragment.newInstance("10"));
                    break;
                case 1:
                    viewList.add(NotSignedPatientChildFragment.newInstance("20"));
                    break;
                case 2:
                    viewList.add(NotSignedPatientChildFragment.newInstance("30"));
                    break;
                case 3:
                    viewList.add(NotSignedPatientChildFragment.newInstance("40"));
                    break;
                case 4:
                    viewList.add(NotSignedPatientChildFragment.newInstance("50"));
                    break;
                case 5:
                    viewList.add(NotSignedPatientChildFragment.newInstance("0"));
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
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPos=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void getOperNumberResult(Status status) {
        if(status!=null){
            stateType_0 = status.getStateType_10();
            stateType_1 = status.getStateType_20();
            stateType_2 = status.getStateType_30();
            stateType_3 = status.getStateType_40();
            stateType_4 = status.getStateType_50();
            stateType_5 = status.getStateType_0();
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
            tableViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading(int code) {

    }
}