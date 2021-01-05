package www.jykj.com.jykj_zxyl.mypatient.presenter;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.mypatient.contract.FragmentContract;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import yyz_exploit.bean.Status;

public class FragmentPresenter  extends BasePresenterImpl<FragmentContract.View>
        implements FragmentContract.Presenter  {
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG="send_operupd_drug_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendSearchPatientListRequest(String rowNum, String pageNum, String loginDoctorPosition
            , String searchDoctorCode, String searchStateType, String patientName,
                                             Integer ageStart, Integer ageEnd) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("rowNum",rowNum);
        hashMap.put("pageNum",pageNum);
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("searchDoctorCode",searchDoctorCode);
        hashMap.put("searchStateType",searchStateType);
        hashMap.put("patientName",patientName);
        hashMap.put("ageStart",ageStart);
        hashMap.put("ageEnd",ageEnd);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchList(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                    String resJsonData = baseBean.getResJsonData();
                    if (resCode==1) {
                        List<ProvideViewPatientLablePunchClockState>
                                provideViewPatientLablePunchClockStates = GsonUtils.jsonToList(resJsonData, ProvideViewPatientLablePunchClockState.class);
                        mView.getOperListResult(provideViewPatientLablePunchClockStates);
                    }else{

                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    //   mView.getOperUpdDrugInfoResult(false,s);
                }
            }

            @Override
            protected String setTag() {
                return SEND_OPERUPD_DRUG_INFO_REQUEST_TAG;
            }
        });
    }
}
