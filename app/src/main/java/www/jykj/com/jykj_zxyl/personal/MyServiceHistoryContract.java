package www.jykj.com.jykj_zxyl.personal;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.ReservePatientDoctorInfo;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:我的服务历史契约类
 *
 * @author: qiuxinhai
 * @date: 2020-10-06 11:41
 */
public class MyServiceHistoryContract {

    public interface View extends BaseView {

        /**
         * 获取返回结果
         * @param list 返回列表
         */
        void getSearchReserveInfoMyHistoryResult(
                List<ReservePatientDoctorInfo> list);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送历史服务请求
         * @param reserveType 就诊类型 (1图文，2音频，3视频，4签约，5电话)
         * @param rowNum 页面显示数量
         * @param pageNum 页码
         * @param activity Activity
         */
        void sendSearchReserveInfoMyHistoryRequest(int reserveType, int rowNum, int pageNum, Activity activity);

    }
}
