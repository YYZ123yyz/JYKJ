package com.hyphenate.easeui.hyhd;

import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMCallSession;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.MediaSoundUtil;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


/**
 * 语音通话页面
 *
 */
public class VoiceCallActivity extends CallActivity implements OnClickListener {
    private LinearLayout comingBtnContainer;
    private Button hangupBtn;
    private LinearLayout refuseBtn;
    private LinearLayout answerBtn;
    private ImageView muteImage;
    private ImageView handsFreeImage;

    private boolean isMuteState;
    private boolean isHandsfreeState;

    private TextView callStateTextView;
    private boolean endCallTriggerByMe = false;
    private Chronometer chronometer;
    String st1;
    private RelativeLayout voiceContronlLayout; //声音控制 两个按钮
    private TextView netwrokStatusVeiw;
    private boolean monitor = false;

    private             int mVoiceTime;                //可拨打语音时长（单位：秒）
    private Handler mHandler;
    private TextView msgState;
    private LinearLayout hangUpLayout;
    private LinearLayout muitLayout;
    private LinearLayout noHandLayout;
    private ImageView ivMuit,ivNoHand;
    private String headUrl;
    private ImageView headView;
    private LinearLayout cancleCallLayout;
    private RelativeLayout sendingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            finish();
            return;
        }
        setContentView(R.layout.em_activity_voice_call);
        mVoiceTime = getIntent().getIntExtra(EaseConstant.EXTRA_VOICE_NUM, 0);
        if (mVoiceTime ==0)
            mVoiceTime = 1000000;
        DemoHelper.getInstance().isVoiceCalling = true;
        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(true);
        callType = 0;
        headView = findViewById(R.id.swing_card);
        comingBtnContainer = (LinearLayout) findViewById(R.id.linyout_is_accept);  //ll_coming_call
        refuseBtn = (LinearLayout) findViewById(R.id.linyout_refuse); //拒绝
        answerBtn = (LinearLayout) findViewById(R.id.linyout_accept);//接受
        hangupBtn = (Button) findViewById(R.id.btn_hangup_call); //挂断
        muteImage = (ImageView) findViewById(R.id.iv_mute);
        handsFreeImage = (ImageView) findViewById(R.id.iv_handsfree);
        callStateTextView = (TextView) findViewById(R.id.tv_call_state);
        TextView nickTextView = (TextView) findViewById(R.id.tv_nick);
