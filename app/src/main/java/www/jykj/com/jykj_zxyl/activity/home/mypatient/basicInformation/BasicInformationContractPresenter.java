package www.jykj.com.jykj_zxyl.activity.home.mypatient.basicInformation;

import android.text.TextUtils;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvideViewPatientHealthyAndBasicsBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class BasicInformationContractPresenter extends BasePresenterImpl<BasicInformationContract.View>
        implements BasicInformationContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendSearchBasichealthinformationRequest(String loginDoctorPosition, String requestClientType, String operDoctorCode, String operDoctorName, String searchPatientCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition", loginDoctorPosition);
        hashMap.put("requestClientType", requestClientType);
        hashMap.put("operDoctorCode", operDoctorCode);
        hashMap.put("operDoctorName", operDoctorName);
        hashMap.put("searchPatientCode", searchPatientCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchBasichealthinformation(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (resJsonData!=null) {
                            ProvideViewPatientHealthyAndBasicsBean provideViewPatientHealthyAndBasicsBean = GsonUtils.fromJson(resJsonData, ProvideViewPatientHealthyAndBasicsBean.class);
                            mView.getSearchBasichealthinformationResult(provideViewPatientHealthyAndBasicsBean);
                        }

                    } else {
                        mView.getSearchBasichealthinformationResultError(baseBean.getResMsg());
                    }
                }
            }
        });
    }
}
