package www.jykj.com.jykj_zxyl.fragment.home;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.HealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:首页资源契约类
 *
 * @author: qiuxinhai
 * @date: 2020-10-12 14:42
 */
public class HealthEducationContract {

    public interface View extends BaseView {
        /**
         * 获取搜索健康教育返回结果
         */
        void getSearchIndexHealthEducationResult(List<HealthEducationBean> healthEducationBeans);
    }

    public interface Presenter extends BasePresenter<View> {
        /**
         * 发送搜索搜索健康教育请求
         * @param searchPosition 查询位置.1.首页;2.更多;
         * @param searchType 查询类型.0.综合数据;1.视频;2.音频;3.图文;
         * @param rowNum 每页数量
         * @param pageNum 当前页码
         * @param activity Activity
         */
        void sendSearchIndexHealthEducationRequest(String searchPosition, String searchType
                , int rowNum, int pageNum, Activity activity);

    }

}
