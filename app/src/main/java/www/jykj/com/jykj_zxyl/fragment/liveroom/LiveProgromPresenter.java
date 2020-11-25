package www.jykj.com.jykj_zxyl.fragment.liveroom;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.LiveProtromDetialBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

/**
 * Description:直播大纲presenter
 *
 * @author: qiuxinhai
 * @date: 2020-11-19 15:19
 */
public class LiveProgromPresenter extends BasePresenterImpl<LiveProgromContract.View>
        implements LiveProgromContract.Presenter {
    private static final String SEND_GET_BROAD_CAST_DETAILINFO_REQUEST_TAG
            ="send_get_braod_cast_detialinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_BROAD_CAST_DETAILINFO_REQUEST_TAG};
    }

    @Override
    public void sendGetBroadcastDetailInfoRequest(String detailsCode, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().getBroadcastDetailInfo(s).compose(
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
                        LiveProtromDetialBean liveProtromDetialBean
                                = GsonUtils.fromJson(resJsonData, LiveProtromDetialBean.class);
                        mView.getBroadcastDetailInfoResult(liveProtromDetialBean);
                    }else{
                        mView.showEmpty();
                    }


                }


            }

            @Override
            protected String setTag() {
                return SEND_GET_BROAD_CAST_DETAILINFO_REQUEST_TAG;
            }
        });
    }
}
