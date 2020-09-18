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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.utils.ToastUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemProjectBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.InspectionItemCategoryBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;
import www.jykj.com.jykj_zxyl.app_base.base_utils.StringUtils;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;
import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionProjectChoosedContract;
import www.jykj.com.jykj_zxyl.medicalrecord.InspectionProjectChoosedPresenter;
import www.jykj.com.jykj_zxyl.medicalrecord.adapter.InspectionItemProjectAdapter;
import www.jykj.com.jykj_zxyl.medicalrecord.popup.InspectionCategoryPopup;

/**
 * Description:检查检验项目选择
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 15:19
 */
public class InspectionProjectChoosedActivity extends AbstractMvpBaseActivity<
        InspectionProjectChoosedContract.View, InspectionProjectChoosedPresenter>
        implements InspectionProjectChoosedContract.View{
    @BindView(R.id.toolbar)
    BaseToolBar toolbar;
    @BindView(R.id.tv_title_type)
    TextView tvTitleType;
    @BindView(R.id.rl_choose_type)
    RelativeLayout rlChooseType;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.ed_input_content)
    EditText edInputContent;
    @BindView(R.id.tv_search_btn)
    TextView tvSearchBtn;
    @BindView(R.id.rl_search_title)
    RelativeLayout rlSearchTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_data_2)
    TextView tvNoData2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.viewl_top_line)
    View viewTopLine;
    @BindView(R.id.tv_ensure_btn)
    TextView tvEnsureBtn;
    private JYKJApplication mApp;
    private InspectionCategoryPopup inspectionCategoryPopup;//检查检验列表弹框
    private List<InspectionItemCategoryBean> mCategoryBeans;//检查检验类别
    private List<InspectionItemProjectBean> mInspectionItemBeans;//检查检验项目列表
    private LoadingLayoutManager mLoadingLayoutManager;//重新加载布局
    private InspectionItemProjectAdapter mInspectionItemAdapter;//检查检验项目适配器
    private InspectionItemProjectBean currentInspectionItemBean;//当前选中的检查检验项目
    private InspectionItemCategoryBean currentInspectionItemCategoryBean;//检查检验大类
    private String inspectionName;
    private boolean isShowLoading;
    private int pos;
    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            pos=extras.getInt("pos");
            currentInspectionItemBean=(InspectionItemProjectBean)extras.getSerializable
                    ("currentProject");
        }
        mCategoryBeans=new ArrayList<>();
        mInspectionItemBeans=new ArrayList<>();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inspection_project_choosed;
    }

    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) getApplication();
        inspectionCategoryPopup=new InspectionCategoryPopup(this);
        setToolBar();
        initLoadingAndRetryManager();
        initRecyclerView();
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.sendSearchInspectionProjectDetailClassListRequest(
                mApp.mViewSysUserDoctorInfoAndHospital.getHospitalInfoId()+"",this);
    }



    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolbar.setMainTitle("检查检验项目选择");
        //返回键
        toolbar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayoutManager = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayoutManager.setRetryListener(v -> {
            mLoadingLayoutManager.showLoading();

        });
        mLoadingLayoutManager.showLoading();

    }

    /**
     * 初始化Recyclerview
     */
    private void initRecyclerView(){
        mInspectionItemAdapter=new InspectionItemProjectAdapter(this
                ,mInspectionItemBeans);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mInspectionItemAdapter);
    }

    /**
     * 添加监听
     */
    private void addListener(){
        rlChooseType.setOnClickListener(v -> {
            inspectionCategoryPopup.show(viewTopLine);
            inspectionCategoryPopup.setData(mCategoryBeans);
        });

        inspectionCategoryPopup.setOnClickListener(categoryBean -> {
            currentInspectionItemCategoryBean=categoryBean;
            inspectionName = categoryBean.getInspectionName();
            mPresenter.sendSearchInspectionProjectDetailListRequest(
                    categoryBean.getHospitalInfoCode(), categoryBean.getInspectionCode()
                    , inspectionName,
                    pageSize + "", pageIndex + "",
                    InspectionProjectChoosedActivity.this);
        });



        tvEnsureBtn.setOnClickListener(v -> {
            currentInspectionItemBean = mInspectionItemAdapter.getCurrentChoosedItem();
            if(currentInspectionItemBean ==null){
                ToastUtils.showToast("请选择检查检验项目");
                return;
            }

            Bundle bundle=new Bundle();
            bundle.putSerializable("result",currentInspectionItemBean);
            bundle.putInt("pos",pos);
            Intent intent=new Intent();
            intent.putExtras(bundle);
            setResult(1000,intent);
            InspectionProjectChoosedActivity.this.finish();

        });
        tvSearchBtn.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(this);
            inspectionName = edInputContent.getText().toString();
            if (!StringUtils.isNotEmpty(inspectionName)) {
                ToastUtils.showToast("请输入搜索内容");
                return;
            }
            pageIndex=1;
            isShowLoading=true;
            mPresenter.sendSearchInspectionProjectDetailListRequest(
                    currentInspectionItemCategoryBean.getHospitalInfoCode()
                    , currentInspectionItemCategoryBean.getInspectionCode()
                    , inspectionName,
                    pageSize + "", pageIndex + "",
                    InspectionProjectChoosedActivity.this);
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
                if (TextUtils.isEmpty(s)) {
                    pageIndex=1;
                    inspectionName="";
                    mPresenter.sendSearchInspectionProjectDetailListRequest(
                            currentInspectionItemCategoryBean.getHospitalInfoCode()
                            , currentInspectionItemCategoryBean.getInspectionCode()
                            , inspectionName,
                            pageSize + "", pageIndex + "",
                            InspectionProjectChoosedActivity.this);
                }
            }
        });
    }







    @Override
    public void showLoading(int code) {
        if (isShowLoading) {
            showLoading("",null);
            isShowLoading=false;
        }
    }

    @Override
    public void hideLoading() {
       dismissLoading();
    }

    @Override
    public void getSearchInspectionProjectDetialClassListResult(
            List<InspectionItemCategoryBean> inspectionItemBeans) {
        mCategoryBeans = inspectionItemBeans;
        currentInspectionItemCategoryBean = inspectionItemBeans.get(0);
        inspectionName=currentInspectionItemCategoryBean.getInspectionName();
        mPresenter.sendSearchInspectionProjectDetailListRequest(
                currentInspectionItemCategoryBean.getHospitalInfoCode()
                , currentInspectionItemCategoryBean.getInspectionCode()
                , inspectionName,
                pageSize + "", pageIndex + "", this);

    }

    @Override
    public void getSearchInspectionProjectDetailListResult(
            List<InspectionItemProjectBean> inspectionItemBeans) {
        if (pageIndex == 1) {
            mInspectionItemBeans.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(inspectionItemBeans)) {
            mInspectionItemBeans.addAll(inspectionItemBeans);
            handlerData(currentInspectionItemBean);
            mInspectionItemAdapter.setData(mInspectionItemBeans);
            mInspectionItemAdapter.notifyDataSetChanged();
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
     * 处理数据
     *
     * @param currentInspectionItemBean 当前项目
     */
    private void handlerData(InspectionItemProjectBean currentInspectionItemBean) {
        if (currentInspectionItemBean != null) {
            for (InspectionItemProjectBean mInspectionItemBean : mInspectionItemBeans) {
                if (mInspectionItemBean.getInspectionCode()
                        .equals(currentInspectionItemBean.getInspectionCode())) {
                    mInspectionItemBean.setChoosed(true);
                } else {
                    mInspectionItemBean.setChoosed(false);
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
