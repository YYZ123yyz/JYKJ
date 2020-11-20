package www.jykj.com.jykj_zxyl.fragment.liveroom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UpLoadLiveProgromBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 17:08
 */
public class LiveProgromPicAdapter extends RecyclerView.Adapter<LiveProgromPicAdapter.ViewHolder> {
    private Context mContext;
    private  List<LiveProtromDetialBean.ImageListBean> datas;

    public LiveProgromPicAdapter(List<LiveProtromDetialBean.ImageListBean> list, Context context) {
        mContext = context;
        datas = new ArrayList<>();
        datas = list;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_live_photo_detial,
                        parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    public void setData(List<LiveProtromDetialBean.ImageListBean> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgUrl = datas.get(position).getImgUrl();
        Glide.with(mContext).load(imgUrl)
                .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.ivLiveProgromPic);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLiveProgromPic;
        public ViewHolder(View view) {
            super(view);
            ivLiveProgromPic = view.findViewById(R.id.iv_live_progrom_pic);

        }
    }
}


