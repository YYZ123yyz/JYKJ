package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;


public class MyselfAdapter extends RecyclerView.Adapter<MyselfAdapter.ViewHolder>   {
    private List<DoctorRecordBean> datas;
    private Rv_CoachingAdapter.OnItemCoachingClickListener OnItemCoachingClickListener;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private OnDeleteClickLister mDeleteClickListener;
    public MyselfAdapter(List<DoctorRecordBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_myself,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DoctorRecordBean doctorRecordBean = datas.get(i);
        if(doctorRecordBean!=null){
            viewHolder.tv_doctorname.setText("记录医生:  "+doctorRecordBean.getRecordName());
            long createDate = doctorRecordBean.getTreatmentDate();
            String dates = DateUtils.stampToDates(createDate);
            viewHolder.item_time.setText(dates);
            viewHolder.tv_diagnosis.setText(doctorRecordBean.getRecordContent());
            if (OnItemCoachingClickListener != null) {
                viewHolder.clickLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnItemCoachingClickListener.onClick(i);
                    }
                });

                viewHolder.clickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {
                        OnItemCoachingClickListener.onLongClick(i);
                        return false;
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_doctorname,item_time,tv_diagnosis,tv_Suggest;
        private LinearLayout clickLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_doctorname=itemView.findViewById(R.id.tv_doctorname);
            item_time=itemView.findViewById(R.id.item_time);
            tv_diagnosis=itemView.findViewById(R.id.tv_diagnosis);
            clickLinearLayout=itemView.findViewById(R.id.clickLinearLayout);
        }
    }

    public void setOnItemCoachingClickListener(Rv_CoachingAdapter.OnItemCoachingClickListener onItemCoachingClickListener) {
        this.OnItemCoachingClickListener = onItemCoachingClickListener;
    }

    public interface OnItemCoachingLinClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
    public void setmDeleteClickListener(OnDeleteClickLister mDeleteClickListener) {
        this.mDeleteClickListener = mDeleteClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
