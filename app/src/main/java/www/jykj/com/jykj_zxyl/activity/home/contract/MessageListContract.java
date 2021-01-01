package www.jykj.com.jykj_zxyl.activity.home.contract;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.MessageListChildBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 14:21
 */
public class MessageListContract {

    public interface View extends BaseView {

        void getMessageListResult(List<MessageListChildBean> messageListChildBean);
    }

    public interface Presenter extends BasePresenter<View> {

        void sendMessageListRequest(String msgType,String flagMsgRead,
                                    int rowNum, int pageNum,Activity activity);

    }
}
