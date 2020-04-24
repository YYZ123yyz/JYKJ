package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import entity.yhhd.ProvideBindingDoctorDoctorApply;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 *  患医互动 ==》医生好友 ==》好友申请
 */
public class YHHD_HYSQRecycleAdapter extends RecyclerView.Adapter<YHHD_HYSQRecycleAdapter.ViewHolder> {
    public          List<ProvideBindingDoctorDoctorApply>                    datas = new ArrayList<>();
    private         OnItemTYClickListener             mOnItemTYClickListener;           //同意
    private         OnItemJJClickListener             mOnItemJJClickListener;           //拒绝


    private         Context                         mContext;


    public YHHD_HYSQRecycleAdapter(List<ProvideBindingDoctorDoctorApply> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideBindingDoctorDoctorApply> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hz_yshy_hysq_,viewGroup,false);
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
        if (datas.get(position).getUserLogoUrl() != null && !"".equals(datas.get(position).getUserLogoUrl()))
        {
            try {
                int avatarResId = Integer.parseInt(datas.get(position).getUserLogoUrl());
                Glide.with(mContext).load(avatarResId).into(viewHolder.mImage);
            } catch (Exception e) {
                //use default avatar
                Glide.with(mContext).load(datas.get(position).getUserLogoUrl())
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(viewHolder.mImage);
            }
        }
        viewHolder.mUserName.setText(datas.get(position).getLaunchDoctorName());
        switch (datas.get(position).getFlagApplyType())
        {
            case 0:
                viewHolder.flagApplyType.setText("未知");
                break;
            case 1:
                viewHolder.flagApplyType.setText("扫码添加");
                break;
            case 2:
                viewHolder.flagApplyType.setText("(通过输入二维码)");
                break;

            case 3:
                viewHolder.flagApplyType.setText("医生联盟内添加");
                break;
        }

        if (datas.get(position).getApplyDate() != null)
            viewHolder.date.setText(Util.dateToStr(datas.get(position).getApplyDate()));
        switch (datas.get(position).getFlagApplyState())
        {
            case 0:
                if (datas.get(position).getApplyReason() == null || "".equals(datas.get(position).getApplyReason()))
                    viewHolder.applyReason.setVisibility(View.GONE);
                else
                    viewHolder.applyReason.setVisibility(View.VISIBLE);
                viewHolder.applyReason.setText(datas.get(position).getApplyReason());
                viewHolder.opera.setVisibility(View.VISIBLE);
                viewHolder.jjyy.setVisibility(View.GONE);
                break;
            case 1:
                if (datas.get(position).getRefuseReason() == null || "".equals(datas.get(position).getRefuseReason()))
                    viewHolder.applyReason.setVisibility(View.GONE);
                else
                    viewHolder.applyReason.setVisibility(View.VISIBLE);
                viewHolder.applyReason.setText(datas.get(position).getRefuseReason());
                viewHolder.opera.setVisibility(View.GONE);
                viewHolder.jjyy.setVisibility(View.VISIBLE);
                viewHolder.jjyy.setText("未通过");
                break;
            case 2:
                if (datas.get(position).getApplyReason() == null || "".equals(datas.get(position).getApplyReason()))
                    viewHolder.applyReason.setVisibility(View.GONE);
                else
                    viewHolder.applyReason.setVisibility(View.VISIBLE);
                viewHolder.applyReason.setText(datas.get(position).getApplyReason());
                viewHolder.opera.setVisibility(View.GONE);
                viewHolder.jjyy.setVisibility(View.VISIBLE);
                viewHolder.jjyy.setText("已过期");
                break;
            case 3:
                if (datas.get(position).getApplyReason() == null || "".equals(datas.get(position).getApplyReason()))
                    viewHolder.applyReason.setVisibility(View.GONE);
                else
                    viewHolder.applyReason.setVisibility(View.VISIBLE);
                viewHolder.applyReason.setText(datas.get(position).getApplyReason());
                viewHolder.opera.setVisibility(View.GONE);
                viewHolder.jjyy.setVisibility(View.VISIBLE);
                viewHolder.jjyy.setText("已通过");
                break;
        }
        if (mOnItemTYClickListener != null)
        {
            viewHolder.ty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemTYClickListener.onClick(position);
                }
            });

            viewHolder.ty.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemTYClickListener.onLongClick(position);
                    return false;
                }
            });
        }


        if (mOnItemJJClickListener != null)
        {
            viewHolder.jj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemJJClickListener.onClick(position);
                }
            });

            viewHolder.jj.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemJJClickListener.onLongClick(position);
                    return false;
                }
            });
        }




    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
//            return 10;
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            private ImageView   mImage;
            private TextView    mUserName;                                  //姓名
            private TextView    flagApplyType;                                  //申请类型
            private TextView    date;                                  //申请时间
            private TextView    applyReason;                                  //描述
            private LinearLayout opera;                                 //操作

            private TextView    ty;                                  //同意
            private TextView    jj;                                  //拒绝
            private TextView    jjyy;                                  //拒绝原因


            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemActivityTWJZ_hzxx);
                mImage = (ImageView)view.findViewById(R.id.tv_itemUserImageText);
                mUserName = (TextView)view.findViewById(R.id.tv_itemUserNameText);
                flagApplyType = (TextView)view.findViewById(R.id.tv_itemSSYText);
                date = (TextView)view.findViewById(R.id.date);
                applyReason = (TextView)view.findViewById(R.id.tv_applyReason);
                opera = (LinearLayout)view.findViewById(R.id.opera);
                ty = (TextView)view.findViewById(R.id.tv_ty);
                jj = (TextView)view.findViewById(R.id.tv_jj);
                jjyy = (TextView)view.findViewById(R.id.flagApplyStateText);
            }
        }



    public interface OnItemTYClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemTYClickListenerr(OnItemTYClickListener onItemClickListener ){
        this.mOnItemTYClickListener=onItemClickListener;
    }


    public interface OnItemJJClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemJJClickListenerr(OnItemJJClickListener onItemClickListener ){
        this.mOnItemJJClickListener=onItemClickListener;
    }





}