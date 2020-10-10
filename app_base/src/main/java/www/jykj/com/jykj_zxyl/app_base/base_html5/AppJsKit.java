package www.jykj.com.jykj_zxyl.app_base.base_html5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


import java.lang.ref.WeakReference;

/**
 * Description::用于js交互使用
 *
 * @author: qiuxinhai
 * @date: 2019-06-21 14:31
 */
public class AppJsKit {
    /**上下文*/
    private Context mContext;
    /**Js跳转Handler*/
    private JsHandler jsHandler;

    AppJsKit(Context mContext) {
        this.mContext = mContext;
        this.jsHandler=new JsHandler(this);
    }

    @JavascriptInterface
    public void testJump(){

    }

    //Js调用原生方法跳页传值
    @JavascriptInterface
    public void jsCallNativeToActivity(String msg){
        Intent intent = new Intent(mContext,H5Activity.class);
        intent.putExtra("msg",msg);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(mContext).
                        setMessage(text).show();
            }
        });
    }


    @JavascriptInterface
    public void onSumResult(int sum) {
        Toast.makeText(mContext, sum + "",
                Toast.LENGTH_SHORT).show();
    }

    private static class JsHandler extends Handler {
        private WeakReference<AppJsKit> activityWeakReference;

        private JsHandler(AppJsKit appJsKit) {
            activityWeakReference = new WeakReference<>(appJsKit);
        }

        @Override
        public void handleMessage(Message msg) {
            AppJsKit appJsKit = activityWeakReference.get();
            if (appJsKit != null) {
                //处理js发送的指令
            }

        }
    }
}
