package www.jykj.com.jykj_zxyl.capitalpool.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hyphenate.easeui.jykj.utils.DateUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.DrugDosageBean;
import www.jykj.com.jykj_zxyl.app_base.base_view.BaseToolBar;
import www.jykj.com.jykj_zxyl.app_base.base_view.LoadingLayoutManager;
import www.jykj.com.jykj_zxyl.app_base.http.ApiHelper;
import www.jykj.com.jykj_zxyl.app_base.http.CommonDataObserver;
import www.jykj.com.jykj_zxyl.app_base.http.ParameUtil;
import www.jykj.com.jykj_zxyl.app_base.http.RetrofitUtil;
import www.jykj.com.jykj_zxyl.app_base.mvp.AbstractMvpBaseActivity;

import www.jykj.com.jykj_zxyl.application.JYKJApplication;
import www.jykj.com.jykj_zxyl.capitalpool.adapter.WithdrawDetAdapter;
import www.jykj.com.jykj_zxyl.capitalpool.bean.WithdrawDetBean;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetContract;
import www.jykj.com.jykj_zxyl.capitalpool.contract.WithdrawDetPresenter;
import www.jykj.com.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.jykj.com.jykj_zxyl.util.GsonUtils;
import www.jykj.com.jykj_zxyl.util.Util;

/**
 * 医生提现明细列表
 */
