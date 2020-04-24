package util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import www.jykj.com.jykj_zxyl.R;
import yyz_exploit.Utils.MyDialog;

import com.bumptech.glide.Glide;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * Created by 胡涛.
 */
public class VersionsUpdata {
    private static String TAG = "VersionsUpdata";
    private Activity mainActivity;
    private String loaduri;

    public VersionsUpdata(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * 更新的内容  是否强制更新
     */
    public void initdata(StringBuffer string, final boolean isflag, String uri) {
        loaduri = uri;
        View view = View.inflate(mainActivity, R.layout.banbengengxin, null);
        final MyDialog dialog = new MyDialog(mainActivity, 0, 0, view, R.style.dialog);
        if (isflag) {
            dialog.setCancelable(false);   //返回键不可关闭
        }
        dialog.show();
        TextView viewById = view.findViewById(R.id.remarks);
        viewById.setText(string);
        Button gengxin = view.findViewById(R.id.gengxin);
        Button quxiao = view.findViewById(R.id.quxiao);
     //   final NumberProgressBar number_progress_bar = view.findViewById(R.id.number_progress_bar);
        final ImageView load_img = view.findViewById(R.id.load_img);
      //  Glide.with(mainActivity).load(R.drawable.loding).asGif().into(load_img);

        final LinearLayout ly = view.findViewById(R.id.ly);
        gengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (flag != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission =
                            mainActivity.getApplicationContext().getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        startInstallPermissionSettingActivity();
                        return;
                    }
                }


                //下载apk
                downloadApk(ly,load_img, dialog);
            }
        });


        //取消退出app
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mainActivity.finish();
            }
        });

    }

    /**
     * 下载apk
     */
    private void downloadApk(LinearLayout ly,ImageView load_img, MyDialog dialog) {
        //显示下载进度
        load_img.setVisibility(View.VISIBLE);
        ly.setVisibility(View.GONE);
        //访问网络下载apk
        new Thread(new DownloadApk(load_img, dialog)).start();
    }


    /**
     * 访问网络下载apk
     */
    private class DownloadApk implements Runnable {
        private ImageView dialog;
        private MyDialog mydialog;
        InputStream is;
        FileOutputStream fos;

        private DownloadApk(ImageView dialog, MyDialog mydialog) {
            this.dialog = dialog;
            this.mydialog = mydialog;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(loaduri).build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    //获取内容总长度
                    final long contentLength = response.body().contentLength();
                    //保存到sd卡
                    File apkFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".apk");
                    fos = new FileOutputStream(apkFile);
                    //获得输入流
                    is = response.body().byteStream();
                    //定义缓冲区大小
                    byte[] bys = new byte[1024];
                    int sum = 0;
                    int len = -1;
                    while ((len = is.read(bys)) != -1) {
                        try {
                            Thread.sleep(1);
                            fos.write(bys, 0, len);
                            fos.flush();
                            sum += len;
                            final int progress = (int) (sum * 1.0f / contentLength * 100);


                            //设置进度
                            mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  /*  dialog.setMax(100);
                                    dialog.setProgress(progress);*/
                                }
                            });
                        } catch (InterruptedException e) {
                            Log.e("erroy", e.toString());
                        }
                    }
                    //下载完成,提示用户安装
                    mydialog.dismiss();
//                    android.os.Process.killProcess(android.os.Process.myPid());
//                        if(!isAppInstalled(mainActivity,"com.example.zysyv1")){
                    installApk(mainActivity, apkFile.getCanonicalPath());  //安装
//                        }
                }
            } catch (IOException e) {
                Log.e("erroy", e.toString());
            } finally {
                //关闭io流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    is = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fos = null;
                }
            }

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setVisibility(View.GONE);
                }
            });
        }
    }

    public static void installApk(Context context, String apkPath) {

        if (context == null || TextUtils.isEmpty(apkPath)) {
            return;
        }
        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            Log.v(TAG, "7.0以上，正在安装apk...");
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, "android.support.v4.content.FileProvider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            Log.v(TAG, "7.0以下，正在安装apk...");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);

    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainActivity.getApplicationContext().startActivity(intent);
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     */

    public static void unstallApp(Context context, String packageName) {
        Intent uninstall_intent = new Intent();
        uninstall_intent.setAction(Intent.ACTION_DELETE);
        uninstall_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        uninstall_intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(uninstall_intent);
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }


}
