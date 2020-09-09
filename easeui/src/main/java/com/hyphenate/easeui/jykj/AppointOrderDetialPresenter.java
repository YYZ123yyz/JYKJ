package com.hyphenate.easeui.jykj;

import android.app.Activity;
import android.text.TextUtils;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.OrderInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:续约订单详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-08 10:51
 */
public class AppointOrderDetialPresenter
        extends BasePresenterImpl<AppointOrderDetialContract.View> implements AppointOrderDetialContract.Presenter {

    /**发送订单请求tag*/
    private static final String SEND_ORDER_INFO_REQUEST_TAG="send_order_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_ORDER_INFO_REQUEST_TAG};
    }

    @Override
    public void sendSearchReserveInfoRequest( String orderCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
       ApiHelper.getApiService().searchReserveInfo(s).compose(Transformer.<String>switchSchedulers(new ILoadingView() {
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
                       if (StringUtils.isNotEmpty(resJsonData)) {
                           OrderInfoBean orderInfoBean = GsonUtils.fromJson(resJsonData, OrderInfoBean.class);
                           if (orderInfoBean!=null) {
                               mView.getSearchReserveInfoResult(orderInfoBean);
                           }else{
                               mView.showEmpty();
                           }
                       }
                   }else{
                       mView.showRetry();
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
               return SEND_ORDER_INFO_REQUEST_TAG;
           }
       });
    }
}
