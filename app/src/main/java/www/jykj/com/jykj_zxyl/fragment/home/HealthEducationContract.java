package www.jykj.com.jykj_zxyl.fragment.home;

import android.app.Activity;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.HealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HomeHealthEducationBean;
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
         * 返回健康教育数据
         * @param healthEducationBeans 健康教育列表
         */
        void getIndexHealthEducationResult(List<HomeHealthEducationBean> healthEducationBeans);
    }

    public interface Presenter extends BasePresenter<View> {


        /**
         * 发送健康教请求
         * @param searchType 查询类型.0.综合数据;1.视频;2.音频;3.图文;
         * @param createDate 时间
         * @param pageSize 每页大小
         */
        void sendListGetIndexHealthEducationRequest(String searchType,String createDate
                , int pageSize,Activity activity);

    }

}
