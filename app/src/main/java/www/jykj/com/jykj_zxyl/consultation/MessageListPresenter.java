package www.jykj.com.jykj_zxyl.consultation;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import entity.basicDate.ProvideDoctorPatientUserInfo;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:消息列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 14:47
 */
public class MessageListPresenter extends BasePresenterImpl<MessageListContract.View>
        implements MessageListContract.Presenter {
    private static final String SEND_MESSAGE_LIST_REQUEST_TAG = "send_message_list_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_MESSAGE_LIST_REQUEST_TAG};
    }

    @Override
    public void sendMessageListRequest(String userCodeList) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCodeList",userCodeList);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfoList(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        List<ProvideDoctorPatientUserInfo> provideDoctorPatientUserInfos
                                = GsonUtils.jsonToList(baseBean.getResJsonData(),
                                ProvideDoctorPatientUserInfo.class);
                        if (!CollectionUtils.isEmpty(provideDoctorPatientUserInfos)) {
                            mView.getMessageListResult(provideDoctorPatientUserInfos);
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
