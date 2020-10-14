package www.jykj.com.jykj_zxyl.app_base.base_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseAppFragment";

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected Activity activity;
    private Unbinder unbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init(view);
    }


    /**
     * 加载布局资源
     *
     * @return 布局资源id
     */
    protected abstract int getFragmentLayoutId();

    /**
     * 初始化界面
     *
     * @param view 布局view对象
     */
    protected void init(View view) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解绑布局
        unbinder.unbind();
    }


}
