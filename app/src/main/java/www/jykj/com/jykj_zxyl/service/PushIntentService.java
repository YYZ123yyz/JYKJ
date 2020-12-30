package www.jykj.com.jykj_zxyl.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

import www.jykj.com.jykj_zxyl.activity.SplashActivity;
import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ApplicationUtil;


/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-05-21 14:21
 */
public class PushIntentService extends UmengMessageService {
    private static final String TAG = PushIntentService.class.getName();
    public static final String INTENT_FILTER = "Notification_Action";
    public static final int ACTION_CLICK = 1;
    public static final int ACTION_DISMISS = 2;
    public static final String KEY_ACTION = "key_action";
    public static final String KEY_MSG = "key_msg";

    @Override
    public void onMessage(Context context, Intent intent) {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        UMessage msg;
        try {
            msg = new UMessage(new JSONObject(message));
            if (ApplicationUtil.isBackground(context)) {
                showNotification(context,msg,message,10);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    /**
     * 展示通知栏
     * @param context 上下文
     * @param msg 消息对象
     * @param id 通知Id
     */
    public void showNotification(Context context, UMessage msg,String msgJson, int id) {
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
        Intent contentIntent = new Intent(INTENT_FILTER);
        contentIntent.putExtra(KEY_ACTION, ACTION_CLICK);
        contentIntent.putExtra(KEY_MSG, msg.text);
        PendingIntent pendingContentIntent = PendingIntent.getBroadcast(context, (new Random(System.nanoTime())).nextInt(), contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingContentIntent);

        //添加通知的事件(点击跳转到指定页面SecondActivity)
        Intent resultIntent = new Intent(this, SplashActivity.class);
        //intent可以携带参数到指定页面的，这里省略
        resultIntent.putExtra("message",msgJson);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //自定义布局
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_view);
        contentView.setImageViewResource(R.id.iv_notification_icon, R.mipmap.logo);
        contentView.setTextViewText(R.id.tv_notification_title, msg.title);
        contentView.setTextViewText(R.id.tv_notification_text, msg.text);
        mNotification.contentView = contentView;
        builder.setAutoCancel(true);

        manager.notify(id, mNotification);
        playNotificationRing(this);
        playNotificationVibrate(this);
    }


    /**
     * 播放通知声音
     */
    private  void playNotificationRing(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri);
        rt.play();
    }

    /**
     * 手机震动一下
     */
    private  void playNotificationVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        long[] vibrationPattern = new long[]{0, 180, 80, 120};
        // 第一个参数为开关开关的时间，第二个参数是重复次数，振动需要添加权限
        vibrator.vibrate(vibrationPattern, -1);
    }
}
