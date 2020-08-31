package www.jykj.com.jykj_zxyl.app_base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.lang.reflect.ParameterizedType;


/**
 * Description:mvp模式的Fragment基类
 *
 * @author: qiuxin
 * @date: 2018/5/28 14:10
 */


public abstract class AbstractMvpDialogFragment<V extends BaseView, P extends BasePresenterImpl<V>>
        extends DialogFragment
        implements BaseView {

    protected P mPresenter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        mContext = getContext();
    }


    /**
     * 实例化Presenter
     *
     * @param o   泛型对象
     * @param i   i
     * @param <P> Presenter实例
     * @return Presenter实例
     */
    public <P> P getInstance(Object o, int i) {
        try {
            try {
                return ((Class<P>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
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
        return false;
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
}
