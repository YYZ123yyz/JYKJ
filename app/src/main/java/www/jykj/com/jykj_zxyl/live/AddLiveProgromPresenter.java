package www.jykj.com.jykj_zxyl.live;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description: ti
 *
 * @author: qiuxinhai
 * @date: 2020-11-14 15:05
 */
public class AddLiveProgromPresenter extends
        BasePresenterImpl<AddLiveProgromContract.View> implements AddLiveProgromContract.Presenter{

    private static final String SEND_ADD_BROAD_CASTSYLLABUS_REQUEST_TAG
            ="send_add_broad_castsyllabus_request_tag";

    private static final String SEND_UPDATE_BROAD_CASTSYLLABUS_REQUEST_TAG
            ="send_update_broad_castsyllabus_request_tag";
    private static final String SEND_BROAD_CAST_DETIALINFO_REQUEST_TAG
            ="send_broad_cast_detialinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_ADD_BROAD_CASTSYLLABUS_REQUEST_TAG,
                SEND_UPDATE_BROAD_CASTSYLLABUS_REQUEST_TAG
                ,SEND_BROAD_CAST_DETIALINFO_REQUEST_TAG};
    }

    @Override
    public void sendAddBroadcastSyllabusRequest(String operUserCode, String detailsCode, String syllabusContent) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("operUserCode",operUserCode);
        hashMap.put("detailsCode",detailsCode);
        hashMap.put("syllabusContent",syllabusContent);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().addBroadcastSyllabus(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getAddBroadcastSyllabusResult(true,baseBean.getResMsg());
                    }else{
                        mView.getAddBroadcastSyllabusResult(false,baseBean.getResMsg());
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
                return SEND_ADD_BROAD_CASTSYLLABUS_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendUpdateBroadcastSyllabusRequest(String operUserCode, String syllabusCode, String syllabusContent) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("operUserCode",operUserCode);
        hashMap.put("syllabusCode",syllabusCode);
        hashMap.put("syllabusContent",syllabusContent);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().updateBroadcastSyllabus(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getUpdateBroadcastSyllabusResult(true,baseBean.getResMsg());
                    }else{
                        mView.getUpdateBroadcastSyllabusResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_UPDATE_BROAD_CASTSYLLABUS_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendGetBroadcastDetailInfoRequest(String detailsCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().getBroadcastDetailInfo(s).compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        LiveProtromDetialBean liveProtromDetialBean
                                = GsonUtils.fromJson(resJsonData, LiveProtromDetialBean.class);
                        mView.getBroadcastDetailInfoResult(liveProtromDetialBean);
                    }

                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_BROAD_CAST_DETIALINFO_REQUEST_TAG;
            }
        });
    }
}
