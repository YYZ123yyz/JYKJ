package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import entity.MemberListInfo;
import entity.unionInfo.ProvideViewUnionDoctorMemberDetailInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.yslm.ViewPatientActivity;

/**
 * 我的联盟
 */
public class MyUnionMemberListAdapter extends RecyclerView.Adapter<MyUnionMemberListAdapter.MyViewHolder>{

    public List<ProvideViewUnionDoctorMemberDetailInfo> datas = new ArrayList<>();
    private Context mContext;
    private OnItemSettingClickListener mOnSettingItemClickListener;           //设置


    public MyUnionMemberListAdapter(Context mContext,List<ProvideViewUnionDoctorMemberDetailInfo>datas){
        this.mContext = mContext;
        this.datas = datas;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (datas.get(position).getUserLogoUrl() != null && !"".equals(datas.get(position).getUserLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(datas.get(position).getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(holder.mHead);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(datas.get(position).getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(holder.mHead);
            }
        }

        holder.mName.setText(datas.get(position).getUserName());
        holder.mViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ViewPatientActivity.class));
            }
        });

        holder.mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSettingItemClickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setDate(List<ProvideViewUnionDoctorMemberDetailInfo> mProvideViewUnionDoctorMemberDetailInfos) {
        datas = mProvideViewUnionDoctorMemberDetailInfos;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mHead;
        private TextView mName;
        private TextView mSetting;
        private LinearLayout mViewPatient;

        public MyViewHolder(View itemView) {
            super(itemView);
            mHead = itemView.findViewById(R.id.iv_head);
            mName = itemView.findViewById(R.id.tv_name);
            mSetting = itemView.findViewById(R.id.tv_setting);
            mViewPatient = itemView.findViewById(R.id.ll_view_patient);
        }
    }

    public interface OnItemSettingClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }




    public void setOnSettingItemClickListener(OnItemSettingClickListener onItemSettingClickListener ){
        this.mOnSettingItemClickListener=onItemSettingClickListener;
    }
}
