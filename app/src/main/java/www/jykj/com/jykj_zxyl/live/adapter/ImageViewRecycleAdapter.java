package www.jykj.com.jykj_zxyl.live.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ImageInfoBean;
import www.jykj.com.jykj_zxyl.util.BitmapUtil;
import yyz_exploit.Utils.StrUtils;


public class ImageViewRecycleAdapter extends RecyclerView.Adapter<ImageViewRecycleAdapter.ViewHolder> {
    public List<ImageInfoBean> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private boolean mCanClick = true;

    public ImageViewRecycleAdapter(List<ImageInfoBean> list) {
        datas = new ArrayList<>();
        datas.addAll(list);
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recycleview_imageview, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        /**
         * 强制关闭复用，否则出现数据混乱
         */
        viewHolder.setIsRecyclable(false);
        if ("ADDPHOTO".equals(datas.get(position).getPhotoID())) {
            viewHolder.mImageView.setBackgroundResource(R.mipmap.bg_addphoto);
            viewHolder.mIvDeleteBtn.setVisibility(View.GONE);
            Log.i("执行", "执行Add:" + position);
        } else if (datas.get(position).getPhoto() != null) {
            viewHolder.mIvDeleteBtn.setVisibility(View.VISIBLE);
            viewHolder.mImageView.setImageBitmap(BitmapUtil.stringtoBitmap(datas.get(position).getPhoto()));
            Log.i("执行", "执行:" + position);
        } else if (StrUtils.defaultStr(datas.get(position).getPhotoUrl()).length() > 0) {
            viewHolder.mIvDeleteBtn.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.mImageView.getContext()).load(datas.get(position).getPhotoUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(viewHolder.mImageView);

        }


        if (mOnItemClickListener != null) {
            viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCanClick) {
                        mOnItemClickListener.onClick(position);
                    }

                }
            });

            viewHolder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    if (mCanClick) {
                        mOnItemClickListener.onLongClick(position);
                    }
                    return false;
                }
            });
        }
        viewHolder.mImageView.setClickable(mCanClick);
        viewHolder.mIvDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null) {
                    mOnItemClickListener.onClickDelete(position);
                }
            }
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 重置数据
     *
     * @param mPhotoInfos
     */
    public void setDate(List<ImageInfoBean> mPhotoInfos) {
        datas.clear();
        datas.addAll(mPhotoInfos);
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;            //
        public ImageView mIvDeleteBtn;
        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_itempublishActivity_addPicture);
            mIvDeleteBtn=view.findViewById(R.id.iv_delete_btn);
        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);

        void onClickDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setCanClick(boolean canClick) {
        this.mCanClick = canClick;
    }
}