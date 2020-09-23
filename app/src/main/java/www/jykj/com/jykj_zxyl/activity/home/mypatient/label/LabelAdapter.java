package www.jykj.com.jykj_zxyl.activity.home.mypatient.label;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.List;

import entity.patientInfo.ProvidePatientLabel;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientLabelBean;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder>  {
    private List<ProvidePatientLabelBean> datas;

    public LabelAdapter(List<ProvidePatientLabelBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public LabelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_patientlaber_patientlabers,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LabelAdapter.ViewHolder viewHolder, int i) {
        ProvidePatientLabelBean providePatientLabelBean = datas.get(i);
        if(providePatientLabelBean!=null){
            viewHolder.tv_name.setText(providePatientLabelBean.getCreateMan());
            String dates = DateUtils.stampToDates(providePatientLabelBean.getCreateDate());
            viewHolder.tv_time.setText(dates);
            viewHolder.tv_status.setText(providePatientLabelBean.getUserLabelSecondName());
            Integer flagUseState = providePatientLabelBean.getFlagUseState();
            if(flagUseState==0){
                viewHolder.iv_stamp_icon.setImageResource(R.mipmap.ls);
            }else if(flagUseState==1){
                viewHolder.iv_stamp_icon.setImageResource(R.mipmap.dq);
            }

        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    //重新设置数据
    public void setDate(List<ProvidePatientLabelBean> list){
        datas = list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_status;
        private ImageView    iv_stamp_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_status=itemView.findViewById(R.id.tv_status);
            iv_stamp_icon=itemView.findViewById(R.id.iv_stamp_icon);
        }
    }
}
