package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.hyphenate.easeui.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemProjectBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemCategoryBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:检查检验项目选项列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-11 10:34
 */
public class InspectionProjectChoosedPresenter extends BasePresenterImpl<InspectionProjectChoosedContract.View>
        implements InspectionProjectChoosedContract.Presenter {

    private static final String SEND_SEARCH_INSPECTION_PROJECT_DETIAL_CLASS_LIST_REQUEST_TAG
            ="send_search_inspection_project_class_list_request_tag";

    private static final String SEND_SEARCH_INSPECTION_PROJECT_DETAIL_LIST_REQUEST_TAG
            ="send_search_inspection_project_detial_list_rquest_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_SEARCH_INSPECTION_PROJECT_DETIAL_CLASS_LIST_REQUEST_TAG
                ,SEND_SEARCH_INSPECTION_PROJECT_DETAIL_LIST_REQUEST_TAG};
    }


    @Override
    public void sendSearchInspectionProjectDetailClassListRequest(String hospitalInfoCode,
                                                                  Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("hospitalInfoCode",hospitalInfoCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchInspectionProjectDetailClassList(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        List<InspectionItemCategoryBean> inspectionItemBeans =
                                GsonUtils.jsonToList(baseBean.getResJsonData(), InspectionItemCategoryBean.class);
                        if (!CollectionUtils.isEmpty(inspectionItemBeans)) {
                            mView.getSearchInspectionProjectDetialClassListResult(inspectionItemBeans);
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
                return SEND_SEARCH_INSPECTION_PROJECT_DETIAL_CLASS_LIST_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSearchInspectionProjectDetailListRequest(String hospitalInfoCode
            , String parentInspectionCode, String inspectionName, String rowNum, String pageNum, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("hospitalInfoCode",hospitalInfoCode);
        hashMap.put("parentInspectionCode",parentInspectionCode);
        hashMap.put("inspectionName",inspectionName);
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchInspectionProjectDetailList(s)
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
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<InspectionItemProjectBean>
                                inspectionItemBeans =
                                GsonUtils.jsonToList(resJsonData, InspectionItemProjectBean.class);

                        if (!CollectionUtils.isEmpty(inspectionItemBeans)) {
                            mView.getSearchInspectionProjectDetailListResult(inspectionItemBeans);
                        } else {
                            mView.showEmpty();
                        }
                    } else {
                        mView.showEmpty();
                    }

                }
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_INSPECTION_PROJECT_DETAIL_LIST_REQUEST_TAG;
            }
        });
    }
}
