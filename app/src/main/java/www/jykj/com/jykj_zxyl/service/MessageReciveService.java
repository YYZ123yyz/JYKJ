package www.jykj.com.jykj_zxyl.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.hyhd.model.CallReceiver;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.MainMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import entity.basicDate.EMMessageEntity;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.activity.MainActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;

public class MessageReciveService extends Service {


    // 当服务第一次创建的时候调用
    @Override
    public void onCreate() {
        Log.e("service", "onCreate!");
        super.onCreate();
    }

    // 每次调用startService都会执行下面的方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "onStartCommand!");
        registerMessageListener();
        retistHXConnectListener();
        startMessageTimer();

        return super.onStartCommand(intent, flags, startId);
    }



    /**
     * 启动定时器，发送透传消息
     */
    private void startMessageTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                String action="action1";//action可以自定义
                EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
                String toUsername = "test1";//发送给某个人
                cmdMsg.setTo(toUsername);
                cmdMsg.addBody(cmdBody);
                EMClient.getInstance().chatManager().sendMessage(cmdMsg);

            }
        };
        timer.schedule(task, 0, 15000);
    }

    /**
     * 连接状态监听
     */
    private void retistHXConnectListener() {

        EMConnectionListener connectionListener = new EMConnectionListener() {

            @Override
            public void onDisconnected(int error) {
                JYKJApplication  application = (JYKJApplication) getApplication();
                application.gNetConnectionHX = false;
                application.setNetConnectionStateHX();
                if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                    System.out.println("掉线");
                } else {
                    System.out.println("掉线");
                }
            }

            @Override
            public void onConnected() {
                System.out.println("连接中");
                JYKJApplication  application = (JYKJApplication) getApplication();
                application.gNetConnectionHX = true;
                application.setNetConnectionStateHX();
            }
        };

        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    // 当服务销毁的时候调用
    @Override
    public void onDestroy() {
        Log.e("service", "onDestroy!");
        super.onDestroy();
    }

    // 调用 bindService 会调用下面的这个方法
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("service", "onBind!");
        return null;
    }

    private void registerMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                JYKJApplication application = (JYKJApplication) getApplication();
                //播放声音
                MediaPlayer  mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tsy);
                mPlayer.start();
                EaseAtMessageHelper.get().parseMessages(messages);
                application.setNewsMessage();
                application.gMainActivity.setHZTabView();
                //判断程序是否运行在前台
                if (!application.isApplicationBroughtToBackground(getApplicationContext()))
                    setNotificationDemoForAndroidO(messages);
                else
                {
                    //设置fragment显示新消息
                    application.gMainActivity.setNewsMessageView();
                }
                EventBus.getDefault().post(new MainMessage(messages.size()+""));
            }
            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }
            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onGroupMessageRead(List<EMGroupReadAck> groupReadAcks) {
                for (EMGroupReadAck ack: groupReadAcks) {
                    EaseDingMessageHelper.get().handleGroupReadAck(ack);
                }
            }
        });
    }

    /**
     *
     */
    private void setNotificationDemoForAndroidO(List<EMMessage> messages) {
        //ID
        String id = "jykjNewMessage";
        //名称
        String name = "notification";
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager
                    .IMPORTANCE_DEFAULT);
            channel.setSound(null,null);
            mBuilder.setChannelId(id);

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
            notificationManager.createNotificationChannel(channel);

            mBuilder.setSmallIcon(R.mipmap.logo);
            mBuilder.setContent(remoteViews);
//            if (progress == 1) {
//                mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//            }
//            String str = "{"+messages.get(0).getBody().toString()+"}";
//            EMMessageEntity emMessageEntity = new Gson().fromJson(str,EMMessageEntity.class);

            String ticker = EaseCommonUtils.getMessageDigest(messages.get(0), this);
            remoteViews.setImageViewResource(R.id.image, R.mipmap.logo);
            remoteViews.setTextViewText(R.id.title, "您有一条新消息");
            remoteViews.setTextViewText(R.id.content,ticker);
//            remoteViews.setProgressBar(R.id.pBar, 10, progress, false);
//            remoteViews.setTextViewText(R.id.proNum, progress + "/10");
            notificationManager.notify(20, mBuilder.build());
//            notificationManager.cancel(20);
        }
    }

}
