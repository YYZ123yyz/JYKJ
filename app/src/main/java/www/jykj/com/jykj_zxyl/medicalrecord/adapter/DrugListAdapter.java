package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_adapter.SecondaryListAdapter;

/**
 * Description:药品列表
 *
 * @author: qiuxinhai
 * @date: 2020-11-26 17:16
 */
public class DrugListAdapter extends SecondaryListAdapter<DrugListAdapter.GroupItemViewHolder,
        DrugListAdapter.SubItemViewHolder> {


    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {

    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder,
                                        int groupItemIndex, int subItemIndex) {

    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {

    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

    }

    /**
     * 二级评论列表
     */
    static class SubItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvChildDrugName;

        SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChildDrugName = itemView.findViewById(R.id.tv_child_drug_name);

        }
    }

    /**
     * 一级评论列表
     */
    static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvDrugName;
        GroupItemViewHolder(View itemView) {
            super(itemView);
            tvDrugName = itemView.findViewById(R.id.tv_drug_name);


        }
    }
}
