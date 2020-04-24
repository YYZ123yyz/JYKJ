package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import entity.unionInfo.ProvideViewUnionDoctorMemberApplyInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberInviteInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 联盟信息，我的被邀请
 */
public class UnionMyBeInvateAdapter extends RecyclerView.Adapter<UnionMyBeInvateAdapter.ViewHolder>{

    private List<ProvideViewUnionDoctorMemberInviteInfo> mDate;
    private Context                                     mContext;
    private JYKJApplication                             mApp;

    public UnionMyBeInvateAdapter(List<ProvideViewUnionDoctorMemberInviteInfo> list, Context context, JYKJApplication app){
        mDate = list;
        mContext = context;
        mApp = app;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unioninvate_mybeinvate,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mUnionCreateDate.setText(Util.dateToStr(mDate.get(position).getInviteDate()));
        //邀请状态.0:待处理;1:未通过;2:已过期;3:通过;
        switch (mDate.get(position).getFlagInviteState())
        {
            case 0:
                holder.mUnionState.setText("还未处理");
                break;
            case 1:
                holder.mUnionState.setText("未通过");
                break;
            case 2:
                holder.mUnionState.setText("已过期");
                break;
            case 3:
                holder.mUnionState.setText("已通过");
                break;
        }
        holder.mUnionName.setText(mDate.get(position).getUnionName());
        holder.mUnionLeave.setText(mDate.get(position).getUnionGradeName());
        holder.mUnionYSNum.setText("医生:"+mDate.get(position).getUpperLimitDoctorNumNow()+"(当前)/"+mDate.get(position).getUpperLimitDoctorNum()+"(上限)");
        holder.mUnionHZNum.setText("患者:"+mDate.get(position).getUpperLimitPatientNumNow()+"(当前)/"+mDate.get(position).getUpperLimitPatientNum()+"(上限)");

        if (mDate.get(position).getUnionLogoUrl() != null && !"".equals(mDate.get(position).getUnionLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(mDate.get(position).getUnionLogoUrl());
                Glide.with(mContext).load(avatarResId).into(holder.mImageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(mDate.get(position).getUnionLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.mImageView);
            }
        }

        if (mOnItemClickListener != null)
        {
            holder.mClickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
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
     */
    public void setDate(List<ProvideViewUnionDoctorMemberInviteInfo> list) {
        mDate = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mUnionCreateDate;                                  //联盟创建时间
        public TextView mUnionState;                                  //申请状态
        public TextView mUnionName;                                  //联盟名称
        public TextView mUnionLeave;                                  //联盟等级
        public TextView mUnionYSNum;                                  //联盟医生数
        public TextView mUnionHZNum;                                  //联盟患者输
        public RelativeLayout mClickLayout;

        public ImageView mImageView;

        public ViewHolder(View view){
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.iv_head);

            mUnionCreateDate = (TextView)view.findViewById(R.id.tv_itemMyApply_applyData);
            mUnionState = (TextView)view.findViewById(R.id.tv_itemMyApply_applyState);
            mUnionName = (TextView)view.findViewById(R.id.tv_itemMyApply_unionName);
            mUnionLeave = (TextView)view.findViewById(R.id.tv_itemMyApply_unionLeave);
            mUnionYSNum = (TextView)view.findViewById(R.id.tv_itemMyApply_ysUserNum);
            mUnionHZNum = (TextView)view.findViewById(R.id.tv_itemMyApply_hzUserNum);
            mClickLayout = (RelativeLayout)view.findViewById(R.id.clickLayout);
        }
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
