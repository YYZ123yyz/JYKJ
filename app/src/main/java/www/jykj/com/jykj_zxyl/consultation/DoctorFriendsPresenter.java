package www.jykj.com.jykj_zxyl.consultation;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import entity.yhhd.ProvideDoctorGoodFriendGroup;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.util.GsonUtils;

/**
 * Description:医生好友Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 17:59
 */
public class DoctorFriendsPresenter
        extends BasePresenterImpl<DoctorFriendsContract.View> implements DoctorFriendsContract.Presenter{
    private static final String SEND_DOCTOR_FRIENDS_REQUEST_TAG="send_doctor_friends_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_DOCTOR_FRIENDS_REQUEST_TAG};
    }

    @Override
    public void sendDoctorFriendsRequest(String doctorId, String doctorCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("doctorId",doctorId);
        hashMap.put("doctorCode",doctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().interactDoctorUnionAllList(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        List<ProvideDoctorGoodFriendGroup> provideDoctorGoodFriendGroups
                                = GsonUtils.jsonToList(baseBean.getResJsonData(), ProvideDoctorGoodFriendGroup.class);
                        if (!CollectionUtils.isEmpty(provideDoctorGoodFriendGroups)) {
                            mView.getDoctorFriendsResult(provideDoctorGoodFriendGroups);
                        }else{
                            mView.showEmpty();
                        }
                    }else{
                        mView.showEmpty();
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_DOCTOR_FRIENDS_REQUEST_TAG;
            }
        });

    }

}
