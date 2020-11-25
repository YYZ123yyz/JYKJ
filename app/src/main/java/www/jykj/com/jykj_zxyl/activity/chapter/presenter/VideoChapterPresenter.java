package www.jykj.com.jykj_zxyl.activity.chapter.presenter;


import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.activity.chapter.contract.VideoChapterContract;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.StringUtils;


public class VideoChapterPresenter extends BasePresenterImpl<VideoChapterContract.View>
        implements VideoChapterContract.Presenter {

    private static final String GET_PATIENT_TAG = "get_video_chapter";

    private static final String GET_IM_TEST_REQUEST_TAG = "get_video_chapter";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{GET_PATIENT_TAG, GET_IM_TEST_REQUEST_TAG};
    }


    @Override
    public void getVideoChapterList(String params) {
  /*      HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);*/
        ApiHelper.getLiveApi().getHomeChapterList(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView!=null) {
//                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView!=null) {
//                            mView.hideLoading();
                        }
                    }
                })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        LogUtils.e("课件详情  "+ resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            ChapterListBean chapterListBean = GsonUtils.fromJson(resJsonData, ChapterListBean.class);
                            mView.getListSucess(chapterListBean);
                        }else{
                            mView.getDataFail(baseBean.getResMsg());
                        }

                    }else{
                        mView.getDataFail(baseBean.getResMsg());
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
                return GET_PATIENT_TAG;
            }
        });
    }
}
