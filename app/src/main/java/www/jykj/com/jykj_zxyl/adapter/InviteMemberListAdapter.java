package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.DoctorInfo.ProvideViewBindingDdGetBindingDoctor;
import entity.MemberListInfo;
import www.jykj.com.jykj_zxyl.R;

/**
 * 邀请加入联盟 医生列表
 */
public class InviteMemberListAdapter extends RecyclerView.Adapter<InviteMemberListAdapter.MyViewHolder>{

    public List<ProvideViewBindingDdGetBindingDoctor> datas = new ArrayList<>();
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public InviteMemberListAdapter(Context mContext, List<ProvideViewBindingDdGetBindingDoctor>datas){
        this.mContext = mContext;
        this.datas = datas;
        System.out.println();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invitemember_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (datas.get(position).isClick())
            holder.mChoice.setBackgroundResource(R.mipmap.invite_choice);
        else
            holder.mChoice.setBackgroundResource(R.mipmap.invite_nochoice);

        if (datas.get(position).getGender() == 0)
            holder.mSex.setBackgroundResource(R.mipmap.sex_wz);
        if (datas.get(position).getGender() == 1)
            holder.mSex.setBackgroundResource(R.mipmap.sex_man);
        if (datas.get(position).getGender() == 2)
            holder.mSex.setBackgroundResource(R.mipmap.sex_woman);
        holder.mName.setText(datas.get(position).getUserName());
        holder.mHospital.setText("@"+datas.get(position).getHospitalInfoName());
        holder.mDepartment.setText(datas.get(position).getDepartmentName()+"（"+datas.get(position).getDepartmentSecondName()+"）");

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
        return datas.size();
    }


    public void setData(List<ProvideViewBindingDdGetBindingDoctor> mDoctorFriendList) {
        datas = mDoctorFriendList;
        System.out.println();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView mChoice;
        private ImageView mHead;
        private TextView mName;
        private LinearLayout mClickLayout;
        private TextView    mDepartment;                //科室
        private TextView    mHospital;          //医院
        private ImageView    mSex;               //性别


        public MyViewHolder(View itemView) {
            super(itemView);
            mChoice = itemView.findViewById(R.id.choice);
            mHead = itemView.findViewById(R.id.iv_head);
            mName = itemView.findViewById(R.id.tv_name);
            mDepartment = itemView.findViewById(R.id.tv_ks);
            mHospital = itemView.findViewById(R.id.tv_hospital);
            mClickLayout = itemView.findViewById(R.id.li_clickLayout);
            mSex = itemView.findViewById(R.id.iv_sex);
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
