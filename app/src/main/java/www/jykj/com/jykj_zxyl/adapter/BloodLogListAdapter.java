package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.patientInfo.ProvidePatientConditionBloodPressureGroup;
import entity.patientInfo.ProvidePatientConditionBloodPressureRecord;
import entity.patientInfo.ProvideViewPatientInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.Constant;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class BloodLogListAdapter extends RecyclerView.Adapter<BloodLogListAdapter.ViewHolder> {
    public List<ProvidePatientConditionBloodPressureGroup> datas;
    private Context mContext;
    private BloodLogListDetailAdapter adapter;


    private         OnItemClickListener             mOnItemClickListener;

    public BloodLogListAdapter(List<ProvidePatientConditionBloodPressureGroup> list, Context context) {
        this.mContext = context;
        this.datas = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_blood_log_list, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProvidePatientConditionBloodPressureGroup data = datas.get(position);
        holder.tvDayNum.setText(data.getDayAvgHighPressureNum()+"/"+data.getDayAvgLowPressureNum());
        holder.tvMorningNum.setText(data.getMorningAvgHighPressureNum()+"/"+data.getMorningAvgLowPressureNum());
        holder.tvNightNum.setText(data.getNightAvgHighPressureNum()+"/"+data.getNightAvgLowPressureNum());
        holder.tvGroupTime.setText(data.getGroupRecordDate());
        holder.tvDayType.setText(data.getDayBloodTypeSecondName());
        holder.tvMorningType.setText(data.getMorningBloodTypeSecondName());
        holder.tvNightType.setText(data.getNightBloodTypeSecondName());

        if(data.getDayBloodTypeSecondName().equals("偏低")||data.getDayBloodTypeSecondName().equals("正常")){
            holder.ivDayType.setVisibility(View.VISIBLE);
            holder.ivDayType.setImageResource(R.mipmap.xy1);
        }else if(data.getDayBloodTypeSecondName().equals("轻度")||data.getDayBloodTypeSecondName().equals("中度")){
            holder.ivDayType.setImageResource(R.mipmap.xy2);
            holder.ivDayType.setVisibility(View.VISIBLE);
        }else if(data.getDayBloodTypeSecondName().equals("重度")){
            holder.ivDayType.setImageResource(R.mipmap.xy3);
            holder.ivDayType.setVisibility(View.VISIBLE);
        }else{
            holder.ivDayType.setVisibility(View.GONE);
        }

        if(data.getMorningBloodTypeSecondName().equals("偏低")||data.getMorningBloodTypeSecondName().equals("正常")){
            holder.ivMorningType.setImageResource(R.mipmap.xy1);
            holder.ivMorningType.setVisibility(View.VISIBLE);
        }else if(data.getMorningBloodTypeSecondName().equals("轻度")||data.getMorningBloodTypeSecondName().equals("中度")){
            holder.ivMorningType.setImageResource(R.mipmap.xy2);
            holder.ivMorningType.setVisibility(View.VISIBLE);
        }else if(data.getMorningBloodTypeSecondName().equals("重度")){
            holder.ivMorningType.setImageResource(R.mipmap.xy3);
            holder.ivMorningType.setVisibility(View.VISIBLE);
        }else{
            holder.ivMorningType.setVisibility(View.GONE);
        }

        if(data.getNightBloodTypeSecondName().equals("偏低")||data.getNightBloodTypeSecondName().equals("正常")){
            holder.ivNightType.setImageResource(R.mipmap.xy1);
            holder.ivNightType.setVisibility(View.VISIBLE);
        }else if(data.getNightBloodTypeSecondName().equals("轻度")||data.getNightBloodTypeSecondName().equals("中度")){
            holder.ivNightType.setImageResource(R.mipmap.xy2);
            holder.ivNightType.setVisibility(View.VISIBLE);
        }else if(data.getNightBloodTypeSecondName().equals("重度")){
            holder.ivNightType.setImageResource(R.mipmap.xy3);
            holder.ivNightType.setVisibility(View.VISIBLE);
        }else{
            holder.ivNightType.setVisibility(View.GONE);
        }


        if(mOnItemClickListener!=null){
            holder.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position,holder);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivArrow;
        private LinearLayout llDetails;
        private TextView tvDayNum, tvMorningNum, tvNightNum;
        private TextView tvGroupTime;
        private TextView tvDayType,tvMorningType,tvNightType;
        private ImageView ivDayType,ivMorningType,ivNightType;
        private RecyclerView rvBloodListDetail;
        private LinearLayout llRoot;

        public ViewHolder(View view) {
            super(view);
            ivArrow = view.findViewById(R.id.iv_arrow);
            llDetails = view.findViewById(R.id.ll_details);
            tvDayNum = view.findViewById(R.id.tv_day_num);
            tvMorningNum = view.findViewById(R.id.tv_morning_num);
            tvNightNum = view.findViewById(R.id.tv_night_num);
            tvGroupTime = view.findViewById(R.id.tv_group_time);
            tvDayType = view.findViewById(R.id.tv_day_type);
            tvMorningType = view.findViewById(R.id.tv_morning_type);
            tvNightType = view.findViewById(R.id.tv_night_type);
            ivDayType = view.findViewById(R.id.iv_day_type);
            ivMorningType = view.findViewById(R.id.iv_day_morning);
            ivNightType = view.findViewById(R.id.iv_night_type);
            rvBloodListDetail = view.findViewById(R.id.rv_log_list);
            llRoot = view.findViewById(R.id.ll_root);
        }
    }

    //重新设置数据
    public void setDate(List<ProvidePatientConditionBloodPressureGroup> list) {
        datas = list;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position,ViewHolder holder);
    }



}
