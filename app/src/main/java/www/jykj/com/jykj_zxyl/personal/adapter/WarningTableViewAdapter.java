package www.jykj.com.jykj_zxyl.personal.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

public class WarningTableViewAdapter extends FragmentPagerAdapter {
    List<Fragment> pagerList;
    List<String> titleList;
    public WarningTableViewAdapter(FragmentManager fm , List<Fragment> pagerList , List<String> titleList) {
        super(fm);
        this.pagerList = pagerList;
        this.titleList = titleList;
    }
    @Override
    public int getCount() {
        return pagerList != null ? pagerList.size() : 0;
    }
    @Override
    public Fragment getItem(int position) {
        return pagerList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder(titleList.get(position));
        switch (position) {
            case 0:
                ForegroundColorSpan redSpan0 = new ForegroundColorSpan(Color.parseColor("#FEA32C"));
                builder.setSpan(redSpan0, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;

            case 1:
                ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.parseColor("#FE6600"));
                builder.setSpan(redSpan1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.parseColor("#FE2C2C"));
                builder.setSpan(redSpan2, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 3:
                ForegroundColorSpan redSpan3 = new ForegroundColorSpan(Color.parseColor("#D30005"));
                builder.setSpan(redSpan3, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 4:
                ForegroundColorSpan redSpan4 = new ForegroundColorSpan(Color.parseColor("#38CF40"));
                builder.setSpan(redSpan4, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 5:
                ForegroundColorSpan redSpan5 = new ForegroundColorSpan(Color.parseColor("#333333"));
                builder.setSpan(redSpan5, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }

       /* ForegroundColorSpan redSpan0 = new ForegroundColorSpan(Color.parseColor("#FEA32C"));
        builder.setSpan(redSpan0, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        return builder;
    }

    public void updataData(List<String> list ){
        LogUtils.e("xxx更新   "+list.size());
        titleList= list;
        notifyDataSetChanged();
    }
}