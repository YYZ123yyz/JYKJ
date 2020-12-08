 package www.jykj.com.jykj_zxyl.live.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import orcameralib.util.BitmapUtil;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.myself.Photo_Info;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import yyz_exploit.Utils.StrUtils;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public List<Photo_Info> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private JYKJApplication mApp;
    private boolean mCanClick = true;

    public ImageAdapter(List<Photo_Info> list, JYKJApplication app) {
        datas = new ArrayList<>();
        datas.addAll(list);
        mApp = app;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycleview_imageview, viewGroup, false);
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
        String status = datas.get(position).getStatus();
        if(!TextUtils.isEmpty(status)){
            if(status.equals("1")){
                Log.e("TAG", "onBindViewHolder: "+status );
                viewHolder.delete_img.setVisibility(View.VISIBLE);
                if ("ADDPHOTO".equals(datas.get(position).getPhotoID())) {
                    //   viewHolder.mImageView.setBackgroundResource(R.mipmap.pz);
                    Log.i("执行", "执行Add:" + position);
                    viewHolder.delete_img.setVisibility(View.GONE);
                    viewHolder.img_view.setVisibility(View.VISIBLE);
                } else if (datas.get(position).getPhoto() != null) {
                    viewHolder.mImageView.setImageBitmap(BitmapUtil.stringtoBitmap(datas.get(position).getPhoto()));
                    Log.i("执行", "执行:" + position);
                    viewHolder.delete_img.setVisibility(View.VISIBLE);
                    viewHolder.img_view.setVisibility(View.GONE);
                } else if (StrUtils.defaultStr(datas.get(position).getPhotoUrl()).length() > 0) {
                    Glide.with(viewHolder.mImageView.getContext()).load(datas.get(position).getPhotoUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(viewHolder.mImageView);
                    viewHolder.img_view.setVisibility(View.GONE);

                }

            }
            else{
                viewHolder.delete_img.setVisibility(View.GONE);
                viewHolder.img_view.setVisibility(View.GONE);
                Log.e("TAG", "onBindViewHolder:vvvvvv "+status );
            }
        }



        if (mOnItemClickListener != null) {
            viewHolder.delete_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCanClick){
                        mOnItemClickListener.onClick(position);
                    }

                }
            });

            viewHolder.delete_img.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    if (mCanClick){
                        mOnItemClickListener.onLongClick(position);
                    }
                    return false;
                }
            });
        }
        viewHolder.delete_img.setClickable(mCanClick);
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
    public void setDate(List<Photo_Info> mPhotoInfos) {
        datas.clear();
        datas.addAll(mPhotoInfos);
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView,delete_img,img_view;            //

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.iv_itempublishActivity_addPicture);
            delete_img = (ImageView) view.findViewById(R.id.iv_delete_btn);
            img_view = (ImageView) view.findViewById(R.id.img_view);

        }
    }


    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setCanClick(boolean canClick) {
        this.mCanClick = canClick;
    }
}