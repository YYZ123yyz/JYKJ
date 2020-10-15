package www.jykj.com.jykj_zxyl.personal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DiagnosisReplayBean;
import www.jykj.com.jykj_zxyl.util.DateUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-15 19:13
 */
public class DiagnosisReplayAdapter  extends RecyclerView.Adapter<DiagnosisReplayAdapter.ViewHolder> {
    private Context context;
    private List<DiagnosisReplayBean.InteractPatientMessageActiveListBean> list;


    public DiagnosisReplayAdapter(Context mContext,
                                  List<DiagnosisReplayBean.InteractPatientMessageActiveListBean> list) {
        this.list=list;
        this.context=mContext;
    }

    public void setData(List<DiagnosisReplayBean.InteractPatientMessageActiveListBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_replay, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        DiagnosisReplayBean.InteractPatientMessageActiveListBean activeListBean = list.get(i);
        holder.tvReypayType.setText(activeListBean.getMessageTypeName());
        holder.tvReplayDate.setText(DateUtils.stampToDate(activeListBean.getDoctorReplyDate()));
        holder.tvMessageType.setText(activeListBean.getFlagDoctorReplyTypeName());
        holder.tvReplayContent.setText(activeListBean.getDoctorReplyContent());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvReypayType;
        private TextView tvReplayDate;
        private TextView tvMessageType;
        private TextView tvReplayContent;
        public ViewHolder(View view) {
            super(view);

            tvReypayType= view.findViewById(R.id.tv_replay_type);
            tvReplayDate=view.findViewById(R.id.tv_replay_date);
            tvMessageType=view.findViewById(R.id.tv_message_type);
            tvReplayContent=view.findViewById(R.id.tv_replay_content);
        }
    }



}

