package www.jykj.com.jykj_zxyl.fragment.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allin.commonadapter.ViewHolder;
import com.allin.commonadapter.recyclerview.MultiItemRecycleViewAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MultiItemEntity;
import www.jykj.com.jykj_zxyl.app_base.base_view.RoundedRectangleImageView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-12 16:18
 */
public class HealthEducationAdapter extends MultiItemRecycleViewAdapter<MultiItemEntity> {
    OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public HealthEducationAdapter(Context context, List<MultiItemEntity> datas) {
        super(context, datas, new HealthEducationItemType());
    }


    @Override
    public void convert(ViewHolder viewHolder, MultiItemEntity multiItemEntity, int i) {
        HealthEducationBean healthEducationBean = (HealthEducationBean) multiItemEntity;
        int layoutId = viewHolder.getLayoutId();
        if (layoutId == R.layout.item_health_education_video) {
            View layoutRoot = viewHolder.getView(R.id.rl_layout_root);
            RoundedRectangleImageView roundedRectangleImageView
                    = viewHolder.getView(R.id.iv_health_education_icon);
            ImageView ivContentLabel = viewHolder.getView(R.id.iv_content_label);
            TextView tvLabelName = viewHolder.getView(R.id.tv_label_name);
            TextView tvContentTitle = viewHolder.getView(R.id.tv_content_title);
            TextView tvContentMsg = viewHolder.getView(R.id.tv_content_msg);
            TextView tvCategoryName = viewHolder.getView(R.id.tv_category_name);
            TextView tvBrowseNam = viewHolder.getView(R.id.tv_browse_num);
            TextView tvPriceValue = viewHolder.getView(R.id.tv_price_vlaue);
            Glide.with(mContext).load(healthEducationBean.getBroadcastCoverImgUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(roundedRectangleImageView);
            int flagBroadcastState = healthEducationBean.getFlagBroadcastState();
            if (flagBroadcastState == 1) {
                ivContentLabel.setImageResource(R.mipmap.bg_notice_label);
            } else if (flagBroadcastState == 2) {
                ivContentLabel.setImageResource(R.mipmap.bg_hot_seed);
            } else if (flagBroadcastState == 3) {
                ivContentLabel.setImageResource(R.mipmap.bg_hot_seed);
            }
            tvLabelName.setText("视频直播");
            tvContentTitle.setText(healthEducationBean.getBroadcastTitle());
            tvCategoryName.setText(String.format("类目：%s", healthEducationBean.getClassName()));
            tvBrowseNam.setText(String.format("浏览量：%s", healthEducationBean.getShowWatchNum()));
            tvPriceValue.setText(healthEducationBean.getShowExtendBroadcastPrice());
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickItemPos(i);
                    }
                }
            });

        } else if (layoutId == R.layout.item_health_education_audio) {
            View layoutRoot = viewHolder.getView(R.id.rl_layout_root);
            RoundedRectangleImageView roundedRectangleImageView
                    = viewHolder.getView(R.id.iv_health_education_icon);
            ImageView ivContentLabel = viewHolder.getView(R.id.iv_content_label);
            TextView tvLabelName = viewHolder.getView(R.id.tv_label_name);
            TextView tvContentTitle = viewHolder.getView(R.id.tv_content_title);
            TextView tvContentMsg = viewHolder.getView(R.id.tv_content_msg);
            TextView tvCategoryName = viewHolder.getView(R.id.tv_category_name);
            TextView tvBrowseNam = viewHolder.getView(R.id.tv_browse_num);
            TextView tvPriceValue = viewHolder.getView(R.id.tv_price_vlaue);
            Glide.with(mContext).load(healthEducationBean.getBroadcastCoverImgUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(roundedRectangleImageView);
            int flagBroadcastState = healthEducationBean.getFlagBroadcastState();
            if (flagBroadcastState == 1) {
                ivContentLabel.setImageResource(R.mipmap.bg_notice_label);
            } else if (flagBroadcastState == 2) {
                ivContentLabel.setImageResource(R.mipmap.bg_hot_seed);
            } else if (flagBroadcastState == 3) {
                ivContentLabel.setImageResource(R.mipmap.bg_hot_seed);
            }
            tvLabelName.setText("音频直播");
            tvContentTitle.setText(healthEducationBean.getBroadcastTitle());
            tvCategoryName.setText(String.format("类目：%s", healthEducationBean.getClassName()));
            tvBrowseNam.setText(String.format("浏览量：%s", healthEducationBean.getShowWatchNum()));
            tvPriceValue.setText(healthEducationBean.getShowExtendBroadcastPrice());
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickItemPos(i);
                    }
                }
            });

        } else if (layoutId == R.layout.item_health_education_picture_text) {
            View layoutRoot = viewHolder.getView(R.id.rl_layout_root);

            TextView tvContentMsg = viewHolder.getView(R.id.tv_content_msg);
            String imageTextTitle = healthEducationBean.getImageTextTitle();
            tvContentMsg.setText(imageTextTitle);
            TextView tvBrowseNum = viewHolder.getView(R.id.tv_browse_num);
            tvBrowseNum.setText(String.format("%s人看过", healthEducationBean.getShowWatchNum()));
            TextView tvDateTime = viewHolder.getView(R.id.tv_date_time);
            String showBroadcastDate = healthEducationBean.getImageTextCreateDate();
            tvDateTime.setText(showBroadcastDate);
            ImageView ivContentImg = viewHolder.getView(R.id.iv_content_img);
            Glide.with(mContext).load(healthEducationBean.getImageTextCoverImgUrl())
                    .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(ivContentImg);
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemListener!=null) {
                        onClickItemListener.onClickItemPos(i);
                    }
                }
            });

        }

    }

    public interface OnClickItemListener{

        void onClickItemPos(int pos);
    }
}

