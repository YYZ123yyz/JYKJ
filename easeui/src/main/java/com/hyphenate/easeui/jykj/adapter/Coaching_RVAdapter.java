package com.hyphenate.easeui.jykj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.jykj.bean.DetectBean;

import java.util.List;

public class Coaching_RVAdapter extends RecyclerView.Adapter<Coaching_RVAdapter.ViewHolder> {
    private List<DetectBean> coachingBeans;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public Coaching_RVAdapter(List<DetectBean> list, Context mContext) {
        this.coachingBeans = list;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coaching_choice, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //重新设置数据
    public void setDate(List<DetectBean> list) {
        coachingBeans = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.tv_zzxx_mc.setText(coachingBeans.get(i).getConfigDetailName());
        if (coachingBeans.get(i).isChoice())
            viewHolder.iv_zzxx_choice.setBackgroundResource(R.mipmap.zzxx_choice_c);
        else
            viewHolder.iv_zzxx_choice.setBackgroundResource(R.mipmap.zzxx_choice);
        viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return coachingBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
    //    public LinearLayout lin_frequency;                     //整个布局，用来监听点击事件
        public TextView tv_zzxx_mc;                                //症状信息
        public ImageView iv_zzxx_choice;                             //选择图标

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mClickLinearLayout = (LinearLayout) itemView.findViewById(R.id.li_clickLayout);
      //      lin_frequency = (LinearLayout) itemView.findViewById(R.id.lin_frequency);
            tv_zzxx_mc = (TextView) itemView.findViewById(R.id.tv_zzxx_mc);
            iv_zzxx_choice = (ImageView) itemView.findViewById(R.id.iv_zzxx_choice);
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