//        TextView durationTextView = (TextView) findViewById(R.id.tv_calling_duration);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        voiceContronlLayout = (RelativeLayout) findViewById(R.id.linyout_is_calling);
        netwrokStatusVeiw = (TextView) findViewById(R.id.tv_network_status);
        msgState = (TextView) findViewById(R.id.msg_state);
        hangUpLayout = (LinearLayout) findViewById(R.id.layout_hang_up);
        muitLayout = (LinearLayout) findViewById(R.id.layout_muit);
        noHandLayout = (LinearLayout) findViewById(R.id.layout_no_hand);
        ivMuit =(ImageView)findViewById(R.id.iv_muit);
        ivNoHand =(ImageView)findViewById(R.id.iv_no_hand);
        cancleCallLayout = (LinearLayout) findViewById(R.id.layout_cancel_call);
        sendingLayout = (RelativeLayout) findViewById(R.id.layout_sending);

        refuseBtn.setOnClickListener(this);
        answerBtn.setOnClickListener(this);
        hangupBtn.setOnClickListener(this);
        muteImage.setOnClickListener(this);
        handsFreeImage.setOnClickListener(this);
        hangUpLayout.setOnClickListener(this);
        muitLayout.setOnClickListener(this);
        noHandLayout.setOnClickListener(this);
        cancleCallLayout.setOnClickListener(this);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        addCallStateListener();
        msgid = UUID.randomUUID().toString();

        username = getIntent().getStringExtra("username");
        nickName = getIntent().getStringExtra("nickName");
        isInComingCall = getIntent().getBooleanExtra("isComingCall", false);
        if (getIntent().hasExtra("headUrl")){
            headUrl = getIntent().getStringExtra("headUrl");
            Glide.with(this).load(headUrl).into(headView);
        }
        nickTextView.setText(nickName);
        if (!isInComingCall) {// outgoing call
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.em_outgoing, 1);

            comingBtnContainer.setVisibility(View.INVISIBLE);
            // TODO: 2020/11/4 0004 之前的挂断visible
            hangupBtn.setVisibility(View.GONE);
            st1 = getResources().getString(R.string.Are_connected_to_each_other);
            callStateTextView.setText(st1);
            handler.sendEmptyMessage(MSG_CALL_MAKE_VOICE);
            handler.postDelayed(new Runnable() {
                public void run() {
                    streamID = playMakeCallSounds();
                }
            }, 300);
        } else { // incoming call
            voiceContronlLayout.setVisibility(View.INVISIBLE);
            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();
        }
        final int MAKE_CALL_TIMEOUT = 50 * 1000;
        handler.removeCallbacks(timeoutHangup);
        handler.postDelayed(timeoutHangup, MAKE_CALL_TIMEOUT);
        initHandler();
    }


    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        /**
                         * 挂断通话
                         */
                        try {
                            EMClient.getInstance().callManager().endCall();
                        } catch (EMNoActiveCallException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(VoiceCallActivity.this,"语音时长已用尽",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    /**
     * set call state listener
     */
    void addCallStateListener() {
        callStateListener = new EMCallStateChangeListener() {

            @Override
            public void onCallStateChanged(CallState callState, final CallError error) {
                // Message msg = handler.obtainMessage();
                EMLog.d("EMCallManager", "onCallStateChanged:" + callState);
                switch (callState) {

                    case CONNECTING:
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                callStateTextView.setText(st1);
                            }
                        });
                        break;
                    case CONNECTED:
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
//                            String st3 = "已经和对方建立连接";
//                            callStateTextView.setText(st3);
                            }
                        });
                        break;

                    case ACCEPTED:
                        handler.removeCallbacks(timeoutHangup);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if (soundPool != null)
                                        soundPool.stop(streamID);
                                } catch (Exception e) {
                                }
                                if(!isHandsfreeState)
                                    closeSpeakerOn();
                                chronometer.setBase(SystemClock.elapsedRealtime());
                                // duration start
                                chronometer.start();
                                //show relay or direct call, for testing purpose
//                            ((TextView)findViewById(R.id.tv_is_p2p)).setText(EMClient.getInstance().callManager().isDirectCall()
//                                    ? R.string.direct_call : R.string.relay_call);
                                sendingLayout.setVisibility(View.GONE);
                                msgState.setVisibility(View.GONE);
                                voiceContronlLayout.setVisibility(View.VISIBLE);
                                chronometer.setVisibility(View.VISIBLE);
