package www.jykj.com.jykj_zxyl.appointment;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:历史记录Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-08-27 15:59
 */
public class HistoryRecordListPresenter extends
        BasePresenterImpl<HistoryRecordListContract.View>implements HistoryRecordListContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
