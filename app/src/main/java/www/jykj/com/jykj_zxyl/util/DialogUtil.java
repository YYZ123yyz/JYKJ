package www.jykj.com.jykj_zxyl.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jykj.com.jykj_zxyl.R;

public class DialogUtil implements View.OnClickListener {

    private Dialog dialog;
    private View view;
    private TextView tvLoadTitle;
    private OnCancleAndConfirmListener listener;
    private Window window;
    private LinearLayout content_ll;
    private RelativeLayout single_rl;
    private RelativeLayout confirm_and_cancle_rl;
    private TextView title_tv;

    /**
     * 设置dialog中间内容
     */
    public void setContent(View content) {
        content_ll.addView(content);
    }

    public void setContentView(View view) {
        content_ll.addView(view);
    }

    public void setTitleGone() {
        title_tv.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        title_tv.setText(toString());
    }


    /**
     * 圆形进度条
     *
     * @param context
     */
    public DialogUtil(Context context) {
        super();
        dialog = new Dialog(context, R.style.DialgStyle);
        dialog.setCancelable(false);
        dialog.show();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);
        tvLoadTitle = view.findViewById(R.id.tv_title);
        Window window = dialog.getWindow();
        window.setContentView(view);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
    }



    //
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_cancel:
//                dialog.dismiss();
////                listener.cancle();
//                break;
//
//            case R.id.confirm1_tv:
//            case R.id.confirm_tv:
//                dialog.dismiss();
//                listener.confirm();
//                break;
//
//            default:
//                break;
        }
    }

    public interface OnCancleAndConfirmListener {
        public void cancle();

        public void confirm();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public boolean isShow() {
        return dialog.isShowing();
    }

    public void setLoadTitle(String s) {
        tvLoadTitle.setText(s);
    }
}
