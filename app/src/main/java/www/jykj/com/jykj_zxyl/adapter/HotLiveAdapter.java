package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import entity.liveroom.HotLiveInfo;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.util.StrUtils;

import java.util.List;

public class HotLiveAdapter extends RecyclerView.Adapter<HotLiveAdapter.ViewHolder>{
    List<HotLiveInfo> datas;
    OnHotliveItemClickListener myListener;
    public HotLiveAdapter(List<HotLiveInfo> datas){
        this.datas = datas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hot_live_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HotLiveInfo parinfo = datas.get(i);
        viewHolder.live_catalog.setText("类目:"+StrUtils.defaulObjToStr(parinfo.getBroadcastTypeName()));
        viewHolder.live_desc.setText(StrUtils.defaulObjToStr(parinfo.getKeywordsName()));
        viewHolder.live_price.setText(StrUtils.defaulObjToStr(parinfo.getExtendBroadcastPriceShow()));
        viewHolder.live_title.setText(StrUtils.defaulObjToStr(parinfo.getBroadcastTitle()));
        viewHolder.watch_num.setText(StrUtils.defaulObjToStr(parinfo.getExtendBroadcastWatchNum()));
        viewHolder.play_live_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myListener.onClick(i,v);
            }
        });
        viewHolder.play_live_btn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                myListener.onLongClick(i,v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView play_live_btn;
        public TextView live_title;
        public TextView live_desc;
        public TextView live_catalog;
        public TextView watch_num;
        public TextView live_price;
        public ViewHolder(View view){
            super(view);
            play_live_btn = view.findViewById(R.id.play_live_btn);
            live_title = view.findViewById(R.id.live_title);
            live_desc = view.findViewById(R.id.live_desc);
            live_catalog = view.findViewById(R.id.live_catalog);
            watch_num = view.findViewById(R.id.watch_num);
            live_price = view.findViewById(R.id.live_price);
        }

    }

    public OnHotliveItemClickListener getMyListener() {
        return myListener;
    }

    public void setMyListener(OnHotliveItemClickListener myListener) {
        this.myListener = myListener;
    }

    public interface OnHotliveItemClickListener{
        void onClick(int position,View view);
        void onLongClick(int position,View view);
    }
    public void setData(List<HotLiveInfo> datas){
        this.datas = datas;
    }
}
