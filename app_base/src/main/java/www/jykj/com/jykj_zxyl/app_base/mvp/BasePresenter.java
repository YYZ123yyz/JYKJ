package www.jykj.com.jykj_zxyl.app_base.mvp;

/**
 * Description:Presenter基类
 *
 * @author: qiuxinhai
 * @date: 2018/5/28 14:18
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * 绑定view
     *
     * @param view view
     */
    void attachView(V view);

    /**
     * 解除绑定
     */
    void detachView();
}
