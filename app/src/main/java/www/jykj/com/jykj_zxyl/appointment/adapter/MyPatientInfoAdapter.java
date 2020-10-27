package www.jykj.com.jykj_zxyl.appointment.adapter;

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
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientInfoBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-31 15:53
 */
public class MyPatientInfoAdapter extends RecyclerView.Adapter<MyPatientInfoAdapter.ViewHolder> {
    private Context context;
    private List<PatientInfoBean> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MyPatientInfoAdapter(Context mContext, List<PatientInfoBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_paitent_info, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PatientInfoBean patientInfoBean = list.get(position);
        holder.tvUserName.setText(patientInfoBean.getMainPatientName());
        String patientSex = patientInfoBean.getPatientSex();
        if (patientSex.equals("1")) {
            holder.rlAgeAndGendarRoot.setBackgroundResource(R.drawable.bg_round_2581ff_6);
        } else if (patientSex.equals("2")) {
            holder.rlAgeAndGendarRoot.setBackgroundResource(R.drawable.bg_round_eb6877_6);
        }
        String reserveStatus = patientInfoBean.getReserveStatus();
        if (reserveStatus.equals("10")) {
            holder.llAppointPatientRoot.setVisibility(View.VISIBLE);
            holder.llReceiveSucessRoot.setVisibility(View.GONE);
            holder.rlCancelledRoot.setVisibility(View.GONE);
            holder.tvSourceType.setText("预约患者");
        } else if (reserveStatus.equals("20")) {
            holder.llAppointPatientRoot.setVisibility(View.GONE);
            holder.llReceiveSucessRoot.setVisibility(View.VISIBLE);
            holder.rlCancelledRoot.setVisibility(View.GONE);
            holder.tvSourceType.setText("接诊患者");
        }else if(reserveStatus.equals("130")){
            holder.llAppointPatientRoot.setVisibility(View.GONE);
            holder.llReceiveSucessRoot.setVisibility(View.GONE);
            holder.rlCancelledRoot.setVisibility(View.VISIBLE);
            holder.tvSourceType.setText("取消预约患者");
        }else if(reserveStatus.equals("110")){
            holder.llAppointPatientRoot.setVisibility(View.GONE);
            holder.llReceiveSucessRoot.setVisibility(View.GONE);
            holder.rlCancelledRoot.setVisibility(View.VISIBLE);
        }

        holder.tvAgeNum.setText(patientInfoBean.getPatientAge());
        long reserveConfigStart = patientInfoBean.getReserveConfigStart();
        holder.tvAppointmentTime.setText(DateUtils.getDateToStringYYYMMDDHHMM(reserveConfigStart));
        holder.tvPppointmentSubject.setText(patientInfoBean.getReserveProjectName());
        String chiefComplaint = patientInfoBean.getChiefComplaint();
        holder.tvPatientChief.setText(StringUtils.isNotEmpty(chiefComplaint)?chiefComplaint:"无");
        Glide.with(context).load(patientInfoBean.getPatientLogoUrl())
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.ivUserHead);
        holder.tvVideoInteractionBtn.setText(patientInfoBean.getReserveProjectName());

        holder.tvCancelAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickCancelAppointment(position);
                }
            }
        });
        holder.tvReceiveTreatmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickReceiveTreatment(position);
                }
            }
        });
        holder.tvMedicalRecordDetialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickMedicalRecordDetial(position);
                }
            }
        });
        holder.tvComprehensiveSurfaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickStatisticTable(position);
                }
            }
        });
        holder.tvComprehensiveSurfaceBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickStatisticTable(position);
                }
            }
        });
        holder.tvVideoInteractionBtn.setOnClickListener(v -> {
            if (onClickItemListener!=null) {
                onClickItemListener.onClickInterrogation(position);
            }
        });
        holder.tvConsultDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickConsultItem(position);
                }
            }
        });
        holder.tvConsultDataBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickConsultItem(position);
                }
            }
        });
        holder.llRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickImItem(position);
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
        private TextView tvCancelAppointmentBtn;
        private TextView tvReceiveTreatmentBtn;
        private TextView tvConsultDataBtn;
        private TextView tvComprehensiveSurfaceBtn;
        private TextView tvVideoInteractionBtn;
        private TextView tvMedicalRecordDetialBtn;
        private TextView tvConsultDataBtn1;
        private TextView tvComprehensiveSurfaceBtn1;
        private TextView tvSourceType;
        private LinearLayout llReceiveSucessRoot;
        private LinearLayout llAppointPatientRoot;
        private RelativeLayout rlCancelledRoot;
        private LinearLayout llRootView;
        public ViewHolder(View view) {
            super(view);
            ivUserHead = view.findViewById(R.id.iv_userhead);
            tvUserName=view.findViewById(R.id.tv_user_name);
            rlAgeAndGendarRoot=view.findViewById(R.id.rl_age_and_gendar_root);
            ivGenderIcon=view.findViewById(R.id.iv_gender_icon);
            tvAgeNum=view.findViewById(R.id.tv_age_num);
            tvAppointmentTime=view.findViewById(R.id.tv_appointment_time);
            tvPppointmentSubject=view.findViewById(R.id.tv_appointment_subject);
            tvPatientChief=view.findViewById(R.id.tv_patient_chief);
            tvCancelAppointmentBtn=view.findViewById(R.id.tv_cancel_appointment_btn);
            tvReceiveTreatmentBtn=view.findViewById(R.id.tv_receive_treatment_btn);
            tvConsultDataBtn=view.findViewById(R.id.tv_consult_data_btn);
            tvComprehensiveSurfaceBtn=view.findViewById(R.id.tv_comprehensive_surface_btn);
            tvVideoInteractionBtn=view.findViewById(R.id.tv_video_interaction_btn);
            tvMedicalRecordDetialBtn=view.findViewById(R.id.tv_medical_record_detial_btn);
            llReceiveSucessRoot=view.findViewById(R.id.ll_receive_sucess_root);
            llAppointPatientRoot=view.findViewById(R.id.ll_appoint_patient_root);
            rlCancelledRoot=view.findViewById(R.id.rl_cancelled_root);
            tvSourceType=view.findViewById(R.id.tv_source_type);
            tvConsultDataBtn1=view.findViewById(R.id.tv_consult_data_btn_1);
            tvComprehensiveSurfaceBtn1 = view.findViewById(R.id.tv_comprehensive_surface_btn_1);
            llRootView=view.findViewById(R.id.ll_root_view);
        }
    }

    public void setData(List<PatientInfoBean>datas){
        this.list=datas;
    }


    public interface OnClickItemListener{

        void onClickCancelAppointment(int pos);

        void onClickReceiveTreatment(int pos);

        void onClickMedicalRecordDetial(int pos);

        void onClickStatisticTable(int pos);

        void onClickImItem(int pos);

        void onClickConsultItem(int pos);

        void onClickInterrogation(int pos);
    }
}
