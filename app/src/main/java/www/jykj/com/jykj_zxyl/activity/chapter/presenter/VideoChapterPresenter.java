package www.jykj.com.jykj_zxyl.activity.chapter.presenter;


import android.app.Activity;
import android.text.TextUtils;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPriceBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChatperSourceBean;
import www.jykj.com.jykj_zxyl.activity.chapter.contract.VideoChapterContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;


public class VideoChapterPresenter extends BasePresenterImpl<VideoChapterContract.View>
        implements VideoChapterContract.Presenter {

    private static final String GET_PATIENT_TAG = "get_video_chapter";

    private static final String GET_IM_TEST_REQUEST_TAG = "get_video_chapter";

    private static final String GET_ACCOUNT_BALANCE_REQUEST_TAG="get_account_balance_request_tag";
    private static final String SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE=
            "send_search_account_doctor_assets_info_code";
    private static final String SEND_GET_CHECK_PWD_REQUEST_TAG="send_get_check_pwd_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{GET_PATIENT_TAG, GET_IM_TEST_REQUEST_TAG
                ,GET_ACCOUNT_BALANCE_REQUEST_TAG
                ,SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE,SEND_GET_CHECK_PWD_REQUEST_TAG};
    }


    @Override
    public void getVideoChapterList(String params) {
        ApiHelper.getLiveApi().getHomeChapterList(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
//                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView!=null) {
//                            mView.hideLoading();
                        }
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("课件详情  "+ resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            ChapterListBean chapterListBean = GsonUtils.fromJson(resJsonData, ChapterListBean.class);
                            mView.getListSucess(chapterListBean);
                        }else{
                            mView.getDataFail(baseBean.getResMsg());
                        }

                    }else{
                        mView.getDataFail(baseBean.getResMsg());
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
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void getChapterPriceDet(String params) {
        ApiHelper.getLiveApi().getChapterPrice(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
//                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView!=null) {
//                            mView.hideLoading();
                        }
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("课件详情价格 "+ resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            ChapterPriceBean chapterListBean = GsonUtils.fromJson(resJsonData, ChapterPriceBean.class);
                            mView.getChatperPriceSucess(chapterListBean);
                        }else{
                            mView.getDataFail(baseBean.getResMsg());
                        }

                    }else{
                        mView.getDataFail(baseBean.getResMsg());
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
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void go2Pay(String params,int type) {
        ApiHelper.getLiveApi().go2payChapter(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
//                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView!=null) {
//                            mView.hideLoading();
                        }
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("课件详情  "+ resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {

                            if (type==1){
                                ChapterPayBean chapterListBean = GsonUtils.fromJson(resJsonData, ChapterPayBean.class);
                                mView.getPayInfoSucess(chapterListBean);
                            }else if (type ==2){
                                if (TextUtils.isEmpty(resJsonData)){
                                    mView.getDataFail("获取支付信息失败");
                                }else {
                                    mView.getAliPayInfoSucess(resJsonData);
                                }
                            }else if (type ==4){//支付金额=0
                                mView.paySucess("支付成功");
                            }
                        }else{
                            if(type==4){
                                mView.paySucess("支付成功");
                            }else{
                                mView.getDataFail(baseBean.getResMsg());
                            }

                        }

                    }else{
                        mView.getDataFail(baseBean.getResMsg());
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
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void getChapterSource(String params) {
        ApiHelper.getLiveApi().getChapterSource(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
//                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView!=null) {
//                            mView.hideLoading();
                        }
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("课件详情  "+ resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            ChatperSourceBean chapterListBean = GsonUtils.fromJson(resJsonData, ChatperSourceBean.class);
                            mView.getChapterSourceSucess(chapterListBean);
                        }else{
                            mView.getDataFail(baseBean.getResMsg());
                        }

                    }else{
                        mView.getDataFail(baseBean.getResMsg());
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
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void getAccountBalance(Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getFundPoolApi().searchAccountDoctorAssetsInfo(s)
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
                        AccountBalanceBean accountBalanceBean =
                                GsonUtils.fromJson(resJsonData, AccountBalanceBean.class);
                        mView.getAccountBalanceResult(accountBalanceBean);
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
                return SEND_SEARCH_ACCOUNT_DOCTOR_ASSETS_INFO_CODE;
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
}
