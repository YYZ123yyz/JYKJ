/*
package www.jykj.com.jykj_zxyl.app_base.base_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;


import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

import www.jykj.com.jykj_zxyl.app_base.R;

*/
/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-05-22 09:55
 *//*

public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private static final String KEY_MESSAGE_BODY = "MESSAGE_BODY";
    private static final String KEY_MESSAGE_TYPE = "MESSAGE_TYPE";
    */
/** 错误类型 *//*

    public static final int MSG_TYPE_ERROR = -1;
    private NotifyActionReceiver mReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册广播接受者
        mReceiver = new NotifyActionReceiver();
        registerReceiver(mReceiver, new IntentFilter(NotifyActionReceiver.INTENT_FILTER));
    }

    */
/**
     * start Notification Service
     *//*

    public static void start(@NonNull Context context, int msgType, @NonNull String message){
        Intent i = new Intent();
        i.putExtra(KEY_MESSAGE_TYPE, msgType);
        i.putExtra(KEY_MESSAGE_BODY, message);
        i.setClass(context, NotificationService.class);
        context.startService(i);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent!=null) {
            UMessage msg=null;
            if(intent.hasExtra(KEY_MESSAGE_TYPE) && intent.hasExtra(KEY_MESSAGE_BODY)){
                int msgType = intent.getIntExtra(KEY_MESSAGE_TYPE, MSG_TYPE_ERROR);
                String message = intent.getStringExtra(KEY_MESSAGE_BODY);
                try {
                    msg = new UMessage(new JSONObject(message));
                    showNotification(this,msg,getNotifyId(msg.msg_id));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        return super.onStartCommand(intent, flags, startId);


    }

    */
/**
     * 展示通知栏
     * @param context 上下文
     * @param msg 消息对象
     * @param id 通知Id
     *//*

    public void showNotification(Context context, UMessage msg, int id) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        //解决Android8.0收不到消息问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("static", "Primary Channel", NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(mChannel);
            builder.setChannelId("static");
        }
        Notification mNotification = builder.build();
        mNotification.icon = R.mipmap.logo;//notification通知栏图标
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        mNotification.defaults |= Notification.DEFAULT_VIBRATE ;
        mNotification.tickerText=msg.ticker;
        Intent contentIntent = new Intent(NotifyActionReceiver.INTENT_FILTER);
        contentIntent.putExtra(NotifyActionReceiver.KEY_ACTION, NotifyActionReceiver.ACTION_CLICK);
        contentIntent.putExtra(NotifyActionReceiver.KEY_MSG, msg.text);
        PendingIntent pendingContentIntent = PendingIntent.getBroadcast(context, (new Random(System.nanoTime())).nextInt(), contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingContentIntent);
        //自定义布局
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_view);
        contentView.setImageViewResource(R.id.iv_notification_icon, R.mipmap.logo);
        contentView.setTextViewText(R.id.tv_notification_title, msg.title);
        contentView.setTextViewText(R.id.tv_notification_text, msg.text);
        mNotification.contentView = contentView;
        builder.setAutoCancel(true);

        manager.notify(id, mNotification);
    }
    public static class NotifyActionReceiver extends BroadcastReceiver {
        public static final String INTENT_FILTER = "Notification_Action";
        public static final int ACTION_CLICK = 1;
        public static final int ACTION_DISMISS = 2;
        public static final String KEY_ACTION = "key_action";
        public static final String KEY_MSG = "key_msg";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent!=null) {
                int action = intent.getIntExtra(KEY_ACTION, -1);
                String msg = intent.getStringExtra(KEY_MSG);
                if(action==ACTION_CLICK){
//                    CC.obtainBuilder(CC_Component_Constant.WorkComponent)
//                            .setActionName(CC_ActionName_Constant.showSysMessageContentActivity)
//                            .build().call();
                }
            }
        }
    }
    private int getNotifyId(String messageId) {
        try {
            return UUID.nameUUIDFromBytes(messageId.getBytes()).hashCode();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
*/
