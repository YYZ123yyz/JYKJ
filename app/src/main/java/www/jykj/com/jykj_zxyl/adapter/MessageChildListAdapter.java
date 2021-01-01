package www.jykj.com.jykj_zxyl.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MessageListChildBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 14:40
 */
public class MessageChildListAdapter extends BaseQuickAdapter<MessageListChildBean, BaseViewHolder> {

    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MessageChildListAdapter(int layoutResId, @Nullable List<MessageListChildBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListChildBean item) {
        TextView tvMsgTitle = helper.getView(R.id.msgOperName);

        TextView tvMsgContent = helper.getView(R.id.tv_itemNewsAdapter_userMessageText);

        TextView tvMsgDate = helper.getView(R.id.tv_itemNewMessageAdapter_userDateText);

        ImageView ivNewHead = helper.getView(R.id.iv_itemNewsAdapter_head);
        ImageView ivUnread = helper.getView(R.id.iv_un_read);
        TextView tvUnreadStatus = helper.getView(R.id.tv_unread_status);

        LinearLayout llContentRoot = helper.getView(R.id.ll_content_root);

        int flagMsgRead = item.getFlagMsgRead();
        if (flagMsgRead==0) {
            ivUnread.setVisibility(View.VISIBLE);
            tvUnreadStatus.setText("未读");
        }else{
            ivUnread.setVisibility(View.GONE);
            tvUnreadStatus.setText("已读");
        }
        tvMsgTitle.setText(item.getSenderUserName());
        tvMsgDate.setText(item.getSendMsgDate());
        String userLogoUrl = item.getUserLogoUrl();
        if(StringUtils.isNotEmpty(userLogoUrl)){
            Glide.with(mContext).load(userLogoUrl).into(ivNewHead);
        }
        tvMsgContent.setText(item.getMsgTitle());
        llContentRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickItemPos(item);
                }
            }
        });
    }


    public interface OnClickItemListener{
        void onClickItemPos(MessageListChildBean item);
    }
}
