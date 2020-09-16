package www.jykj.com.jykj_zxyl.medicalrecord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemPositionBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionPositionChoosedContract;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionPositionChoosedPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.InspectionItemPostionAdapter;

/**
 * Description:检查检验部位选择
 *
 * @author: qiuxinhai
 * @date: 2020-09-14 16:14
 */
public class InspectionPositionChoosedActivity extends AbstractMvpBaseActivity<
        InspectionPositionChoosedContract.View, InspectionPositionChoosedPresenter>
        implements InspectionPositionChoosedContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.tv_search_btn)
    TextView tvSearchBtn;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private InspectionItemPostionAdapter mInspectionItemPostionAdapter;
    private List<InspectionItemPositionBean> mPositionBeans;
    private List<InspectionItemPositionBean> mChoosedPositionBeans;
    private String hospitalInfoCode;
    private String inspectionCode;
    private String positionName;
    private boolean isShowLoading;
    private int pos;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            pos=extras.getInt("pos");
            hospitalInfoCode = extras.getString("hospitalInfoCode");
            inspectionCode = extras.getString("inspectionCode");
            mChoosedPositionBeans=(List<InspectionItemPositionBean>)extras.getSerializable("positionList");
        }
        mPositionBeans=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inspection_position_choosed;
    }

    @Override
    protected void initView() {
        super.initView();

        setToolBar();
        //初始化Loading
        initLoadingAndRetryManager();
        //初始化RecyclerView
        initRecyclerView();
        //添加监听
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchInspectionPositionRequest(hospitalInfoCode
                ,inspectionCode,positionName,pageSize + "", pageIndex + "",this);
    }


    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();
            mPresenter.sendSearchInspectionPositionRequest(hospitalInfoCode
                    ,inspectionCode,positionName,pageSize + "", pageIndex + "",
                    InspectionPositionChoosedActivity.this);
        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvSearchBtn.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            positionName = edInputContent.getText().toString();
            if (!StringUtils.isNotEmpty(positionName)) {
                ToastUtils.showToast("请输入搜索内容");
                return;
            }
            pageIndex=1;
            isShowLoading=true;
            mPresenter.sendSearchInspectionPositionRequest(hospitalInfoCode
                    ,inspectionCode,positionName,pageSize + "", pageIndex + ""
                    ,InspectionPositionChoosedActivity.this);

        });
        tvEnsureBtn.setOnClickListener(v -> {
            List<InspectionItemPositionBean> choosedItemList =
                    mInspectionItemPostionAdapter.getChoosedItemList();
            if (CollectionUtils.isEmpty(choosedItemList)) {
                ToastUtils.showToast("请选择部位");
            }
            Bundle bundle=new Bundle();
            bundle.putSerializable("result", (Serializable) choosedItemList);
            bundle.putInt("pos",pos);
            Intent intent=new Intent();
            intent.putExtras(bundle);
            setResult(1001,intent);
            InspectionPositionChoosedActivity.this.finish();

        });
        edInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    pageIndex=1;
                    positionName="";
                    mPresenter.sendSearchInspectionPositionRequest(hospitalInfoCode
                            ,inspectionCode,positionName,pageSize + "",
                            pageIndex + "",
                            InspectionPositionChoosedActivity.this);
                }
            }
        });
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        mInspectionItemPostionAdapter=new InspectionItemPostionAdapter(this,mPositionBeans);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mInspectionItemPostionAdapter);
    }


    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("检查检验部位选择");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    @Override
    public void showLoading(int code) {
        if(isShowLoading){
            showLoading("",null);
            isShowLoading=false;
        }
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getSearchInspectionPositionResult(List<InspectionItemPositionBean>
                                                              inspectionItemPositionBeans) {

        if (pageIndex == 1) {
            mPositionBeans.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(inspectionItemPositionBeans)) {
            mPositionBeans.addAll(inspectionItemPositionBeans);
            handlerData(mPositionBeans,mChoosedPositionBeans);
            mInspectionItemPostionAdapter.setData(mPositionBeans);
            mInspectionItemPostionAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayoutManager.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayoutManager.showContent();

    }

    /**
     * 处理选中苏剧
     * @param mPositionBeans 全部部位列表
     * @param mChoosedPositionBeans 选中的部位列表
     */
    private void handlerData(List<InspectionItemPositionBean> mPositionBeans,
                             List<InspectionItemPositionBean> mChoosedPositionBeans){
        if (!CollectionUtils.isEmpty(mChoosedPositionBeans)) {
            for (InspectionItemPositionBean mPositionBean : mPositionBeans) {
                for (InspectionItemPositionBean mChoosedPositionBean : mChoosedPositionBeans) {
                    if (mPositionBean.getPositionCode()
                            .equals(mChoosedPositionBean.getPositionCode())) {
                        mPositionBean.setChoosed(true);
                    }
                }
            }
        }
    }

    @Override
    public void showEmpty() {
        if(pageIndex == 1){
            mLoadingLayoutManager.showEmpty();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    @Override
    public void showRetry() {
        if (pageIndex==1) {
            mLoadingLayoutManager.showError();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }
}
