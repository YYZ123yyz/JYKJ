package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionMedicinalItemDataBean;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:处方药品适配器
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 15:54
 */
public class PrescriptionMedicinalAdapter extends  RecyclerView.Adapter
        <PrescriptionMedicinalAdapter.ViewHolder> {

    private Context context;
    private List<PrescriptionMedicinalItemDataBean> list;
    private OnClickItemListener onClickItemListener;


    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public List<PrescriptionMedicinalItemDataBean> getList() {
        return list;
    }

    public PrescriptionMedicinalAdapter(Context mContext, List<PrescriptionMedicinalItemDataBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription_medicinal, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrescriptionMedicinalItemDataBean itemDataBean = list.get(position);
        String prescriptionTypeName = itemDataBean.getPrescriptionTypeName();
        holder.mTvPrescriptionType.setText(StringUtils.isNotEmpty(prescriptionTypeName)
                ?prescriptionTypeName:"未填写");
        String drugName = itemDataBean.getDrugName();
        holder.mTvMedicinalName.setText(StringUtils.isNotEmpty(drugName)?drugName:"未填写");
        String takeMedicinalRateName = itemDataBean.getTakeMedicinalRateName();
        holder.mTvTakeMedicinalRate.setText(StringUtils.isNotEmpty(takeMedicinalRateName)
                ?takeMedicinalRateName:"未填写");
        holder.mTvUnitName.setText(itemDataBean.getUnitName());
        String takeUnmUnitName = itemDataBean.getUnitName();
        holder.mTvTakeUnmUnit.setText(takeUnmUnitName);
        String drugPack = itemDataBean.getDrugPack();
        holder.mTvUnitName.setText(drugPack);
        holder.mIvDeteteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickDeleteItem(position);
                }
            }
        });
        holder.mRlPrescriptionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickPrescriptionType(position);
                }
            }
        });
        holder.mRlMedicinalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickMedicinalName(position);
                }
            }
        });
        holder.mRlbuyMedicinalNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickBuyMedicinalNum(position);
                }
            }
        });

        holder.mRlTakeMedicinalNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickTakeMedicinalNum(position);
                }
            }
        });
        holder.mRlTakeMedicinalRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickTakeMedicinalRate(position);
                }
            }
        });
        holder.mRlTakeMedicinalCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickTakeMedicinalCycle(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvDeteteBtn;
        private TextView mTvPrescriptionType;
        private TextView mTvMedicinalName;
        private EditText mEdBuyMedicinalNum;
        private EditText mEdTakeMedicinalNum;
        private TextView mTvTakeMedicinalRate;
        private TextView mTvTakeMedicinalCycle;
        private EditText edInputContent;
        private TextView mTvUnitName;
        private TextView mTvTakeUnmUnit;
        private RelativeLayout mRlPrescriptionType;
        private RelativeLayout mRlMedicinalName;
        private RelativeLayout mRlbuyMedicinalNum;
        private RelativeLayout mRlTakeMedicinalNum;
        private RelativeLayout mRlTakeMedicinalRate;
        private RelativeLayout mRlTakeMedicinalCycle;
        public ViewHolder(View view) {
            super(view);
            mIvDeteteBtn=view.findViewById(R.id.iv_delete_btn);
            mTvPrescriptionType = view.findViewById(R.id.tv_prescription_type);
            mTvMedicinalName = view.findViewById(R.id.tv_medicinal_name);
            mEdBuyMedicinalNum=view.findViewById(R.id.ed_buy_medicinal_num);
            mEdTakeMedicinalNum = view.findViewById(R.id.ed_take_medicinal_num);
            mTvTakeMedicinalRate=view.findViewById(R.id.tv_take_medicinal_rate);
            mTvTakeMedicinalCycle=view.findViewById(R.id.tv_take_medicinal_cycle);
            mTvUnitName=view.findViewById(R.id.tv_unit_name);
            mTvTakeUnmUnit=view.findViewById(R.id.tv_take_num_unit);
            edInputContent=view.findViewById(R.id.ed_input_content);
            mRlPrescriptionType=view.findViewById(R.id.rl_prescription_type);
            mRlMedicinalName=view.findViewById(R.id.rl_medicinal_name);
            mRlbuyMedicinalNum=view.findViewById(R.id.rl_buy_medicinal_num);
            mRlTakeMedicinalNum=view.findViewById(R.id.rl_take_medicinal_num);
            mRlTakeMedicinalRate=view.findViewById(R.id.rl_take_medicinal_rate);
            mRlTakeMedicinalCycle=view.findViewById(R.id.rl_take_medicinal_cycle);

        }
    }



    public void setData(List<PrescriptionMedicinalItemDataBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);

        void onClickDeleteItem(int pos);

        void onClickPrescriptionType(int pos);

        void onClickMedicinalName(int pos);

        void onClickBuyMedicinalNum(int pos);

        void onClickTakeMedicinalNum(int pos);

        void onClickTakeMedicinalRate(int pos);

        void onClickTakeMedicinalCycle(int pos);

    }
}

