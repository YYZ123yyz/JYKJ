package com.hyphenate.easeui.jykj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.activity.SigningDetailsActivity;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.jykj.bean.GetdetailsBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 检测类型
 */
public class Item_Rv_CoachingAdapter extends RecyclerView.Adapter<Item_Rv_CoachingAdapter.ViewHolder> {
    public List<GetdetailsBean.OrderDetailListBean> datas = new ArrayList<>();
    private SigningDetailsActivity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    private OnItemCoachingClickListener OnItemCoachingClickListener;
    private OnItemCoachingLinClickListener OnItemCoachingLinClickListener;
    public Item_Rv_CoachingAdapter(List<GetdetailsBean.OrderDetailListBean> list, Context context, SigningDetailsActivity mainActivity) {
        mContext = context;
        datas = list;
        mActivity = mainActivity;
    }

    //重新设置数据
    public void setDate(List<GetdetailsBean.OrderDetailListBean> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coaching, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 如果是第一条数据，那么他肯定是该组的第一个用户，所以显示组别
     * 如果该用户是该组的第一个用户，那么就显示组别
     * 否则不再显示
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
         if(datas.get(position).getConfigDetailTypeCode().equals("10")){
             viewHolder.time.setVisibility(View.GONE);
             //频次
             viewHolder.lin_frequency.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     OnItemCoachingClickListener.onClick(position);
                 }
             });
         }else{
             viewHolder.time.setVisibility(View.VISIBLE);
             //频次
             viewHolder.lin_frequency.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                  //   OnItemCoachingClickListener.onClick(position);
                     OnItemCoachingLinClickListener.onClick(position);
                 }
             });
         }
        viewHolder.tv_name.setText(datas.get(position).getMainConfigDetailName());
        viewHolder.ed_price.setText(datas.get(position).getTotlePrice()+"");
        if (mOnItemClickListener != null) {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView tv_name,ed_price,time;                                //类型
            public LinearLayout lin_frequency;
        public ViewHolder(View view) {
            super(view);
            lin_frequency = (LinearLayout) itemView.findViewById(R.id.lin_frequency);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            ed_price = (TextView)view.findViewById(R.id.ed_price);
            time = (TextView)view.findViewById(R.id.time);

        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemCoachingClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemCoachingClickListener(OnItemCoachingClickListener onItemCoachingClickListener) {
        this.OnItemCoachingClickListener = onItemCoachingClickListener;
    }

    public interface OnItemCoachingLinClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemCoachingLinClickListener(OnItemCoachingLinClickListener onItemCoachingLinClickListener) {
        this.OnItemCoachingLinClickListener = onItemCoachingLinClickListener;
    }
}