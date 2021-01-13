package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawDetBean;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

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
                    int resCode = baseBean.getResCode();
                    String resJsonData = baseBean.getResJsonData();
                    Log.e("TAG", "onSuccessResult:  qingqiu  "+resJsonData );
                    if (resCode == 1) {
                        List<WithdrawDetBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                WithdrawDetBean.class);
                        if (takeMedicinalRateBeans.size() > 0) {
                            mView.getWithdrawDetResult(takeMedicinalRateBeans);
                        } else {
                            mView.getDetSize();
                        }
                    } else {
                        if (baseBean.getResMsg().contains("暂无相关记录")) {
                            mView.getDetSize();
                        }
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
