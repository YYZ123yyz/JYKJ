package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceListInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountStisticInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:零钱明细列表
 *
 * @author: qiuxinhai
 * @date: 2020-12-22 18:29
 */
public class AccountBalanceListPresenter extends BasePresenterImpl<AccountBalanceListContract.View>
        implements AccountBalanceListContract.Presenter  {
    private static final String SEND_SEARCH_ACCOUNT_BALANCE_LIST_REQUEST_TAG="send_search_account_balance_list_request_tag";
    private static final String SEND_SEARCH_ACCOUNT_DOCTOR_INCOME_OUTINFO_REQUEST_TAG
            ="send_search_account_doctor_income_outinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_ACCOUNT_BALANCE_LIST_REQUEST_TAG,
                SEND_SEARCH_ACCOUNT_DOCTOR_INCOME_OUTINFO_REQUEST_TAG};
    }




    @Override
    public void sendSearchAccountDoctorBalanceInfoListRequest(String countPeriod,
                                                              int rowNum, int pageNum,
                                                              Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("countPeriod",countPeriod);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().searchAccountDoctorBalanceInfoList(s)
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
                        String resJsonData = baseBean.getResJsonData();
                        AccountBalanceListInfoBean accountBalanceListInfoBean
                                = GsonUtils.fromJson(resJsonData, AccountBalanceListInfoBean.class);
                        mView.getSearchAccountDoctorBalanceInfoResult(accountBalanceListInfoBean);
                    }else{
                        mView.showEmpty();
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_ACCOUNT_BALANCE_LIST_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSearchAccountDoctorIncomeOutInfoRequest(String countPeriod, String changeType, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("countPeriod",countPeriod);
        hashMap.put("changeType",changeType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().searchAccountDoctorIncomeOutInfo(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
                            mView.showLoading(101);
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
                        String resJsonData = baseBean.getResJsonData();
                        AccountStisticInfoBean accountStisticInfoBean =
                                GsonUtils.fromJson(resJsonData
                                        , AccountStisticInfoBean.class);
                        mView.getSearchAccountDoctorIncomOutInfoResult(accountStisticInfoBean);
                    }else{
                        mView.showEmpty();
                    }

                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_ACCOUNT_DOCTOR_INCOME_OUTINFO_REQUEST_TAG;
            }
        });
    }
}
