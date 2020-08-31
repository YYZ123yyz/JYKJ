package www.jykj.com.jykj_zxyl.appointment;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:未完成订单列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:51
 */
public class NotComplatedPresenter extends
        BasePresenterImpl<NotComplatedListContract.View>
        implements NotComplatedListContract.Presenter{
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
