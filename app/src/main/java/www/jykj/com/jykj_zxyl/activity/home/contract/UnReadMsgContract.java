package www.jykj.com.jykj_zxyl.activity.home.contract;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.UnReadMsgBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.consultation.MessageListContract;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 13:16
 */
public class UnReadMsgContract {

    public interface View extends BaseView {

        /**
         * 未读消息提醒返回结果
         * @param unReadMsgBean 未读消息返回结果
         */
        void getSearchMsgPUshReminderAllResult(UnReadMsgBean unReadMsgBean);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 未读消息数量(统计数据)
         */
        void sendSearchMsgPushReminderAllCount(Activity activity);

    }
}
