package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.DocterFriendEntity;
import entity.DoctorInfo.InteractDoctorUnionInfo;
import entity.yhhd.ProvideDoctorGoodFriendGroup;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * Created by admin on 2017/5/22.
 * 医生好友适配器
 */

public class DorcerFriendExpandableListViewAdapter extends BaseExpandableListAdapter {

    private ImageLoadingListener animateFirstListener = new DorcerFriendExpandableListViewAdapter.AnimateFirstDisplayListener();


    public List<ProvideDoctorGoodFriendGroup> mDatas = new ArrayList<>();
    public Context mContext;
    public JYKJApplication  mApp;

    public DorcerFriendExpandableListViewAdapter(List<ProvideDoctorGoodFriendGroup> unitList, Context context, JYKJApplication application){
        mDatas.clear();
        mDatas.addAll(unitList);
        mContext = context;
        mApp = application;
    }

    public void setDate(List<ProvideDoctorGoodFriendGroup> list) {
        mDatas.clear();
        mDatas.addAll(list);
    }


    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return
                mDatas.get(i).getDoctorGoodFriendInfoList().size();

    }

    @Override
    public Object getGroup(int i) {
        return mDatas.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDatas.get(i).getDoctorGoodFriendInfoList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {

        return i1;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupViewHolder = new GroupViewHolder();
            view = inflater.inflate(R.layout.item_docterfriend_title, null);
            groupViewHolder.mImageView = (ImageView)view.findViewById(R.id.item_fragmentHYHD_titleImage);
            groupViewHolder.mTitleName = (TextView)view.findViewById(R.id.item_fragmentHYHD_titleName);
            view.setTag(groupViewHolder);
        }
        else
        {
            groupViewHolder = (GroupViewHolder)view.getTag();
        }


        groupViewHolder.mTitleName.setText(mDatas.get(i).getGroupName());
        if (b)
        {
            groupViewHolder.mImageView.setBackgroundResource(R.mipmap.arrow_dd);
        }
        else
        {
            groupViewHolder.mImageView.setBackgroundResource(R.mipmap.arrow_hyhdr);
        }

        return view;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = new ChildViewHolder();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_docterfriend_user, null);
            childViewHolder.mImageView = (ImageView)view.findViewById(R.id.tv_itemUserImageText);
            childViewHolder.userName = (TextView) view.findViewById(R.id.tv_itemUserNameText);
            childViewHolder.userSSY = (TextView)view.findViewById(R.id.tv_itemSSYText);
            childViewHolder.unionName = (TextView)view.findViewById(R.id.tv_itemUnionNameText);
            childViewHolder.date = (TextView)view.findViewById(R.id.tv_date);
            view.setTag(childViewHolder);
        }
        else
        {
            childViewHolder = (ChildViewHolder)view.getTag();
        }
        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         */
        mApp.imageLoader.displayImage(mDatas.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorUserLogoUrl(), childViewHolder.mImageView, mApp.mImageOptions, animateFirstListener);
        childViewHolder.userName.setText(mDatas.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorUserName());
        childViewHolder.userSSY.setText(mDatas.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorTitleName());
        childViewHolder.unionName.setText(mDatas.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorHospitalInfo());
        childViewHolder.date.setText(mDatas.get(i).getDoctorGoodFriendInfoList().get(i1).getDoctorNewLoginDate());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    static class GroupViewHolder {
        ImageView mImageView;
        TextView  mTitleName;
    }

    static class ChildViewHolder {
        ImageView mImageView;
        TextView userName;
        TextView userSSY;
        TextView unionName;
        TextView date;
    }

    /**
     * 图片加载第一次显示监听器
     * @author Administrator
     *
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
