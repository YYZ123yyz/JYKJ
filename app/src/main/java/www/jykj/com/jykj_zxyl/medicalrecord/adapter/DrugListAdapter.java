package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_adapter.SecondaryListAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;

/**
 * Description:药品列表
 *
 * @author: qiuxinhai
 * @date: 2020-11-26 17:16
 */
public class DrugListAdapter extends SecondaryListAdapter<DrugListAdapter.GroupItemViewHolder,
        DrugListAdapter.SubItemViewHolder> {
    private List<DataTree<DrugClassificationBean,
                DrugClassificationBean.DrugTypeMedicineListBean>> dts;
    private Context mContext;
    public DrugListAdapter(Context context){
        this.mContext=context;
        dts = new ArrayList<>();
    }

    /**
     * 设置数据
     *
     * @param datas 数据
     */
    public void setData(List<DataTree<DrugClassificationBean,
            DrugClassificationBean.DrugTypeMedicineListBean>> datas) {
        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_leval_one,
                parent, false);
        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_leval_two,
                parent, false);
        return new SubItemViewHolder(v);
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

        DrugClassificationBean groupItem = dts.get(groupItemIndex).getGroupItem();
        holder.tvDrugName.setText(groupItem.getMedicineName());
    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {
        List<DrugClassificationBean.DrugTypeMedicineListBean> subItems = dts.get(groupItemIndex).getSubItems();
        DrugClassificationBean.DrugTypeMedicineListBean drugTypeMedicineListBean = subItems.get(subItemIndex);
        holder.tvChildDrugName.setText(drugTypeMedicineListBean.getMedicineName());
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
