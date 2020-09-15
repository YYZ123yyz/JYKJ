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
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 18:07
 */
public class InspectionItemPostionAdapter extends RecyclerView.Adapter
        <InspectionItemPostionAdapter.ViewHolder> {

    private Context context;
    private List<InspectionItemPositionBean> list;
    private boolean onBind;

    public List<InspectionItemPositionBean> getList() {
        return list;
    }

    public InspectionItemPostionAdapter(Context mContext, List<InspectionItemPositionBean> list) {
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
        InspectionItemPositionBean inspectionItemBean = list.get(position);
        holder.mTvInspectionItemName.setText(inspectionItemBean.getPositionName());

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
                list.get(position).setChoosed(!list.get(position).isChoosed());
                InspectionItemPostionAdapter.this.notifyDataSetChanged();

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
     * 获取当前选中的检查检验项目
     * @return InspectionItemProjectBean
     */
    public List<InspectionItemPositionBean> getChoosedItemList(){
        List<InspectionItemPositionBean> positionBeans=new ArrayList<>();
        for (InspectionItemPositionBean inspectionProjectBean : list) {
            boolean choosed = inspectionProjectBean.isChoosed();
            if(choosed){
                positionBeans.add(inspectionProjectBean);
            }
        }
        return positionBeans;
    }


    public void setData(List<InspectionItemPositionBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);
    }
}
