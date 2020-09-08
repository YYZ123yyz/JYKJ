package www.jykj.com.jykj_zxyl.appointment;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:续约订单详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 10:51
 */
public class AppointOrderDetialPresenter
        extends BasePresenterImpl<AppointOrderDetialContract.View> implements AppointOrderDetialContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
