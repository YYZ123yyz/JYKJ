package www.jykj.com.jykj_zxyl.activity.myself.coupon.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.MyViewPager;

/**
 * 优惠券
 */
public class CouponActivity extends BaseActivity implements View.OnClickListener{
    private BaseToolBar mToolBar;//顶部toolBar
   private CouponActivity mActivity;
    private TabLayout mTabLayout;
    private MyViewPager mVpager;
    private ImageButton imageButtonE;
    private TextView gogetcoupons,beusable,used;
    private ViewPager roompager;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;
    private List<String> mTitles;
    private RecyclerView recy;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initView() {
        super.initView();
        mActivity=this;
        mToolBar=findViewById(R.id.toolbar);
        mTabLayout=findViewById(R.id.tab_layout);
        mVpager=findViewById(R.id.vp_pager);
        imageButtonE = findViewById(R.id.right_image_search);
        setToolBar();
        initTabLayout();
    }
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();

    }

    private void initTabLayout() {
        //去使用
        gogetcoupons = findViewById(R.id.gogetcoupons);
        gogetcoupons.setOnClickListener(this);
        //可使用
        beusable = findViewById(R.id.beusable);
        beusable.setOnClickListener(this);
        //已使用
        used = findViewById(R.id.used);
        used.setOnClickListener(this);

        recy = findViewById(R.id.recy);


    }

    @Override
    protected void initData() {
        super.initData();

    }

    private void setToolBar() {
        mToolBar.setMainTitle("优惠券");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
        //add
        mToolBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(mActivity);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.room_forecast:

                break;
            case R.id.room_Hit:

                break;
            case R.id.room_Lecture:

                break;
        }
    }
}