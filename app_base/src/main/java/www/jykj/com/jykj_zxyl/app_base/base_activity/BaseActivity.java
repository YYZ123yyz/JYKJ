package www.jykj.com.jykj_zxyl.app_base.base_activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import com.gyf.immersionbar.ImmersionBar;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityStackManager;
import www.jykj.com.jykj_zxyl.app_base.base_utils.AndroidThreadExecutor;
import www.jykj.com.jykj_zxyl.app_base.base_utils.SharedPreferences_DataSave;

/**
 * Description:基础类
 *
 * @author: qiuxinhai
 * @date: 2020-07-14 11:38
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**上下文*/
    protected Context context;
    /**ButterKnife*/
    private Unbinder unbinder;
    /**进度加载框*/
    private Dialog dialog;
    private TextView tvMessage;
    private View view;

    private EMConnectionListener emConnectionListener;
    protected int pageIndex=1;
    protected int pageSize=10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        onBeforeSetContentLayout();
        context = this;
        Resources res = getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        //将Activity添加到队列方便管理
        ActivityStackManager.getInstance().add(this);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(setLayoutId());

        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
            setStatusBarColor();
        }
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
        //添加Im监听
        addImConnectionListener();
    }


    @Override
    protected void onPause() {
        super.onPause();
        EMClient.getInstance().removeConnectionListener(emConnectionListener);
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this).titleBar(R.id.toolbar).init();
    }

    protected void setStatusBarColor(){
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .navigationBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    protected abstract int setLayoutId();

    protected void initData() {

    }

    protected void initView() {
    }

    protected void setListener() {
    }

    protected void onBeforeSetContentLayout() {

    }


    private void addImConnectionListener(){
        emConnectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {

                if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                    AndroidThreadExecutor.getInstance().executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            showComplexDialog();
                        }
                    });

                } else {

                    Log.e("tag", "onDisconnected: " + "00000");
                }
            }

            @Override
            public void onConnected() {

            }
        };
        EMClient.getInstance().addConnectionListener(emConnectionListener);
    }


    void showComplexDialog() {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        //    设置Content来显示一个信息
        builder.setTitle("异地登录");
        builder.setMessage("您的账号已在其他地方登陆！");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    cleanPersistence();
                    EMClient.getInstance().logout(true,null);
                    Intent intent=new Intent();
                    intent.setAction("www.jykj.com.jykj_zxyl.LoginActivity");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setCancelable(false);
        //    显示出该对话框
        builder.show();

    }

    //清空数据
    public void cleanPersistence() {
        SharedPreferences_DataSave  m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.remove("loginUserInfo");
        m_persist.remove("viewSysUserDoctorInfoAndHospital");

        m_persist.commit();
    }

    /**
     * 开启进度框
     */
    protected void showLoading(String msg, DialogInterface.OnCancelListener onCancelListener) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (dialog == null) {
            dialog = new AlertDialog.Builder(context, R.style.NormalDialogStyle).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            view = inflater.inflate(R.layout.dialog_loading, null, false);
            if (dialog.getWindow() == null) {
                return;
            }
            tvMessage = view.findViewById(R.id.tvMessage);
            dialog.getWindow().setContentView(view.getRootView());
        } else if (dialog.isShowing()) {
            return;
        } else {
            dialog.show();
        }
        if (TextUtils.isEmpty(msg)) {
            tvMessage.setText("正在加载...");
        } else {
            tvMessage.setText(msg);
        }
        if (onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
    }


    /**
     * 关闭对话框
     */
    protected void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    protected void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this, paramClass);
        this.startActivity(localIntent);
    }

    /**
     * 跳转Activity,需要回传值
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     * @param requestCode 请求码
     */
    @SuppressWarnings("rawtypes")
    protected void startActivity(Class paramClass, Bundle paramBundle, int requestCode) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this, paramClass);
        this.startActivityForResult(localIntent, requestCode);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(emConnectionListener);
        unbinder.unbind();
    }


}
