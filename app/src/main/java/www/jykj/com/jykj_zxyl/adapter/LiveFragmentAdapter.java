package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class LiveFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;//各导航的Fragment
    private List<String> mTitle; //导航的标题
    public LiveFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> title){
        super(fragmentManager);
        this.mFragmentList = fragmentList;
        this.mTitle =  title;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
