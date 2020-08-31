package www.jykj.com.jykj_zxyl.app_base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.lang.reflect.ParameterizedType;

/**
 * Description:mvp模式的Fragment基类
 *
 * @author: qiuxinhai
 * @date: 2018/5/28 14:10
 */

public abstract class AbstractMvpBaseFragment<V extends BaseView, P extends BasePresenterImpl<V>>
        extends BaseLazyFragment implements BaseView {
    /**Presenter*/
    protected P mPresenter;
    /**当前页*/
    protected  int pageIndex=1;
    /**每页大小*/
    protected  int pageSize=10;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
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
            return ((Class<P>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }  catch (java.lang.InstantiationException e) {
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
        //return NetworkUtil.isNetworkAvailable();
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

    @Override
    public void showLoading(int code) {

    }


    /**
     * 跳转Activity
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     */
    @SuppressWarnings("rawtypes")
    public void startActivity(Class paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this.getActivity(), paramClass);
        this.startActivity(localIntent);
    }

    /**
     * 跳转Activity,需要回传值
     *
     * @param paramClass  跳转目标Activity
     * @param paramBundle 需要携带的参数
     * @param requestCode 请求码
     */
    @SuppressWarnings("rawtypes")
    public void startActivity(Class paramClass, Bundle paramBundle, int requestCode) {
        Intent localIntent = new Intent();
        if (paramBundle != null) {
            localIntent.putExtras(paramBundle);
        }
        localIntent.setClass(this.getActivity(), paramClass);
        this.startActivityForResult(localIntent, requestCode);
    }
}
