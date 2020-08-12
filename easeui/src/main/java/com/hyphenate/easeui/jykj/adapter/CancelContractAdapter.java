package com.hyphenate.easeui.jykj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.bean.CancelContractBean;

import java.util.List;

/**
 * Description:

 */
public class CancelContractAdapter extends RecyclerView.Adapter<CancelContractAdapter.ViewHolder> {

    private List<CancelContractBean> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public CancelContractAdapter(List<CancelContractBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }

    public List<CancelContractBean> getDatas() {
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_cancel_contract,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.mTvCancelContractText.setText(datas.get(position).getAttrName());
        if (datas.get(position).isChoice()) {
            holder.mIvItemChoosed.setImageResource(R.mipmap.bg_item_choosed);
        }else{
            holder.mIvItemChoosed.setImageResource(R.mipmap.bg_item_normal);
        }
        holder.li_clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(position);
            }
        });
    }
    //重新设置数据
    public void setDate(List<CancelContractBean> list) {
        datas = list;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvCancelContractText;
        private ImageView mIvItemChoosed;
          private RelativeLayout li_clickLayout;
        public ViewHolder(View view) {
            super(view);
            mTvCancelContractText = view.findViewById(R.id.tv_cancel_contact_text);
            mIvItemChoosed = view.findViewById(R.id.iv_item_choosed);
            li_clickLayout = view.findViewById(R.id.li_clickLayout);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}