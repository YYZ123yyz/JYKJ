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

import entity.MySurplusDetailEntity;
import entity.PBInfo;
import entity.mySelf.ProvideDoctorSetScheduling;
import entity.mySelf.doctorScheduling.DoctorSetScheduling;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.MyPBActivity;
import www.jykj.com.jykj_zxyl.activity.myself.SurplusDetailActivity;

/**
 * 余额明细适配器
 */
public class MyPBRecycleAdapter extends RecyclerView.Adapter<MyPBRecycleAdapter.ViewHolder> {
    public List<DoctorSetScheduling> datas = new ArrayList<>();
    private MyPBActivity mActivity;
    private OnItemMoningClickListener mOnItemMoningClickListener;           //早上点击事件
    private OnItemNoonClickListener mOnItemNoonClickListener;           //中午点击事件
    private OnItemNightClickListener mOnItemNightClickListener;           //晚上点击事件

    private Context mContext;


    public MyPBRecycleAdapter(List<DoctorSetScheduling> list, Context context, MyPBActivity pbActivity) {
        mContext = context;
        datas = list;
        mActivity = pbActivity;
    }

    //重新设置数据
    public void setDate(List<DoctorSetScheduling> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitypbinfo_pbinfoinfo, viewGroup, false);
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        switch (datas.get(position).getDate()) {
            case 1:
                viewHolder.mDate.setText("周一");
                break;
            case 2:
                viewHolder.mDate.setText("周二");
                break;
            case 3:
                viewHolder.mDate.setText("周三");
                break;
            case 4:
                viewHolder.mDate.setText("周四");
                break;
            case 5:
                viewHolder.mDate.setText("周五");
                break;
            case 6:
                viewHolder.mDate.setText("周六");
                break;
            case 7:
                viewHolder.mDate.setText("周日");
                break;
        }

        if (datas.get(position).getMorning() == 0)
            viewHolder.mMoning.setImageResource(R.mipmap.activity_pbinfo_nochoice);
        else
            viewHolder.mMoning.setImageResource(R.mipmap.activity_pbinfo_choice);

        if (datas.get(position).getNoon() == 0)
            viewHolder.mAfternoon.setImageResource(R.mipmap.activity_pbinfo_nochoice);
        else
            viewHolder.mAfternoon.setImageResource(R.mipmap.activity_pbinfo_choice);

        if (datas.get(position).getNight() == 0)
            viewHolder.mNinght.setImageResource(R.mipmap.activity_pbinfo_nochoice);
        else
            viewHolder.mNinght.setImageResource(R.mipmap.activity_pbinfo_choice);

        //早上
        if (viewHolder.mMoning != null)
            viewHolder.mMoning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemMoningClickListener.onClick(position);
                }
            });
        //中午
        if (viewHolder.mAfternoon != null)
            viewHolder.mAfternoon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemNoonClickListener.onClick(position);
                }
            });
        //晚上
        if (viewHolder.mNinght != null)
            viewHolder.mNinght.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemNightClickListener.onClick(position);
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
        public TextView mDate;                                   //时间
        public ImageView mMoning;                                  //早上
        public ImageView mAfternoon;                                  //早上
        public ImageView mNinght;                                  //早上

        public ViewHolder(View view) {
            super(view);
//                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_surplusDetail_suType);
            mDate = (TextView) view.findViewById(R.id.item_activityPBInfo_date);
            mMoning = (ImageView) view.findViewById(R.id.item_activityPBInfo_morning);
            mAfternoon = (ImageView) view.findViewById(R.id.item_activityPBInfo_afternoon);
            mNinght = (ImageView) view.findViewById(R.id.item_activityPBInfo_ninght);

        }
    }

    /**
     * 早上
     */
    public interface OnItemMoningClickListener {
        void onClick(int position);
    }

    public void setOnItemMoningClickListener(OnItemMoningClickListener onMoningItemClickListener) {
        this.mOnItemMoningClickListener = onMoningItemClickListener;
    }

    /**
     * 中午
     */
    public interface OnItemNoonClickListener {
        void onClick(int position);
    }

    public void setOnItemNoonClickListener(OnItemNoonClickListener onItemNoonClickListener) {
        this.mOnItemNoonClickListener = onItemNoonClickListener;
    }

    /**
     * 晚上
     */
    public interface OnItemNightClickListener {
        void onClick(int position);
    }

    public void setOnItemNightClickListener(OnItemNightClickListener onItemNightClickListener) {
        this.mOnItemNightClickListener = onItemNightClickListener;
    }
}