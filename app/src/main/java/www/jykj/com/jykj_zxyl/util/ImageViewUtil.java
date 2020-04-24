package www.jykj.com.jykj_zxyl.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageViewUtil {

    /**
     * imagaView 显示图片
     */
    public static void showImageView(Context context, String url, ImageView imageView){
        if (url != null && !"".equals(url))
        {
            try {
                int avatarResId = Integer.parseInt(url);
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(url)
                        .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
        }
    }
}
