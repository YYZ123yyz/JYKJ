package www.jykj.com.jykj_zxyl.appointment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorScheduTimesBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-30 12:42
 */
public class DoctorSeheduTimeAdapter extends RecyclerView.Adapter<DoctorSeheduTimeAdapter.ViewHolder>  {
    private Context context;
    private List<DoctorScheduTimesBean> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public DoctorSeheduTimeAdapter(Context mContext, List<DoctorScheduTimesBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_schedu_time_info, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorScheduTimesBean doctorScheduTimesBean = list.get(position);
        String startTime = DateUtils.getDateToStringDD(doctorScheduTimesBean.getStartTimes());
        String endTime = DateUtils.getDateToStringDD(doctorScheduTimesBean.getEndTimes());
        holder.tvTimeSlot.setText(String.format("%s-%s", startTime, endTime));
        holder.tvSignalNum.setText(doctorScheduTimesBean.getReserveCount()+"");
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickUpdate(position);
                }

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickDelete(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimeSlot;
        private TextView tvSignalNum;
        private Button btnUpdate;
        private Button btnDelete;
        public ViewHolder(View view) {
            super(view);
            tvTimeSlot = view.findViewById(R.id.tv_time_slot);
            tvSignalNum = view.findViewById(R.id.tv_signal_num);
            btnUpdate=view.findViewById(R.id.btn_update);
            btnDelete=view.findViewById(R.id.btn_delete);
        }
    }

    public void setData(List<DoctorScheduTimesBean>datas){
        this.list=datas;
    }


    public interface OnClickItemListener{
        void onClickUpdate(int pos);

        void onClickDelete(int pos);
    }
}