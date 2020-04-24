package NotifyExample;


import java.io.File;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import NotifyExample.base.BaseActivity;
import www.jykj.com.jykj_zxyl.R;

/*
 * 通知栏应用
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	private Button btn_show;
	private Button btn_bigstyle_show;
	private Button btn_show_progress;
	private Button btn_show_cz;
	private Button btn_clear;
	private Button btn_clear_all;
	private Button btn_show_custom;
	/** 点击跳转到指定的界面 */
	private Button btn_show_intent_act;
	/** 点击打开指定的界apk */
	private Button btn_show_intent_apk;
	/** Notification构造器 */
	NotificationCompat.Builder mBuilder;
	/** Notification的ID */
	int notifyId = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
//		initNotify();
		//第一步：实例化通知栏构造器Notification.Builder：
		Notification.Builder builder = new Notification.Builder(this);//实例化通知栏构造器Notification.Builder，参数必填（Context类型），为创建实例的上下文
		//第二步：获取状态通知栏管理：
		NotificationManager mNotifyMgr = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);//获取状态栏通知的管理类（负责发通知、清除通知等操作）
		//第三步：设置通知栏PendingIntent（点击动作事件等都包含在这里）:
//        Intent push =newIntent(MainActivity.this,MainActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
//        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
//        //本程序用来响应点击通知的打开应用，第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
//        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,push,FLAG_CANCEL_CURRENT);
		//第四步：对Builder进行配置：
		builder
				.setContentTitle("My notification")//标题
				.setContentText("Hello World!")// 详细内容
//                .setContentIntent(contentIntent)//设置点击意图
				.setTicker("New message")//第一次推送，角标旁边显示的内容
				.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), com.hyphenate.easeui.R.mipmap.ic_launcher))
				.setDefaults(Notification.DEFAULT_ALL);//打开呼吸灯，声音，震动，触发系统默认行为
        /*Notification.DEFAULT_VIBRATE    //添加默认震动提醒  需要VIBRATE permission
        Notification.DEFAULT_SOUND    //添加默认声音提醒
        Notification.DEFAULT_LIGHTS//添加默认三色灯提醒
        Notification.DEFAULT_ALL//添加默认以上3种全部提醒*/
		//.setLights(Color.YELLOW, 300, 0)//单独设置呼吸灯，一般三种颜色：红，绿，蓝，经测试，小米支持黄色
		//.setSound(url)//单独设置声音
		//.setVibrate(new long[] { 100, 250, 100, 250, 100, 250 })//单独设置震动
		//比较手机sdk版本与Android 5.0 Lollipop的sdk
		if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.LOLLIPOP) {
			builder
					/*android5.0加入了一种新的模式Notification的显示等级，共有三种：
                    VISIBILITY_PUBLIC只有在没有锁屏时会显示通知
                    VISIBILITY_PRIVATE任何情况都会显示通知
                    VISIBILITY_SECRET在安全锁和没有锁屏的情况下显示通知*/
					.setVisibility(Notification.VISIBILITY_PUBLIC)
					.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
					.setCategory(Notification.CATEGORY_MESSAGE)//设置通知类别
			.setColor(this.getResources().getColor(R.color.gray_normal))//设置smallIcon的背景色
//                    .setFullScreenIntent(contentIntent, true)//将Notification变为悬挂式Notification
                    .setSmallIcon(R.mipmap.logo);//设置小图标
		}
		else{
			builder
					.setSmallIcon(com.hyphenate.easeui.R.mipmap.ic_launcher);//设置小图标
		}
		//第五步：发送通知请求：
		Notification notify = builder.build();//得到一个Notification对象
		mNotifyMgr.notify(1,notify);//发送通知请求
	}

	private void initView() {
		btn_show = (Button)findViewById(R.id.btn_show);
		btn_bigstyle_show = (Button)findViewById(R.id.btn_bigstyle_show);
		btn_show_progress = (Button)findViewById(R.id.btn_show_progress);
		btn_show_cz = (Button)findViewById(R.id.btn_show_cz);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		btn_clear_all = (Button)findViewById(R.id.btn_clear_all);
		btn_show_custom = (Button)findViewById(R.id.btn_show_custom);
		btn_show_intent_act = (Button)findViewById(R.id.btn_show_intent_act);
		btn_show_intent_apk = (Button)findViewById(R.id.btn_show_intent_apk);
		btn_show.setOnClickListener(this);
		btn_bigstyle_show.setOnClickListener(this);
		btn_show_progress.setOnClickListener(this);
		btn_show_cz.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_clear_all.setOnClickListener(this);
		btn_show_custom.setOnClickListener(this);
		btn_show_intent_act.setOnClickListener(this);
		btn_show_intent_apk.setOnClickListener(this);
	}

	/** 初始化通知栏 */
	private void initNotify(){
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
	}

	/** 显示通知栏 */
	public void showNotify(){
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
//				.setNumber(number)//显示数量
				.setTicker("测试通知来啦");//通知首次出现在通知栏，带上升动画效果的
		mNotificationManager.notify(notifyId, mBuilder.build());
//		mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
	}

	/** 显示大视图风格通知栏 */
	public void showBigStyleNotify() {
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		String[] events = new String[5];
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("大视图内容:");
		// Moves events into the big view
		for (int i=0; i < events.length; i++) {
			inboxStyle.addLine(events[i]);
		}
		mBuilder.setContentTitle("测试标题")
				.setContentText("测试内容")
//				.setNumber(number)//显示数量
				.setStyle(inboxStyle)//设置风格
				.setTicker("测试通知来啦");// 通知首次出现在通知栏，带上升动画效果的
		mNotificationManager.notify(notifyId, mBuilder.build());
		// mNotification.notify(getResources().getString(R.string.app_name),
		// notiId, mBuilder.build());
	}

	/** 显示常驻通知栏 */
	public void showCzNotify(){
//		Notification mNotification = new Notification();//为了兼容问题，不用该方法，所以都采用BUILD方式建立
//		Notification mNotification  = new Notification.Builder(this).getNotification();//这种方式已经过时
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//		//PendingIntent 跳转动作
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, getIntent(), 0);
		mBuilder.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("常驻通知来了")
				.setContentTitle("常驻测试")
				.setContentText("使用cancel()方法才可以把我去掉哦")
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();
		//设置通知  消息  图标
		mNotification.icon = R.drawable.ic_launcher;
		//在通知栏上点击此通知后自动清除此通知
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
		//设置显示通知时的默认的发声、震动、Light效果
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
		//设置发出消息的内容
		mNotification.tickerText = "通知来了";
		//设置发出通知的时间
		mNotification.when=System.currentTimeMillis();
