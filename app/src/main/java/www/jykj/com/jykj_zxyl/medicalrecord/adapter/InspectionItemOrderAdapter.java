package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemDataBean;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:检查检验项目
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 14:49
 */
public class InspectionItemOrderAdapter extends RecyclerView.Adapter
        <InspectionItemOrderAdapter.ViewHolder> {
    private Context context;
    private List<InspectionItemDataBean> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public InspectionItemOrderAdapter(Context mContext, List<InspectionItemDataBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspection_project, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspectionItemDataBean itemDataBean = list.get(position);
        String inspectionName = itemDataBean.getInspectionName();
        holder.mTvInspectProject.setText(StringUtils.isNotEmpty(inspectionName) ? inspectionName : "未填写");
        String positionName = itemDataBean.getPositionName();
        holder.mTvInspectionPosition.setText(StringUtils.isNotEmpty(positionName) ? positionName : "未填写");
        String inspectionGrade = itemDataBean.getInspectionGradeName();
        holder.mTvInspectionGrade.setText(StringUtils.isNotEmpty(inspectionGrade) ? inspectionGrade : "未填写");
        String inspectionTime = itemDataBean.getInspectionTime();
        holder.mTvInspectionTime.setText(StringUtils.isNotEmpty(inspectionTime) ? inspectionTime : "未填写");
        String inspectionPurpose = itemDataBean.getInspectionPurpose();
        holder.edInputContent.setText(inspectionPurpose);
        holder.rlInspectionProject.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickInspectionProject(position);
            }
        });
        holder.rlInspectionPosition.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickInspectionPosition(position);
            }
        });
        holder.rlInspectionGrade.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickInspectionGrade(position);
            }
        });
        holder.rlInspectionTime.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClickInspectionTime(position);
            }
        });
        holder.edInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                list.get(position).setInspectionPurpose(s.toString());
            }
        });
        holder.ivDeleteBtn.setOnClickListener(v -> {
            if (onItemClickListener!=null) {
                onItemClickListener.onClickDeleteItem(position);
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvInspectProject;
        private TextView mTvInspectionPosition;
        private TextView mTvInspectionGrade;
        private TextView mTvInspectionTime;
        private RelativeLayout rlInspectionProject;
        private RelativeLayout rlInspectionPosition;
        private RelativeLayout rlInspectionGrade;
        private RelativeLayout rlInspectionTime;
        private EditText edInputContent;
        private ImageView ivDeleteBtn;

        public ViewHolder(View view) {
            super(view);
            mTvInspectionPosition=view.findViewById(R.id.tv_inspection_position);
            mTvInspectProject = view.findViewById(R.id.tv_inspect_project);
            mTvInspectionGrade=view.findViewById(R.id.tv_inspection_grade);
            mTvInspectionTime=view.findViewById(R.id.tv_inspection_time);
            rlInspectionProject = view.findViewById(R.id.rl_inspection_project);
            rlInspectionPosition = view.findViewById(R.id.rl_inspection_position);
            rlInspectionGrade = view.findViewById(R.id.rl_inspection_grade);
            rlInspectionTime = view.findViewById(R.id.rl_inspection_time);
            edInputContent = view.findViewById(R.id.ed_input_content);
            ivDeleteBtn=view.findViewById(R.id.iv_delete_btn);

        }
    }

    public interface OnItemClickListener {

        void onClickItem(int pos);

        void onClickInspectionProject(int pos);

        void onClickInspectionPosition(int pos);

        void onClickInspectionGrade(int pos);

        void onClickInspectionTime(int pos);

        void onClickDeleteItem(int pos);
    }
}

