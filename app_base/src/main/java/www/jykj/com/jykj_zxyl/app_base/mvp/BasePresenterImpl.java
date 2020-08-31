package www.jykj.com.jykj_zxyl.app_base.mvp;

import com.allen.library.RxHttpUtils;
/**
 * Description:Presenter通用实现类
 *
 * @author: qiuxinhai
 * @date: 2018/5/28 15:10
 */

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    /**
     * 布局页面
     */
    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unsubscribe();
    }

    /**
     * RXjava取消注册，以避免内存泄露
     */
    private void unsubscribe() {
        Object[] requestTags = getRequestTags();
        if(requestTags!=null&&requestTags.length>0){
            for (Object requestTag : requestTags) {
                RxHttpUtils.cancel(requestTag);
            }
        }
    }

    /**
     * 获取请求tags
     * @return Object[]
     */
    protected abstract Object[] getRequestTags();

}
