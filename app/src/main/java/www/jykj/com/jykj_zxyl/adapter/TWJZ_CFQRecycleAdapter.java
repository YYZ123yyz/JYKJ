package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.wdzs.ProvideInteractOrderPrescribe;
import www.jykj.com.jykj_zxyl.R;

/**
 *  图文就诊处方签
 */
public class TWJZ_CFQRecycleAdapter extends RecyclerView.Adapter<TWJZ_CFQRecycleAdapter.ViewHolder> {
    public          List<ProvideInteractOrderPrescribe>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //点击事件
    private         OnItemDeleteClickListener             mOnItemDeleteClickListener;           //删除

    private         Context                         mContext;


    public TWJZ_CFQRecycleAdapter(List<ProvideInteractOrderPrescribe> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractOrderPrescribe> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitytwjz_cfq,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);

                return vh;
       }
        //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.name.setText(datas.get(position).getDrugName());
        viewHolder.prescribeType.setText(datas.get(position).getPrescribeTypeName());
        viewHolder.drugAmount.setText("购买数量："+datas.get(position).getDrugAmount());
        viewHolder.useNum.setText("每次："+datas.get(position).getUseNum()+datas.get(position).getSpecUnit());
        viewHolder.useFrequency.setText("服用频率："+datas.get(position).getUseFrequency()+"次/天");
        viewHolder.useCycle.setText("服用周期："+datas.get(position).getUseCycle()+"天");
        viewHolder.useDesc.setText(datas.get(position).getUseDesc());

        if (mOnItemClickListener != null)
        {
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

        if (mOnItemDeleteClickListener != null)
        {
            viewHolder.mClickDeleteLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemDeleteClickListener.onClick(position);
                }
            });

            viewHolder.mClickDeleteLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemDeleteClickListener.onLongClick(position);
                    return false;
                }
            });


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
            public TextView mClickDeleteLinearLayout;                     //删除
            public TextView     name;                                  //药品名称
            public TextView     prescribeType;                                  //处方类型
            public TextView     useNum;                                  //每次服用数量
            public TextView     useFrequency;                                  //用药频率
            public TextView     useCycle;                                  //服用周期
            public TextView     useDesc;                                  //用药描述

            public TextView     drugAmount;                             //购买数量

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.clickLayout);
                mClickDeleteLinearLayout = (TextView) view.findViewById(R.id.tv_delete);
                name = (TextView)view.findViewById(R.id.name);
                prescribeType = (TextView)view.findViewById(R.id.prescribeType);
                useNum = (TextView)view.findViewById(R.id.useNum);
                useFrequency = (TextView)view.findViewById(R.id.useFrequency);

                useCycle = (TextView)view.findViewById(R.id.useCycle);
                useDesc = (TextView)view.findViewById(R.id.useDesc);
                prescribeType = (TextView)view.findViewById(R.id.prescribeType);
                drugAmount = (TextView)view.findViewById(R.id.drugAmount);
            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    public interface OnItemDeleteClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnDeleteItemClickListener(OnItemDeleteClickListener onItemDeleteClickListener ){
        this.mOnItemDeleteClickListener=onItemDeleteClickListener;
    }

}