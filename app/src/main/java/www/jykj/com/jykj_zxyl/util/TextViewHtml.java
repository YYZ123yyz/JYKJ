package www.jykj.com.jykj_zxyl.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;


/**
 * 对HTML文本进行特殊处理的TextView
 *
 */
public class TextViewHtml extends android.support.v7.widget.AppCompatTextView {

    // 出处:http://stackoverflow.com/questions/26121657/showing-images-from-html-to-textview-android
    /**
     * 默认的占位图片资源
     */
    private Drawable defaultDrawable;

    private int width;
    private int height;

    public TextViewHtml(Context context) {
        super(context);
    }

    public TextViewHtml(Context context, AttributeSet attr) {
        super(context, attr);

        //TypedArray ta = context.obtainStyledAttributes(attr,
        //	R.styleable.TextViewHtml);
        //defaultDrawable = ta
        //	.getDrawable(R.styleable.TextViewHtml_defaultDrawable);//从xml文件中读取默认的占位图片

//        width = ScreenUtil.getScreenWidth(context);
//        height = ScreenUtil.getScreenHeight(context);

        //ta.recycle();
    }

    /**
     * <strong>主要方法<strong><br>
     * 因为{@link TextView#setText(CharSequence)}是final,所以无法重写<br>
     * 故在此提供一个{@link TextViewHtml#setHtmlText(CharSequence)}方法，接受传入的html形式的文本
     *
     * @param charSequence
     */
    public void setHtmlText(CharSequence charSequence) {

        Spanned spanned = Html.fromHtml(charSequence.toString(), imageGetter,
                null);
        setText(spanned);
    }

    public Drawable getDefaultDrawable() {
        return defaultDrawable;
    }

    public void setDefaultDrawable(Drawable defaultDrawable) {
        this.defaultDrawable = defaultDrawable;
    }

    public void setDefaultDrawableRes(int res) {
        this.defaultDrawable = getResources().getDrawable(res);
    }

    private ImageGetter imageGetter = new ImageGetter() {

        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            if (null == defaultDrawable) {
                defaultDrawable = getResources().getDrawable(
                        R.drawable.ic_launcher);
            }

            Drawable empty = defaultDrawable;
            d.addLevel(0, 0, empty);
            d.setBounds(0, 0, empty.getIntrinsicWidth(),
                    empty.getIntrinsicHeight());

            new LoadImage().execute(source, d);

            return d;
        }
    };

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                //根据屏幕的大小与图片的大小，对实际显示的图片宽高进行处理
                int w = bitmap.getWidth() > width ? width : bitmap.getWidth();
                int h = bitmap.getHeight() > height ? height : bitmap
                        .getHeight();
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, w, h);
                mDrawable.setLevel(1);

                //直接invalidate并不会起到作用，所以使用setText来完成图片显示刷新

                               CharSequence t = TextViewHtml.this.getText();
                TextViewHtml.this.setText(t);
                // TextViewHtml.this.invalidate();
            }
        }
    }
}