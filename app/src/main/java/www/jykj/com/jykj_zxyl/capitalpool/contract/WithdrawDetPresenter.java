package www.jykj.com.jykj_zxyl.capitalpool.contract;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class WithdrawDetPresenter extends BasePresenterImpl<WithdrawDetContract.View>
        implements WithdrawDetContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getWithdrawDet(String params) {
        ApiHelper.getCapitalPoolApi().getWithdrawDet(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    LogUtils.e("xxxxxx   "+baseBean.toString());
                    int resCode = baseBean.getResCode();
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        LogUtils.e("医生账户  成功  "+resJsonData);
                        /*List<TakeMedicinalRateBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                TakeMedicinalRateBean.class);
                        mView.getTakeMedicinalRateResult(takeMedicinalRateBeans);*/
                    }else {
                        LogUtils.e("医生账户  失败  "+resJsonData);
                        mView.showRetry();
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("错误信息   "+s);
            }

            @Override
            protected String setTag() {
                return "docassets";
            }
        });
    }
}
