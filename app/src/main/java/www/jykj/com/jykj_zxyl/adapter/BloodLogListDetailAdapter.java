package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import entity.patientInfo.ProvidePatientConditionBloodPressureRecord;
import www.jykj.com.jykj_zxyl.R;

public class BloodLogListDetailAdapter extends RecyclerView.Adapter<BloodLogListDetailAdapter.ViewHolder> {

    public List<ProvidePatientConditionBloodPressureRecord> datas ;


    private Context mContext;


    public BloodLogListDetailAdapter(List<ProvidePatientConditionBloodPressureRecord> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionBloodPressureRecord> list) {
        datas = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blood_log_list_detail, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvidePatientConditionBloodPressureRecord data = datas.get(position);
        holder.tvXy.setText(data.getBloodTypeName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvXy,tvXl,tvSj;

        public ViewHolder(View view) {
            super(view);
            tvXy = view.findViewById(R.id.tv_xy);
            tvXl = view.findViewById(R.id.tv_xl);
            tvSj = view.findViewById(R.id.tv_sj);
        }
    }
}
