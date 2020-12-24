package www.jykj.com.jykj_zxyl.capitalpool.contract;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawTypelListBean;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class UserAccountPresenter extends BasePresenterImpl<UserAccountContract.View>
        implements UserAccountContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getDoctorInfo(String params) {
        ApiHelper.getCapitalPoolApi().getBankcardList(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    LogUtils.e("xxxxxx   " + baseBean.toString());
                    int resCode = baseBean.getResCode();
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode == 1) {
                        LogUtils.e("医生账户  账户信息  " + resJsonData);
                        List<WithdrawTypelListBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                WithdrawTypelListBean.class);
                        ArrayList<WithdrawTypelListBean> withdrawTypelListData = new ArrayList<>();
                        ArrayList<WithdrawTypelListBean> aliData = new ArrayList<>();
                        ArrayList<WithdrawTypelListBean> weiChatData = new ArrayList<>();
                        for (int i = 0; i < takeMedicinalRateBeans.size(); i++) {
                            if (takeMedicinalRateBeans.get(i).getWithdrawalType() == 1) {//微信
                                weiChatData.add(takeMedicinalRateBeans.get(i));
                            } else if (takeMedicinalRateBeans.get(i).getWithdrawalType() == 2) {//支付宝
                                aliData.add(takeMedicinalRateBeans.get(i));
                            } else {
                                withdrawTypelListData.add(takeMedicinalRateBeans.get(i));
                            }
                        }
                        if (weiChatData.size()==0){
                            mView.getEmpeyData(1);
                        }else {
                            mView.getWeiChat(weiChatData.get(0));
                        }
                        if (aliData.size()==0){
                            mView.getEmpeyData(2);
                        }else {
                            mView.getAli(aliData.get(0));
                        }
                        mView.getDataSucess(withdrawTypelListData);
                    } else {
                        mView.getNodata();
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


