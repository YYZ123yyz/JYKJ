package util;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import www.jykj.com.jykj_zxyl.R;

public class LinkdoodLoadingDialog extends BaseDialog {

   // private FlippingImageView mFivIcon;
    private LinkdoodTextView mHtvText;
    private String mText;
    private Activity mParentActivity;

    public LinkdoodLoadingDialog(Context context, String text) {
        super(context);
        mParentActivity = (Activity) context;
        mText = text;
        init();
        setCanceledOnTouchOutside(false);
    }

    public LinkdoodLoadingDialog(Activity context, String text) {
        super(context);
        mParentActivity = context;
        mText = text;
        init();
        setCanceledOnTouchOutside(false);
    }

    private void init() {
        setContentView(R.layout.common_flipping_loading_diloag);
//		mFivIcon = (FlippingImageView) findViewById(R.id.loadingdialog_fiv_icon);
        mHtvText = (LinkdoodTextView) findViewById(R.id.loadingdialog_htv_text);
//		mFivIcon.startAnimation();
        mHtvText.setText(mText);
    }

    public void setText(String text) {
        mText = text;
        mHtvText.setText(mText);
    }


    @Override
    public void dismiss() {
        if (mParentActivity != null && !mParentActivity.isFinishing()) {
            if (isShowing()) {
                super.dismiss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mParentActivity != null && !mParentActivity.isFinishing()) {
                if (isShowing()) {
                    super.dismiss();
                    mParentActivity.finish();
                } else {
                    mParentActivity.finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 更改dialog样式activity在屏幕中显示的位置
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        Display d = getWindow().getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.y = (int) (d.getHeight() * 1); // 开始高度设置为屏幕的0.5;
        getWindow().getWindowManager().updateViewLayout(view, lp);
    }
}
