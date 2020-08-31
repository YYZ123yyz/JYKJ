package www.jykj.com.jykj_zxyl.appointment.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.appointment.fragment.HistoryRecordsListFragment;
import www.jykj.com.jykj_zxyl.appointment.fragment.NotComplateListFragment;
import www.jykj.com.jykj_zxyl.appointment.listener.MyItemClickListener;
import www.jykj.com.jykj_zxyl.appointment.view.FirstView;
import www.jykj.com.jykj_zxyl.appointment.view.SecView;

/**
 * Description:我的诊所
 *
 * @author: qiuxinhai
 * @date: 2020-08-26 18:12
 */
public class MyClinicDetialActivity extends BaseActivity implements MyItemClickListener {
    @BindView(R.id.txt_left_title)
    TextView txtLeftTitle;
    @BindView(R.id.left_image_id)
    ImageButton leftImageId;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.txt_right_title)
    TextView txtRightTitle;
    @BindView(R.id.right_image_search)
    ImageButton rightImageSearch;
    @BindView(R.id.right_image_id)
    ImageButton rightImageId;
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.ll_sign_up_treatment)
    LinearLayout llSignUpTreatment;
    @BindView(R.id.iv_sign_up_treatment)
    ImageView ivSignUpTreatment;
    @BindView(R.id.ll_one_treatment)
    LinearLayout llOneTreatment;
    @BindView(R.id.iv_one_treatment)
    ImageView ivOneTreatment;
    @BindView(R.id.ll_timely_treatment)
    LinearLayout llTimelyTreatment;
    @BindView(R.id.iv_timely_treatment)
    ImageView ivTimelyTreatment;
    @BindView(R.id.ll_sign_up_root)
    LinearLayout llSignUpRoot;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPager;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private int visitType=1;//就诊类型 1签约就诊 2一次性就诊 3及时就诊
    private String headers[] = {"预约日期", "价格","接诊状态","更多筛选"};
    private List<String> mTitles;
    private List<View> popupViews;
    private List<Fragment> fragments;
    private int mPageSelect;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mTitles=new ArrayList<>();
        fragments=new ArrayList<>();
        popupViews=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_clinic_detial;
    }

    @Override
    protected void initView() {
        super.initView();
        mTitles.add("未完成");
        mTitles.add("历史记录");
        setToolBar();
        //初始化popview
        initPopupView();
        //添加监听
        addListener();
        //设置选中状态
        setChooseStatus(visitType);
        //初始化viewpager
        initViewPager();
    }


    /**
     * 初始化popupView
     */
    private void initPopupView(){
        FirstView firstView = new FirstView(MyClinicDetialActivity.this);
        firstView.setListener(this);
        popupViews.add(firstView.firstView());

        SecView secView = new SecView(this);
        secView.setListener(this);
        popupViews.add(secView.secView());

        SecView thirdView = new SecView(this);
        thirdView.setListener(this);
        popupViews.add(thirdView.secView());


        SecView fourView = new SecView(this);
        fourView.setListener(this);
        popupViews.add(fourView.secView());

        /**
         * Dropdownmenu下面的主体部分
         * */
        View fifthView = LayoutInflater.from(this).inflate(R.layout.activity_one_visit, null);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, fifthView);
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager(){
        fragments.add(new NotComplateListFragment());
        fragments.add(new HistoryRecordsListFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments, mTitles);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPageSelect = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("我的诊所");
        toolbar.setRightTitleDrawable(R.mipmap.bg_schedu_set);
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 设置按钮状态
     * @param visitType 类型
     */
    private void setChooseStatus(int visitType) {
        switch (visitType) {
            case 1:
                llSignUpRoot.setVisibility(View.VISIBLE);
                dropDownMenu.setVisibility(View.GONE);
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment_press);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment);
                break;
            case 2:
                llSignUpRoot.setVisibility(View.GONE);
                dropDownMenu.setVisibility(View.VISIBLE);
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment_press);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment);
                break;
            case 3:
                ivSignUpTreatment.setImageResource(R.mipmap.bg_sign_up_treatment);
                ivOneTreatment.setImageResource(R.mipmap.bg_one_treatment);
                ivTimelyTreatment.setImageResource(R.mipmap.bg_timely_treatment_press);
                break;
            default:
        }
    }


    /**
     * 添加监听
     */
    private void addListener(){
        llSignUpTreatment.setOnClickListener(v -> {
            visitType=1;
            setChooseStatus(visitType);
        });
        llOneTreatment.setOnClickListener(v -> {
            visitType=2;
            setChooseStatus(visitType);
        });
        llTimelyTreatment.setOnClickListener(v -> {
            visitType=3;
            setChooseStatus(visitType);
        });
    }
    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onItemClick(View view, int postion, String string) {

    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}
