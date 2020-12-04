package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.utils.ExtEaseUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.TextFormater;

import java.io.File;

public class EaseChatRowFile extends EaseChatRow{
    private static final String TAG = "EaseChatRowFile";

    protected TextView fileNameView;
	protected TextView fileSizeView;
    protected TextView fileStateView;
    protected TextView tvUserStatus;
    private LinearLayout llUserInfoRoot;
    private TextView tvUserName;
    private EMNormalFileMessageBody fileMessageBody;

    public EaseChatRowFile(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
	protected void onInflateView() {
	    inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
	            R.layout.ease_row_received_file : R.layout.ease_row_sent_file, this);
	}

	@Override
	protected void onFindViewById() {
	    fileNameView = (TextView) findViewById(R.id.tv_file_name);
        fileSizeView = (TextView) findViewById(R.id.tv_file_size);
        fileStateView = (TextView) findViewById(R.id.tv_file_state);
        percentageView = (TextView) findViewById(R.id.percentage);
        llUserInfoRoot=findViewById(R.id.ll_userinfo_root);
        tvUserName=findViewById(R.id.tv_user_name);
        tvUserStatus=findViewById(R.id.tv_user_status);
	}


	@Override
	protected void onSetUpView() {
	    fileMessageBody = (EMNormalFileMessageBody) message.getBody();
        String filePath = fileMessageBody.getLocalUrl();
        fileNameView.setText(fileMessageBody.getFileName());
        fileSizeView.setText(TextFormater.getDataSize(fileMessageBody.getFileSize()));
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            File file = new File(filePath);
            if (file.exists()) {
                fileStateView.setText(R.string.Have_downloaded);
            } else {
                fileStateView.setText(R.string.Did_not_download);
            }
            return;
        }
        if(null!=tvUserName){
            if (message.direct() == EMMessage.Direct.SEND) {
                EaseUserUtils.setUserNick(ExtEaseUtils.getInstance().getNickName(),tvUserName);
            }else{
                EaseUserUtils.setUserNick(message.getUserName(),tvUserName);
            }
        }
        long reserveConfigStart = message.getLongAttribute("reserveConfigStart", 0);
        long reserveConfigEnd = message.getLongAttribute("reserveConfigEnd", 0);

        if(reserveConfigStart!=0&&reserveConfigEnd!=0){
            long msgTime = message.getMsgTime();
            if(msgTime>=reserveConfigStart&&msgTime<=reserveConfigEnd){
                llUserInfoRoot.setVisibility(View.VISIBLE);

            }else{
                llUserInfoRoot.setVisibility(View.GONE);
            }
        }else{
            llUserInfoRoot.setVisibility(View.GONE);
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

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        if (percentageView != null)
            percentageView.setVisibility(View.INVISIBLE);
        if (statusView != null)
            statusView.setVisibility(View.INVISIBLE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        if (percentageView != null)
            percentageView.setVisibility(View.INVISIBLE);
        if (statusView != null)
            statusView.setVisibility(View.INVISIBLE);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.INVISIBLE);
        if (percentageView != null)
            percentageView.setVisibility(View.INVISIBLE);
        if (statusView != null)
            statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        if (percentageView != null) {
            percentageView.setVisibility(View.VISIBLE);
            percentageView.setText(message.progress() + "%");
        }
        if (statusView != null)
            statusView.setVisibility(View.INVISIBLE);
    }
}
