package www.jykj.com.jykj_zxyl.activity.home.contract;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MessageListChildBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
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
 * @date: 2021-01-01 14:24
 */
public class MessageListPresenter extends
        BasePresenterImpl<MessageListContract.View> implements MessageListContract.Presenter{
    private static final String SEND_MESSAGE_LIST_REQUEST_TAG="send_message_list_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_MESSAGE_LIST_REQUEST_TAG};
    }

    @Override
    public void sendMessageListRequest(String msgType, String flagMsgRead, int rowNum,
                                       int pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("msgType",msgType);
        hashMap.put("flagMsgRead",flagMsgRead);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMsgPushReminderListResAllData(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        List<MessageListChildBean> messageListChildBeans =
                                GsonUtils.jsonToList(resJsonData, MessageListChildBean.class);
                        if (!CollectionUtils.isEmpty(messageListChildBeans)) {
                            mView.getMessageListResult(messageListChildBeans);
                        }else{
                            mView.showEmpty();
                        }
                    }else{
                        mView.showEmpty();
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
                return SEND_MESSAGE_LIST_REQUEST_TAG;
            }
        });
    }
}
