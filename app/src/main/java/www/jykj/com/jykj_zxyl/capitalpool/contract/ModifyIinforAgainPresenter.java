package www.jykj.com.jykj_zxyl.capitalpool.contract;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.capitalpool.bean.EasyBean;
import yyz_exploit.Utils.GsonUtil;

public class ModifyIinforAgainPresenter extends BasePresenterImpl<ModifyIinforAgainContract.View>
        implements ModifyIinforAgainContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void setPassword(String params) {
        ApiHelper.getCapitalPoolApi().setAccountPassword(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode == 1) {

                        EasyBean easyBean = GsonUtil.parseJsonToBean(resJsonData, EasyBean.class);
                        mView.setPasswordSucess(easyBean.getResMsg());
                    } else {

                        mView.showMsg(baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("错误信息   " + s);
            }

            @Override
            protected String setTag() {
                return "docassets";
            }
        });
    }
}


