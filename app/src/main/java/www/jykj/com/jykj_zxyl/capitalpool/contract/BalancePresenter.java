package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:余额Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 17:31
 */
public class BalancePresenter extends BasePresenterImpl<BalanceContract.View> implements
        BalanceContract.Presenter {
    private static final String SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE=
            "send_search_account_doctor_assets_info_code";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE};
    }


    @Override
    public void sendSearchAccountDoctorAssetsInfoRequest(Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().searchAccountDoctorAssetsInfo(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        AccountBalanceBean accountBalanceBean =
                                GsonUtils.fromJson(resJsonData, AccountBalanceBean.class);
                        mView.getSearchAccountBalanceResult(accountBalanceBean);
                    } else{
                        mView.getSearchAccountBalanceError();
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
                return SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE;
            }
        });
    }
}
