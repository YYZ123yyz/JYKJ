package com.hyphenate.easeui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.EditVoiceActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VoiceInputDialog extends Dialog {


    public ProgressDialog mDialogProgress = null;

    private Context mContext;
    private ArrayList<String> strings;
    private OptionsPickerView optionPick;
    private LinearLayout inputLayout;
    private RelativeLayout allLayout;
    private TextView changeTv;
    private TextView etInput;
    private RelativeLayout partLayout;
    private SpeechRecognizer mIat;
    private RelativeLayout rlBack;

    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            LogUtils.e("SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                LogUtils.e("初始化失败，错误码：" + code);
            }
        }
    };
    private StringBuffer textBuffer;
    private TextView clearTv;
    private TextView sendTv;

    public VoiceInputDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        mContext = context;
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_voice_input);
        initSpeech();
        allLayout = (RelativeLayout) findViewById(R.id.layout_part_1);
        strings = new ArrayList<>();
        textBuffer = new StringBuffer();
        strings.add("普通话");
        strings.add("陕西方言");
        strings.add("粤语");
        initChangeLanguage();
        inputLayout = (LinearLayout) findViewById(R.id.layout_voice_input);
        changeTv = findViewById(R.id.choose_lang);
        etInput = findViewById(R.id.voice_text);
        partLayout = findViewById(R.id.part_0);
        clearTv = findViewById(R.id.tv_clear);
        sendTv = findViewById(R.id.tv_send);
        rlBack=findViewById(R.id.rl_back);
        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        changeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new OptionsPickerBuilder()
                hide();
                optionPick.show();
            }
        });
        ImageView inputPress = findViewById(R.id.input_press);
        inputPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击     0000   ");
                if (mIat.isListening()){
                    mIat.stopListening();
                }
            }
        });
        inputPress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtils.e("长按   43434   ");
                if (etInput.getText().toString().trim().length() == 0) {
                    partLayout.setVisibility(View.GONE);
                    etInput.setVisibility(View.VISIBLE);
                    if (textBuffer.toString().length()>0){
                        clearTv.setVisibility(View.VISIBLE);
                        sendTv.setVisibility(View.VISIBLE);
                    }
                }
                int ret = mIat.startListening(mRecognizerListener);
                return false;
            }
        });
        clearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textBuffer.setLength(0);
                etInput.setText("");
                etInput.setVisibility(View.GONE);
                partLayout.setVisibility(View.VISIBLE);
            }
        });
        sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etInput.getText().toString().trim())){
                    ToastUtils.showShort("请输入内容");
                    return;
                }
                EventBus.getDefault().post(new MessageBus(etInput.getText().toString().trim()));
                etInput.setVisibility(View.GONE);
                partLayout.setVisibility(View.VISIBLE);
            }
        });
        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etInput.getText().toString().trim())){
                    Intent intent = new Intent(mContext, EditVoiceActivity.class);
                    intent.putExtra("data",etInput.getText().toString());
                    mContext.startActivity(intent);
                }

            }
        });
    }

    private void initSpeech() {


        //初始化识别无UI识别对象
//使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);

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

//开始识别，并设置监听器
//        mIat.startListening(mRecognizerListener);
    }


    private void initChangeLanguage() {


        optionPick = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                show();
                changeTv.setText(strings.get(options1));
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {


            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("")//标题
                .setSubCalSize(15)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.parseColor("#00CD05"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show();
                    }
                })
                .build();
        optionPick.setPicker(strings);//添加数据源

    }

    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。

//            showTip(error.getPlainDescription(true));

        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {


            LogUtils.e(results.getResultString());

            /* printResult(results);*/

            String text = JsonParser.parseIatResult(results.getResultString());

            textBuffer.append(text);
            clearTv.setVisibility(View.VISIBLE);
            sendTv.setVisibility(View.VISIBLE);

            etInput.setText(textBuffer.toString());


           /* if (isLast & cyclic) {
                // TODO 最后的结果
                Message message = Message.obtain();
                message.what = 0x001;
                han.sendMessageDelayed(message, 100);
            }*/
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            ToastUtils.showShort("当前正在说话，音量大小：" + volume);

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

}
