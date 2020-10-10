package www.jykj.com.jykj_zxyl.medicalrecord;


import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:检查部位查询列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 16:23
 */
public class InspectionPositionChoosedPresenter
        extends BasePresenterImpl<InspectionPositionChoosedContract.View>
        implements InspectionPositionChoosedContract.Presenter {

    private static final String SEND_INSPECTION_POSITION_REQUEST_TAG = "send_inspection_position_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_INSPECTION_POSITION_REQUEST_TAG};
    }

    @Override
    public void sendSearchInspectionPositionRequest(String hospitalInfoCode,
                                                                   String inspectionCode,
                                                                   String positionName,
                                                                   String rowNum,
                                                                   String pageNum,
                                                                   Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("hospitalInfoCode", hospitalInfoCode);
        hashMap.put("inspectionCode", inspectionCode);
        hashMap.put("positionName", positionName);
        hashMap.put("rowNum", rowNum);
        hashMap.put("pageNum", pageNum);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchInspectionProjectDetailConfigList(s).compose(
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
                        String resJsonData = baseBean.getResJsonData();
                        List<InspectionItemPositionBean> inspectionItemPositionBeans =
                                GsonUtils.jsonToList(resJsonData, InspectionItemPositionBean.class);
                        if (!CollectionUtils.isEmpty(inspectionItemPositionBeans)) {
                            mView.getSearchInspectionPositionResult(inspectionItemPositionBeans);
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
                return SEND_INSPECTION_POSITION_REQUEST_TAG;
            }
        });

    }
}
