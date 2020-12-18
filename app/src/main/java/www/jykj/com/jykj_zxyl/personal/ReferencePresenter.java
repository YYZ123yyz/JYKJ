package www.jykj.com.jykj_zxyl.personal;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class ReferencePresenter extends BasePresenterImpl<ReferenceContract.View>
        implements ReferenceContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void getReferenceData(String params) {
        ApiHelper.getApiService().getReferceList(params)
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

                        LogUtils.e("获取  结果"+ baseBean.getResData());
                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    }else{
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);


            }

            @Override
            protected String setTag() {
                return "";
            }
        });
    }

    @Override
    public void updataReference(String params) {
        ApiHelper.getApiService().updataReferceData(params)
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
                        LogUtils.e("修改   结果"+ baseBean.getResData());
                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    }else{
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);


            }

            @Override
            protected String setTag() {
                return "";
            }
        });
    }
}
