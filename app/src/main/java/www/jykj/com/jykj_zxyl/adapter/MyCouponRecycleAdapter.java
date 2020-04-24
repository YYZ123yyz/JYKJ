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

import entity.MyCouponEntity;
import entity.RedeemProductInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.MyCouponActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyIntegralInfoActivity;

/**
 * 我的优惠券适配器
 */
public class MyCouponRecycleAdapter extends RecyclerView.Adapter<MyCouponRecycleAdapter.ViewHolder> {
    public          List<MyCouponEntity>            datas = new ArrayList<>();
    private         MyCouponActivity                mActivity;
    private         OnItemClickListener                mOnItemClickListener;
    private         Context                            mContext;


    public MyCouponRecycleAdapter(List<MyCouponEntity> list, Context context, MyCouponActivity myCouponActivity){
        mContext = context;
        datas = list;
        mActivity = myCouponActivity;
    }

    //重新设置数据
    public void setDate(List<MyCouponEntity> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitycoupon_couponinfo,viewGroup,false);
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

    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView     mItName;                                //产品兑换名称
            public TextView     mItExplain;                             //产品说明
            public TextView     mItPrice;                               //产品价格

            public ViewHolder(View view){
                super(view);
//                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_surplusDetail_suType);
                mItName = (TextView)view.findViewById(R.id.tv_itemIntegralInfo_productName);
                mItExplain = (TextView)view.findViewById(R.id.tv_itemIntegralInfo_productExplain);
                mItPrice = (TextView)view.findViewById(R.id.tv_itemIntegralInfo_productIntegralPrice);
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