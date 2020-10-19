package com.hyphenate.easeui.jykj.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.jykj.activity.SigningDetailsActivity;
import com.hyphenate.easeui.jykj.bean.CoachingBean;
import com.hyphenate.easeui.jykj.bean.DetectBean;

import java.util.ArrayList;
import java.util.List;



/**
 * 检测类型
 */
public class Rv_CoachingAdapter extends RecyclerView.Adapter<Rv_CoachingAdapter.ViewHolder> {
    public List<DetectBean> datas = new ArrayList<>();
    private SigningDetailsActivity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    private OnItemCoachingClickListener OnItemCoachingClickListener;
    private OnItemCoachingLinClickListener OnItemCoachingLinClickListener;
    public Rv_CoachingAdapter(List<DetectBean> list, Context context, SigningDetailsActivity mainActivity) {
        mContext = context;
        datas = list;
        mActivity = mainActivity;
    }

    //重新设置数据
    public void setDate(List<DetectBean> list) {
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
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
//        if(TextUtils.isEmpty(datas.get(position).getMonth())&&TextUtils.isEmpty(datas.get(position).getTime())&&TextUtils.isEmpty(datas.get(position).getTimes())){
//
//        }else{
//            viewHolder.time.setText(datas.get(position).getTime()+"分钟");
//            viewHolder.frequency.setText(datas.get(position).getTimes()+"/"+ datas.get(position).getMonth());
//        }
 //       viewHolder.ed_price.setCursorVisible(false);
        DetectBean detectBean = datas.get(position);
        int minute = detectBean.getMinute();
        if (minute!=0) {
            viewHolder.time.setText(minute+"分钟，");
        }else{
            viewHolder.time.setText("");

        }
        int frequency = detectBean.getFrequency();
        int months = detectBean.getMonths();
        if (frequency!=0&&months!=0) {
            viewHolder.frequency.setText(String.format("%d次/%d月", frequency, months));
        }else{
            viewHolder.frequency.setText("频次");
        }
        if(datas.get(position).getConfigDetailName().equals("图文")){
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


        viewHolder.tv_name.setText(datas.get(position).getConfigDetailName());
        viewHolder.ed_price.setText(datas.get(position).getPrice()+"");
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
        addListener(viewHolder.ed_price,position);


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView tv_name,time,frequency;                                //类型
            public LinearLayout lin_frequency;
            public EditText ed_price;
        public ViewHolder(View view) {
            super(view);
            lin_frequency = (LinearLayout) itemView.findViewById(R.id.lin_frequency);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            ed_price = (EditText) view.findViewById(R.id.ed_price);
            time = (TextView)view.findViewById(R.id.time);
            frequency = (TextView)view.findViewById(R.id.frequency);

        }
    }


    private void addListener(EditText editText, final int pos){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mOnItemClickListener!=null) {
                    mOnItemClickListener.onTextChanged(pos,s.toString());
                    String value = s.toString();
                    if (!TextUtils.isEmpty(value)) {
                        double price = Double.parseDouble(value);
                        datas.get(pos).setPrice(price);
                    }

                }
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);

        void onTextChanged(int pos,String value);
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

        void onTextChanged(int pos,String value);
    }

    public void setOnItemCoachingLinClickListener(OnItemCoachingLinClickListener onItemCoachingLinClickListener) {
        this.OnItemCoachingLinClickListener = onItemCoachingLinClickListener;
    }
}