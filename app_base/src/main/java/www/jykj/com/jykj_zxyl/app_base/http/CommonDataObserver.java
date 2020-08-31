package www.jykj.com.jykj_zxyl.app_base.http;

import com.allen.library.observer.StringObserver;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.GsonUtils;


/**
 * Description:解析公共参数
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 19:44
 */
public abstract class CommonDataObserver extends StringObserver {
    @Override
    protected void onError(String s) {

    }

    protected abstract void onSuccessResult(BaseBean baseBean);

    @Override
    protected void onSuccess(String s) {
        BaseBean baseBean = GsonUtils.fromJson(s, BaseBean.class);
        if (baseBean!=null) {
            onSuccessResult(baseBean);
        }

    }


}
