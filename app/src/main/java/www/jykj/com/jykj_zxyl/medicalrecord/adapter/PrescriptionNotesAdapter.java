package www.jykj.com.jykj_zxyl.medicalrecord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class PrescriptionNotesAdapter extends  RecyclerView.Adapter
        <PrescriptionNotesAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionNotesBean> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public List<PrescriptionNotesBean> getList() {
        return list;
    }

    public PrescriptionNotesAdapter(Context mContext, List<PrescriptionNotesBean> list) {
        this.context = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prescription_notes, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mRvList.setLayoutManager(new LinearLayoutManager(context));
        PrescriptionNotesBean prescriptionNotesBean = list.get(position);
        PrescriptionNotesChildsAdapter prescriptionNotesChildsAdapter
                =new PrescriptionNotesChildsAdapter(context,prescriptionNotesBean.getPrescribeInfo());
        prescriptionNotesChildsAdapter.setData(prescriptionNotesBean.getPrescribeInfo());
        holder.mRvList.setAdapter(prescriptionNotesChildsAdapter);
        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickDeleteItem(position);
                }
            }
        });
        holder.mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickUpdateItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       private RecyclerView mRvList;
       //private TextView mTvDelete;
       private Button mBtnUpdate;
       private Button mBtnDelete;
        public ViewHolder(View view) {
            super(view);
            mRvList=view.findViewById(R.id.rv_list);
            //mTvDelete=view.findViewById(R.id.tv_delete);
            mBtnUpdate=view.findViewById(R.id.btn_update);
            mBtnDelete=view.findViewById(R.id.btn_delete);
        }
    }






    public void setData(List<PrescriptionNotesBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);

        void onClickDeleteItem(int pos);

        void onClickUpdateItem(int pos);
    }
}

