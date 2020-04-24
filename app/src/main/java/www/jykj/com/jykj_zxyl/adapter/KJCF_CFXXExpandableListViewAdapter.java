package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.DoctorInfo.InteractDoctorUnionInfo;
import entity.wdzs.ProvideInteractOrderPrescribeGroup;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * Created by admin on 2017/5/22.
 * 处方列表适配器
 */

public class KJCF_CFXXExpandableListViewAdapter extends BaseExpandableListAdapter {



    public List<ProvideInteractOrderPrescribeGroup> mDatas = new ArrayList<>();
    public Context mContext;
    public JYKJApplication  mApp;

    public KJCF_CFXXExpandableListViewAdapter(List<ProvideInteractOrderPrescribeGroup> list, Context context, JYKJApplication application){
        mDatas.clear();
        mDatas.addAll(list);
        mContext = context;
        mApp = application;
    }

    public void setDate(List<ProvideInteractOrderPrescribeGroup> list) {
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
                mDatas.get(i).getInteractOrderPrescribeInfoList().size();

    }

    @Override
    public Object getGroup(int i) {
        return mDatas.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mDatas.get(i).getInteractOrderPrescribeInfoList().get(i1);
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


        groupViewHolder.mTitleName.setText(mDatas.get(i).getPrescribeTypeName());
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
            view.setTag(childViewHolder);
        }
        else
        {
            childViewHolder = (ChildViewHolder)view.getTag();
        }

//        childViewHolder.userName.setText(mDatas.get(i).getDoctorUnionPersonnelList().get(i1).getDoctorUserName());
//        childViewHolder.userSSY.setText(mDatas.get(i).getDoctorUnionPersonnelList().get(i1).getDoctorTitleDesc());
//        childViewHolder.unionName.setText(mDatas.get(i).getUnionName());
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
