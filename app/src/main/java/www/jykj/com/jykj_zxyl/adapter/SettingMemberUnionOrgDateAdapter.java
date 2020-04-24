package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import entity.unionInfo.ProvideUnionDoctorOrg;
import www.jykj.com.jykj_zxyl.R;

public class SettingMemberUnionOrgDateAdapter extends RecyclerView.Adapter<SettingMemberUnionOrgDateAdapter.ViewHolder> {

    protected       Context         mContext;
    protected       List<ProvideUnionDoctorOrg>   mDate;
    private         OnNextItemClickListener mOnNextItemClickListener;           //下一层
    private         OnClickLayoutItemClickListener mOnClickLayoutItemClickListener;           //选择
    public SettingMemberUnionOrgDateAdapter(Context context, List<ProvideUnionDoctorOrg> list)
    {
        mContext = context;
        mDate = list;
    }
    /**
     * 设置数据
     * @param provideUnionDoctorOrgs
     */
    public void setDate(List<ProvideUnionDoctorOrg> provideUnionDoctorOrgs) {
        mDate= provideUnionDoctorOrgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_orgdate, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mUnionOrgName.setText(mDate.get(position).getOrgName());
        if (mDate.get(position).isChoice())
            holder.mChoice.setBackgroundResource(R.mipmap.choice);
        else
            holder.mChoice.setBackgroundResource(R.mipmap.invite_nochoice);

        holder.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnNextItemClickListener.onClick(position);
            }
        });

        holder.mClickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickLayoutItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mUnionOrgName;             //层级名称
        private ImageView mChoice;                //选择
        private LinearLayout   mClickLayout;            //点击选择
        private  TextView   mNext;                  //下一层

        public ViewHolder(View view) {
            super(view);
            mUnionOrgName = (TextView)view.findViewById(R.id.tv_name);
            mClickLayout = (LinearLayout)view.findViewById(R.id.li_clickLayout);
            mChoice = (ImageView)view.findViewById(R.id.iv_choice);
            mNext = (TextView)view.findViewById(R.id.tv_setting);

        }
    }

    public interface OnNextItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }


    public interface OnClickLayoutItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }




    public void setOnNextItemClickListener(OnNextItemClickListener onOnNextItemClickListener ){
        this.mOnNextItemClickListener=onOnNextItemClickListener;
    }


    public void setOnItemClickListener(OnClickLayoutItemClickListener onClickLayoutItemClickListener ){
        this.mOnClickLayoutItemClickListener=onClickLayoutItemClickListener;
    }

}
