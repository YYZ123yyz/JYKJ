package www.jykj.com.jykj_zxyl.app_base.base_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.app_base.R;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-11 15:24
 */
public class ZoomTextView extends android.support.v7.widget.AppCompatTextView {
    public static final int AMPLIFY = 1;
    public static final int REDUCE = 2;
    private boolean isZoom;
    private SpannableString msp;
    float strethScale = 1.2f;// 缩放比例
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case AMPLIFY:
                    amplify();
                    break;
                case REDUCE:
                    reduce();
                    break;
            }
        };
    };

    public void reduce() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                msp = new SpannableString(getText().toString());
//                msp.setSpan(new RelativeSizeSpan(1), 0, getText().toString()
//                        .length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                msp.setSpan(new ScaleXSpan(1), 0,
//                        getText().toString().length(),
//                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                setText(msp);
                Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scalesmall);
                a.setFillAfter(true);
                ZoomTextView.this.startAnimation(a);
                mHandler.sendEmptyMessage(AMPLIFY);
            }
        }, 1000);
    }

    public void amplify() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
//                msp = new SpannableString(getText().toString());
//                // 相对比缩放
//                msp.setSpan(new RelativeSizeSpan(strethScale), 0, getText()
//                        .toString().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                // 横向缩放
//                msp.setSpan(new ScaleXSpan(strethScale), 0, getText()
//                        .toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                setText(msp);
                Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scalebig);
                a.setFillAfter(true);
                ZoomTextView.this.startAnimation(a);
                mHandler.sendEmptyMessage(REDUCE);
            }
        }, 1000);

    }

    public ZoomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public ZoomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomTextView(Context context) {
        this(context, null);
    }

    public boolean isZoom() {
        return isZoom;
    }

    public void setZoom(boolean isZoom) {
        this.isZoom = isZoom;
        if (isZoom) {
            reduce();
        }
    }


}
