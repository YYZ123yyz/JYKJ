package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
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
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG = "send_operupd_drug_info_request_tag";

    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG = "send_operupd_drug_info_request_new_tag";

    private static final String SEND_SEARCH_DRUG_TYPE_DOSAGE_REQUEST_TAG = "send_search_drug_type_dosage_request_tag";
    private static final String SEND_GET_DRUG_TYPE_MEDICINE_REQUEST_TAG = "send_get_drug_type_medicine_request_tag";
    private static final String SEND_GET_SMALL_UNIT_REQUEST_TAG = "send_get_small_unit_request_tag";
    private static final String SEND_GET_BIG_UNIT_REQUEST_TAG = "send_get_big_unit_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_OPERUPD_DRUG_INFO_REQUEST_TAG
                , SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG, SEND_SEARCH_DRUG_TYPE_DOSAGE_REQUEST_TAG
                , SEND_GET_DRUG_TYPE_MEDICINE_REQUEST_TAG
                , SEND_GET_SMALL_UNIT_REQUEST_TAG,
                SEND_GET_BIG_UNIT_REQUEST_TAG};
    }

    @Override
    public void sendOperUpdDrugInfoRequest(String drugUseType, String drugName, String specUnit, String specName, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("drugUseType", drugUseType);
        hashMap.put("drugName", drugName);
        hashMap.put("specUnit", specUnit);
        hashMap.put("specName", specName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDrugInfo(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperUpdDrugInfoResult(true, baseBean.getResMsg());
                    } else {
                        mView.getOperUpdDrugInfoResult(false, baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView != null) {
                    mView.getOperUpdDrugInfoResult(false, s);
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
                                           String drugUsageDay, String drugUsageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode", medicineCode);
        hashMap.put("drugName", drugName);
        hashMap.put("drugNameAlias", drugNameAlias);
        hashMap.put("specUnit", specUnit);
        hashMap.put("specName", specName);
        hashMap.put("dosageCode", dosageCode);
        hashMap.put("factoryName", factoryName);
        hashMap.put("drugUsageDay", drugUsageDay);
        hashMap.put("drugUsageNum", drugUsageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDrugInfo_201116(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperUpdDrugInfoResult(true, baseBean.getResMsg());
                    } else {
                        mView.getOperUpdDrugInfoResult(false, baseBean.getResMsg());
                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG;
            }
        });

    }

    @Override
    public void sendOperUpdDrugInfo_201208(String medicineCode, String drugName,
                                           String drugNameAlias, String specUnitNum,
                                           String specUnit, String specUnitBig, String specName,
                                           String dosageCode, String factoryName,
                                           String drugUsageRateNum, String drugUsageRateDay,
                                           String drugUsageNumUnit, String drugUsageNumFrequency,
                                           Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode",medicineCode);
        hashMap.put("drugName",drugName);
        hashMap.put("drugNameAlias",drugNameAlias);
        hashMap.put("specUnitNum",specUnitNum);
        hashMap.put("specUnit",specUnit);
        hashMap.put("specUnitBig",specUnitBig);
        hashMap.put("specName",specName);
        hashMap.put("dosageCode",dosageCode);
        hashMap.put("factoryName",factoryName);
        hashMap.put("drugUsageRateNum",drugUsageRateNum);
        hashMap.put("drugUsageRateDay",drugUsageRateDay);
        hashMap.put("drugUsageNumUnit",drugUsageNumUnit);
        hashMap.put("drugUsageNumFrequency",drugUsageNumFrequency);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdDrugInfo_201208(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getOperUpdDrugInfoResult(true, baseBean.getResMsg());
                    } else {
                        mView.getOperUpdDrugInfoResult(false, baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPERUPD_DRUG_INFO_REQUEST_NEW_TAG;
            }
        });


    }

    @Override
    public void sendSearchDrugTypeDosageRequest(Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchDrugTypeDosage(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (mView != null) {
                            int resCode = baseBean.getResCode();
                            if (resCode == 1) {
                                List<DrugDosageBean> drugDosageBeans
                                        = GsonUtils.jsonToList(baseBean.getResJsonData(), DrugDosageBean.class);
                                mView.getSearchDrugTypeDosageResult(drugDosageBeans);
                            }
                        }
                    }

                    @Override
                    protected void onError(String s) {
                        super.onError(s);
                    }

                    @Override
                    protected String setTag() {
                        return SEND_SEARCH_DRUG_TYPE_DOSAGE_REQUEST_TAG;
                    }
                });
    }

    @Override
    public void sendGetDrugTypeMedicineRequest(String medicineCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode", medicineCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getDrugTypeMedicine(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView != null) {
                    mView.showLoading(103);
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
                        List<DrugClassificationBean> drugClassificationBeans
                                = GsonUtils.jsonToList(resJsonData, DrugClassificationBean.class);
                        if (!CollectionUtils.isEmpty(drugClassificationBeans)) {
                            mView.getDrugClassificationBeanResult(drugClassificationBeans);
                        } else {
                            mView.showEmpty();
                        }
                    } else {
                        mView.showEmpty();
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_DRUG_TYPE_MEDICINE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendGetDurgSmallUnitRequest(String baseCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (mView != null) {
                            int resCode = baseBean.getResCode();
                            if (resCode == 1) {
                                List<BaseReasonBean> baseReasonBeans =
                                        GsonUtils.jsonToList(baseBean.getResJsonData(), BaseReasonBean.class);
                                mView.getDurgSmallUnitResult(baseReasonBeans);
                            }
                        }
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_SMALL_UNIT_REQUEST_TAG;
                    }
                });
    }

    @Override
    public void sendGetDrugBigUnitRequest(String baseCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (mView != null) {
                            int resCode = baseBean.getResCode();
                            if (resCode == 1) {
                                List<BaseReasonBean> baseReasonBeans =
                                        GsonUtils.jsonToList(baseBean.getResJsonData(), BaseReasonBean.class);
                                mView.getDrugBigUnitResult(baseReasonBeans);
                            }
                        }
                    }

                    @Override
                    protected String setTag() {
                        return SEND_GET_BIG_UNIT_REQUEST_TAG;
                    }
                });
    }



}
