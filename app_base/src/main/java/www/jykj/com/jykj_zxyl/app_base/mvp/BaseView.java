package www.jykj.com.jykj_zxyl.app_base.mvp;

/**
 * Description:View基类
 *
 * @author: qiuxinhai
 * @date: 2018/5/28 16:10
 */

public interface BaseView {

    /**
     * 显示loading
     * @param code 请求code
     */
    void showLoading(int code);

    /**
     * 隐藏loading
     */
    void hideLoading();

    /**
     * 显示空布局
     */
    void showEmpty();

    /**
     * 显示重试
     */
    void showRetry();

    /**
     * 网络是否连接
     *
     * @return 是否有网
     */
    boolean isNetworkConnected();

    /**
     * 隐藏键盘
     */
    void hideKeyboard();

    /**
     * 显示等待框
     */
    void showWaitLoading();

    /**
     * 隐藏等待框
     */
    void hideWaitLoading();
}
