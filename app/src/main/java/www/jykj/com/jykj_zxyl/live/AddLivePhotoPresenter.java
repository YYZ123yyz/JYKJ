package www.jykj.com.jykj_zxyl.live;

import android.app.Activity;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.observer.CommonObserver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.UploadLiveImageResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.RxUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.ApiService;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:上传直播大纲图片
 *
 * @author: qiuxinhai
 * @date: 2020-11-16 17:18
 */
public class AddLivePhotoPresenter extends BasePresenterImpl<AddLivePhotoContract.View>
        implements AddLivePhotoContract.Presenter {

    private static final String SEND_ADD_BROAD_CAST_IMAGE_REQUEST_TAG
            ="send_add_broad_cast_image_request_tag";

    private static final String SEND_BROAD_CAST_DETIALINFO_REQUEST_TAG
            ="send_broad_cast_detialinfo_request_tag";

    private static final String SEND_UPDATE_BROAD_CAST_IMAGE_REQUEST_TAG=
            "send_upeate_broad_cast_image_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_ADD_BROAD_CAST_IMAGE_REQUEST_TAG
                ,SEND_BROAD_CAST_DETIALINFO_REQUEST_TAG,SEND_UPDATE_BROAD_CAST_IMAGE_REQUEST_TAG};
    }


    @Override
    public void sendAddBroadCastImageRequest(String detailsCode, String imgBase64Array, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        hashMap.put("imgBase64Array",imgBase64Array);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().addBroadcastImage(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        UploadLiveImageResultBean
                                uploadLiveImageResultBean = GsonUtils.fromJson(resJsonData, UploadLiveImageResultBean.class);
                        mView.getAddBroadCastImageResult(uploadLiveImageResultBean);
                    }else{
                        mView.getAddBroadCastImageError(baseBean.getResMsg());
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
                return SEND_ADD_BROAD_CAST_IMAGE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendAddBroadCastImageRequest2(String detailsCode, List<String> urls, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        String uploadFileUrl = ApiHelper.getUploadFileUrl(100);
        String s = RetrofitUtil.encodeParam(hashMap);
        RxHttpUtils.createApi(ApiService.class).addBroadcastImage2(uploadFileUrl,s,
                RxUtils.getMultipartPart("imgArray", null,
                        urls)).
                compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        UploadLiveImageResultBean
                                uploadLiveImageResultBean = GsonUtils.fromJson(resJsonData, UploadLiveImageResultBean.class);
                        mView.getAddBroadCastImageResult(uploadLiveImageResultBean);
                    }else{
                        mView.getAddBroadCastImageError(baseBean.getResMsg());
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
                return SEND_ADD_BROAD_CAST_IMAGE_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendUpdateBroadcastImageRequest(String imageCode, String detailsCode,
                                                String imgIdArray, String imgBase64Array,
                                                Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("imageCode",imageCode);
        hashMap.put("detailsCode",detailsCode);
        hashMap.put("imgIdArray",imgIdArray);
        hashMap.put("imgBase64Array",imgBase64Array);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().updateBroadcastImage(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        UploadLiveImageResultBean
                                uploadLiveImageResultBean = GsonUtils.fromJson(resJsonData, UploadLiveImageResultBean.class);
                        mView.getAddBroadCastImageResult(uploadLiveImageResultBean);
                    }else{
                        mView.getAddBroadCastImageError(baseBean.getResMsg());
                    }

                }

            }

            @Override
            protected String setTag() {
                return SEND_UPDATE_BROAD_CAST_IMAGE_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendUpdateBroadcastImageRequest2(String imageCode, String detailsCode, String imgIdArray, List<String> urls, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("imageCode",imageCode);
        hashMap.put("detailsCode",detailsCode);
        hashMap.put("imgIdArray",imgIdArray);
        String s = RetrofitUtil.encodeParam(hashMap);
        String uploadFileUrl = ApiHelper.getUploadFileUrl(101);
        RxHttpUtils.createApi(ApiService.class).addBroadcastImage2(uploadFileUrl,s,
                RxUtils.getMultipartPart("imgArray", null,
                        urls)).
                compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        UploadLiveImageResultBean
                                uploadLiveImageResultBean = GsonUtils.fromJson(resJsonData, UploadLiveImageResultBean.class);
                        mView.getAddBroadCastImageResult(uploadLiveImageResultBean);
                    }else{
                        mView.getAddBroadCastImageError(baseBean.getResMsg());
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
                return SEND_UPDATE_BROAD_CAST_IMAGE_REQUEST_TAG;
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
