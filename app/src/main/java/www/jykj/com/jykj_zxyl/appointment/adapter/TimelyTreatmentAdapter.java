package www.jykj.com.jykj_zxyl.appointment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TimelyTreatmentBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-02 15:02
 */
public class TimelyTreatmentAdapter extends RecyclerView.Adapter<TimelyTreatmentAdapter.ViewHolder> {
    private Context context;
    private List<TimelyTreatmentBean> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public TimelyTreatmentAdapter(Context mContext, List<TimelyTreatmentBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timely_visit,
                parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimelyTreatmentBean timelyTreatmentBean = list.get(position);
        holder.tvTimeSlot.setText(String.format("%s-%s", timelyTreatmentBean.getStartTimes()
                , timelyTreatmentBean.getEndTimes()));

        holder.tvAppointmentNum.setText(String.format("%d", timelyTreatmentBean.getReservedCount()));
        holder.tvReceiveTreatmentNum.setText(String.format("%s", timelyTreatmentBean.getConfirmCount()));
        holder.tvCancelAppointmentNum.setText(String.format("%s", timelyTreatmentBean.getCancelCount()));

        String sourceType = timelyTreatmentBean.getSourceType();
        if (sourceType.equals("1")) {
            holder.tvSignalNum.setTextColor(ContextCompat.getColor(context,R.color.color_7a9eff));
            holder.tvSignalNum.setText(String.format("排班号源数量 %d", timelyTreatmentBean.getReserveCount()));
            holder.ivEditBtn.setVisibility(View.GONE);
        } else if (sourceType.equals("2")) {
            holder.tvSignalNum.setTextColor(ContextCompat.getColor(context,R.color.color_00ed16));
            holder.tvSignalNum.setText(String.format("临时号源数量 %d", timelyTreatmentBean.getReserveCount()));
            holder.ivEditBtn.setVisibility(View.VISIBLE);
        }
        holder.rlLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickItem(position);
                }
            }
        });
        holder.llAppointRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickAppoint(position);
                }
            }
        });

        holder.llReceiveRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickReceive(position);
                }
            }
        });
        holder.llCancelAppointRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickCancelAppoint(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<TimelyTreatmentBean> datas) {
        this.list = datas;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimeSlot;
        private TextView tvSignalNum;
        private TextView tvAppointmentNum;
        private TextView tvReceiveTreatmentNum;
        private TextView tvCancelAppointmentNum;
        private ImageView ivEditBtn;
        private RelativeLayout rlLayoutRoot;
        private LinearLayout llAppointRoot;
        private LinearLayout llReceiveRoot;
        private LinearLayout llCancelAppointRoot;

        public ViewHolder(View view) {
            super(view);
            tvTimeSlot = view.findViewById(R.id.tv_time_slot);
            tvSignalNum = view.findViewById(R.id.tv_signal_num);
            tvAppointmentNum = view.findViewById(R.id.tv_appointment_num);
            tvReceiveTreatmentNum = view.findViewById(R.id.tv_receive_treatment_num);
            tvCancelAppointmentNum = view.findViewById(R.id.tv_cancel_appointment_num);
            ivEditBtn = view.findViewById(R.id.iv_eidt_btn);
            rlLayoutRoot=view.findViewById(R.id.rl_layout_root);
            llAppointRoot=view.findViewById(R.id.ll_appoint_root);
            llReceiveRoot=view.findViewById(R.id.ll_receive_root);
            llCancelAppointRoot=view.findViewById(R.id.ll_cancel_appoint_root);

        }
    }

    public interface OnClickItemListener{
        void onClickItem(int pos);

        void onClickAppoint(int pos);

        void onClickReceive(int pos);

        void onClickCancelAppoint(int pos);
    }

}

