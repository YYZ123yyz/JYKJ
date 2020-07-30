package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

import java.util.ArrayList;
import java.util.List;

public class HeadImageViewRecycleAdapter extends RecyclerView.Adapter<HeadImageViewRecycleAdapter.ViewHolder>{
    public List<String> datas = new ArrayList<>();
    private JYKJApplication mApp;

    public HeadImageViewRecycleAdapter(List<String> datas, JYKJApplication mApp) {
        this.datas = datas;
        this.mApp = mApp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.head_recycleview_imageview,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Glide.with(viewHolder.iv_head_img.getContext()).load(datas.get(position))
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.iv_head_img);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 重置数据
     * @param
     */
    public void setDate(List<String> pardatas) {
        this.datas = pardatas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_head_img;
        public ViewHolder(View view){
            super(view);
            iv_head_img = (ImageView)view.findViewById(R.id.iv_head_img);
        }
    }
}
