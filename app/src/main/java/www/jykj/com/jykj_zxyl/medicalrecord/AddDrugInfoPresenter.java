package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:添加药品Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-18 10:14
 */
public class AddDrugInfoPresenter extends BasePresenterImpl<AddDrugInfoContract.View>
        implements AddDrugInfoContract.Presenter {
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG="send_operupd_drug_info_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_OPERUPD_DRUG_INFO_REQUEST_TAG};
    }

    @Override
    public void sendOperUpdDrugInfoRequest(String drugUseType, String drugName, String specUnit, String specName, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("drugUseType",drugUseType);
        hashMap.put("drugName",drugName);
        hashMap.put("specUnit",specUnit);
        hashMap.put("specName",specName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDrugInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        mView.getOperUpdDrugInfoResult(true,baseBean.getResMsg());
                    }else{
                        mView.getOperUpdDrugInfoResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getOperUpdDrugInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPERUPD_DRUG_INFO_REQUEST_TAG;
            }
        });

    }
}
