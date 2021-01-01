package www.jykj.com.jykj_zxyl.activity.home.contract;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.observer.CommonObserver;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UnReadMsgBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2021-01-01 13:21
 */
public class UnReadMsgPresenter extends BasePresenterImpl<UnReadMsgContract.View> implements
        UnReadMsgContract.Presenter {
    private static final String SEND_GET_UNREAD_MSG_COUNT_REQUEST_TAG="send_get_unread_msg_count_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_UNREAD_MSG_COUNT_REQUEST_TAG};
    }

    @Override
    public void sendSearchMsgPushReminderAllCount(Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        String s = RetrofitUtil.encodeParam(hashMap);

        ApiHelper.getApiService().searchMsgPushReminderAllCount(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(100);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView!=null) {
                    mView.hideLoading();
                }

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        UnReadMsgBean unReadMsgBean =
                                GsonUtils.fromJson(resJsonData, UnReadMsgBean.class);
                        mView.getSearchMsgPUshReminderAllResult(unReadMsgBean);
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_UNREAD_MSG_COUNT_REQUEST_TAG;
            }
        });

    }


}
