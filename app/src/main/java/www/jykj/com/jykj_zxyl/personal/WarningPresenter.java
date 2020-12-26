package www.jykj.com.jykj_zxyl.personal;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.personal.bean.AllNumBean;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class WarningPresenter extends BasePresenterImpl<WarningContract.View>
        implements WarningContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void getStateNum(String params) {
        ApiHelper.getApiService().getDataByTotal(params)
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
                        AllNumBean allNumBean = GsonUtils.fromJson(baseBean.getResJsonData(), AllNumBean.class);
                        mView.getNumSucess(allNumBean);
//                        mView.updataSucess();
                        /*mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(true
                                ,baseBean.getResMsg());*/
                    } else {
                        mView.getNumFail();
                       /* mView.getOperUpdMyClinicDetailByOrderPatientMessageResult(false
                                ,baseBean.getResMsg());*/
                        LogUtils.e("修改   结果" + baseBean.getResMsg());
//                        mView.showMsg(baseBean.getResMsg());
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

