package www.jykj.com.jykj_zxyl.personal;

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
                            LogUtils.e("诗句   "+baseBean.getResJsonData());
                            ArrayList<RefrecenmapBean> manBeans = new ArrayList<>();
                            ArrayList<RefrecenmapBean> womenBeans = new ArrayList<>();
                            List<RefrecenmapBean> refrecenmapBeans = GsonUtils.jsonToList(baseBean.getResJsonData(), RefrecenmapBean.class);
                            LogUtils.e("诗句  xxx   "+refrecenmapBeans.size());
                            for (int i = 0; i < refrecenmapBeans.size(); i++) {
                                if (refrecenmapBeans.get(i).getFlagGender() == 1) { //男
                                    manBeans.add(refrecenmapBeans.get(i));
                                } else {//女
                                    womenBeans.add(refrecenmapBeans.get(i));
                                }
                            }
                            mView.getManDataSucess(manBeans);
                            mView.getWomenDataSucess(womenBeans);

                        } else {
                            mView.showMsg("加载失败");
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
                LogUtils.e("加载失败  "+s);

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
                        LogUtils.e("修改   结果" + baseBean.getResJsonData());
                        mView.updataSucess();
                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    } else {
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/
                        LogUtils.e("修改   结果" + baseBean.getResMsg());
                        mView.showMsg(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);

                LogUtils.e("修改   错误" + s);
            }

            @Override
            protected String setTag() {
                return "";
            }
        });
    }
}
