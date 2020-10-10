package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.TakeMedicinalRateBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:处方药品Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-17 14:25
 */
public class PrescriptionMedicinalPresenter extends BasePresenterImpl
        <PrescriptionMedicinalContract.View> implements PrescriptionMedicinalContract.Presenter {

    private static final String SEND_TAKE_MEDICINAL_RATE_REQUEST_TAG="send_take_medicinal_rate_request_tag";
    private static final String SEND_PRESCRIPTION_TYPE_REQUEST_TAG="send_prescription_type_request_tag";
    private static final String SEND_SAVE_AND_UPDATE_PRESCRIPTION_REQUEST_TAG
            ="send_save_and_update_prescription_request_tag";
    private static final String SEND_DELETE_PRESCRIPTION_REQUEST_TAG="send_delete_prescription_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_TAKE_MEDICINAL_RATE_REQUEST_TAG
                ,SEND_PRESCRIPTION_TYPE_REQUEST_TAG
                ,SEND_SAVE_AND_UPDATE_PRESCRIPTION_REQUEST_TAG
                ,SEND_DELETE_PRESCRIPTION_REQUEST_TAG};
    }


    @Override
    public void sendTakeMedicinalRateRequest(String baseCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        List<TakeMedicinalRateBean>
                                takeMedicinalRateBeans = GsonUtils.jsonToList(resJsonData,
                                TakeMedicinalRateBean.class);
                        mView.getTakeMedicinalRateResult(takeMedicinalRateBeans);
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);

            }

            @Override
            protected String setTag() {
                return SEND_TAKE_MEDICINAL_RATE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendPrescriptionTypeRequest(String baseCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(101);
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
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        List<PrescriptionTypeBean>
                                prescriptionTypeBeans = GsonUtils.jsonToList(resJsonData,
                                PrescriptionTypeBean.class);
                        mView.getPrescriptionTypeResult(prescriptionTypeBeans);
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);

            }

            @Override
            protected String setTag() {
                return SEND_PRESCRIPTION_TYPE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSaveAndUpdatePrescriptionRequest(List<PrescriptionItemUploadBean> uploadBeans
            ,String prescribeVouche, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("prescribeListStr",uploadBeans);
        hashMap.put("prescribeVoucher",prescribeVouche);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLocalApi().operUpdMyClinicDetailByPrescribe_200915(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(102);
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
                        mView.getSaveAndUpdatePrescriptionResult(true,baseBean.getResMsg());
                    }else{
                        mView.getSaveAndUpdatePrescriptionResult(false,baseBean.getResMsg());
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getSaveAndUpdatePrescriptionResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_SAVE_AND_UPDATE_PRESCRIPTION_REQUEST_TAG;
            }
        });
    }



    @Override
    public void sendDeletePrescriptionRequest(String drugOrderCode, String orderCode,int pos, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("drugOrderCode",drugOrderCode);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operDelMyClinicDetailByPrescribe_200915(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(103);
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
                      mView.getDeletePrescriptionResult(true,pos,baseBean.getResMsg());
                    }else{
                        mView.getDeletePrescriptionResult(false,pos,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getDeletePrescriptionResult(false,pos,s);
                }
            }
        });

    }
}