//		mNotification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
//		mNotification.setLatestEventInfo(this, "常驻测试", "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息  ,这个方法现在已经不用了
		mNotificationManager.notify(notifyId, mNotification);
	}

	/** 显示通知栏点击跳转到指定Activity */
	public void showIntentActivityNotify(){
		// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
//		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
		mBuilder.setAutoCancel(true)//点击后让通知将消失
				.setContentTitle("测试标题")
				.setContentText("点击跳转")
				.setTicker("点我");
		//点击的意图ACTION是跳转到Intent
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/** 显示通知栏点击打开Apk */
	public void showIntentApkNotify(){
		// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
//		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
		mBuilder.setAutoCancel(true)//点击后让通知将消失
				.setContentTitle("下载完成")
				.setContentText("点击安装")
				.setTicker("下载完成！");
		//我们这里需要做的是打开一个安装包
		Intent apkIntent = new Intent();
		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		apkIntent.setAction(android.content.Intent.ACTION_VIEW);
		//注意：这里的这个APK是放在assets文件夹下，获取路径不能直接读取的，要通过COYP出去在读或者直接读取自己本地的PATH，这边只是做一个跳转APK，实际打不开的
		String apk_path = "file:///android_asset/cs.apk";
//		Uri uri = Uri.parse(apk_path);
		Uri uri = Uri.fromFile(new File(apk_path));
		apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		// context.startActivity(intent);
		PendingIntent contextIntent = PendingIntent.getActivity(this, 0,apkIntent, 0);
		mBuilder.setContentIntent(contextIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_show:
				showNotify();
				break;
			case R.id.btn_bigstyle_show:
				showBigStyleNotify();
				break;
			case R.id.btn_show_cz:
				showCzNotify();
				break;
			case R.id.btn_clear:
				clearNotify(notifyId);
				break;
			case R.id.btn_clear_all:
				clearAllNotify();
				break;
			case R.id.btn_show_intent_act:
				showIntentActivityNotify();
				break;
			case R.id.btn_show_intent_apk:
				showIntentApkNotify();
				break;
			case R.id.btn_show_progress:
				startActivity(new Intent(getApplicationContext(), ProgressAcitivty.class));
				break;
			case R.id.btn_show_custom:
				startActivity(new Intent(getApplicationContext(), CustomActivity.class));
				break;
			default:
				break;
		}
	}

}
