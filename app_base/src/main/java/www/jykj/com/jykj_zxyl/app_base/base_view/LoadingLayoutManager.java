package www.jykj.com.jykj_zxyl.app_base.base_view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import www.jykj.com.jykj_zxyl.app_base.R;


/**
 * 多状态布局
 */
public class LoadingLayoutManager extends FrameLayout {

    /**
     * Inflater
     */
    private LayoutInflater mInflater;
    /**
     * 原布局
     */
    private int mContentId;
    /**
     * 加载中布局
     */
    private int mLoadingResId;
    /**
     * 无数据布局
     */
    private int mEmptyResId;
    /**
     * 加载出错布局
     */
    private int mErrorResId;
    /**
     * 刷新重试监听
     */
    private OnClickListener mRetryListener;
    /**
     * 创建操作
     */
    private OnClickListener mCreateListener;
    /**
     * 布局集合
     */
    private Map<Integer, View> mLayouts;


    public static LoadingLayoutManager wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static LoadingLayoutManager wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static LoadingLayoutManager wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        LoadingLayoutManager layout = new LoadingLayoutManager(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    public LoadingLayoutManager(Context context) {
        this(context, null, 0);
    }

    public LoadingLayoutManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayoutManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化参数
     */
    @SuppressLint("UseSparseArrays")
    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mEmptyResId = R.layout._loading_layout_empty;
        mLoadingResId = R.layout._loading_layout_loading;
        mErrorResId = R.layout._loading_layout_error;
        mLayouts = new HashMap<>();
    }

    /**
     * 显示加载数据布局
     */
    public void showLoading() {
        show(mLoadingResId);
    }

    /**
     * 显示无数据布局
     */
    public void showEmpty() {
        show(mEmptyResId);
    }

    /**
     * 显示网络出错布局
     */
    public void showError() {
        show(mErrorResId);
    }

    /**
     * 显示原布局内容
     */
    public void showContent() {
        show(mContentId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);
        if (layoutId == mErrorResId) {
            TextView btn = layout.findViewById(R.id.retry_button);
            //重试监听
            if (btn != null) {
                btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mRetryListener != null) {
                            mRetryListener.onClick(v);
                        }
                    }
                });
            }
        }else if(layoutId == mEmptyResId){
            TextView btn = layout.findViewById(R.id.retry_button);
            //重试监听
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    if (mCreateListener != null) {
                        mCreateListener.onClick(v);
                    }
                });
            }
        }
        return layout;
    }

    /*@Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }*/

    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }

    /**
     * 重新设置加载中布局
     */
    public LoadingLayoutManager setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }

    /**
     * 重新设置无数据布局
     */
    public LoadingLayoutManager setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }

    /**
     * 设置无数据布局页面图片资源
     */
    public LoadingLayoutManager setEmptyImage(@DrawableRes int resId) {
        image(mEmptyResId, R.id.empty_image, resId);
        return this;
    }

    /**
     * 设置无数据布局文字
     */
    public LoadingLayoutManager setEmptyText(String value) {
        text(mEmptyResId, R.id.empty_text, value);
        return this;
    }

    /**
     * 设置默认布局加载中显示文字
     */
    public LoadingLayoutManager setLoadingText(String value) {
        text(mLoadingResId, R.id.loading_text, value);
        return this;
    }

    /**
     * 设置加载出错时显示图片
     */
    public LoadingLayoutManager setErrorImage(@DrawableRes int resId) {
        image(mErrorResId, R.id.error_image, resId);
        return this;
    }

    /**
     * 设置加载出错时显示文字
     */
    public LoadingLayoutManager setErrorText(String value) {
        text(mErrorResId, R.id.error_text, value);
        return this;
    }

    /**
     * 设置重试按钮文字
     */
    public LoadingLayoutManager setRetryText(String text) {
        text(mErrorResId, R.id.retry_button, text);
        return this;
    }

    public void setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
    }

    public void setCreateListener(OnClickListener listener){
        mCreateListener = listener;
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    private void text(int layoutId, int ctrlId, CharSequence value) {
        if (mLayouts.containsKey(layoutId)) {
            TextView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setText(value);
            }
        }
    }

    private void image(int layoutId, int ctrlId, int resId) {
        if (mLayouts.containsKey(layoutId)) {
            ImageView view = mLayouts.get(layoutId).findViewById(ctrlId);
            if (view != null) {
                view.setImageResource(resId);
            }
        }
    }

    public void showEmptyData(int mEmptyResId) {
        this.mEmptyResId = mEmptyResId;
        show(mEmptyResId);
    }
}
