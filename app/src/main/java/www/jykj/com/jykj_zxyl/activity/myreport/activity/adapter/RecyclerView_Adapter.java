package www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.CommitBean;
import www.jykj.com.jykj_zxyl.adapter.ApplicationAuditRecycleAdapter;


public class RecyclerView_Adapter  extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    private List<CommitBean> datas;
    private OnItemClickListener mOnItemClickListener;
    public RecyclerView_Adapter(List<CommitBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inquire, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            if(datas!=null){
                Log.e("TAG", "onBindViewHolder: "+datas.size() );
                CommitBean commitBean = datas.get(i);
                if(commitBean.getDepartmentName()==null){
                    commitBean.setStatus(2);
                    viewHolder.item_name.setText(commitBean.getDoctorName());
                }else{
                    commitBean.setStatus(1);
                    viewHolder.item_name.setText(commitBean.getDepartmentName());
                }
                viewHolder.tv_price.setText("￥"+commitBean.getTotalDayAmount()+"元");

                if (mOnItemClickListener != null){
                    viewHolder.item_lin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(i);
                        }
                    });
                }

            }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //重新设置数据
    public void setDate(List<CommitBean> list) {
        datas = list;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView item_name,tv_price;
        private LinearLayout item_lin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            item_lin = itemView.findViewById(R.id.item_lin);
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

}
