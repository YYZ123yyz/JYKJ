package www.jykj.com.jykj_zxyl.activity.home.mypatient.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.easeui.jykj.adapter.Rv_CoachingAdapter;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.Date;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.home.mypatient.label.LabelAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>   {
    private List<ProvidePatientConditionDiseaseRecordBean> datas;
    private Rv_CoachingAdapter.OnItemCoachingClickListener OnItemCoachingClickListener;
    public DoctorAdapter(List<ProvidePatientConditionDiseaseRecordBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doctorfragment,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ProvidePatientConditionDiseaseRecordBean providePatientConditionDiseaseRecordBean = datas.get(i);
        if(providePatientConditionDiseaseRecordBean!=null){
            viewHolder.tv_doctorname.setText("记录医生:  "+providePatientConditionDiseaseRecordBean.getDoctorName());
            long createDate = providePatientConditionDiseaseRecordBean.getCreateDate();
            String dates = DateUtils.stampToDates(createDate);
            viewHolder.item_time.setText(dates);
            viewHolder.tv_diagnosis.setText(providePatientConditionDiseaseRecordBean.getDiagnosisName());
            viewHolder.tv_Suggest.setText(providePatientConditionDiseaseRecordBean.getTreatmentProposal());
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_doctorname,item_time,tv_diagnosis,tv_Suggest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_doctorname=itemView.findViewById(R.id.tv_doctorname);
            item_time=itemView.findViewById(R.id.item_time);
            tv_diagnosis=itemView.findViewById(R.id.tv_diagnosis);
            tv_Suggest=itemView.findViewById(R.id.tv_Suggest);
        }
    }

    public void setOnItemCoachingClickListener(Rv_CoachingAdapter.OnItemCoachingClickListener onItemCoachingClickListener) {
        this.OnItemCoachingClickListener = onItemCoachingClickListener;
    }

    public interface OnItemCoachingLinClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
}