public class WithdrawDetActivity extends AbstractMvpBaseActivity<WithdrawDetContract.View
        , WithdrawDetPresenter> implements WithdrawDetContract.View {

    @BindView(R.id.my_recycleview)
    RecyclerView myRecyeleview;
    @BindView(R.id.tv_date)
    TextView dateTv;
    @BindView(R.id.withdrawState_tv)
    TextView stateTv;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLyout;
    private BaseToolBar mToolBar;//顶部toolBar
    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private JYKJApplication mApp;
    private String countPeriod = "";
    private String withdrawalState = "0";
    private int pageNum = 1;
    private int rowNum = 10;
    private List<String> drugDosageNameList;
    private LoadingLayoutManager mLoadingLayout;
    private ImageButton imageButtonE;
    private List<BaseReasonBean> classBean;
    private int attrCode;
    private String baseName;
    private WithdrawDetAdapter withdrawDetAdapter;
    private List<WithdrawDetBean> mData = new ArrayList<>();
    @Override
    protected int setLayoutId() {
        return R.layout.activity_withdraw_det;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void initView() {
        super.initView();
        imageButtonE = findViewById(R.id.right_image_search);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        dateTv.setText(DateUtils.getDeviceTimeOfYM());
        countPeriod = DateUtils.getDeviceTimeOfYM();
        myRecyeleview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> strings = new ArrayList<>();
        mToolBar = findViewById(R.id.toolbar);
        setToolBar();
       // initLoadingAndRetryManager();
        addListener();
      /*  for (int i = 0; i < 8; i++) {
            strings.add("xx");
        }*/

    }

    private void addListener() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this)));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                mPresenter.getWithdrawDet(getParams());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                mPresenter.getWithdrawDet(getParams());
            }

        });
    }

    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mPresenter.getWithdrawDet(getParams());
        });
        mLoadingLayout.showLoading();
    }

    private void setToolBar() {
        mToolBar.setMainTitle("提现明细");
        //返回键
        mToolBar.setLeftTitleClickListener(view -> finish());
        //add
        mToolBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeaturesPopupWindow mPopupWindow = new MoreFeaturesPopupWindow(WithdrawDetActivity.this);
                if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(imageButtonE, 0, 0, Gravity.TOP + Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mApp = (JYKJApplication) getApplication();
        drugDosageNameList = getDrugDosageNameList();
        mPresenter.getWithdrawDet(getParams());

        //获取编码
        Obtain();
    }

    private void Obtain() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("baseCode", "900163");
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {

                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        int resCode = baseBean.getResCode();
                        Log.e("TAG", "onSuccessResult:  leixing " + baseBean.getResJsonData());
                        if (resCode == 1) {
                            String resJsonData = baseBean.getResJsonData();
                            List<BaseReasonBean> cancelContractBeans
                                    = GsonUtils.jsonToList(resJsonData, BaseReasonBean.class);
                            classBean = cancelContractBeans;
                        }
                    }
                });
    }

    private List<String> getstatusStrList(List<BaseReasonBean> classBean) {
        List<String> list = new ArrayList<>();
        for (BaseReasonBean baseReasonBean : classBean) {
            list.add(baseReasonBean.getAttrName());
        }
        return list;
    }


    private String getParams() {
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
        stringStringHashMap.put("requestClientType", "1");
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        stringStringHashMap.put("withdrawalState", withdrawalState);
        stringStringHashMap.put("countPeriod", countPeriod);
        stringStringHashMap.put("rowNum", String.valueOf(rowNum));
        stringStringHashMap.put("pageNum", String.valueOf(pageNum));
        Log.e("TAG", "getParams: " + stringStringHashMap.toString());
        return RetrofitUtil.encodeParam(stringStringHashMap);
    }

    @OnClick({R.id.withdraw_tv, R.id.tv_date, R.id.withdrawState_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.withdraw_tv:
                startActivity(new Intent(WithdrawDetActivity.this, WithdrawActivity.class));
                break;
            case R.id.tv_date:
                showCalendarDialog();
                break;
            case R.id.withdrawState_tv:
                showDrugDosageDialog();
                break;
        }
    }


    private void showCalendarDialog() {
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateTime = DateUtils.getDate(date);
                dateTv.setText(dateTime);
                countPeriod = dateTime;
                pageNum = 1;
                mPresenter.getWithdrawDet(getParams());
            }

        }).setCancelColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_vt)).setSubmitColor(getResources().getColor(com.hyphenate.easeui.R.color.textColor_hzgltabzc))
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "").build();
        timePickerView.show();
    }

    private void showDrugDosageDialog() {
        OptionsPickerView optionPickUnit = new OptionsPickerBuilder(this,
                (options1, options2, options3, v) -> {
                    attrCode = classBean.get(options1).getAttrCode();
                    baseName = classBean.get(options1).getAttrName();
                    stateTv.setText(baseName);
                    withdrawalState = attrCode + "";

                    pageNum = 1;
                    mPresenter.getWithdrawDet(getParams());
                })
                .setCancelColor(getResources().getColor(R.color.textColor_vt))
                .setSubmitColor(getResources().getColor(R.color.textColor_hzgltabzc))
                .setSelectOptions(0).build();

        optionPickUnit.setNPicker(getstatusStrList(classBean),
                null, null);
        optionPickUnit.show();
    }

    private List<String> getDrugDosageNameList() {
        List<String> list = new ArrayList<>();
        list.add("已提交");
        list.add("审核中");
        list.add("打款中");
        list.add("已完成");
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLaodData();
    }

    /**
     * 刷新数据
     */

    public void refreshLaodData() {
        pageNum = 1;
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginDoctorPosition", mApp.loginDoctorPosition);
        stringStringHashMap.put("operDoctorCode", mApp.mViewSysUserDoctorInfoAndHospital.getDoctorCode());
        stringStringHashMap.put("operDoctorName", mApp.mViewSysUserDoctorInfoAndHospital.getUserName());
        stringStringHashMap.put("withdrawalState", withdrawalState);
        stringStringHashMap.put("countPeriod", countPeriod);
        stringStringHashMap.put("rowNum", String.valueOf(rowNum));
        stringStringHashMap.put("pageNum", String.valueOf(pageNum));
        String s = RetrofitUtil.encodeParam(stringStringHashMap);
        mPresenter.getWithdrawDet(s);
    }

   /* @Override
    public void showRetry() {
        if (pageNum == 1) {
            mLoadingLayout.showEmpty();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }*/


    @Override
    public void getWithdrawDetResult(List<WithdrawDetBean> withdrawDetBeans) {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (withdrawDetBeans != null) {
            mRefreshLayout.setVisibility(View.VISIBLE);
            emptyLyout.setVisibility(View.GONE);
            if (withdrawDetAdapter == null) {
                mData.addAll(withdrawDetBeans);
                withdrawDetAdapter = new WithdrawDetAdapter(R.layout.item_withdraw_det, mData);
                myRecyeleview.setAdapter(withdrawDetAdapter);
            } else {
                if (pageNum == 1) {
                    mData.clear();
                }

                mData.addAll(withdrawDetBeans);
                withdrawDetAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getDetSize() {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (pageNum == 1) {
            emptyLyout.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        }
    }
}


