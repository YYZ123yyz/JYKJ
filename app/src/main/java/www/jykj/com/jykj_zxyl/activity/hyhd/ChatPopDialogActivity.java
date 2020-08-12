package www.jykj.com.jykj_zxyl.activity.hyhd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.*;
import com.hyphenate.chat.adapter.EMAChatRoomManagerListener;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.entity.UpdMyClinicDetailByOrderTreatmentLimitNum;
import com.hyphenate.easeui.hyhd.VideoCallActivity;
import com.hyphenate.easeui.hyhd.VoiceCallActivity;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseCompat;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.ui.EaseChatRoomListener;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.ExtEaseUtils;
import com.hyphenate.easeui.widget.*;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.util.SwipeAnimationController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.hyphenate.easeui.EaseConstant.CHATTYPE_GROUP;

public abstract class ChatPopDialogActivity extends AppCompatActivity implements EMMessageListener {
    protected static final String TAG = "EaseChatFragment";
    protected EaseTitleBar titleBar;
    protected InputMethodManager inputMethodManager;
    protected static final int REQUEST_CODE_MAP = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    protected static final int REQUEST_CODE_DING_MSG = 4;
    protected static final int REQUEST_CODE_VIDEO = 5;
    protected static final int REQUEST_CODE_CALL = 6;
    protected static final int REQUEST_CODE_FILE = 7;


    protected static final int MSG_TYPING_BEGIN = 0;
    protected static final int MSG_TYPING_END = 1;

    protected static final String ACTION_TYPING_BEGIN = "TypingBegin";
    protected static final String ACTION_TYPING_END = "TypingEnd";

    protected static final int TYPING_SHOW_TIME = 5000;
    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected int chatType;
    protected String toChatUsername;
    protected String toChatUsernameName;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;

    protected EMConversation conversation;

    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;

    protected Handler handler = new Handler();
    protected File cameraFile;
    protected File videoFile;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;

    private View kickedForOfflineLayout;

    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected int pagesize = 20;
    protected GroupListener groupListener;
    protected ChatRoomListener chatRoomListener;
    protected EMMessage contextMenuMessage;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;

    static final int ITEM_CALL = 3;        //语音通话
    static final int ITEM_VIDEO = 4;        //视频
    static final int ITEM_WJ = 5;        //文件


    protected int[] itemStrings = { com.hyphenate.easeui.R.string.attach_take_pic, com.hyphenate.easeui.R.string.attach_picture,
            com.hyphenate.easeui.R.string.attach_voice_call, com.hyphenate.easeui.R.string.attach_video, com.hyphenate.easeui.R.string.attach_file};
    protected int[] itemdrawables = { com.hyphenate.easeui.R.mipmap.hyhd_pz, com.hyphenate.easeui.R.mipmap.hyhd_tp,
            com.hyphenate.easeui.R.mipmap.hyhd_yy, com.hyphenate.easeui.R.mipmap.hyhd_sp, com.hyphenate.easeui.R.mipmap.hyhd_wj };
    protected int[] itemIds = { ITEM_TAKE_PICTURE, ITEM_PICTURE,ITEM_CALL,ITEM_VIDEO,ITEM_WJ };
    private boolean isMessageListInited;
    protected MyItemClickListener extendMenuItemClickListener;
    protected boolean isRoaming = false;
    private ExecutorService fetchQueue;
    // to handle during-typing actions.
    private Handler typingHandler = null;
    // "正在输入"功能的开关，打开后本设备发送消息将持续发送cmd类型消息通知对方"正在输入"
    private boolean turnOnTyping;

