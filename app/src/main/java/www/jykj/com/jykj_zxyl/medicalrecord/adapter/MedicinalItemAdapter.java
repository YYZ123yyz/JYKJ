package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;

/**
 * Description:药品选项适配器
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 11:39
 */
public class MedicinalItemAdapter extends  RecyclerView.Adapter
        <MedicinalItemAdapter.ViewHolder> {
    private Context context;
    private List<MedicinalInfoBean> list;
    private boolean onBind;
    public List<MedicinalInfoBean> getList() {
        return list;
    }

    public MedicinalItemAdapter(Context mContext, List<MedicinalInfoBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicinal_option, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicinalInfoBean medicinalInfoBean = list.get(position);
        holder.mTvMedicinalItemName.setText(
                medicinalInfoBean.getDrugName()+"["+medicinalInfoBean.getDrugSpec()+"]");

        if (medicinalInfoBean.isChoosed()) {
            onBind = true;
            medicinalInfoBean.setChoosed(true);
            holder.ivItemChoosed.setImageResource(R.mipmap.bg_choosed_press);
            onBind = false;
        } else {
            onBind = true;
            holder.ivItemChoosed.setImageResource(R.mipmap.bg_choosed_normal);
            medicinalInfoBean.setChoosed(false);
            onBind = false;
        }
        holder.mRlLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list.get(position).setChoosed(!list.get(position).isChoosed());
                setChoosedItemStatus(position);
                MedicinalItemAdapter.this.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMedicinalItemName;
        private RelativeLayout mRlLayoutRoot;
        private ImageView ivItemChoosed;

        public ViewHolder(View view) {
            super(view);
            mTvMedicinalItemName = view.findViewById(R.id.tv_medicinal_item_name);
            mRlLayoutRoot = view.findViewById(R.id.rl_layout_root);
            ivItemChoosed = view.findViewById(R.id.iv_item_choosed);
        }
    }


    /**
     * 设置选中状态
     * @param pos 当前位置
     */
    private void setChoosedItemStatus(int pos){
        for (int i = 0; i < list.size(); i++) {
            if(pos==i){
                list.get(i).setChoosed(true);
            }else{
                list.get(i).setChoosed(false);
            }
        }
    }

    /**
     * 获取当前选中的检查检验项目
     * @return InspectionItemProjectBean
     */
    public MedicinalInfoBean getCurrentMedicinalInfoBean(){
        MedicinalInfoBean currentInfoBean=null;
        for (MedicinalInfoBean medicinalInfoBean : list) {
            boolean choosed = medicinalInfoBean.isChoosed();
            if(choosed){
                currentInfoBean=medicinalInfoBean;
               break;
            }
        }
        return currentInfoBean;
    }


    public void setData(List<MedicinalInfoBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);
    }
}
