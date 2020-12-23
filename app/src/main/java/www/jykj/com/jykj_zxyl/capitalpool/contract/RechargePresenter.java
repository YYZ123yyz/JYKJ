package www.jykj.com.jykj_zxyl.capitalpool.contract;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.personal.StateDetContract;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class RechargePresenter extends BasePresenterImpl<RechargeContract.View>
        implements RechargeContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getDocdorAsset(String params) {
        ApiHelper.getCapitalPoolApi().getDoctorAssets(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        LogUtils.e("医生账户    "+resJsonData);
                        /*List<TakeMedicinalRateBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                TakeMedicinalRateBean.class);
                        mView.getTakeMedicinalRateResult(takeMedicinalRateBeans);*/
                    }else {
                        LogUtils.e("医生账户    "+resJsonData);
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

    @Override
    public void getCardList(String params) {
        ApiHelper.getCapitalPoolApi().getBankcardList(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        /*List<TakeMedicinalRateBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                TakeMedicinalRateBean.class);
                        mView.getTakeMedicinalRateResult(takeMedicinalRateBeans);*/
                    }else {
                        LogUtils.e("医生账户  账户列表  "+resJsonData);
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


