package www.jykj.com.jykj_zxyl.app_base.base_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;


/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-01 09:40
 */
public class CancelContractAdapter extends RecyclerView.Adapter<CancelContractAdapter.ViewHolder> {

    private List<BaseReasonBean> datas;
    private Context mContext;

    public CancelContractAdapter(List<BaseReasonBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }

    public List<BaseReasonBean> getDatas() {
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_cancel_contract,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTvCancelContractText.setText(datas.get(position).getAttrName());
        if (datas.get(position).isChoosed()) {
            holder.mIvItemChoosed.setImageResource(R.mipmap.bg_item_choosed);
        }else{
            holder.mIvItemChoosed.setImageResource(R.mipmap.bg_item_normal);
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvCancelContractText;
        private ImageView mIvItemChoosed;

        public ViewHolder(View view) {
            super(view);
            mTvCancelContractText = view.findViewById(R.id.tv_cancel_contact_text);
            mIvItemChoosed = view.findViewById(R.id.iv_item_choosed);
        }
    }
}