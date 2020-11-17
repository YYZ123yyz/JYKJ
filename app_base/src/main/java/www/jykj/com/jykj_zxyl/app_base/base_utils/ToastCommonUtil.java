package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import www.jykj.com.jykj_zxyl.app_base.R;


/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-13 16:02
 */
public class ToastCommonUtil {

    /**
     * 自定义toast
     *
     * @param text
     */
    private static Toast mToast;

    /**
     * 视频播放器竖屏自定义toast
     *
     * @param stringContent
     */
    public static void showToastCustom(Context context,String stringContent,int gravity) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.toast_common, null);
            TextView content = view.findViewById(R.id.tv_toast_content);
            LinearLayout llRootView  = view.findViewById(R.id.ll_root_view);
            content.setText(stringContent);
            //llRootView.setBackgroundResource(R.drawable.video_terminal_prompt);
            if (mToast == null) {
                mToast = new Toast(context);
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setView(view);
                mToast.show();
            }
            mToast.setGravity(gravity, 0, 240);
            mToast.setView(view);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
