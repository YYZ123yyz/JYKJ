package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-13 14:33
 */
public class CopyButtonLibrary {

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;
    private TextView textView;

    public CopyButtonLibrary(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public void init() {
        myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        String text;
        text = textView.getText().toString();

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        ToastUtils.showToast("已复制");
    }

}
