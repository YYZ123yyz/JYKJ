package www.jykj.com.jykj_zxyl.activity.home.mypatient.fillindetails;

import android.text.TextUtils;
import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.activity.home.mypatient.history.HistoryContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ViewInteractOrderMedicalRecordDetailBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class FillindetailsPresenter extends BasePresenterImpl<FillindetailsContract.View>
        implements FillindetailsContract.Presenter {
    @Override
    public void sendSearchFillindetailsRequest(String loginPatientPosition, String requestClientType, String operPatientCode, String operPatientName, String orderCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition", loginPatientPosition);
        hashMap.put("requestClientType", requestClientType);
        hashMap.put("operPatientCode", operPatientCode);
        hashMap.put("operPatientName", operPatientName);
        hashMap.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getFillindetails(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        Log.e("TAG", "详情: "+resJsonData );
                        if (!TextUtils.isEmpty(resJsonData)) {
                            ViewInteractOrderMedicalRecordDetailBean viewInteractOrderMedicalRecordDetailBean = GsonUtils.fromJson(resJsonData, ViewInteractOrderMedicalRecordDetailBean.class);
                            mView.getSearchFillindetailsResult(viewInteractOrderMedicalRecordDetailBean);
                        }

                    } else {

                    }
                }
            }
        });
    }

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
