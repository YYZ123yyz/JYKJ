package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.CallStatusEnum;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.ExtEaseUtils;

import java.util.Date;
import java.util.List;

public class EaseChatRowText extends EaseChatRow {


    private TextView contentView;
    private LinearLayout llUserInfoRoot;
    private TextView tvUserName;
    private TextView tvUserStatus;
    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
        llUserInfoRoot = findViewById(R.id.ll_userinfo_root);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserStatus=findViewById(R.id.tv_user_status);
    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);
        if (null != tvUserName) {
            if (message.direct() == EMMessage.Direct.SEND) {
                EaseUserUtils.setUserNick(ExtEaseUtils.getInstance().getNickName(), tvUserName);
            } else {
                EaseUserUtils.setUserNick(message.getUserName(), tvUserName);
            }
        }
        long reserveConfigStart = message.getLongAttribute("reserveConfigStart", 0);
        long reserveConfigEnd = message.getLongAttribute("reserveConfigEnd", 0);
        if (reserveConfigStart != 0 && reserveConfigEnd != 0) {
            long msgTime = message.getMsgTime();
            if (msgTime >= reserveConfigStart && msgTime <= reserveConfigEnd) {
                tvUserStatus.setVisibility(View.VISIBLE);
            } else {
                tvUserStatus.setVisibility(View.GONE);
            }
        } else {
            tvUserStatus.setVisibility(View.GONE);
        }

        int intAttribute = message.getIntAttribute("callStatus", 0);
        switch (intAttribute) {
            case CallStatusEnum.CANCEL_BY_SELF_AUDIO_CODE:
            case CallStatusEnum.TALK_TIME_AUDIO_CODE:
            case CallStatusEnum.OTHER_SIDE_REFUSED_AUDIO_CODE:
                Drawable drawable = getResources().getDrawable(
                        R.mipmap.bg_im_call);
                // / 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                contentView.setCompoundDrawablePadding(20);
                contentView.setCompoundDrawables(null, null, drawable, null);
                break;
            case CallStatusEnum.CANCEL_BY_SELF_VIDEO_CODE:
            case CallStatusEnum.TALK_TIME_VIDEO_CODE:
            case CallStatusEnum.OTHER_SIDE_REFUSED_VIDEO_CODE:
                Drawable drawable1 = getResources().getDrawable(
                        R.mipmap.bg_im_video);
                // / 这一步必须要做,否则不会显示.
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                        drawable1.getMinimumHeight());
                contentView.setCompoundDrawablePadding(20);
                contentView.setCompoundDrawables(null, null, drawable1, null);

                break;
            default:
        }


    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            int count = message.groupAckCount();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
