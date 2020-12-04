package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugClassificationBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.MedicinalTypeBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:选择药物Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-16 11:32
 */
public class ChoosedMedicinalPresenter
        extends BasePresenterImpl<ChoosedMedicinalContract.View>
        implements ChoosedMedicinalContract.Presenter{

    private static final String SEND_MEDICINAL_TYPE_LIST_REQUEST_TAG="send_medicinal_type_list_request_tag";

    private static final String SEND_MEDICINAL_INFO_LIST_REQUEST_TAG="send_medicinal_info_request_tag";
    private static final String SEND_SEARCH_MY_CLINIC_DETAIL_REQUEST_TAG="send_search_my_clinic" +
            "_detial_request_tag";
    private static final String SEND_GET_DRUG_TYPE_MEDICINE_REQUEST_TAG="send_get_drug_type_medicine_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_MEDICINAL_TYPE_LIST_REQUEST_TAG,SEND_MEDICINAL_INFO_LIST_REQUEST_TAG};
    }

    @Override
    public void sendMedicinalTypeListRequest(String baseCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("baseCode",baseCode);
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
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<MedicinalTypeBean> medicinalTypeBeans =
                                GsonUtils.jsonToList(resJsonData, MedicinalTypeBean.class);
                        mView.getMedicinalTypeListResult(medicinalTypeBeans);

                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_MEDICINAL_TYPE_LIST_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendMedicinalInfoListRequest(String drugUseType, String srarchDrugName,
                                             String rowNum, String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("drugUseType",drugUseType);
        hashMap.put("srarchDrugName",srarchDrugName);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMyClinicDetailResPrescribeDrugInfo_200915(s)
                .compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<MedicinalInfoBean> medicinalInfoBeans =
                                GsonUtils.jsonToList(resJsonData, MedicinalInfoBean.class);
                        if (!CollectionUtils.isEmpty(medicinalInfoBeans)) {
                            mView.getMedicinalInfoListResult(medicinalInfoBeans);
                        }else{
                            mView.showEmpty();
                        }

                    }else{
                        mView.showEmpty();
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_MEDICINAL_INFO_LIST_REQUEST_TAG;
            }
        });


    }

    @Override
    public void sendSearchMyClinicDetailResPrescribeDrugInfo_201116(String medicineCode,
                                                                    String srarchDrugName,
                                                                    int rowNum, int pageNum,
                                                                    Activity activity) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode",medicineCode);
        hashMap.put("srarchDrugName",srarchDrugName);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMyClinicDetailResPrescribeDrugInfo_201116(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_MY_CLINIC_DETAIL_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendGetDrugTypeMedicineRequest(String medicineCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("medicineCode",medicineCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getDrugTypeMedicine(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        String resJsonData = baseBean.getResJsonData();
                        List<DrugClassificationBean> drugClassificationBeans
                                = GsonUtils.jsonToList(resJsonData, DrugClassificationBean.class);
                        if (!CollectionUtils.isEmpty(drugClassificationBeans)) {
                            mView.getDrugClassificationBeanResult(drugClassificationBeans);
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
            }

            @Override
            protected String setTag() {
                return SEND_GET_DRUG_TYPE_MEDICINE_REQUEST_TAG;
            }
        });
    }
}
