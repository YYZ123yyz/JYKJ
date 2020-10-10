package www.jykj.com.jykj_zxyl.fragment;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BannerAndHospitalInfoBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import yyz_exploit.Utils.GsonUtil;

/**
 * Description:首页Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-09 18:07
 */
public class HomePagerPresenter extends BasePresenterImpl<HomePagerContract.View>
        implements HomePagerContract.Presenter {
    private static final String SEND_GET_BANNER_AND_HOSPITALINFO_REQUESt_TAG="send_get_banner_and_hospitalinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_BANNER_AND_HOSPITALINFO_REQUESt_TAG};
    }


    @Override
    public void sendGetBannerAndHospitalInfoRequest(String appTypeCode,
                                                    List<String> positionTypeList,
                                                    String hospitalCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("requestClientType","1");
        hashMap.put("appTypeCode",appTypeCode);
        hashMap.put("positionTypes",positionTypeList);
        hashMap.put("hospitalCode",hospitalCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBannerAndHospitalInfo(s)
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
                        BannerAndHospitalInfoBean
                                bannerAndHospitalInfoBean = GsonUtil.parseJsonToBean(resJsonData, BannerAndHospitalInfoBean.class);
                        mView.getBannerAndHospitalInfoResult(bannerAndHospitalInfoBean);
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);


            }

            @Override
            protected String setTag() {
                return SEND_GET_BANNER_AND_HOSPITALINFO_REQUESt_TAG;
            }
        });

    }
}
