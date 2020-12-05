package www.jykj.com.jykj_zxyl.live;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UploadLiveImageResultBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-16 17:17
 */
public class AddLivePhotoContract {

    public interface View extends BaseView {

        /**
         * 获取添加直播大纲返回结果
         * @param uploadLiveImageResultBean 直播大纲返回结果
         */
        void getAddBroadCastImageResult(UploadLiveImageResultBean uploadLiveImageResultBean);

        /**
         * 获取添加直播大纲请求是吧
         */
        void getAddBroadCastImageError(String msg);

        /**
         * 获取直播详情返回结果
         * @param liveProtromDetialBean 直播大纲
         */
        void getBroadcastDetailInfoResult(LiveProtromDetialBean liveProtromDetialBean);
    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送添加直播大纲图片请求
         * @param detailsCode 直播间code
         * @param imgBase64Array 编码
         * @param activity Activity
         */
        void sendAddBroadCastImageRequest(String detailsCode, String imgBase64Array, Activity activity);

        /**
         * 发送添加直播大纲图片请求
         * @param detailsCode 直播间code
         * @param urls url集合
         * @param activity Activity
         */
        void sendAddBroadCastImageRequest2(String detailsCode, List<String> urls, Activity activity);

        /**
         * 发送更新图片请求
         * @param imageCode
         * @param detailsCode
         * @param imgIdArray
         * @param imgBase64Array
         * @param activity
         */
        void sendUpdateBroadcastImageRequest(String imageCode
                ,String detailsCode,String imgIdArray,String imgBase64Array,Activity activity);

        /**
         * 发送更新图片请求
         * @param imageCode
         * @param detailsCode
         * @param imgIdArray
         * @param activity
         */
        void sendUpdateBroadcastImageRequest2(String imageCode
                ,String detailsCode,String imgIdArray,List<String> urls,Activity activity);

        /**
         * 获取直播大纲信息
         * @param detailsCode 直播间code
         * @param activity Activity
         */
        void sendGetBroadcastDetailInfoRequest(String detailsCode,Activity activity);
    }
}
