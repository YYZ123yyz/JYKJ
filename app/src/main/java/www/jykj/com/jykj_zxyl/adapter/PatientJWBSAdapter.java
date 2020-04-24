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
import entity.patientInfo.ProvidePatientLabel;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 患者既往病史适配器
 */
public class PatientJWBSAdapter extends RecyclerView.Adapter<PatientJWBSAdapter.ViewHolder> {
    public          List<ProvidePatientConditionDiseaseRecord>            datas = new ArrayList<>();
    private         RedeemRecordActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public PatientJWBSAdapter(List<ProvidePatientConditionDiseaseRecord> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionDiseaseRecord> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_patientlaber_patientjwbs,viewGroup,false);
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
        viewHolder.mBM.setText(datas.get(position).getRecordName());
        viewHolder.mDate.setText(Util.dateToStr(datas.get(position).getTreatmentDate()));
        viewHolder.mType.setText(datas.get(position).getRecordTypeName());
        viewHolder.mContent.setText(datas.get(position).getRecordContent());

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
        public TextView     mBM;                                //别名
        public TextView     mDate;                             //时间
        public TextView     mType;                             //类型
        public TextView     mContent;                             //内容


        public ViewHolder(View view){
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mBM = (TextView)view.findViewById(R.id.tv_activityPatientJWBS_bm);
            mDate = (TextView)view.findViewById(R.id.tv_activityPatientLaber_createDate);
            mType = (TextView)view.findViewById(R.id.tv_activityPatientLaber_laberName);
            mContent = (TextView)view.findViewById(R.id.tv_activityPatientLaber_content);

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