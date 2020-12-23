package www.jykj.com.jykj_zxyl.capitalpool.contract;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawCostBean;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class WithdrawPresenter extends BasePresenterImpl<WithdrawContract.View>
        implements WithdrawContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getWithdrawType(String params) {
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
                    LogUtils.e("xxxxxx   "+baseBean.toString());
                    int resCode = baseBean.getResCode();
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        LogUtils.e("医生账户  账户列表  "+resJsonData);
                        List<WithdrawTypelListBean>
                                data = GsonUtils.jsonToList(resJsonData,
                                WithdrawTypelListBean.class);

                        mView.getTypeSucess(data);
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

    @Override
    public void go2Withdraw(String params) {
        ApiHelper.getCapitalPoolApi().go2Withdraw(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.withDrawSucess();
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
    public void getWithdrawCost(String params) {
        ApiHelper.getCapitalPoolApi().rateCalculation(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        WithdrawCostBean withdrawCostBean = GsonUtils.fromJson(resJsonData, WithdrawCostBean.class);
                        mView.getWithdrawCost(withdrawCostBean);
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
    public void checkPassword(String params) {
        ApiHelper.getCapitalPoolApi().checkPassword(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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

                        mView.checkSucess();
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
}
