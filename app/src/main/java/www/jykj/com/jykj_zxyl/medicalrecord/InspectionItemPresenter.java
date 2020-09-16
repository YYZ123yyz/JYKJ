package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemGradeBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemUploadBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:检查检验Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 14:33
 */
public class InspectionItemPresenter extends BasePresenterImpl<InspectionItemContract.View>
        implements InspectionItemContract.Presenter {

    private static final String SEND_SEARCH_INTERACT_ORDER_LIST_REQUEST_TAG="send_search_interact_order_request_tag";

    private static final String SEND_SEARCH_INSPECTION_GRADLE_LIST_REQUEST_TAG
            ="send_search_inspection_gradle_list_request_tag";

    private static final String SEND_OPER_DEL_INTERACT_ORDER_INSPECTION_REQUEST_TAG
            ="send_oper_del_interct_order_inspection_request_tag";
    private static final String SEND_OPER_UPDATE_INTERACT_ORDER_INSPECTION_REQUEST_TAG="send_oper_upd_inter_inspection_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_INTERACT_ORDER_LIST_REQUEST_TAG
                ,SEND_SEARCH_INSPECTION_GRADLE_LIST_REQUEST_TAG
                ,SEND_OPER_DEL_INTERACT_ORDER_INSPECTION_REQUEST_TAG
                ,SEND_OPER_UPDATE_INTERACT_ORDER_INSPECTION_REQUEST_TAG};
    }


    @Override
    public void sendSearchInteractOrderInspectionListRequest(String hospitalInfoCode,
                                                             String orderCode,
                                                             String inspectionOrderCode,Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("hospitalInfoCode",hospitalInfoCode);
        hashMap.put("orderCode",orderCode);
        hashMap.put("inspectionOrderCode",inspectionOrderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchInteractOrderInspectionList(s)
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
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<InspectionItemBean> inspectionItemBeans
                                = GsonUtils.jsonToList(resJsonData, InspectionItemBean.class);
                        mView.getSearchInspectionItemListResult(inspectionItemBeans);
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
                return SEND_SEARCH_INTERACT_ORDER_LIST_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSearchInspectionGradeListRequest(String hospitalInfoCode,
                                                     String gradeCode,int pos, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("hospitalInfoCode",hospitalInfoCode);
        hashMap.put("gradeCode",gradeCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchInspectionProjectDetailGradeList(s)
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
                        InspectionItemGradeBean inspectionItemGradeBean =
                                GsonUtils.fromJson(resJsonData, InspectionItemGradeBean.class);
                        mView.getSearchInspectionGradeListResult(inspectionItemGradeBean,pos);
                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_INSPECTION_GRADLE_LIST_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendOperDelInteractOrderInspectionRequest(String inspectionOrderCode,int pos, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("inspectionOrderCode",inspectionOrderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operDelInteractOrderInspection(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getDelInteractOrderInspectionResult(true,pos,baseBean.getResMsg());
                    }else{
                        mView.getDelInteractOrderInspectionResult(false,pos,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_OPER_DEL_INTERACT_ORDER_INSPECTION_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendOperUpdInteractOrderInspectionRequest(List<InspectionItemUploadBean> uploadBeans, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("orderListStr", uploadBeans);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operUpdInteractOrderInspection(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getUpdateInteractOrderInspectionResult(
                                true,baseBean.getResMsg());
                    }else{
                        mView.getUpdateInteractOrderInspectionResult(
                                false,baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.getUpdateInteractOrderInspectionResult(
                            false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_UPDATE_INTERACT_ORDER_INSPECTION_REQUEST_TAG;
            }
        });
    }
}
