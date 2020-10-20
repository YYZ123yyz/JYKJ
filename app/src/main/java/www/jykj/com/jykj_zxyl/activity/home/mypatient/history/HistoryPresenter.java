package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import android.text.TextUtils;
import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.activity.home.mypatient.label.LabelContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientConditionDiseaseRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.ProvidePatientLabelBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class HistoryPresenter  extends BasePresenterImpl<HistoryContract.View>
        implements HistoryContract.Presenter {

    @Override
    public void sendSearchHistoryRequest(String rowNum, String pageNum, String loginDoctorPosition, String requestClientType, String operDoctorCode, String operDoctorName, String searchPatientCode, String dataSourceType) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("rowNum", rowNum);
        hashMap.put("pageNum", pageNum);
        hashMap.put("loginDoctorPosition", loginDoctorPosition);
        hashMap.put("requestClientType", requestClientType);
        hashMap.put("operDoctorCode", operDoctorCode);
        hashMap.put("operDoctorName", operDoctorName);
        hashMap.put("searchPatientCode", searchPatientCode);
        hashMap.put("dataSourceType", dataSourceType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchHistoryformation(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        if (!TextUtils.isEmpty(resJsonData)) {
                            List<DoctorRecordBean> doctorRecordBeans = GsonUtils.jsonToList(resJsonData, DoctorRecordBean.class);
                            mView.getSearchHistoryResult(doctorRecordBeans);
                        }

                    } else {
                        mView.getSearchHistoryResultError(baseBean.getResMsg());
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
