package www.jykj.com.jykj_zxyl.consultation;


import java.util.List;

import entity.basicDate.ProvideDoctorPatientUserInfo;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:消息列表契约类
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 14:44
 */
public class MessageListContract {

    public interface View extends BaseView {

        /**
         * 获取消息列表返回结果
         * @param list 消息列表
         */
        void getMessageListResult(List<ProvideDoctorPatientUserInfo> list);

    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 获取用户消息列表
         * @param userCodeList 用户id list
         */
        void sendMessageListRequest(String userCodeList);

    }
}
