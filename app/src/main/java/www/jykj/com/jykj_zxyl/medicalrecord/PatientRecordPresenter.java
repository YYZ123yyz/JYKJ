package www.jykj.com.jykj_zxyl.medicalrecord;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Created by G on 2020/9/19 9:56
 */
public class PatientRecordPresenter extends BasePresenterImpl<PatientRecordContract.View>
        implements PatientRecordContract.Presenter {

    private static final String GET_PATIENT_TAG="get_patient_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getPatientRecord(String param) {
        ApiHelper.getApiService().getPatientMedicalRecord(param).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("解析数据"+resJsonData);

                        PatientRecordDetBean patientRecordDetBean =
                                GsonUtils.fromJson(resJsonData, PatientRecordDetBean.class);
                        mView.getPatientRecordDet(patientRecordDetBean);
                    }else{
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
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

                        mView.dealDataSucess("保存成功");
//                        PatientRecordDetBean patientRecordDetBean =
//                                GsonUtils.fromJson(resJsonData, PatientRecordDetBean.class);
//                        mView.getPatientRecordDet(patientRecordDetBean);
                    }else{
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
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

                        mView.dealDataSucess("发送成功");
//                        PatientRecordDetBean patientRecordDetBean =
//                                GsonUtils.fromJson(resJsonData, PatientRecordDetBean.class);
//                        mView.getPatientRecordDet(patientRecordDetBean);
                    }else{
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getDataFailure(s);
                }
            }

            @Override
            protected String setTag() {
                return GET_PATIENT_TAG;
            }
        });
    }
}
