package www.jykj.com.jykj_zxyl.patient;

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
 * Description:签约患者Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-30 10:31
 */
public class MySignUpPatientPresenter extends
        BasePresenterImpl<MySignUpPatientContract.View>
        implements MySignUpPatientContract.Presenter {
    public static final String SEND_SIGN_UP_PATIENT_LIST_REQUEST_TAG="send_sign_up_patient_list_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SIGN_UP_PATIENT_LIST_REQUEST_TAG};
    }



    @Override
    public void sendSearchDoctorManagePatientDataByParamRequest(int rowNum, int pageNum,
                                                                String searchDoctorCode,
                                                                String searchStateType,
                                                                Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        hashMap.put("searchDoctorCode",searchDoctorCode);
        hashMap.put("searchStateType",searchStateType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchDoctorManagePatientDataByParam(s)
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

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_SIGN_UP_PATIENT_LIST_REQUEST_TAG;
            }
        });
    }
}
