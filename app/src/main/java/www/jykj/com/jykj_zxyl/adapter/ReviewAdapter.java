package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import entity.patientInfo.ProvideBindingDoctorPatientApply;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.DateUtils;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public List<ProvideBindingDoctorPatientApply> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private boolean showCheckBox;

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    public ReviewAdapter(List<ProvideBindingDoctorPatientApply> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideBindingDoctorPatientApply> list) {
        datas = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activityapplicationaudit_applicationlist, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mCbItem.setTag(position);

        //判断当前checkbox的状态
        if (showCheckBox) {
            if(datas.get(position).getFlagApplyState() == 0||datas.get(position).getFlagApplyState() == 1) {
                viewHolder.mCbItem.setVisibility(View.VISIBLE);
                //防止显示错乱
                viewHolder.mCbItem.setChecked(mCheckStates.get(position, false));
            }else{
                viewHolder.mCbItem.setVisibility(View.GONE);
                viewHolder.mCbItem.setChecked(false);
            }
        } else {
            viewHolder.mCbItem.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            viewHolder.mCbItem.setChecked(false);
            mCheckStates.clear();
        }
        viewHolder.tvUserName.setText(datas.get(position).getPatientUserName());

        viewHolder.tvAge.setText("" + DateUtils.getAgeFromBirthDate(datas.get(position).getPatientBirthday()));

        if (datas.get(position).getPatientGender() == 1) {
            viewHolder.tvAge.setBackgroundResource(R.mipmap.man);
        }
        if (datas.get(position).getPatientGender() == 2) {
            viewHolder.tvAge.setBackgroundResource(R.mipmap.women);
        }

        viewHolder.tvDate.setText(DateUtils.stampToDate(datas.get(position).getApplyDate()));

        if (datas.get(position).getFlagApplyState() == 0) {
            viewHolder.tvState.setText("待审核");
            viewHolder.ivArrow.setVisibility(View.VISIBLE);
            viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.textColor_vo));
        }
        if (datas.get(position).getFlagApplyState() == 1) {
            viewHolder.tvState.setText("未通过");
            viewHolder.ivArrow.setVisibility(View.INVISIBLE);
            viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
        }
        if (datas.get(position).getFlagApplyState() == 2) {
            viewHolder.tvState.setText("已过期");
            viewHolder.ivArrow.setVisibility(View.INVISIBLE);
            viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.textColor_vo));
        }
        if (datas.get(position).getFlagApplyState() == 3) {
            viewHolder.tvState.setText("通过");
            viewHolder.ivArrow.setVisibility(View.INVISIBLE);
            viewHolder.tvState.setTextColor(mContext.getResources().getColor(R.color.tabColor_press));
        }

        viewHolder.tvContent.setText(datas.get(position).getPatientLabelName());
        if (mOnItemClickListener != null) {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (showCheckBox) {
                        viewHolder.mCbItem.setChecked(!viewHolder.mCbItem.isChecked());
                    }
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        //对checkbox的监听 保存选择状态 防止checkbox显示错乱
        viewHolder.mCbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int) compoundButton.getTag();
                if (b) {
                    mCheckStates.put(pos, true);
                } else {
                    mCheckStates.delete(pos);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvDate;
        private TextView tvUserName;
        private TextView tvState;
        private TextView tvAge;
        private ImageView ivArrow;
        private TextView tvContent;
        private LinearLayout mClickLinearLayout;
        private CheckBox mCbItem;

        public ViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.iv_userhead);
            tvDate = view.findViewById(R.id.tv_date);
            tvUserName = view.findViewById(R.id.tv_username);
            tvState = view.findViewById(R.id.tv_state);
            tvAge = view.findViewById(R.id.tv_age);
            ivArrow = view.findViewById(R.id.iv_arrow);
            tvContent = view.findViewById(R.id.tv_content);
            mClickLinearLayout = view.findViewById(R.id.li_clickLayout);
            mCbItem = (CheckBox) itemView.findViewById(R.id.cb_item);
        }

    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
