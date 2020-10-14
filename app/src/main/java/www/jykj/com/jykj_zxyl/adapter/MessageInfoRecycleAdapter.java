package www.jykj.com.jykj_zxyl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.DoctorInfo.InteractPatient;
import entity.HZIfno;
import entity.MyCouponDetailEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyCouponDetailActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

/**
 * 聊天记录列表
 */
public class MessageInfoRecycleAdapter extends RecyclerView.Adapter<MessageInfoRecycleAdapter.ViewHolder> {

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    public List<InteractPatient> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private JYKJApplication mApp;


    public MessageInfoRecycleAdapter(JYKJApplication application, List<InteractPatient> list, Context context) {
        mApp = application;
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDatas(List<InteractPatient> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmentmessageinfo_messagelist, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         */
        mApp.imageLoader.displayImage(datas.get(position).getPatientUserLogoUrl(), viewHolder.mImageView, mApp.mImageOptions, animateFirstListener);

        viewHolder.mUserName.setText(datas.get(position).getPatientUserName());
        viewHolder.mSSY.setText(datas.get(position).getPatientTitleDesc());
        viewHolder.mDate.setText(datas.get(position).getPatientNewLoginDate());
//        int avatarResId = Integer.parseInt(datas.get(position).getPatientUserLogoUrl());
//        Glide.with(mContext).load(avatarResId).into(viewHolder.mImageView);
//        String patientUserLogoUrl = datas.get(position).getPatientUserLogoUrl();
//        viewHolder.mImageView.setImageURI(Uri.parse(patientUserLogoUrl));
        if ("user".equals(datas.get(position).getType()))
            viewHolder.mMessage.setText(datas.get(position).getPatientUserLabelName());
        else if ("message".equals(datas.get(position).getType())) {
            if (datas.get(position).isNoRead()) {
                viewHolder.mMessage.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_hztltabyj));
                viewHolder.mMessage.setText(datas.get(position).getLastMessage());
                viewHolder.mTvReadIcon.setVisibility(View.VISIBLE);
                String patientUserName = datas.get(position).getPatientCode();
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(patientUserName);
                if (conversation != null) {
                    int unreadMsgCount = conversation.getUnreadMsgCount();
                    viewHolder.mTvReadIcon.setText(String.format("%d", unreadMsgCount));
                }

            } else {
                viewHolder.mMessage.setTextColor(ContextCompat.getColor(mContext, R.color.textColor_vt));
                viewHolder.mMessage.setText(datas.get(position).getLastMessage());
                viewHolder.mTvReadIcon.setVisibility(View.GONE);
            }
        }

        if (mOnItemClickListener != null) {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public ImageView mImageView;                             // 头像
        public TextView mUserName;
        public TextView mSSY;
        public TextView mMessage;
        public TextView mDate;
        public TextView mTvReadIcon;

        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemFragmentHYHD_clickLayout);
            mUserName = (TextView) view.findViewById(R.id.tv_itemMessageAdapter_userNameText);
            mSSY = (TextView) view.findViewById(R.id.tv_itemMessageAdapter_userSSYText);
            mDate = (TextView) view.findViewById(R.id.tv_itemMessageAdapter_userDateText);
            mMessage = (TextView) view.findViewById(R.id.tv_itemMessageAdapter_userMessageText);
            mImageView = (ImageView) view.findViewById(R.id.iv_itemMessageAdapter_head);
            mTvReadIcon = view.findViewById(R.id.tv_unread_icon);
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
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