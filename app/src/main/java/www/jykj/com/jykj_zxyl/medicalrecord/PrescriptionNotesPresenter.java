package www.jykj.com.jykj_zxyl.medicalrecord;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PrescriptionNotesBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:处方笺列表Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-09-19 16:23
 */
public class PrescriptionNotesPresenter extends BasePresenterImpl<PrescriptionNotesContract.View>
        implements PrescriptionNotesContract.Presenter  {

    private static final String SEND_PRESCRIPTION_NOTES_REQUEST_TAG="send_prescription_notes_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_PRESCRIPTION_NOTES_REQUEST_TAG};
    }

    @Override
    public void sendPrescriptionNotesRequest(String orderCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchMyClinicDetailResPrescribe_200915(s)
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
                    mView.hideKeyboard();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        List<PrescriptionNotesBean> prescriptionNotesBeans
                                = GsonUtils.jsonToList(baseBean.getResJsonData(), PrescriptionNotesBean.class);

                        if (!CollectionUtils.isEmpty(prescriptionNotesBeans)) {
                            mView.getPrescriptionNotesResult(prescriptionNotesBeans);
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
                return SEND_PRESCRIPTION_NOTES_REQUEST_TAG;
            }
        });
    }
}
