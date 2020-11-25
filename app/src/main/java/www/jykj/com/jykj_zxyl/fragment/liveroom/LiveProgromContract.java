package www.jykj.com.jykj_zxyl.fragment.liveroom;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 15:16
 */
public class LiveProgromContract {

    public interface View extends BaseView {

        /**
         * 获取直播详情信息
         * @param liveProtromDetialBean 直播详情
         */
        void getBroadcastDetailInfoResult(LiveProtromDetialBean liveProtromDetialBean);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送直播详情请求
         * @param detailsCode 直播间code
         * @param activity Activity
         */
        void sendGetBroadcastDetailInfoRequest(String detailsCode, Activity activity);
    }
}
