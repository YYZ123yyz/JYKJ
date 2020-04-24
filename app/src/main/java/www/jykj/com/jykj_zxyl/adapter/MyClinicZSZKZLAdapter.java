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

import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideInteractClinicStateOverview;
import www.jykj.com.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 就诊状况总览
 */
public class MyClinicZSZKZLAdapter extends RecyclerView.Adapter<MyClinicZSZKZLAdapter.ViewHolder> {
    public          List<ProvideInteractClinicStateOverview>            datas = new ArrayList<>();
    private         RedeemRecordActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public MyClinicZSZKZLAdapter(List<ProvideInteractClinicStateOverview> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractClinicStateOverview> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitymyclinic_zszkzl,viewGroup,false);
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
        viewHolder.mContent01.setText(datas.get(position).getContentInfo1());
        viewHolder.mContent02.setText(datas.get(position).getContentInfo2());
        viewHolder.mContent03.setText(datas.get(position).getContentInfo3());
        viewHolder.mContent04.setText(datas.get(position).getContentInfo4());
        viewHolder.mContent05.setText(datas.get(position).getContentInfo5());

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
        public TextView     mContent01;                                //别名
        public TextView     mContent02;                             //时间
        public TextView     mContent03;                             //类型
        public TextView     mContent04;                             //内容
        public TextView     mContent05;                             //内容


        public ViewHolder(View view){
            super(view);
//            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mContent01 = (TextView)view.findViewById(R.id.content01);
            mContent02 = (TextView)view.findViewById(R.id.content02);
            mContent03 = (TextView)view.findViewById(R.id.content03);
            mContent04 = (TextView)view.findViewById(R.id.content04);
            mContent05 = (TextView)view.findViewById(R.id.content05);
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