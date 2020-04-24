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

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;

/**
 *  就医总览==>个人总览
 */
public class JYZL_GRZLRecycleAdapter extends RecyclerView.Adapter<JYZL_GRZLRecycleAdapter.ViewHolder> {
    public          List<HZIfno>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //用户资料点击事件


    private         Context                         mContext;


    public JYZL_GRZLRecycleAdapter(List<HZIfno> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<HZIfno> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activityjyzl_grzl,viewGroup,false);
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

//        return datas.size();
            return 10;
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView     mWZXX;                                  //问诊信息
            public TextView     mYHHD;                                  //医患互动
            public TextView     mKJCF;                                  //开具处方
            public TextView     mZHLY;                                  //诊后留言
            public TextView     mCFQ;                                  //处方签


            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemJZZL_click);

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