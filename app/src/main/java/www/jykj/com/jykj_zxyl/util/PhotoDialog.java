package www.jykj.com.jykj_zxyl.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import entity.wdzs.ProvideBasicsImg;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.marqueeimage.MarqueeImage;
import www.jykj.com.jykj_zxyl.util.marqueeimage.transforms.AccordionTransformer;

public class PhotoDialog extends Dialog {
    ImageLoadingListener animateFirstListener = new PhotoDialog.AnimateFirstDisplayListener();
    ArrayList<View> mImageList=new ArrayList<View>();//数据源
    MarqueeImage marqueeImage;
    private JYKJApplication mApp;
    private List<ProvideBasicsImg> mPhotoInfos;
    private Context mContext;
    private                         int                             mIndex;

    Context context;
    public PhotoDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public PhotoDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    public void setDate(Context context, JYKJApplication application, List<ProvideBasicsImg> photo_infos, int index){
        mApp = application;
        mContext = context;
        mPhotoInfos = photo_infos;
        mIndex = index;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.popuwindow_photo);
        marqueeImage = (MarqueeImage)this.findViewById(R.id.iv_popuwindowphoto_photoDetail);

        mImageList.clear();
        for (int i = 0; i < mPhotoInfos.size(); i++)
        {
            ImageView iv=new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            /**
             * 显示图片
             * 参数1：图片url
             * 参数2：显示图片的控件
             * 参数3：显示图片的设置
             * 参数4：监听器
             */
            if (mPhotoInfos.get(i).getImgUrl() != null && !"".equals(mPhotoInfos.get(i).getImgUrl()))
            {
                try {
                    int avatarResId = Integer.parseInt(mPhotoInfos.get(i).getImgUrl());
                    Glide.with(mContext).load(avatarResId).into(iv);
                } catch (Exception e) {
                    //use default avatar
                    Glide.with(mContext).load(mPhotoInfos.get(i).getImgUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(iv);
                }
            }
            mImageList.add(iv);
        }
        marqueeImage.setData(mImageList);
        marqueeImage.setCurrentIndex(mIndex);
        /**
         * 设置动画
         *  new CubeOutTransformer());
         new AccordionTransformer());
         new FlipHorizontalTransformer());
         new RotateUpTransformer());
         new ZoomOutTranformer());
         new ZoomOutSlideTransformer());
         new TabletTransformer());
         */
        marqueeImage.setScrollerAnimation(new AccordionTransformer());
    }

    /**
     * 图片加载第一次显示监听器
     * @author Administrator
     *
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}