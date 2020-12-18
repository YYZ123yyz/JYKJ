package www.jykj.com.jykj_zxyl.personal;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.LogUtils;

import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;

public class StateDetPresenter extends BasePresenterImpl<StateDetContract.View>
        implements StateDetContract.Presenter {

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }




}

