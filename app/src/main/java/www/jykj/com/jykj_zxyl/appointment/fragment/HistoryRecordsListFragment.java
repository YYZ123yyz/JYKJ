package www.jykj.com.jykj_zxyl.appointment.fragment;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseFragment;
import www.jykj.com.jykj_zxyl.appointment.HistoryRecordListContract;
import www.jykj.com.jykj_zxyl.appointment.HistoryRecordListPresenter;

/**
 * Description:历史记录fragment
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:57
 */
public class HistoryRecordsListFragment extends AbstractMvpBaseFragment<HistoryRecordListContract.View
        , HistoryRecordListPresenter> implements HistoryRecordListContract.View{
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void showLoading(int code) {

    }
}
