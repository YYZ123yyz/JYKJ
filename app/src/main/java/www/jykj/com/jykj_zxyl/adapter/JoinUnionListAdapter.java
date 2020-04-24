package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import www.jykj.com.jykj_zxyl.R;

public class JoinUnionListAdapter extends RecyclerView.Adapter<JoinUnionListAdapter.ViewHolder>{

    private         Context                                     mContext;
    private         List<ProvideViewUnionDoctorDetailInfo>     mDate = new ArrayList<>();

    private         OnItemClickListener mOnItemClickListener;

    public JoinUnionListAdapter(Context context, List<ProvideViewUnionDoctorDetailInfo> list){
        mContext = context;
        mDate = list;
    }
    public void setDate(List<ProvideViewUnionDoctorDetailInfo> list){
        mDate = list;
    }
    @Override
    public JoinUnionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_join_union,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mDate.get(position).getUnionLogoUrl() != null && !"".equals(mDate.get(position).getUnionLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(mDate.get(position).getUnionLogoUrl());
                Glide.with(mContext).load(avatarResId).into(holder.mHead);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mDate.get(position).getUnionLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.mHead);
            }
        }

        holder.mUnionName.setText(mDate.get(position).getUnionName());
        holder.mUnionLeave.setText(mDate.get(position).getUnionGradeName()+"");
        holder.mUnionUserNum.setText("医生:"+mDate.get(position).getUpperLimitDoctorNumNow()+"(当前)/"+mDate.get(position).getUpperLimitDoctorNum()+"(上限)");
        holder.mUnionHZNum.setText("患者:"+mDate.get(position).getUpperLimitPatientNumNow()+"(当前)/"+mDate.get(position).getUpperLimitPatientNum()+"(上限)");
        if (mOnItemClickListener != null)
        {
            holder.mJoinUnionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.mJoinUnionText.setOnLongClickListener(new View.OnLongClickListener() {

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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView     mUnionName;                                  //联盟名称
        public TextView     mUnionLeave;                                  //联盟等级
        public TextView     mUnionUserNum;                                  //联盟人数
        public TextView     mUnionHZNum;                                  //患者数量
        public TextView     mJoinUnionText;                                //加入联盟
        public ImageView   mHead;

        public ViewHolder(View view){
            super(view);
            mUnionName = (TextView)view.findViewById(R.id.item_joinUnionActivity_unionNameText);
            mUnionLeave = (TextView)view.findViewById(R.id.item_joinUnionActivity_unionLeaveText);
            mUnionUserNum = (TextView)view.findViewById(R.id.item_joinUnionActivity_unionUserNumText);
            mUnionHZNum = (TextView)view.findViewById(R.id.item_joinUnionActivity_unionHZNumText);
            mJoinUnionText = (TextView)view.findViewById(R.id.tv_itemJoinUnionActivity_joinUnionText);
            mHead = (ImageView)view.findViewById(R.id.iv_head);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
