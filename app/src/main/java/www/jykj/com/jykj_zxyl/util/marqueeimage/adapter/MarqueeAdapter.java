package www.jykj.com.jykj_zxyl.util.marqueeimage.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * @author fcm
 * @Create at 2013-8-27 下午2:48:34
 * @Version 1.0
 * <p>
	Features draft description.
	主要功能介绍
   </p>
 */
public class MarqueeAdapter extends PagerAdapter {
	private ArrayList<View> mPageViews=new ArrayList<View>();
	
	/**
	 * 添加数据
	 * @param mPageViews
	 */
	public void setData(ArrayList<View> mPageViews){
		this.mPageViews=mPageViews;
	}
	@Override
    public int getCount() {  
        return mPageViews.size();  
    }  

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;  
    }  

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub  
        return super.getItemPosition(object);  
    }  

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        // TODO Auto-generated method stub  
        ((ViewPager) arg0).removeView(mPageViews.get(arg1));
    }  

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        // TODO Auto-generated method stub
        ((ViewPager) arg0).addView(mPageViews.get(arg1));
        return mPageViews.get(arg1);  
    }  

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub  

    }  

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub  
        return null;  
    }  

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub  

    }  

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub  

    } 
	
}
