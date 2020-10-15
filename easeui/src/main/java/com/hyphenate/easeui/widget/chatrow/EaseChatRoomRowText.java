package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

public class EaseChatRoomRowText extends EaseChatRow{

	private TextView contentView;
    protected RelativeLayout chat_gridrow_layout;
    public EaseChatRoomRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
        /*EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        if(null!=txtBody.getMessage() && (txtBody.getMessage().contains("加入直播间了")||txtBody.getMessage().contains("离开直播间了"))) {
            inflater.inflate(R.layout.ease_row_room_remind_message, this);
        }else{
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_room_received_message : R.layout.ease_row_room_sent_message, this);
        }*/
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_room_received_message : R.layout.ease_row_room_sent_message, this);
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
        chat_gridrow_layout = (RelativeLayout)findViewById(R.id.chat_gridrow_lay);
	}

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();

        if(!itemStyle.isShowChatBack() && null!=txtBody.getMessage() && (txtBody.getMessage().equals("加入直播间了")||txtBody.getMessage().equals("离开直播间了"))) {
            try {
                String parnickname = message.getStringAttribute("nickName");
                Spannable span = EaseSmileUtils.getSmiledText(context, parnickname+txtBody.getMessage());
                // 设置内容
                contentView.setText(span, BufferType.SPANNABLE);
            }catch (Exception ex){

            }
        }else{
            Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
            // 设置内容
            contentView.setText(span, BufferType.SPANNABLE);
        }
        /*if(null!=txtBody.getMessage() && txtBody.getMessage().contains("加入直播间了")){
            chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorEnterroomBack));
        }else if(null!=txtBody.getMessage() && txtBody.getMessage().contains("离开直播间了")){
            chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorExitroomBack));
        }else{
            if(itemStyle.isShowChatBack()) {
                chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorNomalroomRow));
            }
        }*/
        //&& (null!=txtBody.getMessage() && (txtBody.getMessage().contains("加入直播间了") || txtBody.getMessage().contains("离开直播间了")))
        if(!itemStyle.isShowChatBack()) {
            //chat_gridrow_layout.setVisibility(VISIBLE);
            chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.back_low_gray));
            if(null!=txtBody.getMessage() && (txtBody.getMessage().contains("加入直播间了") || txtBody.getMessage().contains("离开直播间了"))){
                contentView.setBackgroundColor(getResources().getColor(R.color.hintTextColor));
                contentView.setTextColor(getResources().getColor(R.color.writeColor));
            }else{
                contentView.setBackgroundColor(Color.TRANSPARENT);
                contentView.setTextColor(getResources().getColor(R.color.textColor_black));
            }
        }else{
            if(null!=txtBody.getMessage() && txtBody.getMessage().contains("加入直播间了")){
                chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorEnterroomBack));
            }else if(null!=txtBody.getMessage() && txtBody.getMessage().contains("离开直播间了")){
                chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorExitroomBack));
            }else{
                if(itemStyle.isShowChatBack()) {
                    chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.colorNomalroomRow));
                }
            }
        }

        /*else{
            chat_gridrow_layout.setBackgroundColor(getResources().getColor(R.color.back_low_gray));
            chat_gridrow_layout.setVisibility(VISIBLE);
        }*/
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
