package www.jykj.com.jykj_zxyl.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.activity.hyhd.ChatActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotifyUtil {

    private         Context             mContext;
    private         NotificationManager mManager;
    private         Notification        mNotification;
    public  static  NotifyUtil          mNotifyUtil;

    public static NotifyUtil getInstance(){
        if (mNotifyUtil == null)
            mNotifyUtil = new NotifyUtil();
        return mNotifyUtil;
    }
    public void initNotify(Context context){
        mContext = context;

        mManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

        }
        Intent intent = new Intent(mContext, ChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        mNotification = new NotificationCompat.Builder(context, "jykj")
                .setAutoCancel(true)
                .setContentTitle("收到聊天消息")
                .setContentText("今天晚上吃什么")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置红色
                .setColor(Color.parseColor("#F00606"))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();

    }

    public void notifycation(){
        try {
            mManager.notify(1, mNotification);
        }catch (Exception e)
        {
            System.out.println();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
