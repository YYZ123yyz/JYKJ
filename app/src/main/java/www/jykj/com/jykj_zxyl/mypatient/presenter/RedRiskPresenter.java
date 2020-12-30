package www.jykj.com.jykj_zxyl.mypatient.presenter;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.custom.RefrecenmapBean;
import www.jykj.com.jykj_zxyl.mypatient.bean.WarningListBean;
import www.jykj.com.jykj_zxyl.mypatient.contract.RedRiskContract;


public class RedRiskPresenter extends BasePresenterImpl<RedRiskContract.View>
        implements RedRiskContract.Presenter {
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG = "send_operupd_drug_info_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getWarningList(String params) {
        ApiHelper.getApiService().getWrningList(params)
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

                        if (baseBean.getResJsonData() != null) {
                            LogUtils.e("诗句   " + baseBean.getResJsonData());
                            if (baseBean.getResCode() == 1) {
                                List<WarningListBean> warningListBeans = GsonUtils.jsonToList(baseBean.getResJsonData(), WarningListBean.class);
                                mView.getListSucess(warningListBeans);
                            } else {
                                mView.showMsg(baseBean.getResMsg());
                            }
                           /* mView.getManDataSucess(manBeans);
                            mView.getWomenDataSucess(womenBeans);*/

                        } else {
//                            mView.showMsg("加载失败");
                        }

                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    } else {
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/

                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("加载失败  " + s);

            }

            @Override
            protected String setTag() {
                return "";
            }
        });
    }

    @Override
    public void setPatientWarning(String params) {
        ApiHelper.getApiService().setPatientWarning(params)
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

                        if (baseBean.getResJsonData() != null) {
                            LogUtils.e("诗句   " + baseBean.getResJsonData());
                            if (baseBean.getResCode() == 1) {
                                List<WarningListBean> warningListBeans = GsonUtils.jsonToList(baseBean.getResJsonData(), WarningListBean.class);
                                mView.getListSucess(warningListBeans);
                            } else {
                                mView.showMsg(baseBean.getResMsg());
                            }
                           /* mView.getManDataSucess(manBeans);
                            mView.getWomenDataSucess(womenBeans);*/

                        } else {
//                            mView.showMsg("加载失败");
                        }

                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    } else {
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/

                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("加载失败  " + s);

            }

            @Override
            protected String setTag() {
                return "";
            }
        });
    }
}

