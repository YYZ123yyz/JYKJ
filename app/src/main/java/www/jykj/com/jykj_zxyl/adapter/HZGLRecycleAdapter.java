package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.MyCouponDetailEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.myself.MyCouponDetailActivity;

/**
 *患者管理患者信息适配器
 */
public class HZGLRecycleAdapter extends RecyclerView.Adapter<HZGLRecycleAdapter.ViewHolder> {
    public          List<HZIfno>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //用户资料点击事件
    private         OnXYItemClickListener           mOnXYItemClickListener;         //血压点击事件
    private         OnYYItemClickListener           mOnYYItemClickListener;        //用药点击事件
    private         OnQTDKItemClickListener         mOnQTDKItemClickListenerl;     //其他打卡点击事件
    private         OnTXHZItemClickListener         mOnTXHZItemClickListenerl;      //提醒患者点击事件

    private         Context                         mContext;


    public HZGLRecycleAdapter(List<HZIfno> list, Context context){
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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragmenthzgl_hzlinfo,viewGroup,false);
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
        viewHolder.mHzName.setText(datas.get(position).getHzName());
        viewHolder.mHzAge.setText(datas.get(position).getHzAge());
        if (datas.get(position).getHzSex() == 1)
            viewHolder.mHzSex.setBackgroundResource(R.mipmap.man);
        if (datas.get(position).getHzSex() == -1)
            viewHolder.mHzSex.setBackgroundResource(R.mipmap.women);

        if (datas.get(position).getState() == 2)
        {
            viewHolder.mHzState.setTextColor(mContext.getResources().getColor(R.color.textColor_hzgltabtx));
            viewHolder.mHzState.setText("当前状态：提醒");
        }
        if (datas.get(position).getState() == 1)
        {
            viewHolder.mHzState.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
            viewHolder.mHzState.setText("当前状态：预警");
        }
        if (datas.get(position).getState() == 3)
        {
            viewHolder.mHzState.setTextColor(mContext.getResources().getColor(R.color.textColor_hzgltabzc));
            viewHolder.mHzState.setText("当前状态：正常");
        }

        viewHolder.mHzLaber.setText(datas.get(position).getLaber());

        //用户资料点击事件
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

        //用户血压点击事件
        if (mOnXYItemClickListener != null)
        {
            viewHolder.mXY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnXYItemClickListener.onClick(position);
                }
            });

            viewHolder.mXY.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnXYItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        //用用药点击事件
        if (mOnYYItemClickListener != null)
        {
            viewHolder.mYY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnYYItemClickListener.onClick(position);
                }
            });

            viewHolder.mYY.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnYYItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


        //其他打卡点击事件
        if (mOnQTDKItemClickListenerl != null)
        {
            viewHolder.mQTDK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnQTDKItemClickListenerl.onClick(position);
                }
            });

            viewHolder.mQTDK.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnQTDKItemClickListenerl.onLongClick(position);
                    return false;
                }
            });
        }

        //提醒患者点击事件
        if (mOnTXHZItemClickListenerl != null)
        {
            viewHolder.mTXHZ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnTXHZItemClickListenerl.onClick(position);
                }
            });

            viewHolder.mTXHZ.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnTXHZItemClickListenerl.onLongClick(position);
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
            public RelativeLayout mXY;                                  //血压
            public RelativeLayout mYY;                                  //用药
            public RelativeLayout mQTDK;                                //其他打卡
            public RelativeLayout mTXHZ;                                //提醒患者

            public TextView     mHzName;                                //患者姓名
            public TextView     mHzAge;                                 //患者年龄
            public LinearLayout mHzSex;                                 //患者性别
            public TextView     mHzState;                                //患者状态
            public TextView     mHzLaber;                                //患者标签

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemFragmentHZGL_hzInfoLayout);
                mXY = (RelativeLayout)view.findViewById(R.id.item_fragmentHZGL_xy);
                mYY = (RelativeLayout)view.findViewById(R.id.rv_fragmentHZGL_yy);
                mQTDK = (RelativeLayout)view.findViewById(R.id.item_fragmentHZGL_qtdk);
                mTXHZ = (RelativeLayout)view.findViewById(R.id.item_fragmentHZGL_TXHZ);

                mHzName = (TextView)view.findViewById(R.id.item_fragmentHZGL_hzName);
                mHzAge = (TextView)view.findViewById(R.id.item_fragmentHZGL_hzAge);
                mHzSex = (LinearLayout) view.findViewById(R.id.item_fragmentHZGL_hzSex);
                mHzState = (TextView) view.findViewById(R.id.item_fragmentHZGL_hzState);
                mHzLaber = (TextView)view.findViewById(R.id.item_fragmentHZGL_hzLaber);
            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    public interface OnXYItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnXYItemClickListener(OnXYItemClickListener onXYItemClickListener ){
        this.mOnXYItemClickListener=onXYItemClickListener;
    }

    public interface OnYYItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnYYItemClickListener(OnYYItemClickListener onYYItemClickListener ){
        this.mOnYYItemClickListener=onYYItemClickListener;
    }


    public interface OnQTDKItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnQTDKItemClickListener(OnQTDKItemClickListener onQTDKItemClickListener ){
        this.mOnQTDKItemClickListenerl=onQTDKItemClickListener;
    }


    public interface OnTXHZItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnTXHZItemClickListener(OnTXHZItemClickListener onTXHZItemClickListener ){
        this.mOnTXHZItemClickListenerl=onTXHZItemClickListener;
    }
}