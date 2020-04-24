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

import entity.wdzs.ProvideBasicsDisease;
import entity.wdzs.ProvideDrugInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.RedeemRecordActivity;

/**
 * 我的诊所==》药品信息
 */
public class WDZS_YPXXAdapter extends RecyclerView.Adapter<WDZS_YPXXAdapter.ViewHolder> {
    public          List<ProvideDrugInfo>            datas = new ArrayList<>();
    private         RedeemRecordActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public WDZS_YPXXAdapter(List<ProvideDrugInfo> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideDrugInfo> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wdzs_ypxx,viewGroup,false);
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
        viewHolder.mJB1.setText(datas.get(position).getDrugName());
        viewHolder.mTV1.setText("规格："+datas.get(position).getDrugSpec());
        viewHolder.mTV2.setText("单位"+datas.get(position).getDrugUnit());
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

    }
    //获取数据的数量
    @Override
    public int getItemCount(){
        if (datas == null )
            return 0;
        return datas.size();
    }




    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView     mJB1;                                //疾病名称
        public TextView     mTV1;
        public TextView     mTV2;



        public ViewHolder(View view){
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mJB1 = (TextView)view.findViewById(R.id.jbmc);
            mTV1 = (TextView)view.findViewById(R.id.tv1);
            mTV2 = (TextView)view.findViewById(R.id.tv2);

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