package www.jykj.com.jykj_zxyl.activity.hyhd.presenter;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.activity.hyhd.contract.VideoDetialContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.VideoDetialBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:视频详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-11-25 16:01
 */
public class VideoDetialPresenter extends BasePresenterImpl<VideoDetialContract.View> implements
        VideoDetialContract.Presenter {

    private static final String SEND_GET_VIDEO_DETIAL_REQUEST_TAG="send_get_video_detial_request_tag";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_VIDEO_DETIAL_REQUEST_TAG};
    }



    @Override
    public void sendGetCourseWareDetailRequest(String courseWareCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("courseWareCode",courseWareCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().getCourseWareDetail(s).compose(
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
                        VideoDetialBean videoDetialBean =
                                GsonUtils.fromJson(resJsonData, VideoDetialBean.class);
                        mView.getCourseWareDetailResult(videoDetialBean);
                    }else{
                        mView.showRetry();
                    }

                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_VIDEO_DETIAL_REQUEST_TAG;
            }
        });
    }
}
