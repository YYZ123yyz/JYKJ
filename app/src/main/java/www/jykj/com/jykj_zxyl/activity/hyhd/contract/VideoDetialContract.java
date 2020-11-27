package www.jykj.com.jykj_zxyl.activity.hyhd.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.app_base.base_bean.VideoDetialBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description: 视频详情契约类
 *
 * @author: qiuxinhai
 * @date: 2020-11-25 15:58
 */
public class VideoDetialContract {


    public interface View extends BaseView {

        /**
         * 获取返回课件结果
         * @param videoDetialBean 课件详情
         */
        void getCourseWareDetailResult(VideoDetialBean videoDetialBean);
    }


    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送获取课件详情请求
         * @param courseWareCode 课件Id
         * @param activity Activity
         */
        void sendGetCourseWareDetailRequest(String courseWareCode, Activity activity);
    }

}
