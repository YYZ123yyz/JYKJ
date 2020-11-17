package www.jykj.com.jykj_zxyl.live;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 15:04
 */
public class AddLiveProgromContract {
    public interface View extends BaseView {

        /**
         * 创建直播大纲返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getAddBroadcastSyllabusResult(boolean isSucess, String msg);

        /**
         * 修改直播大纲返回结果
         * @param isSucess true or false
         * @param msg 返回信息
         */
        void getUpdateBroadcastSyllabusResult(boolean isSucess,String msg);

        /**
         * 获取直播详情返回结果
         * @param liveProtromDetialBean 直播大纲
         */
        void getBroadcastDetailInfoResult(LiveProtromDetialBean liveProtromDetialBean);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送创建直播大纲请求
         * @param operUserCode 操作用户code
         * @param detailsCode 直播间code码
         * @param syllabusContent 直播内容
         */
        void sendAddBroadcastSyllabusRequest(String operUserCode
                , String detailsCode, String syllabusContent);

        /**
         * 修改直播大纲请求
         * @param operUserCode 操作用户code
         * @param syllabusCode 直播间code码
         * @param syllabusContent 直播内容
         */
        void sendUpdateBroadcastSyllabusRequest(String operUserCode
                , String syllabusCode, String syllabusContent);


        /**
         * 获取直播大纲信息
         * @param detailsCode 直播间code
         * @param activity Activity
         */
        void sendGetBroadcastDetailInfoRequest(String detailsCode,Activity activity);

    }

}
