package www.jykj.com.jykj_zxyl.consultation;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import entity.DoctorInfo.InteractPatient;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:签约用户Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 17:28
 */
public class SignUpPatientListPresenter extends BasePresenterImpl<SignUpPatientListContract.View>
        implements SignUpPatientListContract.Presenter {
    private static final String SEND_SIGN_UP_PATIENT_REQUEST_TAG="send_sign_up_patient_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SIGN_UP_PATIENT_REQUEST_TAG};
    }

    @Override
    public void sendGetSignUpPatientRequest(String doctorId, String doctorCode) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("doctorId",doctorId);
        hashMap.put("doctorCode",doctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().interactPatientSigningList(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        List<InteractPatient> interactPatients
                                = GsonUtils.jsonToList(resJsonData, InteractPatient.class);
                        if (!CollectionUtils.isEmpty(interactPatients)) {
                            mView.getSignUpPatientResult(interactPatients);
                        }else{
                            mView.showEmpty();
                        }
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
                return SEND_SIGN_UP_PATIENT_REQUEST_TAG;
            }
        });
    }


}
