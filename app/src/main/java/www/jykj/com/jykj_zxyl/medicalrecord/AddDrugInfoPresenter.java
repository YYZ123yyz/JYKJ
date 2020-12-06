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
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:添加药品Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-18 10:14
 */
public class AddDrugInfoPresenter extends BasePresenterImpl<AddDrugInfoContract.View>
        implements AddDrugInfoContract.Presenter {
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG="send_operupd_drug_info_request_tag";

    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG="send_operupd_drug_info_request_new_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_OPERUPD_DRUG_INFO_REQUEST_TAG,SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG};
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



    @Override
    public void sendOperUpdDrugInfo_201116(String medicineCode, String drugName, String drugNameAlias
            , String specUnit, String specName, String dosageCode, String factoryName,
                                           String drugUsageDay, String drugUsageNum,Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode",medicineCode);
        hashMap.put("drugName",drugName);
        hashMap.put("drugNameAlias",drugNameAlias);
        hashMap.put("specUnit",specUnit);
        hashMap.put("specName",specName);
        hashMap.put("dosageCode",dosageCode);
        hashMap.put("factoryName",factoryName);
        hashMap.put("drugUsageDay",drugUsageDay);
        hashMap.put("drugUsageNum",drugUsageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDrugInfo_201116(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(101);
                }
            }

            @Override
            public void hideLoadingView() {

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
            protected String setTag() {
                return SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG;
            }
        });

    }
}
