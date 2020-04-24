package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.MyIntegralEntity;
import entity.MySurplusDetailEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.MyIntegralActivity;
import www.jykj.com.jykj_zxyl.activity.myself.SurplusDetailActivity;

/**
 * 积分明细适配器
 */
public class MyIntegralRecycleAdapter extends RecyclerView.Adapter<MyIntegralRecycleAdapter.ViewHolder> {
    public List<MyIntegralEntity> datas = new ArrayList<>();
    private         MyIntegralActivity           mActivity;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public MyIntegralRecycleAdapter(List<MyIntegralEntity> list, Context context, MyIntegralActivity myIntegralActivity){
        mContext = context;
        datas = list;
        mActivity = myIntegralActivity;
    }

    //重新设置数据
    public void setDate(List<MyIntegralEntity> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitymyintegral_integralinfo,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
       }
        //将数据与界面进行绑定的操作

    /**
     * 如果是第一条数据，那么他肯定是该组的第一个用户，所以显示组别
     * 如果该用户是该组的第一个用户，那么就显示组别
     * 否则不再显示
     *
     *
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.mDate.setText(datas.get(position).getDate());
        viewHolder.price.setText(datas.get(position).getPrice());
        viewHolder.mIntegralType.setText(datas.get(position).getIntegralTypeString());
        if (datas.get(position).getIntegralType() == 1)
        {
            viewHolder.price.setTextColor(mActivity.getResources().getColor(R.color.tabColor_nomal));
        }
        if (datas.get(position).getIntegralType() == -1)

        {
            viewHolder.price.setTextColor(mActivity.getResources().getColor(R.color.colorRed));
        }


    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView mIntegralType;                                //类型
            public TextView price;                                  //金额
            public TextView mDate;                                  //时间

            public ViewHolder(View view){
                super(view);
//                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_surplusDetail_suType);
                mIntegralType = (TextView) view.findViewById(R.id.item_integralDetail_suType);
                price = (TextView) view.findViewById(R.id.item_integralDetail_price);
                mDate = (TextView) view.findViewById(R.id.item_integralDetail_data);
            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}