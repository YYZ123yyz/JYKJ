package www.jykj.com.jykj_zxyl.activity.home.mypatient.history;

import android.text.TextUtils;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DoctorRecordBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.Myself_DetaiBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

public class Myself_DetailPresenter  extends BasePresenterImpl<Myself_DetailContract.View>
        implements Myself_DetailContract.Presenter {
    @Override
    public void sendSearchMyself_DetaiRequest(String loginPatientPosition, String requestClientType, String operPatientCode, String operPatientName, String recordId) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition", loginPatientPosition);
        hashMap.put("requestClientType", requestClientType);
        hashMap.put("operPatientCode", operPatientCode);
        hashMap.put("operPatientName", operPatientName);
        hashMap.put("recordId", recordId);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchmyself_detail(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                            Myself_DetaiBean myself_detaiBean = GsonUtils.fromJson(resJsonData, Myself_DetaiBean.class);
                            mView.getSearchMyself_DetailResult(myself_detaiBean);
                        }

                    } else {
                        mView.getSearchMyself_DetailResultError(baseBean.getResMsg());
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
