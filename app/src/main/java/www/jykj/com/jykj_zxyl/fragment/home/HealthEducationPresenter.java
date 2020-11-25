package www.jykj.com.jykj_zxyl.fragment.home;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.HomeHealthEducationBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:首页内容Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-12 14:48
 */
public class HealthEducationPresenter extends BasePresenterImpl<HealthEducationContract.View>
        implements HealthEducationContract.Presenter {
    private static final String SEND_SEARCH_LIST_GET_HEALTH_EDUCATION_REQUEST_TAG="send_search_list_get_health_education_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_LIST_GET_HEALTH_EDUCATION_REQUEST_TAG};
    }



    @Override
    public void sendListGetIndexHealthEducationRequest(String searchType, String createDate,
                                                       int pageSize,Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("searchType",searchType);
        hashMap.put("createDate",createDate);
        hashMap.put("pageSize",pageSize);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().listGetIndexHealthEducation(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        String resJsonData = baseBean.getResJsonData();
                        List<HomeHealthEducationBean>
                                healthEducationBeans = GsonUtils.jsonToList(resJsonData, HomeHealthEducationBean.class);
                        if (!CollectionUtils.isEmpty(healthEducationBeans)) {
                            mView.getIndexHealthEducationResult(healthEducationBeans);
                        }else{
                            mView.showEmpty();
                        }
                    } else {
                        mView.showEmpty();
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_LIST_GET_HEALTH_EDUCATION_REQUEST_TAG;
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }
        });
    }


}
