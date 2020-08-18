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
import com.hyphenate.easeui.jykj.bean.DetectBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 检测类型
 */
public class Rv_detectAdapter extends RecyclerView.Adapter<Rv_detectAdapter.ViewHolder> {
    public List<DetectBean> datas = new ArrayList<>();
    private SigningDetailsActivity mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public Rv_detectAdapter(List<DetectBean> list, Context context, SigningDetailsActivity mainActivity) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_detect, viewGroup, false);
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
        viewHolder.ed_price.setCursorVisible(false);
        viewHolder.tv_name.setText(datas.get(position).getConfigDetailName());
        viewHolder.ed_price.setText(datas.get(position).getPrice()+"");
//        if (mOnItemClickListener != null) {
//            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickListener.onClick(position);
//                }
//            });
//
//            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View view) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
//        }
        addListener(viewHolder.ed_price,position);

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

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView tv_name;                                //类型
        public EditText ed_price;
        public ViewHolder(View view) {
            super(view);
            //mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_fragmentYLZX_rmjxLayout);
            tv_name = view.findViewById(R.id.tv_name);
            ed_price = view.findViewById(R.id.ed_price);
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);

        void onTextChanged(int pos,String value);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}