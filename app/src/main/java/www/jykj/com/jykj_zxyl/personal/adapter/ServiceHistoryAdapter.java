package www.jykj.com.jykj_zxyl.personal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.meg7.widget.CircleImageView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReservePatientDoctorInfo;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:服务历史适配器
 *
 * @author: qiuxinhai
 * @date: 2020-10-06 15:39
 */
public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryAdapter.ViewHolder> {
    private Context context;
    private List<ReservePatientDoctorInfo> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public ServiceHistoryAdapter(Context mContext, List<ReservePatientDoctorInfo> list) {
        this.list=list;
        this.context=mContext;
    }

    public void setData(List<ReservePatientDoctorInfo> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_history, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ReservePatientDoctorInfo patientInfoBean = list.get(i);
        holder.tvUserName.setText(patientInfoBean.getMainPatientName());
        String patientSex = patientInfoBean.getPatientSex();
        if (patientSex.equals("1")) {
            holder.rlAgeAndGendarRoot.setBackgroundResource(R.drawable.bg_round_2581ff_6);
        } else if (patientSex.equals("2")) {
            holder.rlAgeAndGendarRoot.setBackgroundResource(R.drawable.bg_round_eb6877_6);
        }
        holder.tvAgeNum.setText(String.valueOf(patientInfoBean.getPatientAge()));
        long reserveConfigStart = patientInfoBean.getReserveConfigStart();
        holder.tvAppointmentTime.setText(DateUtils.getDateToStringYYYMMDDHHMM(reserveConfigStart));
        holder.tvPppointmentSubject.setText(patientInfoBean.getReserveProjectName());
        String chiefComplaint = patientInfoBean.getChiefComplaint();
        holder.tvPatientChief.setText(StringUtils.isNotEmpty(chiefComplaint)?chiefComplaint:"无");
        Glide.with(context).load(patientInfoBean.getPatientLogoUrl())
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.ivUserHead);

        holder.tvConsultDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickConsultDataItem(i);
                }
            }
        });
        holder.tvComprehensiveSurfaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickStatisticTableItem(i);
                }
            }
        });
        holder.tvMedicalRecordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickMedicalRecordItem(i);
                }
            }
        });
        holder.tvDiagnosisMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickDiagnosisMessageItem(i);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivUserHead;
        private TextView tvUserName;
        private RelativeLayout rlAgeAndGendarRoot;
        private ImageView ivGenderIcon;
        private TextView tvAgeNum;
        private TextView tvAppointmentTime;
        private TextView tvPppointmentSubject;
        private TextView tvPatientChief;
        private TextView tvConsultDataBtn;
        private TextView tvComprehensiveSurfaceBtn;
        private TextView tvMedicalRecordsBtn;
        private TextView tvDiagnosisMessageBtn;

        public ViewHolder(View view) {
            super(view);
            ivUserHead = view.findViewById(R.id.iv_userhead);
            tvUserName = view.findViewById(R.id.tv_user_name);
            rlAgeAndGendarRoot = view.findViewById(R.id.rl_age_and_gendar_root);
            ivGenderIcon = view.findViewById(R.id.iv_gender_icon);
            tvAgeNum = view.findViewById(R.id.tv_age_num);
            tvAppointmentTime = view.findViewById(R.id.tv_appointment_time);
            tvPppointmentSubject = view.findViewById(R.id.tv_appointment_subject);
            tvPatientChief = view.findViewById(R.id.tv_patient_chief);
            tvConsultDataBtn = view.findViewById(R.id.tv_consult_data_btn);
            tvComprehensiveSurfaceBtn = view.findViewById(R.id.tv_comprehensive_surface_btn);
            tvMedicalRecordsBtn = view.findViewById(R.id.tv_medical_records_btn);
            tvDiagnosisMessageBtn = view.findViewById(R.id.tv_diagnosis_message_btn);
        }
    }

    public interface  OnClickItemListener{

        void onClickConsultDataItem(int pos);

        void onClickStatisticTableItem(int pos);

        void onClickMedicalRecordItem(int pos);

        void onClickDiagnosisMessageItem(int pos);
    }

}
