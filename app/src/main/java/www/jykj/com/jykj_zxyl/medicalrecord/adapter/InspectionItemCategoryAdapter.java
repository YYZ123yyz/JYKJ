package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemCategoryBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 11:17
 */
public class InspectionItemCategoryAdapter extends RecyclerView.Adapter<InspectionItemCategoryAdapter.ViewHolder> {
    private Context context;
    private List<InspectionItemCategoryBean> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InspectionItemCategoryAdapter(Context mContext, List<InspectionItemCategoryBean> list){
        this.context=mContext;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspection_option_category, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspectionItemCategoryBean inspectionItemBean = list.get(position);
        holder.mTvInspectionItemName.setText(inspectionItemBean.getInspectionName());
        holder.mRlLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null) {
                    onItemClickListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvInspectionItemName;
        private RelativeLayout mRlLayoutRoot;
        public ViewHolder(View view) {
            super(view);
            mTvInspectionItemName=view.findViewById(R.id.tv_inspection_item_name);
            mRlLayoutRoot=view.findViewById(R.id.rl_layout_root);
        }
    }

    public interface OnItemClickListener{
        void onClickItem(int pos);
    }
}
