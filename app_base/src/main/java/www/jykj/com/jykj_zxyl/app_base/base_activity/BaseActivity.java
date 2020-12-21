package www.jykj.com.jykj_zxyl.app_base.base_activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;


import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.smarx.notchlib.NotchScreenManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ActivityStackManager;
import www.jykj.com.jykj_zxyl.app_base.base_utils.AndroidThreadExecutor;
import www.jykj.com.jykj_zxyl.app_base.base_utils.Constants;
import www.jykj.com.jykj_zxyl.app_base.base_utils.JsonParserUtil;
import www.jykj.com.jykj_zxyl.app_base.base_utils.LogUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.SPUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.SharedPreferences_DataSave;

/**
 * Description:基础类
 *
 * @author: qiuxinhai
 * @date: 2020-07-14 11:38
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnLayoutChangeListener, View.OnFocusChangeListener{
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
    private SpeechRecognizer mIat;
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            LogUtils.e("SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                LogUtils.e("初始化失败，错误码：" + code);
            }
        }
    };
    private PopupWindow popupWindow;
    private TextView languageTv;
    private PopupWindow mPopWindow;
    private View decorView;
    private int popHigh;
    private float fontSizeScale;

    //偏移量
    private int offset;
    //当前页面获取了焦点的editText
    private NestedScrollView mainScrollView;
    private Runnable scrollRunnable;
    private boolean normalCanScroll = true;
    private EditText currentFocusEt;
    //当前页面获取了所有的editText
    private List<EditText> editTexts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        onBeforeSetContentLayout();
        context = this;
        fontSizeScale = (float) SPUtils.get(this, Constants.SP_FontScale, 0.0f);
        String localClassName = getLocalClassName();
        decorView = this.getWindow().getDecorView();
        com.blankj.utilcode.util.LogUtils.e("activity    " + localClassName);
        Resources res = getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        NotchScreenManager.getInstance().setDisplayInNotch(this);
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
        initSpeech();
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
        //添加Im监听
        addImConnectionListener();


        KeyboardUtils.registerSoftInputChangedListener(this, new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                if (height > 0) {
                    if (!localClassName.contains("ChatActivity") && !localClassName.contains("ReferenceMapActivity")) {

                        showPopupCommnet();
                    }
                } else {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    if (mPopWindow != null){
                        mPopWindow.dismiss();
                    }

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        //EMClient.getInstance().removeConnectionListener(emConnectionListener);
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

                if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
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
                    ActivityStackManager.getInstance().finishActivityList();
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
        KeyboardUtils.unregisterSoftInputChangedListener(this.getWindow());
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
        if (mPopWindow !=null ){
            if (mPopWindow.isShowing()){
                mPopWindow.dismiss();
            }
            mPopWindow = null;
        }
        if (mainScrollView != null) {
            mainScrollView.removeCallbacks(scrollRunnable);
        }
    }

    private void initSpeech() {
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        //设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
    }

    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onError(SpeechError error) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {


            LogUtils.e(results.getResultString());

            /* printResult(results);*/

            String text = JsonParserUtil.parseIatResult(results.getResultString());


            View view = getWindow().getDecorView().findFocus();


            if (view instanceof EditText) {
                ((EditText) view).append(text);
            }

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {


        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };


    private void showPopupCommnet() {
        if (popupWindow !=null ){
            if (popupWindow.isShowing()){
                return;
            }
        }
        View view = LayoutInflater.from(this).inflate(
                R.layout.voice_popupwindow, null);
        LinearLayout part1 = view.findViewById(R.id.layout_part_1);
        languageTv = view.findViewById(R.id.choose_lang);
        final View decorView = this.getWindow().getDecorView();
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);


        popHigh = decorView.getBottom() - outRect.bottom;
        view.findViewById(R.id.iv_input_voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIat.isListening()) {
                    mIat.stopListening();
                }
            }
        });


        view.findViewById(R.id.iv_input_voice).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mIat.startListening(mRecognizerListener);
                return false;
            }
        });
        view.findViewById(R.id.lanuage_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                optionPick.show(decorView);
//                initChoose ();
                showPopupWindow();

            }
        });