    private             String                  mChatType;
    private             int                     mMessageNum;                //可发送消息数量
    private             Long                     mVoiceTime;                //可拨打语音时长（单位：秒）
    private             Long                     mVedioTime;                //可拨打视频时长
    private             String                   mStopDate;                 //停止时间
    private             String                  loginDoctorPosition;
    private             String                  operDoctorCode;
    private             String                  operDoctorName;
    private             String                  orderCode;
    private ChatPopDialogActivity myActivity = null;
    private SwipeAnimationController mSwipeAnimationController;
    protected RelativeLayout chatViewLayout;
    protected JYKJApplication mApp;
    public abstract void createChat();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        inputMethodManager = (InputMethodManager) myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    public UpdMyClinicDetailByOrderTreatmentLimitNum updMyClinicDetailByOrderTreatmentLimitNum;
    protected void initChat(Bundle params){
        fragmentArgs = params;
        chatType = fragmentArgs.getInt(EaseConstant.CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);

        //消息数量限制
        mMessageNum = fragmentArgs.getInt(EaseConstant.EXTRA_MESSAGE_NUM, Integer.MAX_VALUE);
        mVoiceTime = fragmentArgs.getLong(EaseConstant.EXTRA_VOICE_NUM, Integer.MAX_VALUE);
        mVedioTime = fragmentArgs.getLong(EaseConstant.EXTRA_VEDIO_NUM,Integer.MAX_VALUE);
        mStopDate = fragmentArgs.getString("date");
        loginDoctorPosition = fragmentArgs.getString("loginDoctorPosition","");
        operDoctorCode = fragmentArgs.getString("operDoctorCode","");
        operDoctorName = fragmentArgs.getString("operDoctorName","");
        orderCode = fragmentArgs.getString("orderCode","");
        // userId you are chat with or group id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        toChatUsernameName = fragmentArgs.getString(EaseConstant.EXTRA_USER_NAME);
        mChatType = fragmentArgs.getString("chatType");
        itemStrings = new int[]{ R.string.attach_take_pic, R.string.attach_picture,
                R.string.attach_voice_call,R.string.attach_video,R.string.attach_file};
        itemdrawables = new int[]{ R.mipmap.hyhd_pz, R.mipmap.hyhd_tp,
                R.mipmap.hyhd_yy, R.mipmap.hyhd_sp,R.mipmap.hyhd_wj };
        itemIds = new int[]{ ITEM_TAKE_PICTURE, ITEM_PICTURE,ITEM_CALL,ITEM_VIDEO,ITEM_WJ };
        if ("twjz".equals(mChatType))
        {
            itemStrings = new int[]{ R.string.attach_take_pic, R.string.attach_picture};
            itemdrawables = new int[]{ R.mipmap.hyhd_pz, R.mipmap.hyhd_tp};
            itemIds = new int[]{ ITEM_TAKE_PICTURE, ITEM_PICTURE };
        }
        if ("dhjz".equals(mChatType))
        {
            itemStrings = new int[]{R.string.attach_voice_call};
            itemdrawables = new int[]{ R.mipmap.hyhd_yy};
            itemIds = new int[]{ ITEM_CALL };
        }

        if ("spjz".equals(mChatType))
        {
            itemStrings = new int[]{R.string.attach_video};
            itemdrawables = new int[]{ R.mipmap.hyhd_sp};
            itemIds = new int[]{ ITEM_VIDEO };
        }
        this.turnOnTyping = turnOnTyping();
    }
    /**
     * init view
     */
    protected void initChatView() {
        // hold to record voice
        //noinspection ConstantConditions
        chatViewLayout = (RelativeLayout)findViewById(R.id.chat_layout);
        mSwipeAnimationController = new SwipeAnimationController(myActivity);
        mSwipeAnimationController.setAnimationView(chatViewLayout);
        //noinspection ConstantConditions
        titleBar = (EaseTitleBar) findViewById(R.id.title_bar);
        voiceRecorderView = (EaseVoiceRecorderView)findViewById(R.id.voice_recorder);

        // message list layout
        messageList = (EaseChatMessageList)findViewById(R.id.message_list);
        //if(chatType != EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(true);
//        messageList.setAvatarShape(1);
        listView = messageList.getListView();

        kickedForOfflineLayout =findViewById(R.id.layout_alert_kicked_off);
        kickedForOfflineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChatRoomViewCreation();
            }
        });

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu)findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {
                // send action:TypingBegin cmd msg.
                typingHandler.sendEmptyMessage(MSG_TYPING_BEGIN);
            }

            @Override
            public void onSendMessage(String content) {
                sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) myActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        myActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (isRoaming) {
            fetchQueue = Executors.newSingleThreadExecutor();
        }

        // to handle during-typing actions.
        typingHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_TYPING_BEGIN: // Notify typing start

                        if (!turnOnTyping) return;

                        // Only support single-chat type conversation.
                        if (chatType != EaseConstant.CHATTYPE_SINGLE)
                            return;

                        if (hasMessages(MSG_TYPING_END)) {
                            // reset the MSG_TYPING_END handler msg.
                            removeMessages(MSG_TYPING_END);
                        } else {
                            // Send TYPING-BEGIN cmd msg
                            EMMessage beginMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                            EMCmdMessageBody body = new EMCmdMessageBody(ACTION_TYPING_BEGIN);
                            // Only deliver this cmd msg to online users
                            body.deliverOnlineOnly(true);
                            beginMsg.addBody(body);
                            beginMsg.setTo(toChatUsername);
                            EMClient.getInstance().chatManager().sendMessage(beginMsg);
                        }

                        sendEmptyMessageDelayed(MSG_TYPING_END, TYPING_SHOW_TIME);
                        break;
                    case MSG_TYPING_END:

                        if (!turnOnTyping) return;

                        // Only support single-chat type conversation.
                        if (chatType != EaseConstant.CHATTYPE_SINGLE)
                            return;

                        // remove all pedding msgs to avoid memory leak.
                        removeCallbacksAndMessages(null);
                        // Send TYPING-END cmd msg
                        EMMessage endMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
                        EMCmdMessageBody body = new EMCmdMessageBody(ACTION_TYPING_END);
                        // Only deliver this cmd msg to online users
                        body.deliverOnlineOnly(true);
                        endMsg.addBody(body);
                        endMsg.setTo(toChatUsername);
                        EMClient.getInstance().chatManager().sendMessage(endMsg);
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            }
        };

    }

    protected boolean turnOnTyping() {
        return false;
    }

    protected void joinChatroom(){
        if(chatType==EaseConstant.CHATTYPE_CHATROOM){
            EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

                @Override
                public void onSuccess(EMChatRoom value) {
                    //加入聊天室成功
                    Log.i("roomtab","success");
                }

                @Override
                public void onError(final int error, String errorMsg) {
                    //加入聊天室失败
                    Log.i("roomtab","failure");
                }
            });
        }
    }

    protected void setUpView() {
        titleBar.setTitle(toChatUsernameName);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            if(EaseUserUtils.getUserInfo(toChatUsername) != null){
                EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);
                if (user != null) {
                    titleBar.setTitle(user.getNickname());
                }
            }
            titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);
        } else {
            titleBar.setRightImageResource(R.drawable.ease_to_group_details_normal);
            if (chatType == CHATTYPE_GROUP) {
                //group chat
                EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
                if (group != null)
                    titleBar.setTitle(group.getGroupName());
                // listen the event that user moved out group or group is dismissed
                groupListener = new GroupListener();
                EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
            } else {
                chatRoomListener = new ChatRoomListener();
                EMClient.getInstance().chatroomManager().addChatRoomChangeListener(chatRoomListener);
                onChatRoomViewCreation();
            }

        }


        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    emptyHistory();
                } else {
                    toGroupDetails();
                }
            }
        });

        setRefreshLayoutListener();

        // show forward message if the message is not null
        String forward_msg_id = getIntent().getExtras().getString("forward_msg_id");
        if (forward_msg_id != null) {
            forwardMessage(forward_msg_id);
        }
    }

    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void registerExtendMenuItem(){
        for(int i = 0; i < itemStrings.length; i++){
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    protected void onConversationInit(){
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number

        if (!isRoaming) {
            final List<EMMessage> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            }
        } else {
            fetchQueue.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().chatManager().fetchHistoryMessages(
                                toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize, "");
                        final List<EMMessage> msgs = conversation.getAllMessages();
                        int msgCount = msgs != null ? msgs.size() : 0;
                        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                            String msgId = null;
                            if (msgs != null && msgs.size() > 0) {
                                msgId = msgs.get(0).getMsgId();
                            }
                            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
                        }
                        messageList.refreshSelectLast();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected void onMessageListInit(){
        messageList.init(toChatUsername, chatType, chatFragmentHelper != null ?
                chatFragmentHelper.onSetCustomChatRowProvider() : null);
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        isMessageListInited = true;
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public boolean onResendClick(final EMMessage message) {
                EMLog.i(TAG, "onResendClick");
                new EaseAlertDialog(myActivity, R.string.resend, R.string.confirm_resend, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (!confirmed) {
                            return;
                        }
                        message.setStatus(EMMessage.Status.CREATE);
                        sendMessage(message);
                    }
                }, true).show();
                return true;
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if(chatFragmentHelper == null){
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);
            }

            @Override
            public void onMessageInProgress(EMMessage message) {
                message.setMessageStatusCallback(messageStatusCallback);
            }
        });
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (!isRoaming) {
                            loadMoreLocalMessage();
                        } else {
                            loadMoreRoamingMessages();
                        }
                    }
                }, 600);
            }
        });
    }

    private void loadMoreLocalMessage() {
        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
            List<EMMessage> messages;
            try {
                messages = conversation.loadMoreMsgFromDB(conversation.getAllMessages().size() == 0 ? "" : conversation.getAllMessages().get(0).getMsgId(),
                        pagesize);
            } catch (Exception e1) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            if (messages.size() > 0) {
                messageList.refreshSeekTo(messages.size() - 1);
                if (messages.size() != pagesize) {
                    haveMoreData = false;
                }
            } else {
                haveMoreData = false;
            }

            isloading = false;
        } else {
            Toast.makeText(myActivity, getResources().getString(R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadMoreRoamingMessages() {
        if (!haveMoreData) {
            Toast.makeText(myActivity, getResources().getString(R.string.no_more_messages),
                    Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        if (fetchQueue != null) {
            fetchQueue.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<EMMessage> messages = conversation.getAllMessages();
                        EMClient.getInstance().chatManager().fetchHistoryMessages(
                                toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize,
                                (messages != null && messages.size() > 0) ? messages.get(0).getMsgId() : "");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    } finally {
                        if (myActivity != null) {
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadMoreLocalMessage();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_MAP) { // location
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    sendLocationMessage(latitude, longitude, locationAddress);
                } else {
                    Toast.makeText(myActivity, R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == REQUEST_CODE_DING_MSG) { // To send the ding-type msg.
                String msgContent = data.getStringExtra("msg");
                EMLog.i(TAG, "To send the ding-type msg, content: " + msgContent);
                // Send the ding-type msg.
                EMMessage dingMsg = EaseDingMessageHelper.get().createDingMessage(toChatUsername, msgContent);
                sendMessage(dingMsg);
            }
            else if (requestCode == REQUEST_CODE_VIDEO) { // capture new image
                if (videoFile != null && videoFile.exists())
                    sendVideoMessage(videoFile.getAbsolutePath(),getVideoPhoto(videoFile.getAbsolutePath()),
                            Integer.parseInt(getVideoDuration(videoFile.getAbsolutePath())));
            } else if (requestCode == REQUEST_CODE_VIDEO) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            }
            else if (requestCode == REQUEST_CODE_FILE) { // send local image
                if (data != null) {
                    Uri selectedFile = data.getData();
                    if (selectedFile != null) {
                        sendFileByUri(selectedFile);
                    }
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(isMessageListInited)
            messageList.refresh();
        EaseUI.getInstance().pushActivity(myActivity);
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);

        if(chatType == CHATTYPE_GROUP){
            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);

        // remove activity from foreground activity list
        EaseUI.getInstance().popActivity(myActivity);

        // Remove all padding actions in handler
        handler.removeCallbacksAndMessages(null);
        if(null!=typingHandler) {
            typingHandler.sendEmptyMessage(MSG_TYPING_END);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (groupListener != null) {
            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
        }

        if (chatRoomListener != null) {
            EMClient.getInstance().chatroomManager().removeChatRoomListener(chatRoomListener);
        }

        if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
        }
    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            myActivity.finish();
            if(chatType == CHATTYPE_GROUP){
                EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
                EaseAtMessageHelper.get().cleanToAtUserList();
            }
            if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
            }
        }
    }

    protected void onChatRoomViewCreation() {
        final ProgressDialog pd = ProgressDialog.show(myActivity, "", "Joining......");
        EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

            @Override
            public void onSuccess(final EMChatRoom value) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(myActivity.isFinishing() || !toChatUsername.equals(value.getId()))
                            return;
                        pd.dismiss();
                        EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
                        if (room != null) {
                            titleBar.setTitle(room.getName());
                            EMLog.d(TAG, "join room success : " + room.getName());
                        } else {
                            titleBar.setTitle(toChatUsername);
                        }
                        onConversationInit();
                        onMessageListInit();

                        // Dismiss the click-to-rejoin layout.
                        kickedForOfflineLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(final int error, String errorMsg) {
                // TODO Auto-generated method stub
                EMLog.d(TAG, "join room failure : " + error);
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                myActivity.finish();
            }
        });
    }

    Map msgmap = new HashMap();

    public abstract void showMessages(EMMessage paramMessage);

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        for (EMMessage message : messages) {
            String username = null;
            // group message
            if (message.direct() == EMMessage.Direct.SEND) {
                username = message.getTo();
            } else if (message.direct() == EMMessage.Direct.RECEIVE){
                // single chat message
                username = message.getFrom();
            }

            // if the message is for current conversation
            if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername) || message.conversationId().equals(toChatUsername)) {
                messageList.refreshSelectLast();
                conversation.markMessageAsRead(message.getMsgId());
            }
            if(null!=username && ""!=username){
                if(!msgmap.containsKey(username)){
                    msgmap.put(username,"1");
                    showMessages(message);
                }
            }

            EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
        }
    }


    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        for (final EMMessage msg : messages) {
            final EMCmdMessageBody body = (EMCmdMessageBody) msg.getBody();
            EMLog.i(TAG, "Receive cmd message: " + body.action() + " - " + body.isDeliverOnlineOnly());
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ACTION_TYPING_BEGIN.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                        titleBar.setTitle(getString(R.string.alert_during_typing));
                    } else if (ACTION_TYPING_END.equals(body.action()) && msg.getFrom().equals(toChatUsername)) {
                        titleBar.setTitle(toChatUsername);
                    }
                }
            });
        }
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onGroupMessageRead(List<EMGroupReadAck> list) {
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onReadAckForGroupMessageUpdated() {
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {
        if(isMessageListInited) {
            messageList.refresh();
        }
    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    /**
     * 返回图文消息剩余量
     * @return
     */
    public int getMessageNum() {
        return mMessageNum;
    }
    /**
     * input @
     * @param username
     */
    protected void inputAtUsername(String username){
        inputAtUsername(username, true);
    }

    /**
     * input @
     * @param username
     */
    protected void inputAtUsername(String username, boolean autoAddAtSymbol){
        if(EMClient.getInstance().getCurrentUser().equals(username) ||
                chatType != CHATTYPE_GROUP){
            return;
        }
        EaseAtMessageHelper.get().addAtUser(username);
        EaseUser user = EaseUserUtils.getUserInfo(username);
        if (user != null){
            username = user.getNickname();
        }
        if(autoAddAtSymbol)
            inputMenu.insertText("@" + username + " ");
        else
            inputMenu.insertText(username + " ");
    }

    /**
     * listen the group event
     *
     */
    class GroupListener extends EaseGroupListener {

        @Override
        public void onUserRemoved(final String groupId, String groupName) {
            myActivity.runOnUiThread(new Runnable() {

                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        makeText(myActivity, R.string.you_are_group, LENGTH_LONG).show();
                        myActivity.finish();

                    }
                }
            });
        }

        @Override
        public void onGroupDestroyed(final String groupId, String groupName) {
            // prompt group is dismissed and finish this activity
            myActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (toChatUsername.equals(groupId)) {
                        makeText(myActivity, R.string.the_current_group_destroyed, LENGTH_LONG).show();
                        myActivity.finish();

                    }
                }
            });
        }
    }

    /**
     * listen chat room event
     */
    class ChatRoomListener extends EaseChatRoomListener {

        @Override
        public void onChatRoomDestroyed(final String roomId, final String roomName) {
            myActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (roomId.equals(toChatUsername)) {
                        Toast.makeText(myActivity, R.string.the_current_chat_room_destroyed, Toast.LENGTH_LONG).show();
                        myActivity.finish();
                    }
                }
            });
        }

        @Override
        public void onRemovedFromChatRoom(final int reason, final String roomId, final String roomName, final String participant) {
            myActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (roomId.equals(toChatUsername)) {
                        if (reason == EMAChatRoomManagerListener.BE_KICKED) {
                            Toast.makeText(myActivity, R.string.quiting_the_chat_room, Toast.LENGTH_LONG).show();
                            if (myActivity.isFinishing()) {
                                myActivity.finish();
                            }
                        } else { // BE_KICKED_FOR_OFFLINE
                            // Current logged in user be kicked out by server for current user offline,
                            // show disconnect title bar, click to rejoin.
                            Toast.makeText(myActivity, "User be kicked for offline", Toast.LENGTH_SHORT).show();
                            kickedForOfflineLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }



        @Override
        public void onMemberJoined(final String roomId, final String participant) {
            if (roomId.equals(toChatUsername)) {
                myActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(myActivity, "member join:" + participant, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        @Override
        public void onMemberExited(final String roomId, final String roomName, final String participant) {
            if (roomId.equals(toChatUsername)) {
                myActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(myActivity, "member exit:" + participant, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }


    }

    protected EaseChatFragmentHelper chatFragmentHelper;
    public void setChatFragmentHelper(EaseChatFragmentHelper chatFragmentHelper){
        this.chatFragmentHelper = chatFragmentHelper;
    }

    public interface EaseChatFragmentHelper{
        /**
         * set message attribute
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * enter to chat detail
         */
        void onEnterToChatDetails();

        /**
         * on avatar clicked
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * on avatar long pressed
         * @param username
         */
        void onAvatarLongClick(String username);

        /**
         * on message bubble clicked
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * on message bubble long pressed
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * on extend menu item clicked, return true if you want to override
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * on set custom chat row provider
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }

    /**
     * 获取当前系统时间，并以年-月-日 时:分:秒的格式输出
     * @return
     */
    public static String getCurrentFormart(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 两个【日期字符串】进行比较
     * 对比精度：年、月、日、时、分、秒
     *
     * 【日期1】 在 【日期2】 之前，返回true；【1<2  true】
     * 【日期1】 在 【日期2】 之后，返回false；【1>2  false】
     * 【日期1】 和 【日期2】 相等，返回false；【1=2  false】
     *
     * @param date1  待比对的日期类型字符串
     * @param date2  待比对的日期类型字符串
     * @return   返回比对结果
     */
    public static boolean isCompareDateTime(String date1, String date2) {
        boolean flag = false;

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //日期【String】转换为【Date】
            if(null==date1 || date2.length()==0 || date2==null || date2.length()==0){
                return true;
            }
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            /*
             * java.util.Date.before(Date when) 方法检查此日期是否在指定日期date之前
             * 参数:  when -- 要比较的日期
             * 返回值： true如果Date对象在when表示的时刻之前，否则为false。
             *
             * date1 在 date2 之前，返回true；【1<2  true】
             * date1 在 date2 之后，返回false；【1>2  false】
             * date1 和 date2 相等，返回false；【1=2  false】
             * */
            flag = dt1.before(dt2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Method Error：public static boolean isCompareDateTime(String date1, String date2) ==> " + e.getMessage());
            return false;
        }
        return flag;
    }

    /**
     * handle the click event for extend menu
     *
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {
            if(chatFragmentHelper != null){
                if(chatFragmentHelper.onExtendMenuItemClick(itemId, view)){
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    if (!isCompareDateTime(getCurrentFormart(),mStopDate))
                    {
                        Toast.makeText(myActivity,"图文消息次数已用尽",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
                    {
                        if (mMessageNum <= 0)
                        {
                            Toast.makeText(myActivity,"图文消息次数已用尽",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
                    {
                        if (mMessageNum <= 0)
                        {
                            Toast.makeText(myActivity,"图文消息次数已用尽",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    selectPicFromLocal();
                    break;
//            case ITEM_LOCATION:
//                startActivityForResult(new Intent(myActivity, EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
//                break;
                case ITEM_VIDEO:
//                selectVideo();
                    //视频通话就是跳转VideoCallActivity
                    if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
                    {
                        if (mVedioTime <= 0)
                        {
                            Toast.makeText(myActivity,"视频时长已用尽",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    startActivity(new Intent(myActivity, VideoCallActivity.class).putExtra("username", toChatUsername)
                            .putExtra("isComingCall", false).putExtra(EaseConstant.EXTRA_VEDIO_NUM, mVedioTime));
                    break;
                case ITEM_CALL:
                    /**
                     * 拨打语音电话
                     * @param to
                     * @throws EMServiceNotReadyException
                     */
//               startActivity(new Intent(myActivity,VoiceCallActivity.class));
                    //视频通话就是跳转VideoCallActivity
//                if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
//                {
//                    if (mVoiceTime <= 0)
//                    {
//                        Toast.makeText(myActivity,"语音时长已用尽",Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                }
                    startActivity(new Intent(myActivity, VoiceCallActivity.class).putExtra("username", toChatUsername)
                            .putExtra("isComingCall", false)
                            .putExtra("nickName", toChatUsernameName)
                            .putExtra(EaseConstant.EXTRA_VOICE_NUM, mVoiceTime));
                    break;

                case ITEM_WJ:
                    //调用系统文件管理器打开指定路径目录
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, REQUEST_CODE_FILE);
                    break;
                default:
                    break;
            }
        }

    }

    //send message
    protected void sendTextMessage(String content) {
        if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
        {
            if (mMessageNum == -1)

                //如果次数用完，则停止发送
                if ( mMessageNum == -1 != mMessageNum <= 0)
                {
                    Toast.makeText(myActivity,"图文消息次数已用完",Toast.LENGTH_LONG).show();
                    return;
                }
        }
        if(EaseAtMessageHelper.get().containsAtUsername(content)){
            sendAtMessage(content);
        }else{
            EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
            sendMessage(message);
        }
    }

    /**
     * send @ message, only support group chat message
     * @param content
     */
    @SuppressWarnings("ConstantConditions")
    private void sendAtMessage(String content){
        if(chatType != CHATTYPE_GROUP){
            EMLog.e(TAG, "only support group chat message");
            return;
        }
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
        if(EMClient.getInstance().getCurrentUser().equals(group.getOwner()) && EaseAtMessageHelper.get().containsAtAll(content)){
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL);
        }else {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG,
                    EaseAtMessageHelper.get().atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)));
        }
        sendMessage(message);

    }

    protected void sendBigExpressionMessage(String name, String identityCode){
        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
        sendMessage(message);
    }

    protected void sendVoiceMessage(String filePath, int length) {
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        sendMessage(message);
    }

    protected void sendImageMessage(String imagePath) {
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
        sendMessage(message);
    }

    protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        sendMessage(message);
    }

    protected void sendFileMessage(String filePath) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        sendMessage(message);
    }
    protected void sendMessage(EMMessage message){
        if (message == null) {
            return;
        }
        String theNickName = ExtEaseUtils.getInstance().getNickName();
        String theImageUrl = ExtEaseUtils.getInstance().getImageUrl();
        theNickName = (null==theNickName)?"":theNickName;
        theImageUrl = (null==theImageUrl)?"":theImageUrl;
        if (theNickName.length()>0) {
            message.setAttribute("nickName", theNickName);
        }
        if (theImageUrl.length()>0) {
            message.setAttribute("imageUrl", theImageUrl);
        }
        if(chatFragmentHelper != null){
            //set extension
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        if (chatType == CHATTYPE_GROUP){
            message.setChatType(EMMessage.ChatType.GroupChat);
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }

        message.setMessageStatusCallback(messageStatusCallback);

        // Send message.
        EMClient.getInstance().chatManager().sendMessage(message);
        //refresh ui
        if(isMessageListInited) {
            messageList.refreshSelectLast();
        }
    }

//===================================================================================

    protected EMCallBack messageStatusCallback = new EMCallBack() {
        @Override
        public void onSuccess() {
            //上报消息发送
            updateTW("1","1","1");
            if ("twjz".equals(mChatType) || "dhjz".equals(mChatType) ||"spjz".equals(mChatType))
            {
                if (mMessageNum != -1)
                    mMessageNum -= 1;
            }
            if(isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onError(int code, String error) {
            Log.i("EaseChatRowPresenter", "onError: " + code + ", error: " + error);
            if(isMessageListInited) {
                messageList.refresh();
            }
        }

        @Override
        public void onProgress(int progress, String status) {
            Log.i(TAG, "onProgress: " + progress);
            if(isMessageListInited) {
                messageList.refresh();
            }
        }
    };

    /**
     * 上报图文消息使用量
     */
    private void updateTW(String operType,String limitNum,String treatmentType){
        updMyClinicDetailByOrderTreatmentLimitNum = new UpdMyClinicDetailByOrderTreatmentLimitNum();
        updMyClinicDetailByOrderTreatmentLimitNum.setLoginDoctorPosition(loginDoctorPosition);
        updMyClinicDetailByOrderTreatmentLimitNum.setOperDoctorCode(operDoctorCode);
        updMyClinicDetailByOrderTreatmentLimitNum.setOperDoctorName(operDoctorName);
        updMyClinicDetailByOrderTreatmentLimitNum.setOrderCode(orderCode);
        updMyClinicDetailByOrderTreatmentLimitNum.setTreatmentType(treatmentType);
        updMyClinicDetailByOrderTreatmentLimitNum.setOperType(operType);
        updMyClinicDetailByOrderTreatmentLimitNum.setLimitNum(limitNum);

        new Thread(){
            public void run(){
                try {

                    String string = new Gson().toJson(updMyClinicDetailByOrderTreatmentLimitNum);
                    //HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"doctorInteractDataControlle/operUpdMyClinicDetailByOrderTreatmentLimitNum");
                    String string01 = Constant.SERVICEURL+"msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string+string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * send image
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = myActivity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(myActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(myActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * send file
     * @param uri
     */
    protected void sendFileByUri(Uri uri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = myActivity.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(myActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(uri.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(myActivity, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendFileMessage(file.getAbsolutePath());
        }
    }

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(myActivity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT, EaseCompat.getUriForFile(myActivity, cameraFile)), REQUEST_CODE_CAMERA);
    }


    /**
     * 录制视频     */
    private void selectVideo() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(myActivity, R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        videoFile = new File(PathUtil.getInstance().getVideoPath(), EMClient.getInstance().getCurrentUser()                + System.currentTimeMillis() + ".mp4");
        videoFile.getParentFile().mkdirs();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    EaseCompat.getUriForFile(myActivity, videoFile));
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
        }
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }


    //根据路径得到视频缩略图
    public  String  getVideoPhoto(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();

        String savePath;
        File filePic;

        savePath = PathUtil.getInstance().getVideoPath().getAbsolutePath();

        try {
            filePic = new File(savePath +  EMClient.getInstance().getCurrentUser()
                    + System.currentTimeMillis() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    //获取视频总时长
    public  String getVideoDuration(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); //
        return duration;
    }

    /**
     * select local image
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * select local image
     */
    protected void selectFileFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * clear the conversation history
     *
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(myActivity,null, msg, null,new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if(confirmed){
                    if (conversation != null) {
                        conversation.clearAllMessages();
                    }
                    messageList.refresh();
                    haveMoreData = true;
                }
            }
        }, true).show();
    }

    /**
     * open group detail
     *
     */
    protected void toGroupDetails() {
        if (chatType == CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(myActivity, R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            if(chatFragmentHelper != null){
                chatFragmentHelper.onEnterToChatDetails();
            }
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
            if(chatFragmentHelper != null){
                chatFragmentHelper.onEnterToChatDetails();
            }
        }
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (myActivity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (myActivity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(myActivity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * forward message
     *
     * @param forward_msg_id
     */
    protected void forwardMessage(String forward_msg_id) {
        final EMMessage forward_msg = EMClient.getInstance().chatManager().getMessage(forward_msg_id);
        EMMessage.Type type = forward_msg.getType();
        switch (type) {
            case TXT:
                if(forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                    sendBigExpressionMessage(((EMTextMessageBody) forward_msg.getBody()).getMessage(),
                            forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
                }else{
                    // get the content and send it
                    String content = ((EMTextMessageBody) forward_msg.getBody()).getMessage();
                    sendTextMessage(content);
                }
                break;
            case IMAGE:
                // send image
                String filePath = ((EMImageMessageBody) forward_msg.getBody()).getLocalUrl();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        // send thumb nail if original image does not exist
                        filePath = ((EMImageMessageBody) forward_msg.getBody()).thumbnailLocalPath();
                    }
                    sendImageMessage(filePath);
                }
                break;
            default:
                break;
        }

        if(forward_msg.getChatType() == EMMessage.ChatType.ChatRoom){
            EMClient.getInstance().chatroomManager().leaveChatRoom(forward_msg.getTo());
        }
    }
}
