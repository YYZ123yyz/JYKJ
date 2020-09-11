package www.jykj.com.jykj_zxyl.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseLazyFragment extends Fragment {

    private boolean isViewCreated = false;//布局是否被创建
    private boolean isLoadData = false;//数据是否加载
    private boolean isFirstVisible = true;//是否第一次可见

    abstract protected void onLazyLoadData();//子类实现这个方法，加载数据
    protected abstract int getLayoutId();//获取Fragment的布局Id
    protected abstract void initView(View view);
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
        initView(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isFragmentVisible(this) && this.isAdded()){
            if (this.getParentFragment() == null || isFragmentVisible(this.getParentFragment())) {
                onLazyLoadData();
                isLoadData = true;
                if(isFirstVisible)
                    isFirstVisible = false;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFragmentVisible(this) && !isLoadData && isViewCreated && this.isAdded()){
            onLazyLoadData();
            isLoadData = true;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && !this.isResumed())
            return;

        if(!hidden && isFirstVisible && this.isAdded()){
            onLazyLoadData();
            isFirstVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoadData = false;
        isFirstVisible = true;
    }

    private boolean isFragmentVisible(Fragment fragment) {
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }
}

