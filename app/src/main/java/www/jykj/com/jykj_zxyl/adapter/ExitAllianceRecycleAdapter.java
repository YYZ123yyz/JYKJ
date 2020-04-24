package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.AllianceEntity;
import entity.PBInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.ExitAllianceActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyPBActivity;

/**
 * 联盟信息适配器
 */
public class ExitAllianceRecycleAdapter extends RecyclerView.Adapter<ExitAllianceRecycleAdapter.ViewHolder> {
    public          List<AllianceEntity>            datas = new ArrayList<>();
    private         ExitAllianceActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public ExitAllianceRecycleAdapter(List<AllianceEntity> list, Context context, ExitAllianceActivity pbActivity){
        mContext = context;
        datas = list;
        mActivity = pbActivity;
    }

    //重新设置数据
    public void setDate(List<AllianceEntity> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activityexitalliance_allianceinfo,viewGroup,false);
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
        viewHolder.mAllianceName.setText(datas.get(position).getAllianceName());
        viewHolder.mAllianceRemark.setText(datas.get(position).getAllianceRemark());
    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView mAllianceName;                                   //联盟名称
            public TextView mAllianceRemark;                                  //联盟说明

            public ViewHolder(View view){
                super(view);
//                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_surplusDetail_suType);
                mAllianceName = (TextView)view.findViewById(R.id.tv_itemExitAlliance_allianceName);
                mAllianceRemark = (TextView)view.findViewById(R.id.tv_itemExitAlliance_allianceRemark);

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