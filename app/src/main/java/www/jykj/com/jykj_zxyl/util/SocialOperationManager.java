package www.jykj.com.jykj_zxyl.util;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;

import java.util.HashMap;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-12-04 18:10
 */
public class SocialOperationManager {

    private static class InstanceHolder {
        private static final SocialOperationManager INSTANCE = new SocialOperationManager();
    }

    public static SocialOperationManager getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * 发送喜欢请求
     * @param detailsCode 直播间Id
     * @param activity Activity
     * @param onCallBackListener 监听
     */
    public void sendExtendBroadcastFollowNumRequest(String detailsCode,
                                                    Activity activity,OnCallBackListener
                                                            onCallBackListener){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().extendBroadcastFollowNum(s).compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    if (onCallBackListener!=null) {
                        onCallBackListener.onResult(true,baseBean.getResMsg());
                    }
                }else{
                    if (onCallBackListener!=null) {
                        onCallBackListener.onResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (onCallBackListener!=null) {
                    onCallBackListener.onResult(false,s);
                }
            }
        });
    }


    /**
     * 发送取消喜欢请求
     * @param detailsCode 直播间Id
     * @param activity Activity
     * @param onCallBackListener 监听
     */
    public void sendNumberofprecastviewerscancelledRequest(String detailsCode,
                                                           Activity activity,OnCallBackListener
            onCallBackListener ){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseDoctorParam(activity);
        hashMap.put("detailsCode",detailsCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getLiveApi().Numberofprecastviewerscancelled(s).compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    if (onCallBackListener!=null) {
                        onCallBackListener.onResult(true,baseBean.getResMsg());
                    }
                }else{
                    if (onCallBackListener!=null) {
                        onCallBackListener.onResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                if (onCallBackListener!=null) {
                    onCallBackListener.onResult(false,s);
                }
            }
        });


    }




    public interface OnCallBackListener {

        void onResult(boolean isSucess, String msg);
    }
}
