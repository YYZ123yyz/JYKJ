package www.jykj.com.jykj_zxyl.consultation.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.MyLiveRoomActivity;
import www.jykj.com.jykj_zxyl.activity.myself.couponFragment.FragmentAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseFragment;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;

/**
 * Description:会诊
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 10:51
 */
public class ConsultationFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_pager)
    ViewPager vpPager;
    @BindView(R.id.right_image_search)
    ImageButton ivAdd;
    private List<String> mTitles;//标题集合列表
    private List<Fragment> mFragments;//Fragment集合列表
    private FragmentAdapter fragmentAdapter;
    private MessageListFragment mMessageListFragment;
    private AllPatientListFragment mAllPatientListFragment;
    private SignUpPatientListFragment mSignUpPatientListFragment;
    private DoctorFriendsListFragment mDoctorFriendsListFragment;
    private MoreFeaturesPopupWindow mPopupWindow;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_consultation_list;
    }


    @Override
    protected void init(View view) {
        super.init(view);

        mTitles=new ArrayList<>();
        mFragments=new ArrayList<>();
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow ==null){
                    mPopupWindow = new MoreFeaturesPopupWindow(getActivity());
                }
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(ivAdd, 0, 0);
                }else {
                    mPopupWindow.dismiss();
                }
            }
        });
        //初始化tablelayout
        initTabLayout();
    }




    /**
     * 初始化TabLayout
     */
    private void initTabLayout(){
        mTitles.add("消息列表");
        mTitles.add("全部患者");
        mTitles.add("签约患者");
        mTitles.add("医生好友");
        if (mMessageListFragment==null) {
            mMessageListFragment=new MessageListFragment();
        }
        if (mAllPatientListFragment==null) {
            mAllPatientListFragment=new AllPatientListFragment();
        }
        if(mSignUpPatientListFragment==null){
            mSignUpPatientListFragment=new SignUpPatientListFragment();
        }
        if(mDoctorFriendsListFragment==null){
            mDoctorFriendsListFragment=new DoctorFriendsListFragment();
        }
        mFragments.add(mMessageListFragment);
        mFragments.add(mAllPatientListFragment);
        mFragments.add(mSignUpPatientListFragment);
        mFragments.add(mDoctorFriendsListFragment);
        fragmentAdapter = new FragmentAdapter(this.getChildFragmentManager(),
                mFragments, mTitles);
        vpPager.setAdapter(fragmentAdapter);
        vpPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(vpPager);//与ViewPage建立关系
    }


}
