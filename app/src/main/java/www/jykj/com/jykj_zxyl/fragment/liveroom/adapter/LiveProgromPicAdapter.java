package www.jykj.com.jykj_zxyl.fragment.liveroom.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 17:08
 */
public class LiveProgromPicAdapter extends RecyclerView.Adapter<LiveProgromPicAdapter.ViewHolder> {
    private Context mContext;
    private  List<LiveProtromDetialBean.ImageListBean> datas;
    private int mWidth;
    public LiveProgromPicAdapter(List<LiveProtromDetialBean.ImageListBean> list, Context context) {
        mContext = context;
        datas = new ArrayList<>();
        datas = list;
        Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        mWidth = defaultDisplay.getWidth();
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
                .apply(RequestOptions.placeholderOf(R.mipmap.docter_heard)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable,
                                                   Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {

                        BitmapDrawable bd = (BitmapDrawable) drawable;
                        Bitmap bm= bd.getBitmap();
                        int h = bm.getHeight();
                        ViewGroup.LayoutParams para= holder.ivLiveProgromPic.getLayoutParams();
                        para.height =h ;
                        para.width = mWidth;
                        holder.ivLiveProgromPic.setLayoutParams(para);
                        ViewGroup.LayoutParams layoutParams = holder.mLlRootView.getLayoutParams();
                        layoutParams.width=mWidth;
                        layoutParams.height=h;
                        holder.mLlRootView.setLayoutParams(layoutParams);
                        return false;
                    }
                })
                .into(holder.ivLiveProgromPic);




    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLiveProgromPic;
        private LinearLayout mLlRootView;
        public ViewHolder(View view) {
            super(view);
            mLlRootView=view.findViewById(R.id.ll_root_view);
            ivLiveProgromPic = view.findViewById(R.id.iv_live_progrom_pic);

        }
    }
}


