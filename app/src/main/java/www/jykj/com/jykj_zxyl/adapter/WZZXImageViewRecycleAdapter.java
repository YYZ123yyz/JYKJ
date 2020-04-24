package www.jykj.com.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import entity.wdzs.ProvideBasicsImg;
import www.jykj.com.jykj_zxyl.R;


public class WZZXImageViewRecycleAdapter extends RecyclerView.Adapter<WZZXImageViewRecycleAdapter.ViewHolder> {
    public List<ProvideBasicsImg> datas = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;



        public WZZXImageViewRecycleAdapter(List<ProvideBasicsImg> list,Context context){
            datas = list;
            mContext = context;
        }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycleview_imageview,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
       }
        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            if (datas.get(position).getImgUrl() != null && !"".equals(datas.get(position).getImgUrl()))
            {
                try {
                    int avatarResId = Integer.parseInt(datas.get(position).getImgUrl());
                    Glide.with(mContext).load(avatarResId).into(viewHolder.mImageView);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(datas.get(position).getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(viewHolder.mImageView);
                }
            }
            if (mOnItemClickListener != null)
            {
                viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onClick(position);

                    }
                });

                viewHolder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickListener.onLongClick(position);
                        return false;
                    }
                });
            }
        }
        //获取数据的数量
        @Override
        public int getItemCount(){
            return datas.size();
        }

    /**
     * 重置数据
     * @param mPhotoInfos
     */
    public void setDate(List<ProvideBasicsImg> mPhotoInfos) {
        datas.clear();
        datas.addAll(mPhotoInfos);
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView mImageView;            //

            public ViewHolder(View view){
                super(view);
                mImageView = (ImageView)view.findViewById(R.id.iv_itempublishActivity_addPicture);
            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}