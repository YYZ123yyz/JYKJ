package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.jykj.com.jykj_zxyl.R;

/**
 * 患者管理用药信息适配器
 */
public class HZGL_YYXX_RecycleAdapter extends RecyclerView.Adapter<HZGL_YYXX_RecycleAdapter.ViewHolder> {
    public List<HZIfno> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;           //用户资料点击事件


    private Context mContext;


    public HZGL_YYXX_RecycleAdapter(List<HZIfno> list, Context context) {
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<HZIfno> list) {
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activityhzgl_yyxx, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        HZIfno hzIfno = datas.get(position);
        viewHolder.tvDrugName.setText(hzIfno.getDrugName());
        if(hzIfno.getFlagTakingMedicineUserType()==1) {
            viewHolder.tvUserType.setText("由患者本人添加");
        }else{
            viewHolder.tvUserType.setText("由患者亲属添加");
        }
        if(hzIfno.getFlagTakingMedicine()==1){
            viewHolder.tvMedicine.setText("未服用");
        }else {
            viewHolder.tvMedicine.setText("已服用");
        }

        viewHolder.tvUseFrequency.setText(hzIfno.getUseNum()+"/"+hzIfno.getUseUnit());


    }

    //获取数据的数量
    @Override
    public int getItemCount() {

        return datas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        private TextView tvDrugName;
        private TextView tvUserType;
        private TextView tvMedicine;
        private TextView tvTime;
        private TextView tvUseFrequency;


        public ViewHolder(View view) {
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemFragmentHZGL_hzInfoLayout);
            tvDrugName = (TextView)view.findViewById(R.id.tv_drug_name);
            tvUserType = (TextView)view.findViewById(R.id.tv_uesr_type);
            tvMedicine = (TextView)view.findViewById(R.id.tv_medicine);
            tvTime = (TextView)view.findViewById(R.id.tv_time);
            tvUseFrequency = (TextView)view.findViewById(R.id.tv_use_frequency);
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