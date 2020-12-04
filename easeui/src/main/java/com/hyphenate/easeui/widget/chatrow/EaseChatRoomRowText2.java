package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-01 15:48
 */
public class EaseChatRoomRowText2 extends EaseChatRow {

    private TextView mTvUserStatus;
    private TextView mTvUserName;
    private TextView mTvMsgSendContent;
    private TextView mTvMsgReceiveContent;
    private RelativeLayout mRlContentRoot;
    public EaseChatRoomRowText2(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_room_received_message_2 : R.layout.ease_row_room_send_message_2, this);
    }

    @Override
    protected void onFindViewById() {
        mTvUserStatus=findViewById(R.id.tv_user_status);
        mTvUserName=findViewById(R.id.tv_user_name);
        mTvMsgSendContent=findViewById(R.id.tv_send_msg_content);
        mTvMsgReceiveContent=findViewById(R.id.tv_receive_msg_content);
        mRlContentRoot=findViewById(R.id.rl_content_root);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        String parnickname = message.getStringAttribute("nickName","");
        mTvUserName.setText(parnickname);
        EMMessage.Direct direct = message.direct();
        if (direct== EMMessage.Direct.RECEIVE) {
            mTvMsgReceiveContent.setText(span);
        }else{
            mTvMsgSendContent.setText(span);
        }
        String em_join = message.getStringAttribute("em_join", "");
        String em_leave = message.getStringAttribute("em_leave", "");
        if (StringUtils.isNotEmpty(em_join)) {
            mRlContentRoot.setVisibility(View.GONE);
            mTvUserStatus.setVisibility(View.VISIBLE);
            // 设置内容
            mTvUserStatus.setText(String.format("%s加入直播间了", parnickname));
        }else if(StringUtils.isNotEmpty(em_leave)){
            mRlContentRoot.setVisibility(View.GONE);
            mTvUserStatus.setVisibility(View.VISIBLE);
            mTvUserStatus.setText(String.format("%s退出直播间了", parnickname));
        }else{
            mRlContentRoot.setVisibility(View.VISIBLE);
            mTvUserStatus.setVisibility(View.GONE);
        }
    }
}
