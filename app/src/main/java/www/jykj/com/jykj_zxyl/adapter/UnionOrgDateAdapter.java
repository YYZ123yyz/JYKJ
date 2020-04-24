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

public class UnionOrgDateAdapter extends RecyclerView.Adapter<UnionOrgDateAdapter.ViewHolder> {

    protected       Context         mContext;
    protected       List<ProvideUnionDoctorOrg>   mDate;
    private         OnUpdateItemClickListener mOnUpdateItemClickListener;           //修改
    private         OnDeleteItemClickListener mOnDeleteItemClickListener;           //删除
    private         OnClickLayoutItemClickListener mOnClickLayoutItemClickListener;           //下一层
    public UnionOrgDateAdapter(Context context, List<ProvideUnionDoctorOrg> list)
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctorunion_orgdate, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mUnionOrgName.setText(mDate.get(position).getOrgName());
        holder.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnUpdateItemClickListener.onClick(position);
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDeleteItemClickListener.onClick(position);
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
        private TextView   mUpdate;                //修改
        private TextView   mDelete;                //删除
        private LinearLayout   mClickLayout;            //点击下一层
        public ViewHolder(View view) {
            super(view);
            mUnionOrgName = (TextView)view.findViewById(R.id.tv_name);
            mUpdate = (TextView)view.findViewById(R.id.tv_setting);
            mDelete = (TextView)view.findViewById(R.id.tv_delete);
            mClickLayout = (LinearLayout)view.findViewById(R.id.li_clickLayout);
        }
    }

    public interface OnUpdateItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public interface OnDeleteItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public interface OnClickLayoutItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }




    public void setOnUpdateItemClickListener(OnUpdateItemClickListener onUpdateItemClickListener ){
        this.mOnUpdateItemClickListener=onUpdateItemClickListener;
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener onDeleteItemClickListener ){
        this.mOnDeleteItemClickListener=onDeleteItemClickListener;
    }

    public void setOnItemClickListener(OnClickLayoutItemClickListener onClickLayoutItemClickListener ){
        this.mOnClickLayoutItemClickListener=onClickLayoutItemClickListener;
    }

}
