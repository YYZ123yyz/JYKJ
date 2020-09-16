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

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemProjectBean;

/**
 * Description:检查检验项目列表适配器
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 16:59
 */
public class InspectionItemProjectAdapter extends RecyclerView.Adapter<
        InspectionItemProjectAdapter.ViewHolder> {
    private Context context;
    private List<InspectionItemProjectBean> list;
    private boolean onBind;

    public List<InspectionItemProjectBean> getList() {
        return list;
    }

    public InspectionItemProjectAdapter(Context mContext, List<InspectionItemProjectBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspection_option, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspectionItemProjectBean inspectionItemBean = list.get(position);
        holder.mTvInspectionItemName.setText(inspectionItemBean.getInspectionName());

        if (inspectionItemBean.isChoosed()) {
            onBind = true;
            inspectionItemBean.setChoosed(true);
            holder.ivItemChoosed.setImageResource(R.mipmap.bg_choosed_press);
            onBind = false;
        } else {
            onBind = true;
            holder.ivItemChoosed.setImageResource(R.mipmap.bg_choosed_normal);
            inspectionItemBean.setChoosed(false);
            onBind = false;
        }
        holder.mRlLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChoosedPos(position);
                InspectionItemProjectAdapter.this.notifyDataSetChanged();

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
        private ImageView ivItemChoosed;

        public ViewHolder(View view) {
            super(view);
            mTvInspectionItemName = view.findViewById(R.id.tv_inspection_item_name);
            mRlLayoutRoot = view.findViewById(R.id.rl_layout_root);
            ivItemChoosed = view.findViewById(R.id.iv_item_choosed);
        }
    }

    /**
     * 设置选中状态
     *
     * @param pos 当前位置
     */
    private void setChoosedPos(int pos) {
        for (int i = 0; i < list.size(); i++) {
            if (i == pos) {
                list.get(i)
                        .setChoosed(true);
            }else{
                list.get(i)
                        .setChoosed(false);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 获取当前选中的检查检验项目
     * @return InspectionItemProjectBean
     */
    public InspectionItemProjectBean getCurrentChoosedItem(){
        InspectionItemProjectBean currentInspectionProjectBean=null;
        for (InspectionItemProjectBean inspectionProjectBean : list) {
            boolean choosed = inspectionProjectBean.isChoosed();
            if(choosed){
                currentInspectionProjectBean=inspectionProjectBean;
                break;
            }
        }
        return currentInspectionProjectBean;
    }


    public void setData(List<InspectionItemProjectBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);
    }

}
