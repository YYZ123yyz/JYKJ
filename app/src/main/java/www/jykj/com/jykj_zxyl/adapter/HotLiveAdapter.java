package www.jykj.com.jykj_zxyl.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        HotLiveInfo parinfo = datas.get(i);
        viewHolder.live_catalog.setText("类目:"+StrUtils.defaulObjToStr(parinfo.getClassName()));
        viewHolder.live_desc.setText(StrUtils.defaulObjToStr(parinfo.getAttrName()));
        viewHolder.live_price.setText(StrUtils.defaulObjToStr(parinfo.getExtendBroadcastPriceShow()));
        viewHolder.live_title.setText(StrUtils.defaulObjToStr(parinfo.getBroadcastTitle()));
        viewHolder.watch_num.setText("观看人数："+StrUtils.defaulObjToStr(parinfo.getExtendBroadcastWatchNum()));
        if(StrUtils.defaulObjToStr(parinfo.getBroadcastCoverImgUrl()).length()>0){
            Glide.with(viewHolder.hot_live_cover.getContext()).load(parinfo.getBroadcastCoverImgUrl()).into(viewHolder.hot_live_cover);
        }
        viewHolder.ll_root.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myListener.onClick(i,v);
            }
        });
        viewHolder.ll_root.setOnLongClickListener(new View.OnLongClickListener(){
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
        private ImageView hot_live_cover;
        private ImageView play_live_btn;
        private TextView live_title;
        private TextView live_desc;
        private TextView live_catalog;
        private TextView watch_num;
        private TextView live_price;
        private LinearLayout ll_root;
        public ViewHolder(View view){
            super(view);
            hot_live_cover = view.findViewById(R.id.hot_live_cover);
            play_live_btn = view.findViewById(R.id.play_live_btn);
            live_title = view.findViewById(R.id.live_title);
            live_desc = view.findViewById(R.id.live_desc);
            live_catalog = view.findViewById(R.id.live_catalog);
            watch_num = view.findViewById(R.id.watch_num);
            live_price = view.findViewById(R.id.live_price);
            ll_root=view.findViewById(R.id.ll_root);
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
