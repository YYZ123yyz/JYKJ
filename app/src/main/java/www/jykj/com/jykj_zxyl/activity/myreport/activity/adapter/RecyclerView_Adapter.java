package www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.CommitBean;


public class RecyclerView_Adapter  extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolder> {
    private List<CommitBean> datas;

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
               /* departmentName : 科室1
                        * departmentSumMoney : 5000.00*/
                Log.e("TAG", "onBindViewHolder: "+datas.size() );
                CommitBean commitBean = datas.get(i);
                viewHolder.item_name.setText(commitBean.getDepartmentName());
                viewHolder.tv_price.setText("￥"+commitBean.getDepartmentSumMoney()+"元");
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
