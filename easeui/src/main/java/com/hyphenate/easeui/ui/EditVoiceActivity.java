package com.hyphenate.easeui.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.JsonParser;
import com.hyphenate.easeui.utils.MessageBus;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;


public class EditVoiceActivity extends BaseActivity implements View.OnClickListener{

    private ArrayList<EaseEmojiconGroupEntity> emojiconGroupList;
    private EditText editTextVoice;
    private Button emojiconButton;
    private EaseEmojiconMenu emojiconMenu;
    protected InputMethodManager inputManager;
    private Button voiceButton;
    private RelativeLayout voiceLayout;
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
    private TextView sendButton;
    private Button keywordButton;
    private TextView sendTv;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_text;
    }

    @Override
    protected void initView() {
        super.initView();
        emojiconMenu = findViewById(R.id.emojicon);
        editTextVoice = findViewById(R.id.edit_voice);
        emojiconButton = findViewById(R.id.btn_set_mode_voice);
        voiceButton = findViewById(R.id.btn_set_voice);
        voiceLayout = findViewById(R.id.voice_layout);
        sendButton = findViewById(R.id.btn_send);
        keywordButton = findViewById(R.id.btn_set_mode_keyboard);
        sendTv = findViewById(R.id.tv_send);
        sendTv.setOnClickListener(this);
        voiceButton.setOnClickListener(this);
        keywordButton.setOnClickListener(this);
        emojiconButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
        emojiconGroupList.add(new EaseEmojiconGroupEntity(R.drawable.ee_1,  Arrays.asList(EaseDefaultEmojiconDatas.getData())));
        emojiconMenu.init(emojiconGroupList);
        emojiconMenu.setEmojiciconClickListen(new EaseEmojiconMenu.EmojiciconClick() {
            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {

                editTextVoice.append(EaseSmileUtils.getSmiledText(context,emojicon.getEmojiText()));
            }

            @Override
            public void onDeleteImageClicked() {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                editTextVoice.dispatchKeyEvent(event);
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

                mIat.startListening(mRecognizerListener);
                return false;
            }
        });
        editTextVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordButton.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
                voiceLayout.setVisibility(View.GONE);
                sendButton.setVisibility(View.VISIBLE);
                emojiconButton.setVisibility(View.VISIBLE);
                voiceButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initSpeech();

        String data = getIntent().getStringExtra("data");
        editTextVoice.append(data);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_set_mode_voice) { //表情按钮
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                emojiconMenu.setVisibility(View.GONE);
            } else {
                KeyboardUtils.hideSoftInput(this);
                emojiconMenu.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.btn_send) {//发送
            if (TextUtils.isEmpty(editTextVoice.getText().toString().trim())){
                ToastUtils.showShort("请输入内容");
                return;
            }
            EventBus.getDefault().post(new MessageBus(editTextVoice.getText().toString().trim()));
            finish();
        } else if (id == R.id.tv_send){
            if (TextUtils.isEmpty(editTextVoice.getText().toString().trim())){
                ToastUtils.showShort("请输入内容");
                return;
            }
            EventBus.getDefault().post(new MessageBus(editTextVoice.getText().toString().trim()));
            finish();
        }else if (id == R.id.btn_set_voice) {//语音

            emojiconMenu.setVisibility(View.GONE);
            sendButton.setVisibility(View.GONE);
            emojiconButton.setVisibility(View.GONE);
            voiceButton.setVisibility(View.GONE);
            keywordButton.setVisibility(View.VISIBLE);
            KeyboardUtils.hideSoftInput(this);
            voiceLayout.setVisibility(View.VISIBLE);
        }else if (id == R.id.btn_set_mode_keyboard){ //键盘

            keywordButton.setVisibility(View.GONE);
            emojiconMenu.setVisibility(View.GONE);
            voiceLayout.setVisibility(View.GONE);
            sendButton.setVisibility(View.VISIBLE);
            emojiconButton.setVisibility(View.VISIBLE);
            voiceButton.setVisibility(View.VISIBLE);
            KeyboardUtils.showSoftInput();
        }
    }




    private void initSpeech() {


        //初始化识别无UI识别对象
//使用SpeechRecognizer对象，可根据回调消息自定义界面；
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

//开始识别，并设置监听器
//        mIat.startListening(mRecognizerListener);
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




            editTextVoice.append(text);


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
