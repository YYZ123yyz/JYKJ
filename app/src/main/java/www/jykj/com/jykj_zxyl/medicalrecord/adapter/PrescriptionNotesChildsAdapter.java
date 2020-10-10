package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-19 17:37
 */
public class PrescriptionNotesChildsAdapter extends  RecyclerView.Adapter
        <PrescriptionNotesChildsAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionNotesBean.PrescribeInfoBean> list;
    public List<PrescriptionNotesBean.PrescribeInfoBean> getList() {
        return list;
    }
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public PrescriptionNotesChildsAdapter(Context mContext, List<PrescriptionNotesBean.PrescribeInfoBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription_notes_child, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrescriptionNotesBean.PrescribeInfoBean prescribeInfoBean = list.get(position);
        String drugName = prescribeInfoBean.getDrugName();
        String prescribeTypeName = prescribeInfoBean.getPrescribeTypeName();
        holder.mTvMedicalName.setText(drugName);
        holder.mTvPrescriptionSource.setText(prescribeTypeName);
        String drugAmountName = prescribeInfoBean.getDrugAmountName();
        holder.mTvPurchaseName.setText(String.format("购买数量：%s", drugAmountName));
        double drugMoneys = prescribeInfoBean.getDrugMoneys();
        holder.mTvPriceValue.setText(drugMoneys+"");
        String useNumName = prescribeInfoBean.getUseNumName();
        holder.mTvMedicalNum.setText(String.format("用药数量：%s", useNumName));
        String useFrequencyName = prescribeInfoBean.getUseFrequencyName();
        holder.mTvTakeMedicinalRate.setText(String.format("服药频率：%s", useFrequencyName));
        int useCycle = prescribeInfoBean.getUseCycle();
        holder.mTvTakeMedicinalCycle.setText(String.format("%d天", useCycle));
        String useDesc = prescribeInfoBean.getUseDesc();
        holder.mTvTakeMedicinalRemind.setText(useDesc);
        holder.mLLRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickItem(position);
                }
            }
        });
        if(position==list.size()-1){
            holder.mIvDeleteBtn.setVisibility(View.VISIBLE);
        }else{
            holder.mIvDeleteBtn.setVisibility(View.GONE);
        }
        holder.mIvDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickDeleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMedicalName;
        private TextView mTvPrescriptionSource;
        private TextView mTvPurchaseName;
        private TextView mTvPriceValue;
        private TextView mTvMedicalNum;
        private TextView mTvTakeMedicinalRate;
        private TextView mTvTakeMedicinalCycle;
        private TextView mTvTakeMedicinalRemind;
        private LinearLayout mLLRootView;
        private ImageView mIvDeleteBtn;
        public ViewHolder(View view) {
            super(view);
            mTvMedicalName=view.findViewById(R.id.tv_medical_name);
            mTvPrescriptionSource=view.findViewById(R.id.tv_prescription_source);
            mTvPurchaseName=view.findViewById(R.id.tv_purchase_name);
            mTvPriceValue=view.findViewById(R.id.tv_price_vlaue);
            mTvMedicalNum=view.findViewById(R.id.tv_medical_num);
            mTvTakeMedicinalRate=view.findViewById(R.id.tv_take_medicinal_rate);
            mTvTakeMedicinalCycle=view.findViewById(R.id.tv_take_medicinal_cycle);
            mTvTakeMedicinalRemind=view.findViewById(R.id.tv_take_medicinal_remind);
            mLLRootView=view.findViewById(R.id.ll_root_view);
            mIvDeleteBtn=view.findViewById(R.id.iv_delete_btn);

        }
    }






    public void setData(List<PrescriptionNotesBean.PrescribeInfoBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {

        void onClickItem(int pos);

        void onClickDeleteItem(int pos);
    }
}

