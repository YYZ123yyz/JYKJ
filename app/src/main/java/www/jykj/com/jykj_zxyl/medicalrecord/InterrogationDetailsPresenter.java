package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InteractPatientInterrogation;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:问诊详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-25 10:30
 */
public class InterrogationDetailsPresenter extends
        BasePresenterImpl<InterrogationDetailsContract.View>
        implements InterrogationDetailsContract.Presenter {

    public static final String SEND_INTERROGATIOND_DETAIL_REQUEST_TAG="" +
            "send_interrogationd_detail_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_INTERROGATIOND_DETAIL_REQUEST_TAG};
    }

    @Override
    public void sendInterrogationDetailRequest(String orderCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMyClinicDetailResPatientInterrogation(s)
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
                        InteractPatientInterrogation
                                interactPatientInterrogation = GsonUtils.fromJson(resJsonData, InteractPatientInterrogation.class);
                        mView.getInterrogationDetailResult(interactPatientInterrogation);
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
                return SEND_INTERROGATIOND_DETAIL_REQUEST_TAG;
            }
        });
    }
}
