package www.jykj.com.jykj_zxyl.capitalpool.contract;

import android.app.Activity;
import android.text.TextUtils;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import java.util.HashMap;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class RechargePresenter extends BasePresenterImpl<RechargeContract.View>
        implements RechargeContract.Presenter {
    private static final String SEND_GET_DOCDOR_ASSET_REQUEST_TAG="send_get_doctor_asset_request_tag";
    private static final String SEND_GET_OPEN_ACCOUNT_DOCTOR_BALANCE_PAY_REQUEST_TAG=
            "send_get_open_account_doctor_balance_pay_request_tag";
    private static final String SEND_GET_CHECK_PWD_REQUEST_TAG="send_get_check_pwd_request_tag";

    private static final String SEND_GET_ORDER_STATUS_REQUEST_TAG="send_get_order_status_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_DOCDOR_ASSET_REQUEST_TAG
                ,SEND_GET_CHECK_PWD_REQUEST_TAG
                ,SEND_GET_OPEN_ACCOUNT_DOCTOR_BALANCE_PAY_REQUEST_TAG,SEND_GET_ORDER_STATUS_REQUEST_TAG};
    }



    @Override
    public void sendAccountDoctorBalanceInfoPayRequest(String payType,
                                                       String rechargeMoney,
                                                       Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("payType",payType);
        hashMap.put("rechargeMoney",rechargeMoney);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().openAccountDoctorBalanceInfoPay(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

                if (mView!=null) {
                    mView.showLoading(102);
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
                        if (payType.equals("1")) {
                            ChapterPayBean chapterListBean = GsonUtils.fromJson(resJsonData, ChapterPayBean.class);
                            mView.getPayInfoSucess(chapterListBean);
                        }else if(payType.equals("2")){
                            if (TextUtils.isEmpty(resJsonData)){
                                mView.getDataFail("获取支付信息失败");
                            }else {
                                mView.getAliPayInfoSucess(resJsonData);
                            }
                        }
                    }else{
                        mView.getAccountDoctorBalanceInfoPayError(baseBean.getResMsg());
                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_GET_OPEN_ACCOUNT_DOCTOR_BALANCE_PAY_REQUEST_TAG;
            }
        });
    }

    @Override
    public void checkPassword(String params) {
        ApiHelper.getCapitalPoolApi().checkPassword(params).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.checkPasswordResult(true,baseBean.getResMsg());
                    } else {
                        mView.checkPasswordResult(false,baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_CHECK_PWD_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendGetOrderStatusRequest(String payType, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("payType", payType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().getOrderStatus(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (mView!=null) {
                            int resCode = baseBean.getResCode();
                            if (resCode==1) {
                                mView.getOrderStatusResult(true,baseBean.getResMsg());
                            }else{
                                mView.getOrderStatusResult(false,baseBean.getResMsg());
                            }
                        }
                    }

                    @Override
                    protected void onError(String s) {
                        super.onError(s);
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_ORDER_STATUS_REQUEST_TAG;
                    }
                });
    }
}


