package www.jykj.com.jykj_zxyl.appointment.fragment;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.appointment.NotComplatedListContract;
import www.jykj.com.jykj_zxyl.appointment.NotComplatedPresenter;

/**
 * Description:未完成订单列表Fragment
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:46
 */
public class NotComplateListFragment extends AbstractMvpBaseFragment<NotComplatedListContract.View,
        NotComplatedPresenter>implements NotComplatedListContract.View {
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }
}
