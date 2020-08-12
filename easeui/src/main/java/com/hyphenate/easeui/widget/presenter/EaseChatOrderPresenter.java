package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowOrderCard;

/**
 * Description:订单
 *
 * @author: qiuxinhai
 * @date: 2020-07-29 14:25
 */
public class EaseChatOrderPresenter extends EaseChatRowPresenter {
    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowOrderCard(cxt, message, position, adapter);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
//        EMLocationMessageBody locBody = (EMLocationMessageBody) message.getBody();
//        Intent intent = new Intent(getContext(), SignOrderDetialActivity.class);
//        intent.putExtra("address", locBody.getAddress());
//        getContext().startActivity(intent);

    }
}
