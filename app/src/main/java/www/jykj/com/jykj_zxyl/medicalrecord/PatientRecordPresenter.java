package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CheckImResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Created by G on 2020/9/19 9:56
 */
public class PatientRecordPresenter extends BasePresenterImpl<PatientRecordContract.View>
        implements PatientRecordContract.Presenter {

    private static final String GET_PATIENT_TAG = "get_patient_tag";

    private static final String GET_IM_TEST_REQUEST_TAG = "get_im_test_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{GET_PATIENT_TAG, GET_IM_TEST_REQUEST_TAG};
    }


    @Override
    public void getPatientRecord(String param) {
        ApiHelper.getApiService().getPatientMedicalRecord(param).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        String resJsonData = baseBean.getResJsonData();
                        //LogUtils.e("解析数据" + resJsonData);

                        PatientRecordDetBean patientRecordDetBean =
                                GsonUtils.fromJson(resJsonData, PatientRecordDetBean.class);
                        mView.getPatientRecordDet(patientRecordDetBean);
                    } else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView != null) {
                    mView.getDataFailure(s);
                }
            }

            @Override
            protected String setTag() {
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void savePatientMedicalRecord(String param) {
        ApiHelper.getApiService().savePatientMedicalRecord(param)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView != null) {
                            mView.showLoading(101);
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
                        mView.getSaveSucess(true);

                    } else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView != null) {
                    mView.getDataFailure(s);
                }
            }

            @Override
            protected String setTag() {
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void sendPatientRecord(String param) {
        ApiHelper.getApiService().sendPatientMedicalRecord(param).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView != null) {
                    mView.showLoading(102);
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
                        mView.getSendSucess(true);
                    } else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView != null) {
                    mView.getDataFailure(s);
                }
            }

            @Override
            protected String setTag() {
                return GET_PATIENT_TAG;
            }
        });
    }

    @Override
    public void sendiMTestingRequest(String mainPatientCode, String mainPatientName,
                                     Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainPatientCode", mainPatientCode);
        hashMap.put("mainPatientName", mainPatientName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().iMTesting(s).compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        CheckImResultBean checkImResultBean =
                                GsonUtils.fromJson(resJsonData, CheckImResultBean.class);
                        mView.getCheckImResult(checkImResultBean);
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return GET_IM_TEST_REQUEST_TAG;
            }
        });
    }
}
