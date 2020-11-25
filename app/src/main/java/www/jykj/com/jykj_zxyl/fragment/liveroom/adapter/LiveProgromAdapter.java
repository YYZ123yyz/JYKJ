package www.jykj.com.jykj_zxyl.fragment.liveroom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UpLoadLiveProgromBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 16:23
 */
public class LiveProgromAdapter extends RecyclerView.Adapter<LiveProgromAdapter.ViewHolder> {
    private Context mContext;
    private List<UpLoadLiveProgromBean> datas;

    public LiveProgromAdapter(List<UpLoadLiveProgromBean> list, Context context) {
        mContext = context;
        datas = new ArrayList<>();
        datas = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_live_protrom_detial,
                parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    public void setData(List<UpLoadLiveProgromBean> datas){
        this.datas=datas;
        this.notifyDataSetChanged();
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String content = datas.get(position).getContent();
        int titleNumber=position+1;
        holder.tvLiveProgromContent.setText(String.format("%d %s", titleNumber, content));
        if (position==0) {
            holder.rlContentRoot.setBackgroundResource(R.drawable.bg_round_eff6fe_top_5);
        }else{
            if (position==datas.size()-1) {
                if ((position+1)%2==0) {
                    holder.rlContentRoot.setBackgroundResource(R.drawable.bg_round_f1f1f1_buttom_5);
                }else{
                    holder.rlContentRoot.setBackgroundResource(R.drawable.bg_round_eff6fe_buttom_5);
                }
            }else{
                if ((position+1)%2==0) {
                    holder.rlContentRoot.setBackgroundColor(ContextCompat.getColor(mContext
                            ,R.color.color_f1f1f1));
                }else{
                    holder.rlContentRoot.setBackgroundColor(ContextCompat.getColor(mContext
                            ,R.color.color_EFF6FE));
                }
            }

        }



    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlContentRoot;
        private TextView tvLiveProgromContent;
        public ViewHolder(View view) {
            super(view);
            rlContentRoot=view.findViewById(R.id.rl_content_root);
            tvLiveProgromContent=view.findViewById(R.id.tv_live_progrom_content);

        }
    }
}

