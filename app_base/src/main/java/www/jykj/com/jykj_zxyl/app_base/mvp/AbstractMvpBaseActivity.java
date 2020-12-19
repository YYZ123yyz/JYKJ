package www.jykj.com.jykj_zxyl.app_base.mvp;




import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;


import com.blankj.utilcode.util.SizeUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import www.jykj.com.jykj_zxyl.app_base.base_activity.BaseActivity;
import www.jykj.com.jykj_zxyl.app_base.base_utils.NetworkUtil;


/**
 * Description:mvp模式的Activity基类
 *
 * @author: qiuxinhai
 * @date: 2018/5/28 14:10
 */

public abstract class AbstractMvpBaseActivity<V extends BaseView, P extends BasePresenterImpl<V>>
        extends BaseActivity implements BaseView, View.OnLayoutChangeListener, View.OnFocusChangeListener {

    protected P mPresenter;
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
    }


    public <P> P getInstance(Object o, int i) {
        try {
            return ((Class<P>) ((ParameterizedType)
                    (o.getClass().getGenericSuperclass())).getActualTypeArguments()
                    [i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }





    @Override
    public void hideLoading() {
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isNetworkAvailable(this);
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void showWaitLoading() {

    }

    @Override
    public void hideWaitLoading() {

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }
}
