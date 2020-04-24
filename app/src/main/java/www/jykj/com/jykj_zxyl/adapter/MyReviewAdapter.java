package www.jykj.com.jykj_zxyl.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.Util;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder>{
    private OnItemClickListener onItemClickListener = null;
    private List<ProvideViewUnionDoctorMemberApplyInfo> mDate;
    private Context mContext;
    private JYKJApplication mApp;

    public MyReviewAdapter(List<ProvideViewUnionDoctorMemberApplyInfo> list,Context context,JYKJApplication app){
        mDate = list;
        mContext = context;
        mApp = app;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_review,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        holder.mUserName.setText(mDate.get(position).getApplyDoctorName()+"("+mDate.get(position).getUnionName()+")");

        if (mDate.get(position).getUserLogoUrl() != null && !"".equals(mDate.get(position).getUserLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(mDate.get(position).getUnionLogoUrl());
                Glide.with(mContext).load(avatarResId).into(holder.mUserHeard);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mDate.get(position).getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.mUserHeard);
            }
        }

        holder.mUserName.setText(mDate.get(position).getApplyDoctorName());
        holder.mDate.setText(Util.dateToStr(mDate.get(position).getApplyDate()));
        //申请状态.0:待处理;1:未通过;2:已过期;3:通过;
        if (mDate.get(position).getFlagApplyState() == 0)
        {
            holder.mState.setText("待处理");
            holder.mState.setTextColor(mContext.getResources().getColor(R.color.groabColor));
            System.out.println();
        }
        if (mDate.get(position).getFlagApplyState() == 1) {
            holder.mState.setText("未通过");
            holder.mState.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
        }
        if (mDate.get(position).getFlagApplyState() == 2) {
            holder.mState.setText("已过期");
            holder.mState.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
        }
        if (mDate.get(position).getFlagApplyState() == 3)
        {
            holder.mState.setText("已通过");
            holder.mState.setTextColor(mContext.getResources().getColor(R.color.groabColor));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    /**
     * 设置数据
     */
    public void setDate(List<ProvideViewUnionDoctorMemberApplyInfo> list) {
        mDate = list;
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView   mUserHeard;
        private TextView    mUserName;
        private TextView    mDate;
        private TextView    mState;

        public ViewHolder(View view){
            super(view);
            mUserHeard = (ImageView)view.findViewById(R.id.iv_head);
            mUserName = (TextView)view.findViewById(R.id.tv_name);
            mDate = (TextView)view.findViewById(R.id.tv_date);
            mState = (TextView)view.findViewById(R.id.tv_state);
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }

}