//                                chronometer.setBase(SystemClock.elapsedRealtime());
//                                // duration start
//                                chronometer.start();
                                String str4 = getResources().getString(R.string.In_the_call);
                                MediaSoundUtil mediaSoundUtil = new MediaSoundUtil(getApplicationContext());
                                mediaSoundUtil.stopPlay();
                                callStateTextView.setText(str4);
                                callingState = CallingState.NORMAL;
                                startMonitor();
                                // Start to watch the phone call state.
                                PhoneStateManager.get(VoiceCallActivity.this).addStateCallback(phoneStateCallback);
                                //启动定时器，监听剩余时间
                                final Timer timer = new Timer();
                                final TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {

                                        if (mVoiceTime <= 0)
                                        {
                                            //挂断电话
                                            mHandler.sendEmptyMessage(1);
                                            //结束计时器
                                            timer.cancel();

                                        }
                                        else
                                            mVoiceTime -= 1;
                                    }
                                };
                                timer.schedule(task, 0, 1000);
                            }
                        });
                        break;
                    case NETWORK_UNSTABLE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                netwrokStatusVeiw.setVisibility(View.VISIBLE);
                                if(error == CallError.ERROR_NO_DATA){
                                    netwrokStatusVeiw.setText("已经和对方建立连接");
                                }else{
                                    netwrokStatusVeiw.setText("网络不稳定");
                                }
                            }
                        });
                        break;
                    case NETWORK_NORMAL:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                netwrokStatusVeiw.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;
                    case VOICE_PAUSE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VOICE_PAUSE", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case VOICE_RESUME:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VOICE_RESUME", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case DISCONNECTED:
                        handler.removeCallbacks(timeoutHangup);
                        @SuppressWarnings("UnnecessaryLocalVariable") final CallError fError = error;
                        runOnUiThread(new Runnable() {
                            private void postDelayedCloseMsg() {
                                handler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("AAA", "CALL DISCONNETED");
                                                removeCallStateListener();

                                                // Stop to watch the phone call state.
                                                PhoneStateManager.get(VoiceCallActivity.this).removeStateCallback(phoneStateCallback);

                                                saveCallRecord();
                                                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                                                animation.setDuration(800);
                                                findViewById(R.id.root_layout).startAnimation(animation);
                                                finish();
                                            }
                                        });
                                    }
                                }, 200);
                            }

                            @Override
                            public void run() {
                                chronometer.stop();
                                callDruationText = chronometer.getText().toString();
                                String st1 = getResources().getString(R.string.Refused);
                                String st2 = getResources().getString(R.string.The_other_party_refused_to_accept);
                                String st3 = getResources().getString(R.string.Connection_failure);
                                String st4 = getResources().getString(R.string.The_other_party_is_not_online);
                                String st5 = getResources().getString(R.string.The_other_is_on_the_phone_please);

                                String st6 = getResources().getString(R.string.The_other_party_did_not_answer_new);
                                String st7 = getResources().getString(R.string.hang_up);
                                String st8 = getResources().getString(R.string.The_other_is_hang_up);

                                String st9 = getResources().getString(R.string.did_not_answer);
                                String st10 = getResources().getString(R.string.Has_been_cancelled);
                                String st11 = getResources().getString(R.string.hang_up);
                                String st12 = "service not enable";
                                String st13 = "service arrearages";
                                String st14 = "service forbidden";

                                if (fError == CallError.REJECTED) {
                                    callingState = CallingState.BEREFUSED;
                                    callStateTextView.setText(st2);
                                } else if (fError == CallError.ERROR_TRANSPORT) {
                                    callStateTextView.setText(st3);
                                } else if (fError == CallError.ERROR_UNAVAILABLE) {
                                    callingState = CallingState.OFFLINE;
                                    callStateTextView.setText(st4);
                                } else if (fError == CallError.ERROR_BUSY) {
                                    callingState = CallingState.BUSY;
                                    callStateTextView.setText(st5);
                                } else if (fError == CallError.ERROR_NORESPONSE) {
                                    callingState = CallingState.NO_RESPONSE;
                                    callStateTextView.setText(st6);
                                } else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED){
                                    callingState = CallingState.VERSION_NOT_SAME;
                                    callStateTextView.setText("通话协议版本不一致");
                                } else if(fError == CallError.ERROR_SERVICE_NOT_ENABLE) {
                                    callingState = CallingState.SERVICE_NOT_ENABLE;
                                    callStateTextView.setText(st12);
                                } else if(fError == CallError.ERROR_SERVICE_ARREARAGES) {
                                    callingState = CallingState.SERVICE_ARREARAGES;
                                    callStateTextView.setText(st13);
                                } else if(fError == CallError.ERROR_SERVICE_FORBIDDEN) {
                                    callingState = CallingState.SERVICE_NOT_ENABLE;
                                    callStateTextView.setText(st14);
                                }
                                else {
                                    if (isRefused) {
                                        callingState = CallingState.REFUSED;
                                        callStateTextView.setText(st1);
                                    }
                                    else if (isAnswered) {
                                        callingState = CallingState.NORMAL;
                                        if (endCallTriggerByMe) {
//                                        callStateTextView.setText(st7);
                                        } else {
                                            callStateTextView.setText(st8);
                                        }
                                    } else {
                                        if (isInComingCall) {
                                            callingState = CallingState.UNANSWERED;
                                            callStateTextView.setText(st9);
                                        } else {
                                            if (callingState != CallingState.NORMAL) {
                                                callingState = CallingState.CANCELLED;
                                                callStateTextView.setText(st10);
                                            }else {
                                                callStateTextView.setText(st11);
                                            }
                                        }
                                    }
                                }
                                postDelayedCloseMsg();
                            }

                        });

                        break;

                    default:
                        break;
                }

            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener);
    }

    void removeCallStateListener() {
        EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener);
    }

    PhoneStateManager.PhoneStateCallback phoneStateCallback = new PhoneStateManager.PhoneStateCallback() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:   // 电话响铃
                    break;
                case TelephonyManager.CALL_STATE_IDLE:      // 电话挂断
                    // resume current voice conference.
                    if (isMuteState) {
                        try {
                            EMClient.getInstance().callManager().resumeVoiceTransfer();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:   // 来电接通 或者 去电，去电接通  但是没法区分
                    // pause current voice conference.
                    if (!isMuteState) {
                        try {
                            EMClient.getInstance().callManager().pauseVoiceTransfer();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.linyout_refuse) {
            isRefused = true;
            refuseBtn.setEnabled(false);
            handler.sendEmptyMessage(MSG_CALL_REJECT);

        } else if (i == R.id.linyout_accept) {
            answerBtn.setEnabled(false);
            closeSpeakerOn();
            callStateTextView.setText("正在接听...");
            msgState.setVisibility(View.INVISIBLE);
            comingBtnContainer.setVisibility(View.INVISIBLE);
            // TODO: 2020/11/4 0004 之前的挂断visible
            hangupBtn.setVisibility(View.GONE);
            voiceContronlLayout.setVisibility(View.VISIBLE);
            handler.sendEmptyMessage(MSG_CALL_ANSWER);

        } else if (i == R.id.btn_hangup_call  ||i ==R.id.layout_cancel_call) {   //挂断
            hangupBtn.setEnabled(false);
            chronometer.stop();
            endCallTriggerByMe = true;
            callStateTextView.setText(getResources().getString(R.string.hanging_up));
            handler.sendEmptyMessage(MSG_CALL_END);

        }else if (i ==R.id.layout_hang_up){
            hangupBtn.setEnabled(false);
            chronometer.stop();
            endCallTriggerByMe = true;
            handler.sendEmptyMessage(MSG_CALL_END);
        }else if (i ==R.id.layout_no_hand){

            if (isHandsfreeState) {
//                handsFreeImage.setImageResource(R.mipmap.em_icon_speaker_normal);
                ivNoHand.setSelected(false);
                closeSpeakerOn();
                isHandsfreeState = false;
            } else {
//                handsFreeImage.setImageResource(R.mipmap.em_icon_speaker_on);
                ivNoHand.setSelected(true);
                openSpeakerOn();
                isHandsfreeState = true;
            }
        }else if (i == R.id.layout_muit){
            if (isMuteState) {
//                muteImage.setImageResource(R.mipmap.em_icon_mute_normal);
                ivMuit.setSelected(false);
                try {
                    EMClient.getInstance().callManager().resumeVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = false;
            } else {
//                muteImage.setImageResource(R.mipmap.em_icon_mute_on);
                ivMuit.setSelected(true);
                try {
                    EMClient.getInstance().callManager().pauseVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = true;
            }
        }
        else if (i == R.id.iv_mute) {
            if (isMuteState) {
                muteImage.setImageResource(R.mipmap.em_icon_mute_normal);
                try {
                    EMClient.getInstance().callManager().resumeVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = false;
            } else {
                muteImage.setImageResource(R.mipmap.em_icon_mute_on);
                try {
                    EMClient.getInstance().callManager().pauseVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = true;
            }

        } else if (i == R.id.iv_handsfree) {
            if (isHandsfreeState) {
                handsFreeImage.setImageResource(R.mipmap.em_icon_speaker_normal);
                closeSpeakerOn();
                isHandsfreeState = false;
            } else {
                handsFreeImage.setImageResource(R.mipmap.em_icon_speaker_on);
                openSpeakerOn();
                isHandsfreeState = true;
            }

        } else {
        }
    }

    @Override
    protected void onDestroy() {
        DemoHelper.getInstance().isVoiceCalling = false;
        stopMonitor();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        callDruationText = chronometer.getText().toString();
    }

    /**
     * for debug & testing, you can remove this when release
     */
    void startMonitor(){
        monitor = true;
        EMCallSession callSession = EMClient.getInstance().callManager().getCurrentCallSession();
        final boolean isRecord = callSession.isRecordOnServer();
        final String serverRecordId = callSession.getServerRecordId();

        EMLog.e(TAG, "server record: " + isRecord );
        if (isRecord) {
            EMLog.e(TAG, "server record id: " + serverRecordId);
        }

        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        String status = getApplicationContext().getString(EMClient.getInstance().callManager().isDirectCall()
//                                ? R.string.direct_call : R.string.relay_call);
//                        status += " record? " + isRecord;
//                        status += " id: " + serverRecordId;
//
//                        ((TextView)findViewById(R.id.tv_is_p2p)).setText(status);
                    }
                });
                while(monitor){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }, "CallMonitor").start();
    }

    void stopMonitor() {

    }

}