//        initChangeLanguage();

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, (decorView.getBottom() - outRect.bottom));
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        popupWindow.update();


    }


    private void showPopupWindow(){

        if (mPopWindow !=null ){
            if (mPopWindow.isShowing()){
                return;
            }
        }


        View view = LayoutInflater.from(this).inflate(R.layout.voice_popupwindow_1, null);


        mPopWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopWindow.setFocusable(false);

  /*      mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE );*/
        view.findViewById(R.id.lanuage_nomal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                languageTv.setText("普通话");

                mPopWindow.dismiss();
            }
        });
        view.findViewById(R.id.lanuage_english).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
                languageTv.setText("英语");
                mPopWindow.dismiss();
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        mPopWindow.setOutsideTouchable(false);

        mPopWindow.update();
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        mPopWindow.setWidth(width-70);

        mPopWindow.showAtLocation(decorView,
                Gravity.BOTTOM, 0, popHigh + SizeUtils.dp2px(90));
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public Resources getResources() {
        Resources res =super.getResources();
        Configuration config = res.getConfiguration();
        if(fontSizeScale>0.5){
            config.fontScale= fontSizeScale;//1 设置正常字体大小的倍数
        }
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    /**
     * 要键盘弹出时，scrollView做滑动需，调用此方法
     *
     */
    protected void initKeyBoardListener(NestedScrollView scrollView) {
        this.mainScrollView = scrollView;
        this.editTexts = new ArrayList<>();
        findEditText(scrollView);
        setFoucesListener();
    }

    protected void setEditTextList(List<EditText> list,NestedScrollView scrollView){
        this.mainScrollView=scrollView;
        this.editTexts = new ArrayList<>();
        editTexts.addAll(list);
        setFoucesListener();
    }

    protected void x(NestedScrollView scrollView, int offset) {
        this.mainScrollView = scrollView;
        this.editTexts = new ArrayList<>();
        findEditText(scrollView);
        setFoucesListener();
        this.offset = offset;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        getContentView().addOnLayoutChangeListener(this);
    }

    public View getContentView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, final int bottom, int oldLeft
            , int oldTop, int oldRight, int oldBottom) { //获取屏幕高度
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        int keyHeight = screenHeight / 3;
        if (mainScrollView != null && normalCanScroll) {
            normalCanScroll = mainScrollView.canScrollVertically(1);
        }

        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起

        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            if (currentFocusEt != null) {
                int[] location = new int[2];
                currentFocusEt.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                int height = currentFocusEt.getHeight();
                y = y + height;
                if (y > bottom && mainScrollView != null) {
                    final int finalY = y;
                    if (normalCanScroll) {
                        scrollRunnable = () -> mainScrollView.scrollBy(0, finalY - bottom
                                + offset+SizeUtils.dp2px(90));
                        mainScrollView.postDelayed(scrollRunnable, 100);
                    } else {
                        //mainScrollView.scrollBy(0, finalY - bottom + offset+SizeUtils.dp2px(90));

                        scrollRunnable = new Runnable() {
                            @Override
                            public void run() {
                                mainScrollView.scrollBy(0, finalY - bottom
                                        +offset+SizeUtils.dp2px(90));
                            }
                        };
                        mainScrollView.postDelayed(scrollRunnable, 100);
                    }
                }
            }
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
        }
    }

    /**
     * 监听焦点获取当前的获取焦点的editText
     *
     * @param v 监听的view
     * @param hasFocus 是否获取到焦点
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && v instanceof EditText) {
            currentFocusEt = (EditText) v;
        }
    }

    /**
     * 找出当前页面的所有editText
     *
     * @param view 遍历View中的EditText加入集合
     */
    private void findEditText(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View v = viewGroup.getChildAt(i);
                findEditText(v);
            }
        } else if (view instanceof EditText) {
            editTexts.add((EditText) view);
        }
    }

    /**
     * 当前页面的所有editText设置焦点监听
     */
    private void setFoucesListener() {
        for (EditText et : editTexts) {
            et.setOnFocusChangeListener(this);
        }
    }
}
