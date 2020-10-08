package www.jykj.com.jykj_zxyl.personal;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ReservePatientDoctorInfo;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:我的服务历史Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-06 11:43
 */
public class MyServiceHistoryPresenter extends BasePresenterImpl<MyServiceHistoryContract.View>
        implements MyServiceHistoryContract.Presenter {

    private static final String SEND_SEARCH_RESERVE_INFO_REQUEST_TAG = "send_search_reserve_info_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_RESERVE_INFO_REQUEST_TAG};
    }


    @Override
    public void sendSearchReserveInfoMyHistoryRequest(int reserveType, int rowNum, int pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("reserveType", reserveType);
        hashMap.put("rowNum", rowNum);
        hashMap.put("pageNum", pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchReserveInfoMyHistory(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView != null) {
                    mView.showLoading(100);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView != null) {
                    mView.hideLoading();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        List<ReservePatientDoctorInfo> reservePatientDoctorInfos
                                = GsonUtils.jsonToList(baseBean.getResJsonData(), ReservePatientDoctorInfo.class);
                        if (!CollectionUtils.isEmpty(reservePatientDoctorInfos)) {
                            mView.getSearchReserveInfoMyHistoryResult(reservePatientDoctorInfos);
                        }else{
                            mView.showEmpty();
                        }
                    } else {
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
                return SEND_SEARCH_RESERVE_INFO_REQUEST_TAG;
            }
        });
    }
}
