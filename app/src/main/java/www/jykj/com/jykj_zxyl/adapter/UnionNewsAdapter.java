package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import entity.home.newsMessage.ProvideMsgPushReminder;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.HzyqActivity;

public class UnionNewsAdapter extends RecyclerView.Adapter<UnionNewsAdapter.ViewHolder>{

    private         List<ProvideMsgPushReminder>   mDate = new ArrayList<>();

    public UnionNewsAdapter(List<ProvideMsgPushReminder> mDate, Context mContext) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    private         Context                         mContext;
    private         OnItemClickListener mOnItemClickListener;
    private String mMessageType;               //消息类型
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_union_news_list,parent,false);
        UnionNewsAdapter.ViewHolder vh = new UnionNewsAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDate.get(position).getFlagMsgRead() == null)
            return;
        if (mDate.get(position).getFlagMsgRead() == 1)
            holder.mUserName.setTextColor(mContext.getResources().getColor(R.color.tabColor_nomal));
        else
            holder.mUserName.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        holder.mUserName.setText(mDate.get(position).getSenderUserName());
        if (mDate.get(position).getFlagMsgRead() == 1)
            holder.mMessageDatet.setTextColor(mContext.getResources().getColor(R.color.tabColor_nomal));
        else
            holder.mMessageDatet.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        holder.mMessageDatet.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mDate.get(position).getMsgCreateDate()));

        if (mDate.get(position).getFlagMsgRead() == 1)
            holder.mMessageType.setTextColor(mContext.getResources().getColor(R.color.tabColor_nomal));
        else
            holder.mMessageType.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        if (mDate.get(position).getMsgType()==4000101)
            holder.mMessageType.setText("患者就诊");
        if (mDate.get(position).getMsgType()==4000102)
            holder.mMessageType.setText("诊后留言");
        if (mDate.get(position).getMsgType()==4000103)
            holder.mMessageType.setText("添加患者");
        if (mDate.get(position).getMsgType()==4000104)
            holder.mMessageType.setText("联盟消息");
        if (mDate.get(position).getMsgType()==4000105)
            holder.mMessageType.setText("医患圈");
        if (mDate.get(position).getMsgType()==4000106)
            holder.mMessageType.setText("紧急提醒");
        if (mDate.get(position).getMsgType()==4000107)
            holder.mMessageType.setText("患者签约");
        if (mDate.get(position).getMsgType()==4000108)
            holder.mMessageType.setText("系统消息");

        Log.e("tag", "onBindViewHolder:2222 "+mDate.toString() );

        if (mDate.get(position).getUserLogoUrl() != null && !"".equals(mDate.get(position).getUserLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(mDate.get(position).getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(holder.mHead);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mDate.get(position).getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.mHead);
            }
        }

        if (mOnItemClickListener != null)
        {
            holder.mClickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mOnItemClickListener.onClick(position);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.mClickLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    /**
     * 设置数据
     * @param mMsgPushReminders
     */
    public void setdate(List<ProvideMsgPushReminder> mMsgPushReminders) {
        mDate = mMsgPushReminders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView   mHead;
        private TextView    mUserName;
        private TextView    mMessageType;
        private TextView    mMessageDatet;
        private LinearLayout    mClickLayout;


        public ViewHolder(View view){
            super(view);
            mHead = (ImageView)view.findViewById(R.id.iv_itemNewsAdapter_head);
            mUserName = (TextView)view.findViewById(R.id.tv_itemNewsAdapter_userNameText);
            mMessageType = (TextView)view.findViewById(R.id.tv_itemNewsAdapter_userMessageText);
            mMessageDatet = (TextView)view.findViewById(R.id.tv_itemNewMessageAdapter_userDateText);
            mClickLayout = (LinearLayout)view.findViewById(R.id.click);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position) throws UnsupportedEncodingException;
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
