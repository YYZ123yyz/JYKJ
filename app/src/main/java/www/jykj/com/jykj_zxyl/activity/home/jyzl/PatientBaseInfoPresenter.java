package www.jykj.com.jykj_zxyl.activity.home.jyzl;

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
 * Description:患者基本信息Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 14:26
 */
public class PatientBaseInfoPresenter extends
        BasePresenterImpl<PatientBaseInfoContract.View> implements PatientBaseInfoContract.Presenter {

    private static final String SEND_GET_PATIENT_BASEINFO_REQUEST_TAG="send_get_patient_baseinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_PATIENT_BASEINFO_REQUEST_TAG};
    }

    @Override
    public void sendPatientBaseInfoRequest(String searchPatientCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("searchPatientCode",searchPatientCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchDoctorManagePatientStateResBasic(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_PATIENT_BASEINFO_REQUEST_TAG;
            }
        });
    }
}
